package com.FileManagerX.FileModels;

public class CFG extends com.FileManagerX.BasicModels.File {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	private java.util.List<String> content;
	private com.FileManagerX.BasicCollections.Configs configs;
	private boolean error;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	public boolean setIndex() {
		this.setIndex(com.FileManagerX.Globals.Configurations.Next_CFGIndex());
		return true;
	}
	
	public boolean setContent(java.util.List<String> content) {
		if(content == null) {
			return false;
		}
		this.content = content;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	public java.util.List<String> getContent() {
		return this.content;
	}
	public com.FileManagerX.BasicCollections.Configs getConfigs() {
		if(this.configs == null) {
			configs = new com.FileManagerX.BasicCollections.Configs();
			for(String i : content) {
				com.FileManagerX.BasicModels.Config ic = new com.FileManagerX.BasicModels.Config(i);
				if(!ic.isEmpty()) {
					configs.add(ic);
				}
			}
		}
		return configs;
	}
	public boolean isErrorOccurs() {
		return this.error;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	public CFG() {
		initThis();
	}
	private void initThis() {
		content = new java.util.ArrayList<>();
		configs = null;
		error = false;
		
		this.setType(com.FileManagerX.BasicEnums.FileType.CFG);
		this.setState(com.FileManagerX.BasicEnums.FileState.Preparing);
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
		c.addToBottom(super.toConfig());
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
		c = super.input(c);
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
		if(o instanceof CFG) {
			super.copyReference(o);
			CFG cfg = (CFG)o;
			this.content = cfg.content;
			this.error = false;
			return;
		}
		if(o instanceof com.FileManagerX.BasicModels.File) {
			super.copyReference(o);
			return;
		}
	}
	public void copyValue(Object o) {
		if(o == null) { return; }
		if(o instanceof CFG) {
			super.copyValue(o);
			CFG cfg = (CFG)o;
			this.content.clear();
			for(String s : cfg.content) { this.content.add(new String(s)); }
			this.error = false;
			return;
		}
		if(o instanceof com.FileManagerX.BasicModels.File) {
			super.copyValue(o);
			return;
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	public boolean loadFromRemote() {
		return com.FileManagerX.Tools.CFG.Loader.remote(this);
	}
	public boolean saveToRemote() {
		return com.FileManagerX.Tools.CFG.Saver.remote(this);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	public boolean loadFromLocal() {
		return com.FileManagerX.Tools.CFG.Loader.local(this);
	}
	public boolean saveToLocal() {
		return com.FileManagerX.Tools.CFG.Saver.local(this);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
