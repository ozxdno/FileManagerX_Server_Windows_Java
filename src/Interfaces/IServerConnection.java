package Interfaces;

public interface IServerConnection {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setServerMachineInfo(BasicModels.MachineInfo serverMachineInfo);
	public boolean setClientMachineInfo(BasicModels.MachineInfo clientMachineInfo);
	public boolean setUser(BasicModels.User user);
	
	public boolean setReceiveCommand(String receiveCommand);
	public boolean setReplyCommand(String replyCommand);
	public boolean setIsCommandConnector(boolean isCommandConnector);
	
	public boolean setReceiveFile(BasicModels.BaseFile f);
	public boolean setSendFile(BasicModels.BaseFile f);
	public boolean setIsFileConnector(boolean isFileConnector);
	public boolean setTotalBytes(long totalBytes);
	
	public boolean setAbort(boolean abort);
	public boolean setBusy(boolean busy);
	
	public boolean setLastReceiveTime(long lastReceiveTime);
	public boolean setLastReceiveTime();
	public boolean setBufferSize(int bufferSize);
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public BasicModels.MachineInfo getServerMachineInfo();
	public BasicModels.MachineInfo getClientMachineInfo();
	public BasicModels.User getUser();
	
	public String getReceiveCommand();
	public String getReplyCommand();
	public boolean isCommandConnector();
	
	public BasicModels.BaseFile getReceiveFile();
	public BasicModels.BaseFile getSendFile();
	public boolean isFileConnector();
	public long getTotalBytes();
	public long getFinishedBytes();
	
	public boolean isAbort();
	public boolean isRunning();
	public boolean isBusy();
	
	public long getLastReceiveTime();
	public int getBufferSize();
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void disconnect();
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
