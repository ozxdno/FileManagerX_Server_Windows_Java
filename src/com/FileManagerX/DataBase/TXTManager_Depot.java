package com.FileManagerX.DataBase;

public class TXTManager_Depot extends TXTManager_ANY<com.FileManagerX.BasicModels.DepotInfo> {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public TXTManager_Depot() {
		initThis();
	}
	private void initThis() {
		this.setName("Depots");
		this.setContent(new com.FileManagerX.BasicCollections.DepotInfos());
		this.setUnit(Unit.Depot);
		this.reflect();
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

	public synchronized boolean check() {
		com.FileManagerX.Globals.Configurations.setNext_DepotIndex(this.checkIndex());
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public synchronized void nextIndex(com.FileManagerX.BasicModels.DepotInfo item) {
		item.setIndex();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean satisfyUnsupport(Object item, Object field, QueryCondition qc) {
		if(qc.getItemName().equals("machineInfo")) {
			com.FileManagerX.BasicModels.MachineInfo m = (com.FileManagerX.BasicModels.MachineInfo)field;
			return com.FileManagerX.DataBase.TXTManager_ANY.satisfyLong(m.getIndex(), qc);
		}
		if(qc.getItemName().equals("dbInfo")) {
			com.FileManagerX.BasicModels.DataBaseInfo db = (com.FileManagerX.BasicModels.DataBaseInfo)field;
			return com.FileManagerX.DataBase.TXTManager_ANY.satisfyLong(db.getIndex(), qc);
		}
		return false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
