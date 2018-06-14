package DataBaseManager;

import java.util.*;

public class QueryConditions implements Tools.IPublic {

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
		for(QueryCondition m : content) {
			res += ", " + m.toString();
		}
		return res;
	}
	public String output() {
		if(content == null || content.size() == 0) {
			return "";
		}
		String res = content.get(0).output();
		for(QueryCondition m : content) {
			res += "\n" + m.output();
		}
		return res;
	}
	public boolean input(String in) {
		initThis();
		String[] items = Tools.String.split(in, '\n');
		boolean ok = true;
		for(int i=0; i<items.length; i++) {
			QueryCondition o = new QueryCondition();
			ok = o.input(items[i]);
			if(!ok) {
				return ok;
			}
			content.add(o);
		}
		return ok;
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
	
	public int size() {
		return content.size();
	}
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
	public boolean add(QueryCondition item) {
		if(item == null) {
			return false;
		}
		content.add(item);
		return true;
	}
	public void delete(String itemName) {
		int index = this.indexOf(itemName);
		if(index >= 0) {
			content.remove(index);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
