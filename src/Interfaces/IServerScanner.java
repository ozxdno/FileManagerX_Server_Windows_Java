package Interfaces;

public interface IServerScanner {
	
	public boolean isRunning();
	
	public boolean initialize(Object infos);
	public boolean connect();
	public void disconnect();
	
	public void removeIdleConnections();
	public void removeAllConnections();
	
	public int indexOf(String ip);
	public IServerConnection search(String ip);
	public int indexOf(long userIndex);
	public IServerConnection search(long userIndex);
}
