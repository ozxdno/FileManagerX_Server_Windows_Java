package BasicCollections;

import java.util.*;

public class Invitations implements Interfaces.IPublic {

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
		String res = content.get(0).output();
		for(int i=1; i<content.size(); i++) {
			res += "\n" + content.get(i).output();
		}
		return res;
	}
	public String input(String in) {
		initThis();
		String[] items = Tools.String.split(in, '\n');
		String res = "";
		for(int i=0; i<items.length; i++) {
			BasicModels.Invitation o = new BasicModels.Invitation();
			res = o.input(items[i]);
			if(res == null) {
				return null;
			}
			content.add(o);
		}
		return "";
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
	public boolean add(BasicModels.Invitation item) {
		if(item == null) {
			return false;
		}
		content.add(item);
		return true;
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
