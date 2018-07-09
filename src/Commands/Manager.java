package Commands;

public class Manager implements Interfaces.ICommandsManager {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private Interfaces.IReplies reply;
	private Interfaces.IClientConnection connection;
	private Interfaces.ISWRE swre;

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setConnection(Interfaces.IClientConnection connection) {
		if(connection == null) {
			BasicEnums.ErrorType.COMMON_SET_WRONG_VALUE.register("NULL", "connection = NULL");
			return false;
		}
		this.connection = connection;
		swre.setConnection(connection);
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Interfaces.IReplies getReply() {
		return this.reply;
	}
	public Interfaces.IClientConnection getConnection() {
		return this.connection;
	}
	public Interfaces.ISWRE getSWRE() {
		return this.swre;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Manager() {
		initThis();
	}
	public Manager(Interfaces.IClientConnection connection) {
		this.connection = connection;
		initThis();
	}
	private void initThis() {
		reply = null;
		connection = Globals.Datas.ServerConnection;
		swre = Factories.CommunicatorFactory.createSWRE();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Replies.QueryConfigurations queryConfigurations() {
		this.reply = null;
		if(!this.isConnectionRunning()) {
			return null;
		}
		if(this.isDirectConnection()) {
			return this.directlyQueryConfigurations();
		}
		
		Commands.QueryConfigurations cmd = new Commands.QueryConfigurations();
		reply = swre.execute(cmd.output());
		if(this.reply != null && !(reply instanceof Replies.QueryConfigurations)) {
			BasicEnums.ErrorType.OTHERS.register("Type of Reply is Wrong", "replyClass = " + reply.getClass().getSimpleName());
			this.reply = null;
			return null;
		}
		if(reply == null || !reply.isOK()) {
			return null;
		}
		return (Replies.QueryConfigurations)this.reply;
		
	}
	public BasicCollections.MachineInfos queryMachines(Object conditions) {
		this.reply = null;
		
		if(Globals.Configurations.IsServer) {
			BasicCollections.MachineInfos machines = Globals.Datas.DBManager.QueryMachineInfos(conditions);
			if(machines == null) { machines = new BasicCollections.MachineInfos(); }
			this.reply = new Replies.QueryMachines();
			((Replies.QueryMachines)this.reply).setAmount(machines.size());
			((Replies.QueryMachines)this.reply).setMachineInfos(machines);
			this.reply.setOK(machines != null);
			return machines;
		}
		else {
			if(!this.isConnectionRunning()) {
				return null;
			}
			if(this.isDirectConnection()) {
				return this.directlyQueryMachines(conditions);
			}
			
			Commands.QueryMachines cmd = new Commands.QueryMachines();
			cmd.getBasicMessagePackage().setSourUserIndex(this.connection.getUser().getIndex());
			cmd.getBasicMessagePackage().setPassword(this.connection.getUser().getPassword());
			if(conditions instanceof String) {
				cmd.setQueryConditions((String)conditions);
			}
			else if(conditions instanceof DataBaseManager.QueryCondition) {
				cmd.setQueryCondition((DataBaseManager.QueryCondition)conditions);
			}
			else if(conditions instanceof  DataBaseManager.QueryConditions) {
				cmd.setQueryConditions((DataBaseManager.QueryConditions)conditions);
			}
			else {
				BasicEnums.ErrorType.OTHERS.register("Type of conditions is Wrong", conditions.toString());
				return null;
			}
			
			reply = swre.execute(cmd.output());
			if(this.reply != null && !(reply instanceof Replies.QueryMachines)) {
				BasicEnums.ErrorType.OTHERS.register("Type of Reply is Wrong", "replyClass = " + reply.getClass().getSimpleName());
				this.reply = null;
				return null;
			}
			if(reply == null || !reply.isOK()) {
				return null;
			}
			return ((Replies.QueryMachines)reply).getMachineInfos();
		}
	}
	public BasicCollections.DepotInfos queryDepots(Object conditions) {
		this.reply = null;
		
		if(Globals.Configurations.IsServer) {
			BasicCollections.DepotInfos depots = Globals.Datas.DBManager.QueryDepotInfos(conditions);
			if(depots == null) { depots = new BasicCollections.DepotInfos(); }
			this.reply = new Replies.QueryDepots();
			((Replies.QueryDepots)this.reply).setAmount(depots.size());
			((Replies.QueryDepots)this.reply).setDepotInfos(depots);
			this.reply.setOK(depots != null);
			return depots;
		}
		else {
			if(!this.isConnectionRunning()) {
				return null;
			}
			if(this.isDirectConnection()) {
				return this.directlyQueryDepots(conditions);
			}
			
			Commands.QueryDepots cmd = new Commands.QueryDepots();
			cmd.getBasicMessagePackage().setSourUserIndex(this.connection.getUser().getIndex());
			cmd.getBasicMessagePackage().setPassword(this.connection.getUser().getPassword());
			if(conditions instanceof String) {
				cmd.setQueryConditions((String)conditions);
			}
			else if(conditions instanceof DataBaseManager.QueryCondition) {
				cmd.setQueryCondition((DataBaseManager.QueryCondition)conditions);
			}
			else if(conditions instanceof  DataBaseManager.QueryConditions) {
				cmd.setQueryConditions((DataBaseManager.QueryConditions)conditions);
			}
			else {
				BasicEnums.ErrorType.OTHERS.register("Type of conditions is Wrong", conditions.toString());
				return null;
			}
			
			reply = swre.execute(cmd.output());
			if(this.reply != null && !(reply instanceof Replies.QueryDepots)) {
				BasicEnums.ErrorType.OTHERS.register("Type of Reply is Wrong", "replyClass = " + reply.getClass().getSimpleName());
				this.reply = null;
				return null;
			}
			if(reply == null || !reply.isOK()) {
				return null;
			}
			return ((Replies.QueryDepots)reply).getDepotInfos();
		}
	}
	public BasicCollections.DataBaseInfos queryDataBases(Object conditions) {
		this.reply = null;
		
		if(Globals.Configurations.IsServer) {
			BasicCollections.DataBaseInfos dbs = Globals.Datas.DBManager.QueryDataBaseInfos(conditions);
			if(dbs == null) { dbs = new BasicCollections.DataBaseInfos(); }
			this.reply = new Replies.QueryDataBases();
			((Replies.QueryDataBases)this.reply).setAmount(dbs.size());
			((Replies.QueryDataBases)this.reply).setDBInfos(dbs);
			this.reply.setOK(dbs != null);
			return dbs;
		}
		else {
			if(!this.isConnectionRunning()) {
				return null;
			}
			if(this.isDirectConnection()) {
				return this.directlyQueryDataBases(conditions);
			}
			
			Commands.QueryDataBases cmd = new Commands.QueryDataBases();
			cmd.getBasicMessagePackage().setSourUserIndex(this.connection.getUser().getIndex());
			cmd.getBasicMessagePackage().setPassword(this.connection.getUser().getPassword());
			if(conditions instanceof String) {
				cmd.setQueryConditions((String)conditions);
			}
			else if(conditions instanceof  DataBaseManager.QueryCondition) {
				cmd.setQueryCondition((DataBaseManager.QueryCondition)conditions);
			}
			else if(conditions instanceof  DataBaseManager.QueryConditions) {
				cmd.setQueryConditions((DataBaseManager.QueryConditions)conditions);
			}
			else {
				BasicEnums.ErrorType.OTHERS.register("Type of conditions is Wrong", conditions.toString());
				return null;
			}
			
			reply = swre.execute(cmd.output());
			if(this.reply != null && !(reply instanceof Replies.QueryDataBases)) {
				BasicEnums.ErrorType.OTHERS.register("Type of Reply is Wrong", "replyClass = " + reply.getClass().getSimpleName());
				this.reply = null;
				return null;
			}
			if(reply == null || !reply.isOK()) {
				return null;
			}
			return ((Replies.QueryDataBases)reply).getDBInfos();
		}
	}
	public BasicCollections.Users queryUsers(Object conditions) {
		this.reply = null;
		
		if(Globals.Configurations.IsServer) {
			BasicCollections.Users users = Globals.Datas.DBManager.QueryUsers(conditions);
			if(users == null) { users = new BasicCollections.Users(); }
			this.reply = new Replies.QueryUsers();
			((Replies.QueryUsers)this.reply).setAmount(users.size());
			((Replies.QueryUsers)this.reply).setUsers(users);
			this.reply.setOK(users != null);
			return users;
		}
		else {
			if(!this.isConnectionRunning()) {
				return null;
			}
			if(this.isDirectConnection()) {
				return this.directlyQueryUsers(conditions);
			}
			
			Commands.QueryUsers cmd = new Commands.QueryUsers();
			cmd.getBasicMessagePackage().setSourUserIndex(this.connection.getUser().getIndex());
			cmd.getBasicMessagePackage().setPassword(this.connection.getUser().getPassword());
			if(conditions instanceof String) {
				cmd.setQueryConditions((String)conditions);
			}
			else if(conditions instanceof  DataBaseManager.QueryCondition) {
				cmd.setQueryCondition((DataBaseManager.QueryCondition)conditions);
			}
			else if(conditions instanceof  DataBaseManager.QueryConditions) {
				cmd.setQueryConditions((DataBaseManager.QueryConditions)conditions);
			}
			else {
				BasicEnums.ErrorType.OTHERS.register("Type of conditions is Wrong", conditions.toString());
				return null;
			}
			
			reply = swre.execute(cmd.output());
			if(this.reply != null && !(reply instanceof Replies.QueryUsers)) {
				BasicEnums.ErrorType.OTHERS.register("Type of Reply is Wrong", "replyClass = " + reply.getClass().getSimpleName());
				this.reply = null;
				return null;
			}
			if(reply == null || !reply.isOK()) {
				return null;
			}
			return ((Replies.QueryUsers)reply).getUsers();
		}
	}
	public BasicCollections.Invitations queryInvitations(Object conditions) {
		this.reply = null;
		
		if(Globals.Configurations.IsServer) {
			BasicCollections.Invitations invitations = Globals.Datas.DBManager.QueryInvitations(conditions);
			if(invitations == null) { invitations = new BasicCollections.Invitations(); }
			this.reply = new Replies.QueryInvitations();
			((Replies.QueryInvitations)this.reply).setAmount(invitations.size());
			((Replies.QueryInvitations)this.reply).setInvitations(invitations);
			this.reply.setOK(invitations != null);
			return invitations;
		}
		else {
			if(!this.isConnectionRunning()) {
				return null;
			}
			if(this.isDirectConnection()) {
				return this.directlyQueryInvitations(conditions);
			}
			
			Commands.QueryInvitations cmd = new Commands.QueryInvitations();
			cmd.getBasicMessagePackage().setSourUserIndex(this.connection.getUser().getIndex());
			cmd.getBasicMessagePackage().setPassword(this.connection.getUser().getPassword());
			if(conditions instanceof String) {
				cmd.setQueryConditions((String)conditions);
			}
			else if(conditions instanceof  DataBaseManager.QueryCondition) {
				cmd.setQueryCondition((DataBaseManager.QueryCondition)conditions);
			}
			else if(conditions instanceof  DataBaseManager.QueryConditions) {
				cmd.setQueryConditions((DataBaseManager.QueryConditions)conditions);
			}
			else {
				BasicEnums.ErrorType.OTHERS.register("Type of conditions is Wrong", conditions.toString());
				return null;
			}
			
			reply = swre.execute(cmd.output());
			if(this.reply != null && !(reply instanceof Replies.QueryInvitations)) {
				BasicEnums.ErrorType.OTHERS.register("Type of Reply is Wrong", "replyClass = " + reply.getClass().getSimpleName());
				this.reply = null;
				return null;
			}
			if(reply == null || !reply.isOK()) {
				return null;
			}
			return ((Replies.QueryInvitations)reply).getInvitations();
		}
	}
	
	public BasicCollections.Folders queryFolders(long depotIndex, Object conditions) {
		return this.queryFolders(
				this.connection.getServerMachineInfo().getIndex(),
				depotIndex,
				conditions
				);
	}
	public BasicCollections.Folders queryFolders(long machineIndex, long depotIndex, Object conditions) {
		this.reply = null;
		
		if(machineIndex == Globals.Configurations.This_MachineIndex) {
			Interfaces.IDBManager dbm = Globals.Datas.DBManagers.searchDepotIndex(depotIndex);
			if(dbm == null) {
				return null;
			}
			BasicCollections.Folders folders = dbm.QueryFolders(conditions);
			if(folders == null) {
				return null;
			}
			this.reply = new Replies.QueryFolders();
			((Replies.QueryFolders)this.reply).setFolders(folders);
			return folders;
		}
		else {
			Commands.QueryFolders cmd = new Commands.QueryFolders();
			cmd.getBasicMessagePackage().setSourUserIndex(this.connection.getUser().getIndex());
			cmd.getBasicMessagePackage().setPassword(this.connection.getUser().getPassword());
			cmd.getBasicMessagePackage().setDestMachineIndex(machineIndex);
			cmd.getBasicMessagePackage().setDestDepotIndex(depotIndex);
			if(conditions instanceof String) {
				cmd.setQueryConditions((String)conditions);
			}
			else if(conditions instanceof  DataBaseManager.QueryCondition) {
				cmd.setQueryCondition((DataBaseManager.QueryCondition)conditions);
			}
			else if(conditions instanceof  DataBaseManager.QueryConditions) {
				cmd.setQueryConditions((DataBaseManager.QueryConditions)conditions);
			}
			else {
				BasicEnums.ErrorType.OTHERS.register("Type of conditions is Wrong", conditions.toString());
				return null;
			}
			
			reply = swre.execute(cmd.output());
			if(this.reply != null && !(reply instanceof Replies.QueryFolders)) {
				BasicEnums.ErrorType.OTHERS.register("Type of Reply is Wrong", "replyClass = " + reply.getClass().getSimpleName());
				this.reply = null;
				return null;
			}
			if(reply == null || !reply.isOK()) {
				return null;
			}
			return ((Replies.QueryFolders)reply).getFolders();
		}
	}
	
	public BasicCollections.BaseFiles queryFiles(long depotIndex, Object conditions) {
		return this.queryFiles(
				this.connection.getServerMachineInfo().getIndex(),
				depotIndex,
				conditions
				);
	}
	public BasicCollections.BaseFiles queryFiles(long machineIndex, long depotIndex, Object conditions) {
		this.reply = null;
		
		if(machineIndex == Globals.Configurations.This_MachineIndex) {
			Interfaces.IDBManager dbm = Globals.Datas.DBManagers.searchDepotIndex(depotIndex);
			if(dbm == null) {
				return null;
			}
			BasicCollections.BaseFiles files = dbm.QueryFiles(conditions);
			if(files == null) {
				return null;
			}
			this.reply = new Replies.QueryFiles();
			((Replies.QueryFiles)this.reply).setFiles(files);
			return files;
		}
		else {
			Commands.QueryFiles cmd = new Commands.QueryFiles();
			cmd.getBasicMessagePackage().setSourUserIndex(this.connection.getUser().getIndex());
			cmd.getBasicMessagePackage().setPassword(this.connection.getUser().getPassword());
			cmd.getBasicMessagePackage().setDestMachineIndex(machineIndex);
			cmd.getBasicMessagePackage().setDestDepotIndex(depotIndex);
			if(conditions instanceof String) {
				cmd.setQueryConditions((String)conditions);
			}
			else if(conditions instanceof  DataBaseManager.QueryCondition) {
				cmd.setQueryCondition((DataBaseManager.QueryCondition)conditions);
			}
			else if(conditions instanceof  DataBaseManager.QueryConditions) {
				cmd.setQueryConditions((DataBaseManager.QueryConditions)conditions);
			}
			else {
				BasicEnums.ErrorType.OTHERS.register("Type of conditions is Wrong", conditions.toString());
				return null;
			}
			
			reply = swre.execute(cmd.output());
			if(this.reply != null && !(reply instanceof Replies.QueryFiles)) {
				BasicEnums.ErrorType.OTHERS.register("Type of Reply is Wrong", "replyClass = " + reply.getClass().getSimpleName());
				this.reply = null;
				return null;
			}
			if(reply == null || !reply.isOK()) {
				return null;
			}
			return ((Replies.QueryFiles)reply).getFiles();
		}
	}
	
	public BasicModels.User queryUser(Object conditions) {
		this.reply = null;
		
		if(Globals.Configurations.IsServer) {
			BasicModels.User user = Globals.Datas.DBManager.QueryUser(conditions);
			this.reply = new Replies.QueryUser();
			((Replies.QueryUser)this.reply).setUser(user);
			this.reply.setOK(user != null);
			return user;
		}
		else {
			if(!this.isConnectionRunning()) {
				return null;
			}
			if(this.isDirectConnection()) {
				return this.directlyQueryUser(conditions);
			}
			
			Commands.QueryUser cmd = new Commands.QueryUser();
			cmd.getBasicMessagePackage().setSourUserIndex(this.connection.getUser().getIndex());
			cmd.getBasicMessagePackage().setPassword(this.connection.getUser().getPassword());
			if(conditions instanceof String) {
				cmd.setQueryConditions((String)conditions);
			}
			else if(conditions instanceof  DataBaseManager.QueryCondition) {
				cmd.setQueryCondition(( DataBaseManager.QueryCondition)conditions);
			}
			else if(conditions instanceof  DataBaseManager.QueryConditions) {
				cmd.setQueryConditions((DataBaseManager.QueryConditions)conditions);
			}
			else {
				BasicEnums.ErrorType.OTHERS.register("Type of conditions is Wrong");
				return null;
			}
			
			reply = swre.execute(cmd.output());
			if(this.reply != null && !(reply instanceof Replies.QueryUser)) {
				BasicEnums.ErrorType.OTHERS.register("Type of Reply is Wrong", "replyClass = " + reply.getClass().getSimpleName());
				this.reply = null;
				return null;
			}
			if(reply == null || !reply.isOK()) {
				return null;
			}
			
			return ((Replies.QueryUser)reply).getUser();
		}
	}
	public BasicModels.Invitation queryInvitation(Object conditions) {
		this.reply = null;
		
		if(Globals.Configurations.IsServer) {
			BasicModels.Invitation invitation = Globals.Datas.DBManager.QueryInvitation(conditions);
			this.reply = new Replies.QueryInvitation();
			((Replies.QueryInvitation)this.reply).setInvitation(invitation);
			this.reply.setOK(invitation != null);
			return invitation;
		}
		else {
			if(!this.isConnectionRunning()) {
				return null;
			}
			if(this.isDirectConnection()) {
				return this.directlyQueryInvitation(conditions);
			}
			
			Commands.QueryInvitation cmd = new Commands.QueryInvitation();
			cmd.getBasicMessagePackage().setSourUserIndex(this.connection.getUser().getIndex());
			cmd.getBasicMessagePackage().setPassword(this.connection.getUser().getPassword());
			if(conditions instanceof String) {
				cmd.setQueryConditions((String)conditions);
			}
			else if(conditions instanceof  DataBaseManager.QueryCondition) {
				cmd.setQueryCondition(( DataBaseManager.QueryCondition)conditions);
			}
			else if(conditions instanceof  DataBaseManager.QueryConditions) {
				cmd.setQueryConditions((DataBaseManager.QueryConditions)conditions);
			}
			else {
				BasicEnums.ErrorType.OTHERS.register("Type of conditions is Wrong");
				return null;
			}
			
			reply = swre.execute(cmd.output());
			if(this.reply != null && !(reply instanceof Replies.QueryInvitation)) {
				BasicEnums.ErrorType.OTHERS.register("Type of Reply is Wrong", "replyClass = " + reply.getClass().getSimpleName());
				this.reply = null;
				return null;
			}
			if(reply == null || !reply.isOK()) {
				return null;
			}
			
			return ((Replies.QueryInvitation)reply).getInvitation();
		}
	}
	public BasicModels.MachineInfo queryMachine(Object conditions) {
		this.reply = null;
		
		if(Globals.Configurations.IsServer) {
			BasicModels.MachineInfo machine = Globals.Datas.DBManager.QueryMachineInfo(conditions);
			this.reply = new Replies.QueryMachine();
			((Replies.QueryMachine)this.reply).setMachineInfo(machine);
			this.reply.setOK(machine != null);
			return machine;
		}
		else {
			if(!this.isConnectionRunning()) {
				return null;
			}
			if(this.isDirectConnection()) {
				return this.directlyQueryMachine(conditions);
			}
			
			Commands.QueryMachine cmd = new Commands.QueryMachine();
			cmd.getBasicMessagePackage().setSourUserIndex(this.connection.getUser().getIndex());
			cmd.getBasicMessagePackage().setPassword(this.connection.getUser().getPassword());
			if(conditions instanceof String) {
				cmd.setQueryConditions((String)conditions);
			}
			else if(conditions instanceof DataBaseManager.QueryCondition) {
				cmd.setQueryCondition((DataBaseManager.QueryCondition)conditions);
			}
			else if(conditions instanceof DataBaseManager.QueryConditions) {
				cmd.setQueryConditions((DataBaseManager.QueryConditions)conditions);
			}
			else {
				BasicEnums.ErrorType.OTHERS.register("Type of conditions is Wrong");
				return null;
			}
			
			reply = swre.execute(cmd.output());
			if(this.reply != null && !(reply instanceof Replies.QueryMachine)) {
				BasicEnums.ErrorType.OTHERS.register("Type of Reply is Wrong", "replyClass = " + reply.getClass().getSimpleName());
				this.reply = null;
				return null;
			}
			if(reply == null || !reply.isOK()) {
				return null;
			}
			
			return ((Replies.QueryMachine)reply).getMachineInfo();
		}
	}
	public BasicModels.DepotInfo queryDepot(Object conditions) {
		this.reply = null;
		
		if(Globals.Configurations.IsServer) {
			BasicModels.DepotInfo depot = Globals.Datas.DBManager.QueryDepotInfo(conditions);
			this.reply = new Replies.QueryDepot();
			((Replies.QueryDepot)this.reply).setDepotInfo(depot);
			this.reply.setOK(depot != null);
			return depot;
		}
		else {
			if(!this.isConnectionRunning()) {
				return null;
			}
			if(this.isDirectConnection()) {
				return this.directlyQueryDepot(conditions);
			}
			
			Commands.QueryDepot cmd = new Commands.QueryDepot();
			cmd.getBasicMessagePackage().setSourUserIndex(this.connection.getUser().getIndex());
			cmd.getBasicMessagePackage().setPassword(this.connection.getUser().getPassword());
			if(conditions instanceof String) {
				cmd.setQueryConditions((String)conditions);
			}
			else if(conditions instanceof DataBaseManager.QueryCondition) {
				cmd.setQueryCondition((DataBaseManager.QueryCondition)conditions);
			}
			else if(conditions instanceof DataBaseManager.QueryConditions) {
				cmd.setQueryConditions((DataBaseManager.QueryConditions)conditions);
			}
			else {
				BasicEnums.ErrorType.OTHERS.register("Type of conditions is Wrong");
				return null;
			}
			
			reply = swre.execute(cmd.output());
			if(this.reply != null && !(reply instanceof Replies.QueryDepot)) {
				BasicEnums.ErrorType.OTHERS.register("Type of Reply is Wrong", "replyClass = " + reply.getClass().getSimpleName());
				this.reply = null;
				return null;
			}
			if(reply == null || !reply.isOK()) {
				return null;
			}
			
			return ((Replies.QueryDepot)reply).getDepotInfo();
		}
	}
	public BasicModels.DataBaseInfo queryDataBase(Object conditions) {
		this.reply = null;
		
		if(Globals.Configurations.IsServer) {
			BasicModels.DataBaseInfo db = Globals.Datas.DBManager.QueryDataBaseInfo(conditions);
			this.reply = new Replies.QueryDataBase();
			((Replies.QueryDataBase)reply).setDataBaseInfo(db);
			this.reply.setOK(db != null);
			return db;
		}
		else {
			if(!this.isConnectionRunning()) {
				return null;
			}
			if(this.isDirectConnection()) {
				return this.directlyQueryDataBase(conditions);
			}
			
			Commands.QueryDataBase cmd = new Commands.QueryDataBase();
			cmd.getBasicMessagePackage().setSourUserIndex(this.connection.getUser().getIndex());
			cmd.getBasicMessagePackage().setPassword(this.connection.getUser().getPassword());
			if(conditions instanceof String) {
				cmd.setQueryConditions((String)conditions);
			}
			else if(conditions instanceof DataBaseManager.QueryCondition) {
				cmd.setQueryCondition((DataBaseManager.QueryCondition)conditions);
			}
			else if(conditions instanceof DataBaseManager.QueryConditions) {
				cmd.setQueryConditions((DataBaseManager.QueryConditions)conditions);
			}
			else {
				BasicEnums.ErrorType.OTHERS.register("Type of conditions is Wrong");
				return null;
			}
			
			reply = swre.execute(cmd.output());
			if(this.reply != null && !(reply instanceof Replies.QueryDataBase)) {
				BasicEnums.ErrorType.OTHERS.register("Type of Reply is Wrong", "replyClass = " + reply.getClass().getSimpleName());
				this.reply = null;
				return null;
			}
			if(reply == null || !reply.isOK()) {
				return null;
			}
			return ((Replies.QueryDataBase)reply).getDataBaseInfo();
		}
	}
	
	public BasicModels.Folder queryFolder(long depotIndex, Object conditions) {
		return this.queryFolder(
				this.connection.getServerMachineInfo().getIndex(),
				depotIndex,
				conditions
				);
	}
	public BasicModels.Folder queryFolder(long machineIndex, long depotIndex, Object conditions) {
		this.reply = null;
		
		if(machineIndex == Globals.Configurations.This_MachineIndex) {
			Interfaces.IDBManager dbm = Globals.Datas.DBManagers.searchDepotIndex(depotIndex);
			if(dbm == null) {
				return null;
			}
			BasicModels.Folder folder = dbm.QueryFolder(conditions);
			if(folder == null) {
				return null;
			}
			this.reply = new Replies.QueryFolder();
			((Replies.QueryFolder)this.reply).setFolder(folder);
			return folder;
		}
		else {
			Commands.QueryFolder cmd = new Commands.QueryFolder();
			cmd.getBasicMessagePackage().setSourUserIndex(this.connection.getUser().getIndex());
			cmd.getBasicMessagePackage().setPassword(this.connection.getUser().getPassword());
			cmd.getBasicMessagePackage().setDestMachineIndex(machineIndex);
			cmd.getBasicMessagePackage().setDestDepotIndex(depotIndex);
			if(conditions instanceof String) {
				cmd.setQueryConditions((String)conditions);
			}
			else if(conditions instanceof  DataBaseManager.QueryCondition) {
				cmd.setQueryCondition((DataBaseManager.QueryCondition)conditions);
			}
			else if(conditions instanceof  DataBaseManager.QueryConditions) {
				cmd.setQueryConditions((DataBaseManager.QueryConditions)conditions);
			}
			else {
				BasicEnums.ErrorType.OTHERS.register("Type of conditions is Wrong", conditions.toString());
				return null;
			}
			
			reply = swre.execute(cmd.output());
			if(this.reply != null && !(reply instanceof Replies.QueryFolder)) {
				BasicEnums.ErrorType.OTHERS.register("Type of Reply is Wrong", "replyClass = " + reply.getClass().getSimpleName());
				this.reply = null;
				return null;
			}
			if(reply == null || !reply.isOK()) {
				return null;
			}
			return ((Replies.QueryFolder)reply).getFolder();
		}
	}
	
	public BasicModels.BaseFile queryFile(long depotIndex, Object conditions) {
		return this.queryFile(
				this.connection.getServerMachineInfo().getIndex(),
				depotIndex,
				conditions
				);
	}
	public BasicModels.BaseFile queryFile(long machineIndex, long depotIndex, Object conditions) {
		this.reply = null;
		
		if(machineIndex == Globals.Configurations.This_MachineIndex) {
			Interfaces.IDBManager dbm = Globals.Datas.DBManagers.searchDepotIndex(depotIndex);
			if(dbm == null) {
				return null;
			}
			BasicModels.BaseFile file = dbm.QueryFile(conditions);
			if(file == null) {
				return null;
			}
			this.reply = new Replies.QueryFile();
			((Replies.QueryFile)this.reply).setFile(file);
			return file;
		}
		else {
			Commands.QueryFile cmd = new Commands.QueryFile();
			cmd.getBasicMessagePackage().setSourUserIndex(this.connection.getUser().getIndex());
			cmd.getBasicMessagePackage().setPassword(this.connection.getUser().getPassword());
			cmd.getBasicMessagePackage().setDestMachineIndex(machineIndex);
			cmd.getBasicMessagePackage().setDestDepotIndex(depotIndex);
			if(conditions instanceof String) {
				cmd.setQueryConditions((String)conditions);
			}
			else if(conditions instanceof  DataBaseManager.QueryCondition) {
				cmd.setQueryCondition((DataBaseManager.QueryCondition)conditions);
			}
			else if(conditions instanceof  DataBaseManager.QueryConditions) {
				cmd.setQueryConditions((DataBaseManager.QueryConditions)conditions);
			}
			else {
				BasicEnums.ErrorType.OTHERS.register("Type of conditions is Wrong", conditions.toString());
				return null;
			}
			
			reply = swre.execute(cmd.output());
			if(this.reply != null && !(reply instanceof Replies.QueryFile)) {
				BasicEnums.ErrorType.OTHERS.register("Type of Reply is Wrong", "replyClass = " + reply.getClass().getSimpleName());
				this.reply = null;
				return null;
			}
			if(reply == null || !reply.isOK()) {
				return null;
			}
			return ((Replies.QueryFile)reply).getFile();
		}
	}
	
	public boolean removeDepot(Object conditions) {
		this.reply = null;
		
		if(Globals.Configurations.IsServer) {
			BasicModels.DepotInfo depot = Globals.Datas.DBManager.QueryDepotInfo(conditions);
			boolean ok = true;
			if(depot != null) {
				ok = Globals.Datas.DBManager.removeDepotInfo(depot);
			}
			this.reply = new Replies.RemoveDepot();
			this.reply.setOK(ok);
			return ok;
		}
		else {
			if(!this.isConnectionRunning()) {
				return false;
			}
			if(this.isDirectConnection()) {
				return this.directlyRemoveDepot(conditions);
			}
			
			Commands.RemoveDepot cmd = new Commands.RemoveDepot();
			cmd.getBasicMessagePackage().setSourUserIndex(this.connection.getUser().getIndex());
			cmd.getBasicMessagePackage().setPassword(this.connection.getUser().getPassword());
			if(conditions instanceof String) {
				cmd.setQueryConditions((String)conditions);
			}
			else if(conditions instanceof DataBaseManager.QueryCondition) {
				cmd.setQueryCondition((DataBaseManager.QueryCondition)conditions);
			}
			else if(conditions instanceof DataBaseManager.QueryConditions) {
				cmd.setQueryConditions((DataBaseManager.QueryConditions)conditions);
			}
			else {
				BasicEnums.ErrorType.OTHERS.register("Type of conditions is Wrong");
				return false;
			}
			
			reply = swre.execute(cmd.output());
			if(this.reply != null && !(reply instanceof Replies.RemoveDepot)) {
				BasicEnums.ErrorType.OTHERS.register("Type of Reply is Wrong", "replyClass = " + reply.getClass().getSimpleName());
				this.reply = null;
				return false;
			}
			if(reply == null || !reply.isOK()) {
				return false;
			}
			return true;
		}
	}
	public boolean removeDataBase(Object conditions) {
		this.reply = null;
		
		if(Globals.Configurations.IsServer) {
			BasicModels.DataBaseInfo db = Globals.Datas.DBManager.QueryDataBaseInfo(conditions);
			boolean ok = true;
			if(db != null) {
				ok = Globals.Datas.DBManager.removeDataBaseInfo(db);
			}
			this.reply = new Replies.RemoveDataBase();
			this.reply.setOK(ok);
			return ok;
		}
		else {
			if(!this.isConnectionRunning()) {
				return false;
			}
			if(this.isDirectConnection()) {
				return this.directlyRemoveDataBase(conditions);
			}
			
			Commands.RemoveDataBase cmd = new Commands.RemoveDataBase();
			cmd.getBasicMessagePackage().setSourUserIndex(this.connection.getUser().getIndex());
			cmd.getBasicMessagePackage().setPassword(this.connection.getUser().getPassword());
			if(conditions instanceof String) {
				cmd.setQueryConditions((String)conditions);
			}
			else if(conditions instanceof DataBaseManager.QueryCondition) {
				cmd.setQueryCondition((DataBaseManager.QueryCondition)conditions);
			}
			else if(conditions instanceof DataBaseManager.QueryConditions) {
				cmd.setQueryConditions((DataBaseManager.QueryConditions)conditions);
			}
			else {
				BasicEnums.ErrorType.OTHERS.register("Type of conditions is Wrong");
				return false;
			}
			
			reply = swre.execute(cmd.output());
			if(this.reply != null && !(reply instanceof Replies.RemoveDataBase)) {
				BasicEnums.ErrorType.OTHERS.register("Type of Reply is Wrong", "replyClass = " + reply.getClass().getSimpleName());
				this.reply = null;
				return false;
			}
			if(reply == null || !reply.isOK()) {
				return false;
			}
			return true;
		}
	}
	
	public boolean removeDepots(Object conditions) {
		this.reply = null;
		
		if(Globals.Configurations.IsServer) {
			BasicCollections.DepotInfos depots = Globals.Datas.DBManager.QueryDepotInfos(conditions);
			boolean ok = true;
			if(depots != null) {
				ok = Globals.Datas.DBManager.removeDepotInfos(depots);
			}
			this.reply = new Replies.RemoveDepots();
			this.reply.setOK(ok);
			return ok;
		}
		else {
			if(!this.isConnectionRunning()) {
				return false;
			}
			if(this.isDirectConnection()) {
				return this.directlyRemoveDepots(conditions);
			}
			
			Commands.RemoveDepots cmd = new Commands.RemoveDepots();
			cmd.getBasicMessagePackage().setSourUserIndex(this.connection.getUser().getIndex());
			cmd.getBasicMessagePackage().setPassword(this.connection.getUser().getPassword());
			if(conditions instanceof String) {
				cmd.setQueryConditions((String)conditions);
			}
			else if(conditions instanceof DataBaseManager.QueryCondition) {
				cmd.setQueryCondition((DataBaseManager.QueryCondition)conditions);
			}
			else if(conditions instanceof DataBaseManager.QueryConditions) {
				cmd.setQueryConditions((DataBaseManager.QueryConditions)conditions);
			}
			else {
				BasicEnums.ErrorType.OTHERS.register("Type of conditions is Wrong");
				return false;
			}
			
			reply = swre.execute(cmd.output());
			if(this.reply != null && !(reply instanceof Replies.RemoveDepots)) {
				BasicEnums.ErrorType.OTHERS.register("Type of Reply is Wrong", "replyClass = " + reply.getClass().getSimpleName());
				this.reply = null;
				return false;
			}
			if(reply == null || !reply.isOK()) {
				return false;
			}
			return true;
		}
	}
	public boolean removeDataBases(Object conditions) {
		this.reply = null;
		
		if(Globals.Configurations.IsServer) {
			BasicCollections.DataBaseInfos dbs = Globals.Datas.DBManager.QueryDataBaseInfos(conditions);
			boolean ok = true;
			if(dbs != null) {
				ok = Globals.Datas.DBManager.removeDataBaseInfos(dbs);
			}
			this.reply = new Replies.RemoveDataBases();
			this.reply.setOK(ok);
			return ok;
		}
		else {
			if(!this.isConnectionRunning()) {
				return false;
			}
			if(this.isDirectConnection()) {
				return this.directlyRemoveDataBases(conditions);
			}
			
			Commands.RemoveDataBase cmd = new Commands.RemoveDataBase();
			cmd.getBasicMessagePackage().setSourUserIndex(this.connection.getUser().getIndex());
			cmd.getBasicMessagePackage().setPassword(this.connection.getUser().getPassword());
			if(conditions instanceof String) {
				cmd.setQueryConditions((String)conditions);
			}
			else if(conditions instanceof DataBaseManager.QueryCondition) {
				cmd.setQueryCondition((DataBaseManager.QueryCondition)conditions);
			}
			else if(conditions instanceof DataBaseManager.QueryConditions) {
				cmd.setQueryConditions((DataBaseManager.QueryConditions)conditions);
			}
			else {
				BasicEnums.ErrorType.OTHERS.register("Type of conditions is Wrong");
				return false;
			}
			
			reply = swre.execute(cmd.output());
			if(this.reply != null && !(reply instanceof Replies.RemoveDataBases)) {
				BasicEnums.ErrorType.OTHERS.register("Type of Reply is Wrong", "replyClass = " + reply.getClass().getSimpleName());
				this.reply = null;
				return false;
			}
			if(reply == null || !reply.isOK()) {
				return false;
			}
			return true;
		}
	}

	public boolean updateMachine(BasicModels.MachineInfo machine) {
		this.reply = null;
		
		if(Globals.Configurations.IsServer) {
			boolean ok = Globals.Datas.DBManager.updataMachineInfo(machine);
			this.reply = new Replies.UpdateMachine();
			this.reply.setOK(ok);
			((Replies.UpdateMachine)this.reply).setMachineInfo(machine);
			return ok;
		}
		else {
			if(!this.isConnectionRunning()) {
				return false;
			}
			if(this.isDirectConnection()) {
				return this.directlyUpdateMachine(machine);
			}
			
			Commands.UpdateMachine cmd = new Commands.UpdateMachine();
			cmd.getBasicMessagePackage().setSourUserIndex(this.connection.getUser().getIndex());
			cmd.getBasicMessagePackage().setPassword(this.connection.getUser().getPassword());
			cmd.setMachineInfo(machine);
			
			reply = swre.execute(cmd.output());
			if(this.reply != null && !(reply instanceof Replies.UpdateMachine)) {
				BasicEnums.ErrorType.OTHERS.register("Type of Reply is Wrong", "replyClass = " + reply.getClass().getSimpleName());
				this.reply = null;
				return false;
			}
			if(reply == null || !reply.isOK()) {
				return false;
			}
			return true;
		}
	}
	public boolean updateDepot(BasicModels.DepotInfo depot) {
		this.reply = null;
		
		if(Globals.Configurations.IsServer) {
			boolean ok = Globals.Datas.DBManager.updataDepotInfo(depot);
			this.reply = new Replies.UpdateDepot();
			((Replies.UpdateDepot)this.reply).setDepotInfo(depot);
			this.reply.setOK(ok);
			return ok;
		}
		else {
			if(!this.isConnectionRunning()) {
				return false;
			}
			if(this.isDirectConnection()) {
				return this.directlyUpdateDepot(depot);
			}
			
			Commands.UpdateDepot cmd = new Commands.UpdateDepot();
			cmd.getBasicMessagePackage().setSourUserIndex(this.connection.getUser().getIndex());
			cmd.getBasicMessagePackage().setPassword(this.connection.getUser().getPassword());
			cmd.setDepotInfo(depot);

			reply = swre.execute(cmd.output());
			if(this.reply != null && !(reply instanceof Replies.UpdateDepot)) {
				BasicEnums.ErrorType.OTHERS.register("Type of Reply is Wrong", "replyClass = " + reply.getClass().getSimpleName());
				this.reply = null;
				return false;
			}
			if(reply == null || !reply.isOK()) {
				return false;
			}
			return true;
		}
	}
	public boolean updateDataBase(BasicModels.DataBaseInfo database) {
		this.reply = null;
		
		if(Globals.Configurations.IsServer) {
			boolean ok = Globals.Datas.DBManager.updataDataBaseInfo(database);
			this.reply = new Replies.UpdateDataBase();
			((Replies.UpdateDataBase)this.reply).setDataBaseInfo(database);
			this.reply.setOK(ok);
			return ok;
		}
		else {
			if(!this.isConnectionRunning()) {
				return false;
			}
			if(this.isDirectConnection()) {
				return this.directlyUpdateDataBase(database);
			}
			
			Commands.UpdateDataBase cmd = new Commands.UpdateDataBase();
			cmd.getBasicMessagePackage().setSourUserIndex(this.connection.getUser().getIndex());
			cmd.getBasicMessagePackage().setPassword(this.connection.getUser().getPassword());
			cmd.setDataBaseInfo(database);
			
			reply = swre.execute(cmd.output());
			if(this.reply != null && !(reply instanceof Replies.UpdateDataBase)) {
				BasicEnums.ErrorType.OTHERS.register("Type of Reply is Wrong", "replyClass = " + reply.getClass().getSimpleName());
				this.reply = null;
				return false;
			}
			if(reply == null || !reply.isOK()) {
				return false;
			}
			return true;
		}
	}
	
	public boolean loginConnection() {
		if(!this.loginUser()) {
			return false;
		}
		if(!this.loginMachine()) {
			return false;
		}
		if(!this.loginType()) {
			return false;
		}
		
		return true;
	}
	
	public boolean loginUser() {
		this.reply = null;
		if(!this.isConnectionRunning()) {
			return false;
		}
		
		Commands.LoginUser cmd = new Commands.LoginUser();
		cmd.getBasicMessagePackage().setPassword(this.connection.getUser().getPassword());
		cmd.setLoginName(this.connection.getUser().getLoginName());
		
		reply = swre.execute(cmd.output());
		if(this.reply != null && !(reply instanceof Replies.LoginUser)) {
			BasicEnums.ErrorType.OTHERS.register("Type of Reply is Wrong", "replyClass = " + reply.getClass().getSimpleName());
			this.reply = null;
			return false;
		}
		if(reply == null || !reply.isOK()) {
			return false;
		}
		
		Replies.LoginUser replu = (Replies.LoginUser)this.reply;
		long userIndex = ((Replies.LoginUser)this.reply).getBasicMessagePackage().getSourUserIndex();
		this.connection.getUser().setIndex(userIndex);
		
		BasicModels.User user = this.queryUser("[&] Index = " + userIndex);
		this.reply = replu;
		if(user == null) {
			this.reply.setFailedReason("QueryUser Failed");
			this.reply.setOK(false);
			return false;
		}
		
		this.connection.setUser(user);
		return true;
	}
	
	public boolean loginMachine() {
		this.reply = null;
		if(!this.isConnectionRunning()) {
			return false;
		}
		
		BasicModels.MachineInfo machine = this.queryMachine("[&] Name = '" + this.connection.getClientMachineInfo().getName() + "'");
		if(machine != null) {
			if(machine.getIndex() != this.connection.getClientMachineInfo().getIndex()) {
				BasicEnums.ErrorType.COMMANDS_EXECUTE_FAILED.register(
						"Login Failed",
						"MachineName Existed, But Index is Not Equal");
				return false;
			}
		}
		
		if(!this.updateMachine(this.connection.getClientMachineInfo())) {
			BasicEnums.ErrorType.COMMANDS_EXECUTE_FAILED.register("Update Machine Failed");
			return false;
		}
		this.connection.setClientMachineInfo(((Replies.UpdateMachine)this.reply).getMachineInfo());
		this.reply = null;
		
		Commands.LoginMachine cmd = new Commands.LoginMachine();
		cmd.getBasicMessagePackage().setSourUserIndex(this.connection.getUser().getIndex());
		cmd.getBasicMessagePackage().setPassword(this.connection.getUser().getPassword());
		cmd.getBasicMessagePackage().setSourMachineIndex(this.connection.getClientMachineInfo().getIndex());
		
		reply = swre.execute(cmd.output());
		if(this.reply != null && !(reply instanceof Replies.LoginMachine)) {
			BasicEnums.ErrorType.OTHERS.register("Type of Reply is Wrong", "replyClass = " + reply.getClass().getSimpleName());
			this.reply = null;
			return false;
		}
		if(reply == null || !reply.isOK()) {
			return false;
		}
		
		this.connection.setConnectionName();
		return true;
	}
	
	public boolean loginType() {
		this.reply = null;
		if(!this.isConnectionRunning()) {
			return false;
		}
		
		Commands.LoginType cmd = new Commands.LoginType();
		cmd.getBasicMessagePackage().setSourUserIndex(this.connection.getUser().getIndex());
		cmd.getBasicMessagePackage().setPassword(this.connection.getUser().getPassword());
		cmd.setConnectionType(connection.getType());
		
		this.reply = swre.execute(cmd.output());
		if(this.reply != null && !(reply instanceof Replies.LoginType)) {
			BasicEnums.ErrorType.OTHERS.register("Type of Reply is Wrong", "replyClass = " + reply.getClass().getSimpleName());
			this.reply = null;
			return false;
		}
		if(reply == null || !reply.isOK()) {
			return false;
		}
		return true;
	}

	public boolean test() {
		long waitReceiveTicks = this.swre.getReceiveWaitTicks();
		long waitSendTicks = this.swre.getSendWaitTicks();
		
		this.swre.setReceiveWaitTicks(Globals.Configurations.TimeForTestReceiveCommand);
		this.swre.setSendWaitTicks(Globals.Configurations.TimeForTestSendCommand);
		
		String s = this.test("Test");
		
		this.swre.setReceiveWaitTicks(waitReceiveTicks);
		this.swre.setSendWaitTicks(waitSendTicks);
		return s != null && s.equals("Test");
	}
	public String test(String str) {
		return this.test(
				this.connection.getServerMachineInfo().getIndex(),
				str);
	}
	public String test(long destMachine, String str) {
		this.reply = null;
		if(!this.isConnectionRunning()) {
			return null;
		}
		
		Commands.Test cmd = new Commands.Test();
		cmd.getBasicMessagePackage().setSourUserIndex(this.connection.getUser().getIndex());
		cmd.getBasicMessagePackage().setPassword(this.connection.getUser().getPassword());
		cmd.getBasicMessagePackage().setDestMachineIndex(destMachine);
		cmd.setTestString(str);
		
		reply = swre.execute(cmd.output());
		if(this.reply != null && !(reply instanceof Replies.Test)) {
			BasicEnums.ErrorType.OTHERS.register("Type of Reply is Wrong", "replyClass = " + reply.getClass().getSimpleName());
			this.reply = null;
			return null;
		}
		if(reply == null || !reply.isOK()) {
			return null;
		}
		return ((Replies.Test)reply).getTestString();
	}
	
	public boolean closeServer() {
		return this.closeServer(this.connection.getServerMachineInfo().getIndex());
	}
	public boolean closeServer(long destMachine) {
		this.reply = null;
		if(!this.isConnectionRunning()) {
			return false;
		}
		
		Commands.CloseServer cmd = new Commands.CloseServer();
		cmd.getBasicMessagePackage().setSourUserIndex(this.connection.getUser().getIndex());
		cmd.getBasicMessagePackage().setPassword(this.connection.getUser().getPassword());
		cmd.getBasicMessagePackage().setDestMachineIndex(destMachine);
		
		this.reply = swre.execute(cmd.output());
		if(this.reply != null && !(reply instanceof Replies.CloseServer)) {
			BasicEnums.ErrorType.OTHERS.register("Type of Reply is Wrong", "replyClass = " + reply.getClass().getSimpleName());
			this.reply = null;
			return false;
		}
		if(reply == null || !reply.isOK()) {
			return false;
		}
		return true;
	}
	
	public boolean input(String sourUrl, String destUrl) {
		return this.input(
				sourUrl,
				destUrl,
				0,
				true);
	}
	public boolean input(String sourUrl, String destUrl, boolean cover) {
		return this.input(
				sourUrl,
				destUrl,
				0,
				cover);
	}
	public boolean input(String sourUrl, String destUrl, long finishedBytes) {
		return this.input(
				sourUrl,
				destUrl,
				finishedBytes,
				true);
	}
	public boolean input(String sourUrl, String destUrl, long finishedBytes, boolean cover) {
		return this.input(
				this.connection.getClientMachineInfo().getIndex(),
				this.connection.getServerMachineInfo().getIndex(),
				sourUrl,
				destUrl,
				finishedBytes,
				cover);
	}
	public boolean input(long sourMachine, long destMachine, String sourUrl, String destUrl) {
		return this.input(
				sourMachine,
				destMachine,
				sourUrl,
				destUrl,
				0,
				true);
	}
	public boolean input(long sourMachine, long destMachine, String sourUrl, String destUrl, boolean cover) {
		return this.input(
				sourMachine,
				destMachine,
				sourUrl,
				destUrl,
				0,
				cover);
	}
	public boolean input(long sourMachine, long destMachine, String sourUrl, String destUrl, long finishedBytes) {
		return this.input(
				sourMachine,
				destMachine,
				sourUrl,
				destUrl,
				finishedBytes,
				true);
	}
	public boolean input(long sourMachine, long destMachine, String sourUrl, String destUrl, long finishedBytes, boolean cover) {
		this.reply = null;
		if(!this.isConnectionRunning()) {
			return false;
		}
		
		long sourDepot = -1;
		BasicCollections.DepotInfos sdepots = this.queryDepots("[&] MachineIndex = " + sourMachine);
		if(sdepots == null) {
			BasicEnums.ErrorType.COMMON_NULL.register("sdepots is NULL");
			return false;
		}
		for(BasicModels.DepotInfo i : sdepots.getContent()) {
			if(Tools.Url.isFileIn(destUrl, i.getUrl())) {
				sourDepot = i.getIndex();
			}
		}
		if(sourDepot == -1) {
			BasicEnums.ErrorType.COMMON_NOT_EXIST.register("Sour Depot Not Exist", "sourUrl = " + sourUrl);
			return false;
		}
		
		long destDepot = -1;
		BasicCollections.DepotInfos ddepots = this.queryDepots("[&] MachineIndex = " + destMachine);
		if(ddepots == null) {
			BasicEnums.ErrorType.COMMON_NULL.register("ddepots is NULL");
			return false;
		}
		for(BasicModels.DepotInfo i : ddepots.getContent()) {
			if(Tools.Url.isFileIn(sourUrl, i.getUrl())) {
				destDepot = i.getIndex();
			}
		}
		if(destDepot == -1) {
			BasicEnums.ErrorType.COMMON_NOT_EXIST.register("Sour Depot Not Exist", "sourUrl = " + destUrl);
			return false;
		}
		
		return this.input(sourMachine, destMachine, sourDepot, destDepot, sourUrl, destUrl, finishedBytes, cover);
	}
	public boolean input(long sourMachine, long destMachine, long sourDepot, long destDepot, String sourUrl, String destUrl, long finishedBytes, boolean cover) {
		this.reply = null;
		if(!this.isConnectionRunning()) {
			return false;
		}
		
		if(sourMachine == Globals.Configurations.This_MachineIndex) {
			java.io.File destFile = new java.io.File(destUrl);
			if(destFile.exists() && destFile.isFile() && !cover) {
				BasicEnums.ErrorType.COMMON_FILE_EXISTED.register("destUrl =" + destUrl);
				return false;
			}
		}
		
		Commands.Input cmd = new Commands.Input();
		cmd.getBasicMessagePackage().setSourUserIndex(this.connection.getUser().getIndex());
		cmd.getBasicMessagePackage().setPassword(this.connection.getUser().getPassword());
		cmd.getBasicMessagePackage().setSourMachineIndex(sourMachine);
		cmd.getBasicMessagePackage().setDestMachineIndex(destMachine);
		cmd.getBasicMessagePackage().setSourDepotIndex(sourDepot);
		cmd.getBasicMessagePackage().setDestDepotIndex(destDepot);
		cmd.setSourUrl(sourUrl);
		cmd.setDestUrl(destUrl);
		cmd.setFinishedBytes(finishedBytes);
		cmd.setTotalBytes(0);
		cmd.setCover(cover);
		
		this.reply = swre.execute(cmd.output());
		if(this.reply != null && !(reply instanceof Replies.Input)) {
			BasicEnums.ErrorType.OTHERS.register("Type of Reply is Wrong", "replyClass = " + reply.getClass().getSimpleName());
			this.reply = null;
			return false;
		}
		if(reply == null || !reply.isOK()) {
			return false;
		}
		return true;
	}
	
	public boolean output(String sourUrl, String destUrl) {
		return this.output(
				sourUrl,
				destUrl,
				0,
				true);
	}
	public boolean output(String sourUrl, String destUrl, boolean cover) {
		return this.output(
				sourUrl,
				destUrl,
				0,
				cover);
	}
	public boolean output(String sourUrl, String destUrl, long finishedBytes) {
		return this.output(
				sourUrl,
				destUrl,
				finishedBytes,
				true);
	}
	public boolean output(String sourUrl, String destUrl, long finishedBytes, boolean cover) {
		return this.output(
				this.connection.getClientMachineInfo().getIndex(),
				this.connection.getServerMachineInfo().getIndex(),
				sourUrl,
				destUrl,
				finishedBytes,
				cover);
	}
	public boolean output(long sourMachine, long destMachine, String sourUrl, String destUrl) {
		return this.output(
				sourMachine,
				destMachine,
				sourUrl,
				destUrl,
				0,
				true);
	}
	public boolean output(long sourMachine, long destMachine, String sourUrl, String destUrl, boolean cover) {
		return this.output(
				sourMachine,
				destMachine,
				sourUrl,
				destUrl,
				0,
				cover);
	}
	public boolean output(long sourMachine, long destMachine, String sourUrl, String destUrl, long finishedBytes) {
		return this.output(
				sourMachine,
				destMachine,
				sourUrl,
				destUrl,
				finishedBytes,
				true);
	}
	public boolean output(long sourMachine, long destMachine, String sourUrl, String destUrl, long finishedBytes, boolean cover) {
		this.reply = null;
		if(!this.isConnectionRunning()) {
			return false;
		}
		
		long sourDepot = -1;
		BasicCollections.DepotInfos sdepots = this.queryDepots("[&] MachineIndex = " + sourMachine);
		if(sdepots == null) {
			BasicEnums.ErrorType.COMMON_NULL.register("sdepots is NULL");
			return false;
		}
		for(BasicModels.DepotInfo i : sdepots.getContent()) {
			if(Tools.Url.isFileIn(sourUrl, i.getUrl())) {
				sourDepot = i.getIndex();
			}
		}
		if(sourDepot == -1) {
			BasicEnums.ErrorType.COMMON_NOT_EXIST.register("Sour Depot Not Exist", "sourUrl = " + sourUrl);
			return false;
		}
		
		long destDepot = -1;
		BasicCollections.DepotInfos ddepots = this.queryDepots("[&] MachineIndex = " + destMachine);
		if(ddepots == null) {
			BasicEnums.ErrorType.COMMON_NULL.register("ddepots is NULL");
			return false;
		}
		for(BasicModels.DepotInfo i : ddepots.getContent()) {
			if(Tools.Url.isFileIn(destUrl, i.getUrl())) {
				destDepot = i.getIndex();
			}
		}
		if(destDepot == -1) {
			BasicEnums.ErrorType.COMMON_NOT_EXIST.register("Sour Depot Not Exist", "sourUrl = " + destUrl);
			return false;
		}
		
		return this.output(sourMachine, destMachine, sourDepot, destDepot, sourUrl, destUrl, finishedBytes, 0, cover);
	}
	public boolean output(long sourMachine, long destMachine, long sourDepot, long destDepot, String sourUrl, String destUrl, long finishedBytes, long totalBytes, boolean cover) {
		this.reply = null;
		if(!this.isConnectionRunning()) {
			return false;
		}
		
		if(sourMachine == Globals.Configurations.This_MachineIndex) {
			java.io.File sourFile = new java.io.File(sourUrl);
			if(!sourFile.exists()) {
				BasicEnums.ErrorType.COMMON_FILE_NOT_EXIST.register("sourUrl = " + sourUrl);
				return false;
			}
			if(sourFile.isDirectory()) {
				BasicEnums.ErrorType.COMMON_FILE_NOT_EXIST.register("sourUrl is a Directory", "sourUrl = " + sourUrl);
				return false;
			}
			totalBytes = sourFile.length();
		}
		
		Commands.Output cmd = new Commands.Output();
		cmd.getBasicMessagePackage().setSourUserIndex(this.connection.getUser().getIndex());
		cmd.getBasicMessagePackage().setPassword(this.connection.getUser().getPassword());
		cmd.getBasicMessagePackage().setSourMachineIndex(sourMachine);
		cmd.getBasicMessagePackage().setDestMachineIndex(destMachine);
		cmd.getBasicMessagePackage().setSourDepotIndex(sourDepot);
		cmd.getBasicMessagePackage().setDestDepotIndex(destDepot);
		cmd.setSourUrl(sourUrl);
		cmd.setDestUrl(destUrl);
		cmd.setFinishedBytes(finishedBytes);
		cmd.setTotalBytes(totalBytes);
		cmd.setCover(cover);
		
		this.reply = swre.execute(cmd.output());
		if(this.reply != null && !(reply instanceof Replies.Output)) {
			BasicEnums.ErrorType.OTHERS.register("Type of Reply is Wrong", "replyClass = " + reply.getClass().getSimpleName());
			this.reply = null;
			return false;
		}
		if(reply == null || !reply.isOK()) {
			return false;
		}
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private Replies.QueryConfigurations directlyQueryConfigurations() {
		Interfaces.ICommandsManager cm = Globals.Datas.ServerConnection.getCommandsManager();
		Replies.QueryConfigurations res = cm.queryConfigurations();
		this.reply = cm.getReply();
		return res;
	}
	private BasicCollections.MachineInfos directlyQueryMachines(Object conditions) {
		Interfaces.ICommandsManager cm = Globals.Datas.ServerConnection.getCommandsManager();
		BasicCollections.MachineInfos res = cm.queryMachines(conditions);
		this.reply = cm.getReply();
		return res;
	}
	private BasicCollections.DepotInfos directlyQueryDepots(Object conditions) {
		Interfaces.ICommandsManager cm = Globals.Datas.ServerConnection.getCommandsManager();
		BasicCollections.DepotInfos res = cm.queryDepots(conditions);
		this.reply = cm.getReply();
		return res;
	}
	private BasicCollections.DataBaseInfos directlyQueryDataBases(Object conditions) {
		Interfaces.ICommandsManager cm = Globals.Datas.ServerConnection.getCommandsManager();
		BasicCollections.DataBaseInfos res = cm.queryDataBases(conditions);
		this.reply = cm.getReply();
		return res;
	}
	private BasicCollections.Users directlyQueryUsers(Object conditions) {
		Interfaces.ICommandsManager cm = Globals.Datas.ServerConnection.getCommandsManager();
		BasicCollections.Users res = cm.queryUsers(conditions);
		this.reply = cm.getReply();
		return res;
	}
	private BasicCollections.Invitations directlyQueryInvitations(Object conditions) {
		Interfaces.ICommandsManager cm = Globals.Datas.ServerConnection.getCommandsManager();
		BasicCollections.Invitations res = cm.queryInvitations(conditions);
		this.reply = cm.getReply();
		return res;
	}
	
	private BasicModels.User directlyQueryUser(Object conditions) {
		Interfaces.ICommandsManager cm = Globals.Datas.ServerConnection.getCommandsManager();
		BasicModels.User res = cm.queryUser(conditions);
		this.reply = cm.getReply();
		return res;
	}
	private BasicModels.Invitation directlyQueryInvitation(Object conditions) {
		Interfaces.ICommandsManager cm = Globals.Datas.ServerConnection.getCommandsManager();
		BasicModels.Invitation res = cm.queryInvitation(conditions);
		this.reply = cm.getReply();
		return res;
	}
	private BasicModels.MachineInfo directlyQueryMachine(Object conditions) {
		Interfaces.ICommandsManager cm = Globals.Datas.ServerConnection.getCommandsManager();
		BasicModels.MachineInfo res = cm.queryMachine(conditions);
		this.reply = cm.getReply();
		return res;
	}
	private BasicModels.DepotInfo directlyQueryDepot(Object conditions) {
		Interfaces.ICommandsManager cm = Globals.Datas.ServerConnection.getCommandsManager();
		BasicModels.DepotInfo res = cm.queryDepot(conditions);
		this.reply = cm.getReply();
		return res;
	}
	private BasicModels.DataBaseInfo directlyQueryDataBase(Object conditions) {
		Interfaces.ICommandsManager cm = Globals.Datas.ServerConnection.getCommandsManager();
		BasicModels.DataBaseInfo res = cm.queryDataBase(conditions);
		this.reply = cm.getReply();
		return res;
	}
	
	private boolean directlyRemoveDepots(Object conditions) {
		Interfaces.ICommandsManager cm = Globals.Datas.ServerConnection.getCommandsManager();
		boolean res = cm.removeDepots(conditions);
		this.reply = cm.getReply();
		return res;
	}
	private boolean directlyRemoveDataBases(Object conditions) {
		Interfaces.ICommandsManager cm = Globals.Datas.ServerConnection.getCommandsManager();
		boolean res = cm.removeDataBases(conditions);
		this.reply = cm.getReply();
		return res;
	}
	
	private boolean directlyRemoveDepot(Object conditions) {
		Interfaces.ICommandsManager cm = Globals.Datas.ServerConnection.getCommandsManager();
		boolean res = cm.removeDepot(conditions);
		this.reply = cm.getReply();
		return res;
	}
	private boolean directlyRemoveDataBase(Object conditions) {
		Interfaces.ICommandsManager cm = Globals.Datas.ServerConnection.getCommandsManager();
		boolean res = cm.removeDataBase(conditions);
		this.reply = cm.getReply();
		return res;
	}
	
	private boolean directlyUpdateMachine(BasicModels.MachineInfo machine) {
		Interfaces.ICommandsManager cm = Globals.Datas.ServerConnection.getCommandsManager();
		boolean res = cm.updateMachine(machine);
		this.reply = cm.getReply();
		return res;
	}
	private boolean directlyUpdateDepot(BasicModels.DepotInfo depot) {
		Interfaces.ICommandsManager cm = Globals.Datas.ServerConnection.getCommandsManager();
		boolean res = cm.updateDepot(depot);
		this.reply = cm.getReply();
		return res;
	}
	private boolean directlyUpdateDataBase(BasicModels.DataBaseInfo database) {
		Interfaces.ICommandsManager cm = Globals.Datas.ServerConnection.getCommandsManager();
		boolean res = cm.updateDataBase(database);
		this.reply = cm.getReply();
		return res;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private boolean isConnectionRunning() {
		if(this.connection == null) {
			BasicEnums.ErrorType.COMMON_NULL.register("connection is NULL");
			return false;
		}
		if(!this.connection.isRunning()) {
			BasicEnums.ErrorType.COMMUNICATOR_CONNECTION_CLOSED.register();
			return false;
		}
		return true;
	}
	
	private boolean isDirectConnection() {
		return this.connection.getServerMachineInfo().getIndex() != Globals.Configurations.Server_MachineIndex &&
				this.connection.getClientMachineInfo().getIndex() != Globals.Configurations.Server_MachineIndex &&
						!Globals.Configurations.IsServer;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
