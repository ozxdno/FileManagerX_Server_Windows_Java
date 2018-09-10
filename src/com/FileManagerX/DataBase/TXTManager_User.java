package com.FileManagerX.DataBase;

public class TXTManager_User extends TXTManager_ANY<com.FileManagerX.BasicModels.User> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public TXTManager_User() {
		initThis();
	}
	private void initThis() {
		this.setName("Users");
		this.setContent(new com.FileManagerX.BasicCollections.Users());
		this.setUnit(Unit.User);
		this.reflect();
	}
	public com.FileManagerX.BasicModels.User createT() {
		return new com.FileManagerX.BasicModels.User();
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

	public synchronized boolean check() {
		com.FileManagerX.Globals.Configurations.setNext_UserIndex(this.checkIndex());
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public synchronized void nextIndex(com.FileManagerX.BasicModels.User item) {
		item.setIndex();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean satisfyUnsupport(Object item, Object field, QueryCondition qc) {
		return false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
