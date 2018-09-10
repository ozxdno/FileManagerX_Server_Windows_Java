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
		return true;
	}
	
	public final static boolean DefaultTM() {
		return true;
	}
	
	public final static boolean TemporaryTM() {
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
