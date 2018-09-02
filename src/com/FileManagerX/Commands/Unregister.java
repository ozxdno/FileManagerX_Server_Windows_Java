package com.FileManagerX.Commands;

public class Unregister extends BaseCommand {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.Replies.Unregister getReply() {
		if(super.getReply() == null) { this.setReply(new com.FileManagerX.Replies.Unregister()); }
		return (com.FileManagerX.Replies.Unregister)super.getReply();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Unregister() {
		initThis();
	}
	private void initThis() {
		;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean execute() {
		if(!this.isConnected()) {
			this.getReply().send();
			return false;
		}
		if(!this.isLoginUser()) {
			this.getReply().send();
			return false;
		}
		if(!this.isLoginMachine()) {
			this.getReply().send();
			return false;
		}
		if(!this.isUserIndexRight()) {
			this.getReply().send();
			return false;
		}
		if(!this.isPasswordRight()) {
			this.getReply().send();
			return false;
		}
		if(!this.isPriorityEnough(com.FileManagerX.BasicEnums.UserPriority.Member)) {
			this.getReply().send();
			return false;
		}
		
		boolean ok = this.executeInLocal();
		this.getReply().send();
		return ok;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean executeInLocal() {
		long user = this.getBasicMessagePackage().getSourUserIndex();
		com.FileManagerX.BasicCollections.MachineInfos machines = (com.FileManagerX.BasicCollections.MachineInfos)
				com.FileManagerX.Globals.Datas.DBManager.query2(
						"[&] UserIndex = " + user,
						com.FileManagerX.DataBase.Unit.Machine
					);
		com.FileManagerX.Globals.Datas.DBManager.remove(
				"[&] UserIndex = " + user,
				com.FileManagerX.DataBase.Unit.Machine
			);
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.MachineInfo> it = machines.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.MachineInfo m = it.getNext();
			com.FileManagerX.Globals.Datas.DBManager.remove(
					"[&] MachineIndex = " + m.getIndex(),
					com.FileManagerX.DataBase.Unit.Depot
				);
			com.FileManagerX.Globals.Datas.DBManager.remove(
					"[&] MachineIndex = " + m.getIndex(),
					com.FileManagerX.DataBase.Unit.DataBase
				);
		}
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
