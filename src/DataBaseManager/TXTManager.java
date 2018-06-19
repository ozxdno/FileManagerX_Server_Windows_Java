package DataBaseManager;

import java.util.*;

public class TXTManager implements Interfaces.IDBManager {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private BasicModels.DataBaseInfo dbInfo;
	private boolean isConnected;
	private boolean isQueryOK;
	private boolean isUpdataOK;
	
	private BasicCollections.Folders folders;
	private BasicCollections.BaseFiles files;
	private BasicCollections.MachineInfos machineInfos;
	private BasicCollections.DepotInfos depotInfos;
	private BasicCollections.DataBaseInfos dbInfos;
	private BasicCollections.Supports supports;
	private BasicCollections.Users users;
	private BasicCollections.Invitations invitations;
	
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
		return this.isConnected;
	}
	public boolean isQueryOK() {
		return this.isQueryOK;
	}
	public boolean isUpdataOK() {
		return this.isUpdataOK;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public TXTManager() {
		initThis();
	}
	public TXTManager(BasicModels.DataBaseInfo dbInfo) {
		initThis();
		this.setDBInfo(dbInfo);
	}
	private void initThis() {
		this.isConnected = false;
		this.isQueryOK = false;
		this.isUpdataOK = false;
		this.dbInfo = new  BasicModels.DataBaseInfo();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean initialize(Object infos) {
		return this.setDBInfo((BasicModels.DataBaseInfo)infos);
	}
	public boolean connect() {
		return new java.io.File(Tools.Url.getLocalUrl(dbInfo.getUrl())).exists();
	}
	public void disconnect() {
		this.isConnected = false;
	}
	
	public boolean create() {
		if(!this.createTable_Folders()) {
			return false;
		}
		if(!this.createTable_Files()) {
			return false;
		}
		return true;
	}
	public boolean createTable(String tableName, String[] columns, String[] types) {
		if(tableName == null || tableName.length() == 0) {
			return false;
		}
		java.io.File f = new java.io.File(dbInfo.getUrl() + "\\" + tableName + ".txt");
		if(f.exists()) {
			return true;
		}
		try {
			return f.createNewFile();
		} catch(Exception e) {
			return false;
		}
	}
	public boolean createTable_Configurations() {
		return this.createTable("Configurations", null, null);
	}
	public boolean createTable_Files() {
		return this.createTable("Files", null, null);
	}
	public boolean createTable_Folders() {
		return this.createTable("Folders", null, null);
	}
	public boolean createTable_MachineInfo() {
		return this.createTable("MachineInfo", null, null);
	}
	public boolean createTable_DepotInfo() {
		return this.createTable("DepotInfo", null, null);
	}
	public boolean createTable_DataBaseInfo() {
		return this.createTable("DataBaseInfo", null, null);
	}
	public boolean createTable_Supports() {
		return this.createTable("Supports", null, null);
	}
	public boolean createTable_Users() {
		return this.createTable("Users", null, null);
	}
	public boolean createTable_Invitations() {
		return this.createTable("Invitations", null, null);
	}
	
	public boolean delete() {
		if(!this.deleteTable_Folders()) {
			return false;
		}
		if(!this.deleteTable_Files()) {
			return false;
		}
		return true;
	}
	public boolean deleteTable(String tableName) {
		java.io.File f = new java.io.File(dbInfo.getUrl() + "\\" + tableName + ".txt");
		if(!f.exists()) {
			return true;
		}
		try {
			return f.delete();
		} catch(Exception e) {
			return false;
		}
	}
	public boolean deleteTable_Configurations() {
		return this.deleteTable("Configurations");
	}
	public boolean deleteTable_Files() {
		return this.deleteTable("Files");
	}
	public boolean deleteTable_Folders() {
		return this.deleteTable("Folders");
	}
	public boolean deleteTable_MachineInfo() {
		return this.deleteTable("MachineInfo");
	}
	public boolean deleteTable_DepotInfo() {
		return this.deleteTable("DepotInfo");
	}
	public boolean deleteTable_DataBaseInfo() {
		return this.deleteTable("DataBaseInfo");
	}
	public boolean deleteTable_Supports() {
		return this.deleteTable("Supports");
	}
	public boolean deleteTable_Users() {
		return this.deleteTable("Users");
	}
	public boolean deleteTable_Invitations() {
		return this.deleteTable("Invitations");
	}
	
	public BasicCollections.Folders QueryFolders(Object conditions) {
		return null;
	}
	
	public boolean QueryConfigurations() {
		
		return true;
	}
	public BasicModels.Folder QueryFolder(Object conditions) {
		List<Integer> res = new ArrayList<Integer>();
		if(this.folders == null) {
			FileModels.Text txt = new FileModels.Text(this.dbInfo.getUrl() + "\\Folders.txt");
			txt.load(false);
			for(String line : txt.getContent()) {
				BasicModels.Folder f = new BasicModels.Folder();
				String r = f.input(line);
				if(r != null) {
					this.folders.add(f);
				}
			}
		}
		QueryConditions qcs = (QueryConditions)conditions;
		if(qcs.size() == 0) {
			if(this.folders.size() == 0) {
				return null;
			}
			return this.folders.getContent().get(0);
		}
		for(QueryCondition qc : qcs.getContent()) {
			if(qc.getItemName().equals("Index")) {
				if(qc.getEqual() != null) {
					
				}
			}
		}
		return null;
	}
	public BasicModels.Invitation QueryInvitation(Object conditions) {
		return null;
	}
	public BasicModels.User QueryUser(Object conditions) {
		return null;
	}
	
	public boolean updataFolders(BasicCollections.Folders folders) {
		return false;
	}
	
	public boolean updataConfigurations() {
		return true;
	}
	public boolean updataFolder(BasicModels.Folder folder) {
		
		return false;
	}
	public boolean updataUser(BasicModels.User user) {
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private boolean loadFolders() {
		return true;
	}
	private boolean loadFiles() {
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
