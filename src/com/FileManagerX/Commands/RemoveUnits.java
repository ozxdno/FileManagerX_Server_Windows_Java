package com.FileManagerX.Commands;

import com.FileManagerX.BasicEnums.*;
import com.FileManagerX.BasicModels.Config;
import com.FileManagerX.Globals.*;
import com.FileManagerX.Interfaces.*;
import com.FileManagerX.DataBase.*;

public class RemoveUnits extends BaseCommand {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private com.FileManagerX.DataBase.Unit unit;
	private QueryConditions conditions;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setUnit(com.FileManagerX.DataBase.Unit unit) {
		if(unit == null) {
			return false;
		}
		this.unit = unit;
		return true;
	}
	public boolean setQueryConditions(QueryConditions conditions) {
		this.conditions = conditions;
		return true;
	}
	public boolean setQueryConditions(String conditions) {
		QueryConditions qcs = new QueryConditions();
		try {
			qcs.stringToThis(conditions);
			this.conditions = qcs;
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	public boolean setQueryCondition(QueryCondition condition) {
		QueryConditions qcs = new QueryConditions();
		qcs.add(condition);
		this.conditions = qcs;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.DataBase.Unit getUnit() {
		return this.unit;
	}
	public QueryConditions getQueryConditions() {
		return this.conditions;
	}
	
	public com.FileManagerX.Replies.RemoveUnits getReply() {
		if(super.getReply() == null) { this.setReply(new com.FileManagerX.Replies.RemoveUnits()); }
		return (com.FileManagerX.Replies.RemoveUnits)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public RemoveUnits() {
		initThis();
	}
	public RemoveUnits(String command) {
		initThis();
		this.input(command);
	}
	private void initThis() {
		this.unit = com.FileManagerX.DataBase.Unit.BaseFile;
		this.conditions = new QueryConditions();
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
		c.addToBottom(new Config(this.conditions.output()).getValue());
		
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
			in = c.output();
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			return null;
		}
		
		return this.conditions.input(in);
	}
	public void copyReference(Object o) {
		RemoveUnits qf = (RemoveUnits)o;
		super.copyReference(o);
		this.unit = qf.unit;
		this.conditions = qf.conditions;
	}
	public void copyValue(Object o) {
		RemoveUnits qf = (RemoveUnits)o;
		super.copyValue(o);
		this.unit = qf.unit;
		this.conditions.copyValue(qf.conditions);
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
		if(this.conditions == null) {
			super.getReply().setOK(false);
			super.getReply().setFailedReason("Wrong Query Conditions");
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
		
		com.FileManagerX.Interfaces.IPublic results = (IPublic)dbm.querys(conditions);
		if(results == null) {
			this.getReply().setOK(false);
			this.getReply().setFailedReason("Not Exist");
			return false;
		}
		
		com.FileManagerX.Interfaces.IPublic errors = (IPublic)dbm.removes(results);
		this.getReply().setResults(errors);
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
