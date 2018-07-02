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
	            	String res = "CloseServer = ";
	            	res += Globals.Configurations.This_UserIndex;
	            	res += "|" + Globals.Datas.ThisUser.getPassword();
	            	res += "|-1";
	            	res += "|-1";
	            	res += "|-1";
	            	jInput.setText(res);
	            }
	            if(q.equals("input") || q.equals("i")) {
	            	String res = "Input = " +
	            		Globals.Configurations.This_UserIndex + "|" +
	            		Globals.Datas.ThisUser.getPassword() + "|" +
	            		"targetMachine|" +
	            		"targetDepot|" +
	            		"-1|" +
	            		"sourMachine|" +
	            		"sourDepot|" +
	            		"isCover|" +
	            		"sourUrl|" +
	            		"destUrl|" +
	            		"totalBytes|" +
	            		"finishedBytes";
	            	jInput.setText(res);
	            }
	            if(q.equals("output") || q.equals("o")) {
	            	String res = "Output = " +
	            		Globals.Configurations.This_UserIndex + "|" +
	            		Globals.Datas.ThisUser.getPassword() + "|" +
	            		"targetMachine|" +
	            		"targetDepot|" +
	            		"-1|" +
	            		"sourMachine|" +
	            		"sourDepot|" +
	            		"isCover|" +
	            		"sourUrl|" +
	            		"destUrl|" +
	            		"totalBytes|" +
	            		"finishedBytes";
	            	jInput.setText(res);
	            }
	        }
	    }
	};
	
	private ActionListener listenExecute = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
	        if (jExecute == e.getSource()) {//如果是jBtn这个按钮被点击了,
	        	
	        	String cmd = jInput.getText();
	        	
	            Reply = SWRE.execute(cmd);
	            jResult.setText("executing ...");
	            
	            if(Reply == null) {
	            	jResult.setText("NULL");
	            	return;
	            }
	            if(Reply instanceof Replies.CloseServer) {
	            	Replies.CloseServer reply = (Replies.CloseServer)Reply;
	            	jResult.setText(reply.toString());
	            	return;
	            }
	            
	            jResult.setText(Reply.toString());
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


