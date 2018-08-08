package com.FileManagerX.Commands;

import com.FileManagerX.BasicEnums.*;
import com.FileManagerX.Globals.*;

public class LoginConnection extends BaseCommand {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.Replies.LoginConnection getReply() {
		if(super.getReply() == null) { this.setReply(new com.FileManagerX.Replies.LoginConnection()); }
		return (com.FileManagerX.Replies.LoginConnection)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public LoginConnection() {
		initThis();
	}
	public LoginConnection(String cmd) {
		initThis();
		this.input(cmd);
	}
	private void initThis() {
		;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void clear() {
		super.clear();
		this.initThis();
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
		Datas.Server.add(this.getConnection().getServerConnection());
		Datas.Client.add(this.getConnection().getClientConnection());
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
