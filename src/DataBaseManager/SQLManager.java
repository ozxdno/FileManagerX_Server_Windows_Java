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
		BasicModels.Config c = new BasicModels.Config(this.dbInfo.getUrl().replace('\\', '|'));
		String ip_port = c.fetchFirstString();
		String loginName = c.fetchFirstString();
		String password = c.fetchFirstString();
		String dbName = c.fetchFirstString();
		
		String url = "jdbc:mysql://" + 
				ip_port + "/" + 
				dbName;
		this.isConnected = true;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, loginName, password);
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
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean createServerTables() {
		if(!this.createTable_MachineInfo()) {
			return false;
		}
		if(!this.createTable_DepotInfo()) {
			return false;
		}
		if(!this.createTable_DataBaseInfo()) {
			return false;
		}
		if(!this.createTable_Users()) {
			return false;
		}
		if(!this.createTable_Invitations()) {
			return false;
		}
		return true;
	}
	public boolean createDepotTables() {
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
				exp += "`" + columns[i] + "` " + types[i] + " NOT NULL, ";
			}
			exp = exp.substring(0, exp.length()-2);
			exp += ");";
			return statement.executeUpdate(exp) == 0;
		} catch(Exception e) {
			return false;
		}
	}
	public boolean createTable_Files() {
		String tableName = "Files";
		String[] columns = new String[] {
				"Index",
				"Father",
				"Machine",
				"Depot",
				"DataBase",
				"Url",
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
				"VARCHAR(1024)",
				"VARCHAR(100)",
				"VARCHAR(100)",
				"BIGINT",
				"BIGINT",
				"INT",
				"VARCHAR(1024)"
		};
		return this.createTable(tableName, columns, types);
	}
	public boolean createTable_Folders() {
		String tableName = "Folders";
		String[] columns = new String[] {
				"Index",
				"Father",
				"Machine",
				"Depot",
				"DataBase",
				"Url",
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
				"VARCHAR(1024)",
				"VARCHAR(100)",
				"VARCHAR(100)",
				"BIGINT",
				"BIGINT",
				"INT",
				"VARCHAR(1024)"
		};
		return this.createTable(tableName, columns, types);
	}
	public boolean createTable_MachineInfo() {
		String tableName = "MachineInfo";
		String[] columns = new String[] {
				"Index",
				"Name",
				"Ip",
				"Port"
		};
		String[] types = new String[] {
				"BIGINT",
				"VARCHAR(100)",
				"VARCHAR(16)",
				"INT"
		};
		return this.createTable(tableName, columns, types);
	}
	public boolean createTable_DepotInfo() {
		String tableName = "DepotInfo";
		String[] columns = new String[] {
				"Index",
				"Name",
				"MachineIndex",
				"DataBaseIndex",
				"State",
				"Url"
		};
		String[] types = new String[] {
				"BIGINT",
				"VARCHAR(100)",
				"BIGINT",
				"BIGINT",
				"VARCHAR(100)",
				"VARCHAR(1024)"
		};
		return this.createTable(tableName, columns, types);
	}
	public boolean createTable_DataBaseInfo() {
		String tableName = "DataBaseInfo";
		String[] columns = new String[] {
				"Index",
				"Name",
				"MachineIndex",
				"DepotIndex",
				"Type",
				"Url"
		};
		String[] types = new String[] {
				"BIGINT",
				"VARCHAR(100)",
				"BIGINT",
				"BIGINT",
				"VARCHAR(100)",
				"VARCHAR(1024)"
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
				"PhotoUrl",
				"Coins",
				"Money"
		};
		String[] types = new String[] {
				"BIGINT",
				"VARCHAR(100)",
				"VARCHAR(100)",
				"VARCHAR(100)",
				"VARCHAR(1024)",
				"VARCHAR(100)",
				"VARCHAR(100)",
				"VARCHAR(100)",
				"VARCHAR(100)",
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
				"VARCHAR(100)",
				"VARCHAR(100)",
				"VARCHAR(100)",
				"BIGINT",
				"BIGINT",
				"DOUBLE"
		};
		return this.createTable(tableName, columns, types);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean deleteServerTables() {
		if(!this.deleteTable_MachineInfo()) {
			return false;
		}
		if(!this.deleteTable_DepotInfo()) {
			return false;
		}
		if(!this.deleteTable_DataBaseInfo()) {
			return false;
		}
		if(!this.deleteTable_Users()) {
			return false;
		}
		if(!this.deleteTable_Invitations()) {
			return false;
		}
		return true;
	}
	public boolean deleteDepotTables() {
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
			statement.execute(exp);
			return true;
		} catch(Exception e) {
			return false;
		}
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
	public boolean deleteTable_Users() {
		return this.deleteTable("Users");
	}
	public boolean deleteTable_Invitations() {
		return this.deleteTable("Invitations");
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public BasicCollections.Folders QueryFolders(Object conditions) {
		BasicCollections.Folders res = new BasicCollections.Folders();
		if(conditions == null) {
			return res;
		}
		String con = "";
		if(conditions instanceof QueryConditions) {
			con = this.queryConditionsToStatement((QueryConditions)conditions);
		}
		else if(conditions instanceof QueryCondition) {
			con = this.queryConditionToStatement((QueryCondition)conditions);
		}
		else {
			return null;
		}
		String exp = "SELECT * FROM Folders " + con + ";";
		try {
			ResultSet set = statement.executeQuery(exp);
			
			while(set.next()) {
				BasicModels.Folder f = new BasicModels.Folder();
				f.setIndex(set.getLong("Index"));
				f.setFather(set.getLong("Father"));
				f.setMachine(set.getLong("Machine"));
				f.setDepot(set.getLong("Depot"));
				f.setDataBase(set.getLong("DataBase"));
				f.setUrl(set.getString("Url"));
				f.setType(BasicEnums.FileType.valueOf(set.getString("Type")));
				f.setState(BasicEnums.FileState.valueOf(set.getString("State")));
				f.setModify(set.getLong("Modify"));
				f.setLength(set.getLong("Length"));
				f.setScore(set.getInt("Score"));
				f.setTags(set.getString("Tags"));
				res.add(f);
			}
			
			return res;
		}catch(Exception e) {
			return res;
		}
	}
	public BasicCollections.BaseFiles QueryFiles(Object conditions) {
		BasicCollections.BaseFiles res = new BasicCollections.BaseFiles();
		if(conditions == null) {
			return res;
		}
		String con = "";
		if(conditions instanceof QueryConditions) {
			con = this.queryConditionsToStatement((QueryConditions)conditions);
		}
		else if(conditions instanceof QueryCondition) {
			con = this.queryConditionToStatement((QueryCondition)conditions);
		}
		else {
			return null;
		}
		String exp = "SELECT * FROM Files " + con + ";";
		try {
			ResultSet set = statement.executeQuery(exp);
			
			while(set.next()) {
				BasicModels.BaseFile f = new BasicModels.BaseFile();
				f.setIndex(set.getLong("Index"));
				f.setFather(set.getLong("Father"));
				f.setMachine(set.getLong("Machine"));
				f.setDepot(set.getLong("Depot"));
				f.setDataBase(set.getLong("DataBase"));
				f.setUrl(set.getString("Url"));
				f.setType(BasicEnums.FileType.valueOf(set.getString("Type")));
				f.setState(BasicEnums.FileState.valueOf(set.getString("State")));
				f.setModify(set.getLong("Modify"));
				f.setLength(set.getLong("Length"));
				f.setScore(set.getInt("Score"));
				f.setTags(set.getString("Tags"));
				res.add(f);
			}
			
			return res;
		}catch(Exception e) {
			return res;
		}
	}
	public BasicCollections.Users QueryUsers(Object conditions) {
		return null;
	}
	public BasicCollections.Invitations QueryInvitations(Object conditions) {
		return null;
	}
	public BasicCollections.MachineInfos QueryMachineInfos(Object conditions) {
		return null;
	}
	public BasicCollections.DepotInfos QueryDepotInfos(Object conditions) {
		return null;
	}
	public BasicCollections.DataBaseInfos QueryDataBaseInfos(Object conditions) {
		if(conditions == null) {
			return null;
		}
		String con = "";
		if(conditions instanceof QueryConditions) {
			con = this.queryConditionsToStatement((QueryConditions)conditions);
		}
		else if(conditions instanceof QueryCondition) {
			con = this.queryConditionToStatement((QueryCondition)conditions);
		}
		else {
			return null;
		}
		String exp = "SELECT * FROM DataBaseInfo " + con + ";";
		try {
			ResultSet set = statement.executeQuery(exp);
			BasicCollections.DataBaseInfos dbs = new BasicCollections.DataBaseInfos();
			
			while(set.next()) {
				BasicModels.DataBaseInfo d = new BasicModels.DataBaseInfo();
				d.setIndex(set.getLong("Index"));
				d.setName(set.getString("Name"));
				d.getMachineInfo().setIndex(set.getLong("MachineIndex"));
				d.setDepotIndex(set.getLong("DepotIndex"));
				d.setType(BasicEnums.DataBaseType.valueOf(set.getString("Type")));
				d.setUrl(set.getString("Url"));
				dbs.add(d);
			}
			
			return dbs;
		}catch(Exception e) {
			return null;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public BasicModels.Folder QueryFolder(Object conditions) {
		if(conditions == null) {
			return null;
		}
		String con = "";
		if(conditions instanceof QueryConditions) {
			con = this.queryConditionsToStatement((QueryConditions)conditions);
		}
		else if(conditions instanceof QueryCondition) {
			con = this.queryConditionToStatement((QueryCondition)conditions);
		}
		else {
			return null;
		}
		String exp = "SELECT * FROM Folders " + con + ";";
		try {
			ResultSet set = statement.executeQuery(exp);
			
			if(set.next()) {
				BasicModels.Folder f = new BasicModels.Folder();
				f.setIndex(set.getLong("Index"));
				f.setFather(set.getLong("Father"));
				f.setMachine(set.getLong("Machine"));
				f.setDepot(set.getLong("Depot"));
				f.setDataBase(set.getLong("DataBase"));
				f.setUrl(set.getString("Url"));
				f.setType(BasicEnums.FileType.valueOf(set.getString("Type")));
				f.setState(BasicEnums.FileState.valueOf(set.getString("State")));
				f.setModify(set.getLong("Modify"));
				f.setLength(set.getLong("Length"));
				f.setScore(set.getInt("Score"));
				f.setTags(set.getString("Tags"));
				return f;
			}
			
			return null;
		}catch(Exception e) {
			return null;
		}
	}
	public BasicModels.BaseFile QueryFile(Object conditions) {
		if(conditions == null) {
			return null;
		}
		String con = "";
		if(conditions instanceof QueryConditions) {
			con = this.queryConditionsToStatement((QueryConditions)conditions);
		}
		else if(conditions instanceof QueryCondition) {
			con = this.queryConditionToStatement((QueryCondition)conditions);
		}
		else {
			return null;
		}
		String exp = "SELECT * FROM Files " + con + ";";
		try {
			ResultSet set = statement.executeQuery(exp);
			
			if(set.next()) {
				BasicModels.BaseFile f = new BasicModels.BaseFile();
				f.setIndex(set.getLong("Index"));
				f.setFather(set.getLong("Father"));
				f.setMachine(set.getLong("Machine"));
				f.setDepot(set.getLong("Depot"));
				f.setDataBase(set.getLong("DataBase"));
				f.setUrl(set.getString("Url"));
				f.setType(BasicEnums.FileType.valueOf(set.getString("Type")));
				f.setState(BasicEnums.FileState.valueOf(set.getString("State")));
				f.setModify(set.getLong("Modify"));
				f.setLength(set.getLong("Length"));
				f.setScore(set.getInt("Score"));
				f.setTags(set.getString("Tags"));
				return f;
			}
			
			return null;
		}catch(Exception e) {
			return null;
		}
	}
	public BasicModels.User QueryUser(Object conditions) {
		if(conditions == null) {
			return null;
		}
		String con = "";
		if(conditions instanceof QueryConditions) {
			con = this.queryConditionsToStatement((QueryConditions)conditions);
		}
		else if(conditions instanceof QueryCondition) {
			con = this.queryConditionToStatement((QueryCondition)conditions);
		}
		else {
			return null;
		}
		String exp = "SELECT * FROM Users " + con + ";";
		try {
			ResultSet set = statement.executeQuery(exp);
			
			if(set.next()) {
				BasicModels.User u = new BasicModels.User();
				u.setIndex(set.getLong("Index"));
				u.setLoginName(set.getString("LoginName"));
				u.setNickName(set.getString("NickName"));
				u.setPassword(set.getString("Password"));
				u.setEmail(set.getString("Email"));
				u.setPhone(set.getString("Phone"));
				u.setState(BasicEnums.UserState.valueOf(set.getString("State")));
				u.setPriority(BasicEnums.UserPriority.valueOf(set.getString("Priority")));
				u.setLevel(BasicEnums.UserLevel.valueOf(set.getString("Level")));
				u.setExperience(set.getLong("Experience"));
				u.setPhotoUrl(set.getString("PhotoUrl"));
				u.setCoins(set.getLong("Coins"));
				u.setMoney(set.getDouble("Money"));
				return u;
			}
			
			return null;
		}catch(Exception e) {
			return null;
		}
	}
	public BasicModels.Invitation QueryInvitation(Object conditions) {
		if(conditions == null) {
			return null;
		}
		String con = "";
		if(conditions instanceof QueryConditions) {
			con = this.queryConditionsToStatement((QueryConditions)conditions);
		}
		else if(conditions instanceof QueryCondition) {
			con = this.queryConditionToStatement((QueryCondition)conditions);
		}
		else {
			return null;
		}
		String exp = "SELECT * FROM Invitations " + con + ";";
		try {
			ResultSet set = statement.executeQuery(exp);
			
			if(set.next()) {
				BasicModels.Invitation i = new BasicModels.Invitation();
				i.setUser(new BasicModels.User());
				
				i.setCode(set.getString("Code"));
				i.getUser().setPriority(BasicEnums.UserPriority.valueOf(set.getString("Priority")));
				i.getUser().setLevel(BasicEnums.UserLevel.valueOf(set.getString("Level")));
				i.getUser().setExperience(set.getLong("Experience"));
				i.getUser().setCoins(set.getLong("Coins"));
				i.getUser().setMoney(set.getDouble("Money"));
				return i;
			}
			
			return null;
		}catch(Exception e) {
			return null;
		}
	}
	public BasicModels.MachineInfo QueryMachineInfo(Object conditions) {
		if(conditions == null) {
			return null;
		}
		String con = "";
		if(conditions instanceof QueryConditions) {
			con = this.queryConditionsToStatement((QueryConditions)conditions);
		}
		else if(conditions instanceof QueryCondition) {
			con = this.queryConditionToStatement((QueryCondition)conditions);
		}
		else {
			return null;
		}
		String exp = "SELECT * FROM MachineInfo " + con + ";";
		try {
			ResultSet set = statement.executeQuery(exp);
			
			if(set.next()) {
				BasicModels.MachineInfo m = new BasicModels.MachineInfo();
				m.setIndex(set.getLong("Index"));
				m.setName(set.getString("Name"));
				m.setIp(set.getString("IP"));
				m.setPort(set.getInt("Port"));
				return m;
			}
			
			return null;
		}catch(Exception e) {
			return null;
		}
	}
	public BasicModels.DepotInfo QueryDepotInfo(Object conditions) {
		if(conditions == null) {
			return null;
		}
		String con = "";
		if(conditions instanceof QueryConditions) {
			con = this.queryConditionsToStatement((QueryConditions)conditions);
		}
		else if(conditions instanceof QueryCondition) {
			con = this.queryConditionToStatement((QueryCondition)conditions);
		}
		else {
			return null;
		}
		String exp = "SELECT * FROM DepotInfo " + con + ";";
		try {
			ResultSet set = statement.executeQuery(exp);
			
			if(set.next()) {
				BasicModels.DepotInfo d = new BasicModels.DepotInfo();
				d.setIndex(set.getLong("Index"));
				d.setName(set.getString("Name"));
				d.getMachineInfo().setIndex(set.getLong("MachineIndex"));
				d.setDBIndex(set.getLong("DataBaseIndex"));
				d.setState(BasicEnums.DepotState.valueOf(set.getString("State")));
				d.setUrl(set.getString("Url"));
				return d;
			}
			
			return null;
		}catch(Exception e) {
			return null;
		}
	}
	public BasicModels.DataBaseInfo QueryDataBaseInfo(Object conditions) {
		if(conditions == null) {
			return null;
		}
		String con = "";
		if(conditions instanceof QueryConditions) {
			con = this.queryConditionsToStatement((QueryConditions)conditions);
		}
		else if(conditions instanceof QueryCondition) {
			con = this.queryConditionToStatement((QueryCondition)conditions);
		}
		else {
			return null;
		}
		String exp = "SELECT * FROM DataBaseInfo " + con + ";";
		try {
			ResultSet set = statement.executeQuery(exp);
			
			if(set.next()) {
				BasicModels.DataBaseInfo d = new BasicModels.DataBaseInfo();
				d.setIndex(set.getLong("Index"));
				d.setName(set.getString("Name"));
				d.getMachineInfo().setIndex(set.getLong("MachineIndex"));
				d.setDepotIndex(set.getLong("DepotIndex"));
				d.setType(BasicEnums.DataBaseType.valueOf(set.getString("Type")));
				d.setUrl(set.getString("Url"));
				return d;
			}
			
			return null;
		}catch(Exception e) {
			return null;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean updataFolders(BasicCollections.Folders folders) {
		boolean ok = true;
		for(BasicModels.Folder f : folders.getContent()) {
			ok &= this.updataFolder(f);
		}
		return ok;
	}
	public boolean updataFiles(BasicCollections.BaseFiles files) {
		boolean ok = true;
		for(BasicModels.BaseFile f : files.getContent()) {
			if(f.getType().equals(BasicEnums.FileType.Folder)) {
				ok &= this.updataFolder((BasicModels.Folder)f);
			} else {
				ok &= this.updataFile(f);
			}
		}
		return ok;
	}
	public boolean updataUsers(BasicCollections.Users users) {
		return false;
	}
	public boolean updataInvitations(BasicCollections.Invitations invitations) {
		return false;
	}
	public boolean updataMachineInfos(BasicCollections.MachineInfos machineInfos) {
		return false;
	}
	public boolean updataDepotInfos(BasicCollections.DepotInfos depotInfos) {
		return false;
	}
	public boolean updataDataBaseInfos(BasicCollections.DataBaseInfos dbInfos) {
		return false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean updataFolder(BasicModels.Folder folder) {
		if(folder == null) {
			return false;
		}
		String exp = "";
		QueryCondition qc = new QueryCondition();
		qc.setItemName("Index");
		qc.setSign(Sign.EQUAL);
		qc.setValue(String.valueOf(folder.getIndex()));
		BasicModels.Folder exists = folder.getIndex() >= 0 ? this.QueryFolder(qc) : null;
		if(exists == null) {
			folder.setIndex();
			exp = "INSERT INTO Folders VALUES(" +
				String.valueOf(folder.getIndex()) + ", " +
				String.valueOf(folder.getFather()) + ", " +
				String.valueOf(folder.getMachine()) + ", " +
				String.valueOf(folder.getDepot()) + ", " +
				String.valueOf(folder.getDataBase()) + ", " +
				"'" + folder.getUrl().replace("\\", "\\\\") + "', " +
				"'" + folder.getType().toString() + "', " +
				"'" + folder.getState().toString() + "', " +
				String.valueOf(folder.getModify()) + ", " +
				String.valueOf(folder.getLength()) + ", " +
				String.valueOf(folder.getScore()) + ", " +
				"'" + folder.getTags() + "'" +
				");";
		}
		else {
			exp = "UPDATE Folders SET " +
				"`Index` = " + String.valueOf(folder.getIndex()) + ", " +
				"`Father` = " + String.valueOf(folder.getFather()) + ", " +
				"`Machine` = " + String.valueOf(folder.getMachine()) + ", " +
				"`Depot` = " + String.valueOf(folder.getDepot()) + ", " +
				"`DataBase` = " + String.valueOf(folder.getDataBase()) + ", " +
				"`Url` = '" + folder.getUrl().replace("\\", "\\\\") + "', " + 
				"`Type` = '" + folder.getType().toString() + "', " +
				"`State` = '" + folder.getState().toString() + "', " +
				"`Modify` = " + String.valueOf(folder.getModify()) + ", " +
				"`Length` = " + String.valueOf(folder.getLength()) + ", " +
				"`Score` = " + String.valueOf(folder.getScore()) + ", " +
				"`Tags` = '" + folder.getTags() + "' " + 
				"WHERE " + 
				"`Index` = " + String.valueOf(folder.getIndex()) +
				";";
		}
		try {
			statement.execute("SET SQL_SAFE_UPDATES = 0;");
			statement.executeUpdate(exp);
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	public boolean updataFile(BasicModels.BaseFile file) {
		if(file == null) {
			return false;
		}
		String exp = "";
		QueryCondition qc = new QueryCondition();
		qc.setItemName("Index");
		qc.setSign(Sign.EQUAL);
		qc.setValue(String.valueOf(file.getIndex()));
		BasicModels.BaseFile exists = file.getIndex() >= 0 ? this.QueryFile(qc) : null;
		if(exists == null) {
			file.setIndex();
			exp = "INSERT INTO Files VALUES(" +
				String.valueOf(file.getIndex()) + ", " +
				String.valueOf(file.getFather()) + ", " +
				String.valueOf(file.getMachine()) + ", " +
				String.valueOf(file.getDepot()) + ", " +
				String.valueOf(file.getDataBase()) + ", " +
				"'" + file.getUrl().replace("\\", "\\\\") + "', " +
				"'" + file.getType().toString() + "', " +
				"'" + file.getState().toString() + "', " +
				String.valueOf(file.getModify()) + ", " +
				String.valueOf(file.getLength()) + ", " +
				String.valueOf(file.getScore()) + ", " +
				"'" + file.getTags() + "'" +
				");";
		}
		else {
			exp = "UPDATE Files SET " +
				"`Index` = " + String.valueOf(file.getIndex()) + ", " +
				"`Father` = " + String.valueOf(file.getFather()) + ", " +
				"`Machine` = " + String.valueOf(file.getMachine()) + ", " +
				"`Depot` = " + String.valueOf(file.getDepot()) + ", " +
				"`DataBase` = " + String.valueOf(file.getDataBase()) + ", " +
				"`Url` = '" + file.getUrl().replace("\\", "\\\\") + "', " + 
				"`Type` = '" + file.getType().toString() + "', " +
				"`State` = '" + file.getState().toString() + "', " +
				"`Modify` = " + String.valueOf(file.getModify()) + ", " +
				"`Length` = " + String.valueOf(file.getLength()) + ", " +
				"`Score` = " + String.valueOf(file.getScore()) + ", " +
				"`Tags` = '" + file.getTags() + "' " + 
				"WHERE " + 
				"`Index` = " + String.valueOf(file.getIndex()) +
				";";
		}
		try {
			statement.execute("SET SQL_SAFE_UPDATES = 0;");
			statement.executeUpdate(exp);
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	public boolean updataUser(BasicModels.User user) {
		if(user == null) {
			return false;
		}
		String exp = "";
		QueryCondition qc = new QueryCondition();
		qc.setItemName("Index");
		qc.setSign(Sign.EQUAL);
		qc.setValue(String.valueOf(user.getIndex()));
		BasicModels.User exists = user.getIndex() >=0 ? this.QueryUser(qc) : null;
		if(exists == null) {
			user.setIndex();
			exp = "INSERT INTO Users VALUES(" +
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
			exp = "UPDATE Users SET " +
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
			return false;
		}
	}
	public boolean updataInvitation(BasicModels.Invitation invitation) {
		if(invitation == null) {
			return false;
		}
		String exp = "";
		QueryCondition qc = new QueryCondition();
		qc.setItemName("Code");
		qc.setSign(Sign.EQUAL);
		qc.setValue("'" + invitation.getCode() + "'");
		BasicModels.Invitation exists =
			(invitation.getCode() != null && invitation.getCode().length() > 0) ?
			this.QueryInvitation(qc) : null;
		if(exists == null) {
			exp = "INSERT INTO Invitations VALUES(" +
				"'" + invitation.getCode() + "', " +
				"'" + invitation.getUser().getPriority().toString() + "', " +
				"'" + invitation.getUser().getLevel().toString() + "', " +
				String.valueOf(invitation.getUser().getExperience()) + ", " +
				String.valueOf(invitation.getUser().getCoins()) + ", " +
				String.valueOf(invitation.getUser().getMoney()) +
				");";
		}
		else {
			exp = "UPDATE Invitations SET " +
				"`Code` = '" + invitation.getCode() + "', " + 
				"`Priority` = '" + invitation.getUser().getPriority().toString() + "', " + 
				"`Level` = '" + invitation.getUser().getLevel().toString() + "', " + 
				"`Experience` = '" + invitation.getUser().getExperience() + "', " + 
				"`Coins` = '" + invitation.getUser().getCoins() + "', " + 
				"`Money` = '" + invitation.getUser().getMoney() + "' " +
				"WHERE " + 
				"`Code` = " + invitation.getCode() +
				";";
		}
		try {
			statement.execute("SET SQL_SAFE_UPDATES = 0;");
			statement.executeUpdate(exp);
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	public boolean updataMachineInfo(BasicModels.MachineInfo machineInfo) {
		if(machineInfo == null) {
			return false;
		}
		String exp = "";
		QueryCondition qc = new QueryCondition();
		qc.setItemName("Index");
		qc.setSign(Sign.EQUAL);
		qc.setValue(String.valueOf(machineInfo.getIndex()));
		BasicModels.MachineInfo exists = machineInfo.getIndex() >= 0 ? this.QueryMachineInfo(qc) : null;
		if(exists == null) {
			machineInfo.setIndex();
			exp = "INSERT INTO MachineInfo VALUES(" +
				String.valueOf(machineInfo.getIndex()) + ", " +
				"'" + machineInfo.getName() + "', " +
				"'" + machineInfo.getIp() + "', " +
				String.valueOf(machineInfo.getPort()) +
				");";
		}
		else {
			exp = "UPDATE MachineInfo SET " +
				"`Index` = " + String.valueOf(machineInfo.getIndex()) + ", " +
				"`Name` = '" + machineInfo.getName() + "', " + 
				"`IP` = '" + machineInfo.getIp() + "', " + 
				"`Port` = " + String.valueOf(machineInfo.getPort()) + " " +
				"WHERE " +
				"`Index` = " + String.valueOf(machineInfo.getIndex()) +
				";";
		}
		try {
			statement.execute("SET SQL_SAFE_UPDATES = 0;");
			statement.executeUpdate(exp);
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	public boolean updataDepotInfo(BasicModels.DepotInfo depotInfo) {
		if(depotInfo == null || depotInfo.getMachineInfo() == null) {
			return false;
		}
		String exp = "";
		QueryCondition qc = new QueryCondition();
		qc.setItemName("Index");
		qc.setSign(Sign.EQUAL);
		qc.setValue(String.valueOf(depotInfo.getIndex()));
		BasicModels.DepotInfo exists = depotInfo.getIndex() >= 0 ? this.QueryDepotInfo(qc) : null;
		if(exists == null) {
			depotInfo.setIndex();
			exp = "INSERT INTO DepotInfo VALUES(" +
				String.valueOf(depotInfo.getIndex()) + ", " +
				"'" + depotInfo.getName() + "', " +
				String.valueOf(depotInfo.getMachineInfo().getIndex()) + ", " +
				String.valueOf(depotInfo.getIndex()) + ", " +
				"'" + depotInfo.getState().toString() + "', " +
				"'" + depotInfo.getUrl().replace("\\", "\\\\") + "'" +
				");";
		}
		else {
			exp = "UPDATE DepotInfo SET " +
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
			return false;
		}
	}
	public boolean updataDataBaseInfo(BasicModels.DataBaseInfo dbInfo) {
		if(dbInfo == null || dbInfo.getMachineInfo() == null) {
			return false;
		}
		String exp = "";
		QueryCondition qc = new QueryCondition();
		qc.setItemName("Index");
		qc.setSign(Sign.EQUAL);
		qc.setValue(String.valueOf(dbInfo.getIndex()));
		BasicModels.DataBaseInfo exists = dbInfo.getIndex() >= 0 ? this.QueryDataBaseInfo(qc) : null;
		if(exists == null) {
			dbInfo.setIndex();
			exp = "INSERT INTO DataBaseInfo VALUES(" +
				String.valueOf(dbInfo.getIndex()) + ", " +
				"'" + dbInfo.getName() + "', " +
				String.valueOf(dbInfo.getMachineInfo().getIndex()) + ", " +
				String.valueOf(dbInfo.getIndex()) + ", " +
				"'" + dbInfo.getType().toString() + "', " +
				"'" + dbInfo.getUrl().replace("\\", "\\\\") + "'" +
				");";
		}
		else {
			exp = "UPDATE DataBaseInfo SET " +
				"`Index` = " + String.valueOf(dbInfo.getIndex()) + ", " +
				"`Name` = '" + dbInfo.getName() + "', " + 
				"`MachineIndex` = " + String.valueOf(dbInfo.getMachineInfo().getIndex()) + ", " +
				"`DepotIndex` = " + String.valueOf(dbInfo.getDepotIndex()) + ", " +
				"`Type` = '" + dbInfo.getType().toString() + "', " +
				"`Url` = '" + dbInfo.getUrl().replace("\\", "\\\\") + "' " + 
				"WHERE " + 
				"`Index` = " + String.valueOf(dbInfo.getIndex()) +
				";";
		}
		try {
			statement.execute("SET SQL_SAFE_UPDATES = 0;");
			statement.executeUpdate(exp);
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean removeFolders(BasicCollections.Folders folders) {
		boolean ok = true;
		for(BasicModels.Folder f : folders.getContent()) {
			ok &= this.removeFolder(f);
		}
		return ok;
	}
	public boolean removeFiles(BasicCollections.BaseFiles files) {
		boolean ok = true;
		for(BasicModels.BaseFile f : files.getContent()) {
			if(f.getType().equals(BasicEnums.FileType.Folder)) {
				ok &= this.removeFolder((BasicModels.Folder)f);
			} else {
				ok &= this.removeFile(f);
			}
		}
		return ok;
	}
	public boolean removeUsers(BasicCollections.Users users) {
		return false;
	}
	public boolean removeInvitations(BasicCollections.Invitations invitations) {
		return false;
	}
	public boolean removeMachineInfos(BasicCollections.MachineInfos machineInfos) {
		return false;
	}
	public boolean removeDepotInfos(BasicCollections.DepotInfos depotInfos) {
		return false;
	}
	public boolean removeDataBaseInfos(BasicCollections.DataBaseInfos dbInfos) {
		return false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean removeFolder(BasicModels.Folder folder) {
		try {
			String exp = "DELETE FROM Folders WHERE `Index` = " + String.valueOf(folder.getIndex()) + ";";
			this.statement.execute(exp);
			return true;
		} catch(Exception e) {
			return true;
		}
	}
	public boolean removeFile(BasicModels.BaseFile file) {
		try {
			String exp = "DELETE FROM Files WHERE `Index` = " + String.valueOf(file.getIndex()) + ";";
			this.statement.execute(exp);
			return true;
		} catch(Exception e) {
			return true;
		}
	}
	public boolean removeUser(BasicModels.User user) {
		return true;
	}
	public boolean removeInvitation(BasicModels.Invitation invitation) {
		try {
			String exp = "DELETE FROM Invitations WHERE `Code` = '" + invitation.getCode() + "';";
			this.statement.execute(exp);
			return true;
		} catch(Exception e) {
			return true;
		}
	}
	public boolean removeMachineInfo(BasicModels.MachineInfo machineInfo) {
		return false;
	}
	public boolean removeDepotInfo(BasicModels.DepotInfo depotInfo) {
		try {
			String exp = "DELETE FROM DepotInfo WHERE `Index` = " + String.valueOf(depotInfo.getIndex()) + ";";
			this.statement.execute(exp);
			return true;
		} catch(Exception e) {
			return true;
		}
	}
	public boolean removeDataBaseInfo(BasicModels.DataBaseInfo dbInfo) {
		try {
			String exp = "DELETE FROM DataBaseInfo WHERE `Index` = " + String.valueOf(dbInfo.getIndex()) + ";";
			this.statement.execute(exp);
			return true;
		} catch(Exception e) {
			return true;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private String queryConditionsToStatement(QueryConditions conditions) {
		if(conditions == null || conditions.size() == 0) {
			return "";
		}
		String con = "`" + conditions.getContent().get(0).getItemName() + "` " +
				conditions.getContent().get(0).getSign().getSignString() + " " +
				conditions.getContent().get(0).getValue();
		
		for(int i=1; i<conditions.size(); i++) {
			QueryCondition qc = conditions.getContent().get(i);
			con += qc.getRelation().toString() + " " +
				"`" + qc.getItemName() + "` " +
				qc.getSign().getSignString() + " " +
				qc.getValue();
		}
		return "WHERE " + con;
	}
	private String queryConditionToStatement(QueryCondition condition) {
		String con = "WHERE `" + condition.getItemName() + "` " + 
				condition.getSign().getSignString() + " " +
				condition.getValue();
		return con;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
