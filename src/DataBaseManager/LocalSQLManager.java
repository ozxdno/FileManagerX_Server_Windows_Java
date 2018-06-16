package DataBaseManager;

import java.sql.*;

public class LocalSQLManager implements IDBManager{

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
	
	public LocalSQLManager() {
		initThis();
	}
	public LocalSQLManager(BasicModels.DataBaseInfo dbInfo) {
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
	
	private BasicModels.Folder readFolderFromSet(ResultSet set) {
		return null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
