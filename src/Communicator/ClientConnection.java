package Communicator;

import java.io.*;
import java.net.*;

public class ClientConnection extends Thread implements IClientConnection{

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private BasicModels.MachineInfo serverMachineInfo;
	private BasicModels.MachineInfo clientMachineInfo;
	private BasicModels.User user;
	
	public String receiveCommand;
	public String replyCommand;
	public boolean isCommandConnector;
	
	public BasicModels.BaseFile receiveFile;
	public BasicModels.BaseFile sendFile;
	public long totalBytes;
	public long finishedBytes;
	public boolean isFileConnector;
	
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
	
	public boolean setReceiveFile(BasicModels.BaseFile f) {
		if(f == null) {
			return false;
		}
		this.receiveFile = f;
		return true;
	}
	public boolean setSendFile(BasicModels.BaseFile f) {
		if(f == null) {
			return false;
		}
		this.sendFile = f;
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
		
		this.receiveCommand = "";
		this.replyCommand = "";
		
		this.socket = null;
		this.abort = false;
		this.running = true;
		this.busy = false;
		
		this.setName("TCP Client Connection");
		
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
		
		while(!abort && !socket.isClosed() && receiveCommand != null) {
			try {
				DataOutputStream sendstream = new DataOutputStream(socket.getOutputStream());
	            String sendstr = "test";
	            byte[] sendbyte = sendstr.getBytes();
	            sendstream.write(sendbyte);

	            DataInputStream recestream =  new DataInputStream(socket.getInputStream());
	            recestream.read(this.receiveBuffer);
	            String recestr = new String(this.receiveBuffer);
	            sendstr = recestr;
			}catch(Exception e) {
				;
			}
		}
		running = false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean writeCommand(String cmd) {
		if(this.busy) {
			return false;
		}
		this.replyCommand = cmd;
		return true;
	}
	public String readCommand() {
		return this.receiveCommand;
	}
	
	public boolean receiveFile(String url) {
		return true;
	}
	public boolean sendFile(String url) {
		return true;
	}
	
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
