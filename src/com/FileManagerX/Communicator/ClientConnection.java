package com.FileManagerX.Communicator;

/**
 * 封装一个 Socket ，该线程只负责发送信息。
 * 
 * @author ozxdno
 *
 */
public class ClientConnection extends Thread implements com.FileManagerX.Interfaces.IClientConnection {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private com.FileManagerX.BasicModels.MachineInfo serverMachineInfo;
	private com.FileManagerX.BasicModels.MachineInfo clientMachineInfo;
	private com.FileManagerX.BasicModels.User serverUser;
	private com.FileManagerX.BasicModels.User clientUser;
	private com.FileManagerX.BasicEnums.ConnectionType type;
	private int index;
	private String name;
	
	private com.FileManagerX.Interfaces.ISocketC socket;
	private boolean abort;
	private boolean running;
	
	private String receiveString;
	private String sendString;
	
	private long lastOperationTime;
	private long permitIdle;
	private long maxFlow;
	
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
	public boolean setIndex(int index) {
		this.index = index;
		return true;
	}
	public boolean setIndex() {
		if(com.FileManagerX.Globals.Datas.Client != null) {
			this.index = com.FileManagerX.Globals.Datas.Client.getNext_ConnectionIndex() + 1;
			com.FileManagerX.Globals.Datas.Client.setNext_ConnectionIndex(index);
		}
		return true;
	}
	public boolean setConnectionName(String name) {
		if(name == null) {
			com.FileManagerX.BasicEnums.ErrorType.COMMON_SET_WRONG_VALUE.register("name = NULL");
			return false;
		}
		if(name.length() == 0) {
			com.FileManagerX.BasicEnums.ErrorType.COMMON_SET_WRONG_VALUE.register("name is Empty");
			return false;
		}
		this.name = name;
		return true;
	}
	public boolean setConnectionName() {
		String name = this.clientMachineInfo.getName();
		if(name.length() == 0) {
			name = "No Name";
		}
		return this.setConnectionName(name);
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
		return true;
	}
	public boolean setSocket(com.FileManagerX.BasicEnums.SocketType type) {
		this.socket = type.getSocketC();
		this.socket.setServerMachineInfo(this.serverMachineInfo);
		return this.socket.setSocket();
	}
	public boolean setAbort(boolean abort) {
		this.abort = abort;
		return true;
	}
	
	public boolean setLastOperationTime(long lastOperationTime) {
		if(lastOperationTime < 0) {
			return false;
		}
		this.lastOperationTime = lastOperationTime;
		return true;
	}
	public boolean setLastOperationTime() {
		this.lastOperationTime = com.FileManagerX.Tools.Time.getTicks();
		return true;
	}
	public boolean setPermitIdle(long idle) {
		if(idle < 0) {
			return false;
		}
		this.permitIdle = idle;
		return true;
	}
	public boolean setMaxFlow(long maxFlow) {
		if(maxFlow < 0) {
			return false;
		}
		this.maxFlow = maxFlow;
		return true;
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
	public int getIndex() {
		return this.index;
	}
	public String getConnectionName() {
		return this.name;
	}
	
	public com.FileManagerX.Interfaces.ISocketC getSocket() {
		return this.socket;
	}
	public boolean isAbort() {
		return this.abort;
	}
	public boolean isRunning() {
		return this.running;
	}
	
	public String getReceiveString() {
		return this.receiveString;
	}
	public String getSendString() {
		return this.sendString;
	}
	
	public long getLastOperationTime() {
		return this.lastOperationTime;
	}
	public long getPermitIdle() {
		return this.permitIdle;
	}
	public long getMaxFlow() {
		return this.maxFlow;
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
		serverMachineInfo = new com.FileManagerX.BasicModels.MachineInfo();
		clientMachineInfo = new com.FileManagerX.BasicModels.MachineInfo();
		serverUser = null;
		clientUser = null;
		type = com.FileManagerX.BasicEnums.ConnectionType.TRANSPORT_COMMAND;
		index = 0;
		name = "";
		
		this.socket = null;
		this.abort = false;
		this.running = false;
		
		this.receiveString = null;
		this.sendString = null;
		
		this.setLastOperationTime();
		this.permitIdle = com.FileManagerX.Globals.Configurations.TimeForClientPermitIdle;
		this.setName("TCP Client Connection");
		
		this.sends = new java.util.PriorityQueue<>(getComparator());
	}
	public void run() {
		abort = false;
		running = true;
		if(socket == null) {
			running = false;
			return;
		}
		
		try {
			super.setName(
					"->" + 
					this.socket.getClientMachineInfo().getIp() + 
					":" +
					this.socket.getClientMachineInfo().getPort()
					);
			this.name = this.serverMachineInfo.getName();
			
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register("Set Name Failed", e.toString());
		}
		
		com.FileManagerX.Globals.Datas.Processes.add(this);
		while(!abort && !socket.isClosed()) {
			try {
				if(sends.isEmpty()) {
					com.FileManagerX.Tools.Time.sleepUntil(10);
					continue;
				}
				
				// 取出优先级最高的命令
				com.FileManagerX.Interfaces.ITransport send = sends.poll();
				
				// 发送命令
				this.setLastOperationTime();
				this.socket.send(com.FileManagerX.Coder.Encoder.Encode_Transport2Byte(send));
				
				// 记录
				if(com.FileManagerX.Globals.Configurations.Record || send.getBasicMessagePackage().isRecord()) {
					this.record();
				}
				
			} catch(Exception e) {
				com.FileManagerX.BasicEnums.ErrorType.COMMUNICATOR_RUNNING_FAILED.register(
						this.serverMachineInfo.getName() + "->" +
						this.clientMachineInfo.getName() + ": " + 
						e.toString());
				break;
			}
		}
		
		this.running = false;
		this.brother.setAbort(true);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean connect() {
		if(this.running) { return true; }
		if(this.socket == null || this.socket.isClosed()) { return false; }
		this.start();
		com.FileManagerX.Tools.Time.waitUntil(100);
		return this.running;
	}
	public void disconnect() {
		this.socket.close();
		this.abort = true;
	}
	public String login() {
		
		com.FileManagerX.Interfaces.IReply reply = null;
		
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
		if(!this.running) { return false; }
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

	public boolean isFinished() {
		return !this.running;
	}
	public boolean isStop() {
		return !this.running;
	}
	
	public boolean initialize(Object infos) {
		return true;
	}
	
	public boolean startProcess() {
		return this.connect();
	}
	public boolean stopProcess() {
		this.disconnect();
		return true;
	}
	public boolean continueProcess() {
		return false;
	}
	public boolean restartProcess() {
		return this.connect();
	}
	public boolean exitProcess() {
		this.disconnect();
		return true;
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
		r.setConnectionName(name);
		r.setThreadName(super.getName());
		r.setContent(this.sendString);
		com.FileManagerX.Globals.Datas.Records.add(r);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
