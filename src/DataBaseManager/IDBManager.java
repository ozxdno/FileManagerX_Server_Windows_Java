package DataBaseManager;

public interface IDBManager {
	
	public boolean isConnected();
	public boolean isQueryOK();
	public boolean isUpdataOK();
	
	public boolean initialize(Object infos);
	public boolean connect();
	public boolean disconnect();
	
	public BasicCollections.Folders QueryFolders(Object conditions);
	public BasicCollections.BaseFiles QueryFiles(Object conditions);
	
	public BasicModels.Folder QueryFolder(Object conditions);
	public BasicModels.BaseFile QueryFile(Object conditions);
	public BasicModels.User QueryUser(Object conditions);
	public BasicModels.Invitation QueryInvitation(Object conditions);
	public BasicModels.Support QuerySupport(Object conditions);
	public BasicModels.MachineInfo QueryMachineInfo(Object conditions);
	public BasicModels.DepotInfo QueryDepotInfo(Object conditions);
	public BasicModels.DataBaseInfo QueryDataBaseInfo(Object conditions);
	
	public boolean updataFolders(BasicCollections.Folders folders);
	public boolean updataFiles(BasicCollections.BaseFiles files);
	
	public boolean updataFolder(BasicModels.Folder folder);
	public boolean updataFile(BasicModels.BaseFile file);
}
