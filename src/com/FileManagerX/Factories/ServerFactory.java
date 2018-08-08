package com.FileManagerX.Factories;

import com.FileManagerX.BasicEnums.DataBaseType;
import com.FileManagerX.BasicEnums.DepotState;
import com.FileManagerX.BasicEnums.UserLevel;
import com.FileManagerX.BasicEnums.UserPriority;
import com.FileManagerX.BasicEnums.UserState;
import com.FileManagerX.BasicModels.*;
import com.FileManagerX.Globals.Datas;

public class ServerFactory {
	
	public final static com.FileManagerX.BasicModels.MachineInfo createServerMachine() {
		MachineInfo def = new MachineInfo();
		def.setIndex(1);
		def.setUserIndex(1);
		def.setIp("172.24.136.41");
		def.setName();
		def.setPort(40000);
		return def;
	}
	
	/**
	 * 通过此函数返回服务器的资源站属性信息。
	 * 
	 * @return
	 */
	public final static DepotInfo createServerDepotInfo() {
		
		DepotInfo def = new DepotInfo();
		def.setIndex(1);
		def.setMachineInfo(Datas.ThisMachine);
		def.setName("FileManagerX");
		def.setState(DepotState.Running);
		def.setUrl("");
		
		DataBaseInfo db = new DataBaseInfo();
		db.setIndex(1);
		db.setName("FileManagerX");
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
	 * 通过此函数返回服务器的数据库属性信息。（可在配置文件重新设置）
	 * 
	 * @return
	 */
	public final static DataBaseInfo createServerDataBaseInfo() {
		
		DepotInfo depot = createServerDepotInfo();
		return depot.getDBInfo();
	}
	
	public final static User createServerUser() {
		
		User def = new User();
		def.setIndex(1);
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
