package com.FileManagerX.Processes;

public class Sender extends Thread implements com.FileManagerX.Interfaces.IProcess {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private com.FileManagerX.Interfaces.IClientConnection connection;
	private boolean running;
	private boolean abort;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setConnection(com.FileManagerX.Interfaces.IClientConnection connection) {
		if(connection == null) {
			return false;
		}
		this.connection = connection;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.Interfaces.IClientConnection getConnection() {
		return this.connection;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Sender() {
		initThis();
	}
	private void initThis() {
		running = false;
		abort = false;
	}
	
	public void run() {
		
		com.FileManagerX.Globals.Datas.Processes.add(this);
		
		this.running = true;
		this.abort = false;
		java.util.Random random = new java.util.Random();
		
		while(!abort) {
			int waitTicks = random.nextInt(1000) + 1;
			com.FileManagerX.Tools.Time.sleepUntil(waitTicks);
			com.FileManagerX.Commands.Test t = new com.FileManagerX.Commands.Test();
			t.setThis(String.valueOf(com.FileManagerX.Tools.Time.getTicks()), connection);
			t.send();
			if(t.receive() == null) { break; }
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
		return this.abort;
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
		return false;
	}
	public boolean exitProcess() {
		this.abort = true;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
