package com.FileManagerX.DataBase;

public class MySQLManager_Invitation extends com.FileManagerX.DataBase.MySQLManager_ANY
	<com.FileManagerX.BasicModels.Invitation, String> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public MySQLManager_Invitation() {
		initThis();
	}
	private void initThis() {
		this.setName("Invitations");
		this.setUnit(Unit.Invitation);
		this.reflect();
		this.setKey("code");
	}
	public com.FileManagerX.BasicModels.Invitation createT() {
		return new com.FileManagerX.BasicModels.Invitation();
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Object querys2(Object conditions) {
		com.FileManagerX.BasicCollections.Invitations res = new com.FileManagerX.BasicCollections.Invitations();
		this.querys(conditions, res);
		return res;
	}
	public Object query2(Object conditions) {
		com.FileManagerX.BasicModels.Invitation res = new com.FileManagerX.BasicModels.Invitation();
		this.query(conditions, res);
		return res;
	}
	public Object removes2(Object items) {
		com.FileManagerX.BasicCollections.Invitations res = new com.FileManagerX.BasicCollections.Invitations();
		this.removes(items, res);
		return res;
	}
	public Object updates2(Object items) {
		com.FileManagerX.BasicCollections.Invitations res = new com.FileManagerX.BasicCollections.Invitations();
		this.updates(items, res);
		return res;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Object readUnsupportField(String field, String value, Object target) {
		if(field.equals("content")) {
			com.FileManagerX.FileModels.CFG cfg = new com.FileManagerX.FileModels.CFG();
			cfg.input(value);
			return cfg.getContent();
		}
		if(field.equals("type")) {
			return com.FileManagerX.BasicEnums.FileType.valueOf(value);
		}
		if(field.equals("state")) {
			return com.FileManagerX.BasicEnums.FileState.valueOf(value);
		}
		return null;
	}
	public String writeUnsupportField(String field, Object value, Object target) {
		if(field.equals("content")) {
			com.FileManagerX.FileModels.CFG cfg = (com.FileManagerX.FileModels.CFG)target;
			return cfg.output();
		}
		return "";
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean check() {
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
