package Main;

import java.util.*;

import DataBaseManager.*;

public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
		LocalSQLManager sql = new LocalSQLManager();
		BasicModels.DataBaseInfo dbi = new BasicModels.DataBaseInfo();
		dbi.getMachineInfo().setIp("127.0.0.1");
		dbi.getMachineInfo().setPort(3306);
		dbi.setLoginName("root");
		dbi.setPassword("ani1357658uiu");
		dbi.setDBName("filemanager");
		dbi.setType(BasicEnums.DataBaseType.SQL);
		sql.setDBInfo(dbi);
		
		QueryConditions qds = new QueryConditions();
		QueryCondition qd = new QueryCondition();
		qd.setItemName("index");
		qd.setUBound("9999999");
		qd.setDBound("0");
		qds.add(qd);
		
		//sql.connect();
		//sql.QueryFolder(qds);
		
		Processes.InitializeProcess pInit = new Processes.InitializeProcess();
		pInit.setConfigurations(dbi);
		pInit.setThisMachineInfo(new BasicModels.MachineInfo(0));
		pInit.startProcess();
		
		BasicModels.MachineInfo sm1 = new BasicModels.MachineInfo();
		sm1.setName("ozxdno");
		sm1.setIp("172.28.13.37");
		sm1.setPort(40000);
		
		BasicModels.MachineInfo sm2 = new BasicModels.MachineInfo();
		sm2.setName("ozxdno");
		sm2.setIp("172.24.136.41");
		sm2.setPort(40000);
		
		BasicModels.MachineInfo cm = new BasicModels.MachineInfo();
		cm.setName("ozxdno");
		cm.setIp("172.24.136.41");
		cm.setPort(50000);
		
		Communicator.ClientTCP client = new Communicator.ClientTCP();
		client.setServerMachineInfo(sm1);
		client.setClientMachineInfo(cm);
		//client.connect();
		
		Communicator.ServerTCP server = new Communicator.ServerTCP();
		server.setServerMachineInfo(sm2);
		//server.connect();
		
		System.out.println("Exit");
	}

}
