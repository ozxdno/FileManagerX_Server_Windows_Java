package com.FileManagerX.Tools.CFG;

public class Register {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static boolean registerMachine = false;
	public static boolean registerUser = false;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static boolean load(com.FileManagerX.FileModels.CFG cfg) {
		com.FileManagerX.BasicModels.Config c =  cfg.getConfigs().fetchByField("RegisterUser");
		registerUser = c != null;
		if(c != null) {
			String loginName = c.fetchFirstString();
			String password = c.fetchFirstString();
			String code = c.fetchFirstString();
			
			com.FileManagerX.Commands.RegisterUser ru = new com.FileManagerX.Commands.RegisterUser();
			ru.setThis(code, loginName, password);
			ru.setDestConnection(com.FileManagerX.Globals.Datas.ServerConnection);
			ru.send();
			com.FileManagerX.Replies.RegisterUser rep = (com.FileManagerX.Replies.RegisterUser)ru.receive();
			if(rep != null && rep.isOK()) {
				com.FileManagerX.Globals.Datas.ThisUser.copyReference(rep.getUser());
				com.FileManagerX.Globals.Configurations.This_UserIndex =
						com.FileManagerX.Globals.Datas.ThisUser.getIndex();
			}
		}
		
		c = cfg.getConfigs().fetchByField("RegisterMachine");
		registerMachine = c != null;
		if(c != null) {
			String name = c.fetchFirstString();
			String ip = c.fetchFirstString();
			int port = c.fetchFirstInt();
			
			if(!com.FileManagerX.Tools.Url.isIp(ip)) {
				ip = com.FileManagerX.Globals.Datas.ThisMachine.getIp();
			}
			if(port <= 1024) {
				port = com.FileManagerX.Globals.Datas.ThisMachine.getPort();
			}
			
			com.FileManagerX.BasicModels.MachineInfo m = new com.FileManagerX.BasicModels.MachineInfo();
			m.setName(name);
			m.setUserIndex(com.FileManagerX.Globals.Configurations.This_UserIndex);
			m.setType(com.FileManagerX.Globals.Configurations.MachineType);
			m.setMac(com.FileManagerX.Globals.Datas.ThisMachine.getMac());
			m.setIp(ip);
			m.setPort(port);
			
			com.FileManagerX.Commands.RegisterMachine rm = new com.FileManagerX.Commands.RegisterMachine();
			rm.setThis(m);
			rm.setDestConnection(com.FileManagerX.Globals.Datas.ServerConnection);
			rm.send();
			com.FileManagerX.Replies.RegisterMachine rep = (com.FileManagerX.Replies.RegisterMachine)rm.receive();
			if(rep != null && rep.isOK()) {
				com.FileManagerX.Globals.Datas.ThisMachine.copyReference(rep.getMachineInfo());
				com.FileManagerX.Globals.Configurations.This_MachineIndex =
						com.FileManagerX.Globals.Datas.ThisMachine.getIndex();
			}
		}
		
		return true;
	}
	
	public final static boolean save(com.FileManagerX.FileModels.CFG cfg) {
		java.lang.String line = "";
		cfg.getContent().add(line);
		line = "/***************************************************** Register ******************************************************/";
		cfg.getContent().add(line);
		line = "";
		cfg.getContent().add(line);
		
		line = "Example: RegisterUser = LoginName|Password|InvitationCode";
		cfg.getContent().add(line);
		line = "Example: RegisterMachine = Name";
		cfg.getContent().add(line);
		line = "";
		cfg.getContent().add(line);
		
		return true;
	}
	
	public final static boolean saveNew(com.FileManagerX.FileModels.CFG cfg) {
		return save(cfg);
	}
	
}
