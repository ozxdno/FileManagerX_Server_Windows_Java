package com.FileManagerX.Deliver;

public class Deliver {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public final static boolean deliver(com.FileManagerX.Interfaces.ITransport t) {
		
		boolean bc = t.getBasicMessagePackage().getBroadcast().broadcast();
		return bc ? broadcast(t) : p2p(t);
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
		t.getBasicMessagePackage().getRoutePathPackage().addDeliverMachine(0);
		
		long gateMachine = t.getBasicMessagePackage().getBroadcast().getDestMachine();
		
		// 分发给子连接
		for(com.FileManagerX.Interfaces.IClientConnection con : 
			com.FileManagerX.Globals.Datas.Client.getContent()) {
			long next = con.getServerMachineInfo().getIndex();
			
			if(next == prevMachine) { continue; }
			if(t.getBasicMessagePackage().getRoutePathPackage().passed(gateMachine)) { continue; }
			
			t.getBasicMessagePackage().getRoutePathPackage().setDeliverMachine(next);
			con.send(t);
		}
		
		return true;
	}
	
	public final static boolean p2p(com.FileManagerX.Interfaces.ITransport t) {
		
		// Time is out
		if(com.FileManagerX.Tools.Time.getTicks() - t.getBasicMessagePackage().getSendTime() > 
			t.getBasicMessagePackage().getPermitIdle()) {
			return true;
		}
		
		//
		com.FileManagerX.Interfaces.IRoutePathPackage rrp = t.getBasicMessagePackage().getRoutePathPackage();
		com.FileManagerX.Interfaces.IClientConnection con = null;
		long sourMachine = 0;
		long prevMachine = 0;
		long nextMachine = 0;
		
		// 获取信息
		if(rrp.getDepth() > 1) {
			prevMachine = rrp.getDeliverMachine(rrp.getDepth()-1);
		}
		rrp.backToMachine();
		if(rrp.getDepth() > 1) {
			sourMachine = rrp.getDeliverMachine(rrp.getDepth()-1);
		}
		
		// 第一次到达该节点，判断该节点下有没有目标机器。
		if(sourMachine == 0 || sourMachine == prevMachine) {
			nextMachine = rrp.getDestMachine();
			con = com.FileManagerX.Globals.Datas.Client.searchMachineIndex(
					nextMachine,
					com.FileManagerX.BasicEnums.ConnectionType.TRANSPORT_COMMAND
				);
			if(con != null) { return con.send(t); }
		}
		
		// 获取下一个连接。
		if(sourMachine == 0 || sourMachine == prevMachine) {
			if(com.FileManagerX.Globals.Datas.OtherServers.size() == 0) {
				long server = com.FileManagerX.Globals.Configurations.Server_MachineIndex;
				if(server == sourMachine) {
					con = null;
				}
				else {
					con = com.FileManagerX.Globals.Datas.ServerConnection;
				}
			}
			else {
				con = com.FileManagerX.Globals.Datas.OtherServers.getContent().get(0);
				if(con.getServerMachineInfo().getIndex() == sourMachine) {
					if(com.FileManagerX.Globals.Datas.OtherServers.size() == 1) {
						con = com.FileManagerX.Globals.Datas.ServerConnection;
					}
					else {
						con = com.FileManagerX.Globals.Datas.OtherServers.getContent().get(1);
					}
				}
			}
		}
		else {
			for(int i=0; i<com.FileManagerX.Globals.Datas.OtherServers.size(); i++) {
				com.FileManagerX.Interfaces.IClientConnection c = 
						com.FileManagerX.Globals.Datas.OtherServers.getContent().get(i);
				if(c != t.getConnection()) {
					continue;
				}
				if(i == com.FileManagerX.Globals.Datas.OtherServers.size()-1) {
					long server = com.FileManagerX.Globals.Configurations.Server_MachineIndex;
					if(server == sourMachine) {
						con = null;
					}
					else {
						con = com.FileManagerX.Globals.Datas.ServerConnection;
					}
				}
				else {
					con = com.FileManagerX.Globals.Datas.OtherServers.getContent().get(i);
				}
			}
		}
		
		// 找到了
		if(con != null && con.isRunning()) {
			rrp.setMoreDepth();
			rrp.addDeliverMachine(con.getServerMachineInfo().getIndex());
			return con.send(t);
		}
		
		// 又到了初始目标
		if(com.FileManagerX.Globals.Configurations.This_MachineIndex == rrp.getSourMachine()) {
			if(t instanceof com.FileManagerX.Interfaces.IReply) { // discard
				return true;
			}
			com.FileManagerX.Commands.BaseCommand cmd = (com.FileManagerX.Commands.BaseCommand)t;
			cmd.getReply().setFailedReason(com.FileManagerX.Commands.BaseCommand.FAILED_NO_TARGET);
			cmd.getReply().setOK(false);
			cmd.getReply().send();
			return true;
		}
		
		// 返回源目标
		con = com.FileManagerX.Globals.Datas.Client.searchMachineIndex(
				sourMachine,
				com.FileManagerX.BasicEnums.ConnectionType.TRANSPORT_COMMAND
			);
		if(con != null && con.isRunning()) {
			rrp.setMoreDepth();
			rrp.addDeliverMachine(con.getServerMachineInfo().getIndex());
			return con.send(t);
		}
		
		// 从源处来，返回去却找不到源了，丢弃。
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
