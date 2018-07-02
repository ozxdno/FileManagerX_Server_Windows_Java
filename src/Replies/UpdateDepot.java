package Replies;

public class UpdateDepot extends Comman implements Interfaces.IReplies {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private BasicModels.DepotInfo depotInfo;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setDepotInfo(BasicModels.DepotInfo depotInfo) {
		if(depotInfo == null) {
			return false;
		}
		this.depotInfo = depotInfo;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public BasicModels.DepotInfo getDepotInfo() {
		return this.depotInfo;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public UpdateDepot() {
		initThis();
	}
	private void initThis() {
		this.depotInfo = new BasicModels.DepotInfo();
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
		c.addToBottom(this.isOK());
		c.addToBottom(this.getUserIndex());
		c.addToBottom(this.getPassword());
		c.addToBottom(this.getFailedReason());
		c.addToBottom(new BasicModels.Config(this.depotInfo.output()));
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		return this.depotInfo.input(in);
	}
	public void copyReference(Object o) {
		super.copyReference(o);
		UpdateDepot qf = (UpdateDepot)o;
		this.depotInfo = qf.depotInfo;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		UpdateDepot qf = (UpdateDepot)o;
		this.depotInfo.copyValue(qf.depotInfo);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
