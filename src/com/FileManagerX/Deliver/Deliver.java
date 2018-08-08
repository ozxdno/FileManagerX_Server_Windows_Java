package com.FileManagerX.Deliver;

public class Deliver {
	
	public final static boolean deliver(com.FileManagerX.Interfaces.ITransport t) {
		if(t.getBasicMessagePackage().isBroadCast() && (t instanceof com.FileManagerX.Interfaces.ICommand)) {
			return broadcast(t);
		}
		else {
			return p2p(t);
		}
	}
	
	public final static boolean deliverToServer(com.FileManagerX.Interfaces.ITransport t) {
		t.getBasicMessagePackage().setDestMachineIndex(
				com.FileManagerX.Globals.Configurations.Server_MachineIndex);
		t.getBasicMessagePackage().setDestUserIndex(
				com.FileManagerX.Globals.Configurations.Server_UserIndex);
		
		return com.FileManagerX.Globals.Datas.ServerConnection.send(t);
	}
	
	public final static boolean broadcast(com.FileManagerX.Interfaces.ITransport t) {
		if(com.FileManagerX.Tools.Time.getTicks() - t.getBasicMessagePackage().getSendTime() > 
			t.getBasicMessagePackage().getPermitIdle()) {
			return true;
		}
		
		for(com.FileManagerX.Interfaces.IClientConnection con : com.FileManagerX.Globals.Datas.Client.getConnections()) {
			t.getBasicMessagePackage().getRoutePathPackage().add(con.getServerMachineInfo().getIndex());
			t.getBasicMessagePackage().getRoutePathPackage().setMoreDepth();
			con.send(t);
		}
		return true;
	}
	public final static boolean p2p(com.FileManagerX.Interfaces.ITransport t) {
		if(com.FileManagerX.Tools.Time.getTicks() - t.getBasicMessagePackage().getSendTime() > 
			t.getBasicMessagePackage().getPermitIdle()) {
			return true;
		}
		
		long nextMachine = t.getBasicMessagePackage().getRoutePathPackage().getDeliverMachine();
		t.getBasicMessagePackage().getRoutePathPackage().setMoreDepth();
		
		com.FileManagerX.Interfaces.IClientConnection con = 
				com.FileManagerX.Globals.Datas.Client.search(nextMachine);
		if(con == null) {
			con = search(t);
		}
		
		if(con == null) {
			com.FileManagerX.BasicEnums.ErrorType.COMMUNICATOR_SEND_FAILED.register("Not Found Route Path");
			return false;
		}
		
		return con.send(t);
	}
	
	private final static com.FileManagerX.Interfaces.IClientConnection search(com.FileManagerX.Interfaces.ITransport t) {
		com.FileManagerX.Interfaces.IRoutePathPackage rrp = t.getBasicMessagePackage().getRoutePathPackage();
		long destMachine = t.getBasicMessagePackage().getDestMachineIndex();
		
		for(com.FileManagerX.MyNet.User u : com.FileManagerX.Factories.MyNetFactory.createHideGroup().getUsers()) {
			for(com.FileManagerX.MyNet.Machine m : u.getMachines()) {
				if(m.getMachine().getIndex() == destMachine) {
					rrp = m.getRoutePathPackage();
					rrp.setDepth(1);
					break;
				}
			}
		}
		
		com.FileManagerX.Interfaces.IClientConnection con = null;
		long deliverMachine = rrp.getDeliverMachine();
		if(rrp.getDeliverMachine() > 0) {
			con = com.FileManagerX.Globals.Datas.Client.search(deliverMachine);
		}
		if(con == null) {
			con = com.FileManagerX.Globals.Datas.ServerConnection;
		}
		if(!con.isRunning()) {
			com.FileManagerX.BasicEnums.ErrorType.COMMUNICATOR_SEND_FAILED.register
				("Not Found Next Route Path: MachineIndex -> "  + deliverMachine);
			return null;
		}
		
		return con;
	}
}
