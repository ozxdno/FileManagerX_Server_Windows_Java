package Replies;

public class QueryDepots extends Comman implements Interfaces.IReplies {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private int amount;
	private BasicModels.DepotInfo depotInfo;
	private BasicCollections.DepotInfos depotInfos;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setAmount(int amount) {
		if(amount < 0) {
			return false;
		}
		this.amount = amount;
		return true;
	}
	public boolean setDepotInfo(BasicModels.DepotInfo depotInfo) {
		if(depotInfo == null) {
			return false;
		}
		this.depotInfo = depotInfo;
		return true;
	}
	public boolean setDepotInfos(BasicCollections.DepotInfos depotInfos) {
		if(depotInfos == null) {
			return false;
		}
		this.depotInfos = depotInfos;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public int getAmount() {
		return this.amount;
	}
	public BasicModels.DepotInfo getDepotInfo() {
		return this.depotInfo;
	}
	public BasicCollections.DepotInfos getDepotInfos() {
		return this.depotInfos;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public QueryDepots() {
		initThis();
	}
	private void initThis() {
		this.amount = 0;
		this.depotInfo = new BasicModels.DepotInfo();
		this.depotInfos = new BasicCollections.DepotInfos();
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
		c.addToBottom(new BasicModels.Config(this.depotInfo.output()));
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
		
		return this.depotInfo.input(c.output());
	}
	public void copyReference(Object o) {
		super.copyReference(o);
		QueryDepots qf = (QueryDepots)o;
		this.depotInfo = qf.depotInfo;
		this.depotInfos = qf.depotInfos;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		QueryDepots qf = (QueryDepots)o;
		this.depotInfo.copyValue(qf.depotInfo);
		this.depotInfos.copyValue(qf.depotInfos);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean execute(Interfaces.IConnection connection) {
		if(!this.isOK()) {
			return false;
		}
		if(this.depotInfos.size() >= this.amount) {
			return true;
		}
		
		BasicModels.DepotInfo newDepot = new BasicModels.DepotInfo();
		newDepot.copyValue(depotInfo);
		this.depotInfos.add(newDepot);
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
