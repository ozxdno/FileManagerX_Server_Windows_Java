package com.FileManagerX.Commands;

import com.FileManagerX.BasicEnums.UserPriority;
import com.FileManagerX.BasicModels.Config;

public class LoginIndex extends BaseCommand {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private long index;
	private int count;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setLoginIndex(long index) {
		this.index = index;
		return true;
	}
	public boolean setCount(int count) {
		this.count = count;
		return true;
	}
	
	public boolean setThis(long index, int count) {
		boolean ok = true;
		ok &= this.setLoginIndex(index);
		ok &= this.setCount(count);
		return ok;
	}
	public boolean setThis(long index, int count, com.FileManagerX.Interfaces.IConnection connection) {
		boolean ok = true;
		ok &= this.getBasicMessagePackage().setThis(connection.getClientConnection());
		ok &= this.setConnection(connection);
		ok &= this.setThis(index, count);
		return ok;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public long getLoginIndex() {
		return this.index;
	}
	public int getCount() {
		return this.count;
	}
	
	public com.FileManagerX.Replies.LoginIndex getReply() {
		if(super.getReply() == null) { this.setReply(new com.FileManagerX.Replies.LoginIndex()); }
		return (com.FileManagerX.Replies.LoginIndex)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public LoginIndex() {
		initThis();
	}
	public LoginIndex(String command) {
		initThis();
		this.input(command);
	}
	private void initThis() {
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
		c.addToBottom(this.index);
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		
		Config c = new Config(in);
		this.index = c.fetchFirstInt();
		if(!c.getIsOK()) { return null; }
		
		return c.output();
	}
	public void copyReference(Object o) {
		super.copyReference(o);
		LoginIndex t = (LoginIndex)o;
		this.index = t.index;
		this.count = t.count;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		LoginIndex t = (LoginIndex)o;
		this.index = t.index;
		this.count = t.count;
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
		long loginIndex = this.getConnection().getIndex();
		if(loginIndex < this.index) {
			loginIndex = this.index;
		}
		
		this.getConnection().setIndex(loginIndex);
		this.getConnection().getBrother().setIndex(loginIndex);
		this.getReply().setThis(index, count);
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
