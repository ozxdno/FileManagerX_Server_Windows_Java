package com.FileManagerX.Deliver;

public class Broadcast implements com.FileManagerX.Interfaces.IPublic {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private com.FileManagerX.BasicEnums.BroadcastType type;
	private boolean execute;
	private boolean arrive;
	private long depth;
	private long dest;
	private long gate;
	
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
	public com.FileManagerX.BasicModels.Config toConfig() {
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config();
		c.setField(this.getClass().getSimpleName());
		c.addToBottom(this.type.toString());
		c.addToBottom(this.execute);
		c.addToBottom(this.arrive);
		c.addToBottom(this.depth);
		c.addToBottom(this.dest);
		return c;
	}
	public String output() {
		return this.toConfig().output();
	}
	public com.FileManagerX.BasicModels.Config input(String in) {
		return this.input(new com.FileManagerX.BasicModels.Config(in));
	}
	public com.FileManagerX.BasicModels.Config input(com.FileManagerX.BasicModels.Config c) {
		if(c == null) { return null; }
		try {
			if(!c.getIsOK()) { return c; }
			this.type = com.FileManagerX.BasicEnums.BroadcastType.valueOf(c.fetchFirstString());
			if(!c.getIsOK()) { return c; }
			this.execute = c.fetchFirstBoolean();
			if(!c.getIsOK()) { return c; }
			this.arrive = c.fetchFirstBoolean();
			if(!c.getIsOK()) { return c; }
			this.depth = c.fetchFirstLong();
			if(!c.getIsOK()) { return c; }
			this.dest = c.fetchFirstLong();
			if(!c.getIsOK()) { return c; }
			
			return c;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			e.printStackTrace();
			c.setIsOK(false);
			return c;
		}
	}
	public void copyReference(Object o) {
		if(o == null) { return; }
		if(o instanceof Broadcast) {
			Broadcast bc = (Broadcast)o;
			this.type = bc.type;
			this.execute = bc.execute;
			this.arrive = bc.arrive;
			this.depth = bc.depth;
			this.dest = bc.dest;
			return;
		}
	}
	public void copyValue(Object o) {
		if(o == null) { return; }
		if(o instanceof Broadcast) {
			Broadcast bc = (Broadcast)o;
			this.execute = bc.execute;
			this.arrive = bc.arrive;
			this.type = bc.type;
			this.depth = bc.depth;
			this.dest = bc.dest;
			return;
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
