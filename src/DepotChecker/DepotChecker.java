package DepotChecker;

public class DepotChecker implements Interfaces.IDepotChecker{

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private Interfaces.IDBManager dbmanager;
	
	private BasicCollections.BaseFiles totalFiles;
	private BasicCollections.Folders totalFolders;
	
	private BasicCollections.BaseFiles addFiles;
	private BasicCollections.Folders addFolders;
	
	private BasicCollections.BaseFiles deleteFiles;
	private BasicCollections.Folders deleteFolders;
	
	private boolean[] checkedMark_Folders;
	private boolean[] checkedMark_Files;
	
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
	
	public Interfaces.IServerChecker getServerChecker() {
		if(this.dbmanager == null) {
			return null;
		}
		return this.dbmanager.getServerChecker();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public DepotChecker() {
		initThis();
	}
	public DepotChecker(Interfaces.IDBManager dbmanager) {
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
		this.totalFolders = dbmanager.QueryFolders(new DataBaseManager.QueryConditions());
		this.totalFiles = dbmanager.QueryFiles(new DataBaseManager.QueryConditions());
		this.checkedMark_Folders = new boolean[this.totalFolders.size()];
		this.checkedMark_Files = new boolean[this.totalFiles.size()];
		this.addFolders = new BasicCollections.Folders();
		this.addFiles = new BasicCollections.BaseFiles();
		this.deleteFolders = new BasicCollections.Folders();
		this.deleteFiles = new BasicCollections.BaseFiles();
		
		
		long currentFileIndex = Globals.Configurations.Next_FileIndex;
		BasicModels.Folder f = new BasicModels.Folder(this.dbmanager.getDBInfo().getDepotInfo().getUrl());
		this.checkFolder(f);
		Globals.Configurations.Next_FileIndex = currentFileIndex;
		
		for(int i=0; i<this.checkedMark_Folders.length; i++) {
			if(!this.checkedMark_Folders[i]) {
				this.deleteFolders.add(this.totalFolders.getContent().get(i));
			}
		}
		for(int i=0; i<this.checkedMark_Files.length; i++) {
			if(!this.checkedMark_Files[i]) {
				this.deleteFiles.add(this.totalFiles.getContent().get(i));
			}
		}
		
		long database = this.dbmanager.getDBInfo().getIndex();
		long machine = this.dbmanager.getDBInfo().getMachineInfo().getIndex();
		long depot = this.dbmanager.getDBInfo().getDepotIndex();
		
		int nextAddFolder = 0;
		int nextAddFile = 0;
		while(true) {
			if(nextAddFolder < this.addFolders.size() && nextAddFile < this.addFiles.size()) {
				BasicModels.Folder nextFolder = this.addFolders.getContent().get(nextAddFolder);
				BasicModels.BaseFile nextFile = this.addFiles.getContent().get(nextAddFile);
				if(nextFolder.getIndex() < nextFile.getIndex()) {
					nextFolder.setIndex(-1);
					nextFolder.setDataBase(database);
					nextFolder.setMachine(machine);
					nextFolder.setDepot(depot);
					this.dbmanager.updataFolder(nextFolder);
					nextAddFolder++;
				}
				else {
					nextFile.setIndex(-1);
					nextFile.setDataBase(database);
					nextFile.setMachine(machine);
					nextFile.setDepot(depot);
					this.dbmanager.updataFile(nextFile);
					nextAddFile++;
				}
				continue;
			}
			if(nextAddFolder < this.addFolders.size()) {
				BasicModels.Folder nextFolder = this.addFolders.getContent().get(nextAddFolder);
				nextFolder.setIndex(-1);
				nextFolder.setDataBase(database);
				nextFolder.setMachine(machine);
				nextFolder.setDepot(depot);
				this.dbmanager.updataFolder(nextFolder);
				nextAddFolder++;
				continue;
			}
			if(nextAddFile < this.addFiles.size()) {
				BasicModels.BaseFile nextFile = this.addFiles.getContent().get(nextAddFile);
				nextFile.setIndex(-1);
				nextFile.setDataBase(database);
				nextFile.setMachine(machine);
				nextFile.setDepot(depot);
				this.dbmanager.updataFile(nextFile);
				nextAddFile++;
				continue;
			}
			break;
		}
		
		this.dbmanager.removeFolders(this.deleteFolders);
		this.dbmanager.removeFiles(this.deleteFiles);
		return true;
	}
	public boolean checkFileType() {
		boolean ok = true;
		this.totalFiles = dbmanager.QueryFiles(new DataBaseManager.QueryConditions());
		for(int i=0; i<this.totalFiles.size(); i++) {
			BasicModels.BaseFile f = this.totalFiles.getContent().get(i);
			BasicEnums.FileType tp0 = f.getType();
			BasicModels.Support s = Globals.Datas.Supports.search(f.getExtension());
			BasicEnums.FileType tp1 = s == null ? BasicEnums.FileType.Unsupport : s.getType();
			if(!tp0.equals(tp1)) {
				f.setType(tp1);
				ok &= dbmanager.updataFile(f);
			}
		}
		return ok;
	}
	
	public boolean checkPixes() {
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void checkFolder(BasicModels.Folder folder) {
		int idxFolder = totalFolders.indexOf(folder.getUrl());
		if( idxFolder < 0) {
			folder.setIndex();
			this.addFolders.add(folder);
		}
		else {
			this.checkedMark_Folders[idxFolder] = true;
			folder = this.totalFolders.getContent().get(idxFolder);
		}
		
		java.io.File dir = new java.io.File(folder.getUrl());
		java.io.File[] subfiles = dir.listFiles();
		
		for(java.io.File i : subfiles) {
			if(i.isDirectory()) {
				BasicModels.Folder f = new BasicModels.Folder(i.getAbsolutePath());
				f.setFather(folder.getIndex());
				this.checkFolder(f);
				continue;
			}
			if(i.isFile()) {
				int idxFile = totalFiles.indexOf(i.getAbsolutePath());
				if(idxFile < 0) {
					BasicModels.BaseFile f = new BasicModels.BaseFile(i.getAbsolutePath());
					f.setIndex();
					f.setFather(folder.getIndex());
					addFiles.add(f);
				}
				else {
					this.checkedMark_Files[idxFile] = true;
				}
				continue;
			}
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
