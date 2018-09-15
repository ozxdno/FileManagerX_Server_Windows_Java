package com.FileManagerX.Tools;

public class TMP {

	public static final boolean clear() {
		com.FileManagerX.Interfaces.IDepotManager dm = com.FileManagerX.Factories.DepotFactory.createManager();
		dm.setUncheck(true);
		
		return dm.deleteContent(com.FileManagerX.Tools.Pathes.TMP.getAbsolute());
	}
	
	
}
