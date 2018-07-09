package Factories;

public class CommandFactory {

	public final static Interfaces.ICommandExecutor createExecutor() {
		return new Commands.Executor();
	}
	
	public final static Interfaces.ICommandsManager createCommandsManager(Interfaces.IClientConnection connection) {
		return new Commands.Manager(connection);
	}
}
