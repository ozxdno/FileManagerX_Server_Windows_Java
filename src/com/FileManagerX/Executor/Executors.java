package com.FileManagerX.Executor;

public class Executors extends com.FileManagerX.Processes.Manager<Executor> {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Executors() {
		initThis();
	}
	private void initThis() {
		;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public synchronized Executor nextIdleExecutor() {
		
		for(Executor e : this.getContent()) {
			if(e.isFinished()) { return e; }
		}
		Executor e = new Executor();
		e.startProcess();
		return e;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
