package Communicator;

public class ExcuteClientCommand implements Interfaces.IExcuteClientCommand {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private Interfaces.IClientConnection connection;
	private BasicModels.Config cmd;
	
	private BasicEnums.CMDType type;
	private long userIndex;
	private String userPassword;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setConnection(Interfaces.IClientConnection connection) {
		if(connection == null) {
			return false;
		}
		this.connection = connection;
		return true;
	}
	public boolean setCMD(BasicModels.Config cmd) {
		if(cmd == null) {
			return false;
		}
		this.cmd = cmd;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Interfaces.IClientConnection getConnection() {
		return this.connection;
	}
	public BasicModels.Config getCMD() {
		return this.cmd;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public ExcuteClientCommand() {
		initThis();
	}
	public ExcuteClientCommand(Interfaces.IClientConnection connection) {
		initThis();
		this.setConnection(connection);
	}
	public ExcuteClientCommand(Interfaces.IClientConnection connection, BasicModels.Config cmd) {
		initThis();
		this.setConnection(connection);
		this.setCMD(cmd);
	}
	private void initThis() {
		this.connection = null;
		this.cmd = new BasicModels.Config();
		
		//type = BasicEnums.CMDType.Unsupport;
		userIndex = -1;
		userPassword = "";
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public String excute() {
		type = BasicEnums.CMDType.valueOf(cmd.fetchFirstString());
		if(!cmd.getIsOK()) {
			return this.showReceiveCommand(BasicEnums.CMDType.WrongReturn);
		}
		userIndex = cmd.fetchFirstLong();
		if(!cmd.getIsOK()) {
			return this.showReceiveCommand(BasicEnums.CMDType.WrongReturn);
		}
		userPassword = cmd.fetchFirstString();
		if(!cmd.getIsOK()) {
			return this.showReceiveCommand(BasicEnums.CMDType.WrongReturn);
		}
		
		if(type.compareTo(BasicEnums.CMDType.Normal) > 0) {
			return cmd.output();
		}
		
		return cmd.output();
	}
	public String excute(String cmd) {
		this.cmd.input(cmd);
		return this.excute();
	}
	public String excute(BasicModels.Config cmd) {
		this.cmd = cmd;
		return this.excute();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	private String showReceiveCommand(BasicEnums.CMDType type) {
		BasicModels.Config c = new BasicModels.Config();
		c.setField(type.toString());
		c.addToBottom(this.userIndex);
		c.addToBottom(this.userPassword);
		return c.output();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
}
