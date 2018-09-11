package com.FileManagerX.Processes;

public class Pool <T extends com.FileManagerX.Interfaces.IProcess>
	extends com.FileManagerX.BasicCollections.BasicCollection<T> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static com.FileManagerX.Interfaces.ICollection.IKey KeyForIndex =
		new com.FileManagerX.Interfaces.ICollection.IKey() {
			public Object getKey(Object item) {
				if(item instanceof com.FileManagerX.Interfaces.IProcess) {
					com.FileManagerX.Interfaces.IProcess i = (com.FileManagerX.Interfaces.IProcess)item;
					return i.getProcessIndex();
				}
				return null;
			}
		};
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private int size;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setPoolSize(int size) {
		this.size = size;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public int getPoolSize() {
		return size;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Pool() {
		initThis();
	}
	public Pool(int size) {
		initThis();
		this.setPoolSize(size);
	}
	private void initThis() {
		this.setContent(new com.FileManagerX.BasicCollections.BasicHashMap<>());
		this.setKey(KeyForIndex);
		this.setSafe(true);
		this.size = 0;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void removeIdleProcesses() {
		com.FileManagerX.Interfaces.IIterator<T> it = this.getIterator();
		while(it.hasNext()) {
			T p = it.getNext();
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

	public T nextIdleProcess() {
		com.FileManagerX.Interfaces.IIterator<T> it = this.getIterator();
		while(it.hasNext()) {
			T p = it.getNext();
			if(p.isRunning() && p.isFinished()) {
				p.setIsFinished(false);
				return p;
			}
		}
		
		if(size <= 0 || this.size() < this.getPoolSize()) {
			T p = this.createT();
			p.startProcess();
			return p;
		}
		
		return null;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public T searchByProcessIndex(long index) {
		com.FileManagerX.Interfaces.IIterator<T> it = this.getIterator();
		while(it.hasNext()) {
			T p = it.getNext();
			if(p.getProcessIndex() == index) { return p; }
		}
		return null;
	}
	public T fetchByProcessIndex(long index) {
		com.FileManagerX.Interfaces.IIterator<T> it = this.getIterator();
		while(it.hasNext()) {
			T p = it.getNext();
			if(p.getProcessIndex() == index) {
				it.remove();
				return p;
			}
		}
		return null;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
