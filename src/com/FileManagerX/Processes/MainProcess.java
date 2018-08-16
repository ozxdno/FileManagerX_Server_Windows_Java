package com.FileManagerX.Processes;

import com.FileManagerX.BasicEnums.DataBaseType;
import com.FileManagerX.BasicEnums.StartType;
import com.FileManagerX.Globals.Configurations;
import com.FileManagerX.Globals.Datas;
import com.FileManagerX.Interfaces.IDBChecker;
import com.FileManagerX.Interfaces.IDBManager;

public class MainProcess extends BasicProcess {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public MainProcess() {
		initThis();
	}
	private void initThis() {
		this.setName("Process Main");
		this.setRunnable(new RunImpl());
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private class RunImpl implements BasicProcess.Runnable {
		public String run() {
			
			MainProcess.this.parepare();
			MainProcess.this.task();
			MainProcess.this.finish();
			
			return null;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private boolean parepare() {
		
		// create other folders and files
		com.FileManagerX.Tools.Pathes.createAll(Configurations.StartType);
		
		// load CFG file
		com.FileManagerX.Globals.Datas.CFG.load();
		
		// Set Form Title
		if(Datas.Form_Test != null) {
			Datas.Form_Test.setTitle(
					"FileManagerX" + 
					" [" + Configurations.This_MachineIndex + "]" +
					" - " + 
					Configurations.StartType.toString() + " : " + 
					Datas.ThisUser.getNickName()
					);
		}
		
		// check server
		if(Configurations.StartType.equals(StartType.Server)) {
			IDBChecker dbc = Datas.DBManager.getDBInfo().getChecker();
			dbc.checkServer();
		}
		
		// check local depots
		for(IDBManager dbm : Datas.DBManagers.getContent()) {
			IDBChecker dbc = dbm.getDBInfo().getChecker();
			dbc.checkNotServer();
		}
		
		// add server dbm to dbms
		if(com.FileManagerX.Globals.Configurations.IsServer) {
			Datas.DBManagers.add(Datas.DBManager);
		}
		
		// delete log file
		Datas.Errors.deleteAgoLogs(30);
		
		// delete rec file
		Datas.Records.deleteAgoRecords(30);
		
		// save CFG before execute tasks
		if(!com.FileManagerX.Globals.Datas.CFG.isErrorOccurs()) {
			com.FileManagerX.Globals.Datas.CFG.save();
		}
		
		
		// save Errors before execute tasks
		Datas.Errors.save();
		
		return true;
	}
	
	private boolean task() {
		
		while(!this.isAbort() && !com.FileManagerX.Globals.Configurations.Restart) {
			com.FileManagerX.Tools.Time.sleepUntil(1*1000);
			
			Datas.Records.save(100);
			Datas.Errors.save(100);
			
			Datas.Server.removeIdleProcesses();
			Datas.Client.removeIdleProcesses();
			Datas.Operators.removeIdleProcesses();
			Datas.Executors.removeIdleProcesses();
			Datas.Processes.removeIdleProcesses();
		}
		
		return true;
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
		
		// save DataBases
		for(IDBManager dbm : Datas.DBManagers.getContent()) {
			if(dbm.getDBInfo().getType().equals(DataBaseType.TXT)) {
				dbm.save();
			}
			dbm.disconnect();
		}
		
		// Close Form
		if(Datas.Form_Test != null) {
			Datas.Form_Test.dispose();
		}
		
		// Close All Threads
		com.FileManagerX.Globals.Datas.Processes.removeAllProcesses();
		
		// Restart
		if(com.FileManagerX.Globals.Configurations.Restart) {
			com.FileManagerX.Globals.Configurations.Restart = false;
			this.restartProcess();
		}
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
