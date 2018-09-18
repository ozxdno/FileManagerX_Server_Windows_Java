package com.FileManagerX.Tools.CFG;

public class ADepot {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static boolean load(com.FileManagerX.FileModels.CFG cfg) {
		while(true) {
			com.FileManagerX.BasicModels.Config cdepot = cfg.getConfigs().fetchByField("+Depot");
			if(cdepot == null) { break; }
			com.FileManagerX.BasicModels.Config cdb = cfg.getConfigs().fetchByField("+DataBase");
			if(cdb == null) { break; }
			
			com.FileManagerX.BasicModels.DepotInfo depotInfo = 
					com.FileManagerX.Factories.DefaultFactory.createDefaultDepotInfo();
			com.FileManagerX.BasicModels.DataBaseInfo dbInfo = depotInfo.getDBInfo();
			
			try {
				java.lang.String name = cdepot.fetchFirstString();
				if(name != null && name.length() > 0) {
					depotInfo.setName(name);
				} else {
					continue;
				}
			} catch(Exception e) {
				continue;
			}
			try {
				java.lang.String url = cdepot.fetchFirstString();
				if(url == null || url.length() == 0) {
					continue;
				}
				depotInfo.setUrl(url);
			} catch(Exception e) {
				continue;
			}
			
			try {
				java.lang.String name = cdb.fetchFirstString();
				if(name != null && name.length() > 0) {
					dbInfo.setName(name);
				} else {
					continue;
				}
			} catch(Exception e) {
				continue;
			}
			try {
				com.FileManagerX.BasicEnums.DataBaseType type = com.FileManagerX.BasicEnums.DataBaseType.valueOf(cdb.fetchFirstString());
				dbInfo.setType(type);
			} catch(Exception e) {
				dbInfo.setType(com.FileManagerX.BasicEnums.DataBaseType.TXT);
			}
			try {
				java.lang.String url = cdb.fetchFirstString();
				if(url != null && url.length() > 0) {
					dbInfo.setUrl(url);
				}
			} catch(Exception e) {
				;
			}
			
			depotInfo.setDBInfo(dbInfo);
			dbInfo.setDepotInfo(depotInfo);
			depotInfo.setDBIndex();
			dbInfo.setDepotIndex();
			
			if(com.FileManagerX.Globals.Configurations.IsServer) { loadByServer(depotInfo, dbInfo); }
		}
		
		return true;
	}
	
	private final static boolean loadByServer(com.FileManagerX.BasicModels.DepotInfo depotInfo,
			com.FileManagerX.BasicModels.DataBaseInfo dbInfo) {
		String qcs = "=2|" +
				"[&] machineInfo = " + com.FileManagerX.Globals.Configurations.This_MachineIndex + "|" +
				"[&] name = " + depotInfo.getName();
		if(com.FileManagerX.Globals.Datas.DBManager.query(qcs, null, com.FileManagerX.DataBase.Unit.Depot)) {
			return false;
		}
		
		qcs = "=2|" +
			"[&] machineInfo = " + com.FileManagerX.Globals.Configurations.This_MachineIndex + "|" +
			"[&] name = " + dbInfo.getName();
		if(com.FileManagerX.Globals.Datas.DBManager.query(qcs, null, com.FileManagerX.DataBase.Unit.DataBase)) {
			return false;
		}
		
		if(!com.FileManagerX.Globals.Datas.DBManager.update(dbInfo, com.FileManagerX.DataBase.Unit.DataBase)) {
			return false;
		}
		if(!com.FileManagerX.Globals.Datas.DBManager.update(depotInfo, com.FileManagerX.DataBase.Unit.Depot)) {
			return false;
		}
		
		depotInfo.setDBInfo(dbInfo);
		dbInfo.setDepotInfo(depotInfo);
		depotInfo.setDBIndex();
		dbInfo.setDepotIndex();
		if(!com.FileManagerX.Globals.Datas.DBManager.update(dbInfo, com.FileManagerX.DataBase.Unit.DataBase)) {
			return false;
		}
		if(!com.FileManagerX.Globals.Datas.DBManager.update(depotInfo, com.FileManagerX.DataBase.Unit.Depot)) {
			return false;
		}
		
		if(dbInfo.getType().equals(com.FileManagerX.BasicEnums.DataBaseType.TXT) &&
				dbInfo.getUrl().length() == 0) {
			com.FileManagerX.Tools.Pathes.URL dbs = com.FileManagerX.Tools.Pathes.getDBS_I(dbInfo.getIndex());
			boolean ok = dbs.createAsFolder();
			if(!ok) {
				com.FileManagerX.BasicEnums.ErrorType.COMMON_FILE_OPERATE_FAILED.register
					("Create DataBaseFolder Failed");
				return false;
			}
			dbInfo.setUrl(dbs.getAbsolute());
			if(!com.FileManagerX.Globals.Datas.DBManager.update(dbInfo, com.FileManagerX.DataBase.Unit.DataBase)) {
				com.FileManagerX.BasicEnums.ErrorType.COMMANDS_EXECUTE_FAILED.register
					("Update DataBase Url Failed", "Url = " + dbs.getAbsolute());
				return false;
			}
		}
		
		depotInfo.setDBInfo(dbInfo);
		dbInfo.setDepotInfo(depotInfo);
		depotInfo.setDBIndex();
		dbInfo.setDepotIndex();
		com.FileManagerX.Interfaces.IDBManager dbm = dbInfo.getManager();
		dbm.connect();
		com.FileManagerX.Globals.Datas.DBManagers.add(dbm);
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public final static boolean save(com.FileManagerX.FileModels.CFG cfg) {
		java.lang.String line = "";
		cfg.getContent().add(line);
		line = "/********************************************** Add Local Depot & DataBase *******************************************/";
		cfg.getContent().add(line);
		line = "";
		cfg.getContent().add(line);
		
		line = "[Attention]: At Each Add Operation, You Should add Depot and DataBase Two Items,";
		cfg.getContent().add(line);
		line = "             Otherwise add will Failed.";
		cfg.getContent().add(line);
		line = "";
		cfg.getContent().add(line);
		
		line = "Example: +Depot = [DepotName1]|[DepotFolderAbsolutePath]";
		cfg.getContent().add(line);
		line = "Example: +DataBase = [DataBaseName1]|MySQL|[LoginIP]:[LoginPort]\\[LoginName]\\[Password]\\[DataBaseNameInSQL]";
		cfg.getContent().add(line);
		line = "";
		cfg.getContent().add(line);
		line = "Example: +Depot = [DepotName2]|[DepotFolderAbsolutePath]";
		cfg.getContent().add(line);
		line = "Example: +DataBase = [DataBaseName2]|TXT|[DataBaseFolderAbsolutePath]";
		cfg.getContent().add(line);
		line = "";
		cfg.getContent().add(line);
		line = "Example: +Depot = [DepotName3]|[DepotFolderAbsolutePath]";
		cfg.getContent().add(line);
		line = "Example: +DataBase = [DataBaseName3]";
		cfg.getContent().add(line);
		
		return true;
	}
	public final static boolean saveNew(com.FileManagerX.FileModels.CFG cfg) {
		return save(cfg);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
