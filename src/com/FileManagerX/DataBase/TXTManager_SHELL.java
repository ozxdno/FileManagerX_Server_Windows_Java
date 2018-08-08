package com.FileManagerX.DataBase;

public class TXTManager_SHELL implements com.FileManagerX.Interfaces.IDBManager {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private com.FileManagerX.BasicModels.DataBaseInfo database;
	private com.FileManagerX.DataBase.Unit unit;
	private boolean running;
	private String name;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public synchronized boolean setDBInfo(com.FileManagerX.BasicModels.DataBaseInfo database) {
		if(database == null) {
			return false;
		}
		this.database = database;
		this.name = database.getUrl();
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

	public TXTManager_SHELL() {
		initThis();
	}
	private void initThis() {
		this.database = null;
		this.unit = Unit.SHELL;
		this.running = false;
		this.name = "";
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public synchronized boolean isConnected() {
		return this.running;
	}
	public synchronized boolean connect() {
		this.name = this.database.getUrl();
		return this.running = this.exists();
	}
	public synchronized boolean disconnect() {
		return this.running = false;
	}
	public synchronized boolean load() {
		return true;
	}
	public synchronized boolean save() {
		return true;
	}
	public synchronized boolean create() {
		if(this.exists()) { return true; }
		
		try {
			java.io.File folder = new java.io.File(this.name);
			return folder.mkdirs();
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.DB_OPERATE_FAILED.register("Create DataBase Failed");
			return false;
		}
	}
	public synchronized boolean delete() {
		if(!this.exists()) { return true; }
		
		try {
			com.FileManagerX.Interfaces.IDepotManager dm = this.getDBInfo().getDepotInfo().getManager();
			dm.setUncheck(true);
			return dm.deleteDirectory(this.name);
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.DB_OPERATE_FAILED.register("Delete DataBase Failed");
			return false;
		}
	}
	public synchronized boolean exists() {
		if(this.database == null) {
			return false;
		}
		
		java.io.File folder = new java.io.File(this.name);
		return folder.exists() && folder.isDirectory();
	}
	public synchronized boolean clear() {
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public synchronized Object querys(Object conditions) {
		return null;
	}
	public synchronized Object query(Object conditions) {
		return null;
	}
	public synchronized Object updates(Object items) {
		return null;
	}
	public synchronized boolean update(Object item) {
		return false;
	}
	public synchronized Object removes(Object items) {
		return null;
	}
	public synchronized boolean remove(Object item) {
		return false;
	}
	public synchronized long size() {
		return 1;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static boolean satisfyBoolean(boolean target, QueryCondition qc) {
		if(qc.getSign().equals(Sign.EQUAL)) {
			try {
				return target == (Integer.parseInt(qc.getValue()) != 0);
			} catch(Exception e) {
				return false;
			}
		}
		if(qc.getSign().equals(Sign.NOT_EQUAL)) {
			try {
				return target != (Integer.parseInt(qc.getValue()) != 0);
			} catch(Exception e) {
				return false;
			}
		}
		return false;
	}
	public final static boolean satisfyInteger(int target, QueryCondition qc) {
		if(qc.getSign().equals(Sign.EQUAL)) {
			try {
				return target == Integer.parseInt(qc.getValue());
			} catch(Exception e) {
				return false;
			}
		}
		if(qc.getSign().equals(Sign.NOT_EQUAL)) {
			try {
				return target != Integer.parseInt(qc.getValue());
			} catch(Exception e) {
				return false;
			}
		}
		if(qc.getSign().equals(Sign.GREATER)) {
			try {
				return target > Integer.parseInt(qc.getValue());
			} catch(Exception e) {
				return false;
			}
		}
		if(qc.getSign().equals(Sign.LESS)) {
			try {
				return target < Integer.parseInt(qc.getValue());
			} catch(Exception e) {
				return false;
			}
		}
		if(qc.getSign().equals(Sign.GREATER_OR_EQUAL)) {
			try {
				return target >= Integer.parseInt(qc.getValue());
			} catch(Exception e) {
				return false;
			}
		}
		if(qc.getSign().equals(Sign.LESS_OR_EQUAL)) {
			try {
				return target <= Integer.parseInt(qc.getValue());
			} catch(Exception e) {
				return false;
			}
		}
		return false;
	}
	public final static boolean satisfyLong(long target, QueryCondition qc) {
		if(qc.getSign().equals(Sign.EQUAL)) {
			try {
				return target == Long.parseLong(qc.getValue());
			} catch(Exception e) {
				return false;
			}
		}
		if(qc.getSign().equals(Sign.GREATER)) {
			try {
				return target > Long.parseLong(qc.getValue());
			} catch(Exception e) {
				return false;
			}
		}
		if(qc.getSign().equals(Sign.LESS)) {
			try {
				return target < Long.parseLong(qc.getValue());
			} catch(Exception e) {
				return false;
			}
		}
		if(qc.getSign().equals(Sign.GREATER_OR_EQUAL)) {
			try {
				return target >= Long.parseLong(qc.getValue());
			} catch(Exception e) {
				return false;
			}
		}
		if(qc.getSign().equals(Sign.LESS_OR_EQUAL)) {
			try {
				return target <= Long.parseLong(qc.getValue());
			} catch(Exception e) {
				return false;
			}
		}
		return false;
	}
	public final static boolean satisfyDouble(double target, QueryCondition qc) {
		if(qc.getSign().equals(Sign.EQUAL)) {
			try {
				return target == Double.parseDouble(qc.getValue());
			} catch(Exception e) {
				return false;
			}
		}
		if(qc.getSign().equals(Sign.GREATER)) {
			try {
				return target > Double.parseDouble(qc.getValue());
			} catch(Exception e) {
				return false;
			}
		}
		if(qc.getSign().equals(Sign.LESS)) {
			try {
				return target < Double.parseDouble(qc.getValue());
			} catch(Exception e) {
				return false;
			}
		}
		if(qc.getSign().equals(Sign.GREATER_OR_EQUAL)) {
			try {
				return target >= Double.parseDouble(qc.getValue());
			} catch(Exception e) {
				return false;
			}
		}
		if(qc.getSign().equals(Sign.LESS_OR_EQUAL)) {
			try {
				return target <= Double.parseDouble(qc.getValue());
			} catch(Exception e) {
				return false;
			}
		}
		return false;
	}
	public final static boolean satisfyString(String target, QueryCondition qc) {
		String v = qc.getValue();
		if(v != null && v.length() > 0 && v.charAt(0) == '\'' && v.charAt(v.length()-1) == '\'') {
			v = v.substring(0, v.length()-1);
			v = v.substring(1);
		}
		
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
