package com.FileManagerX.BasicCollections;

import java.util.*;

public class Chats implements com.FileManagerX.Interfaces.IPublic, com.FileManagerX.Interfaces.ICollection {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private List<com.FileManagerX.BasicModels.Chat> content;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean setContent(List<com.FileManagerX.BasicModels.Chat> content) {
		if(content == null) {
			com.FileManagerX.BasicEnums.ErrorType.COMMON_SET_WRONG_VALUE.register("content is NULL");
			return false;
		}
		this.content = content;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public List<com.FileManagerX.BasicModels.Chat> getContent() {
		return this.content;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Chats() {
		initThis();
	}
	private void initThis() {
		if(content == null) {
			content = new ArrayList<com.FileManagerX.BasicModels.Chat>();
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
			
			com.FileManagerX.BasicModels.Chat e = new com.FileManagerX.BasicModels.Chat();
			out = e.input(in);
			if(out == null) { break; }
			this.content.add(e);
			in = out;
		}
		return in;
	}
	public void copyReference(Object o) {
		Chats m = (Chats)o;
		this.content = m.content;
	}
	public void copyValue(Object o) {
		Chats m = (Chats)o;
		initThis();
		for(int i=0; i<m.getContent().size(); i++) {
			com.FileManagerX.BasicModels.Chat im = new com.FileManagerX.BasicModels.Chat();
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
			this.content.add((com.FileManagerX.BasicModels.Chat)item);
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	/**
	 * Sort By Time
	 * 
	 */
	@SuppressWarnings("unchecked")
	public boolean sortIncrease() {
		@SuppressWarnings("rawtypes")
		Comparator c = new Comparator<com.FileManagerX.BasicModels.Chat>() {
			public int compare(com.FileManagerX.BasicModels.Chat e1, com.FileManagerX.BasicModels.Chat e2) {
				if(e1.getTime() > e2.getTime()) {
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
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register("Error in Compare",e.toString());
			return false;
		}
	}
	
	/**
	 * Sort By Time
	 * 
	 */
	@SuppressWarnings("unchecked")
	public boolean sortDecrease() {
		@SuppressWarnings("rawtypes")
		Comparator c = new Comparator<com.FileManagerX.BasicModels.Chat>() {
			public int compare(com.FileManagerX.BasicModels.Chat e1, com.FileManagerX.BasicModels.Chat e2) {
				if(e1.getTime() > e2.getTime()) {
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
	
	public int indexOf(String content) {
		for(int i=0; i<this.content.size(); i++) {
			if(this.content.get(i).getContent().equals(content)) {
				return i;
			}
		}
		return -1;
	}
	public com.FileManagerX.BasicModels.Chat search(String content) {
		int index = this.indexOf(content);
		if(index < 0) {
			return null;
		}
		return this.content.get(index);
	}
	public com.FileManagerX.BasicModels.Chat fetch(String content) {
		int index = this.indexOf(content);
		if(index < 0) {
			return null;
		}
		com.FileManagerX.BasicModels.Chat i = this.content.get(index);
		this.content.remove(index);
		return i;
	}
	public void delete(String content) {
		int index = this.indexOf(content);
		if(index >= 0) {
			this.content.remove(index);
		}
	}
	
	public int indexOf(long time) {
		for(int i=0; i<content.size(); i++) {
			if(content.get(i).getTime() == time) {
				return i;
			}
		}
		return -1;
	}
	public com.FileManagerX.BasicModels.Chat search(long time) {
		int index = this.indexOf(time);
		if(index < 0) {
			return null;
		}
		return content.get(index);
	}
	public com.FileManagerX.BasicModels.Chat fetch(long time) {
		int index = this.indexOf(time);
		if(index < 0) {
			return null;
		}
		com.FileManagerX.BasicModels.Chat i = content.get(index);
		content.remove(index);
		return i;
	}
	public void delete(long time) {
		int index = this.indexOf(time);
		if(index >= 0) {
			content.remove(index);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
