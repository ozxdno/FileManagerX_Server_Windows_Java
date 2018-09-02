package com.FileManagerX.Replies;

import com.FileManagerX.BasicEnums.*;

public class LoginType extends BaseReply {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private ConnectionType type;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setType(ConnectionType type) {
		if(type == null) {
			return false;
		}
		this.type = type;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public ConnectionType getType() {
		return this.type;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public LoginType() {
		initThis();
	}
	private void initThis() {
		this.type = ConnectionType.TRANSPORT_COMMAND;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setThis(ConnectionType type) {
		boolean ok = true;
		ok &= this.setType(type);
		return ok;
	}
	public boolean setThis(ConnectionType type, com.FileManagerX.Interfaces.IConnection connection) {
		boolean ok = true;
		ok &= this.getBasicMessagePackage().setThis(connection.getClientConnection());
		ok &= this.setConnection(connection);
		ok &= this.setThis(type);
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
			this.type = com.FileManagerX.BasicEnums.ConnectionType.valueOf(c.fetchFirstString());
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
	
	public boolean executeInLocal() {
		
		if(!this.isOK()) {
			return false;
		}
		
		this.getConnection().setType(type);
		this.getConnection().getBrother().setType(type);
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
