package DataBaseManager;

public class GAPManager implements Interfaces.IGAPManager {

	
	public BasicModels.User QeuryUser(Object conditions) {
		if(Globals.Configurations.IsServer) {
			return Globals.Datas.DBManager.QueryUser(conditions);
		}
		else {
			Commands.QueryUser cmd = new Commands.QueryUser();
			if(conditions instanceof String) {
				cmd.setQueryConditions((String)conditions);
			}
			else if(conditions instanceof QueryCondition) {
				cmd.setQueryCondition((QueryCondition)conditions);
			}
			else if(conditions instanceof QueryConditions) {
				cmd.setQueryConditions((QueryConditions)conditions);
			}
			else {
				BasicEnums.ErrorType.UNKNOW.register("Type of conditions is Wrong");
				return null;
			}
			
			Interfaces.ISWRE swre = Factories.CommunicatorFactory.createSWRE();
			Replies.QueryUser rep = (Replies.QueryUser)swre.execute(cmd.output());
			if(rep == null || !rep.isOK()) {
				return null;
			}
			
			return rep.getUser();
		}
	}
	public BasicModels.MachineInfo QeuryMachineInfo(Object conditions) {
		if(Globals.Configurations.IsServer) {
			return Globals.Datas.DBManager.QueryMachineInfo(conditions);
		}
		else {
			Commands.QueryMachine cmd = new Commands.QueryMachine();
			if(conditions instanceof String) {
				cmd.setQueryConditions((String)conditions);
			}
			else if(conditions instanceof QueryCondition) {
				cmd.setQueryCondition((QueryCondition)conditions);
			}
			else if(conditions instanceof QueryConditions) {
				cmd.setQueryConditions((QueryConditions)conditions);
			}
			else {
				BasicEnums.ErrorType.UNKNOW.register("Type of conditions is Wrong");
				return null;
			}
			
			Interfaces.ISWRE swre = Factories.CommunicatorFactory.createSWRE();
			Replies.QueryMachine rep = (Replies.QueryMachine)swre.execute(cmd.output());
			if(rep == null || !rep.isOK()) {
				return null;
			}
			
			return rep.getMachineInfo();
		}
	}
	public BasicModels.DepotInfo QueryDepotInfo(Object conditions) {
		if(Globals.Configurations.IsServer) {
			return Globals.Datas.DBManager.QueryDepotInfo(conditions);
		}
		else {
			Commands.QueryDepot cmd = new Commands.QueryDepot();
			if(conditions instanceof String) {
				cmd.setQueryConditions((String)conditions);
			}
			else if(conditions instanceof QueryCondition) {
				cmd.setQueryCondition((QueryCondition)conditions);
			}
			else if(conditions instanceof QueryConditions) {
				cmd.setQueryConditions((QueryConditions)conditions);
			}
			else {
				BasicEnums.ErrorType.UNKNOW.register("Type of conditions is Wrong");
				return null;
			}
			
			Interfaces.ISWRE swre = Factories.CommunicatorFactory.createSWRE();
			Replies.QueryDepot rep = (Replies.QueryDepot)swre.execute(cmd.output());
			if(rep == null || !rep.isOK()) {
				return null;
			}
			
			return rep.getDepotInfo();
		}
	}
	public BasicModels.DataBaseInfo QueryDataBaseInfo(Object conditions) {
		if(Globals.Configurations.IsServer) {
			return Globals.Datas.DBManager.QueryDataBaseInfo(conditions);
		}
		else {
			Commands.QueryDataBase cmd = new Commands.QueryDataBase();
			if(conditions instanceof String) {
				cmd.setQueryConditions((String)conditions);
			}
			else if(conditions instanceof QueryCondition) {
				cmd.setQueryCondition((QueryCondition)conditions);
			}
			else if(conditions instanceof QueryConditions) {
				cmd.setQueryConditions((QueryConditions)conditions);
			}
			else {
				BasicEnums.ErrorType.UNKNOW.register("Type of conditions is Wrong");
				return null;
			}
			
			Interfaces.ISWRE swre = Factories.CommunicatorFactory.createSWRE();
			Replies.QueryDataBase rep = (Replies.QueryDataBase)swre.execute(cmd.output());
			if(rep == null || !rep.isOK()) {
				return null;
			}
			
			return rep.getDataBaseInfo();
		}
	}
	
	public boolean loginUser() {
		return this.loginUser(
				Globals.Datas.ThisUser.getLoginName(),
				Globals.Datas.ThisUser.getPassword(),
				Globals.Datas.ServerConnection
				);
	}
	public boolean loginUser(Interfaces.IConnection connection) {
		return this.loginUser(
				Globals.Datas.ThisUser.getLoginName(),
				Globals.Datas.ThisUser.getPassword(),
				connection
				);
	}
	public boolean loginUser(String loginName, String password) {
		return this.loginUser(
				loginName,
				password,
				Globals.Datas.ServerConnection
				);
	}
	public boolean loginUser(String loginName, String password, Interfaces.IConnection connection) {
		Interfaces.ISWRE swre = Factories.CommunicatorFactory.createSWRE();
		swre.setConnection(connection);
		
		Commands.LoginUser cmd = new Commands.LoginUser();
		cmd.getBasicMessagePackage().setPassword(password);
		cmd.setLoginName(loginName);
		
		Interfaces.IReplies rep = swre.execute(cmd.output());
		return rep != null && rep.isOK();
	}
	
	public boolean loginMachine() {
		return this.loginMachine(
				Globals.Configurations.This_MachineIndex,
				Globals.Configurations.This_UserIndex,
				Globals.Datas.ThisUser.getPassword(),
				Globals.Datas.ServerConnection
				);
	}
	public boolean loginMachine(Interfaces.IConnection connection) {
		return this.loginMachine(
				Globals.Configurations.This_MachineIndex,
				Globals.Configurations.This_UserIndex,
				Globals.Datas.ThisUser.getPassword(),
				connection
				);
	}
	public boolean loginMachine(long machineIndex, Interfaces.IConnection connection) {
		return this.loginMachine(
				machineIndex,
				Globals.Configurations.This_UserIndex,
				Globals.Datas.ThisUser.getPassword(),
				connection
				);
	}
	public boolean loginMachine(long machineIndex, long userIndex, String password, Interfaces.IConnection connection) {
		Interfaces.ISWRE swre = Factories.CommunicatorFactory.createSWRE();
		swre.setConnection(connection);
		
		Commands.LoginMachine cmd = new Commands.LoginMachine();
		cmd.getBasicMessagePackage().setSourMachineIndex(machineIndex);
		cmd.getBasicMessagePackage().setSourUserIndex(userIndex);
		cmd.getBasicMessagePackage().setPassword(password);
		
		Interfaces.IReplies rep = swre.execute(cmd.output());
		return rep != null && rep.isOK();
	}
	
	public String test() {
		return this.test(
				"This is a test String",
				Globals.Configurations.This_UserIndex,
				Globals.Datas.ThisUser.getPassword(),
				Globals.Datas.ServerConnection
				);
	}
	public String test(String str) {
		return this.test(
				str,
				Globals.Configurations.This_UserIndex,
				Globals.Datas.ThisUser.getPassword(),
				Globals.Datas.ServerConnection
				);
	}
	public String test(Interfaces.IConnection connection) {
		return this.test(
				"This is a test String",
				Globals.Configurations.This_UserIndex,
				Globals.Datas.ThisUser.getPassword(),
				connection
				);
	}
	public String test(String str, Interfaces.IConnection connection) {
		return this.test(
				str,
				Globals.Configurations.This_UserIndex,
				Globals.Datas.ThisUser.getPassword(),
				connection
				);
	}
	public String test(String str, long userIndex, String password, Interfaces.IConnection connection) {
		Interfaces.ISWRE swre = Factories.CommunicatorFactory.createSWRE();
		swre.setConnection(connection);
		
		Commands.Test cmd = new Commands.Test();
		cmd.getBasicMessagePackage().setSourUserIndex(userIndex);
		cmd.getBasicMessagePackage().setPassword(password);
		cmd.setTestString(str);
		
		Replies.Test rep = (Replies.Test)swre.execute(cmd.output());
		if(rep != null && rep.isOK()) {
			return rep.getTestString();
		}
		return null;
	}
}
