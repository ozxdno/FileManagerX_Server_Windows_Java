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
	
	
	
}
