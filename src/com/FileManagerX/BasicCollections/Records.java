package com.FileManagerX.BasicCollections;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.*;

public class Records implements com.FileManagerX.Interfaces.IPublic, com.FileManagerX.Interfaces.ICollection {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private List<com.FileManagerX.BasicModels.Record> content;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean setContent(List<com.FileManagerX.BasicModels.Record> content) {
		if(content == null) {
			return false;
		}
		this.content = content;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public List<com.FileManagerX.BasicModels.Record> getContent() {
		return this.content;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Records() {
		initThis();
	}
	private void initThis() {
		if(content == null) {
			content = new ArrayList<com.FileManagerX.BasicModels.Record>();
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
			
			com.FileManagerX.BasicModels.Record e = new com.FileManagerX.BasicModels.Record();
			out = e.input(in);
			if(out == null) { break; }
			this.content.add(e);
			in = out;
		}
		return in;
	}
	public void copyReference(Object o) {
		Records m = (Records)o;
		this.content = m.content;
	}
	public void copyValue(Object o) {
		Records m = (Records)o;
		initThis();
		for(int i=0; i<m.getContent().size(); i++) {
			com.FileManagerX.BasicModels.Record im = new com.FileManagerX.BasicModels.Record();
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
			this.content.add((com.FileManagerX.BasicModels.Record)item);
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
		Comparator c = new Comparator<com.FileManagerX.BasicModels.Record>() {
			public int compare(com.FileManagerX.BasicModels.Record e1, com.FileManagerX.BasicModels.Record e2) {
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
		Comparator c = new Comparator<com.FileManagerX.BasicModels.Record>() {
			public int compare(com.FileManagerX.BasicModels.Record e1, com.FileManagerX.BasicModels.Record e2) {
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
	
	public boolean save() {
		boolean ok = true;
		
		for(com.FileManagerX.BasicModels.Record e : this.content) {
			String url = com.FileManagerX.Tools.Pathes.getFolder_REC() + "\\" + com.FileManagerX.Tools.Time.ticks2ShortTime_Date(e.getTime()) + ".rec";
			java.io.File rec = new java.io.File(url);
			try {
				if(!rec.exists()) {
					rec.createNewFile();
				}
				BufferedWriter bw = new BufferedWriter(new FileWriter(rec, true));
				bw.write(e.output());
				bw.newLine();
				bw.flush();
				bw.close();
				
			} catch(Exception exception) {
				ok = false;
				com.FileManagerX.BasicEnums.ErrorType.COMMON_FILE_WRITE_FAILED.register(exception.toString());
				continue;
			}
		}
		
		this.content.clear();
		return ok;
	}
	public boolean save(int amount) {
		if(this.content.size() < amount) {
			return true;
		}
		
		boolean ok = true;
		for(int i=0; i<amount; i++) {
			com.FileManagerX.BasicModels.Record e = this.content.get(0);
			this.content.remove(0);
			String url = com.FileManagerX.Tools.Pathes.getFolder_REC() + "\\" + com.FileManagerX.Tools.Time.ticks2ShortTime_Date(e.getTime()) + ".rec";
			java.io.File rec = new java.io.File(url);
			try {
				if(!rec.exists()) {
					rec.createNewFile();
				}
				BufferedWriter bw = new BufferedWriter(new FileWriter(rec, true));
				bw.write(e.output());
				bw.newLine();
				bw.flush();
				bw.close();
				
			} catch(Exception exception) {
				ok = false;
				com.FileManagerX.BasicEnums.ErrorType.COMMON_FILE_WRITE_FAILED.register(exception.toString());
				continue;
			}
		}
		return ok;
	}
	public boolean deleteAgoRecords(int permitAmount) {
		boolean ok = true;
		
		java.io.File logFolder = new java.io.File(com.FileManagerX.Tools.Pathes.getFolder_REC());
		java.io.File[] files = logFolder.listFiles();
		java.util.List<Long> time = new java.util.ArrayList<Long>();
		for(java.io.File f : files) {
			if(com.FileManagerX.Tools.Url.getExtension(f.getName()).equals(".rec")) {
				long t = com.FileManagerX.Tools.Time.shortTimeDate2Ticks(
						com.FileManagerX.Tools.Url.getNameWithoutExtension(f.getName())
						);
				if(t > 0) {
					time.add(t);
				}
			}
		}
		if(time.size() > permitAmount) {
			java.util.Collections.sort(time);
			for(int i=permitAmount; i<time.size(); i++) {
				String url = com.FileManagerX.Tools.Pathes.getFolder_REC() + 
						"\\" + 
						com.FileManagerX.Tools.Time.ticks2ShortTime_Date(time.get(i)) +
						".rec";
				java.io.File f = new java.io.File(url);
				try {
					ok &= f.delete();
				} catch(Exception e) {
					com.FileManagerX.BasicEnums.ErrorType.COMMON_FILE_OPERATE_FAILED.register(
							"Delete Rec File Failed",
							e.toString()
							);
					ok = false;
				}
			}
		}
		return ok;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public int indexOf(long time) {
		for(int i=0; i<content.size(); i++) {
			if(content.get(i).getTime() == time) {
				return i;
			}
		}
		return -1;
	}
	public com.FileManagerX.BasicModels.Record search(long time) {
		int index = this.indexOf(time);
		if(index < 0) {
			return null;
		}
		return content.get(index);
	}
	public com.FileManagerX.BasicModels.Record fetch(long time) {
		int index = this.indexOf(time);
		if(index < 0) {
			return null;
		}
		com.FileManagerX.BasicModels.Record i = content.get(index);
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
