package com.FileManagerX.BasicModels;

public class Group implements com.FileManagerX.Interfaces.IPublic {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private long index;
	private com.FileManagerX.BasicCollections.Users users;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setIndex(long index) {
		this.index = index;
		return true;
	}
	public boolean setIndex() {
		this.index = ++com.FileManagerX.Globals.Configurations.Next_GroupIndex;
		return true;
	}
	public boolean setUsers(com.FileManagerX.BasicCollections.Users users) {
		if(users == null) {
			return false;
		}
		this.users = users;
		return true;
	}
	
	public boolean setGroup(com.FileManagerX.MyNet.Group group) {
		if(group == null) {
			return false;
		}
		
		this.index = group.getIndex();
		this.users.clear();
		for(com.FileManagerX.MyNet.User u : group.getUsers()) {
			this.users.add(u.getUser());
		}
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public long getIndex() {
		return index;
	}
	public com.FileManagerX.BasicCollections.Users getUsers() {
		return users;
	}
	
	public com.FileManagerX.MyNet.Group getGroup() {
		com.FileManagerX.MyNet.Group group = new com.FileManagerX.MyNet.Group();
		group.setIndex(index);
		for(User u : this.users.getContent()) {
			com.FileManagerX.MyNet.User uNet = new com.FileManagerX.MyNet.User();
			uNet.setUser(u);
			group.addUser(uNet);
		}
		return group;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Group() {
		initThis();
	}
	private void initThis() {
		index = 0;
		users = new com.FileManagerX.BasicCollections.Users();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void clear() {
		initThis();
	}
	public String toString() {
		return this.output();
	}
	public String output() {
		long[] users = new long[this.users.size()];
		for(int i=0; i<users.length; i++) {
			users[i] = this.users.getContent().get(i).getIndex();
		}
		
		Config c = new Config();
		c.setField(this.getClass().getSimpleName());
		c.addToBottom(this.index);
		c.addToBottom(com.FileManagerX.Tools.String.link(users, " "));
		return c.output();
	}
	public String input(String in) {
		Config c = new Config(in);
		this.index = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		long[] users = com.FileManagerX.Tools.String.splitToLongArray(c.fetchFirstString(), ' ');
		if(!c.getIsOK()) { return null; }
		
		this.users.clear();
		for(int i=0; i<users.length; i++) {
			User u = new User();
			u.setIndex(users[i]);
			this.users.add(u);
		}
		
		return c.output();
	}
	public void copyReference(Object o) {
		if(o instanceof Group) {
			Group g = (Group)o;
			this.index = g.index;
			this.users = g.users;
		}
	}
	public void copyValue(Object o) {
		if(o instanceof Group) {
			Group g = (Group)o;
			this.index = g.index;
			this.users.copyValue(users);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
