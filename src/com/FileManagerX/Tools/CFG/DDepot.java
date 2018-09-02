package com.FileManagerX.Tools.CFG;

public class DDepot {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static boolean load(com.FileManagerX.FileModels.CFG cfg) {
		while(true) {
			com.FileManagerX.BasicModels.Config cdepot = cfg.getConfigs().fetchByField("-Depot");
			if(cdepot == null) { break; }
			com.FileManagerX.BasicModels.Config cdb = cfg.getConfigs().fetchByField("-DataBase");
			if(cdb == null) { break; }
			
			String qcs1 = "=2|[&] machineInfo = " + com.FileManagerX.Globals.Configurations.This_MachineIndex + ", " +
					"[&] name = "  + cdepot.getValue();
			com.FileManagerX.BasicModels.DepotInfo depot = null;
			if(com.FileManagerX.Globals.Configurations.IsServer) {
				depot = (com.FileManagerX.BasicModels.DepotInfo)com.FileManagerX.Globals.Datas.DBManager.query2(
						qcs1,
						com.FileManagerX.DataBase.Unit.Depot
					);
				if(depot != null) {
					com.FileManagerX.Globals.Datas.DBManager.remove(depot, com.FileManagerX.DataBase.Unit.Depot);
				}
			}
			
			String qcs2 = "=2|[&] machineInfo = " + com.FileManagerX.Globals.Configurations.This_MachineIndex + ", " +
					"[&] name = "  + cdb.getValue();
			com.FileManagerX.BasicModels.DataBaseInfo db = null;
			if(com.FileManagerX.Globals.Configurations.IsServer) {
				db = (com.FileManagerX.BasicModels.DataBaseInfo)com.FileManagerX.Globals.Datas.DBManager.query2(
						qcs2,
						com.FileManagerX.DataBase.Unit.DataBase
					);
				if(depot != null) {
					com.FileManagerX.Globals.Datas.DBManager.remove(db, com.FileManagerX.DataBase.Unit.DataBase);
				}
			}
			
			if(db != null) {
				com.FileManagerX.Interfaces.IDBManager dbm = com.FileManagerX.Globals.Datas.DBManagers.fetchByKey(
						db.getIndex()
					);
				dbm.disconnect();
				dbm.delete();
			}
		}
		
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public final static boolean save(com.FileManagerX.FileModels.CFG cfg) {
		java.lang.String line = "";
		cfg.getContent().add(line);
		line = "/********************************************** Del Local Depot & DataBase *******************************************/";
		cfg.getContent().add(line);
		line = "";
		cfg.getContent().add(line);
		
		line = "[Attention]: At Each Del Operation, You should delete Depot and DataBase Two Items, ";
		cfg.getContent().add(line);
		line = "             Otherwise some Errors will happen in Running.";
		cfg.getContent().add(line);
		
		line = "";
		cfg.getContent().add(line);
		line = "Example: -Depot = [DepotName1]";
		cfg.getContent().add(line);
		line = "Example: -DataBase = [DataBaseName1]";
		cfg.getContent().add(line);
		
		line = "";
		cfg.getContent().add(line);
		line = "Example: -Depot = [DepotName2]";
		cfg.getContent().add(line);
		line = "Example: -DataBase = [DataBaseName2]";
		cfg.getContent().add(line);
		
		line = "";
		cfg.getContent().add(line);
		line = "Example: -Depot = [DepotName3]";
		cfg.getContent().add(line);
		line = "Example: -DataBase = [DataBaseName3]";
		cfg.getContent().add(line);
		
		return true;
	}
	public final static boolean saveNew(com.FileManagerX.FileModels.CFG cfg) {
		return save(cfg);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
