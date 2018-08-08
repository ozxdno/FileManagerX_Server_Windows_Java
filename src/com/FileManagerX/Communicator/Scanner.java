package com.FileManagerX.Communicator;

import com.FileManagerX.BasicModels.*;
import com.FileManagerX.BasicEnums.*;
import com.FileManagerX.Globals.*;
import com.FileManagerX.Interfaces.*;
import com.FileManagerX.Factories.*;

public class Scanner extends Thread implements IScanner {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private MachineInfo serverMachineInfo;
	
	private com.FileManagerX.Interfaces.ISocketS socket;
	private boolean abort;
	private boolean running;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setServerMachineInfo(MachineInfo serverMachineInfo) {
		if(serverMachineInfo == null) {
			return false;
		}
		this.serverMachineInfo = serverMachineInfo;
		return true;
	}
	
	public boolean setSocket(com.FileManagerX.Interfaces.ISocketS socket) {
		if(socket == null) {
			return false;
		}
		this.socket = socket;
		return true;
	}
	public boolean setSocket(com.FileManagerX.BasicEnums.SocketType type) {
		this.socket = type.getSocketS();
		this.socket.setServerMachineInfo(this.serverMachineInfo);
		return this.socket.setSocket();
	}
	public boolean setAbort(boolean abort) {
		this.abort = abort;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public MachineInfo getServerMachineInfo() {
		return this.serverMachineInfo;
	}
	
	public com.FileManagerX.Interfaces.ISocketS getSocket() {
		return this.socket;
	}
	public boolean isAbort() {
		return this.abort;
	}
	public boolean isRunning() {
		return this.running;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Scanner() {
		initThis();
	}
	private void initThis() {
		serverMachineInfo = Datas.ServerMachine;
		
		socket = null;
		abort = false;
		running = false;
	}
	public void run() {
		abort = false;
		running = true;
		if(socket == null) {
			running = false;
			return;
		}
		
		this.setName("@" + this.serverMachineInfo.getIp() + ":" + this.serverMachineInfo.getPort());
		com.FileManagerX.Globals.Datas.Processes.add(this);
		
		while(!abort && !socket.isClosed() && !com.FileManagerX.Globals.Configurations.Close) {
			try {
				com.FileManagerX.Interfaces.ISocketC clientSocket = socket.receive();
				
				// loginConnection 完成最后的添加工作。
				// 信息不完全会导致 Connections 检索产生错误。
				
				IServerConnection sc = CommunicatorFactory.createServerConnection();
				sc.setSocket(clientSocket);
				sc.setServerMachineInfo(serverMachineInfo);
				sc.connect();
				//if(sc.isRunning()) { Datas.Server.add(sc); }
				
				IClientConnection cc = CommunicatorFactory.createClientConnection();
				cc.setIndex(sc.getIndex());
				cc.setSocket(clientSocket);
				cc.setClientMachineInfo(serverMachineInfo);
				cc.connect();
				//if(cc.isRunning()) { Datas.Client.add(cc); }
				
				sc.setBrother(cc);
				cc.setBrother(sc);
				
			}catch(Exception e) {
				ErrorType.COMMUNICATOR_RUNNING_FAILED.register("Server Scanner Running Failed", e.toString());
			}
		}
		running = false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean connect() {
		if(this.running) { return true; }
		this.start();
		com.FileManagerX.Tools.Time.sleepUntil(10);
		return running;
	}
	public void disconnect() {
		try {
			if(socket != null) {
				socket.close();
			}
		}catch(Exception e) {
			;
		}
		while(running) {
			com.FileManagerX.Tools.Time.sleepUntil(1);
			abort = true;
		}
		try {
			if(socket != null) {
				socket.close();
			}
		}catch(Exception e) {
			;
		}
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
}
