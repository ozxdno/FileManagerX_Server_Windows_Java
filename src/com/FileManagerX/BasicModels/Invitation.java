package com.FileManagerX.BasicModels;


public class Invitation implements com.FileManagerX.Interfaces.IPublic {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private String code;
	private com.FileManagerX.BasicModels.User user;
	private String activeTime;
	private int activeAmount;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public String getCode() {
		return code;
	}
	public com.FileManagerX.BasicModels.User getUser() {
		return user;
	}
	public String getActiveTime() {
		return activeTime;
	}
	public int getActiveAmount() {
		return activeAmount;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean setCode(String code) {
		if(code == null) {
			return false;
		}
		this.code = code;
		return true;
	}
	public boolean setUser(com.FileManagerX.BasicModels.User user) {
		if(user == null) {
			return false;
		}
		this.user = user;
		return true;
	}
	public boolean setActiveTime(String activeTime) {
		if(activeTime == null) {
			return false;
		}
		if(activeTime.length() == 0) {
			return false;
		}
		this.activeTime = activeTime;
		return true;
	}
	public boolean setActiveAmount(int amount) {
		if(amount < 0) {
			amount = 0;
		}
		this.activeAmount = amount;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Invitation() {
		initThis();
	}
	public Invitation(String code, com.FileManagerX.BasicModels.User user) {
		initThis();
		setCode(code);
		setUser(user);
	}
	private void initThis() {
		this.code = "";
		this.user = new com.FileManagerX.BasicModels.User();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	
	public void clear() {
		initThis();
	}
	public String toString() {
		return this.code;
	}
	public String output() {
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config();
		c.setField(this.getClass().getSimpleName());
		c.addToBottom(this.code);
		c.addToBottom(this.activeTime);
		c.addToBottom(this.activeAmount);
		c.addToBottom(new com.FileManagerX.BasicModels.Config(this.user.output()));
		
		return c.output();
	}
	public String input(String in) {
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config(in);
		this.code = c.fetchFirstString();
		if(!c.getIsOK()) { return null; }
		this.activeTime = c.fetchFirstString();
		if(!c.getIsOK()) { return null; }
		this.activeAmount = c.fetchFirstInt();
		if(!c.getIsOK()) { return null; }
		
		return user.input(c.output());
	}
	public void copyReference(Object o) {
		Invitation i = (Invitation)o;
		this.code = i.code;
		this.user.copyReference(i.user);
	}
	public void copyValue(Object o) {
		Invitation i = (Invitation)o;
		this.code = new String(i.code);
		this.user.copyValue(i.user);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}