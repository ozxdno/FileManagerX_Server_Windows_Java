package com.FileManagerX.Tools.CFG;

public class Transports {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static boolean load(com.FileManagerX.FileModels.CFG cfg) {
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static boolean save(com.FileManagerX.FileModels.CFG cfg) {
		String line = "";
		cfg.getContent().add(line);
		line = "/*************************************************** CMD & REP *******************************************************/";
		cfg.getContent().add(line);
		line = "";
		cfg.getContent().add(line);
		
		line = "Example: CMD = CommandsPackageName";
		cfg.getContent().add(line);
		line = "Example: REP = RepliesPackageName";
		cfg.getContent().add(line);
		line = "";
		cfg.getContent().add(line);
		
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static boolean saveNew(com.FileManagerX.FileModels.CFG cfg) {
		return save(cfg);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}

