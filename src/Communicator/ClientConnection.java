package Communicator;

import java.io.*;
import java.net.*;

public class ClientConnection extends Thread implements Interfaces.IClientConnection {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private BasicModels.MachineInfo serverMachineInfo;
	private BasicModels.MachineInfo clientMachineInfo;
	private BasicModels.User user;
	private BasicEnums.ConnectionType type;
	private int index;
	private String name;
	
	private Interfaces.IReplyExecutor executor;
	private Interfaces.IFileConnector fileConnector;
	private Interfaces.ICommandsManager cmdManager;
	
	private Socket socket;
	private volatile boolean abort;
	private volatile boolean running;
	private volatile boolean busy;
	
	private boolean closeServer;
	private boolean exchange;
	
	private boolean activeEexcutor;
	private boolean continueReceiveString;
	private boolean continueSendString;
	
	private String receiveString;
	private String sendString;
	private byte[] receiveBuffer;
	private byte[] sendBuffer;
	
	private int bufferSize;
	private int receiveLength;
	private int sendLength;
	
	private long lastOperationTime;
	private int cntError;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setServerMachineInfo(BasicModels.MachineInfo serverMachineInfo) {
		if(serverMachineInfo == null) {
			return false;
		}
		this.serverMachineInfo = serverMachineInfo;
		return true;
	}
	public boolean setClientMachineInfo(BasicModels.MachineInfo clientMachineInfo) {
		if(clientMachineInfo == null) {
			return false;
		}
		this.clientMachineInfo = clientMachineInfo;
		return true;
	}
	public boolean setUser(BasicModels.User user) {
		if(user == null) {
			return false;
		}
		this.user = user;
		return true;
	}
	public boolean setType(BasicEnums.ConnectionType type) {
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
		if(Globals.Datas.Client != null) {
			this.index = Globals.Datas.Client.getNext_ConnectionIndex() + 1;
			Globals.Datas.Client.setNext_ConnectionIndex(index);
		}
		return true;
	}
	public boolean setConnectionName(String name) {
		if(name == null) {
			BasicEnums.ErrorType.COMMON_SET_WRONG_VALUE.register("name = NULL");
			return false;
		}
		if(name.length() == 0) {
			BasicEnums.ErrorType.COMMON_SET_WRONG_VALUE.register("name is Empty");
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
	
	public boolean setExecutor(Interfaces.IExecutor executor) {
		if(executor == null) {
			return false;
		}
		if(!(executor instanceof Interfaces.IReplyExecutor)) {
			return false;
		}
		this.executor = (Interfaces.IReplyExecutor)executor;
		return true;
	}
	public boolean setExecutor(Interfaces.IReplyExecutor executor) {
		if(executor == null) {
			return false;
		}
		this.executor = executor;
		return true;
	}
	public boolean setFileConnector(Interfaces.IFileConnector fileConnector) {
		if(fileConnector == null) {
			return false;
		}
		this.fileConnector = fileConnector;
		return true;
	}
	public boolean setCommandsManager(Interfaces.ICommandsManager cmdManager) {
		if(cmdManager == null) {
			return false;
		}
		this.cmdManager = cmdManager;
		return true;
	}
	
	public boolean setCloseServer(boolean closeServer) {
		this.closeServer = closeServer;
		return true;
	}
	public boolean setExchange(boolean exchange) {
		this.exchange = exchange;
		return true;
	}
	
	public boolean setActiveExecutor(boolean active) {
		this.activeEexcutor = active;
		return true;
	}
	public boolean setContinueReceiveString() {
		this.busy = true;
		this.continueReceiveString = true;
		this.continueSendString = false;
		return true;
	}
	public boolean setContinueSendString() {
		this.busy = true;
		this.continueReceiveString = false;
		this.continueSendString = true;
		return true;
	}
	public boolean setContinueWait() {
		this.continueReceiveString = false;
		this.continueSendString = false;
		return true;
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
	public boolean setReceiveBuffer(byte[] receiveBuffer) {
		if(receiveBuffer == null) {
			return false;
		}
		this.receiveBuffer = receiveBuffer;
		return true;
	}
	public boolean setSendBuffer(byte[] sendBuffer) {
		if(sendBuffer == null) {
			return false;
		}
		this.sendBuffer = sendBuffer;
		return true;
	}
	
	public boolean setBufferSize(int bufferSize) {
		if(bufferSize < 0) {
			return false;
		}
		this.bufferSize = bufferSize;
		this.receiveBuffer = new byte[bufferSize];
		this.sendBuffer = new byte[bufferSize];
		return true;
	}
	public boolean setReceiveLength(int length) {
		if(length < 0) {
			return false;
		}
		this.receiveLength = length;
		return true;
	}
	public boolean setSendLength(int length) {
		if(length < 0) {
			return false;
		}
		this.sendLength = length;
		return true;
	}

	public boolean setSocket(Socket socket) {
		if(socket == null) {
			return false;
		}
		this.socket = socket;
		return true;
	}
	public boolean setSocket() {
		try {
			InetAddress ip = InetAddress.getByName(this.serverMachineInfo.getIp());
			int port = this.serverMachineInfo.getPort();
			this.socket = new Socket(ip,port);
			return true;
		} catch(Exception e) {
			BasicEnums.ErrorType.COMMUNICATOR_BUILD_SOCKED_FAILED.register(e.toString());
			return false;
		}
	}
	public boolean setAbort(boolean abort) {
		this.abort = abort;
		return true;
	}
	public boolean setBusy(boolean busy) {
		this.busy = busy;
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
		this.lastOperationTime = Tools.Time.getTicks();
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public BasicModels.MachineInfo getServerMachineInfo() {
		return this.serverMachineInfo;
	}
	public BasicModels.MachineInfo getClientMachineInfo() {
		return this.clientMachineInfo;
	}
	public BasicModels.User getUser() {
		return this.user;
	}
	public BasicEnums.ConnectionType getType() {
		return this.type;
	}
	public int getIndex() {
		return this.index;
	}
	public String getConnectionName() {
		return this.name;
	}
	
	public Interfaces.IReplyExecutor getExecutor() {
		return this.executor;
	}
	public Interfaces.IFileConnector getFileConnector() {
		return this.fileConnector;
	}
	public Interfaces.ICommandsManager getCommandsManager() {
		if(this.cmdManager.getConnection() == null) {
			this.cmdManager.setConnection(this);
		}
		if(this.cmdManager.getConnection() != this) {
			this.cmdManager.setConnection(this);
		}
		return this.cmdManager;
	}

	public Socket getSocket() {
		return this.socket;
	}
	public boolean isAbort() {
		return this.abort;
	}
	public boolean isRunning() {
		return this.running;
	}
	public boolean isBusy() {
		return this.busy;
	}
	
	public boolean getCloseServer() {
		return this.closeServer;
	}
	public boolean getExchange() {
		return this.exchange;
	}
	
	public boolean isActiveExecutor() {
		return this.activeEexcutor;
	}
	public boolean isContinueReceiveString() {
		return this.continueReceiveString;
	}
	public boolean isContinueSendString() {
		return this.continueSendString;
	}
	public boolean isContinueReceiveByte() {
		return this.isContinueReceiveByte();
	}
	public boolean isContinueSendByte() {
		return this.isContinueSendByte();
	}
	
	public String getReceiveString() {
		return this.receiveString;
	}
	public String getSendString() {
		return this.sendString;
	}
	public byte[] getReceiveBuffer() {
		return this.receiveBuffer;
	}
	public byte[] getSendBuffer() {
		return this.sendBuffer;
	}
	
	public int getBufferSize() {
		return this.bufferSize;
	}
	public int getReceiveLength() {
		return this.receiveLength;
	}
	public int getSendLength() {
		return this.sendLength;
	}
	
	public long getLastOperationTime() {
		return this.lastOperationTime;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public ClientConnection() {
		initThis();
	}
	public ClientConnection(Socket socket,
			BasicModels.MachineInfo serverMachineInfo, 
			BasicModels.MachineInfo clientMachineInfo) {
		initThis();
		this.setSocket(socket);
		this.setServerMachineInfo(serverMachineInfo);
		this.setClientMachineInfo(clientMachineInfo);
	}
	private void initThis() {
		serverMachineInfo = new BasicModels.MachineInfo();
		clientMachineInfo = new BasicModels.MachineInfo();
		user = null;
		type = BasicEnums.ConnectionType.TRANSPORT_COMMAND;
		index = 0;
		name = "";
		
		executor = Factories.CommunicatorFactory.createClientExcutor();
		fileConnector = Factories.CommunicatorFactory.createFileConnector();
		cmdManager = Factories.CommandFactory.createCommandsManager(this);
		
		this.socket = null;
		this.abort = false;
		this.running = false;
		this.busy = false;
		
		this.closeServer = false;
		this.exchange = false;
		
		this.activeEexcutor = true;
		this.continueReceiveString = false;
		this.continueSendString = false;
		
		this.receiveString = null;
		this.sendString = null;
		this.receiveBuffer = null;
		this.sendBuffer = null;
		
		this.setBufferSize(1024);
		this.receiveLength = 0;
		this.sendLength = 0;
		
		this.setLastOperationTime();
		this.cntError = 0;
		this.setName("TCP Client Connection");
	}
	public void run() {
		abort = false;
		running = true;
		if(socket == null) {
			running = false;
			return;
		}
		
		try {
			super.setName("->" + this.socket.getInetAddress().getHostAddress() + ":" + this.socket.getPort());
			this.name = this.serverMachineInfo.getName();
		} catch(Exception e) {
			BasicEnums.ErrorType.OTHERS.register("Set Name Failed", e.toString());
		}
		
		BufferedReader br = null;
		PrintWriter pw = null;
		DataInputStream dis = null;
		DataOutputStream dos = null;
		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());
		}catch(Exception e) {
			BasicEnums.ErrorType.OTHERS.register("Build Stream of Input or Output of Client Failed", e.toString());
			return;
		}
		
		while(!abort && !socket.isClosed() && !closeServer && !exchange) {
			try {
				if(this.continueSendString) {
					this.setLastOperationTime();
					this.busy = true;
					if(this.sendString == null) {
						this.continueSendString = false;
						this.busy = false;
						continue;
					}
					
					BasicModels.Record r = new BasicModels.Record();
					r.setType(BasicEnums.RecordType.CLIENT_CMD);
					r.setConnectionName(name);
					r.setThreadName(super.getName());
					r.setContent(this.sendString);
					Globals.Datas.Records.add(r);
					
					pw.println(this.sendString);
					pw.flush();
					//this.busy = false;
					this.continueSendString = false;
					this.setLastOperationTime();
					this.continueReceiveString = true; // 自动开启接收
				}
				if(this.continueReceiveString) {
					this.setLastOperationTime();
					this.busy = true;
					this.receiveLength = 0;
					int partId = 1;
					while(true) {
						String receItem = br.readLine();
						Interfaces.ICommunicatorReceivePart RP = Factories.CommunicatorFactory.createReceivePart();
						RP.input(receItem);
						if(RP.isPart() && RP.getPartId() == partId) {
							if(partId == 1) {
								this.receiveString = RP.getContent();
							} else {
								this.receiveString += "\n" + RP.getContent();
							}
							this.receiveLength += receItem.length();
							partId++;
							if(partId > RP.getTotalAmount()) {
								break;
							}
						} else {
							this.receiveLength = receItem.length();
							this.receiveString = receItem;
							break;
						}
					}
					
					BasicModels.Record r = new BasicModels.Record();
					r.setType(BasicEnums.RecordType.CLIENT_REP);
					r.setConnectionName(name);
					r.setThreadName(super.getName());
					r.setContent(this.receiveString);
					Globals.Datas.Records.add(r);
					
					if(this.activeEexcutor && this.executor != null) {
						this.executor.execute(this);
					}
					this.continueReceiveString = false;
					this.busy = false;
					this.setLastOperationTime();
				}
				if(!this.continueReceiveString && !this.continueSendString && this.fileConnector.isActive() && 
						this.fileConnector.isInputCommand()) { // save data to file
					
					this.setLastOperationTime();
					
					// 缓冲区不能为空。
					if(this.receiveBuffer == null || this.receiveBuffer.length == 0) {
						this.busy = false;
						continue;
					}
					
					// 初始化
					this.fileConnector.setState_Busy(true);
					this.fileConnector.clear();
					this.receiveLength = 0;
					this.sendLength = 0;
					
					// 接收
					while(!this.fileConnector.isFinished()) {
						this.receiveLength = dis.read(receiveBuffer, 0, receiveBuffer.length);
						if(this.receiveLength < 0) {
							break;
						}
						this.fileConnector.setReceiveBytes(receiveBuffer);
						this.fileConnector.setReceiveLength(receiveLength);
						if(!this.fileConnector.save()) {
							break;
						}
					}
					
					// 更新数据库
					if(this.fileConnector.isWriteToLocal()) {
						Tools.Update.addSingleFile(this.fileConnector.getSourDepot(), this.fileConnector.getDestUrl());
					}
					
					// 延迟关闭，等待接收完毕。
					Tools.Time.sleepUntil(Globals.Configurations.TimeForInputFileWait);
					
					// 结束
					this.fileConnector.setState_Busy(false);
					this.fileConnector.close();
					dis.close();
					
					// 退出
					this.abort = true;
				}
				if(!this.continueReceiveString && !this.continueSendString && this.fileConnector.isActive() &&
						this.fileConnector.isOutputCommand()) { // load data to send
					
					this.setLastOperationTime();
					
					// 缓冲区不能为空。
					if(this.sendBuffer == null || this.sendBuffer.length == 0) {
						this.busy = false;
						continue;
					}
					
					// 初始化
					this.fileConnector.setState_Busy(true);
					this.fileConnector.clear();
					this.receiveLength = 0;
					this.sendLength = 0;
					
					// 接收
					this.fileConnector.setState_Busy(true);
					this.fileConnector.clear();
					this.fileConnector.setSendBytes(this.sendBuffer);
					while(!this.fileConnector.isFinished()) {
						if(!this.fileConnector.load()) {
							break;
						}
						this.sendLength = this.fileConnector.getSendLength();
						dos.write(this.sendBuffer, 0, this.sendLength);  
						dos.flush();
					}
					
					// 结束
					this.fileConnector.setState_Busy(false);
					this.fileConnector.close();
					dos.close();
					
					// 退出
					this.abort = true;
				}
				
				Tools.Time.sleepUntil(10);
				
			}catch(Exception e) {
				BasicEnums.ErrorType.COMMUNICATOR_RUNNING_FAILED.register(
						this.serverMachineInfo.getName() + "->" +
						this.clientMachineInfo.getName() + ": " + 
						e.toString());
				break;
			}
		}
		
		if(this.exchange) {
			this.toServerConnection();
			this.running = false;
			Globals.Datas.Client.removeIdleConnections();
		}
		else {
			running = false;
			this.fileConnector.close();
			this.disconnect();
			Globals.Datas.Client.removeIdleConnections();
			if(closeServer) {
				Globals.Datas.Server.disconnect();
			}
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean connect() {
		if(this.socket == null || this.socket.isClosed()) { return false; }
		this.start();
		Tools.Time.waitUntil(100);
		return this.running;
	}
	
	public void disconnect() {
		try {
			socket.shutdownInput();
			socket.shutdownOutput();
			socket.close();
		} catch(Exception e) {
			;
		}
		
		while(running) {
			Tools.Time.sleepUntil(1);
			abort = true;
		}
		
		try {
			socket.shutdownInput();
			socket.shutdownOutput();
			socket.close();
		} catch(Exception e) {
			;
		}
	}
	public boolean test() {
		if(Tools.Time.getTicks() - this.lastOperationTime <= Globals.Datas.Client.getPermitIdle()) {
			return true;
		}
		if(!this.type.equals(BasicEnums.ConnectionType.TRANSPORT_COMMAND)) {
			return true;
		}
		
		String tick = String.valueOf(Tools.Time.getTicks());
		String rece = this.getCommandsManager().test(tick);
		boolean ok = tick.equals(rece);
		if(ok) {
			this.cntError = 0;
			return true;
		}
		
		this.cntError ++;
		return this.cntError <= Globals.Configurations.PermitConnectionErrorAmount;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private boolean toServerConnection() {
		Interfaces.IServerConnection con = Factories.CommunicatorFactory.createServerConnection();
		con.setSocket(this.socket);
		this.copyTo(con);
		con.connect();
		return con.isRunning();
	}
	private void copyTo(Interfaces.IServerConnection con) {
		con.setServerMachineInfo(clientMachineInfo);
		con.setClientMachineInfo(serverMachineInfo);
		con.setType(type);
		con.setConnectionName(name);
		con.setIndex(index);
		con.setUser(user);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
