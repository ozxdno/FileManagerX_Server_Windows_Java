package BasicModels;

import java.io.*;

public class Folder extends BaseFile implements Interfaces.IPublic {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private BasicCollections.Folders subfolders;
	private BasicCollections.BaseFiles subfiles;
	
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
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Folder() {
		initThis();
	}
	public Folder(String url) {
		
	}
	public Folder(File localFile) {
		
	}
	public Folder(MachineInfo m, File f) {
		
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
		String subfolders = "Subfolders: None";
		if(this.subfolders.size() != 0) {
			subfolders = "Subfolders: " + this.subfolders.getContent().get(0).getName();
			for(int i=1; i<this.subfolders.size(); i++) {
				subfolders += ", " + this.subfolders.getContent().get(i).getName();
			}
		}
		String subfiles = "Subfiles: None";
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
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
