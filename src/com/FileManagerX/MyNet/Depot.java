package com.FileManagerX.MyNet;

public class Depot implements com.FileManagerX.Interfaces.IPublic {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private com.FileManagerX.BasicModels.DepotInfo depot;
	private java.util.List<String> permitGroups;
	private boolean exist;
	private String name;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setDepot(com.FileManagerX.BasicModels.DepotInfo depot) {
		if(depot == null) {
			return false;
		}
		this.depot = depot;
		return true;
	}
	public boolean setPermitGroups(java.util.List<String> groupNames) {
		if(groupNames == null) {
			return false;
		}
		this.permitGroups = groupNames;
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
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.BasicModels.DepotInfo getDepot() {
		return this.depot;
	}
	public java.util.List<String> getPermitGroups() {
		return this.permitGroups;
	}
	public String getName() {
		return this.name;
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

	public Depot() {
		initThis();
	}
	private void initThis() {
		this.depot = new com.FileManagerX.BasicModels.DepotInfo();
		this.permitGroups = new java.util.ArrayList<>();
		this.name = "";
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void clear() {
		initThis();
	}
	public String toString() {
		return this.depot.getName();
	}
	public String output() {
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config();
		c.setField("Net-" + this.getClass().getSimpleName());
		c.addToBottom(this.depot.getIndex());
		c.addToBottom(com.FileManagerX.Tools.String.link(
				com.FileManagerX.Tools.List2Array.toStringArray(permitGroups),
				"|")
			);
		c.addToBottom(this.name);
		
		return c.output();
	}
	public String input(String in) {
		try {
			com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config(in);
			this.depot.setIndex(c.fetchFirstLong());
			if(!c.getIsOK()) { return null; }
			this.permitGroups = com.FileManagerX.Tools.Array2List.toStringList(
					com.FileManagerX.Tools.String.split(c.fetchFirstString(), '|')
				);
			if(!c.getIsOK()) { return null; }
			this.name = c.fetchFirstString();
			if(!c.getIsOK()) { return null; }
			
			return c.output();
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			return null;
		}
	}
	public void copyReference(Object o) {
		Depot d = (Depot)o;
		this.depot = d.depot;
		this.permitGroups = d.permitGroups;
	}
	public void copyValue(Object o) {
		Depot d = (Depot)o;
		this.depot.copyValue(d.depot);
		this.permitGroups.clear();
		for(String s : d.permitGroups) { this.permitGroups.add(new String(s)); }
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean load() {
		if(com.FileManagerX.Globals.Configurations.IsServer) {
			com.FileManagerX.Globals.Datas.DBManager.setUnit(com.FileManagerX.DataBase.Unit.Depot);
			com.FileManagerX.BasicModels.DepotInfo depot = (com.FileManagerX.BasicModels.DepotInfo)
					com.FileManagerX.Globals.Datas.DBManager.query
					("[&] Index = " + this.depot.getIndex());
			this.exist = depot != null;
			if(depot == null) { return false; }
			this.depot = depot;
			return true;
		}
		else {
			com.FileManagerX.Commands.QueryUnit qu = new com.FileManagerX.Commands.QueryUnit();
			qu.setThis(
					com.FileManagerX.DataBase.Unit.Depot,
					"[&] Index = " + this.depot.getIndex(),
					com.FileManagerX.Globals.Datas.ServerConnection
					);
			qu.send();
			com.FileManagerX.Replies.QueryUnit rep = (com.FileManagerX.Replies.QueryUnit)qu.receive();
			this.exist = rep != null && rep.isOK();
			if(rep == null || !rep.isOK()) { return false; }
			this.depot = (com.FileManagerX.BasicModels.DepotInfo)rep.getResult();
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
	public void refresh() {
		this.load();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
