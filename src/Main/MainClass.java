package Main;

import java.util.*;

public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		BasicModels.BaseFile file = new BasicModels.BaseFile("172.28.13.37:40000\\C:\\Users\\ozxdno\\Desktop\\dc\\rename.txt");
		DepotManager.LocalDepotManager dm = new DepotManager.LocalDepotManager();
		dm.setFile(file);
		
		dm.renameFileWithoutExtension("rename.txt");
		
		BasicModels.MachineInfo sm = new BasicModels.MachineInfo();
		sm.setName("ozxdno");
		sm.setIp("172.28.13.37");
		sm.setPort(40000);
		
		BasicModels.MachineInfo cm = new BasicModels.MachineInfo();
		cm.setName("ozxdno");
		cm.setIp("172.24.136.41");
		cm.setPort(50000);
		
		Communicator.ClientTCP client = new Communicator.ClientTCP();
		client.setServerMachineInfo(sm);
		client.setClientMachineInfo(cm);
		//client.connect();
		
		Communicator.ServerTCP server = new Communicator.ServerTCP();
		server.setServerMachineInfo(sm);
		server.connect();
		
		System.out.println("Exit");
	}

}
