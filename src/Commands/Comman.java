package Commands;

public class Comman implements Interfaces.ICommands {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Interfaces.IBasicMessagePackage bmp;
	private Interfaces.IConnection connection;
	private Interfaces.IReplies reply;
	
	private BasicModels.User suser;
	private BasicModels.MachineInfo smachine;
	private BasicModels.DepotInfo sdepot;
	private BasicModels.DataBaseInfo sdatabase;
	private BasicModels.User duser;
	private BasicModels.MachineInfo dmachine;
	private BasicModels.DepotInfo ddepot;
	private BasicModels.DataBaseInfo ddatabase;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setBasicMessagePackage(Interfaces.IBasicMessagePackage bmp) {
		if(bmp == null) {
			return false;
		}
		this.bmp = bmp;
		return true;
	}
	public boolean setConnection(Interfaces.IConnection connection) {
		if(connection == null) {
			return false;
		}
		this.connection = connection;
		return true;
	}
	public boolean setReply(Interfaces.IReplies reply) {
		if(reply == null) {
			return false;
		}
		this.reply = reply;
		return true;
	}
	public void setBasicMessagePackageToReply() {
		this.reply.getBasicMessagePackage().copyValue(this.bmp);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Interfaces.IBasicMessagePackage getBasicMessagePackage() {
		return this.bmp;
	}
	public Interfaces.IConnection getConnection() {
		return this.connection;
	}
	public Interfaces.IReplies getReply() {
		return this.reply;
	}
	
	public BasicModels.User getSourUser() {
		return this.suser;
	}
	public BasicModels.MachineInfo getSourMachineInfo() {
		return this.smachine;
	}
	public BasicModels.DepotInfo getSourDepotInfo() {
		return this.sdepot;
	}
	public BasicModels.DataBaseInfo getSourDataBaseInfo() {
		return this.sdatabase;
	}
	public BasicModels.User getDestUser() {
		return this.duser;
	}
	public BasicModels.MachineInfo getDestMachineInfo() {
		return this.dmachine;
	}
	public BasicModels.DepotInfo getDestDepotInfo() {
		return this.ddepot;
	}
	public BasicModels.DataBaseInfo getDestDataBaseInfo() {
		return this.ddatabase;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Comman() {
		initThis();
	}
	private void initThis() {
		this.bmp = Factories.CommunicatorFactory.createBasicMessagePackage();
		this.reply = new Replies.Comman();
		this.connection = null;
		this.suser = null;
		this.smachine = null;
		this.sdepot = null;
		this.sdatabase = null;
		this.duser = null;
		this.dmachine = null;
		this.ddepot = null;
		this.ddatabase = null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void clear() {
		initThis();
	}
	public String toString() {
		return this.output();
	}
	public String output() {
		BasicModels.Config c = new BasicModels.Config();
		c.setField(this.getClass().getSimpleName());
		c.addToBottom(new BasicModels.Config(this.bmp.output()));
		
		return c.output();
	}
	public String output(String cmdName) {
		BasicModels.Config c = new BasicModels.Config();
		c.setField(cmdName);
		c.addToBottom(new BasicModels.Config(this.bmp.output()));
		
		return c.output();
	}
	public String input(String in) {
		return this.bmp.input(in);
	}
	public void copyReference(Object o) {
		Comman c = (Comman)o;
		this.bmp = c.bmp;
		this.reply = c.reply;
		this.connection = c.connection;
	}
	public void copyValue(Object o) {
		Comman c = (Comman)o;
		this.bmp.copyValue(c.bmp);
		this.reply.copyValue(c.reply);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean execute() {
		if(!this.isConnected_Login_UserIndexRigth_PasswordRight()) {
			this.reply();
			return false;
		}
		
		if(this.isArriveTargetMachine()) {
			this.reply();
			return this.getReply().isOK();
		}
		else {
			Interfaces.ICommandConnector cc = Factories.CommunicatorFactory.createCommandConnector();
			cc.setIsExecuteCommand(true);
			cc.setDestMachineIndex(this.bmp.getDestMachineIndex());
			cc.setSendCommand(this.output());
			cc.setSourConnection(this.getConnection());
			Replies.Comman rep = (Replies.Comman)cc.execute();
			if(rep == null) { 
				this.replyNULL();
				return false;
			}
			this.setReply(rep);
			this.reply();
			return true;
		}
	}
	public void reply() {
		this.setBasicMessagePackageToReply();
		this.connection.setSendString(this.reply.output());
		this.connection.setSendLength(this.getConnection().getSendString().length());
		this.connection.setContinueSendString();
	}
	public void replyTotal(Interfaces.ICommunicatorSendTotal sendTotal) {
		this.setBasicMessagePackageToReply();
		this.connection.setSendString(sendTotal.output());
		this.connection.setSendLength(this.getConnection().getSendString().length());
		this.connection.setContinueSendString();
	}
	public void replyNULL() {
		this.getReply().setOK(false);
		this.getReply().setFailedReason("The Reply from other Connection is NULL");
		this.reply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean isArriveTargetMachine() {
		return this.bmp.getDestMachineIndex() == Globals.Configurations.This_MachineIndex;
	}
	public boolean isArriveSourMachine() {
		return this.bmp.getSourMachineIndex() == Globals.Configurations.This_MachineIndex;
	}
	public boolean isArriveDestMachine() {
		return this.bmp.getDestMachineIndex() == Globals.Configurations.This_MachineIndex;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean isPriorityEnough(BasicEnums.UserPriority p) {
		return this.connection.getUser().getPriority().isEnough(p);
	}
	public boolean isLevelEnough(BasicEnums.UserLevel level) {
		return this.connection.getUser().getLevel().isEnough(level);
	}
	
	public boolean isConnected() {
		if(connection == null || !connection.isRunning()) {
			reply.setOK(false);
			reply.setFailedReason("Disconnected");
			return false;
		}
		return true;
	}
	public boolean isLogin() {
		if(this.connection.getUser() == null || this.connection.getUser().getIndex() <= 0) {
			reply.setOK(false);
			reply.setFailedReason("No Login User");
			return false;
		}
		/*
		if(this.connection.getClientMachineInfo() == null || this.connection.getClientMachineInfo().getIndex() <= 0) {
			reply.setOK(false);
			reply.setFailedReason("No Login Machine");
			return false;
		}
		*/
		
		return true;
	}
	public boolean isUserIndexRight() {
		if(this.bmp.getSourUserIndex() <= 0) {
			reply.setOK(false);
			reply.setFailedReason("Wrong User Index");
			return false;
		}
		if(this.connection.getUser().getIndex() != this.bmp.getSourUserIndex()) {
			reply.setOK(false);
			reply.setFailedReason("Wrong User Index");
			return false;
		}
		return true;
	}
	public boolean isPasswordRight() {
		if(this.bmp.getPassword() == null || this.bmp.getPassword().length() == 0) {
			reply.setOK(false);
			reply.setFailedReason("Wrong Password");
			return false;
		}
		if(!this.bmp.getPassword().equals(this.connection.getUser().getPassword())) {
			reply.setOK(false);
			reply.setFailedReason("Wrong Password");
			return false;
		}
		return true;
	}
	
	public boolean isConnected_Login_UserIndexRigth_PasswordRight() {
		if(!this.isConnected()) {
			return false;
		}
		if(!this.isLogin()) {
			return false;
		}
		if(!this.isUserIndexRight()) {
			return false;
		}
		if(!this.isPasswordRight()) {
			return false;
		}
		
		return true;
	}
	public boolean isConnected_Login_UserIndexRigth_PasswordRight_PriorityEnough(BasicEnums.UserPriority p) {
		if(!this.isConnected_Login_UserIndexRigth_PasswordRight()) {
			return false;
		}
		if(!this.isPriorityEnough(p)) {
			return false;
		}
		return true;
	}
	public boolean isConnected_Login_UserIndexRigth_PasswordRight_LevelEnough(BasicEnums.UserLevel level) {
		if(!this.isConnected_Login_UserIndexRigth_PasswordRight()) {
			return false;
		}
		if(!this.isLevelEnough(level)) {
			return false;
		}
		return true;
	}
	public boolean isConnected_Login_UserIndexRigth_PasswordRight_PriorityEnough_LevelEnough(BasicEnums.UserPriority p, BasicEnums.UserLevel level) {
		if(!this.isConnected_Login_UserIndexRigth_PasswordRight()) {
			return false;
		}
		if(!this.isPriorityEnough(p)) {
			return false;
		}
		if(!this.isLevelEnough(level)) {
			return false;
		}
		return true;
	}
	
	
	public boolean isSourUserExist() {
		if(Globals.Configurations.IsServer) {
			BasicModels.User u = Globals.Datas.DBManager.QueryUser("[&] Index = " + this.bmp.getSourUserIndex());
			if(u == null) {
				reply.setOK(false);
				reply.setFailedReason("Not Exist such User in DataBase");
				return false;
			} else {
				this.suser = u;
				return true;
			}
		}
		else {
			Interfaces.ISWRE swre = Factories.CommunicatorFactory.createSWRE();
			
			Commands.QueryUser cmd = new Commands.QueryUser();
			cmd.setQueryConditions("[&] Index = " + this.bmp.getSourUserIndex());
			Replies.QueryUser rep = (Replies.QueryUser)swre.execute(cmd.output());
			
			if(rep == null) {
				reply.setOK(false);
				reply.setFailedReason("Not Exist such User in DataBase");
				return false;
			}
			if(!rep.isOK()) {
				reply.setOK(false);
				reply.setFailedReason(rep.getFailedReason());
				return false;
			}
			
			this.suser = rep.getUser();
			return true;
		}
	}
	public boolean isSourMachineIndexExist() {
		if(Globals.Configurations.IsServer) {
			BasicModels.MachineInfo m = Globals.Datas.DBManager.QueryMachineInfo("[&] Index = " + this.bmp.getSourMachineIndex());
			if(m == null) {
				reply.setOK(false);
				reply.setFailedReason("Not Exist such MachineIndex in DataBase");
				return false;
			} else {
				this.smachine = m;
				return true;
			}
		}
		else {
			Interfaces.ISWRE swre = Factories.CommunicatorFactory.createSWRE();
			
			Commands.QueryMachine cmd = new Commands.QueryMachine();
			cmd.setQueryConditions("[&] Index = " + this.bmp.getSourMachineIndex());
			Replies.QueryMachine rep = (Replies.QueryMachine)swre.execute(cmd.output());
			
			if(rep == null) {
				reply.setOK(false);
				reply.setFailedReason("Not Exist such MachineIndex in DataBase");
				return false;
			}
			if(!rep.isOK()) {
				reply.setOK(false);
				reply.setFailedReason(rep.getFailedReason());
				return false;
			}
			
			this.smachine = rep.getMachineInfo();
			return true;
		}
	}
	public boolean isSourDepotIndexExist() {
		if(Globals.Configurations.IsServer) {
			BasicModels.DepotInfo d = Globals.Datas.DBManager.QueryDepotInfo("[&] Index = " + this.bmp.getSourDepotIndex());
			if(d == null) {
				reply.setOK(false);
				reply.setFailedReason("Not Exist such DepotIndex in DataBase");
				return false;
			} else {
				this.sdepot = d;
				return true;
			}
		}
		else {
			Interfaces.ISWRE swre = Factories.CommunicatorFactory.createSWRE();
			
			Commands.QueryDepot cmd = new Commands.QueryDepot();
			cmd.setQueryConditions("[&] Index = " + this.bmp.getSourDepotIndex());
			Replies.QueryDepot rep = (Replies.QueryDepot)swre.execute(cmd.output());
			
			if(rep == null) {
				reply.setOK(false);
				reply.setFailedReason("Not Exist such DepotIndex in DataBase");
				return false;
			}
			if(!rep.isOK()) {
				reply.setOK(false);
				reply.setFailedReason(rep.getFailedReason());
				return false;
			}
			
			this.sdepot = rep.getDepotInfo();
			return true;
		}
	}
	public boolean isSourDataBaseIndexExist() {
		if(Globals.Configurations.IsServer) {
			BasicModels.DataBaseInfo d = Globals.Datas.DBManager.QueryDataBaseInfo("[&] Index = " + this.bmp.getSourDataBaseIndex());
			if(d == null) {
				reply.setOK(false);
				reply.setFailedReason("Not Exist such DataBaseIndex in DataBase");
				return false;
			} else {
				this.sdatabase = d;
				return true;
			}
		}
		else {
			Interfaces.ISWRE swre = Factories.CommunicatorFactory.createSWRE();
			
			Commands.QueryDataBase cmd = new Commands.QueryDataBase();
			cmd.setQueryConditions("[&] Index = " + this.bmp.getSourDataBaseIndex());
			Replies.QueryDataBase rep = (Replies.QueryDataBase)swre.execute(cmd.output());
			
			if(rep == null) {
				reply.setOK(false);
				reply.setFailedReason("Not Exist such DataBaseIndex in DataBase");
				return false;
			}
			if(!rep.isOK()) {
				reply.setOK(false);
				reply.setFailedReason(rep.getFailedReason());
				return false;
			}
			
			this.sdatabase = rep.getDataBaseInfo();
			return true;
		}
	}
	public boolean isDestUserExist() {
		if(Globals.Configurations.IsServer) {
			BasicModels.User u = Globals.Datas.DBManager.QueryUser("[&] Index = " + this.bmp.getDestUserIndex());
			if(u == null) {
				reply.setOK(false);
				reply.setFailedReason("Not Exist such User in DataBase");
				return false;
			} else {
				this.duser = u;
				return true;
			}
		}
		else {
			Interfaces.ISWRE swre = Factories.CommunicatorFactory.createSWRE();
			
			Commands.QueryUser cmd = new Commands.QueryUser();
			cmd.setQueryConditions("[&] Index = " + this.bmp.getDestUserIndex());
			Replies.QueryUser rep = (Replies.QueryUser)swre.execute(cmd.output());
			
			if(rep == null) {
				reply.setOK(false);
				reply.setFailedReason("Not Exist such User in DataBase");
				return false;
			}
			if(!rep.isOK()) {
				reply.setOK(false);
				reply.setFailedReason(rep.getFailedReason());
				return false;
			}
			
			this.duser = rep.getUser();
			return true;
		}
	}
	public boolean isDestMachineIndexExist() {
		if(Globals.Configurations.IsServer) {
			BasicModels.MachineInfo m = Globals.Datas.DBManager.QueryMachineInfo("[&] Index = " + this.bmp.getDestMachineIndex());
			if(m == null) {
				reply.setOK(false);
				reply.setFailedReason("Not Exist such MachineIndex in DataBase");
				return false;
			} else {
				this.dmachine = m;
				return true;
			}
		}
		else {
			Interfaces.ISWRE swre = Factories.CommunicatorFactory.createSWRE();
			
			Commands.QueryMachine cmd = new Commands.QueryMachine();
			cmd.setQueryConditions("[&] Index = " + this.bmp.getDestMachineIndex());
			Replies.QueryMachine rep = (Replies.QueryMachine)swre.execute(cmd.output());
			
			if(rep == null) {
				reply.setOK(false);
				reply.setFailedReason("Not Exist such MachineIndex in DataBase");
				return false;
			}
			if(!rep.isOK()) {
				reply.setOK(false);
				reply.setFailedReason(rep.getFailedReason());
				return false;
			}
			
			this.dmachine = rep.getMachineInfo();
			return true;
		}
	}
	public boolean isDestDepotIndexExist() {
		if(Globals.Configurations.IsServer) {
			BasicModels.DepotInfo d = Globals.Datas.DBManager.QueryDepotInfo("[&] Index = " + this.bmp.getDestDepotIndex());
			if(d == null) {
				reply.setOK(false);
				reply.setFailedReason("Not Exist such DepotIndex in DataBase");
				return false;
			} else {
				this.ddepot = d;
				return true;
			}
		}
		else {
			Interfaces.ISWRE swre = Factories.CommunicatorFactory.createSWRE();
			
			Commands.QueryDepot cmd = new Commands.QueryDepot();
			cmd.setQueryConditions("[&] Index = " + this.bmp.getDestDepotIndex());
			Replies.QueryDepot rep = (Replies.QueryDepot)swre.execute(cmd.output());
			
			if(rep == null) {
				reply.setOK(false);
				reply.setFailedReason("Not Exist such DepotIndex in DataBase");
				return false;
			}
			if(!rep.isOK()) {
				reply.setOK(false);
				reply.setFailedReason(rep.getFailedReason());
				return false;
			}
			
			this.ddepot = rep.getDepotInfo();
			return true;
		}
	}
	public boolean isDestDataBaseIndexExist() {
		if(Globals.Configurations.IsServer) {
			BasicModels.DataBaseInfo d = Globals.Datas.DBManager.QueryDataBaseInfo("[&] Index = " + this.bmp.getDestDataBaseIndex());
			if(d == null) {
				reply.setOK(false);
				reply.setFailedReason("Not Exist such DataBaseIndex in DataBase");
				return false;
			} else {
				this.ddatabase = d;
				return true;
			}
		}
		else {
			Interfaces.ISWRE swre = Factories.CommunicatorFactory.createSWRE();
			
			Commands.QueryDataBase cmd = new Commands.QueryDataBase();
			cmd.setQueryConditions("[&] Index = " + this.bmp.getDestDataBaseIndex());
			Replies.QueryDataBase rep = (Replies.QueryDataBase)swre.execute(cmd.output());
			
			if(rep == null) {
				reply.setOK(false);
				reply.setFailedReason("Not Exist such DataBaseIndex in DataBase");
				return false;
			}
			if(!rep.isOK()) {
				reply.setOK(false);
				reply.setFailedReason(rep.getFailedReason());
				return false;
			}
			
			this.ddatabase = rep.getDataBaseInfo();
			return true;
		}
	}
	
	public boolean isExistSour_MachineIndex_DepotIndex_DataBaseIndex() {
		return this.isSourMachineIndexExist() &&
			this.isSourDepotIndexExist() &&
			this.isSourDataBaseIndexExist();
	}
	public boolean isExistSour_MachineIndex_DepotIndex() {
		return this.isSourMachineIndexExist() &&
			this.isSourDepotIndexExist();
	}
	public boolean isExistSour_MachineIndex_DataBaseIndex() {
		return this.isSourMachineIndexExist() &&
			this.isSourDataBaseIndexExist();
	}
	public boolean isExistSour_DepotIndex_DataBaseIndex() {
		return this.isSourDepotIndexExist() &&
			this.isSourDataBaseIndexExist();
	}
	
	public boolean isExistDest_MachineIndex_DepotIndex_DataBaseIndex() {
		return this.isDestMachineIndexExist() &&
			this.isDestDepotIndexExist() &&
			this.isDestDataBaseIndexExist();
	}
	public boolean isExistDest_MachineIndex_DepotIndex() {
		return this.isDestMachineIndexExist() &&
			this.isDestDepotIndexExist();
	}
	public boolean isExistDest_MachineIndex_DataBaseIndex() {
		return this.isDestMachineIndexExist() &&
			this.isDestDataBaseIndexExist();
	}
	public boolean isExistDest_DepotIndex_DataBaseIndex() {
		return this.isDestDepotIndexExist() &&
			this.isDestDataBaseIndexExist();
	}
	
	
	public boolean isSelfToSelf() {
		if(this.bmp.getSourMachineIndex() == this.bmp.getDestMachineIndex()) {
			this.reply.setFailedReason("You send command self to self");
			this.reply.setOK(false);
			return true;
		}
		return false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
