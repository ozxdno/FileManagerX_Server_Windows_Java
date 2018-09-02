package com.FileManagerX.BasicModels;

public class Chat implements com.FileManagerX.Interfaces.IPublic {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private long index;
	private com.FileManagerX.BasicEnums.ChatType type;
	private long time;
	private long sourUser;
	private long destUser;
	private long sourGroup;
	private long destGroup;
	private String content;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean setIndex(long index) {
		this.index = index;
		return true;
	}
	public boolean setIndex() {
		this.index = com.FileManagerX.Globals.Configurations.Next_ChatIndex();
		return true;
	}
	public boolean setType(com.FileManagerX.BasicEnums.ChatType type) {
		if(type == null) {
			return false;
		}
		this.type = type;
		return true;
	}
	public boolean setTime(long time) {
		this.time = time;
		return true;
	}
	public boolean setTime() {
		this.time = com.FileManagerX.Tools.Time.getTicks();
		return true;
	}
	public boolean setSourUser(long user) {
		this.sourUser = user;
		return true;
	}
	public boolean setDestUser(long user) {
		this.destUser = user;
		return true;
	}
	public boolean setSourGroup(long group) {
		this.sourGroup = group;
		return true;
	}
	public boolean setDestGroup(long group) {
		this.destGroup = group;
		return true;
	}
	public boolean setContent(String content) {
		if(content == null) {
			return false;
		}
		if(content.length() == 0) {
			return false;
		}
		this.content = content;
		return true;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public long getIndex() {
		return index;
	}
	public com.FileManagerX.BasicEnums.ChatType getType() {
		return type;
	}
	public long getTime() {
		return time;
	}
	public long getSourUser() {
		return sourUser;
	}
	public long getDestUser() {
		return destUser;
	}
	public long getSourGroup() {
		return sourGroup;
	}
	public long getDestGroup() {
		return destGroup;
	}
	public String getContent() {
		return content;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Chat() {
		initThis();
	}
	private void initThis() {
		this.index = 0;
		this.type = com.FileManagerX.BasicEnums.ChatType.ONE_ONE;
		this.time = com.FileManagerX.Tools.Time.getTicks();
		this.sourUser = 0;
		this.destUser = 0;
		this.sourGroup = 0;
		this.destGroup = 0;
		this.content = "";
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void clear() {
		initThis();
	}
	public String toString() {
		return this.content.length() == 0 ? "Empty" : this.content;
	}
	public Config toConfig() {
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config();
		c.setField(this.getClass().getSimpleName());
		c.addToBottom(this.index);
		c.addToBottom(this.time);
		c.addToBottom(this.sourUser);
		c.addToBottom(this.destUser);
		c.addToBottom(this.sourGroup);
		c.addToBottom(this.destGroup);
		c.addToBottom_Encode(this.content);
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
			this.time = c.fetchFirstLong();
			if(!c.getIsOK()) { return c; }
			this.sourUser = c.fetchFirstLong();
			if(!c.getIsOK()) { return c; }
			this.destUser = c.fetchFirstLong();
			if(!c.getIsOK()) { return c; }
			this.sourGroup = c.fetchFirstLong();
			if(!c.getIsOK()) { return c; }
			this.destGroup = c.fetchFirstLong();
			if(!c.getIsOK()) { return c; }
			this.content = c.fetchFirstString_Decode();
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
		
		if(o instanceof Chat) {
			Chat c = (Chat)o;
			this.index = c.index;
			this.time = c.time;
			this.destUser = c.destUser;
			this.sourUser = c.sourUser;
			this.sourGroup = c.sourGroup;
			this.destGroup = c.destGroup;
			this.content = c.content;
			return;
		}
	}
	public void copyValue(Object o) {
		if(o == null) { return; }
		
		if(o instanceof Chat) {
			Chat c = (Chat)o;
			this.index = c.index;
			this.time = c.time;
			this.destUser = c.destUser;
			this.sourUser = c.sourUser;
			this.sourGroup = c.sourGroup;
			this.destGroup = c.destGroup;
			this.content = new String(c.content);
			return;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
