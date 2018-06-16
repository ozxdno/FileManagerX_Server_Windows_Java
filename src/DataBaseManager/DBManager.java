package DataBaseManager;

public class DBManager implements IDBManager{

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private BasicModels.DataBaseInfo dbInfo;
	
	private IDBManager manager;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setDBInfo(BasicModels.DataBaseInfo dbInfo) {
		if(dbInfo == null) {
			return false;
		}
		this.dbInfo = dbInfo;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public BasicModels.DataBaseInfo getDBInfo() {
		return this.dbInfo;
	}
	
	public boolean isConnected() {
		return this.manager.isConnected();
	}
	public boolean isQueryOK() {
		return this.manager.isQueryOK();
	}
	public boolean isUpdataOK() {
		return this.manager.isUpdataOK();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public DBManager() {
		initThis();
	}
	public DBManager(BasicModels.DataBaseInfo dbInfo) {
		initThis();
		this.setDBInfo(dbInfo);
	}
	private void initThis() {
		this.dbInfo = new  BasicModels.DataBaseInfo();
		this.manager = null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean initialize(Object infos) {
		this.setDBInfo((BasicModels.DataBaseInfo)infos);
		
		if(this.dbInfo.getType() == BasicEnums.DataBaseType.SQL) {
			this.manager = new SQLManager();
			this.manager.initialize(dbInfo);
		}
		
		return true;
	}
	public boolean connect() {
		return this.manager.connect();
	}
	public void disconnect() {
		this.manager.disconnect();
	}
	
	public BasicCollections.Folders QueryFolders(Object conditions) {
		return this.manager.QueryFolders(conditions);
	}
	
	public boolean QueryConfigurations() {
		return this.manager.QueryConfigurations();
	}
	public BasicModels.Folder QueryFolder(Object conditions) {
		return this.manager.QueryFolder(conditions);
	}
	
	public boolean updataFolders(BasicCollections.Folders folders) {
		return this.manager.updataFolders(folders);
	}
	
	public boolean updataConfigurations() {
		return this.manager.updataConfigurations();
	}
	public boolean updataFolder(BasicModels.Folder folder) {
		return this.manager.updataFolder(folder);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
