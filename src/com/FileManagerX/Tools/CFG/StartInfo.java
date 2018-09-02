package com.FileManagerX.Tools.CFG;

import com.FileManagerX.BasicEnums.MachineType;
import com.FileManagerX.Globals.Configurations;

public class StartInfo {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static boolean load(com.FileManagerX.FileModels.CFG cfg) {
		com.FileManagerX.BasicModels.Config c = cfg.getConfigs().fetchByField("StartInfo");
		String startInfo = c == null ? "" : c.getValue().toLowerCase();
		
		com.FileManagerX.BasicEnums.MachineType type = com.FileManagerX.BasicEnums.MachineType.TEMPORARY;
		if(startInfo.contains("server")) { type = MachineType.SERVER; }
		if(startInfo.contains("depot")) { type = MachineType.DEPOT; }
		if(startInfo.contains("client")) { type = MachineType.CLIENT; }
		
		com.FileManagerX.Globals.Configurations.ShowForm = startInfo.contains("showform");
		if(com.FileManagerX.Globals.Datas.Form_Test != null) {
			com.FileManagerX.Globals.Datas.Form_Test.setVisible(
					com.FileManagerX.Globals.Configurations.ShowForm
				);
		}
		
		com.FileManagerX.Globals.Configurations.IsAncestor = startInfo.contains("ancestor");
		
		com.FileManagerX.Globals.Configurations.MachineType = type;
		com.FileManagerX.Globals.Configurations.IsServer = type.equals(MachineType.SERVER);
		com.FileManagerX.Globals.Configurations.IsDepot = type.equals(MachineType.DEPOT);
		com.FileManagerX.Globals.Configurations.IsClient = type.equals(MachineType.CLIENT);
		com.FileManagerX.Globals.Configurations.IsTemporary = type.equals(MachineType.TEMPORARY);
		
		Uninstall.uninstall = startInfo.contains("uninstall");
		
		c = cfg.getConfigs().fetchByField("LoginName");
		String loginName = c == null ? "" : c.getValue();
		c = cfg.getConfigs().fetchByField("Password");
		String password = c == null ? "" : c.getValue();
		com.FileManagerX.Globals.Datas.ThisUser.setLoginName(loginName);
		com.FileManagerX.Globals.Datas.ThisUser.setPassword(password);
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public final static boolean save(com.FileManagerX.FileModels.CFG cfg) {
		String line = "";
		
		cfg.getContent().add(line);
		line = "StartInfo = " + Configurations.MachineType.toString() + 
				(Configurations.ShowForm ? "|ShowForm" : "") +
				(Configurations.IsAncestor ? "|Ancestor" : "");
		cfg.getContent().add(line);
		
		line = "LoginName = " + com.FileManagerX.Globals.Datas.ThisUser.getLoginName();
		cfg.getContent().add(line);
		line = "Password = " + com.FileManagerX.Globals.Datas.ThisUser.getPassword();
		cfg.getContent().add(line);
		
		return true;
	}
	public final static boolean saveNew(com.FileManagerX.FileModels.CFG cfg) {
		return save(cfg);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
