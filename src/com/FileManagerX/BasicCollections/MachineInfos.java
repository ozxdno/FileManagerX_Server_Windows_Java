package com.FileManagerX.BasicCollections;

public class MachineInfos extends BasicCollection<com.FileManagerX.BasicModels.MachineInfo> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static com.FileManagerX.Interfaces.ICollection.IKey KeyForIndex =
		new com.FileManagerX.Interfaces.ICollection.IKey() {
			public Object getKey(Object item) {
				if(item instanceof com.FileManagerX.BasicModels.MachineInfo) {
					com.FileManagerX.BasicModels.MachineInfo i = (com.FileManagerX.BasicModels.MachineInfo)item;
					return i.getIndex();
				}
				return null;
			}
		};
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public MachineInfos() {
		this.initThis();
	}
	private void initThis() {
		this.setContent(new com.FileManagerX.BasicCollections.BasicHashMap<>());
		this.setKey(KeyForIndex);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.BasicModels.MachineInfo searchByIndex(long index) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.MachineInfo> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.MachineInfo m = it.getNext();
			if(m.getIndex() == index) {
				return m;
			}
		}
		return null;
	}
	public com.FileManagerX.BasicModels.MachineInfo fetchByIndex(long index) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.MachineInfo> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.MachineInfo m = it.getNext();
			if(m.getIndex() == index) {
				it.remove();
				return m;
			}
		}
		return null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.BasicModels.MachineInfo searchByName(String name) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.MachineInfo> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.MachineInfo m = it.getNext();
			if(m.getName().equals(name)) {
				return m;
			}
		}
		return null;
	}
	public com.FileManagerX.BasicModels.MachineInfo fetchByName(String name) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.MachineInfo> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.MachineInfo m = it.getNext();
			if(m.getName().equals(name)) {
				it.remove();
				return m;
			}
		}
		return null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.BasicModels.MachineInfo searchByMac(String mac) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.MachineInfo> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.MachineInfo m = it.getNext();
			if(m.getMac().equals(mac)) {
				return m;
			}
		}
		return null;
	}
	public com.FileManagerX.BasicModels.MachineInfo fetchByMac(String mac) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.MachineInfo> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.MachineInfo m = it.getNext();
			if(m.getMac().equals(mac)) {
				it.remove();
				return m;
			}
		}
		return null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.BasicModels.MachineInfo searchByIp(String ip) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.MachineInfo> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.MachineInfo m = it.getNext();
			if(m.getIp().equals(ip)) {
				return m;
			}
		}
		return null;
	}
	public com.FileManagerX.BasicModels.MachineInfo fetchByIp(String ip) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.MachineInfo> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.MachineInfo m = it.getNext();
			if(m.getIp().equals(ip)) {
				it.remove();
				return m;
			}
		}
		return null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.BasicModels.MachineInfo searchByIpAndPort(String ip, int port) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.MachineInfo> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.MachineInfo m = it.getNext();
			if(m.getIp().equals(ip) && m.getPort() == port) {
				return m;
			}
		}
		return null;
	}
	public com.FileManagerX.BasicModels.MachineInfo fetchByIpAndPort(String ip, int port) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.MachineInfo> it = this.getIterator();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.MachineInfo m = it.getNext();
			if(m.getIp().equals(ip) && m.getPort() == port) {
				it.remove();
				return m;
			}
		}
		return null;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
