package com.FileManagerX.FileModels;

import java.io.*;
import java.util.*;

public class Text extends com.FileManagerX.BasicModels.File {

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
	public com.FileManagerX.BasicModels.Config toConfig() {
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config();
		c.setField(this.getClass().getSimpleName());
		c.addToBottom(this.content.size());
		for(String s : this.content) {
			c.addToBottom_Encode(s);
		}
		return c;
	}
	public String output() {
		return this.toConfig().output();
	}
	public com.FileManagerX.BasicModels.Config input(String in) {
		return this.input(new com.FileManagerX.BasicModels.Config(in));
	}
	public com.FileManagerX.BasicModels.Config input(com.FileManagerX.BasicModels.Config c) {
		if(c == null) { return null; }
		this.content.clear();
		
		if(!c.getIsOK()) { return c; }
		int amount = c.fetchFirstInt();
		if(!c.getIsOK()) { return c; }
		for(int i=0; i<amount; i++) {
			this.content.add(c.fetchFirstString_Decode());
			if(!c.getIsOK()) { return c; }
		}
		return c;
	}
	public void copyReference(Object o) {
		if(o == null) { return; }
		if(o instanceof Text) {
			super.copyReference(o);
			Text t = (Text)o;
			this.content = t.content;
			return;
		}
		if(o instanceof com.FileManagerX.BasicModels.File) {
			super.copyReference(o);
		}
	}
	public void copyValue(Object o) {
		if(o == null) { return; }
		if(o instanceof Text) {
			super.copyValue(o);
			Text t = (Text)o;
			this.content = t.content;
			return;
		}
		if(o instanceof com.FileManagerX.BasicModels.File) {
			super.copyValue(o);
		}
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
			com.FileManagerX.BasicEnums.ErrorType.COMMON_FILE_READ_FAILED.register(e.toString());
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
			com.FileManagerX.BasicEnums.ErrorType.COMMON_FILE_WRITE_FAILED.register(e.toString());
			return false;
		}
	}
	public com.FileManagerX.BasicCollections.Configs toConfigs() {
		com.FileManagerX.BasicCollections.Configs c = new com.FileManagerX.BasicCollections.Configs();
		for(String i : content) {
			com.FileManagerX.BasicModels.Config ic = new com.FileManagerX.BasicModels.Config(i);
			if(!ic.isEmpty()) {
				c.add(ic);
			}
		}
		return c;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

}
