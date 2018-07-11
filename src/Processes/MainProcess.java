package Processes;


public class MainProcess extends Thread implements Interfaces.IProcess {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private boolean finished;
	private boolean running;
	private boolean abort;
	private boolean stop;
	
	private long ticks;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean setTicks(long ticks) {
		this.ticks = ticks;
		return true;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
	
	public long getTicks() {
		return ticks;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public MainProcess() {
		initThis();
	}
	private void initThis() {
		this.finished = false;
		this.running = false;
		this.abort = false;
		this.stop = false;
		
		this.setName("Process Main");
	}
	public void run() {
		
		while(Globals.Configurations.Restart) {
			
			Globals.Configurations.Restart = false;
			this.finished = false;
			this.running = true;
			this.abort = false;
			this.stop = false;
			
			boolean operateOK = true;
			boolean cfgOK = true;
			
			//////////////////////////////////////////// INIT ///////////////////////////////////////////////
			
			// create other folders and files
			Tools.Pathes.createAll(Globals.Configurations.StartType);
			
			// create and load CFG file
			if(operateOK) {
				cfgOK &= Tools.CFGFile.createCFG();
				operateOK &= cfgOK;
			}
			if(operateOK) {
				cfgOK &= Tools.CFGFile.loadCFG();
				operateOK &= cfgOK;
			}
			
			// Set Form Title
			if(operateOK && Globals.Datas.Form_Main != null) {
				Globals.Datas.Form_Main.setTitle(
						"FileManagerX - " + 
						Globals.Configurations.StartType.toString() + " : " + 
						Globals.Datas.ThisUser.getNickName()
						);
			}
			
			// check server
			if(operateOK && Globals.Configurations.StartType.equals(BasicEnums.StartType.Server)) {
				Interfaces.IServerChecker sc = Factories.ServerCheckerFactory.createServerChecker();
				sc.setDBManager(Globals.Datas.DBManager);
				sc.check();
			}
			
			// check local depots
			if(operateOK) {
				for(Interfaces.IDBManager dbm : Globals.Datas.DBManagers.getContent()) {
					Interfaces.IDepotChecker dc = Factories.DepotCheckerFactory.createDepotChecker();
					dc.initialize(dbm);
					dc.check();
				}
			}
			
			// start server
			if(operateOK) {
				Globals.Datas.Server.initialize(Globals.Datas.ThisMachine);
				Globals.Datas.Server.connect();
				operateOK &= Globals.Datas.Server.isRunning();
			}
			
			// delete log file
			if(operateOK) {
				Globals.Datas.Errors.deleteAgoLogs(30);
			}
			
			// save CFG before execute tasks
			if(cfgOK) {
				Tools.CFGFile.saveCFG();
			}
			
			// save Server DataBase before execute tasks
			if(operateOK) {
				if(Globals.Configurations.StartType.equals(BasicEnums.StartType.Server) && Globals.Datas.DBManager != null &&
						Globals.Datas.DBManager.getDBInfo().getType().equals(BasicEnums.DataBaseType.TXT)) {
					Globals.Datas.DBManager.disconnect();
					Globals.Datas.DBManager.connect();
				}
			}
			
			// save DataBases before execute tasks
			if(operateOK) {
				for(Interfaces.IDBManager dbm : Globals.Datas.DBManagers.getContent()) {
					if(dbm.getDBInfo().getType().equals(BasicEnums.DataBaseType.TXT)) {
						dbm.disconnect();
						dbm.connect();
					}
				}
			}
			
			// save Errors before execute tasks
			Globals.Datas.Errors.save();
			
			//////////////////////////////////////////// TASK ///////////////////////////////////////////////
			
			// tasks
			if(operateOK) {
				while(!this.abort) {
					if(!Globals.Datas.Server.isRunning()) {
						BasicEnums.ErrorType.COMMUNICATOR_CONNECTION_CLOSED.register("Server[This Machine] Closed");
						break;
					}
					
					if(!Globals.Configurations.IsServer && !Globals.Datas.ServerConnection.isRunning()) {
						BasicEnums.ErrorType.COMMUNICATOR_CONNECTION_CLOSED.register("Server[Remote Machine] Closed");
						break;
					}
					
					Tools.Time.sleepUntil(1000);
					
					Globals.Datas.Errors.save(100);
					
					Globals.Datas.Operators.removeIdleOperator();
				}
			}
			
			//////////////////////////////////////////// EXIT ///////////////////////////////////////////////
			
			// save CFG
			if(cfgOK) {
				Tools.CFGFile.saveCFG();
			}
			
			// save Errors
			Globals.Datas.Errors.save();
			
			// save Server DataBase before execute tasks
			if(operateOK) {
				if(Globals.Configurations.StartType.equals(BasicEnums.StartType.Server) && Globals.Datas.DBManager != null &&
						Globals.Datas.DBManager.getDBInfo().getType().equals(BasicEnums.DataBaseType.TXT)) {
					Globals.Datas.DBManager.disconnect();
					Globals.Datas.DBManager.connect();
				}
			}
			
			// save DataBases
			if(operateOK) {
				for(Interfaces.IDBManager dbm : Globals.Datas.DBManagers.getContent()) {
					if(dbm.getDBInfo().getType().equals(BasicEnums.DataBaseType.TXT)) {
						dbm.disconnect();
					}
				}
			}
			
			// Close Form
			if(Globals.Datas.Form_Main != null) {
				Globals.Datas.Form_Main.dispose();
			}
			
			// Close Server
			Globals.Datas.Server.disconnect();
			
			// Close Connection
			Globals.Datas.ServerConnection.disconnect();
			Globals.Datas.Client.removeAllConnections();
			
			// end
			this.finished = true;
			this.running = false;
			
			// ÑÓ³Ù¿ªÆô
			if(Globals.Configurations.Restart) {
				Tools.Time.sleepUntil(Globals.Configurations.TimeForRestart);
			}
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean initialize(Object infos) {
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
}
