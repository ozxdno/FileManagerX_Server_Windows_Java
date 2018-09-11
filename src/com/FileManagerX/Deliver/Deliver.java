package com.FileManagerX.Deliver;

public class Deliver {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static String FAILED_NO_RECEIVER = "No Receiver";
	public final static String MYNET_RECOMMEND_PATHES = "Recommend Pathes";
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public final static boolean deliver(com.FileManagerX.Interfaces.ITransport t) {
		boolean bc = t.getBasicMessagePackage().getBroadcast().getBroadcastThisDepth();
		boolean ok = bc ? broadcast(t) : p2p(t);
		if(ok) { return true; }
		return executeBack(t) ? true : executeNoReceiver(t);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static boolean completeRPP(com.FileManagerX.Interfaces.ITransport t) {
		// RPP
		com.FileManagerX.Interfaces.IRoutePathPackage rpp = t.getBasicMessagePackage().getRoutePathPackage();
		
		// Initializing ...
		if(com.FileManagerX.Globals.Configurations.This_MachineIndex <= 0) {
			return true;
		}
		
		// Direct connect
		if(t.getBasicMessagePackage().isDirect()) {
			return true;
		}
		
		// Invalid Destination
		if(com.FileManagerX.BasicEnums.RppExecutePart.E.equals(rpp.getExecutePart()) &&
				t.getBasicMessagePackage().getDestMachineIndex() != 
				com.FileManagerX.Globals.Configurations.This_MachineIndex) {
			rpp.setExecutePart(com.FileManagerX.BasicEnums.RppExecutePart.R);
			rpp.setActualDepth(rpp.getActualPath().size()-1);
			rpp.setDestMountServer(-1);
		}
		
		// Finished RPP
		if(rpp.getDestMountServer() >= 0) {
			return true;
		}
		
		// Destination
		long dest = t.getBasicMessagePackage().getDestMachineIndex();
		
		// Complete By Buffer
		if(com.FileManagerX.Globals.Configurations.This_MachineIndex ==
				t.getBasicMessagePackage().getSourMachineIndex()) {
			com.FileManagerX.MyNet.Group tempGroup = com.FileManagerX.Factories.MyNetFactory.createTempGroup();
			com.FileManagerX.MyNet.User user = tempGroup.searchByKey(MYNET_RECOMMEND_PATHES);
			if(user != null) {
				com.FileManagerX.MyNet.Machine machine = user.fetchByKey(dest);
				if(machine != null) {
					rpp.setSourMountPath(machine.getRoutePathPackage().getSourMountPath());
					rpp.setRecommendPath(machine.getRoutePathPackage().getRecommendPath());
					rpp.setDestMountPath(machine.getRoutePathPackage().getDestMountPath());
					rpp.setSourMountServer(machine.getRoutePathPackage().getSourMountServer());
					rpp.setDestMountServer(machine.getRoutePathPackage().getDestMountServer());
					return true;
				}
			}
		}
		
		
		// Complete By Server
		if(!com.FileManagerX.Globals.Configurations.IsServer) {
			return false;
		}
		
		// Initializing ...
		if(com.FileManagerX.Globals.Datas.DBManager.getDBInfo() == null) {
			return false;
		}
		
		// Complete destination
		if(rpp.getDestMountServer() <= 0) {
			com.FileManagerX.BasicModels.MachineInfo m = (com.FileManagerX.BasicModels.MachineInfo)
					com.FileManagerX.Globals.Datas.DBManager.query2(
							"qcs = 1|[&] index = " + dest,
							com.FileManagerX.DataBase.Unit.Machine
						);
			String dp = m == null ? "" : m.getPath();
			
			java.util.ArrayList<Long> path = com.FileManagerX.Tools.StringUtil.split2long(dp, " ");
			long dserver = path.size() > 0 ? path.get(path.size()-1) : -1;
			
			rpp.reverse(path);
			rpp.setDestMountPath(path);
			rpp.setDestMountServer(dserver);
			return true;
		}
		
		return false;
	}
	
	public final static boolean refreshRPP(com.FileManagerX.Interfaces.ITransport t) {
		if(com.FileManagerX.Globals.Configurations.This_MachineIndex <= 0) {
			return false;
		}
		if(com.FileManagerX.Globals.Configurations.This_MachineIndex !=
				t.getBasicMessagePackage().getDestMachineIndex()) {
			return false;
		}
		if(!(t instanceof com.FileManagerX.Replies.BaseReply)) {
			return false;
		}
		com.FileManagerX.Replies.BaseReply rep = (com.FileManagerX.Replies.BaseReply)t;
		if(!rep.isOK()) {
			return false;
		}
		
		com.FileManagerX.Interfaces.IRoutePathPackage rpp = t.getBasicMessagePackage().getRoutePathPackage();
		boolean existU = false;
		boolean existM = false;
		
		com.FileManagerX.MyNet.Machine machine = null;
		com.FileManagerX.MyNet.User user = null;
		
		com.FileManagerX.MyNet.Group tempGroup = com.FileManagerX.Factories.MyNetFactory.createTempGroup();
		user = tempGroup.searchByKey(MYNET_RECOMMEND_PATHES);
		existU = user != null;
		if(!existU) {
			user = new com.FileManagerX.MyNet.User();
			user.setName(MYNET_RECOMMEND_PATHES);
			com.FileManagerX.BasicCollections.BasicLRUMap<com.FileManagerX.MyNet.Machine> content =
					new com.FileManagerX.BasicCollections.BasicLRUMap<>();
			content.setSafe(true);
			content.setKey(com.FileManagerX.MyNet.User.KeyForIndex);
			content.setLimit(com.FileManagerX.Globals.Configurations.LimitForUserMachine);
			user.setContent(content);
			tempGroup.add(user);
		}
		
		machine = user.searchByKey(t.getBasicMessagePackage().getSourMachineIndex());
		existM = machine != null;
		if(!existM) {
			machine = new com.FileManagerX.MyNet.Machine();
			machine.setName(String.valueOf(t.getBasicMessagePackage().getSourMachineIndex()));
			machine.getMachine().setIndex(t.getBasicMessagePackage().getSourMachineIndex());
			user.add(machine);
		}
		
		machine.getRoutePathPackage().refreshRecommendPath(rpp);
		return true;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static boolean completeBroadcast(com.FileManagerX.Interfaces.ITransport t) {
		t.getBasicMessagePackage().getBroadcast().setDestMachine(
				t.getBasicMessagePackage().getDestMachineIndex()
			);
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static boolean completeTimeInReceiver(com.FileManagerX.Interfaces.ITransport t) {
		if(t instanceof com.FileManagerX.Commands.BaseCommand) {
			if(com.FileManagerX.Globals.Configurations.This_MachineIndex == 
					t.getBasicMessagePackage().getDestMachineIndex()) {
				t.getBasicMessagePackage().getRoutePathPackage().setEndTime1();
				return true;
			}
		}
		if(t instanceof com.FileManagerX.Replies.BaseReply) {
			if(com.FileManagerX.Globals.Configurations.This_MachineIndex == 
					t.getBasicMessagePackage().getDestMachineIndex()) {
				t.getBasicMessagePackage().getRoutePathPackage().setEndTime2();
				return true;
			}
		}
		return false;
	}
	
	public final static boolean completeTimeInSender(com.FileManagerX.Interfaces.ITransport t) {
		if(t instanceof com.FileManagerX.Commands.BaseCommand) {
			if(com.FileManagerX.Globals.Configurations.This_MachineIndex == 
					t.getBasicMessagePackage().getSourMachineIndex()) {
				t.getBasicMessagePackage().getRoutePathPackage().setStartTime1();
				return true;
			}
		}
		if(t instanceof com.FileManagerX.Replies.BaseReply) {
			if(com.FileManagerX.Globals.Configurations.This_MachineIndex == 
					t.getBasicMessagePackage().getSourMachineIndex()) {
				t.getBasicMessagePackage().getRoutePathPackage().setStartTime2();
				return true;
			}
		}
		return false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static boolean executeNoReceiver(com.FileManagerX.Interfaces.ITransport t) {
		if(com.FileManagerX.Globals.Configurations.This_MachineIndex != 
				t.getBasicMessagePackage().getDestMachineIndex()) {
			return false;
		}
		
		if(t instanceof com.FileManagerX.Replies.BaseReply) { return false; }
		if(t.getBasicMessagePackage().getRoutePathPackage().getVisitedPath().size() != 0) {
			return false;
		}
		
		com.FileManagerX.Interfaces.IReply rep = ((com.FileManagerX.Commands.BaseCommand)t).getReply();
		rep.setFailedReason(FAILED_NO_RECEIVER);
		rep.setOK(false);
		
		com.FileManagerX.Globals.Datas.Receiver.add(rep);
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static boolean broadcast(com.FileManagerX.Interfaces.ITransport t) {
		// Time is Out
		if(t.isTimeOut()) { return true; }
		
		// Distribute
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IClientConnection> it =
				com.FileManagerX.Globals.Datas.Client.getIterator();
		com.FileManagerX.BasicEnums.MachineType target = 
				t.getBasicMessagePackage().getBroadcast().getTarget();
		com.FileManagerX.Interfaces.IRoutePathPackage rpp = 
				t.getBasicMessagePackage().getRoutePathPackage();
		
		while(it.hasNext()) {
			com.FileManagerX.Interfaces.IClientConnection con = it.getNext();
			long m = con.getServerMachineInfo().getIndex();
			com.FileManagerX.BasicEnums.MachineType tg = con.getServerMachineInfo().getType();
			
			if((tg.equals(com.FileManagerX.BasicEnums.MachineType.ANY) || tg.equals(target)) && !rpp.visited(m)) {
				com.FileManagerX.Interfaces.ITransport clone = (com.FileManagerX.Interfaces.ITransport)
						com.FileManagerX.Tools.Reflect.clone(t);
				clone.getBasicMessagePackage().getRoutePathPackage().addAsNext();
				clone.setDestConnection(con);
				con.send(clone);
			}
		}
		
		return true;
	}
	
	public final static boolean p2p(com.FileManagerX.Interfaces.ITransport t) {
		// Time is out
		if(t.isTimeOut()) { return true; }
		
		// RPP
		com.FileManagerX.Interfaces.IRoutePathPackage rpp = t.getBasicMessagePackage().getRoutePathPackage();
		rpp.addAsNext();
		
		// Arrive Destination
		if(com.FileManagerX.Globals.Configurations.This_MachineIndex == 
				t.getBasicMessagePackage().getDestMachineIndex()) {
			executeAtLocal(t);
			return true;
		}
		
		// Direct by preset
		if(t.getDestConnection() != null) {
			return t.getDestConnection().send(t);
		}
		
		// Direct by local
		if(true) {
			com.FileManagerX.Interfaces.IClientConnection con = com.FileManagerX.Globals.Datas.Client.searchByKey(
					t.getBasicMessagePackage().getDestMachineIndex()
				);
			if(con != null && con.isRunning()) { return con.send(t); }
		}
		
		// Direct by sour mount path
		if(com.FileManagerX.BasicEnums.RppExecutePart.S.equals(rpp.getExecutePart())) {
			com.FileManagerX.Interfaces.IClientConnection con = recommendS(rpp);
			if(con != null && con.isRunning()) { return con.send(t); }
			
			if(t.getBasicMessagePackage().getProcess() > 0) {
				con = recommendL(t.getBasicMessagePackage().getProcess());
				if(con == null) { return false; }
				return con.send(t);
			}
			
			con = com.FileManagerX.Globals.Datas.ServerConnection;
			if(con.isRunning() && !rpp.visited(con.getServerMachineInfo().getIndex())) { 
				return con.send(t);
			}
			
			com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IClientConnection> it =
					com.FileManagerX.Globals.Datas.Client.getIterator();
			while(it.hasNext()) {
				con = it.getNext();
				if(con.isRunning() && !rpp.visited(con.getServerMachineInfo().getIndex())) {
					return con.send(t);
				}
			}
			return false;
		}
		
		// Direct by recommend
		if(com.FileManagerX.BasicEnums.RppExecutePart.R.equals(rpp.getExecutePart())) {
			com.FileManagerX.Interfaces.IClientConnection con = recommendR(rpp);
			if(con != null && con.isRunning()) { return con.send(t); }
			
			if(t.getBasicMessagePackage().getProcess() > 0) {
				con = recommendL(t.getBasicMessagePackage().getProcess());
				if(con == null) { return false; }
				return con.send(t);
			}
			
			con = com.FileManagerX.Globals.Datas.ServerConnection;
			if(con.isRunning() && !rpp.visited(con.getServerMachineInfo().getIndex())) { 
				return con.send(t);
			}
			
			com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IClientConnection> it =
					com.FileManagerX.Globals.Datas.OtherServers.getIterator();
			while(it.hasNext()) {
				con = it.getNext();
				if(con.isRunning() && !rpp.visited(con.getServerMachineInfo().getIndex())) {
					return con.send(t);
				}
			}
			return false;
		}
		
		// Direct by dest mount path
		if(com.FileManagerX.BasicEnums.RppExecutePart.D.equals(rpp.getExecutePart())) {
			com.FileManagerX.Interfaces.IClientConnection con = recommendD(rpp);
			if(con != null && con.isRunning()) { return con.send(t); }
			
			if(t.getBasicMessagePackage().getProcess() > 0) {
				con = recommendL(t.getBasicMessagePackage().getProcess());
				if(con == null) { return false; }
				return con.send(t);
			}
			
			com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IClientConnection> it =
					com.FileManagerX.Globals.Datas.Client.getIterator();
			while(it.hasNext()) {
				con = it.getNext();
				if(con.isRunning() && !rpp.visited(con.getServerMachineInfo().getIndex())) {
					return con.send(t);
				}
			}
			return false;
		}
		
		// Send Back
		return false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static boolean executeBack(com.FileManagerX.Interfaces.ITransport t) {
		com.FileManagerX.Interfaces.IRoutePathPackage rpp = t.getBasicMessagePackage().getRoutePathPackage();
		rpp.delToBack();
		
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IClientConnection> it =
				com.FileManagerX.Globals.Datas.Client.getIterator();
		long prev = rpp.getActualMachineByDepth();
		while(it.hasNext()) {
			com.FileManagerX.Interfaces.IClientConnection con = it.getNext();
			if(con.getServerMachineInfo().getIndex() == prev) {
				con.send(t);
				return true;
			}
		}
		
		return false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static boolean executeAtLocal(com.FileManagerX.Interfaces.ITransport t) {
		com.FileManagerX.Executor.Executor ex = com.FileManagerX.Globals.Datas.Executors.nextIdleProcess();
		ex.setReceive(t);
		ex.restartProcess();
		return true;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static boolean record(com.FileManagerX.Interfaces.ITransport t) {
		boolean record = com.FileManagerX.Globals.Configurations.Record ||
				t.getBasicMessagePackage().isRecord();
		if(!record) { return true; }
		
		com.FileManagerX.BasicEnums.RecordType type = com.FileManagerX.BasicEnums.RecordType.UNDEFINE;
		if(t instanceof com.FileManagerX.Commands.BaseCommand) {
			type = com.FileManagerX.BasicEnums.RecordType.CMD;
		}
		if(t instanceof com.FileManagerX.Replies.BaseReply) {
			type = com.FileManagerX.BasicEnums.RecordType.REP;
		}
		
		com.FileManagerX.BasicModels.Record r = new com.FileManagerX.BasicModels.Record();
		r.setType(type);
		r.setSour(String.valueOf(t.getBasicMessagePackage().getSourMachineIndex()));
		r.setDest(String.valueOf(t.getBasicMessagePackage().getDestMachineIndex()));
		r.setDeliver(String.valueOf(com.FileManagerX.Globals.Configurations.This_MachineIndex));
		r.setContent(t.output());
		com.FileManagerX.Globals.Datas.Records.add(r);
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static com.FileManagerX.Interfaces.IClientConnection recommendS
			(com.FileManagerX.Interfaces.IRoutePathPackage rpp) {
		java.util.List<Long> recommend = rpp.getSourMountPath();
		int depth = rpp.getRecommendDepth();
		
		for(int i=recommend.size()-1; i>depth; i--) {
			com.FileManagerX.Interfaces.IClientConnection con = com.FileManagerX.Globals.Datas.Client.searchByKey(
					recommend.get(i)
				);
			if(con != null && !rpp.visited(con.getServerMachineInfo().getIndex())) {
				rpp.setRecommendDepth(i);
				return con;
			}
		}
		return null;
	}
	public final static com.FileManagerX.Interfaces.IClientConnection recommendR
			(com.FileManagerX.Interfaces.IRoutePathPackage rpp) {
		java.util.List<Long> recommend = rpp.getRecommendPath();
		int depth = rpp.getRecommendDepth();
		
		for(int i=recommend.size()-1; i>depth; i--) {
			com.FileManagerX.Interfaces.IClientConnection con = com.FileManagerX.Globals.Datas.Client.searchByKey(
					recommend.get(i)
				);
			if(con != null && !rpp.visited(con.getServerMachineInfo().getIndex())) {
				rpp.setRecommendDepth(i);
				return con;
			}
		}
		return null;
	}
	public final static com.FileManagerX.Interfaces.IClientConnection recommendD
			(com.FileManagerX.Interfaces.IRoutePathPackage rpp) {
		java.util.List<Long> recommend = rpp.getDestMountPath();
		int depth = rpp.getRecommendDepth();
		
		for(int i=recommend.size()-1; i>depth; i--) {
			com.FileManagerX.Interfaces.IClientConnection con = com.FileManagerX.Globals.Datas.Client.searchByKey(
					recommend.get(i)
				);
			if(con != null && !rpp.visited(con.getServerMachineInfo().getIndex())) {
				rpp.setRecommendDepth(i);
				return con;
			}
		}
		return null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static com.FileManagerX.Interfaces.IClientConnection recommendL(long index) {
		com.FileManagerX.Interfaces.IProcess p = com.FileManagerX.Globals.Datas.Processes.searchByKey(index);
		if(p == null) { return null; }
		if(!(p instanceof com.FileManagerX.Interfaces.IConnection)) { return null; }
		com.FileManagerX.Interfaces.IConnection con = (com.FileManagerX.Interfaces.IConnection)p;
		return con.getClientConnection();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
