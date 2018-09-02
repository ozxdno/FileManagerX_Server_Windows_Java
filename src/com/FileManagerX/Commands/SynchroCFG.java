package com.FileManagerX.Commands;

public class SynchroCFG extends BaseCommand {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static String FAILED_OPERATE_DATABASE = "Operate DataBase Failed";
	public final static String FAILED_NO_USER = "No Such User";
	public final static String FAILED_WRONG_PASSWORD = "Password is Wrong";
	public final static String FAILED_NO_MACHINE = "No Such Machine";
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private String loginName;
	private String password;
	private long machineIndex;
	private com.FileManagerX.BasicEnums.MachineType type;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setLoginName(String loginName) {
		if(loginName == null) {
			return false;
		}
		if(loginName.length() == 0) {
			return false;
		}
		this.loginName = loginName;
		return true;
	}
	public boolean setPassword(String password) {
		if(password == null) {
			return false;
		}
		if(password.length() == 0) {
			return false;
		}
		this.password = password;
		return true;
	}
	public boolean setMachineIndex(long machine) {
		this.machineIndex = machine;
		return true;
	}
	public boolean setMachineType(com.FileManagerX.BasicEnums.MachineType type) {
		if(type == null) {
			return false;
		}
		this.type = type;
		return true;
	}
	
	public boolean setThis(String loginName, String password, long machineIndex, 
			com.FileManagerX.BasicEnums.MachineType type) {
		boolean ok = true;
		ok &= this.setLoginName(loginName);
		ok &= this.setPassword(password);
		ok &= this.setMachineIndex(machineIndex);
		ok &= this.setMachineType(type);
		return ok;
	}
	public boolean setThis(String loginName, String password, long machineIndex, 
			com.FileManagerX.BasicEnums.MachineType type,
			com.FileManagerX.Interfaces.IConnection connection) {
		boolean ok = true;
		ok &= this.getBasicMessagePackage().setThis(connection.getClientConnection());
		ok &= this.setConnection(connection);
		ok &= this.setThis(loginName, password, machineIndex, type);
		return ok;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public String getLoginName() {
		return this.loginName;
	}
	public String getPassword() {
		return this.password;
	}
	public long getMachineIndex() {
		return this.machineIndex;
	}
	public com.FileManagerX.BasicEnums.MachineType getMachineType() {
		return this.type;
	}
	
	public com.FileManagerX.Replies.SynchroCFG getReply() {
		if(super.getReply() == null) { this.setReply(new com.FileManagerX.Replies.SynchroCFG()); }
		return (com.FileManagerX.Replies.SynchroCFG)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public SynchroCFG() {
		initThis();
	}
	private void initThis() {
		this.loginName = "";
		this.password = "";
		this.machineIndex = 0;
		this.type = com.FileManagerX.BasicEnums.MachineType.TEMPORARY;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void clear() {
		initThis();
	}
	public String toString() {
		return this.output();
	}
	public com.FileManagerX.BasicModels.Config toConfig() {
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config();
		c.setField(this.getClass().getSimpleName());
		c.addToBottom(super.toConfig());
		c.addToBottom(this.loginName);
		c.addToBottom(this.password);
		c.addToBottom(this.machineIndex);
		c.addToBottom(this.type.toString());
		return c;
	}
	public String output() {
		return this.toConfig().output();
	}
	public com.FileManagerX.BasicModels.Config input(String in) {
		return this.input(new com.FileManagerX.BasicModels.Config(in));
	}
	public com.FileManagerX.BasicModels.Config input(com.FileManagerX.BasicModels.Config c) {
		if(c == null) { return null; }
		try {
			if(!c.getIsOK()) { return c; }
			c = super.input(c);
			if(!c.getIsOK()) { return c; }
			this.loginName = c.fetchFirstString();
			if(!c.getIsOK()) { return c; }
			this.password = c.fetchFirstString();
			if(!c.getIsOK()) { return c; }
			this.machineIndex = c.fetchFirstLong();
			if(!c.getIsOK()) { return c; }
			this.type = com.FileManagerX.BasicEnums.MachineType.valueOf(c.fetchFirstString());
			if(!c.getIsOK()) { return c; }
			return c;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			c.setIsOK(false);
			return c;
		}
	}
	public void copyReference(Object o) {
		if(o instanceof SynchroCFG) {
			super.copyReference(o);
			SynchroCFG s = (SynchroCFG)o;
			this.loginName = s.loginName;
			this.password = s.password;
			this.machineIndex = s.machineIndex;
			this.type = s.type;
			return;
		}
		if(o instanceof BaseCommand) {
			super.copyReference(o);
			return;
		}
	}
	public void copyValue(Object o) {
		if(o instanceof SynchroCFG) {
			super.copyValue(o);
			SynchroCFG s = (SynchroCFG)o;
			this.loginName = s.loginName;
			this.password = s.password;
			this.machineIndex = s.machineIndex;
			this.type = s.type;
			return;
		}
		if(o instanceof BaseCommand) {
			super.copyValue(o);
			return;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean execute() {
		if(!this.isConnected()) {
			this.getReply().send();
			return false;
		}
		
		boolean ok = this.executeInLocal();
		this.getReply().send();
		return ok;
	}
	public boolean executeInLocal() {
		
		String qcs = "[&] LoginName = '" + this.loginName + "'";
		com.FileManagerX.BasicModels.User user = (com.FileManagerX.BasicModels.User)
				com.FileManagerX.Globals.Datas.DBManager.query2
					(qcs, com.FileManagerX.DataBase.Unit.User);
		if(user == null) {
			this.getReply().setThis(false, FAILED_OPERATE_DATABASE);
			return false;
		}
		if(!user.getPassword().equals(this.password)) {
			this.getReply().setThis(false, FAILED_WRONG_PASSWORD);
			return false;
		}
		
		if(this.machineIndex < 0) {
			qcs = "[&] UserIndex = " + user.getIndex() + ", [&] Type = '" + 
					com.FileManagerX.BasicEnums.MachineType.TEMPORARY.toString() + "'";
			com.FileManagerX.BasicModels.MachineInfo m = (com.FileManagerX.BasicModels.MachineInfo)
					com.FileManagerX.Globals.Datas.DBManager.query2
						(qcs, com.FileManagerX.DataBase.Unit.Machine);
			if(m == null) {
				this.getReply().setThis(false, FAILED_OPERATE_DATABASE);
				return false;
			}
			this.machineIndex = m.getIndex();
		}
		
		qcs = "[&] MachineIndex = " + this.machineIndex + ", [&] Type = '" +
				com.FileManagerX.BasicEnums.FileType.CFG.toString() + "'";
		com.FileManagerX.FileModels.CFG cfg = (com.FileManagerX.FileModels.CFG)
				com.FileManagerX.Globals.Datas.DBManager.query2
					(qcs, com.FileManagerX.DataBase.Unit.CFG);
		if(cfg == null) {
			cfg = new com.FileManagerX.FileModels.CFG();
			//cfg.saveCFGCore(type, true);
			cfg.setMachine(machineIndex);
		}
		
		this.getReply().setContent(cfg.output());
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
