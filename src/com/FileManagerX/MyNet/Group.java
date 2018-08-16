package com.FileManagerX.MyNet;

public class Group implements com.FileManagerX.Interfaces.IPublic {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private long index;
	private java.util.List<User> users;
	private String name;
	private int amount;
	private int limit;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setIndex(long index) {
		this.index = index;
		return true;
	}
	public boolean setIndex() {
		this.index = ++com.FileManagerX.Globals.Configurations.Next_GroupIndex;
		return true;
	}
	public boolean setUsers(java.util.List<User> users) {
		if(users == null) {
			return false;
		}
		this.users = users;
		return true;
	}
	public boolean setName(String name) {
		if(name == null) {
			return false;
		}
		if(name.length() == 0) {
			return false;
		}
		this.name = name;
		return true;
	}
	public boolean setAmount(int amount) {
		if(amount < 0) {
			return false;
		}
		this.amount = amount;
		return true;
	}
	public boolean setLimit(int limit) {
		if(limit < 0) {
			return false;
		}
		this.limit = limit;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public long getIndex() {
		return this.index;
	}
	public java.util.List<User> getUsers() {
		return this.users;
	}
	public String getName() {
		return this.name;
	}
	public int getAmount() {
		return this.amount;
	}
	public int getLimit() {
		return this.limit;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Group() {
		initThis();
	}
	private void initThis() {
		this.users = new java.util.ArrayList<>();
		this.name = "Default Group";
		this.amount = 0;
		this.limit = 100;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void clear() {
		initThis();
	}
	public String toString() {
		return this.name;
	}
	public String output() {
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config();
		c.setField("Net-" + this.getClass().getSimpleName());
		c.addToBottom(this.index);
		c.addToBottom(this.name);
		c.addToBottom(this.users.size());
		c.addToBottom(this.limit);
		return c.output();
	}
	public String input(String in) {
		try {
			com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config(in);
			this.index = c.fetchFirstLong();
			if(!c.getIsOK()) { return null; }
			this.name = c.fetchFirstString();
			if(!c.getIsOK()) { return null; }
			this.amount = c.fetchFirstInt();
			if(!c.getIsOK()) { return null; }
			this.limit = c.fetchFirstInt();
			if(!c.getIsOK()) { return null; }
			
			return c.output();
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			return null;
		}
	}
	public void copyReference(Object o) {
		;
	}
	public void copyValue(Object o) {
		;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean exist(long userIndex) {
		for(User u : this.users) {
			if(u.getUser().getIndex() == userIndex) {
				return true;
			}
		}
		return false;
	}
	public boolean loadPrevious(int amount) {
		if(amount < 0) {
			return false;
		}
		if(amount > this.limit) {
			amount = this.limit;
		}
		return false;
	}
	public boolean loadNext(int amount) {
		if(amount < 0) {
			return false;
		}
		if(amount > this.limit) {
			amount = this.limit;
		}
		return false;
	}
	public boolean addUser(User user) {
		if(user == null) {
			return false;
		}
		this.users.add(user);
		return true;
	}
	public User findUser(String user) {
		for(User u : this.users) {
			if(u.getName().equals(user)) { return u; }
		}
		return null;
	}
	
	public void refresh() {
		for(User u : this.users) { u.refresh(); }
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
