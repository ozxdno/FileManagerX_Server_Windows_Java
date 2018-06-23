package Main;

import java.util.*;

import DataBaseManager.*;

public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Tools.CFGFile.createCFG();
		Tools.CFGFile.loadCFG();
		//Tools.CFGFile.resetCFG();
		
		for(Interfaces.IDBManager dbm : Globals.Datas.DBManagers.getContent()) {
			Interfaces.IDepotChecker dc = new DepotChecker.DepotChecker();
			dc.initialize(dbm);
			dc.check();
			
			if(dbm.getDBInfo().getType().equals(BasicEnums.DataBaseType.TXT)) {
				dbm.disconnect();
				dbm.connect();
			}
		}
		
		Tools.CFGFile.saveCFG();
		
		Globals.Datas.Server.initialize(Globals.Datas.ThisMachine);
		Globals.Datas.Server.connect();
		
		System.out.println("End");
	}

}
