package com.FileManagerX.Interfaces;

public interface IProcess {
	
	public boolean isFinished();
	public boolean isRunning();
	public boolean isAbort();
	public boolean isStop();
	
	public boolean initialize(Object infos);
	
	public boolean startProcess();
	public boolean stopProcess();
	public boolean continueProcess();
	public boolean restartProcess();
	public boolean exitProcess();
}
