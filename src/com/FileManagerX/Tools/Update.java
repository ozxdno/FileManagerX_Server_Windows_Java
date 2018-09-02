package com.FileManagerX.Tools;

public class Update {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public final static boolean addSingleFolder(String folderUrl) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IDBManager> it =
				com.FileManagerX.Globals.Datas.DBManagers.getIterator();
		long depotIndex = 0;
		while(it.hasNext()) {
			com.FileManagerX.Interfaces.IDBManager dbm = it.getNext();
			if(com.FileManagerX.Tools.Url.isFolderIn(folderUrl, dbm.getDBInfo().getDepotInfo().getUrl())) {
				depotIndex = dbm.getDBInfo().getDepotIndex();
				break;
			}
		}
		return addSingleFolder(depotIndex, folderUrl);
	}
	public final static boolean addSingleFolder(long depotIndex, String folderUrl) {
		com.FileManagerX.Interfaces.IDBManager dbm =
				com.FileManagerX.Globals.Datas.DBManagers.searchByDepotIndex(depotIndex);
		if(dbm == null) { return false; }
		
		String fatherUrl = com.FileManagerX.Tools.Url.getDriver(folderUrl) + ":" + 
				com.FileManagerX.Tools.Url.getPath(folderUrl);
		com.FileManagerX.BasicModels.Folder father = (com.FileManagerX.BasicModels.Folder)
				dbm.query2(
						"[&] Url = '" + fatherUrl + "'",
						com.FileManagerX.DataBase.Unit.Folder
					);
		
		com.FileManagerX.BasicModels.Folder old = (com.FileManagerX.BasicModels.Folder)
				dbm.query2(
						"[&] Url = '" + folderUrl + "'",
						com.FileManagerX.DataBase.Unit.Folder
					);
		long index = old == null ? -1 : old.getIndex();
		
		com.FileManagerX.BasicModels.Folder f = new com.FileManagerX.BasicModels.Folder();
		f.setUrl(folderUrl);
		f.loadBasicInfo();
		f.setIndex(index);
		f.setMachine(com.FileManagerX.Globals.Configurations.This_MachineIndex);
		f.setDepot(depotIndex);
		f.setDataBase(dbm.getDBInfo().getIndex());
		f.setFather(father == null ? -1 : father.getIndex());
		
		return dbm.update(f);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static boolean addSingleFile(String fileUrl) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IDBManager> it =
				com.FileManagerX.Globals.Datas.DBManagers.getIterator();
		long depotIndex = 0;
		while(it.hasNext()) {
			com.FileManagerX.Interfaces.IDBManager dbm = it.getNext();
			if(com.FileManagerX.Tools.Url.isFileIn(fileUrl, dbm.getDBInfo().getDepotInfo().getUrl())) {
				depotIndex = dbm.getDBInfo().getDepotIndex();
				break;
			}
		}
		return addSingleFile(depotIndex, fileUrl);
	}
	public final static boolean addSingleFile(long depotIndex, String fileUrl) {
		com.FileManagerX.Interfaces.IDBManager dbm = 
				com.FileManagerX.Globals.Datas.DBManagers.searchByDepotIndex(depotIndex);
		if(dbm == null) { return false; }
		
		String fatherUrl = com.FileManagerX.Tools.Url.getDriver(fileUrl) + ":" +
				com.FileManagerX.Tools.Url.getPath(fileUrl);
		com.FileManagerX.BasicModels.Folder father = (com.FileManagerX.BasicModels.Folder)
				dbm.query2(
						"[&] Url = '" + fatherUrl + "'",
						com.FileManagerX.DataBase.Unit.Folder
					);
		
		com.FileManagerX.BasicModels.File old = (com.FileManagerX.BasicModels.File)
				dbm.query2(
						"[&] Url = '" + fileUrl + "'",
						com.FileManagerX.DataBase.Unit.File
					);
		long index = old == null ? -1 : old.getIndex();
		
		com.FileManagerX.BasicModels.File f = new com.FileManagerX.BasicModels.File();
		f.setUrl(fileUrl);
		f.loadBasicInfo();
		f.setIndex(index);
		f.setMachine(com.FileManagerX.Globals.Configurations.This_MachineIndex);
		f.setDepot(depotIndex);
		f.setDataBase(dbm.getDBInfo().getIndex());
		f.setFather(father == null ? -1 : father.getIndex());
		
		return dbm.update(f);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static boolean delSingleFolder(String folderUrl) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IDBManager> it =
				com.FileManagerX.Globals.Datas.DBManagers.getIterator();
		long depotIndex = 0;
		while(it.hasNext()) {
			com.FileManagerX.Interfaces.IDBManager dbm = it.getNext();
			if(com.FileManagerX.Tools.Url.isFolderIn(folderUrl, dbm.getDBInfo().getDepotInfo().getUrl())) {
				depotIndex = dbm.getDBInfo().getDepotIndex();
				break;
			}
		}
		return delSingleFolder(depotIndex, folderUrl);
	}
	public final static boolean delSingleFolder(long depotIndex, String folderUrl) {
		com.FileManagerX.Interfaces.IDBManager dbm = 
				com.FileManagerX.Globals.Datas.DBManagers.searchByDepotIndex(depotIndex);
		if(dbm == null) {
			return false;
		}
		
		com.FileManagerX.BasicModels.Folder old = (com.FileManagerX.BasicModels.Folder)
				dbm.query2(
						"[&] Url = '" + folderUrl + "'",
						com.FileManagerX.DataBase.Unit.Folder
					);
		if(old == null) {
			return true;
		}
		
		return dbm.remove(old);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static boolean delSingleFile(String fileUrl) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IDBManager> it =
				com.FileManagerX.Globals.Datas.DBManagers.getIterator();
		long depotIndex = 0;
		while(it.hasNext()) {
			com.FileManagerX.Interfaces.IDBManager dbm = it.getNext();
			if(com.FileManagerX.Tools.Url.isFileIn(fileUrl, dbm.getDBInfo().getDepotInfo().getUrl())) {
				depotIndex = dbm.getDBInfo().getDepotIndex();
				break;
			}
		}
		return delSingleFile(depotIndex, fileUrl);
	}
	public final static boolean delSingleFile(long depotIndex, String fileUrl) {
		com.FileManagerX.Interfaces.IDBManager dbm = 
				com.FileManagerX.Globals.Datas.DBManagers.searchByDepotIndex(depotIndex);
		if(dbm == null) {
			return false;
		}
		
		com.FileManagerX.BasicModels.File old = (com.FileManagerX.BasicModels.File)
				dbm.query2(
						"[&] Url = '" + fileUrl + "'",
						com.FileManagerX.DataBase.Unit.File
					);
		if(old == null) {
			return true;
		}
		
		return dbm.remove(old);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static boolean movSingleFolder(String sourUrl, String destUrl) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IDBManager> it =
				com.FileManagerX.Globals.Datas.DBManagers.getIterator();
		long depotIndex = 0;
		while(it.hasNext()) {
			com.FileManagerX.Interfaces.IDBManager dbm = it.getNext();
			if(com.FileManagerX.Tools.Url.isFolderIn(destUrl, dbm.getDBInfo().getDepotInfo().getUrl())) {
				depotIndex = dbm.getDBInfo().getDepotIndex();
				break;
			}
		}
		return movSingleFolder(depotIndex, sourUrl, destUrl);
	}
	public final static boolean movSingleFolder(long depotIndex, String sourUrl, String destUrl) {
		com.FileManagerX.Interfaces.IDBManager dbm = 
				com.FileManagerX.Globals.Datas.DBManagers.searchByDepotIndex(depotIndex);
		if(dbm == null) {
			return false;
		}
		
		String fatherUrl = com.FileManagerX.Tools.Url.getDriver(destUrl) + ":" +
				com.FileManagerX.Tools.Url.getPath(destUrl);
		com.FileManagerX.BasicModels.Folder father = (com.FileManagerX.BasicModels.Folder)
				dbm.query2(
						"[&] Url = '" + fatherUrl + "'",
						com.FileManagerX.DataBase.Unit.Folder
					);
		
		com.FileManagerX.BasicModels.Folder old = (com.FileManagerX.BasicModels.Folder)
				dbm.query2(
						"[&] Url = '" + sourUrl + "'",
						com.FileManagerX.DataBase.Unit.Folder
					);
		long index = old == null ? -1 : old.getIndex();
		
		com.FileManagerX.BasicModels.Folder f = new com.FileManagerX.BasicModels.Folder();
		f.setUrl(destUrl);
		f.loadBasicInfo();
		f.setIndex(index);
		f.setMachine(com.FileManagerX.Globals.Configurations.This_MachineIndex);
		f.setDepot(depotIndex);
		f.setDataBase(dbm.getDBInfo().getIndex());
		f.setFather(father == null ? -1 : father.getIndex());
		
		return dbm.update(f);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static boolean movSingleFile(String sourUrl, String destUrl) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IDBManager> it =
				com.FileManagerX.Globals.Datas.DBManagers.getIterator();
		long depotIndex = 0;
		while(it.hasNext()) {
			com.FileManagerX.Interfaces.IDBManager dbm = it.getNext();
			if(com.FileManagerX.Tools.Url.isFileIn(destUrl, dbm.getDBInfo().getDepotInfo().getUrl())) {
				depotIndex = dbm.getDBInfo().getDepotIndex();
				break;
			}
		}
		return movSingleFile(depotIndex, sourUrl, destUrl);
	}
	public final static boolean movSingleFile(long depotIndex, String sourUrl, String destUrl) {
		com.FileManagerX.Interfaces.IDBManager dbm = 
				com.FileManagerX.Globals.Datas.DBManagers.searchByDepotIndex(depotIndex);
		if(dbm == null) {
			return false;
		}
		
		String fatherUrl = com.FileManagerX.Tools.Url.getDriver(destUrl) + ":" +
				com.FileManagerX.Tools.Url.getPath(destUrl);
		com.FileManagerX.BasicModels.Folder father = (com.FileManagerX.BasicModels.Folder)
				dbm.query2(
						"[&] Url = '" + fatherUrl + "'",
						com.FileManagerX.DataBase.Unit.Folder
					);
		
		com.FileManagerX.BasicModels.File old = (com.FileManagerX.BasicModels.File)
				dbm.query2(
						"[&] Url = '" + sourUrl + "'",
						com.FileManagerX.DataBase.Unit.File
					);
		long index = old == null ? -1 : old.getIndex();
		
		com.FileManagerX.BasicModels.File f = new com.FileManagerX.BasicModels.File();
		f.setUrl(destUrl);
		f.loadBasicInfo();
		f.setIndex(index);
		f.setMachine(com.FileManagerX.Globals.Configurations.This_MachineIndex);
		f.setDepot(depotIndex);
		f.setDataBase(dbm.getDBInfo().getIndex());
		f.setFather(father == null ? -1 : father.getIndex());
		
		return dbm.update(f);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static boolean fixSingleFolderUrl(String sourUrl, String destUrl) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IDBManager> it =
				com.FileManagerX.Globals.Datas.DBManagers.getIterator();
		long depotIndex = 0;
		while(it.hasNext()) {
			com.FileManagerX.Interfaces.IDBManager dbm = it.getNext();
			if(com.FileManagerX.Tools.Url.isFolderIn(destUrl, dbm.getDBInfo().getDepotInfo().getUrl())) {
				depotIndex = dbm.getDBInfo().getDepotIndex();
				break;
			}
		}
		return fixSingleFolderUrl(depotIndex, sourUrl, destUrl);
	}
	public final static boolean fixSingleFolderUrl(long depotIndex, String sourUrl, String destUrl) {
		com.FileManagerX.Interfaces.IDBManager dbm =
				com.FileManagerX.Globals.Datas.DBManagers.searchByDepotIndex(depotIndex);
		if(dbm == null) {
			return false;
		}
		
		com.FileManagerX.BasicModels.Folder old = (com.FileManagerX.BasicModels.Folder)
				dbm.query2(
						"[&] Url = '" + sourUrl + "'",
						com.FileManagerX.DataBase.Unit.Folder
					);
		if(old == null) {
			return true;
		}
		
		old.setUrl(destUrl);
		return dbm.update(old);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static boolean fixSingleFileUrl(String sourUrl, String destUrl) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IDBManager> it =
				com.FileManagerX.Globals.Datas.DBManagers.getIterator();
		long depotIndex = 0;
		while(it.hasNext()) {
			com.FileManagerX.Interfaces.IDBManager dbm = it.getNext();
			if(com.FileManagerX.Tools.Url.isFileIn(destUrl, dbm.getDBInfo().getDepotInfo().getUrl())) {
				depotIndex = dbm.getDBInfo().getDepotIndex();
				break;
			}
		}
		return fixSingleFileUrl(depotIndex, sourUrl, destUrl);
	}
	public final static boolean fixSingleFileUrl(long depotIndex, String sourUrl, String destUrl) {
		com.FileManagerX.Interfaces.IDBManager dbm = 
				com.FileManagerX.Globals.Datas.DBManagers.searchByDepotIndex(depotIndex);
		if(dbm == null) {
			return false;
		}
		
		com.FileManagerX.BasicModels.File old = (com.FileManagerX.BasicModels.File)
				dbm.query2(
						"[&] Url = '" + sourUrl + "'",
						com.FileManagerX.DataBase.Unit.File
					);
		if(old == null) {
			return true;
		}
		
		old.setUrl(destUrl);
		return dbm.update(old);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
