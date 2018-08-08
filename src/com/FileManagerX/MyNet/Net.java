package com.FileManagerX.MyNet;

public class Net implements com.FileManagerX.Interfaces.IPublic {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private java.util.List<Group> groups;
	private String name;
	private int amount;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setGroups(java.util.List<Group> groups) {
		if(groups == null) {
			return false;
		}
		this.groups = groups;
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
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public java.util.List<Group> getGroups() {
		return this.groups;
	}
	public String getName() {
		return this.name;
	}
	public int getAmount() {
		return this.amount;
	}
	
	public Manager getManager() {
		Manager m = new Manager();
		m.setNet(this);
		return m;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Net() {
		initThis();
	}
	private void initThis() {
		this.groups = new java.util.ArrayList<>();
		this.name = "Default Net";
		this.amount = 0;
		
		com.FileManagerX.Factories.MyNetFactory.createServerGroup(this);
		com.FileManagerX.Factories.MyNetFactory.createMyGroup(this);
		com.FileManagerX.Factories.MyNetFactory.createTempGroup(this);
		com.FileManagerX.Factories.MyNetFactory.createHideGroup(this);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void clear() {
		initThis();
	}
	public String toString() {
		return this.name;
	}
	public String output() {
		String o = "MyNet" + " = " +
				this.name.length() + "|" +
				this.name + "|"  + 
				this.groups.size() + "|";
		return o;
	}
	public String input(String in) {
		try {
			in = com.FileManagerX.Tools.String.getValue(in);
			
			int idx = in.indexOf('|');
			int nameLen = Integer.parseInt(in.substring(0, idx));
			in = in.substring(idx + 1);
			this.name = in.substring(0, nameLen);
			in = in.substring(nameLen + 1);
			
			idx = in.indexOf('|');
			int groupAmount = Integer.parseInt(in.substring(0, idx));
			in = in.substring(idx + 1);
			this.amount = groupAmount;
			
			return in;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			return null;
		}
	}
	public void copyReference(Object o) {
		
	}
	public void copyValue(Object o) {
		
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean addGroup(Group group) {
		if(group == null) {
			return false;
		}
		this.groups.add(group);
		return true;
	}
	public boolean addGroup(String groupName) {
		for(Group g : this.groups) {
			if(g.getName().equals(groupName)) {
				return true;
			}
		}
		Group g = new Group();
		g.setName(groupName);
		this.groups.add(g);
		return true;
	}
	public boolean delGroup(String groupName) {
		java.util.Iterator<Group> it = this.groups.iterator();
		while(it.hasNext()) {
			if(it.next().getName().equals(groupName)) {
				it.remove();
				return true;
			}
		}
		return true;
	}
	public Group findGroup(String groupName) {
		for(Group g : this.groups) {
			if(g.getName().equals(groupName)) {
				return g;
			}
		}
		return null;
	}
	public void refresh() {
		com.FileManagerX.Factories.MyNetFactory.createServerGroup(this);
		com.FileManagerX.Factories.MyNetFactory.createMyGroup(this);
		com.FileManagerX.Factories.MyNetFactory.createTempGroup(this);
		com.FileManagerX.Factories.MyNetFactory.createHideGroup(this);
		
		for(Group g : this.groups) { g.refresh(); }
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
