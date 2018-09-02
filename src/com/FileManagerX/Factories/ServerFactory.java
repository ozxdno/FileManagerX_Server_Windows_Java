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
		def.setIndex(com.FileManagerX.Globals.Configurations.This_MachineIndex);
		def.setUserIndex(1);
		def.setMac();
		def.setIp();
		def.setName();
		def.setPort(40000);
		
		def.setType(com.FileManagerX.BasicEnums.MachineType.SERVER);
		def.setState(com.FileManagerX.BasicEnums.MachineState.RUNNING);
		
		return def;
	}
	
	/**
	 * ͨ���˺������ط���������Դվ������Ϣ��
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
	 * ͨ���˺������ط����������ݿ�������Ϣ�������������ļ��������ã�
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
