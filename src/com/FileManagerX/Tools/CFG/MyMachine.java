package com.FileManagerX.Tools.CFG;

public class MyMachine {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static boolean load(com.FileManagerX.FileModels.CFG cfg) {
		com.FileManagerX.BasicCollections.MachineInfos machines = new com.FileManagerX.BasicCollections.MachineInfos();
		com.FileManagerX.Globals.Datas.DBManager.querys(
				"=1|[&] userIndex = " + com.FileManagerX.Globals.Configurations.This_UserIndex,
				machines,
				com.FileManagerX.DataBase.Unit.Machine
			);
		
		com.FileManagerX.Globals.Datas.MyMachines.copyReference(machines);
		return true;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
