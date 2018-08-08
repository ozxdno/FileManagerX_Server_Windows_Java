package com.FileManagerX.Commands;

import com.FileManagerX.BasicEnums.*;

public class QueryConfigurations extends BaseCommand {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.Replies.QueryConfigurations getReply() {
		if(super.getReply() == null) { this.setReply(new com.FileManagerX.Replies.QueryConfigurations()); }
		return (com.FileManagerX.Replies.QueryConfigurations)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public QueryConfigurations() {
		initThis();
	}
	public QueryConfigurations(String command) {
		initThis();
		this.input(command);
	}
	private void initThis() {
		;
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
		return super.output();
	}
	public String input(String in) {
		return super.input(in);
	}
	public void copyReference(Object o) {
		super.copyReference(o);
	}
	public void copyValue(Object o) {
		super.copyValue(o);
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
		this.getReply().setThis();
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
