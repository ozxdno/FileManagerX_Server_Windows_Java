package com.FileManagerX.Processes;

import com.FileManagerX.Interfaces.*;

public class Manager implements IProcessManager {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private java.util.LinkedList<IProcess> processes;
	private java.util.LinkedList<Long> startTime;
	private long permitIdle;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public java.util.LinkedList<IProcess> getContent() {
		return processes;
	}
	public long getPermitIdle() {
		return this.permitIdle;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setContent(java.util.LinkedList<IProcess> processes) {
		if(processes == null) {
			return false;
		}
		this.processes = processes;
		return true;
	}
	public boolean setPermitIdle(long idle) {
		if(idle < 0) {
			idle = 0;
		}
		this.permitIdle = idle;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public int size() {
		return this.processes.size();
	}
	public void clear() {
		this.processes.clear();
	}
	public boolean add(Object item) {
		if(item == null) {
			return false;
		}
		if(item instanceof IProcess) {
			this.processes.add((IProcess)item);
			this.startTime.add(com.FileManagerX.Tools.Time.getTicks());
			return true;
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
		if(this.startTime == null) {
			this.startTime = new java.util.LinkedList<>();
		}
		this.startTime.clear();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public synchronized void removeIdleProcesses() {
		if(this.processes.size() == 0) {
			return;
		}
		try {
			java.util.Iterator<IProcess> it1 = this.processes.iterator();
			java.util.Iterator<Long> it2 = this.startTime.iterator();
			while(it1.hasNext() && it2.hasNext()) {
				IProcess p = it1.next();
				it2.next();
				
				if(p.isFinished()) {
					it1.remove();
					it2.remove();
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
				IProcess p = this.processes.removeFirst();
				p.exitProcess();
				this.startTime.removeFirst();
			}
		} catch(Exception e) {
			;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public int indexOf(String threadName) {
		int i = -1;
		for(IProcess p : this.processes) {
			Thread t = (Thread)p;
			i++;
			if(t.getName().equals(threadName)) {
				return i;
			}
		}
		return -1;
	}
	public IProcess search(String threadName) {
		for(IProcess p : this.processes) {
			Thread t = (Thread)p;
			if(t.getName().equals(threadName)) {
				return p;
			}
		}
		return null;
	}
	public IProcess fetch(String threadName) {
		
		
		java.util.Iterator<IProcess> it1 = this.processes.iterator();
		java.util.Iterator<Long> it2 = this.startTime.iterator();
		while(it1.hasNext() && it2.hasNext()) {
			IProcess p = it1.next();
			it2.next();
			
			if(((Thread)p).getName().equals(threadName)) {
				it1.remove();
				it2.remove();
				return p;
			}
		}
		return null;
	}
	public void delete(String threadName) {
		IProcess p = this.fetch(threadName);
		if(p != null) {
			p.exitProcess();
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}

