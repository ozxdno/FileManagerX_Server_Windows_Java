package Interfaces;

public interface IDBManager {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	public boolean isConnected();
	public boolean isQueryOK();
	public boolean isUpdataOK();
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	public boolean initialize(Object infos);
	public boolean connect();
	public void disconnect();
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	public boolean create();
	public boolean createTable(String tableName, String[] columns, String[] types);
	public boolean createTable_Configurations();
	public boolean createTable_Folders();
	public boolean createTable_Files();
	public boolean createTable_MachineInfo();
	public boolean createTable_DepotInfo();
	public boolean createTable_DataBaseInfo();
	public boolean createTable_Supports();
	public boolean createTable_Users();
	public boolean createTable_Invitations();
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	public boolean delete();
	public boolean deleteTable(String tableName);
	public boolean deleteTable_Configurations();
	public boolean deleteTable_Folders();
	public boolean deleteTable_Files();
	public boolean deleteTable_MachineInfo();
	public boolean deleteTable_DepotInfo();
	public boolean deleteTable_DataBaseInfo();
	public boolean deleteTable_Supports();
	public boolean deleteTable_Users();
	public boolean deleteTable_Invitations();
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	public boolean QueryConfigurations();
	public BasicCollections.Folders QueryFolders(Object conditions);
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	public BasicModels.Folder QueryFolder(Object conditions);
	public BasicModels.Invitation QueryInvitation(Object conditions);
	public BasicModels.User QueryUser(Object conditions);
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	public boolean updataConfigurations();
	public boolean updataFolders(BasicCollections.Folders folders);
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	public boolean updataFolder(BasicModels.Folder folder);
	public boolean updataUser(BasicModels.User user);
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
}
