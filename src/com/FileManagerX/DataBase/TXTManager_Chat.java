package com.FileManagerX.DataBase;

public class TXTManager_Chat extends TXTManager_ANY<com.FileManagerX.BasicModels.Chat> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public TXTManager_Chat() {
		initThis();
	}
	private void initThis() {
		this.setName("Chats");
		this.setContent(new com.FileManagerX.BasicCollections.Chats());
		this.setUnit(Unit.Chat);
		this.setSaveImmediately(false);
		this.reflect();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Object querys2(Object conditions) {
		com.FileManagerX.BasicCollections.Chats res = new com.FileManagerX.BasicCollections.Chats();
		this.querys(conditions, res);
		return res;
	}
	public Object query2(Object conditions) {
		com.FileManagerX.BasicModels.Chat res = new com.FileManagerX.BasicModels.Chat();
		this.query(conditions, res);
		return res;
	}
	public Object removes2(Object items) {
		com.FileManagerX.BasicCollections.Chats res = new com.FileManagerX.BasicCollections.Chats();
		this.removes(items, res);
		return res;
	}
	public Object updates2(Object items) {
		com.FileManagerX.BasicCollections.Chats res = new com.FileManagerX.BasicCollections.Chats();
		this.updates(items, res);
		return res;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public synchronized void nextIndex(com.FileManagerX.BasicModels.Chat item) {
		item.setIndex();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean satisfyUnsupport(Object item, Object field, QueryCondition qc) {
		return false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
