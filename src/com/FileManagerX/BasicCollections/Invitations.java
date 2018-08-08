package com.FileManagerX.BasicCollections;

import java.util.*;

public class Invitations implements com.FileManagerX.Interfaces.IPublic, com.FileManagerX.Interfaces.ICollection {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private List<com.FileManagerX.BasicModels.Invitation> content;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean setContent(List<com.FileManagerX.BasicModels.Invitation> content) {
		if(content == null) {
			return false;
		}
		this.content = content;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public List<com.FileManagerX.BasicModels.Invitation> getContent() {
		return this.content;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Invitations() {
		initThis();
	}
	private void initThis() {
		if(content == null) {
			content = new ArrayList<com.FileManagerX.BasicModels.Invitation>();
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
			
			com.FileManagerX.BasicModels.Invitation e = new com.FileManagerX.BasicModels.Invitation();
			out = e.input(in);
			if(out == null) { break; }
			this.content.add(e);
			in = out;
		}
		return in;
	}
	public void copyReference(Object o) {
		Invitations m = (Invitations)o;
		this.content = m.content;
	}
	public void copyValue(Object o) {
		Invitations m = (Invitations)o;
		initThis();
		for(int i=0; i<m.getContent().size(); i++) {
			com.FileManagerX.BasicModels.Invitation im = new com.FileManagerX.BasicModels.Invitation();
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
			this.content.add((com.FileManagerX.BasicModels.Invitation)item);
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	/**
	 * Sort By Code
	 * 
	 */
	@SuppressWarnings("unchecked")
	public boolean sortIncrease() {
		@SuppressWarnings("rawtypes")
		Comparator c = new Comparator<com.FileManagerX.BasicModels.Invitation>() {
			public int compare(com.FileManagerX.BasicModels.Invitation e1, com.FileManagerX.BasicModels.Invitation e2) {
				if(e1.getCode() == null) {
					return -1;
				}
				if(e2.getCode() == null) {
					return 1;
				}
				if(e1.getCode().compareTo(e2.getCode()) > 0) {
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
	 * Sort By Code
	 * 
	 */
	@SuppressWarnings("unchecked")
	public boolean sortDecrease() {
		@SuppressWarnings("rawtypes")
		Comparator c = new Comparator<com.FileManagerX.BasicModels.Invitation>() {
			public int compare(com.FileManagerX.BasicModels.Invitation e1, com.FileManagerX.BasicModels.Invitation e2) {
				if(e1.getCode() == null) {
					return 1;
				}
				if(e2.getCode() == null) {
					return -1;
				}
				if(e1.getCode().compareTo(e2.getCode()) > 0) {
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
	
	public int indexOf(String code) {
		for(int i=0; i<content.size(); i++) {
			if(content.get(i).getCode().equals(code)) {
				return i;
			}
		}
		return -1;
	}
	public com.FileManagerX.BasicModels.Invitation search(String code) {
		int index = this.indexOf(code);
		if(index < 0) {
			return null;
		}
		return content.get(index);
	}
	public com.FileManagerX.BasicModels.Invitation fetch(String code) {
		int index = this.indexOf(code);
		if(index < 0) {
			return null;
		}
		com.FileManagerX.BasicModels.Invitation i = content.get(index);
		content.remove(index);
		return i;
	}
	public void delete(String code) {
		int index = this.indexOf(code);
		if(index >= 0) {
			content.remove(index);
		}
	}
	
	public int[] indexOfAll(String code) {
		ArrayList<Integer> idxs = new ArrayList<Integer>();
		for(int i=0; i<content.size(); i++) {
			if(content.get(i).getCode().equals(code)) {
				idxs.add(i);
			}
		}
		return com.FileManagerX.Tools.List2Array.toIntArray(idxs);
	}
	public ArrayList<com.FileManagerX.BasicModels.Invitation> searchAll(String code) {
		int[] idxs = this.indexOfAll(code);
		ArrayList<com.FileManagerX.BasicModels.Invitation> res = new ArrayList<com.FileManagerX.BasicModels.Invitation>();
		for(int i=0; i<idxs.length; i++) {
			res.add(content.get(idxs[i]));
		}
		return res;
	}
	public ArrayList<com.FileManagerX.BasicModels.Invitation> fetchAll(String code) {
		int[] idxs = this.indexOfAll(code);
		ArrayList<com.FileManagerX.BasicModels.Invitation> res = new ArrayList<com.FileManagerX.BasicModels.Invitation>();
		for(int i=idxs.length-1; i>=0; i--) {
			res.add(content.get(idxs[i]));
			content.remove(idxs[i]);
		}
		return res;
	}
	public void deleteAll(String code) {
		int[] idxs = this.indexOfAll(code);
		for(int i=idxs.length-1; i>=0; i--) {
			content.remove(idxs[i]);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
