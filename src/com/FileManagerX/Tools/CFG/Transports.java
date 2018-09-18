package com.FileManagerX.Tools.CFG;

public class Transports {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static boolean load(com.FileManagerX.FileModels.CFG cfg) {
		loadCommands("com.FileManagerX.Commands");
		loadReplies("com.FileManagerX.Replies");
		
		while(true) {
			com.FileManagerX.BasicModels.Config c = cfg.getConfigs().fetchByKey("CMD");
			if(c == null) { break; }
			loadCommands(c.getValue());
		}
		while(true) {
			com.FileManagerX.BasicModels.Config c = cfg.getConfigs().fetchByKey("REP");
			if(c == null) { break; }
			loadReplies(c.getValue());
		}
		
		return true;
	}
	private final static boolean loadCommands(String packageName) {
		java.util.HashMap<String, Class<?>> cmds = com.FileManagerX.Tools.Package.iteratorPackage(
				packageName,
				com.FileManagerX.Interfaces.ICommand.class
			);
		com.FileManagerX.Globals.Datas.Commands.putAll(cmds);
		return true;
	}
	private final static boolean loadReplies(String packageName) {
		java.util.HashMap<String, Class<?>> cmds = com.FileManagerX.Tools.Package.iteratorPackage(
				packageName,
				com.FileManagerX.Interfaces.IReply.class
			);
		com.FileManagerX.Globals.Datas.Replies.putAll(cmds);
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static boolean save(com.FileManagerX.FileModels.CFG cfg) {
		String line = "";
		cfg.getContent().add(line);
		line = "/*************************************************** CMD & REP *******************************************************/";
		cfg.getContent().add(line);
		line = "";
		cfg.getContent().add(line);
		
		line = "Example: CMD = CommandsPackageName";
		cfg.getContent().add(line);
		line = "Example: REP = RepliesPackageName";
		cfg.getContent().add(line);
		line = "";
		cfg.getContent().add(line);
		
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static boolean saveNew(com.FileManagerX.FileModels.CFG cfg) {
		return save(cfg);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}

