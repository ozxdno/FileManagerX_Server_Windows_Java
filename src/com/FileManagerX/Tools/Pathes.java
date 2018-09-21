package com.FileManagerX.Tools;

public class Pathes {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public final static URL EXE = new URL(true);
	public final static URL CFG = new URL("", "cfg\\FileManagerX.cfg");
	public final static URL LOG = new URL("", "log");
	public final static URL DBS = new URL("", "dbs");
	public final static URL TMP = new URL("", "tmp");
	public final static URL REC = new URL("", "rec");
	
	public final static URL TMP_0 = new URL("", "tmp\\0");
	public final static URL TMP_0_SCREEN = new URL("", "tmp\\0\\Screen");
	public final static URL TMP_0_PHOTO = new URL("", "tmp\\0\\Photo");
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static boolean createAll() {
		boolean ok = true;
		
		ok &= LOG.createAsFolder();
		ok &= DBS.createAsFolder();
		ok &= TMP.createAsFolder();
		ok &= REC.createAsFolder();
		
		ok &= TMP_0.createAsFolder();
		ok &= TMP_0_SCREEN.createAsFolder();
		ok &= TMP_0_PHOTO.createAsFolder();
		return ok;
	}
	
	public final static URL getDBS_I(long i) {
		URL dbs = new URL(DBS.directory, DBS.name + "\\" + i);
		dbs.createAsFolder();
		return dbs;
	}
	
	public final static URL getTMP_I(long i) {
		URL tmp = new URL(TMP.directory, TMP.name + "\\" + i);
		tmp.createAsFolder();
		return tmp;
	}
	public final static URL getTMP_ScreenI(long i) {
		URL tmp = new URL(TMP.directory, TMP.name + "\\" + i + "\\Screen");
		tmp.createAsFolder();
		return tmp;
	}
	public final static URL getTMP_PhotoI(long i) {
		URL tmp = new URL(TMP.directory, TMP.name + "\\" + i + "\\Photo");
		tmp.createAsFolder();
		return tmp;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static class URL {
		private String directory = null;
		private String name = null;
		
		public URL() {}
		public URL(String dir, String name) { this.directory = dir; this.name = name; }
		public URL(boolean root) { if(root) { this.directory = System.getProperties().getProperty("user.dir"); } }
		
		public boolean setDirectory(String dir) {
			this.directory = dir;
			return true;
		}
		public boolean setName(String name) {
			this.name = name;
			return true;
		}
		
		public String getDirectory() {
			boolean empty = this.directory == null || this.directory.length() == 0;
			return empty ? EXE.directory : this.directory;
		}
		public String getName() {
			return this.name == null ? "" : this.name;
		}
		public String getAbsolute() {
			boolean ok = this.name != null && this.name.length() != 0;
			return ok ? this.getDirectory() + "\\" + this.getName() : this.getDirectory();
		}
		
		public boolean createAsFolder() {
			com.FileManagerX.Interfaces.IDepotManager dm = com.FileManagerX.Factories.DepotFactory.createManager();
			dm.setUncheck(true);
			return dm.createFolder(this.getAbsolute());
		}
		public boolean createAsFile() {
			com.FileManagerX.Interfaces.IDepotManager dm = com.FileManagerX.Factories.DepotFactory.createManager();
			dm.setUncheck(true);
			return dm.createFile(this.getAbsolute());
		}
		public boolean clear() {
			return true;
		}
		public boolean delete() {
			return true;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
