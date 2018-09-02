package com.FileManagerX.BasicModels;

public class Folder extends File implements com.FileManagerX.Interfaces.IPublic {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private java.util.ArrayList<com.FileManagerX.BasicModels.Folder> subfolders;
	private java.util.ArrayList<com.FileManagerX.BasicModels.File> subfiles;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean setIndex() {
		this.setIndex(com.FileManagerX.Globals.Configurations.Next_FolderIndex());
		return true;
	}
	
	public boolean setSubFolders(java.util.ArrayList<com.FileManagerX.BasicModels.Folder> subfolders) {
		if(subfolders == null) {
			return false;
		}
		this.subfolders = subfolders;
		return true;
	}
	public boolean setSubFiles(java.util.ArrayList<com.FileManagerX.BasicModels.File> subfiles) {
		if(subfiles == null) {
			return false;
		}
		this.subfiles = subfiles;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public java.util.ArrayList<com.FileManagerX.BasicModels.Folder> getSubfolders() {
		return this.subfolders;
	}
	public java.util.ArrayList<com.FileManagerX.BasicModels.File> getSubfiles() {
		return this.subfiles;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Folder() {
		initThis();
	}
	private void initThis() {
		this.subfolders = new java.util.ArrayList<>();
		this.subfiles = new java.util.ArrayList<>();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void clear() {
		super.clear();
		initThis();
	}
	public String toString() {
		return this.getName();
	}
	public Config toConfig() {
		return super.toConfig();
	}
	public String output() {
		return super.output();
	}
	public Config input(String in) {
		return super.input(in);
	}
	public Config input(Config c) {
		return super.input(c);
	}
	public void copyReference(Object o) {
		if(o == null) { return; }
		
		if(o instanceof Folder) {
			Folder f = (Folder)o;
			super.copyReference(f);
			this.subfolders = f.subfolders;
			this.subfiles = f.subfiles;
			return;
		}
		if(o instanceof File) {
			super.copyReference(o);
			return;
		}
	}
	public void copyValue(Object o) {
		if(o == null) { return; }
		
		if(o instanceof Folder) {
			Folder f = (Folder)o;
			super.copyValue(f);
			return;
		}
		if(o instanceof File) {
			super.copyValue(o);
			return;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean load() {
		java.io.File folder = new java.io.File(this.getUrl());
		if(!folder.exists()) {
			return false;
		}
		
		java.io.File[] subs = folder.listFiles();
		for(java.io.File i : subs) {
			if(i.isDirectory()) {
				Folder f = new Folder();
				f.loadBasicInfo(i);
				f.setIndex(-1);
				f.setFather(this.getIndex());
				f.setMachine(this.getMachine());
				f.setDepot(this.getDepot());
				f.setDataBase(this.getDataBase());
				f.load();
				this.subfolders.add(f);
			}
			if(i.isFile()) {
				File f = new File();
				f.loadBasicInfo(i);
				f.setIndex(-1);
				f.setFather(this.getIndex());
				f.setMachine(this.getMachine());
				f.setDepot(this.getDepot());
				f.setDataBase(this.getDataBase());
				this.subfiles.add(f);
			}
		}
		
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
