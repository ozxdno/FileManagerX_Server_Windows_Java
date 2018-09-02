package com.FileManagerX.Replies;

public class LoginIndex extends BaseReply {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static String FAILED_OVER_COUNT = "Login Times over Count";

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
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public long getLoginIndex() {
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
		c.addToBottom(this.index);
		c.addToBottom(this.count);
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
			this.index = c.fetchFirstLong();
			if(!c.getIsOK()) { return c; }
			this.count = c.fetchFirstInt();
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
			return false;
		}
		
		if(com.FileManagerX.Globals.Configurations.Next_ConnectionIndex > this.index) {
			this.count--;
			if(this.count < 0) {
				this.setThis(false, FAILED_OVER_COUNT);
				return false;
			}
			else {
				this.getConnection().setIndex();
				
				com.FileManagerX.Commands.LoginIndex li = new com.FileManagerX.Commands.LoginIndex();
				li.setThis(this.getConnection().getIndex(), count, this.getConnection());
				return li.send();
			}
		}
		
		this.getConnection().setIndex(index);
		this.getConnection().getBrother().setIndex(index);
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
