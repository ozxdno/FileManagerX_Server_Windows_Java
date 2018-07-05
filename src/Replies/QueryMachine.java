package Replies;

public class QueryMachine extends Comman implements Interfaces.IReplies {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private BasicModels.MachineInfo machineInfo;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setMachineInfo(BasicModels.MachineInfo machineInfo) {
		if(machineInfo == null) {
			return false;
		}
		this.machineInfo = machineInfo;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public BasicModels.MachineInfo getMachineInfo() {
		return this.machineInfo;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public QueryMachine() {
		initThis();
	}
	private void initThis() {
		this.machineInfo = new BasicModels.MachineInfo();
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
		c.addToBottom(new BasicModels.Config(this.machineInfo.output()));
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		return this.machineInfo.input(in);
	}
	public void copyReference(Object o) {
		super.copyReference(o);
		QueryMachine qf = (QueryMachine)o;
		this.machineInfo = qf.machineInfo;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		QueryMachine qf = (QueryMachine)o;
		this.machineInfo.copyValue(qf.machineInfo);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
