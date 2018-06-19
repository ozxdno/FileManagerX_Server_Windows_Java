package BasicCollections;

import java.util.*;

public class Configs implements Interfaces.IPublic {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private List<BasicModels.Config> configs;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public List<BasicModels.Config> getContent() {
		return configs;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean setContent(List<BasicModels.Config> configs) {
		if(configs == null) {
			return false;
		}
		this.configs = configs;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Configs() {
		initThis();
	}
	private void initThis() {
		if(configs == null) {
			configs = new ArrayList<BasicModels.Config>();
		}
		configs.clear();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public String toString() {
		
		if(configs == null) {
			return "NULL";
		}
		if(configs.size() == 0) {
			return "Empty";
		}
		String res = configs.get(0).getField();
		if(res.length() == 0) {
			res = "[No Name]";
		}
		String f = "";
		for(int i=1; i<configs.size(); i++) {
			f = configs.get(i).getField();
			if(f.length() == 0) {
				res += ", [No Name]";
			} else {
				res += ", " + configs.get(i).getField();
			}
		}
		return res;
	}
	public String output() {
		if(configs == null || configs.size() == 0) {
			return "";
		}
		String res = configs.get(0).output();
		for(int i=1; i<configs.size(); i++) {
			res += "\n" + configs.get(i).output();
		}
		return res;
	}
	public String input(String in) {
		initThis();
		String[] items = Tools.String.split(in, '\n');
		String res = "";
		for(int i=0; i<items.length; i++) {
			BasicModels.Config c = new BasicModels.Config();
			res = c.input(items[i]);
			if(res == null) {
				return res;
			}
			configs.add(c);
		}
		return res;
	}
	public void copyReference(Object o) {
		Configs c = (Configs)o;
		this.configs = c.configs;
	}
	public void copyValue(Object o) {
		Configs c = (Configs)o;
		initThis();
		for(int i=0; i<c.getContent().size(); i++) {
			BasicModels.Config ic = new BasicModels.Config();
			ic.copyValue(c.getContent().get(i));
			this.configs.add(ic);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void clear() {
		initThis();
	}
	
	public int size() {
		return configs.size();
	}
	public int indexOf(String field) {
		for(int i=0; i<configs.size(); i++) {
			if(configs.get(i).getField().equals(field)) {
				return i;
			}
		}
		return -1;
	}
	public BasicModels.Config search(String field) {
		int index = this.indexOf(field);
		if(index < 0) {
			return null;
		}
		return configs.get(index);
	}
	public BasicModels.Config fetch(String field) {
		int index = this.indexOf(field);
		if(index < 0) {
			return null;
		}
		BasicModels.Config c = configs.get(index);
		configs.remove(index);
		return c;
	}
	public boolean add(BasicModels.Config c) {
		if(c == null) {
			return false;
		}
		configs.add(c);
		return true;
	}
	public void delete(String field) {
		int index = this.indexOf(field);
		if(index >= 0) {
			configs.remove(index);
		}
	}
	
	public int[] indexOfAll(String field) {
		ArrayList<Integer> idxs = new ArrayList<Integer>();
		for(int i=0; i<configs.size(); i++) {
			if(configs.get(i).getField().equals(field)) {
				idxs.add(i);
			}
		}
		return Tools.List2Array.toIntArray(idxs);
	}
	public ArrayList<BasicModels.Config> searchAll(String field) {
		int[] idxs = this.indexOfAll(field);
		ArrayList<BasicModels.Config> res = new ArrayList<BasicModels.Config>();
		for(int i=0; i<idxs.length; i++) {
			res.add(configs.get(idxs[i]));
		}
		return res;
	}
	public ArrayList<BasicModels.Config> fetchAll(String field) {
		int[] idxs = this.indexOfAll(field);
		ArrayList<BasicModels.Config> res = new ArrayList<BasicModels.Config>();
		for(int i=idxs.length-1; i>=0; i--) {
			res.add(configs.get(idxs[i]));
			configs.remove(idxs[i]);
		}
		return res;
	}
	public void deleteAll(String field) {
		int[] idxs = this.indexOfAll(field);
		for(int i=idxs.length-1; i>=0; i--) {
			configs.remove(idxs[i]);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
