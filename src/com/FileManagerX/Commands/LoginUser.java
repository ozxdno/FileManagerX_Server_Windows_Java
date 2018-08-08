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
	public boolean setThis(User serverUser, User clientUser, com.FileManagerX.Interfaces.IConnection connection) {
		boolean ok = true;
		ok &= this.getBasicMessagePackage().setThis(connection.getClientConnection());
		ok &= this.setConnection(connection);
		ok &= this.setThis(serverUser, clientUser);
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
		if(super.getReply() == null) {
			this.setReply(new com.FileManagerX.Replies.LoginUser());
		}
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
		in = suser.input(in);
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
		if(this.isArriveServer()) {
			com.FileManagerX.Globals.Datas.DBManager.setUnit(com.FileManagerX.DataBase.Unit.User);
			this.cuser = (com.FileManagerX.BasicModels.User)
					com.FileManagerX.Globals.Datas.DBManager.query
					("[&] LoginName = '" + this.cuser.getLoginName() + "'");
		}
		
		if(this.cuser == null) {
			this.getReply().setFailedReason("User is NULL");
			this.getReply().setOK(false);
			return false;
		}
		if(!cuser.getPassword().equals(this.getBasicMessagePackage().getPassword())) {
			this.getReply().setOK(false);
			this.getReply().setFailedReason("Wrong Password");
			return false;
		}
		
		this.suser = com.FileManagerX.Globals.Datas.ThisUser;
		
		this.getConnection().getServerConnection().setServerUser(suser);
		this.getConnection().getServerConnection().setClientUser(cuser);
		this.getConnection().getClientConnection().setServerUser(cuser);
		this.getConnection().getClientConnection().setClientUser(suser);
		
		this.getReply().setThis(suser, cuser);
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
