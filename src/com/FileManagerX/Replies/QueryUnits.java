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
	public boolean setQueryFolders(com.FileManagerX.Interfaces.IPublic results, com.FileManagerX.Interfaces.IConnection connection) {
		boolean ok = true;
		ok &= this.getBasicMessagePackage().setThis(connection.getClientConnection());
		ok &= this.setConnection(connection);
		ok &= this.setThis(results);
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
	public String output() {
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config();
		c.setField(this.getClass().getSimpleName());
		c.addToBottom(new com.FileManagerX.BasicModels.Config(super.output()));
		c.addToBottom(com.FileManagerX.Coder.Encoder.Encode_String2String(this.results.output()));
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) { return null; }
		
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config(in);
		this.results.input(com.FileManagerX.Coder.Decoder.Decode_String2String(c.fetchFirstString()));
		if(!c.getIsOK()) { return null; }
		
		return c.output();
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
