package com.FileManagerX.DataBase;

public class TXTManager_Chat implements com.FileManagerX.Interfaces.IDBManager {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private com.FileManagerX.BasicModels.DataBaseInfo database;
	private com.FileManagerX.DataBase.Unit unit;
	private boolean connected;
	private boolean running;
	private String name;
	
	private com.FileManagerX.BasicCollections.Chats chats;
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

	public TXTManager_Chat() {
		initThis();
	}
	private void initThis() {
		this.database = null;
		this.unit = Unit.Chat;
		this.connected = false;
		this.name = "Chats";
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
		this.chats = new com.FileManagerX.BasicCollections.Chats();
		com.FileManagerX.FileModels.Text txt = new com.FileManagerX.FileModels.Text
				(this.database.getUrl() + "\\" + this.name + ".txt");
		txt.load(true);
		for(String line : txt.getContent()) {
			com.FileManagerX.BasicModels.Chat c = new com.FileManagerX.BasicModels.Chat();
			String r = c.input(line);
			if(r != null) {
				this.chats.add(c);
			}
		}
		return true;
	}
	public synchronized boolean save() {
		com.FileManagerX.FileModels.Text txt = new com.FileManagerX.FileModels.Text
				(this.database.getUrl() + "\\" + this.name + ".txt");
		for(com.FileManagerX.BasicModels.Chat c : this.chats.getContent()) {
			txt.getContent().add(c.output());
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
	
	public synchronized com.FileManagerX.BasicCollections.Chats querys(Object conditions) {
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
			return this.chats;
		}
		
		boolean[] checkedMark = new boolean[this.chats.size()];
		for(int i=0; i<this.chats.size(); i++) {
			checkedMark[i] = true;
		}
		for(int i=0; i<qcs.size(); i++) {
			QueryCondition qc = qcs.getContent().get(i);
			if(qc.getItemName().equals("Index")) {
				for(int j=0; j<this.chats.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(this.chats.getContent().get(j).getIndex(), qc);
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
				for(int j=0; j<this.chats.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyString(this.chats.getContent().get(j).getType().toString(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Time")) {
				for(int j=0; j<this.chats.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(this.chats.getContent().get(j).getTime(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("SourUser")) {
				for(int j=0; j<this.chats.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(this.chats.getContent().get(j).getSourUser(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("DestUser")) {
				for(int j=0; j<this.chats.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(this.chats.getContent().get(j).getDestUser(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("SourGroup")) {
				for(int j=0; j<this.chats.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(this.chats.getContent().get(j).getSourGroup(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("DestGroup")) {
				for(int j=0; j<this.chats.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(this.chats.getContent().get(j).getDestGroup(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Content")) {
				for(int j=0; j<this.chats.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyString(this.chats.getContent().get(j).getContent(), qc);
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
		
		com.FileManagerX.BasicCollections.Chats res = new com.FileManagerX.BasicCollections.Chats();
		int cnt = 0;
		
		for(int i=0; i<checkedMark.length; i++) {
			if(checkedMark[i]) {
				com.FileManagerX.BasicModels.Chat n = new com.FileManagerX.BasicModels.Chat();
				n.copyValue(this.chats.getContent().get(i));
				res.add(n);
				
				if(++cnt > com.FileManagerX.Globals.Configurations.DataBaseQueryLimit) { break; }
			}
		}
		return res;
	}
	public synchronized com.FileManagerX.BasicModels.Chat query(Object conditions) {
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
			if(this.chats.size() == 0) {
				return null;
			}
			return this.chats.getContent().get(0);
		}
		
		for(com.FileManagerX.BasicModels.Chat ie : this.chats.getContent()) {
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
				if(qc.getItemName().equals("Type")) {
					boolean satisfied = TXTManager_SHELL.satisfyString(ie.getType().toString(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Time")) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(ie.getTime(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("SourUser")) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(ie.getSourUser(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("DestUser")) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(ie.getDestUser(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("SourGroup")) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(ie.getSourGroup(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("DestGroup")) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(ie.getDestGroup(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Content")) {
					boolean satisfied = TXTManager_SHELL.satisfyString(ie.getContent(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
			}
			
			if(ok) {
				com.FileManagerX.BasicModels.Chat r = new com.FileManagerX.BasicModels.Chat();
				r.copyValue(ie);
				return r;
			}
		}
		
		return null;
	}
	public synchronized com.FileManagerX.BasicCollections.Chats updates(Object items) {
		if(items == null) {
			return null;
		}
		if(!(items instanceof com.FileManagerX.BasicCollections.Chats)) {
			return null;
		}
		com.FileManagerX.BasicCollections.Chats chats = (com.FileManagerX.BasicCollections.Chats)items;
		if(chats.size() == 0) {
			return chats;
		}
		
		com.FileManagerX.BasicCollections.Chats errors = new com.FileManagerX.BasicCollections.Chats();
		boolean save = this.saveImmediately;
		this.saveImmediately = false;
		
		for(com.FileManagerX.BasicModels.Chat c : chats.getContent()) {
			if(!this.update(c)) { errors.add(c); }
		}
		
		this.saveImmediately = save;
		if(this.saveImmediately) { this.save(); }
		return errors;
	}
	public synchronized boolean update(Object item) {
		if(item == null) {
			return false;
		}
		if(!(item instanceof com.FileManagerX.BasicModels.Chat)) {
			return false;
		}
		
		com.FileManagerX.BasicModels.Chat chat = (com.FileManagerX.BasicModels.Chat)item;
		int index = (
					chat.getIndex() >= 0 && 
					chat.getIndex() <= com.FileManagerX.Globals.Configurations.Next_FileIndex
					) ?
				this.chats.indexOf(chat.getIndex()) :
				-1;
		
		if(index >= 0) {
			this.chats.getContent().set(index, chat);
		} else {
			chat.setIndex();
			com.FileManagerX.BasicModels.Chat n = new com.FileManagerX.BasicModels.Chat();
			n.copyValue(chat);
			this.chats.add(n);
		}
		
		return this.saveImmediately ? this.save() : true;
	}
	public synchronized com.FileManagerX.BasicCollections.Chats removes(Object items) {
		if(items == null) {
			return null;
		}
		if(!(items instanceof com.FileManagerX.BasicCollections.Chats)) {
			return null;
		}
		com.FileManagerX.BasicCollections.Chats chats = (com.FileManagerX.BasicCollections.Chats)items;
		if(chats.size() == 0) {
			return chats;
		}
		
		com.FileManagerX.BasicCollections.Chats errors = new com.FileManagerX.BasicCollections.Chats();
		boolean save = this.saveImmediately;
		this.saveImmediately = false;
		
		for(com.FileManagerX.BasicModels.Chat c : chats.getContent()) {
			if(!this.remove(c)) { errors.add(c); }
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
		if(item instanceof com.FileManagerX.BasicModels.Chat) {
			index = ((com.FileManagerX.BasicModels.Chat)item).getIndex();
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
		
		this.chats.delete(index);
		return this.saveImmediately ? this.save() : true;
	}
	public synchronized long size() {
		return this.chats.size();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
