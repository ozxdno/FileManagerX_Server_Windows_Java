package com.FileManagerX.Commands;

import com.FileManagerX.DataBase.*;
import com.FileManagerX.BasicModels.*;
import com.FileManagerX.BasicEnums.*;
import com.FileManagerX.Globals.*;
import com.FileManagerX.Interfaces.*;

public class RemoveUnit extends BaseCommand {

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
	
	public com.FileManagerX.Replies.RemoveUnit getReply() {
		if(super.getReply() == null) { this.setReply(new com.FileManagerX.Replies.RemoveUnit()); }
		return (com.FileManagerX.Replies.RemoveUnit)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setThis(com.FileManagerX.DataBase.Unit unit, Object conditions) {
		this.conditions.clear();
		boolean ok = true;
		ok &= (conditions != null && (conditions instanceof QueryConditions) && 
				this.setQueryConditions((QueryConditions) conditions) );
		ok &= (conditions != null && (conditions instanceof QueryCondition) && 
				this.setQueryCondition((QueryCondition) conditions) );
		ok &= (conditions != null && (conditions instanceof String) && 
				this.setQueryConditions((String) conditions) );
		ok &= this.setUnit(unit);
		return ok;
	}
	public boolean setThis(com.FileManagerX.DataBase.Unit unit, Object conditions,
			com.FileManagerX.Interfaces.IConnection connection) {
		boolean ok = true;
		ok &= this.getBasicMessagePackage().setThis(connection.getClientConnection());
		ok &= this.setConnection(connection);
		ok &= this.setThis(unit, conditions);
		return ok;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public RemoveUnit() {
		initThis();
	}
	public RemoveUnit(String command) {
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
		RemoveUnit qf = (RemoveUnit)o;
		super.copyReference(o);
		this.unit = qf.unit;
		this.conditions = qf.conditions;
	}
	public void copyValue(Object o) {
		RemoveUnit qf = (RemoveUnit)o;
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
		
		com.FileManagerX.Interfaces.IPublic result = (IPublic)dbm.query(conditions);
		if(result == null) {
			this.getReply().setOK(false);
			this.getReply().setFailedReason("Not Exist");
			return false;
		}
		
		boolean ok = dbm.remove(result);
		if(!ok) {
			this.getReply().setOK(false);
			this.getReply().setFailedReason("Remove From Database Failed");
			return false;
		}
		
		this.getReply().setOK(ok);
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
