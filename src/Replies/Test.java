package Replies;

public class Test extends Comman implements Interfaces.IReplies {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private String test;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setTestString(String test) {
		if(test == null) {
			return false;
		}
		this.test = test;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public String getTestString() {
		return this.test;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Test() {
		initThis();
	}
	private void initThis() {
		this.test = "";
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
		c.addToBottom(this.test);
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		
		BasicModels.Config c = new BasicModels.Config(in);
		this.test = c.fetchFirstString();
		if(!c.getIsOK()) { return null; }
		
		return c.output();
	}
	public void copyReference(Object o) {
		super.copyReference(o);
		Test t = (Test)o;
		this.test = t.test;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		Test t = (Test)o;
		this.test = new String(t.test);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean execute(Interfaces.IConnection connection) {
		if(!this.isOK()) {
			return false;
		}
		
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
