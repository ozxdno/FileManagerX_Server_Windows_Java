package Replies;

public class UpdateDataBases extends Comman implements Interfaces.IReplies {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private int amount;
	private BasicModels.DataBaseInfo dbInfo;
	private BasicCollections.DataBaseInfos dbInfos;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setAmount(int amount) {
		if(amount < 0) {
			return false;
		}
		this.amount = amount;
		return true;
	}
	public boolean setDBtInfo(BasicModels.DataBaseInfo dbInfo) {
		if(dbInfo == null) {
			return false;
		}
		this.dbInfo = dbInfo;
		return true;
	}
	public boolean setDBInfos(BasicCollections.DataBaseInfos dbInfos) {
		if(dbInfos == null) {
			return false;
		}
		this.dbInfos = dbInfos;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public int getAmount() {
		return this.amount;
	}
	public BasicModels.DataBaseInfo getDBInfo() {
		return this.dbInfo;
	}
	public BasicCollections.DataBaseInfos getDBInfos() {
		return this.dbInfos;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public UpdateDataBases() {
		initThis();
	}
	private void initThis() {
		this.dbInfo = new BasicModels.DataBaseInfo();
		this.dbInfos = new BasicCollections.DataBaseInfos();
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
		BasicModels.Config c = new BasicModels.Config();
		c.setField(this.getClass().getSimpleName());
		c.addToBottom(new BasicModels.Config(super.output()));
		c.addToBottom(this.amount);
		c.addToBottom(new BasicModels.Config(this.dbInfo.output()));
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		
		BasicModels.Config c = new BasicModels.Config(in);
		this.amount = c.fetchFirstInt();
		if(!c.getIsOK()) { return null; }
		
		return this.dbInfo.input(c.output());
	}
	public void copyReference(Object o) {
		super.copyReference(o);
		UpdateDataBases qf = (UpdateDataBases)o;
		this.amount = qf.amount;
		this.dbInfo = qf.dbInfo;
		this.dbInfos = qf.dbInfos;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		UpdateDataBases qf = (UpdateDataBases)o;
		this.amount = qf.amount;
		this.dbInfo.copyValue(qf.dbInfo);
		this.dbInfos.copyValue(qf.dbInfos);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean execute(Interfaces.IConnection connection) {
		if(!this.isOK()) {
			return false;
		}
		if(this.dbInfos.size() >= this.amount) {
			return true;
		}
		
		BasicModels.DataBaseInfo newDB = new BasicModels.DataBaseInfo();
		newDB.copyValue(dbInfo);
		this.dbInfos.add(newDB);
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
