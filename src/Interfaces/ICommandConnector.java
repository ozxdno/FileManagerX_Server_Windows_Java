package Interfaces;

/**
 * Some Info you may need to fill:<BR/>
 * <BR/>
 * ExecuteCommand; <BR/>
 * ExecuteReply; <BR/>
 *  <BR/>
 * SourMachineIndex;  <BR/>
 * DestMachineIndex;  <BR/>
 * SourMachineInfo;  <BR/>
 * DestMachineInfo;  <BR/>
 *   <BR/>
 * ReceiveCommand; <BR/>
 * SendCommand; <BR/>
 *  <BR/>
 *  SourConnection; <BR/>
 *  DestConnection; <BR/>
 *  
 * @author ozxdno
 *
 */
public interface ICommandConnector extends IPublic {

	public boolean setIsExecuteCommand(boolean executeCommand);
	public boolean setIsExecuteReply(boolean executeReply);
	public boolean setSourMachineIndex(long index);
	public boolean setDestMachineIndex(long index);
	public boolean setSourMachineInfo(BasicModels.MachineInfo machine);
	public boolean setDestMachineInfo(BasicModels.MachineInfo machine);
	public boolean setSourMachineInfo();
	public boolean setDestMachineInfo();
	public boolean setReceiveCommand(String receiveCommand);
	public boolean setSendCommand(String sendCommand);
	public boolean setSourConnection(Interfaces.IConnection connection);
	public boolean setDestConnection(Interfaces.IConnection connection);
	
	public boolean isExecuteCommand();
	public boolean isExecuteReply();
	public long getSourMachineIndex();
	public long getDestMachineIndex();
	public BasicModels.MachineInfo getSourMachineInfo();
	public BasicModels.MachineInfo getDestMachineInfo();
	public String getReceiveCommand();
	public String getSendCommand();
	public Interfaces.IConnection getSourConnection();
	public Interfaces.IConnection getDestConnection();
	
	public Interfaces.ICommandsManager execute();
}
