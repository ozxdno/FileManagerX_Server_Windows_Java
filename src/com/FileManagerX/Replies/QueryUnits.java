package com.FileManagerX.Replies;

public class QueryUnits extends BaseReply {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private com.FileManagerX.Interfaces.IPublic results;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setResults(com.FileManagerX.Interfaces.IPublic results) {
		if(results == null) {
			return false;
		}
		this.results = results;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.Interfaces.IPublic getResults() {
		return this.results;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public QueryUnits() {
		initThis();
	}
	private void initThis() {
		this.results = null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setThis(com.FileManagerX.Interfaces.IPublic results) {
		boolean ok = true;
		ok &= this.setResults(results);
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
		c.addToBottom(this.results.toConfig());
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
			c = this.results.input(c);
			if(!c.getIsOK()) { return c; }
			return c;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			c.setIsOK(false);
			return c;
		}
	}
	public void copyReference(Object o) {
		if(o instanceof QueryUnits) {
			QueryUnits q = (QueryUnits)o;
			super.copyReference(o);
			this.results.copyReference(q.results);
		}
		if(o instanceof BaseReply) {
			super.copyReference(o);
		}
	}
	public void copyValue(Object o) {
		if(o instanceof QueryUnits) {
			QueryUnits q = (QueryUnits)o;
			super.copyReference(o);
			this.results.copyValue(q.results);
		}
		if(o instanceof BaseReply) {
			super.copyValue(o);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
}
