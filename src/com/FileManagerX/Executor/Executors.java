package com.FileManagerX.Executor;

public class Executors extends com.FileManagerX.Processes.Pool<Executor> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Long getKey(Executor item) {
		return item == null ? null : item.getIndex();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Executor createT() {
		return new Executor();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
