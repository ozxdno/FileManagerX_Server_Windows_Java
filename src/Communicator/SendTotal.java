package Communicator;

public class SendTotal implements Interfaces.ICommunicatorSendTotal {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private String sendString;
	private java.util.List<String> lines;
	private int currentLine;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setSendString(String send) {
		this.input(send);
		return true;
	}
	public boolean setLines(java.util.List<String> lines) {
		if(lines == null) {
			return false;
		}
		this.lines = lines;
		return true;
	}
	public boolean setCurrentLine(int current) {
		if(current < 1) {
			current = 1;
		}
		if(current > lines.size()) {
			current = lines.size();
		}
		this.currentLine = current;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public String getSendString() {
		return this.sendString;
	}
	public java.util.List<String> getLines() {
		return this.lines;
	}
	public int getCurrentLine() {
		return this.currentLine;
	}
	public int getTotalLine() {
		return this.lines.size();
	}
	public boolean isFinished() {
		return this.currentLine > this.lines.size();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public SendTotal() {
		initThis();
	}
	public SendTotal(String send) {
		initThis();
		this.setSendString(send);
	}
	private void initThis() {
		this.sendString = "";
		this.currentLine = 1;
		if(this.lines == null) {
			this.lines = new java.util.ArrayList<String>();
		}
		this.lines.clear();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void clear() {
		initThis();
	}
	public String toString() {
		return this.output();
	}
	public String output() {
		String o = "";
		if(lines.size() == 0) {
			return o;
		}
		o = lines.get(0);
		for(int i=1; i<lines.size(); i++) {
			o += '\n' + lines.get(i);
		}
		return o;
	}
	public String input(String in) {
		String[] lines = Tools.String.split(in, '\n');
		this.sendString = in;
		this.lines = Tools.Array2List.toStringList(lines);
		this.currentLine = 1;
		return "";
	}
	public void copyReference(Object o) {
		SendTotal st = (SendTotal)o;
		this.sendString = st.sendString;
		this.lines = st.lines;
		this.currentLine = st.currentLine;
	}
	public void copyValue(Object o) {
		SendTotal st = (SendTotal)o;
		this.sendString = st.sendString;
		this.currentLine = st.currentLine;
		this.lines.clear();
		for(String line : st.lines) {
			this.lines.add(new String(line));
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean inputNextItem(String nextItem) {
		if(nextItem == null) {
			return false;
		}
		this.lines.add(nextItem);
		return true;
	}
	public String outputCurrentItem() {
		return this.outputItem(currentLine++);
	}
	public String outputItem(int itemIndex) {
		if(itemIndex < 1 || itemIndex > this.lines.size()) {
			return "";
		}
		Interfaces.ICommunicatorReceivePart rp = Factories.CommunicatorFactory.createReceivePart();
		rp.setContent(this.lines.get(itemIndex-1));
		rp.setTotalAmount(this.lines.size());
		rp.setPartId(itemIndex);
		
		return rp.output();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
