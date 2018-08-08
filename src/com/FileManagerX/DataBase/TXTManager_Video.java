package com.FileManagerX.DataBase;

public class TXTManager_Video implements com.FileManagerX.Interfaces.IDBManager {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private com.FileManagerX.BasicModels.DataBaseInfo database;
	private com.FileManagerX.DataBase.Unit unit;
	private boolean running;
	private String name;
	
	private com.FileManagerX.BasicCollections.BaseFiles videos;
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

	public TXTManager_Video() {
		initThis();
	}
	private void initThis() {
		this.database = null;
		this.unit = Unit.BaseFile;
		this.running = false;
		this.name = "Videos";
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
		this.videos = new com.FileManagerX.BasicCollections.BaseFiles();
		com.FileManagerX.FileModels.Text txt = new com.FileManagerX.FileModels.Text
				(this.database.getUrl() + "\\" + this.name + ".txt");
		txt.load(true);
		for(String line : txt.getContent()) {
			com.FileManagerX.FileModels.Video f = new com.FileManagerX.FileModels.Video();
			String r = f.input(line);
			if(r != null) {
				this.videos.add(f);
			}
		}
		return true;
	}
	public synchronized boolean save() {
		com.FileManagerX.FileModels.Text txt = new com.FileManagerX.FileModels.Text
				(this.database.getUrl() + "\\" + this.name + ".txt");
		for(com.FileManagerX.BasicModels.BaseFile f : this.videos.getContent()) {
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
	
	public synchronized com.FileManagerX.BasicCollections.BaseFiles querys(Object conditions) {
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
			return this.videos;
		}
		
		boolean[] checkedMark = new boolean[this.videos.size()];
		for(int i=0; i<this.videos.size(); i++) {
			checkedMark[i] = true;
		}
		
		for(int i=0; i<qcs.size(); i++) {
			QueryCondition qc = qcs.getContent().get(i);
			com.FileManagerX.FileModels.Video p = null;
			if(qc.getItemName().equals("Index")) {
				for(int j=0; j<this.videos.size(); j++) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(videos.getContent().get(j).getIndex(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Height")) {
				for(int j=0; j<this.videos.size(); j++) {
					p = (com.FileManagerX.FileModels.Video)videos.getContent().get(j);
					boolean satisfied = TXTManager_SHELL.satisfyInteger(p.getHeight(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Width")) {
				for(int j=0; j<this.videos.size(); j++) {
					p = (com.FileManagerX.FileModels.Video)videos.getContent().get(j);
					boolean satisfied = TXTManager_SHELL.satisfyInteger(p.getWidth(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						checkedMark[j] &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						checkedMark[j] |= satisfied;
					}
				}
				continue;
			}
			if(qc.getItemName().equals("Lasting")) {
				for(int j=0; j<this.videos.size(); j++) {
					p = (com.FileManagerX.FileModels.Video)videos.getContent().get(j);
					boolean satisfied = TXTManager_SHELL.satisfyLong(p.getLasting(), qc);
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
				com.FileManagerX.FileModels.Video n = new com.FileManagerX.FileModels.Video();
				n.copyValue(this.videos.getContent().get(i));
				res.add(n);
				
				if(++cnt > com.FileManagerX.Globals.Configurations.DataBaseQueryLimit) { break; }
			}
		}
		return res;
	}
	public synchronized com.FileManagerX.FileModels.Video query(Object conditions) {
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
			return this.videos.size() == 0 ? null : (com.FileManagerX.FileModels.Video)
					this.videos.getContent().get(0);
		}
		
		for(com.FileManagerX.BasicModels.BaseFile f : this.videos.getContent()) {
			boolean ok = true;
			com.FileManagerX.FileModels.Video p = (com.FileManagerX.FileModels.Video)f;
			for(QueryCondition qc : qcs.getContent()) {
				if(qc.getItemName().equals("Index")) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(p.getIndex(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Height")) {
					boolean satisfied = TXTManager_SHELL.satisfyInteger(p.getHeight(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Width")) {
					boolean satisfied = TXTManager_SHELL.satisfyInteger(p.getWidth(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
				if(qc.getItemName().equals("Lasting")) {
					boolean satisfied = TXTManager_SHELL.satisfyLong(p.getLasting(), qc);
					if(qc.getRelation().equals(Relation.AND)) {
						ok &= satisfied;
					}
					if(qc.getRelation().equals(Relation.OR)) {
						ok |= satisfied;
					}
				}
			}
			
			if(ok) {
				com.FileManagerX.FileModels.Video r = new com.FileManagerX.FileModels.Video();
				r.copyValue(p);
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
		
		com.FileManagerX.BasicCollections.BaseFiles videos = (com.FileManagerX.BasicCollections.BaseFiles)items;
		if(videos.size() == 0) {
			return videos;
		}
		
		com.FileManagerX.BasicCollections.BaseFiles errors = new com.FileManagerX.BasicCollections.BaseFiles();
		boolean save = this.saveImmediately;
		this.saveImmediately = false;
		
		for(com.FileManagerX.BasicModels.BaseFile f : videos.getContent()) {
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
		if(!(item instanceof com.FileManagerX.BasicModels.BaseFile)) {
			return false;
		}
		
		com.FileManagerX.BasicModels.BaseFile video = (com.FileManagerX.BasicModels.BaseFile)item;
		int index = (
					video.getIndex() >= 0 && 
					video.getIndex() <= com.FileManagerX.Globals.Configurations.Next_FileIndex
					) ?
				this.videos.indexOf(video.getIndex()) :
				-1;
		
		if(index >= 0) {
			this.videos.getContent().set(index, video);
		} else {
			video.setIndex();
			com.FileManagerX.FileModels.Video n = new com.FileManagerX.FileModels.Video();
			n.copyValue(video);
			this.videos.add(n);
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
		
		com.FileManagerX.BasicCollections.BaseFiles videos = (com.FileManagerX.BasicCollections.BaseFiles)items;
		if(videos.size() == 0) {
			return videos;
		}
		
		com.FileManagerX.BasicCollections.BaseFiles errors = new com.FileManagerX.BasicCollections.BaseFiles();
		boolean save = this.saveImmediately;
		this.saveImmediately = false;
		
		for(com.FileManagerX.BasicModels.BaseFile f : videos.getContent()) {
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
		if(item instanceof com.FileManagerX.FileModels.Video) {
			index = ((com.FileManagerX.FileModels.Video)item).getIndex();
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
		
		this.videos.delete(index);
		return this.saveImmediately ? this.save() : true;
	}
	public synchronized long size() {
		return this.videos.size();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
