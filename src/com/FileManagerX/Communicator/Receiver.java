package com.FileManagerX.Communicator;

public class Receiver {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private java.util.HashMap<Long, com.FileManagerX.Interfaces.IReply> content;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Receiver() {
		initThis();
	}
	private void initThis() {
		content = new java.util.HashMap<>();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public synchronized boolean removeIdleReplies() {
		java.util.Iterator<java.util.Map.Entry<Long, com.FileManagerX.Interfaces.IReply>> it =
				this.content.entrySet().iterator();
		while(it.hasNext()) {
			com.FileManagerX.Interfaces.IReply item = it.next().getValue();
			if(item.isTimeOut()) {
				it.remove();
			}
		}
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public synchronized boolean add(com.FileManagerX.Interfaces.IReply t) {
		if(content.containsKey(t.getBasicMessagePackage().getIndex())) {
			return false;
		}
		this.content.put(t.getBasicMessagePackage().getIndex(), t);
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.Interfaces.IReply get(long key, long waitTicks, boolean fetch) {
		long start = com.FileManagerX.Tools.Time.getTicks();
		com.FileManagerX.Interfaces.IReply rep = null;
		
		while(com.FileManagerX.Tools.Time.getTicks() - start < waitTicks) {
			rep = fetch ? this.fetchByKey(key) : this.searchByKey(key);
			if(rep != null) {
				return rep;
			}
			com.FileManagerX.Tools.Time.sleepUntil(1);
		}
		
		return null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public synchronized com.FileManagerX.Interfaces.IReply searchByKey(Long key) {
		return this.content.get(key);
	}
	public synchronized com.FileManagerX.Interfaces.IReply fetchByKey(Long key) {
		return this.content.remove(key);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
