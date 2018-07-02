package Interfaces;

public interface ICommunicatorSendTotal extends IPublic {

	public boolean setSendString(String send);
	public boolean setLines(java.util.List<String> lines);
	public boolean setCurrentLine(int current);
	
	public String getSendString();
	public java.util.List<String> getLines();
	public int getCurrentLine();
	public int getTotalLine();
	public boolean isFinished();
	
	public boolean inputNextItem(String nextItem);
	public String outputCurrentItem();
	public String outputItem(int itemIndex);
}
