package BasicCollections;

import java.util.*;

public class Users implements Interfaces.IPublic, Interfaces.ICollection {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private List<BasicModels.User> content;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean setContent(List<BasicModels.User> content) {
		if(content == null) {
			return false;
		}
		this.content = content;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public List<BasicModels.User> getContent() {
		return this.content;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Users() {
		initThis();
	}
	private void initThis() {
		if(content == null) {
			content = new ArrayList<BasicModels.User>();
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
			BasicModels.User e = new BasicModels.User();
			out = e.input(in);
			if(out == null) { break; }
			this.content.add(e);
			in = out;
		}
		return in;
	}
	public void copyReference(Object o) {
		Users m = (Users)o;
		this.content = m.content;
	}
	public void copyValue(Object o) {
		Users m = (Users)o;
		initThis();
		for(int i=0; i<m.getContent().size(); i++) {
			BasicModels.User im = new BasicModels.User();
			im.copyValue(m.getContent().get(i));
			this.content.add(im);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public int size() {
		return content.size();
	}
	/**
	 * Sort By Index
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void sortIncrease() {
		@SuppressWarnings("rawtypes")
		Comparator c = new Comparator<BasicModels.User>() {
			public int compare(BasicModels.User e1, BasicModels.User e2) {
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
		Comparator c = new Comparator<BasicModels.User>() {
			public int compare(BasicModels.User e1, BasicModels.User e2) {
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
	
	public int indexOf(String loginName) {
		for(int i=0; i<content.size(); i++) {
			if(content.get(i).getLoginName().equals(loginName)) {
				return i;
			}
		}
		return -1;
	}
	public BasicModels.User search(String loginName) {
		int index = this.indexOf(loginName);
		if(index < 0) {
			return null;
		}
		return content.get(index);
	}
	public BasicModels.User fetch(String loginName) {
		int index = this.indexOf(loginName);
		if(index < 0) {
			return null;
		}
		BasicModels.User i = content.get(index);
		content.remove(index);
		return i;
	}
	public boolean add(BasicModels.User item) {
		if(item == null) {
			return false;
		}
		content.add(item);
		return true;
	}
	public void delete(String loginName) {
		int index = this.indexOf(loginName);
		if(index >= 0) {
			content.remove(index);
		}
	}
	
	public int indexOfNickName(String nickName) {
		for(int i=0; i<content.size(); i++) {
			if(content.get(i).getNickName().equals(nickName)) {
				return i;
			}
		}
		return -1;
	}
	public BasicModels.User searchNickName(String nickName) {
		int index = this.indexOfNickName(nickName);
		if(index < 0) {
			return null;
		}
		return content.get(index);
	}
	public BasicModels.User fetchNickName(String nickName) {
		int index = this.indexOfNickName(nickName);
		if(index < 0) {
			return null;
		}
		BasicModels.User i = content.get(index);
		content.remove(index);
		return i;
	}
	public void deleteNickName(String nickName) {
		int index = this.indexOfNickName(nickName);
		if(index >= 0) {
			content.remove(index);
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
	public BasicModels.User search(long idx) {
		int index = this.indexOf(idx);
		if(index < 0) {
			return null;
		}
		return content.get(index);
	}
	public BasicModels.User fetch(long idx) {
		int index = this.indexOf(idx);
		if(index < 0) {
			return null;
		}
		BasicModels.User i = content.get(index);
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
