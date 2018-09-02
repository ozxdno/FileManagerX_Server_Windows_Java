package com.FileManagerX.DataBase;

public class QueryConditions extends com.FileManagerX.BasicCollections.BasicArrayList<QueryCondition, String> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public String getKey(QueryCondition item) {
		return item == null ? null : item.getItemName();
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
}
