package com.FileManagerX.BasicModels;

public class Error implements com.FileManagerX.Interfaces.IPublic {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private com.FileManagerX.BasicEnums.ErrorType type;
	private long time;
	private com.FileManagerX.BasicEnums.ErrorLevel level;
	private String reason;
	private String tip;
	private String detail;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.BasicEnums.ErrorType getType() {
		return type;
	}
	public long getTime() {
		return time;
	}
	public String getLongTime() {
		return com.FileManagerX.Tools.Time.ticks2LongTime(time);
	}
	public String getShortTime_Date() {
		return com.FileManagerX.Tools.Time.ticks2ShortTime_Date(time);
	}
	public String getShortTime_Second() {
		return com.FileManagerX.Tools.Time.ticks2ShortTime_Second(time);
	}
	public com.FileManagerX.BasicEnums.ErrorLevel getLevel() {
		return level;
	}
	public String getReason() {
		return reason;
	}
	public String getTip() {
		return tip;
	}
	public String getDetail() {
		return detail;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setType(com.FileManagerX.BasicEnums.ErrorType type) {
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
	public boolean setLevel(com.FileManagerX.BasicEnums.ErrorLevel level) {
		if(level == null) {
			com.FileManagerX.BasicEnums.ErrorType.COMMON_NULL.register();
			return false;
		}
		this.level = level;
		return true;
	}
	public boolean setReason(String reason) {
		if(reason == null) {
			return false;
		}
		this.reason = reason;
		return true;
	}
	public boolean setTip(String tip) {
		if(tip == null) {
			return false;
		}
		this.tip = tip;
		return true;
	}
	public boolean setDetail(String detail) {
		if(detail == null) {
			return false;
		}
		this.detail = detail;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Error() {
		initThis();
	}
	public Error(com.FileManagerX.BasicEnums.ErrorType type) {
		initThis();
		this.setType(type);
		this.setTime();
	}
	private void initThis() {
		type = com.FileManagerX.BasicEnums.ErrorType.NORMAL;
		time = com.FileManagerX.Tools.Time.getTicks();
		level = com.FileManagerX.BasicEnums.ErrorLevel.Normal;
		reason = "";
		tip = "";
		detail = "";
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void clear() {
		initThis();
	}
	public String toString() {
		return "[" + this.getLongTime() + "] " + type.toString() + ": " + reason;
	}
	public Config toConfig() {
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config();
		c.setField(this.getShortTime_Second());
		c.addToBottom(type.toString());
		c.addToBottom(time);
		c.addToBottom(level.toString());
		c.addToBottom(reason);
		c.addToBottom(tip);
		c.addToBottom(detail);
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
			type = com.FileManagerX.BasicEnums.ErrorType.valueOf(c.fetchFirstString());
			if(!c.getIsOK()) { return c; }
			time = c.fetchFirstLong();
			if(!c.getIsOK()) { return c; }
			level = com.FileManagerX.BasicEnums.ErrorLevel.valueOf(c.fetchFirstString());
			if(!c.getIsOK()) { return c; }
			reason = c.fetchFirstString();
			if(!c.getIsOK()) { return c; }
			tip = c.fetchFirstString();
			if(!c.getIsOK()) { return c; }
			detail = c.fetchFirstString();
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
		
		if(o instanceof Error) {
			Error e = (Error)o;
			type = e.type;
			time = e.time;
			level = e.level;
			reason = e.reason;
			tip = e.tip;
			detail = e.detail;
			return;
		}
	}
	public void copyValue(Object o) {
		if(o == null) { return; }
		
		if(o instanceof Error) {
			Error e = (Error)o;
			type = e.type;
			time = e.time;
			level = e.level;
			reason = new String(e.reason);
			tip = new String(e.tip);
			detail = new String(e.detail);
			return;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void register() {
		com.FileManagerX.Globals.Datas.Errors.add(this);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
