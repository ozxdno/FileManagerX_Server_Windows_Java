package com.FileManagerX.Replies;

public class QueryUnit extends BaseReply {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private com.FileManagerX.Interfaces.IPublic result;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setResult(com.FileManagerX.Interfaces.IPublic result) {
		if(result == null) {
			return false;
		}
		this.result = result;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.Interfaces.IPublic getResult() {
		return this.result;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public QueryUnit() {
		initThis();
	}
	private void initThis() {
		this.result = null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setThis(com.FileManagerX.Interfaces.IPublic result) {
		boolean ok = true;
		ok &= this.setResult(result);
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
		c.addToBottom(this.result.toConfig());
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
			c = this.result.input(c);
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
		QueryUnit qf = (QueryUnit)o;
		this.result = qf.result;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		QueryUnit qf = (QueryUnit)o;
		this.result.copyValue(qf.result);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
