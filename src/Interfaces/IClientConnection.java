package Interfaces;

public interface IClientConnection extends IConnection {

	public boolean setExecutor(Interfaces.IReplyExecutor executor);
	public Interfaces.IReplyExecutor getExecutor();
	
	public boolean setCommandsManager(Interfaces.ICommandsManager cmdManager);
	public Interfaces.ICommandsManager getCommandsManager();
}
