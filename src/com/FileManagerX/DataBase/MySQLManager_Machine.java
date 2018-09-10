package com.FileManagerX.DataBase;

public class MySQLManager_Machine extends com.FileManagerX.DataBase.MySQLManager_ANY
	<com.FileManagerX.BasicModels.MachineInfo> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public MySQLManager_Machine() {
		initThis();
	}
	private void initThis() {
		this.setName("Machines");
		this.setUnit(Unit.Machine);
		this.reflect();
		this.setKey("index");
	}
	public com.FileManagerX.BasicModels.MachineInfo createT() {
		return new com.FileManagerX.BasicModels.MachineInfo();
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Object querys2(Object conditions) {
		com.FileManagerX.BasicCollections.MachineInfos res = new com.FileManagerX.BasicCollections.MachineInfos();
		this.querys(conditions, res);
		return res;
	}
	public Object query2(Object conditions) {
		com.FileManagerX.BasicModels.MachineInfo res = new com.FileManagerX.BasicModels.MachineInfo();
		this.query(conditions, res);
		return res;
	}
	public Object removes2(Object items) {
		com.FileManagerX.BasicCollections.MachineInfos res = new com.FileManagerX.BasicCollections.MachineInfos();
		this.removes(items, res);
		return res;
	}
	public Object updates2(Object items) {
		com.FileManagerX.BasicCollections.MachineInfos res = new com.FileManagerX.BasicCollections.MachineInfos();
		this.updates(items, res);
		return res;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Object readUnsupportField(String field, String value, Object target) {
		if(field.equals("type")) {
			return com.FileManagerX.BasicEnums.MachineType.valueOf(value);
		}
		if(field.equals("state")) {
			return com.FileManagerX.BasicEnums.MachineState.valueOf(value);
		}
		return null;
	}
	public String writeUnsupportField(String field, Object value, Object target) {
		return null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean check() {
		return true;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
