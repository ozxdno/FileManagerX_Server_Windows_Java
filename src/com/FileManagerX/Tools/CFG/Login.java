package com.FileManagerX.Tools.CFG;

public class Login {

	public final static boolean login() {
		if(com.FileManagerX.Globals.Configurations.IsAncestor) { return true; }
		
		com.FileManagerX.Globals.Configurations.Refresh = true;
		String error = com.FileManagerX.Globals.Datas.ServerConnection.login();
		com.FileManagerX.Globals.Configurations.Refresh = false;
		return error == null;
	}
	
}
