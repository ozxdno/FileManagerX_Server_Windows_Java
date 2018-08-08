package com.FileManagerX.Commands;

import com.FileManagerX.BasicModels.Config;
import com.FileManagerX.Replies.BaseReply;

public class Unsupport extends BaseCommand {

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
	
	public com.FileManagerX.Replies.Unsupport getReply() {
		if(super.getReply() == null) { this.setReply(new com.FileManagerX.Replies.Unsupport()); }
		return (com.FileManagerX.Replies.Unsupport)this.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Unsupport() {
		initThis();
	}
	private void initThis() {
		this.content = "";
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

	public boolean execute() {
		boolean ok = this.executeInLocal();
		this.getReply().send();
		return ok;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean executeInLocal() {
		this.getBasicMessagePackage().setIsRecord(true);
		this.getReply().setContent(content);
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
