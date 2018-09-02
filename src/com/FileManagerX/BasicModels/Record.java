package com.FileManagerX.BasicModels;

public class Record implements com.FileManagerX.Interfaces.IPublic {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private long time;
	private com.FileManagerX.BasicEnums.RecordType type;
	private String connectionName;
	private String threadName;
	private String content;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setTime(long time) {
		if(time < 0) {
			return false;
		}
		this.time = time;
		return true;
	}
	public boolean setTime() {
		this.time = com.FileManagerX.Tools.Time.getTicks();
		return true;
	}
	public boolean setType(com.FileManagerX.BasicEnums.RecordType type) {
		if(type == null) {
			return false;
		}
		this.type = type;
		return true;
	}
	public boolean setConnectionName(String name) {
		if(name == null) {
			return false;
		}
		this.connectionName = name;
		return true;
	}
	public boolean setThreadName(String name) {
		if(name == null) {
			return false;
		}
		this.threadName = name;
		return true;
	}
	public boolean setContent(String content) {
		if(content == null) {
			content = "NULL";
		}
		this.content = content;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public long getTime() {
		return time;
	}
	public com.FileManagerX.BasicEnums.RecordType getType() {
		return this.type;
	}
	public String getConnectionName() {
		return this.connectionName;
	}
	public String getThreadName() {
		return this.threadName;
	}
	public String getContent() {
		return this.content;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Record() {
		initThis();
	}
	public void initThis() {
		
		this.time = com.FileManagerX.Tools.Time.getTicks();
		this.type = com.FileManagerX.BasicEnums.RecordType.UNDEFINE;
		this.connectionName = "";
		this.threadName = "";
		this.content = "";
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void clear() {
		initThis();
	}
	public String toString() {
		return "[" + this.threadName + " " + this.connectionName + "] " + 
			    this.type.toString() + ": " +
				com.FileManagerX.Tools.StringUtil.getField_FV(this.content);
	}
	public Config toConfig() {
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config();
		c.setField(com.FileManagerX.Tools.Time.ticks2ShortTime_Second(this.time));
		c.addToBottom(this.time);
		c.addToBottom(this.type.toString());
		c.addToBottom(this.connectionName);
		c.addToBottom(this.threadName);
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
			this.time = c.fetchFirstLong();
			if(!c.getIsOK()) { return c; }
			this.type = com.FileManagerX.BasicEnums.RecordType.valueOf(c.fetchFirstString());
			if(!c.getIsOK()) { return c; }
			this.connectionName = c.fetchFirstString();
			if(!c.getIsOK()) { return c; }
			this.threadName = c.fetchFirstString();
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
		
		if(o instanceof Record) {
			Record r = (Record)o;
			this.time = r.time;
			this.type = r.type;
			this.connectionName = r.connectionName;
			this.threadName = r.threadName;
			this.content = r.content;
		}
	}
	public void copyValue(Object o) {
		if(o == null) { return; }
		
		if(o instanceof Record) {
			Record r = (Record)o;
			this.time = r.time;
			this.type = r.type;
			this.connectionName = new String(r.connectionName);
			this.threadName = new String(r.threadName);
			this.content = new String(r.content);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
