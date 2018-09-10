package com.FileManagerX.DataBase;

public class QueryConditions extends com.FileManagerX.BasicCollections.BasicCollection<QueryCondition> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public QueryConditions() {
		initThis();
	}
	private void initThis() {
		this.setContent(new com.FileManagerX.BasicCollections.BasicLinkedList<>());
		this.setKey(new KeyForItemName());
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public QueryCondition searchByItemName(String item) {
		com.FileManagerX.Interfaces.IIterator<QueryCondition> it = this.getIterator();
		while(it.hasNext()) {
			QueryCondition qc = it.getNext();
			if(qc.getItemName().equals(item)) {
				return qc;
			}
		}
		return null;
	}
	public QueryCondition fetchByItemName(String item) {
		com.FileManagerX.Interfaces.IIterator<QueryCondition> it = this.getIterator();
		while(it.hasNext()) {
			QueryCondition qc = it.getNext();
			if(qc.getItemName().equals(item)) {
				it.remove();
				return qc;
			}
		}
		return null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static class KeyForItemName implements com.FileManagerX.Interfaces.ICollection.IKey {
		public Object getKey(Object item) {
			if(item instanceof QueryCondition) {
				QueryCondition i = (QueryCondition)item;
				return i.getItemName();
			}
			return null;
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
