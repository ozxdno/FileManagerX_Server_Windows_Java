package com.FileManagerX.DataBase;

public class TXTManager_DataBase extends TXTManager_ANY<com.FileManagerX.BasicModels.DataBaseInfo> {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public TXTManager_DataBase() {
		initThis();
	}
	private void initThis() {
		this.setName("DataBases");
		this.setContent(new com.FileManagerX.BasicCollections.DataBaseInfos());
		this.setUnit(Unit.DataBase);
		this.reflect();
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

	public synchronized boolean check() {
		com.FileManagerX.Globals.Configurations.setNext_DataBaseIndex(this.checkIndex());
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public synchronized void nextIndex(com.FileManagerX.BasicModels.DataBaseInfo item) {
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
