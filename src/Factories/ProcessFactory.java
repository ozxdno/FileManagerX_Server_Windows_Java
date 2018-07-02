package Factories;

public class ProcessFactory {

	public static final Interfaces.IProcess createProcessMain() {
		return new Processes.MainProcess();
	}
	
}
