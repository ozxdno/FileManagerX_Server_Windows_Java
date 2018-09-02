package com.FileManagerX.Processes;

public class Manager <T extends com.FileManagerX.Interfaces.IProcess>
	extends com.FileManagerX.Safe.BasicCollections.BasicHashMap<T, Long> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Long getKey(com.FileManagerX.Interfaces.IProcess item) {
		return item == null ? null : item.getProcessIndex();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
}

