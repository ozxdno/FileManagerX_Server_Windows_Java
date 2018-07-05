package Main;

import java.awt.event.*;
import javax.swing.*;

// Output = 1|**********|1|2|-1|1|1|1|D:\Space_For_Media\Pictures\FMX_Test_Depot_F\a.jpg|D:\Space_For_Media\Pictures\FMX_Test_Depot_F\b.jpg|980911|0
// Input = 1|**********|1|2|-1|1|1|1|D:\Space_For_Media\Pictures\FMX_Test_Depot_F\d.jpg|D:\Space_For_Media\Pictures\FMX_Test_Depot_F\a.jpg|980911|0

@SuppressWarnings("serial")
public class MainForm extends JFrame {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public JTextField jInput = new JTextField();
	public JTextField jResult = new JTextField();
	public JButton jQuery = new JButton("query");
	public JButton jExecute = new JButton("execute");
	
	public String CmdName = "";
	public Interfaces.ICommands Command = null;
	public Interfaces.IReplies Reply = null;
	public Interfaces.ISWRE SWRE = Factories.CommunicatorFactory.createSWRE();
	 
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public MainForm() {
        
		this.initThis();
        this.initInput();
        this.initResult();
        this.initQuery();
        this.initExecute();
        
    }
	private void initInput() {
		
		jInput.setLocation(10, 10);
		jInput.setSize(360, 30);
	}
	private void initResult() {
		
		jResult.setLocation(10, 50);
		jResult.setSize(360, 30);
		//jResult.setEditable(false);
	}
	private void initQuery() {
		
		jQuery.addActionListener(this.listenQuery);
		jQuery.setLocation(140, 100);
		jQuery.setSize(100, 25);
	}
	private void initExecute() {
		
		jExecute.addActionListener(listenExecute);
		jExecute.setLocation(270, 100);
		jExecute.setSize(100, 25);
	}
	private void initThis() {
		
		this.add(jInput);
		this.add(jResult);
		this.add(jQuery);
		this.add(jExecute);
		
		this.setLayout(null);
		this.setTitle("FileManagerX");
		this.setSize(600, 180);
		this.setLocationRelativeTo(null);//窗口居中
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setVisible(true);//窗口可见
		this.addComponentListener(listenFrameResize);
		
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void resizeForm() {
		if(Globals.Datas.Form_Main == null) {
			return;
		}
		
		int wForm = Globals.Datas.Form_Main.getSize().width;
		//int hForm = Globals.Datas.Form_Main.getSize().height;
		
		int wInput = wForm - 40;
		int hInput = 30;
		int xInput = 10;
		int yInput = 10;
		
		int wResult = wInput;
		int hResult = hInput;
		int xResult = xInput;
		int yResult = yInput + hInput + yInput;
		
		int wExecute = 100;
		int hExecute = 25;
		int xExecute = wForm - 30 - wExecute;
		int yExecute = yResult + hResult + 20;
		
		int wQuery = wExecute;
		int hQuery = hExecute;
		int xQuery = xExecute - 30 - wQuery;
		int yQuery = yExecute;
		
		Globals.Datas.Form_Main.jInput.setLocation(xInput, yInput);
		Globals.Datas.Form_Main.jInput.setSize(wInput, hInput);
		Globals.Datas.Form_Main.jResult.setLocation(xResult, yResult);
		Globals.Datas.Form_Main.jResult.setSize(wResult, hResult);
		Globals.Datas.Form_Main.jExecute.setLocation(xExecute, yExecute);
		Globals.Datas.Form_Main.jExecute.setSize(wExecute, hExecute);
		Globals.Datas.Form_Main.jQuery.setLocation(xQuery, yQuery);
		Globals.Datas.Form_Main.jQuery.setSize(wQuery, hQuery);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private ActionListener listenQuery = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
	        if (jQuery == e.getSource()) {//如果是jBtn这个按钮被点击了,
	            String q = jInput.getText();
	            if(q == null || q.length() == 0) {
	            	return;
	            }
	            
	            q = q.toLowerCase();
	            
	            if(q.equals("closeserver") || q.equals("cs")) {
	            	CmdName = "CloseServer";
	            	Command = new Commands.CloseServer();
	            	jInput.setText(((Commands.CloseServer)Command).output());
	            	
	            	String tip = CmdName + " = " +
	            			"[01-sourMachineIndex]" +
	            			"[02-destMachineIndex]" +
	            			"[03-ourDepotIndex]" +
	            			"[04-destDepotIndex]" +
	            			"[05-sourDataBaseIndex]" + 
	            			"[06-destDataBaseIndex]" +
	            			"[07-sourUserIndex]" +
	            			"[08-destUserIndex]" +
	            			"[09-ip1]" + 
	            			"[10-ip2]" +
	            			"[11-port1]" +
	            			"[12-port2]" +
	            			"[13-password]";
	            	jResult.setText(tip);
	            }
	            if(q.equals("test") || q.equals("t")) {
	            	CmdName = "Test";
	            	Command = new Commands.Test();
	            	jInput.setText(((Commands.Test)Command).output());
	            	
	            	String tip = CmdName + " = " +
	            			"[01-sourMachineIndex]" +
	            			"[02-destMachineIndex]" +
	            			"[03-ourDepotIndex]" +
	            			"[04-destDepotIndex]" +
	            			"[05-sourDataBaseIndex]" + 
	            			"[06-destDataBaseIndex]" +
	            			"[07-sourUserIndex]" +
	            			"[08-destUserIndex]" +
	            			"[09-ip1]" + 
	            			"[10-ip2]" +
	            			"[11-port1]" +
	            			"[12-port2]" +
	            			"[13-password]" +
	            			"[14-testString]";
	            	jResult.setText(tip);
	            }
	            if(q.equals("input") || q.equals("i")) {
	            	CmdName = "Input";
	            	Command = new Commands.Input();
	            	jInput.setText(Command.output());
	            	
	            	String tip = CmdName + " = " +
	            			"[01-sourMachineIndex]" +
	            			"[02-destMachineIndex]" +
	            			"[03-ourDepotIndex]" +
	            			"[04-destDepotIndex]" +
	            			"[05-sourDataBaseIndex]" + 
	            			"[06-destDataBaseIndex]" +
	            			"[07-sourUserIndex]" +
	            			"[08-destUserIndex]" +
	            			"[09-ip1]" + 
	            			"[10-ip2]" +
	            			"[11-port1]" +
	            			"[12-port2]" +
	            			"[13-password]" +
	            			"[14-cover]" +
	            			"[15-sourUrl]" +
	            			"[16-destUrl]" +
	            			"[17-totalBytes]" +
	            			"[18-finishedBytes]";
	            	jResult.setText(tip);
	            }
	            if(q.equals("output") || q.equals("o")) {
	            	CmdName = "Output";
	            	Command = new Commands.Output();
	            	jInput.setText(Command.output());
	            	
	            	String tip = CmdName + " = " +
	            			"[01-sourMachineIndex]" +
	            			"[02-destMachineIndex]" +
	            			"[03-ourDepotIndex]" +
	            			"[04-destDepotIndex]" +
	            			"[05-sourDataBaseIndex]" + 
	            			"[06-destDataBaseIndex]" +
	            			"[07-sourUserIndex]" +
	            			"[08-destUserIndex]" +
	            			"[09-ip1]" + 
	            			"[10-ip2]" +
	            			"[11-port1]" +
	            			"[12-port2]" +
	            			"[13-password]" +
	            			"[14-cover]" +
	            			"[15-sourUrl]" +
	            			"[16-destUrl]" +
	            			"[17-totalBytes]" +
	            			"[18-finishedBytes]";
	            	jResult.setText(tip);
	            }
	        }
	    }
	};
	
	private ActionListener listenExecute = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
	        if (jExecute == e.getSource()) {//如果是jBtn这个按钮被点击了,
	        	
	        	String cmd = jInput.getText();
	        	boolean directAccess = cmd.length() > 2 && cmd.substring(0, 2).equals("d ");
	        	if(directAccess) {
	        		cmd = cmd.substring(2);
	        		cmd = Tools.String.clearLRSpace(cmd);
	        	}
	        	
	        	CmdName = Tools.String.clearLRSpace(Tools.String.getField(cmd));
	        	
	        	if(CmdName.equals("Input") || CmdName.equals("Output")) {
	        		Interfaces.IFSWRE fswre = Factories.CommunicatorFactory.createFSWRE();
	        		if(directAccess) {
	        			Reply = fswre.executeDirectly(cmd);
	        		} else {
	        			fswre.setConnection();
	        			Reply = fswre.execute(cmd);
	        		}
	        	}
	        	else if(directAccess) {
	        		Interfaces.IDSWRE dswre = Factories.CommunicatorFactory.createDSWRE();
	        		Reply = dswre.execute(cmd);
	        	}
	        	else {
	        		Reply = SWRE.execute(cmd);
	        	}
	        	
	            if(Reply == null) {
	            	jResult.setText("NULL");
	            	return;
	            }
	            
	            /*
	            if(Reply instanceof Replies.CloseServer) {
	            	Replies.CloseServer reply = (Replies.CloseServer)Reply;
	            	jResult.setText(reply.toString());
	            	return;
	            }
	            if(Reply instanceof Replies.Unsupport) {
	            	Replies.Unsupport reply = (Replies.Unsupport)Reply;
	            	jResult.setText(reply.toString());
	            	return;
	            }
	            */
	            
	            jResult.setText(Reply.output());
	        }
	    }
	};
	
	private ComponentAdapter listenFrameResize = new ComponentAdapter() {
		public void componentResized(ComponentEvent e){
			Globals.Datas.Form_Main.resizeForm();
		}
	};
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}


