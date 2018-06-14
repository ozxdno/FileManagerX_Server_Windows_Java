package Communicator;

public interface IClientConnection {

	public boolean isRunning();
	public boolean isBusy();
	public boolean isCommandConnector();
	public boolean isFileConnector();
	
	public boolean writeCommand(String cmd);
	public String readCommand();
	
	public boolean receiveFile(String url);
	public boolean sendFile(String url);
	public long getTotalBytes();
	public long getFinishedBytes();
	
	public void disconnect();
}
