package DataBaseManager;

import Interfaces.IDBManager;

public class DBManager implements Interfaces.IDBManager{

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
	
	public boolean create() {
		return manager.create();
	}
	public boolean createTable(String tableName, String[] columns, String[] types) {
		return manager.createTable(tableName, columns, types);
	}
	public boolean createTable_Configurations() {
		return manager.createTable_Configurations();
	}
	public boolean createTable_Folders() {
		return manager.createTable_Folders();
	}
	public boolean createTable_Files() {
		return manager.createTable_Files();
	}
	public boolean createTable_MachineInfo() {
		return manager.createTable_MachineInfo();
	}
	public boolean createTable_DepotInfo() {
		return manager.createTable_DepotInfo();
	}
	public boolean createTable_DataBaseInfo() {
		return manager.createTable_DataBaseInfo();
	}
	public boolean createTable_Supports() {
		return manager.createTable_Supports();
	}
	public boolean createTable_Users() {
		return manager.createTable_Users();
	}
	public boolean createTable_Invitations() {
		return manager.createTable_Invitations();
	}
	
	public boolean delete() {
		return manager.delete();
	}
	public boolean deleteTable(String tableName) {
		return manager.deleteTable(tableName);
	}
	public boolean deleteTable_Configurations() {
		return manager.deleteTable_Configurations();
	}
	public boolean deleteTable_Folders() {
		return manager.deleteTable_Folders();
	}
	public boolean deleteTable_Files() {
		return manager.deleteTable_Files();
	}
	public boolean deleteTable_MachineInfo() {
		return manager.createTable_MachineInfo();
	}
	public boolean deleteTable_DepotInfo() {
		return manager.deleteTable_DepotInfo();
	}
	public boolean deleteTable_DataBaseInfo() {
		return manager.deleteTable_DataBaseInfo();
	}
	public boolean deleteTable_Supports() {
		return manager.deleteTable_Supports();
	}
	public boolean deleteTable_Users() {
		return manager.deleteTable_Users();
	}
	public boolean deleteTable_Invitations() {
		return manager.deleteTable_Invitations();
	}
	
	public boolean QueryConfigurations() {
		return this.manager.QueryConfigurations();
	}
	public BasicCollections.Folders QueryFolders(Object conditions) {
		return this.manager.QueryFolders(conditions);
	}
	
	
	public BasicModels.Folder QueryFolder(Object conditions) {
		return this.manager.QueryFolder(conditions);
	}
	public BasicModels.Invitation QueryInvitation(Object conditions) {
		return this.manager.QueryInvitation(conditions);
	}
	public BasicModels.User QueryUser(Object conditions) {
		return this.manager.QueryUser(conditions);
	}
	
	public boolean updataConfigurations() {
		return this.manager.updataConfigurations();
	}
	public boolean updataFolders(BasicCollections.Folders folders) {
		return this.manager.updataFolders(folders);
	}
	
	
	public boolean updataFolder(BasicModels.Folder folder) {
		return this.manager.updataFolder(folder);
	}
	public boolean updataUser(BasicModels.User user) {
		return this.manager.updataUser(user);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
