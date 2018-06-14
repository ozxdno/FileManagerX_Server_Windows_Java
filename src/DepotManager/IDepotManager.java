package DepotManager;

public interface IDepotManager {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean receiveFile();
	public boolean sendFile();
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean renameFile(String sourName, String destName);
	public boolean renameFileWithoutExtension(String sourName, String destName);
	public boolean deleteFile(String url);
	public boolean moveFile(String sourUrl, String destUrl);
	public boolean copyFile(String sourUrl, String destUrl);
	
	public boolean renameDirectory(String sourName, String destName);
	public boolean deleteDirectory(String url);
	public boolean moveDirectory(String sourUrl, String destUrl);
	public boolean copyDirectory(String sourUrl, String destUrl);
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean renameFile(long index, String destName);
	public boolean renameFileWithoutExtension(long index, String destName);
	public boolean deleteFile(long index);
	public boolean moveFile(long index, String destUrl);
	public boolean copyFile(long index, String destUrl);
	
	public boolean renameDirectory(long index, String destName);
	public boolean deleteDirectory(long index);
	public boolean moveDirectory(long index, String destUrl);
	public boolean copyDirectory(long index, String destUrl);
}
