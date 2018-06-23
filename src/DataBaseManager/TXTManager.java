package DataBaseManager;

public class TXTManager implements Interfaces.IDBManager {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private BasicModels.DataBaseInfo dbInfo;
	private boolean isConnected;
	private boolean isQueryOK;
	private boolean isUpdataOK;
	
	private BasicCollections.Folders folders;
	private BasicCollections.BaseFiles files;
	//private BasicCollections.MachineInfos machineInfos;
	//private BasicCollections.DepotInfos depotInfos;
	//private BasicCollections.DataBaseInfos dbInfos;
	//private BasicCollections.Users users;
	//private BasicCollections.Invitations invitations;
	
	private boolean[] checkedMark;
	
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
		
		if(this.folders != null) {
			FileModels.Text txt = new FileModels.Text(this.dbInfo.getUrl() + "\\Folders.txt");
			for(BasicModels.Folder f : this.folders.getContent()) {
				txt.getContent().add(f.output());
			}
			txt.save();
		}
		if(this.files != null) {
			FileModels.Text txt = new FileModels.Text(this.dbInfo.getUrl() + "\\Files.txt");
			for(BasicModels.BaseFile f : this.files.getContent()) {
				txt.getContent().add(f.output());
			}
			txt.save();
		}
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
		else {
			return null;
		}
		if(qcs.size() == 0) {
			return this.folders;
		}
		checkedMark = new boolean[this.folders.size()];
		for(int i=0; i<this.folders.size(); i++) {
			checkedMark[i] = true;
		}
		
		for(int i=0; i<qcs.size(); i++) {
			QueryCondition qc = qcs.getContent().get(i);
			if(qc.getItemName().equals("Index")) {
				for(int j=0; j<this.folders.size(); j++) {
					boolean satisfied = this.satisfyLong(folders.getContent().get(j).getIndex(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[i] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[i] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Father")) {
				for(int j=0; j<this.folders.size(); j++) {
					boolean satisfied = this.satisfyLong(folders.getContent().get(j).getFather(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[i] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[i] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Machine")) {
				for(int j=0; j<this.folders.size(); j++) {
					boolean satisfied = this.satisfyLong(folders.getContent().get(j).getMachine(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[i] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[i] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Depot")) {
				for(int j=0; j<this.folders.size(); j++) {
					boolean satisfied = this.satisfyLong(folders.getContent().get(j).getDepot(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[i] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[i] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("DataBase")) {
				for(int j=0; j<this.folders.size(); j++) {
					boolean satisfied = this.satisfyLong(folders.getContent().get(j).getDataBase(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[i] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[i] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Url")) {
				for(int j=0; j<this.folders.size(); j++) {
					boolean satisfied = this.satisfyString(folders.getContent().get(j).getUrl(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[i] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[i] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Type")) {
				for(int j=0; j<this.folders.size(); j++) {
					boolean satisfied = this.satisfyInteger(folders.getContent().get(j).getType().ordinal(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[i] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[i] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("State")) {
				for(int j=0; j<this.folders.size(); j++) {
					boolean satisfied = this.satisfyInteger(folders.getContent().get(j).getState().ordinal(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[i] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[i] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Modify")) {
				for(int j=0; j<this.folders.size(); j++) {
					boolean satisfied = this.satisfyLong(folders.getContent().get(j).getModify(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[i] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[i] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Length")) {
				for(int j=0; j<this.folders.size(); j++) {
					boolean satisfied = this.satisfyLong(folders.getContent().get(j).getLength(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[i] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[i] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Score")) {
				for(int j=0; j<this.folders.size(); j++) {
					boolean satisfied = this.satisfyLong(folders.getContent().get(j).getScore(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[i] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[i] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Tags")) {
				for(int j=0; j<this.folders.size(); j++) {
					boolean satisfied = this.satisfyString(folders.getContent().get(j).getTags(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[i] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[i] |= satisfied;
					}
				}
				continue;
			}
		}
		
		BasicCollections.Folders res = new BasicCollections.Folders();
		for(int i=0; i<this.checkedMark.length; i++) {
			if(this.checkedMark[i]) {
				res.add(this.folders.getContent().get(i));
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
		else {
			return null;
		}
		if(qcs.size() == 0) {
			return this.files;
		}
		
		checkedMark = new boolean[this.files.size()];
		for(int i=0; i<this.files.size(); i++) {
			checkedMark[i] = true;
		}
		
		for(int i=0; i<qcs.size(); i++) {
			QueryCondition qc = qcs.getContent().get(i);
			if(qc.getItemName().equals("Index")) {
				for(int j=0; j<this.files.size(); j++) {
					boolean satisfied = this.satisfyLong(files.getContent().get(j).getIndex(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[i] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[i] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Father")) {
				for(int j=0; j<this.files.size(); j++) {
					boolean satisfied = this.satisfyLong(files.getContent().get(j).getFather(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[i] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[i] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Machine")) {
				for(int j=0; j<this.files.size(); j++) {
					boolean satisfied = this.satisfyLong(files.getContent().get(j).getMachine(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[i] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[i] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Depot")) {
				for(int j=0; j<this.files.size(); j++) {
					boolean satisfied = this.satisfyLong(files.getContent().get(j).getDepot(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[i] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[i] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("DataBase")) {
				for(int j=0; j<this.files.size(); j++) {
					boolean satisfied = this.satisfyLong(files.getContent().get(j).getDataBase(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[i] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[i] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Url")) {
				for(int j=0; j<this.files.size(); j++) {
					boolean satisfied = this.satisfyString(files.getContent().get(j).getUrl(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[i] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[i] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Type")) {
				for(int j=0; j<this.files.size(); j++) {
					boolean satisfied = this.satisfyInteger(files.getContent().get(j).getType().ordinal(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[i] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[i] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("State")) {
				for(int j=0; j<this.files.size(); j++) {
					boolean satisfied = this.satisfyInteger(files.getContent().get(j).getState().ordinal(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[i] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[i] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Modify")) {
				for(int j=0; j<this.files.size(); j++) {
					boolean satisfied = this.satisfyLong(files.getContent().get(j).getModify(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[i] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[i] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Length")) {
				for(int j=0; j<this.files.size(); j++) {
					boolean satisfied = this.satisfyLong(files.getContent().get(j).getLength(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[i] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[i] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Score")) {
				for(int j=0; j<this.files.size(); j++) {
					boolean satisfied = this.satisfyLong(files.getContent().get(j).getScore(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[i] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[i] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Tags")) {
				for(int j=0; j<this.files.size(); j++) {
					boolean satisfied = this.satisfyString(files.getContent().get(j).getTags(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[i] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[i] |= satisfied;
					}
				}
				continue;
			}
		}
		
		BasicCollections.BaseFiles res = new BasicCollections.BaseFiles();
		for(int i=0; i<this.checkedMark.length; i++) {
			if(this.checkedMark[i]) {
				res.add(this.files.getContent().get(i));
			}
		}
		return res;
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
		return null;
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
				return f;
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
				return f;
			}
		}
		
		return null;
	}
	public BasicModels.User QueryUser(Object conditions) {
		return null;
	}
	public BasicModels.Invitation QueryInvitation(Object conditions) {
		return null;
	}
	public BasicModels.MachineInfo QueryMachineInfo(Object conditions) {
		return null;
	}
	public BasicModels.DepotInfo QueryDepotInfo(Object conditions) {
		return null;
	}
	public BasicModels.DataBaseInfo QueryDataBaseInfo(Object conditions) {
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
		if(this.folders == null) {
			this.loadFolders();
		}
		int index = this.folders.indexOf(folder.getIndex());
		if(index > 0) {
			this.folders.getContent().set(index, folder);
		} else {
			folder.setIndex();
			this.folders.add(folder);
		}
		
		return true;
	}
	public boolean updataFile(BasicModels.BaseFile file) {
		if(this.files == null) {
			this.loadFiles();
		}
		int index = this.files.indexOf(file.getIndex());
		if(index > 0) {
			this.files.getContent().set(index, file);
		} else {
			file.setIndex();
			this.files.add(file);
		}
		
		return true;
	}
	public boolean updataUser(BasicModels.User user) {
		return true;
	}
	public boolean updataInvitation(BasicModels.Invitation invitation) {
		return false;
	}
	public boolean updataMachineInfo(BasicModels.MachineInfo machineInfo) {
		return false;
	}
	public boolean updataDepotInfo(BasicModels.DepotInfo depotInfo) {
		return false;
	}
	public boolean updataDataBaseInfo(BasicModels.DataBaseInfo dbInfo) {
		return false;
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
		return true;
	}
	public boolean removeInvitation(BasicModels.Invitation invitation) {
		return false;
	}
	public boolean removeMachineInfo(BasicModels.MachineInfo machineInfo) {
		return false;
	}
	public boolean removeDepotInfo(BasicModels.DepotInfo depotInfo) {
		return false;
	}
	public boolean removeDataBaseInfo(BasicModels.DataBaseInfo dbInfo) {
		return false;
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
		if(qc.getSign().equals(Sign.lESS_OR_EQUAL)) {
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
		if(qc.getSign().equals(Sign.lESS_OR_EQUAL)) {
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
		if(qc.getSign().equals(Sign.lESS_OR_EQUAL)) {
			try {
				return target <= Double.parseDouble(qc.getValue());
			} catch(Exception e) {
				return false;
			}
		}
		return false;
	}
	public boolean satisfyString(String target, QueryCondition qc) {
		if(qc.getSign().equals(Sign.EQUAL)) {
			try {
				return target.equals(qc.getValue());
			} catch(Exception e) {
				return false;
			}
		}
		if(qc.getSign().equals(Sign.NOT_EQUAL)) {
			try {
				return !target.equals(qc.getValue());
			} catch(Exception e) {
				return false;
			}
		}
		if(qc.getSign().equals(Sign.CONTAIN)) {
			try {
				return target.indexOf(qc.getValue()) >= 0;
			} catch(Exception e) {
				return false;
			}
		}
		return false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
