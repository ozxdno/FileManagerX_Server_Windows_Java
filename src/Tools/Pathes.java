package Tools;

public class Pathes {
	
	private static java.lang.String exePath = "";

	public final static boolean setExePath(java.lang.String exePath) {
		if(exePath == null || exePath.length() == 0) {
			BasicEnums.ErrorType.COMMON_SET_WRONG_VALUE.register("exePath = " + (exePath == null ? "NULL" : ""));
			return false;
		}
		
		java.io.File f = new java.io.File(exePath);
		if(!f.exists()) {
			BasicEnums.ErrorType.COMMON_SET_WRONG_VALUE.register("exePath Existed", "exePath = " + exePath);
			return false;
		}
		if(!f.isDirectory()) {
			BasicEnums.ErrorType.COMMON_SET_WRONG_VALUE.register("exePath is NOT a Folder", "exePath = " + exePath);
			return false;
		}
		
		Pathes.exePath = exePath;
		return true;
	}
	
	public final static java.lang.String getExePath() {
		if(exePath == null || exePath.length() == 0) {
			java.util.Properties properties = System.getProperties();
			return properties.getProperty("user.dir");
		}
		else {
			return exePath;
		}
	}
	public final static java.lang.String getJarPath() {
		BasicModels.BaseFile f = new BasicModels.BaseFile();
		return f.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
	}
	
	public final static java.lang.String getFolder_CFG() {
		return getExePath() + "\\cfg";
	}
	public final static java.lang.String getFolder_LOG() {
		return getExePath() + "\\log";
	}
	public final static java.lang.String getFolder_DBS() {
		return getExePath() + "\\dbs";
	}
	public final static java.lang.String getFolder_TMP() {
		return getExePath() + "\\tmp";
	}
	public final static java.lang.String getFolder_TMP_0() {
		return getFolder_TMP() + "\\0";
	}
	public final static java.lang.String getFolder_TMP_0_Screen() {
		return getFolder_TMP_0() + "\\Screen";
	}
	
	public final static java.lang.String getFile_CFG() {
		//return getFolder_CFG() + "\\FileManagerX_Server_TXT.cfg";
		//return getFolder_CFG() + "\\FileManagerX_Server_SQL.cfg";
		//return getFolder_CFG() + "\\FileManagerX_Depot.cfg";
		//return getFolder_CFG() + "\\FileManagerX_Client.cfg";
		return getFolder_CFG() + "\\FileManagerX.cfg";
	}
	
	
	public final static boolean createServerCFG() {
		java.io.File cfg = new java.io.File(getFile_CFG());
		if(cfg.exists() && cfg.isFile()) {
			return true;
		}
		
		Interfaces.IDepotManager dm = Factories.DepotManagerFactory.createDepotManager();
		dm.setUncheck(true);
		if(!dm.createFile(Tools.Pathes.getFile_CFG())) {
			return false;
		}
		
		return Tools.CFGFile.saveCFGCore(BasicEnums.StartType.Server, true);
	}
	public final static boolean createDepotCFG() {
		java.io.File cfg = new java.io.File(getFile_CFG());
		if(cfg.exists() && cfg.isFile()) {
			return true;
		}
		
		Interfaces.IDepotManager dm = Factories.DepotManagerFactory.createDepotManager();
		dm.setUncheck(true);
		if(!dm.createFile(Tools.Pathes.getFile_CFG())) {
			return false;
		}
		
		return Tools.CFGFile.saveCFGCore(BasicEnums.StartType.Depot, true);
	}
	public final static boolean createClientCFG() {
		java.io.File cfg = new java.io.File(getFile_CFG());
		if(cfg.exists() && cfg.isFile()) {
			return true;
		}
		
		Interfaces.IDepotManager dm = Factories.DepotManagerFactory.createDepotManager();
		dm.setUncheck(true);
		if(!dm.createFile(Tools.Pathes.getFile_CFG())) {
			return false;
		}
		
		return Tools.CFGFile.saveCFGCore(BasicEnums.StartType.Client, true);
	}
	
	public final static boolean createFolder_CFG() {
		Interfaces.IDepotManager dm = Factories.DepotManagerFactory.createDepotManager();
		dm.setUncheck(true);
		return dm.createFolder(getFolder_CFG());
	}
	public final static boolean createFolder_LOG() {
		Interfaces.IDepotManager dm = Factories.DepotManagerFactory.createDepotManager();
		dm.setUncheck(true);
		return dm.createFolder(getFolder_LOG());
	}
	public final static boolean createFolder_DBS() {
		Interfaces.IDepotManager dm = Factories.DepotManagerFactory.createDepotManager();
		dm.setUncheck(true);
		return dm.createFolder(getFolder_DBS());
	}
	public final static boolean createFolder_TMP() {
		Interfaces.IDepotManager dm = Factories.DepotManagerFactory.createDepotManager();
		dm.setUncheck(true);
		return dm.createFolder(getFolder_TMP());
	}
	public final static boolean createFolder_TMP_0() {
		Interfaces.IDepotManager dm = Factories.DepotManagerFactory.createDepotManager();
		dm.setUncheck(true);
		return dm.createFolder(getFolder_TMP_0());
	}
	public final static boolean createFolder_TMP_0_Screen() {
		Interfaces.IDepotManager dm = Factories.DepotManagerFactory.createDepotManager();
		dm.setUncheck(true);
		return dm.createFolder(getFolder_TMP_0_Screen());
	}
	
	public final static boolean createAll() {
		boolean ok = true;
		
		ok &= createFolder_CFG();
		ok &= createFolder_LOG();
		ok &= createFolder_DBS();
		ok &= createFolder_TMP();
		
		ok &= createFolder_TMP_0();
		ok &= createFolder_TMP_0_Screen();
		
		return ok;
	}
	public final static boolean createAll(BasicEnums.StartType type) {
		boolean ok = createAll();
		
		if(BasicEnums.StartType.Server.equals(type)) {
			ok &= createServerCFG();
		}
		if(BasicEnums.StartType.Depot.equals(type)) {
			ok &= createDepotCFG();
		}
		if(BasicEnums.StartType.Client.equals(type)) {
			ok &= createClientCFG();
		}
		
		return ok;
	}
	
}
