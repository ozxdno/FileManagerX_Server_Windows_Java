package Replies;


public class LoginIndex extends Comman implements Interfaces.IReplies {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private int index;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setLoginIndex(int index) {
		this.index = index;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public int getLoginIndex() {
		return this.index;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public LoginIndex() {
		initThis();
	}
	private void initThis() {
		this.index = 0;
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
		c.addToBottom(this.index);
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		
		BasicModels.Config c = new BasicModels.Config(in);
		this.index = c.fetchFirstInt();
		if(!c.getIsOK()) { return null; }
		
		return c.output();
	}
	public void copyReference(Object o) {
		super.copyReference(o);
		LoginIndex t = (LoginIndex)o;
		this.index = t.index;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		LoginIndex t = (LoginIndex)o;
		this.index = t.index;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean execute(Interfaces.IConnection connection) {
		if(!this.isOK()) {
			return false;
		}
		
		connection.setIndex(index);
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
