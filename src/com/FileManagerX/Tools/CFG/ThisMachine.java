package com.FileManagerX.Tools.CFG;

public class ThisMachine {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static boolean load(com.FileManagerX.FileModels.CFG cfg) {
		if(com.FileManagerX.Globals.Configurations.IsAncestor) { return AncestorTM(); }
		if(com.FileManagerX.Globals.Configurations.IsServer) { return ServerTM(); }
		if(com.FileManagerX.Globals.Configurations.IsTemporary) { return TemporaryTM(); }
		return DefaultTM();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static boolean AncestorTM() {
		com.FileManagerX.Globals.Datas.ThisMachine.copyReference(
				com.FileManagerX.Factories.AncestorFactory.createAncestorMachine()
			);
		return true;
	}
	
	public final static boolean ServerTM() {
		com.FileManagerX.Globals.Datas.ThisMachine.copyReference(
				com.FileManagerX.Factories.ServerFactory.createServerMachine()
			);
		return true;
	}
	
	public final static boolean DefaultTM() {
		com.FileManagerX.Globals.Datas.ThisMachine.copyReference(
				com.FileManagerX.Factories.DefaultFactory.createDefaultMachineInfo()
			);
		return true;
	}
	
	public final static boolean TemporaryTM() {
		com.FileManagerX.Globals.Datas.ThisMachine.copyReference(
				com.FileManagerX.Factories.DefaultFactory.createDefaultMachineInfo()
			);
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
