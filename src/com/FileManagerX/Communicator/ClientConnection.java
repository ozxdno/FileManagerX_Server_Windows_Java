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
	
	private java.util.PriorityQueue<com.FileManagerX.Interfaces.ITransport> sends;
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
		
		this.sends = new java.util.PriorityQueue<>(getComparator());
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private class RunImpl implements com.FileManagerX.Processes.BasicProcess.Runnable {
		public String run() {
			if(socket == null) {
				ClientConnection.this.setSocket(com.FileManagerX.BasicEnums.SocketType.IPV4_TCP);
			}
			
			if(socket.isClosed()) {
				setIsAbort(true);
				if(brother != null) { brother.exitProcess(); }
				return ERROR_SOCKET_CLOSED;
			}
			
			while(!isAbort() && !isStop() && !socket.isClosed()) {
				try {
					if(sends.isEmpty()) {
						com.FileManagerX.Tools.Time.sleepUntil(10);
						continue;
					}
					
					// 取出优先级最高的命令
					com.FileManagerX.Interfaces.ITransport send = sends.poll();
					
					// 发送命令
					setBegin();
					socket.send(com.FileManagerX.Coder.Encoder.Encode_Transport2Byte(send));
					setEnd();
					
					// 记录
					if(com.FileManagerX.Globals.Configurations.Record || send.getBasicMessagePackage().isRecord()) {
						record();
					}
					
				} catch(Exception e) {
					com.FileManagerX.BasicEnums.ErrorType.COMMUNICATOR_RUNNING_FAILED.register(
							"[" + getName() + "] " + e.toString());
					setIsAbort(true);
					if(brother != null) { brother.exitProcess(); }
					socket.close();
					return e.toString();
				}
			}
			
			setIsAbort(true);
			if(brother != null) { brother.exitProcess(); }
			socket.close();
			return null;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public String login() {
		
		com.FileManagerX.Interfaces.IReply reply = null;
		
		com.FileManagerX.Commands.LoginServer ls = new com.FileManagerX.Commands.LoginServer();
		ls.setThis(com.FileManagerX.Globals.Datas.ThisMachine, this);
		ls.send();
		reply = ls.receive();
		if(reply == null) { return "LoginServer Failed: reply NULL"; }
		if(!reply.isOK()) { return reply.getFailedReason(); }
		
		com.FileManagerX.Commands.LoginUser lu = new com.FileManagerX.Commands.LoginUser();
		lu.setThis(com.FileManagerX.Globals.Datas.ThisUser, com.FileManagerX.Globals.Datas.ThisUser, this);
		lu.send();
		reply = lu.receive();
		if(reply == null) { return "LoginUser Failed: reply NULL"; }
		if(!reply.isOK()) { return reply.getFailedReason(); }
		
		com.FileManagerX.Commands.LoginMachine lm = new com.FileManagerX.Commands.LoginMachine();
		lm.setThis(com.FileManagerX.Globals.Datas.ThisMachine, this);
		lm.send();
		reply = lm.receive();
		if(reply == null) { return "LoginMachine Failed: reply NULL"; }
		if(!reply.isOK()) { return reply.getFailedReason(); }
		
		com.FileManagerX.Commands.LoginType lt = new com.FileManagerX.Commands.LoginType();
		lt.setThis(this.getType(), this);
		lt.send();
		reply = lt.receive();
		if(reply == null) { return "LoginType Failed: reply NULL"; }
		if(!reply.isOK()) { return reply.getFailedReason(); }
		
		com.FileManagerX.Commands.LoginIndex li = new com.FileManagerX.Commands.LoginIndex();
		li.setThis(this.getIndex(), 100, this);
		li.send();
		reply = li.receive();
		if(reply == null) { return "LoginIndex Failed: reply NULL"; }
		if(!reply.isOK()) { return reply.getFailedReason(); }
		
		com.FileManagerX.Commands.LoginConnection lc = new com.FileManagerX.Commands.LoginConnection();
		lc.setThis(this);
		lc.send();
		reply = lc.receive();
		if(reply == null) { return "LoginConnection Failed: reply NULL"; }
		if(!reply.isOK()) { return reply.getFailedReason(); }
		
		return "";
	}
	
	public boolean send(com.FileManagerX.Interfaces.ITransport send) {
		if(!this.isRunning()) { return false; }
		return this.sends.add(send);
	}
	public boolean store(com.FileManagerX.Interfaces.IReply reply) {
		return this.brother == null ? false : this.brother.store(reply);
	}
	public com.FileManagerX.Interfaces.IReply receive(long index, long wait) {
		return this.brother == null ? null : this.brother.receive(index, wait);
	}
	public com.FileManagerX.Interfaces.IReply search(long index) {
		return this.brother == null ? null : this.brother.search(index);
	}
	public com.FileManagerX.Interfaces.IReply fetch(long index) {
		return this.brother == null ? null : this.brother.fetch(index);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private static java.util.Comparator<com.FileManagerX.Interfaces.ITransport> getComparator() {
		java.util.Comparator<com.FileManagerX.Interfaces.ITransport> c = new java.util.Comparator<com.FileManagerX.Interfaces.ITransport>() {
			public int compare(com.FileManagerX.Interfaces.ITransport t1, com.FileManagerX.Interfaces.ITransport t2) {
				if(t1 == null) {
					return -1;
				}
				if(t2 == null) {
					return 1;
				}
				if(t1.getBasicMessagePackage().getPriority() > t2.getBasicMessagePackage().getPriority()) {
					return 1;
				}
				else {
					return -1;
				}
			}
		};
		return c;
	}
	private void record() {
		com.FileManagerX.BasicModels.Record r = new com.FileManagerX.BasicModels.Record();
		r.setType(com.FileManagerX.BasicEnums.RecordType.CLIENT_REC);
		r.setConnectionName(this.getName());
		r.setThreadName(this.getName());
		r.setContent(this.sendString);
		com.FileManagerX.Globals.Datas.Records.add(r);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
