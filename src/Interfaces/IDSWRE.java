package Interfaces;

/**
 * SWRE Only For Direct Transport
 * 
 * @author ozxdno
 *
 */
public interface IDSWRE extends ISWRE {
	
	public boolean setDestMachineIndex(long destMachineIndex);
	public boolean setDestMachineInfo(BasicModels.MachineInfo destMachineInfo);
	public boolean setDestMachineInfo();
	public boolean setConnection();
	
	public long getDestMachineIndex();
	public BasicModels.MachineInfo getDestMachineInfo();
}
