package Communicator;

import java.io.*;
import java.net.*;

public class ClientConnection extends Thread implements Interfaces.IClientConnection{

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private BasicModels.MachineInfo serverMachineInfo;
	private BasicModels.MachineInfo clientMachineInfo;
	private BasicModels.User user;
	private Interfaces.IExcuteClientCommand excutor;
	
	private String receiveCommand;
	private String sendCommand;
	private boolean isCommandConnector;
	
	private BasicModels.BaseFile receiveFile;
	private BasicModels.BaseFile sendFile;
	private long totalBytes;
	private long finishedBytes;
	private boolean isFileConnector;
	
	private Socket socket;
	private boolean abort;
	private boolean running;
	private boolean busy;
	
	private long lastReceiveTime;
	
	private byte[] receiveBuffer;
	private byte[] sendBuffer;
	private int bufferSize;
	
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
	public boolean setExcutor(Interfaces.IExcuteClientCommand excutor) {
		if(excutor == null) {
			return false;
		}
		this.excutor = excutor;
		return true;
	}
	
	public boolean setReceiveCommand(String receiveCommand) {
		if(receiveCommand == null) {
			return false;
		}
		this.receiveCommand = receiveCommand;
		return true;
	}
	public boolean setSendCommand(String sendCommand) {
		if(sendCommand == null) {
			return false;
		}
		this.sendCommand = sendCommand;
		return true;
	}
	public boolean setIsCommandConnector(boolean isCommandConnector) {
		this.isCommandConnector = isCommandConnector;
		this.isFileConnector = !this.isCommandConnector;
		return true;
	}
	
	public boolean setReceiveFile(BasicModels.BaseFile f) {
		if(f == null) {
			return false;
		}
		this.sendFile.clear();
		this.receiveFile = f;
		return true;
	}
	public boolean setSendFile(BasicModels.BaseFile f) {
		if(f == null) {
			return false;
		}
		this.receiveFile.clear();
		this.sendFile = f;
		return true;
	}
	public boolean setIsFileConnector(boolean isFileConnector) {
		this.isFileConnector = isFileConnector;
		this.isCommandConnector = !this.isFileConnector;
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
	
	public boolean setLastReceiveTime(long lastReceiveTime) {
		if(lastReceiveTime < 0) {
			return false;
		}
		this.lastReceiveTime = lastReceiveTime;
		return true;
	}
	public boolean setLastReceiveTime() {
		this.lastReceiveTime = Tools.Time.getTicks();
		return true;
	}
	
	public boolean setBufferSize(int bufferSize) {
		if(bufferSize < 0) {
			return false;
		}
		this.bufferSize = bufferSize;
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
	public Interfaces.IExcuteClientCommand getExcutor() {
		return this.excutor;
	}
	
	public String getReceiveCommand() {
		return this.receiveCommand;
	}
	public String getSendCommand() {
		return this.sendCommand;
	}
	public boolean isCommandConnector() {
		return this.isCommandConnector;
	}
	
	public BasicModels.BaseFile getReceiveFile() {
		return this.receiveFile;
	}
	public BasicModels.BaseFile getSendFile() {
		return this.sendFile;
	}
	public long getTotalBytes() {
		return this.totalBytes;
	}
	public long getFinishedBytes() {
		return this.finishedBytes;
	}
	public boolean isFileConnector() {
		return this.isFileConnector;
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
	
	public long getLastReceiveTime() {
		return this.lastReceiveTime;
	}
	
	public int getBufferSize() {
		return this.bufferSize;
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
		serverMachineInfo = null;
		clientMachineInfo = null;
		user = null;
		excutor = new ExcuteClientCommand(this);
		
		this.receiveCommand = "";
		this.sendCommand = "";
		this.isCommandConnector = true;
		
		this.receiveFile = new BasicModels.BaseFile();
		this.sendFile = new BasicModels.BaseFile();
		this.totalBytes = 0;
		this.finishedBytes = 0;
		this.isFileConnector = false;
		
		this.socket = null;
		this.abort = false;
		this.running = true;
		this.busy = false;
		
		this.setName("TCP Client Connection");
		this.setLastReceiveTime();
		
		this.bufferSize = 1024;
		this.receiveBuffer = new byte[this.bufferSize];
		this.sendBuffer = new byte[this.bufferSize];
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
		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		}catch(Exception e) {
			return;
		}
		
		DataInputStream dis = null;
		FileOutputStream fos = null;
		try {
			dis = new DataInputStream(socket.getInputStream());
		} catch(Exception e) {
			return;
		}
		
		DataOutputStream dos = null;
		FileInputStream fis = null;
		try {
			dos = new DataOutputStream(socket.getOutputStream()); 
		} catch(Exception e) {
			return;
		}
		
		while(!abort && !socket.isClosed()) {
			try {
				if(this.isCommandConnector) {
					if(this.sendCommand == null || this.sendCommand.length() == 0) {
						continue;
					}
					this.sendCommand += '\n';
					pw.println(this.sendCommand);
					pw.flush();
					this.receiveCommand = br.readLine();
					this.excutor.excute(this.receiveCommand);
				}
				if(this.isFileConnector) {
					if(this.sendFile.getUrl().length() == 0) { // received one file
						if(fos == null) {
							fos = new FileOutputStream(new File(receiveFile.getUrl()));
						}
						int length = dis.read(receiveBuffer, 0, receiveBuffer.length);
						if(length > 0) {
							fos.write(receiveBuffer, 0, length);
							fos.flush();
							this.finishedBytes += length;
						}
						if(length <= this.bufferSize) {
							fos.close();
							fos = null;
							this.setIsCommandConnector(true);
						}
					}
					if(this.receiveFile.getUrl().length() == 0) { // want to send a file
						if(fis == null) {
							fis = new FileInputStream(new File(sendFile.getUrl()));
						}
						int length = fis.read(sendBuffer, 0, sendBuffer.length);
						if (length > 0) {
			                dos.write(sendBuffer, 0, length);  
			                dos.flush();
			                this.finishedBytes += length;
			            }
						if(length <= this.bufferSize) {
							fis.close();
							fis = null;
							this.setIsCommandConnector(true);
						}
					}
				}
				
			}catch(Exception e) {
				break;
			}
		}
		running = false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean connect() {
		disconnect();
		try {
			java.net.InetAddress ip = java.net.InetAddress.getByName(this.serverMachineInfo.getIp());
			int port = this.serverMachineInfo.getPort();
			this.socket = new java.net.Socket(ip,port);
			this.start();
			return true;
		} catch(Exception e) {
			return false;
		}
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
