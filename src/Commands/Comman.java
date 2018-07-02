package Commands;

public class Comman implements Interfaces.ICommands {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private long userIndex;
	private String password;
	private long machineIndex;
	private long depotIndex;
	private long dbIndex;
	
	private Interfaces.IConnection connection;
	private Interfaces.IReplies reply;
	private BasicModels.User user;
	private BasicModels.MachineInfo machine;
	private BasicModels.DepotInfo depot;
	private BasicModels.DataBaseInfo database;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setUserIndex(long userIndex) {
		if(userIndex <= 0) {
			return false;
		}
		this.userIndex = userIndex;
		return true;
	}
	public boolean setPassword(String password) {
		if(password == null) {
			return false;
		}
		this.password = password;
		return true;
	}
	public boolean setMachineIndex(long machineIndex) {
		if(machineIndex <= 0) {
			return false;
		}
		this.machineIndex = machineIndex;
		return true;
	}
	public boolean setDepotIndex(long depotIndex) {
		if(depotIndex <= 0) {
			return false;
		}
		this.depotIndex = depotIndex;
		return true;
	}
	public boolean setDataBaseIndex(long dbIndex) {
		if(dbIndex <= 0) {
			return false;
		}
		this.dbIndex = dbIndex;
		return true;
	}
	
	public boolean setConnection(Interfaces.IConnection connection) {
		if(connection == null) {
			return false;
		}
		this.connection = connection;
		return true;
	}
	public boolean setReply(Interfaces.IReplies reply) {
		if(reply == null) {
			return false;
		}
		this.reply = reply;
		return true;
	}
	public void setUserIndexAndPassword() {
		this.reply.setUserIndex(this.userIndex);
		this.reply.setPassword(this.password);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public long getUserIndex() {
		return this.userIndex;
	}
	public String getPassword() {
		return this.password;
	}
	public long getMachineIndex() {
		return this.machineIndex;
	}
	public long getDepotIndex() {
		return this.depotIndex;
	}
	public long getDataBaseIndex() {
		return this.dbIndex;
	}
	
	public Interfaces.IConnection getConnection() {
		return this.connection;
	}
	public Interfaces.IReplies getReply() {
		return this.reply;
	}
	public BasicModels.User getUser() {
		return this.user;
	}
	public BasicModels.MachineInfo getMachineInfo() {
		return this.machine;
	}
	public BasicModels.DepotInfo getDepotInfo() {
		return this.depot;
	}
	public BasicModels.DataBaseInfo getDataBaseInfo() {
		return this.database;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Comman() {
		initThis();
	}
	private void initThis() {
		this.userIndex = Globals.Datas.ThisUser.getIndex();
		this.password = Globals.Datas.ThisUser.getPassword();
		this.machineIndex = Globals.Configurations.This_MachineIndex;
		this.depotIndex = -1;
		this.dbIndex = -1;
		this.reply = new Replies.Comman();
		this.connection = null;
		this.user = null;
		this.machine = null;
		this.depot = null;
		this.database = null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void clear() {
		initThis();
	}
	public String toString() {
		return this.output();
	}
	public String output() {
		BasicModels.Config c = new BasicModels.Config();
		c.setField(this.getClass().getSimpleName());
		c.addToBottom(this.userIndex);
		c.addToBottom(this.password);
		c.addToBottom(this.machineIndex);
		c.addToBottom(this.depotIndex);
		c.addToBottom(this.dbIndex);
		
		return c.output();
	}
	public String output(String cmdName) {
		BasicModels.Config c = new BasicModels.Config();
		c.setField(cmdName);
		c.addToBottom(this.userIndex);
		c.addToBottom(this.password);
		c.addToBottom(this.machineIndex);
		c.addToBottom(this.depotIndex);
		c.addToBottom(this.dbIndex);
		
		return c.output();
	}
	public String input(String in) {
		BasicModels.Config c = new BasicModels.Config(in);
		this.userIndex = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		this.password = c.fetchFirstString();
		if(!c.getIsOK()) { return null; }
		this.machineIndex = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		this.depotIndex = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		this.dbIndex = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		
		return c.output();
	}
	public void copyReference(Object o) {
		Comman c = (Comman)o;
		this.userIndex = c.userIndex;
		this.password = c.password;
		this.machineIndex = c.machineIndex;
		this.depotIndex = c.depotIndex;
		this.dbIndex = c.dbIndex;
		this.reply = c.reply;
		this.connection = c.connection;
	}
	public void copyValue(Object o) {
		Comman c = (Comman)o;
		this.userIndex = c.userIndex;
		this.password = new String(c.password);
		this.machineIndex = c.machineIndex;
		this.depotIndex = c.depotIndex;
		this.dbIndex = c.dbIndex;
		this.reply.copyValue(c.reply);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean execute() {
		if(!this.isConnected()) {
			return false;
		}
		
		return Globals.Configurations.StartType.equals(BasicEnums.StartType.Server) ?
				this.executeInServer() :
				this.executeInDepot();
	}
	public void reply() {
		this.setUserIndexAndPassword();
		this.connection.setSendString(this.reply.output());
		this.connection.setSendLength(this.getConnection().getSendString().length());
		this.connection.setContinueSendString();
	}
	public void replyTotal(Interfaces.ICommunicatorSendTotal sendTotal) {
		this.setUserIndexAndPassword();
		this.connection.setSendString(sendTotal.output());
		this.connection.setSendLength(this.getConnection().getSendString().length());
		this.connection.setContinueSendString();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private boolean executeInServer() {
		if(!this.isLogin()) {
			return false;
		}
		if(!this.isUserIndexRight()) {
			return false;
		}
		if(!this.isPasswordRight()) {
			return false;
		}
		if(!this.isMechineIndexRight()) {
			return false;
		}
		if(!this.isDepotIndexRight()) {
			return false;
		}
		if(!this.isDataBaseIndexRight()) {
			return false;
		}
		return true;
	}
	private boolean executeInDepot() {
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean isLocal() {
		return this.machineIndex == Globals.Configurations.This_MachineIndex;
	}
	public boolean isServer() {
		return this.machineIndex == Globals.Configurations.Server_MachineIndex;
	}
	public boolean isInLAN() {
		if(Globals.Configurations.Server_MachineIndex == Globals.Configurations.This_MachineIndex) {
			return true;
		}
		for(int i=0; i<Globals.Datas.LANMachineIndexes.size(); i++) {
			if(this.machineIndex == Globals.Datas.LANMachineIndexes.get(i)) {
				return true;
			}
		}
		return false;
	}
	public boolean isConnected() {
		if(connection == null || !connection.isRunning()) {
			reply.setOK(false);
			reply.setFailedReason("Disconnected");
			return false;
		}
		return true;
	}
	public boolean isLogin() {
		if(this.connection.getUser() == null || this.connection.getUser().getIndex() <= 0) {
			reply.setOK(false);
			reply.setFailedReason("No Login");
			return false;
		}
		return true;
	}
	public boolean isUserIndexRight() {
		if(this.userIndex <= 0) {
			reply.setOK(false);
			reply.setFailedReason("Wrong User Index");
			return false;
		}
		if(this.connection.getUser().getIndex() != this.userIndex) {
			reply.setOK(false);
			reply.setFailedReason("Wrong User Index");
			return false;
		}
		return true;
	}
	public boolean isUserExist() {
		DataBaseManager.QueryCondition qc = new DataBaseManager.QueryCondition();
		qc.setItemName("Index");
		qc.setValue(String.valueOf(this.userIndex));
		this.user = Globals.Datas.DBManager.QueryUser(qc);
		if(this.user == null) {
			reply.setOK(false);
			reply.setFailedReason("Not Exist such User in DataBase");
			return false;
		}
		return true;
	}
	public boolean isPasswordRight() {
		if(this.password == null || this.password.length() == 0) {
			reply.setOK(false);
			reply.setFailedReason("Wrong Password");
			return false;
		}
		if(!this.password.equals(this.connection.getUser().getPassword())) {
			reply.setOK(false);
			reply.setFailedReason("Wrong Password");
			return false;
		}
		return true;
	}
	public boolean isMechineIndexRight() {
		if(this.machineIndex <= 0) {
			reply.setOK(false);
			reply.setFailedReason("Wrong Machine Index");
			return false;
		}
		DataBaseManager.QueryCondition qc = new DataBaseManager.QueryCondition();
		qc.setItemName("Index");
		qc.setValue(String.valueOf(this.machineIndex));
		this.machine = Globals.Datas.DBManager.QueryMachineInfo(qc);
		if(this.machine == null) {
			reply.setOK(false);
			reply.setFailedReason("Not Exist such Machine Index");
			return false;
		}
		return true;
	}
	public boolean isDepotIndexRight() {
		if(this.depotIndex <= 0) {
			reply.setOK(false);
			reply.setFailedReason("Wrong Depot Index");
			return false;
		}
		DataBaseManager.QueryCondition qc = new DataBaseManager.QueryCondition();
		qc.setItemName("Index");
		qc.setValue(String.valueOf(this.depotIndex));
		this.depot = Globals.Datas.DBManager.QueryDepotInfo(qc);
		if(this.depot == null) {
			reply.setOK(false);
			reply.setFailedReason("Not Exist such Depot Index");
			return false;
		}
		return true;
	}
	public boolean isDataBaseIndexRight() {
		if(this.dbIndex <= 0) {
			reply.setOK(false);
			reply.setFailedReason("Wrong DataBase Index");
			return false;
		}
		DataBaseManager.QueryCondition qc = new DataBaseManager.QueryCondition();
		qc.setItemName("Index");
		qc.setValue(String.valueOf(this.dbIndex));
		this.database = Globals.Datas.DBManager.QueryDataBaseInfo(qc);
		if(this.database == null) {
			reply.setOK(false);
			reply.setFailedReason("Not Exist such DataBase Index");
			return false;
		}
		return true;
	}
	public boolean isPriorityEnough(BasicEnums.UserPriority p) {
		return this.connection.getUser().getPriority().isEnough(p);
	}
	public boolean isLevelEnough(BasicEnums.UserLevel level) {
		return this.connection.getUser().getLevel().isEnough(level);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
