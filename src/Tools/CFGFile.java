package Tools;

public class CFGFile {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public final static boolean createCFG() {
		java.io.File f = new java.io.File(Tools.Pathes.getFile_CFG());
		if(f.exists()) {
			return true;
		}
		try {
			return f.createNewFile();
		} catch(Exception e) {
			BasicEnums.ErrorType.COMMON_FILE_OPERATE_FAILED.register(e.toString());
			return false;
		}
	}
	public final static boolean loadCFG() {
		FileModels.Text txt = new FileModels.Text(Tools.Pathes.getFile_CFG());
		txt.load(true);
		BasicCollections.Configs cs = txt.toConfigs();
		
		BasicModels.Config c = cs.fetch("StartType");
		if(c == null) {
			BasicEnums.ErrorType.CFG_NOT_EXIST.register("Not Exist StartType");
			return false;
		}
		BasicEnums.StartType type = BasicEnums.StartType.Server;
		try {
			type = BasicEnums.StartType.valueOf(c.fetchFirstString());
		} catch(Exception e) {
			BasicEnums.ErrorType.CFG_WRONG_CONTENT.register("Wrong StartType, No such StartType: " + c.getValue());
			return false;
		}
		// 显示窗体
		try {
			for(int i=0; i<c.getItems().length; i++) {
				if(c.getString(i).equals("ShowForm")) {
					Globals.Configurations.ShowForm = true;
					Globals.Datas.Form_Main.setVisible(true);
					break;
				}
			}
		} catch(Exception e) {
			;
		}
		
		Globals.Configurations.StartType = type;
		Globals.Configurations.IsServer = type.equals(BasicEnums.StartType.Server);
		Globals.Configurations.IsDepot = type.equals(BasicEnums.StartType.Depot);
		Globals.Configurations.IsClient = type.equals(BasicEnums.StartType.Client);
		
		// 创建新文件
		try {
			for(int i=0; i<c.getItems().length; i++) {
				if(c.getString(i).equals("New")) {
					Tools.CFGFile.saveCFGCore(type, true);
					return false;
				}
			}
		} catch(Exception e) {
			;
		}
		
		boolean ok = true;
		if(type.equals(BasicEnums.StartType.Server)) {
			ok &= loadServerCFG(cs);
		}
		if(type.equals(BasicEnums.StartType.Depot)) {
			ok &= loadDepotCFG(cs);
		}
		if(type.equals(BasicEnums.StartType.Client)) {
			ok &= loadClientCFG(cs);
		}
		
		try {
			for(int i=0; i<c.getItems().length; i++) {
				if(c.getString(i).equals("Reset")) {
					ok &= resetCFG();
					ok = false;
					break;
				}
			}
		} catch(Exception e) {
			;
		}
		
		// 网络类型
		try {
			if(Globals.Configurations.IsServer) {
				c = cs.fetch("NetType");
				Globals.Configurations.NetType = BasicEnums.NetType.valueOf(c.fetchFirstString());
			}
		} catch(Exception e) {
			;
		}
		
		return ok;
	}
	public final static boolean saveCFG() {
		if(Globals.Configurations.StartType.equals(BasicEnums.StartType.Server)) {
			return saveServerCFG();
		}
		if(Globals.Configurations.StartType.equals(BasicEnums.StartType.Depot)) {
			return saveDepotCFG();
		}
		if(Globals.Configurations.StartType.equals(BasicEnums.StartType.Client)) {
			return saveClientCFG();
		}
		
		return false;
	}
	public final static boolean resetCFG() {
		if(Globals.Configurations.StartType.equals(BasicEnums.StartType.Server)) {
			return resetServerCFG();
		}
		if(Globals.Configurations.StartType.equals(BasicEnums.StartType.Depot)) {
			return resetDepotCFG();
		}
		if(Globals.Configurations.StartType.equals(BasicEnums.StartType.Client)) {
			return resetClientCFG();
		}
		return false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final static boolean loadServerCFG(BasicCollections.Configs cs) {

		// Configurations
		// All the index you not set will be preset to 0.
		// You should not change it.
		// This_MachineIndex equals to Server_MachineIndex
		// This_UserIndex equals to Server_UserIndex
		// if you set not same value, the process will exit.
		// if you set wrong value, it will copy which is right.
		if(true) {
			try {
				Globals.Configurations.Next_MachineIndex = cs.fetch("Next_MachineIndex").getLong();
			}catch(Exception e) {
				Globals.Configurations.Next_MachineIndex = 0;
			}
			try {
				Globals.Configurations.Next_DepotIndex = cs.fetch("Next_DepotIndex").getLong();
			}catch(Exception e) {
				Globals.Configurations.Next_DepotIndex = 0;
			}
			try {
				Globals.Configurations.Next_DataBaseIndex = cs.fetch("Next_DataBaseIndex").getLong();
			}catch(Exception e) {
				Globals.Configurations.Next_DataBaseIndex = 0;
			}
			try {
				Globals.Configurations.Next_FileIndex = cs.fetch("Next_FileIndex").getLong();
			}catch(Exception e) {
				Globals.Configurations.Next_FileIndex = 0;
			}
			try {
				Globals.Configurations.Next_UserIndex = cs.fetch("Next_UserIndex").getLong();
			}catch(Exception e) {
				Globals.Configurations.Next_UserIndex = 0;
			}
			
			try {
				Globals.Configurations.This_MachineIndex = cs.fetch("This_MachineIndex").getLong();
			}catch(Exception e) {
				Globals.Configurations.This_MachineIndex = 0;
			}
			try {
				Globals.Configurations.This_UserIndex = cs.fetch("This_UserIndex").getLong();
			}catch(Exception e) {
				Globals.Configurations.This_UserIndex = 0;
			}
			
			try {
				Globals.Configurations.Server_MachineIndex = cs.fetch("Server_MachineIndex").getLong();
			}catch(Exception e) {
				Globals.Configurations.Server_MachineIndex = 0;
			}
			try {
				Globals.Configurations.Server_UserIndex = cs.fetch("Server_UserIndex").getLong();
			}catch(Exception e) {
				Globals.Configurations.Server_UserIndex = 0;
			}
			
			if(Globals.Configurations.Server_MachineIndex <= 0 && Globals.Configurations.This_MachineIndex > 0) {
				Globals.Configurations.Server_MachineIndex = Globals.Configurations.This_MachineIndex;
			}
			if(Globals.Configurations.Server_MachineIndex > 0 && Globals.Configurations.This_MachineIndex <= 0) {
				Globals.Configurations.This_MachineIndex = Globals.Configurations.Server_MachineIndex;
			}
			if(Globals.Configurations.Server_MachineIndex != Globals.Configurations.This_MachineIndex) {
				BasicEnums.ErrorType.CFG_NOT_EQUAL.register(
						"This_MachineIndex not equals to Server_MachineIndex, " +
						"This_MachineIndex and Server_MachineIndex must have same value or you can remain it blank."
						);
				return false;
			}
			
			if(Globals.Configurations.Server_UserIndex <=0 && Globals.Configurations.This_UserIndex > 0) {
				Globals.Configurations.This_UserIndex = Globals.Configurations.Server_UserIndex;
			}
			if(Globals.Configurations.Server_UserIndex > 0 && Globals.Configurations.This_UserIndex <= 0) {
				Globals.Configurations.Server_UserIndex = Globals.Configurations.This_UserIndex;
			}
			if(Globals.Configurations.Server_UserIndex != Globals.Configurations.This_UserIndex) {
				BasicEnums.ErrorType.CFG_NOT_EQUAL.register(
						"This_UserIndex not equals to Server_UserIndex, " + 
						"This_UserIndex and Server_UserIndex must have same value or you can remain it blank."
						);
				return false;
			}
		}
		
		// machine info
		// if machine index is wrong, it set to 0.
		// if the Index of machine <= 0, it will register a new machine.
		// You should not change index of machine.
		// You change other info of machine is OK, it will update to database.
		// if you changed machine index, you cannot find the depots and databases of this machine.
		// many items have default value.
		// server has default index: 1.
		if(true) {
			BasicModels.MachineInfo machine = Factories.DefaultFactory.createDefaultMachineInfo();
			try {
				long idx = cs.fetch("MachineIndex").getLong();
				if(idx > 0) {
					machine.setIndex(idx);
				}
			}catch(Exception e) {
				;
			}
			try {
				java.lang.String name = cs.fetch("MachineName").getValue();
				if(name != null && name.length() != 0) {
					machine.setName(name);
				}
			}catch(Exception e) {
				;
			}
			try {
				java.lang.String ip = cs.fetch("MachineIP").getValue();
				if(Tools.Url.isIp(ip)) {
					machine.setIp(ip);
				}
			}catch(Exception e) {
				;
			}
			try {
				int port = cs.fetch("MachinePort").getInt();
				if(port > 0 && port < 65536) {
					machine.setPort(port);
				}
			}catch(Exception e) {
				;
			}
			
			Globals.Datas.ThisMachine.copyReference(machine);
			Globals.Datas.ServerMachine.copyReference(machine);
		}
		
		// Create Server DataBase
		// if the content of ServerDepotIndex or ServerDataBaseIndex is wrong, it will set to 0.
		// if ServerDepotIndex or ServerDataBaseIndex is wrong, it will set to 1.
		// you change other info is OK, it will update to database.
		// you should not change ServerDepotIndex and ServerDataBaseIndex.
		// ServerDepotIndex default set is 1.
		// ServerDataBaseIndex default set is 1.
		if(true) {
			BasicModels.DepotInfo depotInfo = Factories.DefaultFactory.createDefaultDepotInfo();
			BasicModels.DataBaseInfo dbInfo = Factories.DefaultFactory.createDefaultDataBaseInfo();
			
			try {
				long idx = cs.fetch("ServerDepotIndex").getLong();
				if(idx > 0) {
					depotInfo.setIndex(idx);
				}
			}catch(Exception e) {
				;
			}
			try {
				java.lang.String name = cs.fetch("ServerDepotName").getString();
				if(name != null && name.length() > 0) {
					depotInfo.setName(name);
				}
			}catch(Exception e) {
				;
			}
			try {
				java.lang.String url = cs.fetch("ServerDepotUrl").getString();
				depotInfo.setUrl(url);
			}catch(Exception e) {
				;
			}
			
			try {
				long idx = cs.fetch("ServerDataBaseIndex").getLong();
				if(idx > 0) {
					dbInfo.setIndex(idx);
				}
			}catch(Exception e) {
				;
			}
			try {
				java.lang.String name = cs.fetch("ServerDataBaseName").getString();
				if(name != null && name.length() > 0) {
					dbInfo.setName(name);
				}
			}catch(Exception e) {
				;
			}
			try {
				BasicEnums.DataBaseType type = BasicEnums.DataBaseType.valueOf(cs.fetch("ServerDataBaseType").getString());
				dbInfo.setType(type);
			}catch(Exception e) {
				dbInfo.setType(BasicEnums.DataBaseType.TXT);
			}
			try {
				java.lang.String url = cs.fetch("ServerDataBaseUrl").getString();
				if(url != null && url.length() != 0) {
					dbInfo.setUrl(url);
				}
			}catch(Exception e) {
				;
			}
			
			if(dbInfo.getType().equals(BasicEnums.DataBaseType.TXT)) {
				java.io.File dbFolder = new java.io.File(dbInfo.getUrl());
				if(!dbFolder.exists() || !dbFolder.isDirectory()) {
					java.lang.String url = Tools.Pathes.getFolder_DBS(0);
					boolean ok = Tools.Pathes.createFolder_DBS(0);
					if(!ok) {
						BasicEnums.ErrorType.COMMON_FILE_OPERATE_FAILED.register("Create Folder_DBS:0 Failed");
						return false;
					}
					dbInfo.setUrl(url);
				}
			}
			
			depotInfo.setDBInfo(dbInfo);
			dbInfo.setDepotInfo(depotInfo);
			depotInfo.setDBIndex();
			dbInfo.setDepotIndex();
			
			Globals.Datas.DBManager.initialize(dbInfo);
			Globals.Datas.DBManager.connect();
			if(!Globals.Datas.DBManager.isConnected()) {
				BasicEnums.ErrorType.DB_CONNECT_FAILED.register();
				return false;
			}
			
			Globals.Datas.DBManager.createServerTables();
			Globals.Datas.DBManager.updataMachineInfo(Globals.Datas.ThisMachine);
			Globals.Datas.DBManager.updataDepotInfo(depotInfo);
			Globals.Datas.DBManager.updataDataBaseInfo(dbInfo);
			
			//Globals.Datas.ThisMachine.copyReference(Globals.Datas.ThisMachine);
			Globals.Datas.ServerMachine.copyReference(Globals.Datas.ThisMachine);
			Globals.Configurations.This_MachineIndex = Globals.Datas.ThisMachine.getIndex();
			Globals.Configurations.Server_MachineIndex = Globals.Configurations.This_MachineIndex;
			
			// if database still remain, but you reset the index of machine/depot/database.
			// then the index in Configurations is invalid.
			// we should report this error.
			if(Globals.Configurations.Next_MachineIndex < Globals.Configurations.This_MachineIndex) {
				BasicEnums.ErrorType.CFG_WRONG_CONTENT.register("You should clear Server DataBase");
				return false;
			}
			if(Globals.Configurations.Next_DepotIndex < depotInfo.getIndex()) {
				BasicEnums.ErrorType.CFG_WRONG_CONTENT.register("You should clear Server DataBase");
				return false;
			}
			if(Globals.Configurations.Next_DataBaseIndex < dbInfo.getIndex()) {
				BasicEnums.ErrorType.CFG_WRONG_CONTENT.register("You should clear Server DataBase");
				return false;
			}
		}
		
		// user info
		// in CFG file, it only record two items: This_UserIndex and Server_UserIndex, them have same value.
		// it has default value: 1.
		// if you not set user index or it is wrong, it will create a default one as code shows.
		// You should not change This_UserIndex and Server_UserIndex.
		if(true) {
			BasicModels.User user = Factories.DefaultFactory.createDefaultUser();
			
			if(Globals.Configurations.This_UserIndex <= 0) {
				Globals.Datas.DBManager.updataUser(user);
			} else {
				DataBaseManager.QueryCondition qc = new DataBaseManager.QueryCondition();
				qc.setSign(DataBaseManager.Sign.EQUAL);
				qc.setItemName("Index");
				qc.setValue(java.lang.String.valueOf(Globals.Configurations.This_UserIndex));
				BasicModels.User user2 = Globals.Datas.DBManager.QueryUser(qc);
				if(user2 != null) {
					user = user2;
				} else {
					Globals.Datas.DBManager.updataUser(user);
				}
			}
			
			Globals.Datas.ThisUser.copyReference(user);
			Globals.Datas.ServerUser.copyReference(user);
			Globals.Configurations.This_UserIndex = Globals.Datas.ThisUser.getIndex();
			Globals.Configurations.Server_UserIndex = Globals.Configurations.This_UserIndex;
			
			// if database still remain, but you reset the index of user.
			// then the index in Configurations is invalid.
			// we should report this error.
			if(Globals.Configurations.Next_UserIndex < Globals.Configurations.This_UserIndex) {
				BasicEnums.ErrorType.CFG_WRONG_CONTENT.register("You should clear Server DataBase");
				return false;
			}
		}
		
		// supports
		// you can add or delete them at any time, it will reload at server start.
		while(true) {
			BasicModels.Config c = cs.fetch("Support");
			if(c == null) {
				break;
			}
			try {
				BasicModels.Support s = new BasicModels.Support();
				s.setType(BasicEnums.FileType.valueOf(c.fetchFirstString()));
				if(!c.getIsOK()) { continue; }
				s.setExtension(c.fetchFirstString());
				if(!c.getIsOK()) { continue; }
				s.setShowExtension(new java.lang.String(s.getExtension()));
				if(!c.getIsOK()) { continue; }
				s.setHideExtension(c.fetchFirstString());
				if(!c.getIsOK()) { continue; }
				s.setIsSupport(true);
				Globals.Datas.Supports.add(s);
				
				BasicModels.Support s2 = new BasicModels.Support();
				s2.setExtension(new java.lang.String(s.getHideExtension()));
				s2.setType(s.getType());
				s2.setShowExtension(new java.lang.String(s.getShowExtension()));
				s2.setHideExtension(new java.lang.String(s.getHideExtension()));
				s2.setIsSupport(false);
				if(s2.getExtension() != null && s2.getExtension().length() > 0) {
					Globals.Datas.Supports.add(s2);
				}
			} catch(Exception e) {
				BasicEnums.ErrorType.OTHERS.register(e.toString());
			}
		}
		
		// LAN Machines
		// you can add or delete it at any time, it will reload at server start.
		while(true) {
			BasicModels.Config c = cs.fetch("LANMachineIndex");
			if(c == null) {
				break;
			}
			
			long idx = c.getLong();
			if(c.getIsOK()) {
				Globals.Datas.LANMachineIndexes.add(idx);
			}
		}
		
		// load all local depots & databases
		// all depots & databases get from machine index, if index is wrong, load will failed.
		// so You should Not change machine index.
		// machine index: This_MachineIndex, Server_MachineIndex(them are equal).
		if(true) {
			DataBaseManager.QueryCondition qc = new DataBaseManager.QueryCondition();
			qc.setItemName("MachineIndex");
			qc.setValue(java.lang.String.valueOf(Globals.Configurations.This_MachineIndex));
			
			BasicCollections.DataBaseInfos dbs = Globals.Datas.DBManager.QueryDataBaseInfos(qc);
			if(dbs != null) {
				for(int i=0; i<dbs.getContent().size(); i++) {
					BasicModels.DataBaseInfo db = dbs.getContent().get(i);
					if(db.getIndex() != Globals.Datas.DBManager.getDBInfo().getIndex()) {
						qc.setItemName("Index");
						qc.setValue(java.lang.String.valueOf(db.getDepotIndex()));
						BasicModels.DepotInfo depot = Globals.Datas.DBManager.QueryDepotInfo(qc);
						
						qc.setValue(java.lang.String.valueOf(db.getMachineInfo().getIndex()));
						BasicModels.MachineInfo machine = Globals.Datas.DBManager.QueryMachineInfo(qc);
						
						db.setDepotInfo(depot);
						depot.setDBInfo(db);
						db.setMachineInfo(machine);
						depot.setMachineInfo(machine);
						Interfaces.IDBManager dbmanager = db.getManager();
						dbmanager.connect();
						Globals.Datas.DBManagers.add(dbmanager);
					}
				}
			}
		}
		
		// add database & depot
		// you can add database and depot at any time, it will reload at server start. 
		// you should make sure that Depot and DataBase is a couple, otherwise it will make mistake.
		// couple means the +Depot and +DataBase of same depot should write one first and another second.
		while(true) {
			BasicModels.Config cdepot = cs.fetch("+Depot");
			if(cdepot == null) {
				break;
			}
			BasicModels.Config cdb = cs.fetch("+DataBase");
			if(cdb == null) {
				break;
			}
			
			BasicModels.DepotInfo depotInfo = Factories.DefaultFactory.createDefaultDepotInfo();
			BasicModels.DataBaseInfo dbInfo = depotInfo.getDBInfo();
			
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
				BasicEnums.DataBaseType type = BasicEnums.DataBaseType.valueOf(cdb.fetchFirstString());
				dbInfo.setType(type);
			} catch(Exception e) {
				dbInfo.setType(BasicEnums.DataBaseType.TXT);
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
			
			DataBaseManager.QueryCondition qc1 = new DataBaseManager.QueryCondition();
			qc1.setItemName("Name");
			qc1.setValue("'" + depotInfo.getName() + "'");
			DataBaseManager.QueryCondition qc2 = new DataBaseManager.QueryCondition();
			qc2.setItemName("MachineIndex");
			qc2.setValue("" + Globals.Configurations.Server_MachineIndex);
			DataBaseManager.QueryConditions qcs = new DataBaseManager.QueryConditions();
			qcs.add(qc1);
			qcs.add(qc2);
			
			if(Globals.Datas.DBManager.QueryDepotInfo(qcs) != null) {
				continue;
			}
			
			qcs.getContent().get(0).setValue("'" + dbInfo.getName() + "'");
			if(Globals.Datas.DBManager.QueryDataBaseInfo(qcs) != null) {
				continue;
			}
			
			if(!Globals.Datas.DBManager.updataDataBaseInfo(dbInfo)) {
				continue;
			}
			if(!Globals.Datas.DBManager.updataDepotInfo(depotInfo)) {
				continue;
			}
			
			if(dbInfo.getType().equals(BasicEnums.DataBaseType.TXT) && dbInfo.getUrl().length() == 0) {
				java.lang.String url = Tools.Pathes.getFolder_DBS(dbInfo.getIndex());
				boolean ok = Tools.Pathes.createFolder_DBS(dbInfo.getIndex());
				if(!ok) {
					BasicEnums.ErrorType.COMMON_FILE_OPERATE_FAILED.register("Create DataBaseFolder Failed");
					continue;
				}
				dbInfo.setUrl(url);
				if(!Globals.Datas.DBManager.updataDataBaseInfo(dbInfo)) {
					BasicEnums.ErrorType.COMMANDS_EXECUTE_FAILED.register("Update DataBase Url Failed", "Url = " + url);
					continue;
				}
			}
			
			BasicModels.Folder f = new BasicModels.Folder(depotInfo.getUrl());
			long currentFileIndex = Globals.Configurations.Next_FileIndex;
			f.setIndex();
			f.setMachine(Globals.Configurations.This_MachineIndex);
			f.setDepot(depotInfo.getIndex());
			f.setDataBase(dbInfo.getIndex());
			f.load();
			Globals.Configurations.Next_FileIndex = currentFileIndex;
			BasicCollections.BaseFiles files = f.getAllSubs(f.getUrl());
			files.sortIncrease();
			
			Interfaces.IDBManager dbmanager = dbInfo.getManager();
			Globals.Datas.DBManagers.add(dbmanager);
			dbmanager.connect();
			if(dbmanager.isConnected()) {
				dbmanager.deleteDepotTables();
				dbmanager.createDataBase();
				dbmanager.createDepotTables();
				if(!dbmanager.updataFolder(f)) {
					continue;
				}
				if(!dbmanager.updataFiles(files)) {
					continue;
				}
			}
		}
		
		// delete database & depot
		// you can delete database and depot at any time, it will reload at server start. 
		// you should make sure that Depot and DataBase is a couple, otherwise it will make mistake.
		// couple means the -Depot and -DataBase of same depot should write one first and another second.
		while(true) {
			BasicModels.Config cdepot = cs.fetch("-Depot");
			if(cdepot == null) {
				break;
			}
			BasicModels.Config cdb = cs.fetch("-DataBase");
			if(cdb == null) {
				break;
			}
			
			DataBaseManager.QueryCondition qc1 = new DataBaseManager.QueryCondition();
			qc1.setItemName("Name");
			qc1.setValue("'" + cdepot.getValue() + "'");
			DataBaseManager.QueryCondition qc2 = new DataBaseManager.QueryCondition();
			qc2.setItemName("MachineIndex");
			qc2.setValue("" + Globals.Configurations.Server_MachineIndex);
			DataBaseManager.QueryConditions qcs = new DataBaseManager.QueryConditions();
			qcs.add(qc1);
			qcs.add(qc2);
			
			BasicModels.DepotInfo depotInfo = Globals.Datas.DBManager.QueryDepotInfo(qcs);
			if(depotInfo == null) {
				continue;
			}
			
			qcs.getContent().get(0).setValue("'" + cdb.getValue() + "'");
			BasicModels.DataBaseInfo dbInfo = Globals.Datas.DBManager.QueryDataBaseInfo(qcs);
			if(dbInfo == null) {
				continue;
			}
			
			if(dbInfo.getDepotIndex() != depotInfo.getIndex()) {
				continue;
			}
			
			Globals.Datas.DBManager.removeDepotInfo(depotInfo);
			Globals.Datas.DBManager.removeDataBaseInfo(dbInfo);
			Interfaces.IDBManager dbm = Globals.Datas.DBManagers.searchDepotIndex(depotInfo.getIndex());
			if(dbm != null) {
				dbm.deleteDepotTables();
				dbm.deleteDataBase();
			}
			Globals.Datas.DBManagers.deleteDataBaseName(dbInfo.getName());
		}
		
		// all info load finished, return right operate.
		return true;
	}
	private final static boolean saveServerCFG() {
		return CFGFile.saveCFGCore(BasicEnums.StartType.Server, false);
	}
	private final static boolean resetServerCFG() {
		// delete all depots
		for(Interfaces.IDBManager dbm : Globals.Datas.DBManagers.getContent()) {
			dbm.deleteDepotTables();
			dbm.deleteDataBase();
		}
		
		// delete server
		Globals.Datas.DBManager.deleteServerTables();
		
		// clear Indexes
		Globals.Configurations.Next_FileIndex = 0;
		Globals.Configurations.Next_UserIndex = 0;
		Globals.Configurations.Next_MachineIndex = 0;
		Globals.Configurations.Next_DepotIndex = 0;
		Globals.Configurations.Next_DataBaseIndex = 0;
		Globals.Configurations.This_MachineIndex = 0;
		Globals.Configurations.This_UserIndex = 0;
		Globals.Configurations.Server_MachineIndex = 0;
		Globals.Configurations.Server_UserIndex = 0;
		
		Globals.Datas.ThisMachine.setIndex(0);
		Globals.Datas.ThisUser.setIndex(0);
		Globals.Datas.ServerMachine.setIndex(0);
		Globals.Datas.ServerUser.setIndex(0);
		
		if(Globals.Datas.DBManager.getDBInfo() != null) {
			Globals.Datas.DBManager.getDBInfo().setIndex(0);
			Globals.Datas.DBManager.getDBInfo().setDepotIndex(0);
			if(Globals.Datas.DBManager.getDBInfo().getDepotInfo() != null) {
				Globals.Datas.DBManager.getDBInfo().getDepotInfo().setIndex(0);
				Globals.Datas.DBManager.getDBInfo().getDepotInfo().setDBIndex(0);
			}
		}
		
		// save to file
		return saveServerCFG();
	}
	
	private final static boolean loadDepotCFG(BasicCollections.Configs cs) {
		
		// Configurations
		// All the index you not set will be preset to 0.
		// You should not change it.
		// This_MachineIndex equals to Server_MachineIndex
		// This_UserIndex equals to Server_UserIndex
		// if you set not same value, the process will exit.
		// if you set wrong value, it will copy which is right.
		if(true) {
			try {
				Globals.Configurations.Next_MachineIndex = cs.fetch("Next_MachineIndex").getLong();
			}catch(Exception e) {
				Globals.Configurations.Next_MachineIndex = 0;
			}
			try {
				Globals.Configurations.Next_DepotIndex = cs.fetch("Next_DepotIndex").getLong();
			}catch(Exception e) {
				Globals.Configurations.Next_DepotIndex = 0;
			}
			try {
				Globals.Configurations.Next_DataBaseIndex = cs.fetch("Next_DataBaseIndex").getLong();
			}catch(Exception e) {
				Globals.Configurations.Next_DataBaseIndex = 0;
			}
			try {
				Globals.Configurations.Next_FileIndex = cs.fetch("Next_FileIndex").getLong();
			}catch(Exception e) {
				Globals.Configurations.Next_FileIndex = 0;
			}
			try {
				Globals.Configurations.Next_UserIndex = cs.fetch("Next_UserIndex").getLong();
			}catch(Exception e) {
				Globals.Configurations.Next_UserIndex = 0;
			}
			
			try {
				Globals.Configurations.This_MachineIndex = cs.fetch("This_MachineIndex").getLong();
			}catch(Exception e) {
				Globals.Configurations.This_MachineIndex = 0;
			}
			try {
				Globals.Configurations.This_UserIndex = cs.fetch("This_UserIndex").getLong();
			}catch(Exception e) {
				Globals.Configurations.This_UserIndex = 0;
			}
			
			try {
				Globals.Configurations.Server_MachineIndex = cs.fetch("Server_MachineIndex").getLong();
			}catch(Exception e) {
				Globals.Configurations.Server_MachineIndex = 0;
			}
			try {
				Globals.Configurations.Server_UserIndex = cs.fetch("Server_UserIndex").getLong();
			}catch(Exception e) {
				Globals.Configurations.Server_UserIndex = 0;
			}
		}
		
		// Prepare Word
		// SWRE contains the full process of:
		// send,
		// wait,
		// receive,
		// execute
		Interfaces.ISWRE swre = Factories.CommunicatorFactory.createSWRE();
		DataBaseManager.QueryConditions dbqcs = new DataBaseManager.QueryConditions();
		BasicModels.Config c = null;
		
		// Server Machine Info
		// each item of ServerMachineInfo is very important except ServerMachineName.
		// any wrong item will cause process crash except ServerMachineName.
		// You should not change ServerMachineInfo except ServerMachineName.
		// the change of ServerMachineName is useless.
		// more: ServerMachineIndex like ServerMachineName is not so important.
		// in all:
		// You can change: ServerMachineIndex, ServerMachineName
		// You cannot change: ServerMachineIP, ServerMachinePort
		if(true) {
			BasicModels.MachineInfo machine = new BasicModels.MachineInfo();
			machine.setIndex(Globals.Configurations.Server_MachineIndex);
			
			c = cs.fetch("ServerMachineIndex");
			if(c != null) {
				long idx = c.getLong();
				if(c.getIsOK()) {
					machine.setIndex(idx);
				}
			}
			
			c = cs.fetch("ServerMachineIP");
			if(c == null) {
				BasicEnums.ErrorType.CFG_NOT_EXIST.register("Not Find: ServerMachineIP");
				return false;
			}
			java.lang.String ip = c.getValue();
			if(!Tools.Url.isIp(ip)) {
				BasicEnums.ErrorType.CFG_WRONG_CONTENT.register("the Value of ServerMachineIP is wrong");
				return false;
			}
			machine.setIp(ip);
			
			c = cs.fetch("ServerMachinePort");
			if(c == null) {
				BasicEnums.ErrorType.CFG_NOT_EXIST.register("Not Find: ServerMachinePort");
				return false;
			}
			int port = c.getInt();
			if(port <=0 || port > 65535) {
				BasicEnums.ErrorType.CFG_WRONG_CONTENT.register("the Value of ServerMachinePort is wrong");
				return false;
			}
			machine.setPort(port);
			
			if(machine.getIndex() != Globals.Configurations.Server_MachineIndex) {
				BasicEnums.ErrorType.CFG_NOT_EQUAL.register("ServerMachineIndex Not Equals to Server_MachineIndex");
				return false;
			}
			Globals.Datas.ServerMachine.copyReference(machine);
		}
		
		// This Machine Info
		// You should Not change MachineIndex.
		// if MachineIndex is wrong, you cannot query right depots and databases.
		// if MachineIndex is wrong, This Machine Info will register to database.
		// all items of This Machine Info have default values.
		if(true) {
			BasicModels.MachineInfo machine = Factories.DefaultFactory.createDefaultMachineInfo();
			machine.setIndex(Globals.Configurations.This_MachineIndex);
			
			try {
				c = cs.fetch("MachineIndex");
				if(c != null) {
					long idx = c.getLong();
					if(c.getIsOK()) {
						machine.setIndex(idx);
					}
				}
			}catch(Exception e) {
				;
			}
			try {
				java.lang.String name = cs.fetch("MachineName").getValue();
				if(name != null && name.length() > 0) {
					machine.setName(name);
				}
			}catch(Exception e) {
				;
			}
			try {
				java.lang.String ip = cs.fetch("MachineIP").getValue();
				if(Tools.Url.isIp(ip)) {
					machine.setIp(ip);
				}
			}catch(Exception e) {
				;
			}
			try {
				int port = cs.fetch("MachinePort").getInt();
				if(port > 0 && port < 65536) {
					machine.setPort(port);
				}
			}catch(Exception e) {
				;
			}
			
			if(machine.getIndex() != Globals.Configurations.This_MachineIndex) {
				BasicEnums.ErrorType.CFG_NOT_EQUAL.register("MachineIndex Not Equals to This_MachineIndex");
			}
			Globals.Datas.ThisMachine.copyReference(machine);
		}
		
		// User Info
		// UserLoginName and UserPassword is necessary for process to connect Server.
		// any wrong of them will cause process exit.
		// because any wrong will cause cannot connect to Server.
		if(true) {
			BasicModels.User user = Factories.DefaultFactory.createDefaultUser();
			try {
				java.lang.String name = cs.fetch("UserLoginName").getValue();
				if(name != null && name.length() > 0) {
					user.setLoginName(name);
				}
			} catch(Exception e) {
				;
			}
			try {
				java.lang.String pw = cs.fetch("UserPassword").getValue();
				if(pw != null && pw.length() > 0) {
					user.setPassword(pw);
				}
			} catch(Exception e) {
				;
			}
			
			Globals.Datas.ThisUser.copyReference(user);
			Globals.Datas.ThisUser.setIndex(Globals.Configurations.This_UserIndex);
		}
		
		// Build ServerConnection
		// if build failed, process break.
		if(true) {
			Globals.Datas.ServerConnection.setServerMachineInfo(Globals.Datas.ServerMachine);
			Globals.Datas.ServerConnection.setClientMachineInfo(Globals.Datas.ThisMachine);
			Globals.Datas.ServerConnection.setSocket();
			Globals.Datas.ServerConnection.connect();
			if(!Globals.Datas.ServerConnection.isRunning()) {
				return false;
			}
			
			Globals.Datas.ServerConnection.setUser(Globals.Datas.ThisUser);
			Globals.Datas.ServerConnection.setType(BasicEnums.ConnectionType.TRANSPORT_COMMAND);
			
			boolean ok = Globals.Datas.ServerConnection.getCommandsManager().loginConnection();
			if(!ok) {
				BasicEnums.ErrorType.COMMANDS_EXECUTE_FAILED.register("loginConnection Failed");
				return false;
			}
			Globals.Datas.ThisMachine.copyValue(Globals.Datas.ServerConnection.getClientMachineInfo());
			Globals.Datas.ThisUser.copyValue(Globals.Datas.ServerConnection.getUser());
			Globals.Configurations.This_MachineIndex = Globals.Datas.ThisMachine.getIndex();
			Globals.Configurations.This_UserIndex = Globals.Datas.ThisUser.getIndex();
			
			Replies.QueryConfigurations repqc = Globals.Datas.ServerConnection.getCommandsManager().queryConfigurations();
			if(repqc == null) {
				BasicEnums.ErrorType.COMMANDS_EXECUTE_FAILED.register("queryConfigurations Failed");
				return false;
			}
			Globals.Configurations.Server_MachineIndex = repqc.getServer_MachineIndex();
			Globals.Configurations.Server_UserIndex = repqc.getServer_UserIndex();
			Globals.Configurations.NetType = repqc.getNetType();
			
			BasicModels.MachineInfo sm = Globals.Datas.ServerConnection.getCommandsManager().queryMachine(
					"[&] Index = " + Globals.Configurations.Server_MachineIndex);
			if(sm == null) {
				BasicEnums.ErrorType.COMMANDS_EXECUTE_FAILED.register("qeuryMachine Failed");
				return false;
			}
			Globals.Datas.ServerMachine.copyValue(sm);
			
			BasicModels.User user = Globals.Datas.ServerConnection.getCommandsManager().queryUser(
					"[&] Index = " + Globals.Configurations.Server_UserIndex);
			if(user == null) {
				BasicEnums.ErrorType.COMMANDS_EXECUTE_FAILED.register("qeuryUser Failed");
				return false;
			}
			Globals.Datas.ServerUser.copyValue(user);
		}
		
		// Supports
		// you can add or delete it at any time, it will reload at next start process.
		while(true) {
			c = cs.fetch("Support");
			if(c == null) {
				break;
			}
			try {
				BasicModels.Support s = new BasicModels.Support();
				s.setType(BasicEnums.FileType.valueOf(c.fetchFirstString()));
				if(!c.getIsOK()) { continue; }
				s.setExtension(c.fetchFirstString());
				if(!c.getIsOK()) { continue; }
				s.setShowExtension(new java.lang.String(s.getExtension()));
				if(!c.getIsOK()) { continue; }
				s.setHideExtension(c.fetchFirstString());
				if(!c.getIsOK()) { continue; }
				s.setIsSupport(true);
				Globals.Datas.Supports.add(s);
				
				BasicModels.Support s2 = new BasicModels.Support();
				s2.setExtension(new java.lang.String(s.getHideExtension()));
				s2.setType(s.getType());
				s2.setShowExtension(new java.lang.String(s.getShowExtension()));
				s2.setHideExtension(new java.lang.String(s.getHideExtension()));
				s2.setIsSupport(false);
				if(s2.getExtension() != null && s2.getExtension().length() > 0) {
					Globals.Datas.Supports.add(s2);
				}
			} catch(Exception e) {
				BasicEnums.ErrorType.OTHERS.register(e.toString());
			}
		}
		
		// LAN Machines
		// you can add or delete it at any time, it will reload at next start process.
		while(true) {
			c = cs.fetch("LANMachineIndex");
			if(c == null) {
				break;
			}
			
			long idx = c.getLong();
			if(c.getIsOK()) {
				Globals.Datas.LANMachineIndexes.add(idx);
			}
		}
		
		// Load All Depots and DataBases
		// all depots & databases get from machine index, if index is wrong, load will failed.
		// so You should Not change machine index.
		// machine index: MachineIndex in CFG File.
		if(true) {
			BasicCollections.DepotInfos des = new BasicCollections.DepotInfos();
			BasicCollections.DataBaseInfos dbs = new BasicCollections.DataBaseInfos();
			
			// QueryDepots
			Commands.QueryDepots qde = new Commands.QueryDepots();
			dbqcs.stringToThis("[&] MachineIndex = " + Globals.Configurations.This_MachineIndex);
			qde.setQueryConditions(dbqcs);
			Replies.QueryDepots repqde = (Replies.QueryDepots)swre.execute(qde.output());
			if(repqde == null) { return false; }
			if(!repqde.isOK()) {
				BasicEnums.ErrorType.COMMANDS_EXECUTE_FAILED.register("QueryDepots[load] Failed");
				return false;
			}
			des = repqde.getDepotInfos();
			
			// QueryDataBases
			Commands.QueryDataBases qdb = new Commands.QueryDataBases();
			dbqcs.stringToThis("[&] MachineIndex = " + Globals.Configurations.This_MachineIndex);
			qdb.setQueryConditions(dbqcs);
			Replies.QueryDataBases repqdb = (Replies.QueryDataBases)swre.execute(qdb.output());
			if(repqdb == null) { return false; }
			if(!repqdb.isOK()) {
				BasicEnums.ErrorType.COMMANDS_EXECUTE_FAILED.register("QueryDataBases[load] Failed");
				return false;
			}
			dbs = repqdb.getDBInfos();
			
			// load
			for(int i=0; i<des.size(); i++) {
				BasicModels.DepotInfo de = des.getContent().get(i);
				for(int j=0; j<dbs.size(); j++) {
					BasicModels.DataBaseInfo db = dbs.getContent().get(j);
					if(de.getIndex() == db.getDepotIndex()) {
						de.setMachineInfo(Globals.Datas.ThisMachine);
						db.setMachineInfo(Globals.Datas.ThisMachine);
						de.setDBInfo(db);
						db.setDepotInfo(de);
						Interfaces.IDBManager dbm = db.getManager();
						dbm.connect();
						if(dbm.isConnected()) {
							Globals.Datas.DBManagers.add(dbm);
						}
					}
				}
			}
		}
		
		// add database & depot
		// you can add database and depot at any time, it will reload at server start. 
		// you should make sure that Depot and DataBase is a couple, otherwise it will make mistake.
		// couple means the +Depot and +DataBase of same depot should write one first and another second.
		while(true) {
			
			BasicModels.Config cdepot = cs.fetch("+Depot");
			if(cdepot == null) {
				break;
			}
			BasicModels.Config cdb = cs.fetch("+DataBase");
			if(cdb == null) {
				break;
			}
			
			// Build Depot and DataBase
			BasicModels.DepotInfo depotInfo = Factories.DefaultFactory.createDefaultDepotInfo();
			BasicModels.DataBaseInfo dbInfo = depotInfo.getDBInfo();
			
			try {
				java.lang.String name = cdepot.fetchFirstString();
				if(name == null || name.length() == 0) {
					continue;
				}
				depotInfo.setName(name);
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
				if(name == null || name.length() == 0) {
					continue;
				}
				dbInfo.setName(name);
			} catch(Exception e) {
				continue;
			}
			try {
				BasicEnums.DataBaseType type = BasicEnums.DataBaseType.valueOf(cdb.fetchFirstString());
				dbInfo.setType(type);
			} catch(Exception e) {
				dbInfo.setType(BasicEnums.DataBaseType.TXT);
			}
			try {
				java.lang.String url = cdb.fetchFirstString();
				dbInfo.setUrl(url);
			} catch(Exception e) {
				;
			}
			
			// QueryConfigurations
			Commands.QueryConfigurations qcfg = new Commands.QueryConfigurations();
			Replies.QueryConfigurations repqcfg = (Replies.QueryConfigurations)swre.execute(qcfg.output());
			if(repqcfg == null) { return false; }
			if(!repqcfg.isOK()) {
				BasicEnums.ErrorType.COMMANDS_EXECUTE_FAILED.register("QueryConfigurations Failed");
				return false;
			}
			
			depotInfo.setIndex(repqcfg.getNext_DepotIndex() + 1);
			dbInfo.setIndex(repqcfg.getNext_DataBaseIndex() + 1);
			depotInfo.setDBInfo(dbInfo);
			dbInfo.setDepotInfo(depotInfo);
			depotInfo.setDBIndex();
			dbInfo.setDepotIndex();
			
			// QueryDepot
			Commands.QueryDepot qd = new Commands.QueryDepot();
			dbqcs.stringToThis("[&] MachineIndex = " + depotInfo.getMachineInfo().getIndex() + " , [&] Name = '" + depotInfo.getName() + "'");
			qd.setQueryConditions(dbqcs);
			Replies.QueryDepot repqd = (Replies.QueryDepot)swre.execute(qd.output());
			if(repqd == null) { continue; }
			if(!(!repqd.isOK() && repqd.getFailedReason().equals("Not Exist"))) {
				BasicEnums.ErrorType.COMMANDS_EXECUTE_FAILED.register( "Add Depot Failed, Existed: " + depotInfo.getName() );
				continue;
			}
			
			// QueryDataBase
			Commands.QueryDataBase qdb = new Commands.QueryDataBase();
			dbqcs.stringToThis("[&] MachineIndex = " + dbInfo.getMachineInfo().getIndex() + " , [&] Name = '" + dbInfo.getName() + "'");
			qdb.setQueryConditions(dbqcs);
			Replies.QueryDataBase repqdb = (Replies.QueryDataBase)swre.execute(qdb.output());
			if(repqdb == null) { continue; }
			if(!(!repqdb.isOK() && repqdb.getFailedReason().equals("Not Exist"))) {
				BasicEnums.ErrorType.COMMANDS_EXECUTE_FAILED.register( "Add DataBase Failed, Existed: " + dbInfo.getName() );
				continue;
			}
			
			//UpdateDepot
			Commands.UpdateDepot ud = new Commands.UpdateDepot();
			ud.setDepotInfo(depotInfo);
			Replies.UpdateDepot repud = (Replies.UpdateDepot)swre.execute(ud.output());
			if(repud == null) { continue; }
			if(!repud.isOK()) {
				BasicEnums.ErrorType.COMMANDS_EXECUTE_FAILED.register( "UpdataDepot Failed, " + repud.getFailedReason() );
				continue;
			}
			depotInfo = repud.getDepotInfo();
			
			//UpdateDataBase
			Commands.UpdateDataBase udb = new Commands.UpdateDataBase();
			udb.setDataBaseInfo(dbInfo);
			Replies.UpdateDataBase repudb = (Replies.UpdateDataBase)swre.execute(udb.output());
			if(repudb == null) { continue; }
			if(!repudb.isOK()) {
				BasicEnums.ErrorType.COMMANDS_EXECUTE_FAILED.register( "UpdateDataBase Failed, " + repudb.getFailedReason() );
				continue;
			}
			dbInfo = repudb.getDataBaseInfo();
			
			// UpdateDataBaseUrl
			if(dbInfo.getType().equals(BasicEnums.DataBaseType.TXT) && dbInfo.getUrl().length() == 0) {
				java.lang.String url = Tools.Pathes.getFolder_DBS(dbInfo.getIndex());
				boolean ok = Tools.Pathes.createFolder_DBS(dbInfo.getIndex());
				if(!ok) {
					BasicEnums.ErrorType.COMMON_FILE_OPERATE_FAILED.register("Create DataBaseFolder Failed");
					continue;
				}
				dbInfo.setUrl(url);
				udb.setDataBaseInfo(dbInfo);
				repudb = (Replies.UpdateDataBase)swre.execute(udb.output());
				if(repudb == null || !repudb.isOK()) {
					BasicEnums.ErrorType.COMMANDS_EXECUTE_FAILED.register("Update DataBaseUrl Failed", "Url = " + url);
					continue;
				}
			}
			
			// Create DataBase
			dbInfo.setDepotInfo(depotInfo);
			depotInfo.setDBInfo(dbInfo);
			dbInfo.setDepotIndex();
			depotInfo.setDBIndex();
			
			BasicModels.Folder f = new BasicModels.Folder(depotInfo.getUrl());
			long currentFileIndex = Globals.Configurations.Next_FileIndex;
			f.setIndex();
			f.setMachine(Globals.Configurations.This_MachineIndex);
			f.setDepot(depotInfo.getIndex());
			f.setDataBase(dbInfo.getIndex());
			f.load();
			Globals.Configurations.Next_FileIndex = currentFileIndex;
			BasicCollections.BaseFiles files = f.getAllSubs(f.getUrl());
			files.sortIncrease();
			
			Interfaces.IDBManager dbmanager = dbInfo.getManager();
			Globals.Datas.DBManagers.add(dbmanager);
			dbmanager.connect();
			if(dbmanager.isConnected()) {
				dbmanager.deleteDepotTables();
				dbmanager.createDataBase();
				dbmanager.createDepotTables();
				if(!dbmanager.updataFolder(f)) { continue; }
				if(!dbmanager.updataFiles(files)) { continue; }
			}
			
		}
		
		// delete database & depot
		// you can delete database and depot at any time, it will reload at server start. 
		// you should make sure that Depot and DataBase is a couple, otherwise it will make mistake.
		// couple means the -Depot and -DataBase of same depot should write one first and another second.
		// remove database & depot
		while(true) {
			BasicModels.Config cdepot = cs.fetch("-Depot");
			if(cdepot == null) {
				break;
			}
			BasicModels.Config cdb = cs.fetch("-DataBase");
			if(cdb == null) {
				break;
			}
			
			// unregister depot
			Commands.RemoveDepot rd = new Commands.RemoveDepot();
			dbqcs.stringToThis("[&] MachineIndex = " + Globals.Configurations.This_MachineIndex + " , [&] Name = '" + cdepot.getValue() + "'");
			rd.setQueryConditions(dbqcs);
			Interfaces.IReplies reprd = swre.execute(rd.output());
			
			// unregister database
			Commands.RemoveDataBase rdb = new Commands.RemoveDataBase();
			dbqcs.stringToThis("[&] MachineIndex = " + Globals.Configurations.This_MachineIndex + " , [&] Name = '" + cdb.getValue() + "'");
			rdb.setQueryConditions(dbqcs);
			Interfaces.IReplies reprdb = swre.execute(rdb.output());
			
			// operate failed
			if(reprd == null || !reprd.isOK() || reprdb == null || !reprdb.isOK()) {
				BasicEnums.ErrorType.COMMANDS_EXECUTE_FAILED.register(BasicEnums.ErrorLevel.Warning, "Delete Depot or DataBase Failed");
			}
			
			// remove database
			Interfaces.IDBManager dbm = Globals.Datas.DBManagers.searchDataBaseName(cdb.getValue());
			if(dbm == null) { continue; }
			
			dbm.deleteDepotTables();
			dbm.deleteDataBase();
			Globals.Datas.DBManagers.deleteDataBaseIndex(dbm.getDBInfo().getIndex());
		}
		
		// end
		return true;
	}
	private final static boolean saveDepotCFG() {
		return CFGFile.saveCFGCore(BasicEnums.StartType.Depot, false);
	}
	private final static boolean resetDepotCFG() {
		// delete all depots
		for(Interfaces.IDBManager dbm : Globals.Datas.DBManagers.getContent()) {
			dbm.deleteDepotTables();
			dbm.deleteDataBase();
		}
		
		// unregister
		for(Interfaces.IDBManager dbm : Globals.Datas.DBManagers.getContent()) {
			try {
				boolean ok = Globals.Datas.ServerConnection.getCommandsManager().removeDataBase(
						"[&] Index = " + dbm.getDBInfo().getIndex());
				if(!ok) {
					BasicEnums.ErrorType.COMMANDS_EXECUTE_FAILED.register(
							"Remove DataBase Failed, Index = " +
							dbm.getDBInfo().getIndex());
				}
				ok = Globals.Datas.ServerConnection.getCommandsManager().removeDepot(
						"[&] Index = " + dbm.getDBInfo().getDepotIndex());
				if(!ok) {
					BasicEnums.ErrorType.COMMANDS_EXECUTE_FAILED.register(
							"Remove Depot Failed, Index = " +
							dbm.getDBInfo().getDepotIndex());
				}
			} catch(Exception e) {
				;
			}
		}
		
		// clear
		Globals.Datas.DBManagers.clear();
		
		// clear Indexes
		Globals.Configurations.Next_FileIndex = 0;
		Globals.Configurations.Next_UserIndex = 0;
		Globals.Configurations.Next_MachineIndex = 0;
		Globals.Configurations.Next_DepotIndex = 0;
		Globals.Configurations.Next_DataBaseIndex = 0;
		//Globals.Configurations.This_MachineIndex = 0;
		//Globals.Configurations.This_UserIndex = 0;
		//Globals.Configurations.Server_MachineIndex = 0;
		//Globals.Configurations.Server_UserIndex = 0;
		
		//Globals.Datas.ThisMachine.setIndex(0);
		//Globals.Datas.ThisUser.setIndex(0);
		//Globals.Datas.ServerMachine.setIndex(0);
		//Globals.Datas.ServerUser.setIndex(0);
		
		/*
		if(Globals.Datas.DBManager.getDBInfo() != null) {
			Globals.Datas.DBManager.getDBInfo().setIndex(0);
			Globals.Datas.DBManager.getDBInfo().setDepotIndex(0);
			if(Globals.Datas.DBManager.getDBInfo().getDepotInfo() != null) {
				Globals.Datas.DBManager.getDBInfo().getDepotInfo().setIndex(0);
				Globals.Datas.DBManager.getDBInfo().getDepotInfo().setDBIndex(0);
			}
		}
		*/
		
		// save to file
		return saveDepotCFG();
	}
	
	private final static boolean loadClientCFG(BasicCollections.Configs cs) {
		return CFGFile.loadDepotCFG(cs);
	}
	private final static boolean saveClientCFG() {
		return CFGFile.saveCFGCore(BasicEnums.StartType.Client, false);
	}
	private final static boolean resetClientCFG() {
		return CFGFile.resetDepotCFG();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static boolean saveCFGCore(BasicEnums.StartType type, boolean createNew) {
		FileModels.Text txt = new FileModels.Text(Tools.Pathes.getFile_CFG());
		
		java.lang.String line = "";
		txt.getContent().add(line);
		line = "/****************************************** This File Is VERY IMPORTANT **********************************************/";
		txt.getContent().add(line);
		txt.getContent().add(line);
		txt.getContent().add(line);
		
		line = "";
		txt.getContent().add(line);
		line = "[Attention]: Do Not Save Other Info in This File, It will reset At Running.";
		txt.getContent().add(line);
		
		line = "";
		txt.getContent().add(line);
		line = "StartType = " + Globals.Configurations.StartType.toString() + 
				(Globals.Configurations.ShowForm ? "|ShowForm" : "");
		txt.getContent().add(line);
		
		if(Globals.Configurations.IsServer) {
			line = "NetType = " + Globals.Configurations.NetType.toString();
			txt.getContent().add(line);
		}
		
		line = "";
		txt.getContent().add(line);
		line = "/**************************************** A List of Current Depots & DataBases ***************************************/";
		txt.getContent().add(line);
		line = "";
		txt.getContent().add(line);
		
		if(createNew) {
			;
		}
		else {
			for(Interfaces.IDBManager dbm : Globals.Datas.DBManagers.getContent()) {
				line = "Depot = " + dbm.getDBInfo().getDepotInfo().getName() + "|" + dbm.getDBInfo().getDepotInfo().getUrl();
				txt.getContent().add(line);
				line = "DataBase = " + dbm.getDBInfo().getName() + "|" + dbm.getDBInfo().getType().toString() + "|" + dbm.getDBInfo().getUrl();
				txt.getContent().add(line);
			}
		}
		
		
		
		line = "";
		txt.getContent().add(line);
		line = "/*************************************************** All Indexes *****************************************************/";
		txt.getContent().add(line);
		line = "";
		txt.getContent().add(line);
		
		if(createNew) {
			line = "Next_FileIndex = 0";
			txt.getContent().add(line);
			line = "Next_UserIndex = 0";
			txt.getContent().add(line);
			line = "Next_MachineIndex = 0";
			txt.getContent().add(line);
			line = "Next_DepotIndex = 0";
			txt.getContent().add(line);
			line = "Next_DataBaseIndex = 0";
			txt.getContent().add(line);
			line = "This_MachineIndex = 0";
			txt.getContent().add(line);
			line = "This_UserIndex = 0";
			txt.getContent().add(line);
			line = "Server_MachineIndex = 0";
			txt.getContent().add(line);
			line = "Server_UserIndex = 0";
			txt.getContent().add(line);
		}
		else {
			line = "Next_FileIndex = " + java.lang.String.valueOf(Globals.Configurations.Next_FileIndex);
			txt.getContent().add(line);
			line = "Next_UserIndex = " + java.lang.String.valueOf(Globals.Configurations.Next_UserIndex);
			txt.getContent().add(line);
			line = "Next_MachineIndex = " + java.lang.String.valueOf(Globals.Configurations.Next_MachineIndex);
			txt.getContent().add(line);
			line = "Next_DepotIndex = " + java.lang.String.valueOf(Globals.Configurations.Next_DepotIndex);
			txt.getContent().add(line);
			line = "Next_DataBaseIndex = " + java.lang.String.valueOf(Globals.Configurations.Next_DataBaseIndex);
			txt.getContent().add(line);
			line = "This_MachineIndex = " + java.lang.String.valueOf(Globals.Configurations.This_MachineIndex);
			txt.getContent().add(line);
			line = "This_UserIndex = " + java.lang.String.valueOf(Globals.Configurations.This_UserIndex);
			txt.getContent().add(line);
			line = "Server_MachineIndex = " + java.lang.String.valueOf(Globals.Configurations.Server_MachineIndex);
			txt.getContent().add(line);
			line = "Server_UserIndex = " + java.lang.String.valueOf(Globals.Configurations.Server_UserIndex);
			txt.getContent().add(line);
		}
		
		
		
		line = "";
		txt.getContent().add(line);
		line = "/*************************************************** Machine Info ****************************************************/";
		txt.getContent().add(line);
		line = "";
		txt.getContent().add(line);
		
		if(createNew) {
			line = "MachineIndex = ";
			txt.getContent().add(line);
			line = "MachineName = ";
			txt.getContent().add(line);
			line = "MachineIP = ";
			txt.getContent().add(line);
			line = "MachinePort = ";
			txt.getContent().add(line);
		}
		else {
			line = "MachineIndex = " + Globals.Datas.ThisMachine.getIndex();
			txt.getContent().add(line);
			line = "MachineName = " + Globals.Datas.ThisMachine.getName();
			txt.getContent().add(line);
			line = "MachineIP = " + Globals.Datas.ThisMachine.getIp();
			txt.getContent().add(line);
			line = "MachinePort = " + Globals.Datas.ThisMachine.getPort();
			txt.getContent().add(line);
		}
		
		line = "";
		txt.getContent().add(line);
		line = "/*************************************************** Server Info *****************************************************/";
		txt.getContent().add(line);
		line = "";
		txt.getContent().add(line);
		
		if(createNew) {
			if(BasicEnums.StartType.Server.equals(type)) {
				line = "ServerDepotIndex = ";
				txt.getContent().add(line);
				line = "ServerDepotName = ";
				txt.getContent().add(line);
				line = "ServerDepotUrl = ";
				txt.getContent().add(line);
				
				line = "ServerDataBaseIndex = ";
				txt.getContent().add(line);
				line = "ServerDataBaseName = ";
				txt.getContent().add(line);
				line = "ServerDataBaseType = ";
				txt.getContent().add(line);
				line = "ServerDataBaseUrl = ";
				txt.getContent().add(line);
			}
			if(BasicEnums.StartType.Depot.equals(type) || BasicEnums.StartType.Client.equals(type)) {
				line = "ServerMachineIndex = ";
				txt.getContent().add(line);
				line = "ServerMachineName = ";
				txt.getContent().add(line);
				line = "ServerMachineIP = [You Must Fill]";
				txt.getContent().add(line);
				line = "ServerMachinePort = [You Must Fill]";
				txt.getContent().add(line);
			}
		}
		else {
			if(BasicEnums.StartType.Server.equals(type)) {
				line = "ServerDepotIndex = " + Globals.Datas.DBManager.getDBInfo().getDepotInfo().getIndex();
				txt.getContent().add(line);
				line = "ServerDepotName = " + Globals.Datas.DBManager.getDBInfo().getDepotInfo().getName();
				txt.getContent().add(line);
				line = "ServerDepotUrl = " + Globals.Datas.DBManager.getDBInfo().getDepotInfo().getUrl();
				txt.getContent().add(line);
				
				line = "ServerDataBaseIndex = " + Globals.Datas.DBManager.getDBInfo().getIndex();
				txt.getContent().add(line);
				line = "ServerDataBaseName = " + Globals.Datas.DBManager.getDBInfo().getName();
				txt.getContent().add(line);
				line = "ServerDataBaseType = " + Globals.Datas.DBManager.getDBInfo().getType().toString();
				txt.getContent().add(line);
				line = "ServerDataBaseUrl = " + Globals.Datas.DBManager.getDBInfo().getUrl();
				txt.getContent().add(line);
			}
			if(BasicEnums.StartType.Depot.equals(type) || BasicEnums.StartType.Client.equals(type)) {
				line = "ServerMachineIndex = " + Globals.Datas.ServerMachine.getIndex();
				txt.getContent().add(line);
				line = "ServerMachineName = " + Globals.Datas.ServerMachine.getName();
				txt.getContent().add(line);
				line = "ServerMachineIP = " + Globals.Datas.ServerMachine.getIp();
				txt.getContent().add(line);
				line = "ServerMachinePort = " + Globals.Datas.ServerMachine.getPort();
				txt.getContent().add(line);
			}
		}
		
		if(!BasicEnums.StartType.Server.equals(type)) {
			line = "";
			txt.getContent().add(line);
			line = "/***************************************************** User Info *****************************************************/";
			txt.getContent().add(line);
			line = "";
			txt.getContent().add(line);
			
			line = "UserLoginName = " + Globals.Datas.ThisUser.getLoginName();
			txt.getContent().add(line);
			line = "UserPassword = " + Globals.Datas.ThisUser.getPassword();
			txt.getContent().add(line);
		}
		
		line = "";
		txt.getContent().add(line);
		line = "/*************************************************** Supports Info ***************************************************/";
		txt.getContent().add(line);
		line = "";
		txt.getContent().add(line);
		line = "Example: Support = Picture|.jpg|.pv1";
		txt.getContent().add(line);
		line = "";
		txt.getContent().add(line);
		
		if(createNew) {
			line = "Support = ";
			txt.getContent().add(line);
		}
		else {
			for(BasicModels.Support s : Globals.Datas.Supports.getContent()) {
				if(s.isHideExtension()) {
					continue;
				}
				line = "Support = " + s.getType().toString() + "|" + s.getShowExtension() + "|" + s.getHideExtension();
				txt.getContent().add(line);
			}
			if(Globals.Datas.Supports.size() == 0) {
				line = "Support = ";
				txt.getContent().add(line);
			}
		}
		
		line = "";
		txt.getContent().add(line);
		line = "/*************************************************** LAN Machines ****************************************************/";
		txt.getContent().add(line);
		line = "";
		txt.getContent().add(line);
		
		if(createNew) {
			line = "LANMachineIndex = ";
			txt.getContent().add(line);
		}
		else {
			if(Globals.Datas.LANMachineIndexes.size() > 0) {
				for(int i=0; i<Globals.Datas.LANMachineIndexes.size(); i++) {
					line = "LANMachineIndex = " + Globals.Datas.LANMachineIndexes.get(i);
					txt.getContent().add(line);
				}
			} else {
				line = "LANMachineIndex = ";
				txt.getContent().add(line);
			}
		}
		
		line = "";
		txt.getContent().add(line);
		line = "/********************************************** Add Local Depot & DataBase *******************************************/";
		txt.getContent().add(line);
		line = "";
		txt.getContent().add(line);
		
		line = "[Attention]: At Each Add Operation, You Should add Depot and DataBase Two Items, Otherwise add will Failed.";
		txt.getContent().add(line);
		line = "";
		txt.getContent().add(line);
		
		line = "Example: +Depot = [DepotName1]|[DepotFolderAbsolutePath]";
		txt.getContent().add(line);
		line = "Example: +DataBase = [DataBaseName1]|SQL|[LoginIP]:[LoginPort]\\[LoginName]\\[Password]\\[DataBaseNameInSQL]";
		txt.getContent().add(line);
		line = "";
		txt.getContent().add(line);
		line = "Example: +Depot = [DepotName2]|[DepotFolderAbsolutePath]";
		txt.getContent().add(line);
		line = "Example: +DataBase = [DataBaseName2]|TXT|[DataBaseFolderAbsolutePath]";
		txt.getContent().add(line);
		line = "";
		txt.getContent().add(line);
		line = "Example: +Depot = [DepotName3]|[DepotFolderAbsolutePath]";
		txt.getContent().add(line);
		line = "Example: +DataBase = [DataBaseName3]";
		txt.getContent().add(line);
		
		line = "";
		txt.getContent().add(line);
		line = "/********************************************** Del Local Depot & DataBase *******************************************/";
		txt.getContent().add(line);
		line = "";
		txt.getContent().add(line);
		
		line = "[Attention]: At Each Del Operation, You should delete Depot and DataBase Two Items, ";
		txt.getContent().add(line);
		line = "             Otherwise some Errors will happen in Running.";
		txt.getContent().add(line);
		
		line = "";
		txt.getContent().add(line);
		line = "Example: -Depot = [DepotName1]";
		txt.getContent().add(line);
		line = "Example: -DataBase = [DataBaseName1]";
		txt.getContent().add(line);
		
		line = "";
		txt.getContent().add(line);
		line = "Example: -Depot = [DepotName2]";
		txt.getContent().add(line);
		line = "Example: -DataBase = [DataBaseName2]";
		txt.getContent().add(line);
		
		line = "";
		txt.getContent().add(line);
		line = "Example: -Depot = [DepotName3]";
		txt.getContent().add(line);
		line = "Example: -DataBase = [DataBaseName3]";
		txt.getContent().add(line);
		
		line = "";
		txt.getContent().add(line);
		line = "/******************************************************* END *********************************************************/";
		txt.getContent().add(line);
		line = "";
		txt.getContent().add(line);
		
		return txt.save();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
