package Communicator;

public interface IClientLinker {

	public boolean isRunning();
	
	public boolean initialize(Object infos);
	public boolean connect();
	public void disconnect();
	
	public IClientConnection getConnection();
}
