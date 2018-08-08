package com.FileManagerX.Commands;

import com.FileManagerX.BasicEnums.UserPriority;
import com.FileManagerX.BasicModels.*;
import com.FileManagerX.Interfaces.*;

public class Output extends BaseCommand {

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
	
	public com.FileManagerX.Replies.Output getReply() {
		
		return (com.FileManagerX.Replies.Output)super.getReply();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Output() {
		initThis();
	}
	private void initThis() {
		this.iop = com.FileManagerX.Factories.CommunicatorFactory.createIOP();
		this.index = 0;
		this.setReply(new com.FileManagerX.Replies.Output());
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
		
		if(this.isArriveTargetMachine()) {
			boolean ok = this.executeInLocal();
			this.getReply().send();
			return ok;
		}
		else {
			return this.deliver();
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean executeInLocal() {
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
