package ServerChecker;

public class ServerChecker implements Interfaces.IServerChecker {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private Interfaces.IDBManager dbmanager;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setDBManager(Interfaces.IDBManager dbmanager) {
		if(dbmanager == null || !dbmanager.isConnected()) {
			return false;
		}
		this.dbmanager = dbmanager;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Interfaces.IDBManager getDBManager() {
		return this.dbmanager;
	}
	
	public Interfaces.IDepotChecker getDepotChecker() {
		if(this.dbmanager == null) {
			return null;
		}
		return this.dbmanager.getChecker();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public ServerChecker() {
		initThis();
	}
	public ServerChecker(Interfaces.IDBManager dbmanager) {
		initThis();
		this.setDBManager(dbmanager);
	}
	private void initThis() {
		this.dbmanager = null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean initialize(Object infos) {
		return this.setDBManager((Interfaces.IDBManager)infos);
	}
	
	public boolean check() {
		
		if(!this.checkDataBaseAndTables()) {
			return false;
		}
		if(!this.checkIndex()) {
			return false;
		}
		if(!this.checkMachines()) {
			return false;
		}
		if(!this.checkDepots()) {
			return false;
		}
		if(!this.checkDataBases()) {
			return false;
		}
		if(!this.checkUsers()) {
			return false;
		}
		if(!this.checkInvitations()) {
			return false;
		}
		
		return true;
	}
	
	public boolean checkDataBaseAndTables() {
		if(this.dbmanager == null) {
			return false;
		}
		
		this.dbmanager.createDataBase();
		this.dbmanager.createServerTables();
		
		return true;
	}
	public boolean checkIndex() {
		if(this.dbmanager == null) {
			return false;
		}
		long maxIndex = 0;
		
		// Next_FileIndex
		maxIndex = 0;
		for(Interfaces.IDBManager dbm : Globals.Datas.DBManagers.getContent()) {
			BasicCollections.Folders fds = dbm.QueryFolders("");
			BasicCollections.BaseFiles fs = dbm.QueryFiles("");
			for(BasicModels.Folder f : fds.getContent()) {
				if(f.getIndex() > maxIndex) {
					maxIndex = f.getIndex();
				}
			}
			for(BasicModels.BaseFile f : fs.getContent()) {
				if(f.getIndex() > maxIndex) {
					maxIndex = f.getIndex();
				}
			}
		}
		if(maxIndex > Globals.Configurations.Next_FileIndex) {
			Globals.Configurations.Next_FileIndex = maxIndex;
		}
		
		// Next_MachineIndex
		maxIndex = 0;
		BasicCollections.MachineInfos ms = Globals.Datas.DBManager.QueryMachineInfos("");
		for(BasicModels.MachineInfo m : ms.getContent()) {
			if(m.getIndex() > maxIndex) {
				maxIndex = m.getIndex();
			}
		}
		if(maxIndex > Globals.Configurations.Next_MachineIndex) {
			 Globals.Configurations.Next_MachineIndex = maxIndex;
		}
		
		// Next_DepotIndex
		maxIndex = 0;
		BasicCollections.DepotInfos ds = Globals.Datas.DBManager.QueryDepotInfos("");
		for(BasicModels.DepotInfo d : ds.getContent()) {
			if(d.getIndex() > maxIndex) {
				maxIndex = d.getIndex();
			}
		}
		if(maxIndex > Globals.Configurations.Next_DepotIndex) {
			 Globals.Configurations.Next_DepotIndex = maxIndex;
		}
		
		// Next_DataBaseIndex
		maxIndex = 0;
		BasicCollections.DataBaseInfos dbs = Globals.Datas.DBManager.QueryDataBaseInfos("");
		for(BasicModels.DataBaseInfo db : dbs.getContent()) {
			if(db.getIndex() > maxIndex) {
				maxIndex = db.getIndex();
			}
		}
		if(maxIndex > Globals.Configurations.Next_DataBaseIndex) {
			 Globals.Configurations.Next_DataBaseIndex = maxIndex;
		}
		
		// Next_UserIndex
		maxIndex = 0;
		BasicCollections.Users us = Globals.Datas.DBManager.QueryUsers("");
		for(BasicModels.User u : us.getContent()) {
			if(u.getIndex() > maxIndex) {
				maxIndex = u.getIndex();
			}
		}
		if(maxIndex > Globals.Configurations.Next_UserIndex) {
			 Globals.Configurations.Next_UserIndex = maxIndex;
		}
		
		return true;
	}
	
	public boolean checkMachines() {
		if(this.dbmanager == null) {
			return false;
		}
		
		return true;
	}
	public boolean checkDepots() {
		if(this.dbmanager == null) {
			return false;
		}
		
		BasicCollections.DataBaseInfos dbs = this.dbmanager.QueryDataBaseInfos("");
		BasicCollections.DepotInfos ds = this.dbmanager.QueryDepotInfos("");
		for(int i=0; i<ds.size(); i++) {
			BasicModels.DepotInfo d = ds.getContent().get(i);
			BasicModels.DataBaseInfo db = dbs.search(d.getDBIndex());
			if(db == null) {
				this.dbmanager.removeDepotInfo(d);
			}
		}
		
		return true;
	}
	public boolean checkDataBases() {
		if(this.dbmanager == null) {
			return false;
		}
		
		BasicCollections.DataBaseInfos dbs = this.dbmanager.QueryDataBaseInfos("");
		BasicCollections.DepotInfos ds = this.dbmanager.QueryDepotInfos("");
		for(int i=0; i<dbs.size(); i++) {
			BasicModels.DataBaseInfo db = dbs.getContent().get(i);
			BasicModels.DepotInfo d = ds.search(db.getDepotIndex());
			if(d == null) {
				this.dbmanager.removeDataBaseInfo(db);
			}
		}
		
		return true;
	}
	public boolean checkUsers() {
		if(this.dbmanager == null) {
			return false;
		}
		
		return true;
	}
	public boolean checkInvitations() {
		if(this.dbmanager == null) {
			return false;
		}
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
