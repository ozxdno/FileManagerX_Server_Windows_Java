package com.FileManagerX.Factories;

import com.FileManagerX.BasicModels.*;
import com.FileManagerX.BasicEnums.*;
import com.FileManagerX.Globals.*;

public class DefaultFactory {

	public final static MachineInfo createDefaultMachineInfo() {
		MachineInfo def = new MachineInfo();
		def.setIndex(-1);
		def.setIp();
		def.setMac();
		def.setName();
		def.setPort();
		
		def.setType(com.FileManagerX.Globals.Configurations.MachineType);
		def.setState(com.FileManagerX.BasicEnums.MachineState.RUNNING);
		
		return def;
	}
	
	/**
	 * 
	 * 如果创建了 Depot，同时，它会创建一个匹配的 DataBase，你可以通过 getDBInfo() 来获取 DataBase 的信息。
	 * 
	 * 同时利用 createDefaultDepotInfo 和 createDefaultDataBaseInfo 创建的 Depot 与 DataBase 是不匹配的。 
	 * 
	 * @return 一个默认的 Depot。
	 */
	public final static DepotInfo createDefaultDepotInfo() {
		
		DepotInfo def = new DepotInfo();
		def.setIndex(-1);
		def.setMachineInfo(Datas.ThisMachine);
		def.setName(def.getMachineInfo().getName());
		def.setState(DepotState.Running);
		def.setUrl("");
		
		DataBaseInfo db = new DataBaseInfo();
		db.setIndex(-1);
		db.setName(def.getMachineInfo().getName());
		db.setMachineInfo(Datas.ThisMachine);
		db.setType(DataBaseType.TXT);
		db.setUrl("");
		
		def.setDBInfo(db);
		db.setDepotInfo(def);
		def.setDBIndex();
		db.setDepotIndex();
		
		return def;
	}
	
	
	/**
	 * 如果创建了 DataBase，同时，它会创建一个匹配的 Depot，你可以通过 getDepotInfo() 来获取 Depot 的信息。
	 * 
	 * 同时利用 createDefaultDepotInfo 和 createDefaultDataBaseInfo 创建的 Depot 与 DataBase 是不匹配的。 
	 * 
	 * @return 一个默认的 DataBase。
	 */
	public final static DataBaseInfo createDefaultDataBaseInfo() {
		
		DepotInfo depot = createDefaultDepotInfo();
		return depot.getDBInfo();
	}
	
	public final static User createDefaultUser() {
		
		User def = new User();
		def.setIndex(Configurations.Next_UserIndex + 1);
		def.setLoginName("FileManagerX");
		def.setNickName("ozxdno");
		def.setPassword("**********");
		def.setEmail("ozxdno@126.com");
		def.setPhone("");
		def.setState(UserState.Offline);
		def.setPriority(UserPriority.Ozxdno);
		def.setLevel(UserLevel.Level0);
		def.setExperience(0);
		def.setPhotoUrl("");
		def.setCoins(0);
		def.setMoney(0);
		
		return def;
	}
	
}
