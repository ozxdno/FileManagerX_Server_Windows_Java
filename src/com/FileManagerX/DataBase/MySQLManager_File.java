package com.FileManagerX.DataBase;

public class MySQLManager_File extends com.FileManagerX.DataBase.MySQLManager_ANY
	<com.FileManagerX.BasicModels.File> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private com.FileManagerX.Interfaces.IDBManager foldersManager;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setFoldersManager(com.FileManagerX.Interfaces.IDBManager foldersManager) {
		if(foldersManager == null) {
			return false;
		}
		this.foldersManager = foldersManager;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public MySQLManager_File() {
		initThis();
	}
	private void initThis() {
		this.setName("Files");
		this.setUnit(Unit.File);
		this.reflect();
		this.setKey("index");
	}
	public com.FileManagerX.BasicModels.File createT() {
		return new com.FileManagerX.BasicModels.File();
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Object querys2(Object conditions) {
		com.FileManagerX.BasicCollections.Files res = new com.FileManagerX.BasicCollections.Files();
		this.querys(conditions, res);
		return res;
	}
	public Object query2(Object conditions) {
		com.FileManagerX.BasicModels.File res = new com.FileManagerX.BasicModels.File();
		this.query(conditions, res);
		return res;
	}
	public Object removes2(Object items) {
		com.FileManagerX.BasicCollections.Files res = new com.FileManagerX.BasicCollections.Files();
		this.removes(items, res);
		return res;
	}
	public Object updates2(Object items) {
		com.FileManagerX.BasicCollections.Files res = new com.FileManagerX.BasicCollections.Files();
		this.updates(items, res);
		return res;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Object readUnsupportField(String field, String value, Object target) {
		if(field.equals("content")) {
			com.FileManagerX.FileModels.CFG cfg = new com.FileManagerX.FileModels.CFG();
			cfg.input(value);
			return cfg.getContent();
		}
		if(field.equals("type")) {
			return com.FileManagerX.BasicEnums.FileType.valueOf(value);
		}
		if(field.equals("state")) {
			return com.FileManagerX.BasicEnums.FileState.valueOf(value);
		}
		return null;
	}
	public String writeUnsupportField(String field, Object value, Object target) {
		return "";
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean check() {
		this.setIsRunning(true);
		com.FileManagerX.Globals.Configurations.setNext_FileIndex(this.checkIndex());
		
		com.FileManagerX.BasicCollections.Files fs = new com.FileManagerX.BasicCollections.Files();
		long maxIndex = 0;
		this.querys("", fs);
		while(fs.size() != 0) {
			com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.File> it = fs.getIterator();
			while(it.hasNext()) {
				com.FileManagerX.BasicModels.File fd = it.getNext();
				if(fd.getIndex() > maxIndex) {
					maxIndex = fd.getIndex();
				}
				
				java.io.File f = new java.io.File(fd.getUrl());
				if(!f.exists() || !f.isFile()) {
					this.remove(fd);
					continue;
				}
				
				boolean update = false;
				if(!com.FileManagerX.BasicEnums.FileType.Folder.equals(fd.getType())) {
					fd.setType(com.FileManagerX.BasicEnums.FileType.Folder);
					update = true;
				}
				if(fd.getModify() != f.lastModified()) {
					fd.setModify(f.lastModified());
					update = true;
				}
				if(update) { this.update(fd); }
			}
			
			fs.clear();
			this.query("qcs=1|[&] Index > " + maxIndex, fs);
		}
		
		com.FileManagerX.BasicModels.Folder fd = new com.FileManagerX.BasicModels.Folder();
		fd.setUrl(this.getDBInfo().getDepotInfo().getUrl());
		fd.loadBasicInfo();
		fd.setMachine(com.FileManagerX.Globals.Configurations.This_MachineIndex);
		fd.setDataBase(this.getDBInfo().getIndex());
		fd.setFather(-1);
		fd.setDepot(this.getDBInfo().getDepotIndex());
		fd.load();
		
		java.util.LinkedList<com.FileManagerX.BasicModels.Folder> fathers = new java.util.LinkedList<>();
		if(fd.exists()) { fathers.add(fd); }
		while(fathers.size() != 0 && this.foldersManager != null) {
			com.FileManagerX.BasicModels.Folder father = fathers.remove(0);
			java.util.ArrayList<com.FileManagerX.BasicModels.Folder> subfds = father.getSubfolders();
			java.util.ArrayList<com.FileManagerX.BasicModels.File> subfs = father.getSubfiles();
			
			boolean existFather = foldersManager.query("qcs=1|[&] url = " + father.getUrl(), father);
			if(!existFather) { existFather = foldersManager.update(father); }
			if(existFather) {
				java.util.Iterator<com.FileManagerX.BasicModels.Folder> it1 = subfds.iterator();
				while(it1.hasNext()) {
					com.FileManagerX.BasicModels.Folder subfd = it1.next();
					subfd.setFather(father.getIndex());
					fathers.add(subfd);
				}
				java.util.Iterator<com.FileManagerX.BasicModels.File> it2 = subfs.iterator();
				while(it2.hasNext()) {
					com.FileManagerX.BasicModels.File subf = it2.next();
					subf.setFather(father.getIndex());
					boolean ok = this.query("qcs=1|[&] url = " + subf.getUrl(), subf);
					if(!ok) { this.update(subf); }
				}
			}
		}
		
		this.setIsRunning(false);
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
