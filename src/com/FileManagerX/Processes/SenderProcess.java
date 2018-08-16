package com.FileManagerX.Processes;

public class SenderProcess extends BasicProcess {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private com.FileManagerX.Interfaces.IClientConnection connection;
	private java.util.Random random;
	
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

	public SenderProcess() {
		initThis();
	}
	private void initThis() {
		this.random = new java.util.Random();
		this.connection = null;
		this.setRunnable(new RunImpl());
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private class RunImpl implements BasicProcess.Runnable {
		public String run() {
			int waitTicks = random.nextInt(1000) + 1;
			com.FileManagerX.Tools.Time.sleepUntil(waitTicks);
			
			if(connection != null && connection.isRunning()) {
				com.FileManagerX.Commands.Test t = new com.FileManagerX.Commands.Test();
				t.setThis(String.valueOf(com.FileManagerX.Tools.Time.getTicks()), connection);
				t.send();
			}
			else {
				exitProcess();
			}
			
			return null;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
