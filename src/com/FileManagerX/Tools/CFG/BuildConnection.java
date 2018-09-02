package com.FileManagerX.Tools.CFG;

public class BuildConnection {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static boolean build() {
		com.FileManagerX.Globals.Datas.ServerConnection.exitProcess();
		while(com.FileManagerX.Globals.Datas.ServerConnection.isRunning()) {
			com.FileManagerX.Tools.Time.sleepUntil(10);
		}
		
		if(com.FileManagerX.Globals.Configurations.IsAncestor) { return true; }
		return com.FileManagerX.Factories.CommunicatorFactory.createRunningClientConnection();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
