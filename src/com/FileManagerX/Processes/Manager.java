package com.FileManagerX.Processes;

public class Manager <T extends com.FileManagerX.Interfaces.IProcess>
	extends com.FileManagerX.BasicCollections.BasicCollection<T> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Manager() {
		this.initThis();
	}
	private void initThis() {
		this.setContent(new com.FileManagerX.Safe.BasicCollections.BasicHashMap<>());
		this.setKey(new KeyForIndex());
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void removeIdleProcesses() {
		com.FileManagerX.Interfaces.IIterator<T> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.Interfaces.IProcess p = it.getNext();
			if(!p.isRunning() || p.isIdle()) {
				p.exitProcess();
				it.remove();
			}
		}
	}
	public void removeAllProcesses() {
		com.FileManagerX.Interfaces.IIterator<T> it = this.getIterator();
		while(it.hasNext()) {
			it.getNext().exitProcess();
			it.remove();
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.Interfaces.IProcess searchByProcessIndex(long index) {
		com.FileManagerX.Interfaces.IIterator<T> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.Interfaces.IProcess p = it.getNext();
			if(p.getProcessIndex() == index) { return p; }
		}
		return null;
	}
	public com.FileManagerX.Interfaces.IProcess fetchByProcessIndex(long index) {
		com.FileManagerX.Interfaces.IIterator<T> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.Interfaces.IProcess p = it.getNext();
			if(p.getProcessIndex() == index) {
				it.remove();
				return p;
			}
		}
		return null;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static class KeyForIndex implements com.FileManagerX.Interfaces.ICollection.IKey {
		public Object getKey(Object item) {
			if(item instanceof com.FileManagerX.Interfaces.IProcess) {
				com.FileManagerX.Interfaces.IProcess i = (com.FileManagerX.Interfaces.IProcess)item;
				return i.getProcessIndex();
			}
			return null;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}

