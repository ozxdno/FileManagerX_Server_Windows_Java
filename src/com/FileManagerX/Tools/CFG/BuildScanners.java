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
		
		com.FileManagerX.Interfaces.IScanner scannerTCP_IPV4 =
				com.FileManagerX.Factories.CommunicatorFactory.createScanner();
		scannerTCP_IPV4.setServerMachineInfo(com.FileManagerX.Globals.Datas.ThisMachine);
		scannerTCP_IPV4.setSocket(com.FileManagerX.BasicEnums.SocketType.IPV4_TCP);
		scannerTCP_IPV4.startProcess();
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
