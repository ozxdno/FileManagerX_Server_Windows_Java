package com.FileManagerX.Transport;


public class Transports extends com.FileManagerX.BasicCollections.BasicCollection
	<com.FileManagerX.Interfaces.ITransport> {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Transports() {
		this.initThis();
	}
	private void initThis() {
		this.setContent(new com.FileManagerX.BasicCollections.BasicArrayList<>());
		this.setKey(new KeyForIndex());
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.Interfaces.ITransport createT() {
		return new Transport();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static class KeyForIndex implements com.FileManagerX.Interfaces.ICollection.IKey {
		public Object getKey(Object item) {
			if(item instanceof com.FileManagerX.Interfaces.ITransport) {
				com.FileManagerX.Interfaces.ITransport t = (com.FileManagerX.Interfaces.ITransport)item;
				return t.getBasicMessagePackage().getIndex();
			}
			return null;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
