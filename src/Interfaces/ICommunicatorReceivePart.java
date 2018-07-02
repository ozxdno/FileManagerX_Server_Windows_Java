package Interfaces;

public interface ICommunicatorReceivePart extends IPublic {

	public boolean setReceiveString(String receive);
	public boolean setContent(String content);
	public boolean setPartId(int part);
	public boolean setTotalAmount(int total);
	public boolean setIsPart(boolean isPart);
	
	public String getReceiveString();
	public String getContent();
	public int getPartId();
	public int getTotalAmount();
	public boolean isPart();
}
