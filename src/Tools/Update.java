package Tools;

public class Update {
	
	public final static boolean addSingleFolder(java.lang.String folderUrl) {
		long depotIndex = 0;
		for(Interfaces.IDBManager dbm : Globals.Datas.DBManagers.getContent()) {
			if(Tools.Url.isFolderIn(folderUrl, dbm.getDBInfo().getDepotInfo().getUrl())) {
				depotIndex = dbm.getDBInfo().getDepotIndex();
				break;
			}
		}
		return addSingleFolder(depotIndex, folderUrl);
	}
	public final static boolean addSingleFolder(long depotIndex, java.lang.String folderUrl) {
		
		Interfaces.IDBManager dbm = Globals.Datas.DBManagers.searchDepotIndex(depotIndex);
		if(dbm == null) {
			return false;
		}
		
		java.lang.String fatherUrl = Tools.Url.getDriver(folderUrl) + ":" + Tools.Url.getPath(folderUrl);
		BasicModels.Folder father = dbm.QueryFolder("[&] Url = '" + fatherUrl.replace("\\", "\\\\") + "'");
		
		BasicModels.Folder old = dbm.QueryFolder("[&] Url = '" + folderUrl.replace("\\", "\\\\") + "'");
		long index = old == null ? -1 : old.getIndex();
		
		BasicModels.Folder f = new BasicModels.Folder(folderUrl);
		f.setIndex(index);
		f.setMachine(Globals.Configurations.This_MachineIndex);
		f.setDepot(depotIndex);
		f.setDataBase(dbm.getDBInfo().getIndex());
		f.setFather(father == null ? -1 : father.getIndex());
		
		return dbm.updataFolder(f);
	}

	public final static boolean addSingleFile(java.lang.String fileUrl) {
		long depotIndex = 0;
		for(Interfaces.IDBManager dbm : Globals.Datas.DBManagers.getContent()) {
			if(Tools.Url.isFileIn(fileUrl, dbm.getDBInfo().getDepotInfo().getUrl())) {
				depotIndex = dbm.getDBInfo().getDepotIndex();
				break;
			}
		}
		return addSingleFile(depotIndex, fileUrl);
	}
	public final static boolean addSingleFile(long depotIndex, java.lang.String fileUrl) {
		
		Interfaces.IDBManager dbm = Globals.Datas.DBManagers.searchDepotIndex(depotIndex);
		if(dbm == null) {
			return false;
		}
		
		java.lang.String fatherUrl = Tools.Url.getDriver(fileUrl) + ":" + Tools.Url.getPath(fileUrl);
		BasicModels.Folder father = dbm.QueryFolder("[&] Url = '" + fatherUrl.replace("\\", "\\\\") + "'");
		
		BasicModels.BaseFile old = dbm.QueryFile("[&] Url = '" + fileUrl.replace("\\", "\\\\") + "'");
		long index = old == null ? -1 : old.getIndex();
		
		BasicModels.BaseFile f = new BasicModels.BaseFile(fileUrl);
		f.setIndex(index);
		f.setMachine(Globals.Configurations.This_MachineIndex);
		f.setDepot(depotIndex);
		f.setDataBase(dbm.getDBInfo().getIndex());
		f.setFather(father == null ? -1 : father.getIndex());
		
		return dbm.updataFile(f);
	}
	
	public final static boolean delSingleFolder(java.lang.String folderUrl) {
		long depotIndex = 0;
		for(Interfaces.IDBManager dbm : Globals.Datas.DBManagers.getContent()) {
			if(Tools.Url.isFolderIn(folderUrl, dbm.getDBInfo().getDepotInfo().getUrl())) {
				depotIndex = dbm.getDBInfo().getDepotIndex();
				break;
			}
		}
		return delSingleFolder(depotIndex, folderUrl);
	}
	public final static boolean delSingleFolder(long depotIndex, java.lang.String folderUrl) {
		
		Interfaces.IDBManager dbm = Globals.Datas.DBManagers.searchDepotIndex(depotIndex);
		if(dbm == null) {
			return false;
		}
		
		BasicModels.Folder old = dbm.QueryFolder("[&] Url = '" + folderUrl.replace("\\", "\\\\") + "'");
		if(old == null) {
			return true;
		}
		
		return dbm.removeFolder(old);
	}
	
	public final static boolean delSingleFile(java.lang.String fileUrl) {
		long depotIndex = 0;
		for(Interfaces.IDBManager dbm : Globals.Datas.DBManagers.getContent()) {
			if(Tools.Url.isFileIn(fileUrl, dbm.getDBInfo().getDepotInfo().getUrl())) {
				depotIndex = dbm.getDBInfo().getDepotIndex();
				break;
			}
		}
		return delSingleFile(depotIndex, fileUrl);
	}
	public final static boolean delSingleFile(long depotIndex, java.lang.String fileUrl) {
		
		Interfaces.IDBManager dbm = Globals.Datas.DBManagers.searchDepotIndex(depotIndex);
		if(dbm == null) {
			return false;
		}
		
		BasicModels.BaseFile old = dbm.QueryFile("[&] Url = '" + fileUrl.replace("\\", "\\\\") + "'");
		if(old == null) {
			return true;
		}
		
		return dbm.removeFile(old);
	}
	
	public final static boolean movSingleFolder(java.lang.String sourUrl, java.lang.String destUrl) {
		long depotIndex = 0;
		for(Interfaces.IDBManager dbm : Globals.Datas.DBManagers.getContent()) {
			if(Tools.Url.isFileIn(destUrl, dbm.getDBInfo().getDepotInfo().getUrl())) {
				depotIndex = dbm.getDBInfo().getDepotIndex();
				break;
			}
		}
		return movSingleFolder(depotIndex, sourUrl, destUrl);
	}
	public final static boolean movSingleFolder(long depotIndex, java.lang.String sourUrl, java.lang.String destUrl) {
		
		Interfaces.IDBManager dbm = Globals.Datas.DBManagers.searchDepotIndex(depotIndex);
		if(dbm == null) {
			return false;
		}
		
		java.lang.String fatherUrl = Tools.Url.getDriver(destUrl) + ":" + Tools.Url.getPath(destUrl);
		BasicModels.Folder father = dbm.QueryFolder("[&] Url = '" + fatherUrl.replace("\\", "\\\\") + "'");
		
		BasicModels.Folder old = dbm.QueryFolder("[&] Url = '" + sourUrl.replace("\\", "\\\\") + "'");
		long index = old == null ? -1 : old.getIndex();
		
		BasicModels.Folder f = new BasicModels.Folder(destUrl);
		f.setIndex(index);
		f.setMachine(Globals.Configurations.This_MachineIndex);
		f.setDepot(depotIndex);
		f.setDataBase(dbm.getDBInfo().getIndex());
		f.setFather(father == null ? -1 : father.getIndex());
		
		return dbm.updataFolder(f);
	}
	
	public final static boolean movSingleFile(java.lang.String sourUrl, java.lang.String destUrl) {
		long depotIndex = 0;
		for(Interfaces.IDBManager dbm : Globals.Datas.DBManagers.getContent()) {
			if(Tools.Url.isFileIn(destUrl, dbm.getDBInfo().getDepotInfo().getUrl())) {
				depotIndex = dbm.getDBInfo().getDepotIndex();
				break;
			}
		}
		return movSingleFile(depotIndex, sourUrl, destUrl);
	}
	public final static boolean movSingleFile(long depotIndex, java.lang.String sourUrl, java.lang.String destUrl) {
		
		Interfaces.IDBManager dbm = Globals.Datas.DBManagers.searchDepotIndex(depotIndex);
		if(dbm == null) {
			return false;
		}
		
		java.lang.String fatherUrl = Tools.Url.getDriver(destUrl) + ":" + Tools.Url.getPath(destUrl);
		BasicModels.Folder father = dbm.QueryFolder("[&] Url = '" + fatherUrl.replace("\\", "\\\\") + "'");
		
		BasicModels.BaseFile old = dbm.QueryFile("[&] Url = '" + sourUrl.replace("\\", "\\\\") + "'");
		long index = old == null ? -1 : old.getIndex();
		
		BasicModels.BaseFile f = new BasicModels.BaseFile(destUrl);
		f.setIndex(index);
		f.setMachine(Globals.Configurations.This_MachineIndex);
		f.setDepot(depotIndex);
		f.setDataBase(dbm.getDBInfo().getIndex());
		f.setFather(father == null ? -1 : father.getIndex());
		
		return dbm.updataFile(f);
	}
	
	public final static boolean fixSingleFolderUrl(java.lang.String sourUrl, java.lang.String destUrl) {
		long depotIndex = 0;
		for(Interfaces.IDBManager dbm : Globals.Datas.DBManagers.getContent()) {
			if(Tools.Url.isFileIn(destUrl, dbm.getDBInfo().getDepotInfo().getUrl())) {
				depotIndex = dbm.getDBInfo().getDepotIndex();
				break;
			}
		}
		return fixSingleFolderUrl(depotIndex, sourUrl, destUrl);
	}
	public final static boolean fixSingleFolderUrl(long depotIndex, java.lang.String sourUrl, java.lang.String destUrl) {
		
		Interfaces.IDBManager dbm = Globals.Datas.DBManagers.searchDepotIndex(depotIndex);
		if(dbm == null) {
			return false;
		}
		
		BasicModels.Folder old = dbm.QueryFolder("[&] Url = '" + sourUrl.replace("\\", "\\\\") + "'");
		if(old == null) {
			return true;
		}
		
		old.setUrl(destUrl);
		return dbm.updataFolder(old);
	}
	
	public final static boolean fixSingleFileUrl(java.lang.String sourUrl, java.lang.String destUrl) {
		long depotIndex = 0;
		for(Interfaces.IDBManager dbm : Globals.Datas.DBManagers.getContent()) {
			if(Tools.Url.isFileIn(destUrl, dbm.getDBInfo().getDepotInfo().getUrl())) {
				depotIndex = dbm.getDBInfo().getDepotIndex();
				break;
			}
		}
		return fixSingleFileUrl(depotIndex, sourUrl, destUrl);
	}
	public final static boolean fixSingleFileUrl(long depotIndex, java.lang.String sourUrl, java.lang.String destUrl) {
		
		Interfaces.IDBManager dbm = Globals.Datas.DBManagers.searchDepotIndex(depotIndex);
		if(dbm == null) {
			return false;
		}
		
		BasicModels.BaseFile old = dbm.QueryFile("[&] Url = '" + sourUrl.replace("\\", "\\\\") + "'");
		if(old == null) {
			return true;
		}
		
		old.setUrl(destUrl);
		return dbm.updataFile(old);
	}
}
