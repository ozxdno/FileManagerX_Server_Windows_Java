package Replies;

public class UpdateMachines extends Comman implements Interfaces.IReplies {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private int amount;
	private BasicModels.MachineInfo machineInfo;
	private BasicCollections.MachineInfos machineInfos;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setAmount(int amount) {
		if(amount < 0) {
			return false;
		}
		this.amount = amount;
		return true;
	}
	public boolean setMachineInfo(BasicModels.MachineInfo machineInfo) {
		if(machineInfo == null) {
			return false;
		}
		this.machineInfo = machineInfo;
		return true;
	}
	public boolean setMachineInfos(BasicCollections.MachineInfos machineInfos) {
		if(machineInfos == null) {
			return false;
		}
		this.machineInfos = machineInfos;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public int getAmount() {
		return this.amount;
	}
	public BasicModels.MachineInfo getMachineInfo() {
		return this.machineInfo;
	}
	public BasicCollections.MachineInfos getMachineInfos() {
		return this.machineInfos;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public UpdateMachines() {
		initThis();
	}
	private void initThis() {
		this.amount = 0;
		this.machineInfo = new BasicModels.MachineInfo();
		this.machineInfos = new BasicCollections.MachineInfos();
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
		c.addToBottom(this.amount);
		c.addToBottom(new BasicModels.Config(this.machineInfo.output()));
		return c.output();
	}
	public String input(String in) {
		in = super.input(in);
		if(in == null) {
			return null;
		}
		
		BasicModels.Config c = new BasicModels.Config(in);
		this.amount = c.fetchFirstInt();
		if(!c.getIsOK()) { return null; }
		
		return this.machineInfo.input(c.output());
	}
	public void copyReference(Object o) {
		super.copyReference(o);
		UpdateMachines qf = (UpdateMachines)o;
		this.amount = qf.amount;
		this.machineInfo = qf.machineInfo;
		this.machineInfos = qf.machineInfos;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		UpdateMachines qf = (UpdateMachines)o;
		this.amount = qf.amount;
		this.machineInfo.copyValue(qf.machineInfo);
		this.machineInfos.copyValue(qf.machineInfos);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean execute(Interfaces.IConnection connection) {
		if(!this.isOK()) {
			return false;
		}
		if(this.machineInfos.size() >= this.amount) {
			return true;
		}
		
		BasicModels.MachineInfo newMachine = new BasicModels.MachineInfo();
		newMachine.copyValue(machineInfo);
		this.machineInfos.add(newMachine);
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
