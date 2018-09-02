package com.FileManagerX.Interfaces;

public interface IProcess extends IPublic {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setProcessIndex(long index);
	public boolean setProcessIndex();
	public boolean setThread(com.FileManagerX.Processes.BasicProcess.MyThread thread);
	public boolean setName(String name);
	public boolean setBegin(long begin);
	public boolean setBegin();
	public boolean setEnd(long end);
	public boolean setEnd();
	public boolean setPermitIdle(long permitIdle);
	public boolean setPermitIdle();
	public boolean setRunnable(com.FileManagerX.Processes.BasicProcess.Runnable run);
	public boolean setError(String error);
	
	public boolean setIsFinished(boolean isFinished);
	public boolean setIsRunning(boolean isRunning);
	public boolean setIsRestart(boolean isRestart);
	public boolean setIsAbort(boolean isAbort);
	public boolean setIsStop(boolean isStop);
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public long getProcessIndex();
	public com.FileManagerX.Processes.BasicProcess.MyThread getThread();
	public String getName();
	public long getBegin();
	public long getEnd();
	public long getPermitIdle();
	public long getIdleTime();
	public com.FileManagerX.Processes.BasicProcess.Runnable getRunable();
	public String getError();
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean isFinished();
	public boolean isRunning();
	public boolean isRestart();
	public boolean isAbort();
	public boolean isStop();
	public boolean isIdle();
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean startProcess();
	public boolean stopProcess();
	public boolean continueProcess();
	public boolean restartProcess();
	public boolean exitProcess();
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
