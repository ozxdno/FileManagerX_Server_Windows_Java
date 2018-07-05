package BasicCollections;

import java.util.*;

public class Configs implements Interfaces.IPublic, Interfaces.ICollection {
	
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
	
	public void clear() {
		initThis();
	}
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
		String res = this.getClass().getSimpleName() + " = " + Tools.String.getValue(this.getContent().get(0).output());
		for(int i=1; i<this.configs.size(); i++) {
			res += "|" + Tools.String.getValue(this.getContent().get(i).output());
		}
		return res;
	}
	public String input(String in) {
		initThis();
		String out = "";
		while(true) {
			BasicModels.Config e = new BasicModels.Config();
			out = e.input(in);
			if(out == null) { break; }
			this.configs.add(e);
			in = out;
		}
		return in;
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
	
	public int size() {
		return configs.size();
	}
	public boolean add(Object item) {
		if(item == null) {
			return false;
		}
		try {
			this.configs.add((BasicModels.Config)item);
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	/**
	 * Sort By Field
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void sortIncrease() {
		@SuppressWarnings("rawtypes")
		Comparator c = new Comparator<BasicModels.Config>() {
			public int compare(BasicModels.Config c1, BasicModels.Config c2) {
				if(c1 == null ) {
					return -1;
				}
				if(c2 == null) {
					return 1;
				}
				if(c1.getField().compareTo(c2.getField()) > 0) {
					return 1;
				} else {
					return -1;
				}
			}
		};
		
		Collections.sort(this.getContent(), c);
	}
	/**
	 * Sort By Field
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void sortDecrease() {
		@SuppressWarnings("rawtypes")
		Comparator c = new Comparator<BasicModels.Config>() {
			public int compare(BasicModels.Config c1, BasicModels.Config c2) {
				if(c1 == null ) {
					return 1;
				}
				if(c2 == null) {
					return -1;
				}
				if(c1.getField().compareTo(c2.getField()) > 0) {
					return -1;
				} else {
					return 1;
				}
			}
		};
		
		Collections.sort(this.getContent(), c);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	
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
