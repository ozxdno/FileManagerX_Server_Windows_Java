package com.FileManagerX.Executor;

public class Executor extends com.FileManagerX.Processes.BasicProcess {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private com.FileManagerX.Interfaces.ITransport receive;
	private long index;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setIndex(long index) {
		this.index = index;
		return true;
	}
	public boolean setIndex() {
		this.index = com.FileManagerX.Globals.Configurations.Next_ExecutorIndex();
		return true;
	}
	public boolean setReceive(com.FileManagerX.Interfaces.ITransport receive) {
		if(receive == null) {
			return false;
		}
		this.receive = receive;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public long getIndex() {
		return index;
	}
	public com.FileManagerX.Interfaces.ITransport getReceive() {
		return this.receive;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Executor() {
		initThis();
	}
	private void initThis() {
		this.receive = null;
		this.setRunnable(new RunImpl());
		this.setPermitIdle(com.FileManagerX.Globals.Configurations.TimeForPermitIdle_Executor);
		this.setName("Executor");
		this.setIndex();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private class RunImpl implements com.FileManagerX.Processes.BasicProcess.Runnable {
		public String run() {
			if(receive != null) {
				if(receive instanceof com.FileManagerX.Interfaces.IReply) {
					com.FileManagerX.Interfaces.IReply rep = (com.FileManagerX.Interfaces.IReply)receive;
					if(rep.isOK()) { com.FileManagerX.Deliver.Deliver.refreshRPP(rep); }
					
					rep.execute();
					if(rep.isStore()) { com.FileManagerX.Globals.Datas.Receiver.add(rep); }
				}
				
				if(receive instanceof com.FileManagerX.Commands.BaseCommand) {
					com.FileManagerX.Commands.BaseCommand cmd = (com.FileManagerX.Commands.BaseCommand)receive;
					cmd.execute();
				}
			}
			return null;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
