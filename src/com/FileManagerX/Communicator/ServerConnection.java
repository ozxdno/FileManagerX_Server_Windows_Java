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
		
		this.reps = new java.util.HashMap<>();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private class RunImpl implements com.FileManagerX.Processes.BasicProcess.Runnable {
		public String run() {
			if(socket == null) {
				ServerConnection.this.setSocket(com.FileManagerX.BasicEnums.SocketType.IPV4_TCP);
			}
			
			if(socket.isClosed()) {
				setIsAbort(true);
				if(brother != null) { brother.exitProcess(); }
				return ERROR_SOCKET_CLOSED;
			}
			
			java.util.LinkedList<Byte> buffer = new java.util.LinkedList<>();
			byte[] recebytes = new byte[(int) com.FileManagerX.Globals.Configurations.LimitForConnectionBuffer];
			
			while(!isAbort() && !isStop() && !socket.isClosed()) {
				try {
					int length = socket.receive(recebytes);
					if(length < 0) {
						setIsAbort(true);
						if(brother != null) { brother.exitProcess(); }
						socket.close();
						return ERROR_SOCKET_CLOSED;
					}
					
					for(int i=0; i<length; i++) {
						if(recebytes[i] == com.FileManagerX.BasicEnums.EOF.N.getSpecial()) {
							byte[] temp = new byte[buffer.size()];
							int cnt = 0;
							for(Byte b : buffer) { temp[cnt++] = b; }
							buffer.clear();
							receiveString = new String(temp);
							com.FileManagerX.Interfaces.ITransport t =
									com.FileManagerX.Coder.Decoder.Decode_Byte2Transport(temp);
							
							// 记录
							if(com.FileManagerX.Globals.Configurations.Record) { record(); }
							
							// 处理
							if(t instanceof com.FileManagerX.Commands.Unsupport) {
								;
							}
							else if(t instanceof com.FileManagerX.Replies.Unsupport) {
								;
							}
							else {
								t.setConnection(ServerConnection.this);
								if(!t.isTimeOut() && t.isArriveTargetMachine()) { execute(t); }
								if(!t.isTimeOut() && t.isDeliver()) { t.deliver(); }
							}
							
							// 特殊记录
							if(!com.FileManagerX.Globals.Configurations.Record &&
									(t == null || t.getBasicMessagePackage().isRecord())) {
								record();
							}
						}
						else {
							buffer.add(recebytes[i]);
						}
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
									record(r);
								}
								reps.remove(r.getBasicMessagePackage().getIndex());
							}
						}
					}catch(Exception e) {
						;
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
		return this.brother == null ? "ServerConnection No Brother" : this.brother.login();
	}

	public boolean send(com.FileManagerX.Interfaces.ITransport send) {
		return this.brother == null ? false : this.brother.send(send);
	}
	public boolean store(com.FileManagerX.Interfaces.IReply reply) {
		if(this.isRunning() && this.reps.get(reply.getBasicMessagePackage().getIndex()) == null) {
			reply.getBasicMessagePackage().setReceiveTime(com.FileManagerX.Tools.Time.getTicks());
			this.reps.put(reply.getBasicMessagePackage().getIndex(), reply);
		}
		return true;
	}
	
	public com.FileManagerX.Interfaces.IReply receive(long index, long wait) {
		com.FileManagerX.Interfaces.IReply rep = null;
		long start = com.FileManagerX.Tools.Time.getTicks();
		
		while(com.FileManagerX.Tools.Time.getTicks() - start < wait && this.isRunning()) {
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

	private void record() {
		com.FileManagerX.BasicModels.Record r = new com.FileManagerX.BasicModels.Record();
		r.setType(com.FileManagerX.BasicEnums.RecordType.SERVER_REC);
		r.setConnectionName(this.getName());
		r.setThreadName(this.getName());
		r.setContent(this.receiveString);
		com.FileManagerX.Globals.Datas.Records.add(r);
	}
	private void record(com.FileManagerX.Interfaces.IReply rep) {
		com.FileManagerX.BasicModels.Record r = new com.FileManagerX.BasicModels.Record();
		r.setType(com.FileManagerX.BasicEnums.RecordType.SERVER_REC);
		r.setConnectionName(this.getName());
		r.setThreadName(this.getName());
		r.setContent('X' + rep.output());
		com.FileManagerX.Globals.Datas.Records.add(r);
	}
	
	private void execute(com.FileManagerX.Interfaces.ITransport receive) {
		com.FileManagerX.Executor.Executor ex = com.FileManagerX.Globals.Datas.Executors.nextIdleProcess();
		ex.setReceive(receive);
		ex.restartProcess();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}