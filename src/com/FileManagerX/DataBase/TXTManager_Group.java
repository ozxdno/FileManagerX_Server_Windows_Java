package com.FileManagerX.DataBase;

public class TXTManager_Group extends TXTManager_ANY<com.FileManagerX.BasicModels.Group, Long> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public TXTManager_Group() {
		initThis();
	}
	private void initThis() {
		this.setName("Groups");
		this.setContent(new com.FileManagerX.BasicCollections.Groups());
		this.setUnit(Unit.Group);
		this.reflect();
	}
	public com.FileManagerX.BasicModels.Group createT() {
		return new com.FileManagerX.BasicModels.Group();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Object querys2(Object conditions) {
		com.FileManagerX.BasicCollections.Groups res = new com.FileManagerX.BasicCollections.Groups();
		this.querys(conditions, res);
		return res;
	}
	public Object query2(Object conditions) {
		com.FileManagerX.BasicModels.Group res = new com.FileManagerX.BasicModels.Group();
		this.query(conditions, res);
		return res;
	}
	public Object removes2(Object items) {
		com.FileManagerX.BasicCollections.Groups res = new com.FileManagerX.BasicCollections.Groups();
		this.removes(items, res);
		return res;
	}
	public Object updates2(Object items) {
		com.FileManagerX.BasicCollections.Groups res = new com.FileManagerX.BasicCollections.Groups();
		this.updates(items, res);
		return res;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public synchronized void nextIndex(com.FileManagerX.BasicModels.Group item) {
		item.setIndex();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean satisfyUnsupport(Object item, Object field, QueryCondition qc) {
		return false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
