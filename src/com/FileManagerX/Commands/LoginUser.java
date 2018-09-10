package com.FileManagerX.Commands;

import com.FileManagerX.BasicModels.*;

public class LoginUser extends BaseCommand {

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
	
	public boolean setThis(User serverUser, User clientUser) {
		boolean ok = true;
		ok &= this.setServerUser(serverUser);
		ok &= this.setClientUser(clientUser);
		return ok;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public User getServerUser() {
		return this.suser;
	}
	public User getClientUser() {
		return this.cuser;
	}
	
	public com.FileManagerX.Replies.LoginUser getReply() {
		if(super.getReply() == null) { this.setReply(new com.FileManagerX.Replies.LoginUser()); }
		return (com.FileManagerX.Replies.LoginUser)super.getReply();
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

	public void clear() {
		super.clear();
		initThis();
	}
	public String toString() {
		return this.output();
	}
	public com.FileManagerX.BasicModels.Config toConfig() {
		Config c = new Config();
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
		
		if(!c.getIsOK()) { return c; }
		c = super.input(c);
		if(!c.getIsOK()) { return c; }
		c = this.suser.input(c);
		if(!c.getIsOK()) { return c; }
		c = this.cuser.input(c);
		if(!c.getIsOK()) { return c; }
		
		return c;
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
	
	public boolean execute() {
		if(!this.isConnected()) {
			this.getReply().send();
			return false;
		}
		
		boolean ok = this.executeInLocal();
		this.getReply().send();
		return ok;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean executeInLocal() {
		
		if(this.getBasicMessagePackage().isDirect()) {
			this.suser = com.FileManagerX.Globals.Datas.ThisUser;
			this.getSourConnection().getServerConnection().setServerUser(suser);
			this.getSourConnection().getServerConnection().setClientUser(cuser);
			this.getSourConnection().getClientConnection().setServerUser(cuser);
			this.getSourConnection().getClientConnection().setClientUser(suser);
			this.getReply().setThis(suser, cuser);
			return true;
		}
		
		boolean ok = com.FileManagerX.Globals.Datas.DBManager.query(
				"qcs = 1|[&] loginName = " + this.cuser.getLoginName(),
				this.cuser,
				com.FileManagerX.DataBase.Unit.User
			);
		if(!ok) {
			this.getReply().setFailedReason("No Found User");
			this.getReply().setOK(false);
			return false;
		}
		if(!cuser.getPassword().equals(this.getBasicMessagePackage().getPassword())) {
			this.getReply().setOK(false);
			this.getReply().setFailedReason("Wrong Password");
			return false;
		}
		
		this.suser = com.FileManagerX.Globals.Datas.ThisUser;
		this.getSourConnection().getServerConnection().setServerUser(suser);
		this.getSourConnection().getServerConnection().setClientUser(cuser);
		this.getSourConnection().getClientConnection().setServerUser(cuser);
		this.getSourConnection().getClientConnection().setClientUser(suser);
		this.getReply().setThis(suser, cuser);
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
