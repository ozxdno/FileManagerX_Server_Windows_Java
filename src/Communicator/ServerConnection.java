package Communicator;

import java.net.*;

import Interfaces.IServerConnection;

import java.io.*;

public class ServerConnection extends Thread implements IServerConnection{
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private BasicModels.MachineInfo serverMachineInfo;
	private BasicModels.MachineInfo clientMachineInfo;
	private BasicModels.User user;
	private Interfaces.IExcuteServerCommand excutor;
	
	private String receiveCommand;
	private String replyCommand;
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
	public boolean setExcutor(Interfaces.IExcuteServerCommand excutor) {
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
	public boolean setReplyCommand(String replyCommand) {
		if(replyCommand == null) {
			return false;
		}
		this.replyCommand = replyCommand;
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
		this.receiveFile = f;
		this.sendFile.clear();
		return true;
	}
	public boolean setSendFile(BasicModels.BaseFile f) {
		if(f == null) {
			return false;
		}
		this.sendFile = f;
		this.receiveFile.clear();
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
		this.receiveBuffer = new byte[bufferSize];
		this.sendBuffer = new byte[bufferSize];
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
	public Interfaces.IExcuteServerCommand getExcutor() {
		return this.excutor;
	}
	
	public String getReceiveCommand() {
		return this.receiveCommand;
	}
	public String getReplyCommand() {
		return this.replyCommand;
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
	
	public ServerConnection() {
		initThis();
	}
	public ServerConnection(Socket socket, BasicModels.MachineInfo serverMachineInfo) {
		initThis();
		this.setSocket(socket);
		this.setServerMachineInfo(serverMachineInfo);
	}
	private void initThis() {
		serverMachineInfo = null;
		clientMachineInfo = null;
		user = null;
		excutor = new ExcuteServerCommand(this);
		
		this.receiveCommand = "";
		this.replyCommand = "";
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
		
		this.setLastReceiveTime();
		this.setName("TCP Server Connection");
		
		this.setBufferSize(1024);
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
					receiveCommand = br.readLine();
					if(receiveCommand == null || receiveCommand.length() == 0) {
						break;
					}
					lastReceiveTime = Tools.Time.getTicks();
					replyCommand = excutor.excute(receiveCommand);
					pw.println(replyCommand);
	                pw.flush();
				}
				if(this.isFileConnector) {
					if(this.sendFile.getUrl().length() == 0) { // input receive file
						if(fos == null) {
							fos = new FileOutputStream(new File(receiveFile.getLocalUrl()));
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
					if(this.receiveFile.getUrl().length() == 0) { // output send file
						if(fis == null) {
							fis = new FileInputStream(new File(sendFile.getLocalUrl()));
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