package com.FileManagerX.Communicator;

public class Scanners extends com.FileManagerX.Processes.Manager<com.FileManagerX.Interfaces.IScanner> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Scanners() {
		this.initThis();
	}
	private void initThis() {
		this.setKey(new KeyForType());
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static class KeyForIndex implements com.FileManagerX.Interfaces.ICollection.IKey {
		public Object getKey(Object item) {
			if(item instanceof com.FileManagerX.Interfaces.IScanner) {
				com.FileManagerX.Interfaces.IScanner i = (com.FileManagerX.Interfaces.IScanner)item;
				return i.getIndex();
			}
			return null;
		}
	}
	public static class KeyForType implements com.FileManagerX.Interfaces.ICollection.IKey {
		public Object getKey(Object item) {
			if(item instanceof com.FileManagerX.Interfaces.IScanner) {
				com.FileManagerX.Interfaces.IScanner i = (com.FileManagerX.Interfaces.IScanner)item;
				return i.getSocket().getType();
			}
			return null;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
}
