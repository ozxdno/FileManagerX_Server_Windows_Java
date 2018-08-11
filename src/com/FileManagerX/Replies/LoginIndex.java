package com.FileManagerX.Replies;

import com.FileManagerX.BasicModels.*;

public class LoginIndex extends BaseReply {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static String FAILED_OVER_COUNT = "Login Times over Count";

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private int index;
	private int count;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setLoginIndex(int index) {
		this.index = index;
		return true;
	}
	public boolean setCount(int count) {
		this.count = count;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public int getLoginIndex() {
		return this.index;
	}
	public int getCount() {
		return this.count;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public LoginIndex() {
		initThis();
	}
	private void initThis() {
		this.index = 0;
		this.count = 0;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setThis(int index, int count) {
		boolean ok = true;
		ok &= this.setLoginIndex(index);
		ok &= this.setCount(count);
		return ok;
	}
	public boolean setThis(int index, int count, com.FileManagerX.Interfaces.IConnection connection) {
		boolean ok = true;
		ok &= this.getBasicMessagePackage().setThis(connection.getClientConnection());
		ok &= this.setConnection(connection);
		ok &= this.setThis(index, count);
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
		
		boolean ok = this.executeInLocal();
		if(ok) { this.store(); }
		return ok;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean executeInLocal() {
		
		if(!this.isOK()) {
			this.getConnection().disconnect();
			return false;
		}
		
		if(com.FileManagerX.Globals.Datas.Server.getNext_ConnectionIndex() > this.index) {
			this.count--;
			if(this.count < 0) {
				this.setThis(false, FAILED_OVER_COUNT);
				return false;
			}
			else {
				
			}
		}
		
		com.FileManagerX.Globals.Datas.Server.setNext_ConnectionIndex(index);
		this.getConnection().setIndex(index);
		this.getConnection().getBrother().setIndex(index);
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
