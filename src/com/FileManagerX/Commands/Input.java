package com.FileManagerX.Commands;

import com.FileManagerX.BasicEnums.UserPriority;
import com.FileManagerX.Interfaces.*;

public class Input extends BaseCommand {

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
	
	public boolean setThis(IIOPackage iop, long index) {
		boolean ok = true;
		ok &= this.setIOPackage(iop);
		ok &= this.setIndex(index);
		return ok;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public IIOPackage getIOPackage() {
		return this.iop;
	}
	public long getIndex() {
		return this.index;
	}
	
	public com.FileManagerX.Replies.Input getReply() {
		if(super.getReply() == null) { this.setReply(new com.FileManagerX.Replies.Input()); }
		return (com.FileManagerX.Replies.Input)super.getReply();
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
		
		if(!c.getIsOK()) { return c; }
		c = super.input(c);
		if(!c.getIsOK()) { return c; }
		c = this.iop.input(c);
		if(!c.getIsOK()) { return c; }
		this.index = c.fetchFirstLong();
		if(!c.getIsOK()) { return c; }
		
		return c;
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
	
	public boolean execute() {
		if(!this.isConnected()) {
			this.getReply().send();
			return false;
		}
		if(!this.isLogin()) {
			this.getReply().send();
			return false;
		}
		if(!this.isUserIndexRight()) {
			this.getReply().send();
			return false;
		}
		if(!this.isPasswordRight()) {
			this.getReply().send();
			return false;
		}
		if(!this.isPriorityEnough(UserPriority.Member)) {
			this.getReply().send();
			return false;
		}
		
		boolean ok = this.executeInLocal();
		this.getReply().send();
		return ok;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean executeInLocal() {
		return true;
		/*
		com.FileManagerX.Operator.Operator op = com.FileManagerX.Globals.Datas.Operators.searchByKey(index);
		
		if(op == null) {
			op = new com.FileManagerX.Operator.Operator();
			
			this.index = op.getIndex();
			this.iop.setType(com.FileManagerX.BasicEnums.IOPType.READ_DEST);
			this.iop.createStream();
			this.getReply().setThis(iop, index);
			return op.startProcess();
		}
		else {
			this.iop.copyValue(((Input)op.getSource()).iop);
			this.iop.setContent("");
			
			this.getReply().setIOPackage(iop);
			this.getReply().setIndex(op.getIndex());
			return op.restartProcess();
		}
		*/
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
