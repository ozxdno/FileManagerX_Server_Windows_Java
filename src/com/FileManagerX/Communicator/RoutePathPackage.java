package com.FileManagerX.Communicator;

public class RoutePathPackage implements com.FileManagerX.Interfaces.IRoutePathPackage {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private static java.util.List<Long> LocalMountPath = new java.util.ArrayList<>();
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private long startTime1;
	private long endTime1;
	private long startTime2;
	private long endTime2;
	
	private long sourMountServer;
	private long destMountServer;
	
	private java.util.List<Long> sourMountPath;
	private java.util.List<Long> destMountPath;
	private java.util.List<Long> recommendPath;
	private java.util.List<Long> actualPath;
	private java.util.List<Long> visitedPath;
	
	private int recommendDepth;
	private int actualDepth;
	
	private com.FileManagerX.BasicEnums.RppExecutePart execute;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setStartTime1(long startTime) {
		if(startTime < 0) {
			startTime = 0;
		}
		this.startTime1 = startTime;
		return true;
	}
	public boolean setStartTime1() {
		this.startTime1 = com.FileManagerX.Tools.Time.getTicks();
		return true;
	}
	public boolean setEndTime1(long endTime) {
		if(endTime < 0) {
			endTime = 0;
		}
		this.endTime1 = endTime;
		return true;
	}
	public boolean setEndTime1() {
		this.endTime1 = com.FileManagerX.Tools.Time.getTicks();
		return true;
	}
	public boolean setStartTime2(long startTime) {
		if(startTime < 0) {
			startTime = 0;
		}
		this.startTime2 = startTime;
		return true;
	}
	public boolean setStartTime2() {
		this.startTime2 = com.FileManagerX.Tools.Time.getTicks();
		return true;
	}
	public boolean setEndTime2(long endTime) {
		if(endTime < 0) {
			endTime = 0;
		}
		this.endTime2 = endTime;
		return true;
	}
	public boolean setEndTime2() {
		this.endTime2 = com.FileManagerX.Tools.Time.getTicks();
		return true;
	}
	
	public boolean setSourMountServer(long server) {
		this.sourMountServer = server;
		return true;
	}
	public boolean setDestMountServer(long server) {
		this.destMountServer = server;
		return true;
	}
	
	public boolean setSourMountPath(java.util.List<Long> path) {
		if(path == null) { return false; }
		this.sourMountPath = path;
		return true;
	}
	public boolean setDestMountPath(java.util.List<Long> path) {
		if(path == null) { return false; }
		this.destMountPath = path;
		return true;
	}
	public boolean setRecommendPath(java.util.List<Long> path) {
		if(path == null) { return false; }
		this.recommendPath = path;
		return true;
	}
	public boolean setActualPath(java.util.List<Long> path) {
		if(path == null) { return false; }
		this.actualPath = path;
		return true;
	}
	public boolean setVisitedPath(java.util.List<Long> path) {
		if(path == null) { return false; }
		this.visitedPath = path;
		return true;
	}
	public boolean setLocalMountPath(java.util.List<Long> path) {
		if(path == null) { return false; }
		RoutePathPackage.LocalMountPath = path;
		return true;
	}
	
	public boolean setRecommendDepth(int depth) {
		this.recommendDepth = depth;
		return true;
	}
	public boolean setActualDepth(int depth) {
		this.actualDepth = depth;
		return true;
	}
	public boolean setExecutePart(com.FileManagerX.BasicEnums.RppExecutePart execute) {
		this.execute = execute;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public long getStartTime1() {
		return this.startTime1;
	}
	public long getEndTime1() {
		return this.endTime1;
	}
	public long getStartTime2() {
		return this.startTime2;
	}
	public long getEndTime2() {
		return this.endTime2;
	}
	
	public long getSourMountServer() {
		return this.sourMountServer;
	}
	public long getDestMountServer() {
		return this.destMountServer;
	}
	
	public java.util.List<Long> getSourMountPath() {
		return this.sourMountPath;
	}
	public java.util.List<Long> getDestMountPath() {
		return this.destMountPath;
	}
	public java.util.List<Long> getRecommendPath() {
		return this.recommendPath;
	}
	public java.util.List<Long> getActualPath() {
		return this.actualPath;
	}
	public java.util.List<Long> getVisitedPath() {
		return this.visitedPath;
	}
	public java.util.List<Long> getLocalMountPath() {
		return RoutePathPackage.LocalMountPath;
	}
	
	public int getRecommendDepth() {
		return this.recommendDepth;
	}
	public int getActualDepth() {
		return this.actualDepth;
	}
	public com.FileManagerX.BasicEnums.RppExecutePart getExecutePart() {
		return this.execute;
	}
	
	public long getRecommendMachineByDepth() {
		if(com.FileManagerX.BasicEnums.RppExecutePart.S.equals(this.execute)) {
			boolean inS = 0 <= this.recommendDepth && this.recommendDepth < this.sourMountPath.size();
			if(inS) { return this.sourMountPath.get(this.recommendDepth); }
		}
		if(com.FileManagerX.BasicEnums.RppExecutePart.R.equals(this.execute)) {
			boolean inR = 0 <= this.recommendDepth && this.recommendDepth < this.recommendPath.size();
			if(inR) { return this.recommendPath.get(this.recommendDepth); }
		}
		if(com.FileManagerX.BasicEnums.RppExecutePart.D.equals(this.execute)) {
			boolean inD = 0 <= this.recommendDepth && this.recommendDepth < this.destMountPath.size();
			if(inD) { return this.destMountPath.get(this.recommendDepth); }
		}
		
		return -1;
	}
	public long getActualMachineByDepth() {
		boolean inA = 0 <= this.actualDepth && this.actualDepth < this.actualPath.size();
		return inA ? this.actualPath.get(this.actualDepth) : -1;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public RoutePathPackage() {
		initThis();
	}
	private void initThis() {
		this.startTime1 = 0;
		this.endTime1 = 0;
		this.startTime2 = 0;
		this.endTime2 = 0;
		
		this.sourMountServer = -1;
		this.destMountServer = -1;
		
		this.sourMountPath = new java.util.ArrayList<>();
		this.destMountPath = new java.util.ArrayList<>();
		this.recommendPath = new java.util.ArrayList<>();
		this.actualPath = new java.util.ArrayList<>();
		this.visitedPath = new java.util.ArrayList<>();
		
		this.recommendDepth = -1;
		this.actualDepth = -1;
		this.execute = com.FileManagerX.BasicEnums.RppExecutePart.B;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void clear() {
		initThis();
	}
	public String toString() {
		long go = this.endTime1 - this.startTime1;
		long back = this.endTime2 - this.startTime2;
		long execute = this.startTime2 - this.endTime1;
		if(go < 0) { go = 0; }
		if(back < 0) { back = 0; }
		if(execute < 0) { execute = 0; }
		
		String s = "[go]: " + go + " [back]: " + back + " [execute]: " + execute;
		return s;
	}
	public com.FileManagerX.BasicModels.Config toConfig() {
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config();
		c.setField(this.getClass().getSimpleName());
		c.addToBottom(this.startTime1);
		c.addToBottom(this.endTime1);
		c.addToBottom(this.startTime2);
		c.addToBottom(this.endTime2);
		
		c.addToBottom(this.sourMountServer);
		c.addToBottom(this.destMountServer);
		
		c.addToBottom(this.recommendDepth);
		c.addToBottom(this.actualDepth);
		c.addToBottom(this.execute.toString());
		
		String sp = com.FileManagerX.Tools.StringUtil.link(
				com.FileManagerX.Tools.List2Array.toLongArray(this.sourMountPath),
				" "
			);
		String dp = com.FileManagerX.Tools.StringUtil.link(
				com.FileManagerX.Tools.List2Array.toLongArray(this.destMountPath),
				" "
			);
		String rp = com.FileManagerX.Tools.StringUtil.link(
				com.FileManagerX.Tools.List2Array.toLongArray(this.recommendPath),
				" "
			);
		String ap = com.FileManagerX.Tools.StringUtil.link(
				com.FileManagerX.Tools.List2Array.toLongArray(this.actualPath),
				" "
			);
		String vp = com.FileManagerX.Tools.StringUtil.link(
				com.FileManagerX.Tools.List2Array.toLongArray(this.visitedPath),
				" "
			);
		
		c.addToBottom(sp);
		c.addToBottom(dp);
		c.addToBottom(rp);
		c.addToBottom(ap);
		c.addToBottom(vp);
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
			this.startTime1 = c.fetchFirstLong();
			if(!c.getIsOK()) { return c; }
			this.endTime1 = c.fetchFirstLong();
			if(!c.getIsOK()) { return c; }
			this.startTime2 = c.fetchFirstLong();
			if(!c.getIsOK()) { return c; }
			this.endTime2 = c.fetchFirstLong();
			if(!c.getIsOK()) { return c; }
			this.sourMountServer = c.fetchFirstLong();
			if(!c.getIsOK()) { return c; }
			this.destMountServer = c.fetchFirstLong();
			if(!c.getIsOK()) { return c; }
			this.recommendDepth = c.fetchFirstInt();
			if(!c.getIsOK()) { return c; }
			this.actualDepth = c.fetchFirstInt();
			if(!c.getIsOK()) { return c; }
			this.execute = com.FileManagerX.BasicEnums.RppExecutePart.valueOf(c.fetchFirstString());
			if(!c.getIsOK()) { return c; }
			
			this.sourMountPath = com.FileManagerX.Tools.StringUtil.split2long(c.fetchFirstString(), " ");
			if(!c.getIsOK()) { return c; }
			this.destMountPath = com.FileManagerX.Tools.StringUtil.split2long(c.fetchFirstString(), " ");
			if(!c.getIsOK()) { return c; }
			this.recommendPath = com.FileManagerX.Tools.StringUtil.split2long(c.fetchFirstString(), " ");
			if(!c.getIsOK()) { return c; }
			this.actualPath = com.FileManagerX.Tools.StringUtil.split2long(c.fetchFirstString(), " ");
			if(!c.getIsOK()) { return c; }
			this.visitedPath = com.FileManagerX.Tools.StringUtil.split2long(c.fetchFirstString(), " ");
			if(!c.getIsOK()) { return c; }
			
			return c;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			c.setIsOK(false);
			return c;
		}
	}
	public void copyReference(Object o) {
		if(o == null) { return; }
		
		if(o instanceof RoutePathPackage) {
			RoutePathPackage rpp = (RoutePathPackage)o;
			this.startTime1 = rpp.startTime1;
			this.endTime1 = rpp.endTime1;
			this.startTime2 = rpp.startTime2;
			this.endTime2 = rpp.endTime2;
			this.sourMountServer = rpp.sourMountServer;
			this.destMountServer = rpp.destMountServer;
			this.recommendDepth = rpp.recommendDepth;
			this.actualDepth = rpp.actualDepth;
			this.sourMountPath = rpp.sourMountPath;
			this.destMountPath = rpp.destMountPath;
			this.recommendPath = rpp.recommendPath;
			this.actualPath = rpp.actualPath;
			this.visitedPath = rpp.visitedPath;
			this.execute = rpp.execute;
			return;
		}
	}
	public void copyValue(Object o) {
		if(o == null) { return; }
		
		if(o instanceof RoutePathPackage) {
			RoutePathPackage rpp = (RoutePathPackage)o;
			this.startTime1 = rpp.startTime1;
			this.endTime1 = rpp.endTime1;
			this.startTime2 = rpp.startTime2;
			this.endTime2 = rpp.endTime2;
			this.sourMountServer = rpp.sourMountServer;
			this.destMountServer = rpp.destMountServer;
			this.recommendDepth = rpp.recommendDepth;
			this.actualDepth = rpp.actualDepth;
			this.sourMountPath.clear();
			this.destMountPath.clear();
			this.recommendPath.clear();
			this.actualPath.clear();
			this.visitedPath.clear();
			this.sourMountPath.addAll(rpp.sourMountPath);
			this.destMountPath.addAll(rpp.destMountPath);
			this.recommendPath.addAll(rpp.recommendPath);
			this.actualPath.addAll(rpp.actualPath);
			this.visitedPath.addAll(rpp.visitedPath);
			this.execute = rpp.execute;
			return;
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public <T> void reverse(java.util.List<T> path) {
		if(path == null || path.size() < 2) { return; }
		int size = path.size();
		int half = path.size() >> 1;
		for(int i=0; i<half; i++) {
			T temp = path.get(i);
			path.set(i, path.get(size-1-i));
			path.set(size-1-i, temp);
		}
	}
	public boolean visited(long machine) {
		if(this.visitedPath == null) { return false; }
		for(Long m : this.visitedPath) {
			if(m == machine) { return true; }
		}
		return false;
	}
	
	public boolean addAsNext() {
		/*
		if(com.FileManagerX.BasicEnums.RppExecutePart.E.equals(this.execute)) {
			if(this.sourMountServer <= 0) {
				this.sourMountServer = this.actualPath.size() == 0 ?
						com.FileManagerX.Globals.Configurations.This_MachineIndex :
						this.actualPath.get(0);
			}
			if(this.destMountServer <= 0) {
				this.destMountServer = com.FileManagerX.Globals.Configurations.This_MachineIndex;
			}
		}
		*/
		
		
		long machine = com.FileManagerX.Globals.Configurations.This_MachineIndex;
		long prev = this.getActualMachineByDepth();
		if(this.actualDepth >=0 && prev == machine) { return true; }
		
		this.actualPath.add(machine);
		this.actualDepth++;
		this.visitedPath.add(machine);
		return true;
	}
	public boolean delToBack() {
		if(this.actualDepth < 0 || this.actualDepth >= this.actualPath.size()) { return false; }
		this.actualPath.remove(this.actualDepth);
		this.actualDepth--;
		return true;
	}
	public void updateExecutePart(long dest) {
		// D -> E
		if(com.FileManagerX.Globals.Configurations.This_MachineIndex == dest) {
			this.execute = com.FileManagerX.BasicEnums.RppExecutePart.E;
		}
		
		// 自动重载进行转发
		if(com.FileManagerX.BasicEnums.RppExecutePart.E.equals(this.execute)) {
			if(dest != com.FileManagerX.Globals.Configurations.This_MachineIndex) {
				this.execute = com.FileManagerX.BasicEnums.RppExecutePart.R;
				this.actualDepth =this.actualPath.size()-1;
				this.destMountPath.clear();
				this.destMountServer = -1;
			}
		}
		
		// 填充源挂载路径
		if(com.FileManagerX.BasicEnums.RppExecutePart.E.equals(this.execute)) {
			if(com.FileManagerX.Globals.Configurations.IsServer) {
				if(this.sourMountServer < 0) {
					this.sourMountServer = com.FileManagerX.Globals.Configurations.This_MachineIndex;
				}
			}
		}
		
		// 填充目标挂载路径
		if(com.FileManagerX.BasicEnums.RppExecutePart.E.equals(this.execute)) {
			if(com.FileManagerX.Globals.Configurations.IsServer) {
				if(this.destMountServer < 0) {
					this.destMountServer = com.FileManagerX.Globals.Configurations.This_MachineIndex;
				}
			}
		}
		
		// 自动填充源挂载服务器
		if(com.FileManagerX.BasicEnums.RppExecutePart.S.equals(this.execute)) {
			if(this.sourMountServer < 0) {
				if(com.FileManagerX.Globals.Configurations.IsServer) {
					this.sourMountServer = com.FileManagerX.Globals.Configurations.This_MachineIndex;
					this.execute = com.FileManagerX.BasicEnums.RppExecutePart.R;
					this.recommendDepth = -1;
				}
			}
		}
		
		// 自动填充目标挂载服务器
		if(com.FileManagerX.BasicEnums.RppExecutePart.R.equals(this.execute)) {
			if(!com.FileManagerX.Globals.Configurations.IsServer) {
				if(this.actualPath.size() != 0) {
					this.destMountServer = this.getActualMachineByDepth();
					this.execute = com.FileManagerX.BasicEnums.RppExecutePart.D;
					this.recommendDepth = -1;
				}
			}
		}
		
		// B -> S
		if(com.FileManagerX.BasicEnums.RppExecutePart.B.equals(this.execute)) {
			this.execute = com.FileManagerX.BasicEnums.RppExecutePart.S;
			this.recommendDepth = -1;
		}
		
		// S -> R
		if(com.FileManagerX.BasicEnums.RppExecutePart.S.equals(this.execute)) {
			if(com.FileManagerX.Globals.Configurations.IsServer) {
				if(this.sourMountServer < 0) {
					this.execute = com.FileManagerX.BasicEnums.RppExecutePart.R;
					this.recommendDepth = -1;
				}
			}
		}
		
		// R -> D
		if(com.FileManagerX.BasicEnums.RppExecutePart.R.equals(this.execute)) {
			if(com.FileManagerX.Globals.Configurations.This_MachineIndex == this.destMountServer) {
				if(this.destMountServer > 0) {
					this.execute = com.FileManagerX.BasicEnums.RppExecutePart.D;
					this.recommendDepth = -1;
				}
			}
		}
	}
	public void refreshRecommendPath(com.FileManagerX.Interfaces.IRoutePathPackage rpp) {
		this.sourMountServer = rpp.getDestMountServer();
		this.destMountServer = rpp.getSourMountServer();
		this.sourMountPath.clear();
		this.recommendPath.clear();
		this.destMountPath.clear();
		
		int idx1 = 0, idx2 = 0;
		for(int i=0; i<rpp.getActualPath().size(); i++) {
			if(rpp.getActualPath().get(i) == rpp.getSourMountServer()) {
				idx1 = i;
				continue;
			}
			if(rpp.getActualPath().get(i) == rpp.getDestMountServer()) {
				idx2 = i;
				break;
			}
		}
		
		for(int i=0; i<=idx1; i++) {
			this.destMountPath.add(rpp.getActualPath().get(i));
		}
		for(int i=idx1; i<=idx2; i++) {
			this.recommendPath.add(rpp.getActualPath().get(i));
		}
		for(int i=idx2; i<rpp.getActualPath().size(); i++) {
			this.sourMountPath.add(rpp.getActualPath().get(i));
		}
		
		this.reverse(this.sourMountPath);
		this.reverse(this.recommendPath);
		this.reverse(this.destMountPath);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
