package BasicCollections;

import java.util.*;

public class Errors implements Interfaces.IPublic {

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
		String res = content.get(0).output();
		for(BasicModels.Error m : content) {
			res += "\n" + m.output();
		}
		return res;
	}
	public String input(String in) {
		initThis();
		String[] items = Tools.String.split(in, '\n');
		String res = "";
		for(int i=0; i<items.length; i++) {
			BasicModels.Error o = new BasicModels.Error();
			res = o.input(items[i]);
			if(res == null) {
				return null;
			}
			content.add(o);
		}
		return "";
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
}
