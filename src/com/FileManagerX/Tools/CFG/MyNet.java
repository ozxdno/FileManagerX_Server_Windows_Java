package com.FileManagerX.Tools.CFG;

public class MyNet {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static boolean load(com.FileManagerX.FileModels.CFG cfg) {
		
		
		
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public final static boolean save(com.FileManagerX.FileModels.CFG cfg) {
		String line = "";
		
		cfg.getContent().add(line);
		line = "/***************************************************** MyNet *********************************************************/";
		cfg.getContent().add(line);
		line = "";
		cfg.getContent().add(line);
		
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.MyNet.Group> it1 = 
				com.FileManagerX.Globals.Datas.MyNet.getIterator();
		while(it1.hasNext()) {
			com.FileManagerX.MyNet.Group g = it1.getNext();
			line = g.output();
			cfg.getContent().add(line);
			
			com.FileManagerX.Interfaces.IIterator<com.FileManagerX.MyNet.User> it2 = g.getIterator();
			while(it2.hasNext()) {
				com.FileManagerX.MyNet.User u = it2.getNext();
				line = u.output();
				cfg.getContent().add(line);
				
				com.FileManagerX.Interfaces.IIterator<com.FileManagerX.MyNet.Machine> it3 = u.getIterator();
				while(it3.hasNext()) {
					com.FileManagerX.MyNet.Machine m = it3.getNext();
					line = m.output();
					cfg.getContent().add(line);
					
					com.FileManagerX.Interfaces.IIterator<com.FileManagerX.MyNet.Depot> it4 = m.getIterator();
					while(it4.hasNext()) {
						com.FileManagerX.MyNet.Depot d = it4.getNext();
						line = d.output();
						cfg.getContent().add(line);
					}
				}
			}
		}
		
		return true;
	}
	public final static boolean saveNew(com.FileManagerX.FileModels.CFG cfg) {
		return save(cfg);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
