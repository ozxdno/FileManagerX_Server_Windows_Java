package com.FileManagerX.Replies;

import com.FileManagerX.BasicModels.*;

public class QuerySize extends BaseReply {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private long size;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setSize(long size) {
		if(size < 0) {
			return false;
		}
		this.size = size;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public long getSize() {
		return this.size;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public QuerySize() {
		initThis();
	}
	private void initThis() {
		this.size = 0;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setThis(int size) {
		boolean ok = true;
		ok &= this.setSize(size);
		return ok;
	}
	public boolean setThis(int size, com.FileManagerX.Interfaces.IConnection connection) {
		boolean ok = true;
		ok &= this.getBasicMessagePackage().setThis(connection.getClientConnection());
		ok &= this.setConnection(connection);
		ok &= this.setThis(size);
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
		c.addToBottom(this.size);
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		
		Config c = new Config(in);
		this.size = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		
		return c.output();
	}
	public void copyReference(Object o) {
		super.copyReference(o);
		QuerySize t = (QuerySize)o;
		this.size = t.size;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		QuerySize t = (QuerySize)o;
		this.size = t.size;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
