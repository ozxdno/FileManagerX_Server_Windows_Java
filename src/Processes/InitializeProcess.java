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
		
		boolean operateOK = true;
		Globals.Configurations.DBConfigrations.copyReference(configurations);
		Globals.Configurations.ThisMachine.copyReference(thisMachine);
		
		// Step 1: add config infos
		
		if(this.configurations.getMachineInfo().getUrl().length() == 0) {
			BasicModels.Error e = new BasicModels.Error();
			e.setType(BasicEnums.ErrorType.CANNOT_FIND_CONFIGURATIONS_DATABSE);
			e.setLevel(BasicEnums.ErrorLevel.Error);
			e.setReason("Configurations DataBase Url is Empty");
			e.setTip("@Processes.InitializeProcess");
			e.setDetail("reset Configurations use method: Processes.InitializeProcess.setConfigurations");
			e.print();
			Globals.Datas.Errors.add(e);
			this.finished = true;
			this.running = false;
			return;
		}
		DataBaseManager.IDBManager dbmanager = new DataBaseManager.DBManager();
		operateOK = dbmanager.initialize(configurations);
		if(!operateOK) {
			BasicModels.Error e = new BasicModels.Error();
			e.setType(BasicEnums.ErrorType.DATABASE_INITIALIZE_FAILED);
			e.setLevel(BasicEnums.ErrorLevel.Error);
			e.setReason("DataBase Initialize failed");
			e.setTip("@Processes.InitializeProcess");
			e.setDetail("see method: DataBaseManager.DBManager.initialize");
			e.print();
			Globals.Datas.Errors.add(e);
			this.finished = true;
			this.running = false;
			return;
		}
		operateOK = dbmanager.connect();
		if(!operateOK) {
			BasicModels.Error e = new BasicModels.Error();
			e.setType(BasicEnums.ErrorType.DATABASE_CONNECT_FAILED);
			e.setLevel(BasicEnums.ErrorLevel.Error);
			e.setReason("Can Not Connect to DataBase");
			e.setTip("@Processes.InitializeProcess");
			e.setDetail("see method: DataBaseManager.DBManager.connect");
			e.print();
			Globals.Datas.Errors.add(e);
			this.finished = true;
			this.running = false;
			return;
		}
		operateOK = dbmanager.QueryConfigurations();
		if(!operateOK) {
			BasicModels.Error e = new BasicModels.Error();
			e.setType(BasicEnums.ErrorType.WRONG_CONFIGURATION_INFOS);
			e.setLevel(BasicEnums.ErrorLevel.Error);
			e.setReason("the Form of the Configuration info is Not right");
			e.setTip("@Processes.InitializeProcess");
			e.setDetail("see Configuration info : Globals.Configurations, see query method : DataBaseManager.DBManager.QueryConfigurations");
			e.print();
			Globals.Datas.Errors.add(e);
			this.finished = true;
			this.running = false;
			return;
		}
		
		// Step2: run Server of Communicator
		if(this.thisMachine.getIp().length() == 0 || this.thisMachine.getPort() < 0) {
			BasicModels.Error e = new BasicModels.Error();
			e.setType(BasicEnums.ErrorType.WRONG_LOCAL_MACHINE_INFOS);
			e.setLevel(BasicEnums.ErrorLevel.Error);
			e.setReason("the local machine infos of InitializeProcess.ThisMachine is Wrong");
			e.setTip("@Processes.InitializeProcess");
			e.setDetail("reset ThisMachine infos use: InitializeProcess.setThisMachineInfo");
			e.print();
			Globals.Datas.Errors.add(e);
			this.finished = true;
			this.running = false;
			return;
		}
		Communicator.IServerScanner server = new Communicator.ServerTCP();
		operateOK = server.initialize(thisMachine);
		if(!operateOK) {
			BasicModels.Error e = new BasicModels.Error();
			e.setType(BasicEnums.ErrorType.COMMUNICATOR_SERVER_INITIALIZE_FAILED);
			e.setLevel(BasicEnums.ErrorLevel.Error);
			e.setReason("Server Initialie Failed");
			e.setTip("@Processes.InitializeProcess");
			e.setDetail("see: Communicator.ServerTCP.initialize");
			e.print();
			Globals.Datas.Errors.add(e);
			this.finished = true;
			this.running = false;
			return;
		}
		operateOK = server.connect();
		if(!operateOK) {
			BasicModels.Error e = new BasicModels.Error();
			e.setType(BasicEnums.ErrorType.COMMUNICATOR_SERVER_CREATE_CONNECTION_FAILED);
			e.setLevel(BasicEnums.ErrorLevel.Error);
			e.setReason("Server Create Connection Failed");
			e.setTip("@Processes.InitializeProcess");
			e.setDetail("see: Communicator.ServerTCP.connect");
			e.print();
			Globals.Datas.Errors.add(e);
			this.finished = true;
			this.running = false;
			return;
		}
		
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
