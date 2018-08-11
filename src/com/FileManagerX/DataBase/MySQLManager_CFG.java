package com.FileManagerX.DataBase;

import java.sql.*;

public class MySQLManager_CFG implements com.FileManagerX.Interfaces.IDBManager {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private com.FileManagerX.BasicModels.DataBaseInfo database;
	private com.FileManagerX.DataBase.Unit unit;
	private boolean running;
	private String name;
	
	private Connection connection;
	private Statement statement;
	
	private String[] items = new String[] {
			"Index",
			"Machine",
			"State",
			"Modify",
			"Content"
	};
	private String[] types = new String[] {
			"BIGINT UNIQUE",
			"BIGINT UNIQUE",
			"VARCHAR(100)",
			"BIGINT",
			"VARCHAR(" + (5*1024) + ")"
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

	public MySQLManager_CFG() {
		initThis();
	}
	private void initThis() {
		this.database = null;
		this.unit = Unit.CFG;
		this.running = false;
		this.name = "CFG";
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean isConnected() {
		return this.running;
	}
	public boolean connect() {
		this.running = false;
		
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config
				(this.database.getUrl().replace('\\', '|'));
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
		com.FileManagerX.BasicCollections.BaseFiles res = new com.FileManagerX.BasicCollections.BaseFiles();
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
			int cnt = 0;
			
			while(set.next()) {
				com.FileManagerX.FileModels.CFG f = new com.FileManagerX.FileModels.CFG();
				f.setIndex(set.getLong("Index"));
				f.setMachine(set.getLong("Machine"));
				f.setState(com.FileManagerX.BasicEnums.FileState.valueOf(set.getString("State")));
				f.setModify(set.getLong("Modify"));
				f.setContent(com.FileManagerX.Tools.Array2List.toStringList(
						com.FileManagerX.Tools.String.split(
								com.FileManagerX.Coder.Decoder.Decode_String2String(set.getString("Content")),
								'|')
						));
				for(int i=0; i<f.getContent().size(); i++) {
					String s = com.FileManagerX.Coder.Decoder.Decode_String2String(f.getContent().get(i));
					f.getContent().set(i, s);
				}
				res.add(f);
				
				if(++cnt > com.FileManagerX.Globals.Configurations.DataBaseQueryLimit) { break; }
			}
			
			return res;
		}catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.DB_OPERATE_FAILED.register(e.toString());
			return res;
		}
	}
	public com.FileManagerX.FileModels.CFG query(Object conditions) {
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
				com.FileManagerX.FileModels.CFG f = new com.FileManagerX.FileModels.CFG();
				f.setIndex(set.getLong("Index"));
				f.setMachine(set.getLong("Machine"));
				f.setState(com.FileManagerX.BasicEnums.FileState.valueOf(set.getString("State")));
				f.setModify(set.getLong("Modify"));
				f.setContent(com.FileManagerX.Tools.Array2List.toStringList(
						com.FileManagerX.Tools.String.split(
								com.FileManagerX.Coder.Decoder.Decode_String2String(set.getString("Content")),
								'|')
						));
				for(int i=0; i<f.getContent().size(); i++) {
					String s = com.FileManagerX.Coder.Decoder.Decode_String2String(f.getContent().get(i));
					f.getContent().set(i, s);
				}
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
		if(!(item instanceof com.FileManagerX.FileModels.CFG)) {
			return false;
		}
		
		com.FileManagerX.FileModels.CFG file = (com.FileManagerX.FileModels.CFG)item;
		String exp = "";
		QueryCondition qc = new QueryCondition();
		qc.setItemName("Index");
		qc.setSign(Sign.EQUAL);
		qc.setValue(String.valueOf(file.getIndex()));
		com.FileManagerX.FileModels.CFG exists = (file.getIndex() >= 0 && file.getIndex() <= com.FileManagerX.Globals.Configurations.Next_FileIndex) ?
				this.query(qc) :
				null;
		
		String content = file.getContent().size() == 0 ? "" :
			com.FileManagerX.Coder.Encoder.Encode_String2String(file.getContent().get(0));
		for(int i=1; i<file.getContent().size(); i++) {
			content += "|" + com.FileManagerX.Coder.Encoder.Encode_String2String(file.getContent().get(i));
		}
		content = com.FileManagerX.Coder.Encoder.Encode_String2String(content);
				
		if(exists == null) {
			file.setIndex();
			exp = "INSERT INTO " + this.name + " VALUES(" +
				file.getIndex() + ", " +
				file.getMachine() + ", " +
				"'" + file.getState().toString() + "', " +
				file.getModify() + ", " +
				"'" + content.replace("\\", "\\\\") + "' " +
				");";
		}
		else {
			exp = "UPDATE " + this.name + " SET " +
				"`Index` = " + String.valueOf(file.getIndex()) + ", " +
				"`Machine` = " + String.valueOf(file.getMachine()) + ", " +
				"`State` = '" + file.getState().toString() + "', " +
				"`Modify` = " + String.valueOf(file.getModify()) + ", " +
				"`Content` = '" + content.replace("\\", "\\\\") + "' " + 
				"WHERE " + 
				"`Index` = " + String.valueOf(file.getIndex()) +
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
		if(item instanceof com.FileManagerX.FileModels.CFG) {
			index = ((com.FileManagerX.FileModels.CFG)item).getIndex();
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
