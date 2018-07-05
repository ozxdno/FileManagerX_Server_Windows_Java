package Replies;

public class QueryDepot extends Comman implements Interfaces.IReplies {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private BasicModels.DepotInfo depot;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setDepotInfo(BasicModels.DepotInfo depot) {
		if(depot == null) {
			return false;
		}
		this.depot = depot;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public BasicModels.DepotInfo getDepotInfo() {
		return this.depot;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public QueryDepot() {
		initThis();
	}
	private void initThis() {
		this.depot = new BasicModels.DepotInfo();
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
		c.addToBottom(new BasicModels.Config(this.depot.output()));
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		return this.depot.input(in);
	}
	public void copyReference(Object o) {
		super.copyReference(o);
		QueryDepot qf = (QueryDepot)o;
		this.depot = qf.depot;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		QueryDepot qf = (QueryDepot)o;
		this.depot.copyValue(qf.depot);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
