package BasicCollections;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.*;

public class Records implements Interfaces.IPublic, Interfaces.ICollection {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private List<BasicModels.Record> content;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean setContent(List<BasicModels.Record> content) {
		if(content == null) {
			return false;
		}
		this.content = content;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public List<BasicModels.Record> getContent() {
		return this.content;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Records() {
		initThis();
	}
	private void initThis() {
		if(content == null) {
			content = new ArrayList<BasicModels.Record>();
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
			BasicModels.Record e = new BasicModels.Record();
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
			BasicModels.Record im = new BasicModels.Record();
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
			this.content.add((BasicModels.Record)item);
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
		Comparator c = new Comparator<BasicModels.Record>() {
			public int compare(BasicModels.Record e1, BasicModels.Record e2) {
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
			BasicEnums.ErrorType.OTHERS.register(BasicEnums.ErrorLevel.Error,"Error in Compare",e.toString());
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
		Comparator c = new Comparator<BasicModels.Record>() {
			public int compare(BasicModels.Record e1, BasicModels.Record e2) {
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
			BasicEnums.ErrorType.OTHERS.register(BasicEnums.ErrorLevel.Error,"Error in Compare",e.toString());
			return false;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean save() {
		boolean ok = true;
		
		for(BasicModels.Record e : this.content) {
			String url = Tools.Pathes.getFolder_REC() + "\\" + Tools.Time.ticks2ShortTime_Date(e.getTime()) + ".rec";
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
				BasicEnums.ErrorType.COMMON_FILE_WRITE_FAILED.register(exception.toString());
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
			BasicModels.Record e = this.content.get(0);
			this.content.remove(0);
			String url = Tools.Pathes.getFolder_REC() + "\\" + Tools.Time.ticks2ShortTime_Date(e.getTime()) + ".rec";
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
				BasicEnums.ErrorType.COMMON_FILE_WRITE_FAILED.register(exception.toString());
				continue;
			}
		}
		return ok;
	}
	public boolean deleteAgoRecords(int permitAmount) {
		boolean ok = true;
		
		java.io.File logFolder = new java.io.File(Tools.Pathes.getFolder_REC());
		java.io.File[] files = logFolder.listFiles();
		java.util.List<Long> time = new java.util.ArrayList<Long>();
		for(java.io.File f : files) {
			if(Tools.Url.getExtension(f.getName()).equals(".rec")) {
				long t = Tools.Time.shortTimeDate2Ticks(Tools.Url.getNameWithoutExtension(f.getName()));
				if(t > 0) {
					time.add(t);
				}
			}
		}
		if(time.size() > permitAmount) {
			java.util.Collections.sort(time);
			for(int i=permitAmount; i<time.size(); i++) {
				String url = Tools.Pathes.getFolder_REC() + "\\" + Tools.Time.ticks2ShortTime_Date(time.get(i)) + ".rec";
				java.io.File f = new java.io.File(url);
				try {
					ok &= f.delete();
				} catch(Exception e) {
					BasicEnums.ErrorType.COMMON_FILE_OPERATE_FAILED.register("Delete Rec File Failed", e.toString());
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
	public BasicModels.Record search(long time) {
		int index = this.indexOf(time);
		if(index < 0) {
			return null;
		}
		return content.get(index);
	}
	public BasicModels.Record fetch(long time) {
		int index = this.indexOf(time);
		if(index < 0) {
			return null;
		}
		BasicModels.Record i = content.get(index);
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
