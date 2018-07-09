package Interfaces;

public interface IDBManager {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	public boolean isConnected();
	public boolean isQueryOK();
	public boolean isUpdataOK();
	
	public boolean setDBInfo(BasicModels.DataBaseInfo dbInfo);
	public BasicModels.DataBaseInfo getDBInfo();
	
	public Interfaces.IDepotChecker getChecker();
	public Interfaces.IServerChecker getServerChecker();
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	public boolean initialize(Object infos);
	public boolean connect();
	public void disconnect();
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	public boolean createServerTables();
	public boolean createDepotTables();
	public boolean createTable(String tableName, String[] columns, String[] types);
	public boolean createTable_Folders();
	public boolean createTable_Files();
	public boolean createTable_MachineInfo();
	public boolean createTable_DepotInfo();
	public boolean createTable_DataBaseInfo();
	public boolean createTable_Users();
	public boolean createTable_Invitations();
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	public boolean deleteServerTables();
	public boolean deleteDepotTables();
	public boolean deleteTable(String tableName);
	public boolean deleteTable_Folders();
	public boolean deleteTable_Files();
	public boolean deleteTable_MachineInfo();
	public boolean deleteTable_DepotInfo();
	public boolean deleteTable_DataBaseInfo();
	public boolean deleteTable_Users();
	public boolean deleteTable_Invitations();
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	public BasicCollections.Folders QueryFolders(Object conditions);
	public BasicCollections.BaseFiles QueryFiles(Object conditions);
	public BasicCollections.Users QueryUsers(Object conditions);
	public BasicCollections.Invitations QueryInvitations(Object conditions);
	public BasicCollections.MachineInfos QueryMachineInfos(Object conditions);
	public BasicCollections.DepotInfos QueryDepotInfos(Object conditions);
	public BasicCollections.DataBaseInfos QueryDataBaseInfos(Object conditions);
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	public BasicModels.Folder QueryFolder(Object conditions);
	public BasicModels.BaseFile QueryFile(Object conditions);
	public BasicModels.User QueryUser(Object conditions);
	public BasicModels.Invitation QueryInvitation(Object conditions);
	public BasicModels.MachineInfo QueryMachineInfo(Object conditions);
	public BasicModels.DepotInfo QueryDepotInfo(Object conditions);
	public BasicModels.DataBaseInfo QueryDataBaseInfo(Object conditions);
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	public boolean updataFolders(BasicCollections.Folders folders);
	public boolean updataFiles(BasicCollections.BaseFiles files);
	public boolean updataUsers(BasicCollections.Users users);
	public boolean updataInvitations(BasicCollections.Invitations invitations);
	public boolean updataMachineInfos(BasicCollections.MachineInfos machineInfos);
	public boolean updataDepotInfos(BasicCollections.DepotInfos depotInfos);
	public boolean updataDataBaseInfos(BasicCollections.DataBaseInfos dbInfos);
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	public boolean updataFolder(BasicModels.Folder folder);
	public boolean updataFile(BasicModels.BaseFile file);
	public boolean updataUser(BasicModels.User user);
	public boolean updataInvitation(BasicModels.Invitation invitation);
	public boolean updataMachineInfo(BasicModels.MachineInfo machineInfo);
	public boolean updataDepotInfo(BasicModels.DepotInfo depotInfo);
	public boolean updataDataBaseInfo(BasicModels.DataBaseInfo dbInfo);
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	public boolean removeFolders(BasicCollections.Folders folders);
	public boolean removeFiles(BasicCollections.BaseFiles files);
	public boolean removeUsers(BasicCollections.Users users);
	public boolean removeInvitations(BasicCollections.Invitations invitations);
	public boolean removeMachineInfos(BasicCollections.MachineInfos machineInfos);
	public boolean removeDepotInfos(BasicCollections.DepotInfos depotInfos);
	public boolean removeDataBaseInfos(BasicCollections.DataBaseInfos dbInfos);
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	public boolean removeFolder(BasicModels.Folder folder);
	public boolean removeFile(BasicModels.BaseFile file);
	public boolean removeUser(BasicModels.User user);
	public boolean removeInvitation(BasicModels.Invitation invitation);
	public boolean removeMachineInfo(BasicModels.MachineInfo machineInfo);
	public boolean removeDepotInfo(BasicModels.DepotInfo depotInfo);
	public boolean removeDataBaseInfo(BasicModels.DataBaseInfo dbInfo);
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
}
