package com.FileManagerX.Replies;

import com.FileManagerX.BasicModels.*;

public class Test extends BaseReply {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private String test;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setTestString(String test) {
		if(test == null) {
			return false;
		}
		this.test = test;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public String getTestString() {
		return this.test;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Test() {
		initThis();
	}
	private void initThis() {
		this.test = "";
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setThis(String test) {
		boolean ok = true;
		ok &= this.setTestString(test);
		return ok;
	}
	public boolean setThis(String test, com.FileManagerX.Interfaces.IConnection connection) {
		boolean ok = true;
		ok &= this.getBasicMessagePackage().setThis(connection.getClientConnection());
		ok &= this.setConnection(connection);
		ok &= this.setThis(test);
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
		c.addToBottom(com.FileManagerX.Coder.Encoder.Encode_String2String(this.test));
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		
		Config c = new Config(in);
		this.test = com.FileManagerX.Coder.Decoder.Decode_String2String(c.fetchFirstString());
		if(!c.getIsOK()) { return null; }
		
		return c.output();
	}
	public void copyReference(Object o) {
		super.copyReference(o);
		Test t = (Test)o;
		this.test = t.test;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		Test t = (Test)o;
		this.test = new String(t.test);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
