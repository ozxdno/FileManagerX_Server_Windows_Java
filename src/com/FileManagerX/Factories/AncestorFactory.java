package com.FileManagerX.Factories;

public class AncestorFactory {

	public final static com.FileManagerX.BasicModels.MachineInfo createAncestorMachine() {
		com.FileManagerX.BasicModels.MachineInfo def = new com.FileManagerX.BasicModels.MachineInfo();
		def.setIndex(1);
		def.setUserIndex(1);
		def.setMac();
		def.setIp();
		def.setName();
		def.setPort(40000);
		
		def.setType(com.FileManagerX.Globals.Configurations.MachineType);
		def.setState(com.FileManagerX.BasicEnums.MachineState.RUNNING);
		
		return def;
	}
	
	public final static com.FileManagerX.BasicModels.DepotInfo createAncestorDepot() {
		com.FileManagerX.BasicModels.DepotInfo def = new com.FileManagerX.BasicModels.DepotInfo();
		def.setIndex(1);
		def.setMachineInfo(com.FileManagerX.Globals.Datas.ThisMachine);
		def.setName("FileManagerX");
		def.setState(com.FileManagerX.BasicEnums.DepotState.Running);
		def.setUrl("");
		
		com.FileManagerX.BasicModels.DataBaseInfo db = new com.FileManagerX.BasicModels.DataBaseInfo();
		db.setIndex(1);
		db.setName("FileManagerX");
		db.setMachineInfo(com.FileManagerX.Globals.Datas.ThisMachine);
		db.setType(com.FileManagerX.BasicEnums.DataBaseType.TXT);
		db.setUrl("");
		
		def.setDBInfo(db);
		db.setDepotInfo(def);
		def.setDBIndex();
		db.setDepotIndex();
		
		return def;
	}
	
	public final static com.FileManagerX.BasicModels.DataBaseInfo createAncestorDataBase() {
		return createAncestorDepot().getDBInfo();
	}
	
	public final static com.FileManagerX.BasicModels.User createAncestorUser() {
		com.FileManagerX.BasicModels.User def = new com.FileManagerX.BasicModels.User();
		def.setIndex(1);
		def.setLoginName("FileManagerX");
		def.setNickName("ozxdno");
		def.setPassword("**********");
		def.setEmail("ozxdno@126.com");
		def.setPhone("");
		def.setState(com.FileManagerX.BasicEnums.UserState.Offline);
		def.setPriority(com.FileManagerX.BasicEnums.UserPriority.Ozxdno);
		def.setLevel(com.FileManagerX.BasicEnums.UserLevel.Level0);
		def.setExperience(0);
		def.setPhotoUrl("");
		def.setCoins(0);
		def.setMoney(0);
		
		return def;
	}
}
