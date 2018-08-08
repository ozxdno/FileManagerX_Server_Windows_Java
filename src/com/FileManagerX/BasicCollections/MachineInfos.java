package com.FileManagerX.BasicCollections;

import java.util.*;

public class MachineInfos implements com.FileManagerX.Interfaces.IPublic, com.FileManagerX.Interfaces.ICollection {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private List<com.FileManagerX.BasicModels.MachineInfo> content;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean setContent(List<com.FileManagerX.BasicModels.MachineInfo> content) {
		if(content == null) {
			return false;
		}
		this.content = content;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public List<com.FileManagerX.BasicModels.MachineInfo> getContent() {
		return this.content;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public MachineInfos() {
		initThis();
	}
	private void initThis() {
		if(content == null) {
			content = new ArrayList<com.FileManagerX.BasicModels.MachineInfo>();
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
			
			com.FileManagerX.BasicModels.MachineInfo e = new com.FileManagerX.BasicModels.MachineInfo();
			out = e.input(in);
			if(out == null) { break; }
			this.content.add(e);
			in = out;
		}
		return in;
	}
	public void copyReference(Object o) {
		MachineInfos m = (MachineInfos)o;
		this.content = m.content;
	}
	public void copyValue(Object o) {
		MachineInfos m = (MachineInfos)o;
		initThis();
		for(int i=0; i<m.getContent().size(); i++) {
			com.FileManagerX.BasicModels.MachineInfo im = new com.FileManagerX.BasicModels.MachineInfo();
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
			this.content.add((com.FileManagerX.BasicModels.MachineInfo)item);
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
		Comparator c = new Comparator<com.FileManagerX.BasicModels.MachineInfo>() {
			public int compare(com.FileManagerX.BasicModels.MachineInfo e1, com.FileManagerX.BasicModels.MachineInfo e2) {
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
		Comparator c = new Comparator<com.FileManagerX.BasicModels.MachineInfo>() {
			public int compare(com.FileManagerX.BasicModels.MachineInfo e1, com.FileManagerX.BasicModels.MachineInfo e2) {
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
	
	public int indexOf(String ip) {
		for(int i=0; i<content.size(); i++) {
			if(content.get(i).getIp().equals(ip)) {
				return i;
			}
		}
		return -1;
	}
	public com.FileManagerX.BasicModels.MachineInfo search(String ip) {
		int index = this.indexOf(ip);
		if(index < 0) {
			return null;
		}
		return content.get(index);
	}
	public com.FileManagerX.BasicModels.MachineInfo fetch(String ip) {
		int index = this.indexOf(ip);
		if(index < 0) {
			return null;
		}
		com.FileManagerX.BasicModels.MachineInfo i = content.get(index);
		content.remove(index);
		return i;
	}
	public void delete(String ip) {
		int index = this.indexOf(ip);
		if(index >= 0) {
			content.remove(index);
		}
	}
	
	public int[] indexOfAll(String ip) {
		ArrayList<Integer> idxs = new ArrayList<Integer>();
		for(int i=0; i<content.size(); i++) {
			if(content.get(i).getIp().equals(ip)) {
				idxs.add(i);
			}
		}
		return com.FileManagerX.Tools.List2Array.toIntArray(idxs);
	}
	public ArrayList<com.FileManagerX.BasicModels.MachineInfo> searchAll(String ip) {
		int[] idxs = this.indexOfAll(ip);
		ArrayList<com.FileManagerX.BasicModels.MachineInfo> res = new ArrayList<com.FileManagerX.BasicModels.MachineInfo>();
		for(int i=0; i<idxs.length; i++) {
			res.add(content.get(idxs[i]));
		}
		return res;
	}
	public ArrayList<com.FileManagerX.BasicModels.MachineInfo> fetchAll(String ip) {
		int[] idxs = this.indexOfAll(ip);
		ArrayList<com.FileManagerX.BasicModels.MachineInfo> res = new ArrayList<com.FileManagerX.BasicModels.MachineInfo>();
		for(int i=idxs.length-1; i>=0; i--) {
			res.add(content.get(idxs[i]));
			content.remove(idxs[i]);
		}
		return res;
	}
	public void deleteAll(String ip) {
		int[] idxs = this.indexOfAll(ip);
		for(int i=idxs.length-1; i>=0; i--) {
			content.remove(idxs[i]);
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
	public com.FileManagerX.BasicModels.MachineInfo search(long idx) {
		int index = this.indexOf(idx);
		if(index < 0) {
			return null;
		}
		return content.get(index);
	}
	public com.FileManagerX.BasicModels.MachineInfo fetch(long idx) {
		int index = this.indexOf(idx);
		if(index < 0) {
			return null;
		}
		com.FileManagerX.BasicModels.MachineInfo i = content.get(index);
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
