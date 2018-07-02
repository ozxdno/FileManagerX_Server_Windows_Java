package Replies;

public class UpdateDataBase extends Comman implements Interfaces.IReplies {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private BasicModels.DataBaseInfo dbInfo;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setDataBaseInfo(BasicModels.DataBaseInfo dbInfo) {
		if(dbInfo == null) {
			return false;
		}
		this.dbInfo = dbInfo;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public BasicModels.DataBaseInfo getDataBaseInfo() {
		return this.dbInfo;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public UpdateDataBase() {
		initThis();
	}
	private void initThis() {
		this.dbInfo = new BasicModels.DataBaseInfo();
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
		c.addToBottom(new BasicModels.Config(this.dbInfo.output()));
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		return this.dbInfo.input(in);
	}
	public void copyReference(Object o) {
		super.copyReference(o);
		UpdateDataBase qf = (UpdateDataBase)o;
		this.dbInfo = qf.dbInfo;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		UpdateDataBase qf = (UpdateDataBase)o;
		this.dbInfo.copyValue(qf.dbInfo);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
