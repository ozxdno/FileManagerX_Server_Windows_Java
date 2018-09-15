package com.FileManagerX.Communicator;

/**
 * 封装一个 Socket ，该线程只负责发送信息。
 * 
 * @author ozxdno
 *
 */
public class ClientConnection extends com.FileManagerX.Processes.BasicProcess
	implements com.FileManagerX.Interfaces.IClientConnection {

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
	
	private java.util.LinkedList<com.FileManagerX.Interfaces.ITransport> sends;
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
		if(this.socket == null || this.socket.isClosed()) {
			return false;
		}
		if(socket.getServerMachineInfo() == null || socket.getClientMachineInfo() == null) {
			return false;
		}
		
		String name = "->" + 
				socket.getClientMachineInfo().getIp() + 
				":" +
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
		this.socket.setServerMachineInfo(this.serverMachineInfo);
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
		return (com.FileManagerX.Interfaces.IServerConnection)this.brother;
	}
	public com.FileManagerX.Interfaces.IClientConnection getClientConnection() {
		return this;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public ClientConnection() {
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
		this.setPermitIdle(com.FileManagerX.Globals.Configurations.TimeForPermitIdle_Client);
		
		this.receiveString = null;
		this.sendString = null;
		
		this.sends = new java.util.LinkedList<>();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean send(com.FileManagerX.Interfaces.ITransport t) {
		if(!this.isRunning() || this.isFinished()) { return false; }
		return sends.add(t);
	}
	private com.FileManagerX.Interfaces.ITransport poll() {
		return sends.size() == 0 ? null : sends.removeFirst();
	}
	
	public String login() {
		com.FileManagerX.Interfaces.IReply reply = null;
		
		com.FileManagerX.Commands.LoginServer ls = new com.FileManagerX.Commands.LoginServer();
		ls.setDestConnection(this);
		ls.setThis(this.getServerMachineInfo());
		ls.send();
		reply = ls.receive(false);
		if(reply == null) { return "LoginServer Failed: reply NULL"; }
		if(!reply.isOK() && !com.FileManagerX.Replies.LoginServer.FAILED_SERVER_CHANGED.
				equals(reply.getFailedReason())) { 
			return reply.getFailedReason();
		}
		
		if(!reply.isOK()) {
			com.FileManagerX.Tools.Time.sleepUntil(10);
			boolean ok = this.setSocket(this.getSocket().getType());
			if(!ok || this.brother == null) { return "LoginServer Failed: No Server"; }
			this.brother.setSocket(this.getSocket());
			this.restartProcess();
			this.brother.restartProcess();
			com.FileManagerX.Tools.Time.sleepUntil(1000);
		}
		
		if(com.FileManagerX.Globals.Configurations.Refresh) {
			com.FileManagerX.Tools.CFG.Register.load(com.FileManagerX.Globals.Datas.CFG);
		}
		
		com.FileManagerX.Commands.LoginUser lu = new com.FileManagerX.Commands.LoginUser();
		lu.setDestConnection(this);
		lu.setThis(this.getServerUser(), this.getClientUser());
		lu.send();
		reply = lu.receive();
		if(reply == null) { return "LoginUser Failed: reply NULL"; }
		if(!reply.isOK()) { return reply.getFailedReason(); }
		
		com.FileManagerX.Commands.LoginMachine lm = new com.FileManagerX.Commands.LoginMachine();
		lm.setDestConnection(this);
		lm.setThis(com.FileManagerX.Globals.Datas.ThisMachine);
		lm.send();
		reply = lm.receive();
		if(reply == null) { return "LoginMachine Failed: reply NULL"; }
		if(!reply.isOK()) { return reply.getFailedReason(); }
		
		com.FileManagerX.Commands.LoginType lt = new com.FileManagerX.Commands.LoginType();
		com.FileManagerX.BasicEnums.ConnectionType x = com.FileManagerX.BasicEnums.ConnectionType.ANY;
		x = com.FileManagerX.Globals.Configurations.IsServer ?
				x.and(x, com.FileManagerX.BasicEnums.ConnectionType.S2X) :
				x.and(x, com.FileManagerX.BasicEnums.ConnectionType.C2X);
		x = x.and(x, com.FileManagerX.BasicEnums.ConnectionType.TRANSPORT_COMMAND);
		lt.setThis(x);
		lt.send();
		reply = lt.receive();
		if(reply == null) { return "LoginType Failed: reply NULL"; }
		if(!reply.isOK()) { return reply.getFailedReason(); }
		
		com.FileManagerX.Commands.LoginConnection lc = new com.FileManagerX.Commands.LoginConnection();
		lc.send();
		reply = lc.receive();
		if(reply == null) { return "LoginConnection Failed: reply NULL"; }
		if(!reply.isOK()) { return reply.getFailedReason(); }
		
		return null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private class RunImpl implements com.FileManagerX.Processes.BasicProcess.Runnable {
		public String run() {
			if(socket == null) {
				ClientConnection.this.setSocket(com.FileManagerX.BasicEnums.SocketType.IPV4_TCP);
			}
			
			if(socket.isClosed()) {
				ClientConnection.this.stopProcess();
				if(brother != null) { brother.stopProcess(); }
				return ERROR_SOCKET_CLOSED;
			}
			
			while(!isAbort() && !isStop() && !socket.isClosed()) {
				try {
					// 取出优先级最高的命令
					com.FileManagerX.Interfaces.ITransport send = ClientConnection.this.poll();
					if(send == null) {
						com.FileManagerX.Tools.Time.sleepUntil(10);
						continue;
					}
					
					// 打印命令
					if(com.FileManagerX.Globals.Configurations.Record) {
						com.FileManagerX.Deliver.Deliver.printTransport(send, "Send");
					}
					
					// 发送命令
					com.FileManagerX.Deliver.Deliver.completeTimeInSender(send);
					socket.send(com.FileManagerX.Coder.Encoder.Encode_Transport2Byte(send));
					
					
				} catch(Exception e) {
					com.FileManagerX.BasicEnums.ErrorType.COMMUNICATOR_RUNNING_FAILED.register(
							"[" + getName() + "] " + e.toString());
					ClientConnection.this.stopProcess();
					if(brother != null) { brother.stopProcess(); }
					socket.close();
					return e.toString();
				}
			}
			
			ClientConnection.this.stopProcess();
			if(brother != null) { brother.startProcess(); }
			socket.close();
			return null;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
