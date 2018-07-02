package Interfaces;

public interface IServerConnection extends IConnection {
	
	public boolean setExecutor(Interfaces.ICommandExecutor executor);
	public Interfaces.ICommandExecutor getExecutor();
	
}
