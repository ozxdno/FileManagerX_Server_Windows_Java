package Replies;

public class Unsupport extends Comman implements Interfaces.IReplies {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private String cmd;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setUnsupportCommand(String cmd) {
		if(cmd == null) {
			cmd = "NULL";
		}
		this.cmd = cmd;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public String getUnsupportCommand() {
		return this.cmd;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Unsupport() {
		initThis();
	}
	private void initThis() {
		super.setOK(false);
		super.setFailedReason("Unsupport");
		this.cmd = "";
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
		c.addToBottom(this.cmd);
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		
		BasicModels.Config c = new BasicModels.Config(in);
		this.cmd = c.fetchFirstString();
		if(!c.getIsOK()) { return null; }
		
		return c.output();
	}
	public void copyReference(Object o) {
		super.copyReference(o);
		Unsupport t = (Unsupport)o;
		this.cmd = t.cmd;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		Unsupport t = (Unsupport)o;
		this.cmd = new String(t.cmd);
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
