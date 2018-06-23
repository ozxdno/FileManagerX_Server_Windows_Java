package BasicModels;

public class User implements Interfaces.IPublic {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private long index;
	private String loginName;
	private String nickName;
	private String password;
	private String email;
	private String phone;
	private BasicEnums.UserState state;
	private BasicEnums.UserPriority priority;
	private BasicEnums.UserLevel level;
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
	public BasicEnums.UserState getState() {
		return state;
	}
	public BasicEnums.UserPriority getPriority() {
		return priority;
	}
	public BasicEnums.UserLevel getLevel() {
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
		this.index = Globals.Configurations.Next_UserIndex + 1;
		Globals.Configurations.Next_UserIndex = this.index;
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
	public boolean setState(BasicEnums.UserState state) {
		this.state = state;
		return true;
	}
	public boolean setPriority(BasicEnums.UserPriority priority) {
		this.priority = priority;
		return true;
	}
	public boolean setLevel(BasicEnums.UserLevel level) {
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
		state = BasicEnums.UserState.OffLine;
		priority = BasicEnums.UserPriority.Guest;
		level = BasicEnums.UserLevel.Level1;
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
	public String output() {
		BasicModels.Config c = new BasicModels.Config("User = ");
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
		return c.output();
	}
	public String input(String in) {
		BasicModels.Config c = new BasicModels.Config(in);
		index = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		loginName = c.fetchFirstString();
		if(!c.getIsOK()) { return null; }
		nickName = c.fetchFirstString();
		if(!c.getIsOK()) { return null; }
		password = c.fetchFirstString();
		if(!c.getIsOK()) { return null; }
		email = c.fetchFirstString();
		if(!c.getIsOK()) { return null; }
		phone = c.fetchFirstString();
		if(!c.getIsOK()) { return null; }
		state = BasicEnums.UserState.valueOf(c.fetchFirstString());
		if(!c.getIsOK()) { return null; }
		priority = BasicEnums.UserPriority.valueOf(c.fetchFirstString());
		if(!c.getIsOK()) { return null; }
		level = BasicEnums.UserLevel.valueOf(c.fetchFirstString());
		if(!c.getIsOK()) { return null; }
		experience = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		photoUrl = c.fetchFirstString();
		if(!c.getIsOK()) { return null; }
		coins = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		money = c.fetchFirstDouble();
		if(!c.getIsOK()) { return null; }
		return c.output();
	}
	public void copyReference(Object o) {
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
	}
	public void copyValue(Object o) {
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
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
