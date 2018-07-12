package Interfaces;

import java.net.Socket;

public interface IConnection {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setServerMachineInfo(BasicModels.MachineInfo serverMachineInfo);
	public boolean setClientMachineInfo(BasicModels.MachineInfo clientMachineInfo);
	public boolean setUser(BasicModels.User user);
	public boolean setType(BasicEnums.ConnectionType type);
	public boolean setIndex(int index);
	public boolean setIndex(); 
	public boolean setConnectionName(String name);
	public boolean setConnectionName();
	
	public boolean setExecutor(Interfaces.IExecutor executor);
	public boolean setFileConnector(Interfaces.IFileConnector fileConnector);
	
	public boolean setCloseServer(boolean closeServer);
	public boolean setExchange(boolean exchange);
	
	public boolean setActiveExecutor(boolean active);
	public boolean setContinueReceiveString();
	public boolean setContinueSendString();
	public boolean setContinueWait();
	
	public boolean setReceiveString(String str);
	public boolean setSendString(String str);
	public boolean setReceiveBuffer(byte[] receiveBuffer);
	public boolean setSendBuffer(byte[] sendBuffer);
	
	public boolean setBufferSize(int bufferSize);
	public boolean setReceiveLength(int length);
	public boolean setSendLength(int length);
	
	public boolean setSocket(Socket socket);
	public boolean setSocket();
	public boolean setAbort(boolean abort);
	public boolean setBusy(boolean busy);
	
	public boolean setLastOperationTime(long lastReceiveTime);
	public boolean setLastOperationTime();
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public BasicModels.MachineInfo getServerMachineInfo();
	public BasicModels.MachineInfo getClientMachineInfo();
	public BasicModels.User getUser();
	public BasicEnums.ConnectionType getType();
	public int getIndex();
	public String getConnectionName();
	
	public Interfaces.IExecutor getExecutor();
	public Interfaces.IFileConnector getFileConnector();
	
	public Socket getSocket();
	public boolean isAbort();
	public boolean isRunning();
	public boolean isBusy();
	
	public boolean getCloseServer();
	public boolean getExchange();
	
	public boolean isActiveExecutor();
	public boolean isContinueReceiveString();
	public boolean isContinueSendString();
	
	public String getReceiveString();
	public String getSendString();
	public byte[] getReceiveBuffer();
	public byte[] getSendBuffer();
	
	public int getBufferSize();
	public int getReceiveLength();
	public int getSendLength();
	
	public long getLastOperationTime();
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean connect();
	public void disconnect();
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
