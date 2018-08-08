package com.FileManagerX.DataBase;

import java.sql.*;

public class MySQLManager_User implements com.FileManagerX.Interfaces.IDBManager {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private com.FileManagerX.BasicModels.DataBaseInfo database;
	private com.FileManagerX.DataBase.Unit unit;
	private boolean running;
	private String name;
	
	private Connection connection;
	private Statement statement;
	
	private String[] items = new String[] {
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
			"PhotoUrl",
			"Coins",
			"Money"
	};
	String[] types = new String[] {
			"BIGINT UNIQUE",
			"VARCHAR(100) UNIQUE",
			"VARCHAR(100)",
			"VARCHAR(100)",
			"VARCHAR(1024) UNIQUE",
			"VARCHAR(100) UNIQUE",
			"VARCHAR(100)",
			"VARCHAR(100)",
			"VARCHAR(100)",
			"BIGINT",
			"VARCHAR(1024)",
			"BIGINT",
			"DOUBLE"
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

	public MySQLManager_User() {
		initThis();
	}
	private void initThis() {
		this.database = null;
		this.unit = Unit.BaseFile;
		this.running = false;
		this.name = "Users";
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

	public com.FileManagerX.BasicCollections.Users querys(Object conditions) {
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
			com.FileManagerX.BasicCollections.Users us = new com.FileManagerX.BasicCollections.Users();
			int cnt = 0;
			
			while(set.next()) {
				com.FileManagerX.BasicModels.User u = new com.FileManagerX.BasicModels.User();
				u.setIndex(set.getLong("Index"));
				u.setLoginName(set.getString("LoginName"));
				u.setNickName(set.getString("NickName"));
				u.setPassword(set.getString("Password"));
				u.setEmail(set.getString("Email"));
				u.setPhone(set.getString("Phone"));
				u.setState(com.FileManagerX.BasicEnums.UserState.valueOf(set.getString("State")));
				u.setPriority(com.FileManagerX.BasicEnums.UserPriority.valueOf(set.getString("Priority")));
				u.setLevel(com.FileManagerX.BasicEnums.UserLevel.valueOf(set.getString("Level")));
				u.setExperience(set.getLong("Experience"));
				u.setPhotoUrl(set.getString("PhotoUrl"));
				u.setCoins(set.getLong("Coins"));
				u.setMoney(set.getDouble("Money"));
				us.add(u);
				
				if(++cnt > com.FileManagerX.Globals.Configurations.DataBaseQueryLimit) { break; }
			}
			
			return us;
		}catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.DB_OPERATE_FAILED.register(e.toString());
			return null;
		}
	}
	public com.FileManagerX.BasicModels.User query(Object conditions) {
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
				com.FileManagerX.BasicModels.User u = new com.FileManagerX.BasicModels.User();
				u.setIndex(set.getLong("Index"));
				u.setLoginName(set.getString("LoginName"));
				u.setNickName(set.getString("NickName"));
				u.setPassword(set.getString("Password"));
				u.setEmail(set.getString("Email"));
				u.setPhone(set.getString("Phone"));
				u.setState(com.FileManagerX.BasicEnums.UserState.valueOf(set.getString("State")));
				u.setPriority(com.FileManagerX.BasicEnums.UserPriority.valueOf(set.getString("Priority")));
				u.setLevel(com.FileManagerX.BasicEnums.UserLevel.valueOf(set.getString("Level")));
				u.setExperience(set.getLong("Experience"));
				u.setPhotoUrl(set.getString("PhotoUrl"));
				u.setCoins(set.getLong("Coins"));
				u.setMoney(set.getDouble("Money"));
				return u;
			}
			
			return null;
		}catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.DB_OPERATE_FAILED.register(e.toString());
			return null;
		}
	}
	public com.FileManagerX.BasicCollections.Users updates(Object items) {
		if(items == null) {
			return null;
		}
		if(!(items instanceof com.FileManagerX.BasicCollections.Users)) {
			return null;
		}
		com.FileManagerX.BasicCollections.Users users = (com.FileManagerX.BasicCollections.Users)items;
		if(users.size() == 0) {
			return users;
		}
		
		com.FileManagerX.BasicCollections.Users errors = new com.FileManagerX.BasicCollections.Users();
		for(com.FileManagerX.BasicModels.User u : users.getContent()) {
			if(!this.update(u)) { errors.add(u); }
		}
		return errors;
	}
	public boolean update(Object item) {
		if(item == null) {
			return false;
		}
		if(!(item instanceof com.FileManagerX.BasicModels.User)) {
			return false;
		}
		
		com.FileManagerX.BasicModels.User user = (com.FileManagerX.BasicModels.User)item;
		String exp = "";
		QueryCondition qc = new QueryCondition();
		qc.setItemName("Index");
		qc.setSign(Sign.EQUAL);
		qc.setValue(String.valueOf(user.getIndex()));
		com.FileManagerX.BasicModels.User exists = (user.getIndex() >=0 && user.getIndex() <= com.FileManagerX.Globals.Configurations.Next_UserIndex) ? 
				this.query(qc) :
				null;
		if(exists == null) {
			user.setIndex();
			exp = "INSERT INTO " + this.name + " VALUES(" +
				String.valueOf(user.getIndex()) + ", " +
				"'" + user.getLoginName() + "', " +
				"'" + user.getNickName() + "', " +
				"'" + user.getPassword() + "', " +
				"'" + user.getEmail() + "', " +
				"'" + user.getPhone() + "', " +
				"'" + user.getState().toString() + "', " +
				"'" + user.getPriority().toString() + "', " +
				"'" + user.getLevel().toString() + "', " +
				String.valueOf(user.getExperience()) + ", " +
				"'" + user.getPhotoUrl().replace("\\", "\\\\") + "', " +
				String.valueOf(user.getCoins()) + ", " +
				String.valueOf(user.getMoney()) +
				");";
		}
		else {
			exp = "UPDATE " + this.name + " SET " +
				"`Index` = " + String.valueOf(user.getIndex()) + ", " +
				"`LoginName` = '" + user.getLoginName() + "', " + 
				"`NickName` = '" + user.getNickName() + "', " + 
				"`Password` = '" + user.getPassword() + "', " + 
				"`Email` = '" + user.getEmail() + "', " + 
				"`Phone` = '" + user.getPhone() + "', " + 
				"`State` = '" + user.getState().toString() + "', " +
				"`Priority` = '" + user.getPriority().toString() + "', " +
				"`Level` = '" + user.getLevel().toString() + "', " +
				"`Experience` = " + String.valueOf(user.getExperience()) + ", " +
				"`PhotoUrl` = '" + user.getPhotoUrl().replace("\\", "\\\\") + "', " + 
				"`Coins` = " + String.valueOf(user.getCoins()) + " " +
				"WHERE " + 
				"`Index` = " + String.valueOf(user.getIndex()) +
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
	public com.FileManagerX.BasicCollections.Users removes(Object items) {
		if(items == null) {
			return null;
		}
		if(!(items instanceof com.FileManagerX.BasicCollections.Users)) {
			return null;
		}
		com.FileManagerX.BasicCollections.Users users = (com.FileManagerX.BasicCollections.Users)items;
		if(users.size() == 0) {
			return users;
		}
		
		com.FileManagerX.BasicCollections.Users errors = new com.FileManagerX.BasicCollections.Users();
		for(com.FileManagerX.BasicModels.User u : users.getContent()) {
			if(!this.remove(u)) { errors.add(u); }
		}
		return errors;
	}
	public boolean remove(Object item) {
		long index = -1;
		
		if(item == null) {
			return false;
		}
		if(item instanceof com.FileManagerX.BasicModels.User) {
			index = ((com.FileManagerX.BasicModels.User)item).getIndex();
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
