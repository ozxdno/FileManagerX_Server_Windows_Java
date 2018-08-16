package com.FileManagerX.Replies;

import com.FileManagerX.BasicModels.Config;
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
		
		try {
			Config c = new Config(in);
			this.type = ConnectionType.valueOf(c.fetchFirstString());
			if(!c.getIsOK()) { return null; }
			
			return c.output();
		} catch(Exception e) {
			ErrorType.OTHERS.register(e.toString());
			return null;
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
			this.getConnection().exitProcess();
			return false;
		}
		
		this.getConnection().setType(type);
		this.getConnection().getBrother().setType(type);
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
