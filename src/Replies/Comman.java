package Replies;

/**
 * 处理接收到的消息
 * 
 * @author ozxdno ; 
 * 
 * 如果需要添加对消息的处理，需要更改：
 * 
 * Package: Commands -> 在包Commands中添加同名发送处理类。
 * Package: Replies -> 在包Replies中添加同名消息处理类。
 * 
 * Class: Commands.Executor -> 在该类中注册。
 * Class: Replies.Executor -> 在该类中注册。
 * 
 * Class: SWRE -> 在该类中进行类型校验。
 *
 */
public class Comman implements Interfaces.IReplies {

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

	public Comman() {
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
	public String output(String cmdName) {
		BasicModels.Config c = new BasicModels.Config();
		c.setField(cmdName);
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
		Comman c = (Comman)o;
		this.ok = c.ok;
		this.userIndex = c.userIndex;
		this.password = c.password;
		this.failedReason = c.failedReason;
	}
	public void copyValue(Object o) {
		Comman c = (Comman)o;
		this.ok = c.ok;
		this.userIndex = c.userIndex;
		this.password = new String(c.password);
		this.failedReason = new String(c.failedReason);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean execute(Interfaces.IConnection connection) {
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
