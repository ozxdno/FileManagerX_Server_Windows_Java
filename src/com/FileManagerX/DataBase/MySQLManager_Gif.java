package com.FileManagerX.DataBase;

import java.sql.*;

public class MySQLManager_Gif implements com.FileManagerX.Interfaces.IDBManager {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private com.FileManagerX.BasicModels.DataBaseInfo database;
	private com.FileManagerX.DataBase.Unit unit;
	private boolean running;
	private String name;
	
	private Connection connection;
	private Statement statement;
	
	private String[] items = new String[] {
			"Index",
			"Height",
			"Width",
			"Lasting",
			"RowPixels",
			"ColPixels"
	};
	private String[] types = new String[] {
			"INT UNIQUE",
			"INT",
			"INT",
			"BIGINT",
			"VARCHAR(2048)",
			"VARCHAR(2048)"
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

	public MySQLManager_Gif() {
		initThis();
	}
	private void initThis() {
		this.database = null;
		this.unit = Unit.BaseFile;
		this.running = false;
		this.name = "Gifs";
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

	public com.FileManagerX.BasicCollections.BaseFiles querys(Object conditions) {
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
			com.FileManagerX.BasicCollections.BaseFiles fs = new com.FileManagerX.BasicCollections.BaseFiles();
			int cnt = 0;
			
			while(set.next()) {
				com.FileManagerX.FileModels.Gif f = new com.FileManagerX.FileModels.Gif();
				f.setIndex(set.getLong(1));
				f.setHeight(set.getInt(2));
				f.setWidth(set.getInt(3));
				f.setLasting(set.getLong(4));
				f.setRowPixels(com.FileManagerX.Tools.String.splitToIntArray(set.getString(5), ' '));
				f.setColPixels(com.FileManagerX.Tools.String.splitToIntArray(set.getString(6), ' '));
				fs.add(f);
				
				if(++cnt > com.FileManagerX.Globals.Configurations.DataBaseQueryLimit) { break; }
			}
			return fs;
			
		}catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.DB_OPERATE_FAILED.register(e.toString());
			return null;
		}
	}
	public com.FileManagerX.FileModels.Gif query(Object conditions) {
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
		String exp = "SELECT * FROM " + this.name + "s " + con + ";";
		try {
			ResultSet set = statement.executeQuery(exp);
			
			if(set.next()) {
				com.FileManagerX.FileModels.Gif f = new com.FileManagerX.FileModels.Gif();
				f.setIndex(set.getLong(1));
				f.setHeight(set.getInt(2));
				f.setWidth(set.getInt(3));
				f.setLasting(set.getLong(4));
				f.setRowPixels(com.FileManagerX.Tools.String.splitToIntArray(set.getString(5), ' '));
				f.setColPixels(com.FileManagerX.Tools.String.splitToIntArray(set.getString(6), ' '));
				return f;
			}
			return null;
			
		}catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.DB_OPERATE_FAILED.register(e.toString());
			return null;
		}
	}
	public com.FileManagerX.BasicCollections.BaseFiles updates(Object items) {
		if(items == null) {
			return null;
		}
		if(!(items instanceof com.FileManagerX.BasicCollections.BaseFiles)) {
			return null;
		}
		com.FileManagerX.BasicCollections.BaseFiles files = (com.FileManagerX.BasicCollections.BaseFiles)items;
		if(files.size() == 0) {
			return files;
		}
		
		com.FileManagerX.BasicCollections.BaseFiles errors = new com.FileManagerX.BasicCollections.BaseFiles();
		for(com.FileManagerX.BasicModels.BaseFile f : files.getContent()) {
			if(!this.update(f)) { errors.add(f); }
		}
		return errors;
	}
	public boolean update(Object item) {
		if(item == null) {
			return false;
		}
		if(!(item instanceof com.FileManagerX.FileModels.Gif)) {
			return false;
		}
		
		com.FileManagerX.FileModels.Gif gif = (com.FileManagerX.FileModels.Gif)item;
		String exp = "";
		QueryCondition qc = new QueryCondition();
		qc.setItemName("Index");
		qc.setSign(Sign.EQUAL);
		qc.setValue(String.valueOf(gif.getIndex()));
		com.FileManagerX.FileModels.Gif exists = (gif.getIndex() >= 0 && gif.getIndex() <= com.FileManagerX.Globals.Configurations.Next_FileIndex) ?
				this.query(qc) :
				null;
		if(exists == null) {
			exp = "INSERT INTO " + this.name + " Values(" +
				gif.getIndex() + ", " +
				gif.getHeight() + ", " +
				gif.getWidth() + ", " +
				gif.getLasting() + ", " +
				"'" + com.FileManagerX.Tools.String.link(gif.getRowPixels(), " ") + "', " +
				"'" + com.FileManagerX.Tools.String.link(gif.getColPixels(), " ") + "'" +
				");";
		}
		else {
			exp = "UPDATE " + this.name + " SET " +
					"`Index` = " + gif.getIndex() + ", " +
					"`Height` = " + gif.getHeight() + ", " +
					"`Width` = " + gif.getWidth() + ", " +
					"`Lasting` = " + gif.getLasting() + ", " +
					"`RowPixels` = '" + com.FileManagerX.Tools.String.link(gif.getRowPixels(), " ") + "', " +
					"`ColPixels` = '" + com.FileManagerX.Tools.String.link(gif.getColPixels(), " ") + "' " +
					"WHERE " + 
					"`Index` = " + gif.getIndex() +
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
	public com.FileManagerX.BasicCollections.BaseFiles removes(Object items) {
		if(items == null) {
			return null;
		}
		if(!(items instanceof com.FileManagerX.BasicCollections.BaseFiles)) {
			return null;
		}
		com.FileManagerX.BasicCollections.BaseFiles files = (com.FileManagerX.BasicCollections.BaseFiles)items;
		if(files.size() == 0) {
			return files;
		}
		
		com.FileManagerX.BasicCollections.BaseFiles errors = new com.FileManagerX.BasicCollections.BaseFiles();
		for(com.FileManagerX.BasicModels.BaseFile f : files.getContent()) {
			if(!this.remove(f)) { errors.add(f); }
		}
		return errors;
	}
	public boolean remove(Object item) {
		long index = -1;
		
		if(item == null) {
			return false;
		}
		if(item instanceof com.FileManagerX.FileModels.Gif) {
			index = ((com.FileManagerX.FileModels.Gif)item).getIndex();
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