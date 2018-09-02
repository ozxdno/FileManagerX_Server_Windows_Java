package com.FileManagerX.Tools.CFG;

import com.FileManagerX.Globals.Configurations;

public class Index {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static boolean load(com.FileManagerX.FileModels.CFG cfg) {
		try {
			Configurations.Server_MachineIndex = cfg.getConfigs().fetchByField("Server_MachineIndex").getLong();
		}catch(Exception e) {
			Configurations.Server_MachineIndex = 0;
		}
		try {
			Configurations.Server_UserIndex = cfg.getConfigs().fetchByField("Server_UserIndex").getLong();
		}catch(Exception e) {
			Configurations.Server_UserIndex = 0;
		}
		
		try {
			Configurations.This_MachineIndex = cfg.getConfigs().fetchByField("This_MachineIndex").getLong();
		}catch(Exception e) {
			Configurations.This_MachineIndex = 0;
		}
		try {
			Configurations.This_UserIndex = cfg.getConfigs().fetchByField("This_UserIndex").getLong();
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
		line = "Server_MachineIndex = " + java.lang.String.valueOf(Configurations.Server_MachineIndex);
		cfg.getContent().add(line);
		line = "Server_UserIndex = " + java.lang.String.valueOf(Configurations.Server_UserIndex);
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
		line = "Server_MachineIndex = 0";
		cfg.getContent().add(line);
		line = "Server_UserIndex = 0";
		cfg.getContent().add(line);
		
		return true;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
