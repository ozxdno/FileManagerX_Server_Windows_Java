package com.FileManagerX.DataBase;

public class TXTManager_Invitation extends TXTManager_ANY<com.FileManagerX.BasicModels.Invitation, String> {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public TXTManager_Invitation() {
		initThis();
	}
	private void initThis() {
		this.setName("Invitations");
		this.setContent(new com.FileManagerX.BasicCollections.Invitations());
		this.setUnit(Unit.Invitation);
		this.reflect();
	}
	public com.FileManagerX.BasicModels.Invitation createT() {
		return new com.FileManagerX.BasicModels.Invitation();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Object querys2(Object conditions) {
		com.FileManagerX.BasicCollections.Invitations res = new com.FileManagerX.BasicCollections.Invitations();
		this.querys(conditions, res);
		return res;
	}
	public Object query2(Object conditions) {
		com.FileManagerX.BasicModels.Invitation res = new com.FileManagerX.BasicModels.Invitation();
		this.query(conditions, res);
		return res;
	}
	public Object removes2(Object items) {
		com.FileManagerX.BasicCollections.Invitations res = new com.FileManagerX.BasicCollections.Invitations();
		this.removes(items, res);
		return res;
	}
	public Object updates2(Object items) {
		com.FileManagerX.BasicCollections.Invitations res = new com.FileManagerX.BasicCollections.Invitations();
		this.updates(items, res);
		return res;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public synchronized void nextIndex(com.FileManagerX.BasicModels.Invitation item) {
		;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean satisfyUnsupport(Object item, Object field, QueryCondition qc) {
		return false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
