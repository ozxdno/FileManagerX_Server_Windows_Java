package com.FileManagerX.Communicator;

/**
 * 封装一个 Socket ，该线程只负责接收信息。
 * 
 * @author ozxdno
 *
 */
public class ServerConnection extends Thread implements com.FileManagerX.Interfaces.IServerConnection {
	
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
	
	private java.util.HashMap<Long, com.FileManagerX.Interfaces.IReply> reps;
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
		if(com.FileManagerX.Globals.Datas.Server != null) {
			this.index = com.FileManagerX.Globals.Datas.Server.getNext_ConnectionIndex() + 1;
			com.FileManagerX.Globals.Datas.Server.setNext_ConnectionIndex(index);
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
		this.socket.setServerMachineInfo(this.clientMachineInfo);
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
		this.permitIdle = com.FileManagerX.Globals.Configurations.TimeForServerPermitIdle;
		this.setName("TCP Server Connection");
		
		this.reps = new java.util.HashMap<>();
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
					"<-" + 
					this.socket.getClientMachineInfo().getIp() + ":" +
					this.socket.getClientMachineInfo().getPort()
					);
			this.name = this.clientMachineInfo.getName();
			
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register("Set Name Failed", e.toString());
		}
		
		com.FileManagerX.Globals.Datas.Processes.add(this);
		java.util.LinkedList<Byte> buffer = new java.util.LinkedList<>();
		byte[] recebytes = new byte[(int) com.FileManagerX.Globals.Configurations.MaxConnectionFlow];
		
		while(!abort && !socket.isClosed()) {
			try {
				
				int length = this.socket.receive(recebytes);
				for(int i=0; i<length; i++) {
					if(recebytes[i] == com.FileManagerX.BasicEnums.EOF.N.getSpecial()) {
						byte[] temp = new byte[buffer.size()];
						int cnt = 0;
						for(Byte b : buffer) { temp[cnt++] = b; }
						buffer.clear();
						this.receiveString = new String(temp);
						com.FileManagerX.Interfaces.ITransport t =
								com.FileManagerX.Coder.Decoder.Decode_Byte2Transport(temp);
						
						// 记录
						if(com.FileManagerX.Globals.Configurations.Record) { this.record(); }
						
						// 处理
						t.setConnection(this);
						if(!t.isTimeOut() && t.isArriveTargetMachine()) { this.execute(t); }
						if(!t.isTimeOut() && t.isDeliver()) { t.deliver(); }
						
						// 特殊记录
						if(!com.FileManagerX.Globals.Configurations.Record &&
								(t == null || t.getBasicMessagePackage().isRecord())) {
							this.record();
						}
						
						// 清理缓存
						
						
					}
					buffer.add(recebytes[i]);
				}
				
				// 清理缓存
				try {
					for(com.FileManagerX.Interfaces.IReply r : reps.values()) {
						long permit = 2 * r.getBasicMessagePackage().getPermitIdle();
						if(permit < 0) { permit = Long.MAX_VALUE; }
						if(com.FileManagerX.Tools.Time.getTicks() - r.getBasicMessagePackage().getReceiveTime() > permit) {
							if(!com.FileManagerX.Globals.Configurations.Record) {
								r.setFailedReason("No Receiver to Accept");
								r.setOK(false);
								this.record(r);
							}
							reps.remove(r.getBasicMessagePackage().getIndex());
						}
					}
				}catch(Exception e) {
					;
				}
				
			} catch(Exception e) {
				com.FileManagerX.BasicEnums.ErrorType.COMMUNICATOR_RUNNING_FAILED.register(e.toString());
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
		return this.brother == null ? "ServerConnection No Brother" : this.brother.login();
	}

	public boolean send(com.FileManagerX.Interfaces.ITransport send) {
		return this.brother == null ? false : this.brother.send(send);
	}
	public boolean store(com.FileManagerX.Interfaces.IReply reply) {
		if(this.reps.get(reply.getBasicMessagePackage().getIndex()) == null) {
			reply.getBasicMessagePackage().setReceiveTime(com.FileManagerX.Tools.Time.getTicks());
			this.reps.put(reply.getBasicMessagePackage().getIndex(), reply);
		}
		return true;
	}
	
	public com.FileManagerX.Interfaces.IReply receive(long index, long wait) {
		com.FileManagerX.Interfaces.IReply rep = null;
		long start = com.FileManagerX.Tools.Time.getTicks();
		
		while(com.FileManagerX.Tools.Time.getTicks() - start < wait && this.running) {
			com.FileManagerX.Tools.Time.sleepUntil(1);
			rep = reps.remove(index);
			if(rep != null) {
				break;
			}
		}
		
		return rep;
	}
	public com.FileManagerX.Interfaces.IReply search(long index) {
		return reps.get(index);
	}
	public com.FileManagerX.Interfaces.IReply fetch(long index) {
		return reps.remove(index);
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

	private void record() {
		com.FileManagerX.BasicModels.Record r = new com.FileManagerX.BasicModels.Record();
		r.setType(com.FileManagerX.BasicEnums.RecordType.SERVER_REC);
		r.setConnectionName(name);
		r.setThreadName(super.getName());
		r.setContent(this.receiveString);
		com.FileManagerX.Globals.Datas.Records.add(r);
	}
	private void record(com.FileManagerX.Interfaces.IReply rep) {
		com.FileManagerX.BasicModels.Record r = new com.FileManagerX.BasicModels.Record();
		r.setType(com.FileManagerX.BasicEnums.RecordType.SERVER_REC);
		r.setConnectionName(name);
		r.setThreadName(super.getName());
		r.setContent('X' + rep.output());
		com.FileManagerX.Globals.Datas.Records.add(r);
	}
	
	private void execute(com.FileManagerX.Interfaces.ITransport receive) {
		com.FileManagerX.Executor.Executor ex = com.FileManagerX.Globals.Datas.Executors.searchIdleExecutor();
		ex.setReceive(receive);
		ex.restartProcess();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}