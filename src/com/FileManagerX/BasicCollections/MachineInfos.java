package com.FileManagerX.BasicCollections;

public class MachineInfos extends BasicHashMap<com.FileManagerX.BasicModels.MachineInfo, Long> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Long getKey(com.FileManagerX.BasicModels.MachineInfo item) {
		return item == null ? null : item.getIndex();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.BasicModels.MachineInfo createT() {
		return new com.FileManagerX.BasicModels.MachineInfo();
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
