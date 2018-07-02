package Interfaces;

/**
 * Some Info you may need to fill:<BR/>
 * <BR/>
 * SourMachine; <BR/>
 * DestMachine; <BR/>
 * SourDepot; <BR/>
 * DestDepot; <BR/>
 *  <BR/>
 * Input;  <BR/>
 * Output;  <BR/>
 * SourUrl;  <BR/>
 * DestUrl;  <BR/>
 *  <BR/>
 * TotalBytes;  <BR/>
 * FinishedBytes; <BR/>
 *  <BR/>
 * Cover;  <BR/>
 * ReadFromLocal;  <BR/>
 * WriteToLocal;  <BR/>
 *   <BR/>
 * Active;  <BR/>
 *  <BR/>
 *  
 * @author ozxdno
 *
 */
public interface IFileConnector {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean setSourMachine(long index);
	public boolean setDestMachine(long index);
	public boolean setSourDepot(long index);
	public boolean setDestDepot(long index);
	
	public boolean setIsInputCommand(boolean isInput);
	public boolean setIsOutputCommand(boolean isOutput);
	public boolean setSourUrl(String url);
	public boolean setDestUrl(String url);
	
	public boolean setFinishedBytes(long finishedBytes);
	public boolean setTotalBytes(long totalBytes);
	
	public boolean setIsCoverExistedFile(boolean isCover);
	public boolean setIsReadFromLocal(boolean readFromLocal);
	public boolean setIsWriteToLocal(boolean writeToLocal);
	
	public boolean setSendBytes(byte[] sendBytes);
	public boolean setReceiveBytes(byte[] receiveBytes);
	public boolean setSendLength(int length);
	public boolean setReceiveLength(int length);
	
	public boolean setConnection(Interfaces.IClientConnection connection);
	
	public boolean setState_Active(boolean active);
	public boolean setState_Busy(boolean busy);
	public boolean setState_Stop(boolean stop); // 是否中断文件传输。
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public long getSourMachine();
	public long getDestMachine();
	public long getSourDepot();
	public long getDestDepot();

	public boolean isInputCommand();
	public boolean isOutputCommand();
	public String getSourUrl();
	public String getDestUrl();
	
	public long getFinishedBytes();
	public long getTotalBytes();
	
	public boolean isCoverExistedFile();
	public boolean isReadFromLocal();
	public boolean isWriteToLocal();
	
	public byte[] getSendBytes();
	public byte[] getReceiveBytes();
	public int getSendLength();
	public int getReceiveLength();
	
	public Interfaces.IClientConnection getConnection();
	
	public boolean isActive();
	public boolean isBusy();
	public boolean isStop();
	public boolean isFinished();
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean save();
	public boolean load();
	public void close();
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
