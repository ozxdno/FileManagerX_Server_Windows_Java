package com.FileManagerX.DataBase;

public class TXTManager_Music extends TXTManager_ANY<com.FileManagerX.BasicModels.File> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public TXTManager_Music() {
		initThis();
	}
	private void initThis() {
		this.setName("Musics");
		this.setContent(new com.FileManagerX.BasicCollections.Files());
		this.setUnit(Unit.Music);
		this.setSaveImmediately(false);
		this.reflect();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Object querys2(Object conditions) {
		com.FileManagerX.BasicCollections.Files res = new com.FileManagerX.BasicCollections.Files();
		this.querys(conditions, res);
		return res;
	}
	public Object query2(Object conditions) {
		com.FileManagerX.FileModels.Music res = new com.FileManagerX.FileModels.Music();
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
			com.FileManagerX.FileModels.Music cfg = (com.FileManagerX.FileModels.Music)item;
			return TXTManager_ANY.satisfyString(cfg.output(), qc);
		}
		
		return false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
