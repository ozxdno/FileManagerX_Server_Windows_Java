package Communicator;

public class ReceivePart implements Interfaces.ICommunicatorReceivePart {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private String receiveString;
	private String content;
	private int part;
	private int total;
	
	private boolean isPart;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setReceiveString(String receive) {
		this.input(receive);
		return true;
	}
	public boolean setContent(String content) {
		this.content = content;
		return true;
	}
	public boolean setPartId(int part) {
		if(part < 0) {
			return false;
		}
		this.part = part;
		return true;
	}
	public boolean setTotalAmount(int total) {
		if(total < 0) {
			return false;
		}
		this.total = total;
		return true;
	}
	public boolean setIsPart(boolean isPart) {
		this.isPart = isPart;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public String getReceiveString() {
		return this.receiveString;
	}
	public String getContent() {
		return this.content;
	}
	public int getPartId() {
		return this.part;
	}
	public int getTotalAmount() {
		return this.total;
	}
	public boolean isPart() {
		return this.isPart;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public ReceivePart() {
		initThis();
	}
	public ReceivePart(String receiveString) {
		initThis();
		this.setReceiveString(receiveString);
	}
	private void initThis() {
		this.receiveString = "";
		this.content = "";
		this.part = 0;
		this.total = 0;
		this.isPart = false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void clear() {
		initThis();
	}
	public String toString() {
		return this.output();
	}
	public String output() {
		if(this.content == null) {
			return null;
		}
		if(this.part == 0 || this.total == 0) {
			return this.content;
		}
		
		return "[" + this.part + "/" + this.total + "] " + this.content;
	}
	public String input(String in) {
		this.isPart = false;
		
		if(in == null) {
			this.receiveString = null;
			this.content = null;
			this.part = 0;
			this.total = 0;
			return "";
		}
		if(in.length() == 0) {
			this.receiveString = "";
			this.content = "";
			this.part = 0;
			this.total = 0;
			return "";
		}
		
		in = Tools.String.clearLRSpace(in);
		if(in.charAt(0) != '[') {
			this.receiveString = in;
			this.content = in;
			this.part = 0;
			this.total = 0;
			return "";
		}
		
		int idxL = 0;
		int idxR = in.indexOf(']');
		if(idxR < 0) {
			this.receiveString = in;
			this.content = in;
			this.part = 0;
			this.total = 0;
			return "";
		}
		
		String part1 = in.substring(idxL+1, idxR);
		int idxC = part1.indexOf('/');
		if(idxC < 0) {
			this.receiveString = in;
			this.content = in;
			this.part = 0;
			this.total = 0;
			return "";
		}
		
		String partStr = part1.substring(0, idxC);
		String totalStr = part1.substring(idxC+1, part1.length());
		try {
			this.part = Integer.parseInt(partStr);
			this.total = Integer.parseInt(totalStr);
			this.isPart = this.part > 0 && this.total > 0;
		} catch(Exception e) {
			this.receiveString = in;
			this.content = in;
			this.part = 0;
			this.total = 0;
			return "";
		}
		
		if(!this.isPart) {
			this.receiveString = in;
			this.content = in;
			this.part = 0;
			this.total = 0;
			return "";
		}
		
		this.receiveString = in;
		this.content = Tools.String.clearLRSpace(in.substring(idxR+1));
		return "";
	}
	public void copyReference(Object o) {
		ReceivePart rp = (ReceivePart)o;
		this.receiveString = rp.receiveString;
		this.content = rp.content;
		this.part = rp.part;
		this.total = rp.total;
	}
	public void copyValue(Object o) {
		ReceivePart rp = (ReceivePart)o;
		this.receiveString = new String(rp.receiveString);
		this.content = new String(rp.content);
		this.part = rp.part;
		this.total = rp.total;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
