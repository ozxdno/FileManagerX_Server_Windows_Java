package com.FileManagerX.DataBase;

public class TXTManager_Invitation implements com.FileManagerX.Interfaces.IDBManager {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private com.FileManagerX.BasicModels.DataBaseInfo database;
	private com.FileManagerX.DataBase.Unit unit;
	private boolean running;
	private String name;
	
	private com.FileManagerX.BasicCollections.Invitations invitations;
	private boolean saveImmediately = true;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public synchronized boolean setDBInfo(com.FileManagerX.BasicModels.DataBaseInfo database) {
		if(database == null) {
			return false;
		}
		this.database = database;
		return true;
	}
	public synchronized boolean setUnit(com.FileManagerX.DataBase.Unit unit) {
		if(unit == null) {
			return false;
		}
		this.unit = unit;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public synchronized com.FileManagerX.BasicModels.DataBaseInfo getDBInfo() {
		return this.database;
	}
	public synchronized com.FileManagerX.DataBase.Unit getUnit() {
		return this.unit;
	}
	public synchronized com.FileManagerX.Interfaces.IDBManager getUnitMananger() {
		return this;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public TXTManager_Invitation() {
		initThis();
	}
	private void initThis() {
		this.database = null;
		this.unit = Unit.BaseFile;
		this.running = false;
		this.name = "Invitations";
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public synchronized boolean isConnected() {
		return this.running;
	}
	public synchronized boolean connect() {
		this.load();
		return this.running = this.exists();
	}
	public synchronized boolean disconnect() {
		return this.running = false;
	}
	public synchronized boolean load() {
		this.invitations = new com.FileManagerX.BasicCollections.Invitations();
		com.FileManagerX.FileModels.Text txt = new com.FileManagerX.FileModels.Text
				(this.database.getUrl() + "\\" + this.name + ".txt");
		txt.load(true);
		for(String line : txt.getContent()) {
			com.FileManagerX.BasicModels.Invitation f = new com.FileManagerX.BasicModels.Invitation();
			String r = f.input(line);
			if(r != null) {
				this.invitations.add(f);
			}
		}
		return true;
	}
	public synchronized boolean save() {
		com.FileManagerX.FileModels.Text txt = new com.FileManagerX.FileModels.Text
				(this.database.getUrl() + "\\" + this.name + ".txt");
		for(com.FileManagerX.BasicModels.Invitation f : this.invitations.getContent()) {
			txt.getContent().add(f.output());
		}
		return txt.save();
	}
	public synchronized boolean create() {
		if(this.exists()) { return true; }
		
		try {
			java.io.File file = new java.io.File(this.database.getUrl() + "\\" + this.name + ".txt");
			return file.createNewFile();
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.DB_OPERATE_FAILED.register("Create Failed");
			return false;
		}
	}
	public synchronized boolean delete() {
		if(!this.exists()) { return true; }
		
		try {
			java.io.File file = new java.io.File(this.database.getUrl() + "\\" + this.name + ".txt");
			return file.delete();
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.DB_OPERATE_FAILED.register("Delete Failed");
			return false;
		}
	}
	public synchronized boolean exists() {
		if(this.database == null) {
			return false;
		}
		
		java.io.File file = new java.io.File(this.name);
		return file.exists() && file.isFile();
	}
	public synchronized boolean clear() {
		return this.delete();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public synchronized com.FileManagerX.BasicCollections.Invitations querys(Object conditions) {
		if(conditions == null) {
			return null;
		}
		QueryConditions qcs = new QueryConditions();
		if(conditions instanceof QueryCondition) {
			qcs.add((QueryCondition)conditions);
		}
		else if(conditions instanceof QueryConditions) {
			qcs = (QueryConditions)conditions;
		}
		else if(conditions instanceof String) {
			try {
				qcs.stringToThis((String)conditions);
			}catch(Exception e) {
				com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
				return null;
			}
		}
		else {
			return null;
		}
		if(qcs.size() == 0) {
			return this.invitations;
		}
		
		boolean[] checkedMark = new boolean[this.invitations.size()];
		for(int i=0; i<this.invitations.size(); i++) {
			checkedMark[i] = true;
		}
		for(int i=0; i<qcs.size(); i++) {
			QueryCondition qc = qcs.getContent().get(i);
			if(qc.getItemName().equals("Code")) {
				for(int j=0; j<this.invitations.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyString(invitations.getContent().get(j).getCode(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("ActiveTime")) {
				for(int j=0; j<this.invitations.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyString(invitations.getContent().get(j).getActiveTime(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("ActiveAmount")) {
				for(int j=0; j<this.invitations.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyInteger(invitations.getContent().get(j).getActiveAmount(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Priority")) {
				for(int j=0; j<this.invitations.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyString(invitations.getContent().get(j).getUser().getPriority().toString(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Level")) {
				for(int j=0; j<this.invitations.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyString(invitations.getContent().get(j).getUser().getLevel().toString(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Experience")) {
				for(int j=0; j<this.invitations.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(invitations.getContent().get(j).getUser().getExperience(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Coins")) {
				for(int j=0; j<this.invitations.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(invitations.getContent().get(j).getUser().getCoins(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Money")) {
				for(int j=0; j<this.invitations.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyDouble(invitations.getContent().get(j).getUser().getMoney(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
		}
		
		com.FileManagerX.BasicCollections.Invitations res = new com.FileManagerX.BasicCollections.Invitations();
		int cnt = 0;
		
		for(int i=0; i<checkedMark.length; i++) {
			if(checkedMark[i]) {
				com.FileManagerX.BasicModels.Invitation n = new com.FileManagerX.BasicModels.Invitation();
				n.copyValue(this.invitations.getContent().get(i));
				res.add(n);
				
				if(++cnt > com.FileManagerX.Globals.Configurations.DataBaseQueryLimit) { break; }
			}
		}
		return res;
	}
	public synchronized com.FileManagerX.BasicModels.Invitation query(Object conditions) {
		if(conditions == null) {
			return null;
		}
		QueryConditions qcs = new QueryConditions();
		if(conditions instanceof QueryCondition) {
			qcs.add((QueryCondition)conditions);
		}
		else if(conditions instanceof QueryConditions) {
			qcs = (QueryConditions)conditions;
		}
		else if(conditions instanceof String) {
			try {
				qcs.stringToThis((String)conditions);
			}catch(Exception e) {
				com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
				return null;
			}
		}
		else {
			return null;
		}
		if(qcs.size() == 0) {
			if(this.invitations.size() == 0) {
				return null;
			}
			return this.invitations.getContent().get(0);
		}
		
		for(com.FileManagerX.BasicModels.Invitation ie : this.invitations.getContent()) {
			boolean ok = true;
			for(QueryCondition qc : qcs.getContent()) {
				if(qc.getItemName().equals("Code")) {
					boolean satisfied = TXTManager_SHELL.satisfyString(ie.getCode(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("ActiveTime")) {
					boolean satisfied = TXTManager_SHELL.satisfyString(ie.getActiveTime(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("ActiveAmount")) {
					boolean satisfied = TXTManager_SHELL.satisfyInteger(ie.getActiveAmount(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Priority")) {
					boolean satisfied = TXTManager_SHELL.satisfyString(ie.getUser().getPriority().toString(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Level")) {
					boolean satisfied = TXTManager_SHELL.satisfyString(ie.getUser().getLevel().toString(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Experience")) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(ie.getUser().getExperience(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Coins")) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(ie.getUser().getCoins(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Money")) {
					boolean satisfied = TXTManager_SHELL.satisfyDouble(ie.getUser().getMoney(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
			}
			
			if(ok) {
				com.FileManagerX.BasicModels.Invitation r = new com.FileManagerX.BasicModels.Invitation();
				r.copyValue(ie);
				return r;
			}
		}
		
		return null;
	}
	public synchronized com.FileManagerX.BasicCollections.Invitations updates(Object items) {
		if(items == null) {
			return null;
		}
		if(!(items instanceof com.FileManagerX.BasicCollections.Invitations)) {
			return null;
		}
		
		com.FileManagerX.BasicCollections.Invitations invitations = (com.FileManagerX.BasicCollections.Invitations)items;
		if(invitations.size() == 0) {
			return invitations;
		}
		
		com.FileManagerX.BasicCollections.Invitations errors = new com.FileManagerX.BasicCollections.Invitations();
		boolean save = this.saveImmediately;
		this.saveImmediately = false;
		
		for(com.FileManagerX.BasicModels.Invitation f : invitations.getContent()) {
			if(!this.update(f)) { errors.add(f); }
		}
		
		this.saveImmediately = save;
		if(this.saveImmediately) { this.save(); }
		return errors;
	}
	public synchronized boolean update(Object item) {
		if(item == null) {
			return false;
		}
		if(!(item instanceof com.FileManagerX.BasicModels.Invitation)) {
			return false;
		}
		
		com.FileManagerX.BasicModels.Invitation invitation = (com.FileManagerX.BasicModels.Invitation)item;
		int index = (
					invitation.getCode() != null &&
					invitation.getCode().length() > 0
					) ?
				this.invitations.indexOf(invitation.getCode()) :
				-1;
		
		if(index >= 0) {
			this.invitations.getContent().set(index, invitation);
		} else {
			com.FileManagerX.BasicModels.Invitation n = new com.FileManagerX.BasicModels.Invitation();
			n.copyValue(invitation);
			this.invitations.add(n);
		}
		
		return this.saveImmediately ? this.save() : true;
	}
	public synchronized com.FileManagerX.BasicCollections.Invitations removes(Object items) {
		if(items == null) {
			return null;
		}
		if(!(items instanceof com.FileManagerX.BasicCollections.Invitations)) {
			return null;
		}
		
		com.FileManagerX.BasicCollections.Invitations invitations = (com.FileManagerX.BasicCollections.Invitations)items;
		if(invitations.size() == 0) {
			return invitations;
		}
		
		com.FileManagerX.BasicCollections.Invitations errors = new com.FileManagerX.BasicCollections.Invitations();
		boolean save = this.saveImmediately;
		this.saveImmediately = false;
		
		for(com.FileManagerX.BasicModels.Invitation f : invitations.getContent()) {
			if(!this.remove(f)) { errors.add(f); }
		}
		
		this.saveImmediately = save;
		if(this.saveImmediately) { this.save(); }
		return errors;
	}
	public synchronized boolean remove(Object item) {
		String code = null;
		
		if(item == null) {
			return false;
		}
		if(item instanceof com.FileManagerX.BasicModels.Invitation) {
			code = ((com.FileManagerX.BasicModels.Invitation)item).getCode();
		}
		else if(item instanceof String) {
			code = (String)item;
		}
		else {
			return false;
		}
		
		this.invitations.delete(code);
		return this.saveImmediately ? this.save() : true;
	}
	public synchronized long size() {
		return this.invitations.size();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
