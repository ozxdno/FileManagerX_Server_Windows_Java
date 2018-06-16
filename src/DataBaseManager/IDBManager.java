package DataBaseManager;

public interface IDBManager {
	
	public boolean isConnected();
	public boolean isQueryOK();
	public boolean isUpdataOK();
	
	public boolean initialize(Object infos);
	public boolean connect();
	public void disconnect();
	
	public BasicCollections.Folders QueryFolders(Object conditions);
	
	public boolean QueryConfigurations();
	public BasicModels.Folder QueryFolder(Object conditions);
	
	public boolean updataFolders(BasicCollections.Folders folders);
	
	public boolean updataConfigurations();
	public boolean updataFolder(BasicModels.Folder folder);
}
