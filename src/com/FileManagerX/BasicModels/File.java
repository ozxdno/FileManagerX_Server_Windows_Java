package com.FileManagerX.BasicModels;

public class File implements com.FileManagerX.Interfaces.IPublic {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private long index;
	private long father;
	private long machine;
	private long depot;
	private long database;
	private String url;
	private com.FileManagerX.BasicEnums.FileType type;
	private com.FileManagerX.BasicEnums.FileState state;
	private long modify;
	private long length;
	private int score;
	private String tags;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public long getIndex() {
		return index;
	}
	public long getFather() {
		return father;
	}
	public long getMachine() {
		return machine;
	}
	public long getDepot() {
		return depot;
	}
	public long getDataBase() {
		return database;
	}
	public String getUrl() {
		return url;
	}
	public String getMachineIp() {
		return com.FileManagerX.Tools.Url.getIp(url);
	}
	public int getMachinePort() {
		return com.FileManagerX.Tools.Url.getPort(url);
	}
	public String getDriver() {
		return com.FileManagerX.Tools.Url.getDriver(url);
	}
	public String getPath() {
		return com.FileManagerX.Tools.Url.getPath(url);
	}
	public String getName() {
		return com.FileManagerX.Tools.Url.getName(url);
	}
	public String getNameWithoutExtension() {
		return com.FileManagerX.Tools.Url.getNameWithoutExtension(url);
	}
	public String getExtension() {
		return com.FileManagerX.Tools.Url.getExtension(url);
	}
	public com.FileManagerX.BasicEnums.FileType getType() {
		return type;
	}
	public com.FileManagerX.BasicEnums.FileState getState() {
		return state;
	}
	public long getModify() {
		return modify;
	}
	public long getLength() {
		return length;
	}
	public int getScore() {
		return score;
	}
	public String getTags() {
		return tags;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean setIndex(long index) {
		this.index = index;
		return true;
	}
	public boolean setIndex() {
		this.index = com.FileManagerX.Globals.Configurations.Next_FileIndex();
		return true;
	}
	public boolean setFather(long father) {
		this.father = father;
		return true;
	}
	public boolean setMachine(long machine) {
		this.machine = machine;
		return true;
	}
	public boolean setDepot(long depot) {
		this.depot = depot;
		return true;
	}
	public boolean setDataBase(long database) {
		this.database = database;
		return true;
	}
	public boolean setUrl(String url) {
		if(url == null || url.length() == 0) {
			return false;
		}
		this.url = url;
		return true;
	}
	public boolean setType(com.FileManagerX.BasicEnums.FileType type) {
		this.type = type;
		return true;
	}
	public boolean setState(com.FileManagerX.BasicEnums.FileState state) {
		this.state = state;
		return true;
	}
	public boolean setModify(long modify) {
		if(modify < 0) {
			return false;
		}
		this.modify = modify;
		return true;
	}
	public boolean setLength(long length) {
		if(length < 0) {
			return false;
		}
		this.length = length;
		return true;
	}
	public boolean setScore(int score) {
		if(score < 0) {
			return false;
		}
		this.score = score;
		return true;
	}
	public boolean setTags(String tags) {
		if(tags == null || tags.length() == 0) {
			return false;
		}
		this.tags = tags;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public File() {
		initThis();
	}
	private void initThis() {
		index = -1;
		father = -1;
		machine = -1;
		depot = -1;
		database = -1;
		url = "";
		type = com.FileManagerX.BasicEnums.FileType.Unsupport;
		state = com.FileManagerX.BasicEnums.FileState.NotExistInRemote;
		modify = -1;
		length = 0;
		score = 0;
		tags = "";
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void clear() {
		initThis();
	}
	public String toString() {
		if(url == null || url.length() == 0) {
			return "No Name";
		}
		return this.getName();
	}
	public Config toConfig() {
		Config c = new Config( "BaseFile = " );
		c.addToBottom(this.index);
		c.addToBottom(this.father);
		c.addToBottom(this.machine);
		c.addToBottom(this.depot);
		c.addToBottom(this.database);
		c.addToBottom(this.url);
		c.addToBottom(this.type.toString());
		c.addToBottom(this.state.toString());
		c.addToBottom(this.modify);
		c.addToBottom(this.length);
		c.addToBottom(this.score);
		c.addToBottom(this.tags);
		return c;
	}
	public String output() {
		return this.toConfig().output();
	}
	public Config input(String in) {
		return this.input(new Config(in));
	}
	public Config input(Config c) {
		if(c == null) { return null; }
		
		try {
			if(!c.getIsOK()) { return c; }
			this.index = c.fetchFirstLong();
			if(!c.getIsOK()) { return c; }
			this.father = c.fetchFirstLong();
			if(!c.getIsOK()) { return c; }
			this.machine = c.fetchFirstLong();
			if(!c.getIsOK()) { return c; }
			this.depot = c.fetchFirstLong();
			if(!c.getIsOK()) { return c; }
			this.database = c.fetchFirstLong();
			if(!c.getIsOK()) { return c; }
			this.url = c.fetchFirstString();
			if(!c.getIsOK()) { return c; }
			this.type = com.FileManagerX.BasicEnums.FileType.valueOf(c.fetchFirstString());
			if(!c.getIsOK()) { return c; }
			this.state = com.FileManagerX.BasicEnums.FileState.valueOf(c.fetchFirstString());
			if(!c.getIsOK()) { return c; }
			this.modify = c.fetchFirstLong();
			if(!c.getIsOK()) { return c; }
			this.length = c.fetchFirstLong();
			if(!c.getIsOK()) { return c; }
			this.score = c.fetchFirstInt();
			if(!c.getIsOK()) { return c; }
			this.tags = c.fetchFirstString();
			if(!c.getIsOK()) { return c; }
			
			return c;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			c.setIsOK(false);
			return c;
		}
	}
	public void copyReference(Object o) {
		if(o == null) { return; }
		
		if(o instanceof File) {
			File model = (File)o;
			this.index = model.index;
			this.machine = model.machine;
			this.database = model.database;
			this.depot = model.depot;
			this.father = model.father;
			this.url = model.url;
			this.type = model.type;
			this.state = model.state;
			this.modify = model.modify;
			this.length = model.length;
			this.tags = model.tags;
		}
	}
	public void copyValue(Object o) {
		if(o == null) { return; }
		
		if(o instanceof File) {
			File model = (File)o;
			this.index = model.index;
			this.machine = model.machine;
			this.database = model.database;
			this.depot = model.depot;
			this.father = model.father;
			this.url = new String(model.url);
			this.type = model.type;
			this.state = model.state;
			this.modify = model.modify;
			this.length = model.length;
			this.tags = new String(model.tags);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean exists() {
		java.io.File f = new java.io.File(this.url);
		if(!f.exists()) {
			return false;
		}
		if(com.FileManagerX.BasicEnums.FileType.Folder.equals(this.type)) {
			return f.isDirectory();
		}
		else {
			return f.isFile();
		}
	}
	public boolean loadBasicInfo() {
		java.io.File f = new java.io.File(this.url);
		if(!f.exists()) {
			return false;
		}
		
		if(f.isDirectory()) {
			url = f.getAbsolutePath();
			type = com.FileManagerX.BasicEnums.FileType.Folder;
			modify = f.lastModified();
			length = 0;
			return true;
		}
		else {
			url = f.getAbsolutePath();
			type = com.FileManagerX.BasicEnums.FileType.Unsupport;
			modify = f.lastModified();
			length = f.length();
			return true;
		}
	}
	public boolean loadBasicInfo(java.io.File f) {
		if(f==null || !f.exists()) {
			return false;
		}
		
		if(f.isDirectory()) {
			url = f.getAbsolutePath();
			type = com.FileManagerX.BasicEnums.FileType.Folder;
			modify = f.lastModified();
			length = 0;
			return true;
		}
		else {
			url = f.getAbsolutePath();
			type = com.FileManagerX.BasicEnums.FileType.Unsupport;
			modify = f.lastModified();
			length = f.length();
			return true;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
