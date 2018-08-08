package com.FileManagerX.BasicCollections;

import java.util.*;

public class Users implements com.FileManagerX.Interfaces.IPublic, com.FileManagerX.Interfaces.ICollection {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private List<com.FileManagerX.BasicModels.User> content;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean setContent(List<com.FileManagerX.BasicModels.User> content) {
		if(content == null) {
			return false;
		}
		this.content = content;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public List<com.FileManagerX.BasicModels.User> getContent() {
		return this.content;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Users() {
		initThis();
	}
	private void initThis() {
		if(content == null) {
			content = new ArrayList<com.FileManagerX.BasicModels.User>();
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
			com.FileManagerX.BasicModels.User e = new com.FileManagerX.BasicModels.User();
			out = e.input(in);
			if(out == null) { break; }
			this.content.add(e);
			in = out;
		}
		return in;
	}
	public void copyReference(Object o) {
		Users m = (Users)o;
		this.content = m.content;
	}
	public void copyValue(Object o) {
		Users m = (Users)o;
		initThis();
		for(int i=0; i<m.getContent().size(); i++) {
			com.FileManagerX.BasicModels.User im = new com.FileManagerX.BasicModels.User();
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
			this.content.add((com.FileManagerX.BasicModels.User)item);
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
	public boolean sortIncrease() {
		@SuppressWarnings("rawtypes")
		Comparator c = new Comparator<com.FileManagerX.BasicModels.User>() {
			public int compare(com.FileManagerX.BasicModels.User e1, com.FileManagerX.BasicModels.User e2) {
				if(e1.getIndex() > e2.getIndex()) {
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
	 * Sort By Index
	 * 
	 */
	@SuppressWarnings("unchecked")
	public boolean sortDecrease() {
		@SuppressWarnings("rawtypes")
		Comparator c = new Comparator<com.FileManagerX.BasicModels.User>() {
			public int compare(com.FileManagerX.BasicModels.User e1, com.FileManagerX.BasicModels.User e2) {
				if(e1.getIndex() > e2.getIndex()) {
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
	
	public int indexOf(String loginName) {
		for(int i=0; i<content.size(); i++) {
			if(content.get(i).getLoginName().equals(loginName)) {
				return i;
			}
		}
		return -1;
	}
	public com.FileManagerX.BasicModels.User search(String loginName) {
		int index = this.indexOf(loginName);
		if(index < 0) {
			return null;
		}
		return content.get(index);
	}
	public com.FileManagerX.BasicModels.User fetch(String loginName) {
		int index = this.indexOf(loginName);
		if(index < 0) {
			return null;
		}
		com.FileManagerX.BasicModels.User i = content.get(index);
		content.remove(index);
		return i;
	}
	public void delete(String loginName) {
		int index = this.indexOf(loginName);
		if(index >= 0) {
			content.remove(index);
		}
	}
	
	public int indexOfNickName(String nickName) {
		for(int i=0; i<content.size(); i++) {
			if(content.get(i).getNickName().equals(nickName)) {
				return i;
			}
		}
		return -1;
	}
	public com.FileManagerX.BasicModels.User searchNickName(String nickName) {
		int index = this.indexOfNickName(nickName);
		if(index < 0) {
			return null;
		}
		return content.get(index);
	}
	public com.FileManagerX.BasicModels.User fetchNickName(String nickName) {
		int index = this.indexOfNickName(nickName);
		if(index < 0) {
			return null;
		}
		com.FileManagerX.BasicModels.User i = content.get(index);
		content.remove(index);
		return i;
	}
	public void deleteNickName(String nickName) {
		int index = this.indexOfNickName(nickName);
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
	public com.FileManagerX.BasicModels.User search(long idx) {
		int index = this.indexOf(idx);
		if(index < 0) {
			return null;
		}
		return content.get(index);
	}
	public com.FileManagerX.BasicModels.User fetch(long idx) {
		int index = this.indexOf(idx);
		if(index < 0) {
			return null;
		}
		com.FileManagerX.BasicModels.User i = content.get(index);
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
