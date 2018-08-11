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
	public boolean setThis(ConnectionType type, com.FileManagerX.Interfaces.IConnection connection) {
		boolean ok = true;
		ok &= this.getBasicMessagePackage().setThis(connection.getClientConnection());
		ok &= this.setThis(type);
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
	public String output() {
		Config c = new Config();
		c.setField(this.getClass().getSimpleName());
		c.addToBottom(new Config(super.output()));
		c.addToBottom(this.type.toString());
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		Config c = new Config(in);
		try {
			this.type = ConnectionType.valueOf(c.fetchFirstString());
			if(!c.getIsOK()) { return null; }
		} catch(Exception e) {
			return null;
		}
		
		return c.output();
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
		this.getConnection().setType(this.type);
		this.getConnection().getBrother().setType(type);
		this.getReply().setType(type);
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}