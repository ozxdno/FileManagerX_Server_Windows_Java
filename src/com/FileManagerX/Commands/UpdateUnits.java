package com.FileManagerX.Commands;

import com.FileManagerX.BasicEnums.*;
import com.FileManagerX.BasicModels.Config;
import com.FileManagerX.Globals.*;
import com.FileManagerX.Interfaces.*;

public class UpdateUnits extends BaseCommand {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private com.FileManagerX.Interfaces.IPublic targets;
	private com.FileManagerX.DataBase.Unit unit;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setTargets(com.FileManagerX.Interfaces.IPublic targets) {
		if(targets == null) {
			return false;
		}
		this.targets = targets;
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

	public com.FileManagerX.Interfaces.IPublic getTargets() {
		return this.targets;
	}
	public com.FileManagerX.DataBase.Unit getUnit() {
		return this.unit;
	}
	
	public com.FileManagerX.Replies.UpdateUnits getReply() {
		if(super.getReply() == null) { this.setReply(new com.FileManagerX.Replies.UpdateUnits()); }
		return (com.FileManagerX.Replies.UpdateUnits)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public UpdateUnits() {
		initThis();
	}
	public UpdateUnits(String command) {
		initThis();
		this.input(command);
	}
	private void initThis() {
		this.unit = com.FileManagerX.DataBase.Unit.BaseFile;
		this.targets = null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setThis(com.FileManagerX.Interfaces.IPublic targets, com.FileManagerX.DataBase.Unit unit) {
		boolean ok = true;
		ok &= this.setTargets(targets);
		ok &= this.setUnit(unit);
		return ok;
	}
	public boolean setThis(com.FileManagerX.Interfaces.IPublic targets, com.FileManagerX.DataBase.Unit unit,
			com.FileManagerX.Interfaces.IConnection connection) {
		boolean ok = true;
		ok &= this.getBasicMessagePackage().setThis(connection.getClientConnection());
		ok &= this.setConnection(connection);
		ok &= this.setThis(targets, unit);
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
		c.addToBottom(new Config(this.targets.output()));
		c.addToBottom(this.unit.toString());
		
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		in = this.targets.input(in);
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
		UpdateUnits qf = (UpdateUnits)o;
		super.copyReference(o);
		this.targets = qf.targets;
		this.unit = qf.unit;
	}
	public void copyValue(Object o) {
		UpdateUnits qf = (UpdateUnits)o;
		super.copyValue(o);
		this.targets.copyValue(qf.targets);
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
		
		com.FileManagerX.Interfaces.IPublic errors = (IPublic)dbm.updates(targets);
		if(errors == null) {
			this.getReply().setOK(false);
			this.getReply().setFailedReason("Update to DataBase Failed");
			return false;
		}
		
		this.getReply().setResults(errors);
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
