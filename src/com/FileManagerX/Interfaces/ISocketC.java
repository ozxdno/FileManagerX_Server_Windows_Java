package com.FileManagerX.Interfaces;

import java.io.IOException;

public interface ISocketC {

	public boolean setType(com.FileManagerX.BasicEnums.SocketType type);
	public boolean setSocket(Object socket);
	public boolean setSocket();
	public boolean setServerMachineInfo(com.FileManagerX.BasicModels.MachineInfo server);
	public boolean setClientMachineInfo(com.FileManagerX.BasicModels.MachineInfo client);
	public boolean setClientMachineInfo();
	
	public com.FileManagerX.BasicEnums.SocketType getType();
	public Object getSocket();
	public com.FileManagerX.BasicModels.MachineInfo getServerMachineInfo();
	public com.FileManagerX.BasicModels.MachineInfo getClientMachineInfo();
	
	public boolean setThis(com.FileManagerX.BasicModels.MachineInfo server, 
			com.FileManagerX.BasicModels.MachineInfo client);
	
	public boolean isClosed();
	public boolean isRunning();
	public int receive(byte[] bytes) throws IOException;
	public boolean send(byte[] bytes) throws IOException;
	public boolean close();
}
