package com.FileManagerX.DataBase;

public class TXTManager_Group implements com.FileManagerX.Interfaces.IDBManager {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private com.FileManagerX.BasicModels.DataBaseInfo database;
	private com.FileManagerX.DataBase.Unit unit;
	private boolean connected;
	private boolean running;
	private String name;
	
	private com.FileManagerX.BasicCollections.Groups groups;
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
	public synchronized boolean setIsRunning(boolean running) {
		this.running = running;
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

	public TXTManager_Group() {
		initThis();
	}
	private void initThis() {
		this.database = null;
		this.unit = Unit.Group;
		this.connected = false;
		this.name = "Groups";
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public synchronized boolean isConnected() {
		return this.connected;
	}
	public synchronized boolean isRunning() {
		return this.running;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public synchronized boolean connect() {
		this.load();
		return this.connected = this.exists();
	}
	public synchronized boolean disconnect() {
		return this.connected = false;
	}
	public synchronized boolean load() {
		this.groups = new com.FileManagerX.BasicCollections.Groups();
		com.FileManagerX.FileModels.Text txt = new com.FileManagerX.FileModels.Text
				(this.database.getUrl() + "\\" + this.name + ".txt");
		txt.load(true);
		for(String line : txt.getContent()) {
			com.FileManagerX.BasicModels.Group d = new com.FileManagerX.BasicModels.Group();
			String r = d.input(line);
			if(r != null) {
				this.groups.add(d);
			}
		}
		return true;
	}
	public synchronized boolean save() {
		com.FileManagerX.FileModels.Text txt = new com.FileManagerX.FileModels.Text
				(this.database.getUrl() + "\\" + this.name + ".txt");
		for(com.FileManagerX.BasicModels.Group d : this.groups.getContent()) {
			txt.getContent().add(d.output());
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
	
	public synchronized com.FileManagerX.BasicCollections.Groups querys(Object conditions) {
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
			return this.groups;
		}
		
		boolean[] checkedMark = new boolean[this.groups.size()];
		for(int i=0; i<this.groups.size(); i++) {
			checkedMark[i] = true;
		}
		for(int i=0; i<qcs.size(); i++) {
			QueryCondition qc = qcs.getContent().get(i);
			if(qc.getItemName().equals("Index")) {
				for(int j=0; j<this.groups.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(this.groups.getContent().get(j).getIndex(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Users")) {
				for(int j=0; j<this.groups.size(); j++) {
					long[] users = new long[this.groups.getContent().get(i).getUsers().size()];
					for(int k=0; k<this.groups.getContent().get(i).getUsers().size(); k++) {
						users[k] = this.groups.getContent().get(i).getUsers().getContent().get(k).getIndex();
					}
					String ustr = com.FileManagerX.Tools.String.link(users, " ");
					boolean satisfied = TXTManager_SHELL.satisfyString(ustr, qc);
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
		
		com.FileManagerX.BasicCollections.Groups res = new com.FileManagerX.BasicCollections.Groups();
		int cnt = 0;
		
		for(int i=0; i<checkedMark.length; i++) {
			if(checkedMark[i]) {
				com.FileManagerX.BasicModels.Group n = new com.FileManagerX.BasicModels.Group();
				n.copyValue(this.groups.getContent().get(i));
				res.add(n);
				
				if(++cnt > com.FileManagerX.Globals.Configurations.DataBaseQueryLimit) { break; }
			}
		}
		return res;
	}
	public synchronized com.FileManagerX.BasicModels.Group query(Object conditions) {
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
			if(this.groups.size() == 0) {
				return null;
			}
			return this.groups.getContent().get(0);
		}
		
		for(com.FileManagerX.BasicModels.Group ie : this.groups.getContent()) {
			long[] users = new long[ie.getUsers().size()];
			for(int k=0; k<ie.getUsers().size(); k++) {
				users[k] = ie.getUsers().getContent().get(k).getIndex();
			}
			String ustr = com.FileManagerX.Tools.String.link(users, " ");
			
			boolean ok = true;
			for(QueryCondition qc : qcs.getContent()) {
				if(qc.getItemName().equals("Index")) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(ie.getIndex(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Users")) {
					boolean satisfied = TXTManager_SHELL.satisfyString(ustr, qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
			}
			
			if(ok) {
				com.FileManagerX.BasicModels.Group r = new com.FileManagerX.BasicModels.Group();
				r.copyValue(ie);
				return r;
			}
		}
		
		return null;
	}
	public synchronized com.FileManagerX.BasicCollections.Groups updates(Object items) {
		if(items == null) {
			return null;
		}
		if(!(items instanceof com.FileManagerX.BasicCollections.Groups)) {
			return null;
		}
		com.FileManagerX.BasicCollections.Groups groups = (com.FileManagerX.BasicCollections.Groups)items;
		if(groups.size() == 0) {
			return groups;
		}
		
		com.FileManagerX.BasicCollections.Groups errors = new com.FileManagerX.BasicCollections.Groups();
		boolean save = this.saveImmediately;
		this.saveImmediately = false;
		
		for(com.FileManagerX.BasicModels.Group d : groups.getContent()) {
			if(!this.update(d)) { errors.add(d); }
		}
		
		this.saveImmediately = save;
		if(this.saveImmediately) { this.save(); }
		return errors;
	}
	public synchronized boolean update(Object item) {
		if(item == null) {
			return false;
		}
		if(!(item instanceof com.FileManagerX.BasicModels.Group)) {
			return false;
		}
		
		com.FileManagerX.BasicModels.Group depot = (com.FileManagerX.BasicModels.Group)item;
		int index = (
					depot.getIndex() >= 0 && 
					depot.getIndex() <= com.FileManagerX.Globals.Configurations.Next_FileIndex
					) ?
				this.groups.indexOf(depot.getIndex()) :
				-1;
		
		if(index >= 0) {
			this.groups.getContent().set(index, depot);
		} else {
			depot.setIndex();
			com.FileManagerX.BasicModels.Group n = new com.FileManagerX.BasicModels.Group();
			n.copyValue(depot);
			this.groups.add(n);
		}
		
		return this.saveImmediately ? this.save() : true;
	}
	public synchronized com.FileManagerX.BasicCollections.Groups removes(Object items) {
		if(items == null) {
			return null;
		}
		if(!(items instanceof com.FileManagerX.BasicCollections.Groups)) {
			return null;
		}
		com.FileManagerX.BasicCollections.Groups groups = (com.FileManagerX.BasicCollections.Groups)items;
		if(groups.size() == 0) {
			return groups;
		}
		
		com.FileManagerX.BasicCollections.Groups errors = new com.FileManagerX.BasicCollections.Groups();
		boolean save = this.saveImmediately;
		this.saveImmediately = false;
		
		for(com.FileManagerX.BasicModels.Group d : groups.getContent()) {
			if(!this.remove(d)) { errors.add(d); }
		}
		
		this.saveImmediately = save;
		if(this.saveImmediately) { this.save(); }
		return errors;
	}
	public synchronized boolean remove(Object item) {
		long index = -1;
		
		if(item == null) {
			return false;
		}
		if(item instanceof com.FileManagerX.BasicModels.Group) {
			index = ((com.FileManagerX.BasicModels.Group)item).getIndex();
		}
		else if(item instanceof Long) {
			index = (long)item;
		}
		else if(item instanceof Integer) {
			index = (int)item;
		}
		else {
			return false;
		}
		
		this.groups.delete(index);
		return this.saveImmediately ? this.save() : true;
	}
	public synchronized long size() {
		return this.groups.size();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
