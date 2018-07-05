package Replies;

/**
 * ������յ�����Ϣ
 * 
 * @author ozxdno ; 
 * 
 * �����Ҫ��Ӷ���Ϣ�Ĵ�����Ҫ���ģ�
 * 
 * Package: Commands -> �ڰ�Commands�����ͬ�����ʹ����ࡣ
 * Package: Replies -> �ڰ�Replies�����ͬ����Ϣ�����ࡣ
 * 
 * Class: Commands.Executor -> �ڸ�����ע�ᡣ
 * Class: Replies.Executor -> �ڸ�����ע�ᡣ
 * 
 * Class: SWRE -> �ڸ����н�������У�顣
 *
 */
public class Comman implements Interfaces.IReplies {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	Interfaces.IBasicMessagePackage bmp;
	
	private boolean ok;
	private String failedReason;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setBasicMessagePackage(Interfaces.IBasicMessagePackage bmp) {
		if(bmp == null) {
			return false;
		}
		this.bmp = bmp;
		return true;
	}
	public boolean setOK(boolean ok) {
		this.ok = ok;
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

	public Interfaces.IBasicMessagePackage getBasicMessagePackage() {
		return this.bmp;
	}
	public boolean isOK() {
		return this.ok;
	}
	public String getFailedReason() {
		return this.failedReason;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Comman() {
		initThis();
	}
	private void initThis() {
		bmp = Factories.CommunicatorFactory.createBasicMessagePackage();
		ok = true;
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
		c.addToBottom(new BasicModels.Config(this.bmp.output()));
		c.addToBottom(ok);
		c.addToBottom(failedReason);
		return c.output();
	}
	public String output(String cmdName) {
		BasicModels.Config c = new BasicModels.Config();
		c.setField(cmdName);
		c.addToBottom(new BasicModels.Config(this.bmp.output()));
		c.addToBottom(ok);
		c.addToBottom(failedReason);
		return c.output();
	}
	public String input(String in) {
		in = this.bmp.input(in);
		if(in == null) {
			return null;
		}
		
		BasicModels.Config c = new BasicModels.Config(in);
		this.ok = c.fetchFirstBoolean();
		if(!c.getIsOK()) { return null; }
		this.failedReason = c.fetchFirstString();
		if(!c.getIsOK()) { return null; }
		return c.output();
	}
	public void copyReference(Object o) {
		Comman c = (Comman)o;
		this.bmp = c.bmp;
		this.ok = c.ok;
		this.failedReason = c.failedReason;
	}
	public void copyValue(Object o) {
		Comman c = (Comman)o;
		this.bmp.copyValue(c.bmp);
		this.ok = c.ok;
		this.failedReason = new String(c.failedReason);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean execute(Interfaces.IConnection connection) {
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean isArriveTargetMachine() {
		return this.bmp.getSourMachineIndex() == Globals.Configurations.This_MachineIndex;
	}
	public boolean isArriveSourMachine() {
		return this.bmp.getSourMachineIndex() == Globals.Configurations.This_MachineIndex;
	}
	public boolean isArriveDestMachine() {
		return this.bmp.getDestMachineIndex() == Globals.Configurations.This_MachineIndex;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
