package com.FileManagerX.Tools.CFG;

public class Saver {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static boolean local(com.FileManagerX.FileModels.CFG cfg) {
		if(cfg == null) { return false; }
		cfg.getContent().clear();
		
		boolean ok = true;
		ok &= Head.save(cfg);
		ok &= StartInfo.save(cfg);
		ok &= Register.save(cfg);
		ok &= Index.save(cfg);
		ok &= DBManager.save(cfg);
		ok &= DBManagers.save(cfg);
		ok &= MyNet.save(cfg);
		ok &= Transports.save(cfg);
		ok &= Supports.save(cfg);
		ok &= ADepot.save(cfg);
		ok &= DDepot.save(cfg);
		ok &= Tail.save(cfg);
		ok &= saveToLocal(cfg);
		return ok;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static boolean remote(com.FileManagerX.FileModels.CFG cfg) {
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private final static boolean saveToLocal(com.FileManagerX.FileModels.CFG cfg) {
		try {
			java.io.BufferedWriter bw = new java.io.BufferedWriter(
					new java.io.FileWriter(new java.io.File(cfg.getUrl()), false)
				);
			for(String i : cfg.getContent()) {
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
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
