package com.FileManagerX.Operator;

import com.FileManagerX.BasicEnums.*;
import com.FileManagerX.BasicModels.*;
import com.FileManagerX.Interfaces.*;

public class Operator extends com.FileManagerX.Processes.BasicProcess implements IPublic {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private OperateType type;
	private long index;
	
	private boolean exitConnectionAtOnce;
	private boolean exitOperatorAtOnce;

	private String reason;
	private String args;
	private int finishedAmount;
	private int remainAmount;
	private java.util.List<String> results;
	
	private ITransport source;
	private Object content;

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setType(OperateType type) {
		if(type == null) {
			return false;
		}
		this.type = type;
		this.setRunnable(this.getRunImpl());
		this.setName();
		return true;
	}
	public boolean setIndex(long index) {
		this.index = index;
		return true;
	}
	public boolean setIndex() {
		this.index = ++com.FileManagerX.Globals.Configurations.Next_OperatorIndex;
		return true;
	}
	
	public boolean setIsExitConnectionAtOnce(boolean exitAtOnce) {
		this.exitConnectionAtOnce = exitAtOnce;
		return true;
	}
	public boolean setIsExitOperatorAtOnce(boolean exitAtOnce) {
		this.exitOperatorAtOnce = exitAtOnce;
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
	public boolean setRemainAmount(int amount) {
		if(amount < 0) {
			return false;
		}
		this.remainAmount = amount;
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
	public boolean setContent(Object content) {
		this.content = content;
		return true;
	}
	public boolean setName() {
		return super.setName("Operator: " + this.type.toString());
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public OperateType getType() {
		return this.type;
	}
	public long getIndex() {
		return this.index;
	}
	
	public boolean isExitConnectionAtOnce() {
		return this.exitConnectionAtOnce;
	}
	public boolean isExitOperatorAtOnce() {
		return this.exitOperatorAtOnce;
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
	public int getRemainAmount() {
		return this.remainAmount;
	}
	public java.util.List<String> getResults() {
		return this.results;
	}
	
	public ITransport getSource() {
		return this.source;
	}
	public Object getContent() {
		return this.content;
	}
	
	public com.FileManagerX.Processes.BasicProcess.Runnable getRunImpl() {
		
		if(com.FileManagerX.BasicEnums.OperateType.INPUT.equals(this.type)) {
			return new RunInput();
		}
		if(com.FileManagerX.BasicEnums.OperateType.OUTPUT.equals(this.type)) {
			return new RunOutput();
		}
		
		return new RunIdle();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Operator() {
		initThis();
	}
	private void initThis() {
		type = OperateType.IDLE;
		index = 0;
		
		exitConnectionAtOnce = false;
		exitOperatorAtOnce = true;
		
		reason = "";
		args = "";
		finishedAmount = 0;
		remainAmount = 0;
		results = new java.util.ArrayList<>();
		this.setIndex();
		
		this.setPermitIdle(com.FileManagerX.Globals.Configurations.TimeForPermitIdle_Operator);
		
		source = null;
		content = null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public class RunIdle implements com.FileManagerX.Processes.BasicProcess.Runnable {
		public String run() {
			return null;
		}
	}
	public class RunInput implements com.FileManagerX.Processes.BasicProcess.Runnable {
		public String run() {
			com.FileManagerX.Interfaces.IIOPackage iop = (com.FileManagerX.Interfaces.IIOPackage)content;
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
				
				while(!iop.isFinished()) {
					
					iop.execute();
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
			}
			if(source instanceof IReply) { 
				iop.setType(IOPType.WRITE_SOUR);
				iop.execute();
			}
			
			if(iop.isFinished() && Operator.this.exitOperatorAtOnce) { 
				Operator.this.exitProcess();
			}
			if(iop.isFinished() && Operator.this.exitConnectionAtOnce) { 
				Operator.this.source.getConnection().exitProcess();
			}
			return null;
		}
	}
	public class RunOutput implements com.FileManagerX.Processes.BasicProcess.Runnable {
		public String run() {
			return null;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
		c.addToBottom(this.exitConnectionAtOnce);
		c.addToBottom(this.exitOperatorAtOnce);
		c.addToBottom(this.getPermitIdle());
		c.addToBottom(this.getBegin());
		c.addToBottom(this.getEnd());
		c.addToBottom(this.isFinished());
		c.addToBottom(this.isRunning());
		c.addToBottom(this.isRestart());
		c.addToBottom(this.isAbort());
		c.addToBottom(this.isStop());
		c.addToBottom(this.reason);
		c.addToBottom(this.finishedAmount);
		c.addToBottom(this.results.size() - this.finishedAmount);
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
			this.exitConnectionAtOnce = c.fetchFirstBoolean();
			if(!c.getIsOK()) { return null; }
			this.exitOperatorAtOnce = c.fetchFirstBoolean();
			if(!c.getIsOK()) { return null; }
			this.setPermitIdle(c.fetchFirstLong());
			if(!c.getIsOK()) { return null; }
			this.setBegin(c.fetchFirstLong());
			if(!c.getIsOK()) { return null; }
			this.setEnd(c.fetchFirstLong());
			if(!c.getIsOK()) { return null; }
			c.fetchFirstBoolean(); // finished
			if(!c.getIsOK()) { return null; }
			c.fetchFirstBoolean(); // running;
			if(!c.getIsOK()) { return null; }
			this.setIsRestart(c.fetchFirstBoolean());
			if(!c.getIsOK()) { return null; }
			this.setIsAbort(c.fetchFirstBoolean());
			if(!c.getIsOK()) { return null; }
			this.setIsStop(c.fetchFirstBoolean());
			if(!c.getIsOK()) { return null; }
			this.reason = c.fetchFirstString();
			if(!c.getIsOK()) { return null; }
			this.finishedAmount = c.fetchFirstInt();
			if(!c.getIsOK()) { return null; }
			this.remainAmount = c.fetchFirstInt();
			if(!c.getIsOK()) { return null; }
			this.args = com.FileManagerX.Coder.Decoder.Decode_String2String(c.fetchFirstString());
			if(!c.getIsOK()) { return null; }
			
			String results = com.FileManagerX.Coder.Decoder.Decode_String2String(c.fetchFirstString());
			if(!c.getIsOK()) { return null; }
			
			java.util.ArrayList<String> resList = com.FileManagerX.Tools.Array2List.toStringList(
					com.FileManagerX.Tools.String.split(results, '|')
				);
			for(int i=0; i<resList.size(); i++) {
				resList.set(i, com.FileManagerX.Coder.Decoder.Decode_String2String(resList.get(i)));
			}
			this.results.addAll(resList);
			
			return c.output();
			
		} catch(Exception e) {
			ErrorType.OTHERS.register(e.toString());
			return null;
		}
	}
	public void copyReference(Object o) {
		if(o instanceof Operator) {
			Operator op = (Operator)o;
			this.exitConnectionAtOnce = op.exitConnectionAtOnce;
			this.exitOperatorAtOnce = op.exitOperatorAtOnce;
			this.reason = op.reason;
			this.args = op.args;
			this.finishedAmount = op.finishedAmount;
			this.remainAmount = op.remainAmount;
			this.results.addAll(op.results);
			
			this.setIsRestart(op.isRestart());
			this.setIsAbort(op.isAbort());
			this.setIsStop(op.isStop());
		}
	}
	public void copyValue(Object o) {
		if(o instanceof Operator) {
			Operator op = (Operator)o;
			this.exitConnectionAtOnce = op.exitConnectionAtOnce;
			this.exitOperatorAtOnce = op.exitOperatorAtOnce;
			this.reason = op.reason;
			this.args = op.args;
			this.finishedAmount = op.finishedAmount;
			this.remainAmount = op.remainAmount;
			this.results.addAll(op.results);
			
			this.setIsRestart(op.isRestart());
			this.setIsAbort(op.isAbort());
			this.setIsStop(op.isStop());
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}

