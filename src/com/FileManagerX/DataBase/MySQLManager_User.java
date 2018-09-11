package com.FileManagerX.DataBase;

public class MySQLManager_User extends com.FileManagerX.DataBase.MySQLManager_ANY
	<com.FileManagerX.BasicModels.User> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public MySQLManager_User() {
		initThis();
	}
	private void initThis() {
		this.setName("Users");
		this.setUnit(Unit.User);
		this.reflect();
		this.setKey("index");
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Object querys2(Object conditions) {
		com.FileManagerX.BasicCollections.Users res = new com.FileManagerX.BasicCollections.Users();
		this.querys(conditions, res);
		return res;
	}
	public Object query2(Object conditions) {
		com.FileManagerX.BasicModels.User res = new com.FileManagerX.BasicModels.User();
		this.query(conditions, res);
		return res;
	}
	public Object removes2(Object items) {
		com.FileManagerX.BasicCollections.Users res = new com.FileManagerX.BasicCollections.Users();
		this.removes(items, res);
		return res;
	}
	public Object updates2(Object items) {
		com.FileManagerX.BasicCollections.Users res = new com.FileManagerX.BasicCollections.Users();
		this.updates(items, res);
		return res;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Object readUnsupportField(String field, String value, Object target) {
		if(field.equals("state")) {
			return com.FileManagerX.BasicEnums.UserState.valueOf(value);
		}
		if(field.equals("priority")) {
			return com.FileManagerX.BasicEnums.UserPriority.valueOf(value);
		}
		if(field.equals("level")) {
			return com.FileManagerX.BasicEnums.UserLevel.valueOf(value);
		}
		return null;
	}
	public String writeUnsupportField(String field, Object value, Object target) {
		return "";
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean check() {
		com.FileManagerX.Globals.Configurations.Next_UserIndex = this.checkIndex();
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
