package com.FileManagerX.Processes;

import com.FileManagerX.Interfaces.*;
import com.FileManagerX.BasicEnums.*;
import com.FileManagerX.Globals.*;

public class Main extends Thread implements IProcess {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private boolean finished;
	private boolean running;
	private boolean abort;
	private boolean stop;
	
	private boolean anyError;
	
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

	public Main() {
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
		
		com.FileManagerX.Globals.Datas.Processes.add(this);
		
		while(Configurations.Restart) {
			
			Configurations.Restart = false;
			
			this.parepare();
			
			if(com.FileManagerX.Globals.Configurations.IsServer) {
				
			}
			else {
				com.FileManagerX.Commands.NewChannel nc = new com.FileManagerX.Commands.NewChannel();
				nc.setThis(com.FileManagerX.Globals.Datas.ServerConnection);
				nc.send();
				nc.receive();
				
				com.FileManagerX.Commands.Input in = new com.FileManagerX.Commands.Input();
				com.FileManagerX.Interfaces.IIOPackage iop =
						com.FileManagerX.Factories.CommunicatorFactory.createIOP();
				iop.setThis(
						com.FileManagerX.BasicEnums.IOPType.READ_DEST,
						true,
						true,
						"D:\\Space_For_Media\\Pictures\\FMX_Test_Depot_H\\a2.jpg",
						"D:\\Space_For_Media\\Pictures\\FMX_Test_Depot_H\\a1.jpg",
						0,
						3096,
						"");
				in.setThis(iop, -1, com.FileManagerX.Globals.Datas.ServerConnection);
				in.send();
			}
			
			this.task();
			
			
			this.finish();
			
			// ÑÓ³Ù¿ªÆô
			if(Configurations.Restart) {
				com.FileManagerX.Tools.Time.sleepUntil(Configurations.TimeForRestart);
			}
		}
	}
	private boolean parepare() {
	
		this.finished = false;
		this.running = true;
		this.abort = false;
		this.stop = false;
		this.anyError = false;

		// build connection
		this.anyError = !com.FileManagerX.Factories.CommunicatorFactory.createRunningClientConnection();
		
		// create other folders and files
		com.FileManagerX.Tools.Pathes.createAll(Configurations.StartType);
		
		// load CFG file
		if(!this.anyError) {
			this.anyError |= !com.FileManagerX.Globals.Datas.CFG.load();
		}
		
		// Set Form Title
		if(!this.anyError && Datas.Form_Test != null) {
			Datas.Form_Test.setTitle(
					"FileManagerX" + 
					" [" + Configurations.This_MachineIndex + "]" +
					" - " + 
					Configurations.StartType.toString() + " : " + 
					Datas.ThisUser.getNickName()
					);
		}
		
		// check server
		if(!this.anyError && Configurations.StartType.equals(StartType.Server)) {
			IDBChecker dbc = Datas.DBManager.getDBInfo().getChecker();
			dbc.checkServer();
		}
		
		// check local depots
		if(!this.anyError) {
			for(IDBManager dbm : Datas.DBManagers.getContent()) {
				IDBChecker dbc = dbm.getDBInfo().getChecker();
				dbc.checkNotServer();
			}
		}
		
		// add server dbm to dbms
		if(com.FileManagerX.Globals.Configurations.IsServer) {
			Datas.DBManagers.add(Datas.DBManager);
		}
		
		// start server
		if(!this.anyError) {
			Datas.Scanner.setServerMachineInfo(Datas.ThisMachine);
			Datas.Scanner.setSocket(com.FileManagerX.BasicEnums.SocketType.IPV4_TCP);
			Datas.Scanner.connect();
			this.anyError |= !Datas.Scanner.isRunning();
		}
		
		// delete log file
		if(!this.anyError) {
			Datas.Errors.deleteAgoLogs(30);
		}
		
		// delete rec file
		if(!this.anyError) {
			Datas.Records.deleteAgoRecords(30);
		}
		
		// save CFG before execute tasks
		if(!com.FileManagerX.Globals.Datas.CFG.isErrorOccurs()) {
			com.FileManagerX.Globals.Datas.CFG.save();
		}
		
		// save Server DataBase before execute tasks
		if(!this.anyError) {
			if(Configurations.StartType.equals(StartType.Server) && Datas.DBManager != null &&
					Datas.DBManager.getDBInfo().getType().equals(DataBaseType.TXT)) {
				Datas.DBManager.disconnect();
				Datas.DBManager.connect();
			}
		}
		
		// save DataBases before execute tasks
		if(!this.anyError) {
			for(IDBManager dbm : Datas.DBManagers.getContent()) {
				if(dbm.getDBInfo().getType().equals(DataBaseType.TXT)) {
					dbm.disconnect();
					dbm.connect();
				}
			}
		}
		
		// save Errors before execute tasks
		Datas.Errors.save();
		
		return !this.anyError;
	}
	private boolean task() {
		
		if(this.anyError) {
			return false;
		}
		
		while(!this.abort) {
			if(!Datas.Scanner.isRunning()) {
				ErrorType.COMMUNICATOR_CONNECTION_CLOSED.register("Server[This Machine] Closed");
				break;
			}
			
			if(!Configurations.IsServer && !Datas.ServerConnection.isRunning()) {
				ErrorType.COMMUNICATOR_CONNECTION_CLOSED.register("Server[Remote Machine] Closed");
				break;
			}
			
			com.FileManagerX.Tools.Time.sleepUntil(1*1000);
			
			Datas.Records.save(100);
			
			Datas.Errors.save(100);
			
			Datas.Operators.removeIdleOperator();
			
			Datas.Server.removeIdleConnections();
			Datas.Client.removeIdleConnections();
			
			Datas.Processes.removeIdleProcesses();
		}
		
		return false;
	}
	private boolean finish() {
		// save CFG
		if(!com.FileManagerX.Globals.Datas.CFG.isErrorOccurs()) {
			com.FileManagerX.Globals.Datas.CFG.save();
		}
		
		// save Errors
		Datas.Errors.save();
		
		// Records
		Datas.Records.save();
		
		// save Server DataBase before execute tasks
		if(!this.anyError) {
			if(Configurations.StartType.equals(StartType.Server) && Datas.DBManager != null &&
					Datas.DBManager.getDBInfo().getType().equals(DataBaseType.TXT)) {
				Datas.DBManager.disconnect();
				Datas.DBManager.connect();
			}
		}
		
		// save DataBases
		if(!this.anyError) {
			for(IDBManager dbm : Datas.DBManagers.getContent()) {
				if(dbm.getDBInfo().getType().equals(DataBaseType.TXT)) {
					dbm.disconnect();
				}
			}
		}
		
		// Close Form
		if(Datas.Form_Test != null) {
			Datas.Form_Test.dispose();
		}
		
		// Close Server
		Datas.Scanner.disconnect();
		
		// Close Connection
		Datas.ServerConnection.disconnect();
		Datas.Server.removeAllConnections();
		Datas.Client.removeAllConnections();
		
		// end
		this.finished = true;
		this.running = false;
		
		return !this.anyError;
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
