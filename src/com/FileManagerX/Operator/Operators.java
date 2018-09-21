package com.FileManagerX.Operator;

public class Operators extends com.FileManagerX.Processes.Manager<Operator> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Operators() {
		this.initThis();
	}
	private void initThis() {
		this.setKey(new KeyForIndex());
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static class KeyForIndex implements com.FileManagerX.Interfaces.ICollection.IKey {
		public Object getKey(Object item) {
			if(item instanceof Operator) {
				Operator i = (Operator)item;
				return i.getOperatorPackage().getIndex();
			}
			return null;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
