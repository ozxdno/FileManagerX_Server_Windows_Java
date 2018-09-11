package com.FileManagerX.DataBase;

public class MySQLManager_Chat extends com.FileManagerX.DataBase.MySQLManager_ANY
	<com.FileManagerX.BasicModels.Chat> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public MySQLManager_Chat() {
		initThis();
	}
	private void initThis() {
		this.setName("Chats");
		this.setUnit(Unit.Chat);
		this.reflect();
		this.setKey("index");
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Object querys2(Object conditions) {
		com.FileManagerX.BasicCollections.Chats res = new com.FileManagerX.BasicCollections.Chats();
		this.querys(conditions, res);
		return res;
	}
	public Object query2(Object conditions) {
		com.FileManagerX.BasicModels.Chat res = new com.FileManagerX.BasicModels.Chat();
		this.query(conditions, res);
		return res;
	}
	public Object removes2(Object items) {
		com.FileManagerX.BasicCollections.Chats res = new com.FileManagerX.BasicCollections.Chats();
		this.removes(items, res);
		return res;
	}
	public Object updates2(Object items) {
		com.FileManagerX.BasicCollections.Chats res = new com.FileManagerX.BasicCollections.Chats();
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
		com.FileManagerX.Globals.Configurations.Next_ChatIndex = this.checkIndex();
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
