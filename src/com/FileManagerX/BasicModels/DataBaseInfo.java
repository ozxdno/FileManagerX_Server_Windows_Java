package com.FileManagerX.BasicModels;

public class DataBaseInfo implements com.FileManagerX.Interfaces.IPublic{
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private long index;
	private long depotIndex;
	private String name;
	private com.FileManagerX.BasicEnums.DataBaseType type;
	private MachineInfo machineInfo;
	private String url;
	private com.FileManagerX.BasicModels.DepotInfo depotInfo;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setIndex(long index) {
		this.index = index;
		return true;
	}
	public boolean setIndex() {
		this.index = com.FileManagerX.Globals.Configurations.Next_DataBaseIndex();
		return true;
	}
	public boolean setDepotIndex(long depotIndex) {
		this.depotIndex = depotIndex;
		return true;
	}
	public boolean setDepotIndex() {
		if(this.depotInfo == null) {
			return false;
		}
		this.depotIndex = this.depotInfo.getIndex();
		return true;
	}
	public boolean setName(String name) {
		if(name == null) {
			return false;
		}
		this.name = name;
		return true;
	}
	public boolean setType(com.FileManagerX.BasicEnums.DataBaseType type) {
		this.type = type;
		return true;
	}
	public boolean setMachineInfo(MachineInfo machineInfo) {
		if(machineInfo == null) {
			return false;
		}
		this.machineInfo = machineInfo;
		return true;
	}
	public boolean setUrl(String url) {
		if(url == null) {
			return false;
		}
		this.url = url;
		return true;
	}
	public boolean setDepotInfo(com.FileManagerX.BasicModels.DepotInfo depotInfo) {
		if(depotInfo == null) {
			return false;
		}
		this.depotInfo = depotInfo;
		return false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public long getIndex() {
		return this.index;
	}
	public long getDepotIndex() {
		return this.depotIndex;
	}
	public String getName() {
		return this.name;
	}
	public com.FileManagerX.BasicEnums.DataBaseType getType() {
		return this.type;
	}
	public MachineInfo getMachineInfo() {
		return this.machineInfo;
	}
	public String getUrl() {
		return this.url;
	}
	public com.FileManagerX.BasicModels.DepotInfo getDepotInfo() {
		return this.depotInfo;
	}
	
	public com.FileManagerX.Interfaces.IDBManager getManager() {
		com.FileManagerX.Interfaces.IDBManager m = 
				com.FileManagerX.Globals.Datas.DBManagers.searchByDataBaseIndex(this.index);
		if(m != null) {
			return m;
		}
		
		m = com.FileManagerX.Factories.DataBaseFactory.createManager();
		m.setDBInfo(this);
		return m;
	}
	public com.FileManagerX.Interfaces.IDBChecker getChecker() {
		com.FileManagerX.Interfaces.IDBChecker c = com.FileManagerX.Factories.DataBaseFactory.createChecker();
		return c;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public DataBaseInfo() {
		initThis();
	}
	public DataBaseInfo(String url) {
		initThis();
		setUrl(url);
	}
	private void initThis() {
		this.index = -1;
		this.depotIndex = -1;
		this.type = com.FileManagerX.BasicEnums.DataBaseType.TXT;
		this.machineInfo = new MachineInfo();
		this.url = "";
		this.name = "";
		this.depotInfo = null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void clear() {
		initThis();
	}
	public String toString() {
		String name = this.name;
		if(name == null || name.length() == 0) {
			name = "No Name";
		}
		return "[" + name + "] " + this.url;
	}
	public Config toConfig() {
		Config c = new Config();
		c.setField(this.getClass().getSimpleName());
		c.addToBottom(this.index);
		c.addToBottom(this.depotIndex);
		c.addToBottom(this.name);
		c.addToBottom(this.type.toString());
		c.addToBottom(this.machineInfo.toConfig());
		c.addToBottom(this.url);
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
			this.index = c.fetchFirstLong();
			if(!c.getIsOK()) { return c; }
			this.depotIndex = c.fetchFirstLong();
			if(!c.getIsOK()) { return c; }
			this.name = c.fetchFirstString();
			if(!c.getIsOK()) { return c; }
			this.type = com.FileManagerX.BasicEnums.DataBaseType.valueOf(c.fetchFirstString());
			if(!c.getIsOK()) { return c; }
			c = this.machineInfo.input(c);
			if(!c.getIsOK()) { return c; }
			this.url = c.fetchFirstString();
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
		
		if(o instanceof DataBaseInfo) {
			DataBaseInfo d = (DataBaseInfo)o;
			this.index = d.index;
			this.depotIndex = d.depotIndex;
			this.type = d.type;
			this.depotInfo = d.depotInfo;
			this.machineInfo = d.machineInfo;
			this.url = d.url;
			this.name = d.name;
			return;
		}
	}
	public void copyValue(Object o) {
		if(o == null) { return; }
		
		if(o instanceof DataBaseInfo) {
			DataBaseInfo d = (DataBaseInfo)o;
			this.index = d.index;
			this.depotIndex = d.depotIndex;
			this.type = d.type;
			this.depotInfo = d.depotInfo;
			this.machineInfo.copyValue(d.machineInfo);
			this.url = new String(d.url);
			this.name = new String(d.name);
			return;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
