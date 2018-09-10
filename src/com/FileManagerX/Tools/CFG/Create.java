package com.FileManagerX.Tools.CFG;

public class Create {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static boolean create(com.FileManagerX.FileModels.CFG cfg) {
		java.io.File cfgFile = new java.io.File(cfg.getUrl());
		if(cfgFile.exists() && cfgFile.isFile()) {
			return true;
		}
		
		boolean ok = true;
		ok &= Head.saveNew(cfg);
		ok &= StartInfo.saveNew(cfg);
		ok &= Register.saveNew(cfg);
		ok &= Index.saveNew(cfg);
		ok &= DBManager.saveNew(cfg);
		ok &= DBManagers.saveNew(cfg);
		ok &= MyNet.saveNew(cfg);
		ok &= Supports.saveNew(cfg);
		ok &= ADepot.saveNew(cfg);
		ok &= DDepot.saveNew(cfg);
		ok &= Tail.saveNew(cfg);
		return ok;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
