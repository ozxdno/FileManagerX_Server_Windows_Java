package BasicModels;

public class DataBaseInfo implements Tools.IPublic{
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private long index;
	private long depotIndex;
	private BasicEnums.DataBaseType type;
	private MachineInfo machineInfo;
	private String url; // db 文件的本地路径（不包含IP/Port）
	private String dbName;
	private String loginName;
	private String password;
	private BasicModels.DepotInfo depotInfo;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setIndex(long index) {
		this.index = index;
		return true;
	}
	public boolean setIndex() {
		return true;
	}
	public boolean setDepotIndex(long depotIndex) {
		this.depotIndex = depotIndex;
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
	public boolean setDBName(String dbName) {
		if(dbName == null) {
			return false;
		}
		this.dbName = dbName;
		return true;
	}
	public boolean setLoginName(String loginName) {
		if(loginName == null) {
			return false;
		}
		this.loginName = loginName;
		return true;
	}
	public boolean setPassword(String password) {
		if(password == null) {
			return false;
		}
		this.password = password;
		return true;
	}
	public boolean setDepotInfo(BasicModels.DepotInfo depotInfo) {
		if(depotInfo == null) {
			return false;
		}
		this.depotInfo = depotInfo;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public long getIndex() {
		return this.index;
	}
	public long getDepotIndex() {
		return this.depotIndex;
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
	public String getDBName() {
		return this.dbName;
	}
	public String getLoginName() {
		return this.loginName;
	}
	public String getPassword() {
		return this.password;
	}
	public DepotInfo getDepotInfo() {
		return this.depotInfo;
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
		this.type = BasicEnums.DataBaseType.Memory;
		this.machineInfo = new MachineInfo();
		this.url = "";
		this.dbName = "";
		this.loginName = "";
		this.password = "";
		this.depotInfo = new DepotInfo();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void clear() {
		initThis();
	}
	public String toString() {
		String name = this.dbName;
		if(name == null || name.length() == 0) {
			name = "No Name";
		}
		String url = this.machineInfo.getUrl() + "\\" + this.url;
		return "[" + name + "]: " + url;
	}
	public String output() {
		Config c = new Config("DataBaseInfo = ");
		c.addToBottom(index);
		c.addToBottom(depotIndex);
		c.addToBottom(this.type.ordinal());
		c.addToBottom(new Config(this.machineInfo.output()));
		c.addToBottom(url);
		c.addToBottom(dbName);
		c.addToBottom(loginName);
		c.addToBottom(password);
		return c.output();
	}
	public String input(String in) {
		Config c = new Config(in);
		this.index = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		this.depotIndex = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		this.type = BasicEnums.DataBaseType.values()[c.fetchFirstInt()];
		if(!c.getIsOK()) { return null; }
		in = this.machineInfo.input(c.output());
		if(in == null) { return null; }
		c.setValue(in);
		this.url = c.fetchFirstString();
		if(!c.getIsOK()) { return null; }
		this.dbName = c.fetchFirstString();
		if(!c.getIsOK()) { return null; }
		this.loginName = c.fetchFirstString();
		if(!c.getIsOK()) { return null; }
		this.password = c.fetchFirstString();
		if(!c.getIsOK()) { return null; }
		
		return c.output();
	}
	public void copyReference(Object o) {
		DataBaseInfo d = (DataBaseInfo)o;
		this.machineInfo = d.machineInfo;
		this.url = d.url;
		this.dbName = d.dbName;
		this.loginName = d.loginName;
		this.password = d.password;
	}
	public void copyValue(Object o) {
		DataBaseInfo d = (DataBaseInfo)o;
		this.machineInfo.copyValue(d.machineInfo);
		this.url = new String(d.url);
		this.dbName = new String(d.dbName);
		this.loginName = new String(d.loginName);
		this.password = new String(d.password);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean isLocal() {
		return this.machineInfo.isLocal();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
