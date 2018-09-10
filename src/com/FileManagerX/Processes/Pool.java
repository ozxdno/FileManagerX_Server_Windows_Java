package com.FileManagerX.Processes;

public class Pool <T extends com.FileManagerX.Interfaces.IProcess>
	extends com.FileManagerX.Safe.BasicCollections.BasicHashMap<T, Long> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private int size;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setPoolSize(int size) {
		this.size = size;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Long getKey(com.FileManagerX.Interfaces.IProcess item) {
		return item == null ? null : item.getProcessIndex();
	}
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
		size = 0;
	}
	public T createT() {
		try {
			@SuppressWarnings("unchecked")
			Class<T> entityClass = (Class<T>) 
		        		((java.lang.reflect.ParameterizedType)
		        				getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		        return entityClass.newInstance();
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			e.printStackTrace();
			return null;
		}
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
