package BasicCollections;

import java.util.*;

public class Supports implements Interfaces.IPublic {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private List<BasicModels.Support> content;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean setContent(List<BasicModels.Support> content) {
		if(content == null) {
			return false;
		}
		this.content = content;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public List<BasicModels.Support> getContent() {
		return this.content;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Supports() {
		initThis();
	}
	private void initThis() {
		if(content == null) {
			content = new ArrayList<BasicModels.Support>();
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
		for(BasicModels.Support m : content) {
			res += ", " + m.toString();
		}
		return res;
	}
	public String output() {
		if(content == null || content.size() == 0) {
			return "";
		}
		String res = content.get(0).output();
		for(BasicModels.Support m : content) {
			res += "\n" + m.output();
		}
		return res;
	}
	public String input(String in) {
		initThis();
		String[] items = Tools.String.split(in, '\n');
		String res = "";
		for(int i=0; i<items.length; i++) {
			BasicModels.Support o = new BasicModels.Support();
			res = o.input(items[i]);
			if(res == null) {
				return null;
			}
			content.add(o);
		}
		return "";
	}
	public void copyReference(Object o) {
		Supports m = (Supports)o;
		this.content = m.content;
	}
	public void copyValue(Object o) {
		Supports m = (Supports)o;
		initThis();
		for(int i=0; i<m.getContent().size(); i++) {
			BasicModels.Support im = new BasicModels.Support();
			im.copyValue(m.getContent().get(i));
			this.content.add(im);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public int size() {
		return content.size();
	}
	public int indexOf(String extension) {
		for(int i=0; i<content.size(); i++) {
			if(content.get(i).getExtension().equals(extension)) {
				return i;
			}
		}
		return -1;
	}
	public BasicModels.Support search(String extension) {
		int index = this.indexOf(extension);
		if(index < 0) {
			return null;
		}
		return content.get(index);
	}
	public BasicModels.Support fetch(String extension) {
		int index = this.indexOf(extension);
		if(index < 0) {
			return null;
		}
		BasicModels.Support i = content.get(index);
		content.remove(index);
		return i;
	}
	public boolean add(BasicModels.Support item) {
		if(item == null) {
			return false;
		}
		content.add(item);
		return true;
	}
	public void delete(String extension) {
		int index = this.indexOf(extension);
		if(index >= 0) {
			content.remove(index);
		}
	}
	
	public int indexOfShowExtension(String show) {
		for(int i=0; i<content.size(); i++) {
			if(content.get(i).getShowExtension().equals(show)) {
				return i;
			}
		}
		return -1;
	}
	public BasicModels.Support searchShowExtension(String show) {
		int index = this.indexOfShowExtension(show);
		if(index < 0) {
			return null;
		}
		return content.get(index);
	}
	public BasicModels.Support fetchShowExtension(String show) {
		int index = this.indexOfShowExtension(show);
		if(index < 0) {
			return null;
		}
		BasicModels.Support i = content.get(index);
		content.remove(index);
		return i;
	}
	public void deleteShowExtension(String show) {
		int index = this.indexOfShowExtension(show);
		if(index >= 0) {
			content.remove(index);
		}
	}
	
	public int indexOfHideExtension(String hide) {
		for(int i=0; i<content.size(); i++) {
			if(content.get(i).getHideExtension().equals(hide)) {
				return i;
			}
		}
		return -1;
	}
	public BasicModels.Support searchHideExtension(String hide) {
		int index = this.indexOfHideExtension(hide);
		if(index < 0) {
			return null;
		}
		return content.get(index);
	}
	public BasicModels.Support fetchHideExtension(String hide) {
		int index = this.indexOfHideExtension(hide);
		if(index < 0) {
			return null;
		}
		BasicModels.Support i = content.get(index);
		content.remove(index);
		return i;
	}
	public void deleteHideExtension(String hide) {
		int index = this.indexOfHideExtension(hide);
		if(index >= 0) {
			content.remove(index);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
