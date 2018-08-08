package com.FileManagerX.BasicCollections;

import java.util.*;

public class Supports implements com.FileManagerX.Interfaces.IPublic, com.FileManagerX.Interfaces.ICollection {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private List<com.FileManagerX.BasicModels.Support> content;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean setContent(List<com.FileManagerX.BasicModels.Support> content) {
		if(content == null) {
			return false;
		}
		this.content = content;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public List<com.FileManagerX.BasicModels.Support> getContent() {
		return this.content;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Supports() {
		initThis();
	}
	private void initThis() {
		if(content == null) {
			content = new ArrayList<com.FileManagerX.BasicModels.Support>();
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
			
			com.FileManagerX.BasicModels.Support e = new com.FileManagerX.BasicModels.Support();
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
			com.FileManagerX.BasicModels.Support im = new com.FileManagerX.BasicModels.Support();
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
			this.content.add((com.FileManagerX.BasicModels.Support)item);
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
		Comparator c = new Comparator<com.FileManagerX.BasicModels.Support>() {
			public int compare(com.FileManagerX.BasicModels.Support e1, com.FileManagerX.BasicModels.Support e2) {
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
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(com.FileManagerX.BasicEnums.ErrorLevel.Error,"Error in Compare",e.toString());
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
		Comparator c = new Comparator<com.FileManagerX.BasicModels.Support>() {
			public int compare(com.FileManagerX.BasicModels.Support e1, com.FileManagerX.BasicModels.Support e2) {
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
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(com.FileManagerX.BasicEnums.ErrorLevel.Error,"Error in Compare",e.toString());
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
	public com.FileManagerX.BasicModels.Support search(String extension) {
		int index = this.indexOf(extension);
		if(index < 0) {
			return null;
		}
		return content.get(index);
	}
	public com.FileManagerX.BasicModels.Support fetch(String extension) {
		int index = this.indexOf(extension);
		if(index < 0) {
			return null;
		}
		com.FileManagerX.BasicModels.Support i = content.get(index);
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
	public com.FileManagerX.BasicModels.Support searchShowExtension(String show) {
		int index = this.indexOfShowExtension(show);
		if(index < 0) {
			return null;
		}
		return content.get(index);
	}
	public com.FileManagerX.BasicModels.Support fetchShowExtension(String show) {
		int index = this.indexOfShowExtension(show);
		if(index < 0) {
			return null;
		}
		com.FileManagerX.BasicModels.Support i = content.get(index);
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
	public com.FileManagerX.BasicModels.Support searchHideExtension(String hide) {
		int index = this.indexOfHideExtension(hide);
		if(index < 0) {
			return null;
		}
		return content.get(index);
	}
	public com.FileManagerX.BasicModels.Support fetchHideExtension(String hide) {
		int index = this.indexOfHideExtension(hide);
		if(index < 0) {
			return null;
		}
		com.FileManagerX.BasicModels.Support i = content.get(index);
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
