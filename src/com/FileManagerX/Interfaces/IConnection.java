package com.FileManagerX.Interfaces;

public interface IConnection extends IProcess {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setServerMachineInfo(com.FileManagerX.BasicModels.MachineInfo serverMachineInfo);
	public boolean setClientMachineInfo(com.FileManagerX.BasicModels.MachineInfo clientMachineInfo);
	public boolean setServerUser(com.FileManagerX.BasicModels.User user);
	public boolean setClientUser(com.FileManagerX.BasicModels.User user);
	public boolean setType(com.FileManagerX.BasicEnums.ConnectionType type);
	public boolean setIndex(long index);
	public boolean setIndex(); 
	public boolean setName();
	
	public boolean setReceiveString(String str);
	public boolean setSendString(String str);
	
	public boolean setSocket(com.FileManagerX.Interfaces.ISocketC socket);
	public boolean setSocket(com.FileManagerX.BasicEnums.SocketType type);

	public boolean setBrother(com.FileManagerX.Interfaces.IConnection brother);
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.BasicModels.MachineInfo getServerMachineInfo();
	public com.FileManagerX.BasicModels.MachineInfo getClientMachineInfo();
	public com.FileManagerX.BasicModels.User getServerUser();
	public com.FileManagerX.BasicModels.User getClientUser();
	public com.FileManagerX.BasicEnums.ConnectionType getType();
	public long getIndex();
	
	public com.FileManagerX.Interfaces.ISocketC getSocket();
	
	public String getReceiveString();
	public String getSendString();
	
	public com.FileManagerX.Interfaces.IConnection getBrother();
	public com.FileManagerX.Interfaces.IServerConnection getServerConnection();
	public com.FileManagerX.Interfaces.IClientConnection getClientConnection();

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean send(com.FileManagerX.Interfaces.ITransport t);
	public String login();
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
