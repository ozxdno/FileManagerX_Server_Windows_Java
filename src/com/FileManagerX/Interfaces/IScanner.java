package com.FileManagerX.Interfaces;

import com.FileManagerX.BasicModels.*;

public interface IScanner extends IProcess {

	public boolean setServerMachineInfo(MachineInfo serverMachineInfo);
	public boolean setSocket(com.FileManagerX.Interfaces.ISocketS socket);
	public boolean setSocket(com.FileManagerX.BasicEnums.SocketType type);
	public boolean setAbort(boolean abort);
	
	public MachineInfo getServerMachineInfo();
	public com.FileManagerX.Interfaces.ISocketS getSocket();
	public boolean isAbort();
	public boolean isRunning();
	
	public boolean connect();
	public void disconnect();
}
