package Replies;

public class RegisterUser extends Comman implements Interfaces.IReplies {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private boolean ok;
	private long userIndex;
	private String password;
	private String failedReason;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setOK(boolean ok) {
		this.ok = ok;
		return true;
	}
	public boolean setUserIndex(long userIndex) {
		this.userIndex = userIndex;
		return true;
	}
	public boolean setPassword(String password) {
		this.password = password;
		return true;
	}
	public boolean setFailedReason(String failedReason) {
		if(failedReason == null) {
			return false;
		}
		this.failedReason = failedReason;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean isOK() {
		return this.ok;
	}
	public long getUserIndex() {
		return this.userIndex;
	}
	public String getPassword() {
		return this.password;
	}
	public String getFailedReason() {
		return this.failedReason;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public RegisterUser() {
		initThis();
	}
	private void initThis() {
		ok = true;
		userIndex = -1;
		password = "";
		failedReason = "";
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void clear() {
		initThis();
	}
	public String toString() {
		return this.output();
	}
	public String output() {
		BasicModels.Config c = new BasicModels.Config();
		c.setField(this.getClass().getSimpleName());
		c.addToBottom(ok);
		c.addToBottom(userIndex);
		c.addToBottom(password);
		c.addToBottom(failedReason);
		return c.output();
	}
	public String input(String in) {
		BasicModels.Config c = new BasicModels.Config(in);
		this.ok = c.fetchFirstBoolean();
		if(!c.getIsOK()) { return null; }
		this.userIndex = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		this.password = c.fetchFirstString();
		if(!c.getIsOK()) { return null; }
		this.failedReason = c.fetchFirstString();
		if(!c.getIsOK()) { return null; }
		return c.output();
	}
	public void copyReference(Object o) {
		RegisterUser ru = (RegisterUser)o;
		this.ok = ru.ok;
		this.userIndex = ru.userIndex;
		this.password = ru.password;
		this.failedReason = ru.failedReason;
	}
	public void copyValue(Object o) {
		RegisterUser ru = (RegisterUser)o;
		this.ok = ru.ok;
		this.userIndex = ru.userIndex;
		this.password = new String(ru.password);
		this.failedReason = new String(ru.failedReason);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean execute(Interfaces.IClientConnection connection) {
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
