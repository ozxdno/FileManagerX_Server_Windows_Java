package BasicModels;

public class DataBaseInfo implements Interfaces.IPublic{
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private long index;
	private long depotIndex;
	private String name;
	private BasicEnums.DataBaseType type;
	private MachineInfo machineInfo;
	private String url; // TXT DataBase: Local url
						// SQL DataBase: ip:port\loginname\password\databasename
	private BasicModels.DepotInfo depotInfo;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setIndex(long index) {
		this.index = index;
		return true;
	}
	public boolean setIndex() {
		this.index = Globals.Configurations.Next_DataBaseIndex + 1;
		Globals.Configurations.Next_DataBaseIndex = this.index;
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
	public boolean setType(BasicEnums.DataBaseType type) {
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
	public boolean setDepotInfo(BasicModels.DepotInfo depotInfo) {
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
	public BasicEnums.DataBaseType getType() {
		return this.type;
	}
	public MachineInfo getMachineInfo() {
		return this.machineInfo;
	}
	public String getUrl() {
		return this.url;
	}
	public BasicModels.DepotInfo getDepotInfo() {
		return this.depotInfo;
	}
	
	public Interfaces.IDBManager getManager() {
		Interfaces.IDBManager m = Globals.Datas.DBManagers.searchDataBaseIndex(this.index);
		if(m != null) {
			return m;
		}
		
		m = Factories.DBManagerFactory.createDBManager();
		m.initialize(this);
		return m;
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
		this.type = BasicEnums.DataBaseType.TXT;
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
	public String output() {
		Config c = new Config("DataBaseInfo = ");
		c.addToBottom(index);
		c.addToBottom(depotIndex);
		c.addToBottom(this.name);
		c.addToBottom(this.type.toString());
		c.addToBottom(new Config(this.machineInfo.output()));
		c.addToBottom(url);
		return c.output();
	}
	public String input(String in) {
		try {
			Config c = new Config(in);
			this.index = c.fetchFirstLong();
			if(!c.getIsOK()) { return null; }
			this.depotIndex = c.fetchFirstLong();
			if(!c.getIsOK()) { return null; }
			this.name = c.fetchFirstString();
			if(!c.getIsOK()) { return null; }
			this.type = BasicEnums.DataBaseType.valueOf(c.fetchFirstString());
			if(!c.getIsOK()) { return null; }
			in = this.machineInfo.input(c.output());
			if(in == null) { return null; }
			c.setLine(in);
			this.url = c.fetchFirstString();
			if(!c.getIsOK()) { return null; }
			
			return c.output();
		} catch(Exception e) {
			BasicEnums.ErrorType.OTHERS.register(e.toString());
			return null;
		}
	}
	public void copyReference(Object o) {
		DataBaseInfo d = (DataBaseInfo)o;
		this.index = d.index;
		this.depotIndex = d.depotIndex;
		this.type = d.type;
		this.depotInfo = d.depotInfo;
		this.machineInfo = d.machineInfo;
		this.url = d.url;
		this.name = d.name;
	}
	public void copyValue(Object o) {
		DataBaseInfo d = (DataBaseInfo)o;
		this.index = d.index;
		this.depotIndex = d.depotIndex;
		this.type = d.type;
		this.depotInfo = d.depotInfo;
		this.machineInfo.copyValue(d.machineInfo);
		this.url = new String(d.url);
		this.name = new String(d.name);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean isLocal() {
		return this.machineInfo.isLocal();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
