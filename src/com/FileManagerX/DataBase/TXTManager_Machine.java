package com.FileManagerX.DataBase;

public class TXTManager_Machine extends TXTManager_ANY<com.FileManagerX.BasicModels.MachineInfo> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public TXTManager_Machine() {
		initThis();
	}
	private void initThis() {
		this.setName("Machines");
		this.setContent(new com.FileManagerX.BasicCollections.MachineInfos());
		this.setUnit(Unit.Machine);
		this.reflect();
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

	public synchronized boolean check() {
		com.FileManagerX.Globals.Configurations.setNext_MachineIndex(this.checkIndex());
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public synchronized void nextIndex(com.FileManagerX.BasicModels.MachineInfo item) {
		item.setIndex();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean satisfyUnsupport(Object item, Object field, QueryCondition qc) {
		
		
		return false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
