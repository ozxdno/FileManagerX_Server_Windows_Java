package FileModels;

import java.io.*;
import java.util.*;

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
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(super.getUrl())));
			String line = br.readLine();
			while(line != null) {
				if(line.length() == 0 && removeEmptyLine) {
					line = br.readLine();
					continue;
				}
				this.content.add(line);
				line = br.readLine();
			}
			try {
				br.close();
			} catch(Exception e) {
				;
			}
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	public boolean save() {
		return this.saveAs(this.getUrl());
	}
	public boolean saveAs(String localUrl) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(localUrl), false));
			for(String i : this.content) {
				bw.write(i);
				bw.newLine();
				bw.flush();
			}
			bw.close();
			return true;
		} catch(Exception e) {
			return false;
		}
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
