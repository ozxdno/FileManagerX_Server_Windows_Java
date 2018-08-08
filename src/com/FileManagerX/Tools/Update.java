package com.FileManagerX.Tools;

public class Update {
	
	public final static boolean addSingleFolder(java.lang.String folderUrl) {
		long depotIndex = 0;
		for(com.FileManagerX.Interfaces.IDBManager dbm : com.FileManagerX.Globals.Datas.DBManagers.getContent()) {
			if(com.FileManagerX.Tools.Url.isFolderIn(folderUrl, dbm.getDBInfo().getDepotInfo().getUrl())) {
				depotIndex = dbm.getDBInfo().getDepotIndex();
				break;
			}
		}
		return addSingleFolder(depotIndex, folderUrl);
	}
	public final static boolean addSingleFolder(long depotIndex, java.lang.String folderUrl) {
		
		com.FileManagerX.Interfaces.IDBManager dbm =
				com.FileManagerX.Globals.Datas.DBManagers.searchDepotIndex(depotIndex);
		if(dbm == null) { return false; }
		dbm.setUnit(com.FileManagerX.DataBase.Unit.Folder);
		
		java.lang.String fatherUrl = com.FileManagerX.Tools.Url.getDriver(folderUrl) + ":" + 
				com.FileManagerX.Tools.Url.getPath(folderUrl);
		com.FileManagerX.BasicModels.Folder father = (com.FileManagerX.BasicModels.Folder)
				dbm.query("[&] Url = '" + fatherUrl.replace("\\", "\\\\") + "'");
		
		com.FileManagerX.BasicModels.Folder old = (com.FileManagerX.BasicModels.Folder)
				dbm.query("[&] Url = '" + folderUrl.replace("\\", "\\\\") + "'");
		long index = old == null ? -1 : old.getIndex();
		
		com.FileManagerX.BasicModels.Folder f = new com.FileManagerX.BasicModels.Folder(folderUrl);
		f.setIndex(index);
		f.setMachine(com.FileManagerX.Globals.Configurations.This_MachineIndex);
		f.setDepot(depotIndex);
		f.setDataBase(dbm.getDBInfo().getIndex());
		f.setFather(father == null ? -1 : father.getIndex());
		
		return dbm.update(f);
	}

	public final static boolean addSingleFile(java.lang.String fileUrl) {
		long depotIndex = 0;
		for(com.FileManagerX.Interfaces.IDBManager dbm : com.FileManagerX.Globals.Datas.DBManagers.getContent()) {
			if(com.FileManagerX.Tools.Url.isFileIn(fileUrl, dbm.getDBInfo().getDepotInfo().getUrl())) {
				depotIndex = dbm.getDBInfo().getDepotIndex();
				break;
			}
		}
		return addSingleFile(depotIndex, fileUrl);
	}
	public final static boolean addSingleFile(long depotIndex, java.lang.String fileUrl) {
		
		com.FileManagerX.Interfaces.IDBManager dbm = 
				com.FileManagerX.Globals.Datas.DBManagers.searchDepotIndex(depotIndex);
		if(dbm == null) { return false; }
		
		java.lang.String fatherUrl = com.FileManagerX.Tools.Url.getDriver(fileUrl) + ":" +
				com.FileManagerX.Tools.Url.getPath(fileUrl);
		dbm.setUnit(com.FileManagerX.DataBase.Unit.Folder);
		com.FileManagerX.BasicModels.Folder father = (com.FileManagerX.BasicModels.Folder)
				dbm.query("[&] Url = '" + fatherUrl.replace("\\", "\\\\") + "'");
		
		dbm.setUnit(com.FileManagerX.DataBase.Unit.BaseFile);
		com.FileManagerX.BasicModels.BaseFile old = (com.FileManagerX.BasicModels.BaseFile)
				dbm.query("[&] Url = '" + fileUrl.replace("\\", "\\\\") + "'");
		long index = old == null ? -1 : old.getIndex();
		
		com.FileManagerX.BasicModels.BaseFile f = new com.FileManagerX.BasicModels.BaseFile(fileUrl);
		f.setIndex(index);
		f.setMachine(com.FileManagerX.Globals.Configurations.This_MachineIndex);
		f.setDepot(depotIndex);
		f.setDataBase(dbm.getDBInfo().getIndex());
		f.setFather(father == null ? -1 : father.getIndex());
		
		return dbm.update(f);
	}
	
	public final static boolean delSingleFolder(java.lang.String folderUrl) {
		long depotIndex = 0;
		for(com.FileManagerX.Interfaces.IDBManager dbm : com.FileManagerX.Globals.Datas.DBManagers.getContent()) {
			if(com.FileManagerX.Tools.Url.isFolderIn(folderUrl, dbm.getDBInfo().getDepotInfo().getUrl())) {
				depotIndex = dbm.getDBInfo().getDepotIndex();
				break;
			}
		}
		return delSingleFolder(depotIndex, folderUrl);
	}
	public final static boolean delSingleFolder(long depotIndex, java.lang.String folderUrl) {
		
		com.FileManagerX.Interfaces.IDBManager dbm = com.FileManagerX.Globals.Datas.DBManagers.searchDepotIndex(depotIndex);
		if(dbm == null) {
			return false;
		}
		
		dbm.setUnit(com.FileManagerX.DataBase.Unit.Folder);
		com.FileManagerX.BasicModels.Folder old = (com.FileManagerX.BasicModels.Folder)
				dbm.query("[&] Url = '" + folderUrl.replace("\\", "\\\\") + "'");
		if(old == null) {
			return true;
		}
		
		return dbm.remove(old);
	}
	
	public final static boolean delSingleFile(java.lang.String fileUrl) {
		long depotIndex = 0;
		for(com.FileManagerX.Interfaces.IDBManager dbm : com.FileManagerX.Globals.Datas.DBManagers.getContent()) {
			if(com.FileManagerX.Tools.Url.isFileIn(fileUrl, dbm.getDBInfo().getDepotInfo().getUrl())) {
				depotIndex = dbm.getDBInfo().getDepotIndex();
				break;
			}
		}
		return delSingleFile(depotIndex, fileUrl);
	}
	public final static boolean delSingleFile(long depotIndex, java.lang.String fileUrl) {
		
		com.FileManagerX.Interfaces.IDBManager dbm = com.FileManagerX.Globals.Datas.DBManagers.searchDepotIndex(depotIndex);
		if(dbm == null) {
			return false;
		}
		
		dbm.setUnit(com.FileManagerX.DataBase.Unit.BaseFile);
		com.FileManagerX.BasicModels.BaseFile old = (com.FileManagerX.BasicModels.BaseFile)
				dbm.query("[&] Url = '" + fileUrl.replace("\\", "\\\\") + "'");
		if(old == null) {
			return true;
		}
		
		return dbm.remove(old);
	}
	
	public final static boolean movSingleFolder(java.lang.String sourUrl, java.lang.String destUrl) {
		long depotIndex = 0;
		for(com.FileManagerX.Interfaces.IDBManager dbm : com.FileManagerX.Globals.Datas.DBManagers.getContent()) {
			if(com.FileManagerX.Tools.Url.isFileIn(destUrl, dbm.getDBInfo().getDepotInfo().getUrl())) {
				depotIndex = dbm.getDBInfo().getDepotIndex();
				break;
			}
		}
		return movSingleFolder(depotIndex, sourUrl, destUrl);
	}
	public final static boolean movSingleFolder(long depotIndex, java.lang.String sourUrl, java.lang.String destUrl) {
		
		com.FileManagerX.Interfaces.IDBManager dbm = com.FileManagerX.Globals.Datas.DBManagers.searchDepotIndex(depotIndex);
		if(dbm == null) {
			return false;
		}
		
		java.lang.String fatherUrl = com.FileManagerX.Tools.Url.getDriver(destUrl) + ":" + com.FileManagerX.Tools.Url.getPath(destUrl);
		dbm.setUnit(com.FileManagerX.DataBase.Unit.Folder);
		com.FileManagerX.BasicModels.Folder father = (com.FileManagerX.BasicModels.Folder)
				dbm.query("[&] Url = '" + fatherUrl.replace("\\", "\\\\") + "'");
		
		com.FileManagerX.BasicModels.Folder old = (com.FileManagerX.BasicModels.Folder)
				dbm.query("[&] Url = '" + sourUrl.replace("\\", "\\\\") + "'");
		long index = old == null ? -1 : old.getIndex();
		
		com.FileManagerX.BasicModels.Folder f = new com.FileManagerX.BasicModels.Folder(destUrl);
		f.setIndex(index);
		f.setMachine(com.FileManagerX.Globals.Configurations.This_MachineIndex);
		f.setDepot(depotIndex);
		f.setDataBase(dbm.getDBInfo().getIndex());
		f.setFather(father == null ? -1 : father.getIndex());
		
		return dbm.update(f);
	}
	
	public final static boolean movSingleFile(java.lang.String sourUrl, java.lang.String destUrl) {
		long depotIndex = 0;
		for(com.FileManagerX.Interfaces.IDBManager dbm : com.FileManagerX.Globals.Datas.DBManagers.getContent()) {
			if(com.FileManagerX.Tools.Url.isFileIn(destUrl, dbm.getDBInfo().getDepotInfo().getUrl())) {
				depotIndex = dbm.getDBInfo().getDepotIndex();
				break;
			}
		}
		return movSingleFile(depotIndex, sourUrl, destUrl);
	}
	public final static boolean movSingleFile(long depotIndex, java.lang.String sourUrl, java.lang.String destUrl) {
		
		com.FileManagerX.Interfaces.IDBManager dbm = com.FileManagerX.Globals.Datas.DBManagers.searchDepotIndex(depotIndex);
		if(dbm == null) {
			return false;
		}
		
		java.lang.String fatherUrl = com.FileManagerX.Tools.Url.getDriver(destUrl) + ":" +
				com.FileManagerX.Tools.Url.getPath(destUrl);
		dbm.setUnit(com.FileManagerX.DataBase.Unit.Folder);
		com.FileManagerX.BasicModels.Folder father = (com.FileManagerX.BasicModels.Folder)
				dbm.query("[&] Url = '" + fatherUrl.replace("\\", "\\\\") + "'");
		
		dbm.setUnit(com.FileManagerX.DataBase.Unit.BaseFile);
		com.FileManagerX.BasicModels.BaseFile old = (com.FileManagerX.BasicModels.BaseFile)
				dbm.query("[&] Url = '" + sourUrl.replace("\\", "\\\\") + "'");
		long index = old == null ? -1 : old.getIndex();
		
		com.FileManagerX.BasicModels.BaseFile f = new com.FileManagerX.BasicModels.BaseFile(destUrl);
		f.setIndex(index);
		f.setMachine(com.FileManagerX.Globals.Configurations.This_MachineIndex);
		f.setDepot(depotIndex);
		f.setDataBase(dbm.getDBInfo().getIndex());
		f.setFather(father == null ? -1 : father.getIndex());
		
		return dbm.update(f);
	}
	
	public final static boolean fixSingleFolderUrl(java.lang.String sourUrl, java.lang.String destUrl) {
		long depotIndex = 0;
		for(com.FileManagerX.Interfaces.IDBManager dbm : com.FileManagerX.Globals.Datas.DBManagers.getContent()) {
			if(com.FileManagerX.Tools.Url.isFileIn(destUrl, dbm.getDBInfo().getDepotInfo().getUrl())) {
				depotIndex = dbm.getDBInfo().getDepotIndex();
				break;
			}
		}
		return fixSingleFolderUrl(depotIndex, sourUrl, destUrl);
	}
	public final static boolean fixSingleFolderUrl(long depotIndex, java.lang.String sourUrl, java.lang.String destUrl) {
		
		com.FileManagerX.Interfaces.IDBManager dbm = com.FileManagerX.Globals.Datas.DBManagers.searchDepotIndex(depotIndex);
		if(dbm == null) {
			return false;
		}
		
		dbm.setUnit(com.FileManagerX.DataBase.Unit.Folder);
		com.FileManagerX.BasicModels.Folder old = (com.FileManagerX.BasicModels.Folder)
				dbm.query("[&] Url = '" + sourUrl.replace("\\", "\\\\") + "'");
		if(old == null) {
			return true;
		}
		
		old.setUrl(destUrl);
		return dbm.update(old);
	}
	
	public final static boolean fixSingleFileUrl(java.lang.String sourUrl, java.lang.String destUrl) {
		long depotIndex = 0;
		for(com.FileManagerX.Interfaces.IDBManager dbm : com.FileManagerX.Globals.Datas.DBManagers.getContent()) {
			if(com.FileManagerX.Tools.Url.isFileIn(destUrl, dbm.getDBInfo().getDepotInfo().getUrl())) {
				depotIndex = dbm.getDBInfo().getDepotIndex();
				break;
			}
		}
		return fixSingleFileUrl(depotIndex, sourUrl, destUrl);
	}
	public final static boolean fixSingleFileUrl(long depotIndex, java.lang.String sourUrl, java.lang.String destUrl) {
		
		com.FileManagerX.Interfaces.IDBManager dbm = com.FileManagerX.Globals.Datas.DBManagers.searchDepotIndex(depotIndex);
		if(dbm == null) {
			return false;
		}
		
		dbm.setUnit(com.FileManagerX.DataBase.Unit.BaseFile);
		com.FileManagerX.BasicModels.BaseFile old = (com.FileManagerX.BasicModels.BaseFile)
				dbm.query("[&] Url = '" + sourUrl.replace("\\", "\\\\") + "'");
		if(old == null) {
			return true;
		}
		
		old.setUrl(destUrl);
		return dbm.update(old);
	}
}
