package BasicCollections;

import java.util.*;

public class BaseFiles implements Interfaces.IPublic {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private List<BasicModels.BaseFile> content;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean setContent(List<BasicModels.BaseFile> content) {
		if(content == null) {
			return false;
		}
		this.content = content;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public List<BasicModels.BaseFile> getContent() {
		return this.content;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public BaseFiles() {
		initThis();
	}
	private void initThis() {
		if(content == null) {
			content = new ArrayList<BasicModels.BaseFile>();
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
		String res = content.get(0).output();
		for(int i=1; i<content.size(); i++) {
			res += "\n" + content.get(i).output();
		}
		return res;
	}
	public String input(String in) {
		initThis();
		String[] items = Tools.String.split(in, '\n');
		String res = "";
		for(int i=0; i<items.length; i++) {
			BasicModels.BaseFile o = new BasicModels.BaseFile();
			res = o.input(items[i]);
			if(res == null) {
				return null;
			}
			content.add(o);
		}
		return "";
	}
	public void copyReference(Object o) {
		BaseFiles m = (BaseFiles)o;
		this.content = m.content;
	}
	public void copyValue(Object o) {
		BaseFiles m = (BaseFiles)o;
		initThis();
		for(int i=0; i<m.getContent().size(); i++) {
			BasicModels.BaseFile im = new BasicModels.BaseFile();
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
	public BasicModels.BaseFile search(String url) {
		int index = this.indexOf(url);
		if(index < 0) {
			return null;
		}
		return content.get(index);
	}
	public BasicModels.BaseFile fetch(String url) {
		int index = this.indexOf(url);
		if(index < 0) {
			return null;
		}
		BasicModels.BaseFile i = content.get(index);
		content.remove(index);
		return i;
	}
	public boolean add(BasicModels.BaseFile item) {
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
	public BasicModels.BaseFile search(long idx) {
		int index = this.indexOf(idx);
		if(index < 0) {
			return null;
		}
		return content.get(index);
	}
	public BasicModels.BaseFile fetch(long idx) {
		int index = this.indexOf(idx);
		if(index < 0) {
			return null;
		}
		BasicModels.BaseFile i = content.get(index);
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
