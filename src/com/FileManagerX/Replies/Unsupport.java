package com.FileManagerX.Replies;

import com.FileManagerX.BasicModels.Config;

public class Unsupport extends BaseReply {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private String content;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setContent(String content) {
		if(content == null) {
			return false;
		}
		this.content = content;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public String getContent() {
		return this.content;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Unsupport() {
		initThis();
	}
	private void initThis() {
		this.content = "";
		this.setFailedReason("Unsupport");
		this.setOK(false);
		this.getBasicMessagePackage().setIsRecord(true);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setThis(String content) {
		boolean ok = true;
		ok &= this.setContent(content);
		return ok;
	}
	public boolean setThis(String content, com.FileManagerX.Interfaces.IConnection connection) {
		boolean ok = true;
		ok &= this.getBasicMessagePackage().setThis(connection.getClientConnection());
		ok &= this.setConnection(connection);
		ok &= this.setThis(content);
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
		c.addToBottom(com.FileManagerX.Coder.Encoder.Encode_String2String(this.content));
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		
		Config c = new Config(in);
		this.content = com.FileManagerX.Coder.Decoder.Decode_String2String(c.fetchFirstString());
		if(!c.getIsOK()) { return null; }
		
		return c.output();
	}
	public void copyReference(Object o) {
		if(o instanceof Unsupport) {
			super.copyReference(o);
			Unsupport t = (Unsupport)o;
			this.content = t.content;
		}
		if(o instanceof BaseReply) {
			super.copyReference(o);
		}
		
	}
	public void copyValue(Object o) {
		if(o instanceof Unsupport) {
			super.copyReference(o);
			Unsupport t = (Unsupport)o;
			this.content = new String(t.content);
		}
		if(o instanceof BaseReply) {
			super.copyReference(o);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
