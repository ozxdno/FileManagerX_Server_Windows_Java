package com.FileManagerX.Communicator;

public class OperatorPackage implements com.FileManagerX.Interfaces.IOperatorPackage {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private boolean start;
	private boolean query;
	private long index;
	
	private boolean exitConnection;
	private boolean exitOperator;
	private boolean stopOperator;
	private boolean restartOperator;
	
	private int finished;
	private int remain;
	private int require;
	private java.util.List<String> results;
	
	private String args;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setIsStart(boolean start) {
		this.start = start;
		this.query = !this.start;
		return true;
	}
	public boolean setIsQuery(boolean query) {
		this.query = query;
		this.start = !this.query;
		return true;
	}
	public boolean setIndex(long index) {
		this.index = index;
		return true;
	}
	public boolean setIndex() {
		this.index = com.FileManagerX.Globals.Configurations.Next_OperatorIndex();
		return true;
	}
	
	public boolean setExitConnection(boolean exit) {
		this.exitConnection = exit;
		return true;
	}
	public boolean setExitOperator(boolean exit) {
		this.exitOperator = exit;
		return true;
	}
	public boolean setStopOperator(boolean stop) {
		this.stopOperator = stop;
		return true;
	}
	public boolean setRestartOperator(boolean restart) {
		this.restartOperator = restart;
		return true;
	}
	
	public boolean setFinishedAmount(int amount) {
		if(amount < 0) { amount = 0; }
		this.finished = amount;
		return true;
	}
	public boolean setRemainAmount(int amount) {
		if(amount < 0) { amount = 0; }
		this.remain = amount;
		return true;
	}
	public boolean setRequireAmount(int amount) {
		if(amount < 0) { amount = 0; }
		this.require = amount;
		return true;
	}
	public boolean setResults(java.util.List<String> results) {
		if(results == null) { return false; }
		this.results = results;
		return true;
	}
	
	public boolean setArgs(String args) {
		if(args == null) { return false; }
		this.args = args;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean isStart() {
		return this.start;
	}
	public boolean isQuery() {
		return this.query;
	}
	public long getIndex() {
		return this.index;
	}
	
	public boolean isExitConnection() {
		return this.exitConnection;
	}
	public boolean isExitOperator() {
		return this.exitOperator;
	}
	public boolean isStopOperator() {
		return this.stopOperator;
	}
	public boolean isRestartOperator() {
		return this.restartOperator;
	}
	
	public int getFinishedAmount() {
		return this.finished;
	}
	public int getRemainAmount() {
		return this.remain;
	}
	public int getRequireAmount() {
		return this.require;
	}
	public java.util.List<String> getResults() {
		return this.results;
	}
	
	public String getArgs() {
		return this.args;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public OperatorPackage() {
		this.initThis();
	}
	private void initThis() {
		this.start = false;
		this.query = false;
		this.index = -1;
		
		this.exitConnection = false;
		this.exitOperator = false;
		this.stopOperator = false;
		this.restartOperator = false;
		
		this.finished = 0;
		this.remain = 0;
		this.require = 0;
		
		this.results = new java.util.LinkedList<>();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void clear() {
		this.initThis();
	}
	public com.FileManagerX.BasicModels.Config toConfig() {
		com.FileManagerX.BasicModels.Config c = new com.FileManagerX.BasicModels.Config();
		c.addToBottom(this.start);
		c.addToBottom(this.query);
		c.addToBottom(this.index);
		c.addToBottom(this.exitConnection);
		c.addToBottom(this.exitOperator);
		c.addToBottom(this.stopOperator);
		c.addToBottom(this.restartOperator);
		c.addToBottom(this.finished);
		
		this.remain = this.results.size() - this.finished;
		this.require = this.require > this.remain ? this.remain : this.require;
		c.addToBottom(this.remain);
		c.addToBottom(this.require);
		for(int i=0; i<this.require; i++) { c.addToBottom_Encode(this.results.get(i+finished)); }
		c.addToBottom_Encode(this.args);
		
		this.finished += this.require;
		return c;
	}
	public String toString() {
		return this.output();
	}
	public String output() {
		return this.toConfig().output();
	}
	public com.FileManagerX.BasicModels.Config input(String in) {
		return this.input(new com.FileManagerX.BasicModels.Config(in));
	}
	public com.FileManagerX.BasicModels.Config input(com.FileManagerX.BasicModels.Config c) {
		if(c == null) { return null; }
		
		if(!c.getIsOK()) {return c; }
		this.start = c.fetchFirstBoolean();
		if(!c.getIsOK()) {return c; }
		this.query = c.fetchFirstBoolean();
		if(!c.getIsOK()) {return c; }
		this.index = c.fetchFirstLong();
		if(!c.getIsOK()) {return c; }
		this.exitConnection = c.fetchFirstBoolean();
		if(!c.getIsOK()) {return c; }
		this.exitOperator = c.fetchFirstBoolean();
		if(!c.getIsOK()) {return c; }
		this.stopOperator = c.fetchFirstBoolean();
		if(!c.getIsOK()) {return c; }
		this.restartOperator = c.fetchFirstBoolean();
		if(!c.getIsOK()) {return c; }
		this.finished = c.fetchFirstInt();
		if(!c.getIsOK()) {return c; }
		this.remain = c.fetchFirstInt();
		if(!c.getIsOK()) {return c; }
		this.require = c.fetchFirstInt();
		if(!c.getIsOK()) {return c; }
		
		for(int i=0; i<this.require; i++) { 
			String ires = c.fetchFirstString_Decode();
			if(!c.getIsOK()) {return c; }
			this.results.add(ires);
		}
		
		this.args = c.fetchFirstString_Decode();
		if(!c.getIsOK()) {return c; }
		return c;
	}
	public void copyReference(Object o) {
		if(o instanceof com.FileManagerX.Interfaces.IOperatorPackage) {
			
		}
	}
	public void copyValue(Object o) {
		if(o instanceof com.FileManagerX.Interfaces.IOperatorPackage) {
			
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
