package Communicator;

public class DSWRE extends SWRE implements Interfaces.IDSWRE {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private long destMachineIndex;
	private BasicModels.MachineInfo destMachineInfo;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public DSWRE() {
		initThis();
	}
	public void initThis() {
		destMachineIndex = Globals.Configurations.Server_MachineIndex;
		destMachineInfo = Globals.Datas.ServerMachine;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setDestMachineIndex(long destMachineIndex) {
		if(destMachineIndex <= 0) {
			return false;
		}
		this.destMachineIndex = destMachineIndex;
		return true;
	}
	public boolean setDestMachineInfo(BasicModels.MachineInfo destMachineInfo) {
		if(destMachineInfo == null) {
			return false;
		}
		this.destMachineInfo = destMachineInfo;
		this.destMachineIndex = this.destMachineInfo.getIndex();
		return true;
	}
	public boolean setDestMachineInfo() {
		if(Globals.Configurations.IsServer) {
			BasicModels.MachineInfo m = Globals.Datas.DBManager.QueryMachineInfo("[&] Index = " + this.destMachineIndex);
			if(m == null) {
				return false;
			}
			this.destMachineInfo = m;
			this.destMachineIndex = this.destMachineInfo.getIndex();
			return true;
		}
		else {
			Commands.QueryMachine qm = new Commands.QueryMachine();
			qm.setQueryConditions("[&] Index = " + this.destMachineIndex);
			Interfaces.ISWRE swre = Factories.CommunicatorFactory.createSWRE();
			Replies.QueryMachine rep = (Replies.QueryMachine)swre.execute(qm.output());
			if(rep == null || !rep.isOK()) {
				return false;
			}
			this.destMachineInfo = rep.getMachineInfo();
			this.destMachineIndex = this.destMachineInfo.getIndex();
			return true;
		}
	}
	public boolean setConnection() {
		boolean inLAN = false;
		for(int i=0; i<Globals.Datas.LANMachineIndexes.size(); i++) {
			if(Globals.Datas.LANMachineIndexes.get(i) == this.destMachineIndex) {
				inLAN = true;
				break;
			}
		}
		if(!inLAN) {
			BasicEnums.ErrorType.UNKNOW.register("Target Machine is Not in LAN List in CFG File");
			return false;
		}
		
		Interfaces.IClientConnection con = Factories.CommunicatorFactory.createClientConnection();
		con.setServerMachineInfo(this.destMachineInfo);
		con.setClientMachineInfo(Globals.Datas.ThisMachine);
		con.setSocket();
		con.connect();
		super.setConnection(con);
		return con.isRunning();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public long getDestMachineIndex() {
		return this.destMachineIndex;
	}
	public BasicModels.MachineInfo getDestMachineInfo() {
		return this.destMachineInfo;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
