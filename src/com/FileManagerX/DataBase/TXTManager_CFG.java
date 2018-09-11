package com.FileManagerX.DataBase;

public class TXTManager_CFG extends TXTManager_ANY<com.FileManagerX.BasicModels.File> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public TXTManager_CFG() {
		initThis();
	}
	private void initThis() {
		this.setName("Cfgs");
		this.setContent(new com.FileManagerX.BasicCollections.Files());
		this.setUnit(Unit.CFG);
		this.reflect();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Object querys2(Object conditions) {
		com.FileManagerX.BasicCollections.Files res = new com.FileManagerX.BasicCollections.Files();
		this.querys(conditions, res);
		return res;
	}
	public Object query2(Object conditions) {
		com.FileManagerX.FileModels.CFG res = new com.FileManagerX.FileModels.CFG();
		this.query(conditions, res);
		return res;
	}
	public Object removes2(Object items) {
		com.FileManagerX.BasicCollections.Files res = new com.FileManagerX.BasicCollections.Files();
		this.removes(items, res);
		return res;
	}
	public Object updates2(Object items) {
		com.FileManagerX.BasicCollections.Files res = new com.FileManagerX.BasicCollections.Files();
		this.updates(items, res);
		return res;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public synchronized void nextIndex(com.FileManagerX.BasicModels.File item) {
		item.setIndex();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean satisfyUnsupport(Object item, Object field, QueryCondition qc) {
		if(qc.getItemName().equals("content")) {
			com.FileManagerX.FileModels.CFG cfg = (com.FileManagerX.FileModels.CFG)item;
			return TXTManager_ANY.satisfyString(cfg.output(), qc);
		}
		
		return false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
