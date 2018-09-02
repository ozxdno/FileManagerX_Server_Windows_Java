package com.FileManagerX.DataBase;

public enum Unit {

	MANAGER,
	SHELL,
	ANY,
	
	Machine,
	Depot,
	DataBase,
	User,
	Invitation,
	Chat,
	Group,
	
	File,
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
		if(Depot.equals(this)) {
			if(type.equals(com.FileManagerX.BasicEnums.DataBaseType.MySQL)) {
				return new MySQLManager_Depot();
			}
			if(type.equals(com.FileManagerX.BasicEnums.DataBaseType.TXT)) {
				return new TXTManager_Depot();
			}
		}
		if(DataBase.equals(this)) {
			if(type.equals(com.FileManagerX.BasicEnums.DataBaseType.MySQL)) {
				return new MySQLManager_DataBase();
			}
			if(type.equals(com.FileManagerX.BasicEnums.DataBaseType.TXT)) {
				return new TXTManager_DataBase();
			}
		}
		if(User.equals(this)) {
			if(type.equals(com.FileManagerX.BasicEnums.DataBaseType.MySQL)) {
				return new MySQLManager_User();
			}
			if(type.equals(com.FileManagerX.BasicEnums.DataBaseType.TXT)) {
				return new TXTManager_User();
			}
		}
		if(Invitation.equals(this)) {
			if(type.equals(com.FileManagerX.BasicEnums.DataBaseType.MySQL)) {
				return new MySQLManager_Invitation();
			}
			if(type.equals(com.FileManagerX.BasicEnums.DataBaseType.TXT)) {
				return new TXTManager_Invitation();
			}
		}
		if(Chat.equals(this)) {
			if(type.equals(com.FileManagerX.BasicEnums.DataBaseType.MySQL)) {
				return new MySQLManager_Chat();
			}
			if(type.equals(com.FileManagerX.BasicEnums.DataBaseType.TXT)) {
				return new TXTManager_Chat();
			}
		}
		if(Group.equals(this)) {
			if(type.equals(com.FileManagerX.BasicEnums.DataBaseType.MySQL)) {
				return new MySQLManager_Group();
			}
			if(type.equals(com.FileManagerX.BasicEnums.DataBaseType.TXT)) {
				return new TXTManager_Group();
			}
		}
		
		if(File.equals(this)) {
			if(type.equals(com.FileManagerX.BasicEnums.DataBaseType.MySQL)) {
				return new MySQLManager_File();
			}
			if(type.equals(com.FileManagerX.BasicEnums.DataBaseType.TXT)) {
				return new TXTManager_File();
			}
		}
		if(Folder.equals(this)) {
			if(type.equals(com.FileManagerX.BasicEnums.DataBaseType.MySQL)) {
				return new MySQLManager_Folder();
			}
			if(type.equals(com.FileManagerX.BasicEnums.DataBaseType.TXT)) {
				return new TXTManager_Folder();
			}
		}
		if(Picture.equals(this)) {
			if(type.equals(com.FileManagerX.BasicEnums.DataBaseType.MySQL)) {
				return new MySQLManager_Picture();
			}
			if(type.equals(com.FileManagerX.BasicEnums.DataBaseType.TXT)) {
				return new TXTManager_Picture();
			}
		}
		if(Gif.equals(this)) {
			if(type.equals(com.FileManagerX.BasicEnums.DataBaseType.MySQL)) {
				return new MySQLManager_Gif();
			}
			if(type.equals(com.FileManagerX.BasicEnums.DataBaseType.TXT)) {
				return new TXTManager_Gif();
			}
		}
		if(Music.equals(this)) {
			if(type.equals(com.FileManagerX.BasicEnums.DataBaseType.MySQL)) {
				return new MySQLManager_Music();
			}
			if(type.equals(com.FileManagerX.BasicEnums.DataBaseType.TXT)) {
				return new TXTManager_Music();
			}
		}
		if(Video.equals(this)) {
			if(type.equals(com.FileManagerX.BasicEnums.DataBaseType.MySQL)) {
				return new MySQLManager_Video();
			}
			if(type.equals(com.FileManagerX.BasicEnums.DataBaseType.TXT)) {
				return new TXTManager_Video();
			}
		}
		if(CFG.equals(this)) {
			if(type.equals(com.FileManagerX.BasicEnums.DataBaseType.MySQL)) {
				return new MySQLManager_CFG();
			}
			if(type.equals(com.FileManagerX.BasicEnums.DataBaseType.TXT)) {
				return new TXTManager_CFG();
			}
		}
		
		return null;
	}
}
