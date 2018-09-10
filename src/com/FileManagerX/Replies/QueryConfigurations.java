package com.FileManagerX.Replies;

import com.FileManagerX.Globals.Configurations;
import com.FileManagerX.BasicEnums.*;

public class QueryConfigurations extends BaseReply {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private long Next_FileIndex = 0;
	private long Next_MachineIndex = 0;
	private long Next_UserIndex = 0;
	private long Next_DepotIndex = 0;
	private long Next_DataBaseIndex = 0;
	
	private long This_MachineIndex = 0;
	private long This_UserIndex = 0;
	
	private long Server_MachineIndex = 0;
	private long Server_UserIndex = 0;
	
	private NetType netType;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setNext_FileIndex(long nextIndex) {
		if(nextIndex < 0) {
			return false;
		}
		this.Next_FileIndex = nextIndex;
		return true;
	}
	public boolean setNext_MachineIndex(long nextIndex) {
		if(nextIndex < 0) {
			return false;
		}
		this.Next_MachineIndex = nextIndex;
		return true;
	}
	public boolean setNext_UserIndex(long nextIndex) {
		if(nextIndex < 0) {
			return false;
		}
		this.Next_UserIndex = nextIndex;
		return true;
	}
	public boolean setNext_DepotIndex(long nextIndex) {
		if(nextIndex < 0) {
			return false;
		}
		this.Next_DepotIndex = nextIndex;
		return true;
	}
	public boolean setNext_DataBaseIndex(long nextIndex) {
		if(nextIndex < 0) {
			return false;
		}
		this.Next_DataBaseIndex = nextIndex;
		return true;
	}
	
	public boolean setThis_MachineIndex(long thisIndex) {
		if(thisIndex < 0) {
			return false;
		}
		this.This_MachineIndex = thisIndex;
		return true;
	}
	public boolean setThis_UserIndex(long thisIndex) {
		if(thisIndex < 0) {
			return false;
		}
		this.This_UserIndex = thisIndex;
		return true;
	}
	
	public boolean setServer_MachineIndex(long serverIndex) {
		if(serverIndex < 0) {
			return false;
		}
		this.Server_MachineIndex = serverIndex;
		return true;
	}
	public boolean setServer_UserIndex(long serverIndex) {
		if(serverIndex < 0) {
			return false;
		}
		this.Server_UserIndex = serverIndex;
		return true;
	}
	
	public boolean setNetType(NetType type) {
		if(type == null) {
			return false;
		}
		this.netType = type;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public long getNext_FileIndex() {
		return this.Next_FileIndex;
	}
	public long getNext_MachineIndex() {
		return this.Next_MachineIndex;
	}
	public long getNext_UserIndex() {
		return this.Next_UserIndex;
	}
	public long getNext_DepotIndex() {
		return this.Next_DepotIndex;
	}
	public long getNext_DataBaseIndex() {
		return this.Next_DataBaseIndex;
	}
	
	public long getThis_MachineIndex() {
		return this.This_MachineIndex;
	}
	public long getThis_UserIndex() {
		return this.This_UserIndex;
	}
	
	public long getServer_MachineIndex() {
		return this.Server_MachineIndex;
	}
	public long getServer_UserIndex() {
		return this.Server_UserIndex;
	}
	
	public NetType getNetType() {
		return this.netType;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public QueryConfigurations() {
		initThis();
	}
	private void initThis() {
		Next_FileIndex = 0;
		Next_MachineIndex = 0;
		Next_UserIndex = 0;
		Next_DepotIndex = 0;
		Next_DataBaseIndex = 0;
		
		This_MachineIndex = 0;
		This_UserIndex = 0;
		
		Server_MachineIndex = 0;
		Server_UserIndex = 0;
		
		netType = NetType.ONE_IN_WWW;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setThis() {
		boolean ok = true;
		ok &= this.setNext_MachineIndex(com.FileManagerX.Globals.Configurations.Next_MachineIndex);
		ok &= this.setNext_MachineIndex(Configurations.Next_MachineIndex);
		ok &= this.setNext_UserIndex(Configurations.Next_UserIndex);
		ok &= this.setNext_DepotIndex(Configurations.Next_DepotIndex);
		ok &= this.setNext_DataBaseIndex(Configurations.Next_DataBaseIndex);
		ok &= this.setThis_MachineIndex(Configurations.This_MachineIndex);
		ok &= this.setThis_UserIndex(Configurations.This_UserIndex);
		ok &= this.setServer_MachineIndex(Configurations.Server_MachineIndex);
		ok &= this.setServer_UserIndex(Configurations.Server_UserIndex);
		ok &= this.setNetType(Configurations.NetType);
		return ok;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void clear() {
		super.clear();
		initThis();
	}
	public String toString() {
		return this.output();
	}
	public com.FileManagerX.BasicModels.Config toConfig() {
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config();
		c.setField(this.getClass().getSimpleName());
		c.addToBottom(super.toConfig());
		c.addToBottom(this.Next_FileIndex);
		c.addToBottom(this.Next_MachineIndex);
		c.addToBottom(this.Next_UserIndex);
		c.addToBottom(this.Next_DepotIndex);
		c.addToBottom(this.Next_DataBaseIndex);
		c.addToBottom(this.This_MachineIndex);
		c.addToBottom(this.This_UserIndex);
		c.addToBottom(this.Server_MachineIndex);
		c.addToBottom(this.Server_UserIndex);
		c.addToBottom(this.netType.toString());
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
			c = super.input(c);
			if(!c.getIsOK()) { return c; }
			this.Next_FileIndex = c.fetchFirstLong();
			if(!c.getIsOK()) { return c; }
			this.Next_MachineIndex = c.fetchFirstLong();
			if(!c.getIsOK()) { return c; }
			this.Next_UserIndex = c.fetchFirstLong();
			if(!c.getIsOK()) { return c; }
			this.Next_DepotIndex = c.fetchFirstLong();
			if(!c.getIsOK()) { return c; }
			this.Next_DataBaseIndex = c.fetchFirstLong();
			if(!c.getIsOK()) { return c; }
			this.This_MachineIndex = c.fetchFirstLong();
			if(!c.getIsOK()) { return c; }
			this.This_UserIndex = c.fetchFirstLong();
			if(!c.getIsOK()) { return c; }
			this.Server_MachineIndex = c.fetchFirstLong();
			if(!c.getIsOK()) { return c; }
			this.Server_UserIndex = c.fetchFirstLong();
			if(!c.getIsOK()) { return c; }
			this.netType = NetType.valueOf(c.fetchFirstString());
			if(!c.getIsOK()) { return c; }
			return c;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			c.setIsOK(false);
			return c;
		}
	}
	public void copyReference(Object o) {
		super.copyReference(o);
		QueryConfigurations qc = (QueryConfigurations)o;
		this.Next_FileIndex = qc.Next_FileIndex;
		this.Next_DataBaseIndex = qc.Next_DataBaseIndex;
		this.Next_DepotIndex = qc.Next_DepotIndex;
		this.Next_MachineIndex = qc.Next_MachineIndex;
		this.Next_UserIndex = qc.Next_UserIndex;
		this.Server_MachineIndex = qc.Server_MachineIndex;
		this.Server_UserIndex = qc.Server_UserIndex;
		this.This_MachineIndex = qc.This_MachineIndex;
		this.This_UserIndex = qc.This_UserIndex;
		this.netType = qc.netType;
	}
	public void copyValue(Object o) {
		super.copyValue(o);
		QueryConfigurations qc = (QueryConfigurations)o;
		this.Next_FileIndex = qc.Next_FileIndex;
		this.Next_DataBaseIndex = qc.Next_DataBaseIndex;
		this.Next_DepotIndex = qc.Next_DepotIndex;
		this.Next_MachineIndex = qc.Next_MachineIndex;
		this.Next_UserIndex = qc.Next_UserIndex;
		this.Server_MachineIndex = qc.Server_MachineIndex;
		this.Server_UserIndex = qc.Server_UserIndex;
		this.This_MachineIndex = qc.This_MachineIndex;
		this.This_UserIndex = qc.This_UserIndex;
		this.netType = qc.netType;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
