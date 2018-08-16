package com.FileManagerX.Interfaces;

import com.FileManagerX.BasicModels.*;

public interface IScanner extends IProcess {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setIndex(long index);
	public boolean setIndex();
	public boolean setServerMachineInfo(MachineInfo serverMachineInfo);
	public boolean setSocket(com.FileManagerX.Interfaces.ISocketS socket);
	public boolean setSocket(com.FileManagerX.BasicEnums.SocketType type);
	public boolean setName();
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public long getIndex();
	public MachineInfo getServerMachineInfo();
	public com.FileManagerX.Interfaces.ISocketS getSocket();
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
