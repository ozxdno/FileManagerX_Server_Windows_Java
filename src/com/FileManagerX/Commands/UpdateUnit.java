package com.FileManagerX.Commands;

import com.FileManagerX.BasicModels.*;
import com.FileManagerX.BasicEnums.*;
import com.FileManagerX.Globals.*;
import com.FileManagerX.Interfaces.*;

public class UpdateUnit extends BaseCommand {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private com.FileManagerX.Interfaces.IPublic target;
	private com.FileManagerX.DataBase.Unit unit;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setTarget(com.FileManagerX.Interfaces.IPublic target) {
		if(target == null) {
			return false;
		}
		this.target = target;
		return true;
	}
	public boolean setUnit(com.FileManagerX.DataBase.Unit unit) {
		if(unit == null) {
			return false;
		}
		this.unit = unit;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.Interfaces.IPublic getTarget() {
		return this.target;
	}
	public com.FileManagerX.DataBase.Unit getUnit() {
		return this.unit;
	}
	
	public com.FileManagerX.Replies.UpdateUnit getReply() {
		if(super.getReply() == null) { this.setReply(new com.FileManagerX.Replies.UpdateUnit()); }
		return (com.FileManagerX.Replies.UpdateUnit)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setThis(com.FileManagerX.DataBase.Unit unit, com.FileManagerX.Interfaces.IPublic target) {
		boolean ok = true;
		ok &= this.setTarget(target);
		ok &= this.setUnit(unit);
		return ok;
	}
	public boolean setThis(com.FileManagerX.DataBase.Unit unit, com.FileManagerX.Interfaces.IPublic target,
			com.FileManagerX.Interfaces.IConnection connection) {
		boolean ok = true;
		ok &= this.getBasicMessagePackage().setThis(connection.getClientConnection());
		ok &= this.setConnection(connection);
		ok &= this.setThis(unit, target);
		return ok;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public UpdateUnit() {
		initThis();
	}
	public UpdateUnit(String command) {
		initThis();
		this.input(command);
	}
	private void initThis() {
		this.unit = com.FileManagerX.DataBase.Unit.BaseFile;
		this.target = null;
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
		c.addToBottom(new Config(this.target.output()));
		c.addToBottom(this.unit.toString());
		
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		in = this.target.input(in);
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
		UpdateUnit qf = (UpdateUnit)o;
		super.copyReference(o);
		this.target = qf.target;
		this.unit = qf.unit;
	}
	public void copyValue(Object o) {
		UpdateUnit qf = (UpdateUnit)o;
		super.copyValue(o);
		this.target.copyValue(qf.target);
		this.unit = qf.unit;
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
		
		boolean ok = dbm.update(target);
		if(!ok) {
			this.getReply().setOK(false);
			this.getReply().setFailedReason("Update to Database Failed");
			return false;
		}
		
		this.getReply().setOK(ok);
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
