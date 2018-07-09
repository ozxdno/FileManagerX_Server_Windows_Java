package DataBaseManager;

public class TXTManager implements Interfaces.IDBManager {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private BasicModels.DataBaseInfo dbInfo;
	private volatile boolean isConnected;
	private volatile boolean isQueryOK;
	private volatile boolean isUpdataOK;
	
	private BasicCollections.Folders folders;
	private BasicCollections.BaseFiles files;
	private BasicCollections.MachineInfos machineInfos;
	private BasicCollections.DepotInfos depotInfos;
	private BasicCollections.DataBaseInfos dbInfos;
	private BasicCollections.Users users;
	private BasicCollections.Invitations invitations;
	
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
	
	public Interfaces.IDepotChecker getChecker() {
		Interfaces.IDepotChecker dc = Factories.DepotCheckerFactory.createDepotChecker();
		dc.setDBManager(this);
		return dc;
	}
	public Interfaces.IServerChecker getServerChecker() {
		Interfaces.IServerChecker sc = Factories.ServerCheckerFactory.createServerChecker();
		sc.setDBManager(this);
		return sc;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public TXTManager() {
		initThis();
	}
	public TXTManager(BasicModels.DataBaseInfo dbInfo) {
		initThis();
		this.setDBInfo(dbInfo);
	}
	private void initThis() {
		this.isConnected = false;
		this.isQueryOK = false;
		this.isUpdataOK = false;
		this.dbInfo = new  BasicModels.DataBaseInfo();
		
		folders = null;
		files = null;
		//machineInfos = null;
		//depotInfos = null;
		//users = null;
		//invitations = null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean initialize(Object infos) {
		return this.setDBInfo((BasicModels.DataBaseInfo)infos);
	}
	public boolean connect() {
		this.isConnected = true;
		return true;
	}
	public void disconnect() {
		this.isConnected = false;
		this.save();
		
	}
	public boolean save() {
		
		boolean ok = true;
		
		if(this.folders != null) {
			FileModels.Text txt = new FileModels.Text(this.dbInfo.getUrl() + "\\Folders.txt");
			for(BasicModels.Folder f : this.folders.getContent()) {
				txt.getContent().add(f.output());
			}
			ok &= txt.save();
		}
		if(this.files != null) {
			FileModels.Text txt = new FileModels.Text(this.dbInfo.getUrl() + "\\Files.txt");
			for(BasicModels.BaseFile f : this.files.getContent()) {
				txt.getContent().add(f.output());
			}
			ok &= txt.save();
		}
		if(this.machineInfos != null) {
			FileModels.Text txt = new FileModels.Text(this.dbInfo.getUrl() + "\\MachineInfo.txt");
			for(BasicModels.MachineInfo f : this.machineInfos.getContent()) {
				txt.getContent().add(f.output());
			}
			ok &= txt.save();
		}
		if(this.depotInfos != null) {
			FileModels.Text txt = new FileModels.Text(this.dbInfo.getUrl() + "\\DepotInfo.txt");
			for(BasicModels.DepotInfo f : this.depotInfos.getContent()) {
				txt.getContent().add(f.output());
			}
			ok &= txt.save();
		}
		if(this.dbInfos != null) {
			FileModels.Text txt = new FileModels.Text(this.dbInfo.getUrl() + "\\DataBaseInfo.txt");
			for(BasicModels.DataBaseInfo f : this.dbInfos.getContent()) {
				txt.getContent().add(f.output());
			}
			ok &= txt.save();
		}
		if(this.users != null) {
			FileModels.Text txt = new FileModels.Text(this.dbInfo.getUrl() + "\\Users.txt");
			for(BasicModels.User f : this.users.getContent()) {
				txt.getContent().add(f.output());
			}
			ok &= txt.save();
		}
		if(this.invitations != null) {
			FileModels.Text txt = new FileModels.Text(this.dbInfo.getUrl() + "\\Invitations.txt");
			for(BasicModels.Invitation f : this.invitations.getContent()) {
				txt.getContent().add(f.output());
			}
			ok &= txt.save();
		}
		
		return ok;
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
		if(tableName == null || tableName.length() == 0) {
			return false;
		}
		java.io.File f = new java.io.File(dbInfo.getUrl() + "\\" + tableName + ".txt");
		if(f.exists()) {
			return true;
		}
		try {
			return f.createNewFile();
		} catch(Exception e) {
			return false;
		}
	}
	public boolean createTable_Files() {
		return this.createTable("Files", null, null);
	}
	public boolean createTable_Folders() {
		return this.createTable("Folders", null, null);
	}
	public boolean createTable_MachineInfo() {
		return this.createTable("MachineInfo", null, null);
	}
	public boolean createTable_DepotInfo() {
		return this.createTable("DepotInfo", null, null);
	}
	public boolean createTable_DataBaseInfo() {
		return this.createTable("DataBaseInfo", null, null);
	}
	public boolean createTable_Users() {
		return this.createTable("Users", null, null);
	}
	public boolean createTable_Invitations() {
		return this.createTable("Invitations", null, null);
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
		java.io.File f = new java.io.File(dbInfo.getUrl() + "\\" + tableName + ".txt");
		if(!f.exists()) {
			return true;
		}
		try {
			return f.delete();
		} catch(Exception e) {
			BasicEnums.ErrorType.DB_OPERATE_FAILED.register("Table Name = " + tableName, e.toString());
			return false;
		}
	}
	public boolean deleteTable_Files() {
		if(this.files != null) {
			this.files.clear();
		}
		return this.deleteTable("Files");
	}
	public boolean deleteTable_Folders() {
		if(this.folders != null) {
			this.folders.clear();
		}
		return this.deleteTable("Folders");
	}
	public boolean deleteTable_MachineInfo() {
		if(this.machineInfos != null) {
			this.machineInfos.clear();
		}
		return this.deleteTable("MachineInfo");
	}
	public boolean deleteTable_DepotInfo() {
		if(this.depotInfos != null) {
			this.depotInfos.clear();
		}
		return this.deleteTable("DepotInfo");
	}
	public boolean deleteTable_DataBaseInfo() {
		if(this.dbInfos != null) {
			this.dbInfos.clear();
		}
		return this.deleteTable("DataBaseInfo");
	}
	public boolean deleteTable_Users() {
		if(this.users != null) {
			this.users.clear();
		}
		return this.deleteTable("Users");
	}
	public boolean deleteTable_Invitations() {
		if(this.invitations != null) {
			this.invitations.clear();
		}
		return this.deleteTable("Invitations");
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public BasicCollections.Folders QueryFolders(Object conditions) {
		if(conditions == null) {
			return null;
		}
		if(this.folders == null) {
			this.loadFolders();
		}
		
		QueryConditions qcs = new QueryConditions();
		if(conditions instanceof QueryCondition) {
			qcs.add((QueryCondition)conditions);
		}
		else if(conditions instanceof QueryConditions) {
			qcs = (QueryConditions)conditions;
		}
		else if(conditions instanceof String) {
			try {
				qcs.stringToThis((String)conditions);
			}catch(Exception e) {
				BasicEnums.ErrorType.OTHERS.register(e.toString());
				return null;
			}
		}
		else {
			return null;
		}
		if(qcs.size() == 0) {
			return this.folders;
		}
		boolean[] checkedMark = new boolean[this.folders.size()];
		for(int i=0; i<this.folders.size(); i++) {
			checkedMark[i] = true;
		}
		
		for(int i=0; i<qcs.size(); i++) {
			QueryCondition qc = qcs.getContent().get(i);
			if(qc.getItemName().equals("Index")) {
				for(int j=0; j<this.folders.size(); j++) {
					boolean satisfied = this.satisfyLong(folders.getContent().get(j).getIndex(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Father")) {
				for(int j=0; j<this.folders.size(); j++) {
					boolean satisfied = this.satisfyLong(folders.getContent().get(j).getFather(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Machine")) {
				for(int j=0; j<this.folders.size(); j++) {
					boolean satisfied = this.satisfyLong(folders.getContent().get(j).getMachine(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Depot")) {
				for(int j=0; j<this.folders.size(); j++) {
					boolean satisfied = this.satisfyLong(folders.getContent().get(j).getDepot(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("DataBase")) {
				for(int j=0; j<this.folders.size(); j++) {
					boolean satisfied = this.satisfyLong(folders.getContent().get(j).getDataBase(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Url")) {
				for(int j=0; j<this.folders.size(); j++) {
					boolean satisfied = this.satisfyString(folders.getContent().get(j).getUrl(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Type")) {
				for(int j=0; j<this.files.size(); j++) {
					boolean satisfied = this.satisfyString(files.getContent().get(j).getType().toString(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("State")) {
				for(int j=0; j<this.files.size(); j++) {
					boolean satisfied = this.satisfyString(files.getContent().get(j).getState().toString(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Modify")) {
				for(int j=0; j<this.folders.size(); j++) {
					boolean satisfied = this.satisfyLong(folders.getContent().get(j).getModify(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Length")) {
				for(int j=0; j<this.folders.size(); j++) {
					boolean satisfied = this.satisfyLong(folders.getContent().get(j).getLength(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Score")) {
				for(int j=0; j<this.folders.size(); j++) {
					boolean satisfied = this.satisfyLong(folders.getContent().get(j).getScore(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Tags")) {
				for(int j=0; j<this.folders.size(); j++) {
					boolean satisfied = this.satisfyString(folders.getContent().get(j).getTags(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
		}
		
		BasicCollections.Folders res = new BasicCollections.Folders();
		for(int i=0; i<checkedMark.length; i++) {
			if(checkedMark[i]) {
				BasicModels.Folder n = new BasicModels.Folder();
				n.copyValue(this.folders.getContent().get(i));
				res.add(n);
			}
		}
		return res;
	}
	public BasicCollections.BaseFiles QueryFiles(Object conditions) {
		if(conditions == null) {
			return null;
		}
		if(this.files == null) {
			this.loadFiles();
		}
		QueryConditions qcs = new QueryConditions();
		if(conditions instanceof QueryCondition) {
			qcs.add((QueryCondition)conditions);
		}
		else if(conditions instanceof QueryConditions) {
			qcs = (QueryConditions)conditions;
		}
		else if(conditions instanceof String) {
			try {
				qcs.stringToThis((String)conditions);
			}catch(Exception e) {
				BasicEnums.ErrorType.OTHERS.register(e.toString());
				return null;
			}
		}
		else {
			return null;
		}
		if(qcs.size() == 0) {
			return this.files;
		}
		
		boolean[] checkedMark = new boolean[this.files.size()];
		for(int i=0; i<this.files.size(); i++) {
			checkedMark[i] = true;
		}
		
		for(int i=0; i<qcs.size(); i++) {
			QueryCondition qc = qcs.getContent().get(i);
			if(qc.getItemName().equals("Index")) {
				for(int j=0; j<this.files.size(); j++) {
					boolean satisfied = this.satisfyLong(files.getContent().get(j).getIndex(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Father")) {
				for(int j=0; j<this.files.size(); j++) {
					boolean satisfied = this.satisfyLong(files.getContent().get(j).getFather(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Machine")) {
				for(int j=0; j<this.files.size(); j++) {
					boolean satisfied = this.satisfyLong(files.getContent().get(j).getMachine(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Depot")) {
				for(int j=0; j<this.files.size(); j++) {
					boolean satisfied = this.satisfyLong(files.getContent().get(j).getDepot(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("DataBase")) {
				for(int j=0; j<this.files.size(); j++) {
					boolean satisfied = this.satisfyLong(files.getContent().get(j).getDataBase(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Url")) {
				for(int j=0; j<this.files.size(); j++) {
					boolean satisfied = this.satisfyString(files.getContent().get(j).getUrl(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Type")) {
				for(int j=0; j<this.files.size(); j++) {
					boolean satisfied = this.satisfyString(files.getContent().get(j).getType().toString(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("State")) {
				for(int j=0; j<this.files.size(); j++) {
					boolean satisfied = this.satisfyString(files.getContent().get(j).getState().toString(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Modify")) {
				for(int j=0; j<this.files.size(); j++) {
					boolean satisfied = this.satisfyLong(files.getContent().get(j).getModify(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Length")) {
				for(int j=0; j<this.files.size(); j++) {
					boolean satisfied = this.satisfyLong(files.getContent().get(j).getLength(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Score")) {
				for(int j=0; j<this.files.size(); j++) {
					boolean satisfied = this.satisfyLong(files.getContent().get(j).getScore(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Tags")) {
				for(int j=0; j<this.files.size(); j++) {
					boolean satisfied = this.satisfyString(files.getContent().get(j).getTags(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
		}
		
		BasicCollections.BaseFiles res = new BasicCollections.BaseFiles();
		for(int i=0; i<checkedMark.length; i++) {
			if(checkedMark[i]) {
				BasicModels.BaseFile n = new BasicModels.BaseFile();
				n.copyValue(this.files.getContent().get(i));
				res.add(n);
			}
		}
		return res;
	}
	public BasicCollections.Users QueryUsers(Object conditions) {
		if(conditions == null) {
			return null;
		}
		if(this.users == null) {
			this.loadUsers();
		}
		QueryConditions qcs = new QueryConditions();
		if(conditions instanceof QueryCondition) {
			qcs.add((QueryCondition)conditions);
		}
		else if(conditions instanceof QueryConditions) {
			qcs = (QueryConditions)conditions;
		}
		else if(conditions instanceof String) {
			try {
				qcs.stringToThis((String)conditions);
			}catch(Exception e) {
				BasicEnums.ErrorType.OTHERS.register(e.toString());
				return null;
			}
		}
		else {
			return null;
		}
		if(qcs.size() == 0) {
			return this.users;
		}
		
		boolean[] checkedMark = new boolean[this.users.size()];
		for(int i=0; i<this.users.size(); i++) {
			checkedMark[i] = true;
		}
		for(int i=0; i<qcs.size(); i++) {
			QueryCondition qc = qcs.getContent().get(i);
			if(qc.getItemName().equals("Index")) {
				for(int j=0; j<this.users.size(); j++) {
					boolean satisfied = this.satisfyLong(users.getContent().get(j).getIndex(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("LoginName")) {
				for(int j=0; j<this.users.size(); j++) {
					boolean satisfied = this.satisfyString(users.getContent().get(j).getLoginName(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("NickName")) {
				for(int j=0; j<this.users.size(); j++) {
					boolean satisfied = this.satisfyString(users.getContent().get(j).getNickName(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Password")) {
				for(int j=0; j<this.users.size(); j++) {
					boolean satisfied = this.satisfyString(users.getContent().get(j).getPassword(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Email")) {
				for(int j=0; j<this.users.size(); j++) {
					boolean satisfied = this.satisfyString(users.getContent().get(j).getEmail(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Phone")) {
				for(int j=0; j<this.users.size(); j++) {
					boolean satisfied = this.satisfyString(users.getContent().get(j).getPhone(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Priority")) {
				for(int j=0; j<this.users.size(); j++) {
					boolean satisfied = this.satisfyString(users.getContent().get(j).getPriority().toString(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Level")) {
				for(int j=0; j<this.users.size(); j++) {
					boolean satisfied = this.satisfyString(users.getContent().get(j).getLevel().toString(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("State")) {
				for(int j=0; j<this.users.size(); j++) {
					boolean satisfied = this.satisfyString(users.getContent().get(j).getState().toString(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Experience")) {
				for(int j=0; j<this.users.size(); j++) {
					boolean satisfied = this.satisfyLong(users.getContent().get(j).getExperience(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("PhotoUrl")) {
				for(int j=0; j<this.users.size(); j++) {
					boolean satisfied = this.satisfyString(users.getContent().get(j).getPhotoUrl(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Coins")) {
				for(int j=0; j<this.users.size(); j++) {
					boolean satisfied = this.satisfyLong(users.getContent().get(j).getCoins(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Money")) {
				for(int j=0; j<this.users.size(); j++) {
					boolean satisfied = this.satisfyDouble(users.getContent().get(j).getMoney(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
		}
		
		BasicCollections.Users res = new BasicCollections.Users();
		for(int i=0; i<checkedMark.length; i++) {
			if(checkedMark[i]) {
				BasicModels.User n = new BasicModels.User();
				n.copyValue(this.users.getContent().get(i));
				res.add(n);
			}
		}
		return res;
	}
	public BasicCollections.Invitations QueryInvitations(Object conditions) {
		if(conditions == null) {
			return null;
		}
		if(this.invitations == null) {
			this.loadInvitations();
		}
		QueryConditions qcs = new QueryConditions();
		if(conditions instanceof QueryCondition) {
			qcs.add((QueryCondition)conditions);
		}
		else if(conditions instanceof QueryConditions) {
			qcs = (QueryConditions)conditions;
		}
		else if(conditions instanceof String) {
			try {
				qcs.stringToThis((String)conditions);
			}catch(Exception e) {
				BasicEnums.ErrorType.OTHERS.register(e.toString());
				return null;
			}
		}
		else {
			return null;
		}
		if(qcs.size() == 0) {
			return this.invitations;
		}
		
		boolean[] checkedMark = new boolean[this.invitations.size()];
		for(int i=0; i<this.invitations.size(); i++) {
			checkedMark[i] = true;
		}
		for(int i=0; i<qcs.size(); i++) {
			QueryCondition qc = qcs.getContent().get(i);
			if(qc.getItemName().equals("Code")) {
				for(int j=0; j<this.invitations.size(); j++) {
					boolean satisfied = this.satisfyString(invitations.getContent().get(j).getCode(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Priority")) {
				for(int j=0; j<this.invitations.size(); j++) {
					boolean satisfied = this.satisfyString(invitations.getContent().get(j).getUser().getPriority().toString(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Level")) {
				for(int j=0; j<this.invitations.size(); j++) {
					boolean satisfied = this.satisfyString(invitations.getContent().get(j).getUser().getLevel().toString(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Experience")) {
				for(int j=0; j<this.invitations.size(); j++) {
					boolean satisfied = this.satisfyLong(invitations.getContent().get(j).getUser().getExperience(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Coins")) {
				for(int j=0; j<this.invitations.size(); j++) {
					boolean satisfied = this.satisfyLong(invitations.getContent().get(j).getUser().getCoins(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Money")) {
				for(int j=0; j<this.invitations.size(); j++) {
					boolean satisfied = this.satisfyDouble(invitations.getContent().get(j).getUser().getMoney(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
		}
		
		BasicCollections.Invitations res = new BasicCollections.Invitations();
		for(int i=0; i<checkedMark.length; i++) {
			if(checkedMark[i]) {
				BasicModels.Invitation n = new BasicModels.Invitation();
				n.copyValue(this.invitations.getContent().get(i));
				res.add(n);
			}
		}
		return res;
	}
	public BasicCollections.MachineInfos QueryMachineInfos(Object conditions) {
		if(conditions == null) {
			return null;
		}
		if(this.machineInfos == null) {
			this.loadMachineInfos();
		}
		QueryConditions qcs = new QueryConditions();
		if(conditions instanceof QueryCondition) {
			qcs.add((QueryCondition)conditions);
		}
		else if(conditions instanceof QueryConditions) {
			qcs = (QueryConditions)conditions;
		}
		else if(conditions instanceof String) {
			try {
				qcs.stringToThis((String)conditions);
			}catch(Exception e) {
				BasicEnums.ErrorType.OTHERS.register(e.toString());
				return null;
			}
		}
		else {
			return null;
		}
		if(qcs.size() == 0) {
			return this.machineInfos;
		}
		
		boolean[] checkedMark = new boolean[this.machineInfos.size()];
		for(int i=0; i<this.machineInfos.size(); i++) {
			checkedMark[i] = true;
		}
		for(int i=0; i<qcs.size(); i++) {
			QueryCondition qc = qcs.getContent().get(i);
			if(qc.getItemName().equals("Index")) {
				for(int j=0; j<this.machineInfos.size(); j++) {
					boolean satisfied = this.satisfyLong(machineInfos.getContent().get(j).getIndex(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Name")) {
				for(int j=0; j<this.machineInfos.size(); j++) {
					boolean satisfied = this.satisfyString(machineInfos.getContent().get(j).getName(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("IP")) {
				for(int j=0; j<this.machineInfos.size(); j++) {
					boolean satisfied = this.satisfyString(machineInfos.getContent().get(j).getIp(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Port")) {
				for(int j=0; j<this.machineInfos.size(); j++) {
					boolean satisfied = this.satisfyInteger(machineInfos.getContent().get(j).getPort(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
		}
		
		BasicCollections.MachineInfos res = new BasicCollections.MachineInfos();
		for(int i=0; i<checkedMark.length; i++) {
			if(checkedMark[i]) {
				BasicModels.MachineInfo n = new BasicModels.MachineInfo();
				n.copyValue(this.machineInfos.getContent().get(i));
				res.add(n);
			}
		}
		return res;
	}
	public BasicCollections.DepotInfos QueryDepotInfos(Object conditions) {
		if(conditions == null) {
			return null;
		}
		if(this.depotInfos == null) {
			this.loadMachineInfos();
		}
		QueryConditions qcs = new QueryConditions();
		if(conditions instanceof QueryCondition) {
			qcs.add((QueryCondition)conditions);
		}
		else if(conditions instanceof QueryConditions) {
			qcs = (QueryConditions)conditions;
		}
		else if(conditions instanceof String) {
			try {
				qcs.stringToThis((String)conditions);
			}catch(Exception e) {
				BasicEnums.ErrorType.OTHERS.register(e.toString());
				return null;
			}
		}
		else {
			return null;
		}
		if(qcs.size() == 0) {
			return this.depotInfos;
		}
		
		boolean[] checkedMark = new boolean[this.depotInfos.size()];
		for(int i=0; i<this.depotInfos.size(); i++) {
			checkedMark[i] = true;
		}
		for(int i=0; i<qcs.size(); i++) {
			QueryCondition qc = qcs.getContent().get(i);
			if(qc.getItemName().equals("Index")) {
				for(int j=0; j<this.depotInfos.size(); j++) {
					boolean satisfied = this.satisfyLong(depotInfos.getContent().get(j).getIndex(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Name")) {
				for(int j=0; j<this.depotInfos.size(); j++) {
					boolean satisfied = this.satisfyString(depotInfos.getContent().get(j).getName(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("MachineIndex")) {
				for(int j=0; j<this.depotInfos.size(); j++) {
					boolean satisfied = this.satisfyLong(depotInfos.getContent().get(j).getMachineInfo().getIndex(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("DataBaseIndex")) {
				for(int j=0; j<this.depotInfos.size(); j++) {
					boolean satisfied = this.satisfyLong(depotInfos.getContent().get(j).getDBIndex(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("State")) {
				for(int j=0; j<this.depotInfos.size(); j++) {
					boolean satisfied = this.satisfyString(depotInfos.getContent().get(j).getState().toString(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Url")) {
				for(int j=0; j<this.depotInfos.size(); j++) {
					boolean satisfied = this.satisfyString(depotInfos.getContent().get(j).getUrl(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
		}
		
		BasicCollections.DepotInfos res = new BasicCollections.DepotInfos();
		for(int i=0; i<checkedMark.length; i++) {
			if(checkedMark[i]) {
				BasicModels.DepotInfo n = new BasicModels.DepotInfo();
				n.copyValue(this.depotInfos.getContent().get(i));
				res.add(n);
			}
		}
		return res;
	}
	public BasicCollections.DataBaseInfos QueryDataBaseInfos(Object conditions) {
		if(conditions == null) {
			return null;
		}
		if(this.dbInfos == null) {
			this.loadDataBaseInfos();
		}
		QueryConditions qcs = new QueryConditions();
		if(conditions instanceof QueryCondition) {
			qcs.add((QueryCondition)conditions);
		}
		else if(conditions instanceof QueryConditions) {
			qcs = (QueryConditions)conditions;
		}
		else if(conditions instanceof String) {
			try {
				qcs.stringToThis((String)conditions);
			}catch(Exception e) {
				BasicEnums.ErrorType.OTHERS.register(e.toString());
				return null;
			}
		}
		else {
			return null;
		}
		if(qcs.size() == 0) {
			return this.dbInfos;
		}
		
		boolean[] checkedMark = new boolean[this.dbInfos.size()];
		for(int i=0; i<this.dbInfos.size(); i++) {
			checkedMark[i] = true;
		}
		for(int i=0; i<qcs.size(); i++) {
			QueryCondition qc = qcs.getContent().get(i);
			if(qc.getItemName().equals("Index")) {
				for(int j=0; j<this.dbInfos.size(); j++) {
					boolean satisfied = this.satisfyLong(dbInfos.getContent().get(j).getIndex(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Name")) {
				for(int j=0; j<this.dbInfos.size(); j++) {
					boolean satisfied = this.satisfyString(dbInfos.getContent().get(j).getName(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("MachineIndex")) {
				for(int j=0; j<this.dbInfos.size(); j++) {
					boolean satisfied = this.satisfyLong(dbInfos.getContent().get(j).getMachineInfo().getIndex(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("DepotIndex")) {
				for(int j=0; j<this.dbInfos.size(); j++) {
					boolean satisfied = this.satisfyLong(dbInfos.getContent().get(j).getDepotIndex(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Type")) {
				for(int j=0; j<this.dbInfos.size(); j++) {
					boolean satisfied = this.satisfyString(dbInfos.getContent().get(j).getType().toString(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Url")) {
				for(int j=0; j<this.dbInfos.size(); j++) {
					boolean satisfied = this.satisfyString(dbInfos.getContent().get(j).getUrl(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
		}
		
		BasicCollections.DataBaseInfos res = new BasicCollections.DataBaseInfos();
		for(int i=0; i<checkedMark.length; i++) {
			if(checkedMark[i]) {
				BasicModels.DataBaseInfo n = new BasicModels.DataBaseInfo();
				n.copyValue(this.dbInfos.getContent().get(i));
				res.add(n);
			}
		}
		return res;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public BasicModels.Folder QueryFolder(Object conditions) {
		if(this.folders == null) {
			this.loadFolders();
		}
		if(conditions == null) {
			return null;
		}
		QueryConditions qcs = new QueryConditions();
		if(conditions instanceof QueryCondition) {
			qcs.add((QueryCondition)conditions);
		}
		else if(conditions instanceof QueryConditions) {
			qcs = (QueryConditions)conditions;
		}
		else if(conditions instanceof String) {
			try {
				qcs.stringToThis((String)conditions);
			}catch(Exception e) {
				BasicEnums.ErrorType.OTHERS.register(e.toString());
				return null;
			}
		}
		else {
			return null;
		}
		if(qcs.size() == 0) {
			if(this.folders.size() == 0) {
				return null;
			}
			return this.folders.getContent().get(0);
		}
		
		for(BasicModels.Folder f : this.folders.getContent()) {
			boolean ok = true;
			for(QueryCondition qc : qcs.getContent()) {
				if(qc.getItemName().equals("Index")) {
					boolean satisfied = this.satisfyLong(f.getIndex(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Father")) {
					boolean satisfied = this.satisfyLong(f.getFather(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Machine")) {
					boolean satisfied = this.satisfyLong(f.getMachine(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Depot")) {
					boolean satisfied = this.satisfyLong(f.getDepot(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("DataBase")) {
					boolean satisfied = this.satisfyLong(f.getDataBase(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Url")) {
					boolean satisfied = this.satisfyString(f.getUrl(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Type")) {
					boolean satisfied = this.satisfyString(f.getType().toString(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("State")) {
					boolean satisfied = this.satisfyString(f.getState().toString(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Modify")) {
					boolean satisfied = this.satisfyLong(f.getModify(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Length")) {
					boolean satisfied = this.satisfyLong(f.getLength(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Score")) {
					boolean satisfied = this.satisfyInteger(f.getScore(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Tags")) {
					boolean satisfied = this.satisfyString(f.getTags(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
			}
			
			if(ok) {
				BasicModels.Folder r = new BasicModels.Folder();
				r.copyValue(f);
				return r;
			}
		}
		
		return null;
	}
	public BasicModels.BaseFile QueryFile(Object conditions) {
		if(this.files == null) {
			this.loadFiles();
		}
		if(conditions == null) {
			return null;
		}
		QueryConditions qcs = new QueryConditions();
		if(conditions instanceof QueryCondition) {
			qcs.add((QueryCondition)conditions);
		}
		else if(conditions instanceof QueryConditions) {
			qcs = (QueryConditions)conditions;
		}
		else if(conditions instanceof String) {
			try {
				qcs.stringToThis((String)conditions);
			}catch(Exception e) {
				BasicEnums.ErrorType.OTHERS.register(e.toString());
				return null;
			}
		}
		else {
			return null;
		}
		if(qcs.size() == 0) {
			if(this.files.size() == 0) {
				return null;
			}
			return this.files.getContent().get(0);
		}
		
		for(BasicModels.BaseFile f : this.files.getContent()) {
			boolean ok = true;
			for(QueryCondition qc : qcs.getContent()) {
				if(qc.getItemName().equals("Index")) {
					boolean satisfied = this.satisfyLong(f.getIndex(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Father")) {
					boolean satisfied = this.satisfyLong(f.getFather(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Machine")) {
					boolean satisfied = this.satisfyLong(f.getMachine(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Depot")) {
					boolean satisfied = this.satisfyLong(f.getDepot(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("DataBase")) {
					boolean satisfied = this.satisfyLong(f.getDataBase(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Url")) {
					boolean satisfied = this.satisfyString(f.getUrl(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Type")) {
					boolean satisfied = this.satisfyString(f.getType().toString(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("State")) {
					boolean satisfied = this.satisfyString(f.getState().toString(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Modify")) {
					boolean satisfied = this.satisfyLong(f.getModify(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Length")) {
					boolean satisfied = this.satisfyLong(f.getLength(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Score")) {
					boolean satisfied = this.satisfyInteger(f.getScore(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Tags")) {
					boolean satisfied = this.satisfyString(f.getTags(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
			}
			
			if(ok) {
				BasicModels.BaseFile r = new BasicModels.BaseFile();
				r.copyValue(f);
				return r;
			}
		}
		
		return null;
	}
	public BasicModels.User QueryUser(Object conditions) {
		if(this.users == null) {
			this.loadUsers();
		}
		if(conditions == null) {
			return null;
		}
		QueryConditions qcs = new QueryConditions();
		if(conditions instanceof QueryCondition) {
			qcs.add((QueryCondition)conditions);
		}
		else if(conditions instanceof QueryConditions) {
			qcs = (QueryConditions)conditions;
		}
		else if(conditions instanceof String) {
			try {
				qcs.stringToThis((String)conditions);
			}catch(Exception e) {
				BasicEnums.ErrorType.OTHERS.register(e.toString());
				return null;
			}
		}
		else {
			return null;
		}
		if(qcs.size() == 0) {
			if(this.users.size() == 0) {
				return null;
			}
			return this.users.getContent().get(0);
		}
		
		for(BasicModels.User ie : this.users.getContent()) {
			boolean ok = true;
			for(QueryCondition qc : qcs.getContent()) {
				if(qc.getItemName().equals("Index")) {
					boolean satisfied = this.satisfyLong(ie.getIndex(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("LoginName")) {
					boolean satisfied = this.satisfyString(ie.getLoginName(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("NickName")) {
					boolean satisfied = this.satisfyString(ie.getNickName(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Password")) {
					boolean satisfied = this.satisfyString(ie.getPassword(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Email")) {
					boolean satisfied = this.satisfyString(ie.getEmail(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Phone")) {
					boolean satisfied = this.satisfyString(ie.getPhone(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("State")) {
					boolean satisfied = this.satisfyString(ie.getState().toString(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Priority")) {
					boolean satisfied = this.satisfyString(ie.getPriority().toString(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Level")) {
					boolean satisfied = this.satisfyString(ie.getLevel().toString(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Experience")) {
					boolean satisfied = this.satisfyLong(ie.getExperience(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("PhotoUrl")) {
					boolean satisfied = this.satisfyString(ie.getPhotoUrl(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Coins")) {
					boolean satisfied = this.satisfyLong(ie.getCoins(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Money")) {
					boolean satisfied = this.satisfyDouble(ie.getMoney(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
			}
			
			if(ok) {
				BasicModels.User r = new BasicModels.User();
				r.copyValue(ie);
				return r;
			}
		}
		
		return null;
	}
	public BasicModels.Invitation QueryInvitation(Object conditions) {
		if(this.invitations == null) {
			this.loadInvitations();
		}
		if(conditions == null) {
			return null;
		}
		QueryConditions qcs = new QueryConditions();
		if(conditions instanceof QueryCondition) {
			qcs.add((QueryCondition)conditions);
		}
		else if(conditions instanceof QueryConditions) {
			qcs = (QueryConditions)conditions;
		}
		else if(conditions instanceof String) {
			try {
				qcs.stringToThis((String)conditions);
			}catch(Exception e) {
				BasicEnums.ErrorType.OTHERS.register(e.toString());
				return null;
			}
		}
		else {
			return null;
		}
		if(qcs.size() == 0) {
			if(this.invitations.size() == 0) {
				return null;
			}
			return this.invitations.getContent().get(0);
		}
		
		for(BasicModels.Invitation ie : this.invitations.getContent()) {
			boolean ok = true;
			for(QueryCondition qc : qcs.getContent()) {
				if(qc.getItemName().equals("Code")) {
					boolean satisfied = this.satisfyString(ie.getCode(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Priority")) {
					boolean satisfied = this.satisfyString(ie.getUser().getPriority().toString(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Level")) {
					boolean satisfied = this.satisfyString(ie.getUser().getLevel().toString(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Experience")) {
					boolean satisfied = this.satisfyLong(ie.getUser().getExperience(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Coins")) {
					boolean satisfied = this.satisfyLong(ie.getUser().getCoins(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Money")) {
					boolean satisfied = this.satisfyDouble(ie.getUser().getMoney(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
			}
			
			if(ok) {
				BasicModels.Invitation r = new BasicModels.Invitation();
				r.copyValue(ie);
				return r;
			}
		}
		
		return null;
	}
	public BasicModels.MachineInfo QueryMachineInfo(Object conditions) {
		if(this.machineInfos == null) {
			this.loadMachineInfos();
		}
		if(conditions == null) {
			return null;
		}
		QueryConditions qcs = new QueryConditions();
		if(conditions instanceof QueryCondition) {
			qcs.add((QueryCondition)conditions);
		}
		else if(conditions instanceof QueryConditions) {
			qcs = (QueryConditions)conditions;
		}
		else if(conditions instanceof String) {
			try {
				qcs.stringToThis((String)conditions);
			}catch(Exception e) {
				BasicEnums.ErrorType.OTHERS.register(e.toString());
				return null;
			}
		}
		else {
			return null;
		}
		if(qcs.size() == 0) {
			if(this.machineInfos.size() == 0) {
				return null;
			}
			return this.machineInfos.getContent().get(0);
		}
		
		for(BasicModels.MachineInfo ie : this.machineInfos.getContent()) {
			boolean ok = true;
			for(QueryCondition qc : qcs.getContent()) {
				if(qc.getItemName().equals("Index")) {
					boolean satisfied = this.satisfyLong(ie.getIndex(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Name")) {
					boolean satisfied = this.satisfyString(ie.getName(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("IP")) {
					boolean satisfied = this.satisfyString(ie.getIp(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Port")) {
					boolean satisfied = this.satisfyInteger(ie.getPort(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
			}
			
			if(ok) {
				BasicModels.MachineInfo r = new BasicModels.MachineInfo();
				r.copyValue(ie);
				return r;
			}
		}
		
		return null;
	}
	public BasicModels.DepotInfo QueryDepotInfo(Object conditions) {
		if(this.depotInfos == null) {
			this.loadDepotInfos();
		}
		if(conditions == null) {
			return null;
		}
		QueryConditions qcs = new QueryConditions();
		if(conditions instanceof QueryCondition) {
			qcs.add((QueryCondition)conditions);
		}
		else if(conditions instanceof QueryConditions) {
			qcs = (QueryConditions)conditions;
		}
		else if(conditions instanceof String) {
			try {
				qcs.stringToThis((String)conditions);
			}catch(Exception e) {
				BasicEnums.ErrorType.OTHERS.register(e.toString());
				return null;
			}
		}
		else {
			return null;
		}
		if(qcs.size() == 0) {
			if(this.depotInfos.size() == 0) {
				return null;
			}
			return this.depotInfos.getContent().get(0);
		}
		
		for(BasicModels.DepotInfo ie : this.depotInfos.getContent()) {
			boolean ok = true;
			for(QueryCondition qc : qcs.getContent()) {
				if(qc.getItemName().equals("Index")) {
					boolean satisfied = this.satisfyLong(ie.getIndex(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Name")) {
					boolean satisfied = this.satisfyString(ie.getName(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("MachineIndex")) {
					boolean satisfied = this.satisfyLong(ie.getMachineInfo().getIndex(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("DataBaseIndex")) {
					boolean satisfied = this.satisfyLong(ie.getDBIndex(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("State")) {
					boolean satisfied = this.satisfyString(ie.getState().toString(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Url")) {
					boolean satisfied = this.satisfyString(ie.getUrl(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
			}
			
			if(ok) {
				BasicModels.DepotInfo r = new BasicModels.DepotInfo();
				r.copyValue(ie);
				return r;
			}
		}
		
		return null;
	}
	public BasicModels.DataBaseInfo QueryDataBaseInfo(Object conditions) {
		if(this.dbInfos == null) {
			this.loadDataBaseInfos();
		}
		if(conditions == null) {
			return null;
		}
		QueryConditions qcs = new QueryConditions();
		if(conditions instanceof QueryCondition) {
			qcs.add((QueryCondition)conditions);
		}
		else if(conditions instanceof QueryConditions) {
			qcs = (QueryConditions)conditions;
		}
		else if(conditions instanceof String) {
			try {
				qcs.stringToThis((String)conditions);
			}catch(Exception e) {
				BasicEnums.ErrorType.OTHERS.register(e.toString());
				return null;
			}
		}
		else {
			return null;
		}
		if(qcs.size() == 0) {
			if(this.dbInfos.size() == 0) {
				return null;
			}
			return this.dbInfos.getContent().get(0);
		}
		
		for(BasicModels.DataBaseInfo ie : this.dbInfos.getContent()) {
			boolean ok = true;
			for(QueryCondition qc : qcs.getContent()) {
				if(qc.getItemName().equals("Index")) {
					boolean satisfied = this.satisfyLong(ie.getIndex(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Name")) {
					boolean satisfied = this.satisfyString(ie.getName(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("MachineIndex")) {
					boolean satisfied = this.satisfyLong(ie.getMachineInfo().getIndex(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("DepotIndex")) {
					boolean satisfied = this.satisfyLong(ie.getDepotIndex(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Type")) {
					boolean satisfied = this.satisfyString(ie.getType().toString(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Url")) {
					boolean satisfied = this.satisfyString(ie.getUrl(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
			}
			
			if(ok) {
				BasicModels.DataBaseInfo r = new BasicModels.DataBaseInfo();
				r.copyValue(ie);
				return r;
			}
		}
		
		return null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean updataFolders(BasicCollections.Folders folders) {
		if(this.folders == null) {
			this.loadFolders();
		}
		boolean ok = true;
		for(BasicModels.Folder f : folders.getContent()) {
			ok &= this.updataFolder(f);
		}
		return ok;
	}
	public boolean updataFiles(BasicCollections.BaseFiles files) {
		if(this.files == null) {
			this.loadFiles();
		}
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
		if(this.users == null) {
			this.loadUsers();
		}
		boolean ok = true;
		for(BasicModels.User ie : users.getContent()) {
			ok &= this.updataUser(ie);
		}
		return ok;
	}
	public boolean updataInvitations(BasicCollections.Invitations invitations) {
		if(this.invitations == null) {
			this.loadInvitations();
		}
		boolean ok = true;
		for(BasicModels.Invitation ie : invitations.getContent()) {
			ok &= this.updataInvitation(ie);
		}
		return ok;
	}
	public boolean updataMachineInfos(BasicCollections.MachineInfos machineInfos) {
		if(this.machineInfos == null) {
			this.loadMachineInfos();
		}
		boolean ok = true;
		for(BasicModels.MachineInfo ie : machineInfos.getContent()) {
			ok &= this.updataMachineInfo(ie);
		}
		return ok;
	}
	public boolean updataDepotInfos(BasicCollections.DepotInfos depotInfos) {
		if(this.depotInfos == null) {
			this.loadDepotInfos();
		}
		boolean ok = true;
		for(BasicModels.DepotInfo ie : depotInfos.getContent()) {
			ok &= this.updataDepotInfo(ie);
		}
		return ok;
	}
	public boolean updataDataBaseInfos(BasicCollections.DataBaseInfos dbInfos) {
		if(this.dbInfos == null) {
			this.loadDataBaseInfos();
		}
		boolean ok = true;
		for(BasicModels.DataBaseInfo ie : dbInfos.getContent()) {
			ok &= this.updataDataBaseInfo(ie);
		}
		return ok;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public synchronized boolean updataFolder(BasicModels.Folder folder) {
		if(this.folders == null) {
			this.loadFolders();
		}
		
		int index = (folder.getIndex() >= 0 && folder.getIndex() <= Globals.Configurations.Next_FileIndex) ?
				this.folders.indexOf(folder.getIndex()) :
				-1;
		
		if(index >= 0) {
			this.folders.getContent().set(index, folder);
		} else {
			folder.setIndex();
			BasicModels.Folder n = new BasicModels.Folder();
			n.copyValue(folder);
			this.folders.add(n);
		}
		
		return true;
	}
	public synchronized boolean updataFile(BasicModels.BaseFile file) {
		if(this.files == null) {
			this.loadFiles();
		}
		
		int index = (file.getIndex() >= 0 && file.getIndex() <= Globals.Configurations.Next_FileIndex) ?
				this.files.indexOf(file.getIndex()) :
				-1;
		
		if(index >= 0) {
			this.files.getContent().set(index, file);
		} else {
			file.setIndex();
			BasicModels.BaseFile n = new BasicModels.BaseFile();
			n.copyValue(file);
			this.files.add(n);
		}
		
		return true;
	}
	public synchronized boolean updataUser(BasicModels.User user) {
		if(this.users == null) {
			this.loadUsers();
		}
		
		int index = (user.getIndex() >= 0 && user.getIndex() <= Globals.Configurations.Next_UserIndex) ?
				this.users.indexOf(user.getIndex()) :
				-1;
		
		if(index >= 0) {
			this.users.getContent().set(index, user);
		} else {
			user.setIndex();
			BasicModels.User n = new BasicModels.User();
			n.copyValue(user);
			this.users.add(n);
		}
		
		return true;
	}
	public synchronized boolean updataInvitation(BasicModels.Invitation invitation) {
		if(this.invitations == null) {
			this.loadInvitations();
		}
		
		int index = (invitation.getCode() != null && invitation.getCode().length() >= 0) ?
				this.invitations.indexOf(invitation.getCode()) :
				-1;
		
		if(index >= 0) {
			this.invitations.getContent().set(index, invitation);
		} else {
			
			BasicModels.Invitation n = new BasicModels.Invitation();
			n.copyValue(invitation);
			
			this.invitations.add(n);
		}
		
		return true;
	}
	public synchronized boolean updataMachineInfo(BasicModels.MachineInfo machineInfo) {
		if(this.machineInfos == null) {
			this.loadMachineInfos();
		}
		
		int index = (machineInfo.getIndex() >= 0 && machineInfo.getIndex() <= Globals.Configurations.Next_MachineIndex) ?
				this.machineInfos.indexOf(machineInfo.getIndex()) :
				-1;
		
		if(index >= 0) {
			this.machineInfos.getContent().set(index, machineInfo);
		} else {
			machineInfo.setIndex();
			BasicModels.MachineInfo n = new BasicModels.MachineInfo();
			n.copyValue(machineInfo);
			
			this.machineInfos.add(n);
		}
		
		return true;
	}
	public synchronized boolean updataDepotInfo(BasicModels.DepotInfo depotInfo) {
		if(this.depotInfos == null) {
			this.loadDepotInfos();
		}
		
		int index = (depotInfo.getIndex() >= 0 && depotInfo.getIndex() <= Globals.Configurations.Next_DepotIndex) ?
				this.depotInfos.indexOf(depotInfo.getIndex()) :
				-1;
		
		if(index >= 0) {
			this.depotInfos.getContent().set(index, depotInfo);
		} else {
			depotInfo.setIndex();
			BasicModels.DepotInfo n = new BasicModels.DepotInfo();
			n.copyValue(depotInfo);
			this.depotInfos.add(n);
		}
		
		return true;
	}
	public synchronized boolean updataDataBaseInfo(BasicModels.DataBaseInfo dbInfo) {
		if(this.dbInfos == null) {
			this.loadDataBaseInfos();
		}

		int index = (dbInfo.getIndex() >= 0 && dbInfo.getIndex() <= Globals.Configurations.Next_DataBaseIndex) ?
				this.dbInfos.indexOf(dbInfo.getIndex()) :
				-1;
		
		if(index >= 0) {
			this.dbInfos.getContent().set(index, dbInfo);
		} else {
			dbInfo.setIndex();
			BasicModels.DataBaseInfo n = new BasicModels.DataBaseInfo();
			n.copyValue(dbInfo);
			this.dbInfos.add(n);
		}
		
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean removeFolders(BasicCollections.Folders folders) {
		if(this.folders == null) {
			this.loadFolders();
		}
		boolean ok = true;
		for(BasicModels.Folder f : folders.getContent()) {
			ok &= this.removeFolder(f);
		}
		return ok;
	}
	public boolean removeFiles(BasicCollections.BaseFiles files) {
		if(this.files == null) {
			this.loadFiles();
		}
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
		if(this.users == null) {
			this.loadUsers();
		}
		boolean ok = true;
		for(BasicModels.User ie : users.getContent()) {
			ok &= this.removeUser(ie);
		}
		return ok;
	}
	public boolean removeInvitations(BasicCollections.Invitations invitations) {
		if(this.invitations == null) {
			this.loadInvitations();
		}
		boolean ok = true;
		for(BasicModels.Invitation ie : invitations.getContent()) {
			ok &= this.removeInvitation(ie);
		}
		return ok;
	}
	public boolean removeMachineInfos(BasicCollections.MachineInfos machineInfos) {
		if(this.machineInfos == null) {
			this.loadMachineInfos();
		}
		boolean ok = true;
		for(BasicModels.MachineInfo ie : machineInfos.getContent()) {
			ok &= this.removeMachineInfo(ie);
		}
		return ok;
	}
	public boolean removeDepotInfos(BasicCollections.DepotInfos depotInfos) {
		if(this.depotInfos == null) {
			this.loadDepotInfos();
		}
		boolean ok = true;
		for(BasicModels.DepotInfo ie : depotInfos.getContent()) {
			ok &= this.removeDepotInfo(ie);
		}
		return ok;
	}
	public boolean removeDataBaseInfos(BasicCollections.DataBaseInfos dbInfos) {
		if(this.dbInfos == null) {
			this.loadDataBaseInfos();
		}
		boolean ok = true;
		for(BasicModels.DataBaseInfo ie : dbInfos.getContent()) {
			ok &= this.removeDataBaseInfo(ie);
		}
		return ok;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean removeFolder(BasicModels.Folder folder) {
		if(this.folders == null) {
			this.loadFolders();
		}
		this.folders.delete(folder.getIndex());
		return true;
	}
	public boolean removeFile(BasicModels.BaseFile file) {
		if(this.files == null) {
			this.loadFiles();
		}
		this.files.delete(file.getIndex());
		return true;
	}
	public boolean removeUser(BasicModels.User user) {
		if(this.users == null) {
			this.loadUsers();
		}
		this.users.delete(user.getIndex());
		return true;
	}
	public boolean removeInvitation(BasicModels.Invitation invitation) {
		if(this.invitations == null) {
			this.loadInvitations();
		}
		this.invitations.delete(invitation.getCode());
		return true;
	}
	public boolean removeMachineInfo(BasicModels.MachineInfo machineInfo) {
		if(this.machineInfos == null) {
			this.loadMachineInfos();
		}
		this.machineInfos.delete(machineInfo.getIndex());
		return true;
	}
	public boolean removeDepotInfo(BasicModels.DepotInfo depotInfo) {
		if(this.depotInfos == null) {
			this.loadDepotInfos();
		}
		this.depotInfos.delete(depotInfo.getIndex());
		return true;
	}
	public boolean removeDataBaseInfo(BasicModels.DataBaseInfo dbInfo) {
		if(this.dbInfos == null) {
			this.loadDataBaseInfos();
		}
		this.dbInfos.delete(dbInfo.getIndex());
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void loadFolders() {
		this.folders = new BasicCollections.Folders();
		FileModels.Text txt = new FileModels.Text(this.dbInfo.getUrl() + "\\Folders.txt");
		txt.load(true);
		for(String line : txt.getContent()) {
			BasicModels.Folder f = new BasicModels.Folder();
			String r = f.input(line);
			if(r != null) {
				this.folders.add(f);
			}
		}
	}
	private void loadFiles() {
		this.files = new BasicCollections.BaseFiles();
		FileModels.Text txt = new FileModels.Text(this.dbInfo.getUrl() + "\\Files.txt");
		txt.load(true);
		for(String line : txt.getContent()) {
			BasicModels.BaseFile f = new BasicModels.BaseFile();
			String r = f.input(line);
			if(r != null) {
				this.files.add(f);
			}
		}
	}
	private void loadUsers() {
		this.users = new BasicCollections.Users();
		FileModels.Text txt = new FileModels.Text(this.dbInfo.getUrl() + "\\Users.txt");
		txt.load(true);
		for(String line : txt.getContent()) {
			BasicModels.User f = new BasicModels.User();
			String r = f.input(line);
			if(r != null) {
				this.users.add(f);
			}
		}
	}
	private void loadInvitations() {
		this.invitations = new BasicCollections.Invitations();
		FileModels.Text txt = new FileModels.Text(this.dbInfo.getUrl() + "\\Invitations.txt");
		txt.load(true);
		for(String line : txt.getContent()) {
			BasicModels.Invitation f = new BasicModels.Invitation();
			String r = f.input(line);
			if(r != null) {
				this.invitations.add(f);
			}
		}
	}
	private void loadMachineInfos() {
		this.machineInfos = new BasicCollections.MachineInfos();
		FileModels.Text txt = new FileModels.Text(this.dbInfo.getUrl() + "\\MachineInfo.txt");
		txt.load(true);
		for(String line : txt.getContent()) {
			BasicModels.MachineInfo f = new BasicModels.MachineInfo();
			String r = f.input(line);
			if(r != null) {
				this.machineInfos.add(f);
			}
		}
	}
	private void loadDepotInfos() {
		this.depotInfos = new BasicCollections.DepotInfos();
		FileModels.Text txt = new FileModels.Text(this.dbInfo.getUrl() + "\\DepotInfo.txt");
		txt.load(true);
		for(String line : txt.getContent()) {
			BasicModels.DepotInfo f = new BasicModels.DepotInfo();
			String r = f.input(line);
			if(r != null) {
				this.depotInfos.add(f);
			}
		}
	}
	private void loadDataBaseInfos() {
		this.dbInfos = new BasicCollections.DataBaseInfos();
		FileModels.Text txt = new FileModels.Text(this.dbInfo.getUrl() + "\\DataBaseInfo.txt");
		txt.load(true);
		for(String line : txt.getContent()) {
			BasicModels.DataBaseInfo f = new BasicModels.DataBaseInfo();
			String r = f.input(line);
			if(r != null) {
				this.dbInfos.add(f);
			}
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean satisfyBoolean(boolean target, QueryCondition qc) {
		if(qc.getSign().equals(Sign.EQUAL)) {
			try {
				return target == (Integer.parseInt(qc.getValue()) != 0);
			} catch(Exception e) {
				return false;
			}
		}
		if(qc.getSign().equals(Sign.NOT_EQUAL)) {
			try {
				return target != (Integer.parseInt(qc.getValue()) != 0);
			} catch(Exception e) {
				return false;
			}
		}
		return false;
	}
	public boolean satisfyInteger(int target, QueryCondition qc) {
		if(qc.getSign().equals(Sign.EQUAL)) {
			try {
				return target == Integer.parseInt(qc.getValue());
			} catch(Exception e) {
				return false;
			}
		}
		if(qc.getSign().equals(Sign.NOT_EQUAL)) {
			try {
				return target != Integer.parseInt(qc.getValue());
			} catch(Exception e) {
				return false;
			}
		}
		if(qc.getSign().equals(Sign.GREATER)) {
			try {
				return target > Integer.parseInt(qc.getValue());
			} catch(Exception e) {
				return false;
			}
		}
		if(qc.getSign().equals(Sign.LESS)) {
			try {
				return target < Integer.parseInt(qc.getValue());
			} catch(Exception e) {
				return false;
			}
		}
		if(qc.getSign().equals(Sign.GREATER_OR_EQUAL)) {
			try {
				return target >= Integer.parseInt(qc.getValue());
			} catch(Exception e) {
				return false;
			}
		}
		if(qc.getSign().equals(Sign.LESS_OR_EQUAL)) {
			try {
				return target <= Integer.parseInt(qc.getValue());
			} catch(Exception e) {
				return false;
			}
		}
		return false;
	}
	public boolean satisfyLong(long target, QueryCondition qc) {
		if(qc.getSign().equals(Sign.EQUAL)) {
			try {
				return target == Long.parseLong(qc.getValue());
			} catch(Exception e) {
				return false;
			}
		}
		if(qc.getSign().equals(Sign.GREATER)) {
			try {
				return target > Long.parseLong(qc.getValue());
			} catch(Exception e) {
				return false;
			}
		}
		if(qc.getSign().equals(Sign.LESS)) {
			try {
				return target < Long.parseLong(qc.getValue());
			} catch(Exception e) {
				return false;
			}
		}
		if(qc.getSign().equals(Sign.GREATER_OR_EQUAL)) {
			try {
				return target >= Long.parseLong(qc.getValue());
			} catch(Exception e) {
				return false;
			}
		}
		if(qc.getSign().equals(Sign.LESS_OR_EQUAL)) {
			try {
				return target <= Long.parseLong(qc.getValue());
			} catch(Exception e) {
				return false;
			}
		}
		return false;
	}
	public boolean satisfyDouble(double target, QueryCondition qc) {
		if(qc.getSign().equals(Sign.EQUAL)) {
			try {
				return target == Double.parseDouble(qc.getValue());
			} catch(Exception e) {
				return false;
			}
		}
		if(qc.getSign().equals(Sign.GREATER)) {
			try {
				return target > Double.parseDouble(qc.getValue());
			} catch(Exception e) {
				return false;
			}
		}
		if(qc.getSign().equals(Sign.LESS)) {
			try {
				return target < Double.parseDouble(qc.getValue());
			} catch(Exception e) {
				return false;
			}
		}
		if(qc.getSign().equals(Sign.GREATER_OR_EQUAL)) {
			try {
				return target >= Double.parseDouble(qc.getValue());
			} catch(Exception e) {
				return false;
			}
		}
		if(qc.getSign().equals(Sign.LESS_OR_EQUAL)) {
			try {
				return target <= Double.parseDouble(qc.getValue());
			} catch(Exception e) {
				return false;
			}
		}
		return false;
	}
	public boolean satisfyString(String target, QueryCondition qc) {
		String v = qc.getValue();
		if(v != null && v.length() > 0 && v.charAt(0) == '\'' && v.charAt(v.length()-1) == '\'') {
			v = v.substring(0, v.length()-1);
			v = v.substring(1);
		}
		
		if(qc.getSign().equals(Sign.EQUAL)) {
			try {
				return target.equals(v);
			} catch(Exception e) {
				return false;
			}
		}
		if(qc.getSign().equals(Sign.NOT_EQUAL)) {
			try {
				return !target.equals(v);
			} catch(Exception e) {
				return false;
			}
		}
		if(qc.getSign().equals(Sign.CONTAIN)) {
			try {
				return target.indexOf(v) >= 0;
			} catch(Exception e) {
				return false;
			}
		}
		return false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
