package com.FileManagerX.Communicator;

/**
 * 封装一个 Socket ，该线程只负责接收信息。
 * 
 * @author ozxdno
 *
 */
public class ServerConnection extends com.FileManagerX.Processes.BasicProcess
	implements com.FileManagerX.Interfaces.IServerConnection {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
	public final static String ERROR_SOCKET_CLOSED = "Not Open Socket";

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private com.FileManagerX.BasicModels.MachineInfo serverMachineInfo;
	private com.FileManagerX.BasicModels.MachineInfo clientMachineInfo;
	private com.FileManagerX.BasicModels.User serverUser;
	private com.FileManagerX.BasicModels.User clientUser;
	private com.FileManagerX.BasicEnums.ConnectionType type;
	private long index;
	
	private com.FileManagerX.Interfaces.ISocketC socket;
	
	private String receiveString;
	private String sendString;
	private com.FileManagerX.Interfaces.IConnection brother;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setServerMachineInfo(com.FileManagerX.BasicModels.MachineInfo serverMachineInfo) {
		if(serverMachineInfo == null) {
			return false;
		}
		this.serverMachineInfo = serverMachineInfo;
		return true;
	}
	public boolean setClientMachineInfo(com.FileManagerX.BasicModels.MachineInfo clientMachineInfo) {
		if(clientMachineInfo == null) {
			return false;
		}
		this.clientMachineInfo = clientMachineInfo;
		return true;
	}
	public boolean setServerUser(com.FileManagerX.BasicModels.User user) {
		if(user == null) {
			return false;
		}
		this.serverUser = user;
		return true;
	}
	public boolean setClientUser(com.FileManagerX.BasicModels.User user) {
		if(user == null) {
			return false;
		}
		this.clientUser = user;
		return true;
	}
	public boolean setType(com.FileManagerX.BasicEnums.ConnectionType type) {
		if(type == null) {
			return false;
		}
		this.type = type;
		return true;
	}
	public boolean setIndex(long index) {
		this.index = index;
		return true;
	}
	public boolean setIndex() {
		this.index = com.FileManagerX.Globals.Configurations.Next_ConnectionIndex();
		return true;
	}
	public boolean setName() {
		if(socket == null) {
			return false;
		}
		if(socket.getServerMachineInfo() == null || socket.getClientMachineInfo() == null) {
			return false;
		}
		
		String name = "<-" + 
				socket.getClientMachineInfo().getIp() + ":" +
				socket.getClientMachineInfo().getPort();
		return super.setName(name);
	}
	
	public boolean setReceiveString(String str) {
		if(str == null) {
			return false;
		}
		this.receiveString = str;
		return true;
	}
	public boolean setSendString(String str) {
		if(str == null) {
			return false;
		}
		this.sendString = str;
		return true;
	}
	
	public boolean setSocket(com.FileManagerX.Interfaces.ISocketC socket) {
		if(socket == null) {
			return false;
		}
		this.socket = socket;
		this.setName();
		return true;
	}
	public boolean setSocket(com.FileManagerX.BasicEnums.SocketType type) {
		this.socket = type.getSocketC();
		this.socket.setServerMachineInfo(this.clientMachineInfo);
		boolean ok = this.socket.setSocket();
		this.setName();
		return ok;
	}
	
	public boolean setBrother(com.FileManagerX.Interfaces.IConnection brother) {
		if(brother == null) {
			return false;
		}
		this.brother = brother;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.BasicModels.MachineInfo getServerMachineInfo() {
		return this.serverMachineInfo;
	}
	public com.FileManagerX.BasicModels.MachineInfo getClientMachineInfo() {
		return this.clientMachineInfo;
	}
	public com.FileManagerX.BasicModels.User getServerUser() {
		return this.serverUser;
	}
	public com.FileManagerX.BasicModels.User getClientUser() {
		return this.clientUser;
	}
	public com.FileManagerX.BasicEnums.ConnectionType getType() {
		return this.type;
	}
	public long getIndex() {
		return this.index;
	}
	
	public com.FileManagerX.Interfaces.ISocketC getSocket() {
		return this.socket;
	}
	
	public String getReceiveString() {
		return this.receiveString;
	}
	public String getSendString() {
		return this.sendString;
	}
	
	public com.FileManagerX.Interfaces.IConnection getBrother() {
		return this.brother;
	}
	public com.FileManagerX.Interfaces.IServerConnection getServerConnection() {
		return this;
	}
	public com.FileManagerX.Interfaces.IClientConnection getClientConnection() {
		return (com.FileManagerX.Interfaces.IClientConnection)this.brother;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public ServerConnection() {
		initThis();
	}
	private void initThis() {
		this.serverMachineInfo = new com.FileManagerX.BasicModels.MachineInfo();
		this.clientMachineInfo = new com.FileManagerX.BasicModels.MachineInfo();
		this.serverUser = null;
		this.clientUser = null;
		this.type = com.FileManagerX.BasicEnums.ConnectionType.TRANSPORT_COMMAND;
		
		this.socket = null;
		this.setRunnable(new RunImpl());
		this.setPermitIdle(com.FileManagerX.Globals.Configurations.TimeForPermitIdle_Server);
		
		this.receiveString = null;
		this.sendString = null;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean send(com.FileManagerX.Interfaces.ITransport t) {
		return this.brother == null ? false : this.brother.send(t);
	}
	public String login() {
		return this.brother == null ? "Brother is NULL" : brother.login();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private class RunImpl implements com.FileManagerX.Processes.BasicProcess.Runnable {
		public String run() {
			if(socket == null) {
				ServerConnection.this.setSocket(com.FileManagerX.BasicEnums.SocketType.IPV4_TCP);
			}
			
			if(socket.isClosed()) {
				ServerConnection.this.stopProcess();
				if(brother != null) { brother.stopProcess(); }
				return ERROR_SOCKET_CLOSED;
			}
			
			java.util.LinkedList<Byte> buffer = new java.util.LinkedList<>();
			byte[] recebytes = new byte[(int) com.FileManagerX.Globals.Configurations.LimitForConnectionBuffer];
			
			while(!isAbort() && !isStop() && !socket.isClosed()) {
				try {
					int length = socket.receive(recebytes);
					if(length < 0) {
						ServerConnection.this.stopProcess();
						if(brother != null) { brother.stopProcess(); }
						socket.close();
						return ERROR_SOCKET_CLOSED;
					}
					
					for(int i=0; i<length; i++) {
						if(recebytes[i] == com.FileManagerX.BasicEnums.EOF.N.getSpecial()) {
							byte[] temp = new byte[buffer.size()];
							int cnt = 0;
							for(Byte b : buffer) { temp[cnt++] = b; }
							buffer.clear();
							
							com.FileManagerX.Interfaces.ITransport t = null;
							t = com.FileManagerX.Coder.Decoder.Decode_Byte2Transport(temp);
							if(t == null) { continue; }
							
							if(com.FileManagerX.Globals.Configurations.Record) {
								com.FileManagerX.Deliver.Deliver.printTransport(t, "Rece");
							}
							
							t.setSourConnection(ServerConnection.this);
							com.FileManagerX.Deliver.Deliver.completeTimeInReceiver(t);
							com.FileManagerX.Globals.Datas.Sender.add(t);
						}
						else {
							buffer.add(recebytes[i]);
						}
					}
					
				} catch(Exception e) {
					com.FileManagerX.BasicEnums.ErrorType.COMMUNICATOR_RUNNING_FAILED.register(
							"[" + getName() + "] " + e.toString());
					ServerConnection.this.stopProcess();
					if(brother != null) { brother.stopProcess(); }
					socket.close();
					return e.toString();
				}
			}
			
			ServerConnection.this.stopProcess();
			if(brother != null) { brother.stopProcess(); }
			socket.close();
			return null;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}