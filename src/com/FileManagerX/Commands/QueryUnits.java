package com.FileManagerX.Commands;

import com.FileManagerX.BasicEnums.*;
import com.FileManagerX.Globals.*;
import com.FileManagerX.Interfaces.*;
import com.FileManagerX.DataBase.*;

public class QueryUnits extends BaseCommand {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public final static String FAILED_OPERATE_DATABASE = "Operate DataBase Failed";
	public final static String FAILED_WRONG_CONDITIONS = "Wrong Query Conditions";
	public final static String FAILED_NOT_EXIST = "Result Set is Empty";
	
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
		qcs.input(conditions);
		this.conditions = qcs;
		return true;
	}
	public boolean setQueryCondition(QueryCondition condition) {
		QueryConditions qcs = new QueryConditions();
		qcs.add(condition);
		this.conditions = qcs;
		return true;
	}
	
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
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.DataBase.Unit getUnit() {
		return this.unit;
	}
	public QueryConditions getQueryConditions() {
		return this.conditions;
	}
	
	public com.FileManagerX.Replies.QueryUnits getReply() {
		if(super.getReply() == null) { this.setReply(new com.FileManagerX.Replies.QueryUnits()); }
		return (com.FileManagerX.Replies.QueryUnits)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public QueryUnits() {
		initThis();
	}
	public QueryUnits(String command) {
		initThis();
		this.input(command);
	}
	private void initThis() {
		this.unit = com.FileManagerX.DataBase.Unit.File;
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
	public com.FileManagerX.BasicModels.Config toConfig() {
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config();
		c.setField(this.getClass().getSimpleName());
		c.addToBottom(super.toConfig());
		c.addToBottom(this.unit.toString());
		c.addToBottom(this.conditions.toConfig());
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
			this.unit = com.FileManagerX.DataBase.Unit.valueOf(c.fetchFirstString());
			if(!c.getIsOK()) { return c; }
			c = this.conditions.input(c);
			if(!c.getIsOK()) { return c; }
			return c;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			c.setIsOK(false);
			return c;
		}
	}
	public void copyReference(Object o) {
		QueryUnits qf = (QueryUnits)o;
		super.copyReference(o);
		this.unit = qf.unit;
		this.conditions = qf.conditions;
	}
	public void copyValue(Object o) {
		QueryUnits qf = (QueryUnits)o;
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
			this.getReply().setFailedReason(FAILED_WRONG_CONDITIONS);
			this.getReply().setOK(false);
			this.getReply().send();
			return false;
		}
		
		boolean ok = this.executeInLocal();
		this.getReply().send();
		return ok;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean executeInLocal() {
		
		IDBManager dbm = Datas.DBManagers.searchByKey(this.getBasicMessagePackage().getDestDataBaseIndex());
		if(dbm == null) { dbm = Datas.DBManager; }
		dbm.setUnit(this.unit);
		
		com.FileManagerX.Interfaces.IPublic results = (IPublic)dbm.querys2(conditions, unit);
		if(results == null) {
			this.getReply().setFailedReason(FAILED_OPERATE_DATABASE);
			this.getReply().setOK(false);
			return false;
		}
		
		this.getReply().setResults(results);
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
