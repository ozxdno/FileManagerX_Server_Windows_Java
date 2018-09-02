package com.FileManagerX.BasicModels;


public class Invitation implements com.FileManagerX.Interfaces.IPublic {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private String code;
	private com.FileManagerX.BasicEnums.UserPriority priority;
	private com.FileManagerX.BasicEnums.UserLevel level;
	private long experience;
	private long coins;
	private double money;
	private long endTime;
	private int remainAmount;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public String getCode() {
		return code;
	}
	public com.FileManagerX.BasicEnums.UserPriority getPriority() {
		return priority;
	}
	public com.FileManagerX.BasicEnums.UserLevel getLevel() {
		return level;
	}
	public long getExperience() {
		return experience;
	}
	public long getCoins() {
		return coins;
	}
	public double getMoney() {
		return money;
	}
	public long getEndTime() {
		return endTime;
	}
	public int getRemainAmount() {
		return remainAmount;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean setCode(String code) {
		if(code == null) {
			return false;
		}
		this.code = code;
		return true;
	}
	public boolean setPriority(com.FileManagerX.BasicEnums.UserPriority priority) {
		this.priority = priority;
		return true;
	}
	public boolean setLevel(com.FileManagerX.BasicEnums.UserLevel level) {
		this.level = level;
		return true;
	}
	public boolean setExperience(long experience) {
		if(experience < 0) {
			return false;
		}
		this.experience = experience;
		return true;
	}
	public boolean setCoins(long coins) {
		if(coins < 0) {
			coins = 0;
		}
		this.coins = coins;
		return true;
	}
	public boolean setMoney(double money) {
		this.money = money;
		return true;
	}
	public boolean setEndTime(long endTime) {
		if(endTime < 0) {
			return false;
		}
		this.endTime = endTime;
		return true;
	}
	public boolean setRemainAmount(int amount) {
		if(amount < 0) {
			amount = 0;
		}
		this.remainAmount = amount;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Invitation() {
		initThis();
	}
	private void initThis() {
		this.code = "";
		this.priority = com.FileManagerX.BasicEnums.UserPriority.Guest;
		this.level = com.FileManagerX.BasicEnums.UserLevel.Level1;
		this.experience = 0;
		this.coins = 0;
		this.money = 0.0;
		this.endTime = Long.MAX_VALUE;
		this.remainAmount = 1;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	
	public void clear() {
		initThis();
	}
	public String toString() {
		return this.code;
	}
	public Config toConfig() {
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config();
		c.setField(this.getClass().getSimpleName());
		c.addToBottom(this.code);
		c.addToBottom(this.endTime);
		c.addToBottom(this.remainAmount);
		c.addToBottom(this.priority.toString());
		c.addToBottom(this.level.toString());
		c.addToBottom(this.experience);
		c.addToBottom(this.coins);
		c.addToBottom(this.money);
		
		return c;
	}
	public String output() {
		return this.toConfig().output();
	}
	public Config input(String in) {
		return this.input(new Config(in));
	}
	public Config input(Config c) {
		if(c == null) { return null; }
		
		try {
			if(!c.getIsOK()) { return c; }
			this.code = c.fetchFirstString();
			if(!c.getIsOK()) { return c; }
			this.endTime = c.fetchFirstLong();
			if(!c.getIsOK()) { return c; }
			this.remainAmount = c.fetchFirstInt();
			if(!c.getIsOK()) { return c; }
			priority = com.FileManagerX.BasicEnums.UserPriority.valueOf(c.fetchFirstString());
			if(!c.getIsOK()) { return c; }
			level = com.FileManagerX.BasicEnums.UserLevel.valueOf(c.fetchFirstString());
			if(!c.getIsOK()) { return c; }
			experience = c.fetchFirstLong();
			if(!c.getIsOK()) { return c; }
			coins = c.fetchFirstLong();
			if(!c.getIsOK()) { return c; }
			money = c.fetchFirstDouble();
			if(!c.getIsOK()) { return c; }
			
			return c;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			c.setIsOK(false);
			return c;
		}
	}
	public void copyReference(Object o) {
		if(o == null) { return; }
		
		if(o instanceof Invitation) {
			Invitation i = (Invitation)o;
			this.code = i.code;
			this.priority = i.priority;
			this.level = i.level;
			this.experience = i.experience;
			this.coins = i.coins;
			this.money = i.money;
			this.endTime = i.endTime;
			this.remainAmount = i.remainAmount;
			return;
		}
	}
	public void copyValue(Object o) {
		if(o == null) { return; }
		
		if(o instanceof Invitation) {
			Invitation i = (Invitation)o;
			this.code = new String(i.code);
			this.priority = i.priority;
			this.level = i.level;
			this.experience = i.experience;
			this.coins = i.coins;
			this.money = i.money;
			this.endTime = i.endTime;
			this.remainAmount = i.remainAmount;
			return;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}