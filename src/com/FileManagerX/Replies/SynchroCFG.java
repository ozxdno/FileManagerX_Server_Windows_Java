package com.FileManagerX.Replies;

public class SynchroCFG extends BaseReply {

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
	
	public String getContent() {
		return this.content;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public SynchroCFG() {
		initThis();
	}
	private void initThis() {
		this.content = "";
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void clear() {
		initThis();
	}
	public String toString() {
		return this.output();
	}
	public com.FileManagerX.BasicModels.Config toConfig() {
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config();
		c.setField(this.getClass().getSimpleName());
		c.addToBottom(super.toConfig());
		c.addToBottom_Encode(this.content);
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
			this.content = c.fetchFirstString_Decode();
			if(!c.getIsOK()) { return c; }
			return c;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			c.setIsOK(false);
			return c;
		}
	}
	public void copyReference(Object o) {
		if(o instanceof SynchroCFG) {
			super.copyReference(o);
			SynchroCFG s = (SynchroCFG)o;
			this.content = s.content;
			return;
		}
		if(o instanceof BaseReply) {
			super.copyReference(o);
			return;
		}
	}
	public void copyValue(Object o) {
		if(o instanceof SynchroCFG) {
			super.copyValue(o);
			SynchroCFG s = (SynchroCFG)o;
			this.content = new String(s.content);
			return;
		}
		if(o instanceof BaseReply) {
			super.copyValue(o);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
