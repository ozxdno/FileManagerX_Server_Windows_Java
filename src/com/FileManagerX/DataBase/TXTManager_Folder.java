package com.FileManagerX.DataBase;

public class TXTManager_Folder implements com.FileManagerX.Interfaces.IDBManager {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private com.FileManagerX.BasicModels.DataBaseInfo database;
	private com.FileManagerX.DataBase.Unit unit;
	private boolean running;
	private String name;
	
	private com.FileManagerX.BasicCollections.Folders folders;
	private boolean saveImmediately = false;
	
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

	public TXTManager_Folder() {
		initThis();
	}
	private void initThis() {
		this.database = null;
		this.unit = Unit.BaseFile;
		this.running = false;
		this.name = "Folders";
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
		this.folders = new com.FileManagerX.BasicCollections.Folders();
		com.FileManagerX.FileModels.Text txt = new com.FileManagerX.FileModels.Text
				(this.database.getUrl() + "\\" + this.name + ".txt");
		txt.load(true);
		for(String line : txt.getContent()) {
			com.FileManagerX.BasicModels.Folder f = new com.FileManagerX.BasicModels.Folder();
			String r = f.input(line);
			if(r != null) {
				this.folders.add(f);
			}
		}
		return true;
	}
	public synchronized boolean save() {
		com.FileManagerX.FileModels.Text txt = new com.FileManagerX.FileModels.Text
				(this.database.getUrl() + "\\" + this.name + ".txt");
		for(com.FileManagerX.BasicModels.Folder f : this.folders.getContent()) {
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
	
	public synchronized com.FileManagerX.BasicCollections.Folders querys(Object conditions) {
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
			return this.folders;
		}
		boolean[] checkedMark = new boolean[this.folders.size()];
		for(int i=0; i<this.folders.size(); i++) {
			checkedMark[i] = true;
		}
		
		for(int i=0; i<qcs.size(); i++) {
			QueryCondition qc = qcs.getContent().get(i);
			if(qc.getItemName().equals("Index")) {
				for(int j=0; j<this.folders.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(folders.getContent().get(j).getIndex(), qc);
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
				for(int j=0; j<this.folders.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(folders.getContent().get(j).getFather(), qc);
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
				for(int j=0; j<this.folders.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(folders.getContent().get(j).getMachine(), qc);
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
				for(int j=0; j<this.folders.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(folders.getContent().get(j).getDepot(), qc);
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
				for(int j=0; j<this.folders.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(folders.getContent().get(j).getDataBase(), qc);
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
				for(int j=0; j<this.folders.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyString(folders.getContent().get(j).getUrl(), qc);
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
				for(int j=0; j<this.folders.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyString(folders.getContent().get(j).getType().toString(), qc);
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
				for(int j=0; j<this.folders.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyString(folders.getContent().get(j).getState().toString(), qc);
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
				for(int j=0; j<this.folders.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(folders.getContent().get(j).getModify(), qc);
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
				for(int j=0; j<this.folders.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(folders.getContent().get(j).getLength(), qc);
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
				for(int j=0; j<this.folders.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(folders.getContent().get(j).getScore(), qc);
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
				for(int j=0; j<this.folders.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyString(folders.getContent().get(j).getTags(), qc);
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
		
		com.FileManagerX.BasicCollections.Folders res = new com.FileManagerX.BasicCollections.Folders();
		int cnt = 0;
		
		for(int i=0; i<checkedMark.length; i++) {
			if(checkedMark[i]) {
				com.FileManagerX.BasicModels.Folder n = new com.FileManagerX.BasicModels.Folder();
				n.copyValue(this.folders.getContent().get(i));
				res.add(n);
				
				if(++cnt > com.FileManagerX.Globals.Configurations.DataBaseQueryLimit) { break; }
			}
		}
		return res;
	}
	public synchronized com.FileManagerX.BasicModels.Folder query(Object conditions) {
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
			if(this.folders.size() == 0) {
				return null;
			}
			return this.folders.getContent().get(0);
		}
		
		for(com.FileManagerX.BasicModels.Folder f : this.folders.getContent()) {
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
				com.FileManagerX.BasicModels.Folder r = new com.FileManagerX.BasicModels.Folder();
				r.copyValue(f);
				return r;
			}
		}
		
		return null;
	}
	public synchronized com.FileManagerX.BasicCollections.Folders updates(Object items) {
		if(items == null) {
			return null;
		}
		if(!(items instanceof com.FileManagerX.BasicCollections.Folders)) {
			return null;
		}
		
		com.FileManagerX.BasicCollections.Folders folders = (com.FileManagerX.BasicCollections.Folders)items;
		if(folders.size() == 0) {
			return folders;
		}
		
		com.FileManagerX.BasicCollections.Folders errors = new com.FileManagerX.BasicCollections.Folders();
		boolean save = this.saveImmediately;
		this.saveImmediately = false;
		
		for(com.FileManagerX.BasicModels.Folder f : folders.getContent()) {
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
		if(!(item instanceof com.FileManagerX.BasicModels.Folder)) {
			return false;
		}
		
		com.FileManagerX.BasicModels.Folder folder = (com.FileManagerX.BasicModels.Folder)item;
		int index = (
					folder.getIndex() >= 0 && 
					folder.getIndex() <= com.FileManagerX.Globals.Configurations.Next_FileIndex
					) ?
				this.folders.indexOf(folder.getIndex()) :
				-1;
		
		if(index >= 0) {
			this.folders.getContent().set(index, folder);
		} else {
			folder.setIndex();
			com.FileManagerX.BasicModels.Folder n = new com.FileManagerX.BasicModels.Folder();
			n.copyValue(folder);
			this.folders.add(n);
		}
		
		return this.saveImmediately ? this.save() : true;
	}
	public synchronized com.FileManagerX.BasicCollections.Folders removes(Object items) {
		if(items == null) {
			return null;
		}
		if(!(items instanceof com.FileManagerX.BasicCollections.Folders)) {
			return null;
		}
		
		com.FileManagerX.BasicCollections.Folders folders = (com.FileManagerX.BasicCollections.Folders)items;
		if(folders.size() == 0) {
			return folders;
		}
		
		com.FileManagerX.BasicCollections.Folders errors = new com.FileManagerX.BasicCollections.Folders();
		boolean save = this.saveImmediately;
		this.saveImmediately = false;
		
		for(com.FileManagerX.BasicModels.Folder f : folders.getContent()) {
			if(!this.remove(f)) { errors.add(f); }
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
		if(item instanceof com.FileManagerX.BasicModels.Folder) {
			index = ((com.FileManagerX.BasicModels.Folder)item).getIndex();
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
		
		this.folders.delete(index);
		return this.saveImmediately ? this.save() : true;
	}
	public synchronized long size() {
		return this.folders.size();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
