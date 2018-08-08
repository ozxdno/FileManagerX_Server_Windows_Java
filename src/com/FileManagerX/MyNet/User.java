package com.FileManagerX.MyNet;

public class User {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private com.FileManagerX.BasicModels.User user;
	private java.util.List<String> permitGroups;
	private java.util.List<Machine> machines;
	private boolean exist;
	private String name;
	private int amount;
	private int limit;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setUser(com.FileManagerX.BasicModels.User user) {
		if(user == null) {
			return false;
		}
		this.user = user;
		return true;
	}
	public boolean setPermitGroups(java.util.List<String> groupNames) {
		if(groupNames == null) {
			return false;
		}
		this.permitGroups = groupNames;
		return true;
	}
	public boolean setMachines(java.util.List<Machine> machines) {
		if(machines == null) {
			return false;
		}
		this.machines = machines;
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

	public com.FileManagerX.BasicModels.User getUser() {
		return this.user;
	}
	public java.util.List<String> getPermitGroups() {
		return this.permitGroups;
	}
	public java.util.List<Machine> getMachines() {
		return this.machines;
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
	
	public boolean isPermit(long userIndex) {
		for(String s : this.permitGroups) {
			Group g = com.FileManagerX.Globals.Datas.MyNet.findGroup(s);
			if(g == null) { continue; }
			if(g.exist(userIndex)) { return true; }
		}
		return false;
	}
	public boolean isExist() {
		return this.exist;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public User() {
		initThis();
	}
	private void initThis() {
		this.user = new com.FileManagerX.BasicModels.User();
		this.permitGroups = new java.util.ArrayList<>();
		this.machines = new java.util.ArrayList<>();
		this.exist = false;
		this.amount = 0;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void clear() {
		initThis();
	}
	public String toString() {
		return this.user.getNickName();
	}
	public String output() {
		String o = "MyNetUser = " +
				this.user.getIndex() + "|" + 
				this.permitGroups.size() + "|" + 
				this.machines.size() + "|";
		for(String s : this.permitGroups) {
			o += s.length() + "|" + s + "|";
		}
		return o;
	}
	public String input(String in) {
		try {
			in = com.FileManagerX.Tools.String.getValue(in);
			
			int idx = in.indexOf('|');
			long userIndex = Long.parseLong(in.substring(0, idx));
			this.user.setIndex(userIndex);
			in = in.substring(idx + 1);
			idx = in.indexOf('|');
			int groupAmount = Integer.parseInt(in.substring(0, idx));
			in = in.substring(idx + 1);
			idx = in.indexOf('|');
			int machineAmount = Integer.parseInt(in.substring(0, idx));
			in = in.substring(idx + 1);
			this.amount = machineAmount;
			
			int length = 0;
			for(int i=0; i<groupAmount; i++) {
				idx = in.indexOf('|');
				length = Integer.parseInt(in.substring(0, idx));
				in = in.substring(idx + 1);
				this.permitGroups.add(in.substring(0, length));
				in = in.substring(length + 1);
			}
			
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

	public boolean load() {
		if(com.FileManagerX.Globals.Configurations.IsServer) {
			com.FileManagerX.Globals.Datas.DBManager.setUnit(com.FileManagerX.DataBase.Unit.User);
			com.FileManagerX.BasicModels.User user = (com.FileManagerX.BasicModels.User)
					com.FileManagerX.Globals.Datas.DBManager.query
					("[&] Index = " + this.user.getIndex());
			this.exist = user != null;
			if(user == null) { return false; }
			this.user = user;
			return true;
		}
		else {
			com.FileManagerX.Commands.QueryUnit qu = new com.FileManagerX.Commands.QueryUnit();
			qu.setThis(
					com.FileManagerX.DataBase.Unit.User,
					"[&] Index = " + this.user.getIndex(), 
					com.FileManagerX.Globals.Datas.ServerConnection
					);
			qu.send();
			com.FileManagerX.Replies.QueryUnit rep = (com.FileManagerX.Replies.QueryUnit)qu.receive();
			this.exist = rep != null && rep.isOK();
			if(rep == null || !rep.isOK()) { return false; }
			this.user = (com.FileManagerX.BasicModels.User)rep.getResult();
			return true;
		}
	}
	public boolean addGroup(String groupName) {
		if(groupName == null) {
			return false;
		}
		if(groupName.length() == 0) {
			return false;
		}
		
		for(String s : this.permitGroups) {
			if(s.equals(groupName)) {
				return true;
			}
		}
		this.permitGroups.add(groupName);
		return true;
	}
	public boolean delGroup(String groupName) {
		if(groupName == null) {
			return false;
		}
		if(groupName.length() == 0) {
			return false;
		}
		
		java.util.Iterator<String> it = this.permitGroups.iterator();
		while(it.hasNext()) {
			if(it.next().equals(groupName)) {
				it.remove();
				return true;
			}
		}
		
		return true;
	}
	public boolean addMachine(Machine machine) {
		if(machine == null) {
			return false;
		}
		this.machines.add(machine);
		return true;
	}
	public Machine findMachine(String machine) {
		for(Machine m : this.machines) {
			if(m.getName().equals(machine)) { return m; }
		}
		return null;
	}
	
	public void refresh() {
		this.load();
		for(Machine m : this.machines) { m.refresh(); }
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
