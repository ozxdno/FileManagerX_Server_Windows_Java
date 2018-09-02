package com.FileManagerX.Tools;

public class Pathes {
	
	private static java.lang.String exePath = "";

	public final static boolean setExePath(java.lang.String exePath) {
		if(exePath == null || exePath.length() == 0) {
			com.FileManagerX.BasicEnums.ErrorType.COMMON_SET_WRONG_VALUE.register("exePath = " + (exePath == null ? "NULL" : ""));
			return false;
		}
		
		java.io.File f = new java.io.File(exePath);
		if(!f.exists()) {
			com.FileManagerX.BasicEnums.ErrorType.COMMON_SET_WRONG_VALUE.register("exePath Existed", "exePath = " + exePath);
			return false;
		}
		if(!f.isDirectory()) {
			com.FileManagerX.BasicEnums.ErrorType.COMMON_SET_WRONG_VALUE.register("exePath is NOT a Folder", "exePath = " + exePath);
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
		com.FileManagerX.BasicModels.File f = new com.FileManagerX.BasicModels.File();
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
	public final static java.lang.String getFolder_DBS(long depotIndex) {
		return getFolder_DBS() + "\\" + depotIndex;
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
	public final static java.lang.String getFolder_TMP_0_Match() {
		return getFolder_TMP_0() + "\\Match";
	}
	public final static java.lang.String getFolder_REC() {
		return getExePath() + "\\rec";
	}
	
	public final static java.lang.String getFile_CFG() {
		//return getFolder_CFG() + "\\FileManagerX_Server_TXT.cfg";
		//return getFolder_CFG() + "\\FileManagerX_Server_SQL.cfg";
		//return getFolder_CFG() + "\\FileManagerX_Depot.cfg";
		//return getFolder_CFG() + "\\FileManagerX_Client.cfg";
		return getFolder_CFG() + "\\FileManagerX.cfg";
	}
	
	public final static boolean createFolder_CFG() {
		com.FileManagerX.Interfaces.IDepotManager dm = com.FileManagerX.Factories.DepotFactory.createManager();
		dm.setUncheck(true);
		return dm.createFolder(getFolder_CFG());
	}
	public final static boolean createFolder_LOG() {
		com.FileManagerX.Interfaces.IDepotManager dm = com.FileManagerX.Factories.DepotFactory.createManager();
		dm.setUncheck(true);
		return dm.createFolder(getFolder_LOG());
	}
	public final static boolean createFolder_DBS() {
		com.FileManagerX.Interfaces.IDepotManager dm = com.FileManagerX.Factories.DepotFactory.createManager();
		dm.setUncheck(true);
		return dm.createFolder(getFolder_DBS());
	}
	public final static boolean createFolder_DBS(long depotIndex) {
		com.FileManagerX.Interfaces.IDepotManager dm = com.FileManagerX.Factories.DepotFactory.createManager();
		dm.setUncheck(true);
		return dm.createFolder(getFolder_DBS(depotIndex));
	}
	public final static boolean createFolder_TMP() {
		com.FileManagerX.Interfaces.IDepotManager dm = com.FileManagerX.Factories.DepotFactory.createManager();
		dm.setUncheck(true);
		return dm.createFolder(getFolder_TMP());
	}
	public final static boolean createFolder_TMP_0() {
		com.FileManagerX.Interfaces.IDepotManager dm = com.FileManagerX.Factories.DepotFactory.createManager();
		dm.setUncheck(true);
		return dm.createFolder(getFolder_TMP_0());
	}
	public final static boolean createFolder_TMP_0_Screen() {
		com.FileManagerX.Interfaces.IDepotManager dm = com.FileManagerX.Factories.DepotFactory.createManager();
		dm.setUncheck(true);
		return dm.createFolder(getFolder_TMP_0_Screen());
	}
	public final static boolean createFolder_TMP_0_Match() {
		com.FileManagerX.Interfaces.IDepotManager dm = com.FileManagerX.Factories.DepotFactory.createManager();
		dm.setUncheck(true);
		return dm.createFolder(getFolder_TMP_0_Match());
	}
	public final static boolean createFolder_REC() {
		com.FileManagerX.Interfaces.IDepotManager dm = com.FileManagerX.Factories.DepotFactory.createManager();
		dm.setUncheck(true);
		return dm.createFolder(getFolder_REC());
	}
	
	public final static boolean createAll() {
		boolean ok = true;
		
		ok &= createFolder_CFG();
		ok &= createFolder_LOG();
		ok &= createFolder_DBS();
		ok &= createFolder_TMP();
		ok &= createFolder_REC();
		
		ok &= createFolder_TMP_0();
		ok &= createFolder_TMP_0_Screen();
		ok &= createFolder_TMP_0_Match();
		
		return ok;
	}
	
}
