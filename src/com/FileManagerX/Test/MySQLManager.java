package com.FileManagerX.Test;

public class MySQLManager {

	public static void main(String[] args) {
		Object res = "";
		
		com.FileManagerX.BasicModels.DepotInfo depot =
				com.FileManagerX.Factories.AncestorFactory.createAncestorDepot();
		com.FileManagerX.BasicModels.DataBaseInfo database =
				depot.getDBInfo();
		
		depot.setUrl("D:\\Space_For_Media\\Pictures\\FMX_Test_Depot_H");
		database.setType(com.FileManagerX.BasicEnums.DataBaseType.MySQL);
		database.setUrl("127.0.0.1:3306\\root\\ani1357658uiu\\Depot_B");
		
		com.FileManagerX.Interfaces.IDBManager dbm = database.getManager();
		dbm.connect();
		
		res = dbm.querys2("", com.FileManagerX.DataBase.Unit.Machine);
		res = dbm.querys2("", com.FileManagerX.DataBase.Unit.Depot);
		res = dbm.querys2("", com.FileManagerX.DataBase.Unit.DataBase);
		res = dbm.querys2("", com.FileManagerX.DataBase.Unit.File);
		
		System.out.println(res);
	}

}
