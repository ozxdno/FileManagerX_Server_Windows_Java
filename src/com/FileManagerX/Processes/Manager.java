package com.FileManagerX.Processes;

import com.FileManagerX.Interfaces.*;

public class Manager<T extends IProcess> implements com.FileManagerX.Interfaces.ICollection {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private java.util.List<T> processes;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public java.util.List<T> getContent() {
		return processes;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setContent(java.util.List<T> processes) {
		if(processes == null) {
			return false;
		}
		this.processes = processes;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public int size() {
		return this.processes.size();
	}
	public void clear() {
		this.processes.clear();
	}
	@SuppressWarnings("unchecked")
	public boolean add(Object item) {
		if(item == null) {
			return false;
		}
		if(item instanceof IProcess) {
			this.processes.add((T)item);
		}
		return false;
	}
	
	public boolean sortIncrease() {
		return false;
	}
	public boolean sortDecrease() {
		return false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Manager() {
		initThis();
	}
	private void initThis() {
		if(this.processes == null) {
			this.processes = new java.util.LinkedList<>();
		}
		this.processes.clear();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public synchronized void removeIdleProcesses() {
		if(this.processes.size() == 0) {
			return;
		}
		try {
			java.util.Iterator<T> it = this.processes.iterator();
			while(it.hasNext()) {
				T p = it.next();
				if(!p.isRunning() || p.isIdle()) {
					it.remove();
				}
			}
		} catch(Exception e) {
			;
		}
	}
	public synchronized void removeAllProcesses() {
		if(this.processes.size() == 0) {
			return;
		}
		try {
			while(!this.processes.isEmpty()) {
				IProcess p = this.processes.remove(0);
				p.exitProcess();
			}
		} catch(Exception e) {
			;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public int indexOfName(String name) {
		int i = -1;
		for(IProcess p : this.processes) {
			i++;
			if(p.getName().equals(name)) {
				return i;
			}
		}
		return -1;
	}
	public IProcess searchName(String name) {
		for(IProcess p : this.processes) {
			if(p.getName().equals(name)) {
				return p;
			}
		}
		return null;
	}
	public IProcess fetchName(String name) {
		
		java.util.Iterator<T> it = this.processes.iterator();
		while(it.hasNext()) {
			IProcess p = it.next();
			if(p.getName().equals(name)) {
				it.remove();
				return p;
			}
		}
		return null;
	}
	public void deleteName(String name) {
		IProcess p = this.fetchName(name);
		if(p != null) {
			p.exitProcess();
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public int indexOfProcessIndex(long index) {
		int i = -1;
		for(IProcess p : this.processes) {
			i++;
			if(p.getProcessIndex() == index) {
				return i;
			}
		}
		return -1;
	}
	public IProcess searchProcessIndex(long index) {
		for(IProcess p : this.processes) {
			if(p.getProcessIndex() == index) {
				return p;
			}
		}
		return null;
	}
	public IProcess fetchProcessIndex(long index) {
		java.util.Iterator<T> it = this.processes.iterator();
		while(it.hasNext()) {
			IProcess p = it.next();
			if(p.getProcessIndex() == index) {
				it.remove();
				return p;
			}
		}
		return null;
	}
	public void deleteProcessIndex(long index) {
		IProcess p = this.fetchProcessIndex(index);
		if(p != null) {
			p.exitProcess();
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}

