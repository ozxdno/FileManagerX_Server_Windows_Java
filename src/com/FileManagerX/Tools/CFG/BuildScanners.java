package com.FileManagerX.Tools.CFG;

public class BuildScanners {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public final static boolean build() {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.Interfaces.IScanner> it =
				com.FileManagerX.Globals.Datas.Scanners.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.Interfaces.IScanner s = it.getNext();
			s.exitProcess();
			while(s.isRunning()) { com.FileManagerX.Tools.Time.sleepUntil(10); }
		}
		
		if(com.FileManagerX.Globals.Configurations.IsAncestor) {
			boolean ok = true;
			ok &= scanner_Ancestor_TCP_IPV4();
			ok &= scanner_Ancestor_UDP_IPV4();
			return ok;
		}
		else {
			boolean ok = true;
			ok &= scanner_Others_TCP_IPV4();
			ok &= scanner_Others_UDP_IPV4();
			return ok;
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private final static boolean scanner_Ancestor_TCP_IPV4() {
		com.FileManagerX.Interfaces.IScanner scanner = 
				com.FileManagerX.Factories.CommunicatorFactory.createScanner();
		com.FileManagerX.Globals.Datas.ThisMachine.setIp();
		com.FileManagerX.Globals.Datas.ThisMachine.setPort();
		scanner.setServerMachineInfo(com.FileManagerX.Globals.Datas.ThisMachine);
		scanner.setSocket(com.FileManagerX.BasicEnums.SocketType.IPV4_TCP);
		scanner.startProcess();
		return true;
	}
	private final static boolean scanner_Others_TCP_IPV4() {
		com.FileManagerX.BasicModels.MachineInfo m = com.FileManagerX.Globals.Datas.ThisMachine;
		int maxPort = 65535;
		int minPort = 1025;
		m.setIp();
		
		com.FileManagerX.Interfaces.ISocketS socket = com.FileManagerX.BasicEnums.SocketType.IPV4_TCP.getSocketS();
		socket.setServerMachineInfo(m);
		for(int i=minPort; i<=maxPort; i++) {
			m.setPort(i);
			boolean ok = socket.setSocket();
			if(ok) {
				com.FileManagerX.Interfaces.IScanner scanner = 
						com.FileManagerX.Factories.CommunicatorFactory.createScanner();
				scanner.setServerMachineInfo(m);
				scanner.setSocket(socket);
				scanner.startProcess();
				return true;
			}
		}
		
		com.FileManagerX.BasicEnums.ErrorType.OTHERS.register("Not Found Available Port");
		return false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private final static boolean scanner_Ancestor_UDP_IPV4() {
		return true;
	}
	private final static boolean scanner_Others_UDP_IPV4() {
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
