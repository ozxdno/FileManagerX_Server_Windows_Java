package com.FileManagerX.Forms;

import java.awt.event.*;
import javax.swing.*;

import com.FileManagerX.Globals.*;

@SuppressWarnings("serial")
public class Test extends JFrame {

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

	public Test() {
        
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
		if(Datas.Form_Test == null) {
			return;
		}
		
		int wForm = Datas.Form_Test.getSize().width;
		//int hForm = Datas.Form_Test.getSize().height;
		
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
		
		Datas.Form_Test.jInput.setLocation(xInput, yInput);
		Datas.Form_Test.jInput.setSize(wInput, hInput);
		Datas.Form_Test.jResult.setLocation(xResult, yResult);
		Datas.Form_Test.jResult.setSize(wResult, hResult);
		Datas.Form_Test.jExecute.setLocation(xExecute, yExecute);
		Datas.Form_Test.jExecute.setSize(wExecute, hExecute);
		Datas.Form_Test.jQuery.setLocation(xQuery, yQuery);
		Datas.Form_Test.jQuery.setSize(wQuery, hQuery);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private ActionListener listenQuery = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
	        if (jQuery == e.getSource()) {//如果是jBtn这个按钮被点击了,
	            
	        }
	    }
	};
	
	private ActionListener listenExecute = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
	        if (jExecute == e.getSource()) {//如果是jBtn这个按钮被点击了,
	        	
	        }
	    }
	};
	
	private ComponentAdapter listenFrameResize = new ComponentAdapter() {
		public void componentResized(ComponentEvent e){
			Datas.Form_Test.resizeForm();
		}
	};
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}


