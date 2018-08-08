package com.FileManagerX.Replies;

import com.FileManagerX.BasicModels.Config;
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
	public boolean setThis(User user, com.FileManagerX.Interfaces.IConnection connection) {
		boolean ok = true;
		ok &= this.getBasicMessagePackage().setThis(connection.getClientConnection());
		ok &= this.setConnection(connection);
		ok &= this.setThis(user);
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
		c.addToBottom(new Config(this.user.output()));
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		return this.user.input(in);
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
