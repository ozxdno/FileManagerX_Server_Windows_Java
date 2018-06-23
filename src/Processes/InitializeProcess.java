package Processes;

public class InitializeProcess extends Thread implements IProcess {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private BasicModels.MachineInfo thisMachine;
	private BasicModels.DataBaseInfo configurations;
	private boolean finished;
	private boolean running;
	private boolean abort;
	private boolean stop;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setThisMachineInfo(BasicModels.MachineInfo thisMachine) {
		if(thisMachine == null) {
			return false;
		}
		this.thisMachine = thisMachine;
		return true;
	}
	public boolean setConfigurations(BasicModels.DataBaseInfo configurations) {
		if(configurations == null) {
			return false;
		}
		this.configurations = configurations;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public BasicModels.MachineInfo getThisMachineInfo() {
		return this.thisMachine;
	}
	public BasicModels.DataBaseInfo getConfigurations () {
		return this.configurations;
	}
	
	public boolean isFinished() {
		return this.finished;
	}
	public boolean isRunning() {
		return this.running;
	}
	public boolean isAbort() {
		return this.abort;
	}
	public boolean isStop() {
		return this.stop;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public InitializeProcess() {
		initThis();
	}
	public InitializeProcess(BasicModels.DataBaseInfo configurations) {
		initThis();
		this.setConfigurations(configurations);
	}
	public InitializeProcess(BasicModels.MachineInfo thisMachine, BasicModels.DataBaseInfo configurations) {
		initThis();
		this.setThisMachineInfo(thisMachine);
		this.setConfigurations(configurations);
	}
	private void initThis() {
		if(this.thisMachine == null) {
			this.thisMachine = new BasicModels.MachineInfo();
		}
		this.thisMachine.clear();
		if(this.configurations == null) {
			this.configurations = new BasicModels.DataBaseInfo();
		}
		this.configurations.clear();
		
		this.finished = false;
		this.running = false;
		this.abort = false;
		this.stop = false;
		
		this.setName("Process Initialize");
	}
	public void run() {
		this.finished = false;
		this.running = true;
		this.abort = false;
		this.stop = false;
		
		// Step 1: load file: FileManagerX.cfg
		
		Tools.CFGFile.createCFG();
		Tools.CFGFile.loadCFG();
		
		// Step 2: check depot
		
		Interfaces.IDepotChecker dc = new DepotChecker.DepotChecker();
		dc.initialize(Globals.Datas.DBManagers.searchDepotName("DepotA"));
		dc.check();
		
		// end
		this.finished = true;
		this.running = false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean initialize(Object infos) {
		Object[] os = (Object[])infos;
		this.setThisMachineInfo((BasicModels.MachineInfo)os[0]);
		this.setConfigurations((BasicModels.DataBaseInfo)os[1]);
		
		return true;
	}
	
	public boolean startProcess() {
		if(this.finished || (!this.finished && !this.stop && !this.running)) {
			this.start();
			return true;
		}
		if(this.stop) {
			return this.continueProcess();
		}
		if(this.running) {
			return true;
		}
		
		return true;
	}
	public boolean stopProcess() {
		this.stop = true;
		return true;
	}
	public boolean continueProcess() {
		this.stop = false;
		return true;
	}
	public boolean restartProcess() {
		this.abort = true;
		while(this.running);
		return this.startProcess();
	}
	public boolean exitProcess() {
		this.abort = true;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
