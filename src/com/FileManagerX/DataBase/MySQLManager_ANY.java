package com.FileManagerX.DataBase;

public class MySQLManager_ANY <T extends com.FileManagerX.Interfaces.IPublic, K>
	implements com.FileManagerX.Interfaces.IDBManager {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private com.FileManagerX.BasicModels.DataBaseInfo database;
	private com.FileManagerX.DataBase.Unit unit;
	private boolean connected;
	private boolean running;
	private String name;
	
	private String[] fields;
	private com.FileManagerX.BasicEnums.DataType[] types;
	private int key;
	private boolean increase;
	
	private java.sql.Connection connection;
	private java.sql.Statement statement;
	
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
	public boolean setName(String name) {
		if(name == null) {
			return false;
		}
		this.name = name;
		return true;
	}
	public boolean setFields(String[] fields) {
		if(fields == null) {
			return false;
		}
		this.fields = fields;
		return true;
	}
	public boolean setDataTypes(com.FileManagerX.BasicEnums.DataType[] types) {
		if(types == null) {
			return false;
		}
		this.types = types;
		return true;
	}
	public boolean setKey(String key) {
		for(int i=0; i<this.fields.length; i++) {
			if(this.fields[i].equals(key)) {
				this.key = i;
				return true;
			}
		}
		return false;
	}
	public boolean setIsIncreaseKey(boolean increase) {
		this.increase = increase;
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
	public String getKey() {
		return this.fields[this.key];
	}
	public com.FileManagerX.BasicEnums.DataType getDataType(String field) {
		for(int i=0; i<fields.length; i++) {
			if(fields[i].equals(field)) {
				return types[i];
			}
		}
		return null;
	}
	
	public boolean isStringType(String field) {
		for(int i=0; i<fields.length; i++) {
			if(fields[i].equals(field)) {
				return types[i].equals(com.FileManagerX.BasicEnums.DataType.STRING) ||
					   types[i].equals(com.FileManagerX.BasicEnums.DataType.ENUM) ||
					   types[i].equals(com.FileManagerX.BasicEnums.DataType.OTHERS);
			}
		}
		return false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public MySQLManager_ANY() {
		initThis();
	}
	private void initThis() {
		this.database = null;
		this.unit = Unit.ANY;
		this.connected = false;
		this.running = false;
		this.name = "Any";
		this.increase = true;
	}
	public T createT() {
		try {
			@SuppressWarnings("unchecked")
			Class<T> entityClass = (Class<T>) 
		        		((java.lang.reflect.ParameterizedType)
		        				getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		        return entityClass.newInstance();
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void reflect() {
		Object target = this.createT();
		java.lang.reflect.Field[] fields = com.FileManagerX.Tools.Reflect.getFields(target);
		this.fields = new String[fields.length];
		this.types = new com.FileManagerX.BasicEnums.DataType[fields.length];
		for(int i=0; i<fields.length; i++) {
			this.fields[i] = fields[i].getName();
			this.types[i] = com.FileManagerX.Tools.Reflect.getFieldValueType(fields[i], target);
		}
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
			connection = java.sql.DriverManager.getConnection(url, loginName, password);
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
		
		boolean ok = this.create();
		ok &= this.check();
		this.connected = true;
		return ok;
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
			this.running = true;
			String exp = "CREATE TABLE `" + this.name + "` (";
			for(int i=0; i<fields.length; i++) {
				boolean ai = this.increase && this.key == i && 
						!this.types[i].equals(com.FileManagerX.BasicEnums.DataType.STRING) &&
						!this.types[i].equals(com.FileManagerX.BasicEnums.DataType.ENUM);
				exp += "`" + fields[i] + "` " + 
						types[i].getDataBaseType() + " " +
						(ai ? "AUTO_INCREMENT" : "") +
						" NOT NULL,";
			}
			exp += "PRIMARY KEY(`" + this.fields[this.key] + "`)";
			exp += ");";
			statement.executeUpdate(exp);
			this.running = false;
			return true;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.DB_OPERATE_FAILED.register
				("Create Table " + name + " Failed", e.toString());
			this.running = false;
			return false;
		}
	}
	public boolean delete() {
		String exp = "DROP TABLE " + name;
		try {
			this.running = true;
			statement.execute(exp);
			this.running = false;
			return true;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.DB_OPERATE_FAILED.register
				("Delete Table " + name + " Failed", e.toString());
			this.running = false;
			return false;
		}
	}
	public boolean exists() {
		try {
			this.running = false;
			java.sql.ResultSet set = this.connection.getMetaData().getTables(null, null, this.name, null);
			boolean ok = set.next();
			this.running = false;
			return ok;
		} catch(Exception e) {
			return false;
		}
	}
	public boolean clear() {
		return this.delete();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@SuppressWarnings("unchecked")
	public boolean querys(Object conditions, Object oResult) {
		this.running = true;
		if(oResult == null || !(oResult instanceof com.FileManagerX.Interfaces.ICollection<?, ?>)) {
			oResult = new com.FileManagerX.BasicCollections.BasicArrayList<>();
		}
		
		com.FileManagerX.Interfaces.ICollection<T, K> result = 
				(com.FileManagerX.Interfaces.ICollection<T, K>)oResult;
		result.clear();
		
		QueryConditions qcs = new QueryConditions();
		if(conditions instanceof String) {
			com.FileManagerX.BasicModels.Config c = qcs.input((String)conditions);
			if(c == null || !c.getIsOK()) {
				com.FileManagerX.BasicEnums.ErrorType.OTHERS.register("QueryConditions is Wrong", (String)conditions);
				this.running = false;
				return false;
			}
		}
		else if(conditions instanceof QueryCondition) {
			qcs.add((QueryCondition)conditions);
		}
		else if(conditions instanceof QueryConditions) {
			qcs = (QueryConditions)conditions;
		}
		else {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register("Type of conditions is Wrong");
			this.running = false;
			return false;
		}
		
		String exp = "SELECT * FROM `" + this.name + "` " + this.qcs2string(qcs) + ";";
		try {
			java.sql.ResultSet set = statement.executeQuery(exp);
			while(set.next()) {
				T item = this.createT();
				
				for(int i=1; i<=this.fields.length; i++) {
					String f = this.fields[i-1];
					com.FileManagerX.BasicEnums.DataType t = this.types[i-1];
					if(com.FileManagerX.BasicEnums.DataType.BOOLEAN.equals(t)) {
						com.FileManagerX.Tools.Reflect.setFeild(f, set.getBoolean(i), item);
						continue;
					}
					if(com.FileManagerX.BasicEnums.DataType.INTEGER.equals(t)) {
						com.FileManagerX.Tools.Reflect.setFeild(f, set.getInt(i), item);
						continue;
					}
					if(com.FileManagerX.BasicEnums.DataType.LONG.equals(t)) {
						com.FileManagerX.Tools.Reflect.setFeild(f, set.getLong(i), item);
						continue;
					}
					if(com.FileManagerX.BasicEnums.DataType.DOUBLE.equals(t)) {
						com.FileManagerX.Tools.Reflect.setFeild(f, set.getDouble(i), item);
						continue;
					}
					if(com.FileManagerX.BasicEnums.DataType.STRING.equals(t)) {
						String s = com.FileManagerX.Coder.Decoder.Decode_DataBaseString2String(set.getString(i));
						com.FileManagerX.Tools.Reflect.setFeild(f, s, item);
						continue;
					}
					if(com.FileManagerX.BasicEnums.DataType.ENUM.equals(t)) {
						String s = com.FileManagerX.Coder.Decoder.Decode_DataBaseString2String(set.getString(i));
						Object value = this.readUnsupportField(f, s, item);
						com.FileManagerX.Tools.Reflect.setFeild(f, value, item);
						continue;
					}
					if(com.FileManagerX.BasicEnums.DataType.OTHERS.equals(t)) {
						String s = com.FileManagerX.Coder.Decoder.Decode_DataBaseString2String(set.getString(i));
						Object value = this.readUnsupportField(f, s, item);
						com.FileManagerX.Tools.Reflect.setFeild(f, value, item);
						continue;
					}
				}
				
				result.add(item);
				if(result.size() > com.FileManagerX.Globals.Configurations.LimitForQuerySize) { break; }
			}
			
			this.running = false;
			return true;
		}catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.DB_OPERATE_FAILED.register(e.toString());
			this.running = false;
			return false;
		}
	}
	@SuppressWarnings("unchecked")
	public boolean query(Object conditions, Object oResult) {
		this.running = true;
		T result = null;
		try {
			result = (T)oResult;
		} catch(Exception e) {
			;
		}
		if(result == null) {
			result = this.createT();
		}
		
		QueryConditions qcs = new QueryConditions();
		if(conditions instanceof String) {
			com.FileManagerX.BasicModels.Config c = qcs.input((String)conditions);
			if(c == null || !c.getIsOK()) {
				com.FileManagerX.BasicEnums.ErrorType.OTHERS.register("QueryConditions is Wrong", (String)conditions);
				this.running = false;
				return false;
			}
		}
		else if(conditions instanceof QueryCondition) {
			qcs.add((QueryCondition)conditions);
		}
		else if(conditions instanceof QueryConditions) {
			qcs = (QueryConditions)conditions;
		}
		else {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register("Type of conditions is Wrong");
			this.running = false;
			return false;
		}
		
		String exp = "SELECT * FROM `" + this.name + "` " + this.qcs2string(qcs) + ";";
		try {
			java.sql.ResultSet set = statement.executeQuery(exp);
			if(set.next()) {
				T item = this.createT();
				
				for(int i=1; i<=this.fields.length; i++) {
					String f = this.fields[i-1];
					com.FileManagerX.BasicEnums.DataType t = this.types[i-1];
					if(com.FileManagerX.BasicEnums.DataType.BOOLEAN.equals(t)) {
						com.FileManagerX.Tools.Reflect.setFeild(f, set.getBoolean(i), item);
						continue;
					}
					if(com.FileManagerX.BasicEnums.DataType.INTEGER.equals(t)) {
						com.FileManagerX.Tools.Reflect.setFeild(f, set.getInt(i), item);
						continue;
					}
					if(com.FileManagerX.BasicEnums.DataType.LONG.equals(t)) {
						com.FileManagerX.Tools.Reflect.setFeild(f, set.getLong(i), item);
						continue;
					}
					if(com.FileManagerX.BasicEnums.DataType.DOUBLE.equals(t)) {
						com.FileManagerX.Tools.Reflect.setFeild(f, set.getDouble(i), item);
						continue;
					}
					if(com.FileManagerX.BasicEnums.DataType.STRING.equals(t)) {
						String s = com.FileManagerX.Coder.Decoder.Decode_DataBaseString2String(set.getString(i));
						com.FileManagerX.Tools.Reflect.setFeild(f, s, item);
						continue;
					}
					if(com.FileManagerX.BasicEnums.DataType.ENUM.equals(t)) {
						String s = com.FileManagerX.Coder.Decoder.Decode_DataBaseString2String(set.getString(i));
						Object value = this.readUnsupportField(f, s, item);
						com.FileManagerX.Tools.Reflect.setFeild(f, value, item);
						continue;
					}
					if(com.FileManagerX.BasicEnums.DataType.OTHERS.equals(t)) {
						String s = com.FileManagerX.Coder.Decoder.Decode_DataBaseString2String(set.getString(i));
						Object value = this.readUnsupportField(f, s, item);
						com.FileManagerX.Tools.Reflect.setFeild(f, value, item);
						continue;
					}
				}
				
				result.copyReference(item);
				this.running = false;
				return true;
			}
			
			this.running = false;
			return false;
		}catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.DB_OPERATE_FAILED.register(e.toString());
			this.running = false;
			return false;
		}
	}
	@SuppressWarnings({ "unused", "unchecked" })
	public boolean updates(Object oItems, Object oErrors) {
		this.running = true;
		if(!(oItems instanceof com.FileManagerX.Interfaces.ICollection<?, ?>)) {
			this.running = false;
			return false;
		}
		if(!(oErrors instanceof com.FileManagerX.Interfaces.ICollection<?, ?>)) {
			this.running = false;
			return false;
		}
		if(oItems == null) {
			this.running = false;
			return false;
		}
		if(oErrors == null) {
			oErrors = new com.FileManagerX.BasicCollections.BasicArrayList<>();
		}
		
		com.FileManagerX.Interfaces.ICollection<T, K> items = 
				(com.FileManagerX.Interfaces.ICollection<T, K>)oItems;
		com.FileManagerX.Interfaces.ICollection<T, K> errors = 
				(com.FileManagerX.Interfaces.ICollection<T, K>)oErrors;
		errors.clear();
		
		com.FileManagerX.Interfaces.IIterator<T> it = items.getIterator();
		while(it.hasNext()) {
			if(!this.update(it.getNext())) { errors.add(it.getNext()); }
		}
		
		this.running = false;
		return errors.size() == 0;
	}
	@SuppressWarnings("unchecked")
	public boolean update(Object oItem) {
		this.running = true;
		T item = null;
		try {
			item = (T)oItem;
		} catch(Exception e) {
			;
		}
		if(item == null) { this.running = false; return false; }
		
		boolean found = this.query(this.qcsForKey(item), null);
		String exp = "";
		if(!found) {
			exp = "INSERT INTO `" + this.name + "`(";
			for(int i=0; i<this.fields.length; i++) {
				if(this.increase && i == this.key &&
						!this.types[i].equals(com.FileManagerX.BasicEnums.DataType.STRING) &&
						!this.types[i].equals(com.FileManagerX.BasicEnums.DataType.ENUM)) {
					continue;
				}
				exp += (i == this.fields.length-1) ? ("`" + this.fields[i] + "`") :
					"`" + this.fields[i] + "`, ";
			}
			exp += ") VALUES(";
			
			for(int i=0; i<this.fields.length; i++) {
				if(this.increase && i == this.key &&
						!this.types[i].equals(com.FileManagerX.BasicEnums.DataType.STRING) &&
						!this.types[i].equals(com.FileManagerX.BasicEnums.DataType.ENUM)) {
					continue;
				}
				String f = this.fields[i];
				com.FileManagerX.BasicEnums.DataType t = this.types[i];
				if(com.FileManagerX.BasicEnums.DataType.OTHERS.equals(t)) {
					String s = this.writeUnsupportField(f, com.FileManagerX.Tools.Reflect.getField(f, item), item);
					exp += "'" + com.FileManagerX.Coder.Encoder.Encode_String2DataBaseString(s) + "'";
				}
				else if(com.FileManagerX.BasicEnums.DataType.STRING.equals(t)) {
					String s = com.FileManagerX.Tools.Reflect.getField(f, item).toString();
					exp += "'" + com.FileManagerX.Coder.Encoder.Encode_String2DataBaseString(s) + "'";
				}
				else if(com.FileManagerX.BasicEnums.DataType.ENUM.equals(t)) {
					String s = com.FileManagerX.Tools.Reflect.getField(f, item).toString();
					exp += "'" + com.FileManagerX.Coder.Encoder.Encode_String2DataBaseString(s) + "'";
				}
				else {
					exp += com.FileManagerX.Tools.Reflect.getField(f, item);
				}
				if(i != this.fields.length-1) {
					exp += ", ";
				}
			}
			exp += ");";
		}
		else {
			exp = "UPDATE `" + this.name + "` SET ";
			for(int i=0; i<this.fields.length; i++) {
				if(this.increase && i == this.key &&
						!this.types[i].equals(com.FileManagerX.BasicEnums.DataType.STRING) &&
						!this.types[i].equals(com.FileManagerX.BasicEnums.DataType.ENUM)) {
					continue;
				}
				String f = this.fields[i];
				com.FileManagerX.BasicEnums.DataType t = this.types[i];
				exp += "`" + f + "` = ";
				if(com.FileManagerX.BasicEnums.DataType.OTHERS.equals(t)) {
					String s = this.writeUnsupportField(f, com.FileManagerX.Tools.Reflect.getField(f, item), item);
					exp += "'" + com.FileManagerX.Coder.Encoder.Encode_String2DataBaseString(s) + "'";
				}
				else if(com.FileManagerX.BasicEnums.DataType.STRING.equals(t)) {
					String s = (String)com.FileManagerX.Tools.Reflect.getField(f, item);
					exp += "'" + com.FileManagerX.Coder.Encoder.Encode_String2DataBaseString(s) + "'";
				}
				else if(com.FileManagerX.BasicEnums.DataType.ENUM.equals(t)) {
					String s = com.FileManagerX.Tools.Reflect.getField(f, item).toString();
					exp += "'" + com.FileManagerX.Coder.Encoder.Encode_String2DataBaseString(s) + "'";
				}
				else {
					exp += com.FileManagerX.Tools.Reflect.getField(f, item);
				}
				if(i != this.fields.length-1) {
					exp += ", ";
				}
			}
			exp += " WHERE " + this.conForKey(item) + ";";
		}
		
		try {
			statement.execute("SET SQL_SAFE_UPDATES = 0;");
			int count = statement.executeUpdate(exp);
			T next = this.queryByCount(count);
			item.copyReference(next);
			this.running = false;
			return true;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.DB_OPERATE_FAILED.register(e.toString(), "exp = " + exp);
			this.running = false;
			return false;
		}
	}
	@SuppressWarnings({ "unchecked", "unused" })
	public synchronized boolean removes(Object oItems, Object oErrors) {
		this.running = true;
		if(!(oItems instanceof com.FileManagerX.Interfaces.ICollection<?, ?>)) {
			this.running = false;
			return false;
		}
		if(!(oErrors instanceof com.FileManagerX.Interfaces.ICollection<?, ?>)) {
			this.running = false;
			return false;
		}
		if(oItems == null) {
			this.running = false;
			return false;
		}
		if(oErrors == null) {
			oErrors = new com.FileManagerX.BasicCollections.BasicArrayList<>();
		}
		
		com.FileManagerX.Interfaces.ICollection<T, K> items = 
				(com.FileManagerX.Interfaces.ICollection<T, K>)oItems;
		com.FileManagerX.Interfaces.ICollection<T, K> errors = 
				(com.FileManagerX.Interfaces.ICollection<T, K>)oErrors;
		errors.clear();
		
		com.FileManagerX.Interfaces.IIterator<T> it = items.getIterator();
		while(it.hasNext()) {
			if(!this.remove(it.getNext())) { errors.add(it.getNext()); }
		}
		
		this.running = false;
		return errors.size() == 0;
	}
	@SuppressWarnings("unchecked")
	public synchronized boolean remove(Object oItem) {
		this.running = true;
		if(oItem == null) { this.running = false; return true; }
		T item = null;
		try {
			item = (T)oItem;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.DB_OPERATE_FAILED.register(e.toString());
			return true;
		}
		try {
			String exp = "DELETE FROM `" + this.name + "` WHERE " + this.qcsForKey(item) + ";";
			this.statement.execute(exp);
			return true;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.DB_OPERATE_FAILED.register(e.toString());
			return false;
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public long size() {
		try {
			this.running = true;
			String exp = "SELECT * FROM `" + this.name + "`;";
			java.sql.ResultSet set = this.statement.executeQuery(exp);
			long cnt = 0;
			while(set.next()) { cnt++; }
			this.running = false;
			return cnt;
			
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.DB_OPERATE_FAILED.register(e.toString());
			this.running = false;
			return 0;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean check() {
		return true;
	}
	public long checkIndex() {
		try {
			String exp = "SELECT * FROM `" + this.name + "`;";
			long maxIndex = 0;
			java.sql.ResultSet set = this.statement.executeQuery(exp);
			while(set.next()) {
				long idx = set.getLong(1);
				if(idx > maxIndex) {
					maxIndex = idx;
				}
			}
			return maxIndex;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.DB_OPERATE_FAILED.register(e.toString());
			return 0;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Object readUnsupportField(String field, String value, Object target) {
		return null;
	}
	public String writeUnsupportField(String field, Object value, Object target) {
		return "";
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public String qcs2string(QueryConditions conditions) {
		if(conditions == null || conditions.size() == 0) {
			return "";
		}
		
		QueryCondition qc0 = conditions.fetchByCount(0);
		boolean iss = this.isStringType(qc0.getItemName());
		if(iss) {
			qc0.setValue(com.FileManagerX.Coder.Encoder.Encode_String2DataBaseString(qc0.getValue()));
		}
		String con = "`" + qc0.getItemName() + "` " +
				qc0.getSign().getSignString() + " " +
				(iss ? "'" : "") + 
				qc0.getValue() +
				(iss ? "'" : "");
		
		com.FileManagerX.Interfaces.IIterator<QueryCondition> it = conditions.getIterator();
		while(it.hasNext()) {
			QueryCondition qc = it.getNext();
			iss = this.isStringType(qc.getItemName());
			if(iss) {
				qc.setValue(com.FileManagerX.Coder.Encoder.Encode_String2DataBaseString(qc.getValue()));
			}
			con += " " + qc.getRelation().toString() + " " +
					"`" + qc.getItemName() + "` " +
					qc.getSign().getSignString() + " " +
					(iss ? "'" : "") + 
					qc.getValue() + 
					(iss ? "'" : "");
		}
		return "WHERE " + con;
	}
	public String qcsForKey(T item) {
		String f = this.fields[this.key];
		Object vo = com.FileManagerX.Tools.Reflect.getField(f, item);
		String v = "";
		com.FileManagerX.BasicEnums.DataType t = this.types[this.key];
		if(com.FileManagerX.BasicEnums.DataType.OTHERS.equals(t)) {
			v = this.writeUnsupportField(f, vo, item);
		}
		else {
			v = vo.toString();
		}
		
		return "qcs = 1|[&] " + f + " = " + v;
	}
	public String conForKey(T item) {
		String f = this.fields[this.key];
		Object vo = com.FileManagerX.Tools.Reflect.getField(f, item);
		String v = "";
		com.FileManagerX.BasicEnums.DataType t = this.types[this.key];
		if(com.FileManagerX.BasicEnums.DataType.OTHERS.equals(t)) {
			v = this.writeUnsupportField(f, vo, item);
		}
		else if(com.FileManagerX.BasicEnums.DataType.ENUM.equals(t)) {
			v = "'" + vo.toString() + "'";
		}
		else if(com.FileManagerX.BasicEnums.DataType.STRING.equals(t)) {
			v = "'" + vo.toString() + "'";
		}
		else {
			v = vo.toString();
		}
		
		return "`" + f + "` = " + v;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public T queryByCount(int count) {
		String exp = "SELECT * FROM `" + this.name + "`;";
		try {
			java.sql.ResultSet set = statement.executeQuery(exp);
			while(set.next()) {
				if(--count > 0) { continue; }
				T item = this.createT();
				
				for(int i=1; i<=this.fields.length; i++) {
					String f = this.fields[i-1];
					com.FileManagerX.BasicEnums.DataType t = this.types[i-1];
					if(com.FileManagerX.BasicEnums.DataType.BOOLEAN.equals(t)) {
						com.FileManagerX.Tools.Reflect.setFeild(f, set.getBoolean(i), item);
						continue;
					}
					if(com.FileManagerX.BasicEnums.DataType.INTEGER.equals(t)) {
						com.FileManagerX.Tools.Reflect.setFeild(f, set.getInt(i), item);
						continue;
					}
					if(com.FileManagerX.BasicEnums.DataType.LONG.equals(t)) {
						com.FileManagerX.Tools.Reflect.setFeild(f, set.getLong(i), item);
						continue;
					}
					if(com.FileManagerX.BasicEnums.DataType.DOUBLE.equals(t)) {
						com.FileManagerX.Tools.Reflect.setFeild(f, set.getDouble(i), item);
						continue;
					}
					if(com.FileManagerX.BasicEnums.DataType.STRING.equals(t)) {
						String s = com.FileManagerX.Coder.Decoder.Decode_DataBaseString2String(set.getString(i));
						com.FileManagerX.Tools.Reflect.setFeild(f, s, item);
						continue;
					}
					if(com.FileManagerX.BasicEnums.DataType.ENUM.equals(t)) {
						String s = com.FileManagerX.Coder.Decoder.Decode_DataBaseString2String(set.getString(i));
						Object value = this.readUnsupportField(f, s, item);
						com.FileManagerX.Tools.Reflect.setFeild(f, value, item);
						continue;
					}
					if(com.FileManagerX.BasicEnums.DataType.OTHERS.equals(t)) {
						String s = com.FileManagerX.Coder.Decoder.Decode_DataBaseString2String(set.getString(i));
						Object value = this.readUnsupportField(f, s, item);
						com.FileManagerX.Tools.Reflect.setFeild(f, value, item);
						continue;
					}
				}
				return item;
			}
			return null;
		}catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.DB_OPERATE_FAILED.register(e.toString());
			this.running = false;
			return null;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
