package BasicModels;

public class MachineInfo implements Tools.IPublic {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private long index;
	private String name;
	private String ip;
	private int port;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public long getIndex() {
		return index;
	}
	public String getName() {
		return name;
	}
	public String getIp() {
		return ip;
	}
	public int getPort() {
		return port;
	}
	public String getUrl() {
		return ip + ":" + String.valueOf(port);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean setIndex(long index) {
		if(index < 0) {
			return false;
		}
		this.index = index;
		return true;
	}
	public boolean setIndex() {
		this.index = Globals.Configurations.Next_MachineIndex + 1;
		Globals.Configurations.Next_MachineIndex = index;
		return true;
	}
	public boolean setName(String name) {
		this.name = name;
		return true;
	}
	public boolean setIp(String ip) {
		if(Tools.Url.isIp(ip)) {
			this.ip = ip;
			return true;
		}
		return false;
	}
	public boolean setPort(int port) {
		if(0 <= port && port < 65536) {
			this.port = port;
			return true;
		}
		return false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public MachineInfo() {
		initThis();
	}
	public MachineInfo(String url) {
		initThis();
		setIp(Tools.Url.getIp(url));
		setPort(Tools.Url.getPort(url));
	}
	public MachineInfo(int port) {
		initThis();
		try {
			java.net.InetAddress adr = java.net.InetAddress.getLocalHost();
			this.ip = adr.getHostAddress().toString();
			this.name = adr.getHostName().toString();
			this.setPort(port);
		} catch(Exception e) {
			;
		}
	}
	public MachineInfo(String ip, int port) {
		initThis();
		setIp(ip);
		setPort(port);
	}
	private void initThis() {
		index = -1;
		name = "";
		ip = "";
		port = -1;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void clear() {
		initThis();
	}
	@Override
	public String toString() {
		String name = this.name;
		if(name == null || name.length() == 0) {
			name = "No Name";
		}
		String ip = this.ip;
		if(ip == null || ip.length() == 0) {
			ip = "[Illegal IP]";
		}
		return "[" + name + "]" + ip + ":" + String.valueOf(port);
	}
	public String output() {
		Config c = new Config("Machine = ");
		c.addToBottom(index);
		c.addToBottom(name);
		c.addToBottom(ip);
		c.addToBottom(port);
		return c.output();
	}
	public String input(String in) {
		Config c = new Config(in);
		this.index = c.fetchFirstLong();
		if(!c.getIsOK()) { return null; }
		this.name = c.fetchFirstString();
		if(!c.getIsOK()) { return null; }
		this.ip = c.fetchFirstString();
		if(!c.getIsOK()) { return null; }
		this.port = c.fetchFirstInt();
		if(!c.getIsOK()) { return null; }
		return c.output();
	}
	public void copyReference(Object o) {
		MachineInfo m = (MachineInfo)o;
		this.index = m.index;
		this.name = m.name;
		this.ip = m.ip;
		this.port = m.port;
	}
	public void copyValue(Object o) {
		MachineInfo m = (MachineInfo)o;
		this.index = m.index;
		this.name = new String(m.name);
		this.ip = new String(m.ip);
		this.port = m.port;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean isLocal() {
		return ip.equals(Globals.Configurations.ThisMachine.getIp());
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
