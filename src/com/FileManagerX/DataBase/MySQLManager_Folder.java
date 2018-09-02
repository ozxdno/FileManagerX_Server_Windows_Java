package com.FileManagerX.DataBase;

public class MySQLManager_Folder extends com.FileManagerX.DataBase.MySQLManager_ANY
	<com.FileManagerX.BasicModels.Folder, Long> {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public MySQLManager_Folder() {
		initThis();
	}
	private void initThis() {
		this.setName("Folders");
		this.setUnit(Unit.Folder);
		this.reflect();
		this.setKey("index");
	}
	public com.FileManagerX.BasicModels.Folder createT() {
		return new com.FileManagerX.BasicModels.Folder();
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Object querys2(Object conditions) {
		com.FileManagerX.BasicCollections.Folders res = new com.FileManagerX.BasicCollections.Folders();
		this.querys(conditions, res);
		return res;
	}
	public Object query2(Object conditions) {
		com.FileManagerX.BasicModels.Folder res = new com.FileManagerX.BasicModels.Folder();
		this.query(conditions, res);
		return res;
	}
	public Object removes2(Object items) {
		com.FileManagerX.BasicCollections.Folders res = new com.FileManagerX.BasicCollections.Folders();
		this.removes(items, res);
		return res;
	}
	public Object updates2(Object items) {
		com.FileManagerX.BasicCollections.Folders res = new com.FileManagerX.BasicCollections.Folders();
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
		if(field.equals("content")) {
			com.FileManagerX.FileModels.CFG cfg = (com.FileManagerX.FileModels.CFG)target;
			return cfg.output();
		}
		return "";
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean check() {
		this.setIsRunning(true);
		com.FileManagerX.Globals.Configurations.setNext_FileIndex(this.checkIndex());
		
		com.FileManagerX.BasicCollections.Folders fds = new com.FileManagerX.BasicCollections.Folders();
		long maxIndex = 0;
		this.querys("", fds);
		while(fds.size() != 0) {
			com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.Folder> it = fds.getIterator();
			while(it.hasNext()) {
				com.FileManagerX.BasicModels.Folder fd = it.getNext();
				if(fd.getIndex() > maxIndex) {
					maxIndex = fd.getIndex();
				}
				
				java.io.File dir = new java.io.File(fd.getUrl());
				if(!dir.exists() || !dir.isDirectory()) {
					this.remove(fd);
					continue;
				}
				
				boolean update = false;
				if(!com.FileManagerX.BasicEnums.FileType.Folder.equals(fd.getType())) {
					fd.setType(com.FileManagerX.BasicEnums.FileType.Folder);
					update = true;
				}
				if(fd.getModify() != dir.lastModified()) {
					fd.setModify(dir.lastModified());
					update = true;
				}
				if(update) { this.update(fd); }
			}
			
			fds.clear();
			this.query("qcs=1|[&] Index > " + maxIndex, fds);
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
		while(fathers.size() != 0) {
			com.FileManagerX.BasicModels.Folder item = fathers.remove(0);
			java.util.ArrayList<com.FileManagerX.BasicModels.Folder> subfds = item.getSubfolders();
			boolean ok = this.query("qcs=1|[&] url = " + item.getUrl(), item);
			if(!ok) { this.update(item); }
			java.util.Iterator<com.FileManagerX.BasicModels.Folder> it = subfds.iterator();
			while(it.hasNext()) {
				com.FileManagerX.BasicModels.Folder subitem = it.next();
				subitem.setFather(item.getIndex());
				fathers.add(subitem);
			}
		}
		
		this.setIsRunning(false);
		return true;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
