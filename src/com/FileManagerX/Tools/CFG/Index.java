package com.FileManagerX.Tools.CFG;

import com.FileManagerX.Globals.Configurations;

public class Index {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static boolean load(com.FileManagerX.FileModels.CFG cfg) {
		Configurations.Server_MachineIndex = 1;
		Configurations.Server_UserIndex = 1;
		
		try {
			if(!Register.registerMachine) {
				Configurations.This_MachineIndex = cfg.getConfigs().fetchByField("This_MachineIndex").getLong();
			}
		}catch(Exception e) {
			Configurations.This_MachineIndex = 0;
		}
		try {
			if(!Register.registerUser) {
				Configurations.This_UserIndex = cfg.getConfigs().fetchByField("This_UserIndex").getLong();
			}
		}catch(Exception e) {
			Configurations.This_UserIndex = 0;
		}
		
		if(com.FileManagerX.Globals.Configurations.IsAncestor) {
			Configurations.Server_MachineIndex = 1;
			Configurations.This_MachineIndex = 1;
		}
		if(com.FileManagerX.Globals.Configurations.IsServer) {
			Configurations.Server_UserIndex = 1;
			Configurations.This_UserIndex = 1;
		}
		
		com.FileManagerX.Globals.Datas.ServerMachine.setIndex(
				com.FileManagerX.Globals.Configurations.Server_MachineIndex);
		com.FileManagerX.Globals.Datas.ServerUser.setIndex(
				com.FileManagerX.Globals.Configurations.Server_UserIndex);
		com.FileManagerX.Globals.Datas.ThisMachine.setIndex(
				com.FileManagerX.Globals.Configurations.This_MachineIndex);
		com.FileManagerX.Globals.Datas.ThisUser.setIndex(
				com.FileManagerX.Globals.Configurations.This_UserIndex);
		com.FileManagerX.Globals.Datas.ThisMachine.setUserIndex(
				com.FileManagerX.Globals.Configurations.This_UserIndex);
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public final static boolean save(com.FileManagerX.FileModels.CFG cfg) {
		java.lang.String line = "";
		cfg.getContent().add(line);
		line = "/*************************************************** All Indexes *****************************************************/";
		cfg.getContent().add(line);
		line = "";
		cfg.getContent().add(line);
		
		line = "This_MachineIndex = " + java.lang.String.valueOf(Configurations.This_MachineIndex);
		cfg.getContent().add(line);
		line = "This_UserIndex = " + java.lang.String.valueOf(Configurations.This_UserIndex);
		cfg.getContent().add(line);
		line = "Server_MachineIndex = 1";
		cfg.getContent().add(line);
		line = "Server_UserIndex = 1";
		cfg.getContent().add(line);
		
		return true;
	}
	public final static boolean saveNew(com.FileManagerX.FileModels.CFG cfg) {
		java.lang.String line = "";
		cfg.getContent().add(line);
		line = "/*************************************************** All Indexes *****************************************************/";
		cfg.getContent().add(line);
		line = "";
		cfg.getContent().add(line);
		
		line = "This_MachineIndex = 0";
		cfg.getContent().add(line);
		line = "This_UserIndex = 0";
		cfg.getContent().add(line);
		line = "Server_MachineIndex = 1";
		cfg.getContent().add(line);
		line = "Server_UserIndex = 1";
		cfg.getContent().add(line);
		
		return true;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
