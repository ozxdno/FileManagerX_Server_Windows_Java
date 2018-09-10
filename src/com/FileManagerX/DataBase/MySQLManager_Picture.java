package com.FileManagerX.DataBase;

public class MySQLManager_Picture extends com.FileManagerX.DataBase.MySQLManager_ANY
	<com.FileManagerX.BasicModels.File> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public MySQLManager_Picture() {
		initThis();
	}
	private void initThis() {
		this.setName("Pictures");
		this.setUnit(Unit.Picture);
		this.reflect();
		this.setKey("index");
		this.setIsIncreaseKey(false);
	}
	public com.FileManagerX.FileModels.Picture createT() {
		return new com.FileManagerX.FileModels.Picture();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.BasicCollections.Files querys2(Object conditions) {
		com.FileManagerX.BasicCollections.Files res = new com.FileManagerX.BasicCollections.Files();
		this.querys(conditions, res);
		return res;
	}
	public com.FileManagerX.FileModels.Picture query2(Object conditions) {
		com.FileManagerX.FileModels.Picture res = new com.FileManagerX.FileModels.Picture();
		this.query(conditions, res);
		return res;
	}
	public com.FileManagerX.BasicCollections.Files updates2(Object items) {
		com.FileManagerX.BasicCollections.Files res = new com.FileManagerX.BasicCollections.Files();
		this.updates(items, res);
		return res;
	}
	public com.FileManagerX.BasicCollections.Files removes2(Object items) {
		com.FileManagerX.BasicCollections.Files res = new com.FileManagerX.BasicCollections.Files();
		this.removes(items, res);
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
			com.FileManagerX.FileModels.Picture cfg = (com.FileManagerX.FileModels.Picture)target;
			return cfg.output();
		}
		return "";
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean check() {
		com.FileManagerX.Globals.Configurations.Next_FileIndex = this.checkIndex();
		return true;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
