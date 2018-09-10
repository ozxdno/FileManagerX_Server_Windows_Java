package com.FileManagerX.Replies;

import com.FileManagerX.BasicModels.User;

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
		c.addToBottom(this.suser.toConfig());
		c.addToBottom(this.cuser.toConfig());
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
			c = this.suser.input(c);
			if(!c.getIsOK()) { return c; }
			c = this.cuser.input(c);
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
	public LoginUser clone() {
		LoginUser c = new LoginUser();
		c.copyValue(this);
		return c;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean executeInLocal() {
		
		if(!this.isOK()) {
			return false;
		}
		
		this.getSourConnection().getServerConnection().setServerUser(cuser);
		this.getSourConnection().getServerConnection().setClientUser(suser);
		this.getSourConnection().getClientConnection().setServerUser(suser);
		this.getSourConnection().getClientConnection().setClientUser(cuser);
		
		if(com.FileManagerX.Globals.Configurations.Refresh) {
			com.FileManagerX.Globals.Configurations.Server_UserIndex = this.suser.getIndex();
			com.FileManagerX.Globals.Configurations.This_UserIndex = this.cuser.getIndex();
			com.FileManagerX.Globals.Datas.ServerUser.copyReference(this.suser);
			com.FileManagerX.Globals.Datas.ThisUser.copyReference(this.cuser);
		}
		
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
