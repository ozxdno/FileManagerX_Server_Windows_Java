package Interfaces;

import java.net.Socket;

public interface IClientConnection {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setServerMachineInfo(BasicModels.MachineInfo serverMachineInfo);
	public boolean setClientMachineInfo(BasicModels.MachineInfo clientMachineInfo);
	public boolean setUser(BasicModels.User user);
	public boolean setExcutor(Interfaces.IExcuteClientCommand excutor);
	
	public boolean setReceiveCommand(String receiveCommand);
	public boolean setSendCommand(String sendCommand);
	public boolean setIsCommandConnector(boolean isCommandConnector);
	
	public boolean setReceiveFile(BasicModels.BaseFile f);
	public boolean setSendFile(BasicModels.BaseFile f);
	public boolean setIsFileConnector(boolean isFileConnector);
	
	public boolean setSocket(Socket socket);
	public boolean setSocket();
	public boolean setAbort(boolean abort);
	public boolean setBusy(boolean busy);
	
	public boolean setLastReceiveTime(long lastReceiveTime);
	public boolean setLastReceiveTime();
	public boolean setBufferSize(int bufferSize);
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public BasicModels.MachineInfo getServerMachineInfo();
	public BasicModels.MachineInfo getClientMachineInfo();
	public BasicModels.User getUser();
	public Interfaces.IExcuteClientCommand getExcutor();
	
	public String getReceiveCommand();
	public String getSendCommand();
	public boolean isCommandConnector();
	
	public BasicModels.BaseFile getReceiveFile();
	public BasicModels.BaseFile getSendFile();
	public long getTotalBytes();
	public long getFinishedBytes();
	public boolean isFileConnector();
	
	public Socket getSocket();
	public boolean isAbort();
	public boolean isRunning();
	public boolean isBusy();
	
	public long getLastReceiveTime();
	public int getBufferSize();
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean connect();
	public void disconnect();
}
