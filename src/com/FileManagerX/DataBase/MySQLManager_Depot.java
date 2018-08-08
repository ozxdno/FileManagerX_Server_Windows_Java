package com.FileManagerX.DataBase;

import java.sql.*;

public class MySQLManager_Depot implements com.FileManagerX.Interfaces.IDBManager {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private com.FileManagerX.BasicModels.DataBaseInfo database;
	private com.FileManagerX.DataBase.Unit unit;
	private boolean running;
	private String name;
	
	private Connection connection;
	private Statement statement;
	
	private String[] items = new String[] {
			"Index",
			"Name",
			"MachineIndex",
			"DataBaseIndex",
			"State",
			"Url"
	};
	private String[] types = new String[] {
			"BIGINT UNIQUE",
			"VARCHAR(100) UNIQUE",
			"BIGINT",
			"BIGINT",
			"VARCHAR(100)",
			"VARCHAR(1024)"
	};
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setDBInfo(com.FileManagerX.BasicModels.DataBaseInfo database) {
		if(database == null) {
			return false;
		}
		this.database = database;
		return true;
	}
	public boolean setUnit(com.FileManagerX.DataBase.Unit unit) {
		if(unit == null) {
			return false;
		}
		this.unit = unit;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.BasicModels.DataBaseInfo getDBInfo() {
		return this.database;
	}
	public com.FileManagerX.DataBase.Unit getUnit() {
		return this.unit;
	}
	public com.FileManagerX.Interfaces.IDBManager getUnitMananger() {
		return this;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public MySQLManager_Depot() {
		initThis();
	}
	private void initThis() {
		this.database = null;
		this.unit = Unit.BaseFile;
		this.running = false;
		this.name = "Depots";
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean isConnected() {
		return this.running;
	}
	public boolean connect() {
		this.running = false;
		
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config(this.database.getUrl().replace('\\', '|'));
		String ip_port = c.fetchFirstString();
		String loginName = c.fetchFirstString();
		String password = c.fetchFirstString();
		String dbName = c.fetchFirstString();
		
		String url = "jdbc:mysql://" + 
				ip_port + "/" + 
				dbName;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, loginName, password);
		}catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.DB_CONNECT_FAILED.register(e.toString());
			return false;
		}
		try {
			statement = connection.createStatement();
		}catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.DB_CONNECT_FAILED.register(
					
					e.toString()
					);
			return false;
		}
		
		return this.running = true;
	}
	public boolean disconnect() {
		this.running = false;
		if(statement != null) {
			try {
				statement.close();
			}catch(Exception e) {
				com.FileManagerX.BasicEnums.ErrorType.DB_DISCONNECT_FAILED.register(e.toString());
				return false;
			}
		}
		
		if(connection != null) {
			try {
				connection.close();
			}catch(Exception e) {
				com.FileManagerX.BasicEnums.ErrorType.DB_DISCONNECT_FAILED.register(e.toString());
				return false;
			}
		}
		
		return true;
	}
	public boolean load() {
		return true;
	}
	public boolean save() {
		return true;
	}
	public boolean create() {
		if(this.exists()) { return true; }
		
		try {
			String exp = "CREATE TABLE " + this.name + " (";
			for(int i=0; i<items.length; i++) {
				exp += "`" + items[i] + "` " + types[i] + " NOT NULL, ";
			}
			exp = exp.substring(0, exp.length()-2);
			exp += ");";
			statement.executeUpdate(exp);
			return true;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.DB_OPERATE_FAILED.register
				("Create Table " + name + " Failed", e.toString());
			return false;
		}
	}
	public boolean delete() {
		String exp = "DROP TABLE " + name;
		try {
			statement.execute(exp);
			return true;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.DB_OPERATE_FAILED.register
				("Delete Table " + name + " Failed", e.toString());
			return false;
		}
	}
	public boolean exists() {
		try {
			ResultSet set = this.connection.getMetaData().getTables(null, null, this.name, null);
			return set.next();
		} catch(Exception e) {
			return false;
		}
	}
	public boolean clear() {
		return this.delete();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.BasicCollections.DepotInfos querys(Object conditions) {
		if(conditions == null) {
			return null;
		}
		String con = "";
		if(conditions instanceof QueryConditions) {
			con = MySQLManager_SHELL.queryConditionsToStatement((QueryConditions)conditions);
		}
		else if(conditions instanceof QueryCondition) {
			con = MySQLManager_SHELL.queryConditionToStatement((QueryCondition)conditions);
		}
		else if(conditions instanceof String) {
			QueryConditions qcs = new QueryConditions();
			try {
				qcs.stringToThis((String)conditions);
			}catch(Exception e) {
				com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
				return null;
			}
			con = MySQLManager_SHELL.queryConditionsToStatement(qcs);
		}
		else {
			return null;
		}
		String exp = "SELECT * FROM " + this.name + " " + con + ";";
		try {
			ResultSet set = statement.executeQuery(exp);
			com.FileManagerX.BasicCollections.DepotInfos ds = new com.FileManagerX.BasicCollections.DepotInfos();
			int cnt = 0;
			
			while(set.next()) {
				com.FileManagerX.BasicModels.DepotInfo d = new com.FileManagerX.BasicModels.DepotInfo();
				d.setIndex(set.getLong("Index"));
				d.setName(set.getString("Name"));
				d.getMachineInfo().setIndex(set.getLong("MachineIndex"));
				d.setDBIndex(set.getLong("DataBaseIndex"));
				d.setState(com.FileManagerX.BasicEnums.DepotState.valueOf(set.getString("State")));
				d.setUrl(set.getString("Url"));
				ds.add(d);
				
				if(++cnt > com.FileManagerX.Globals.Configurations.DataBaseQueryLimit) { break; }
			}
			
			return ds;
		}catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.DB_OPERATE_FAILED.register(e.toString());
			return null;
		}
	}
	public com.FileManagerX.BasicModels.DepotInfo query(Object conditions) {
		if(conditions == null) {
			return null;
		}
		String con = "";
		if(conditions instanceof QueryConditions) {
			con = MySQLManager_SHELL.queryConditionsToStatement((QueryConditions)conditions);
		}
		else if(conditions instanceof QueryCondition) {
			con = MySQLManager_SHELL.queryConditionToStatement((QueryCondition)conditions);
		}
		else if(conditions instanceof String) {
			QueryConditions qcs = new QueryConditions();
			try {
				qcs.stringToThis((String)conditions);
			}catch(Exception e) {
				com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
				return null;
			}
			con = MySQLManager_SHELL.queryConditionsToStatement(qcs);
		}
		else {
			return null;
		}
		String exp = "SELECT * FROM " + this.name + " " + con + ";";
		try {
			ResultSet set = statement.executeQuery(exp);
			
			if(set.next()) {
				com.FileManagerX.BasicModels.DepotInfo d = new com.FileManagerX.BasicModels.DepotInfo();
				d.setIndex(set.getLong("Index"));
				d.setName(set.getString("Name"));
				d.getMachineInfo().setIndex(set.getLong("MachineIndex"));
				d.setDBIndex(set.getLong("DataBaseIndex"));
				d.setState(com.FileManagerX.BasicEnums.DepotState.valueOf(set.getString("State")));
				d.setUrl(set.getString("Url"));
				return d;
			}
			
			return null;
		}catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.DB_OPERATE_FAILED.register(e.toString());
			return null;
		}
	}
	public com.FileManagerX.BasicCollections.DepotInfos updates(Object items) {
		if(items == null) {
			return null;
		}
		if(!(items instanceof com.FileManagerX.BasicModels.DepotInfo)) {
			return null;
		}
		com.FileManagerX.BasicCollections.DepotInfos depots = (com.FileManagerX.BasicCollections.DepotInfos)items;
		if(depots.size() == 0) {
			return depots;
		}
		
		com.FileManagerX.BasicCollections.DepotInfos errors = new com.FileManagerX.BasicCollections.DepotInfos();
		for(com.FileManagerX.BasicModels.DepotInfo d : depots.getContent()) {
			if(!this.update(d)) { errors.add(d); }
		}
		return errors;
	}
	public boolean update(Object item) {
		if(item == null) {
			return false;
		}
		if(!(item instanceof com.FileManagerX.BasicModels.DepotInfo)) {
			return false;
		}
		
		com.FileManagerX.BasicModels.DepotInfo depotInfo = (com.FileManagerX.BasicModels.DepotInfo)item;
		if(depotInfo == null || depotInfo.getMachineInfo() == null) {
			return false;
		}
		String exp = "";
		QueryCondition qc = new QueryCondition();
		qc.setItemName("Index");
		qc.setSign(Sign.EQUAL);
		qc.setValue(String.valueOf(depotInfo.getIndex()));
		com.FileManagerX.BasicModels.DepotInfo exists = (depotInfo.getIndex() >= 0 && depotInfo.getIndex() <= com.FileManagerX.Globals.Configurations.Next_DepotIndex) ?
				this.query(qc) :
				null;
		if(exists == null) {
			depotInfo.setIndex();
			exp = "INSERT INTO " + this.name + " VALUES(" +
				String.valueOf(depotInfo.getIndex()) + ", " +
				"'" + depotInfo.getName() + "', " +
				String.valueOf(depotInfo.getMachineInfo().getIndex()) + ", " +
				String.valueOf(depotInfo.getDBIndex()) + ", " +
				"'" + depotInfo.getState().toString() + "', " +
				"'" + depotInfo.getUrl().replace("\\", "\\\\") + "'" +
				");";
		}
		else {
			exp = "UPDATE " + this.name + " SET " +
				"`Index` = " + String.valueOf(depotInfo.getIndex()) + ", " +
				"`Name` = '" + depotInfo.getName() + "', " + 
				"`MachineIndex` = " + String.valueOf(depotInfo.getMachineInfo().getIndex()) + ", " +
				"`State` = '" + depotInfo.getState().toString() + "', " + 
				"`DataBaseIndex` = " + String.valueOf(depotInfo.getDBIndex()) + ", " +
				"`Url` = '" + depotInfo.getUrl().replace("\\", "\\\\") + "' " +
				"WHERE " +
				"`Index` = " + String.valueOf(depotInfo.getIndex()) +
				";";
		}
		try {
			statement.execute("SET SQL_SAFE_UPDATES = 0;");
			statement.executeUpdate(exp);
			return true;
		}catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.DB_OPERATE_FAILED.register(e.toString(), "exp = " + exp);
			return false;
		}
	}
	public com.FileManagerX.BasicCollections.DepotInfos removes(Object items) {
		if(items == null) {
			return null;
		}
		if(!(items instanceof com.FileManagerX.BasicCollections.DepotInfos)) {
			return null;
		}
		com.FileManagerX.BasicCollections.DepotInfos depots = (com.FileManagerX.BasicCollections.DepotInfos)items;
		if(depots.size() == 0) {
			return depots;
		}
		
		com.FileManagerX.BasicCollections.DepotInfos errors = new com.FileManagerX.BasicCollections.DepotInfos();
		for(com.FileManagerX.BasicModels.DepotInfo d : depots.getContent()) {
			if(!this.remove(d)) { errors.add(d); }
		}
		return errors;
	}
	public boolean remove(Object item) {
		long index = -1;
		
		if(item == null) {
			return false;
		}
		if(item instanceof com.FileManagerX.BasicModels.DepotInfo) {
			index = ((com.FileManagerX.BasicModels.DepotInfo)item).getIndex();
		}
		else if(item instanceof Long) {
			index = (long)item;
		}
		else if(item instanceof Integer) {
			index = (long)item;
		}
		else {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register("Wrong type of Input");
			return false;
		}
		
		try {
			String exp = "DELETE FROM " + this.name + " WHERE `Index` = " + String.valueOf(index) + ";";
			this.statement.execute(exp);
			return true;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.DB_OPERATE_FAILED.register(e.toString());
			return true;
		}
	}
	public long size() {
		try {
			String exp = "SELECT * FROM " + this.name + ";";
			ResultSet set = this.statement.executeQuery(exp);
			long cnt = 0;
			while(set.next()) { cnt++; }
			return cnt;
			
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.DB_OPERATE_FAILED.register(e.toString());
			return 0;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
