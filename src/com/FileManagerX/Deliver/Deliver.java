package com.FileManagerX.Deliver;

public class Deliver {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
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
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static boolean broadcast(com.FileManagerX.Interfaces.ITransport t) {
		
		// Time is Out
		if(com.FileManagerX.Tools.Time.getTicks() - t.getBasicMessagePackage().getSendTime() > 
			t.getBasicMessagePackage().getPermitIdle()) {
			return true;
		}
		
		// depth + 1
		long prevMachine = t.getBasicMessagePackage().getRoutePathPackage().getDeliverMachine();
		t.getBasicMessagePackage().getRoutePathPackage().setMoreDepth();
		t.getBasicMessagePackage().getRoutePathPackage().add(0);
		
		// deliver to other leafs
		for(com.FileManagerX.Interfaces.IClientConnection con : 
			com.FileManagerX.Globals.Datas.OtherServers.getConnections()) {
			
			// next target
			long next = con.getServerMachineInfo().getIndex();
			
			// from this leaf, so will not send again.
			if(next == prevMachine) { continue; }
			
			// reset deliver machine
			t.getBasicMessagePackage().getRoutePathPackage().setDeliverMachine(next);
			con.send(t);
		}
		
		// deliver to server
		if(true) {
			com.FileManagerX.Interfaces.IClientConnection con = com.FileManagerX.Globals.Datas.ServerConnection;
			long next = con.getServerMachineInfo().getIndex();
			if(next != prevMachine) { 
				t.getBasicMessagePackage().getRoutePathPackage().setDeliverMachine(next);
				con.send(t);
			}
		}
		
		// return
		return true;
	}
	public final static boolean p2p(com.FileManagerX.Interfaces.ITransport t) {
		
		// Time is out
		if(com.FileManagerX.Tools.Time.getTicks() - t.getBasicMessagePackage().getSendTime() > 
			t.getBasicMessagePackage().getPermitIdle()) {
			return true;
		}
		
		// if found next connection, deliver by found.
		long nextMachine = t.getBasicMessagePackage().getRoutePathPackage().getDeliverMachine();
		if(true) {
			com.FileManagerX.Interfaces.IClientConnection con = 
					com.FileManagerX.Globals.Datas.Client.search(nextMachine);
			if(con != null) {
				t.getBasicMessagePackage().getRoutePathPackage().setMoreDepth();
				return con.send(t);
			}
		}
		
		// deliver to other servers
		t.getBasicMessagePackage().getRoutePathPackage().setMoreDepth();
		long prevMachine = nextMachine;
		for(com.FileManagerX.Interfaces.IClientConnection con : 
			com.FileManagerX.Globals.Datas.OtherServers.getConnections()) {
			
			// next target
			long next = con.getServerMachineInfo().getIndex();
			
			// from this leaf, so will not send again.
			if(next == prevMachine) { continue; }
			
			// reset deliver machine
			t.getBasicMessagePackage().getRoutePathPackage().setDeliverMachine(next);
			return con.send(t);
		}
		
		// deliver to server
		if(true) {
			com.FileManagerX.Interfaces.IClientConnection con = com.FileManagerX.Globals.Datas.ServerConnection;
			long next = con.getServerMachineInfo().getIndex();
			if(next != prevMachine) { 
				t.getBasicMessagePackage().getRoutePathPackage().setDeliverMachine(next);
				con.send(t);
			}
		}
		
		// not found any server to deal this transport, discard it.
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
