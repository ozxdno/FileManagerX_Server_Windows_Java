package Tools;

public class Directories {

	public final static java.lang.String getExePath() {
		java.util.Properties properties = System.getProperties();
		return properties.getProperty("user.dir");
	}
	
	public final static java.lang.String getJarPath() {
		BasicModels.BaseFile f = new BasicModels.BaseFile();
		return f.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
	}
}
