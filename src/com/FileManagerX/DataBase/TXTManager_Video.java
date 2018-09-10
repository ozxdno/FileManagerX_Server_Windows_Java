package com.FileManagerX.DataBase;

public class TXTManager_Video extends TXTManager_ANY<com.FileManagerX.BasicModels.File> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public TXTManager_Video() {
		initThis();
	}
	private void initThis() {
		this.setName("Videos");
		this.setContent(new com.FileManagerX.BasicCollections.Files());
		this.setUnit(Unit.Video);
		this.setSaveImmediately(false);
		this.reflect();
	}
	public com.FileManagerX.FileModels.Video createT() {
		return new com.FileManagerX.FileModels.Video();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Object querys2(Object conditions) {
		com.FileManagerX.BasicCollections.Files res = new com.FileManagerX.BasicCollections.Files();
		this.querys(conditions, res);
		return res;
	}
	public Object query2(Object conditions) {
		com.FileManagerX.FileModels.Video res = new com.FileManagerX.FileModels.Video();
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
			com.FileManagerX.FileModels.Video cfg = (com.FileManagerX.FileModels.Video)item;
			return TXTManager_ANY.satisfyString(cfg.output(), qc);
		}
		
		return false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
