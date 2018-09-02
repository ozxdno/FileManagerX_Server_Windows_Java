package com.FileManagerX.Tools.CFG;

public class Loader {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static boolean remote(com.FileManagerX.FileModels.CFG cfg) {
		return true;
	}
	public final static boolean local(com.FileManagerX.FileModels.CFG cfg) {
		loadContentFromLocal(cfg);
		boolean ok = true;
		ok &= Create.create(cfg);
		ok &= StartInfo.load(cfg);
		ok &= Index.load(cfg);
		ok &= BuildScanners.build();
		ok &= BuildConnection.build();
		ok &= ServerMachine.load(cfg);
		ok &= ServerUser.load(cfg);
		ok &= ThisMachine.load(cfg);
		ok &= ThisUser.load(cfg);
		ok &= DBManager.load(cfg);
		ok &= DBManagers.load(cfg);
		ok &= MyMachine.load(cfg);
		ok &= MyNet.load(cfg);
		ok &= Supports.load(cfg);
		ok &= ADepot.load(cfg);
		ok &= DDepot.load(cfg);
		ok &= Uninstall.uninstall();
		
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
