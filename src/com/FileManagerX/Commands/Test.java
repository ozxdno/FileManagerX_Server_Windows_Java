package com.FileManagerX.Commands;

import com.FileManagerX.BasicEnums.UserPriority;

public class Test extends BaseCommand {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private String test;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setTestString(String test) {
		if(test == null) {
			return false;
		}
		this.test = test;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public String getTestString() {
		return this.test;
	}
	
	public com.FileManagerX.Replies.Test getReply() {
		if(super.getReply() == null) { this.setReply(new com.FileManagerX.Replies.Test()); }
		return (com.FileManagerX.Replies.Test)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Test() {
		initThis();
	}
	public Test(String command) {
		initThis();
		this.input(command);
	}
	private void initThis() {
		this.test = "";
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setThis(String test) {
		boolean ok = true;
		ok &= this.setTestString(test);
		return ok;
	}
	public boolean setThis(String test, com.FileManagerX.Interfaces.IConnection connection) {
		boolean ok = true;
		ok &= this.getBasicMessagePackage().setThis(connection.getClientConnection());
		ok &= this.setConnection(connection);
		ok &= this.setThis(test);
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
		c.addToBottom_Encode(this.test);
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
			this.test = c.fetchFirstString_Decode();
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
		Test t = (Test)o;
		this.test = t.test;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		Test t = (Test)o;
		this.test = new String(t.test);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean execute() {
		if(!this.isConnected()) {
			this.getReply().send();
			return false;
		}
		if(!this.isLoginUser()) {
			this.getReply().send();
			return false;
		}
		if(!this.isLoginMachine()) {
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
		com.FileManagerX.Replies.Test rep = this.getReply();
		rep.setTestString(test);
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
