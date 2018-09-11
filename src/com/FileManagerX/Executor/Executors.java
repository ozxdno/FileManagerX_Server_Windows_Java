package com.FileManagerX.Executor;

public class Executors extends com.FileManagerX.Processes.Pool<Executor> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Executors() {
		this.initThis();
	}
	private void initThis() {
		this.setKey(new KeyForIndex());
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static class KeyForIndex implements com.FileManagerX.Interfaces.ICollection.IKey {
		public Object getKey(Object item) {
			if(item instanceof Executor) {
				Executor i = (Executor)item;
				return i.getIndex();
			}
			return null;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
