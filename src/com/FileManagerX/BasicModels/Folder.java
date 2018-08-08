package com.FileManagerX.BasicModels;

import java.io.*;

public class Folder extends BaseFile implements com.FileManagerX.Interfaces.IPublic {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private com.FileManagerX.BasicCollections.Folders subfolders;
	private com.FileManagerX.BasicCollections.BaseFiles subfiles;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean setSubFolders(com.FileManagerX.BasicCollections.Folders subfolders) {
		if(subfolders == null) {
			return false;
		}
		this.subfolders = subfolders;
		return true;
	}
	public boolean setSubFiles(com.FileManagerX.BasicCollections.BaseFiles subfiles) {
		if(subfiles == null) {
			return false;
		}
		this.subfiles = subfiles;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public com.FileManagerX.BasicCollections.Folders getSubfolders() {
		return this.subfolders;
	}
	public com.FileManagerX.BasicCollections.BaseFiles getSubfiles() {
		return this.subfiles;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Folder() {
		initThis();
	}
	public Folder(String url) {
		super(url);
		initThis();
	}
	public Folder(File localFile) {
		super(localFile);
		initThis();
	}
	private void initThis() {
		if(this.subfolders == null) {
			this.subfolders = new com.FileManagerX.BasicCollections.Folders();
		}
		if(this.subfiles == null) {
			this.subfiles = new com.FileManagerX.BasicCollections.BaseFiles();
		}
		this.subfolders.clear();
		this.subfiles.clear();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public void clear() {
		initThis();
	}
	@Override
	public String toString() {
		String subfolders = "Subfolders: Empty";
		if(this.subfolders.size() != 0) {
			subfolders = "Subfolders: " + this.subfolders.getContent().get(0).getName();
			for(int i=1; i<this.subfolders.size(); i++) {
				subfolders += ", " + this.subfolders.getContent().get(i).getName();
			}
		}
		String subfiles = "Subfiles: Empty";
		if(this.subfiles.size() != 0) {
			subfiles = "Subfiles: " + this.subfiles.getContent().get(0).getName();
			for(int i=1; i<this.subfiles.size(); i++) {
				subfiles += ", " + this.subfiles.getContent().get(i).getName();
			}
		}
		
		return subfolders + "; " + subfiles;
	}
	@Override
	public String output() {
		return this.getClass().getSimpleName() + " = " + new com.FileManagerX.BasicModels.Config(super.output()).getValue();
	}
	@Override
	public String input(String in) {
		return super.input(in);
	}
	@Override
	public void copyReference(Object o) {
		Folder f = (Folder)o;
		super.copyReference(f);
		this.subfolders = f.subfolders;
		this.subfiles = f.subfiles;
	}
	@Override
	public void copyValue(Object o) {
		Folder f = (Folder)o;
		super.copyValue(f);
		this.subfolders = f.subfolders;
		this.subfiles = f.subfiles;
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
				Folder f = new Folder(i);
				f.setIndex();
				f.setFather(this.getIndex());
				f.setMachine(this.getMachine());
				f.setDepot(this.getDepot());
				f.setDataBase(this.getDataBase());
				f.load();
				this.subfolders.add(f);
			}
			if(i.isFile()) {
				BaseFile f = new BaseFile(i);
				f.setIndex();
				f.setFather(this.getIndex());
				f.setMachine(this.getMachine());
				f.setDepot(this.getDepot());
				f.setDataBase(this.getDataBase());
				f = f.toSpecific();
				this.subfiles.add(f);
			}
		}
		
		return true;
	}
	public boolean load(com.FileManagerX.BasicCollections.BaseFiles total) {
		if(this.subfolders.size() == 0 && this.subfiles.size() == 0) { this.load(); }
		
		java.util.Stack<Folder> stack = new java.util.Stack<>();
		stack.push(this);
		while(!stack.isEmpty()) {
			Folder next = stack.pop();
			
			for(Folder f : next.subfolders.getContent()) { stack.push(f); }
			total.getContent().addAll(next.subfiles.getContent());
			total.add(next);
		}
		
		return true;
	}
	public boolean load(
			com.FileManagerX.BasicCollections.Folders folders, 
			com.FileManagerX.BasicCollections.BaseFiles files
			) {
		if(this.subfolders.size() == 0 && this.subfiles.size() == 0) { this.load(); }
		
		java.util.Stack<Folder> stack = new java.util.Stack<>();
		stack.push(this);
		while(!stack.isEmpty()) {
			Folder next = stack.pop();
			
			for(Folder f : next.subfolders.getContent()) { stack.push(f); }
			files.getContent().addAll(next.subfiles.getContent());
			folders.add(next);
		}
		
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
