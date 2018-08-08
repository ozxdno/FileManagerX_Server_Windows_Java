package com.FileManagerX.Replies;

import com.FileManagerX.BasicModels.*;

public class QueryUnit extends BaseReply {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private com.FileManagerX.Interfaces.IPublic result;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setResult(com.FileManagerX.Interfaces.IPublic result) {
		if(result == null) {
			return false;
		}
		this.result = result;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.Interfaces.IPublic getResult() {
		return this.result;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public QueryUnit() {
		initThis();
	}
	private void initThis() {
		this.result = null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setThis(com.FileManagerX.Interfaces.IPublic result) {
		boolean ok = true;
		ok &= this.setResult(result);
		return ok;
	}
	public boolean setQueryFolder(com.FileManagerX.Interfaces.IPublic result, com.FileManagerX.Interfaces.IConnection connection) {
		boolean ok = true;
		ok &= this.getBasicMessagePackage().setThis(connection.getClientConnection());
		ok &= this.setConnection(connection);
		ok &= this.setThis(result);
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
		c.addToBottom(new Config(this.result.output()));
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		return this.result.input(in);
	}
	public void copyReference(Object o) {
		super.copyReference(o);
		QueryUnit qf = (QueryUnit)o;
		this.result = qf.result;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		QueryUnit qf = (QueryUnit)o;
		this.result.copyValue(qf.result);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
