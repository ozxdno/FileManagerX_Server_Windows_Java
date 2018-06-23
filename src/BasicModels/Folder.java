package BasicModels;

import java.io.*;

public class Folder extends BaseFile implements Interfaces.IPublic {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private BasicCollections.Folders subfolders;
	private BasicCollections.BaseFiles subfiles;
	
	private static BasicCollections.BaseFiles allSubs = new BasicCollections.BaseFiles();
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean setSubFolders(BasicCollections.Folders subfolders) {
		if(subfolders == null) {
			return false;
		}
		this.subfolders = subfolders;
		return true;
	}
	public boolean setSubFiles(BasicCollections.BaseFiles subfiles) {
		if(subfiles == null) {
			return false;
		}
		this.subfiles = subfiles;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public BasicCollections.Folders getSubfolders() {
		return this.subfolders;
	}
	public BasicCollections.BaseFiles getSubfiles() {
		return this.subfiles;
	}
	public long getLength() {
		super.setLength(0);
		for(Folder i : this.subfolders.getContent()) {
			super.setLength(super.getLength() + i.getLength());
		}
		for(BaseFile i : this.subfiles.getContent()) {
			super.setLength(super.getLength() + i.getLength());
		}
		return super.getLength();
	}
	public int getAmount_Subfolders() {
		return this.subfolders.size();
	}
	public int getAmount_Subfiles() {
		return this.subfiles.size();
	}
	public int getAmount_Subs() {
		return this.getAmount_Subfolders() + this.getAmount_Subfiles();
	}
	public int getAllAmount_Subfolders() {
		int amount = 0;
		for(Folder i : this.subfolders.getContent()) {
			amount += i.getAllAmount_Subfolders();
		}
		return amount + this.subfolders.size();
	}
	public int getAllAmount_Subfiles() {
		int amount = 0;
		for(Folder i : this.subfolders.getContent()) {
			amount += i.getAllAmount_Subfiles();
		}
		return amount + this.subfiles.size();
	}
	public int getAllAmount_Subs() {
		int amount = 0;
		for(Folder i : this.subfolders.getContent()) {
			amount += i.getAllAmount_Subfiles();
		}
		return amount + this.subfolders.size() + this.subfiles.size();
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
			this.subfolders = new BasicCollections.Folders();
		}
		if(this.subfiles == null) {
			this.subfiles = new BasicCollections.BaseFiles();
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
		return super.output();
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
		this.subfolders.copyValue(f.subfolders);
		this.subfiles.copyValue(f.subfiles);
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
	public BasicCollections.BaseFiles getAllSubs(String rootFolder) {
		if(this.getUrl().equals(rootFolder)) {
			Folder.allSubs.clear();
		}
		for(Folder i : this.subfolders.getContent()) {
			Folder.allSubs.add(i);
			i.getAllSubs(rootFolder);
		}
		for(BaseFile i : this.subfiles.getContent()) {
			Folder.allSubs.add(i);
		}
		return Folder.allSubs;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
