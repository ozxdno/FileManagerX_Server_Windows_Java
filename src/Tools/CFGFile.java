package Tools;

public class CFGFile {
	
	public static java.lang.String url;
	
	public final static boolean createCFG() {
		if(url == null || url.length() == 0) {
			url = Tools.Directories.getExePath() + "\\cfg\\FileManagerX.cfg";
		}
		
		java.io.File f = new java.io.File(url);
		if(f.exists()) {
			return true;
		}
		try {
			return f.createNewFile();
		} catch(Exception e) {
			return false;
		}
	}
	
	public final static boolean loadCFG() {
		FileModels.Text txt = new FileModels.Text(url);
		txt.load(true);
		BasicCollections.Configs cs = txt.toConfigs();
		
		// Configurations
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
		}
		
		// machine info & reset machine info of server
		if(true) {
			BasicModels.MachineInfo machine = new BasicModels.MachineInfo();
			try {
				machine.setIndex(cs.fetch("MachineIndex").getLong());
			}catch(Exception e) {
				//machine.setIndex();
			}
			try {
				machine.setName(cs.fetch("MachineName").getValue());
			}catch(Exception e) {
				machine.setName();
			}
			try {
				machine.setIp(cs.fetch("MachineIP").getValue());
			}catch(Exception e) {
				machine.setIp();
			}
			try {
				machine.setPort(cs.fetch("MachinePort").getInt());
			}catch(Exception e) {
				machine.setPort();
			}
			
			if(machine.getIndex() <= 0) {
				//machine.setIndex();
			}
			if(machine.getName() == null || machine.getName().length() == 0) {
				machine.setName();
			}
			if(machine.getIp() == null || machine.getIp().length() == 0) {
				machine.setIp();
			}
			if(machine.getPort() <=0 || machine.getPort() > 65535) {
				machine.setPort();
			}
			Globals.Datas.ThisMachine.copyReference(machine);
		}
		
		// Server
		if(true) {
			BasicModels.DepotInfo depotInfo = new BasicModels.DepotInfo();
			try {
				depotInfo.setIndex(cs.fetch("ServerDepotIndex").getLong());
			}catch(Exception e) {
				//depotInfo.setIndex();
			}
			try {
				depotInfo.setName(cs.fetch("ServerDepotName").getString());
			}catch(Exception e) {
				depotInfo.setName(Globals.Datas.ThisMachine.getName());
			}
			try {
				depotInfo.setUrl(cs.fetch("ServerDepotUrl").getString());
			}catch(Exception e) {
				depotInfo.setUrl("");
			}
			if(depotInfo.getIndex() <= 0) {
				//depotInfo.setIndex();
			}
			if(depotInfo.getName() == null || depotInfo.getName().length() == 0) {
				depotInfo.setName(Globals.Datas.ThisMachine.getName());
			}
			if(depotInfo.getUrl() == null || depotInfo.getUrl().length() == 0) {
				depotInfo.setUrl("");
			}
			BasicModels.DataBaseInfo dbInfo = new BasicModels.DataBaseInfo();
			try {
				dbInfo.setIndex(cs.fetch("ServerDataBaseIndex").getLong());
			}catch(Exception e) {
				//dbInfo.setIndex();
			}
			try {
				dbInfo.setName(cs.fetch("ServerDataBaseName").getString());
			}catch(Exception e) {
				dbInfo.setName(Globals.Datas.ThisMachine.getName());
			}
			try {
				dbInfo.setType(BasicEnums.DataBaseType.valueOf(cs.fetch("ServerDataBaseType").getString()));
			}catch(Exception e) {
				return false;
			}
			try {
				dbInfo.setUrl(cs.fetch("ServerDataBaseUrl").getString());
			}catch(Exception e) {
				return false;
			}
			if(dbInfo.getIndex() <= 0) {
				//dbInfo.setIndex();
			}
			if(dbInfo.getName() == null || dbInfo.getName().length() == 0) {
				dbInfo.setName(Globals.Datas.ThisMachine.getName());
			}
			if(dbInfo.getType() == null) {
				return false;
			}
			if(dbInfo.getUrl() == null || dbInfo.getUrl().length() == 0) {
				return false;
			}
			
			depotInfo.setDBIndex(dbInfo.getIndex());
			depotInfo.setDBInfo(dbInfo);
			depotInfo.setMachineInfo(Globals.Datas.ThisMachine);
			dbInfo.setDepotIndex(depotInfo.getIndex());
			dbInfo.setDepotInfo(depotInfo);
			dbInfo.setMachineInfo(Globals.Datas.ThisMachine);
			
			Globals.Datas.DBManager.initialize(dbInfo);
			Globals.Datas.DBManager.connect();
			if(!Globals.Datas.DBManager.isConnected()) {
				return false;
			}
			
			Globals.Datas.DBManager.createServerTables();
			Globals.Datas.DBManager.updataMachineInfo(Globals.Datas.ThisMachine);
			Globals.Datas.DBManager.updataDepotInfo(depotInfo);
			Globals.Datas.DBManager.updataDataBaseInfo(dbInfo);
			Globals.Configurations.This_MachineIndex = Globals.Datas.ThisMachine.getIndex();
		}
		
		// user info
		if(true) {
			if(Globals.Configurations.This_UserIndex <= 0) {
				BasicModels.User u = new BasicModels.User();
				//u.setIndex();
				u.setLoginName("FileManagerX");
				u.setNickName("ozxdno");
				u.setPassword("ani1357658uiu");
				u.setEmail("ozxdno@126.com");
				u.setPhone("18883346652");
				u.setState(BasicEnums.UserState.OffLine);
				u.setPriority(BasicEnums.UserPriority.Ozxdno);
				u.setLevel(BasicEnums.UserLevel.Level0);
				u.setExperience(0);
				u.setPhotoUrl("");
				u.setCoins(0);
				u.setMoney(0);
				Globals.Datas.DBManager.updataUser(u);
				Globals.Datas.ThisUser.copyReference(u);
				Globals.Configurations.This_UserIndex = Globals.Datas.ThisUser.getIndex();
			} else {
				DataBaseManager.QueryConditions qcs = new DataBaseManager.QueryConditions();
				DataBaseManager.QueryCondition qc = new DataBaseManager.QueryCondition();
				qc.setSign(DataBaseManager.Sign.EQUAL);
				qc.setItemName("Index");
				qc.setValue(java.lang.String.valueOf(Globals.Configurations.This_UserIndex));
				qcs.add(qc);
				Globals.Datas.ThisUser.copyReference(Globals.Datas.DBManager.QueryUser(qcs));
				Globals.Configurations.This_UserIndex = Globals.Datas.ThisUser.getIndex();
			}
		}
		
		// supports
		while(true) {
			BasicModels.Config c = cs.fetch("Support");
			if(c == null) {
				break;
			}
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
		}
		
		// load all local depots & databases
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
		while(true) {
			BasicModels.Config cdepot = cs.fetch("+Depot");
			if(cdepot == null) {
				break;
			}
			BasicModels.Config cdb = cs.fetch("+DataBase");
			if(cdb == null) {
				break;
			}
			
			BasicModels.DepotInfo depotInfo = new BasicModels.DepotInfo();
			//depotInfo.setIndex();
			depotInfo.setMachineInfo(Globals.Datas.ThisMachine);
			depotInfo.setName(cdepot.fetchFirstString());
			if(!cdepot.getIsOK()) {
				continue;
			}
			depotInfo.setUrl(cdepot.fetchFirstString());
			if(!cdepot.getIsOK()) {
				continue;
			}
			
			BasicModels.DataBaseInfo dbInfo = new BasicModels.DataBaseInfo();
			//dbInfo.setIndex();
			dbInfo.setMachineInfo(Globals.Datas.ThisMachine);
			dbInfo.setName(cdb.fetchFirstString());
			if(!cdepot.getIsOK()) {
				continue;
			}
			dbInfo.setType(BasicEnums.DataBaseType.valueOf(cdb.fetchFirstString()));
			if(!cdepot.getIsOK()) {
				continue;
			}
			dbInfo.setUrl(cdb.fetchFirstString());
			if(!cdepot.getIsOK()) {
				continue;
			}
			
			depotInfo.setDBInfo(dbInfo);
			depotInfo.setDBIndex();
			dbInfo.setDepotInfo(depotInfo);
			dbInfo.setDepotIndex();
			
			DataBaseManager.QueryCondition qc = new DataBaseManager.QueryCondition();
			qc.setItemName("Name");
			qc.setValue("'" + depotInfo.getName() + "'");
			if(Globals.Datas.DBManager.QueryDepotInfo(qc) != null) {
				continue;
			}
			qc.setItemName("Name");
			qc.setValue("'" + dbInfo.getName() + "'");
			if(Globals.Datas.DBManager.QueryDataBaseInfo(qc) != null) {
				continue;
			}
			
			if(!Globals.Datas.DBManager.updataDataBaseInfo(dbInfo)) {
				continue;
			}
			if(!Globals.Datas.DBManager.updataDepotInfo(depotInfo)) {
				continue;
			}
			
			BasicModels.Folder f = new BasicModels.Folder(depotInfo.getUrl());
			long currentFileIndex = Globals.Configurations.Next_FileIndex;
			f.setIndex();
			f.setMachine(Globals.Configurations.This_MachineIndex);
			f.setDepot(depotInfo.getIndex());
			f.setDataBase(dbInfo.getIndex());
			f.load();
			Globals.Configurations.Next_FileIndex = currentFileIndex;
			f.setIndex(-1);

			BasicCollections.BaseFiles files = f.getAllSubs(f.getUrl());
			for(BasicModels.BaseFile i : files.getContent()) {
				i.setIndex(-1);
			}
			
			Interfaces.IDBManager dbmanager = dbInfo.getManager();
			Globals.Datas.DBManagers.add(dbmanager);
			dbmanager.connect();
			if(dbmanager.isConnected()) {
				dbmanager.deleteDepotTables();
				dbmanager.createDepotTables();
				if(!dbmanager.updataFolder(f)) {
					continue;
				}
				if(!dbmanager.updataFiles(files)) {
					continue;
				}
			}
		}
		
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
			
			DataBaseManager.QueryCondition qc = new DataBaseManager.QueryCondition();
			qc.setItemName("Name");
			qc.setSign(DataBaseManager.Sign.EQUAL);
			qc.setValue("'" + cdepot.getValue() + "'");
			BasicModels.DepotInfo depotInfo = Globals.Datas.DBManager.QueryDepotInfo(qc);
			if(depotInfo == null) {
				continue;
			}
			
			qc.setValue("'" + cdb.getValue() + "'");
			BasicModels.DataBaseInfo dbInfo = Globals.Datas.DBManager.QueryDataBaseInfo(qc);
			if(dbInfo == null) {
				continue;
			}
			
			Globals.Datas.DBManager.removeDataBaseInfo(dbInfo);
			Globals.Datas.DBManager.removeDepotInfo(depotInfo);
			Globals.Datas.DBManagers.deleteDataBaseName(dbInfo.getName());
		}
		
		
		return true;
	}
	
	public final static boolean saveCFG() {
		FileModels.Text txt = new FileModels.Text(url);
		
		java.lang.String line = "";
		txt.getContent().add(line);
		line = "/**************************************** This File Is VERY IMPORTANT ********************************************/";
		txt.getContent().add(line);
		txt.getContent().add(line);
		txt.getContent().add(line);
		
		line = "";
		txt.getContent().add(line);
		txt.getContent().add(line);
		txt.getContent().add(line);
		line = "/************************************** A List of Current Depots & DataBases *************************************/";
		txt.getContent().add(line);
		line = "";
		txt.getContent().add(line);
		
		for(Interfaces.IDBManager dbm : Globals.Datas.DBManagers.getContent()) {
			line = "Depot = " + dbm.getDBInfo().getDepotInfo().getName() + "|" + dbm.getDBInfo().getDepotInfo().getUrl();
			txt.getContent().add(line);
			line = "DataBase = " + dbm.getDBInfo().getName() + "|" + dbm.getDBInfo().getType().toString() + "|" + dbm.getDBInfo().getUrl();
			txt.getContent().add(line);
		}
		
		line = "";
		txt.getContent().add(line);
		line = "/*************************************************** All Indexes *****************************************************/";
		txt.getContent().add(line);
		line = "";
		txt.getContent().add(line);
		
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
		
		line = "";
		txt.getContent().add(line);
		line = "/************************************************** Machine Info ***************************************************/";
		txt.getContent().add(line);
		line = "";
		txt.getContent().add(line);
		
		line = "MachineIndex = " + Globals.Datas.ThisMachine.getIndex();
		txt.getContent().add(line);
		line = "MachineName = " + Globals.Datas.ThisMachine.getName();
		txt.getContent().add(line);
		line = "MachineIP = " + Globals.Datas.ThisMachine.getIp();
		txt.getContent().add(line);
		line = "MachinePort = " + Globals.Datas.ThisMachine.getPort();
		txt.getContent().add(line);
		
		line = "";
		txt.getContent().add(line);
		line = "/*************************************************** Server Info *****************************************************/";
		txt.getContent().add(line);
		line = "";
		txt.getContent().add(line);
		
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
		
		line = "";
		txt.getContent().add(line);
		line = "/************************************************** Supports Info **************************************************/";
		txt.getContent().add(line);
		line = "";
		txt.getContent().add(line);
		line = "Example: Support = Picture|.jpg|.pv1";
		txt.getContent().add(line);
		line = "";
		txt.getContent().add(line);
		
		for(BasicModels.Support s : Globals.Datas.Supports.getContent()) {
			if(s.isHideExtension()) {
				continue;
			}
			line = "Support = " + s.getType().toString() + "|" + s.getShowExtension() + "|" + s.getHideExtension();
			txt.getContent().add(line);
		}
		
		line = "";
		txt.getContent().add(line);
		line = "/******************************************* Add Local Depot & DataBase ****************************************/";
		txt.getContent().add(line);
		line = "";
		txt.getContent().add(line);
		line = "Example: +Depot = DepotA|D:\\Space_For_Media\\Pictures\\FMX_Test_Depot_A";
		txt.getContent().add(line);
		line = "Example: +DataBase = DepotA|SQL|127.0.0.1:3306\\root\\ani1357658uiu\\Depot_A";
		txt.getContent().add(line);
		line = "";
		txt.getContent().add(line);
		line = "Example: +Depot = DepotB|D:\\Space_For_Media\\Pictures\\FMX_Test_Depot_B";
		txt.getContent().add(line);
		line = "Example: +DataBase = DepotB|TXT|D:\\Space_For_Project\\FileManagerX\\Server\\Windows_Java\\dbs";
		txt.getContent().add(line);
		
		line = "";
		txt.getContent().add(line);
		line = "/******************************************* Add Local Depot & DataBase ****************************************/";
		txt.getContent().add(line);
		line = "";
		txt.getContent().add(line);
		line = "Example: -Depot = DepotA";
		txt.getContent().add(line);
		line = "Example: -DataBase = DepotA";
		txt.getContent().add(line);
		line = "";
		txt.getContent().add(line);
		line = "Example: -Depot = DepotB";
		txt.getContent().add(line);
		line = "Example: -DataBase = DepotB";
		txt.getContent().add(line);
		line = "";
		txt.getContent().add(line);
		txt.getContent().add(line);
		txt.getContent().add(line);
		
		return txt.save();
	}
	
	public final static boolean resetCFG() {
		FileModels.Text txt = new FileModels.Text(url);
		txt.load(true);
		BasicCollections.Configs cs = txt.toConfigs();
		
		// Configurations
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
		}
		
		// machine info & reset machine info of server
		if(true) {
			BasicModels.MachineInfo machine = new BasicModels.MachineInfo();
			try {
				machine.setIndex(cs.fetch("MachineIndex").getLong());
			}catch(Exception e) {
				//machine.setIndex();
			}
			try {
				machine.setName(cs.fetch("MachineName").getValue());
			}catch(Exception e) {
				machine.setName();
			}
			try {
				machine.setIp(cs.fetch("MachineIP").getValue());
			}catch(Exception e) {
				machine.setIp();
			}
			try {
				machine.setPort(cs.fetch("MachinePort").getInt());
			}catch(Exception e) {
				machine.setPort();
			}
			
			if(machine.getIndex() <= 0) {
				//machine.setIndex();
			}
			if(machine.getName() == null || machine.getName().length() == 0) {
				machine.setName();
			}
			if(machine.getIp() == null || machine.getIp().length() == 0) {
				machine.setIp();
			}
			if(machine.getPort() <=0 || machine.getPort() > 65535) {
				machine.setPort();
			}
			Globals.Datas.ThisMachine.copyReference(machine);
		}
		
		// Server
		if(true) {
			BasicModels.DepotInfo depotInfo = new BasicModels.DepotInfo();
			try {
				depotInfo.setIndex(cs.fetch("ServerDepotIndex").getLong());
			}catch(Exception e) {
				//depotInfo.setIndex();
			}
			try {
				depotInfo.setName(cs.fetch("ServerDepotName").getString());
			}catch(Exception e) {
				depotInfo.setName(Globals.Datas.ThisMachine.getName());
			}
			try {
				depotInfo.setUrl(cs.fetch("ServerDepotUrl").getString());
			}catch(Exception e) {
				depotInfo.setUrl("");
			}
			if(depotInfo.getIndex() <= 0) {
				//depotInfo.setIndex();
			}
			if(depotInfo.getName() == null || depotInfo.getName().length() == 0) {
				depotInfo.setName(Globals.Datas.ThisMachine.getName());
			}
			if(depotInfo.getUrl() == null || depotInfo.getUrl().length() == 0) {
				depotInfo.setUrl("");
			}
			BasicModels.DataBaseInfo dbInfo = new BasicModels.DataBaseInfo();
			try {
				dbInfo.setIndex(cs.fetch("ServerDataBaseIndex").getLong());
			}catch(Exception e) {
				//dbInfo.setIndex();
			}
			try {
				dbInfo.setName(cs.fetch("ServerDataBaseName").getString());
			}catch(Exception e) {
				dbInfo.setName(Globals.Datas.ThisMachine.getName());
			}
			try {
				dbInfo.setType(BasicEnums.DataBaseType.valueOf(cs.fetch("ServerDataBaseType").getString()));
			}catch(Exception e) {
				return false;
			}
			try {
				dbInfo.setUrl(cs.fetch("ServerDataBaseUrl").getString());
			}catch(Exception e) {
				return false;
			}
			if(dbInfo.getIndex() <= 0) {
				//dbInfo.setIndex();
			}
			if(dbInfo.getName() == null || dbInfo.getName().length() == 0) {
				dbInfo.setName(Globals.Datas.ThisMachine.getName());
			}
			if(dbInfo.getType() == null) {
				return false;
			}
			if(dbInfo.getUrl() == null || dbInfo.getUrl().length() == 0) {
				return false;
			}
			
			depotInfo.setDBIndex(dbInfo.getIndex());
			depotInfo.setDBInfo(dbInfo);
			depotInfo.setMachineInfo(Globals.Datas.ThisMachine);
			dbInfo.setDepotIndex(depotInfo.getIndex());
			dbInfo.setDepotInfo(depotInfo);
			dbInfo.setMachineInfo(Globals.Datas.ThisMachine);
			
			Globals.Datas.DBManager.initialize(dbInfo);
			Globals.Datas.DBManager.connect();
			if(!Globals.Datas.DBManager.isConnected()) {
				return false;
			}
			
			Globals.Datas.DBManager.createServerTables();
			Globals.Datas.DBManager.updataMachineInfo(Globals.Datas.ThisMachine);
			Globals.Datas.DBManager.updataDepotInfo(depotInfo);
			Globals.Datas.DBManager.updataDataBaseInfo(dbInfo);
			Globals.Configurations.This_MachineIndex = Globals.Datas.ThisMachine.getIndex();
		}
		
		// user info
		if(true) {
			if(Globals.Configurations.This_UserIndex <= 0) {
				BasicModels.User u = new BasicModels.User();
				//u.setIndex();
				u.setLoginName("FileManagerX");
				u.setNickName("ozxdno");
				u.setPassword("ani1357658uiu");
				u.setEmail("ozxdno@126.com");
				u.setPhone("18883346652");
				u.setState(BasicEnums.UserState.OffLine);
				u.setPriority(BasicEnums.UserPriority.Ozxdno);
				u.setLevel(BasicEnums.UserLevel.Level0);
				u.setExperience(0);
				u.setPhotoUrl("");
				u.setCoins(0);
				u.setMoney(0);
				Globals.Datas.DBManager.updataUser(u);
				Globals.Datas.ThisUser.copyReference(u);
				Globals.Configurations.This_UserIndex = Globals.Datas.ThisUser.getIndex();
			} else {
				DataBaseManager.QueryConditions qcs = new DataBaseManager.QueryConditions();
				DataBaseManager.QueryCondition qc = new DataBaseManager.QueryCondition();
				qc.setSign(DataBaseManager.Sign.EQUAL);
				qc.setItemName("Index");
				qc.setValue(java.lang.String.valueOf(Globals.Configurations.This_UserIndex));
				qcs.add(qc);
				Globals.Datas.ThisUser.copyReference(Globals.Datas.DBManager.QueryUser(qcs));
				Globals.Configurations.This_UserIndex = Globals.Datas.ThisUser.getIndex();
			}
		}
		
		// supports
		while(true) {
			BasicModels.Config c = cs.fetch("Support");
			if(c == null) {
				break;
			}
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
		}
		
		// load all local depots & databases
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
		
		// install all database & depot
		Globals.Datas.DBManager.deleteServerTables();
		Globals.Datas.DBManager.disconnect();
		
		for(Interfaces.IDBManager dbm : Globals.Datas.DBManagers.getContent()) {
			dbm.deleteDepotTables();
			dbm.disconnect();
		}
		Globals.Datas.DBManagers.removeAllDBManagers();
		
		// clear supports
		Globals.Datas.Supports.clear();
		
		// reset index
		Globals.Configurations.Next_MachineIndex = 0;
		Globals.Configurations.Next_DepotIndex = 0;
		Globals.Configurations.Next_DataBaseIndex = 0;
		Globals.Configurations.Next_FileIndex = 0;
		Globals.Configurations.Next_UserIndex = 0;
		Globals.Configurations.This_MachineIndex = 0;
		Globals.Configurations.This_UserIndex = 0;
		
		Globals.Datas.ThisMachine.setIndex(-1);
		Globals.Datas.DBManager.getDBInfo().setIndex(-1);
		Globals.Datas.DBManager.getDBInfo().getDepotInfo().setIndex(-1);
		
		return true;
	}
}
