package com.FileManagerX.Factories;

import com.FileManagerX.BasicEnums.DataBaseType;
import com.FileManagerX.BasicEnums.DepotState;
import com.FileManagerX.BasicEnums.UserLevel;
import com.FileManagerX.BasicEnums.UserPriority;
import com.FileManagerX.BasicEnums.UserState;
import com.FileManagerX.BasicModels.DataBaseInfo;
import com.FileManagerX.BasicModels.DepotInfo;
import com.FileManagerX.BasicModels.MachineInfo;
import com.FileManagerX.BasicModels.User;
import com.FileManagerX.Globals.Configurations;
import com.FileManagerX.Globals.Datas;

public class NextFactory {
	
	public final static MachineInfo createNextMachineInfo() {
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
	 * ��������� Depot��ͬʱ�����ᴴ��һ��ƥ��� DataBase�������ͨ�� getDBInfo() ����ȡ DataBase ����Ϣ��
	 * 
	 * ͬʱ���� createDefaultDepotInfo �� createDefaultDataBaseInfo ������ Depot �� DataBase �ǲ�ƥ��ġ� 
	 * 
	 * @return һ��Ĭ�ϵ� Depot��
	 */
	public final static DepotInfo createNextDepotInfo() {
		
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
	 * ��������� DataBase��ͬʱ�����ᴴ��һ��ƥ��� Depot�������ͨ�� getDepotInfo() ����ȡ Depot ����Ϣ��
	 * 
	 * ͬʱ���� createDefaultDepotInfo �� createDefaultDataBaseInfo ������ Depot �� DataBase �ǲ�ƥ��ġ� 
	 * 
	 * @return һ��Ĭ�ϵ� DataBase��
	 */
	public final static DataBaseInfo createNextDataBaseInfo() {
		return createNextDepotInfo().getDBInfo();
	}
	
	public final static User createNextUser() {
		
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
