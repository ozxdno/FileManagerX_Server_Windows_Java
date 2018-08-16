package com.FileManagerX.Replies;

import com.FileManagerX.BasicModels.Config;
import com.FileManagerX.BasicModels.User;
import com.FileManagerX.Globals.*;

public class LoginUser extends BaseReply {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private User suser;
	private User cuser;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setServerUser(User user) {
		if(user == null) {
			return false;
		}
		this.suser = user;
		return true;
	}
	public boolean setClientUser(User user) {
		if(user == null) {
			return false;
		}
		this.cuser = user;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public User getServerUser() {
		return this.suser;
	}
	public User getClientUser() {
		return this.cuser;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public LoginUser() {
		initThis();
	}
	private void initThis() {
		this.suser = new User();
		this.cuser = new User();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setThis(User serverUser, User clientUser) {
		boolean ok = true;
		ok &= this.setServerUser(serverUser);
		ok &= this.setClientUser(clientUser);
		return ok;
	}
	public boolean setThis(User serverUser, User clientUser, com.FileManagerX.Interfaces.IConnection connection) {
		boolean ok = true;
		ok &= this.getBasicMessagePackage().setThis(connection.getClientConnection());
		ok &= this.setConnection(connection);
		ok &= this.setThis(serverUser, clientUser);
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
		c.addToBottom(new Config(this.suser.output()));
		c.addToBottom(new Config(this.cuser.output()));
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		in = this.suser.input(in);
		if(in == null) {
			return null;
		}
		return this.cuser.input(in);
	}
	public void copyReference(Object o) {
		super.copyReference(o);
		LoginUser qf = (LoginUser)o;
		this.suser = qf.suser;
		this.cuser = qf.cuser;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		LoginUser qf = (LoginUser)o;
		this.suser.copyValue(qf.suser);
		this.cuser.copyValue(qf.cuser);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean executeInLocal() {
		
		if(!this.isOK()) {
			this.getConnection().exitProcess();
			return false;
		}
		
		this.getConnection().getServerConnection().setServerUser(cuser);
		this.getConnection().getServerConnection().setClientUser(suser);
		
		this.getConnection().getClientConnection().setServerUser(suser);
		this.getConnection().getClientConnection().setClientUser(cuser);
		
		Configurations.This_UserIndex = cuser.getIndex();
		Datas.ThisUser.copyReference(cuser);
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
