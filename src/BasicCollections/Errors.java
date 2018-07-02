package BasicCollections;

import java.io.*;
import java.util.*;

public class Errors implements Interfaces.IPublic, Interfaces.ICollection {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private List<BasicModels.Error> content;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean setContent(List<BasicModels.Error> content) {
		if(content == null) {
			return false;
		}
		this.content = content;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public List<BasicModels.Error> getContent() {
		return this.content;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Errors() {
		initThis();
	}
	private void initThis() {
		if(content == null) {
			content = new ArrayList<BasicModels.Error>();
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
			BasicModels.Error e = new BasicModels.Error();
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
			BasicModels.Error im = new BasicModels.Error();
			im.copyValue(m.getContent().get(i));
			this.content.add(im);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public int size() {
		return content.size();
	}
	/**
	 * Sort By Type
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void sortIncrease() {
		@SuppressWarnings("rawtypes")
		Comparator c = new Comparator<BasicModels.Error>() {
			public int compare(BasicModels.Error e1, BasicModels.Error e2) {
				if(e1.getTime() > e2.getTime()) {
					return 1;
				} else {
					return -1;
				}
			}
		};
		
		Collections.sort(this.getContent(), c);
	}
	
	/**
	 * Sort By Time
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void sortDecrease() {
		@SuppressWarnings("rawtypes")
		Comparator c = new Comparator<BasicModels.Error>() {
			public int compare(BasicModels.Error e1, BasicModels.Error e2) {
				if(e1.getTime() > e2.getTime()) {
					return -1;
				} else {
					return 1;
				}
			}
		};
		
		Collections.sort(this.getContent(), c);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public int indexOf(BasicEnums.ErrorType type) {
		for(int i=0; i<content.size(); i++) {
			if(content.get(i).getType().equals(type)) {
				return i;
			}
		}
		return -1;
	}
	public BasicModels.Error search(BasicEnums.ErrorType type) {
		int index = this.indexOf(type);
		if(index < 0) {
			return null;
		}
		return content.get(index);
	}
	public BasicModels.Error fetch(BasicEnums.ErrorType type) {
		int index = this.indexOf(type);
		if(index < 0) {
			return null;
		}
		BasicModels.Error i = content.get(index);
		content.remove(index);
		return i;
	}
	public boolean add(BasicModels.Error item) {
		if(item == null) {
			return false;
		}
		content.add(item);
		return true;
	}
	public void delete(BasicEnums.ErrorType type) {
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
	public BasicModels.Error search(String tip) {
		int index = this.indexOf(tip);
		if(index < 0) {
			return null;
		}
		return content.get(index);
	}
	public BasicModels.Error fetch(String tip) {
		int index = this.indexOf(tip);
		if(index < 0) {
			return null;
		}
		BasicModels.Error i = content.get(index);
		content.remove(index);
		return i;
	}
	public void delete(String tip) {
		int index = this.indexOf(tip);
		if(index >= 0) {
			content.remove(index);
		}
	}
	
	public int[] indexOfAll(BasicEnums.ErrorType type) {
		ArrayList<Integer> idxs = new ArrayList<Integer>();
		for(int i=0; i<content.size(); i++) {
			if(content.get(i).getType().equals(type)) {
				idxs.add(i);
			}
		}
		return Tools.List2Array.toIntArray(idxs);
	}
	public ArrayList<BasicModels.Error> searchAll(BasicEnums.ErrorType type) {
		int[] idxs = this.indexOfAll(type);
		ArrayList<BasicModels.Error> res = new ArrayList<BasicModels.Error>();
		for(int i=0; i<idxs.length; i++) {
			res.add(content.get(idxs[i]));
		}
		return res;
	}
	public ArrayList<BasicModels.Error> fetchAll(BasicEnums.ErrorType type) {
		int[] idxs = this.indexOfAll(type);
		ArrayList<BasicModels.Error> res = new ArrayList<BasicModels.Error>();
		for(int i=idxs.length-1; i>=0; i--) {
			res.add(content.get(idxs[i]));
			content.remove(idxs[i]);
		}
		return res;
	}
	public void deleteAll(BasicEnums.ErrorType type) {
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
		return Tools.List2Array.toIntArray(idxs);
	}
	public ArrayList<BasicModels.Error> searchAllBefore(long time) {
		int[] idxs = this.indexOfAllBefore(time);
		ArrayList<BasicModels.Error> res = new ArrayList<BasicModels.Error>();
		for(int i=0; i<idxs.length; i++) {
			res.add(content.get(idxs[i]));
		}
		return res;
	}
	public ArrayList<BasicModels.Error> fetchAllBefore(long time) {
		int[] idxs = this.indexOfAllBefore(time);
		ArrayList<BasicModels.Error> res = new ArrayList<BasicModels.Error>();
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
		return Tools.List2Array.toIntArray(idxs);
	}
	public ArrayList<BasicModels.Error> searchAllBehind(long time) {
		int[] idxs = this.indexOfAllBehind(time);
		ArrayList<BasicModels.Error> res = new ArrayList<BasicModels.Error>();
		for(int i=0; i<idxs.length; i++) {
			res.add(content.get(idxs[i]));
		}
		return res;
	}
	public ArrayList<BasicModels.Error> fetchAllBehind(long time) {
		int[] idxs = this.indexOfAllBehind(time);
		ArrayList<BasicModels.Error> res = new ArrayList<BasicModels.Error>();
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
		
		for(BasicModels.Error e : this.content) {
			String url = Tools.Pathes.getFolder_LOG() + "\\" + e.getShortTime_Date() + ".log";
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
				BasicModels.Error ie = BasicEnums.ErrorType.WRITE_FILE_FAILED.getError(exception.toString());
				newErrors.add(ie);
				continue;
			}
		}
		
		this.content = newErrors.content;
		return true;
	}
	public boolean deleteAgoLogs(int permitLogAmount) {
		boolean ok = true;
		
		java.io.File logFolder = new java.io.File(Tools.Pathes.getFolder_LOG());
		java.io.File[] files = logFolder.listFiles();
		java.util.List<Long> time = new java.util.ArrayList<Long>();
		for(java.io.File f : files) {
			if(Tools.Url.getExtension(f.getName()).equals(".log")) {
				long t = Tools.Time.shortTimeDate2Ticks(Tools.Url.getNameWithoutExtension(f.getName()));
				if(t > 0) {
					time.add(t);
				}
			}
		}
		if(time.size() > permitLogAmount) {
			java.util.Collections.sort(time);
			for(int i=365; i<time.size(); i++) {
				String url = Tools.Pathes.getFolder_LOG() + "\\" + Tools.Time.ticks2ShortTime_Date(time.get(i)) + ".log";
				java.io.File f = new java.io.File(url);
				try {
					ok &= f.delete();
				} catch(Exception e) {
					BasicEnums.ErrorType.DELETE_LOG_FAILED.register(e.toString());
					ok = false;
				}
			}
		}
		return ok;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
