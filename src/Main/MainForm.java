package Main;

import java.awt.event.*;
import javax.swing.*;

// reset
// path
// errors
// tip

// Input = 5|6|D:\Space_For_Media\Pictures\FMX_Test_Depot_E\a2.jpg|D:\Space_For_Media\Pictures\FMX_Test_Depot_C\a10.jpg
// Input = D:\Space_For_Media\Pictures\FMX_Test_Depot_A\阿九\a2.jpg|D:\Space_For_Media\Pictures\FMX_Test_Depot_C\a2.jpg
// Input = D:\Space_For_Media\Pictures\FMX_Test_Depot_A\新建文件夹\a1.rar|D:\Space_For_Media\Pictures\FMX_Test_Depot_C\a4.rar
// Input = D:\Space_For_Media\Pictures\FMX_Test_Depot_A\新建文件夹\a1.rar|D:\Space_For_Media\Pictures\FMX_Test_Depot_C\a5.rar|2366464
// Output = 5|6|D:\Space_For_Media\Pictures\FMX_Test_Depot_C\a2.jpg|D:\Space_For_Media\Pictures\FMX_Test_Depot_E\a2.jpg
// Output = D:\Space_For_Media\Pictures\FMX_Test_Depot_C\a2.jpg|D:\Space_For_Media\Pictures\FMX_Test_Depot_A\a3.jpg

@SuppressWarnings("serial")
public class MainForm extends JFrame {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public JTextField jInput = new JTextField();
	public JTextField jResult = new JTextField();
	public JButton jQuery = new JButton("query");
	public JButton jExecute = new JButton("execute");
	
	public String CmdName = "";
	public java.util.ArrayList<String> List = new java.util.ArrayList<String>();
	public String ListType = "";
	public int ListIndex = 0;
	 
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
		this.setVisible(false);//窗口可见
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
	            String f = Tools.String.clearLRSpace(Tools.String.getField(q));
	            String v = Tools.String.clearLRSpace(Tools.String.getValue(q));
	            if(f.isEmpty()) {
	            	f = v;
	            	v = "";
	            }
	            
	            if(f.equals("prev") || v.equals("prev")) {
	            	ListIndex -= 1;
	            	if(ListIndex < 0) {
	            		ListIndex = List.size() - 1;
	            	}
	            	if(List.size() == 0) {
	            		return;
	            	}
	            	jResult.setText(List.get(ListIndex));
	            	return;
	            }
	            if(f.equals("next") || v.equals("next")) {
	            	ListIndex += 1;
	            	if(ListIndex >= List.size()) {
	            		ListIndex = 0;
	            	}
	            	if(List.size() == 0) {
	            		return;
	            	}
	            	jResult.setText(List.get(ListIndex));
	            	return;
	            }
	            if(f.equals("closeserver") || f.equals("cs")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/2][No Args]";
	            	List.add(tip);
	            	tip = "[2/2][destMachineIndex]";
	            	List.add(tip);
	            	
	            	jInput.setText("CloseServer = next");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("test") || f.equals("t")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/3][No Args]";
	            	List.add(tip);
	            	tip = "[2/3][testString]";
	            	List.add(tip);
	            	tip = "[3/3][destMachineIndex][testString]";
	            	List.add(tip);
	            	
	            	jInput.setText("Test = next");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("input") || f.equals("i")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/4][sourUrl][destUrl]";
	            	List.add(tip);
	            	tip = "[2/4][sourUrl][destUrl][finishedBytes]";
	            	List.add(tip);
	            	tip = "[3/4][sourMachine][destMachine][sourUrl][destUrl]";
	            	List.add(tip);
	            	tip = "[4/4][sourMachine][destMachine][sourUrl][destUrl][finishedBytes]";
	            	List.add(tip);
	            	
	            	jInput.setText("Input = next");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("output") || f.equals("o")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/4][sourUrl][destUrl]";
	            	List.add(tip);
	            	tip = "[2/4][sourUrl][destUrl][finishedBytes]";
	            	List.add(tip);
	            	tip = "[3/4][sourMachine][destMachine][sourUrl][destUrl]";
	            	List.add(tip);
	            	tip = "[4/4][sourMachine][destMachine][sourUrl][destUrl][finishedBytes]";
	            	List.add(tip);
	            	
	            	jInput.setText("Output = next");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            jResult.setText("[Unsupport]");
	        }
	    }
	};
	
	private ActionListener listenExecute = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
	        if (jExecute == e.getSource()) {//如果是jBtn这个按钮被点击了,
	        	
	        	String cmd = jInput.getText();
	        	boolean directAccess = cmd.length() > 1 && cmd.charAt(0) == '@';
	        	if(directAccess) { cmd = cmd.substring(1); }
	        	CmdName = Tools.String.clearLRSpace(Tools.String.getField(cmd));
	        	Interfaces.ICommandsManager cm = Globals.Datas.ServerConnection.getCommandsManager();
	        	BasicModels.Config cmdcfg = new BasicModels.Config(cmd);
	        	
	        	if(directAccess) {
	        		long sourMachine = -1;
	        		long destMachine = -1;
	        		try {
	        			sourMachine = cmdcfg.fetchFirstLong();
	        			destMachine = cmdcfg.fetchFirstLong();
	        		} catch(Exception exception) {
	        			;
	        		}
	        		
	        		if(CmdName.equals("Input") || CmdName.equals("Output")) {
	        			Interfaces.IClientConnection con = Factories.CommunicatorFactory.createRunningClientConnection(
	        					destMachine,
	        					sourMachine
	        					);
	        			if(con == null) {
        					jResult.setText("Create Connection Failed");
		        			return;
        				}
	        			con.setUser(Globals.Datas.ThisUser);
	        			con.setType(BasicEnums.ConnectionType.TRANSPORT_FILE);
	        			if(!con.getCommandsManager().loginConnection()) {
	        				jResult.setText("Login Failed");
		        			return;
	        			}
	        			cm = con.getCommandsManager();
	        		}
	        		else {
	        			Interfaces.IClientConnection con = Globals.Datas.Client.search(destMachine);
	        			if(con == null) {
	        				con = Factories.CommunicatorFactory.createRunningClientConnection(
	        						destMachine,
	            					sourMachine
		        					);
	        				if(con == null) {
	        					jResult.setText("Create Connection Failed");
			        			return;
	        				}
		        			con.setUser(Globals.Datas.ThisUser);
		        			con.setType(BasicEnums.ConnectionType.TRANSPORT_COMMAND);
		        			if(!con.getCommandsManager().loginConnection()) {
		        				jResult.setText("Login Failed");
			        			return;
		        			}
	        			}
	        			cm = con.getCommandsManager();
	        		}
	        	}
	        	else if(CmdName.equals("Input") || CmdName.equals("Output")) {
	        		Interfaces.IClientConnection con = Factories.CommunicatorFactory.createRunningClientConnection();
        			if(con == null) {
    					jResult.setText("Create Connection Failed");
	        			return;
    				}
        			con.setUser(Globals.Datas.ThisUser);
        			con.setType(BasicEnums.ConnectionType.TRANSPORT_FILE);
        			if(!con.getCommandsManager().loginConnection()) {
        				jResult.setText("Login Failed");
	        			return;
        			}
        			cm = con.getCommandsManager();
	        	}
	        	
	        	try {
	        		 if(CmdName.equals("CloseServer")) {
	        			if(cmdcfg.getItemsSize() == 0) {
	 	        			boolean ok = cm.closeServer();
	 	        			String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
	 	        			jResult.setText(ok ? "Successed" : "Failed: " + reason);
	 	        			return;
	 	        		}
	        			if(cmdcfg.getItemsSize() == 1) {
	 	        			boolean ok = cm.closeServer(cmdcfg.getLong(0));
	 	        			String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
	 	        			jResult.setText(ok ? "Successed" : "Failed: " + reason);
	 	        			return;
	 	        		}
	 	            }
	 	            if(CmdName.equals("Test")) {
	 	            	if(cmdcfg.getItemsSize() == 0) {
	 	        			boolean res = cm.test();
	 	        			String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
	 	        			jResult.setText(res ? "Successed" : "Failed: " + reason);
	 	        			return;
	 	        		}
	 	        		if(cmdcfg.getItemsSize() == 1) {
	 	        			String res = cm.test(cmdcfg.getString(0));
	 	        			String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
	 	        			jResult.setText(res == null ? "Failed: " + reason : "Receive = " + res);
	 	        			return;
	 	        		}
	 	        		if(cmdcfg.getItemsSize() == 2) {
	 	        			String res = cm.test(cmdcfg.getLong(0), cmdcfg.getString(1));
	 	        			String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
	 	        			jResult.setText(res == null ? "Failed: " + reason : "Receive = " + res);
	 	        			return;
	 	        		}
	 	        	}
	 	            if(CmdName.equals("Input")) {
	 	            	if(cmdcfg.getItemsSize() == 2) {
	 	            		boolean ok = cm.input(cmdcfg.getString(0), cmdcfg.getString(1));
	 	            		String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
	 	            		jResult.setText(ok ? "Successed" : "Failed: " + reason);
	 	            		return;
	 	            	}
	 	            	if(cmdcfg.getItemsSize() == 3) {
	 	            		boolean ok = cm.input(cmdcfg.getString(0), cmdcfg.getString(1), cmdcfg.getLong(2));
	 	            		String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
	 	            		jResult.setText(ok ? "Successed" : "Failed: " + reason);
	 	            		return;
	 	            	}
	 	            	if(cmdcfg.getItemsSize() == 4) {
	 	            		boolean ok = cm.input(cmdcfg.getLong(0), cmdcfg.getLong(1), cmdcfg.getString(2), cmdcfg.getString(3));
	 	            		String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
	 	            		jResult.setText(ok ? "Successed" : "Failed: " + reason);
	 	            		return;
	 	            	}
	 	            	if(cmdcfg.getItemsSize() == 5) {
	 	            		boolean ok = cm.input(cmdcfg.getLong(0), cmdcfg.getLong(1), cmdcfg.getString(2), cmdcfg.getString(3), cmdcfg.getLong(4));
	 	            		String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
	 	            		jResult.setText(ok ? "Successed" : "Failed: " + reason);
	 	            		return;
	 	            	}
	 	            }
	 	           if(CmdName.equals("Output")) {
	 	            	if(cmdcfg.getItemsSize() == 2) {
	 	            		boolean ok = cm.output(cmdcfg.getString(0), cmdcfg.getString(1));
	 	            		String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
	 	            		jResult.setText(ok ? "Successed" : "Failed: " + reason);
	 	            		return;
	 	            	}
	 	            	if(cmdcfg.getItemsSize() == 3) {
	 	            		boolean ok = cm.output(cmdcfg.getString(0), cmdcfg.getString(1), cmdcfg.getLong(2));
	 	            		String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
	 	            		jResult.setText(ok ? "Successed" : "Failed: " + reason);
	 	            		return;
	 	            	}
	 	            	if(cmdcfg.getItemsSize() == 4) {
	 	            		boolean ok = cm.output(cmdcfg.getLong(0), cmdcfg.getLong(1), cmdcfg.getString(2), cmdcfg.getString(3));
	 	            		String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
	 	            		jResult.setText(ok ? "Successed" : "Failed: " + reason);
	 	            		return;
	 	            	}
	 	            	if(cmdcfg.getItemsSize() == 5) {
	 	            		boolean ok = cm.output(cmdcfg.getLong(0), cmdcfg.getLong(1), cmdcfg.getString(2), cmdcfg.getString(3), cmdcfg.getLong(4));
	 	            		String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
	 	            		jResult.setText(ok ? "Successed" : "Failed: " + reason);
	 	            		return;
	 	            	}
	 	            }
	 	        	
	 	            jResult.setText("Unsupport Command");
	        	} catch(Exception ex) {
	        		jResult.setText(ex.toString());
	        	}
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


