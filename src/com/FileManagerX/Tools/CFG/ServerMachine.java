package com.FileManagerX.Tools.CFG;

public class ServerMachine {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static boolean load(com.FileManagerX.FileModels.CFG cfg) {
		return com.FileManagerX.Globals.Configurations.IsAncestor ?
				AncestorSM() :
				OtherSM();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static boolean AncestorSM() {
		com.FileManagerX.Globals.Datas.ServerMachine.copyReference(
				com.FileManagerX.Factories.AncestorFactory.createAncestorMachine()
			);
		return true;
	}
	
	public final static boolean OtherSM() {
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
