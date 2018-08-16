package com.FileManagerX.DataBase;

public class TXTManager_CFG implements com.FileManagerX.Interfaces.IDBManager {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private com.FileManagerX.BasicModels.DataBaseInfo database;
	private com.FileManagerX.DataBase.Unit unit;
	private boolean connected;
	private boolean running;
	private String name;
	
	private com.FileManagerX.BasicCollections.BaseFiles cfgs;
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

	public TXTManager_CFG() {
		initThis();
	}
	private void initThis() {
		this.database = null;
		this.unit = Unit.CFG;
		this.connected = false;
		this.name = "Cfgs";
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
		this.cfgs = new com.FileManagerX.BasicCollections.BaseFiles();
		com.FileManagerX.FileModels.Text txt = new com.FileManagerX.FileModels.Text
				(this.database.getUrl() + "\\" + this.name + ".txt");
		txt.load(true);
		for(String line : txt.getContent()) {
			com.FileManagerX.FileModels.CFG cfg = new com.FileManagerX.FileModels.CFG();
			String r = cfg.input(line);
			if(r != null) {
				this.cfgs.add(cfg);
			}
		}
		return true;
	}
	public synchronized boolean save() {
		com.FileManagerX.FileModels.Text txt = new com.FileManagerX.FileModels.Text
				(this.database.getUrl() + "\\" + this.name + ".txt");
		for(com.FileManagerX.BasicModels.BaseFile cfg : this.cfgs.getContent()) {
			txt.getContent().add(cfg.output());
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
	
	public synchronized com.FileManagerX.BasicCollections.BaseFiles querys(Object conditions) {
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
			return this.cfgs;
		}
		
		boolean[] checkedMark = new boolean[this.cfgs.size()];
		for(int i=0; i<this.cfgs.size(); i++) {
			checkedMark[i] = true;
		}
		for(int i=0; i<qcs.size(); i++) {
			QueryCondition qc = qcs.getContent().get(i);
			if(qc.getItemName().equals("Index")) {
				for(int j=0; j<this.cfgs.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(cfgs.getContent().get(j).getIndex(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Father")) {
				for(int j=0; j<this.cfgs.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(cfgs.getContent().get(j).getFather(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Machine")) {
				for(int j=0; j<this.cfgs.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(cfgs.getContent().get(j).getMachine(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Depot")) {
				for(int j=0; j<this.cfgs.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(cfgs.getContent().get(j).getDepot(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("DataBase")) {
				for(int j=0; j<this.cfgs.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(cfgs.getContent().get(j).getDataBase(), qc);
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
				for(int j=0; j<this.cfgs.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyString(cfgs.getContent().get(j).getUrl(), qc);
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
				for(int j=0; j<this.cfgs.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyString(cfgs.getContent().get(j).getType().toString(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("State")) {
				for(int j=0; j<this.cfgs.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyString(cfgs.getContent().get(j).getState().toString(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Modify")) {
				for(int j=0; j<this.cfgs.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(cfgs.getContent().get(j).getModify(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Length")) {
				for(int j=0; j<this.cfgs.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(cfgs.getContent().get(j).getLength(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Score")) {
				for(int j=0; j<this.cfgs.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(cfgs.getContent().get(j).getScore(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Tags")) {
				for(int j=0; j<this.cfgs.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyString(cfgs.getContent().get(j).getTags(), qc);
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
		
		com.FileManagerX.BasicCollections.BaseFiles res = new com.FileManagerX.BasicCollections.BaseFiles();
		int cnt = 0;
		
		for(int i=0; i<checkedMark.length; i++) {
			if(checkedMark[i]) {
				com.FileManagerX.BasicModels.DepotInfo n = new com.FileManagerX.BasicModels.DepotInfo();
				n.copyValue(this.cfgs.getContent().get(i));
				res.add(n);
				
				if(++cnt > com.FileManagerX.Globals.Configurations.DataBaseQueryLimit) { break; }
			}
		}
		return res;
	}
	public synchronized com.FileManagerX.FileModels.CFG query(Object conditions) {
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
			if(this.cfgs.size() == 0) {
				return null;
			}
			return (com.FileManagerX.FileModels.CFG)this.cfgs.getContent().get(0);
		}
		
		for(com.FileManagerX.BasicModels.BaseFile f : this.cfgs.getContent()) {
			boolean ok = true;
			for(QueryCondition qc : qcs.getContent()) {
				if(qc.getItemName().equals("Index")) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(f.getIndex(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Father")) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(f.getFather(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Machine")) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(f.getMachine(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Depot")) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(f.getDepot(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("DataBase")) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(f.getDataBase(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Url")) {
					boolean satisfied = TXTManager_SHELL.satisfyString(f.getUrl(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Type")) {
					boolean satisfied = TXTManager_SHELL.satisfyString(f.getType().toString(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("State")) {
					boolean satisfied = TXTManager_SHELL.satisfyString(f.getState().toString(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Modify")) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(f.getModify(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Length")) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(f.getLength(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Score")) {
					boolean satisfied = TXTManager_SHELL.satisfyInteger(f.getScore(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Tags")) {
					boolean satisfied = TXTManager_SHELL.satisfyString(f.getTags(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
			}
			
			if(ok) {
				com.FileManagerX.FileModels.CFG r = new com.FileManagerX.FileModels.CFG();
				r.copyValue(f);
				return r;
			}
		}
		
		return null;
	}
	public synchronized com.FileManagerX.BasicCollections.BaseFiles updates(Object items) {
		if(items == null) {
			return null;
		}
		if(!(items instanceof com.FileManagerX.BasicCollections.BaseFiles)) {
			return null;
		}
		com.FileManagerX.BasicCollections.BaseFiles files = (com.FileManagerX.BasicCollections.BaseFiles)items;
		if(files.size() == 0) {
			return files;
		}
		
		com.FileManagerX.BasicCollections.BaseFiles errors = new com.FileManagerX.BasicCollections.BaseFiles();
		boolean save = this.saveImmediately;
		this.saveImmediately = false;
		
		for(com.FileManagerX.BasicModels.BaseFile f : files.getContent()) {
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
		if(!(item instanceof com.FileManagerX.FileModels.CFG)) {
			return false;
		}
		
		com.FileManagerX.FileModels.CFG cfg = (com.FileManagerX.FileModels.CFG)item;
		int index = (
					cfg.getIndex() >= 0 && 
					cfg.getIndex() <= com.FileManagerX.Globals.Configurations.Next_FileIndex
					) ?
				this.cfgs.indexOf(cfg.getIndex()) :
				-1;
		
		if(index >= 0) {
			this.cfgs.getContent().set(index, cfg);
		} else {
			cfg.setIndex();
			com.FileManagerX.FileModels.CFG n = new com.FileManagerX.FileModels.CFG();
			n.copyValue(cfg);
			this.cfgs.add(n);
		}
		
		return this.saveImmediately ? this.save() : true;
	}
	public synchronized com.FileManagerX.BasicCollections.BaseFiles removes(Object items) {
		if(items == null) {
			return null;
		}
		if(!(items instanceof com.FileManagerX.BasicCollections.BaseFiles)) {
			return null;
		}
		com.FileManagerX.BasicCollections.BaseFiles files = (com.FileManagerX.BasicCollections.BaseFiles)items;
		if(files.size() == 0) {
			return files;
		}
		
		com.FileManagerX.BasicCollections.BaseFiles errors = new com.FileManagerX.BasicCollections.BaseFiles();
		boolean save = this.saveImmediately;
		this.saveImmediately = false;
		
		for(com.FileManagerX.BasicModels.BaseFile f : files.getContent()) {
			if(!this.update(f)) { errors.add(f); }
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
		if(item instanceof com.FileManagerX.FileModels.CFG) {
			index = ((com.FileManagerX.FileModels.CFG)item).getIndex();
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
		
		this.cfgs.delete(index);
		return this.saveImmediately ? this.save() : true;
	}
	public synchronized long size() {
		return this.cfgs.size();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
