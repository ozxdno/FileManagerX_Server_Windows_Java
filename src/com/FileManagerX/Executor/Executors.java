package com.FileManagerX.Executor;

public class Executors implements com.FileManagerX.Interfaces.ICollection {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private java.util.List<Executor> executors;
	private long permitIdle;
	private int permitAmount;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public java.util.List<Executor> getContent() {
		return executors;
	}
	public long getPermitIdle() {
		return this.permitIdle;
	}
	public int getPermitAmount() {
		return this.permitAmount;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setContent(java.util.List<Executor> executors) {
		if(executors == null) {
			return false;
		}
		this.executors = executors;
		return true;
	}
	public boolean setPermitIdle(long idle) {
		if(idle < 0) {
			idle = 0;
		}
		this.permitIdle = idle;
		return true;
	}
	public boolean setPermitAmount(int amount) {
		if(amount < 0) {
			amount = 0;
		}
		this.permitAmount = amount;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public int size() {
		return this.executors.size();
	}
	public void clear() {
		this.executors.clear();
	}
	public boolean add(Object item) {
		if(item == null) {
			return false;
		}
		if(item instanceof Executor) {
			this.executors.add((Executor)item);
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

	public Executors() {
		initThis();
	}
	private void initThis() {
		if(this.executors == null) {
			this.executors = new java.util.LinkedList<>();
		}
		this.executors.clear();
		this.permitIdle = com.FileManagerX.Globals.Configurations.TimeForExecutorIdle;
		this.permitAmount = 10;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public synchronized void removeIdleExecutors() {
		if(this.executors.size() == 0) {
			return;
		}
		if(this.executors.size() < this.permitAmount) {
			return;
		}
		
		try {
			java.util.Iterator<Executor> it = this.executors.iterator();
			while(it.hasNext()) {
				Executor p = it.next();
				if(p.getIdleTime() > this.permitIdle) {
					p.exitProcess();
					it.remove();
				}
			}
		} catch(Exception e) {
			;
		}
	}
	public synchronized void removeAllExecutors() {
		if(this.executors.size() == 0) {
			return;
		}
		try {
			while(!this.executors.isEmpty()) {
				Executor exe = this.executors.remove(0);
				exe.exitProcess();
			}
		} catch(Exception e) {
			;
		}
	}
	public synchronized Executor searchIdleExecutor() {
		
		for(Executor e : this.executors) {
			if(e.isIdle()) { return e; }
		}
		Executor e = new Executor();
		e.startProcess();
		return e;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
