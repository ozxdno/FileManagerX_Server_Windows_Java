package com.FileManagerX.BasicModels;

import java.io.*;

/**
 * Tags 用空格作区分
 * 
 * @author ozxdno
 *
 */
public class BaseFile implements com.FileManagerX.Interfaces.IPublic {
	
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
		this.index = com.FileManagerX.Globals.Configurations.Next_FileIndex + 1;
		com.FileManagerX.Globals.Configurations.Next_FileIndex = this.index;
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
	
	public BaseFile() {
		initThis();
	}
	public BaseFile(String url) {
		initThis(new File(url));
	}
	public BaseFile(File localFile) {
		initThis(localFile);
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
	private void initThis(File localFile) {
		index = -1;
		father = -1;
		machine = -1;
		depot = -1;
		database = -1;
		url = localFile.getAbsolutePath();
		type = localFile.isDirectory() ? com.FileManagerX.BasicEnums.FileType.Folder : 
			(com.FileManagerX.Globals.Datas.Supports.search(this.getExtension()) == null ? 
					com.FileManagerX.BasicEnums.FileType.Unsupport :
					com.FileManagerX.Globals.Datas.Supports.search(this.getExtension()).getType());
		state = com.FileManagerX.BasicEnums.FileState.NotExistInRemote;
		modify = localFile.lastModified();
		length = localFile.isDirectory() ? 0 : localFile.length();
		score = 0;
		tags = "";
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void clear() {
		initThis();
	}
	@Override
	public String toString() {
		if(url == null || url.length() == 0) {
			return "[Undefined]";
		}
		return this.url;
	}
	public String output() {
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
		return c.output();
	}
	public String input(String in) {
		Config c = new Config( in );
		this.index = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		this.father = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		this.machine = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		this.depot = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		this.database = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		this.url = c.fetchFirstString();
		if(!c.getIsOK()) { return null; }
		try {
			this.type = com.FileManagerX.BasicEnums.FileType.valueOf(c.fetchFirstString());
			if(!c.getIsOK()) { return null; }
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			return null;
		}
		try {
			this.state = com.FileManagerX.BasicEnums.FileState.valueOf(c.fetchFirstString());
			if(!c.getIsOK()) { return null; }
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			return null;
		}
		
		this.modify = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		this.length = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		this.score = c.fetchFirstInt();
		if(!c.getIsOK()) { return null; }
		this.tags = c.fetchFirstString();
		if(!c.getIsOK()) { return null; }
		
		return c.output();
	}
	public void copyReference(Object o) {
		BaseFile model = (BaseFile)o;
		this.index = model.index;
		this.father = model.father;
		this.url = model.url;
		this.type = model.type;
		this.state = model.state;
		this.modify = model.modify;
		this.length = model.length;
		this.tags = model.tags;
	}
	public void copyValue(Object o) {
		BaseFile model = (BaseFile)o;
		this.index = model.index;
		this.father = model.father;
		this.url = new String(model.url);
		this.type = model.type;
		this.state = model.state;
		this.modify = model.modify;
		this.length = model.length;
		this.tags = new String(model.tags);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public BaseFile toSpecific() {
		if(type == com.FileManagerX.BasicEnums.FileType.Picture) {
		}
		
		return this;
	}
	public boolean exists() {
		File f = new File(url);
		return f.exists();
	}
	public boolean prepare() {
		return true;
	}
	public boolean load() {
		return false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}

