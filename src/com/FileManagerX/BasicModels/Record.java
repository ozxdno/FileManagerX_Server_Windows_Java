package com.FileManagerX.BasicModels;

public class Record implements com.FileManagerX.Interfaces.IPublic {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private long time;
	private com.FileManagerX.BasicEnums.RecordType type;
	private String sour;
	private String dest;
	private String deliver;
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
	public boolean setSour(String sour) {
		if(sour == null) {
			return false;
		}
		this.sour = sour;
		return true;
	}
	public boolean setDest(String dest) {
		if(dest == null) {
			return false;
		}
		this.dest = dest;
		return true;
	}
	public boolean setDeliver(String deliver) {
		if(deliver == null) {
			return false;
		}
		this.deliver = deliver;
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
	public String getSour() {
		return this.sour;
	}
	public String getDest() {
		return this.dest;
	}
	public String getDeliver() {
		return this.deliver;
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
		this.sour = "";
		this.dest = "";
		this.deliver = "";
		this.content = "";
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void clear() {
		initThis();
	}
	public String toString() {
		return this.type.toString() + ": " +
				com.FileManagerX.Tools.StringUtil.getField_FV(this.content);
	}
	public Config toConfig() {
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config();
		c.setField(com.FileManagerX.Tools.Time.ticks2ShortTime_Second(this.time));
		c.addToBottom(this.time);
		c.addToBottom(this.type.toString());
		c.addToBottom(this.sour);
		c.addToBottom(this.dest);
		c.addToBottom(this.deliver);
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
			this.sour = c.fetchFirstString();
			if(!c.getIsOK()) { return c; }
			this.dest = c.fetchFirstString();
			if(!c.getIsOK()) { return c; }
			this.deliver = c.fetchFirstString();
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
			this.sour = r.sour;
			this.dest = r.dest;
			this.content = r.content;
		}
	}
	public void copyValue(Object o) {
		if(o == null) { return; }
		
		if(o instanceof Record) {
			Record r = (Record)o;
			this.time = r.time;
			this.type = r.type;
			this.sour = new String(r.sour);
			this.dest = new String(r.dest);
			this.content = new String(r.content);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
