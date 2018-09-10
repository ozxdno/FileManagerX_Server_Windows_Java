package com.FileManagerX.Commands;

import com.FileManagerX.BasicModels.*;
import com.FileManagerX.BasicEnums.*;

public class LoginType extends BaseCommand {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private ConnectionType type;

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public ConnectionType getType() {
		return this.type;
	}
	
	public com.FileManagerX.Replies.LoginType getReply() {
		if(super.getReply() == null) {
			this.setReply(new com.FileManagerX.Replies.LoginType());
			this.getReply().copyReversePath(this);
		}
		return (com.FileManagerX.Replies.LoginType)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setType(ConnectionType type) {
		if(type == null) {
			return false;
		}
		this.type = type;
		return true;
	}
	
	public boolean setThis(ConnectionType type) {
		boolean ok = true;
		ok &= this.setType(type);
		return ok;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public LoginType() {
		initThis();
	}
	private void initThis() {
		;
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
		c.addToBottom(this.type.toString());
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
			this.type = ConnectionType.valueOf(c.fetchFirstString());
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
		LoginType t = (LoginType)o;
		this.type = t.type;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		LoginType t = (LoginType)o;
		this.type = t.type;
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
		if(!this.isDestMachineIndexExist()) {
			this.getReply().send();
			return false;
		}
		
		boolean ok = this.executeInLocal();
		this.getReply().send();
		return ok;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean executeInLocal() {
		this.type = com.FileManagerX.Globals.Configurations.IsServer ?
				this.type.and(this.type, com.FileManagerX.BasicEnums.ConnectionType.X2S) :
				this.type.and(this.type, com.FileManagerX.BasicEnums.ConnectionType.X2C);
		
		this.getSourConnection().getServerConnection().setType(this.type);
		this.getSourConnection().getClientConnection().setType(this.type.exchange());
		this.getReply().setType(type);
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
