package com.FileManagerX.MyNet;

public class Machine implements com.FileManagerX.Interfaces.IPublic {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private com.FileManagerX.Interfaces.IRoutePathPackage rrp;
	private com.FileManagerX.BasicModels.MachineInfo machine;
	private java.util.List<String> permitGroups;
	private java.util.List<Depot> depots;
	private boolean exist;
	private String name;
	private int amount;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setMachine(com.FileManagerX.BasicModels.MachineInfo machine) {
		if(machine == null) {
			return false;
		}
		this.machine = machine;
		return true;
	}
	public boolean setPermitGroups(java.util.List<String> groupNames) {
		if(groupNames == null) {
			return false;
		}
		this.permitGroups = groupNames;
		return true;
	}
	public boolean setDepots(java.util.List<Depot> depots) {
		if(depots == null) {
			return false;
		}
		this.depots = depots;
		return true;
	}
	public boolean setRoutePathPackage(com.FileManagerX.Interfaces.IRoutePathPackage rrp) {
		if(rrp == null) {
			return false;
		}
		this.rrp = rrp;
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
	
	public com.FileManagerX.BasicModels.MachineInfo getMachine() {
		return this.machine;
	}
	public java.util.List<String> getPermitGroups() {
		return this.permitGroups;
	}
	public java.util.List<Depot> getDepots() {
		return this.depots;
	}
	public com.FileManagerX.Interfaces.IRoutePathPackage getRoutePathPackage() {
		return this.rrp;
	}
	public String getName() {
		return this.name;
	}
	public int getAmount() {
		return this.amount;
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

	public Machine() {
		initThis();
	}
	private void initThis() {
		this.machine = new com.FileManagerX.BasicModels.MachineInfo();
		this.permitGroups = new java.util.ArrayList<>();
		this.depots = new java.util.ArrayList<>();
		this.rrp = com.FileManagerX.Factories.CommunicatorFactory.createRRP();
		this.exist = false;
		this.amount = 0;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void clear() {
		initThis();
	}
	public String toString() {
		return this.machine.getName();
	}
	public String output() {
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config();
		c.setField("Net-" + this.getClass().getSimpleName());
		c.addToBottom(this.name);
		c.addToBottom(this.machine.getIndex());
		c.addToBottom(this.depots.size());
		c.addToBottom(com.FileManagerX.Tools.String.link(
				com.FileManagerX.Tools.List2Array.toStringArray(permitGroups),
				"|")
			);
		c.addToBottom(new com.FileManagerX.BasicModels.Config(this.rrp.output()));
		return c.output();
	}
	public String input(String in) {
		try {
			com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config(in);
			this.name = c.fetchFirstString();
			if(!c.getIsOK()) { return null; }
			this.machine.setIndex(c.fetchFirstLong());
			if(!c.getIsOK()) { return null; }
			this.amount = c.fetchFirstInt();
			if(!c.getIsOK()) { return null; }
			this.permitGroups = com.FileManagerX.Tools.Array2List.toStringList(
					com.FileManagerX.Tools.String.split(c.fetchFirstString(), '|')
				);
			if(!c.getIsOK()) { return null; }
			
			return this.rrp.input(c.output());
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
			com.FileManagerX.Globals.Datas.DBManager.setUnit(com.FileManagerX.DataBase.Unit.Machine);
			com.FileManagerX.BasicModels.MachineInfo m = (com.FileManagerX.BasicModels.MachineInfo)
					com.FileManagerX.Globals.Datas.DBManager.query
					("[&] Index = " + this.machine.getIndex());
			this.exist = m != null;
			if(m == null) { return false; }
			this.machine = m;
			return true;
		}
		else {
			com.FileManagerX.Commands.QueryUnit qu = new com.FileManagerX.Commands.QueryUnit();
			qu.setThis(
					com.FileManagerX.DataBase.Unit.Machine,
					"[&] Index = " + this.machine.getIndex(), 
					com.FileManagerX.Globals.Datas.ServerConnection
					);
			qu.send();
			com.FileManagerX.Replies.QueryUnit rep = (com.FileManagerX.Replies.QueryUnit)qu.receive();
			this.exist = rep != null && rep.isOK();
			if(rep == null || !rep.isOK()) { return false; }
			this.machine = (com.FileManagerX.BasicModels.MachineInfo)rep.getResult();
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
	public boolean addDepot(Depot depot) {
		if(depot == null) {
			return false;
		}
		this.depots.add(depot);
		return true;
	}
	public void refresh() {
		this.load();
		for(Depot d : this.depots) { d.refresh(); }
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
