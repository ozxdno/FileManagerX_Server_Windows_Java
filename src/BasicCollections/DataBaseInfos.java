package BasicCollections;

import java.util.*;

public class DataBaseInfos implements Interfaces.IPublic, Interfaces.ICollection {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private List<BasicModels.DataBaseInfo> content;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean setContent(List<BasicModels.DataBaseInfo> content) {
		if(content == null) {
			return false;
		}
		this.content = content;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public List<BasicModels.DataBaseInfo> getContent() {
		return this.content;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public DataBaseInfos() {
		initThis();
	}
	private void initThis() {
		if(content == null) {
			content = new ArrayList<BasicModels.DataBaseInfo>();
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
		String res = this.getClass().getSimpleName() + " = " + Tools.String.getValue(this.getContent().get(0).output());
		for(int i=1; i<this.content.size(); i++) {
			res += "|" + Tools.String.getValue(this.getContent().get(i).output());
		}
		return res;
	}
	public String input(String in) {
		initThis();
		String out = "";
		while(true) {
			BasicModels.DataBaseInfo e = new BasicModels.DataBaseInfo();
			out = e.input(in);
			if(out == null) { break; }
			this.content.add(e);
			in = out;
		}
		return in;
	}
	public void copyReference(Object o) {
		DataBaseInfos m = (DataBaseInfos)o;
		this.content = m.content;
	}
	public void copyValue(Object o) {
		DataBaseInfos m = (DataBaseInfos)o;
		initThis();
		for(int i=0; i<m.getContent().size(); i++) {
			BasicModels.DataBaseInfo im = new BasicModels.DataBaseInfo();
			im.copyValue(m.getContent().get(i));
			this.content.add(im);
		}
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
			this.content.add((BasicModels.DataBaseInfo)item);
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	/**
	 * Sort By Index
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void sortIncrease() {
		@SuppressWarnings("rawtypes")
		Comparator c = new Comparator<BasicModels.DataBaseInfo>() {
			public int compare(BasicModels.DataBaseInfo e1, BasicModels.DataBaseInfo e2) {
				if(e1.getIndex() > e2.getIndex()) {
					return 1;
				} else {
					return -1;
				}
			}
		};
		
		Collections.sort(this.getContent(), c);
	}
	
	/**
	 * Sort By Index
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void sortDecrease() {
		@SuppressWarnings("rawtypes")
		Comparator c = new Comparator<BasicModels.DataBaseInfo>() {
			public int compare(BasicModels.DataBaseInfo e1, BasicModels.DataBaseInfo e2) {
				if(e1.getIndex() > e2.getIndex()) {
					return -1;
				} else {
					return 1;
				}
			}
		};
		
		Collections.sort(this.getContent(), c);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public int indexOf(String url) {
		for(int i=0; i<content.size(); i++) {
			if(content.get(i).getUrl().equals(url)) {
				return i;
			}
		}
		return -1;
	}
	public BasicModels.DataBaseInfo search(String url) {
		int index = this.indexOf(url);
		if(index < 0) {
			return null;
		}
		return content.get(index);
	}
	public BasicModels.DataBaseInfo fetch(String url) {
		int index = this.indexOf(url);
		if(index < 0) {
			return null;
		}
		BasicModels.DataBaseInfo i = content.get(index);
		content.remove(index);
		return i;
	}
	public void delete(String url) {
		int index = this.indexOf(url);
		if(index >= 0) {
			content.remove(index);
		}
	}
	
	public int indexOf(long idx) {
		for(int i=0; i<content.size(); i++) {
			if(content.get(i).getIndex() == idx) {
				return i;
			}
		}
		return -1;
	}
	public BasicModels.DataBaseInfo search(long idx) {
		int index = this.indexOf(idx);
		if(index < 0) {
			return null;
		}
		return content.get(index);
	}
	public BasicModels.DataBaseInfo fetch(long idx) {
		int index = this.indexOf(idx);
		if(index < 0) {
			return null;
		}
		BasicModels.DataBaseInfo i = content.get(index);
		content.remove(index);
		return i;
	}
	public void delete(long idx) {
		int index = this.indexOf(idx);
		if(index >= 0) {
			content.remove(index);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
