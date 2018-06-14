package Processes;

public interface IProcess {
	
	public boolean isRunning();
	
	public boolean initialize(Object infos);
	
	public boolean startProcess();
	public boolean stopProcess();
	public boolean continueProcess();
	public boolean restartProcess();
}
