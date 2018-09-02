package com.FileManagerX.Tools.CFG;

public class Head {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public final static boolean save(com.FileManagerX.FileModels.CFG cfg) {
		String line = "";
		
		cfg.getContent().add(line);
		line = "/****************************************** This File Is VERY IMPORTANT **********************************************/";
		cfg.getContent().add(line);
		cfg.getContent().add(line);
		cfg.getContent().add(line);
		
		line = "";
		cfg.getContent().add(line);
		line = "[Attention]: Do Not Save Other Info in This File, It will reset At Running.";
		cfg.getContent().add(line);
		
		return true;
	}
	public final static boolean saveNew(com.FileManagerX.FileModels.CFG cfg) {
		return save(cfg);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
