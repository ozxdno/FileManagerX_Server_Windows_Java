package com.FileManagerX.Operator;

import com.FileManagerX.BasicEnums.*;
import com.FileManagerX.BasicModels.*;
import com.FileManagerX.Globals.*;
import com.FileManagerX.Interfaces.*;

public class Operator extends Thread implements IPublic, IProcess {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private OperateType type;
	private long index;
	
	private long permitIdle;
	private long bgTicks;
	private long edTicks;
	
	private boolean shortLife;
	private boolean successed;
	private boolean restart;
	private boolean running;
	private boolean abort;
	private boolean stop;
	
	private String reason;
	private String args;
	private int finishedAmount;
	private java.util.List<String> results;
	
	private ITransport source;
	private Object content;

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setType(OperateType type) {
		if(type == null) {
			return false;
		}
		this.type = type;
		return true;
	}
	public boolean setIndex(long index) {
		this.index = index;
		return true;
	}
	public boolean setIndex() {
		if(Datas.Operators != null) {
			this.index = Datas.Operators.getNextIndex() + 1;
			Datas.Operators.setNextIndex(this.index);
		}
		return true;
	}
	
	public boolean setPermitIdle(long idle) {
		if(idle < 0) {
			return false;
		}
		this.permitIdle = idle;
		return true;
	}
	public boolean setBeginTicks(long ticks) {
		if(ticks < 0) {
			return false;
		}
		this.bgTicks = ticks;
		return true;
	}
	public boolean setBeginTicks() {
		this.bgTicks = com.FileManagerX.Tools.Time.getTicks();
		return true;
	}
	public boolean setEndTicks(long ticks) {
		if(ticks < 0) {
			return false;
		}
		this.edTicks = ticks;
		return true;
	}
	public boolean setEndTicks() {
		this.edTicks = com.FileManagerX.Tools.Time.getTicks();
		return true;
	}
	
	public boolean setIsShortLife(boolean isShortLife) {
		this.shortLife = isShortLife;
		return true;
	}
	public boolean setSuccessed(boolean successed) {
		this.successed = successed;
		return true;
	}
	public boolean setRestart(boolean restart) {
		this.restart = restart;
		return true;
	}
	public boolean setRunning(boolean running) {
		this.running = running;
		return true;
	}
	public boolean setAbort(boolean abort) {
		this.abort = abort;
		return true;
	}
	public boolean setStop(boolean stop) {
		this.stop = stop;
		return true;
	}
	
	public boolean setReason(String reason) {
		if(reason == null) {
			return false;
		}
		this.reason = reason;
		return true;
	}
	public boolean setArgs(String args) {
		if(args == null) {
			return false;
		}
		this.args = args;
		return true;
	}
	public boolean setFinishedAmount(int amount) {
		if(amount < 0) {
			return false;
		}
		this.finishedAmount = amount;
		return true;
	}
	public boolean setResults(java.util.List<String> results) {
		if(results == null) {
			return false;
		}
		this.results = results;
		return true;
	}
	
	public boolean setSource(ITransport source) {
		this.source = source;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public OperateType getType() {
		return this.type;
	}
	public long getIndex() {
		return this.index;
	}
	
	public long getPermitIdle() {
		return this.permitIdle;
	}
	public long getBeginTicks() {
		return this.bgTicks;
	}
	public long getEndTicks() {
		return this.edTicks;
	}
	
	public boolean isShortLife() {
		return this.shortLife;
	}
	public boolean isSuccessed() {
		return this.successed;
	}
	public boolean isRestart() {
		return this.restart;
	}
	public boolean isRunning() {
		return this.running;
	}
	public boolean isAbort() {
		return this.abort;
	}
	public boolean isStop() {
		return this.stop;
	}
	public boolean isFinished() {
		return !this.running;
	}
	
	public String getReason() {
		return this.reason;
	}
	public String getArgs() {
		return this.args;
	}
	public int getFinishedAmount() {
		return this.finishedAmount;
	}
	public java.util.List<String> getResults() {
		return this.results;
	}
	
	public ITransport getSource() {
		return this.source;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Operator() {
		initThis();
	}
	private void initThis() {
		
		type = OperateType.IDLE;
		index = 0;
		
		permitIdle = Configurations.TimeForOperatorIdle;
		bgTicks = 0;
		edTicks = 0;
		
		successed = false;
		restart = true;
		running = false;
		abort = true;
		stop = true;
		
		reason = "";
		args = "";
		finishedAmount = 0;
		results = new java.util.ArrayList<>();
		this.setIndex();
		
		source = null;
		content = null;
	}
	
	public void run() {
		
		com.FileManagerX.Globals.Datas.Processes.add(this);
		boolean operateOK = true;
		
		// start
		this.setBeginTicks();
		this.successed = false;
		this.running = true;
		this.abort = false;
		this.stop = false;
		
		// operate
		while(!abort) {
			if(restart) {
				restart = false;
				operateOK &= this.operate();
			}
			com.FileManagerX.Tools.Time.sleepUntil(1);
		}
		
		// end
		this.setEndTicks();
		this.running = false;
		this.successed = operateOK;
		
		// close
		if(this.source != null && this.shortLife) {
			this.source.getConnection().disconnect();
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setThis(OperateType type, long permitIdle, boolean shortLife, boolean restart,
			boolean abort, boolean stop, String args, java.util.List<String> results) {
		boolean ok = true;
		ok &= this.setType(type);
		ok &= this.setPermitIdle(permitIdle);
		ok &= this.setIsShortLife(shortLife);
		ok &= this.setRestart(restart);
		ok &= this.setAbort(abort);
		ok &= this.setStop(stop);
		ok &= this.setArgs(args);
		ok &= this.setResults(results);
		return ok;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean register() {
		return com.FileManagerX.Globals.Datas.Operators.add(this);
	}
	public void clear() {
		initThis();
	}
	public String toString() {
		return "Operator: " + this.type.toString();
	}
	public String output() {
		Config c = new Config();
		c.setField(this.getClass().getSimpleName());
		c.addToBottom(this.type.toString());
		c.addToBottom(this.index);
		c.addToBottom(this.permitIdle);
		c.addToBottom(this.bgTicks);
		c.addToBottom(this.edTicks);
		c.addToBottom(this.shortLife);
		c.addToBottom(this.successed);
		c.addToBottom(this.restart);
		c.addToBottom(this.running);
		c.addToBottom(this.abort);
		c.addToBottom(this.stop);
		c.addToBottom(this.reason);
		c.addToBottom(this.finishedAmount);
		c.addToBottom(com.FileManagerX.Coder.Encoder.Encode_String2String(this.args));
		
		java.util.List<String> temp = new java.util.ArrayList<>();
		int nextFinishedAmount = this.results.size();
		for(int i=this.finishedAmount; i<nextFinishedAmount; i++) {
			temp.add(com.FileManagerX.Coder.Encoder.Encode_String2String(this.results.get(i)));
		}
		
		String results = com.FileManagerX.Tools.String.link
				(com.FileManagerX.Tools.List2Array.toStringArray(temp), "|");
		c.addToBottom(com.FileManagerX.Coder.Encoder.Encode_String2String(results));
		
		this.finishedAmount = nextFinishedAmount;
		return c.output();
	}
	public String input(String in) {
		try {
			Config c = new Config(in);
			
			this.type = OperateType.valueOf(c.fetchFirstString());
			if(!c.getIsOK()) { return null; }
			this.index = c.fetchFirstLong();
			if(!c.getIsOK()) { return null; }
			this.permitIdle = c.fetchFirstLong();
			if(!c.getIsOK()) { return null; }
			this.bgTicks = c.fetchFirstLong();
			if(!c.getIsOK()) { return null; }
			this.edTicks = c.fetchFirstLong();
			if(!c.getIsOK()) { return null; }
			this.shortLife = c.fetchFirstBoolean();
			if(!c.getIsOK()) { return null; }
			this.successed = c.fetchFirstBoolean();
			if(!c.getIsOK()) { return null; }
			this.restart = c.fetchFirstBoolean();
			if(!c.getIsOK()) { return null; }
			this.running = c.fetchFirstBoolean();
			if(!c.getIsOK()) { return null; }
			this.abort = c.fetchFirstBoolean();
			if(!c.getIsOK()) { return null; }
			this.stop = c.fetchFirstBoolean();
			if(!c.getIsOK()) { return null; }
			this.reason = c.fetchFirstString();
			if(!c.getIsOK()) { return null; }
			this.finishedAmount = c.fetchFirstInt();
			if(!c.getIsOK()) { return null; }
			this.args = com.FileManagerX.Coder.Decoder.Decode_String2String(c.fetchFirstString());
			if(!c.getIsOK()) { return null; }
			String results = com.FileManagerX.Coder.Decoder.Decode_String2String(c.fetchFirstString());
			this.results = com.FileManagerX.Tools.Array2List.toStringList(
					com.FileManagerX.Tools.String.split(results, '|')
				);
			for(int i=0; i<this.results.size(); i++) {
				this.results.set(i, com.FileManagerX.Coder.Decoder.Decode_String2String(this.results.get(i)));
			}
			if(!c.getIsOK()) { return null; }
			
			return c.output();
			
		} catch(Exception e) {
			ErrorType.OTHERS.register(e.toString());
			return null;
		}
	}
	public void copyReference(Object o) {
		Operator op = (Operator)o;
		//this.type = op.type;
		//this.index = op.index;
		//this.bgTicks = op.bgTicks;
		//this.edTicks = op.edTicks;
		this.shortLife = op.shortLife;
		//this.successed = op.successed;
		this.restart = op.restart;
		//this.running = op.running;
		this.abort = op.abort;
		this.stop = op.stop;
		this.reason = op.reason;
		this.args = op.args;
		this.finishedAmount = op.finishedAmount;
		this.results = op.results;
	}
	public void copyValue(Object o) {
		Operator op = (Operator)o;
		//this.type = op.type;
		//this.index = op.index;
		//this.bgTicks = op.bgTicks;
		//this.edTicks = op.edTicks;
		this.shortLife = op.shortLife;
		//this.successed = op.successed;
		this.restart = op.restart;
		//this.running = op.running;
		this.abort = op.abort;
		this.stop = op.stop;
		this.reason = new String(op.reason);
		this.args = new String(op.args);
		this.finishedAmount = op.finishedAmount;
		this.results.clear();
		for(String r : op.results) {
			this.results.add(new String(r));
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean initialize(Object infos) {
		return true;
	}
	
	public boolean startProcess() {
		if(this.running) { return true; }
		this.start();
		return true;
	}
	public boolean stopProcess() {
		this.stop = true;
		return true;
	}
	public boolean continueProcess() {
		this.stop = false;
		return true;
	}
	public boolean restartProcess() {
		this.restart = true;
		if(!this.running) { this.startProcess(); }
		return true;
	}
	public boolean exitProcess() {
		this.abort = true;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private boolean operate() {
		
		boolean ok = true;
		
		if(type.equals(OperateType.INPUT)) {
			return this.Input();
		}
		if(type.equals(OperateType.OUTPUT)) {
			
		}
		
		return ok;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private boolean Input() {
		boolean ok = true;
		com.FileManagerX.Interfaces.IIOPackage iop = (com.FileManagerX.Interfaces.IIOPackage)this.content;
		if(iop == null) { iop = com.FileManagerX.Factories.CommunicatorFactory.createIOP(); }
		
		if(source instanceof com.FileManagerX.Commands.Operator) {
			com.FileManagerX.Interfaces.IIOPackage iop2 = com.FileManagerX.Factories.CommunicatorFactory.createIOP();
			iop2.input(args);
			iop.copyReference(iop2);
		}
		if(source instanceof com.FileManagerX.Replies.Operator) {
			com.FileManagerX.Interfaces.IIOPackage iop2 = com.FileManagerX.Factories.CommunicatorFactory.createIOP();
			iop2.input(args);
			iop.copyReference(iop2);
		}
		if(source instanceof com.FileManagerX.Commands.Input) { 
			com.FileManagerX.Interfaces.IIOPackage iop2 = ((com.FileManagerX.Commands.Input)source).getIOPackage();
			iop.copyReference(iop2);
		}
		if(source instanceof com.FileManagerX.Replies.Input) { 
			com.FileManagerX.Interfaces.IIOPackage iop2 = ((com.FileManagerX.Replies.Input)source).getIOPackage();
			iop.copyReference(iop2);
		}
		
		if(source instanceof ICommand) {
			
			boolean nextReturnOperate = true;
			iop.setType(IOPType.READ_DEST);
			iop.createStream();
			
			// No Set MaxFlow
			while(!iop.isFinished()) {
				
				ok = iop.execute();
				nextReturnOperate = !nextReturnOperate;
				
				if(nextReturnOperate) {
					com.FileManagerX.Replies.Operator rep = new com.FileManagerX.Replies.Operator();
					com.FileManagerX.Operator.Operator op = rep.getOperator();
					op.copyReference(this);
					op.args = iop.output();
					rep.setThis(op, source.getConnection());
					rep.send();
				}
				else {
					com.FileManagerX.Replies.Input rep = new com.FileManagerX.Replies.Input();
					rep.setThis(iop, index, source.getConnection());
					rep.send();
				}
			}
			
			return ok;
		}
		if(source instanceof IReply) { 

			iop.setType(IOPType.WRITE_SOUR);
			ok = iop.execute();
			
			return ok;
		}
		
		return ok;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}

