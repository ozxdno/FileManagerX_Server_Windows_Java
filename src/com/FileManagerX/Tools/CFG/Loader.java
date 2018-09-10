package com.FileManagerX.Tools.CFG;

public class Loader {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static boolean remote(com.FileManagerX.FileModels.CFG cfg) {
		return true;
	}
	public final static boolean local(com.FileManagerX.FileModels.CFG cfg) {
		loadContentFromLocal(cfg);
		boolean ok = true;
		ok = ok && Create.create(cfg);
		ok = ok && StartInfo.load(cfg);
		ok = ok && Index.load(cfg);
		ok = ok && BuildScanners.build();
		ok = ok && BuildConnection.build();
		ok = ok && Register.load(cfg);
		ok = ok && Login.login();
		ok = ok && ServerMachine.load(cfg);
		ok = ok && ServerUser.load(cfg);
		ok = ok && ThisMachine.load(cfg);
		ok = ok && ThisUser.load(cfg);
		ok = ok && DBManager.load(cfg);
		ok = ok && DBManagers.load(cfg);
		ok = ok && MyMachine.load(cfg);
		ok = ok && MyNet.load(cfg);
		ok = ok && Supports.load(cfg);
		ok = ok && ADepot.load(cfg);
		ok = ok && DDepot.load(cfg);
		ok = ok && Uninstall.uninstall();
		
		cfg.getContent().clear();
		cfg.getConfigs().clear();
		return ok;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private final static boolean loadContentFromLocal(com.FileManagerX.FileModels.CFG cfg) {
		try {
			java.io.BufferedReader br = new java.io.BufferedReader(
					new java.io.FileReader(new java.io.File(cfg.getUrl()))
				);
			cfg.getContent().clear();
			String line = br.readLine();
			while(line != null) {
				if(line.length() == 0) {
					line = br.readLine();
					continue;
				}
				cfg.getContent().add(line);
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
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
