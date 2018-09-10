package com.FileManagerX.BasicCollections;

public class Chats extends BasicCollection<com.FileManagerX.BasicModels.Chat> {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Chats() {
		this.initThis();
	}
	private void initThis() {
		this.setContent(new com.FileManagerX.BasicCollections.BasicArrayList<>());
		this.setKey(new KeyForIndex());
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.BasicModels.Chat createT() {
		return new com.FileManagerX.BasicModels.Chat();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.BasicModels.Chat searchByTime(long time) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.Chat> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.Chat c = it.getNext();
			if(c.getTime() == time) {
				return c;
			}
		}
		return null;
	}
	public com.FileManagerX.BasicModels.Chat fetchByTime(long time) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.Chat> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.Chat c = it.getNext();
			if(c.getTime() == time) {
				it.remove();
				return c;
			}
		}
		return null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Chats searchesByTimeBefore(long time) {
		Chats chats = new Chats();
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.Chat> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.Chat c = it.getNext();
			if(c.getTime() < time) {
				chats.add(c);
			}
		}
		return chats;
	}
	public Chats fetchesByTimeBefore(long time) {
		Chats chats = new Chats();
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.Chat> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.Chat c = it.getNext();
			if(c.getTime() < time) {
				it.remove();
				chats.add(c);
			}
		}
		return chats;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Chats searchesByTimeBehind(long time) {
		Chats chats = new Chats();
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.Chat> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.Chat c = it.getNext();
			if(c.getTime() > time) {
				chats.add(c);
			}
		}
		return chats;
	}
	public Chats fetchesByTimeBehind(long time) {
		Chats chats = new Chats();
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.Chat> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.Chat c = it.getNext();
			if(c.getTime() > time) {
				it.remove();
				chats.add(c);
			}
		}
		return chats;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static class KeyForIndex implements com.FileManagerX.Interfaces.ICollection.IKey {
		public Object getKey(Object item) {
			if(item instanceof com.FileManagerX.BasicModels.Chat) {
				com.FileManagerX.BasicModels.Chat i = (com.FileManagerX.BasicModels.Chat)item;
				return i.getIndex();
			}
			return null;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
