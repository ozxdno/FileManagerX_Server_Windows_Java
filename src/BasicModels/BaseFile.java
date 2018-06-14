package BasicModels;

import java.io.*;

public class BaseFile implements Tools.IPublic {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private long index;
	private BaseFile left;
	private BaseFile right;
	private Folder father;
	private BaseFile son;
	private String url;
	private BasicEnums.FileType type;
	private BasicEnums.FileState state;
	private long modify;
	private long length;
	private int score;
	private String tags;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public long getIndex() {
		return index;
	}
	public BaseFile getLeft() {
		return left;
	}
	public BaseFile getRight() {
		return right;
	}
	public Folder getFather() {
		return father;
	}
	public BaseFile getSon() {
		return son;
	}
	public String getUrl() {
		return url;
	}
	public String getMachineUrl() {
		return Tools.Url.getMachineUrl(url);
	}
	public String getMachineIp() {
		return Tools.Url.getIp(url);
	}
	public int getMachinePort() {
		return Tools.Url.getPort(url);
	}
	public String getDriver() {
		return Tools.Url.getDriver(url);
	}
	public String getPath() {
		return Tools.Url.getPath(url);
	}
	public String getName() {
		return Tools.Url.getName(url);
	}
	public String getNameWithoutExtension() {
		return Tools.Url.getNameWithoutExtension(url);
	}
	public String getExtension() {
		return Tools.Url.getExtension(url);
	}
	public BasicEnums.FileType getType() {
		return type;
	}
	public BasicEnums.FileState getState() {
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
		if(index < 0) {
			return false;
		}
		this.index = index;
		return true;
	}
	public boolean setLeft(BaseFile left) {
		this.left = left;
		return true;
	}
	public boolean setRight(BaseFile right) {
		this.right = right;
		return true;
	}
	public boolean setFather(Folder father) {
		this.father = father;
		return true;
	}
	public boolean setSon(BaseFile son) {
		this.son = son;
		return true;
	}
	public boolean setNextIndex() {
		
		return true;
	}
	public boolean setUrl(String url) {
		if(url == null || url.length() == 0) {
			return false;
		}
		this.url = url;
		return true;
	}
	public boolean setType(BasicEnums.FileType type) {
		this.type = type;
		return true;
	}
	public boolean setState(BasicEnums.FileState state) {
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
		setNextIndex();
	}
	public BaseFile(String url) {
		
	}
	public BaseFile(File localFile) {
		
	}
	public BaseFile(MachineInfo machine, File file) {
		
	}
	private void initThis() {
		index = -1;
		left = null;
		right = null;
		father = null;
		son = null;
		url = "";
		type = BasicEnums.FileType.Unsupport;
		state = BasicEnums.FileState.NotExistInRemote;
		modify = -1;
		length = 0;
		score = 0;
		tags = "";
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void clear() {
		initThis();
	}
	@Override
	public String toString() {
		if(url == null || url.length() == 0 || index < 0) {
			return "Undefined";
		}
		return this.getName();
	}
	public String output() {
		Config c = new Config( "BaseFile = " );
		c.addToBottom(this.index);
		c.addToBottom(this.url);
		c.addToBottom(this.type.ordinal());
		c.addToBottom(this.state.ordinal());
		c.addToBottom(this.modify);
		c.addToBottom(this.length);
		c.addToBottom(this.score);
		c.addToBottom(this.tags);
		return c.output();
	}
	public boolean input(String in) {
		Config c = new Config( in );
		this.index = c.fetchFirstLong();
		if(!c.getIsOK()) { return false; }
		this.url = c.fetchFirstString();
		if(!c.getIsOK()) { return false; }
		this.type = BasicEnums.FileType.values()[c.fetchFirstInt()];
		if(!c.getIsOK()) { return false; }
		this.state = BasicEnums.FileState.values()[c.fetchFirstInt()];
		if(!c.getIsOK()) { return false; }
		this.modify = c.fetchFirstLong();
		if(!c.getIsOK()) { return false; }
		this.length = c.fetchFirstLong();
		if(!c.getIsOK()) { return false; }
		this.score = c.fetchFirstInt();
		if(!c.getIsOK()) { return false; }
		this.tags = c.getValue();
		return true;
	}
	public void copyReference(Object o) {
		BaseFile model = (BaseFile)o;
		this.index = model.index;
		this.left = model.left;
		this.right = model.right;
		this.father = model.father;
		this.son = model.son;
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
		this.left = model.left;
		this.right = model.right;
		this.father = model.father;
		this.son = model.son;
		this.url = new String(model.url);
		this.type = model.type;
		this.state = model.state;
		this.modify = model.modify;
		this.length = model.length;
		this.tags = new String(model.tags);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public BaseFile toSpecific() {
		if(type == BasicEnums.FileType.Picture) {
		}
		
		return this;
	}
	public boolean exists() {
		return true;
	}
	public boolean isLocal() {
		MachineInfo m = new MachineInfo(this.getMachineUrl());
		return m.isLocal();
	}
	public boolean register() {
		return true;
	}
	public boolean prepare() {
		return true;
	}
	public boolean load() {
		return false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}

