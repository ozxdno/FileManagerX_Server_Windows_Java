package com.FileManagerX.DataBase;

import java.sql.*;

public class MySQLManager_SHELL implements com.FileManagerX.Interfaces.IDBManager {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private com.FileManagerX.BasicModels.DataBaseInfo database;
	private com.FileManagerX.DataBase.Unit unit;
	private boolean running;
	private String name;
	
	private Connection connection;
	private Statement statement;
	
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

	public MySQLManager_SHELL() {
		initThis();
	}
	private void initThis() {
		this.database = null;
		this.unit = Unit.SHELL;
		this.running = false;
		this.name = "";
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
		this.name = dbName;
		
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
			String exp = "CREATE DATABASE IF NOT EXISTS `"+ name + "`";
			statement.executeUpdate(exp);
			return true;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.DB_OPERATE_FAILED.register("Create DataBase Failed", e.toString());
			return false;
		}
	}
	public boolean delete() {
		try {
			String exp = "DROP DATABASE IF EXISTS `" + name + "`";
			statement.executeUpdate(exp);
			return true;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.DB_OPERATE_FAILED.register("Create DataBase Failed", e.toString());
			return false;
		}
	}
	public boolean exists() {
		return this.running;
	}
	public boolean clear() {
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Object querys(Object conditions) {
		return null;
	}
	public Object query(Object conditions) {
		return null;
	}
	public Object updates(Object items) {
		return null;
	}
	public boolean update(Object item) {
		return false;
	}
	public Object removes(Object items) {
		return null;
	}
	public boolean remove(Object item) {
		return false;
	}
	public long size() {
		return 1;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public final static String queryConditionsToStatement(QueryConditions conditions) {
		if(conditions == null || conditions.size() == 0) {
			return "";
		}
		String con = "`" + conditions.getContent().get(0).getItemName() + "` " +
				conditions.getContent().get(0).getSign().getSignString() + " " +
				conditions.getContent().get(0).getValue();
		
		for(int i=1; i<conditions.size(); i++) {
			QueryCondition qc = conditions.getContent().get(i);
			con += " " + qc.getRelation().toString() + " " +
				"`" + qc.getItemName() + "` " +
				qc.getSign().getSignString() + " " +
				qc.getValue();
		}
		return "WHERE " + con;
	}
	public final static String queryConditionToStatement(QueryCondition condition) {
		String con = "WHERE `" + condition.getItemName() + "` " + 
				condition.getSign().getSignString() + " " +
				condition.getValue();
		return con;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
