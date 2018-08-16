package com.FileManagerX.DataBase;

import java.sql.*;

public class MySQLManager_Chat implements com.FileManagerX.Interfaces.IDBManager {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private com.FileManagerX.BasicModels.DataBaseInfo database;
	private com.FileManagerX.DataBase.Unit unit;
	private boolean connected;
	private boolean running;
	private String chatName;
	private String contentName;
	private long nextContentIndex;
	
	private Connection connection;
	private Statement statement;
	
	private String[] itemsChat = new String[] {
			"Index",
			"Type",
			"Time",
			"SourUser",
			"DestUser",
			"SourGroup",
			"DestGroup",
			"Content"
	};
	private String[] typesChat = new String[] {
			"BIGINT UNIQUE",
			"VARCHAR(100)",
			"BIGINT",
			"BIGINT",
			"BIGINT",
			"BIGINT",
			"BIGINT",
			"BIGINT"
	};
	private String[] itemsContent = new String[] {
			"Index",
			"Content"
	};
	private String[] typesContent = new String[] {
			"BIGINT UNIQUE",
			"VARCHAR(10240)"
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

	public MySQLManager_Chat() {
		initThis();
	}
	private void initThis() {
		this.database = null;
		this.unit = Unit.Chat;
		this.connected = false;
		this.chatName = "Chats";
		this.contentName = "ChatContents";
		this.nextContentIndex = this.nextContentIndex();
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
		
		boolean ok = true;
		ok &= this.createChat();
		ok &= this.createContent();
		return ok;
	}
	public boolean createChat() {
		if(this.existsChat()) { return true; }
		
		try {
			String exp = "CREATE TABLE " + this.chatName + " (";
			for(int i=0; i<itemsChat.length; i++) {
				exp += "`" + itemsChat[i] + "` " + typesChat[i] + " NOT NULL, ";
			}
			exp = exp.substring(0, exp.length()-2);
			exp += ");";
			statement.executeUpdate(exp);
			return true;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.DB_OPERATE_FAILED.register
				("Create Table " + chatName + " Failed", e.toString());
			return false;
		}
	}
	public boolean createContent() {
		if(this.existsContent()) { return true; }
		
		try {
			String exp = "CREATE TABLE " + this.contentName + " (";
			for(int i=0; i<itemsContent.length; i++) {
				exp += "`" + itemsContent[i] + "` " + typesContent[i] + " NOT NULL, ";
			}
			exp = exp.substring(0, exp.length()-2);
			exp += ");";
			statement.executeUpdate(exp);
			return true;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.DB_OPERATE_FAILED.register
				("Create Table " + contentName + " Failed", e.toString());
			return false;
		}
	}
	public boolean delete() {
		boolean ok = true;
		ok &= this.deleteChat();
		ok &= this.deleteContent();
		return ok;
	}
	public boolean deleteChat() {
		String exp = "DROP TABLE " + chatName;
		try {
			statement.execute(exp);
			return true;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.DB_OPERATE_FAILED.register
				("Delete Table " + chatName + " Failed", e.toString());
			return false;
		}
	}
	public boolean deleteContent() {
		String exp = "DROP TABLE " + contentName;
		try {
			statement.execute(exp);
			return true;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.DB_OPERATE_FAILED.register
				("Delete Table " + contentName + " Failed", e.toString());
			return false;
		}
	}
	public boolean exists() {
		boolean ok = true;
		ok &= this.existsChat();
		ok &= this.existsContent();
		return ok;
	}
	public boolean existsChat() {
		try {
			ResultSet set = this.connection.getMetaData().getTables(null, null, this.chatName, null);
			return set.next();
		} catch(Exception e) {
			return false;
		}
	}
	public boolean existsContent() {
		try {
			ResultSet set = this.connection.getMetaData().getTables(null, null, this.contentName, null);
			return set.next();
		} catch(Exception e) {
			return false;
		}
	}
	public boolean clear() {
		return this.delete();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.BasicCollections.Chats querys(Object conditions) {
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
		String exp = "SELECT * FROM " + this.chatName + " " + con + ";";
		try {
			ResultSet set = statement.executeQuery(exp);
			com.FileManagerX.BasicCollections.Chats cs = new com.FileManagerX.BasicCollections.Chats();
			int cnt = 0;
			
			while(set.next()) {
				com.FileManagerX.BasicModels.Chat c = new com.FileManagerX.BasicModels.Chat();
				c.setIndex(set.getLong(1));
				c.setType(com.FileManagerX.BasicEnums.ChatType.valueOf(set.getString(2)));
				c.setTime(set.getLong(3));
				c.setSourUser(set.getLong(4));
				c.setDestUser(set.getLong(5));
				c.setSourGroup(set.getLong(6));
				c.setDestGroup(set.getLong(7));
				c.setContent(this.queryContent(set.getLong(8)));
				cs.add(c);
				
				if(++cnt > com.FileManagerX.Globals.Configurations.DataBaseQueryLimit) { break; }
			}
			
			return cs;
		}catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.DB_OPERATE_FAILED.register(e.toString());
			return null;
		}
	}
	public com.FileManagerX.BasicModels.Chat query(Object conditions) {
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
		String exp = "SELECT * FROM " + this.chatName + " " + con + ";";
		try {
			ResultSet set = statement.executeQuery(exp);
			
			if(set.next()) {
				com.FileManagerX.BasicModels.Chat c = new com.FileManagerX.BasicModels.Chat();
				c.setIndex(set.getLong(1));
				c.setType(com.FileManagerX.BasicEnums.ChatType.valueOf(set.getString(2)));
				c.setTime(set.getLong(3));
				c.setSourUser(set.getLong(4));
				c.setDestUser(set.getLong(5));
				c.setSourGroup(set.getLong(6));
				c.setDestGroup(set.getLong(7));
				c.setContent(this.queryContent(set.getLong(8)));
				return c;
			}
			
			return null;
		}catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.DB_OPERATE_FAILED.register(e.toString());
			return null;
		}
	}
	public com.FileManagerX.BasicCollections.Chats updates(Object items) {
		if(items == null) {
			return null;
		}
		if(!(items instanceof com.FileManagerX.BasicCollections.Chats)) {
			return null;
		}
		com.FileManagerX.BasicCollections.Chats chats = (com.FileManagerX.BasicCollections.Chats)items;
		if(chats.size() == 0) {
			return chats;
		}
		
		com.FileManagerX.BasicCollections.Chats errors = new com.FileManagerX.BasicCollections.Chats();
		for(com.FileManagerX.BasicModels.Chat c : chats.getContent()) {
			if(!this.update(c)) { errors.add(c); }
		}
		return errors;
	}
	public boolean update(Object item) {
		if(item == null) {
			return false;
		}
		if(!(item instanceof com.FileManagerX.BasicModels.Chat)) {
			return false;
		}
		
		com.FileManagerX.BasicModels.Chat chat = (com.FileManagerX.BasicModels.Chat)item;
		String exp = "";
		QueryCondition qc = new QueryCondition();
		qc.setItemName("Index");
		qc.setSign(Sign.EQUAL);
		qc.setValue(String.valueOf(chat.getIndex()));
		com.FileManagerX.BasicModels.Chat exists = (chat.getIndex() >=0 &&
				chat.getIndex() <= com.FileManagerX.Globals.Configurations.Next_UserIndex) ? 
						this.query(qc) :
						null
			;
		
		long contentIndex = this.updateContent(chat.getContent());
		
		if(exists == null) {
			chat.setIndex();
			exp = "INSERT INTO " + this.chatName + " VALUES(" +
				chat.getIndex() + ", " +
				"'" + chat.getType() + "', " +
				chat.getTime() + ", " +
				chat.getSourUser() + ", " +
				chat.getDestUser() + ", " +
				chat.getSourGroup() + ", " +
				chat.getDestGroup() + ", " +
				contentIndex +
				");";
		}
		else {
			exp = "UPDATE " + this.chatName + " SET " +
				"`Index` = " + chat.getIndex() + ", " +
				"`Type` = '" + chat.getType().toString() + "', " + 
				"`Index` = " + chat.getSourUser() + ", " +
				"`Index` = " + chat.getDestUser() + ", " +
				"`Index` = " + chat.getSourGroup() + ", " +
				"`Index` = " + chat.getDestGroup() + ", " +
				"`Index` = " + chat.getContent() + " " +
				"WHERE " + 
				"`Index` = " + chat.getIndex() +
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
	public com.FileManagerX.BasicCollections.Chats removes(Object items) {
		if(items == null) {
			return null;
		}
		if(!(items instanceof com.FileManagerX.BasicCollections.Chats)) {
			return null;
		}
		com.FileManagerX.BasicCollections.Chats chats = (com.FileManagerX.BasicCollections.Chats)items;
		if(chats.size() == 0) {
			return chats;
		}
		
		com.FileManagerX.BasicCollections.Chats errors = new com.FileManagerX.BasicCollections.Chats();
		for(com.FileManagerX.BasicModels.Chat c : chats.getContent()) {
			if(!this.remove(c)) { errors.add(c); }
		}
		return errors;
	}
	public boolean remove(Object item) {
		long index = -1;
		
		if(item == null) {
			return false;
		}
		if(item instanceof com.FileManagerX.BasicModels.Chat) {
			index = ((com.FileManagerX.BasicModels.Chat)item).getIndex();
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
			String exp = "DELETE FROM " + this.chatName + " WHERE `Index` = " + String.valueOf(index) + ";";
			this.statement.execute(exp);
			return true;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.DB_OPERATE_FAILED.register(e.toString());
			return true;
		}
	}
	public long size() {
		try {
			String exp = "SELECT * FROM " + this.chatName + ";";
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

	public String queryContent(long index) {
		String exp = "SELECT * FROM " + this.contentName + " WHERE INDEX = " + index + ";";
		try {
			ResultSet set = statement.executeQuery(exp);
			if(set.next()) {
				return com.FileManagerX.Coder.Decoder.Decode_DataBaseString2String(set.getString(2));
			}
			return null;
		}catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.DB_OPERATE_FAILED.register(e.toString());
			return null;
		}
	}
	public long updateContent(String content) {
		if(content == null) {
			content = "";
		}
		content = com.FileManagerX.Coder.Encoder.Encode_String2DataBaseString(content);
		String exp = "SELECT * FROM " + this.contentName + " WHERE Content = '" + content + "';";
		try {
			ResultSet set = statement.executeQuery(exp);
			if(set.next()) {
				return set.getLong(1);
			}
			else {
				this.nextContentIndex++;
				exp = "INSERT INTO " + this.contentName + " VALUES(" +
						this.nextContentIndex + ", " +
						"'" + content + "'" +
						");";
				statement.execute("SET SQL_SAFE_UPDATES = 0;");
				statement.execute(exp);
				return this.nextContentIndex;
			}
		}catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.DB_OPERATE_FAILED.register(e.toString());
			return -1;
		}
	}
	public long nextContentIndex() {
		try {
			String exp = "SELECT * FROM " + this.contentName + ";";
			ResultSet set = this.statement.executeQuery(exp);
			this.nextContentIndex = 0;
			while(set.next()) { 
				long index = set.getLong(1);
				if(index > this.nextContentIndex) {
					this.nextContentIndex = index;
				}
			}
			return this.nextContentIndex;
			
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.DB_OPERATE_FAILED.register(e.toString());
			return 0;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
