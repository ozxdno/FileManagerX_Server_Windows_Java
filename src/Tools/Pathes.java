package Tools;

public class Pathes {

	public final static java.lang.String getExePath() {
		java.util.Properties properties = System.getProperties();
		return properties.getProperty("user.dir");
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
	
	public final static java.lang.String getFile_CFG() {
		//return getFolder_CFG() + "\\FileManagerX_Server_TXT.cfg";
		//return getFolder_CFG() + "\\FileManagerX_Server_SQL.cfg";
		return getFolder_CFG() + "\\FileManagerX_Depot.cfg";
		//return getFolder_CFG() + "\\FileManagerX_Client.cfg";
	}
	
	
	public final static boolean createServerCFG() {
		Interfaces.IDepotManager dm = Factories.DepotManagerFactory.createDepotManager();
		dm.setUncheck(true);
		
		if(!dm.createFile(Tools.Pathes.getFile_CFG())) {
			return false;
		}
		
		
		
		return true;
	}
	public final static boolean createDepotCFG() {
		return false;
	}
	public final static boolean createClientCFG() {
		return false;
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
	
	public final static boolean createAll() {
		boolean ok = true;
		
		ok &= createFolder_CFG();
		ok &= createFolder_LOG();
		ok &= createFolder_DBS();
		ok &= createFolder_TMP();
		
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
