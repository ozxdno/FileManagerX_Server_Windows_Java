package com.FileManagerX.DataBase;

public class MySQLManager_Depot extends com.FileManagerX.DataBase.MySQLManager_ANY
	<com.FileManagerX.BasicModels.DepotInfo, Long> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public MySQLManager_Depot() {
		initThis();
	}
	private void initThis() {
		this.setName("Depots");
		this.setUnit(Unit.Depot);
		this.reflect();
		this.setKey("index");
	}
	public com.FileManagerX.BasicModels.DepotInfo createT() {
		return new com.FileManagerX.BasicModels.DepotInfo();
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Object querys2(Object conditions) {
		com.FileManagerX.BasicCollections.DepotInfos res = new com.FileManagerX.BasicCollections.DepotInfos();
		this.querys(conditions, res);
		return res;
	}
	public Object query2(Object conditions) {
		com.FileManagerX.BasicModels.DepotInfo res = new com.FileManagerX.BasicModels.DepotInfo();
		this.query(conditions, res);
		return res;
	}
	public Object removes2(Object items) {
		com.FileManagerX.BasicCollections.DepotInfos res = new com.FileManagerX.BasicCollections.DepotInfos();
		this.removes(items, res);
		return res;
	}
	public Object updates2(Object items) {
		com.FileManagerX.BasicCollections.DepotInfos res = new com.FileManagerX.BasicCollections.DepotInfos();
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
		if(field.equals("state")) {
			return com.FileManagerX.BasicEnums.DepotState.valueOf(value);
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
		com.FileManagerX.Globals.Configurations.setNext_DepotIndex(this.checkIndex());
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
