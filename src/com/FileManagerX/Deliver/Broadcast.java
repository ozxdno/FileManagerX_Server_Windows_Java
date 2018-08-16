package com.FileManagerX.Deliver;

public class Broadcast implements com.FileManagerX.Interfaces.IPublic {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private com.FileManagerX.BasicEnums.BroadcastType type;
	private boolean execute;
	private boolean arrive;
	private long depth;
	private long dest;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean setType(com.FileManagerX.BasicEnums.BroadcastType type) {
		if(type == null) {
			return false;
		}
		this.type = type;
		return true;
	}
	public boolean setNeedExecute(boolean need) {
		this.execute = need;
		return true;
	}
	public boolean setIsArrive(boolean arrive) {
		this.arrive = arrive;
		return true;
	}
	public boolean setIsArrive() {
		this.arrive |= dest == com.FileManagerX.Globals.Configurations.This_MachineIndex;
		return true;
	}
	public boolean setDepth(long depth) {
		this.depth = depth;
		return true;
	}
	public boolean setMoreDepth() {
		this.depth++;
		return true;
	}
	public boolean setLessDepth() {
		this.depth--;
		return true;
	}
	public boolean setDestMachine(long dest) {
		this.dest = dest;
		return true;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.BasicEnums.BroadcastType getType() {
		return type;
	}
	public boolean getNeedExecute() {
		return this.execute;
	}
	public boolean isArrive() {
		return this.arrive |= dest == com.FileManagerX.Globals.Configurations.This_MachineIndex;
	}
	public long getDepth() {
		return depth;
	}
	public long getDestMachine() {
		return dest;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Broadcast() {
		initThis();
	}
	private void initThis() {
		this.type = com.FileManagerX.BasicEnums.BroadcastType.TO_ONE;
		this.execute = false;
		this.arrive = false;
		this.depth = -1;
		this.dest = -1;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void clear() {
		initThis();
	}
	public String toString() {
		return this.output();
	}
	public String output() {
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config();
		c.setField(this.getClass().getSimpleName());
		c.addToBottom(this.type.toString());
		c.addToBottom(this.execute);
		c.addToBottom(this.arrive);
		c.addToBottom(this.depth);
		c.addToBottom(this.dest);
		return c.output();
	}
	public String input(String in) {
		try {
			com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config(in);
			this.type = com.FileManagerX.BasicEnums.BroadcastType.valueOf(c.fetchFirstString());
			if(!c.getIsOK()) { return null; }
			this.execute = c.fetchFirstBoolean();
			if(!c.getIsOK()) { return null; }
			this.arrive = c.fetchFirstBoolean();
			if(!c.getIsOK()) { return null; }
			this.depth = c.fetchFirstLong();
			if(!c.getIsOK()) { return null; }
			this.dest = c.fetchFirstLong();
			if(!c.getIsOK()) { return null; }
			
			return c.output();
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			return null;
		}
	}
	public void copyReference(Object o) {
		if(o instanceof Broadcast) {
			Broadcast bc = (Broadcast)o;
			this.type = bc.type;
			this.execute = bc.execute;
			this.arrive = bc.arrive;
			this.depth = bc.depth;
			this.dest = bc.dest;
		}
	}
	public void copyValue(Object o) {
		if(o instanceof Broadcast) {
			Broadcast bc = (Broadcast)o;
			this.execute = bc.execute;
			this.arrive = bc.arrive;
			this.type = bc.type;
			this.depth = bc.depth;
			this.dest = bc.dest;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean broadcast() {
		this.isArrive();
		
		if(com.FileManagerX.BasicEnums.BroadcastType.TO_ONE_ALL.equals(type)) {
			if(this.arrive) {
				this.type = com.FileManagerX.BasicEnums.BroadcastType.TO_ALL;
			}
			else {
				this.execute = false;
				return false;
			}
		}
		if(com.FileManagerX.BasicEnums.BroadcastType.TO_ONE_NEXT_DEPTH.equals(type)) {
			if(this.arrive) {
				this.type = com.FileManagerX.BasicEnums.BroadcastType.TO_NEXT_DEPTH;
			}
			else {
				this.execute = false;
				return false;
			}
		}
		if(com.FileManagerX.BasicEnums.BroadcastType.TO_ONE_N_DEPTH.equals(type)) {
			if(this.arrive) {
				this.type = com.FileManagerX.BasicEnums.BroadcastType.TO_N_DEPTH;
			}
			else {
				this.execute = false;
				return false;
			}
		}
		if(com.FileManagerX.BasicEnums.BroadcastType.TO_ONE_SET_DEPTH.equals(type)) {
			if(this.arrive) {
				this.type = com.FileManagerX.BasicEnums.BroadcastType.TO_SET_DEPTH;
			}
			else {
				this.execute = false;
				return false;
			}
		}
		if(com.FileManagerX.BasicEnums.BroadcastType.TO_ONE_OVER_DEPTH.equals(type)) {
			if(this.arrive) {
				this.type = com.FileManagerX.BasicEnums.BroadcastType.TO_OVER_DEPTH;
			}
			else {
				this.execute = false;
				return false;
			}
		}
		
		if(com.FileManagerX.BasicEnums.BroadcastType.TO_ONE.equals(type)) {
			this.execute = false;
			return false;
		}
		if(com.FileManagerX.BasicEnums.BroadcastType.TO_ALL.equals(type)) {
			this.execute = true;
			return true;
		}
		if(com.FileManagerX.BasicEnums.BroadcastType.TO_NEXT_DEPTH.equals(type)) {
			if(this.depth == 0) {
				this.type = com.FileManagerX.BasicEnums.BroadcastType.TO_SET_DEPTH;
				this.execute = true;
				return true;
			}
			else {
				this.depth--;
				this.execute = false;
				return false;
			}
		}
		if(com.FileManagerX.BasicEnums.BroadcastType.TO_SET_DEPTH.equals(type)) {
			if(this.depth == 0) {
				this.execute = true;
				return true;
			}
			else {
				this.depth--;
				this.execute = false;
				return false;
			}
		}
		if(com.FileManagerX.BasicEnums.BroadcastType.TO_N_DEPTH.equals(type)) {
			if(this.depth >= 0) {
				this.execute = true;
				return true;
			}
			else {
				this.depth--;
				this.execute = false;
				return false;
			}
		}
		if(com.FileManagerX.BasicEnums.BroadcastType.TO_OVER_DEPTH.equals(type)) {
			if(this.depth < 0) {
				this.execute = true;
				return true;
			}
			else {
				this.depth--;
				this.execute = false;
				return false;
			}
		}
		
		this.execute = false;
		return false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
