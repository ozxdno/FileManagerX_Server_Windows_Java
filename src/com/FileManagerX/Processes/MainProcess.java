package com.FileManagerX.Processes;

import com.FileManagerX.Globals.Configurations;
import com.FileManagerX.Globals.Datas;

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
		// Start Sender
		com.FileManagerX.Globals.Datas.Sender.startProcess();
		
		// create other folders and files
		com.FileManagerX.Tools.Pathes.createAll();
		
		// load CFG file
		com.FileManagerX.Globals.Datas.CFG.setUrl(com.FileManagerX.Tools.Pathes.getFile_CFG());
		com.FileManagerX.Globals.Datas.CFG.loadBasicInfo();
		com.FileManagerX.Globals.Datas.CFG.loadFromLocal();
		
		// Set Form Title
		if(Datas.Form_Test != null) {
			Datas.Form_Test.setTitle(
					"FileManagerX" + 
					" [" + Configurations.This_MachineIndex + "]" +
					" - " + 
					Configurations.MachineType.toString() + " : " + 
					Datas.ThisUser.getNickName()
					);
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
			com.FileManagerX.Globals.Datas.CFG.saveToLocal();
		}
		
		Datas.Records.save();
		Datas.Errors.save();
		
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IDBManager> it = 
				com.FileManagerX.Globals.Datas.DBManagers.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.Interfaces.IDBManager dbm = it.getNext();
			if(dbm.getDBInfo().getType().equals(com.FileManagerX.BasicEnums.DataBaseType.TXT)) {
				dbm.save();
			}
		}
		
		// ²âÊÔÏß³Ì¿ªÆô
		if(com.FileManagerX.Globals.Datas.ServerConnection.isRunning()) {
			//SenderProcess sp = new SenderProcess();
			//sp.setConnection(com.FileManagerX.Globals.Datas.ServerConnection);
			//sp.startProcess();
		}
		
		return true;
	}
	
	private boolean task() {
		
		while(!this.isAbort() && !com.FileManagerX.Globals.Configurations.Restart) {
			com.FileManagerX.Tools.Time.sleepUntil(1*1000);
			
			Datas.Records.save();
			Datas.Errors.save();
			
			Datas.Receiver.removeIdleReplies();
			
			Datas.Scanners.removeIdleProcesses();
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
			com.FileManagerX.Globals.Datas.CFG.saveToLocal();
		}
		
		// save Errors
		Datas.Errors.save();
		
		// Records
		Datas.Records.save();
		
		// save DataBases
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IDBManager> it = 
				com.FileManagerX.Globals.Datas.DBManagers.getIterator();
		while(it.hasNext()) {
			if(it.getNext().getDBInfo().getType().equals(com.FileManagerX.BasicEnums.DataBaseType.TXT)) {
				it.getNext().save();
			}
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
			com.FileManagerX.Tools.Time.sleepUntil
				(com.FileManagerX.Globals.Configurations.TimeForPermitIdle_Restart);
			this.restartProcess();
		}
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
