package com.FileManagerX.Commands;

import com.FileManagerX.BasicEnums.UserPriority;
import com.FileManagerX.BasicModels.*;
import com.FileManagerX.Globals.Datas;

public class Operator extends BaseCommand {

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
	public com.FileManagerX.Replies.Operator getReply() {
		if(super.getReply() == null) {
			this.setReply(new com.FileManagerX.Replies.Operator());
		}
		return (com.FileManagerX.Replies.Operator)super.getReply();
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
		c.addToBottom(new Config(super.output()));
		c.addToBottom(new Config(this.operator.output()));
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		return this.operator.input(in);
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
		
		com.FileManagerX.Operator.Operator op = Datas.Operators.searchOperatorIndex(this.operator.getIndex());
		if(op == null) {
			this.operator.setSource(this);
			boolean ok = this.operator.startProcess();
			if(!ok) {
				this.getReply().setFailedReason("Start Operator Failed");
				this.getReply().setOK(false);
				return false;
			}
		}
		else {
			if(this.operator.isRestart()) {
				op.setArgs(this.operator.getArgs());
				boolean ok = op.restartProcess();
				if(!ok) {
					this.getReply().setFailedReason("Restart Operator Failed");
					this.getReply().setOK(false);
					return false;
				}
			}
			this.operator = op;
		}
		
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
