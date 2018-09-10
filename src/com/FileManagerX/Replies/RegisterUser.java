package com.FileManagerX.Replies;

import com.FileManagerX.BasicModels.User;

public class RegisterUser extends BaseReply {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private User user;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setUser(User user) {
		if(user == null) {
			return false;
		}
		this.user = user;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public User getUser() {
		return this.user;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public RegisterUser() {
		initThis();
	}
	private void initThis() {
		this.user = new User();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setThis(User user) {
		boolean ok = true;
		ok &= this.setUser(user);
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
		c.addToBottom(this.user.toConfig());
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
			c = this.user.input(c);
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
		RegisterUser qf = (RegisterUser)o;
		this.user = qf.user;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		RegisterUser qf = (RegisterUser)o;
		this.user.copyValue(qf.user);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
