package com.FileManagerX.DataBase;

public class TXTManager_DataBase implements com.FileManagerX.Interfaces.IDBManager {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private com.FileManagerX.BasicModels.DataBaseInfo database;
	private com.FileManagerX.DataBase.Unit unit;
	private boolean running;
	private String name;
	
	private com.FileManagerX.BasicCollections.DataBaseInfos databases;
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

	public TXTManager_DataBase() {
		initThis();
	}
	private void initThis() {
		this.database = null;
		this.unit = Unit.BaseFile;
		this.running = false;
		this.name = "DataBases";
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
		this.databases = new com.FileManagerX.BasicCollections.DataBaseInfos();
		com.FileManagerX.FileModels.Text txt = new com.FileManagerX.FileModels.Text
				(this.database.getUrl() + "\\" + this.name + ".txt");
		txt.load(true);
		for(String line : txt.getContent()) {
			com.FileManagerX.BasicModels.DataBaseInfo d = new com.FileManagerX.BasicModels.DataBaseInfo();
			String r = d.input(line);
			if(r != null) {
				this.databases.add(d);
			}
		}
		return true;
	}
	public synchronized boolean save() {
		com.FileManagerX.FileModels.Text txt = new com.FileManagerX.FileModels.Text
				(this.database.getUrl() + "\\" + this.name + ".txt");
		for(com.FileManagerX.BasicModels.DataBaseInfo d : this.databases.getContent()) {
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
	
	public synchronized com.FileManagerX.BasicCollections.DataBaseInfos querys(Object conditions) {
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
			return databases;
		}
		
		boolean[] checkedMark = new boolean[databases.size()];
		for(int i=0; i<databases.size(); i++) {
			checkedMark[i] = true;
		}
		for(int i=0; i<qcs.size(); i++) {
			QueryCondition qc = qcs.getContent().get(i);
			if(qc.getItemName().equals("Index")) {
				for(int j=0; j<databases.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(databases.getContent().get(j).getIndex(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Name")) {
				for(int j=0; j<databases.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyString(databases.getContent().get(j).getName(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("MachineIndex")) {
				for(int j=0; j<databases.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(databases.getContent().get(j).getMachineInfo().getIndex(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("DepotIndex")) {
				for(int j=0; j<databases.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(databases.getContent().get(j).getDepotIndex(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Type")) {
				for(int j=0; j<databases.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyString(databases.getContent().get(j).getType().toString(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Url")) {
				for(int j=0; j<databases.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyString(databases.getContent().get(j).getUrl(), qc);
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
		
		com.FileManagerX.BasicCollections.DataBaseInfos res = new com.FileManagerX.BasicCollections.DataBaseInfos();
		int cnt = 0;
		
		for(int i=0; i<checkedMark.length; i++) {
			if(checkedMark[i]) {
				com.FileManagerX.BasicModels.DataBaseInfo n = new com.FileManagerX.BasicModels.DataBaseInfo();
				n.copyValue(databases.getContent().get(i));
				res.add(n);
				
				if(++cnt > com.FileManagerX.Globals.Configurations.DataBaseQueryLimit) { break; }
			}
		}
		return res;
	}
	public synchronized com.FileManagerX.BasicModels.DataBaseInfo query(Object conditions) {
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
			if(databases.size() == 0) {
				return null;
			}
			return databases.getContent().get(0);
		}
		
		for(com.FileManagerX.BasicModels.DataBaseInfo ie : databases.getContent()) {
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
				if(qc.getItemName().equals("Name")) {
					boolean satisfied = TXTManager_SHELL.satisfyString(ie.getName(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("MachineIndex")) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(ie.getMachineInfo().getIndex(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("DepotIndex")) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(ie.getDepotIndex(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Type")) {
					boolean satisfied = TXTManager_SHELL.satisfyString(ie.getType().toString(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Url")) {
					boolean satisfied = TXTManager_SHELL.satisfyString(ie.getUrl(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
			}
			
			if(ok) {
				com.FileManagerX.BasicModels.DataBaseInfo r = new com.FileManagerX.BasicModels.DataBaseInfo();
				r.copyValue(ie);
				return r;
			}
		}
		
		return null;
	}
	public synchronized com.FileManagerX.BasicCollections.DataBaseInfos updates(Object items) {
		if(items == null) {
			return null;
		}
		if(!(items instanceof com.FileManagerX.BasicCollections.DataBaseInfos)) {
			return null;
		}
		
		com.FileManagerX.BasicCollections.DataBaseInfos databases = 
				(com.FileManagerX.BasicCollections.DataBaseInfos)items;
		if(databases.size() == 0) {
			return databases;
		}
		
		com.FileManagerX.BasicCollections.DataBaseInfos errors = new com.FileManagerX.BasicCollections.DataBaseInfos();
		boolean save = this.saveImmediately;
		this.saveImmediately = false;
		
		for(com.FileManagerX.BasicModels.DataBaseInfo d : databases.getContent()) {
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
		if(!(item instanceof com.FileManagerX.BasicModels.DataBaseInfo)) {
			return false;
		}
		
		com.FileManagerX.BasicModels.DataBaseInfo database = (com.FileManagerX.BasicModels.DataBaseInfo)item;
		int index = (
					database.getIndex() >= 0 && 
					database.getIndex() <= com.FileManagerX.Globals.Configurations.Next_FileIndex
					) ?
				this.databases.indexOf(database.getIndex()) :
				-1;
		
		if(index >= 0) {
			this.databases.getContent().set(index, database);
		} else {
			database.setIndex();
			com.FileManagerX.BasicModels.DataBaseInfo n = new com.FileManagerX.BasicModels.DataBaseInfo();
			n.copyValue(database);
			this.databases.add(n);
		}
		
		return this.saveImmediately ? this.save() : true;
	}
	public synchronized com.FileManagerX.BasicCollections.DataBaseInfos removes(Object items) {
		if(items == null) {
			return null;
		}
		if(!(items instanceof com.FileManagerX.BasicCollections.DataBaseInfos)) {
			return null;
		}
		
		com.FileManagerX.BasicCollections.DataBaseInfos databases = 
				(com.FileManagerX.BasicCollections.DataBaseInfos)items;
		if(databases.size() == 0) {
			return databases;
		}
		
		com.FileManagerX.BasicCollections.DataBaseInfos errors = new com.FileManagerX.BasicCollections.DataBaseInfos();
		boolean save = this.saveImmediately;
		this.saveImmediately = false;
		
		for(com.FileManagerX.BasicModels.DataBaseInfo d : databases.getContent()) {
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
		if(item instanceof com.FileManagerX.BasicModels.DataBaseInfo) {
			index = ((com.FileManagerX.BasicModels.DataBaseInfo)item).getIndex();
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
		
		this.databases.delete(index);
		return this.saveImmediately ? this.save() : true;
	}
	public synchronized long size() {
		return this.databases.size();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
