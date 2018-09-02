package com.FileManagerX.Tools.CFG;

public class ServerUser {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static boolean load(com.FileManagerX.FileModels.CFG cfg) {
		return com.FileManagerX.Globals.Configurations.IsServer ?
				AncestorSU() : 
				OtherSU();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static boolean AncestorSU() {
		com.FileManagerX.Globals.Datas.ServerUser.copyReference(
				com.FileManagerX.Factories.AncestorFactory.createAncestorUser()
			);
		return true;
	}
	public final static boolean OtherSU() {
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
