package com.FileManagerX.Commands;

import com.FileManagerX.BasicModels.*;
import com.FileManagerX.BasicEnums.*;
import com.FileManagerX.Globals.*;
import com.FileManagerX.Interfaces.*;

public class QuerySize extends BaseCommand {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private com.FileManagerX.DataBase.Unit unit;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setUnit(com.FileManagerX.DataBase.Unit unit) {
		if(unit == null) {
			return false;
		}
		this.unit = unit;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.DataBase.Unit getUnit() {
		return this.unit;
	}
	
	public com.FileManagerX.Replies.QuerySize getReply() {
		if(super.getReply() == null) { this.setReply(new com.FileManagerX.Replies.QuerySize()); }
		return (com.FileManagerX.Replies.QuerySize)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public QuerySize() {
		initThis();
	}
	public QuerySize(String command) {
		initThis();
		this.input(command);
	}
	private void initThis() {
		this.unit = com.FileManagerX.DataBase.Unit.BaseFile;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setThis(com.FileManagerX.DataBase.Unit unit) {
		boolean ok = true;
		ok &= this.setUnit(unit);
		return ok;
	}
	public boolean setThis(com.FileManagerX.DataBase.Unit unit, com.FileManagerX.Interfaces.IConnection connection) {
		boolean ok = true;
		ok &= this.getBasicMessagePackage().setThis(connection.getClientConnection());
		ok &= this.setConnection(connection);
		ok &= this.setThis(unit);
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
		c.addToBottom(this.unit.toString());
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		
		try {
			Config c = new Config(in);
			this.unit = com.FileManagerX.DataBase.Unit.valueOf(c.fetchFirstString());
			if(!c.getIsOK()) { return null; }
			return c.output();
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			return null;
		}
	}
	public void copyReference(Object o) {
		super.copyReference(o);
		QuerySize t = (QuerySize)o;
		this.unit = t.unit;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		QuerySize t = (QuerySize)o;
		this.unit = t.unit;
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
		IDBManager dbm = Datas.DBManagers.searchDepotIndex(this.getBasicMessagePackage().getDestDepotIndex());
		if(dbm == null) { dbm = Datas.DBManager; }
		dbm.setUnit(this.unit);
		
		long size = dbm.size();
		this.getReply().setSize(size);
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
