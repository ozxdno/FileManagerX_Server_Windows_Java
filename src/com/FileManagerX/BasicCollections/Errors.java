package com.FileManagerX.BasicCollections;

import java.io.*;
import java.util.*;

public class Errors implements com.FileManagerX.Interfaces.IPublic, com.FileManagerX.Interfaces.ICollection {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private List<com.FileManagerX.BasicModels.Error> content;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean setContent(List<com.FileManagerX.BasicModels.Error> content) {
		if(content == null) {
			return false;
		}
		this.content = content;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public List<com.FileManagerX.BasicModels.Error> getContent() {
		return this.content;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Errors() {
		initThis();
	}
	private void initThis() {
		if(content == null) {
			content = new ArrayList<com.FileManagerX.BasicModels.Error>();
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
			
			com.FileManagerX.BasicModels.Error e = new com.FileManagerX.BasicModels.Error();
			out = e.input(in);
			if(out == null) { break; }
			this.content.add(e);
			in = out;
		}
		return in;
	}
	public void copyReference(Object o) {
		Errors m = (Errors)o;
		this.content = m.content;
	}
	public void copyValue(Object o) {
		Errors m = (Errors)o;
		initThis();
		for(int i=0; i<m.getContent().size(); i++) {
			com.FileManagerX.BasicModels.Error im = new com.FileManagerX.BasicModels.Error();
			im.copyValue(m.getContent().get(i));
			this.content.add(im);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public int size() {
		return content.size();
	}
	public synchronized boolean add(Object item) {
		if(item == null) {
			return false;
		}
		try {
			this.content.add((com.FileManagerX.BasicModels.Error)item);
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	/**
	 * Sort By Type
	 * 
	 */
	@SuppressWarnings("unchecked")
	public boolean sortIncrease() {
		@SuppressWarnings("rawtypes")
		Comparator c = new Comparator<com.FileManagerX.BasicModels.Error>() {
			public int compare(com.FileManagerX.BasicModels.Error e1, com.FileManagerX.BasicModels.Error e2) {
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
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(com.FileManagerX.BasicEnums.ErrorLevel.Error,"Error in Compare",e.toString());
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
		Comparator c = new Comparator<com.FileManagerX.BasicModels.Error>() {
			public int compare(com.FileManagerX.BasicModels.Error e1, com.FileManagerX.BasicModels.Error e2) {
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
	
	public int indexOf(com.FileManagerX.BasicEnums.ErrorType type) {
		for(int i=0; i<content.size(); i++) {
			if(content.get(i).getType().equals(type)) {
				return i;
			}
		}
		return -1;
	}
	public com.FileManagerX.BasicModels.Error search(com.FileManagerX.BasicEnums.ErrorType type) {
		int index = this.indexOf(type);
		if(index < 0) {
			return null;
		}
		return content.get(index);
	}
	public com.FileManagerX.BasicModels.Error fetch(com.FileManagerX.BasicEnums.ErrorType type) {
		int index = this.indexOf(type);
		if(index < 0) {
			return null;
		}
		com.FileManagerX.BasicModels.Error i = content.get(index);
		content.remove(index);
		return i;
	}
	public void delete(com.FileManagerX.BasicEnums.ErrorType type) {
		int index = this.indexOf(type);
		if(index >= 0) {
			content.remove(index);
		}
	}
	
	public int indexOf(String tip) {
		for(int i=0; i<content.size(); i++) {
			if(content.get(i).getTip().equals(tip)) {
				return i;
			}
		}
		return -1;
	}
	public com.FileManagerX.BasicModels.Error search(String tip) {
		int index = this.indexOf(tip);
		if(index < 0) {
			return null;
		}
		return content.get(index);
	}
	public com.FileManagerX.BasicModels.Error fetch(String tip) {
		int index = this.indexOf(tip);
		if(index < 0) {
			return null;
		}
		com.FileManagerX.BasicModels.Error i = content.get(index);
		content.remove(index);
		return i;
	}
	public void delete(String tip) {
		int index = this.indexOf(tip);
		if(index >= 0) {
			content.remove(index);
		}
	}
	
	public int[] indexOfAll(com.FileManagerX.BasicEnums.ErrorType type) {
		ArrayList<Integer> idxs = new ArrayList<Integer>();
		for(int i=0; i<content.size(); i++) {
			if(content.get(i).getType().equals(type)) {
				idxs.add(i);
			}
		}
		return com.FileManagerX.Tools.List2Array.toIntArray(idxs);
	}
	public ArrayList<com.FileManagerX.BasicModels.Error> searchAll(com.FileManagerX.BasicEnums.ErrorType type) {
		int[] idxs = this.indexOfAll(type);
		ArrayList<com.FileManagerX.BasicModels.Error> res = new ArrayList<com.FileManagerX.BasicModels.Error>();
		for(int i=0; i<idxs.length; i++) {
			res.add(content.get(idxs[i]));
		}
		return res;
	}
	public ArrayList<com.FileManagerX.BasicModels.Error> fetchAll(com.FileManagerX.BasicEnums.ErrorType type) {
		int[] idxs = this.indexOfAll(type);
		ArrayList<com.FileManagerX.BasicModels.Error> res = new ArrayList<com.FileManagerX.BasicModels.Error>();
		for(int i=idxs.length-1; i>=0; i--) {
			res.add(content.get(idxs[i]));
			content.remove(idxs[i]);
		}
		return res;
	}
	public void deleteAll(com.FileManagerX.BasicEnums.ErrorType type) {
		int[] idxs = this.indexOfAll(type);
		for(int i=idxs.length-1; i>=0; i--) {
			content.remove(idxs[i]);
		}
	}
	
	public int[] indexOfAllBefore(long time) {
		ArrayList<Integer> idxs = new ArrayList<Integer>();
		for(int i=0; i<content.size(); i++) {
			if(content.get(i).getTime() <= time) {
				idxs.add(i);
			}
		}
		return com.FileManagerX.Tools.List2Array.toIntArray(idxs);
	}
	public ArrayList<com.FileManagerX.BasicModels.Error> searchAllBefore(long time) {
		int[] idxs = this.indexOfAllBefore(time);
		ArrayList<com.FileManagerX.BasicModels.Error> res = new ArrayList<com.FileManagerX.BasicModels.Error>();
		for(int i=0; i<idxs.length; i++) {
			res.add(content.get(idxs[i]));
		}
		return res;
	}
	public ArrayList<com.FileManagerX.BasicModels.Error> fetchAllBefore(long time) {
		int[] idxs = this.indexOfAllBefore(time);
		ArrayList<com.FileManagerX.BasicModels.Error> res = new ArrayList<com.FileManagerX.BasicModels.Error>();
		for(int i=idxs.length-1; i>=0; i--) {
			res.add(content.get(idxs[i]));
			content.remove(idxs[i]);
		}
		return res;
	}
	public void deleteAllBefore(long time) {
		int[] idxs = this.indexOfAllBefore(time);
		for(int i=idxs.length-1; i>=0; i--) {
			content.remove(idxs[i]);
		}
	}
	
	public int[] indexOfAllBehind(long time) {
		ArrayList<Integer> idxs = new ArrayList<Integer>();
		for(int i=0; i<content.size(); i++) {
			if(content.get(i).getTime() >= time) {
				idxs.add(i);
			}
		}
		return com.FileManagerX.Tools.List2Array.toIntArray(idxs);
	}
	public ArrayList<com.FileManagerX.BasicModels.Error> searchAllBehind(long time) {
		int[] idxs = this.indexOfAllBehind(time);
		ArrayList<com.FileManagerX.BasicModels.Error> res = new ArrayList<com.FileManagerX.BasicModels.Error>();
		for(int i=0; i<idxs.length; i++) {
			res.add(content.get(idxs[i]));
		}
		return res;
	}
	public ArrayList<com.FileManagerX.BasicModels.Error> fetchAllBehind(long time) {
		int[] idxs = this.indexOfAllBehind(time);
		ArrayList<com.FileManagerX.BasicModels.Error> res = new ArrayList<com.FileManagerX.BasicModels.Error>();
		for(int i=idxs.length-1; i>=0; i--) {
			res.add(content.get(idxs[i]));
			content.remove(idxs[i]);
		}
		return res;
	}
	public void deleteAllBehind(long time) {
		int[] idxs = this.indexOfAllBehind(time);
		for(int i=idxs.length-1; i>=0; i--) {
			content.remove(idxs[i]);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean save() {
		Errors newErrors = new Errors();
		boolean ok = true;
		
		for(com.FileManagerX.BasicModels.Error e : this.content) {
			String url = com.FileManagerX.Tools.Pathes.getFolder_LOG() + "\\" + e.getShortTime_Date() + ".log";
			java.io.File log = new java.io.File(url);
			try {
				if(!log.exists()) {
					log.createNewFile();
				}
				BufferedWriter bw = new BufferedWriter(new FileWriter(log, true));
				bw.write(e.output());
				bw.newLine();
				bw.flush();
				bw.close();
				
			} catch(Exception exception) {
				ok = false;
				com.FileManagerX.BasicModels.Error ie = com.FileManagerX.BasicEnums.ErrorType.COMMON_FILE_WRITE_FAILED.getError(exception.toString());
				newErrors.add(ie);
				continue;
			}
		}
		
		this.content = newErrors.content;
		return ok;
	}
	public boolean save(int amount) {
		if(this.content.size() < amount) {
			return true;
		}
		
		boolean ok = true;
		for(int i=0; i<amount; i++) {
			com.FileManagerX.BasicModels.Error e = this.content.get(0);
			this.content.remove(0);
			String url = com.FileManagerX.Tools.Pathes.getFolder_LOG() + "\\" + e.getShortTime_Date() + ".log";
			java.io.File log = new java.io.File(url);
			try {
				if(!log.exists()) {
					log.createNewFile();
				}
				BufferedWriter bw = new BufferedWriter(new FileWriter(log, true));
				bw.write(e.output());
				bw.newLine();
				bw.flush();
				bw.close();
				
			} catch(Exception exception) {
				ok = false;
				com.FileManagerX.BasicModels.Error ie = com.FileManagerX.BasicEnums.ErrorType.COMMON_FILE_WRITE_FAILED.getError(exception.toString());
				this.content.add(ie);
				continue;
			}
		}
		return ok;
	}
	public boolean deleteAgoLogs(int permitLogAmount) {
		boolean ok = true;
		
		java.io.File logFolder = new java.io.File(com.FileManagerX.Tools.Pathes.getFolder_LOG());
		java.io.File[] files = logFolder.listFiles();
		java.util.List<Long> time = new java.util.ArrayList<Long>();
		for(java.io.File f : files) {
			if(com.FileManagerX.Tools.Url.getExtension(f.getName()).equals(".log")) {
				long t = com.FileManagerX.Tools.Time.shortTimeDate2Ticks(com.FileManagerX.Tools.Url.getNameWithoutExtension(f.getName()));
				if(t > 0) {
					time.add(t);
				}
			}
		}
		if(time.size() > permitLogAmount) {
			java.util.Collections.sort(time);
			for(int i=permitLogAmount; i<time.size(); i++) {
				String url = com.FileManagerX.Tools.Pathes.getFolder_LOG() + "\\" + com.FileManagerX.Tools.Time.ticks2ShortTime_Date(time.get(i)) + ".log";
				java.io.File f = new java.io.File(url);
				try {
					ok &= f.delete();
				} catch(Exception e) {
					com.FileManagerX.BasicEnums.ErrorType.COMMON_FILE_OPERATE_FAILED.register("Delete Log File Failed", e.toString());
					ok = false;
				}
			}
		}
		return ok;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
