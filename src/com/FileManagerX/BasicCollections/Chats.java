package com.FileManagerX.BasicCollections;

public class Chats extends BasicHashMap<com.FileManagerX.BasicModels.Chat, Long> {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Long getKey(com.FileManagerX.BasicModels.Chat item) {
		return item == null ? null : item.getIndex();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.BasicModels.Chat createT() {
		return new com.FileManagerX.BasicModels.Chat();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public java.util.Comparator<com.FileManagerX.BasicModels.Chat> getACmpByTime() {
		return new java.util.Comparator<com.FileManagerX.BasicModels.Chat>() {
			public int compare(com.FileManagerX.BasicModels.Chat e1, com.FileManagerX.BasicModels.Chat e2) {
				if(e1.getTime() > e2.getTime()) {
					return 1;
				} else {
					return -1;
				}
			}
		};
	}
	public java.util.Comparator<com.FileManagerX.BasicModels.Chat> getDCmpByTime() {
		return new java.util.Comparator<com.FileManagerX.BasicModels.Chat>() {
			public int compare(com.FileManagerX.BasicModels.Chat e1, com.FileManagerX.BasicModels.Chat e2) {
				if(e1.getTime() > e2.getTime()) {
					return -1;
				} else {
					return 1;
				}
			}
		};
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public com.FileManagerX.BasicModels.Chat searchByContent(String content) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.Chat> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.Chat c = it.getNext();
			if(c.getContent().equals(content)) {
				return c;
			}
		}
		return null;
	}
	public com.FileManagerX.BasicModels.Chat fetchByContent(String content) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.Chat> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.Chat c = it.getNext();
			if(c.getContent().equals(content)) {
				it.remove();
				return c;
			}
		}
		return null;
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
}
