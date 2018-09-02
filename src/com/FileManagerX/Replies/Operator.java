package com.FileManagerX.Replies;

import com.FileManagerX.Globals.Datas;

public class Operator extends BaseReply {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private com.FileManagerX.Operator.Operator operator;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setOperator(com.FileManagerX.Operator.Operator operator) {
		if(operator == null) {
			return false;
		}
		this.operator = operator;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.Operator.Operator getOperator() {
		return this.operator;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Operator() {
		initThis();
	}
	private void initThis() {
		this.operator = new com.FileManagerX.Operator.Operator();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean setThis(com.FileManagerX.Operator.Operator operator) {
		boolean ok = true;
		ok &= this.setOperator(operator);
		return ok;
	}
	public boolean setThis(com.FileManagerX.Operator.Operator operator,
			com.FileManagerX.Interfaces.IConnection connection) {
		boolean ok = true;
		ok &= this.getBasicMessagePackage().setThis(connection.getClientConnection());
		ok &= this.setConnection(connection);
		ok &= this.setThis(operator);
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
		c.addToBottom(this.operator.toConfig());
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
			c = this.operator.input(c);
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
		Operator t = (Operator)o;
		this.operator = t.operator;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		Operator t = (Operator)o;
		this.operator.copyValue(t.operator);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean executeInLocal() {
		
		if(!super.executeInLocal()) {
			return false;
		}
		
		com.FileManagerX.Operator.Operator op = Datas.Operators.searchByKey(this.operator.getIndex());
		if(op == null) {
			this.operator.setSource(this);
			this.operator.startProcess();
		}
		else {
			op.setSource(this);
			op.copyReference(this.operator);
		}
		
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}

