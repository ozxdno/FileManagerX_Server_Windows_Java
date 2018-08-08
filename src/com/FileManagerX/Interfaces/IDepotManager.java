package com.FileManagerX.Interfaces;

public interface IDepotManager {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean setDepot(com.FileManagerX.BasicModels.DepotInfo depot);
	public boolean setUncheck(boolean uncheck);
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.BasicModels.DepotInfo getDepot();
	public boolean isUncheck();
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean openInSystem(String targetUrl);
	public boolean printScreen();
	
	public boolean renameFile(String sourUrl, String targetName);
	public boolean renameFileWithoutExtension(String sourUrl, String targetNameWithoutExtension);
	public boolean deleteFile(String targetFile);
	public boolean moveFile(String sourUrl, String destUrl);
	public boolean copyFile(String sourUrl, String destUrl);
	
	public boolean renameDirectory(String sourUrl, String targetName);
	public boolean deleteDirectory(String targetFolder);
	public boolean deleteContent(String folderUrl);
	public boolean moveDirectory(String sourUrl, String destUrl);
	public boolean copyDirectory(String sourUrl, String destUrl);
	
	public boolean createFolder(String targetUrl);
	public boolean createFile(String targetUrl);
	
	public boolean executeIOPackage(com.FileManagerX.Interfaces.IIOPackage iop);
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
