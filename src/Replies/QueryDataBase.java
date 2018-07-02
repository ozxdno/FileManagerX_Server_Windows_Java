package Replies;

public class QueryDataBase extends Comman implements Interfaces.IReplies {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private BasicModels.DataBaseInfo database;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setDataBaseInfo(BasicModels.DataBaseInfo database) {
		if(database == null) {
			return false;
		}
		this.database = database;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public BasicModels.DataBaseInfo getDataBaseInfo() {
		return this.database;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public QueryDataBase() {
		initThis();
	}
	private void initThis() {
		this.database = new BasicModels.DataBaseInfo();
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
		c.addToBottom(new BasicModels.Config(this.database.output()));
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		return this.database.input(in);
	}
	public void copyReference(Object o) {
		super.copyReference(o);
		QueryDataBase qf = (QueryDataBase)o;
		this.database = qf.database;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		QueryDataBase qf = (QueryDataBase)o;
		this.database.copyValue(qf.database);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
