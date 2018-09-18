package com.FileManagerX.Tools.CFG;

public class DBManager {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public final static boolean load(com.FileManagerX.FileModels.CFG cfg) {
		com.FileManagerX.BasicModels.DepotInfo depot = 
				com.FileManagerX.Factories.ServerFactory.createServerDepotInfo();
		com.FileManagerX.BasicModels.DataBaseInfo database =
				depot.getDBInfo();
		
		try {
			com.FileManagerX.BasicModels.Config c = cfg.getConfigs().fetchByField("LocalDataBaseInfos");
			com.FileManagerX.BasicEnums.DataBaseType type = 
					com.FileManagerX.BasicEnums.DataBaseType.valueOf(c.fetchFirstString());
			database.setType(type);
			database.setUrl(c.fetchFirstString());
		}catch(Exception e) {
			database.setType(com.FileManagerX.BasicEnums.DataBaseType.TXT);
			database.setUrl("");
		}
		try {
			java.lang.String url = cfg.getConfigs().fetchByField("LocalDepotInfos").getString();
			depot.setUrl(url);
		}catch(Exception e) {
			;
		}
		
		if(database.getType().equals(com.FileManagerX.BasicEnums.DataBaseType.TXT)) {
			java.io.File dbFolder = new java.io.File(database.getUrl());
			if(!dbFolder.exists() || !dbFolder.isDirectory()) {
				com.FileManagerX.Tools.Pathes.URL dbs = com.FileManagerX.Tools.Pathes.getDBS_I(0);
				boolean ok = dbs.createAsFolder();
				if(!ok) {
					com.FileManagerX.BasicEnums.ErrorType.COMMON_FILE_OPERATE_FAILED.register(
							"Create Folder_DBS:0 Failed");
					return false;
				}
				database.setUrl(dbs.getAbsolute());
			}
		}
		
		com.FileManagerX.Globals.Datas.DBManager.setDBInfo(database);
		com.FileManagerX.Globals.Datas.DBManager.connect();
		if(!com.FileManagerX.Globals.Datas.DBManager.isConnected()) {
			com.FileManagerX.BasicEnums.ErrorType.DB_CONNECT_FAILED.register();
			return false;
		}
		
		
		
		if(com.FileManagerX.Globals.Configurations.IsServer) { updateInServer(depot, database); }
		else { updateInOthers(depot, database); }
		
		database.setDepotInfo(depot);
		depot.setDBInfo(database);
		database.setDepotIndex();
		depot.setDBIndex();
		return true;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private final static boolean updateInServer(com.FileManagerX.BasicModels.DepotInfo depot,
			com.FileManagerX.BasicModels.DataBaseInfo database) {
		boolean ok = true;
		if(com.FileManagerX.Globals.Configurations.IsAncestor) {
			ok &= com.FileManagerX.Globals.Datas.DBManager.update(
					com.FileManagerX.Globals.Datas.ThisUser,
					com.FileManagerX.DataBase.Unit.User
				);
			ok &= com.FileManagerX.Globals.Datas.DBManager.update(
					com.FileManagerX.Globals.Datas.ThisMachine,
					com.FileManagerX.DataBase.Unit.Machine
				);
			com.FileManagerX.Globals.Datas.DBManager.update(
					depot,
					com.FileManagerX.DataBase.Unit.Depot
				);
			com.FileManagerX.Globals.Datas.DBManager.update(
					database,
					com.FileManagerX.DataBase.Unit.DataBase
				);
		}
		
		return ok;
	}
	private final static boolean updateInOthers(com.FileManagerX.BasicModels.DepotInfo depot,
			com.FileManagerX.BasicModels.DataBaseInfo database) {
		
		
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public final static boolean save(com.FileManagerX.FileModels.CFG cfg) {
		String line = "";
		
		cfg.getContent().add(line);
		line = "/*************************************************** Local Manager ***************************************************/";
		cfg.getContent().add(line);
		line = "";
		cfg.getContent().add(line);
		
		line = "Example: LocalDataBaseInfos = [DataBaseType]|[Url]";
		cfg.getContent().add(line);
		line = "Example: LocalDepotInfos = [Url]";
		cfg.getContent().add(line);
		line = "";
		cfg.getContent().add(line);
		
		if(com.FileManagerX.Globals.Datas.DBManager.getDBInfo() == null) { return true; }
		
		line = "LocalDataBaseInfos = " + com.FileManagerX.Globals.Datas.DBManager.getDBInfo().getType().toString() +
				"|" +
				com.FileManagerX.Globals.Datas.DBManager.getDBInfo().getUrl();
		cfg.getContent().add(line);
		line = "LocalDepotInfos = " + com.FileManagerX.Globals.Datas.DBManager.getDBInfo().getDepotInfo().getUrl();
		cfg.getContent().add(line);
		line = "";
		cfg.getContent().add(line);
		
		return true;
	}
	public final static boolean saveNew(com.FileManagerX.FileModels.CFG cfg) {
		String line = "";
		
		cfg.getContent().add(line);
		line = "/*************************************************** Local Manager ***************************************************/";
		cfg.getContent().add(line);
		line = "";
		cfg.getContent().add(line);
		
		line = "Example: LocalDataBaseInfos = [DataBaseType]|[Url]";
		cfg.getContent().add(line);
		line = "         LocalDepotInfos = [Url]";
		cfg.getContent().add(line);
		line = "";
		cfg.getContent().add(line);
		
		return true;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
