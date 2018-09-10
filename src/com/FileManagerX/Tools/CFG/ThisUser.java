package com.FileManagerX.Tools.CFG;

public class ThisUser {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static boolean load(com.FileManagerX.FileModels.CFG cfg) {
		if(com.FileManagerX.Globals.Configurations.IsAncestor) { return AncestorTU(); }
		if(com.FileManagerX.Globals.Configurations.IsServer) { return ServerTU(); }
		if(com.FileManagerX.Globals.Configurations.IsTemporary) { return TemporaryTU(); }
		return DefaultTU();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static boolean AncestorTU() {
		com.FileManagerX.Globals.Datas.ThisUser.copyReference(
				com.FileManagerX.Factories.AncestorFactory.createAncestorUser()
			);
		return true;
	}
	
	public final static boolean ServerTU() {
		return true;
	}
	
	public final static boolean DefaultTU() {
		return true;
	}
	
	public final static boolean TemporaryTU() {
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
