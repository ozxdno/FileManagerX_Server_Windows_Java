package com.FileManagerX.BasicModels;


public class Support implements com.FileManagerX.Interfaces.IPublic {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private String extension;
	private com.FileManagerX.BasicEnums.FileType type;
	private String show;
	private String hide;
	private boolean isSupport;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public String getExtension() {
		return extension;
	}
	public com.FileManagerX.BasicEnums.FileType getType() {
		return type;
	}
	public String getShowExtension() {
		return show;
	}
	public String getHideExtension() {
		return hide;
	}
	public boolean IsSupport() {
		return this.isSupport;
	}
	public boolean isShowExtension() {
		return this.extension.equals(this.show);
	}
	public boolean isHideExtension() {
		return this.extension.equals(hide);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean setExtension(String extension) {
		if(extension == null) {
			return false;
		}
		this.extension = com.FileManagerX.Tools.Url.fixExtension(extension);
		return true;
	}
	public boolean setType(com.FileManagerX.BasicEnums.FileType type) {
		this.type = type;
		return true;
	}
	public boolean setShowExtension(String show) {
		if(show == null) {
			return false;
		}
		this.show = com.FileManagerX.Tools.Url.fixExtension(show);
		return true;
	}
	public boolean setHideExtension(String hide) {
		if(hide == null) {
			return false;
		}
		this.hide = com.FileManagerX.Tools.Url.fixExtension(hide);
		return true;
	}
	public boolean setIsSupport(boolean isSupport) {
		this.isSupport = isSupport;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Support() {
		initThis();
	}
	public Support(com.FileManagerX.BasicEnums.FileType type, String extension) {
		initThis();
		setType(type);
		setExtension(extension);
	}
	private void initThis() {
		this.extension = "";
		this.type = com.FileManagerX.BasicEnums.FileType.Unsupport;
		this.show = "";
		this.hide = "";
		this.isSupport = true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	
	public void clear() {
		initThis();
	}
	public String toString() {
		return this.extension;
	}
	public String output() {
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config("Support = ");
		c.addToBottom(extension);
		c.addToBottom(type.toString());
		c.addToBottom(show);
		c.addToBottom(hide);
		c.addToBottom(isSupport);
		return c.output();
	}
	public String input(String in) {
		try {
			com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config(in);
			extension = c.fetchFirstString();
			if(!c.getIsOK()) { return null; }
			type = com.FileManagerX.BasicEnums.FileType.valueOf(c.fetchFirstString());
			if(!c.getIsOK()) { return null; }
			show = c.fetchFirstString();
			if(!c.getIsOK()) { return null; }
			hide = c.fetchFirstString();
			if(!c.getIsOK()) { return null; }
			isSupport = c.fetchFirstBoolean();
			if(!c.getIsOK()) { return null; }
			return c.output();
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			return null;
		}
	}
	public void copyReference(Object o) {
		Support s = (Support)o;
		this.extension = s.extension;
		this.type = s.type;
		this.show = s.show;
		this.hide = s.hide;
		this.isSupport = s.isSupport;
	}
	public void copyValue(Object o) {
		Support s = (Support)o;
		this.extension = new String(s.extension);
		this.type = s.type;
		this.show = new String(s.show);
		this.hide = new String(s.hide);
		this.isSupport = s.isSupport;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}