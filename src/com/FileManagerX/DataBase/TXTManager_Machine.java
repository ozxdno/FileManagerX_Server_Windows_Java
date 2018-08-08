package com.FileManagerX.DataBase;

public class TXTManager_Machine implements com.FileManagerX.Interfaces.IDBManager {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private com.FileManagerX.BasicModels.DataBaseInfo database;
	private com.FileManagerX.DataBase.Unit unit;
	private boolean running;
	private String name;
	
	private com.FileManagerX.BasicCollections.MachineInfos machines;
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

	public TXTManager_Machine() {
		initThis();
	}
	private void initThis() {
		this.database = null;
		this.unit = Unit.BaseFile;
		this.running = false;
		this.name = "Machines";
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
		this.machines = new com.FileManagerX.BasicCollections.MachineInfos();
		com.FileManagerX.FileModels.Text txt = new com.FileManagerX.FileModels.Text
				(this.database.getUrl() + "\\" + this.name + ".txt");
		txt.load(true);
		for(String line : txt.getContent()) {
			com.FileManagerX.BasicModels.MachineInfo m = new com.FileManagerX.BasicModels.MachineInfo();
			String r = m.input(line);
			if(r != null) {
				this.machines.add(m);
			}
		}
		return true;
	}
	public synchronized boolean save() {
		com.FileManagerX.FileModels.Text txt = new com.FileManagerX.FileModels.Text
				(this.database.getUrl() + "\\" + this.name + ".txt");
		for(com.FileManagerX.BasicModels.MachineInfo m : this.machines.getContent()) {
			txt.getContent().add(m.output());
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
	
	public synchronized com.FileManagerX.BasicCollections.MachineInfos querys(Object conditions) {
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
			return this.machines;
		}
		
		boolean[] checkedMark = new boolean[this.machines.size()];
		for(int i=0; i<this.machines.size(); i++) {
			checkedMark[i] = true;
		}
		for(int i=0; i<qcs.size(); i++) {
			QueryCondition qc = qcs.getContent().get(i);
			if(qc.getItemName().equals("Index")) {
				for(int j=0; j<this.machines.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(this.machines.getContent().get(j).getIndex(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("UserIndex")) {
				for(int j=0; j<this.machines.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(this.machines.getContent().get(j).getUserIndex(), qc);
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
				for(int j=0; j<this.machines.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyString(this.machines.getContent().get(j).getName(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("MAC")) {
				for(int j=0; j<this.machines.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyString(this.machines.getContent().get(j).getMac(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("IP")) {
				for(int j=0; j<this.machines.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyString(this.machines.getContent().get(j).getIp(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Port")) {
				for(int j=0; j<this.machines.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyInteger(this.machines.getContent().get(j).getPort(), qc);
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
		
		com.FileManagerX.BasicCollections.MachineInfos res = new com.FileManagerX.BasicCollections.MachineInfos();
		int cnt = 0;
		
		for(int i=0; i<checkedMark.length; i++) {
			if(checkedMark[i]) {
				com.FileManagerX.BasicModels.MachineInfo n = new com.FileManagerX.BasicModels.MachineInfo();
				n.copyValue(this.machines.getContent().get(i));
				res.add(n);
				
				if(++cnt > com.FileManagerX.Globals.Configurations.DataBaseQueryLimit) { break; }
			}
		}
		return res;
	}
	public synchronized com.FileManagerX.BasicModels.MachineInfo query(Object conditions) {
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
			if(this.machines.size() == 0) {
				return null;
			}
			return this.machines.getContent().get(0);
		}
		
		for(com.FileManagerX.BasicModels.MachineInfo ie : this.machines.getContent()) {
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
				if(qc.getItemName().equals("UserIndex")) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(ie.getUserIndex(), qc);
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
				if(qc.getItemName().equals("MAC")) {
					boolean satisfied = TXTManager_SHELL.satisfyString(ie.getMac(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("IP")) {
					boolean satisfied = TXTManager_SHELL.satisfyString(ie.getIp(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Port")) {
					boolean satisfied = TXTManager_SHELL.satisfyInteger(ie.getPort(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
			}
			
			if(ok) {
				com.FileManagerX.BasicModels.MachineInfo r = new com.FileManagerX.BasicModels.MachineInfo();
				r.copyValue(ie);
				return r;
			}
		}
		
		return null;
	}
	public synchronized com.FileManagerX.BasicCollections.MachineInfos updates(Object items) {
		if(items == null) {
			return null;
		}
		if(!(items instanceof com.FileManagerX.BasicCollections.MachineInfos)) {
			return null;
		}
		
		com.FileManagerX.BasicCollections.MachineInfos machines = (com.FileManagerX.BasicCollections.MachineInfos)items;
		if(machines.size() == 0) {
			return machines;
		}
		
		com.FileManagerX.BasicCollections.MachineInfos errors = new com.FileManagerX.BasicCollections.MachineInfos();
		boolean save = this.saveImmediately;
		this.saveImmediately = false;
		
		for(com.FileManagerX.BasicModels.MachineInfo m : machines.getContent()) {
			if(!this.update(m)) { errors.add(m); }
		}
		
		this.saveImmediately = save;
		if(this.saveImmediately) { this.save(); }
		return errors;
	}
	public synchronized boolean update(Object item) {
		if(item == null) {
			return false;
		}
		if(!(item instanceof com.FileManagerX.BasicModels.MachineInfo)) {
			return false;
		}
		
		com.FileManagerX.BasicModels.MachineInfo machine = (com.FileManagerX.BasicModels.MachineInfo)item;
		int index = (
					machine.getIndex() >= 0 && 
					machine.getIndex() <= com.FileManagerX.Globals.Configurations.Next_FileIndex
					) ?
				this.machines.indexOf(machine.getIndex()) :
				-1;
		
		if(index >= 0) {
			this.machines.getContent().set(index, machine);
		} else {
			machine.setIndex();
			com.FileManagerX.BasicModels.MachineInfo n = new com.FileManagerX.BasicModels.MachineInfo();
			n.copyValue(machine);
			this.machines.add(n);
		}
		
		return this.saveImmediately ? this.save() : true;
	}
	public synchronized com.FileManagerX.BasicCollections.MachineInfos removes(Object items) {
		if(items == null) {
			return null;
		}
		if(!(items instanceof com.FileManagerX.BasicCollections.MachineInfos)) {
			return null;
		}
		
		com.FileManagerX.BasicCollections.MachineInfos machines = (com.FileManagerX.BasicCollections.MachineInfos)items;
		if(machines.size() == 0) {
			return machines;
		}
		
		com.FileManagerX.BasicCollections.MachineInfos errors = new com.FileManagerX.BasicCollections.MachineInfos();
		boolean save = this.saveImmediately;
		this.saveImmediately = false;
		
		for(com.FileManagerX.BasicModels.MachineInfo m : machines.getContent()) {
			if(!this.remove(m)) { errors.add(m); }
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
		if(item instanceof com.FileManagerX.BasicModels.MachineInfo) {
			index = ((com.FileManagerX.BasicModels.MachineInfo)item).getIndex();
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
		
		this.machines.delete(index);
		return this.saveImmediately ? this.save() : true;
	}
	public synchronized long size() {
		return this.machines.size();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
