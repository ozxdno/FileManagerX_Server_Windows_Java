package Main;

import java.util.*;

public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		BasicModels.Config c0 = new BasicModels.Config();
		c0.setField("Test0");
		c0.setValue("1|sdf|0.01|||sjhfhiiee|errrrr");
		
		BasicModels.Config c1 = new BasicModels.Config();
		c1.setField("Test1");
		c1.setValue("0|1|2|||3");
		
		c0.addToTop(c1);
		c0.addToBottom(c1);
		
		c1 = c0.fetchFirstConfig(999);
		c0 = c1.fetchLastConfig(999);
		c1 = c0.fetchFirstConfig(-100);
		c1 = c0.fetchLastConfig(-100);
		c1 = c0.fetchFirstConfig(3);
		c1 = c0.fetchLastConfig(3);

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
