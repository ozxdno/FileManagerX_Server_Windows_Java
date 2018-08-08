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
				com.FileManagerX.Tools.String.clearLRSpace(com.FileManagerX.Tools.String.getField(this.content));
	}
	public String output() {
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config();
		c.setField(com.FileManagerX.Tools.Time.ticks2ShortTime_Second(this.time));
		c.addToBottom(this.time);
		c.addToBottom(this.type.toString());
		c.addToBottom(this.connectionName);
		c.addToBottom(this.threadName);
		c.addToBottom(this.content);
		
		return c.output();
	}
	public String input(String in) {
		try {
			com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config(in);
			
			this.time = c.fetchFirstLong();
			if(!c.getIsOK()) { return null; }
			this.type = com.FileManagerX.BasicEnums.RecordType.valueOf(c.fetchFirstString());
			if(!c.getIsOK()) { return null; }
			this.connectionName = c.fetchFirstString();
			if(!c.getIsOK()) { return null; }
			this.threadName = c.fetchFirstString();
			if(!c.getIsOK()) { return null; }
			this.content = c.getValue();
			if(!c.getIsOK()) { return null; }
			
			return c.output();
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			return null;
		}
	}
	public void copyReference(Object o) {
		Record r = (Record)o;
		this.time = r.time;
		this.type = r.type;
		this.connectionName = r.connectionName;
		this.threadName = r.threadName;
		this.content = r.content;
	}
	public void copyValue(Object o) {
		Record r = (Record)o;
		this.time = r.time;
		this.type = r.type;
		this.connectionName = new String(r.connectionName);
		this.threadName = new String(r.threadName);
		this.content = new String(r.content);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
