package com.FileManagerX.Replies;

import com.FileManagerX.BasicModels.*;
import com.FileManagerX.Interfaces.*;

public class Output extends Operator {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private IIOPackage iop;
	private long index;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setIOPackage(IIOPackage iop) {
		if(iop == null) {
			return false;
		}
		this.iop = iop;
		return true;
	}
	public boolean setIndex(long index) {
		this.index = index;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public IIOPackage getIOPackage() {
		return this.iop;
	}
	public long getIndex() {
		return this.index;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Output() {
		initThis();
	}
	private void initThis() {
		this.iop = com.FileManagerX.Factories.CommunicatorFactory.createIOP();
		this.index = 0;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void clear() {
		super.clear();
		initThis();
	}
	public String toString() {
		return this.output();
	}
	public String output() {
		Config c = new Config();
		c.setField(this.getClass().getSimpleName());
		c.addToBottom(new Config(super.output()));
		c.addToBottom(new Config(this.iop.output()));
		c.addToBottom(this.index);
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		in = this.iop.input(in);
		if(in == null) {
			return null;
		}
		
		Config c = new Config(in);
		this.index = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		
		return c.output();
	}
	public void copyReference(Object o) {
		super.copyReference(o);
		Output op = (Output)o;
		this.iop.copyReference(op.iop);
		this.index = op.index;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		Output op = (Output)o;
		this.iop.copyValue(op.iop);
		this.index = op.index;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean execute() {
		
		boolean ok = this.executeInLocal();
		this.store();
		return ok;
	}
	public boolean executeInLocal() {
		
		if(!this.isOK()) {
			return false;
		}
		
		com.FileManagerX.Operator.Operator op = com.FileManagerX.Globals.Datas.Operators.search(index);
		if(op == null) {
			op = new com.FileManagerX.Operator.Operator();
			op.register();
			op.setSource(this);
			return op.startProcess();
		}
		else {
			((Output)op.getSource()).iop.copyReference(iop);
			return op.restartProcess();
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
