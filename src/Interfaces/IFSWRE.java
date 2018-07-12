package Interfaces;

/**
 * SWRE Only For File Transport
 * 
 * 
 * @author ozxdno
 *
 */
public interface IFSWRE extends ISWRE {
	
	public boolean setServerMachineIndex(long index);
	public boolean setClientMachineIndex(long index);
	public boolean setUserIndex(long index);
	public boolean setConnection();
	
	public Interfaces.IReplies executeDirectly();
	public Interfaces.IReplies executeDirectly(String command);

}
