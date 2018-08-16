package com.FileManagerX.Communicator;

import com.FileManagerX.BasicModels.*;
import com.FileManagerX.BasicEnums.*;
import com.FileManagerX.Globals.*;
import com.FileManagerX.Interfaces.*;
import com.FileManagerX.Factories.*;

public class Scanner extends com.FileManagerX.Processes.BasicProcess implements IScanner {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public final static String ERROR_SOCKET_BUILD_FAILED = "Socket is Error";
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private MachineInfo serverMachineInfo;
	private com.FileManagerX.Interfaces.ISocketS socket;
	private long index;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setIndex(long index) {
		this.index = index;
		return true;
	}
	public boolean setIndex() {
		this.index = ++com.FileManagerX.Globals.Configurations.Next_ScannerIndex;
		return true;
	}
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
		this.setName();
		return true;
	}
	public boolean setSocket(com.FileManagerX.BasicEnums.SocketType type) {
		this.socket = type.getSocketS();
		this.socket.setServerMachineInfo(this.serverMachineInfo);
		boolean ok = this.socket.setSocket();
		this.setName();
		return ok;
	}
	public boolean setName() {
		if(socket == null) {
			return false;
		}
		String name = "@" + Scanner.this.serverMachineInfo.getIp() + ":" +
				Scanner.this.serverMachineInfo.getPort();
		return super.setName(name);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public long getIndex() {
		return index;
	}
	public MachineInfo getServerMachineInfo() {
		return this.serverMachineInfo;
	}
	public com.FileManagerX.Interfaces.ISocketS getSocket() {
		return this.socket;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Scanner() {
		initThis();
	}
	private void initThis() {
		this.serverMachineInfo = Datas.ServerMachine;
		this.socket = null;
		this.setRunnable(new RunImpl());
		this.setPermitIdle(com.FileManagerX.Globals.Configurations.TimeForPermitIdle_Scanner);
		this.setIndex();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private class RunImpl implements com.FileManagerX.Processes.BasicProcess.Runnable {
		public String run() {
			if(Scanner.this.socket == null) {
				return ERROR_SOCKET_BUILD_FAILED;
			}
			
			while(!isAbort() && !socket.isClosed() && !com.FileManagerX.Globals.Configurations.Close) {
				try {
					com.FileManagerX.Interfaces.ISocketC clientSocket = socket.receive();
					
					IServerConnection sc = CommunicatorFactory.createServerConnection();
					sc.setSocket(clientSocket);
					sc.setServerMachineInfo(serverMachineInfo);
					sc.startProcess();
					
					IClientConnection cc = CommunicatorFactory.createClientConnection();
					cc.setIndex(sc.getIndex());
					cc.setSocket(clientSocket);
					cc.setClientMachineInfo(serverMachineInfo);
					cc.startProcess();
					
					sc.setBrother(cc);
					cc.setBrother(sc);
					
				}catch(Exception e) {
					ErrorType.COMMUNICATOR_RUNNING_FAILED.register("Server Scanner Running Failed", e.toString());
					return e.toString();
				}
			}
			
			return null;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
