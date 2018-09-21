package com.FileManagerX.Operator;

public class Operator extends com.FileManagerX.Processes.BasicProcess {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private com.FileManagerX.Interfaces.IOperatorPackage op;
	private boolean received;

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setOperatorPackage(com.FileManagerX.Interfaces.IOperatorPackage op) {
		this.op = op;
		return true;
	}
	public boolean setIsReceived(boolean received) {
		this.received = received;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.Interfaces.IOperatorPackage getOperatorPackage() {
		return this.op;
	}
	public boolean isReceived() {
		return this.received;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Operator() {
		initThis();
	}
	private void initThis() {
		;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static abstract class RunImpl implements com.FileManagerX.Processes.BasicProcess.Runnable {
		public com.FileManagerX.Interfaces.IOperatorPackage opackage;
		public com.FileManagerX.Interfaces.ITransport source;
		public Operator operator;
		
		public abstract void restart();
		public abstract void query();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void clear() {
		initThis();
	}
	public String toString() {
		return this.op.toString();
	}
	public com.FileManagerX.BasicModels.Config toConfig() {
		return this.op.toConfig();
	}
	public String output() {
		return this.toConfig().output();
	}
	public com.FileManagerX.BasicModels.Config input(String in) {
		return this.input(new com.FileManagerX.BasicModels.Config(in));
	}
	public com.FileManagerX.BasicModels.Config input(com.FileManagerX.BasicModels.Config c) {
		return this.op.input(c);
	}
	public void copyReference(Object o) {
		if(o instanceof Operator) {
			Operator op = (Operator)o;
			this.op.copyReference(op.op);
		}
	}
	public void copyValue(Object o) {
		if(o instanceof Operator) {
			Operator op = (Operator)o;
			this.op.copyValue(op.op);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}

