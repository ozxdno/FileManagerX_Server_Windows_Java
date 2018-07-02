package DepotChecker;

/**
 * The Server Depot Checker
 * 
 * @author ozxdno
 *
 */
public class DepotChecker2 implements Interfaces.IDepotChecker{

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private Interfaces.IDBManager dbmanager;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setDBManager(Interfaces.IDBManager dbmanager) {
		if(dbmanager == null || !dbmanager.isConnected()) {
			return false;
		}
		this.dbmanager = dbmanager;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Interfaces.IDBManager getDBManager() {
		return this.dbmanager;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public DepotChecker2() {
		initThis();
	}
	public DepotChecker2(Interfaces.IDBManager dbmanager) {
		initThis();
		this.setDBManager(dbmanager);
	}
	private void initThis() {
		this.dbmanager = null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean initialize(Object infos) {
		return this.setDBManager((Interfaces.IDBManager)infos);
	}
	
	public boolean check() {
		if(!this.checkFoldersAndFiles()) {
			return false;
		}
		if(!this.checkFileType()) {
			return false;
		}
		if(!this.checkPixes()) {
			return false;
		}
		
		return true;
	}
	
	public boolean checkFoldersAndFiles() {
		BasicCollections.Folders folders = this.dbmanager.QueryFolders(new DataBaseManager.QueryConditions());
		BasicCollections.BaseFiles files = this.dbmanager.QueryFiles(new DataBaseManager.QueryConditions());
		BasicCollections.BaseFiles subfiles1 = new BasicCollections.BaseFiles();
		
		BasicModels.Folder f = new BasicModels.Folder(this.dbmanager.getDBInfo().getDepotInfo().getUrl());
		long nfi = Globals.Configurations.Next_FileIndex;
		f.setIndex();
		f.setMachine(this.dbmanager.getDBInfo().getMachineInfo().getIndex());
		f.setDepot(this.dbmanager.getDBInfo().getDepotIndex());
		f.setDataBase(this.dbmanager.getDBInfo().getIndex());
		BasicCollections.BaseFiles subfiles2 = f.getAllSubs(f.getUrl());
		subfiles2.add(f);
		Globals.Configurations.Next_FileIndex = nfi;
		
		boolean[] checkedDBFolders = new boolean[folders.size()];
		boolean[] checkedDBFiles = new boolean[files.size()];
		boolean[] checkedExists = new boolean[subfiles2.size()];
		
		Tools.SetElements.setBooleanArrayElements(checkedDBFolders, false);
		Tools.SetElements.setBooleanArrayElements(checkedDBFiles, false);
		Tools.SetElements.setBooleanArrayElements(checkedExists, false);
		
		for(int i=0; i<folders.size(); i++) {
			for(int j=0; j<subfiles2.size(); j++) {
				if(folders.getContent().get(i).getUrl().equals(subfiles2.getContent().get(j).getUrl())) {
					checkedDBFolders[i] = true;
					checkedExists[j] = true;
				}
			}
		}
		for(int i=0; i<files.size(); i++) {
			for(int j=0; j<subfiles2.size(); j++) {
				if(files.getContent().get(i).getUrl().equals(subfiles2.getContent().get(j).getUrl())) {
					checkedDBFiles[i] = true;
					checkedExists[j] = true;
				}
			}
		}
		for(int i=0; i<checkedDBFolders.length; i++) {
			if(!checkedDBFolders[i]) {
				subfiles1.add(folders.getContent().get(i));
			}
		}
		for(int i=0; i<checkedDBFiles.length; i++) {
			if(!checkedDBFiles[i]) {
				subfiles1.add(files.getContent().get(i));
			}
		}
		
		
		boolean ok = true;
		
		for(int i=0; i<checkedDBFolders.length; i++) {
			if(!checkedDBFolders[i]) {
				BasicModels.Folder item = folders.getContent().get(i);
				ok &= this.dbmanager.removeFolder(item);
			}
		}
		for(int i=0; i<checkedDBFiles.length; i++) {
			if(!checkedDBFiles[i]) {
				BasicModels.BaseFile item = folders.getContent().get(i);
				ok &= this.dbmanager.removeFile(item);
			}
		}
		for(int i=0; i<checkedExists.length; i++) {
			if(!checkedExists[i]) {
				ok &= this.dbmanager.updataFile(subfiles2.getContent().get(i));
			}
		}
		
		return ok;
	}
	public boolean checkFileType() {
		BasicCollections.Folders folders = this.dbmanager.QueryFolders(new DataBaseManager.QueryConditions());
		BasicCollections.BaseFiles files = this.dbmanager.QueryFiles(new DataBaseManager.QueryConditions());
		
		boolean ok = true;
		
		for(int i=0; i<folders.size(); i++) {
			BasicModels.Folder f = folders.getContent().get(i);
			if(!f.getType().equals(BasicEnums.FileType.Folder)) {
				f.setType(BasicEnums.FileType.Folder);
				ok &= this.dbmanager.updataFolder(f);
			}
		}
		for(int i=0; i<files.size(); i++) {
			BasicModels.BaseFile f = files.getContent().get(i);
			if(!f.getType().equals(Globals.Datas.Supports.search(f.getExtension()).getType())) {
				f.setType(BasicEnums.FileType.Folder);
				ok &= this.dbmanager.updataFile(f);
			}
		}
		
		return ok;
	}
	public boolean checkFather() {
		return true;
	}
	
	public boolean checkPixes() {
		return true;
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
