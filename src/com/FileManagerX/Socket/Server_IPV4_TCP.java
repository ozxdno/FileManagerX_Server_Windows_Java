package com.FileManagerX.Socket;

import java.io.IOException;

public class Server_IPV4_TCP implements com.FileManagerX.Interfaces.ISocketS {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private com.FileManagerX.BasicEnums.SocketType type;
	private java.net.ServerSocket socket;
	private com.FileManagerX.BasicModels.MachineInfo server;
	private com.FileManagerX.BasicModels.MachineInfo client;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setType(com.FileManagerX.BasicEnums.SocketType type) {
		if(type == null) {
			return false;
		}
		this.type = type;
		return true;
	}
	public boolean setSocket(Object socket) {
		if(socket == null) {
			return false;
		}
		if(!(socket instanceof java.net.ServerSocket)) {
			return false;
		}
		
		this.socket = (java.net.ServerSocket)socket;
		return true;
	}
	public boolean setSocket() {
		try {
			int port = this.server.getPort();
			this.socket = new java.net.ServerSocket(port);
			return true;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.COMMUNICATOR_BUILD_SOCKED_FAILED.register(e.toString());
			return false;
		}
	}
	public boolean setServerMachineInfo(com.FileManagerX.BasicModels.MachineInfo server) {
		if(server == null) {
			return false;
		}
		this.server = server;
		return true;
	}
	public boolean setClientMachineInfo(com.FileManagerX.BasicModels.MachineInfo client) {
		if(client == null) {
			return false;
		}
		this.client = client;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public com.FileManagerX.BasicEnums.SocketType getType() {
		return this.type;
	}
	public java.net.ServerSocket getSocket() {
		return this.socket;
	}
	public com.FileManagerX.BasicModels.MachineInfo getServerMachineInfo() {
		return this.server;
	}
	public com.FileManagerX.BasicModels.MachineInfo getClientMachineInfo() {
		return this.client;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Server_IPV4_TCP() {
		initThis();
	}
	private void initThis() {
		this.type = com.FileManagerX.BasicEnums.SocketType.IPV4_TCP;
		this.socket = null;
		this.server = new com.FileManagerX.BasicModels.MachineInfo();
		this.client = new com.FileManagerX.BasicModels.MachineInfo();
	}
	
	public boolean setThis(com.FileManagerX.BasicModels.MachineInfo server, 
			com.FileManagerX.BasicModels.MachineInfo client) {
		boolean ok = true;
		ok &= this.setServerMachineInfo(server);
		ok &= this.setClientMachineInfo(client);
		ok &= this.setSocket();
		return ok;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean isClosed() {
		return socket == null ? true : socket.isClosed();
	}
	public boolean isRunning() {
		return socket == null ? true : socket.isBound() && !socket.isClosed();
	}
	public Client_IPV4_TCP receive() throws IOException {
		java.net.Socket socket = this.socket.accept();
		Client_IPV4_TCP socketc = new Client_IPV4_TCP();
		socketc.setSocket(socket);
		socketc.setType(type);
		socketc.setServerMachineInfo(server);
		return socketc;
	}
	public boolean close() {
		try {
			socket.close();
		} catch(Exception e) {
			;
		}
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
