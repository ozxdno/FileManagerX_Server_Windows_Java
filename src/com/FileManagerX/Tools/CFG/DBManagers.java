package com.FileManagerX.Tools.CFG;

public class DBManagers {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static boolean load(com.FileManagerX.FileModels.CFG cfg) {
		com.FileManagerX.BasicCollections.DataBaseInfos dbs = null;
		com.FileManagerX.BasicCollections.DepotInfos ds = null;
		
		if(com.FileManagerX.Globals.Configurations.IsServer) {
			dbs = (com.FileManagerX.BasicCollections.DataBaseInfos)com.FileManagerX.Globals.Datas.DBManager.querys2(
					"=1|[&] machineInfo = " + com.FileManagerX.Globals.Configurations.This_MachineIndex,
					com.FileManagerX.DataBase.Unit.DataBase
				);
			ds = (com.FileManagerX.BasicCollections.DepotInfos)com.FileManagerX.Globals.Datas.DBManager.querys2(
					"=1|[&] machineInfo = " + com.FileManagerX.Globals.Configurations.This_MachineIndex,
					com.FileManagerX.DataBase.Unit.Depot
				);
			
		}
		
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.DataBaseInfo> it = dbs.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.DataBaseInfo db = it.getNext();
			com.FileManagerX.BasicModels.DepotInfo d = ds.searchByKey(db.getDepotIndex());
			if(d != null && d.getIndex() != 1) {
				db.setDepotInfo(d);
				d.setDBInfo(db);
				db.setDepotIndex();
				d.setDBIndex();
				com.FileManagerX.Interfaces.IDBManager dbm = db.getManager();
				dbm.connect();
				com.FileManagerX.Globals.Datas.DBManagers.add(dbm);
			}
		}
		
		return true;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public final static boolean save(com.FileManagerX.FileModels.CFG cfg) {
		java.lang.String line = "";
		
		cfg.getContent().add(line);
		line = "/**************************************** A List of Current Depots & DataBases ***************************************/";
		cfg.getContent().add(line);
		line = "";
		cfg.getContent().add(line);
		
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IDBManager> it = 
				com.FileManagerX.Globals.Datas.DBManagers.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.Interfaces.IDBManager dbm = it.getNext();
			line = "Depot = " + dbm.getDBInfo().getDepotInfo().getName() + "|" + dbm.getDBInfo().getDepotInfo().getUrl();
			cfg.getContent().add(line);
			line = "DataBase = " + dbm.getDBInfo().getName() + "|" + dbm.getDBInfo().getType().toString() + "|" + dbm.getDBInfo().getUrl();
			cfg.getContent().add(line);
		}
		
		return true;
	}
	public final static boolean saveNew(com.FileManagerX.FileModels.CFG cfg) {
		java.lang.String line = "";
		
		cfg.getContent().add(line);
		line = "/**************************************** A List of Current Depots & DataBases ***************************************/";
		cfg.getContent().add(line);
		line = "";
		cfg.getContent().add(line);
		
		return true;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
