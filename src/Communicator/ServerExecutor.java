package Communicator;

public class ServerExecutor implements Interfaces.ICommandExecutor {
	
	private Commands.Executor executor;
	
	public ServerExecutor() {
		initThis();
	}
	private void initThis() {
		executor = new Commands.Executor();
	}
	
	public boolean execute(Interfaces.IConnection connection) {
		return executor.execute(connection);
	}
}
