package com.FileManagerX.Interfaces;

public interface IConnection extends IProcess {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setServerMachineInfo(com.FileManagerX.BasicModels.MachineInfo serverMachineInfo);
	public boolean setClientMachineInfo(com.FileManagerX.BasicModels.MachineInfo clientMachineInfo);
	public boolean setServerUser(com.FileManagerX.BasicModels.User user);
	public boolean setClientUser(com.FileManagerX.BasicModels.User user);
	public boolean setType(com.FileManagerX.BasicEnums.ConnectionType type);
	public boolean setIndex(int index);
	public boolean setIndex(); 
	public boolean setConnectionName(String name);
	public boolean setConnectionName();
	
	public boolean setReceiveString(String str);
	public boolean setSendString(String str);
	
	public boolean setSocket(com.FileManagerX.Interfaces.ISocketC socket);
	public boolean setSocket(com.FileManagerX.BasicEnums.SocketType type);
	public boolean setAbort(boolean abort);
	
	public boolean setLastOperationTime(long lastReceiveTime);
	public boolean setLastOperationTime();
	public boolean setPermitIdle(long idle);
	
	public boolean setBrother(com.FileManagerX.Interfaces.IConnection brother);
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.BasicModels.MachineInfo getServerMachineInfo();
	public com.FileManagerX.BasicModels.MachineInfo getClientMachineInfo();
	public com.FileManagerX.BasicModels.User getServerUser();
	public com.FileManagerX.BasicModels.User getClientUser();
	public com.FileManagerX.BasicEnums.ConnectionType getType();
	public int getIndex();
	public String getConnectionName();
	
	public com.FileManagerX.Interfaces.ISocketC getSocket();
	public boolean isAbort();
	public boolean isRunning();
	
	public String getReceiveString();
	public String getSendString();
	
	public long getLastOperationTime();
	public long getPermitIdle();
	
	public com.FileManagerX.Interfaces.IConnection getBrother();
	public com.FileManagerX.Interfaces.IServerConnection getServerConnection();
	public com.FileManagerX.Interfaces.IClientConnection getClientConnection();
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean connect();
	public void disconnect();
	public String login();
	
	public boolean send(com.FileManagerX.Interfaces.ITransport send);
	public boolean store(com.FileManagerX.Interfaces.IReply reply);
	public com.FileManagerX.Interfaces.IReply receive(long index, long wait);
	public com.FileManagerX.Interfaces.IReply search(long index);
	public com.FileManagerX.Interfaces.IReply fetch(long index);
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
