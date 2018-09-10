package com.FileManagerX.Communicator;

public class Sender extends com.FileManagerX.Processes.BasicProcess {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private java.util.PriorityQueue<com.FileManagerX.Interfaces.ITransport> content = null;
	private long index;

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setIndex(long index) {
		this.index = index;
		return true;
	}
	public boolean setIndex() {
		this.index = 1;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public long getIndex() {
		return this.index;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Sender() {
		initThis();
	}
	private void initThis() {
		this.content = new java.util.PriorityQueue<>(com.FileManagerX.Tools.Comparator.Transport.priorityAsc());
		this.setRunnable(new RunImpl());
		this.setPermitIdle(Long.MAX_VALUE);
		this.setName("Sender");
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public synchronized boolean add(com.FileManagerX.Interfaces.ITransport t) {
		return this.content.add(t);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public synchronized com.FileManagerX.Interfaces.ITransport get() {
		if(this.content.isEmpty()) { return null; }
		return this.content.poll();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private class RunImpl implements com.FileManagerX.Processes.BasicProcess.Runnable {
		public String run() {
			while(true) {
				if(!Sender.this.content.isEmpty()) {
					com.FileManagerX.Interfaces.ITransport t = Sender.this.get();
					
					com.FileManagerX.Deliver.Deliver.completeRPP(t);
					com.FileManagerX.Deliver.Deliver.completeBroadcast(t);
					t.getBasicMessagePackage().getBroadcast().broadcast();
					t.getBasicMessagePackage().getRoutePathPackage().updateExecutePart(
							t.getBasicMessagePackage().getDestMachineIndex()
						);
					
					com.FileManagerX.Deliver.Deliver.record(t);
					com.FileManagerX.Deliver.Deliver.deliver(t);
					continue;
				}
				com.FileManagerX.Tools.Time.sleepUntil(1);
			}
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
