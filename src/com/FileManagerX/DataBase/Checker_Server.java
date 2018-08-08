package com.FileManagerX.DataBase;

public class Checker_Server {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private com.FileManagerX.Interfaces.IDBManager dbmanager;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setDBManager(com.FileManagerX.Interfaces.IDBManager dbmanager) {
		if(dbmanager == null || !dbmanager.isConnected()) {
			return false;
		}
		this.dbmanager = dbmanager;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.Interfaces.IDBManager getDBManager() {
		return this.dbmanager;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Checker_Server() {
		initThis();
	}
	public Checker_Server(com.FileManagerX.Interfaces.IDBManager dbmanager) {
		initThis();
		this.setDBManager(dbmanager);
	}
	private void initThis() {
		this.dbmanager = null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean initialize(Object infos) {
		return this.setDBManager((com.FileManagerX.Interfaces.IDBManager)infos);
	}
	
	public boolean check() {
		boolean ok = false;
		if(!this.checkDataBaseAndTables()) {
			ok = false;
		}
		if(!this.checkIndex()) {
			ok = false;
		}
		if(!this.checkMachines()) {
			ok = false;
		}
		if(!this.checkDepots()) {
			ok = false;
		}
		if(!this.checkDataBases()) {
			ok = false;
		}
		if(!this.checkUsers()) {
			ok = false;
		}
		if(!this.checkInvitations()) {
			ok = false;
		}
		
		return ok;
	}
	
	public boolean checkDataBaseAndTables() {
		if(this.dbmanager == null) {
			return false;
		}
		
		this.dbmanager.setUnit(com.FileManagerX.DataBase.Unit.SHELL);
		this.dbmanager.create();
		
		return true;
	}
	public boolean checkIndex() {
		if(this.dbmanager == null) {
			return false;
		}
		long maxIndex = 0;
		
		// Next_FileIndex
		maxIndex = 0;
		for(com.FileManagerX.Interfaces.IDBManager dbm : com.FileManagerX.Globals.Datas.DBManagers.getContent()) {
			dbm.setUnit(com.FileManagerX.DataBase.Unit.Folder);
			com.FileManagerX.BasicCollections.Folders fds = (com.FileManagerX.BasicCollections.Folders) dbm.query("");
			dbm.setUnit(com.FileManagerX.DataBase.Unit.BaseFile);
			com.FileManagerX.BasicCollections.BaseFiles fs = (com.FileManagerX.BasicCollections.BaseFiles) dbm.query("");
			for(com.FileManagerX.BasicModels.Folder f : fds.getContent()) {
				if(f.getIndex() > maxIndex) {
					maxIndex = f.getIndex();
				}
			}
			for(com.FileManagerX.BasicModels.BaseFile f : fs.getContent()) {
				if(f.getIndex() > maxIndex) {
					maxIndex = f.getIndex();
				}
			}
		}
		if(maxIndex > com.FileManagerX.Globals.Configurations.Next_FileIndex) {
			com.FileManagerX.Globals.Configurations.Next_FileIndex = maxIndex;
		}
		
		// Next_MachineIndex
		maxIndex = 0;
		com.FileManagerX.Globals.Datas.DBManager.setUnit(com.FileManagerX.DataBase.Unit.Machine);
		com.FileManagerX.BasicCollections.MachineInfos ms = (com.FileManagerX.BasicCollections.MachineInfos)
				com.FileManagerX.Globals.Datas.DBManager.query("");
		for(com.FileManagerX.BasicModels.MachineInfo m : ms.getContent()) {
			if(m.getIndex() > maxIndex) {
				maxIndex = m.getIndex();
			}
		}
		if(maxIndex > com.FileManagerX.Globals.Configurations.Next_MachineIndex) {
			 com.FileManagerX.Globals.Configurations.Next_MachineIndex = maxIndex;
		}
		
		// Next_DepotIndex
		maxIndex = 0;
		com.FileManagerX.Globals.Datas.DBManager.setUnit(com.FileManagerX.DataBase.Unit.Depot);
		com.FileManagerX.BasicCollections.DepotInfos ds = (com.FileManagerX.BasicCollections.DepotInfos)
				com.FileManagerX.Globals.Datas.DBManager.query("");
		for(com.FileManagerX.BasicModels.DepotInfo d : ds.getContent()) {
			if(d.getIndex() > maxIndex) {
				maxIndex = d.getIndex();
			}
		}
		if(maxIndex > com.FileManagerX.Globals.Configurations.Next_DepotIndex) {
			 com.FileManagerX.Globals.Configurations.Next_DepotIndex = maxIndex;
		}
		
		// Next_DataBaseIndex
		maxIndex = 0;
		com.FileManagerX.Globals.Datas.DBManager.setUnit(com.FileManagerX.DataBase.Unit.DataBase);
		com.FileManagerX.BasicCollections.DataBaseInfos dbs = (com.FileManagerX.BasicCollections.DataBaseInfos)
				com.FileManagerX.Globals.Datas.DBManager.query("");
		for(com.FileManagerX.BasicModels.DataBaseInfo db : dbs.getContent()) {
			if(db.getIndex() > maxIndex) {
				maxIndex = db.getIndex();
			}
		}
		if(maxIndex > com.FileManagerX.Globals.Configurations.Next_DataBaseIndex) {
			 com.FileManagerX.Globals.Configurations.Next_DataBaseIndex = maxIndex;
		}
		
		// Next_UserIndex
		maxIndex = 0;
		com.FileManagerX.Globals.Datas.DBManager.setUnit(com.FileManagerX.DataBase.Unit.User);
		com.FileManagerX.BasicCollections.Users us = (com.FileManagerX.BasicCollections.Users)
				com.FileManagerX.Globals.Datas.DBManager.query("");
		for(com.FileManagerX.BasicModels.User u : us.getContent()) {
			if(u.getIndex() > maxIndex) {
				maxIndex = u.getIndex();
			}
		}
		if(maxIndex > com.FileManagerX.Globals.Configurations.Next_UserIndex) {
			 com.FileManagerX.Globals.Configurations.Next_UserIndex = maxIndex;
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
		
		this.dbmanager.setUnit(com.FileManagerX.DataBase.Unit.DataBase);
		com.FileManagerX.BasicCollections.DataBaseInfos dbs = (com.FileManagerX.BasicCollections.DataBaseInfos)
				this.dbmanager.query("");
		this.dbmanager.setUnit(com.FileManagerX.DataBase.Unit.Depot);
		com.FileManagerX.BasicCollections.DepotInfos ds = (com.FileManagerX.BasicCollections.DepotInfos)
				this.dbmanager.query("");
		
		this.dbmanager.setUnit(com.FileManagerX.DataBase.Unit.Depot);
		for(int i=0; i<ds.size(); i++) {
			com.FileManagerX.BasicModels.DepotInfo d = ds.getContent().get(i);
			com.FileManagerX.BasicModels.DataBaseInfo db = dbs.search(d.getDBIndex());
			if(db == null) {
				this.dbmanager.remove(d);
			}
		}
		
		return true;
	}
	public boolean checkDataBases() {
		if(this.dbmanager == null) {
			return false;
		}
		
		this.dbmanager.setUnit(com.FileManagerX.DataBase.Unit.DataBase);
		com.FileManagerX.BasicCollections.DataBaseInfos dbs = (com.FileManagerX.BasicCollections.DataBaseInfos)
				this.dbmanager.query("");
		this.dbmanager.setUnit(com.FileManagerX.DataBase.Unit.Depot);
		com.FileManagerX.BasicCollections.DepotInfos ds = (com.FileManagerX.BasicCollections.DepotInfos)
				this.dbmanager.query("");
		
		this.dbmanager.setUnit(com.FileManagerX.DataBase.Unit.DataBase);
		for(int i=0; i<dbs.size(); i++) {
			com.FileManagerX.BasicModels.DataBaseInfo db = dbs.getContent().get(i);
			com.FileManagerX.BasicModels.DepotInfo d = ds.search(db.getDepotIndex());
			if(d == null) {
				this.dbmanager.remove(db);
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
