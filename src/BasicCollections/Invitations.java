package BasicCollections;

import java.util.*;

public class Invitations implements Interfaces.IPublic, Interfaces.ICollection {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private List<BasicModels.Invitation> content;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean setContent(List<BasicModels.Invitation> content) {
		if(content == null) {
			return false;
		}
		this.content = content;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public List<BasicModels.Invitation> getContent() {
		return this.content;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Invitations() {
		initThis();
	}
	private void initThis() {
		if(content == null) {
			content = new ArrayList<BasicModels.Invitation>();
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
			if(in == null) { break; }
			if(Tools.String.clearLRSpace(Tools.String.getValue(in)).length() == 0) { break; }
			
			BasicModels.Invitation e = new BasicModels.Invitation();
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
			BasicModels.Invitation im = new BasicModels.Invitation();
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
			this.content.add((BasicModels.Invitation)item);
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
		Comparator c = new Comparator<BasicModels.Invitation>() {
			public int compare(BasicModels.Invitation e1, BasicModels.Invitation e2) {
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
			BasicEnums.ErrorType.OTHERS.register(BasicEnums.ErrorLevel.Error,"Error in Compare",e.toString());
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
		Comparator c = new Comparator<BasicModels.Invitation>() {
			public int compare(BasicModels.Invitation e1, BasicModels.Invitation e2) {
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
			BasicEnums.ErrorType.OTHERS.register(BasicEnums.ErrorLevel.Error,"Error in Compare",e.toString());
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
	public BasicModels.Invitation search(String code) {
		int index = this.indexOf(code);
		if(index < 0) {
			return null;
		}
		return content.get(index);
	}
	public BasicModels.Invitation fetch(String code) {
		int index = this.indexOf(code);
		if(index < 0) {
			return null;
		}
		BasicModels.Invitation i = content.get(index);
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
		return Tools.List2Array.toIntArray(idxs);
	}
	public ArrayList<BasicModels.Invitation> searchAll(String code) {
		int[] idxs = this.indexOfAll(code);
		ArrayList<BasicModels.Invitation> res = new ArrayList<BasicModels.Invitation>();
		for(int i=0; i<idxs.length; i++) {
			res.add(content.get(idxs[i]));
		}
		return res;
	}
	public ArrayList<BasicModels.Invitation> fetchAll(String code) {
		int[] idxs = this.indexOfAll(code);
		ArrayList<BasicModels.Invitation> res = new ArrayList<BasicModels.Invitation>();
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
