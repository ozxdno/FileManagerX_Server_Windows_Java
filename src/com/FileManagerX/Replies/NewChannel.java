package com.FileManagerX.Replies;

import com.FileManagerX.BasicModels.Config;

public class NewChannel extends BaseReply {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private int index;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setIndex(int index) {
		this.index = index;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public int getIndex() {
		return this.index;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public NewChannel() {
		initThis();
	}
	private void initThis() {
		this.index = 0;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setThis(int index) {
		boolean ok = true;
		ok &= this.setIndex(index);
		return ok;
	}
	public boolean setThis(int index, com.FileManagerX.Interfaces.IConnection connection) {
		boolean ok = true;
		ok &= this.getBasicMessagePackage().setThis(connection.getClientConnection());
		ok &= this.setConnection(connection);
		ok &= this.setThis(index);
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
		c.addToBottom(this.index);
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		
		Config c = new Config(in);
		this.index = c.fetchFirstInt();
		if(!c.getIsOK()) { return null; }
		
		return c.output();
	}
	public void copyReference(Object o) {
		super.copyReference(o);
		NewChannel t = (NewChannel)o;
		this.index = t.index;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		NewChannel t = (NewChannel)o;
		this.index = t.index;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
