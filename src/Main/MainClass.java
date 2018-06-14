package Main;

import java.util.*;

public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		BasicModels.MachineInfo sm = new BasicModels.MachineInfo();
		sm.setName("ozxdno");
		sm.setIp("172.24.136.41");
		sm.setPort(40000);
		
		BasicModels.MachineInfo cm = new BasicModels.MachineInfo();
		cm.setName("ozxdno");
		cm.setIp("172.24.136.41");
		cm.setPort(50000);
		
		Communicator.ClientTCP client = new Communicator.ClientTCP();
		client.setServerMachineInfo(sm);
		client.setClientMachineInfo(cm);
		client.connect();
		
		System.out.println("Exit");
	}

}
