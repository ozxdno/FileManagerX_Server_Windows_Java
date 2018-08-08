package com.FileManagerX.DataBase;

import java.util.*;

public class QueryConditions implements com.FileManagerX.Interfaces.IPublic, com.FileManagerX.Interfaces.ICollection {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private List<QueryCondition> content;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean setContent(List<QueryCondition> content) {
		if(content == null) {
			return false;
		}
		this.content = content;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public List<QueryCondition> getContent() {
		return this.content;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public QueryConditions() {
		initThis();
	}
	private void initThis() {
		if(content == null) {
			content = new ArrayList<QueryCondition>();
		}
		content.clear();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void clear() {
		initThis();
	}
	@Override
	public String toString() {
		if(content == null || content.size() == 0) {
			return "Empty";
		}
		String res = content.get(0).toString();
		for(int i=1; i<content.size(); i++) {
			res += ", " + content.get(i).toString();
		}
		return res;
	}
	public String output() {
		if(content == null || content.size() == 0) {
			return "";
		}
		String res = this.getClass().getSimpleName() + " = " + com.FileManagerX.Tools.String.getValue(this.getContent().get(0).output());
		for(int i=1; i<this.content.size(); i++) {
			res += "|" + com.FileManagerX.Tools.String.getValue(this.getContent().get(i).output());
		}
		return res;
	}
	public String input(String in) {
		initThis();
		String out = "";
		while(true) {
			if(in == null) { break; }
			if(com.FileManagerX.Tools.String.clearLRSpace(com.FileManagerX.Tools.String.getValue(in)).length() == 0) { break; }
			
			QueryCondition e = new QueryCondition();
			out = e.input(in);
			if(out == null) { break; }
			this.content.add(e);
			in = out;
		}
		return in;
	}
	public void copyReference(Object o) {
		QueryConditions m = (QueryConditions)o;
		this.content = m.content;
	}
	public void copyValue(Object o) {
		QueryConditions m = (QueryConditions)o;
		initThis();
		for(int i=0; i<m.getContent().size(); i++) {
			QueryCondition im = new QueryCondition();
			im.copyValue(m.getContent().get(i));
			this.content.add(im);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean stringToThis(String str) {
		this.clear();
		if(str == null || str.length() == 0) {
			return true;
		}
		
		String[] items = com.FileManagerX.Tools.String.split(str, ',');
		boolean ok = true;
		for(int i=0; i<items.length; i++) {
			String item = com.FileManagerX.Tools.String.clearLRSpace(items[i]);
			if(item.length() == 0) {
				continue;
			}
			QueryCondition qc = new QueryCondition();
			if(qc.stringToThis(item)) {
				this.content.add(qc);
			}
			else {
				ok = false;
			}
		}
		return ok;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public int size() {
		return content.size();
	}
	public boolean add(Object item) {
		if(item == null) {
			return false;
		}
		try {
			this.content.add((QueryCondition)item);
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	/**
	 * Sort By ItemName
	 * 
	 */
	@SuppressWarnings("unchecked")
	public boolean sortIncrease() {
		@SuppressWarnings("rawtypes")
		Comparator c = new Comparator<QueryCondition>() {
			public int compare(QueryCondition e1, QueryCondition e2) {
				if(e1.getItemName() == null) {
					return -1;
				}
				if(e2.getItemName() == null) {
					return 1;
				}
				if(e1.getItemName().compareTo(e2.getItemName()) > 0) {
					return 1;
				} else {
					return -1;
				}
			}
		};
		
		try {
			Collections.sort(this.getContent(), c);
			return true;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(com.FileManagerX.BasicEnums.ErrorLevel.Error,"Error in Compare",e.toString());
			return false;
		}
	}
	
	/**
	 * Sort By ItemName
	 * 
	 */
	@SuppressWarnings("unchecked")
	public boolean sortDecrease() {
		@SuppressWarnings("rawtypes")
		Comparator c = new Comparator<QueryCondition>() {
			public int compare(QueryCondition e1, QueryCondition e2) {
				if(e1.getItemName() == null) {
					return 1;
				}
				if(e2.getItemName() == null) {
					return -1;
				}
				if(e1.getItemName().compareTo(e2.getItemName()) > 0) {
					return -1;
				} else {
					return 1;
				}
			}
		};
		
		try {
			Collections.sort(this.getContent(), c);
			return true;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(com.FileManagerX.BasicEnums.ErrorLevel.Error,"Error in Compare",e.toString());
			return false;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public int indexOf(String itemName) {
		for(int i=0; i<content.size(); i++) {
			if(content.get(i).getItemName().equals(itemName)) {
				return i;
			}
		}
		return -1;
	}
	public QueryCondition search(String itemName) {
		int index = this.indexOf(itemName);
		if(index < 0) {
			return null;
		}
		return content.get(index);
	}
	public QueryCondition fetch(String itemName) {
		int index = this.indexOf(itemName);
		if(index < 0) {
			return null;
		}
		QueryCondition i = content.get(index);
		content.remove(index);
		return i;
	}
	public void delete(String itemName) {
		int index = this.indexOf(itemName);
		if(index >= 0) {
			content.remove(index);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
