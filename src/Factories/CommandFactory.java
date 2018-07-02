package Factories;

public class CommandFactory {

	public final static Interfaces.ICommandExecutor createExecutor() {
		return new Commands.Executor();
	}
	
}
