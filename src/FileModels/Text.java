package FileModels;

import java.io.File;
import java.util.*;

import BasicModels.MachineInfo;

public class Text extends BasicModels.BaseFile implements Interfaces.IPublic {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	private List<String> content;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	public boolean setContent(List<String> content) {
		if(content == null) {
			return false;
		}
		this.content = content;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	public List<String> getContent() {
		return this.content;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	public Text() {
		initThis();
	}
	public Text(String url){
		super(url);
		initThis();
	}
	public Text(File localFile) {
		super(localFile);
		initThis();
	}
	public Text(MachineInfo m, File f) {
		super(m,f);
		initThis();
	}
	private void initThis() {
		if(content == null) {
			content = new ArrayList<String>();
		}
		content.clear();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	public void clear() {
		super.clear();
		initThis();
	}
	public String toString() {
		return super.toString();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	public boolean load(boolean removeEmptyLine) {
		return true;
	}
	public boolean save() {
		return this.saveAs(this.getLocalUrl());
	}
	public boolean saveAs(String localUrl) {
		return true;
	}
	public BasicCollections.Configs toConfigs() {
		BasicCollections.Configs c = new BasicCollections.Configs();
		for(String i : content) {
			BasicModels.Config ic = new BasicModels.Config(i);
			if(!ic.isEmpty()) {
				c.add(ic);
			}
		}
		return c;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

}
