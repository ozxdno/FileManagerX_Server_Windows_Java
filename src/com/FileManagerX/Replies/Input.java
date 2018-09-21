package com.FileManagerX.Replies;

import com.FileManagerX.Interfaces.*;

public class Input extends BaseReply {

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

	public Input() {
		initThis();
	}
	private void initThis() {
		this.iop = com.FileManagerX.Factories.CommunicatorFactory.createIOP();
		this.index = 0;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setThis(IIOPackage iop, long index) {
		boolean ok = true;
		ok &= this.setIOPackage(iop);
		ok &= this.setIndex(index);
		return ok;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void clear() {
		super.clear();
		initThis();
	}
	public String toString() {
		return this.output();
	}
	public com.FileManagerX.BasicModels.Config toConfig() {
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config();
		c.setField(this.getClass().getSimpleName());
		c.addToBottom(super.toConfig());
		c.addToBottom(this.iop.toConfig());
		c.addToBottom(this.index);
		return c;
	}
	public String output() {
		return this.toConfig().output();
	}
	public com.FileManagerX.BasicModels.Config input(String in) {
		return this.input(new com.FileManagerX.BasicModels.Config(in));
	}
	public com.FileManagerX.BasicModels.Config input(com.FileManagerX.BasicModels.Config c) {
		if(c == null) { return null; }
		try {
			if(!c.getIsOK()) { return c; }
			c = super.input(c);
			if(!c.getIsOK()) { return c; }
			c = this.iop.input(c);
			if(!c.getIsOK()) { return c; }
			this.index = c.fetchFirstLong();
			if(!c.getIsOK()) { return c; }
			return c;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			c.setIsOK(false);
			return c;
		}
	}
	public void copyReference(Object o) {
		super.copyReference(o);
		Input op = (Input)o;
		this.iop.copyReference(op.iop);
		this.index = op.index;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		Input op = (Input)o;
		this.iop.copyValue(op.iop);
		this.index = op.index;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean executeInLocal() {
		if(!this.isOK()) {
			return false;
		}
		/*
		com.FileManagerX.Operator.Operator op = com.FileManagerX.Globals.Datas.Operators.searchByKey(index);
		if(op == null) {
			op = new com.FileManagerX.Operator.Operator();
			op.setType(com.FileManagerX.BasicEnums.OperateType.INPUT);
			op.setSource(this);
			op.startProcess();
			return true;
		}
		else {
			op.setSource(this);
			op.restartProcess();
			return true;
		}
		*/
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
