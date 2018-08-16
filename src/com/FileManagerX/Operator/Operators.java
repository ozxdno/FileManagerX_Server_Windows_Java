package com.FileManagerX.Operator;

public class Operators extends com.FileManagerX.Processes.Manager<Operator> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Operators() {
		initThis();
	}
	private void initThis() {
		;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public int indexOfOperatorIndex(long index) {
		int i = -1;
		for(Operator op : this.getContent()) {
			i++;
			if(op.getIndex() == index) {
				return i;
			}
		}
		return -1;
	}
	public Operator searchOperatorIndex(long index) {
		for(Operator op : this.getContent()) {
			if(op.getIndex() == index) {
				return op;
			}
		}
		return null;
	}
	public Operator fetchOperatorIndex(long index) {
		java.util.Iterator<Operator> it = this.getContent().iterator();
		while(it.hasNext()) {
			Operator op = it.next();
			if(op.getIndex() == index) {
				it.remove();
				return op;
			}
		}
		return null;
	}
	public void deleteOperatorIndex(long index) {
		Operator op = this.fetchOperatorIndex(index);
		if(op != null) {
			op.exitProcess();
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
