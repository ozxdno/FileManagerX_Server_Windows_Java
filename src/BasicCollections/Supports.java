package BasicCollections;

import java.util.*;

public class Supports implements Interfaces.IPublic, Interfaces.ICollection {

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
			if(in == null) { break; }
			if(Tools.String.clearLRSpace(Tools.String.getValue(in)).length() == 0) { break; }
			
			BasicModels.Support e = new BasicModels.Support();
			out = e.input(in);
			if(out == null) { break; }
			this.content.add(e);
			in = out;
		}
		return in;
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
	public boolean add(Object item) {
		if(item == null) {
			return false;
		}
		try {
			this.content.add((BasicModels.Support)item);
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	/**
	 * Sort By Extension
	 * 
	 */
	@SuppressWarnings("unchecked")
	public boolean sortIncrease() {
		@SuppressWarnings("rawtypes")
		Comparator c = new Comparator<BasicModels.Support>() {
			public int compare(BasicModels.Support e1, BasicModels.Support e2) {
				if(e1.getExtension() == null) {
					return -1;
				}
				if(e2.getExtension() == null) {
					return 1;
				}
				if(e1.getExtension().compareTo(e2.getExtension()) > 0) {
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
			BasicEnums.ErrorType.OTHERS.register(BasicEnums.ErrorLevel.Error,"Error in Compare",e.toString());
			return false;
		}
	}
	
	/**
	 * Sort By Extension
	 * 
	 */
	@SuppressWarnings("unchecked")
	public boolean sortDecrease() {
		@SuppressWarnings("rawtypes")
		Comparator c = new Comparator<BasicModels.Support>() {
			public int compare(BasicModels.Support e1, BasicModels.Support e2) {
				if(e1.getExtension() == null) {
					return 1;
				}
				if(e2.getExtension() == null) {
					return -1;
				}
				if(e1.getExtension().compareTo(e2.getExtension()) > 0) {
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
			BasicEnums.ErrorType.OTHERS.register(BasicEnums.ErrorLevel.Error,"Error in Compare",e.toString());
			return false;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
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
	
	public boolean isShowExtension(String extension) {
		return this.indexOfShowExtension(extension) >= 0;
	}
	public boolean isHideExtension(String extension) {
		return this.indexOfHideExtension(extension) >= 0;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
