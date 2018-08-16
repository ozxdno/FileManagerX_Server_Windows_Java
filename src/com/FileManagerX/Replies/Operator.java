package com.FileManagerX.Replies;

import com.FileManagerX.BasicModels.*;
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
	public String output() {
		Config c = new Config();
		c.setField(this.getClass().getSimpleName());
		c.addToBottom(new Config(this.operator.output()));
		c.addToBottom(new Config(super.output()));
		
		return c.output();
	}
	public String input(String in) {
		in = this.operator.input(in);
		if(in == null) {
			return null;
		}
		in = super.input(in);
		if(in == null) {
			return null;
		}
		return in;
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
		
		com.FileManagerX.Operator.Operator op = Datas.Operators.searchOperatorIndex(this.operator.getIndex());
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

