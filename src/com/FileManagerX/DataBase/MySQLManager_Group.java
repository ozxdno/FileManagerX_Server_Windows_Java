package com.FileManagerX.DataBase;

public class MySQLManager_Group extends com.FileManagerX.DataBase.MySQLManager_ANY
	<com.FileManagerX.BasicModels.Group, Long> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public MySQLManager_Group() {
		initThis();
	}
	private void initThis() {
		this.setName("Groups");
		this.setUnit(Unit.Group);
		this.reflect();
		this.setKey("index");
	}
	public com.FileManagerX.BasicModels.Group createT() {
		return new com.FileManagerX.BasicModels.Group();
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Object querys2(Object conditions) {
		com.FileManagerX.BasicCollections.Groups res = new com.FileManagerX.BasicCollections.Groups();
		this.querys(conditions, res);
		return res;
	}
	public Object query2(Object conditions) {
		com.FileManagerX.BasicModels.Group res = new com.FileManagerX.BasicModels.Group();
		this.query(conditions, res);
		return res;
	}
	public Object removes2(Object items) {
		com.FileManagerX.BasicCollections.Groups res = new com.FileManagerX.BasicCollections.Groups();
		this.removes(items, res);
		return res;
	}
	public Object updates2(Object items) {
		com.FileManagerX.BasicCollections.Groups res = new com.FileManagerX.BasicCollections.Groups();
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
		com.FileManagerX.Globals.Configurations.Next_GroupIndex = this.checkIndex();
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
