package com.FileManagerX.Communicator;

public class RoutePathPackage implements com.FileManagerX.Interfaces.IRoutePathPackage {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private long startTime;
	private long arriveTime;
	private long backTime;
	
	private long sourMachine;
	private long destMachine;
	
	private long depth;
	private java.util.List<Long> deliverMachines;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setStartTime(long startTime) {
		if(startTime < 0) {
			startTime = 0;
		}
		this.startTime = startTime;
		return true;
	}
	public boolean setStartTime() {
		this.startTime = com.FileManagerX.Tools.Time.getTicks();
		return true;
	}
	public boolean setArriveTime(long arriveTime) {
		if(arriveTime < 0) {
			arriveTime = 0;
		}
		this.arriveTime = arriveTime;
		return true;
	}
	public boolean setArriveTime() {
		this.arriveTime = com.FileManagerX.Tools.Time.getTicks();
		return true;
	}
	public boolean setBackTime(long backTime) {
		if(backTime < 0) {
			backTime = 0;
		}
		this.backTime = backTime;
		return true;
	}
	public boolean setBackTime() {
		this.backTime = com.FileManagerX.Tools.Time.getTicks();
		return true;
	}
	
	public boolean setDepth(long depth) {
		if(depth < 0) {
			depth = 0;
		}
		this.depth = depth;
		return true;
	}
	public boolean setMoreDepth() {
		this.depth++;
		return true;
	}
	public boolean setLessDepth() {
		this.depth--;
		return true;
	}
	
	public boolean setSourMachine(long sour) {
		this.sourMachine = sour;
		return true;
	}
	public boolean setDestMachine(long dest) {
		this.sourMachine = dest;
		return true;
	}
	public boolean setDeliverMachines(java.util.List<Long> machines) {
		if(machines == null) {
			return false;
		}
		this.deliverMachines = machines;
		return true;
	}
	public boolean setDeliverMachine(long deliver) {
		if(depth < 0) {
			return false;
		}
		if(depth > this.deliverMachines.size() + 1) {
			return false;
		}
		
		if(depth == 0) {
			this.sourMachine = deliver;
		}
		if(depth == this.deliverMachines.size() + 1) {
			this.destMachine = deliver;
		}
		
		this.deliverMachines.set((int)depth-1, deliver);
		return true;
	}
	public boolean setDeliverMachine(long depth, long deliver) {
		if(depth < 0) {
			return false;
		}
		if(depth > this.deliverMachines.size() + 1) {
			return false;
		}
		
		if(depth == 0) {
			this.sourMachine = deliver;
		}
		if(depth == this.deliverMachines.size() + 1) {
			this.destMachine = deliver;
		}
		
		this.deliverMachines.set((int)depth-1, deliver);
		return true;
	}
	
	public boolean setThis(long sourMachine, long destMachine) {
		boolean ok = true;
		ok &= this.setSourMachine(sourMachine);
		ok &= this.setDestMachine(destMachine);
		this.deliverMachines.clear();
		if(this.destMachine != com.FileManagerX.Globals.Configurations.Server_MachineIndex) {
			this.addDeliverMachine(com.FileManagerX.Globals.Configurations.Server_MachineIndex);
		}
		return ok;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public long getStartTime() {
		return this.startTime;
	}
	public long getArriveTime() {
		return this.arriveTime;
	}
	public long getBackTime() {
		return this.backTime;
	}
	
	public long getDepth() {
		return this.depth;
	}
	
	public long getSourMachine() {
		return this.sourMachine;
	}
	public long getDestMachine() {
		return this.destMachine;
	}
	public java.util.List<Long> getDeliverMachines() {
		return this.deliverMachines;
	}
	public long getDeliverMachine() {
		if(this.deliverMachines.size() == 0) {
			return 0;
		}
		if(this.depth < 0) {
			return 0;
		}
		if(this.depth > this.deliverMachines.size() + 1) {
			return 0;
		}
		if(this.depth == 0) {
			return this.sourMachine;
		}
		if(this.depth == this.deliverMachines.size() + 1) {
			return this.destMachine;
		}
		return this.deliverMachines.get((int) (this.depth-1));
	}
	public long getDeliverMachine(long depth) {
		if(this.deliverMachines.size() == 0) {
			return 0;
		}
		if(depth < 0) {
			return 0;
		}
		if(depth > this.deliverMachines.size() + 1) {
			return 0;
		}
		if(depth == 0) {
			return this.sourMachine;
		}
		if(depth == this.deliverMachines.size() + 1) {
			return this.destMachine;
		}
		return this.deliverMachines.get((int) (depth-1));
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public RoutePathPackage() {
		initThis();
	}
	private void initThis() {
		this.startTime = com.FileManagerX.Tools.Time.getTicks();
		this.arriveTime = 0;
		this.backTime = 0;
		
		this.depth = 1;
		
		this.sourMachine = com.FileManagerX.Globals.Configurations.This_MachineIndex;
		this.destMachine = com.FileManagerX.Globals.Configurations.Server_MachineIndex;
		this.deliverMachines = new java.util.ArrayList<>();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void clear() {
		initThis();
	}
	public String toString() {
		long route1 = this.arriveTime - this.startTime;
		long route2 = this.backTime - this.startTime;
		
		String s = "[TimeSpan: " + route1 + "/" + route2 + "] " + this.sourMachine + "->";
		for(int i=0; i<this.deliverMachines.size(); i++) {
			s += this.deliverMachines.get(i) + "->";
		}
		s += this.destMachine;
		return s;
	}
	public String output() {
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config();
		c.setField(this.getClass().getSimpleName());
		c.addToBottom(this.startTime);
		c.addToBottom(this.arriveTime);
		c.addToBottom(this.backTime);
		c.addToBottom(this.depth);
		c.addToBottom(this.sourMachine);
		c.addToBottom(this.destMachine);
		c.addToBottom(com.FileManagerX.Tools.String.link(
				com.FileManagerX.Tools.List2Array.toLongArray(deliverMachines), " "));
		
		return c.output();
	}
	public String input(String in) {
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config(in);
		
		this.startTime = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		this.arriveTime = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		this.backTime = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		this.depth = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		this.sourMachine = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		this.destMachine = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		this.deliverMachines = com.FileManagerX.Tools.Array2List.toLongList(
				com.FileManagerX.Tools.String.splitToLongArray(c.fetchFirstString(), ' '));
		if(!c.getIsOK()) { return null; }
		
		return c.output();
	}
	public void copyReference(Object o) {
		if(o instanceof RoutePathPackage) {
			RoutePathPackage rpp = (RoutePathPackage)o;
			this.startTime = rpp.startTime;
			this.arriveTime = rpp.arriveTime;
			this.backTime = rpp.backTime;
			this.depth = rpp.depth;
			this.sourMachine = rpp.sourMachine;
			this.destMachine = rpp.destMachine;
			this.deliverMachines = rpp.deliverMachines;
		}
	}
	public void copyValue(Object o) {
		if(o instanceof RoutePathPackage) {
			RoutePathPackage rpp = (RoutePathPackage)o;
			this.startTime = rpp.startTime;
			this.arriveTime = rpp.arriveTime;
			this.backTime = rpp.backTime;
			this.depth = rpp.depth;
			this.sourMachine = rpp.sourMachine;
			this.destMachine = rpp.destMachine;
			this.deliverMachines.clear();
			this.deliverMachines.addAll(rpp.deliverMachines);
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void reverse() {
		this.startTime = com.FileManagerX.Tools.Time.getTicks();
		this.arriveTime = 0;
		this.backTime = 0;
		this.depth = 1;
		long temp = this.sourMachine;
		this.sourMachine = this.destMachine;
		this.destMachine = temp;
		int size = this.deliverMachines.size();
		for(int i=0; i<(size>>1); i++) {
			temp = this.deliverMachines.get(i);
			this.deliverMachines.set(i, this.deliverMachines.get(size-1-i));
			this.deliverMachines.set(size-1-i, temp);
		}
	}
	public void addDeliverMachine(long machine) {
		this.deliverMachines.add(machine);
	}
	public com.FileManagerX.Interfaces.IClientConnection nextServerConnection() {
		
		long prevServer = this.deliverMachines.size() == 0 ? 0 :
			this.deliverMachines.get(this.deliverMachines.size()-1);
		
		for(com.FileManagerX.Interfaces.IClientConnection con :
			com.FileManagerX.Globals.Datas.OtherServers.getContent()) {
			
			if(!con.isRunning()) {
				continue;
			}
			if(con.getServerMachineInfo().getIndex() > prevServer) {
				return con;
			}
		}
		
		return com.FileManagerX.Globals.Datas.ServerConnection;
	}
	public void backToMachine(long machine) {
		int index = -1;
		for(int i=0; i<this.deliverMachines.size(); i++) {
			if(this.deliverMachines.get(i) == machine) {
				index = i;
				break;
			}
		}
		if(index == -1) {
			return;
		}
		for(int i=index+1; i<this.deliverMachines.size(); i++) {
			this.deliverMachines.remove(index+1);
		}
		this.depth = this.deliverMachines.size();
	}
	public void backToMachine() {
		long machine = com.FileManagerX.Globals.Configurations.This_MachineIndex;
		
		int index = -1;
		for(int i=0; i<this.deliverMachines.size(); i++) {
			if(this.deliverMachines.get(i) == machine) {
				index = i;
				break;
			}
		}
		if(index == -1) {
			return;
		}
		for(int i=index+1; i<this.deliverMachines.size(); i++) {
			this.deliverMachines.remove(index+1);
		}
		this.depth = this.deliverMachines.size();
	}
	public boolean passed(long machine) {
		if(machine == this.sourMachine) {
			return true;
		}
		for(long m : this.deliverMachines) {
			if(m == machine) { return true; }
		}
		return false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
