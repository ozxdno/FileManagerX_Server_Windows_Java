package com.FileManagerX.DataBase;

public class TXTManager_Depot implements com.FileManagerX.Interfaces.IDBManager {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private com.FileManagerX.BasicModels.DataBaseInfo database;
	private com.FileManagerX.DataBase.Unit unit;
	private boolean connected;
	private boolean running;
	private String name;
	
	private com.FileManagerX.BasicCollections.DepotInfos depots;
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

	public TXTManager_Depot() {
		initThis();
	}
	private void initThis() {
		this.database = null;
		this.unit = Unit.BaseFile;
		this.connected = false;
		this.name = "Depots";
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
		this.depots = new com.FileManagerX.BasicCollections.DepotInfos();
		com.FileManagerX.FileModels.Text txt = new com.FileManagerX.FileModels.Text
				(this.database.getUrl() + "\\" + this.name + ".txt");
		txt.load(true);
		for(String line : txt.getContent()) {
			com.FileManagerX.BasicModels.DepotInfo d = new com.FileManagerX.BasicModels.DepotInfo();
			String r = d.input(line);
			if(r != null) {
				this.depots.add(d);
			}
		}
		return true;
	}
	public synchronized boolean save() {
		com.FileManagerX.FileModels.Text txt = new com.FileManagerX.FileModels.Text
				(this.database.getUrl() + "\\" + this.name + ".txt");
		for(com.FileManagerX.BasicModels.DepotInfo d : this.depots.getContent()) {
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
	
	public synchronized com.FileManagerX.BasicCollections.DepotInfos querys(Object conditions) {
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
			return this.depots;
		}
		
		boolean[] checkedMark = new boolean[this.depots.size()];
		for(int i=0; i<this.depots.size(); i++) {
			checkedMark[i] = true;
		}
		for(int i=0; i<qcs.size(); i++) {
			QueryCondition qc = qcs.getContent().get(i);
			if(qc.getItemName().equals("Index")) {
				for(int j=0; j<this.depots.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(this.depots.getContent().get(j).getIndex(), qc);
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
				for(int j=0; j<this.depots.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyString(this.depots.getContent().get(j).getName(), qc);
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
				for(int j=0; j<this.depots.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(this.depots.getContent().get(j).getMachineInfo().getIndex(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("DataBaseIndex")) {
				for(int j=0; j<this.depots.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(this.depots.getContent().get(j).getDBIndex(), qc);
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
				for(int j=0; j<this.depots.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyString(this.depots.getContent().get(j).getState().toString(), qc);
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
				for(int j=0; j<this.depots.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyString(this.depots.getContent().get(j).getUrl(), qc);
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
		
		com.FileManagerX.BasicCollections.DepotInfos res = new com.FileManagerX.BasicCollections.DepotInfos();
		int cnt = 0;
		
		for(int i=0; i<checkedMark.length; i++) {
			if(checkedMark[i]) {
				com.FileManagerX.BasicModels.DepotInfo n = new com.FileManagerX.BasicModels.DepotInfo();
				n.copyValue(this.depots.getContent().get(i));
				res.add(n);
				
				if(++cnt > com.FileManagerX.Globals.Configurations.DataBaseQueryLimit) { break; }
			}
		}
		return res;
	}
	public synchronized com.FileManagerX.BasicModels.DepotInfo query(Object conditions) {
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
			if(this.depots.size() == 0) {
				return null;
			}
			return this.depots.getContent().get(0);
		}
		
		for(com.FileManagerX.BasicModels.DepotInfo ie : this.depots.getContent()) {
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
				if(qc.getItemName().equals("DataBaseIndex")) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(ie.getDBIndex(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("State")) {
					boolean satisfied = TXTManager_SHELL.satisfyString(ie.getState().toString(), qc);
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
				com.FileManagerX.BasicModels.DepotInfo r = new com.FileManagerX.BasicModels.DepotInfo();
				r.copyValue(ie);
				return r;
			}
		}
		
		return null;
	}
	public synchronized com.FileManagerX.BasicCollections.DepotInfos updates(Object items) {
		if(items == null) {
			return null;
		}
		if(!(items instanceof com.FileManagerX.BasicCollections.DepotInfos)) {
			return null;
		}
		com.FileManagerX.BasicCollections.DepotInfos depots = (com.FileManagerX.BasicCollections.DepotInfos)items;
		if(depots.size() == 0) {
			return depots;
		}
		
		com.FileManagerX.BasicCollections.DepotInfos errors = new com.FileManagerX.BasicCollections.DepotInfos();
		boolean save = this.saveImmediately;
		this.saveImmediately = false;
		
		for(com.FileManagerX.BasicModels.DepotInfo d : depots.getContent()) {
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
		if(!(item instanceof com.FileManagerX.BasicModels.DepotInfo)) {
			return false;
		}
		
		com.FileManagerX.BasicModels.DepotInfo depot = (com.FileManagerX.BasicModels.DepotInfo)item;
		int index = (
					depot.getIndex() >= 0 && 
					depot.getIndex() <= com.FileManagerX.Globals.Configurations.Next_FileIndex
					) ?
				this.depots.indexOf(depot.getIndex()) :
				-1;
		
		if(index >= 0) {
			this.depots.getContent().set(index, depot);
		} else {
			depot.setIndex();
			com.FileManagerX.BasicModels.DepotInfo n = new com.FileManagerX.BasicModels.DepotInfo();
			n.copyValue(depot);
			this.depots.add(n);
		}
		
		return this.saveImmediately ? this.save() : true;
	}
	public synchronized com.FileManagerX.BasicCollections.DepotInfos removes(Object items) {
		if(items == null) {
			return null;
		}
		if(!(items instanceof com.FileManagerX.BasicCollections.DepotInfos)) {
			return null;
		}
		com.FileManagerX.BasicCollections.DepotInfos depots = (com.FileManagerX.BasicCollections.DepotInfos)items;
		if(depots.size() == 0) {
			return depots;
		}
		
		com.FileManagerX.BasicCollections.DepotInfos errors = new com.FileManagerX.BasicCollections.DepotInfos();
		boolean save = this.saveImmediately;
		this.saveImmediately = false;
		
		for(com.FileManagerX.BasicModels.DepotInfo d : depots.getContent()) {
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
		if(item instanceof com.FileManagerX.BasicModels.DepotInfo) {
			index = ((com.FileManagerX.BasicModels.DepotInfo)item).getIndex();
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
		
		this.depots.delete(index);
		return this.saveImmediately ? this.save() : true;
	}
	public synchronized long size() {
		return this.depots.size();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
