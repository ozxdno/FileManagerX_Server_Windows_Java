package Factories;

public class DefaultFactory {

	public final static BasicModels.MachineInfo createDefaultMachineInfo() {
		BasicModels.MachineInfo def = new BasicModels.MachineInfo();
		def.setIndex(Globals.Configurations.Next_MachineIndex + 1);
		def.setIp();
		def.setName();
		def.setPort();
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
	public final static BasicModels.DepotInfo createDefaultDepotInfo() {
		
		BasicModels.DepotInfo def = new BasicModels.DepotInfo();
		def.setIndex(Globals.Configurations.Next_DepotIndex + 1);
		def.setMachineInfo(Globals.Datas.ThisMachine);
		def.setName(def.getMachineInfo().getName());
		def.setState(BasicEnums.DepotState.Running);
		def.setUrl("");
		
		BasicModels.DataBaseInfo db = new BasicModels.DataBaseInfo();
		db.setIndex(Globals.Configurations.Next_DataBaseIndex + 1);
		db.setName(def.getMachineInfo().getName());
		db.setMachineInfo(Globals.Datas.ThisMachine);
		db.setType(BasicEnums.DataBaseType.TXT);
		db.setUrl(Tools.Pathes.getFolder_DBS());
		
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
	public final static BasicModels.DataBaseInfo createDefaultDataBaseInfo() {
		
		BasicModels.DepotInfo depot = createDefaultDepotInfo();
		return depot.getDBInfo();
	}
	
	public final static BasicModels.User createDefaultUser() {
		
		BasicModels.User def = new BasicModels.User();
		def.setIndex(Globals.Configurations.Next_UserIndex + 1);
		def.setLoginName("FileManagerX");
		def.setNickName("ozxdno");
		def.setPassword("**********");
		def.setEmail("ozxdno@126.com");
		def.setPhone("");
		def.setState(BasicEnums.UserState.Offline);
		def.setPriority(BasicEnums.UserPriority.Ozxdno);
		def.setLevel(BasicEnums.UserLevel.Level0);
		def.setExperience(0);
		def.setPhotoUrl("");
		def.setCoins(0);
		def.setMoney(0);
		
		return def;
	}
	
}
