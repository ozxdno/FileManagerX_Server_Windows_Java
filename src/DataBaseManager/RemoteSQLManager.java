package DataBaseManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class RemoteSQLManager implements IDBManager {


	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private boolean isConnected;
	private boolean isQueryOK;
	private boolean isUpdataOK;
	
	private BasicModels.DataBaseInfo dbInfo;
	
	private Connection connection;
	private Statement statement;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setDBInfo(BasicModels.DataBaseInfo dbInfo) {
		if(dbInfo == null) {
			return false;
		}
		this.dbInfo = dbInfo;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public BasicModels.DataBaseInfo getDBInfo() {
		return this.dbInfo;
	}
	
	public boolean isConnected() {
		return this.isConnected;
	}
	public boolean isQueryOK() {
		return this.isQueryOK;
	}
	public boolean isUpdataOK() {
		return this.isUpdataOK;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public RemoteSQLManager() {
		initThis();
	}
	public RemoteSQLManager(BasicModels.DataBaseInfo dbInfo) {
		initThis();
		this.setDBInfo(dbInfo);
	}
	private void initThis() {
		this.isConnected = false;
		this.isQueryOK = false;
		this.isUpdataOK = false;
		this.dbInfo = new  BasicModels.DataBaseInfo();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean initialize(Object infos) {
		return this.setDBInfo((BasicModels.DataBaseInfo)infos);
	}
	public boolean connect() {
		String url = "jdbc:mysql://" + 
				this.dbInfo.getMachineInfo().getIp()+ ":" + 
				String.valueOf(this.dbInfo.getMachineInfo().getPort()) + "/" + 
				this.dbInfo.getDBName();
		this.isConnected = true;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, this.dbInfo.getLoginName(), this.dbInfo.getPassword());
		}catch(Exception e) {
			this.isConnected = false;
			return false;
		}
		try {
			statement = connection.createStatement();
		}catch(Exception e) {
			this.isConnected = false;
			return false;
		}
		return true;
	}
	public void disconnect() {
		if(statement != null) {
			try {
				statement.close();
			}catch(Exception e) {
				return;
			}
		}
		
		if(connection != null) {
			try {
				connection.close();
			}catch(Exception e) {
				return;
			}
		}
		this.isConnected = false;
	}
	
	public BasicCollections.Folders QueryFolders(Object conditions) {
		return null;
	}
	
	public boolean QueryConfigurations() {
		return true;
	}
	public BasicModels.Folder QueryFolder(Object conditions) {
		QueryConditions qc = (QueryConditions)conditions;
		return null;
	}
	
	public boolean updataFolders(BasicCollections.Folders folders) {
		return false;
	}
	
	public boolean updataConfigurations() {
		return true;
	}
	public boolean updataFolder(BasicModels.Folder folder) {
		return false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
