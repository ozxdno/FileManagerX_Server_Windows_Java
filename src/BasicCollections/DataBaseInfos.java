package BasicCollections;

import java.util.*;

public class DataBaseInfos implements Interfaces.IPublic {

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
		for(BasicModels.DataBaseInfo m : content) {
			res += ", " + m.toString();
		}
		return res;
	}
	public String output() {
		if(content == null || content.size() == 0) {
			return "";
		}
		String res = content.get(0).output();
		for(BasicModels.DataBaseInfo m : content) {
			res += "\n" + m.output();
		}
		return res;
	}
	public String input(String in) {
		initThis();
		String[] items = Tools.String.split(in, '\n');
		String res = "";
		for(int i=0; i<items.length; i++) {
			BasicModels.DataBaseInfo o = new BasicModels.DataBaseInfo();
			res = o.input(items[i]);
			if(res == null) {
				return null;
			}
			content.add(o);
		}
		return "";
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
	public boolean add(BasicModels.DataBaseInfo item) {
		if(item == null) {
			return false;
		}
		content.add(item);
		return true;
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
