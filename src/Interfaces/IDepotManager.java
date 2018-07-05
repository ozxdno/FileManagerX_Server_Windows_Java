package Interfaces;

public interface IDepotManager {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean setDepot(BasicModels.DepotInfo depot);
	public BasicModels.DepotInfo getDepot();
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean renameFile(String sourUrl, String targetName);
	public boolean renameFileWithoutExtension(String sourUrl, String targetNameWithoutExtension);
	public boolean deleteFile(String targetFile);
	public boolean moveFile(String sourUrl, String destUrl);
	public boolean copyFile(String sourUrl, String destUrl);
	
	public boolean renameDirectory(String sourUrl, String targetName);
	public boolean deleteDirectory(String targetFolder);
	public boolean moveDirectory(String sourUrl, String destUrl);
	public boolean copyDirectory(String sourUrl, String destUrl);
	
	public boolean createFolder(String targetUrl);
	public boolean createFile(String targetUrl);
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
