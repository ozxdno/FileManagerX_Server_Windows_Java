package com.FileManagerX.Processes;

public class BasicProcess implements com.FileManagerX.Interfaces.IProcess {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static String ERROR_RUNNING_METHOD = "Thread is Running in Runnable.run";
	public final static String ERROR_NULL_THREAD = "Thread is NULL";
	public final static String ERROR_WRONG_THREAD_TYPE = "Thread is not instanceof MyThread";
	public final static String ERROR_START_FAILED = "Process Start Failed";
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private boolean finished;
	private boolean running;
	private boolean restart;
	private boolean abort;
	private boolean stop;
	
	private long processIndex;
	private MyThread thread;
	private String name;
	private long begin;
	private long end;
	private long permitIdle;
	
	private Runnable run;
	private String error;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setProcessIndex(long index) {
		this.processIndex = index;
		return true;
	}
	public boolean setProcessIndex() {
		this.processIndex = ++com.FileManagerX.Globals.Configurations.Next_ProcessIndex;
		return true;
	}
	public boolean setThread(MyThread thread) {
		if(thread == null) {
			return false;
		}
		this.thread = thread;
		return true;
	}
	public boolean setName(String name) {
		if(name == null) {
			return false;
		}
		this.name = name;
		this.thread.setName(name);
		return true;
	}
	public boolean setBegin(long begin) {
		if(begin < 0) {
			begin = 0;
		}
		this.begin = begin;
		return true;
	}
	public boolean setBegin() {
		this.begin = com.FileManagerX.Tools.Time.getTicks();
		return true;
	}
	public boolean setEnd(long end) {
		if(end < 0) {
			end = 0;
		}
		this.end = end;
		return true;
	}
	public boolean setEnd() {
		this.end = com.FileManagerX.Tools.Time.getTicks();
		return true;
	}
	public boolean setPermitIdle(long permitIdle) {
		if(permitIdle < 0) {
			permitIdle = 0;
		}
		this.permitIdle = permitIdle;
		return true;
	}
	public boolean setPermitIdle() {
		this.permitIdle = com.FileManagerX.Globals.Configurations.TimeForPermitIdle_Thread;
		return true;
	}
	
	public boolean setRunnable(Runnable run) {
		if(run == null) {
			return false;
		}
		this.run = run;
		return true;
	}
	public boolean setError(String error) {
		this.error = error;
		return true;
	}
	
	public boolean setIsFinished(boolean isFinished) {
		this.finished = isFinished;
		return true;
	}
	public boolean setIsRunning(boolean isRunning) {
		this.running = isRunning;
		return true;
	}
	public boolean setIsRestart(boolean isRestart) {
		this.restart = isRestart;
		return true;
	}
	public boolean setIsAbort(boolean isAbort) {
		this.abort = isAbort;
		return true;
	}
	public boolean setIsStop(boolean isStop) {
		this.stop = isStop;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean isFinished() {
		return this.finished;
	}
	public boolean isRunning() {
		return this.running;
	}
	public boolean isRestart() {
		return this.restart;
	}
	public boolean isAbort() {
		return this.abort;
	}
	public boolean isStop() {
		return this.stop;
	}
	public boolean isIdle() {
		return this.getIdleTime() > this.permitIdle;
	}
	
	public long getProcessIndex() {
		return this.processIndex;
	}
	public MyThread getThread() {
		return thread;
	}
	public String getName() {
		return name;
	}
	public long getBegin() {
		return begin;
	}
	public long getEnd() {
		return end;
	}
	public long getPermitIdle() {
		return permitIdle;
	}
	public long getIdleTime() {
		if(this.end <= this.begin) {
			return 0;
		}
		return com.FileManagerX.Tools.Time.getTicks() - this.end;
	}
	
	public Runnable getRunable() {
		return run;
	}
	public String getError() {
		return error;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public BasicProcess() {
		initThis();
	}
	private void initThis() {
		this.finished = false;
		this.running = false;
		this.restart = true;
		this.abort = false;
		this.stop = false;
		
		this.thread = new MyThread();
		this.setProcessIndex();
		
		this.name = "No Name";
		this.begin = com.FileManagerX.Tools.Time.getTicks();
		this.end = 0;
		this.permitIdle = com.FileManagerX.Globals.Configurations.TimeForPermitIdle_Thread;
		
		this.run = new Runnable() { public String run() { return null; } };
		this.error = null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static interface Runnable {
		public String run();
	}
	public class MyThread extends Thread {
		public void run() {
			finished = true;
			running = true;
			restart = true;
			abort = false;
			stop = false;
			
			while(!abort) {
				if(stop) {
					com.FileManagerX.Tools.Time.sleepUntil(10);
					continue;
				}
				if(restart) {
					finished = false;
					restart = false;
					setBegin();
					error = run.run();
					setEnd();
					finished = true;
				}
				com.FileManagerX.Tools.Time.sleepUntil(10);
			}
			
			running = false;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean startProcess() {
		if(this.running && !this.finished) {
			this.error = ERROR_RUNNING_METHOD;
			return false;
		}
		if(this.running) {
			return true;
		}
		
		if(thread == null) { 
			this.error = ERROR_NULL_THREAD;
			return false;
		}
		if(!(thread instanceof MyThread)) { 
			this.error = ERROR_WRONG_THREAD_TYPE;
			return false;
		}
		
		com.FileManagerX.Globals.Datas.Processes.add(this);
		
		if(this instanceof com.FileManagerX.Executor.Executor) {
			com.FileManagerX.Globals.Datas.Executors.add(this);
		}
		if(this instanceof com.FileManagerX.Operator.Operator) {
			com.FileManagerX.Globals.Datas.Operators.add(this);
		}
		if(this instanceof com.FileManagerX.Communicator.Scanner) {
			com.FileManagerX.Globals.Datas.Scanners.add(this);
		}
		
		this.thread.setName(name);
		this.thread.start();
		return true;
	}
	public boolean stopProcess() {
		this.stop = true;
		return true;
	}
	public boolean continueProcess() {
		this.stop = false;
		return true;
	}
	public boolean restartProcess() {
		if(!this.startProcess()) {
			this.error = ERROR_START_FAILED;
			return false;
		}
		this.restart = true;
		return true;
	}
	public boolean exitProcess() {
		this.abort = true;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
