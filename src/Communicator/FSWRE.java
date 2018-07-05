package Communicator;

/**
 * SWRE Only For File Transport
 * 
 * 
 * @author ozxdno
 *
 */
public class FSWRE extends SWRE implements Interfaces.IFSWRE {

	private long serverMachine;
	private long clientMachine;
	private long user;
	
	public boolean setServerMachineIndex(long index) {
		this.serverMachine = index;
		return true;
	}
	public boolean setClientMachineIndex(long index) {
		this.clientMachine = index;
		return true;
	}
	public boolean setUserIndex(long index) {
		this.user = index;
		return true;
	}
	public boolean setConnection() {
		Interfaces.IConnection con = Factories.CommunicatorFactory.createRunningClientConnection(
				this.serverMachine,
				this.clientMachine,
				this.user
				);
		if(con == null) { return false; }
		con.setType(BasicEnums.ConnectionType.TRANSPORT_FILE);
		this.setConnection(con);
		Globals.Datas.Client.add(con);
		return true;
	}
	
	public FSWRE() {
		initThis();
	}
	private void initThis() {
		this.serverMachine = Globals.Configurations.Server_MachineIndex;
		this.clientMachine = Globals.Configurations.This_MachineIndex;
		this.user = Globals.Configurations.This_UserIndex;
		
		this.setSendWaitTicks(500);
		this.setReceiveWaitTicks(5000);
	}
	
	public Interfaces.IReplies execute(String command) {
		if(this.getConnection() == null) {
			return null;
		}
		Interfaces.IReplies rep = super.execute(command);
		return rep;
	}
	
	public Interfaces.IReplies executeDirectly() {
		return null;
	}
	public Interfaces.IReplies executeDirectly(String command) {
		return null;
	}
	
}
