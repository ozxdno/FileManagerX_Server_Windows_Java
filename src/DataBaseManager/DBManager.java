package DataBaseManager;

public class DBManager implements Interfaces.IDBManager {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private BasicModels.DataBaseInfo dbInfo;
	
	private Interfaces.IDBManager manager;
	
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
		if(this.dbInfo.getType() == BasicEnums.DataBaseType.TXT) {
			this.manager = new TXTManager();
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
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean createServerTables() {
		return manager.createServerTables();
	}
	public boolean createDepotTables() {
		return manager.createDepotTables();
	}
	public boolean createTable(String tableName, String[] columns, String[] types) {
		return manager.createTable(tableName, columns, types);
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
	public boolean createTable_Users() {
		return manager.createTable_Users();
	}
	public boolean createTable_Invitations() {
		return manager.createTable_Invitations();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean deleteServerTables() {
		return manager.deleteServerTables();
	}
	public boolean deleteDepotTables() {
		return manager.deleteDepotTables();
	}
	public boolean deleteTable(String tableName) {
		return manager.deleteTable(tableName);
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
	public boolean deleteTable_Users() {
		return manager.deleteTable_Users();
	}
	public boolean deleteTable_Invitations() {
		return manager.deleteTable_Invitations();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public BasicCollections.Folders QueryFolders(Object conditions) {
		return this.manager.QueryFolders(conditions);
	}
	public BasicCollections.BaseFiles QueryFiles(Object conditions) {
		return this.manager.QueryFiles(conditions);
	}
	public BasicCollections.Users QueryUsers(Object conditions) {
		return this.manager.QueryUsers(conditions);
	}
	public BasicCollections.Invitations QueryInvitations(Object conditions) {
		return this.manager.QueryInvitations(conditions);
	}
	public BasicCollections.MachineInfos QueryMachineInfos(Object conditions) {
		return this.manager.QueryMachineInfos(conditions);
	}
	public BasicCollections.DepotInfos QueryDepotInfos(Object conditions) {
		return this.manager.QueryDepotInfos(conditions);
	}
	public BasicCollections.DataBaseInfos QueryDataBaseInfos(Object conditions) {
		return this.manager.QueryDataBaseInfos(conditions);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public BasicModels.Folder QueryFolder(Object conditions) {
		return this.manager.QueryFolder(conditions);
	}
	public BasicModels.BaseFile QueryFile(Object conditions) {
		return this.manager.QueryFile(conditions);
	}
	public BasicModels.User QueryUser(Object conditions) {
		return this.manager.QueryUser(conditions);
	}
	public BasicModels.Invitation QueryInvitation(Object conditions) {
		return this.manager.QueryInvitation(conditions);
	}
	public BasicModels.MachineInfo QueryMachineInfo(Object conditions) {
		return this.manager.QueryMachineInfo(conditions);
	}
	public BasicModels.DepotInfo QueryDepotInfo(Object conditions) {
		return this.manager.QueryDepotInfo(conditions);
	}
	public BasicModels.DataBaseInfo QueryDataBaseInfo(Object conditions) {
		return this.manager.QueryDataBaseInfo(conditions);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean updataFolders(BasicCollections.Folders folders) {
		return this.manager.updataFolders(folders);
	}
	public boolean updataFiles(BasicCollections.BaseFiles files) {
		return this.manager.updataFiles(files);
	}
	public boolean updataUsers(BasicCollections.Users users) {
		return this.manager.updataUsers(users);
	}
	public boolean updataInvitations(BasicCollections.Invitations invitations) {
		return this.manager.updataInvitations(invitations);
	}
	public boolean updataMachineInfos(BasicCollections.MachineInfos machineInfos) {
		return this.manager.updataMachineInfos(machineInfos);
	}
	public boolean updataDepotInfos(BasicCollections.DepotInfos depotInfos) {
		return this.manager.updataDepotInfos(depotInfos);
	}
	public boolean updataDataBaseInfos(BasicCollections.DataBaseInfos dbInfos) {
		return this.updataDataBaseInfos(dbInfos);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean updataFolder(BasicModels.Folder folder) {
		return this.manager.updataFolder(folder);
	}
	public boolean updataFile(BasicModels.BaseFile file) {
		return this.manager.updataFile(file);
	}
	public boolean updataUser(BasicModels.User user) {
		return this.manager.updataUser(user);
	}
	public boolean updataInvitation(BasicModels.Invitation invitation) {
		return this.manager.updataInvitation(invitation);
	}
	public boolean updataMachineInfo(BasicModels.MachineInfo machineInfo) {
		return this.manager.updataMachineInfo(machineInfo);
	}
	public boolean updataDepotInfo(BasicModels.DepotInfo depotInfo) {
		return this.manager.updataDepotInfo(depotInfo);
	}
	public boolean updataDataBaseInfo(BasicModels.DataBaseInfo dbInfo) {
		return this.manager.updataDataBaseInfo(dbInfo);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean removeFolders(BasicCollections.Folders folders) {
		return this.manager.removeFolders(folders);
	}
	public boolean removeFiles(BasicCollections.BaseFiles files) {
		return this.manager.removeFiles(files);
	}
	public boolean removeUsers(BasicCollections.Users users) {
		return this.manager.removeUsers(users);
	}
	public boolean removeInvitations(BasicCollections.Invitations invitations) {
		return this.manager.removeInvitations(invitations);
	}
	public boolean removeMachineInfos(BasicCollections.MachineInfos machineInfos) {
		return this.manager.removeMachineInfos(machineInfos);
	}
	public boolean removeDepotInfos(BasicCollections.DepotInfos depotInfos) {
		return this.manager.removeDepotInfos(depotInfos);
	}
	public boolean removeDataBaseInfos(BasicCollections.DataBaseInfos dbInfos) {
		return this.manager.removeDataBaseInfos(dbInfos);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean removeFolder(BasicModels.Folder folder) {
		return this.manager.removeFolder(folder);
	}
	public boolean removeFile(BasicModels.BaseFile file) {
		return this.manager.removeFile(file);
	}
	public boolean removeUser(BasicModels.User user) {
		return this.manager.removeUser(user);
	}
	public boolean removeInvitation(BasicModels.Invitation invitation) {
		return this.manager.removeInvitation(invitation);
	}
	public boolean removeMachineInfo(BasicModels.MachineInfo machineInfo) {
		return this.manager.removeMachineInfo(machineInfo);
	}
	public boolean removeDepotInfo(BasicModels.DepotInfo depotInfo) {
		return this.manager.removeDepotInfo(depotInfo);
	}
	public boolean removeDataBaseInfo(BasicModels.DataBaseInfo dbInfo) {
		return this.manager.removeDataBaseInfo(dbInfo);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
