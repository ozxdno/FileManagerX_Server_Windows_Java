package com.FileManagerX.Replies;

public class QuerySize extends BaseReply {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private long size;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setSize(long size) {
		if(size < 0) {
			return false;
		}
		this.size = size;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public long getSize() {
		return this.size;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public QuerySize() {
		initThis();
	}
	private void initThis() {
		this.size = 0;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setThis(int size) {
		boolean ok = true;
		ok &= this.setSize(size);
		return ok;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void clear() {
		super.clear();
		initThis();
	}
	public String toString() {
		return this.output();
	}
	public com.FileManagerX.BasicModels.Config toConfig() {
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config();
		c.setField(this.getClass().getSimpleName());
		c.addToBottom(super.toConfig());
		c.addToBottom(this.size);
		return c;
	}
	public String output() {
		return this.toConfig().output();
	}
	public com.FileManagerX.BasicModels.Config input(String in) {
		return this.input(new com.FileManagerX.BasicModels.Config(in));
	}
	public com.FileManagerX.BasicModels.Config input(com.FileManagerX.BasicModels.Config c) {
		if(c == null) { return null; }
		try {
			if(!c.getIsOK()) { return c; }
			c = super.input(c);
			if(!c.getIsOK()) { return c; }
			this.size = c.fetchFirstLong();
			if(!c.getIsOK()) { return c; }
			return c;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			c.setIsOK(false);
			return c;
		}
	}
	public void copyReference(Object o) {
		super.copyReference(o);
		QuerySize t = (QuerySize)o;
		this.size = t.size;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		QuerySize t = (QuerySize)o;
		this.size = t.size;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
