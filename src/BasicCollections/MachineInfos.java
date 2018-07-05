package BasicCollections;

import java.util.*;

public class MachineInfos implements Interfaces.IPublic, Interfaces.ICollection {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private List<BasicModels.MachineInfo> content;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean setContent(List<BasicModels.MachineInfo> content) {
		if(content == null) {
			return false;
		}
		this.content = content;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public List<BasicModels.MachineInfo> getContent() {
		return this.content;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public MachineInfos() {
		initThis();
	}
	private void initThis() {
		if(content == null) {
			content = new ArrayList<BasicModels.MachineInfo>();
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
			BasicModels.MachineInfo e = new BasicModels.MachineInfo();
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
			BasicModels.MachineInfo im = new BasicModels.MachineInfo();
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
			this.content.add((BasicModels.MachineInfo)item);
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
	public void sortIncrease() {
		@SuppressWarnings("rawtypes")
		Comparator c = new Comparator<BasicModels.MachineInfo>() {
			public int compare(BasicModels.MachineInfo e1, BasicModels.MachineInfo e2) {
				if(e1.getIndex() > e2.getIndex()) {
					return 1;
				} else {
					return -1;
				}
			}
		};
		
		Collections.sort(this.getContent(), c);
	}
	
	/**
	 * Sort By Index
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void sortDecrease() {
		@SuppressWarnings("rawtypes")
		Comparator c = new Comparator<BasicModels.MachineInfo>() {
			public int compare(BasicModels.MachineInfo e1, BasicModels.MachineInfo e2) {
				if(e1.getIndex() > e2.getIndex()) {
					return -1;
				} else {
					return 1;
				}
			}
		};
		
		Collections.sort(this.getContent(), c);
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
	public BasicModels.MachineInfo search(String ip) {
		int index = this.indexOf(ip);
		if(index < 0) {
			return null;
		}
		return content.get(index);
	}
	public BasicModels.MachineInfo fetch(String ip) {
		int index = this.indexOf(ip);
		if(index < 0) {
			return null;
		}
		BasicModels.MachineInfo i = content.get(index);
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
		return Tools.List2Array.toIntArray(idxs);
	}
	public ArrayList<BasicModels.MachineInfo> searchAll(String ip) {
		int[] idxs = this.indexOfAll(ip);
		ArrayList<BasicModels.MachineInfo> res = new ArrayList<BasicModels.MachineInfo>();
		for(int i=0; i<idxs.length; i++) {
			res.add(content.get(idxs[i]));
		}
		return res;
	}
	public ArrayList<BasicModels.MachineInfo> fetchAll(String ip) {
		int[] idxs = this.indexOfAll(ip);
		ArrayList<BasicModels.MachineInfo> res = new ArrayList<BasicModels.MachineInfo>();
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
	public BasicModels.MachineInfo search(long idx) {
		int index = this.indexOf(idx);
		if(index < 0) {
			return null;
		}
		return content.get(index);
	}
	public BasicModels.MachineInfo fetch(long idx) {
		int index = this.indexOf(idx);
		if(index < 0) {
			return null;
		}
		BasicModels.MachineInfo i = content.get(index);
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
