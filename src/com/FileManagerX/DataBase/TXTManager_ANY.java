package com.FileManagerX.DataBase;

public class TXTManager_ANY<T extends com.FileManagerX.Interfaces.IPublic, K>
	implements com.FileManagerX.Interfaces.IDBManager {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private com.FileManagerX.BasicModels.DataBaseInfo database;
	private com.FileManagerX.DataBase.Unit unit;
	private boolean connected;
	private boolean running;
	private String name;
	
	private com.FileManagerX.Interfaces.ICollection<T, K> content;
	private boolean saveImmediately = true;
	
	private String[] fields;
	private com.FileManagerX.BasicEnums.DataType[] types;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public synchronized boolean setContent(com.FileManagerX.Interfaces.ICollection<T, K> content) {
		if(content == null) {
			return false;
		}
		this.content = content;
		return true;
	}
	
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
	public synchronized boolean setName(String name) {
		if(name == null) {
			return false;
		}
		this.name = name;
		return true;
	}
	public synchronized boolean setFields(String[] fields) {
		if(fields == null) {
			return false;
		}
		this.fields = fields;
		return true;
	}
	public synchronized boolean setTypes(com.FileManagerX.BasicEnums.DataType[] types) {
		if(types == null) {
			return false;
		}
		this.types = types;
		return true;
	}
	public boolean setSaveImmediately(boolean saveImmediately) {
		this.saveImmediately = saveImmediately;
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
	public synchronized String getName() {
		return this.name;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public TXTManager_ANY() {
		initThis();
	}
	private void initThis() {
		this.database = null;
		this.unit = Unit.ANY;
		this.connected = false;
		this.running = false;
		this.name = "Any";
		this.content = null;
	}
	public T createT() {
		try {
			@SuppressWarnings("unchecked")
			Class<T> entityClass = (Class<T>) 
		        		((java.lang.reflect.ParameterizedType)
		        				getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		        return entityClass.newInstance();
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void reflect(Object... targets) {
		java.util.ArrayList<String> f = new java.util.ArrayList<>();
		java.util.ArrayList<com.FileManagerX.BasicEnums.DataType> t = new java.util.ArrayList<>();
		
		Object defaultTarget = this.createT();
		java.lang.reflect.Field[] fields = com.FileManagerX.Tools.Reflect.getFields(defaultTarget);
		for(int j=0; j<fields.length; j++) {
			f.add(fields[j].getName());
			t.add(com.FileManagerX.Tools.Reflect.getFieldValueType(fields[j], defaultTarget));
		}
		for(int i=0; i<targets.length; i++) {
			fields = com.FileManagerX.Tools.Reflect.getFields(targets[i]);
			for(int j=0; j<fields.length; j++) {
				f.add(fields[j].getName());
				t.add(com.FileManagerX.Tools.Reflect.getFieldValueType(fields[j], targets[i]));
			}
		}
		
		this.fields = new String[f.size()];
		this.types = new com.FileManagerX.BasicEnums.DataType[t.size()];
		
		com.FileManagerX.Tools.List2Array.toAnyArray(f, this.fields);
		com.FileManagerX.Tools.List2Array.toAnyArray(t, this.types);
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
		this.create();
		this.load();
		this.check();
		return this.connected = this.exists();
	}
	public synchronized boolean disconnect() {
		return this.connected = false;
	}
	public synchronized boolean load() {
		this.running = true;
		this.content.clear();
		com.FileManagerX.FileModels.Text txt = new com.FileManagerX.FileModels.Text();
		txt.setUrl(this.database.getUrl() + "\\" + this.name + ".txt");
		txt.loadBasicInfo();
		txt.load(true);
		for(String line : txt.getContent()) {
			T t = createT();
			com.FileManagerX.BasicModels.Config c = t.input(line);
			if(c.getIsOK()) { this.content.add(t); }
		}
		this.running = false;
		return true;
	}
	public synchronized boolean save() {
		this.running = true;
		com.FileManagerX.FileModels.Text txt = new com.FileManagerX.FileModels.Text();
		txt.setUrl(this.database.getUrl() + "\\" + this.name + ".txt");
		txt.loadBasicInfo();
		com.FileManagerX.Interfaces.IIterator<T> it = this.content.getIterator();
		while(it.hasNext()) {
			txt.getContent().add(it.getNext().output());
		}
		boolean ok = txt.save();
		this.running = false;
		return ok;
	}
	public synchronized boolean create() {
		if(this.exists()) { return true; }
		
		try {
			this.running = true;
			java.io.File file = new java.io.File(this.database.getUrl() + "\\" + this.name + ".txt");
			boolean ok = file.createNewFile();
			this.running = false;
			return ok;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.DB_OPERATE_FAILED.register("Create Failed");
			this.running = false;
			return false;
		}
	}
	public synchronized boolean delete() {
		if(!this.exists()) { return true; }
		
		try {
			this.running = true;
			java.io.File file = new java.io.File(this.database.getUrl() + "\\" + this.name + ".txt");
			boolean ok = file.delete();
			this.connected = false;
			this.running = false;
			return ok;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.DB_OPERATE_FAILED.register("Delete Failed");
			this.running = false;
			return false;
		}
	}
	public synchronized boolean exists() {
		if(this.database == null) {
			return false;
		}
		
		String url = this.database.getUrl() + "\\" + this.name + ".txt";
		java.io.File file = new java.io.File(url);
		return file.exists() && file.isFile();
	}
	public synchronized boolean clear() {
		this.content.clear();
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@SuppressWarnings("unchecked")
	public synchronized boolean querys(Object conditions, Object oResult) {
		this.running = true;
		if(oResult == null || !(oResult instanceof com.FileManagerX.Interfaces.ICollection<?, ?>)) {
			oResult = new com.FileManagerX.BasicCollections.BasicArrayList<>();
		}
		
		com.FileManagerX.Interfaces.ICollection<T, K> result = 
				(com.FileManagerX.Interfaces.ICollection<T, K>)oResult;
		result.clear();
		
		QueryConditions qcs = new QueryConditions();
		if(conditions instanceof String) {
			com.FileManagerX.BasicModels.Config c = qcs.input((String)conditions);
			if(c == null || !c.getIsOK()) {
				com.FileManagerX.BasicEnums.ErrorType.OTHERS.register("QueryConditions is Wrong", (String)conditions);
				this.running = false;
				return false;
			}
		}
		else if(conditions instanceof QueryCondition) {
			qcs.add((QueryCondition)conditions);
		}
		else if(conditions instanceof QueryConditions) {
			qcs = (QueryConditions)conditions;
		}
		else {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register("Type of conditions is Wrong");
			this.running = false;
			return false;
		}
		
		if(qcs == null || qcs.size() == 0) {
			result.copyValue(this.content);
			this.running = false;
			return true;
		}
		
		com.FileManagerX.Interfaces.IIterator<QueryCondition> it1 = qcs.getIterator();
		java.util.ArrayList<String> qfs = new java.util.ArrayList<>();
		java.util.ArrayList<com.FileManagerX.BasicEnums.DataType> qts = new java.util.ArrayList<>();
		while(it1.hasNext()) {
			QueryCondition qc = it1.getNext();
			boolean found = false;
			for(int i=0; i<this.fields.length; i++) {
				if(this.fields[i].equals(qc.getItemName())) {
					qfs.add(this.fields[i]);
					qts.add(this.types[i]);
					found = true;
					break;
				}
			}
			if(!found) { it1.remove(); }
		}
		if(qfs.size() == 0 || qts.size() == 0) {
			result.clear();
			this.running = false;
			return true;
		}
		
		com.FileManagerX.Interfaces.IIterator<T> it2 = this.content.getIterator();
		while(it2.hasNext()) {
			T target = it2.getNext();
			it1 = qcs.getIterator();
			boolean ok = true;
			int cntQ = 0;
			while(it1.hasNext()) {
				Object value = com.FileManagerX.Tools.Reflect.getField(qfs.get(cntQ), target);
				com.FileManagerX.BasicEnums.DataType type = qts.get(cntQ++);
				QueryCondition qc = it1.getNext();
				
				if(Relation.AND.equals(qc.getRelation())) {
					ok &= com.FileManagerX.BasicEnums.DataType.OTHERS.equals(type) ?
							this.satisfyUnsupport(target, value, qc) :
							satisfy(type, value, qc);
				}
				if(Relation.OR.equals(qc.getRelation())) {
					ok |= com.FileManagerX.BasicEnums.DataType.OTHERS.equals(type) ?
							this.satisfyUnsupport(target, value, qc) :
							satisfy(type, value, qc);
				}
			}
			
			if(ok) { T t = this.createT(); t.copyValue(target); result.add(t); }
		}
		
		this.running = false;
		return true;
	}
	@SuppressWarnings("unchecked")
	public synchronized boolean query(Object conditions, Object oResult) {
		this.running = true;
		T result = null;
		try {
			result = (T)oResult;
		} catch(Exception e) {
			;
		}
		if(result == null) {
			result = this.createT();
		}
		
		QueryConditions qcs = new QueryConditions();
		if(conditions instanceof String) {
			com.FileManagerX.BasicModels.Config c = qcs.input((String)conditions);
			if(c == null || !c.getIsOK()) {
				com.FileManagerX.BasicEnums.ErrorType.OTHERS.register("QueryConditions is Wrong", (String)conditions);
				this.running = false;
				return false;
			}
		}
		else if(conditions instanceof QueryCondition) {
			qcs.add((QueryCondition)conditions);
		}
		else if(conditions instanceof QueryConditions) {
			qcs = (QueryConditions)conditions;
		}
		else {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register("Type of conditions is Wrong");
			this.running = false;
			return false;
		}
		
		if(qcs == null || qcs.size() == 0) {
			if(this.content.size() == 0) {
				this.running = false;
				return false;
			}
			else {
				result.copyValue(this.content.searchByCount(0));
				this.running = false;
				return true;
			}
		}
		
		com.FileManagerX.Interfaces.IIterator<QueryCondition> it1 = qcs.getIterator();
		java.util.ArrayList<String> qfs = new java.util.ArrayList<>();
		java.util.ArrayList<com.FileManagerX.BasicEnums.DataType> qts = new java.util.ArrayList<>();
		while(it1.hasNext()) {
			QueryCondition qc = it1.getNext();
			boolean found = false;
			for(int i=0; i<this.fields.length; i++) {
				if(this.fields[i].equals(qc.getItemName())) {
					qfs.add(this.fields[i]);
					qts.add(this.types[i]);
					found = true;
					break;
				}
			}
			if(!found) { it1.remove(); }
		}
		if(qfs.size() == 0 || qts.size() == 0) {
			this.running = false;
			return false;
		}
		
		com.FileManagerX.Interfaces.IIterator<T> it2 = this.content.getIterator();
		while(it2.hasNext()) {
			T target = it2.getNext();
			it1 = qcs.getIterator();
			boolean ok = true;
			int cntQ = 0;
			while(it1.hasNext()) {
				Object value = com.FileManagerX.Tools.Reflect.getField(qfs.get(cntQ), target);
				com.FileManagerX.BasicEnums.DataType type = qts.get(cntQ++);
				QueryCondition qc = it1.getNext();
				
				if(Relation.AND.equals(qc.getRelation())) {
					ok &= com.FileManagerX.BasicEnums.DataType.OTHERS.equals(type) ?
							this.satisfyUnsupport(target, value, qc) :
							satisfy(type, value, qc);
				}
				if(Relation.OR.equals(qc.getRelation())) {
					ok |= com.FileManagerX.BasicEnums.DataType.OTHERS.equals(type) ?
							this.satisfyUnsupport(target, value, qc) :
							satisfy(type, value, qc);
				}
			}
			
			if(ok) { result.copyValue(target); this.running = false; return true; }
		}
		
		this.running = false;
		return false;
	}
	@SuppressWarnings({ "unused", "unchecked" })
	public synchronized boolean updates(Object oItems, Object oErrors) {
		this.running = true;
		if(!(oItems instanceof com.FileManagerX.Interfaces.ICollection<?, ?>)) {
			this.running = false;
			return false;
		}
		if(!(oErrors instanceof com.FileManagerX.Interfaces.ICollection<?, ?>)) {
			this.running = false;
			return false;
		}
		if(oItems == null) {
			this.running = false;
			return false;
		}
		if(oErrors == null) {
			oErrors = new com.FileManagerX.BasicCollections.BasicArrayList<>();
		}
		
		com.FileManagerX.Interfaces.ICollection<T, K> items = 
				(com.FileManagerX.Interfaces.ICollection<T, K>)oItems;
		com.FileManagerX.Interfaces.ICollection<T, K> errors = 
				(com.FileManagerX.Interfaces.ICollection<T, K>)oErrors;
		errors.clear();
		
		com.FileManagerX.Interfaces.IIterator<T> it = items.getIterator();
		boolean save = this.saveImmediately;
		this.saveImmediately = false;
		while(it.hasNext()) {
			if(!this.update(it.getNext())) { errors.add(it.getNext()); }
		}
		
		this.saveImmediately = save;
		if(this.saveImmediately) { this.save(); }
		this.running = false;
		return errors.size() == 0;
	}
	@SuppressWarnings("unchecked")
	public synchronized boolean update(Object oItem) {
		this.running = true;
		T item = null;
		try {
			item = (T)oItem;
		} catch(Exception e) {
			;
		}
		if(item == null) { this.running = false; return false; }
		
		T found = this.content.fetchByKey(this.content.getKey(item));
		if(found != null) {
			found.copyValue(item);
			this.content.add(found);
		}
		else {
			this.nextIndex(item);
			found = this.createT();
			found.copyValue(item);
			this.content.add(found);
		}
		
		if(this.saveImmediately) { this.save(); }
		this.running = false;
		return true;
	}
	@SuppressWarnings({ "unchecked", "unused" })
	public synchronized boolean removes(Object oItems, Object oErrors) {
		this.running = true;
		if(!(oItems instanceof com.FileManagerX.Interfaces.ICollection<?, ?>)) {
			this.running = false;
			return false;
		}
		if(!(oErrors instanceof com.FileManagerX.Interfaces.ICollection<?, ?>)) {
			this.running = false;
			return false;
		}
		if(oItems == null) {
			this.running = false;
			return false;
		}
		if(oErrors == null) {
			oErrors = new com.FileManagerX.BasicCollections.BasicArrayList<>();
		}
		
		com.FileManagerX.Interfaces.ICollection<T, K> items = 
				(com.FileManagerX.Interfaces.ICollection<T, K>)oItems;
		com.FileManagerX.Interfaces.ICollection<T, K> errors = 
				(com.FileManagerX.Interfaces.ICollection<T, K>)oErrors;
		errors.clear();
		
		com.FileManagerX.Interfaces.IIterator<T> it = items.getIterator();
		boolean save = this.saveImmediately;
		this.saveImmediately = false;
		while(it.hasNext()) {
			if(!this.remove(it.getNext())) { errors.add(it.getNext()); }
		}
		
		this.saveImmediately = save;
		if(this.saveImmediately) { this.save(); }
		this.running = false;
		return errors.size() == 0;
	}
	@SuppressWarnings("unchecked")
	public synchronized boolean remove(Object item) {
		this.running = true;
		K key = null;
		try {
			key = this.content.getKey((T)item);
		} catch(Exception e) {
			;
		}
		
		if(item == null) { this.running = false; return true; }
		this.content.fetchByKey(key);
		if(this.saveImmediately) { this.save(); }
		this.running = false;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public synchronized long size() {
		return this.content.size();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public synchronized boolean check() {
		return true;
	}
	public synchronized long checkIndex() {
		com.FileManagerX.Interfaces.IIterator<T> it = this.content.getIterator();
		long maxIndex = 0;
		while(it.hasNext()) {
			long index = (long)com.FileManagerX.Tools.Reflect.getField("index", it.getNext());
			if(index > maxIndex) { maxIndex = index; }
		}
		return maxIndex;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public synchronized void nextIndex(T item) {
		;
	}
	public boolean satisfyUnsupport(Object item, Object field, QueryCondition qc) {
		return false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static boolean satisfy(com.FileManagerX.BasicEnums.DataType type, Object value, QueryCondition qc) {
		if(com.FileManagerX.BasicEnums.DataType.BOOLEAN.equals(type)) {
			return satisfyBoolean((boolean)value, qc);
		}
		if(com.FileManagerX.BasicEnums.DataType.INTEGER.equals(type)) {
			return satisfyInteger((int)value, qc);
		}
		if(com.FileManagerX.BasicEnums.DataType.LONG.equals(type)) {
			return satisfyLong((long)value, qc);
		}
		if(com.FileManagerX.BasicEnums.DataType.DOUBLE.equals(type)) {
			return satisfyDouble((double)value, qc);
		}
		if(com.FileManagerX.BasicEnums.DataType.STRING.equals(type)) {
			return satisfyString((String)value, qc);
		}
		return false;
	}
	public final static boolean satisfyBoolean(boolean target, QueryCondition qc) {
		if(qc.getSign().equals(Sign.EQUAL)) {
			return qc.getValue_Boolean() == target;
		}
		if(qc.getSign().equals(Sign.NOT_EQUAL)) {
			return qc.getValue_Boolean() != target;
		}
		return false;
	}
	public final static boolean satisfyInteger(int target, QueryCondition qc) {
		if(qc.getSign().equals(Sign.EQUAL)) {
			return qc.getValue_Integer() == target;
		}
		if(qc.getSign().equals(Sign.NOT_EQUAL)) {
			return qc.getValue_Integer() != target;
		}
		if(qc.getSign().equals(Sign.GREATER)) {
			return qc.getValue_Integer() < target;
		}
		if(qc.getSign().equals(Sign.LESS)) {
			return qc.getValue_Integer() > target;
		}
		if(qc.getSign().equals(Sign.GREATER_OR_EQUAL)) {
			return qc.getValue_Integer() <= target;
		}
		if(qc.getSign().equals(Sign.LESS_OR_EQUAL)) {
			return qc.getValue_Integer() >= target;
		}
		return false;
	}
	public final static boolean satisfyLong(long target, QueryCondition qc) {
		if(qc.getSign().equals(Sign.EQUAL)) {
			return qc.getValue_Long() == target;
		}
		if(qc.getSign().equals(Sign.NOT_EQUAL)) {
			return qc.getValue_Long() != target;
		}
		if(qc.getSign().equals(Sign.GREATER)) {
			return qc.getValue_Long() < target;
		}
		if(qc.getSign().equals(Sign.LESS)) {
			return qc.getValue_Long() > target;
		}
		if(qc.getSign().equals(Sign.GREATER_OR_EQUAL)) {
			return qc.getValue_Long() <= target;
		}
		if(qc.getSign().equals(Sign.LESS_OR_EQUAL)) {
			return qc.getValue_Long() >= target;
		}
		return false;
	}
	public final static boolean satisfyDouble(double target, QueryCondition qc) {
		if(qc.getSign().equals(Sign.EQUAL)) {
			return qc.getValue_Double() == target;
		}
		if(qc.getSign().equals(Sign.NOT_EQUAL)) {
			return qc.getValue_Double() != target;
		}
		if(qc.getSign().equals(Sign.GREATER)) {
			return qc.getValue_Double() < target;
		}
		if(qc.getSign().equals(Sign.LESS)) {
			return qc.getValue_Double() > target;
		}
		if(qc.getSign().equals(Sign.GREATER_OR_EQUAL)) {
			return qc.getValue_Double() <= target;
		}
		if(qc.getSign().equals(Sign.LESS_OR_EQUAL)) {
			return qc.getValue_Double() >= target;
		}
		return false;
	}
	public final static boolean satisfyString(String target, QueryCondition qc) {
		String v = qc.getValue();

		if(qc.getSign().equals(Sign.EQUAL)) {
			try {
				return target.equals(v);
			} catch(Exception e) {
				return false;
			}
		}
		if(qc.getSign().equals(Sign.NOT_EQUAL)) {
			try {
				return !target.equals(v);
			} catch(Exception e) {
				return false;
			}
		}
		if(qc.getSign().equals(Sign.CONTAIN)) {
			try {
				return target.indexOf(v) >= 0;
			} catch(Exception e) {
				return false;
			}
		}
		return false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
