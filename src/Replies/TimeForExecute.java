package Replies;

/**
 * 接收原来的等待时间，方便复原。
 * 
 * @author ozxdno
 *
 */
public class TimeForExecute extends Comman implements Interfaces.IReplies {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private long receiveWaitTicks;
	private long sendWaitTicks;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setReceiveWaitTicks(long receiveWaitTicks) {
		if(receiveWaitTicks < 0) {
			return false;
		}
		this.receiveWaitTicks = receiveWaitTicks;
		return true;
	}
	public boolean setSendWaitTicks(long sendWaitTicks) {
		if(sendWaitTicks < 0) {
			return false;
		}
		this.sendWaitTicks = sendWaitTicks;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public long getReceiveWaitTicks() {
		return this.receiveWaitTicks;
	}
	public long getSendWaitTicks() {
		return this.sendWaitTicks;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public TimeForExecute() {
		initThis();
	}
	private void initThis() {
		this.receiveWaitTicks = Globals.Configurations.TimeForCommandReceive;
		this.sendWaitTicks = Globals.Configurations.TimeForCommandSend;
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
		c.addToBottom(this.receiveWaitTicks);
		c.addToBottom(this.sendWaitTicks);
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		
		BasicModels.Config c = new BasicModels.Config(in);
		this.receiveWaitTicks = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		this.sendWaitTicks = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		
		return c.output();
	}
	public void copyReference(Object o) {
		super.copyReference(o);
		TimeForExecute t = (TimeForExecute)o;
		this.receiveWaitTicks = t.receiveWaitTicks;
		this.sendWaitTicks = t.sendWaitTicks;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		TimeForExecute t = (TimeForExecute)o;
		this.receiveWaitTicks = t.receiveWaitTicks;
		this.sendWaitTicks = t.sendWaitTicks;
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
