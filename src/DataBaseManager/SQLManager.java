package DataBaseManager;

import java.sql.*;

public class SQLManager implements Interfaces.IDBManager{

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
	
	public SQLManager() {
		initThis();
	}
	public SQLManager(BasicModels.DataBaseInfo dbInfo) {
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
	
	public boolean create() {
		if(!this.createTable_Folders()) {
			return false;
		}
		if(!this.createTable_Files()) {
			return false;
		}
		return true;
	}
	public boolean createTable(String tableName, String[] columns, String[] types) {
		if(tableName == null || columns == null || types == null) {
			return false;
		}
		if(tableName.length() == 0) {
			return false;
		}
		if(columns.length == 0 || types.length == 0 || columns.length != types.length) {
			return false;
		}
		
		boolean exist = false;
		try {
			ResultSet set = this.connection.getMetaData().getTables(null, null, tableName, null);
			exist = set.next();
		} catch(Exception e) {
			;
		}
		if(exist) {
			return true;
		}
		
		try {
			String exp = "CREATE TABLE " + tableName + " (";
			for(int i=0; i<columns.length; i++) {
				exp += columns[i] + " " + types[i] + " NOT NULL ,";
			}
			exp = exp.substring(0, exp.length()-2);
			exp += ");";
			return statement.executeUpdate(exp) == 0;
		} catch(Exception e) {
			return false;
		}
	}
	public boolean createTable_Configurations() {
		String tableName = "Configurations";
		String[] columns = new String[] {
				"Next_FileIndex",
				"Next_MachineIndex",
				"Next_UserIndex",
				"Next_DepotIndex",
				"Next_DataBaseIndex",
				"This_Machine",
				"This_User"
		};
		String[] types = new String[] {
				"BIGINT",
				"BIGINT",
				"BIGINT",
				"BIGINT",
				"BIGINT",
				"BIGINT",
				"BIGINT"
		};
		return this.createTable(tableName, columns, types);
	}
	public boolean createTable_Files() {
		String tableName = "Files";
		String[] columns = new String[] {
				"Index",
				"MachineIndex",
				"DepotIndex",
				"DataBaseIndex",
				"FatherIndex",
				"SonIndex",
				"URL",
				"Type",
				"State",
				"Modify",
				"Length",
				"Score",
				"Tags"
		};
		String[] types = new String[] {
				"BIGINT",
				"BIGINT",
				"BIGINT",
				"BIGINT",
				"BIGINT",
				"BIGINT",
				"VARCHAR(1024)",
				"INT",
				"INT",
				"BIGINT",
				"BIGINT",
				"BIGINT",
				"VARCHAR(1024)"
		};
		return this.createTable(tableName, columns, types);
	}
	public boolean createTable_Folders() {
		String tableName = "Files";
		String[] columns = new String[] {
				"Index",
				"MachineIndex",
				"DepotIndex",
				"DataBaseIndex",
				"FatherIndex",
				"SonIndex",
				"URL",
				"Type",
				"State",
				"Modify",
				"Length",
				"Score",
				"Tags"
		};
		String[] types = new String[] {
				"BIGINT",
				"BIGINT",
				"BIGINT",
				"BIGINT",
				"BIGINT",
				"BIGINT",
				"VARCHAR(1024)",
				"INT",
				"INT",
				"BIGINT",
				"BIGINT",
				"BIGINT",
				"VARCHAR(1024)"
		};
		return this.createTable(tableName, columns, types);
	}
	public boolean createTable_MachineInfo() {
		String tableName = "MachineInfo";
		String[] columns = new String[] {
				"Index",
				"MachineIndex",
				"DepotIndex",
				"DataBaseIndex",
				"URL",
				"Type",
				"State",
				"Modify",
				"Length",
				"Score",
				"Tags"
		};
		String[] types = new String[] {
				"BIGINT",
				"BIGINT",
				"BIGINT",
				"BIGINT",
				"VARCHAR(1024)",
				"INT",
				"INT",
				"BIGINT",
				"BIGINT",
				"BIGINT",
				"VARCHAR(1024)"
		};
		return this.createTable(tableName, columns, types);
	}
	public boolean createTable_DepotInfo() {
		String tableName = "DepotInfo";
		String[] columns = new String[] {
				"Index",
				"MachineIndex",
				"DataBaseIndex",
				"Name",
				"URL"
		};
		String[] types = new String[] {
				"BIGINT",
				"BIGINT",
				"BIGINT",
				"VARCHAR(1024)",
				"VARCHAR(1024)"
		};
		return this.createTable(tableName, columns, types);
	}
	public boolean createTable_DataBaseInfo() {
		String tableName = "DataBaseInfo";
		String[] columns = new String[] {
				"Index",
				"MachineIndex",
				"DepotIndex",
				"DBName",
				"URL",
				"Type",
				"Password"
		};
		String[] types = new String[] {
				"BIGINT",
				"BIGINT",
				"BIGINT",
				"VARCHAR(1024)",
				"VARCHAR(1024)",
				"INT",
				"VARCHAR(1024)"
		};
		return this.createTable(tableName, columns, types);
	}
	public boolean createTable_Supports() {
		String tableName = "Supports";
		String[] columns = new String[] {
				"Extension",
				"Type",
				"Show",
				"Hide",
				"IsSupport"
		};
		String[] types = new String[] {
				"VARCHAR(1024)",
				"INT",
				"VARCHAR(1024)",
				"VARCHAR(1024)",
				"INT"
		};
		return this.createTable(tableName, columns, types);
	}
	public boolean createTable_Users() {
		String tableName = "Users";
		String[] columns = new String[] {
				"Index",
				"LoginName",
				"NickName",
				"Password",
				"Email",
				"Phone",
				"State",
				"Priority",
				"Level",
				"Experience",
				"PhotoURL",
				"Coins",
				"Money"
		};
		String[] types = new String[] {
				"BIGINT",
				"VARCHAR(1024)",
				"VARCHAR(1024)",
				"VARCHAR(1024)",
				"VARCHAR(1024)",
				"VARCHAR(1024)",
				"INT",
				"INT",
				"INT",
				"BIGINT",
				"VARCHAR(1024)",
				"BIGINT",
				"DOUBLE"
		};
		return this.createTable(tableName, columns, types);
	}
	public boolean createTable_Invitations() {
		String tableName = "Invitations";
		String[] columns = new String[] {
				"Code",
				"Priority",
				"Level",
				"Experience",
				"Coins",
				"Money"
		};
		String[] types = new String[] {
				"VARCHAR(1024)",
				"INT",
				"INT",
				"BIGINT",
				"BIGINT",
				"DOUBLE"
		};
		return this.createTable(tableName, columns, types);
	}
	
	public boolean delete() {
		if(!this.deleteTable_Folders()) {
			return false;
		}
		if(!this.deleteTable_Files()) {
			return false;
		}
		return true;
	}
	public boolean deleteTable(String tableName) {
		String exp = "DROP TABLE " + tableName;
		try {
			return statement.execute(exp);
		} catch(Exception e) {
			return false;
		}
	}
	public boolean deleteTable_Configurations() {
		return this.deleteTable("Configurations");
	}
	public boolean deleteTable_Files() {
		return this.deleteTable("Files");
	}
	public boolean deleteTable_Folders() {
		return this.deleteTable("Folders");
	}
	public boolean deleteTable_MachineInfo() {
		return this.deleteTable("MachineInfo");
	}
	public boolean deleteTable_DepotInfo() {
		return this.deleteTable("DepotInfo");
	}
	public boolean deleteTable_DataBaseInfo() {
		return this.deleteTable("DataBaseInfo");
	}
	public boolean deleteTable_Supports() {
		return this.deleteTable("Supports");
	}
	public boolean deleteTable_Users() {
		return this.deleteTable("Users");
	}
	public boolean deleteTable_Invitations() {
		return this.deleteTable("Invitations");
	}
	
	public BasicCollections.Folders QueryFolders(Object conditions) {
		return null;
	}
	
	public boolean QueryConfigurations() {
		String exp = "SELECT * FROM Configurations";
		try {
			ResultSet set = statement.executeQuery(exp);
			if(set.next()) {
				Globals.Configurations.Next_FileIndex = set.getLong("Next_FileIndex");
				Globals.Configurations.Next_MachineIndex = set.getLong("Next_MachineIndex");
				Globals.Configurations.Next_UserIndex = set.getLong("Next_UserIndex");
				Globals.Configurations.Next_DepotIndex = set.getLong("Next_DepotIndex");
				Globals.Configurations.Next_DataBaseIndex = set.getLong("Next_DataBaseIndex");
			}
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	public BasicModels.Folder QueryFolder(Object conditions) {
		QueryConditions qc = (QueryConditions)conditions;
		String exp = "SELECT * FROM Folders WHERE " + this.queryConditionsToStatement(qc);
		try {
			ResultSet set = statement.executeQuery(exp);
			
			if(set.next()) {
				BasicModels.Folder f = new BasicModels.Folder();
				f.setIndex(set.getLong("index"));
				return f;
			}
			
			return null;
		}catch(Exception e) {
			return null;
		}
	}
	public BasicModels.Invitation QueryInvitation(Object conditions) {
		return null;
	}
	public BasicModels.User QueryUser(Object conditions) {
		return null;
	}
	
	public boolean updataFolders(BasicCollections.Folders folders) {
		return false;
	}
	
	public boolean updataConfigurations() {
		String exp = "UPDATA Configurations SET " + 
			"Next_FileIndex = " + String.valueOf(Globals.Configurations.Next_FileIndex) + ", " +
			"Next_MachineIndex = " + String.valueOf(Globals.Configurations.Next_MachineIndex) + ", " +
			"Next_UserIndex = " + String.valueOf(Globals.Configurations.Next_UserIndex) + ", " +
			"Next_DepotIndex = " + String.valueOf(Globals.Configurations.Next_DepotIndex) + ", " +
			"Next_DataBaseIndex = " + String.valueOf(Globals.Configurations.Next_DataBaseIndex) + ";";
		try {
			statement.executeUpdate(exp);
			return true;
		}  catch(Exception e) {
			return false;
		}
	}
	public boolean updataFolder(BasicModels.Folder folder) {
		
		return false;
	}
	public boolean updataUser(BasicModels.User user) {
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private String queryConditionsToStatement(QueryConditions conditions) {
		if(conditions == null || conditions.size() == 0) {
			return "";
		}
		String c = "";
		String r = "";
		String f = "";
		for(int i=0; i<conditions.size()-2; i++) {
			QueryCondition q = conditions.getContent().get(i);
			r = q.getRelationToNext().toString();
			
			if(q.getLike() != null) {
				f = "LIKE";
				c += "`" + q.getItemName() + "`" + " " + f + " " + q.getLike() + " " + r;
				continue;
			}
			if(q.getEqual() != null) {
				f = "=";
				c += "`" + q.getItemName() + "`" + " " + f + " " + q.getLike() + " " + r;
				continue;
			}
			if(q.getUBound() == null && q.getDBound() == null) {
				continue;
			}
			if(q.getUBound() == null) {
				f = q.getEqualDBound() ? ">=" : "=";
				c += "`" + q.getItemName() + "`" + " " + f + " " + q.getDBound() + " " + r;
				continue;
			}
			if(q.getDBound() == null) {
				f = q.getEqualUBound() ? "<=" : "=";
				c += "`" + q.getItemName() + "`" + " " + f + " " + q.getUBound() + " " + r;
				continue;
			}
			
			c += q.getDBound() + 
				(q.getEqualDBound() ? " <= " : " = ") + 
				"`" + q.getItemName() + "`" +
				" AND " +
				 "`" + q.getItemName() + "`" + " " +
				(q.getEqualUBound() ? " <= " : " = ") + 
				 q.getUBound() + " " +
				 r;
		}
		
		QueryCondition qEnd = conditions.getContent().get(conditions.size()-1);
		if(qEnd.getLike() != null) {
			return c + "`" + qEnd.getItemName() + "`" + " LIKE " + qEnd.getLike();
		}
		if(qEnd.getEqual() != null) {
			return c + "`" + qEnd.getItemName() + "`" + " = " + qEnd.getEqual();
		}
		if(qEnd.getUBound() == null && qEnd.getDBound() == null) {
			return c.substring(0, c.length()-qEnd.getRelationToNext().toString().length()-1);
		}
		if(qEnd.getUBound() == null) {
			f = qEnd.getEqualDBound() ? ">=" : "=";
			c += "`" + qEnd.getItemName() + "`" + " " + f + " " + qEnd.getDBound();
			return c;
		}
		if(qEnd.getDBound() == null) {
			f = qEnd.getEqualUBound() ? "<=" : "=";
			c += "`" + qEnd.getItemName() + "`" + " " + f + " " + qEnd.getUBound();
			return c;
		}
		
		c += qEnd.getDBound() + 
			(qEnd.getEqualDBound() ? " <= " : " = ") + 
			 "`" + qEnd.getItemName() + "`" +
			" AND " +
			 "`" + qEnd.getItemName() + "`" +
			(qEnd.getEqualUBound() ? " <= " : " = ") + 
			 qEnd.getUBound();
		return c;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
