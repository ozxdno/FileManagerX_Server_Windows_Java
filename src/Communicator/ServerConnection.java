package Communicator;

import java.net.*;
import java.io.*;

public class ServerConnection extends Thread implements Interfaces.IServerConnection {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private BasicModels.MachineInfo serverMachineInfo;
	private BasicModels.MachineInfo clientMachineInfo;
	private BasicModels.User user;
	private BasicEnums.ConnectionType type;
	private int index;
	private String name;
	
	private Interfaces.ICommandExecutor executor;
	private Interfaces.IFileConnector fileConnector;
	
	private Socket socket;
	private volatile boolean abort;
	private volatile boolean running;
	private volatile boolean busy;
	
	private boolean closeServer;
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
		if(Globals.Datas.Server != null) {
			this.index = Globals.Datas.Server.getNext_ConnectionIndex() + 1;
			Globals.Datas.Server.setNext_ConnectionIndex(index);
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
		super.setName("TCP Server: " + name);
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
		if(!(executor instanceof Interfaces.ICommandExecutor)) {
			return false;
		}
		this.executor = (Interfaces.ICommandExecutor)executor;
		return true;
	}
	public boolean setExecutor(Interfaces.ICommandExecutor executor) {
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
	
	public boolean setCloseServer(boolean closeServer) {
		this.closeServer = closeServer;
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
	
	public Interfaces.ICommandExecutor getExecutor() {
		return this.executor;
	}
	public Interfaces.IFileConnector getFileConnector() {
		return this.fileConnector;
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
	public boolean isActiveExecutor() {
		return this.activeEexcutor;
	}
	public boolean isContinueReceiveString() {
		return this.continueReceiveString;
	}
	public boolean isContinueSendString() {
		return this.continueSendString;
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
	
	public ServerConnection() {
		initThis();
	}
	public ServerConnection(Socket socket, BasicModels.MachineInfo serverMachineInfo) {
		initThis();
		this.setSocket(socket);
		this.setServerMachineInfo(serverMachineInfo);
	}
	private void initThis() {
		serverMachineInfo = new BasicModels.MachineInfo();
		clientMachineInfo = new BasicModels.MachineInfo();
		user = null;
		type = BasicEnums.ConnectionType.TRANSPORT_COMMAND;
		index = 0;
		name = "";
		
		executor = Factories.CommunicatorFactory.createServerExcutor();
		fileConnector = Factories.CommunicatorFactory.createFileConnector();
		
		this.socket = null;
		this.abort = false;
		this.running = false;
		this.busy = false;
		
		this.closeServer = false;
		this.activeEexcutor = true;
		this.continueReceiveString = true;
		this.continueSendString = false;
		
		this.receiveString = null;
		this.sendString = null;
		this.receiveBuffer = null;
		this.sendBuffer = null;
		
		this.setBufferSize(1024);
		this.receiveLength = 0;
		this.sendLength = 0;
		
		this.setLastOperationTime();
		this.setName("TCP Server Connection");
	}
	public void run() {
		abort = false;
		running = true;
		if(socket == null) {
			running = false;
			return;
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
			BasicEnums.ErrorType.COMMON_STREAM_BUILD_FAILED.register(e.toString());
			return;
		}
		
		while(!abort && !socket.isClosed() && !closeServer) {
			try {
				if(this.continueReceiveString) {
					this.setLastOperationTime();
					this.busy = true;
					this.receiveString = br.readLine();
					this.receiveLength = this.receiveString.length();
					this.continueReceiveString = false;
					if(this.activeEexcutor && this.executor != null) {
						this.executor.execute(this);
					}
					this.busy = false;
				}
				if(this.continueSendString) {
					this.setLastOperationTime();
					this.busy = true;
					if(this.sendString == null) {
						this.continueSendString = false;
						this.busy = false;
						continue;
					}
					Interfaces.ICommunicatorSendTotal st = Factories.CommunicatorFactory.createSendTotal();
					st.input(sendString);
					while(!st.isFinished()) {
						pw.println(st.outputCurrentItem());
						pw.flush();
					}
					
					this.busy = false;
					this.continueSendString = false;
					this.continueReceiveString = !this.fileConnector.isActive();
				}
				if(!this.continueReceiveString && !this.continueSendString && this.fileConnector.isActive() &&
						this.fileConnector.isOutputCommand()) { // save data to file
					
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
					
					// 不着急关，等待一段时间
					Tools.Time.sleepUntil(Globals.Configurations.TimeForInputFileWait);
					
					// 结束
					this.fileConnector.setState_Busy(false);
					this.fileConnector.close();
					dis.close();
					
					// 退出
					this.abort = true;
				}
				if(!this.continueReceiveString && !this.continueSendString && this.fileConnector.isActive() && 
						this.fileConnector.isInputCommand()) { // load data to send
					
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
					
					// 延迟一段时间开始发送（对方接收器可能还没打开）
					Tools.Time.sleepUntil(Globals.Configurations.TimeForInputFileWait);
					
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
				BasicEnums.ErrorType.COMMUNICATOR_RUNNING_FAILED.register(e.toString());
				break;
			}
		}
		running = false;
		try {
			this.fileConnector.close();
			br.close();
			pw.close();
			dis.close();
			dos.close();
		} catch(Exception e) {
			;
		}
		Globals.Datas.Server.removeIdleConnections();
		if(closeServer) {
			Globals.Datas.Server.disconnect();
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
		
		this.fileConnector.close();
		
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
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}