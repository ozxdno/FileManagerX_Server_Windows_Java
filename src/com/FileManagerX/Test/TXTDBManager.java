package com.FileManagerX.Test;

public class TXTDBManager {

	public static void main(String[] args) {
		
		Object res = "";
		
		com.FileManagerX.BasicModels.DepotInfo depot =
				com.FileManagerX.Factories.AncestorFactory.createAncestorDepot();
		com.FileManagerX.BasicModels.DataBaseInfo database =
				depot.getDBInfo();
		
		depot.setUrl("D:\\Space_For_Media\\Pictures\\FMX_Test_Depot_H");
		com.FileManagerX.Interfaces.IDBManager dbm = database.getManager();
		dbm.connect();
		
		res = dbm.querys2("", com.FileManagerX.DataBase.Unit.Machine);
		res = dbm.querys2("", com.FileManagerX.DataBase.Unit.Depot);
		res = dbm.querys2("", com.FileManagerX.DataBase.Unit.DataBase);
		res = dbm.querys2("", com.FileManagerX.DataBase.Unit.File);
		
		System.out.println(res);
	}

}
