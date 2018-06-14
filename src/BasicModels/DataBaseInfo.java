package BasicModels;

public class DataBaseInfo implements Tools.IPublic{
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private long index;
	private BasicEnums.DataBaseType type;
	private MachineInfo machineInfo;
	private String url;
	private String dbName;
	private String loginName;
	private String password;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setIndex(long index) {
		this.index = index;
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
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public long getIndex() {
		return this.index;
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
		this.type = BasicEnums.DataBaseType.Memory;
		this.machineInfo = new MachineInfo();
		this.url = "";
		this.dbName = "";
		this.loginName = "";
		this.password = "";
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void clear() {
		initThis();
	}
	public String toString() {
		return dbName;
	}
	public String output() {
		Config c = new Config(this.machineInfo.output());
		c.setField("DataBaseInfo");
		c.addToTop(this.type.ordinal());;
		c.addToTop(this.index);
		c.addToBottom(url);
		c.addToBottom(dbName);
		c.addToBottom(loginName);
		c.addToBottom(password);
		return c.output();
	}
	public boolean input(String in) {
		Config c = new Config(in);
		this.index = c.fetchFirstLong();
		if(!c.getIsOK()) { return false; }
		this.type = BasicEnums.DataBaseType.values()[c.fetchFirstInt()];
		if(!c.getIsOK()) { return false; }
		this.password = c.fetchLastString();
		if(!c.getIsOK()) { return false; }
		this.loginName = c.fetchLastString();
		if(!c.getIsOK()) { return false; }
		this.dbName = c.fetchLastString();
		if(!c.getIsOK()) { return false; }
		this.url = c.fetchLastString();
		if(!c.getIsOK()) { return false; }
		this.machineInfo.input(c.output());
		if(!c.getIsOK()) { return false; }
		return false;
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
}
