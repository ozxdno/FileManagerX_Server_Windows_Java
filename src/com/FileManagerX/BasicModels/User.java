package com.FileManagerX.BasicModels;

public class User implements com.FileManagerX.Interfaces.IPublic {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private long index;
	private String loginName;
	private String nickName;
	private String password;
	private String email;
	private String phone;
	private com.FileManagerX.BasicEnums.UserState state;
	private com.FileManagerX.BasicEnums.UserPriority priority;
	private com.FileManagerX.BasicEnums.UserLevel level;
	private long experience;
	private String photoUrl;
	private long coins;
	private double money;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public long getIndex() {
		return index;
	}
	public String getLoginName() {
		return loginName;
	}
	public String getNickName() {
		return nickName;
	}
	public String getPassword() {
		return password;
	}
	public String getEmail() {
		return this.email;
	}
	public String getPhone() {
		return this.phone;
	}
	public com.FileManagerX.BasicEnums.UserState getState() {
		return state;
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
	public String getPhotoUrl() {
		return photoUrl;
	}
	public long getCoins() {
		return coins;
	}
	public double getMoney() {
		return money;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean setIndex(long index) {
		this.index = index;
		return true;
	}
	public boolean setIndex() {
		this.index = com.FileManagerX.Globals.Configurations.Next_UserIndex();
		return true;
	}
	public boolean setLoginName(String loginName) {
		if(loginName == null || loginName.length() == 0) {
			return false;
		}
		this.loginName = loginName;
		return true;
	}
	public boolean setNickName(String nickName) {
		if(nickName == null || nickName.length() == 0) {
			return false;
		}
		this.nickName = nickName;
		return true;
	}
	public boolean setPassword(String password) {
		if(password == null || password.length() == 0) {
			return false;
		}
		this.password = password;
		return true;
	}
	public boolean setEmail(String email) {
		if(email == null || email.length() == 0) {
			return false;
		}
		this.email = email;
		return true;
	}
	public boolean setPhone(String phone) {
		if(phone == null || phone.length() == 0) {
			return false;
		}
		this.phone = phone;
		return true;
	}
	public boolean setState(com.FileManagerX.BasicEnums.UserState state) {
		this.state = state;
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
	public boolean setPhotoUrl(String photoUrl) {
		if(photoUrl == null || photoUrl.length() == 0) {
			return false;
		}
		this.photoUrl = photoUrl;
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
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public User() {
		initThis();
	}
	public User(String loginName, String password) {
		initThis();
		setLoginName(loginName);
		setPassword(password);
	}
	private void initThis() {
		index = 0;
		loginName = "";
		nickName = "";
		password = "";
		email = "";
		phone = "";
		state = com.FileManagerX.BasicEnums.UserState.Offline;
		priority = com.FileManagerX.BasicEnums.UserPriority.Guest;
		level = com.FileManagerX.BasicEnums.UserLevel.Level1;
		experience = 0;
		photoUrl = "";
		coins = 0;
		money = 0.0;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void clear() {
		initThis();
	}
	public String toString() {
		return nickName;
	}
	public Config toConfig() {
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config("User = ");
		c.addToBottom(index);
		c.addToBottom(loginName);
		c.addToBottom(nickName);
		c.addToBottom(password);
		c.addToBottom(email);
		c.addToBottom(phone);
		c.addToBottom(state.toString());
		c.addToBottom(priority.toString());
		c.addToBottom(level.toString());
		c.addToBottom(experience);
		c.addToBottom(photoUrl);
		c.addToBottom(coins);
		c.addToBottom(money);
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
			index = c.fetchFirstLong();
			if(!c.getIsOK()) { return c; }
			loginName = c.fetchFirstString();
			if(!c.getIsOK()) { return c; }
			nickName = c.fetchFirstString();
			if(!c.getIsOK()) { return c; }
			password = c.fetchFirstString();
			if(!c.getIsOK()) { return c; }
			email = c.fetchFirstString();
			if(!c.getIsOK()) { return c; }
			phone = c.fetchFirstString();
			if(!c.getIsOK()) { return c; }
			state = com.FileManagerX.BasicEnums.UserState.valueOf(c.fetchFirstString());
			if(!c.getIsOK()) { return c; }
			priority = com.FileManagerX.BasicEnums.UserPriority.valueOf(c.fetchFirstString());
			if(!c.getIsOK()) { return c; }
			level = com.FileManagerX.BasicEnums.UserLevel.valueOf(c.fetchFirstString());
			if(!c.getIsOK()) { return c; }
			experience = c.fetchFirstLong();
			if(!c.getIsOK()) { return c; }
			photoUrl = c.fetchFirstString();
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
		
		if(o instanceof User) {
			User u = (User)o;
			this.index = u.index;
			this.loginName = u.loginName;
			this.nickName = u.nickName;
			this.password = u.password;
			this.email = u.email;
			this.phone = u.phone;
			this.state = u.state;
			this.priority = u.priority;
			this.level = u.level;
			this.experience = u.experience;
			this.photoUrl = u.photoUrl;
			this.coins = u.coins;
			this.money = u.money;
			return;
		}
	}
	public void copyValue(Object o) {
		if(o == null) { return; }
		
		if(o instanceof User) {
			User u = (User)o;
			this.index = u.index;
			this.loginName = new String(u.loginName);
			this.nickName = new String(u.nickName);
			this.password = new String(u.password);
			this.email = new String(u.email);
			this.phone = new String(u.phone);
			this.state = u.state;
			this.priority = u.priority;
			this.level = u.level;
			this.experience = u.experience;
			this.photoUrl = new String(u.photoUrl);
			this.coins = u.coins;
			this.money = u.money;
			return;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
