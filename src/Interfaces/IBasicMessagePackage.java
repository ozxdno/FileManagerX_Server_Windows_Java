package Interfaces;

public interface IBasicMessagePackage extends IPublic {

	public boolean setSourMachineIndex(long index);
	public boolean setDestMachineIndex(long index);
	public boolean setSourDepotIndex(long index);
	public boolean setDestDepotIndex(long index);
	public boolean setSourDataBaseIndex(long index);
	public boolean setDestDataBaseIndex(long index);
	public boolean setSourUserIndex(long index);
	public boolean setDestUserIndex(long index);
	
	public boolean setIp1(String ip);
	public boolean setIp2(String ip);
	public boolean setPort1(int port);
	public boolean setPort2(int port);
	
	public boolean setPassword(String password);
	
	
	public long getSourMachineIndex();
	public long getDestMachineIndex();
	public long getSourDepotIndex();
	public long getDestDepotIndex();
	public long getSourDataBaseIndex();
	public long getDestDataBaseIndex();
	public long getSourUserIndex();
	public long getDestUserIndex();
	
	public String getIp1();
	public String getIp2();
	public int getPort1();
	public int getPort2();
	
	public String getPassword();
}
