package com.FileManagerX.DataBase;

public enum Unit {

	MANAGER,
	SHELL,
	
	Machine,
	Depot,
	DataBase,
	User,
	Invitation,
	Chat,
	Group,
	
	BaseFile,
	Folder,
	CFG,
	Picture,
	Gif,
	Music,
	Video,
	;
	
	public com.FileManagerX.Interfaces.IDBManager getManager(com.FileManagerX.BasicEnums.DataBaseType type) {
		if(type == null) {
			return null;
		}
		
		if(SHELL.equals(this)) {
			if(type.equals(com.FileManagerX.BasicEnums.DataBaseType.MySQL)) {
				return new MySQLManager_SHELL();
			}
			if(type.equals(com.FileManagerX.BasicEnums.DataBaseType.TXT)) {
				return new TXTManager_SHELL();
			}
		}
		if(Machine.equals(this)) {
			if(type.equals(com.FileManagerX.BasicEnums.DataBaseType.MySQL)) {
				return new MySQLManager_Machine();
			}
			if(type.equals(com.FileManagerX.BasicEnums.DataBaseType.TXT)) {
				return new TXTManager_Machine();
			}
		}
		
		return null;
	}
}
