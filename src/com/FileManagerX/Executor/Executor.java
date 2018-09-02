package com.FileManagerX.Executor;

public class Executor extends com.FileManagerX.Processes.BasicProcess {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private com.FileManagerX.Interfaces.ITransport receive;
	private long index;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setIndex(long index) {
		this.index = index;
		return true;
	}
	public boolean setIndex() {
		this.index = com.FileManagerX.Globals.Configurations.Next_ExecutorIndex();
		return true;
	}
	public boolean setReceive(com.FileManagerX.Interfaces.ITransport receive) {
		if(receive == null) {
			return false;
		}
		this.receive = receive;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public long getIndex() {
		return index;
	}
	public com.FileManagerX.Interfaces.ITransport getReceive() {
		return this.receive;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Executor() {
		initThis();
	}
	private void initThis() {
		this.receive = null;
		this.setRunnable(new RunImpl());
		this.setPermitIdle(com.FileManagerX.Globals.Configurations.TimeForPermitIdle_Executor);
		this.setName("Executor");
		this.setIndex();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private class RunImpl implements com.FileManagerX.Processes.BasicProcess.Runnable {
		public String run() {
			if(receive != null) { receive.execute(); }
			return null;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
