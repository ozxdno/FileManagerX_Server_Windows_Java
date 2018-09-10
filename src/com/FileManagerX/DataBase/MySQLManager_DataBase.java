package com.FileManagerX.DataBase;

public class MySQLManager_DataBase extends com.FileManagerX.DataBase.MySQLManager_ANY
	<com.FileManagerX.BasicModels.DataBaseInfo> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public MySQLManager_DataBase() {
		initThis();
	}
	private void initThis() {
		this.setName("DataBases");
		this.setUnit(Unit.DataBase);
		this.reflect();
		this.setKey("index");
	}
	public com.FileManagerX.BasicModels.DataBaseInfo createT() {
		return new com.FileManagerX.BasicModels.DataBaseInfo();
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Object querys2(Object conditions) {
		com.FileManagerX.BasicCollections.DataBaseInfos res = new com.FileManagerX.BasicCollections.DataBaseInfos();
		this.querys(conditions, res);
		return res;
	}
	public Object query2(Object conditions) {
		com.FileManagerX.BasicModels.DataBaseInfo res = new com.FileManagerX.BasicModels.DataBaseInfo();
		this.query(conditions, res);
		return res;
	}
	public Object removes2(Object items) {
		com.FileManagerX.BasicCollections.DataBaseInfos res = new com.FileManagerX.BasicCollections.DataBaseInfos();
		this.removes(items, res);
		return res;
	}
	public Object updates2(Object items) {
		com.FileManagerX.BasicCollections.DataBaseInfos res = new com.FileManagerX.BasicCollections.DataBaseInfos();
		this.updates(items, res);
		return res;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Object readUnsupportField(String field, String value, Object target) {
		if(field.equals("machineInfo")) {
			com.FileManagerX.BasicModels.MachineInfo m = new com.FileManagerX.BasicModels.MachineInfo();
			m.setIndex(com.FileManagerX.Tools.Parse.parseLong(value));
			return m;
		}
		if(field.equals("type")) {
			return com.FileManagerX.BasicEnums.DataBaseType.valueOf(value);
		}
		return null;
	}
	public String writeUnsupportField(String field, Object value, Object target) {
		if(field.equals("machineInfo")) {
			com.FileManagerX.BasicModels.MachineInfo m = (com.FileManagerX.BasicModels.MachineInfo)value;
			return String.valueOf(m.getIndex());
		}
		return "";
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean check() {
		com.FileManagerX.Globals.Configurations.setNext_DataBaseIndex(this.checkIndex());
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
