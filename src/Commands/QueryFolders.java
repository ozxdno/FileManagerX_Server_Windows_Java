package Commands;

public class QueryFolders extends Comman implements Interfaces.ICommands {

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
	
	public Replies.QueryFolders getReply() {
		return (Replies.QueryFolders)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public QueryFolders() {
		initThis();
	}
	public QueryFolders(String command) {
		initThis();
		this.input(command);
	}
	private void initThis() {
		this.conditions = new DataBaseManager.QueryConditions();
		super.setReply(new Replies.QueryFolders());
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
		QueryFolders qf = (QueryFolders)o;
		super.copyReference(o);
		this.conditions = qf.conditions;
	}
	public void copyValue(Object o) {
		QueryFolders qf = (QueryFolders)o;
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
			return res;
		}
		else {
			boolean res = this.executeRemote();
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
		Interfaces.IDBManager dbmanager = Globals.Datas.DBManagers.fetchDataBaseIndex(this.getDataBaseIndex());
		BasicCollections.Folders folders = dbmanager.QueryFolders(getQueryConditions());
		Replies.QueryFolders qf = (Replies.QueryFolders)super.getReply();
		super.setUserIndexAndPassword();
		qf.setAmount(folders.size());
		if(qf.getAmount() == 0) {
			this.reply();
			return true;
		}
		
		for(int i=0; i<folders.size(); i++) {
			qf.setFolder(folders.getContent().get(i));
			this.reply();
		}
		
		return true;
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
			this.reply();
			return false;
		}
		
		Globals.Datas.Client.add(connection);
		connection.setContinueSendString();
		connection.setSendString(this.output());
		connection.setSendLength(connection.getSendString().length());
		connection.notify();
		
		
		Replies.QueryFolders qf = (Replies.QueryFolders)super.getReply();
		int finishedAmount = 0;
		while(true) {
			//super.waitForDepotReply();
			if(connection.isBusy()) {
				super.getReply().setOK(false);
				super.getReply().setFailedReason("Too long to get Reply of Target Depot");
				this.reply();
				return false;
			}
			
			this.getConnection().setSendString(connection.getReceiveString());
			this.getConnection().setSendLength(connection.getReceiveLength());
			this.getConnection().setContinueSendString();
			this.getConnection().notify();
			if(qf.input(this.getConnection().getReceiveString()) == null) {
				super.getReply().setOK(false);
				super.getReply().setFailedReason("Receive Wrong Info from Target Depot");
				this.reply();
				return false;
			}
			finishedAmount++;
			if(finishedAmount >= qf.getAmount()) {
				break;
			}
		}
		
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
