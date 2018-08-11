package com.FileManagerX.Commands;

import com.FileManagerX.BasicEnums.*;
import com.FileManagerX.BasicModels.Config;
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
		
		IDBManager dbm = Datas.DBManagers.searchDepotIndex(this.getBasicMessagePackage().getDestDepotIndex());
		if(dbm == null) { dbm = Datas.DBManager; }
		dbm.setUnit(this.unit);
		
		com.FileManagerX.Interfaces.IPublic results = (IPublic)dbm.querys(conditions);
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