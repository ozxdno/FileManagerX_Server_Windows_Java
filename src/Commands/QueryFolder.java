package Commands;

public class QueryFolder extends Comman implements Interfaces.ICommands {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private DataBaseManager.QueryConditions conditions;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setQueryConditions(DataBaseManager.QueryConditions conditions) {
		this.conditions = conditions;
		return true;
	}
	public boolean setQueryCondition(DataBaseManager.QueryCondition condition) {
		DataBaseManager.QueryConditions qcs = new DataBaseManager.QueryConditions();
		qcs.add(condition);
		this.conditions = qcs;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public DataBaseManager.QueryConditions getQueryConditions() {
		return this.conditions;
	}
	
	public Replies.QueryFolder getReply() {
		return (Replies.QueryFolder)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public QueryFolder() {
		initThis();
	}
	public QueryFolder(String command) {
		initThis();
		this.input(command);
	}
	private void initThis() {
		this.conditions = new DataBaseManager.QueryConditions();
		super.setReply(new Replies.QueryFolder());
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void clear() {
		super.clear();
		initThis();
	}
	public String toString() {
		return this.output();
	}
	public String output() {
		return super.output() + "|" + this.conditions.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		return this.conditions.input(in);
	}
	public void copyReference(Object o) {
		QueryFolder qf = (QueryFolder)o;
		super.copyReference(o);
		this.conditions = qf.conditions;
	}
	public void copyValue(Object o) {
		QueryFolder qf = (QueryFolder)o;
		super.copyValue(o);
		this.conditions.copyValue(qf.conditions);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean execute() {
		if(!super.execute()) {
			this.reply();
			return false;
		}
		if(this.conditions == null) {
			super.getReply().setOK(false);
			super.getReply().setFailedReason("Wrong Query Conditions");
			this.reply();
			return false;
		}
		
		if(super.isLocal()) {
			boolean res = this.executeLocal();
			this.reply();
			return res;
		}
		else {
			boolean res = this.executeRemote();
			this.reply();
			return res;
		}
		
	}
	public void reply() {
		this.getConnection().setSendString(this.getReply().output());
		this.getConnection().setSendLength(this.getConnection().getSendString().length());
		this.getConnection().setContinueSendString();
		this.getConnection().notify();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private boolean executeLocal() {
		Replies.QueryFolder qf = (Replies.QueryFolder)super.getReply();
		Interfaces.IDBManager dbmanager = Globals.Datas.DBManagers.fetchDataBaseIndex(this.getDataBaseIndex());
		qf.setFolder(dbmanager.QueryFolder(getQueryConditions()));
		
		super.setUserIndexAndPassword();
		return qf.getFolder() != null;
	}
	private boolean executeRemote() {
		Interfaces.IClientConnection connection = new Communicator.ClientConnection();
		connection.setServerMachineInfo(super.getMachineInfo());
		connection.setClientMachineInfo(super.getConnection().getServerMachineInfo());
		connection.setSocket();
		connection.connect();
		if(!connection.isRunning()) {
			super.getReply().setOK(false);
			super.getReply().setFailedReason("Connect to Depot Failed");
			return false;
		}
		
		Globals.Datas.Client.add(connection);
		connection.setContinueSendString();
		connection.setSendString(this.output());
		connection.setSendLength(connection.getSendString().length());
		connection.notify();
		
		//super.waitForDepotReply();
		if(connection.isBusy()) {
			super.getReply().setOK(false);
			super.getReply().setFailedReason("Too long to get Reply of Target Depot");
			return false;
		}
		
		Replies.QueryFolder qf = (Replies.QueryFolder)super.getReply();
		this.setUserIndexAndPassword();
		connection.disconnect();
		return qf.input(connection.getReceiveString()) != null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
