package com.FileManagerX.Executor;

public class Executor extends Thread implements com.FileManagerX.Interfaces.IProcess {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private com.FileManagerX.Interfaces.ITransport receive;
	private long edTime;
	private boolean restart;
	private boolean running;
	private boolean abort;
	private boolean idle;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setReceive(com.FileManagerX.Interfaces.ITransport receive) {
		if(receive == null) {
			return false;
		}
		this.receive = receive;
		return true;
	}
	public boolean setEndTime() {
		edTime = com.FileManagerX.Tools.Time.getTicks();
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.Interfaces.ITransport getReceive() {
		return this.receive;
	}
	public long getIdleTime() {
		return com.FileManagerX.Tools.Time.getTicks() - edTime;
	}
	
	public boolean isIdle() {
		return this.idle;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Executor() {
		initThis();
	}
	public Executor(com.FileManagerX.Interfaces.ITransport receive) {
		initThis();
		this.receive = receive;
	}
	private void initThis() {
		restart = false;
		receive = null;
		running = false;
		idle = false;
		edTime = com.FileManagerX.Tools.Time.getTicks();
	}
	
	public void run() {
		
		com.FileManagerX.Globals.Datas.Processes.add(this);
		com.FileManagerX.Globals.Datas.Executors.add(this);
		this.setName("Executor");
		this.running = true;
		this.abort = false;
		
		while(!this.abort) {
			if(this.receive != null && this.restart) {
				
				this.idle = false;
				this.receive.execute();
				this.idle = true;
				this.setEndTime();
			}
			com.FileManagerX.Tools.Time.sleepUntil(1);
		}
		
		this.running = false;
		this.abort = false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean isFinished() {
		return false;
	}
	public boolean isRunning() {
		return running;
	}
	public boolean isAbort() {
		return abort;
	}
	public boolean isStop() {
		return false;
	}
	
	public boolean initialize(Object infos) {
		return true;
	}
	
	public boolean startProcess() {
		if(!this.running) {
			this.start();
		}
		return true;
	}
	public boolean stopProcess() {
		this.abort = true;
		return true;
	}
	public boolean continueProcess() {
		return false;
	}
	public boolean restartProcess() {
		this.restart = true;
		if(!this.running) { this.startProcess(); }
		return true;
	}
	public boolean exitProcess() {
		this.abort = true;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
