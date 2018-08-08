package com.FileManagerX.FileModels;

import java.io.*;
import java.util.*;

import com.FileManagerX.BasicCollections.BaseFiles;
import com.FileManagerX.BasicCollections.Configs;
import com.FileManagerX.BasicCollections.DataBaseInfos;
import com.FileManagerX.BasicCollections.DepotInfos;
import com.FileManagerX.BasicCollections.MachineInfos;
import com.FileManagerX.BasicModels.BaseFile;
import com.FileManagerX.BasicModels.Config;
import com.FileManagerX.BasicModels.DataBaseInfo;
import com.FileManagerX.BasicModels.DepotInfo;
import com.FileManagerX.BasicModels.Folder;
import com.FileManagerX.BasicModels.MachineInfo;
import com.FileManagerX.BasicModels.Support;
import com.FileManagerX.BasicModels.User;
import com.FileManagerX.DataBase.QueryCondition;
import com.FileManagerX.DataBase.QueryConditions;
import com.FileManagerX.DataBase.Unit;
import com.FileManagerX.Factories.DefaultFactory;
import com.FileManagerX.Factories.ServerFactory;
import com.FileManagerX.Globals.Configurations;
import com.FileManagerX.Globals.Datas;
import com.FileManagerX.Interfaces.IDBManager;

public class CFG extends com.FileManagerX.BasicModels.BaseFile {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	private List<String> content;
	private com.FileManagerX.BasicCollections.Configs configs;
	private boolean error;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	public boolean setContent(List<String> content) {
		if(content == null) {
			return false;
		}
		this.content = content;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	public List<String> getContent() {
		return this.content;
	}
	public com.FileManagerX.BasicCollections.Configs getConfigs() {
		if(configs != null) { return this.configs; }
		
		configs = new com.FileManagerX.BasicCollections.Configs();
		for(String i : content) {
			com.FileManagerX.BasicModels.Config ic = new com.FileManagerX.BasicModels.Config(i);
			if(!ic.isEmpty()) {
				configs.add(ic);
			}
		}
		return configs;
	}
	public boolean isErrorOccurs() {
		return this.error;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	public CFG() {
		initThis();
	}
	public CFG(String url){
		super(url);
		initThis();
	}
	public CFG(File localFile) {
		super(localFile);
		initThis();
	}
	private void initThis() {
		if(content == null) {
			content = new ArrayList<String>();
		}
		content.clear();
		error = false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	public void clear() {
		super.clear();
		initThis();
	}
	public String toString() {
		return super.toString();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	public boolean load() {
		this.content.clear();
		this.configs.clear();
		
		error |= !this.createCFG();
		error |= !this.loadFromLocal(true);
		error |= !this.loadCFG();
		
		return !error;
	}
	public boolean save() {
		this.content.clear();
		this.configs.clear();
		
		error |= !this.saveCFG();
		error |= !this.saveToLocal();
		return !error;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	public boolean loadFromDataBase() {
		return true;
	}
	public boolean saveToDataBase() {
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	public boolean loadFromRemote() {
		return true;
	}
	public boolean saveToRemote() {
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	public boolean loadFromLocal(boolean removeEmptyLine) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(super.getUrl())));
			String line = br.readLine();
			while(line != null) {
				if(line.length() == 0 && removeEmptyLine) {
					line = br.readLine();
					continue;
				}
				this.content.add(line);
				line = br.readLine();
			}
			try {
				br.close();
			} catch(Exception e) {
				;
			}
			return true;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.COMMON_FILE_READ_FAILED.register(e.toString());
			return false;
		}
	}
	public boolean saveToLocal() {
		return this.saveAs(this.getUrl());
	}
	public boolean saveAs(String localUrl) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(localUrl), false));
			for(String i : this.content) {
				bw.write(i);
				bw.newLine();
				bw.flush();
			}
			bw.close();
			return true;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.COMMON_FILE_WRITE_FAILED.register(e.toString());
			return false;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean createCFG() {
		java.io.File f = new java.io.File(com.FileManagerX.Tools.Pathes.getFile_CFG());
		if(f.exists()) {
			return true;
		}
		try {
			return f.createNewFile();
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.COMMON_FILE_OPERATE_FAILED.register(e.toString());
			return false;
		}
	}
	public boolean loadCFG() {
		Configs cs = this.getConfigs();
		
		Config c = cs.fetch("StartType");
		if(c == null) {
			com.FileManagerX.BasicEnums.ErrorType.CFG_NOT_EXIST.register("Not Exist StartType");
			return false;
		}
		com.FileManagerX.BasicEnums.StartType type = com.FileManagerX.BasicEnums.StartType.Server;
		try {
			type = com.FileManagerX.BasicEnums.StartType.valueOf(c.fetchFirstString());
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.CFG_WRONG_CONTENT.register("Wrong StartType, No such StartType: " + c.getValue());
			return false;
		}
		// 显示窗体
		try {
			for(int i=0; i<c.getItems().length; i++) {
				if(c.getString(i).equals("ShowForm")) {
					Configurations.ShowForm = true;
					Datas.Form_Test.setVisible(true);
					break;
				}
			}
		} catch(Exception e) {
			;
		}
		
		Configurations.StartType = type;
		Configurations.IsServer = type.equals(com.FileManagerX.BasicEnums.StartType.Server);
		Configurations.IsDepot = type.equals(com.FileManagerX.BasicEnums.StartType.Depot);
		Configurations.IsClient = type.equals(com.FileManagerX.BasicEnums.StartType.Client);
		
		// 创建新文件
		try {
			for(int i=0; i<c.getItems().length; i++) {
				if(c.getString(i).equals("New")) {
					this.saveCFGCore(type, true);
					return false;
				}
			}
		} catch(Exception e) {
			;
		}
		
		boolean ok = true;
		if(type.equals(com.FileManagerX.BasicEnums.StartType.Server)) {
			ok &= loadServerCFG();
		}
		if(type.equals(com.FileManagerX.BasicEnums.StartType.Depot)) {
			ok &= loadDepotCFG();
		}
		if(type.equals(com.FileManagerX.BasicEnums.StartType.Client)) {
			ok &= loadClientCFG();
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
			if(Configurations.IsServer) {
				c = cs.fetch("NetType");
				Configurations.NetType = com.FileManagerX.BasicEnums.NetType.valueOf(c.fetchFirstString());
			}
		} catch(Exception e) {
			;
		}
		
		return ok;
	}
	public boolean saveCFG() {
		if(Configurations.StartType.equals(com.FileManagerX.BasicEnums.StartType.Server)) {
			return saveServerCFG();
		}
		if(Configurations.StartType.equals(com.FileManagerX.BasicEnums.StartType.Depot)) {
			return saveDepotCFG();
		}
		if(Configurations.StartType.equals(com.FileManagerX.BasicEnums.StartType.Client)) {
			return saveClientCFG();
		}
		
		return false;
	}
	public boolean resetCFG() {
		if(Configurations.StartType.equals(com.FileManagerX.BasicEnums.StartType.Server)) {
			return resetServerCFG();
		}
		if(Configurations.StartType.equals(com.FileManagerX.BasicEnums.StartType.Depot)) {
			return resetDepotCFG();
		}
		if(Configurations.StartType.equals(com.FileManagerX.BasicEnums.StartType.Client)) {
			return resetClientCFG();
		}
		return false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private boolean loadServerCFG() {

		this.error |= !this.loadServer_Configurations();
		this.error |= !this.loadServer_ThisMachine();
		this.error |= !this.loadServer_ThisUser();
		this.error |= !this.loadServer_DBManager();
		this.error |= !this.loadServer_Supports();
		this.error |= !this.loadServer_MyMachine();
		this.error |= !this.loadServer_MyNet();
		this.error |= !this.loadServer_DBManagers();
		this.error |= !this.loadServer_AddDepotAndDataBase();
		this.error |= !this.loadServer_DelDepotAndDataBase();
		
		return !this.error;
	}
	private boolean saveServerCFG() {
		return this.saveCFGCore(com.FileManagerX.BasicEnums.StartType.Server, false);
	}
	private boolean resetServerCFG() {
		// delete all depots
		for(IDBManager dbm : Datas.DBManagers.getContent()) {
			dbm.delete();
		}
		
		// clear Indexes
		Configurations.Next_FileIndex = 0;
		Configurations.Next_UserIndex = 0;
		Configurations.Next_MachineIndex = 0;
		Configurations.Next_DepotIndex = 0;
		Configurations.Next_DataBaseIndex = 0;
		Configurations.This_MachineIndex = 0;
		Configurations.This_UserIndex = 0;
		Configurations.Server_MachineIndex = 0;
		Configurations.Server_UserIndex = 0;
		
		Datas.ThisMachine.setIndex(0);
		Datas.ThisUser.setIndex(0);
		Datas.ServerMachine.setIndex(0);
		Datas.ServerUser.setIndex(0);
		
		if(Datas.DBManager.getDBInfo() != null) {
			Datas.DBManager.getDBInfo().setIndex(0);
			Datas.DBManager.getDBInfo().setDepotIndex(0);
			if(Datas.DBManager.getDBInfo().getDepotInfo() != null) {
				Datas.DBManager.getDBInfo().getDepotInfo().setIndex(0);
				Datas.DBManager.getDBInfo().getDepotInfo().setDBIndex(0);
			}
		}
		
		// save to file
		return saveServerCFG();
	}
	
	private boolean loadDepotCFG() {
		
		this.error |= !this.loadDepot_Configurations();
		this.error |= !this.loadDepot_ThisMachine();
		this.error |= !this.loadDepot_ThisUser();
		this.error |= !this.loadDepot_DBManager();
		this.error |= !this.loadDepot_BuildConnection();
		this.error |= !this.loadDepot_Supports();
		this.error |= !this.loadDepot_MyMachine();
		this.error |= !this.loadDepot_MyNet();
		this.error |= !this.loadDepot_DBManagers();
		this.error |= !this.loadDepot_AddDepotAndDataBase();
		this.error |= !this.loadDepot_DelDepotAndDataBase();
		
		return !this.error;
	}
	private boolean saveDepotCFG() {
		return this.saveCFGCore(com.FileManagerX.BasicEnums.StartType.Depot, false);
	}
	private boolean resetDepotCFG() {
		// delete all depots
		for(IDBManager dbm : Datas.DBManagers.getContent()) {
			dbm.delete();
		}
		
		// commands
		com.FileManagerX.Commands.RemoveUnit rd = new com.FileManagerX.Commands.RemoveUnit();
		rd.setUnit(com.FileManagerX.DataBase.Unit.Depot);
		rd.setThis(Datas.ServerConnection);
		com.FileManagerX.Commands.RemoveUnit rdb = new com.FileManagerX.Commands.RemoveUnit();
		rdb.setUnit(com.FileManagerX.DataBase.Unit.DataBase);
		rdb.setThis(Datas.ServerConnection);
		
		// unregister
		for(IDBManager dbm : Datas.DBManagers.getContent()) {
			try {
				rd.setQueryConditions("[&] Index = " + dbm.getDBInfo().getDepotInfo().getIndex());
				rd.send();
				rd.receive();
				
				rdb.setQueryConditions("[&] Index = " + dbm.getDBInfo().getDepotIndex());
				rd.send();
				rd.receive();
				
			} catch(Exception e) {
				;
			}
		}
		
		// clear
		Datas.DBManagers.clear();
		
		// clear Indexes
		Configurations.Next_FileIndex = 0;
		Configurations.Next_UserIndex = 0;
		Configurations.Next_MachineIndex = 0;
		Configurations.Next_DepotIndex = 0;
		Configurations.Next_DataBaseIndex = 0;
		
		// save to file
		return saveDepotCFG();
	}
	
	private boolean loadClientCFG() {
		return this.loadDepotCFG();
	}
	private boolean saveClientCFG() {
		return this.saveCFGCore(com.FileManagerX.BasicEnums.StartType.Client, false);
	}
	private boolean resetClientCFG() {
		return this.resetDepotCFG();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private boolean loadServer_Configurations() {
		if(this.error) { return false; }
		
		try {
			Configurations.Next_MachineIndex = this.configs.fetch("Next_MachineIndex").getLong();
		}catch(Exception e) {
			Configurations.Next_MachineIndex = 0;
		}
		try {
			Configurations.Next_DepotIndex = this.configs.fetch("Next_DepotIndex").getLong();
		}catch(Exception e) {
			Configurations.Next_DepotIndex = 0;
		}
		try {
			Configurations.Next_DataBaseIndex = this.configs.fetch("Next_DataBaseIndex").getLong();
		}catch(Exception e) {
			Configurations.Next_DataBaseIndex = 0;
		}
		try {
			Configurations.Next_FileIndex = this.configs.fetch("Next_FileIndex").getLong();
		}catch(Exception e) {
			Configurations.Next_FileIndex = 0;
		}
		try {
			Configurations.Next_UserIndex = this.configs.fetch("Next_UserIndex").getLong();
		}catch(Exception e) {
			Configurations.Next_UserIndex = 0;
		}
		
		try {
			Configurations.This_MachineIndex = Configurations.Server_MachineIndex;
		}catch(Exception e) {
			Configurations.This_MachineIndex = 0;
		}
		try {
			Configurations.This_UserIndex = Configurations.Server_UserIndex;
		}catch(Exception e) {
			Configurations.This_UserIndex = 0;
		}
		
		return true;
	}
	private boolean loadServer_ThisMachine() {
		if(this.error) { return false; }
		
		MachineInfo machine = ServerFactory.createServerMachine();
		Datas.ThisMachine.copyReference(machine);
		Datas.ServerMachine.copyReference(machine);
		
		return true;
	}
	private boolean loadServer_ThisUser() {
		if(this.error) { return false; }
		
		User user = ServerFactory.createServerUser();
		Datas.ThisUser.copyReference(user);
		Datas.ServerUser.copyReference(user);
		
		return true;
	}
	private boolean loadServer_DBManager() {
		if(this.error) { return false; }
		
		DepotInfo depotInfo = ServerFactory.createServerDepotInfo();
		DataBaseInfo dbInfo = ServerFactory.createServerDataBaseInfo();
		
		try {
			com.FileManagerX.BasicEnums.DataBaseType type = 
					com.FileManagerX.BasicEnums.DataBaseType.valueOf(
							this.configs.fetch("ServerDataBaseType").getString()
						);
			dbInfo.setType(type);
		}catch(Exception e) {
			dbInfo.setType(com.FileManagerX.BasicEnums.DataBaseType.TXT);
		}
		try {
			java.lang.String url = this.configs.fetch("ServerDataBaseUrl").getString();
			if(url != null && url.length() != 0) {
				dbInfo.setUrl(url);
			}
		}catch(Exception e) {
			;
		}
		
		if(dbInfo.getType().equals(com.FileManagerX.BasicEnums.DataBaseType.TXT)) {
			java.io.File dbFolder = new java.io.File(dbInfo.getUrl());
			if(!dbFolder.exists() || !dbFolder.isDirectory()) {
				java.lang.String url = com.FileManagerX.Tools.Pathes.getFolder_DBS(0);
				boolean ok = com.FileManagerX.Tools.Pathes.createFolder_DBS(0);
				if(!ok) {
					com.FileManagerX.BasicEnums.ErrorType.COMMON_FILE_OPERATE_FAILED.register(
							"Create Folder_DBS:0 Failed");
					return false;
				}
				dbInfo.setUrl(url);
			}
		}
		
		depotInfo.setDBInfo(dbInfo);
		dbInfo.setDepotInfo(depotInfo);
		depotInfo.setDBIndex();
		dbInfo.setDepotIndex();
		
		Datas.DBManager.setDBInfo(dbInfo);
		Datas.DBManager.connect();
		if(!Datas.DBManager.isConnected()) {
			com.FileManagerX.BasicEnums.ErrorType.DB_CONNECT_FAILED.register();
			return false;
		}
		
		Datas.DBManager.create();
		Datas.DBManager.setUnit(Unit.Machine);
		Datas.DBManager.update(Datas.ThisMachine);
		Datas.DBManager.setUnit(Unit.Depot);
		Datas.DBManager.update(depotInfo);
		Datas.DBManager.setUnit(Unit.DataBase);
		Datas.DBManager.update(dbInfo);
		
		return true;
	}
	private boolean loadServer_MyMachine() {
		if(this.error) { return false; }
		
		Datas.DBManager.setUnit(Unit.Machine);
		MachineInfos machines = (MachineInfos)Datas.DBManager.querys
				("[&] UserIndex = " + Configurations.This_UserIndex);
		Datas.MyMachines.copyReference(machines);
		
		return true;
	}
	private boolean loadServer_MyNet() {
		if(this.error) { return false; }
		
		try {
			Datas.MyNet.input(this.configs.fetch("MyNet").getValue());
			for(int i=0; i<Datas.MyNet.getAmount(); i++) {
				com.FileManagerX.MyNet.Group g = new com.FileManagerX.MyNet.Group();
				g.input(this.configs.fetch("MyNetGroup").getValue());
				for(int j=0; j<g.getAmount(); j++) {
					com.FileManagerX.MyNet.User u = new com.FileManagerX.MyNet.User();
					u.input(this.configs.fetch("MyNetUser").getValue());
					for(int k=0; k<u.getAmount(); k++) {
						com.FileManagerX.MyNet.Machine m = new com.FileManagerX.MyNet.Machine();
						m.input(this.configs.fetch("MyNetMachine").getValue());
						for(int n=0; n<m.getAmount(); n++) {
							com.FileManagerX.MyNet.Depot d = new com.FileManagerX.MyNet.Depot();
							d.input(this.configs.fetch("MyNetDepot").getValue());
							m.addDepot(d);
						}
						u.addMachine(m);
					}
					g.addUser(u);
				}
				Datas.MyNet.addGroup(g);
			}
			Datas.MyNet.refresh();
			
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.CFG_WRONG_CONTENT.register(e.toString());
		}
		
		return true;
	}
	private boolean loadServer_Supports() {
		if(this.error) { return false; }
		
		while(true) {
			Config c = this.configs.fetch("Support");
			if(c == null) {
				break;
			}
			try {
				Support s = new Support();
				s.setType(com.FileManagerX.BasicEnums.FileType.valueOf(c.fetchFirstString()));
				if(!c.getIsOK()) { continue; }
				s.setExtension(c.fetchFirstString());
				if(!c.getIsOK()) { continue; }
				s.setShowExtension(new java.lang.String(s.getExtension()));
				if(!c.getIsOK()) { continue; }
				s.setHideExtension(c.fetchFirstString());
				if(!c.getIsOK()) { continue; }
				s.setIsSupport(true);
				Datas.Supports.add(s);
				
				Support s2 = new Support();
				s2.setExtension(new java.lang.String(s.getHideExtension()));
				s2.setType(s.getType());
				s2.setShowExtension(new java.lang.String(s.getShowExtension()));
				s2.setHideExtension(new java.lang.String(s.getHideExtension()));
				s2.setIsSupport(false);
				if(s2.getExtension() != null && s2.getExtension().length() > 0) {
					Datas.Supports.add(s2);
				}
			} catch(Exception e) {
				com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			}
		}
		
		return true;
	}
	private boolean loadServer_DBManagers() {
		if(this.error) { return false; }
		
		QueryCondition qc = new QueryCondition();
		qc.setItemName("MachineIndex");
		qc.setValue(java.lang.String.valueOf(Configurations.This_MachineIndex));
		
		Datas.DBManager.setUnit(Unit.DataBase);
		DataBaseInfos dbs = (DataBaseInfos) Datas.DBManager.querys(qc);
		if(dbs != null) {
			for(int i=0; i<dbs.getContent().size(); i++) {
				DataBaseInfo db = dbs.getContent().get(i);
				if(db.getIndex() != Datas.DBManager.getDBInfo().getIndex()) {
					qc.setItemName("Index");
					qc.setValue(java.lang.String.valueOf(db.getDepotIndex()));
					Datas.DBManager.setUnit(Unit.Depot);
					DepotInfo depot = (DepotInfo) Datas.DBManager.query(qc);
					
					db.setDepotInfo(depot);
					depot.setDBInfo(db);
					db.setMachineInfo(Datas.ThisMachine);
					depot.setMachineInfo(Datas.ThisMachine);
					IDBManager dbmanager = db.getManager();
					dbmanager.connect();
					Datas.DBManagers.add(dbmanager);
				}
			}
		}
		
		return true;
	}
	private boolean loadServer_AddDepotAndDataBase() {
		if(this.error) { return false; }
		
		while(true) {
			Config cdepot = this.configs.fetch("+Depot");
			if(cdepot == null) {
				break;
			}
			Config cdb = this.configs.fetch("+DataBase");
			if(cdb == null) {
				break;
			}
			
			DepotInfo depotInfo = DefaultFactory.createDefaultDepotInfo();
			DataBaseInfo dbInfo = depotInfo.getDBInfo();
			
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
			
			QueryCondition qc1 = new QueryCondition();
			qc1.setItemName("Name");
			qc1.setValue("'" + depotInfo.getName() + "'");
			QueryCondition qc2 = new QueryCondition();
			qc2.setItemName("MachineIndex");
			qc2.setValue("" + Configurations.Server_MachineIndex);
			QueryConditions qcs = new QueryConditions();
			qcs.add(qc1);
			qcs.add(qc2);
			
			Datas.DBManager.setUnit(com.FileManagerX.DataBase.Unit.Depot);
			if(Datas.DBManager.query(qcs) != null) {
				continue;
			}
			
			qcs.getContent().get(0).setValue("'" + dbInfo.getName() + "'");
			Datas.DBManager.setUnit(com.FileManagerX.DataBase.Unit.DataBase);
			if(Datas.DBManager.query(qcs) != null) {
				continue;
			}
			
			Datas.DBManager.setUnit(com.FileManagerX.DataBase.Unit.Depot);
			if(!Datas.DBManager.update(dbInfo)) {
				continue;
			}
			Datas.DBManager.setUnit(com.FileManagerX.DataBase.Unit.DataBase);
			if(!Datas.DBManager.update(depotInfo)) {
				continue;
			}
			
			if(dbInfo.getType().equals(com.FileManagerX.BasicEnums.DataBaseType.TXT) &&
					dbInfo.getUrl().length() == 0) {
				java.lang.String url = com.FileManagerX.Tools.Pathes.getFolder_DBS(dbInfo.getIndex());
				boolean ok = com.FileManagerX.Tools.Pathes.createFolder_DBS(dbInfo.getIndex());
				if(!ok) {
					com.FileManagerX.BasicEnums.ErrorType.COMMON_FILE_OPERATE_FAILED.register
						("Create DataBaseFolder Failed");
					continue;
				}
				dbInfo.setUrl(url);
				Datas.DBManager.setUnit(com.FileManagerX.DataBase.Unit.DataBase);
				if(!Datas.DBManager.update(dbInfo)) {
					com.FileManagerX.BasicEnums.ErrorType.COMMANDS_EXECUTE_FAILED.register
						("Update DataBase Url Failed", "Url = " + url);
					continue;
				}
			}
			
			Folder f = new Folder(depotInfo.getUrl());
			long currentFileIndex = Configurations.Next_FileIndex;
			f.setIndex();
			f.setMachine(Configurations.This_MachineIndex);
			f.setDepot(depotInfo.getIndex());
			f.setDataBase(dbInfo.getIndex());
			f.load();
			Configurations.Next_FileIndex = currentFileIndex;
			BaseFiles files = new BaseFiles();
			f.load(files);
			files.sortIncrease();
			
			IDBManager dbmanager = dbInfo.getManager();
			Datas.DBManagers.add(dbmanager);
			dbmanager.connect();
			if(dbmanager.isConnected()) {
				dbmanager.clear();
				dbmanager.create();
			}
			for(BaseFile i : files.getContent()) {
				if(com.FileManagerX.BasicEnums.FileType.Folder.equals(i.getType())) {
					dbmanager.setUnit(Unit.Folder);
				}
				else {
					dbmanager.setUnit(Unit.BaseFile);
				}
				dbmanager.update(i);
			}
		}
		
		return true;
	}
	private boolean loadServer_DelDepotAndDataBase() {
		if(this.error) { return false; }
		
		while(true) {
			Config cdepot = this.configs.fetch("-Depot");
			if(cdepot == null) {
				break;
			}
			Config cdb = this.configs.fetch("-DataBase");
			if(cdb == null) {
				break;
			}
			
			QueryCondition qc1 = new QueryCondition();
			qc1.setItemName("Name");
			qc1.setValue("'" + cdepot.getValue() + "'");
			QueryCondition qc2 = new QueryCondition();
			qc2.setItemName("MachineIndex");
			qc2.setValue("" + Configurations.Server_MachineIndex);
			QueryConditions qcs = new QueryConditions();
			qcs.add(qc1);
			qcs.add(qc2);
			
			Datas.DBManager.setUnit(com.FileManagerX.DataBase.Unit.Depot);
			DepotInfo depotInfo = (DepotInfo)Datas.DBManager.query(qcs);
			if(depotInfo == null) {
				continue;
			}
			
			qcs.getContent().get(0).setValue("'" + cdb.getValue() + "'");
			Datas.DBManager.setUnit(com.FileManagerX.DataBase.Unit.DataBase);
			DataBaseInfo dbInfo = (DataBaseInfo) Datas.DBManager.query(qcs);
			if(dbInfo == null) {
				continue;
			}
			
			if(dbInfo.getDepotIndex() != depotInfo.getIndex()) {
				continue;
			}
			
			Datas.DBManager.setUnit(com.FileManagerX.DataBase.Unit.Depot);
			Datas.DBManager.remove(depotInfo);
			Datas.DBManager.setUnit(com.FileManagerX.DataBase.Unit.DataBase);
			Datas.DBManager.remove(dbInfo);
			IDBManager dbm = Datas.DBManagers.fetchDepotIndex(depotInfo.getIndex());
			if(dbm != null) {
				dbm.delete();
			}
		}
		
		return true;
	}
	
	private boolean loadDepot_Configurations() {
		if(this.error) { return false; }
		
		try {
			Configurations.Next_MachineIndex = this.configs.fetch("Next_MachineIndex").getLong();
		}catch(Exception e) {
			Configurations.Next_MachineIndex = 0;
		}
		try {
			Configurations.Next_DepotIndex = this.configs.fetch("Next_DepotIndex").getLong();
		}catch(Exception e) {
			Configurations.Next_DepotIndex = 0;
		}
		try {
			Configurations.Next_DataBaseIndex = this.configs.fetch("Next_DataBaseIndex").getLong();
		}catch(Exception e) {
			Configurations.Next_DataBaseIndex = 0;
		}
		try {
			Configurations.Next_FileIndex = this.configs.fetch("Next_FileIndex").getLong();
		}catch(Exception e) {
			Configurations.Next_FileIndex = 0;
		}
		try {
			Configurations.Next_UserIndex = this.configs.fetch("Next_UserIndex").getLong();
		}catch(Exception e) {
			Configurations.Next_UserIndex = 0;
		}
		
		try {
			Configurations.This_MachineIndex = this.configs.fetch("This_MachineIndex").getLong();
		}catch(Exception e) {
			Configurations.This_MachineIndex = 0;
		}
		try {
			Configurations.This_UserIndex = this.configs.fetch("This_UserIndex").getLong();
		}catch(Exception e) {
			Configurations.This_UserIndex = 0;
		}
		
		return true;
	}
	private boolean loadDepot_ThisMachine() {
		if(this.error) { return false; }
		MachineInfo machine = com.FileManagerX.Factories.DefaultFactory.createDefaultMachineInfo();
		machine.setIndex(Configurations.This_MachineIndex);
		
		try {
			java.lang.String name = this.configs.fetch("MachineName").getValue();
			if(name != null && name.length() > 0) {
				machine.setName(name);
			}
		}catch(Exception e) {
			;
		}
		try {
			java.lang.String ip = this.configs.fetch("MachineIP").getValue();
			if(com.FileManagerX.Tools.Url.isIp(ip)) {
				machine.setIp(ip);
			}
		}catch(Exception e) {
			;
		}
		try {
			int port = com.FileManagerX.Tools.Port.getIdleSocketPort();
			if(port < 1024) {
				return false;
			}
			else {
				machine.setPort(port);
			}
		}catch(Exception e) {
			;
		}
		
		Datas.ThisMachine.copyReference(machine);
		return true;
	}
	private boolean loadDepot_ThisUser() {
		if(this.error) { return false; }
		
		User user = com.FileManagerX.Factories.DefaultFactory.createDefaultUser();
		try {
			java.lang.String name = this.configs.fetch("UserLoginName").getValue();
			if(name != null && name.length() > 0) {
				user.setLoginName(name);
			}
		} catch(Exception e) {
			;
		}
		try {
			java.lang.String pw = this.configs.fetch("UserPassword").getValue();
			if(pw != null && pw.length() > 0) {
				user.setPassword(pw);
			}
		} catch(Exception e) {
			;
		}
		
		Datas.ThisUser.copyReference(user);
		Datas.ThisUser.setIndex(Configurations.This_UserIndex);
		
		return true;
	}
	private boolean loadDepot_DBManager() {
		if(this.error) { return false; }
		return true;
	}
	private boolean loadDepot_BuildConnection() {
		if(this.error) { return false; }
		Config c = null;
		
		if(!com.FileManagerX.Factories.CommunicatorFactory.createRunningClientConnection()) {
			com.FileManagerX.BasicEnums.ErrorType.COMMUNICATOR_BUILD_SOCKED_FAILED.register();
			return false;
		}
		
		c = this.configs.fetch("RegisterUser");
		if(c != null && c.getItemsSize() == 3) {
			com.FileManagerX.Commands.RegisterUser rgu = new com.FileManagerX.Commands.RegisterUser();
			rgu.setThis(c.getString(0), c.getString(1), c.getString(2), Datas.ServerConnection);
			rgu.send();
			com.FileManagerX.Replies.RegisterUser reprgu = (com.FileManagerX.Replies.RegisterUser)rgu.receive();
			if(reprgu == null || !reprgu.isOK()) { return false; }
			
			Datas.ServerConnection.setServerUser(reprgu.getUser());
			Datas.ServerConnection.setClientUser(reprgu.getUser());
		}
		c = this.configs.fetch("RegisterMachine");
		if(c != null && c.getItemsSize() == 1) {
			com.FileManagerX.BasicModels.MachineInfo machine = DefaultFactory.createDefaultMachineInfo();
			machine.setName(c.getString());
			machine.setPort(com.FileManagerX.Tools.Port.port);
			com.FileManagerX.Commands.RegisterMachine rgm = new com.FileManagerX.Commands.RegisterMachine();
			rgm.setThis(machine);
			com.FileManagerX.Replies.RegisterMachine reprgm = 
					(com.FileManagerX.Replies.RegisterMachine)rgm.receive();
			if(reprgm == null || !reprgm.isOK()) { return false; }
			
			Datas.ServerConnection.setClientMachineInfo(machine);
		}
		
		java.lang.String reason = Datas.ServerConnection.login();
		if(reason.length() != 0) {
			com.FileManagerX.BasicEnums.ErrorType.COMMANDS_EXECUTE_FAILED.register("Login Failed", reason);
			return false;
		}
		
		com.FileManagerX.Globals.Datas.ServerMachine.copyReference(Datas.ServerConnection.getServerMachineInfo());
		com.FileManagerX.Globals.Datas.ThisMachine.copyReference(Datas.ServerConnection.getClientMachineInfo());
		com.FileManagerX.Globals.Datas.ServerUser.copyReference(Datas.ServerConnection.getServerUser());
		com.FileManagerX.Globals.Datas.ThisUser.copyReference(Datas.ServerConnection.getClientUser());
		
		com.FileManagerX.Globals.Configurations.Server_MachineIndex = Datas.ServerMachine.getIndex();
		com.FileManagerX.Globals.Configurations.This_MachineIndex = Datas.ThisMachine.getIndex();
		com.FileManagerX.Globals.Configurations.Server_UserIndex = Datas.ServerUser.getIndex();
		com.FileManagerX.Globals.Configurations.This_UserIndex = Datas.ThisUser.getIndex();
		
		return true;
	}
	private boolean loadDepot_Supports() {
		if(this.error) { return false; }
		
		while(true) {
			Config c = this.configs.fetch("Support");
			if(c == null) { break; }
			
			try {
				Support s = new Support();
				s.setType(com.FileManagerX.BasicEnums.FileType.valueOf(c.fetchFirstString()));
				if(!c.getIsOK()) { continue; }
				s.setExtension(c.fetchFirstString());
				if(!c.getIsOK()) { continue; }
				s.setShowExtension(new java.lang.String(s.getExtension()));
				if(!c.getIsOK()) { continue; }
				s.setHideExtension(c.fetchFirstString());
				if(!c.getIsOK()) { continue; }
				s.setIsSupport(true);
				Datas.Supports.add(s);
				
				Support s2 = new Support();
				s2.setExtension(new java.lang.String(s.getHideExtension()));
				s2.setType(s.getType());
				s2.setShowExtension(new java.lang.String(s.getShowExtension()));
				s2.setHideExtension(new java.lang.String(s.getHideExtension()));
				s2.setIsSupport(false);
				if(s2.getExtension() != null && s2.getExtension().length() > 0) {
					Datas.Supports.add(s2);
				}
			} catch(Exception e) {
				com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			}
		}
		
		return true;
	}
	private boolean loadDepot_MyMachine() {
		if(this.error) { return false; }
		
		com.FileManagerX.Commands.QueryUnits qms = new com.FileManagerX.Commands.QueryUnits();
		qms.setThis(Unit.Machine, "[&] UserIndex = " + Configurations.This_UserIndex, Datas.ServerConnection);
		qms.send();
		com.FileManagerX.Replies.QueryUnits rep = (com.FileManagerX.Replies.QueryUnits)qms.receive();
		MachineInfos ms = (MachineInfos)rep.getResults();
		Datas.MyMachines.copyReference(ms);
		
		return true;
	}
	private boolean loadDepot_MyNet() {
		if(this.error) { return false; }
		
		try {
			Datas.MyNet.input(this.configs.fetch("MyNet").getValue());
			for(int i=0; i<Datas.MyNet.getAmount(); i++) {
				com.FileManagerX.MyNet.Group g = new com.FileManagerX.MyNet.Group();
				g.input(this.configs.fetch("MyNetGroup").getValue());
				for(int j=0; j<g.getAmount(); j++) {
					com.FileManagerX.MyNet.User u = new com.FileManagerX.MyNet.User();
					u.input(this.configs.fetch("MyNetUser").getValue());
					for(int k=0; k<u.getAmount(); k++) {
						com.FileManagerX.MyNet.Machine m = new com.FileManagerX.MyNet.Machine();
						m.input(this.configs.fetch("MyNetMachine").getValue());
						for(int n=0; n<m.getAmount(); n++) {
							com.FileManagerX.MyNet.Depot d = new com.FileManagerX.MyNet.Depot();
							d.input(this.configs.fetch("MyNetDepot").getValue());
							m.addDepot(d);
						}
						u.addMachine(m);
					}
					g.addUser(u);
				}
				Datas.MyNet.addGroup(g);
			}
			Datas.MyNet.refresh();
			
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.CFG_WRONG_CONTENT.register(e.toString());
		}
		
		return true;
	}
	private boolean loadDepot_DBManagers() {
		if(this.error) { return false; }
		
		DepotInfos des = new DepotInfos();
		DataBaseInfos dbs = new DataBaseInfos();
		
		// QueryDepots
		java.lang.String qdsqcs = "[&] MachineIndex = " + Configurations.This_MachineIndex;
		com.FileManagerX.Commands.QueryUnits qds = new com.FileManagerX.Commands.QueryUnits();
		qds.setThis(com.FileManagerX.DataBase.Unit.Depot, qdsqcs, Datas.ServerConnection);
		qds.send();
		com.FileManagerX.Replies.QueryUnits repqds = (com.FileManagerX.Replies.QueryUnits)qds.receive();
		if(repqds != null && repqds.isOK()) { des = (DepotInfos) repqds.getResults(); }
		
		// QueryDataBases
		java.lang.String qdbsqcs = "[&] MachineIndex = " + Configurations.This_MachineIndex;
		com.FileManagerX.Commands.QueryUnits qdbs = new com.FileManagerX.Commands.QueryUnits();
		qdbs.setThis(com.FileManagerX.DataBase.Unit.DataBase, qdbsqcs, Datas.ServerConnection);
		qdbs.send();
		com.FileManagerX.Replies.QueryUnits repqdbs = (com.FileManagerX.Replies.QueryUnits)qdbs.receive();
		if(repqdbs != null && repqdbs.isOK()) { dbs = (DataBaseInfos) repqdbs.getResults(); }
		
		// load
		for(int i=0; i<des.size(); i++) {
			DepotInfo de = des.getContent().get(i);
			for(int j=0; j<dbs.size(); j++) {
				DataBaseInfo db = dbs.getContent().get(j);
				if(de.getIndex() == db.getDepotIndex()) {
					de.setMachineInfo(Datas.ThisMachine);
					db.setMachineInfo(Datas.ThisMachine);
					de.setDBInfo(db);
					db.setDepotInfo(de);
					IDBManager dbm = db.getManager();
					dbm.connect();
					if(dbm.isConnected()) {
						Datas.DBManagers.add(dbm);
					}
				}
			}
		}
		
		return true;
	}
	private boolean loadDepot_AddDepotAndDataBase() {
		if(this.error) { return false; }
		
		while(true) {
			
			Config cdepot = this.configs.fetch("+Depot");
			if(cdepot == null) { break; }
			Config cdb = this.configs.fetch("+DataBase");
			if(cdb == null) { break; }
			
			// Build Depot and DataBase
			DepotInfo depotInfo = DefaultFactory.createDefaultDepotInfo();
			DataBaseInfo dbInfo = depotInfo.getDBInfo();
			
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
				com.FileManagerX.BasicEnums.DataBaseType type = com.FileManagerX.BasicEnums.DataBaseType.valueOf(
						cdb.fetchFirstString());
				dbInfo.setType(type);
			} catch(Exception e) {
				dbInfo.setType(com.FileManagerX.BasicEnums.DataBaseType.TXT);
			}
			try {
				java.lang.String url = cdb.fetchFirstString();
				dbInfo.setUrl(url);
			} catch(Exception e) {
				;
			}
			
			// QueryConfigurations
			com.FileManagerX.Commands.QueryConfigurations qcfg = new com.FileManagerX.Commands.QueryConfigurations();
			qcfg.setThis(Datas.ServerConnection);
			qcfg.send();
			com.FileManagerX.Replies.QueryConfigurations repqcfg = 
					(com.FileManagerX.Replies.QueryConfigurations)qcfg.receive();
			if(repqcfg == null) { return false; }
			if(!repqcfg.isOK()) {
				com.FileManagerX.BasicEnums.ErrorType.COMMANDS_EXECUTE_FAILED.register("QueryConfigurations Failed");
				return false;
			}
			
			depotInfo.setIndex(repqcfg.getNext_DepotIndex() + 1);
			dbInfo.setIndex(repqcfg.getNext_DataBaseIndex() + 1);
			depotInfo.setDBInfo(dbInfo);
			dbInfo.setDepotInfo(depotInfo);
			depotInfo.setDBIndex();
			dbInfo.setDepotIndex();
			
			// QueryDepot
			java.lang.String qdqcs = "[&] MachineIndex = " + depotInfo.getMachineInfo().getIndex() + 
					" , [&] Name = '" + depotInfo.getName() + "'";
			com.FileManagerX.Commands.QueryUnit qd = new com.FileManagerX.Commands.QueryUnit();
			qd.setThis(com.FileManagerX.DataBase.Unit.Depot, qdqcs, Datas.ServerConnection);
			qd.send();
			com.FileManagerX.Replies.QueryUnit repqd = (com.FileManagerX.Replies.QueryUnit)qd.receive();
			if(repqd == null) { continue; }
			if(repqd.isOK()) {
				com.FileManagerX.BasicEnums.ErrorType.COMMANDS_EXECUTE_FAILED.register(
						"Add Depot Failed, Existed: " + depotInfo.getName() );
				continue;
			}
			
			// QueryDataBase
			java.lang.String qdbqcs = "[&] MachineIndex = " + dbInfo.getMachineInfo().getIndex() + 
					" , [&] Name = '" + dbInfo.getName() + "'";
			com.FileManagerX.Commands.QueryUnit qdb = new com.FileManagerX.Commands.QueryUnit();
			qdb.setThis(com.FileManagerX.DataBase.Unit.DataBase, qdbqcs, Datas.ServerConnection);
			qdb.send();
			com.FileManagerX.Replies.QueryUnit repqdb = (com.FileManagerX.Replies.QueryUnit)qdb.receive();
			if(repqdb == null) { continue; }
			if(repqdb.isOK()) {
				com.FileManagerX.BasicEnums.ErrorType.COMMANDS_EXECUTE_FAILED.register(
						"Add DataBase Failed, Existed: " + dbInfo.getName() );
				continue;
			}
			
			//UpdateDepot
			com.FileManagerX.Commands.UpdateUnit ud = new com.FileManagerX.Commands.UpdateUnit();
			ud.setThis(com.FileManagerX.DataBase.Unit.Depot, depotInfo, Datas.ServerConnection);
			ud.send();
			com.FileManagerX.Replies.UpdateUnit repud = (com.FileManagerX.Replies.UpdateUnit)ud.receive();
			if(repud == null) { continue; }
			if(!repud.isOK()) {
				com.FileManagerX.BasicEnums.ErrorType.COMMANDS_EXECUTE_FAILED.register(
						"UpdataDepot Failed, " + repud.getFailedReason() );
			}
			depotInfo = (com.FileManagerX.BasicModels.DepotInfo)repud.getResult();
			
			//UpdateDataBase
			com.FileManagerX.Commands.UpdateUnit udb = new com.FileManagerX.Commands.UpdateUnit();
			udb.setThis(com.FileManagerX.DataBase.Unit.DataBase, dbInfo, Datas.ServerConnection);
			udb.send();
			com.FileManagerX.Replies.QueryUnit repudb = (com.FileManagerX.Replies.QueryUnit)udb.receive();
			if(repudb == null) { continue; }
			if(!repudb.isOK()) {
				com.FileManagerX.BasicEnums.ErrorType.COMMANDS_EXECUTE_FAILED.register( 
						"UpdateDataBase Failed, " + repudb.getFailedReason() );
				continue;
			}
			dbInfo = (com.FileManagerX.BasicModels.DataBaseInfo)repudb.getResult();
			
			// UpdateDataBaseUrl
			if(dbInfo.getType().equals(com.FileManagerX.BasicEnums.DataBaseType.TXT) &&
					dbInfo.getUrl().length() == 0) {
				java.lang.String url = com.FileManagerX.Tools.Pathes.getFolder_DBS(dbInfo.getIndex());
				boolean ok = com.FileManagerX.Tools.Pathes.createFolder_DBS(dbInfo.getIndex());
				if(!ok) {
					com.FileManagerX.BasicEnums.ErrorType.COMMON_FILE_OPERATE_FAILED.register(
							"Create DataBaseFolder Failed");
					continue;
				}
				dbInfo.setUrl(url);
				udb.send();
				repudb = (com.FileManagerX.Replies.QueryUnit)udb.receive();
				if(repudb == null || !repudb.isOK()) {
					com.FileManagerX.BasicEnums.ErrorType.COMMANDS_EXECUTE_FAILED.register(
							"Update DataBaseUrl Failed", "Url = " + url);
					continue;
				}
			}
			
			// Create DataBase
			dbInfo.setDepotInfo(depotInfo);
			depotInfo.setDBInfo(dbInfo);
			dbInfo.setDepotIndex();
			depotInfo.setDBIndex();
			
			Folder f = new Folder(depotInfo.getUrl());
			long currentFileIndex = Configurations.Next_FileIndex;
			f.setIndex();
			f.setMachine(Configurations.This_MachineIndex);
			f.setDepot(depotInfo.getIndex());
			f.setDataBase(dbInfo.getIndex());
			f.load();
			Configurations.Next_FileIndex = currentFileIndex;
			BaseFiles files = new BaseFiles();
			f.load(files);
			files.sortIncrease();
			
			IDBManager dbmanager = dbInfo.getManager();
			Datas.DBManagers.add(dbmanager);
			dbmanager.connect();
			if(dbmanager.isConnected()) {
				dbmanager.clear();
				dbmanager.create();
			}
			
			for(com.FileManagerX.BasicModels.BaseFile i : files.getContent()) {
				if(com.FileManagerX.BasicEnums.FileType.Folder.equals(i.getType())) {
					dbmanager.setUnit(com.FileManagerX.DataBase.Unit.Folder);
				}
				else {
					dbmanager.setUnit(com.FileManagerX.DataBase.Unit.BaseFile);
				}
				
				dbmanager.update(i);
			}
			
		}
		return true;
	}
	private boolean loadDepot_DelDepotAndDataBase() {
		if(this.error) { return false; }
		
		while(true) {
			Config cdepot = this.configs.fetch("-Depot");
			if(cdepot == null) {
				break;
			}
			Config cdb = this.configs.fetch("-DataBase");
			if(cdb == null) {
				break;
			}
			
			boolean ok = true;
			
			// unregister depot
			java.lang.String rdqcs = "[&] MachineIndex = " + Configurations.This_MachineIndex + 
					" , [&] Name = '" + cdepot.getValue() + "'";
			com.FileManagerX.Commands.RemoveUnit rd = new com.FileManagerX.Commands.RemoveUnit();
			rd.setThis(com.FileManagerX.DataBase.Unit.Depot, rdqcs, Datas.ServerConnection);
			rd.send();
			com.FileManagerX.Replies.QueryUnit reply = (com.FileManagerX.Replies.QueryUnit)rd.receive();
			ok &= reply != null && reply.isOK();
			
			// unregister database
			java.lang.String rdbqcs = "[&] MachineIndex = " + Configurations.This_MachineIndex + 
					" , [&] Name = '" + cdb.getValue() + "'";
			com.FileManagerX.Commands.RemoveUnit rdb = new com.FileManagerX.Commands.RemoveUnit();
			rdb.setThis(com.FileManagerX.DataBase.Unit.DataBase, rdbqcs, Datas.ServerConnection);
			rdb.send();
			rdb.receive();
			ok &= reply != null && reply.isOK();
			
			// operate failed
			if(!ok) {
				com.FileManagerX.BasicEnums.ErrorType.COMMANDS_EXECUTE_FAILED.register(
						com.FileManagerX.BasicEnums.ErrorLevel.Warning,
						"Delete Depot or DataBase Failed");
			}
			
			// remove database
			IDBManager dbm = Datas.DBManagers.fetchDataBaseName(cdb.getValue());
			if(dbm == null) { continue; }
			dbm.delete();
		}
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean saveCFGCore(com.FileManagerX.BasicEnums.StartType type, boolean createNew) {
		
		this.error |= !this.saveTitle(createNew);
		this.error |= !this.saveDepotAndDataBaseList(createNew);
		this.error |= !this.saveRegister(createNew);
		this.error |= !this.saveIndexes(createNew);
		this.error |= !this.saveServerInfo(createNew);
		this.error |= !this.saveThisMachine(createNew);
		this.error |= !this.saveThisUser(createNew);
		this.error |= !this.saveSupports(createNew);
		this.error |= !this.saveAddDepotAndDataBaseList(createNew);
		this.error |= !this.saveDelDepotAndDataBaseList(createNew);
		this.error |= !this.saveEnd(createNew);
		
		return !this.error;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private boolean saveTitle(boolean createNew) {
		java.lang.String line = "";
		this.content.add(line);
		line = "/****************************************** This File Is VERY IMPORTANT **********************************************/";
		this.content.add(line);
		this.content.add(line);
		this.content.add(line);
		
		line = "";
		this.content.add(line);
		line = "[Attention]: Do Not Save Other Info in This File, It will reset At Running.";
		this.content.add(line);
		
		line = "";
		this.content.add(line);
		line = "StartType = " + Configurations.StartType.toString() + 
				(Configurations.ShowForm ? "|ShowForm" : "");
		this.content.add(line);
		
		if(Configurations.IsServer) {
			line = "NetType = " + Configurations.NetType.toString();
			this.content.add(line);
		}
		
		return true;
	}
	private boolean saveDepotAndDataBaseList(boolean createNew) {
		java.lang.String line = "";
		this.content.add(line);
		line = "/**************************************** A List of Current Depots & DataBases ***************************************/";
		this.content.add(line);
		line = "";
		this.content.add(line);
		
		if(createNew) { return true; }
		
		for(IDBManager dbm : Datas.DBManagers.getContent()) {
			line = "Depot = " + dbm.getDBInfo().getDepotInfo().getName() + "|" + dbm.getDBInfo().getDepotInfo().getUrl();
			this.content.add(line);
			line = "DataBase = " + dbm.getDBInfo().getName() + "|" + dbm.getDBInfo().getType().toString() + "|" + dbm.getDBInfo().getUrl();
			this.content.add(line);
		}
		
		return true;
	}
	private boolean saveRegister(boolean createNew) {
		java.lang.String line = "";
		this.content.add(line);
		line = "/****************************************************** Register *****************************************************/";
		this.content.add(line);
		line = "";
		this.content.add(line);
		
		line = "Example: RegisterUser = [InvitationCode]|[LoginName]|[Password]";
		this.content.add(line);
		line = "Example: RegisterMachine = [MachineName]";
		this.content.add(line);
		
		return true;
	}
	private boolean saveIndexes(boolean createNew) {
		java.lang.String line = "";
		this.content.add(line);
		line = "/*************************************************** All Indexes *****************************************************/";
		this.content.add(line);
		line = "";
		this.content.add(line);
		
		if(createNew) {
			line = "Next_FileIndex = 0";
			this.content.add(line);
			line = "Next_UserIndex = 0";
			this.content.add(line);
			line = "Next_MachineIndex = 0";
			this.content.add(line);
			line = "Next_DepotIndex = 0";
			this.content.add(line);
			line = "Next_DataBaseIndex = 0";
			this.content.add(line);
			line = "This_MachineIndex = 0";
			this.content.add(line);
			line = "This_UserIndex = 0";
			this.content.add(line);
			line = "Server_MachineIndex = 0";
			this.content.add(line);
			line = "Server_UserIndex = 0";
			this.content.add(line);
		}
		else {
			line = "Next_FileIndex = " + java.lang.String.valueOf(Configurations.Next_FileIndex);
			this.content.add(line);
			line = "Next_UserIndex = " + java.lang.String.valueOf(Configurations.Next_UserIndex);
			this.content.add(line);
			line = "Next_MachineIndex = " + java.lang.String.valueOf(Configurations.Next_MachineIndex);
			this.content.add(line);
			line = "Next_DepotIndex = " + java.lang.String.valueOf(Configurations.Next_DepotIndex);
			this.content.add(line);
			line = "Next_DataBaseIndex = " + java.lang.String.valueOf(Configurations.Next_DataBaseIndex);
			this.content.add(line);
			line = "This_MachineIndex = " + java.lang.String.valueOf(Configurations.This_MachineIndex);
			this.content.add(line);
			line = "This_UserIndex = " + java.lang.String.valueOf(Configurations.This_UserIndex);
			this.content.add(line);
			line = "Server_MachineIndex = " + java.lang.String.valueOf(Configurations.Server_MachineIndex);
			this.content.add(line);
			line = "Server_UserIndex = " + java.lang.String.valueOf(Configurations.Server_UserIndex);
			this.content.add(line);
		}
		
		return true;
	}
	private boolean saveServerInfo(boolean createNew) {
		java.lang.String line = "";
		this.content.add(line);
		line = "/*************************************************** Server Info *****************************************************/";
		this.content.add(line);
		line = "";
		this.content.add(line);
		
		if(createNew) {
			if(com.FileManagerX.BasicEnums.StartType.Server.equals(com.FileManagerX.Globals.Configurations.StartType)) {
				line = "ServerDepotIndex = ";
				this.content.add(line);
				line = "ServerDepotName = ";
				this.content.add(line);
				line = "ServerDepotUrl = ";
				this.content.add(line);
				
				line = "ServerDataBaseIndex = ";
				this.content.add(line);
				line = "ServerDataBaseName = ";
				this.content.add(line);
				line = "ServerDataBaseType = ";
				this.content.add(line);
				line = "ServerDataBaseUrl = ";
				this.content.add(line);
			}
			if(com.FileManagerX.BasicEnums.StartType.Depot.equals(com.FileManagerX.Globals.Configurations.StartType) ||
					com.FileManagerX.BasicEnums.StartType.Client.equals(com.FileManagerX.Globals.Configurations.StartType)) {
				line = "ServerMachineIndex = ";
				this.content.add(line);
				line = "ServerMachineName = ";
				this.content.add(line);
				line = "ServerMachineIP = ";
				this.content.add(line);
				line = "ServerMachinePort = ";
				this.content.add(line);
			}
		}
		else {
			if(com.FileManagerX.BasicEnums.StartType.Server.equals(com.FileManagerX.Globals.Configurations.StartType)) {
				line = "ServerDepotIndex = " + Datas.DBManager.getDBInfo().getDepotInfo().getIndex();
				this.content.add(line);
				line = "ServerDepotName = " + Datas.DBManager.getDBInfo().getDepotInfo().getName();
				this.content.add(line);
				line = "ServerDepotUrl = " + Datas.DBManager.getDBInfo().getDepotInfo().getUrl();
				this.content.add(line);
				
				line = "ServerDataBaseIndex = " + Datas.DBManager.getDBInfo().getIndex();
				this.content.add(line);
				line = "ServerDataBaseName = " + Datas.DBManager.getDBInfo().getName();
				this.content.add(line);
				line = "ServerDataBaseType = " + Datas.DBManager.getDBInfo().getType().toString();
				this.content.add(line);
				line = "ServerDataBaseUrl = " + Datas.DBManager.getDBInfo().getUrl();
				this.content.add(line);
			}
			if(com.FileManagerX.BasicEnums.StartType.Depot.equals(com.FileManagerX.Globals.Configurations.StartType) ||
					com.FileManagerX.BasicEnums.StartType.Client.equals(com.FileManagerX.Globals.Configurations.StartType)) {
				line = "ServerMachineIndex = " + Datas.ServerMachine.getIndex();
				this.content.add(line);
				line = "ServerMachineName = " + Datas.ServerMachine.getName();
				this.content.add(line);
				line = "ServerMachineIP = " + Datas.ServerMachine.getIp();
				this.content.add(line);
				line = "ServerMachinePort = " + Datas.ServerMachine.getPort();
				this.content.add(line);
			}
		}
		
		return true;
	}
	private boolean saveThisMachine(boolean createNew) {
		if(com.FileManagerX.Globals.Configurations.IsServer) { return true; }
		
		java.lang.String line = "";
		this.content.add(line);
		line = "/*************************************************** Machine Info ****************************************************/";
		this.content.add(line);
		line = "";
		this.content.add(line);
		
		if(createNew) {
			line = "MachineIndex = ";
			this.content.add(line);
			line = "MachineName = ";
			this.content.add(line);
			line = "MachineIP = ";
			this.content.add(line);
			line = "MachinePort = ";
			this.content.add(line);
		}
		else {
			line = "MachineIndex = " + Datas.ThisMachine.getIndex();
			this.content.add(line);
			line = "MachineName = " + Datas.ThisMachine.getName();
			this.content.add(line);
			line = "MachineIP = " + Datas.ThisMachine.getIp();
			this.content.add(line);
			line = "MachinePort = " + Datas.ThisMachine.getPort();
			this.content.add(line);
		}
		
		return true;
	}
	private boolean saveThisUser(boolean createNew) {
		if(com.FileManagerX.Globals.Configurations.IsServer) { return true; }
		
		java.lang.String line = "";
		this.content.add(line);
		line = "/***************************************************** User Info *****************************************************/";
		this.content.add(line);
		line = "";
		this.content.add(line);
		
		line = "UserLoginName = " + Datas.ThisUser.getLoginName();
		this.content.add(line);
		line = "UserPassword = " + Datas.ThisUser.getPassword();
		this.content.add(line);
		
		return true;
	}
	private boolean saveSupports(boolean createNew) {
		java.lang.String line = "";
		this.content.add(line);
		line = "/*************************************************** Supports Info ***************************************************/";
		this.content.add(line);
		line = "";
		this.content.add(line);
		line = "Example: Support = Picture|.jpg|.pv1";
		this.content.add(line);
		line = "";
		this.content.add(line);
		
		if(createNew) {
			line = "Support = ";
			this.content.add(line);
		}
		else {
			for(Support s : Datas.Supports.getContent()) {
				if(s.isHideExtension()) {
					continue;
				}
				line = "Support = " + s.getType().toString() + "|" + s.getShowExtension() + "|" + s.getHideExtension();
				this.content.add(line);
			}
		}
		
		return true;
	}
	private boolean saveAddDepotAndDataBaseList(boolean createNew) {
		java.lang.String line = "";
		this.content.add(line);
		line = "/********************************************** Add Local Depot & DataBase *******************************************/";
		this.content.add(line);
		line = "";
		this.content.add(line);
		
		line = "[Attention]: At Each Add Operation, You Should add Depot and DataBase Two Items, Otherwise add will Failed.";
		this.content.add(line);
		line = "";
		this.content.add(line);
		
		line = "Example: +Depot = [DepotName1]|[DepotFolderAbsolutePath]";
		this.content.add(line);
		line = "Example: +DataBase = [DataBaseName1]|SQL|[LoginIP]:[LoginPort]\\[LoginName]\\[Password]\\[DataBaseNameInSQL]";
		this.content.add(line);
		line = "";
		this.content.add(line);
		line = "Example: +Depot = [DepotName2]|[DepotFolderAbsolutePath]";
		this.content.add(line);
		line = "Example: +DataBase = [DataBaseName2]|TXT|[DataBaseFolderAbsolutePath]";
		this.content.add(line);
		line = "";
		this.content.add(line);
		line = "Example: +Depot = [DepotName3]|[DepotFolderAbsolutePath]";
		this.content.add(line);
		line = "Example: +DataBase = [DataBaseName3]";
		this.content.add(line);
		
		return true;
	}
	private boolean saveDelDepotAndDataBaseList(boolean createNew) {
		java.lang.String line = "";
		this.content.add(line);
		line = "/********************************************** Del Local Depot & DataBase *******************************************/";
		this.content.add(line);
		line = "";
		this.content.add(line);
		
		line = "[Attention]: At Each Del Operation, You should delete Depot and DataBase Two Items, ";
		this.content.add(line);
		line = "             Otherwise some Errors will happen in Running.";
		this.content.add(line);
		
		line = "";
		this.content.add(line);
		line = "Example: -Depot = [DepotName1]";
		this.content.add(line);
		line = "Example: -DataBase = [DataBaseName1]";
		this.content.add(line);
		
		line = "";
		this.content.add(line);
		line = "Example: -Depot = [DepotName2]";
		this.content.add(line);
		line = "Example: -DataBase = [DataBaseName2]";
		this.content.add(line);
		
		line = "";
		this.content.add(line);
		line = "Example: -Depot = [DepotName3]";
		this.content.add(line);
		line = "Example: -DataBase = [DataBaseName3]";
		this.content.add(line);
		
		return true;
	}
	private boolean saveEnd(boolean createNew) {
		java.lang.String line = "";
		this.content.add(line);
		line = "/******************************************************* END *********************************************************/";
		this.content.add(line);
		line = "";
		this.content.add(line);
		
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
