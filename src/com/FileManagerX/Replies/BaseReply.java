package com.FileManagerX.Replies;

public class BaseReply extends com.FileManagerX.Transport.Transport 
	implements com.FileManagerX.Interfaces.IReply {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static String FAILED_WRONG_INPUT = "Convert String to Target Object Failed";
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private boolean ok;
	private boolean store;
	private String failedReason;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setOK(boolean ok) {
		this.ok = ok;
		return true;
	}
	public boolean setStore(boolean store) {
		this.store = store;
		return true;
	}
	public boolean setFailedReason(String failedReason) {
		if(failedReason == null) {
			return false;
		}
		this.failedReason = failedReason;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean isOK() {
		return this.ok;
	}
	public boolean isStore() {
		return this.store;
	}
	public String getFailedReason() {
		return this.failedReason;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public BaseReply() {
		initThis();
	}
	private void initThis() {
		ok = true;
		store = true;
		failedReason = "";
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean setThis(boolean OK, String reason) {
		boolean ok = true;
		ok &= this.setOK(OK);
		ok &= this.setFailedReason(reason);
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
	public com.FileManagerX.BasicModels.Config toConfig() {
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config();
		c.setField(this.getClass().getSimpleName());
		c.addToBottom(super.toConfig());
		c.addToBottom(this.ok);
		c.addToBottom(this.store);
		c.addToBottom(this.failedReason);
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
			this.ok = c.fetchFirstBoolean();
			if(!c.getIsOK()) { return c; }
			this.store = c.fetchFirstBoolean();
			if(!c.getIsOK()) { return c; }
			this.failedReason = c.fetchFirstString();
			if(!c.getIsOK()) { return c; }
			return c;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			c.setIsOK(false);
			return c;
		}
	}
	public void copyReference(Object o) {
		if(o instanceof BaseReply) {
			super.copyReference(o);
			BaseReply c = (BaseReply)o;
			this.ok = c.ok;
			this.failedReason = c.failedReason;
			return;
		}
		if(o instanceof com.FileManagerX.Transport.Transport) {
			super.copyReference(o);
			return;
		}
	}
	public void copyValue(Object o) {
		if(o instanceof BaseReply) {
			super.copyValue(o);
			BaseReply c = (BaseReply)o;
			this.ok = c.ok;
			this.failedReason = c.failedReason;
			return;
		}
		if(o instanceof com.FileManagerX.Transport.Transport) {
			super.copyValue(o);
			return;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean execute() {
		ok &= this.executeInLocal();
		return this.ok;
	}
	public boolean executeInLocal() {
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
