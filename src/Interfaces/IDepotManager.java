package Interfaces;

public interface IDepotManager {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean setFile(BasicModels.BaseFile file);
	public BasicModels.BaseFile getFile();
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean renameFile(String name);
	public boolean renameFileWithoutExtension(String nameWithoutExtension);
	public boolean deleteFile();
	public boolean moveFile(String destUrl);
	public boolean copyFile(String destUrl);
	
	public boolean renameDirectory(String name);
	public boolean deleteDirectory();
	public boolean moveDirectory(String destUrl);
	public boolean copyDirectory(String destUrl);
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
