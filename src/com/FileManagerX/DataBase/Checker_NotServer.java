package com.FileManagerX.DataBase;

public class Checker_NotServer {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private com.FileManagerX.Interfaces.IDBManager dbmanager;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setDBManager(com.FileManagerX.Interfaces.IDBManager dbmanager) {
		if(dbmanager == null || !dbmanager.isConnected()) {
			return false;
		}
		this.dbmanager = dbmanager;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.Interfaces.IDBManager getDBManager() {
		return this.dbmanager;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Checker_NotServer() {
		initThis();
	}
	public Checker_NotServer(com.FileManagerX.Interfaces.IDBManager dbmanager) {
		initThis();
		this.setDBManager(dbmanager);
	}
	private void initThis() {
		this.dbmanager = null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean initialize(Object infos) {
		return this.setDBManager((com.FileManagerX.Interfaces.IDBManager)infos);
	}
	
	public boolean check() {
		boolean ok = true;
		if(!this.checkDataBaseAndTables()) {
			ok = false;
		}
		if(!this.checkFoldersAndFiles()) {
			ok = false;
		}
		
		return ok;
	}
	
	public boolean checkDataBaseAndTables() {
		if(this.dbmanager == null) {
			return false;
		}
		this.dbmanager.create();
		return true;
	}
	
	public boolean checkFoldersAndFiles() {
		if(this.dbmanager == null) {
			return false;
		}
		
		// 校验数据库
		this.dbmanager.setUnit(com.FileManagerX.DataBase.Unit.Folder);
		com.FileManagerX.BasicCollections.Folders fds = (com.FileManagerX.BasicCollections.Folders)
				this.dbmanager.querys("");
		while(fds.size() != 0) {
			long maxIndex = 0;
			for(com.FileManagerX.BasicModels.Folder f : fds.getContent()) {
				java.io.File d = new java.io.File(f.getUrl());
				if(!d.exists() || !d.isDirectory()) {
					this.dbmanager.remove(d);
				}
				if(f.getIndex() > maxIndex) { maxIndex = f.getIndex(); }
			}
			fds = (com.FileManagerX.BasicCollections.Folders)this.dbmanager.query
					("[&] Index > " + maxIndex);
		}
		this.dbmanager.setUnit(com.FileManagerX.DataBase.Unit.BaseFile);
		com.FileManagerX.BasicCollections.BaseFiles fs = (com.FileManagerX.BasicCollections.BaseFiles)
				this.dbmanager.querys("");
		while(fs.size() != 0) {
			long maxIndex = 0;
			for(com.FileManagerX.BasicModels.BaseFile f : fs.getContent()) {
				java.io.File d = new java.io.File(f.getUrl());
				if(!d.exists() || !d.isFile()) {
					this.dbmanager.remove(d);
				}
				if(f.getIndex() > maxIndex) { maxIndex = f.getIndex(); }
			}
			fs = (com.FileManagerX.BasicCollections.BaseFiles)this.dbmanager.query
					("[&] Index > " + maxIndex);
		}
		
		// 根目录
		java.io.File root = new java.io.File(this.dbmanager.getDBInfo().getDepotInfo().getUrl());
		if(!root.exists() || !root.isDirectory()) { return false; }
		
		// 获取检验目标
		java.util.Stack<java.io.File> stack = new java.util.Stack<>(); 
		stack.push(root);
		
		// 校验
		long grandfather = -1;
		while(!stack.isEmpty()) {
			java.io.File dir = stack.pop();
			java.io.File[] subs = dir.listFiles();
			
			this.dbmanager.setUnit(com.FileManagerX.DataBase.Unit.Folder);
			com.FileManagerX.BasicModels.Folder fo = (com.FileManagerX.BasicModels.Folder)
					this.dbmanager.query("[&] Url = '" + dir.getAbsolutePath() + "'");
			if(fo == null) {
				fo = new com.FileManagerX.BasicModels.Folder(dir);
				fo.setFather(grandfather);
				fo.setMachine(this.dbmanager.getDBInfo().getMachineInfo().getIndex());
				fo.setDepot(this.dbmanager.getDBInfo().getDepotIndex());
				fo.setDataBase(this.dbmanager.getDBInfo().getIndex());
				this.dbmanager.update(fo);
			}
			else {
				this.checkFolder(fo, dir, subs.length);
			}
			
			this.dbmanager.setUnit(com.FileManagerX.DataBase.Unit.BaseFile);
			for(java.io.File f : subs) {
				if(f.isDirectory()) {
					stack.push(f);
				}
				else if(f.isFile()) {
					com.FileManagerX.BasicModels.BaseFile fi = (com.FileManagerX.BasicModels.BaseFile)
							this.dbmanager.query("[&] Url = '" + f.getAbsolutePath() + "'");
					if(fi == null) {
						fi = new com.FileManagerX.BasicModels.BaseFile(f);
						fi.setFather(fo.getIndex());
						fi.setMachine(this.dbmanager.getDBInfo().getMachineInfo().getIndex());
						fi.setDepot(this.dbmanager.getDBInfo().getDepotIndex());
						fi.setDataBase(this.dbmanager.getDBInfo().getIndex());
						this.dbmanager.update(fi);
					}
					else {
						this.checkFile(fi, f);
					}
				}
			}
			
			grandfather = fo.getIndex();
		}
		
		return true;
	}
	
	public boolean checkPictures() {
		if(this.dbmanager == null) {
			return false;
		}
		
		
		return true;
	}
	public boolean checkGifs() {
		if(this.dbmanager == null) {
			return false;
		}
		return true;
	}
	public boolean checkMusics() {
		if(this.dbmanager == null) {
			return false;
		}
		return true;
	}
	public boolean checkViedos() {
		if(this.dbmanager == null) {
			return false;
		}
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void checkFolder(com.FileManagerX.BasicModels.Folder folder, java.io.File f, int length) {
		boolean need = false;
		
		// type
		if(!com.FileManagerX.BasicEnums.FileType.Folder.equals(folder.getType())) {
			folder.setType(com.FileManagerX.BasicEnums.FileType.Folder);
			need = true;
		}
		
		// modify
		if(folder.getModify() != f.lastModified()) {
			folder.setModify(f.lastModified());
			need = true;
		}
		
		// length
		/*
		if(folder.getLength() != length) {
			folder.setLength(length);
			need = true;
		}
		*/
		
		if(need) { this.dbmanager.update(folder); }
	}
	private void checkFile(com.FileManagerX.BasicModels.BaseFile file, java.io.File f) {
		boolean needUpdate = false;
		
		// type
		com.FileManagerX.BasicEnums.FileType type = com.FileManagerX.BasicEnums.FileType.Unsupport;
		com.FileManagerX.BasicModels.Support s = com.FileManagerX.Globals.Datas.Supports.search(file.getExtension());
		type = s == null ? com.FileManagerX.BasicEnums.FileType.Unsupport : s.getType();
		if(!type.equals(file.getType())) {
			file.setType(type);
			needUpdate = true;
		}
		
		// modify
		if(f.lastModified() != file.getModify()) {
			file.setModify(f.lastModified());
			needUpdate = true;
		}
		
		// length
		if(file.getLength() != f.length()) {
			file.setLength(f.length());
			needUpdate = true;
		}
		
		
		// Update
		if(needUpdate) { this.dbmanager.update(file); }
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
