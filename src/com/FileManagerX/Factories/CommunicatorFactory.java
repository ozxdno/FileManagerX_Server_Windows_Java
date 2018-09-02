package com.FileManagerX.Factories;

public class CommunicatorFactory {

	public final static com.FileManagerX.Interfaces.IBasicMessagePackage createBMP() {
		return new com.FileManagerX.Communicator.BasicMessagePackage();
	}
	public final static com.FileManagerX.Interfaces.IIOPackage createIOP() {
		return new com.FileManagerX.Communicator.IOPackage();
	}
	public final static com.FileManagerX.Interfaces.IRoutePathPackage createRRP() {
		return new com.FileManagerX.Communicator.RoutePathPackage();
	}
	
	public final static com.FileManagerX.Interfaces.IScanner createScanner() {
		com.FileManagerX.Interfaces.IScanner s = new com.FileManagerX.Communicator.Scanner();
		return s;
	}
	
	public final static com.FileManagerX.Interfaces.IServerConnection createServerConnection() {
		com.FileManagerX.Interfaces.IServerConnection s = new com.FileManagerX.Communicator.ServerConnection();
		return s;
	}
	
	public final static com.FileManagerX.Interfaces.IClientConnection createClientConnection() {
		com.FileManagerX.Interfaces.IClientConnection c = new com.FileManagerX.Communicator.ClientConnection();
		return c;
	}
	
	public final static boolean createRunningClientConnection() {
		
		boolean ok = true;
		
		com.FileManagerX.Globals.Datas.ServerConnection.setServerMachineInfo(
				com.FileManagerX.Globals.Datas.ServerMachine);
		com.FileManagerX.Globals.Datas.ServerConnection.setClientMachineInfo(
				com.FileManagerX.Globals.Datas.ThisMachine);
		com.FileManagerX.Globals.Datas.ServerConnection.setServerUser(
				com.FileManagerX.Globals.Datas.ServerUser);
		com.FileManagerX.Globals.Datas.ServerConnection.setClientUser(
				com.FileManagerX.Globals.Datas.ThisUser);
		com.FileManagerX.Globals.Datas.ServerConnection.setType(
				com.FileManagerX.BasicEnums.ConnectionType.TRANSPORT_COMMAND);
		
		if(com.FileManagerX.Globals.Datas.ServerConnection.isRunning()) { return true; }
		
		com.FileManagerX.Globals.Datas.ServerConnection.setSocket(com.FileManagerX.BasicEnums.SocketType.IPV4_TCP);
		com.FileManagerX.Globals.Datas.ServerConnection.startProcess();
		ok = com.FileManagerX.Globals.Datas.ServerConnection.isRunning();
		if(!ok) { return false; }
		
		com.FileManagerX.Interfaces.IServerConnection scon = createServerConnection();
		scon.setServerMachineInfo(com.FileManagerX.Globals.Datas.ThisMachine);
		scon.setClientMachineInfo(com.FileManagerX.Globals.Datas.ServerMachine);
		scon.setServerUser(com.FileManagerX.Globals.Datas.ThisUser);
		scon.setClientUser(com.FileManagerX.Globals.Datas.ServerUser);
		scon.setType(com.FileManagerX.BasicEnums.ConnectionType.TRANSPORT_COMMAND);
		
		scon.setSocket(com.FileManagerX.Globals.Datas.ServerConnection.getSocket());
		scon.startProcess();
		ok = scon.isRunning();
		if(!ok) { com.FileManagerX.Globals.Datas.ServerConnection.exitProcess(); return false; }
		
		com.FileManagerX.Globals.Datas.ServerConnection.setBrother(scon);
		scon.setBrother(com.FileManagerX.Globals.Datas.ServerConnection);
		
		return ok;
	}
	public final static com.FileManagerX.Interfaces.IClientConnection createRunningClientConnection(
			com.FileManagerX.BasicModels.MachineInfo server,
			com.FileManagerX.BasicModels.MachineInfo client,
			com.FileManagerX.BasicEnums.SocketType type
			) {
		com.FileManagerX.Interfaces.IClientConnection ccon = createClientConnection();
		ccon.setServerMachineInfo(server);
		ccon.setClientMachineInfo(client);
		ccon.setSocket(type);
		ccon.startProcess();
		boolean ok = ccon.isRunning();
		
		if(ok) {
			com.FileManagerX.Interfaces.IServerConnection scon = createServerConnection();
			scon.setServerMachineInfo(client);
			scon.setClientMachineInfo(server);
			scon.setSocket(ccon.getSocket());
			scon.startProcess();
			ok = scon.isRunning();
			if(!ok) { ccon.exitProcess(); return null; }
			
			ccon.setBrother(scon);
			scon.setBrother(ccon);
		}
		return ok ? ccon : null;
	}
	
}
