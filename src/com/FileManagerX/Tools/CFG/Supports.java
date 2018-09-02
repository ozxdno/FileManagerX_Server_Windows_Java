package com.FileManagerX.Tools.CFG;

public class Supports {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static boolean load(com.FileManagerX.FileModels.CFG cfg) {
		while(true) {
			com.FileManagerX.BasicModels.Config c = cfg.getConfigs().fetchByField("Support");
			if(c == null) {
				break;
			}
			try {
				com.FileManagerX.BasicModels.Support s = new com.FileManagerX.BasicModels.Support();
				s.setType(com.FileManagerX.BasicEnums.FileType.valueOf(c.fetchFirstString()));
				if(!c.getIsOK()) { continue; }
				s.setExtension(c.fetchFirstString());
				if(!c.getIsOK()) { continue; }
				s.setShowExtension(new java.lang.String(s.getExtension()));
				if(!c.getIsOK()) { continue; }
				s.setHideExtension(c.fetchFirstString());
				if(!c.getIsOK()) { continue; }
				s.setIsSupport(true);
				com.FileManagerX.Globals.Datas.Supports.add(s);
				
				com.FileManagerX.BasicModels.Support s2 = new com.FileManagerX.BasicModels.Support();
				s2.setExtension(new java.lang.String(s.getHideExtension()));
				s2.setType(s.getType());
				s2.setShowExtension(new java.lang.String(s.getShowExtension()));
				s2.setHideExtension(new java.lang.String(s.getHideExtension()));
				s2.setIsSupport(false);
				if(s2.getExtension() != null && s2.getExtension().length() > 0) {
					com.FileManagerX.Globals.Datas.Supports.add(s2);
				}
			} catch(Exception e) {
				com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			}
		}
		
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public final static boolean save(com.FileManagerX.FileModels.CFG cfg) {
		String line = "";
		
		cfg.getContent().add(line);
		line = "/*************************************************** Supports ********************************************************/";
		cfg.getContent().add(line);
		line = "";
		cfg.getContent().add(line);
		
		line = "Example: Support = Picture|.jpg|.pv1";
		cfg.getContent().add(line);
		line = "";
		cfg.getContent().add(line);
		
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.Support> it =
				com.FileManagerX.Globals.Datas.Supports.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.Support s = it.getNext();
			if(s.isHideExtension()) { continue; }
			line = "Support = " + s.getType().toString() + "|" +
					s.getShowExtension() + "|" +
					s.getHideExtension();
			cfg.getContent().add(line);
		}
		
		return true;
	}
	public final static boolean saveNew(com.FileManagerX.FileModels.CFG cfg) {
		return save(cfg);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
