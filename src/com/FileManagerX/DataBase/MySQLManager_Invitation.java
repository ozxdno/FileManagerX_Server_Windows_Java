package com.FileManagerX.DataBase;

import java.sql.*;

public class MySQLManager_Invitation implements com.FileManagerX.Interfaces.IDBManager {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private com.FileManagerX.BasicModels.DataBaseInfo database;
	private com.FileManagerX.DataBase.Unit unit;
	private boolean connected;
	private boolean running;
	private String name;
	
	private Connection connection;
	private Statement statement;
	
	private String[] items = new String[] {
			"Code",
			"ActiveTime",
			"ActiveAmount",
			"Priority",
			"Level",
			"Experience",
			"Coins",
			"Money"
	};
	private String[] types = new String[] {
			"VARCHAR(100) UNIQUE",
			"VARCHAR(100)",
			"INT",
			"VARCHAR(100)",
			"VARCHAR(100)",
			"BIGINT",
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
	public boolean setIsRunning(boolean running) {
		this.running = running;
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

	public MySQLManager_Invitation() {
		initThis();
	}
	private void initThis() {
		this.database = null;
		this.unit = Unit.BaseFile;
		this.connected = false;
		this.connected = false;
		this.name = "Invitations";
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean isConnected() {
		return this.connected;
	}
	public boolean isRunning() {
		return this.running;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean connect() {
		this.connected = false;
		
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
		
		return this.connected = true;
	}
	public boolean disconnect() {
		this.connected = false;
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

	public com.FileManagerX.BasicCollections.Invitations querys(Object conditions) {
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
			com.FileManagerX.BasicCollections.Invitations invs = new com.FileManagerX.BasicCollections.Invitations();
			int cnt = 0;
			
			while(set.next()) {
				com.FileManagerX.BasicModels.Invitation i = new com.FileManagerX.BasicModels.Invitation();
				i.setUser(new com.FileManagerX.BasicModels.User());
				
				i.setCode(set.getString("Code"));
				i.setActiveTime(set.getString("ActiveTime"));
				i.setActiveAmount(set.getInt("ActiveAmount"));
				i.getUser().setPriority(com.FileManagerX.BasicEnums.UserPriority.valueOf(set.getString("Priority")));
				i.getUser().setLevel(com.FileManagerX.BasicEnums.UserLevel.valueOf(set.getString("Level")));
				i.getUser().setExperience(set.getLong("Experience"));
				i.getUser().setCoins(set.getLong("Coins"));
				i.getUser().setMoney(set.getDouble("Money"));
				invs.add(i);
				
				if(++cnt > com.FileManagerX.Globals.Configurations.DataBaseQueryLimit) { break; }
			}
			
			return invs;
		}catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.DB_OPERATE_FAILED.register(e.toString());
			return null;
		}
	}
	public com.FileManagerX.BasicModels.Invitation query(Object conditions) {
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
				com.FileManagerX.BasicModels.Invitation i = new com.FileManagerX.BasicModels.Invitation();
				i.setUser(new com.FileManagerX.BasicModels.User());
				
				i.setCode(set.getString("Code"));
				i.setActiveTime(set.getString("ActiveTime"));
				i.setActiveAmount(set.getInt("ActiveAmount"));
				i.getUser().setPriority(com.FileManagerX.BasicEnums.UserPriority.valueOf(set.getString("Priority")));
				i.getUser().setLevel(com.FileManagerX.BasicEnums.UserLevel.valueOf(set.getString("Level")));
				i.getUser().setExperience(set.getLong("Experience"));
				i.getUser().setCoins(set.getLong("Coins"));
				i.getUser().setMoney(set.getDouble("Money"));
				return i;
			}
			
			return null;
		}catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.DB_OPERATE_FAILED.register(e.toString());
			return null;
		}
	}
	public com.FileManagerX.BasicCollections.Invitations updates(Object items) {
		if(items == null) {
			return null;
		}
		if(!(items instanceof com.FileManagerX.BasicCollections.Invitations)) {
			return null;
		}
		com.FileManagerX.BasicCollections.Invitations invitations = (com.FileManagerX.BasicCollections.Invitations)items;
		if(invitations.size() == 0) {
			return invitations;
		}
		
		com.FileManagerX.BasicCollections.Invitations errors = new com.FileManagerX.BasicCollections.Invitations();
		for(com.FileManagerX.BasicModels.Invitation i : invitations.getContent()) {
			if(!this.update(i)) { errors.add(i); }
		}
		return errors;
	}
	public boolean update(Object item) {
		if(item == null) {
			return false;
		}
		if(!(item instanceof com.FileManagerX.BasicModels.Invitation)) {
			return false;
		}
		
		com.FileManagerX.BasicModels.Invitation invitation = (com.FileManagerX.BasicModels.Invitation)item;
		String exp = "";
		QueryCondition qc = new QueryCondition();
		qc.setItemName("Code");
		qc.setSign(Sign.EQUAL);
		qc.setValue("'" + invitation.getCode() + "'");
		com.FileManagerX.BasicModels.Invitation exists =
			(invitation.getCode() != null && invitation.getCode().length() > 0) ?
			this.query(qc) : null;
		if(exists == null) {
			exp = "INSERT INTO " + this.name + " VALUES(" +
				"'" + invitation.getCode() + "', " +
				"'" + invitation.getUser().getPriority().toString() + "', " +
				"'" + invitation.getUser().getLevel().toString() + "', " +
				String.valueOf(invitation.getUser().getExperience()) + ", " +
				String.valueOf(invitation.getUser().getCoins()) + ", " +
				String.valueOf(invitation.getUser().getMoney()) +
				");";
		}
		else {
			exp = "UPDATE " + this.name + " SET " +
				"`Code` = '" + invitation.getCode() + "', " + 
				"`Priority` = '" + invitation.getUser().getPriority().toString() + "', " + 
				"`Level` = '" + invitation.getUser().getLevel().toString() + "', " + 
				"`Experience` = '" + invitation.getUser().getExperience() + "', " + 
				"`Coins` = '" + invitation.getUser().getCoins() + "', " + 
				"`Money` = '" + invitation.getUser().getMoney() + "' " +
				"WHERE " + 
				"`Code` = '" + invitation.getCode() + "' " +
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
	public com.FileManagerX.BasicCollections.Invitations removes(Object items) {
		if(items == null) {
			return null;
		}
		if(!(items instanceof com.FileManagerX.BasicCollections.Invitations)) {
			return null;
		}
		com.FileManagerX.BasicCollections.Invitations invitations = (com.FileManagerX.BasicCollections.Invitations)items;
		if(invitations.size() == 0) {
			return invitations;
		}
		
		com.FileManagerX.BasicCollections.Invitations errors = new com.FileManagerX.BasicCollections.Invitations();
		for(com.FileManagerX.BasicModels.Invitation i : invitations.getContent()) {
			if(!this.remove(i)) { errors.add(i); }
		}
		return errors;
	}
	public boolean remove(Object item) {
		String code = null;
		
		if(item == null) {
			return false;
		}
		if(item instanceof com.FileManagerX.BasicModels.Invitation) {
			code = ((com.FileManagerX.BasicModels.Invitation)item).getCode();
		}
		else if(item instanceof String) {
			code = (String)item;
		}
		else {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register("Wrong type of Input");
			return false;
		}
		
		try {
			String exp = "DELETE FROM " + this.name + " WHERE `Index` = " + code + ";";
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
