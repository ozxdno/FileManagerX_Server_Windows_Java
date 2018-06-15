package DepotChecker;

public class DepotChecker implements IDepotChecker{

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private BasicModels.DepotInfo depotInfo;
	private IDepotChecker checker;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setDepotInfo(BasicModels.DepotInfo depotInfo) {
		if(depotInfo == null) {
			return false;
		}
		this.depotInfo = depotInfo;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public BasicModels.DepotInfo getDepotInfo() {
		return this.depotInfo;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public DepotChecker() {
		initThis();
	}
	public DepotChecker(BasicModels.DepotInfo depotInfo) {
		initThis();
		this.setDepotInfo(depotInfo);
	}
	private void initThis() {
		this.depotInfo = new BasicModels.DepotInfo();
		this.checker = null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean initialize(Object infos) {
		this.depotInfo = (BasicModels.DepotInfo)infos;
		if(this.depotInfo.isLocal()) {
			this.checker = new LocalDepotChecker();
			this.checker.initialize(infos);
		}
		else {
			this.checker = new RemoteDepotChecker();
			this.checker.initialize(infos);
		}
		return true;
	}
	
	public boolean check() {
		return this.checker.check();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
