package com.FileManagerX.Socket;

import java.io.IOException;

public class Client_IPV4_TCP implements com.FileManagerX.Interfaces.ISocketC {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private com.FileManagerX.BasicEnums.SocketType type;
	private java.net.Socket socket;
	private com.FileManagerX.BasicModels.MachineInfo server;
	private com.FileManagerX.BasicModels.MachineInfo client;
	
	private java.io.DataOutputStream dos;
	private java.io.DataInputStream dis;
	
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
		if(!(socket instanceof java.net.Socket)) {
			return false;
		}
		
		this.socket = (java.net.Socket)socket;
		this.setClientMachineInfo();
		return true;
	}
	public boolean setSocket() {
		try {
			java.net.InetAddress ip = java.net.InetAddress.getByName(this.server.getIp());
			int port = this.server.getPort();
			this.socket = new java.net.Socket(ip,port);
			this.setClientMachineInfo();
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
	public boolean setClientMachineInfo() {
		if(this.socket == null) {
			return false;
		}
		
		try {
			byte[] mac = java.net.NetworkInterface.getByInetAddress(this.socket.getInetAddress()).
					getHardwareAddress();
			this.client = new com.FileManagerX.BasicModels.MachineInfo();
			this.client.copyValue(this.server);
			this.client.setUserIndex(com.FileManagerX.Globals.Configurations.This_MachineIndex);
			this.client.setMac(mac);
			this.client.setIp(this.socket.getInetAddress().getHostAddress());
			this.client.setPort(this.socket.getPort());
			return true;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			return false;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.BasicEnums.SocketType getType() {
		return this.type;
	}
	public java.net.Socket getSocket() {
		return this.socket;
	}
	public com.FileManagerX.BasicModels.MachineInfo getServerMachineInfo() {
		return this.server;
	}
	public com.FileManagerX.BasicModels.MachineInfo getClientMachineInfo() {
		return this.client;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Client_IPV4_TCP() {
		initThis();
	}
	private void initThis() {
		this.type = com.FileManagerX.BasicEnums.SocketType.IPV4_TCP;
		this.socket = null;
		this.server = null;
		this.client = null;
		this.dis = null;
		this.dos = null;
	}
	
	public boolean setThis(com.FileManagerX.BasicModels.MachineInfo server, 
			com.FileManagerX.BasicModels.MachineInfo client) {
		boolean ok = true;
		ok &= this.setServerMachineInfo(server);
		ok &= this.setClientMachineInfo(client);
		return ok;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean isClosed() {
		return socket == null ? true : socket.isClosed();
	}
	public boolean isRunning() {
		return socket == null ? true : socket.isConnected();
	}
	public int receive(byte[] bytes) throws IOException {
		if(dis == null) { dis = new java.io.DataInputStream(socket.getInputStream()); }
		return dis.read(bytes);
	}
	public boolean send(byte[] bytes) throws IOException {
		if(dos == null) { dos = new java.io.DataOutputStream(socket.getOutputStream()); }
		dos.write(bytes);
		dos.flush();
		return true;
	}
	public boolean close() {
		try {
			dis.close();
		} catch(Exception e) {
			;
		}
		try {
			dos.close();
		} catch(Exception e) {
			;
		}
		try {
			socket.close();
		} catch(Exception e) {
			;
		}
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
