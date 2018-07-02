package Interfaces;

public interface IClientConnection extends IConnection {

	public boolean setExecutor(Interfaces.IReplyExecutor executor);
	public Interfaces.IReplyExecutor getExecutor();
	
}
