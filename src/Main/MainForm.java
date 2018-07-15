package Main;

import java.awt.event.*;
import javax.swing.*;

// reset
// path
// errors
// tip

// Input = 2|3|D:\Space_For_Media\Pictures\FMX_Test_Depot_E\a2.jpg|D:\Space_For_Media\Pictures\FMX_Test_Depot_C\a16.jpg
// @Input = 5|6|D:\Space_For_Media\Pictures\FMX_Test_Depot_E\a2.jpg|D:\Space_For_Media\Pictures\FMX_Test_Depot_C\a15.jpg
// Input = 5|6|D:\Space_For_Media\Pictures\FMX_Test_Depot_E\a2.jpg|D:\Space_For_Media\Pictures\FMX_Test_Depot_C\a10.jpg
// Input = D:\Space_For_Media\Pictures\FMX_Test_Depot_A\阿九\a2.jpg|D:\Space_For_Media\Pictures\FMX_Test_Depot_C\a2.jpg
// Input = D:\Space_For_Media\Pictures\FMX_Test_Depot_A\新建文件夹\a1.rar|D:\Space_For_Media\Pictures\FMX_Test_Depot_C\a4.rar
// Input = D:\Space_For_Media\Pictures\FMX_Test_Depot_A\新建文件夹\a1.rar|D:\Space_For_Media\Pictures\FMX_Test_Depot_C\a5.rar|2366464
// Output = 5|6|D:\Space_For_Media\Pictures\FMX_Test_Depot_C\a2.jpg|D:\Space_For_Media\Pictures\FMX_Test_Depot_E\a2.jpg
// Output = D:\Space_For_Media\Pictures\FMX_Test_Depot_C\a2.jpg|D:\Space_For_Media\Pictures\FMX_Test_Depot_A\a3.jpg
// UpdateFile = 6|9|6|1|-1|-1|-1|D:\Space_For_Media\Pictures\FMX_Test_Depot_E\Konachan.com - 50234 sample.jpg|Unsupport|NotExistInRemote|1513230724366|2284384|0|a new Tag
// UpdateInvitation = invitation01|2|FileManagerX|ozxdno|**********|ozxdno@126.com||Offline|Admin|Level9|0||0|0.0
// UpdateInvitation = invitation01|0||||||Offline|Admin|Level9|0||9999|0.0
// OperateDepot = OPEN_IN_SYSTEM|5||D:\Space_For_Media\Pictures\FMX_Test_Depot_A\1 (4).jpg
// OperateDepot = CREATE_FOLDER|5||D:\Space_For_Media\Pictures\FMX_Test_Depot_A\NEW FOLDER
// OperateDepot = RENAME_FILE_WITHOUT_EXTENSION|5|D:\Space_For_Media\Pictures\FMX_Test_Depot_A\a2.jpg|BBBB
// OperateDepot = MOVE_FOLDER|5|D:\Space_For_Media\Pictures\FMX_Test_Depot_A\阿九阿九|D:\Space_For_Media\Pictures\FMX_Test_Depot_A\BBBB\A9A9

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
	            if(f.equals("tip")) {
	            	List.clear();
	            	
	            	List.add("    Tip");
	            	List.add("    Prev");
	            	List.add("    Next");
	            	List.add("    Direct    d    @");
	            	List.add("    CloseServer    cs");
	            	List.add("    Restart    r");
	            	List.add("    Test    t");
	            	List.add("    TimeForExecute    tfe");
	            	List.add("    PrintScreen    ps");
	            	List.add("    OperateDepot    od");
	            	List.add("    Input    i");
	            	List.add("    Output    o");
	            	List.add("    RegisterUser    rgu");
	            	List.add("    LoginUser    lu");
	            	List.add("    LoginMachine    lm");
	            	List.add("    LoginType    lt");
	            	List.add("    QueryConfigurations    qcs");
	            	List.add("    QueryMachines    qms");
	            	List.add("    QueryDepots    qds");
	            	List.add("    QueryDataBases    qdbs");
	            	List.add("    QueryUsers    qus");
	            	List.add("    QueryInvitations    qis");
	            	List.add("    QueryFolders    qfds");
	            	List.add("    QueryFiles    qfs");
	            	List.add("    QueryMachine    qm");
	            	List.add("    QueryDepot    qd");
	            	List.add("    QueryDataBase    qdb");
	            	List.add("    QueryUser    qu");
	            	List.add("    QueryInvitation    qi");
	            	List.add("    QueryFolder    qfd");
	            	List.add("    QueryFile    qf");
	            	List.add("    QuerySize    qsz");
	            	List.add("    UpdateMachine    um");
	            	List.add("    UpdateDepot    ud");
	            	List.add("    UpdateDataBase    udb");
	            	List.add("    UpdateUser    uu");
	            	List.add("    UpdateInvitation    ui");
	            	List.add("    UpdateFolder    ufd");
	            	List.add("    UpdateFile    uf");
	            	List.add("    UpdateMachines    ums");
	            	List.add("    UpdateDepots    uds");
	            	List.add("    UpdateDataBases    udbs");
	            	List.add("    UpdateUsers    uus");
	            	List.add("    UpdateInvitations    uis");
	            	List.add("    UpdateFolders    ufds");
	            	List.add("    UpdateFiles    ufs");
	            	List.add("    RemoveMachines    rms");
	            	List.add("    RemoveDepots    rds");
	            	List.add("    RemoveDataBases    rdbs");
	            	List.add("    RemoveUsers    rus");
	            	List.add("    RemoveInvitations    ris");
	            	List.add("    RemoveFolders    rfds");
	            	List.add("    RemoveFiles    rfs");
	            	List.add("    RemoveMachine    rm");
	            	List.add("    RemoveDepot    rd");
	            	List.add("    RemoveDataBase    rdb");
	            	List.add("    RemoveUser    ru");
	            	List.add("    RemoveInvitation    ri");
	            	List.add("    RemoveFolder    rfd");
	            	List.add("    RemoveFile    rf");
	            	List.add("    MatchPicture    mpic");
	            	
	            	List.add("    QuerySpecificFile    qsp");
	            	List.add("    QuerySpecificFiles    qsps");
	            	List.add("    RemoveSpecificFile    rsp");
	            	List.add("    RemoveSpecificFile    rsps");
	            	List.add("    UpdateSpecificFile    usp");
	            	List.add("    UpdateSpecificFile    usps");
	            	List.add("    OperateMatch    om");
	            	List.add("    OutputMatchFile    omf");
	            	
	            	for(int i=0; i<List.size(); i++) {
	            		List.set(i, "[" + (i+1) + "/" + List.size() + "]" + List.get(i));
	            	}
	            	
	            	jInput.setText("Tip = ");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("direct") || f.equals("d") || f.equals("@")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/1][sourMachineIndex][destMachineIndex][Other Args]";
	            	List.add(tip);
	            	
	            	jInput.setText("@ = ");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("closeserver") || f.equals("cs")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/2][No Args]";
	            	List.add(tip);
	            	tip = "[2/2][destMachineIndex]";
	            	List.add(tip);
	            	
	            	jInput.setText("CloseServer = ");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("restart") || f.equals("r")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/2][No Args]";
	            	List.add(tip);
	            	tip = "[2/2][destMachineIndex]";
	            	List.add(tip);
	            	
	            	jInput.setText("Restart = ");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("timeforexecute") || f.equals("tfe")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/1][destMachineIndex][receiveWaitTicks][sendWaitTicks]";
	            	List.add(tip);
	            	
	            	jInput.setText("TimeForExecute = ");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("printscreen") || f.equals("ps")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/2][No Args]";
	            	List.add(tip);
	            	tip = "[2/2][destMachineIndex]";
	            	List.add(tip);
	            	
	            	jInput.setText("PrintScreen = ");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("operatedepot") || f.equals("od")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/2][operateType][destDepotIndex][sourUrl][destUrl]";
	            	List.add(tip);
	            	tip = "[2/2][operateType][destMachineIndex][destDepotIndex][sourUrl][destUrl]";
	            	List.add(tip);
	            	
	            	jInput.setText("OperateDepot = ");
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
	            	
	            	jInput.setText("Test = This is a Test String.");
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
	            	
	            	jInput.setText("Input = ");
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
	            	
	            	jInput.setText("Output = ");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("registeruser") || f.equals("rgu")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/1][invitationCode][user]";
	            	List.add(tip);
	            	
	            	jInput.setText("RegisterUser = ");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("loginuser") || f.equals("lu")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/1][loginName][password]";
	            	List.add(tip);
	            	
	            	jInput.setText("LoginUser = ");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("loginmachine") || f.equals("lu")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/1][machineInfo]";
	            	List.add(tip);
	            	
	            	jInput.setText("LoginMachine = ");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("logintype") || f.equals("lt")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/1][type]";
	            	List.add(tip);
	            	
	            	jInput.setText("LoginType = ");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("queryconfigurations") || f.equals("qcs")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/1][No Args]";
	            	List.add(tip);
	            	
	            	jInput.setText("QueryConfigurations = ");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("querymachines") || f.equals("qms")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/2][No Args]";
	            	List.add(tip);
	            	tip = "[2/2][conditions]";
	            	List.add(tip);
	            	
	            	jInput.setText("QueryMachines = [&] Index = 1");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("querydepots") || f.equals("qds")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/2][No Args]";
	            	List.add(tip);
	            	tip = "[2/2][conditions]";
	            	List.add(tip);
	            	
	            	jInput.setText("QueryDepots = [&] Index = 1");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("querydatabases") || f.equals("qdbs")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/2][No Args]";
	            	List.add(tip);
	            	tip = "[2/2][conditions]";
	            	List.add(tip);
	            	
	            	jInput.setText("QueryDataBases = [&] Index = 1");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("queryusers") || f.equals("qus")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/2][No Args]";
	            	List.add(tip);
	            	tip = "[2/2][conditions]";
	            	List.add(tip);
	            	
	            	jInput.setText("QueryUsers = [&] Index = 1");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("queryinvitations") || f.equals("qis")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/2][No Args]";
	            	List.add(tip);
	            	tip = "[2/2][conditions]";
	            	List.add(tip);
	            	
	            	jInput.setText("QueryInvitations = [&] Code = '1'");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("queryfolders") || f.equals("qfds")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/2][destDepot][conditions]";
	            	List.add(tip);
	            	tip = "[2/2][destMachine][destDepot][conditions]";
	            	List.add(tip);
	            	
	            	jInput.setText("QueryFolders = ");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("queryfiles") || f.equals("qfs")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/2][destDepot][conditions]";
	            	List.add(tip);
	            	tip = "[2/2][destMachine][destDepot][conditions]";
	            	List.add(tip);
	            	
	            	jInput.setText("QueryFiles = ");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("querymachine") || f.equals("qm")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/1][conditions]";
	            	List.add(tip);
	            	
	            	jInput.setText("QueryMachine = [&] Index = 1");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("querydepot") || f.equals("qd")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/1][conditions]";
	            	List.add(tip);
	            	
	            	jInput.setText("QueryDepot = [&] Index = 1");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("querydatabase") || f.equals("qdb")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/1][conditions]";
	            	List.add(tip);
	            	
	            	jInput.setText("QueryDataBase = [&] Index = 1");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("queryuser") || f.equals("qu")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/1][conditions]";
	            	List.add(tip);
	            	
	            	jInput.setText("QueryUser = [&] Index = 1");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("queryinvitation") || f.equals("qi")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/1][conditions]";
	            	List.add(tip);
	            	
	            	jInput.setText("QueryInvitation = [&] Code = '1'");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("queryfolder") || f.equals("qfd")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/2][destDepot][conditions]";
	            	List.add(tip);
	            	tip = "[2/2][destMachine][destDepot][conditions]";
	            	List.add(tip);
	            	
	            	jInput.setText("QueryFolder = ");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("queryfile") || f.equals("qf")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/2][destDepot][conditions]";
	            	List.add(tip);
	            	tip = "[2/2][destMachine][destDepot][conditions]";
	            	List.add(tip);
	            	
	            	jInput.setText("QueryFile = ");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("querysize") || f.equals("qsz")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/3][queryItem]";
	            	List.add(tip);
	            	tip = "[2/3][destDepot][queryItem]";
	            	List.add(tip);
	            	tip = "[2/3][destMachine][destDepot][queryItem]";
	            	List.add(tip);
	            	
	            	jInput.setText("QuerySize = ");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("updatemachine") || f.equals("um")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/1][machineInfo]";
	            	List.add(tip);
	            	
	            	jInput.setText("UpdateMachine = ");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("updatedepot") || f.equals("ud")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/1][depotInfo]";
	            	List.add(tip);
	            	
	            	jInput.setText("UpdateDepot = ");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("updatedatabase") || f.equals("udb")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/1][databaseInfo]";
	            	List.add(tip);
	            	
	            	jInput.setText("UpdateDataBase = ");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("updateuser") || f.equals("uu")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/1][user]";
	            	List.add(tip);
	            	
	            	jInput.setText("UpdateUser = ");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("updateinvitation") || f.equals("ui")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/1][invitation]";
	            	List.add(tip);
	            	
	            	jInput.setText("UpdateInvitation = ");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("updatefolder") || f.equals("ufd")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/2][destDepot][folder]";
	            	List.add(tip);
	            	tip = "[2/2][destMachine][destDepot][folder]";
	            	List.add(tip);
	            	
	            	jInput.setText("UpdateFolder = ");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("updatefile") || f.equals("uf")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/2][destDepot][basefile]";
	            	List.add(tip);
	            	tip = "[2/2][destMachine][destDepot][basefile]";
	            	List.add(tip);
	            	
	            	jInput.setText("UpdateFile = ");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("updatemachines") || f.equals("ums")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/1][machineIndex][items][conditions]";
	            	List.add(tip);
	            	
	            	jInput.setText("UpdateMachines = ");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("updatedepots") || f.equals("uds")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/1][depotIndex][items][conditions]";
	            	List.add(tip);
	            	
	            	jInput.setText("UpdateDepots = ");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("updatedatabases") || f.equals("udbs")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/1][databaseIndex][items][conditions]";
	            	List.add(tip);
	            	
	            	jInput.setText("UpdateDataBases = ");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("updateusers") || f.equals("uus")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/1][userIndex][items][conditions]";
	            	List.add(tip);
	            	
	            	jInput.setText("UpdateUsers = ");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("updateinvitations") || f.equals("uis")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/1][invitationCode][items][conditions]";
	            	List.add(tip);
	            	
	            	jInput.setText("UpdateInvitations = ");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("updatefolders") || f.equals("ufds")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/2][destDepot][folderIndex][items][conditions]";
	            	List.add(tip);
	            	tip = "[2/2][destMachine][destDepot][folderIndex][items][conditions]";
	            	List.add(tip);
	            	
	            	jInput.setText("UpdateFolders = ");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("updatefiles") || f.equals("ufs")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/2][destDepot][fileIndex][items][conditions]";
	            	List.add(tip);
	            	tip = "[2/2][destMachine][destDepot][fileIndex][items][conditions]";
	            	List.add(tip);
	            	
	            	jInput.setText("UpdateFiles = ");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("removemachines") || f.equals("rms")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/1][conditions]";
	            	List.add(tip);
	            	
	            	jInput.setText("RemoveMachines = [&] Index = 1");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("removedepots") || f.equals("rds")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/1][conditions]";
	            	List.add(tip);
	            	
	            	jInput.setText("RemoveDepots = [&] Index = 1");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("removedatabases") || f.equals("rdbs")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/1][conditions]";
	            	List.add(tip);
	            	
	            	jInput.setText("RemoveDataBases = [&] Index = 1");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("removeusers") || f.equals("rus")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/1][conditions]";
	            	List.add(tip);
	            	
	            	jInput.setText("RemoveUsers = [&] Index = 1");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("removeinvitations") || f.equals("ris")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/1][conditions]";
	            	List.add(tip);
	            	
	            	jInput.setText("RemoveInvitations = [&] Code = '1'");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("removefolders") || f.equals("rfds")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/2][destDepot][conditions]";
	            	List.add(tip);
	            	tip = "[2/2][destMachine][destDepot][conditions]";
	            	List.add(tip);
	            	
	            	jInput.setText("RemoveFolders = ");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("removefiles") || f.equals("rfs")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/2][destDepot][conditions]";
	            	List.add(tip);
	            	tip = "[2/2][destMachine][destDepot][conditions]";
	            	List.add(tip);
	            	
	            	jInput.setText("RemoveFiles = ");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("removemachine") || f.equals("rm")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/1][conditions]";
	            	List.add(tip);
	            	
	            	jInput.setText("RemoveMachine = [&] Index = 1");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("removedepot") || f.equals("rd")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/1][conditions]";
	            	List.add(tip);
	            	
	            	jInput.setText("RemoveDepot = [&] Index = 1");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("removedatabase") || f.equals("rdb")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/1][conditions]";
	            	List.add(tip);
	            	
	            	jInput.setText("RemoveDataBase = [&] Index = 1");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("removeuser") || f.equals("ru")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/1][conditions]";
	            	List.add(tip);
	            	
	            	jInput.setText("RemoveUser = [&] Index = 1");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("removeinvitation") || f.equals("ri")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/1][conditions]";
	            	List.add(tip);
	            	
	            	jInput.setText("RemoveInvitation = [&] Code = '1'");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("removefolder") || f.equals("rfd")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/2][destDepot][conditions]";
	            	List.add(tip);
	            	tip = "[2/2][destMachine][destDepot][conditions]";
	            	List.add(tip);
	            	
	            	jInput.setText("RemoveFolder = ");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("removefile") || f.equals("rf")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/2][destDepot][conditions]";
	            	List.add(tip);
	            	tip = "[2/2][destMachine][destDepot][conditions]";
	            	List.add(tip);
	            	
	            	jInput.setText("RemoveFile = ");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("queryspecificfile") || f.equals("qsp")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/2][destDepot][fileType][conditions]";
	            	List.add(tip);
	            	tip = "[2/2][destMachine][destDepot][fileType][conditions]";
	            	List.add(tip);
	            	
	            	jInput.setText("QuerySpecificFile = ");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("queryspecificfiles") || f.equals("qsps")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/2][destDepot][fileType][conditions]";
	            	List.add(tip);
	            	tip = "[2/2][destMachine][destDepot][fileType][conditions]";
	            	List.add(tip);
	            	
	            	jInput.setText("QuerySpecificFiles = ");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("removespecificfile") || f.equals("rsp")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/2][destDepot][fileType][conditions]";
	            	List.add(tip);
	            	tip = "[2/2][destMachine][destDepot][fileType][conditions]";
	            	List.add(tip);
	            	
	            	jInput.setText("RemoveSpecificFile = ");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("removespecificfiles") || f.equals("rsps")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/2][destDepot][fileType][conditions]";
	            	List.add(tip);
	            	tip = "[2/2][destMachine][destDepot][fileType][conditions]";
	            	List.add(tip);
	            	
	            	jInput.setText("RemoveSpecificFiles = ");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("updatespecificfile") || f.equals("usp")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/1][destMachine][destDepot][fileType][file]";
	            	List.add(tip);
	            	
	            	jInput.setText("UpdateSpecificFile = ");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("updatespecificfiles") || f.equals("usps")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/1][destMachine][destDepot][fileType][fileModel][items][conditions]";
	            	List.add(tip);
	            	
	            	jInput.setText("UpdateSpecificFiles = ");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("operatematch") || f.equals("om")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/1][destMachine][fileType][matchArgs]";
	            	List.add(tip);
	            	
	            	jInput.setText("OperateMatch = ");
	            	jResult.setText(List.get(0));
	            	ListIndex = 0;
	            	return;
	            }
	            if(f.equals("outputmatchfile") || f.equals("omf")) {
	            	String tip = "";
	            	List.clear();
	            	
	            	tip = "[1/3][sourUrl]";
	            	List.add(tip);
	            	tip = "[2/3][destMachine][sourUrl]";
	            	List.add(tip);
	            	tip = "[3/3][destMachine][sourUrl][destUrl]";
	            	List.add(tip);
	            	
	            	jInput.setText("OutputMatchFile = ");
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
	        		
	        		if(CmdName.equals("Input") || CmdName.equals("Output")|| CmdName.equals("PrintScreen")) {
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
	        	else if(CmdName.equals("Input") || CmdName.equals("Output") || CmdName.equals("PrintScreen")) {
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
	        		 if(CmdName.equals("Restart")) {
		        			if(cmdcfg.getItemsSize() == 0) {
		 	        			boolean ok = cm.restart();
		 	        			String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
		 	        			jResult.setText(ok ? "Successed" : "Failed: " + reason);
		 	        			return;
		 	        		}
		        			if(cmdcfg.getItemsSize() == 1) {
		 	        			boolean ok = cm.restart(cmdcfg.getLong(0));
		 	        			String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
		 	        			jResult.setText(ok ? "Successed" : "Failed: " + reason);
		 	        			return;
		 	        		}
		 	            }
	        		 if(CmdName.equals("TimeForExecute")) {
		        			if(cmdcfg.getItemsSize() == 3) {
		 	        			boolean ok = cm.timeForExecute(cmdcfg.getLong(0), cmdcfg.getLong(1), cmdcfg.getLong(2));
		 	        			String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
		 	        			jResult.setText(ok ? "Successed" : "Failed: " + reason);
		 	        			return;
		 	        		}
		 	            }
	        		 if(CmdName.equals("PrintScreen")) {
		        			if(cmdcfg.getItemsSize() == 0) {
		 	        			boolean ok = cm.printScreen();
		 	        			String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
		 	        			jResult.setText(ok ? "Successed" : "Failed: " + reason);
		 	        			return;
		 	        		}
		        			if(cmdcfg.getItemsSize() == 1) {
		 	        			boolean ok = cm.printScreen(cmdcfg.getLong(0));
		 	        			String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
		 	        			jResult.setText(ok ? "Successed" : "Failed: " + reason);
		 	        			return;
		 	        		}
		 	            }
	        		 if(CmdName.equals("OperateDepot")) {
		        			if(cmdcfg.getItemsSize() == 4) {
		        				BasicEnums.OperateType type = BasicEnums.OperateType.valueOf(cmdcfg.getString(0));
		        				long depot = cmdcfg.getLong(1);
		        				String sour = cmdcfg.getString(2);
		        				String dest = cmdcfg.getString(3);
		 	        			boolean ok = cm.operateDepot(type, depot, sour, dest);
		 	        			String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
		 	        			jResult.setText(ok ? "Successed" : "Failed: " + reason);
		 	        			return;
		 	        		}
		        			if(cmdcfg.getItemsSize() == 5) {
		        				BasicEnums.OperateType type = BasicEnums.OperateType.valueOf(cmdcfg.getString(0));
		        				long machine = cmdcfg.getLong(1);
		        				long depot = cmdcfg.getLong(2);
		        				String sour = cmdcfg.getString(3);
		        				String dest = cmdcfg.getString(4);
		 	        			boolean ok = cm.operateDepot(type, machine, depot, sour, dest);
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
	 	           if(CmdName.equals("RegisterUser")) {
	 	        	   	String code = cmdcfg.fetchFirstString();
	 	        	   	BasicModels.User user = new BasicModels.User();
	 	        	   	String res = user.input(cmdcfg.output());
	        			if(res != null) {
	 	        			boolean ok = cm.registerUser(code, user);
	 	        			String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
	 	        			jResult.setText(ok ? "Successed" : "Failed: " + reason);
	 	        			return;
	 	        		}
	 	            }
	 	           if(CmdName.equals("LoginUser")) {
	        			if(cmdcfg.getItemsSize() == 2) {
	        				cm.getConnection().getUser().setLoginName(cmdcfg.getString(0));
	        				cm.getConnection().getUser().setPassword(cmdcfg.getString(1));
	 	        			boolean ok = cm.loginUser();
	 	        			String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
	 	        			jResult.setText(ok ? "Successed" : "Failed: " + reason);
	 	        			return;
	 	        		}
	 	            }
	 	           if(CmdName.equals("LoginMachine")) {
	 	        	   	BasicModels.MachineInfo machine = new BasicModels.MachineInfo();
	 	        	   	String res = machine.input(cmdcfg.output());
	        			if(res != null) {
	        				cm.getConnection().setClientMachineInfo(machine);
	 	        			boolean ok = cm.loginMachine();
	 	        			String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
	 	        			jResult.setText(ok ? "Successed" : "Failed: " + reason);
	 	        			return;
	 	        		}
	 	            }
	 	           if(CmdName.equals("LoginType")) {
	        			if(cmdcfg.getItemsSize() == 1) {
	        				cm.getConnection().setType(BasicEnums.ConnectionType.valueOf(cmdcfg.getString(0)));
	 	        			boolean ok = cm.loginType();
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
	 	           	if(CmdName.equals("QueryConfigurations")) {
	 	           		if(cmdcfg.getItemsSize() == 0) {
	 	           			Replies.QueryConfigurations rep = cm.queryConfigurations();
	 	           			if(rep == null) {
	 	           				jResult.setText("Failed: NULL");
	 	           				return;
	 	           			}
	 	           			if(!rep.isOK()) {
	 	           				jResult.setText("Failed: " + rep.getFailedReason());
	 	           				return;
	 	           			}
	 	           			List.clear();
	 	           			ListIndex = 0;
	 	           			String item = "";
	 	           			
	 	           			item = "[1/10]Next_FileIndex = " + rep.getNext_FileIndex();
	 	           			List.add(item);
		 	           		item = "[2/10]Next_UserIndex = " + rep.getNext_UserIndex();
	 	           			List.add(item);
	 	           			item = "[3/10]Next_MachineIndex = " + rep.getNext_MachineIndex();
	 	           			List.add(item);
	 	           			item = "[4/10]Next_DepotIndex = " + rep.getNext_DepotIndex();
		           			List.add(item);
		           			item = "[5/10]Next_DataBaseIndex = " + rep.getNext_DataBaseIndex();
	 	           			List.add(item);
	 	           			item = "[6/10]This_MachineIndex = " + rep.getThis_MachineIndex();
		           			List.add(item);
		           			item = "[7/10]This_UserIndex = " + rep.getThis_UserIndex();
	 	           			List.add(item);
	 	           			item = "[8/10]Server_MachineIndex = " + rep.getServer_MachineIndex();
		           			List.add(item);
		           			item = "[9/10]Server_UserIndex = " + rep.getServer_UserIndex();
	 	           			List.add(item);
	 	           			item = "[10/10]NetType = " + rep.getNetType().toString();
	 	           			List.add(item);
	 	           			
	 	           			jInput.setText("QueryConfigurations = next");
	 	           			jResult.setText(List.get(ListIndex));
	 	           			return;
	 	           		}
	 	           	}
	 	           if(CmdName.equals("QueryMachines")) {
	 	            	if(cmdcfg.getItemsSize() == 0) {
	 	           			BasicCollections.MachineInfos ms = cm.queryMachines("");
	 	           			if(ms == null) {
	 	           				jResult.setText("Failed: NULL");
	 	           				return;
	 	           			}
	 	           			if(ms.size() == 0) {
	 	           				jResult.setText("Successed: Empty");
	 	           				return;
	 	           			}
	 	           			
	 	           			List.clear();
	 	           			ListIndex = 0;
	 	           			String item = "";
	 	           			
	 	           			for(int i=0; i<ms.size(); i++) {
	 	           				item = "[" + (i+1) + "/" + ms.size() + "]" + ms.getContent().get(i).output();
	 	           				List.add(item);
	 	           			}
	 	           			
	 	           			jInput.setText("QueryMachines = next");
	 	           			jResult.setText(List.get(ListIndex));
	 	           			return;
	 	           		}
	 	            	if(cmdcfg.getItemsSize() == 1) {
	 	           			BasicCollections.MachineInfos ms = cm.queryMachines(cmdcfg.getString(0));
	 	           			if(ms == null) {
	 	           				jResult.setText("Failed: NULL");
	 	           				return;
	 	           			}
	 	           			if(ms.size() == 0) {
	 	           				jResult.setText("Successed: Empty");
	 	           				return;
	 	           			}
	 	           			
	 	           			List.clear();
	 	           			ListIndex = 0;
	 	           			String item = "";
	 	           			
	 	           			for(int i=0; i<ms.size(); i++) {
	 	           				item = "[" + (i+1) + "/" + ms.size() + "]" + ms.getContent().get(i).output();
	 	           				List.add(item);
	 	           			}
	 	           			
	 	           			jResult.setText(List.get(ListIndex));
	 	           			return;
	 	           		}
	 	            }
	 	            if(CmdName.equals("QueryDepots")) {
	 	            	if(cmdcfg.getItemsSize() == 0) {
	 	           			BasicCollections.DepotInfos ds = cm.queryDepots("");
	 	           			if(ds == null) {
	 	           				jResult.setText("Failed: NULL");
	 	           				return;
	 	           			}
	 	           			if(ds.size() == 0) {
	 	           				jResult.setText("Successed: Empty");
	 	           				return;
	 	           			}
	 	           			
	 	           			List.clear();
	 	           			ListIndex = 0;
	 	           			String item = "";
	 	           			
	 	           			for(int i=0; i<ds.size(); i++) {
	 	           				item = "[" + (i+1) + "/" + ds.size() + "]" + ds.getContent().get(i).output();
	 	           				List.add(item);
	 	           			}
	 	           			
	 	           			jInput.setText("QueryDepots = next");
	 	           			jResult.setText(List.get(ListIndex));
	 	           			return;
	 	           		}
	 	            	if(cmdcfg.getItemsSize() == 1) {
	 	           			BasicCollections.DepotInfos ds = cm.queryDepots(cmdcfg.getString(0));
	 	           			if(ds == null) {
	 	           				jResult.setText("Failed: NULL");
	 	           				return;
	 	           			}
	 	           			if(ds.size() == 0) {
	 	           				jResult.setText("Successed: Empty");
	 	           				return;
	 	           			}
	 	           			
	 	           			List.clear();
	 	           			ListIndex = 0;
	 	           			String item = "";
	 	           			
	 	           			for(int i=0; i<ds.size(); i++) {
	 	           				item = "[" + (i+1) + "/" + ds.size() + "]" + ds.getContent().get(i).output();
	 	           				List.add(item);
	 	           			}
	 	           			
	 	           			jResult.setText(List.get(ListIndex));
	 	           			return;
	 	           		}
	 	            }
	 	           if(CmdName.equals("QueryDataBases")) {
	 	            	if(cmdcfg.getItemsSize() == 0) {
	 	           			BasicCollections.DataBaseInfos dbs = cm.queryDataBases("");
	 	           			if(dbs == null) {
	 	           				jResult.setText("Failed: NULL");
	 	           				return;
	 	           			}
	 	           			if(dbs.size() == 0) {
	 	           				jResult.setText("Successed: Empty");
	 	           				return;
	 	           			}
	 	           			
	 	           			List.clear();
	 	           			ListIndex = 0;
	 	           			String item = "";
	 	           			
	 	           			for(int i=0; i<dbs.size(); i++) {
	 	           				item = "[" + (i+1) + "/" + dbs.size() + "]" + dbs.getContent().get(i).output();
	 	           				List.add(item);
	 	           			}
	 	           			
	 	           			jInput.setText("QueryDataBases = next");
	 	           			jResult.setText(List.get(ListIndex));
	 	           			return;
	 	           		}
	 	            	if(cmdcfg.getItemsSize() == 1) {
	 	           			BasicCollections.DataBaseInfos dbs = cm.queryDataBases(cmdcfg.getString(0));
	 	           			if(dbs == null) {
	 	           				jResult.setText("Failed: NULL");
	 	           				return;
	 	           			}
	 	           			if(dbs.size() == 0) {
	 	           				jResult.setText("Successed: Empty");
	 	           				return;
	 	           			}
	 	           			
	 	           			List.clear();
	 	           			ListIndex = 0;
	 	           			String item = "";
	 	           			
	 	           			for(int i=0; i<dbs.size(); i++) {
	 	           				item = "[" + (i+1) + "/" + dbs.size() + "]" + dbs.getContent().get(i).output();
	 	           				List.add(item);
	 	           			}
	 	           			
	 	           			jResult.setText(List.get(ListIndex));
	 	           			return;
	 	           		}
	 	            }
	 	           if(CmdName.equals("QueryUsers")) {
	 	            	if(cmdcfg.getItemsSize() == 0) {
	 	           			BasicCollections.Users users = cm.queryUsers("");
	 	           			if(users == null) {
	 	           				jResult.setText("Failed: NULL");
	 	           				return;
	 	           			}
	 	           			if(users.size() == 0) {
	 	           				jResult.setText("Successed: Empty");
	 	           				return;
	 	           			}
	 	           			
	 	           			List.clear();
	 	           			ListIndex = 0;
	 	           			String item = "";
	 	           			
	 	           			for(int i=0; i<users.size(); i++) {
	 	           				item = "[" + (i+1) + "/" + users.size() + "]" + users.getContent().get(i).output();
	 	           				List.add(item);
	 	           			}
	 	           			
	 	           			jInput.setText("QueryUsers = next");
	 	           			jResult.setText(List.get(ListIndex));
	 	           			return;
	 	           		}
	 	            	if(cmdcfg.getItemsSize() == 1) {
	 	           			BasicCollections.Users users = cm.queryUsers(cmdcfg.getString(0));
	 	           			if(users == null) {
	 	           				jResult.setText("Failed: NULL");
	 	           				return;
	 	           			}
	 	           			if(users.size() == 0) {
	 	           				jResult.setText("Successed: Empty");
	 	           				return;
	 	           			}
	 	           			
	 	           			List.clear();
	 	           			ListIndex = 0;
	 	           			String item = "";
	 	           			
	 	           			for(int i=0; i<users.size(); i++) {
	 	           				item = "[" + (i+1) + "/" + users.size() + "]" + users.getContent().get(i).output();
	 	           				List.add(item);
	 	           			}
	 	           			
	 	           			jResult.setText(List.get(ListIndex));
	 	           			return;
	 	           		}
	 	            }
	 	           if(CmdName.equals("QueryInvitations")) {
	 	            	if(cmdcfg.getItemsSize() == 0) {
	 	           			BasicCollections.Invitations invitations = cm.queryInvitations("");
	 	           			if(invitations == null) {
	 	           				jResult.setText("Failed: NULL");
	 	           				return;
	 	           			}
	 	           			if(invitations.size() == 0) {
	 	           				jResult.setText("Successed: Empty");
	 	           				return;
	 	           			}
	 	           			
	 	           			List.clear();
	 	           			ListIndex = 0;
	 	           			String item = "";
	 	           			
	 	           			for(int i=0; i<invitations.size(); i++) {
	 	           				item = "[" + (i+1) + "/" + invitations.size() + "]" + invitations.getContent().get(i).output();
	 	           				List.add(item);
	 	           			}
	 	           			
	 	           			jInput.setText("QueryInvitations = next");
	 	           			jResult.setText(List.get(ListIndex));
	 	           			return;
	 	           		}
	 	            	if(cmdcfg.getItemsSize() == 1) {
	 	            		BasicCollections.Invitations invitations = cm.queryInvitations(cmdcfg.getString(0));
	 	           			if(invitations == null) {
	 	           				jResult.setText("Failed: NULL");
	 	           				return;
	 	           			}
	 	           			if(invitations.size() == 0) {
	 	           				jResult.setText("Successed: Empty");
	 	           				return;
	 	           			}
	 	           			
	 	           			List.clear();
	 	           			ListIndex = 0;
	 	           			String item = "";
	 	           			
	 	           			for(int i=0; i<invitations.size(); i++) {
	 	           				item = "[" + (i+1) + "/" + invitations.size() + "]" + invitations.getContent().get(i).output();
	 	           				List.add(item);
	 	           			}
	 	           			
	 	           			jResult.setText(List.get(ListIndex));
	 	           			return;
	 	           		}
	 	            }
	 	            if(CmdName.equals("QueryFolders")) {
	 	            	if(cmdcfg.getItemsSize() == 2) {
	 	           			BasicCollections.Folders folders = cm.queryFolders(cmdcfg.getLong(0), cmdcfg.getString(1));
	 	           			if(folders == null) {
	 	           				jResult.setText("Failed: NULL");
	 	           				return;
	 	           			}
	 	           			if(folders.size() == 0) {
	 	           				jResult.setText("Successed: Empty");
	 	           				return;
	 	           			}
	 	           			
	 	           			List.clear();
	 	           			ListIndex = 0;
	 	           			String item = "";
	 	           			
	 	           			for(int i=0; i<folders.size(); i++) {
	 	           				item = "[" + (i+1) + "/" + folders.size() + "]" + folders.getContent().get(i).output();
	 	           				List.add(item);
	 	           			}
	 	           			
	 	           			jResult.setText(List.get(ListIndex));
	 	           			return;
	 	           		}
	 	            	if(cmdcfg.getItemsSize() == 3) {
	 	            		BasicCollections.Folders folders = cm.queryFolders(cmdcfg.getLong(0), cmdcfg.getLong(1), cmdcfg.getString(1));
	 	           			if(folders == null) {
	 	           				jResult.setText("Failed: NULL");
	 	           				return;
	 	           			}
	 	           			if(folders.size() == 0) {
	 	           				jResult.setText("Successed: Empty");
	 	           				return;
	 	           			}
	 	           			
	 	           			List.clear();
	 	           			ListIndex = 0;
	 	           			String item = "";
	 	           			
	 	           			for(int i=0; i<folders.size(); i++) {
	 	           				item = "[" + (i+1) + "/" + folders.size() + "]" + folders.getContent().get(i).output();
	 	           				List.add(item);
	 	           			}
	 	           			
	 	           			jResult.setText(List.get(ListIndex));
	 	           			return;
	 	           		}
	 	            }
	 	           if(CmdName.equals("QueryFiles")) {
	 	            	if(cmdcfg.getItemsSize() == 2) {
	 	           			BasicCollections.BaseFiles files = cm.queryFiles(cmdcfg.getLong(0), cmdcfg.getString(1));
	 	           			if(files == null) {
	 	           				jResult.setText("Failed: NULL");
	 	           				return;
	 	           			}
	 	           			if(files.size() == 0) {
	 	           				jResult.setText("Successed: Empty");
	 	           				return;
	 	           			}
	 	           			
	 	           			List.clear();
	 	           			ListIndex = 0;
	 	           			String item = "";
	 	           			
	 	           			for(int i=0; i<files.size(); i++) {
	 	           				item = "[" + (i+1) + "/" + files.size() + "]" + files.getContent().get(i).output();
	 	           				List.add(item);
	 	           			}
	 	           			
	 	           			jResult.setText(List.get(ListIndex));
	 	           			return;
	 	           		}
	 	            	if(cmdcfg.getItemsSize() == 3) {
	 	            		BasicCollections.BaseFiles files = cm.queryFiles(cmdcfg.getLong(0), cmdcfg.getLong(1), cmdcfg.getString(1));
	 	           			if(files == null) {
	 	           				jResult.setText("Failed: NULL");
	 	           				return;
	 	           			}
	 	           			if(files.size() == 0) {
	 	           				jResult.setText("Successed: Empty");
	 	           				return;
	 	           			}
	 	           			
	 	           			List.clear();
	 	           			ListIndex = 0;
	 	           			String item = "";
	 	           			
	 	           			for(int i=0; i<files.size(); i++) {
	 	           				item = "[" + (i+1) + "/" + files.size() + "]" + files.getContent().get(i).output();
	 	           				List.add(item);
	 	           			}
	 	           			
	 	           			jResult.setText(List.get(ListIndex));
	 	           			return;
	 	           		}
	 	            }
		 	          if(CmdName.equals("QueryMachine")) {
		 	        	 if(cmdcfg.getItemsSize() == 1) {
		 	        		 BasicModels.MachineInfo res = cm.queryMachine(cmdcfg.getString(0));
		 	        		 String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
		 	        		 
		 	        		 jResult.setText(res == null ? "Failed: " + reason : res.output());
		 	        		 return;
		 	        	 }
		 	          }
		 	         if(CmdName.equals("QueryDepot")) {
		 	        	 if(cmdcfg.getItemsSize() == 1) {
		 	        		 BasicModels.DepotInfo res = cm.queryDepot(cmdcfg.getString(0));
		 	        		 String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
		 	        		 
		 	        		 jResult.setText(res == null ? "Failed: " + reason : res.output());
		 	        		 return;
		 	        	 }
		 	          }
		 	        if(CmdName.equals("QueryDataBase")) {
		 	        	 if(cmdcfg.getItemsSize() == 1) {
		 	        		 BasicModels.DataBaseInfo res = cm.queryDataBase(cmdcfg.getString(0));
		 	        		 String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
		 	        		 
		 	        		 jResult.setText(res == null ? "Failed: " + reason : res.output());
		 	        		 return;
		 	        	 }
		 	          }
		 	        if(CmdName.equals("QueryUser")) {
		 	        	 if(cmdcfg.getItemsSize() == 1) {
		 	        		 BasicModels.User res = cm.queryUser(cmdcfg.getString(0));
		 	        		 String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
		 	        		 
		 	        		 jResult.setText(res == null ? "Failed: " + reason : res.output());
		 	        		 return;
		 	        	 }
		 	          }
		 	       	if(CmdName.equals("QueryInvitation")) {
		 	        	 if(cmdcfg.getItemsSize() == 1) {
		 	        		 BasicModels.Invitation res = cm.queryInvitation(cmdcfg.getString(0));
		 	        		 String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
		 	        		 
		 	        		 jResult.setText(res == null ? "Failed: " + reason : res.output());
		 	        		 return;
		 	        	 }
		 	          }
		 	       	if(CmdName.equals("QueryFolder")) {
		 	        	 if(cmdcfg.getItemsSize() == 2) {
		 	        		 BasicModels.Folder res = cm.queryFolder(cmdcfg.getLong(0), cmdcfg.getString(1));
		 	        		 String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
		 	        		 
		 	        		 jResult.setText(res == null ? "Failed: " + reason : res.output());
		 	        		 return;
		 	        	 }
		 	        	if(cmdcfg.getItemsSize() == 3) {
		 	        		 BasicModels.Folder res = cm.queryFolder(cmdcfg.getLong(0), cmdcfg.getLong(1), cmdcfg.getString(2));
		 	        		 String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
		 	        		 
		 	        		 jResult.setText(res == null ? "Failed: " + reason : res.output());
		 	        		 return;
		 	        	 }
		 	          }
			 	   	if(CmdName.equals("QueryFile")) {
		 	        	 if(cmdcfg.getItemsSize() == 2) {
		 	        		 BasicModels.BaseFile res = cm.queryFile(cmdcfg.getLong(0), cmdcfg.getString(1));
		 	        		 String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
		 	        		 
		 	        		 jResult.setText(res == null ? "Failed: " + reason : res.output());
		 	        		 return;
		 	        	 }
		 	        	if(cmdcfg.getItemsSize() == 3) {
		 	        		 BasicModels.BaseFile res = cm.queryFile(cmdcfg.getLong(0), cmdcfg.getLong(1), cmdcfg.getString(2));
		 	        		 String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
		 	        		 
		 	        		 jResult.setText(res == null ? "Failed: " + reason : res.output());
		 	        		 return;
		 	        	 }
		 	          }
			 	   	if(CmdName.equals("QuerySize")) {
				 	   	 if(cmdcfg.getItemsSize() == 1) {
		 	        		 int res = cm.querySize(cmdcfg.getString(0));
		 	        		 String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
		 	        		 
		 	        		 jResult.setText(res < 0 ? "Failed: " + reason : "Size: " + res);
		 	        		 return;
		 	        	 }
		 	        	 if(cmdcfg.getItemsSize() == 2) {
		 	        		 int res = cm.querySize(cmdcfg.getLong(0), cmdcfg.getString(1));
		 	        		 String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
		 	        		 
		 	        		 jResult.setText(res < 0 ? "Failed: " + reason : "Size: " + res);
		 	        		 return;
		 	        	 }
		 	        	if(cmdcfg.getItemsSize() == 3) {
		 	        		int res = cm.querySize(cmdcfg.getLong(0), cmdcfg.getLong(1), cmdcfg.getString(2));
		 	        		 String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
		 	        		 
		 	        		 jResult.setText(res < 0 ? "Failed: " + reason : "Size: " + res);
		 	        		 return;
		 	        	 }
		 	          }
			 	   	if(CmdName.equals("UpdateMachine")) {
			 	   		BasicModels.MachineInfo para = new BasicModels.MachineInfo();
			 	   		int sz = new BasicModels.Config(para.output()).getItemsSize();
	   
			 	   		if(cmdcfg.getItemsSize() == sz) {
			 	   			
				 	   		 String rem = para.input(cmdcfg.output());
				 	   		 if(rem == null) {
				 	   			 jResult.setText("Failed: Input is Wrong");
				 	   		 }
		 	        		 boolean ok = cm.updateMachine(para);
		 	        		 String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
		 	        		 
		 	        		 jResult.setText(ok ? "Successed" : "Failed: " + reason);
		 	        		 return;
		 	        	 }
		 	          }
			 	   	if(CmdName.equals("UpdateDepot")) {
			 	   		BasicModels.DepotInfo para = new BasicModels.DepotInfo();
			 	   		int sz = new BasicModels.Config(para.output()).getItemsSize();
 	   
	 		   			if(cmdcfg.getItemsSize() == sz) {
				 	   		 
				 	   		 String rem = para.input(cmdcfg.output());
				 	   		 if(rem == null) {
				 	   			 jResult.setText("Failed: Input is Wrong");
				 	   		 }
		 	        		 boolean ok = cm.updateDepot(para);
		 	        		 String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
		 	        		 
		 	        		 jResult.setText(ok ? "Successed" : "Failed: " + reason);
		 	        		 return;
		 	        	 }
		 	          }
			 	   if(CmdName.equals("UpdateDataBase")) {
			 		   		BasicModels.DataBaseInfo para = new BasicModels.DataBaseInfo();
			 		   		int sz = new BasicModels.Config(para.output()).getItemsSize();
		 	   
		 	   			if(cmdcfg.getItemsSize() == sz) {
				 	   		 
				 	   		 String rem = para.input(cmdcfg.output());
				 	   		 if(rem == null) {
				 	   			 jResult.setText("Failed: Input is Wrong");
				 	   		 }
		 	        		 boolean ok = cm.updateDataBase(para);
		 	        		 String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
		 	        		 
		 	        		 jResult.setText(ok ? "Successed" : "Failed: " + reason);
		 	        		 return;
		 	        	 }
		 	          }
			 	   	if(CmdName.equals("UpdateUser")) {
			 	   			BasicModels.User para = new BasicModels.User();
			 	   			int sz = new BasicModels.Config(para.output()).getItemsSize();
			 	   
				 	   	 if(cmdcfg.getItemsSize() == sz) {
				 	   		 
				 	   		 String rem = para.input(cmdcfg.output());
				 	   		 if(rem == null) {
				 	   			 jResult.setText("Failed: Input is Wrong");
				 	   		 }
		 	        		 boolean ok = cm.updateUser(para);
		 	        		 String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
		 	        		 
		 	        		 jResult.setText(ok ? "Successed" : "Failed: " + reason);
		 	        		 return;
		 	        	 }
		 	          }
			 	   if(CmdName.equals("UpdateInvitation")) {
			 		   		BasicModels.Invitation para = new BasicModels.Invitation();
			 		   		int sz = new BasicModels.Config(para.output()).getItemsSize();
			 		   
				 	   	 if(cmdcfg.getItemsSize() == sz) {
				 	   		 
				 	   		 String rem = para.input(cmdcfg.output());
				 	   		 if(rem == null) {
				 	   			 jResult.setText("Failed: Input is Wrong");
				 	   		 }
		 	        		 boolean ok = cm.updateInvitation(para);
		 	        		 String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
		 	        		 
		 	        		 jResult.setText(ok ? "Successed" : "Failed: " + reason);
		 	        		 return;
		 	        	 }
		 	          }
			 	   if(CmdName.equals("UpdateFolder")) {
			 		     BasicModels.Folder para = new BasicModels.Folder();
			 		     int sz = new BasicModels.Config(para.output()).getItemsSize();
			 		     
			 		     long machine = cm.getConnection().getServerMachineInfo().getIndex();
			 		     long depot = 0;
				 		 if(cmdcfg.getItemsSize() == sz + 1) {
				 		     depot = cmdcfg.fetchFirstLong();
				 		 }
				 		 if(cmdcfg.getItemsSize() == sz + 2) {
				 			 machine = cmdcfg.fetchFirstLong();
				 		     depot = cmdcfg.fetchFirstLong();
				 		 }
			 		     
				 	   	 if(depot > 0) {
				 	   		 String rem = para.input(cmdcfg.output());
				 	   		 if(rem == null) {
				 	   			 jResult.setText("Failed: Input is Wrong");
				 	   		 }
		 	        		 boolean ok = cm.updateFolder(machine, depot, para);
		 	        		 String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
		 	        		 
		 	        		 jResult.setText(ok ? "Successed" : "Failed: " + reason);
		 	        		 return;
				 	   	 }
		 	          }
				 	 if(CmdName.equals("UpdateFile")) {
			 		     BasicModels.BaseFile para = new BasicModels.BaseFile();
			 		     int sz = new BasicModels.Config(para.output()).getItemsSize();
			 		     
			 		     long machine = cm.getConnection().getServerMachineInfo().getIndex();
			 		     long depot = 0;
				 		 if(cmdcfg.getItemsSize() == sz + 1) {
				 		     depot = cmdcfg.fetchFirstLong();
				 		 }
				 		 if(cmdcfg.getItemsSize() == sz + 2) {
				 			 machine = cmdcfg.fetchFirstLong();
				 		     depot = cmdcfg.fetchFirstLong();
				 		 }
			 		     
				 	   	 if(depot > 0) {
				 	   		 String rem = para.input(cmdcfg.output());
				 	   		 if(rem == null) {
				 	   			 jResult.setText("Failed: Input is Wrong");
				 	   		 }
		 	        		 boolean ok = cm.updateFile(machine, depot, para);
		 	        		 String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
		 	        		 
		 	        		 jResult.setText(ok ? "Successed" : "Failed: " + reason);
		 	        		 return;
				 	   	 }
		 	          }
				 	if(CmdName.equals("UpdateMachines")) {
				 		if(cmdcfg.getItemsSize() == 3) {
				 			boolean ok = cm.updateMachines(cmdcfg.getLong(0), cmdcfg.getString(1), cmdcfg.getString(2));
				 			String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
				 			if(!ok) {
				 				jResult.setText("Failed: " + reason);
				 				return;
				 			}
				 			
				 			BasicCollections.MachineInfos res = ((Replies.UpdateMachines)cm.getReply()).getMachineInfos();
				 			if(res.size() == 0) {
				 				jResult.setText("Successed: No Failed Item");
				 				return;
				 			}
				 			
				 			List.clear();
				 			ListIndex = 0;
				 			
				 			for(int i=0; i<res.size(); i++) {
				 				String str = "[" + (i+1) + "/" + res.size() + "] Failed " + res.getContent().get(i).output();
				 				List.add(str);
				 			}
				 			
				 			jResult.setText(List.get(ListIndex));
				 			return;
				 		}
				 	}
				 	if(CmdName.equals("UpdateDepots")) {
				 		if(cmdcfg.getItemsSize() == 3) {
				 			boolean ok = cm.updateDepots(cmdcfg.getLong(0), cmdcfg.getString(1), cmdcfg.getString(2));
				 			String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
				 			if(!ok) {
				 				jResult.setText("Failed: " + reason);
				 				return;
				 			}
				 			
				 			BasicCollections.DepotInfos res = ((Replies.UpdateDepots)cm.getReply()).getDepotInfos();
				 			if(res.size() == 0) {
				 				jResult.setText("Successed: No Failed Item");
				 				return;
				 			}
				 			
				 			List.clear();
				 			ListIndex = 0;
				 			
				 			for(int i=0; i<res.size(); i++) {
				 				String str = "[" + (i+1) + "/" + res.size() + "] Failed " + res.getContent().get(i).output();
				 				List.add(str);
				 			}
				 			
				 			jResult.setText(List.get(ListIndex));
				 			return;
				 		}
				 	}
				 	if(CmdName.equals("UpdateDataBases")) {
				 		if(cmdcfg.getItemsSize() == 3) {
				 			boolean ok = cm.updateDataBases(cmdcfg.getLong(0), cmdcfg.getString(1), cmdcfg.getString(2));
				 			String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
				 			if(!ok) {
				 				jResult.setText("Failed: " + reason);
				 				return;
				 			}
				 			
				 			BasicCollections.DataBaseInfos res = ((Replies.UpdateDataBases)cm.getReply()).getDBInfos();
				 			if(res.size() == 0) {
				 				jResult.setText("Successed: No Failed Item");
				 				return;
				 			}
				 			
				 			List.clear();
				 			ListIndex = 0;
				 			
				 			for(int i=0; i<res.size(); i++) {
				 				String str = "[" + (i+1) + "/" + res.size() + "] Failed " + res.getContent().get(i).output();
				 				List.add(str);
				 			}
				 			
				 			jResult.setText(List.get(ListIndex));
				 			return;
				 		}
				 	}
				 	if(CmdName.equals("UpdateUsers")) {
				 		if(cmdcfg.getItemsSize() == 3) {
				 			boolean ok = cm.updateUsers(cmdcfg.getLong(0), cmdcfg.getString(1), cmdcfg.getString(2));
				 			String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
				 			if(!ok) {
				 				jResult.setText("Failed: " + reason);
				 				return;
				 			}
				 			
				 			BasicCollections.Users res = ((Replies.UpdateUsers)cm.getReply()).getUsers();
				 			if(res.size() == 0) {
				 				jResult.setText("Successed: No Failed Item");
				 				return;
				 			}
				 			
				 			List.clear();
				 			ListIndex = 0;
				 			
				 			for(int i=0; i<res.size(); i++) {
				 				String str = "[" + (i+1) + "/" + res.size() + "] Failed " + res.getContent().get(i).output();
				 				List.add(str);
				 			}
				 			
				 			jResult.setText(List.get(ListIndex));
				 			return;
				 		}
				 	}
				 	if(CmdName.equals("UpdateInvitations")) {
				 		if(cmdcfg.getItemsSize() == 3) {
				 			boolean ok = cm.updateInvitations(cmdcfg.getString(0), cmdcfg.getString(1), cmdcfg.getString(2));
				 			String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
				 			if(!ok) {
				 				jResult.setText("Failed: " + reason);
				 				return;
				 			}
				 			
				 			BasicCollections.Invitations res = ((Replies.UpdateInvitations)cm.getReply()).getInvitations();
				 			if(res.size() == 0) {
				 				jResult.setText("Successed: No Failed Item");
				 				return;
				 			}
				 			
				 			List.clear();
				 			ListIndex = 0;
				 			
				 			for(int i=0; i<res.size(); i++) {
				 				String str = "[" + (i+1) + "/" + res.size() + "] Failed " + res.getContent().get(i).output();
				 				List.add(str);
				 			}
				 			
				 			jResult.setText(List.get(ListIndex));
				 			return;
				 		}
				 	}
				 	if(CmdName.equals("UpdateFolders")) {
				 		long machineIndex = cm.getConnection().getServerMachineInfo().getIndex();
				 		long depotIndex = 0;
				 		if(cmdcfg.getItemsSize() == 4) {
				 			depotIndex = cmdcfg.fetchFirstLong();
				 		}
				 		if(cmdcfg.getItemsSize() == 5) {
				 			machineIndex = cmdcfg.fetchFirstLong();
				 			depotIndex = cmdcfg.fetchFirstLong();
				 		}
				 		if(depotIndex > 0) {
				 			boolean ok = cm.updateFolders(
				 					machineIndex,
				 					depotIndex,
				 					cmdcfg.getLong(0),
				 					cmdcfg.getString(1),
				 					cmdcfg.getString(2));
				 			String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
				 			if(!ok) {
				 				jResult.setText("Failed: " + reason);
				 				return;
				 			}
				 			
				 			BasicCollections.Folders res = ((Replies.UpdateFolders)cm.getReply()).getFolders();
				 			if(res.size() == 0) {
				 				jResult.setText("Successed: No Failed Item");
				 				return;
				 			}
				 			
				 			List.clear();
				 			ListIndex = 0;
				 			
				 			for(int i=0; i<res.size(); i++) {
				 				String str = "[" + (i+1) + "/" + res.size() + "] Failed " + res.getContent().get(i).output();
				 				List.add(str);
				 			}
				 			
				 			jResult.setText(List.get(ListIndex));
				 			return;
				 		}
				 	}
				 	if(CmdName.equals("UpdateFiles")) {
				 		long machineIndex = cm.getConnection().getServerMachineInfo().getIndex();
				 		long depotIndex = 0;
				 		if(cmdcfg.getItemsSize() == 4) {
				 			depotIndex = cmdcfg.fetchFirstLong();
				 		}
				 		if(cmdcfg.getItemsSize() == 5) {
				 			machineIndex = cmdcfg.fetchFirstLong();
				 			depotIndex = cmdcfg.fetchFirstLong();
				 		}
				 		if(depotIndex > 0) {
				 			boolean ok = cm.updateFiles(
				 					machineIndex,
				 					depotIndex,
				 					cmdcfg.getLong(0),
				 					cmdcfg.getString(1),
				 					cmdcfg.getString(2));
				 			String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
				 			if(!ok) {
				 				jResult.setText("Failed: " + reason);
				 				return;
				 			}
				 			
				 			BasicCollections.BaseFiles res = ((Replies.UpdateFiles)cm.getReply()).getFiles();
				 			if(res.size() == 0) {
				 				jResult.setText("Successed: No Failed Item");
				 				return;
				 			}
				 			
				 			List.clear();
				 			ListIndex = 0;
				 			
				 			for(int i=0; i<res.size(); i++) {
				 				String str = "[" + (i+1) + "/" + res.size() + "] Failed " + res.getContent().get(i).output();
				 				List.add(str);
				 			}
				 			
				 			jResult.setText(List.get(ListIndex));
				 			return;
				 		}
				 	}
				 	if(CmdName.equals("RemoveMachines")) {
	 	            	if(cmdcfg.getItemsSize() == 1) {
	 	           			boolean ok = cm.removeMachines(cmdcfg.getString(0));
	 	           			String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
	 	           			if(!ok) {
	 	           				jResult.setText("Failed: " + reason);
	 	           				return;
	 	           			}
	 	           			BasicCollections.MachineInfos ms = ((Replies.RemoveMachines)cm.getReply()).getMachineInfos();
	 	           			if(ms.size() == 0) {
	 	           				jResult.setText("Successed: Empty");
	 	           				return;
	 	           			}
	 	           			
	 	           			List.clear();
	 	           			ListIndex = 0;
	 	           			String item = "";
	 	           			
	 	           			for(int i=0; i<ms.size(); i++) {
	 	           				item = "[" + (i+1) + "/" + ms.size() + "] Failed " + ms.getContent().get(i).output();
	 	           				List.add(item);
	 	           			}
	 	           			
	 	           			jResult.setText(List.get(ListIndex));
	 	           			return;
	 	           		}
	 	            }
	 	            if(CmdName.equals("RemoveDepots")) {
	 	            	if(cmdcfg.getItemsSize() == 1) {
	 	            		boolean ok = cm.removeDepots(cmdcfg.getString(0));
	 	           			String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
	 	           			if(!ok) {
	 	           				jResult.setText("Failed: " + reason);
	 	           				return;
	 	           			}
	 	           			BasicCollections.DepotInfos ds = ((Replies.RemoveDepots)cm.getReply()).getDepotInfos();
	 	           			if(ds.size() == 0) {
	 	           				jResult.setText("Successed: Empty");
	 	           				return;
	 	           			}
	 	           			
	 	           			List.clear();
	 	           			ListIndex = 0;
	 	           			String item = "";
	 	           			
	 	           			for(int i=0; i<ds.size(); i++) {
	 	           				item = "[" + (i+1) + "/" + ds.size() + "] Failed " + ds.getContent().get(i).output();
	 	           				List.add(item);
	 	           			}
	 	           			
	 	           			jResult.setText(List.get(ListIndex));
	 	           			return;
	 	           		}
	 	            }
	 	           if(CmdName.equals("RemoveDataBases")) {
	 	            	if(cmdcfg.getItemsSize() == 1) {
	 	            		boolean ok = cm.removeDataBases(cmdcfg.getString(0));
	 	            		String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
	 	           			if(!ok) {
	 	           				jResult.setText("Failed: " + reason);
	 	           				return;
	 	           			}
	 	           			BasicCollections.DataBaseInfos dbs = ((Replies.RemoveDataBases)cm.getReply()).getDBInfos();
	 	           			if(dbs == null) {
	 	           				jResult.setText("Failed: NULL");
	 	           				return;
	 	           			}
	 	           			if(dbs.size() == 0) {
	 	           				jResult.setText("Successed: Empty");
	 	           				return;
	 	           			}
	 	           			
	 	           			List.clear();
	 	           			ListIndex = 0;
	 	           			String item = "";
	 	           			
	 	           			for(int i=0; i<dbs.size(); i++) {
	 	           				item = "[" + (i+1) + "/" + dbs.size() + "] Failed " + dbs.getContent().get(i).output();
	 	           				List.add(item);
	 	           			}
	 	           			
	 	           			jResult.setText(List.get(ListIndex));
	 	           			return;
	 	           		}
	 	            }
	 	           if(CmdName.equals("RemoveUsers")) {
	 	            	if(cmdcfg.getItemsSize() == 1) {
	 	            		boolean ok = cm.removeUsers(cmdcfg.getString(0));
	 	            		String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
	 	            		if(!ok) {
	 	           				jResult.setText("Failed: " + reason);
	 	           				return;
	 	           			}
	 	           			BasicCollections.Users users = ((Replies.RemoveUsers)cm.getReply()).getUsers();
	 	           			if(users == null) {
	 	           				jResult.setText("Failed: NULL");
	 	           				return;
	 	           			}
	 	           			if(users.size() == 0) {
	 	           				jResult.setText("Successed: Empty");
	 	           				return;
	 	           			}
	 	           			
	 	           			List.clear();
	 	           			ListIndex = 0;
	 	           			String item = "";
	 	           			
	 	           			for(int i=0; i<users.size(); i++) {
	 	           				item = "[" + (i+1) + "/" + users.size() + "] Failed " + users.getContent().get(i).output();
	 	           				List.add(item);
	 	           			}
	 	           			
	 	           			jResult.setText(List.get(ListIndex));
	 	           			return;
	 	           		}
	 	            }
	 	           if(CmdName.equals("RemoveInvitations")) {
	 	            	if(cmdcfg.getItemsSize() == 1) {
	 	            		boolean ok = cm.removeInvitations(cmdcfg.getString(0));
	 	            		String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
	 	            		if(!ok) {
	 	           				jResult.setText("Failed: " + reason);
	 	           				return;
	 	           			}
	 	            		BasicCollections.Invitations invitations = ((Replies.RemoveInvitations)cm.getReply()).getInvitations();
	 	           			if(invitations == null) {
	 	           				jResult.setText("Failed: NULL");
	 	           				return;
	 	           			}
	 	           			if(invitations.size() == 0) {
	 	           				jResult.setText("Successed: Empty");
	 	           				return;
	 	           			}
	 	           			
	 	           			List.clear();
	 	           			ListIndex = 0;
	 	           			String item = "";
	 	           			
	 	           			for(int i=0; i<invitations.size(); i++) {
	 	           				item = "[" + (i+1) + "/" + invitations.size() + "]" + invitations.getContent().get(i).output();
	 	           				List.add(item);
	 	           			}
	 	           			
	 	           			jResult.setText(List.get(ListIndex));
	 	           			return;
	 	           		}
	 	            }
	 	            if(CmdName.equals("RemoveFolders")) {
	 	            	if(cmdcfg.getItemsSize() == 2) {
	 	            		boolean ok = cm.removeFolders(cmdcfg.getLong(0), cmdcfg.getString(1));
	 	            		String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
	 	            		if(!ok) {
	 	           				jResult.setText("Failed: " + reason);
	 	           				return;
	 	           			}
	 	           			BasicCollections.Folders folders = ((Replies.RemoveFolders)cm.getReply()).getFolders();
	 	           			if(folders == null) {
	 	           				jResult.setText("Failed: NULL");
	 	           				return;
	 	           			}
	 	           			if(folders.size() == 0) {
	 	           				jResult.setText("Successed: Empty");
	 	           				return;
	 	           			}
	 	           			
	 	           			List.clear();
	 	           			ListIndex = 0;
	 	           			String item = "";
	 	           			
	 	           			for(int i=0; i<folders.size(); i++) {
	 	           				item = "[" + (i+1) + "/" + folders.size() + "]" + folders.getContent().get(i).output();
	 	           				List.add(item);
	 	           			}
	 	           			
	 	           			jResult.setText(List.get(ListIndex));
	 	           			return;
	 	           		}
	 	            	if(cmdcfg.getItemsSize() == 3) {
	 	            		boolean ok = cm.removeFolders(cmdcfg.getLong(0), cmdcfg.getLong(1), cmdcfg.getString(1));
	 	            		String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
	 	            		if(!ok) {
	 	           				jResult.setText("Failed: " + reason);
	 	           				return;
	 	           			}
	 	            		BasicCollections.Folders folders = ((Replies.RemoveFolders)cm.getReply()).getFolders();
	 	           			if(folders == null) {
	 	           				jResult.setText("Failed: NULL");
	 	           				return;
	 	           			}
	 	           			if(folders.size() == 0) {
	 	           				jResult.setText("Successed: Empty");
	 	           				return;
	 	           			}
	 	           			
	 	           			List.clear();
	 	           			ListIndex = 0;
	 	           			String item = "";
	 	           			
	 	           			for(int i=0; i<folders.size(); i++) {
	 	           				item = "[" + (i+1) + "/" + folders.size() + "]" + folders.getContent().get(i).output();
	 	           				List.add(item);
	 	           			}
	 	           			
	 	           			jResult.setText(List.get(ListIndex));
	 	           			return;
	 	           		}
	 	            }
	 	           if(CmdName.equals("RemoveFiles")) {
	 	            	if(cmdcfg.getItemsSize() == 2) {
	 	            		boolean ok = cm.removeFiles(cmdcfg.getLong(0), cmdcfg.getString(1));
	 	            		String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
	 	            		if(!ok) {
	 	           				jResult.setText("Failed: " + reason);
	 	           				return;
	 	           			}
	 	           			BasicCollections.BaseFiles files = ((Replies.RemoveFiles)cm.getReply()).getFiles();
	 	           			if(files == null) {
	 	           				jResult.setText("Failed: NULL");
	 	           				return;
	 	           			}
	 	           			if(files.size() == 0) {
	 	           				jResult.setText("Successed: Empty");
	 	           				return;
	 	           			}
	 	           			
	 	           			List.clear();
	 	           			ListIndex = 0;
	 	           			String item = "";
	 	           			
	 	           			for(int i=0; i<files.size(); i++) {
	 	           				item = "[" + (i+1) + "/" + files.size() + "]" + files.getContent().get(i).output();
	 	           				List.add(item);
	 	           			}
	 	           			
	 	           			jResult.setText(List.get(ListIndex));
	 	           			return;
	 	           		}
	 	            	if(cmdcfg.getItemsSize() == 3) {
	 	            		boolean ok = cm.removeFiles(cmdcfg.getLong(0), cmdcfg.getLong(1), cmdcfg.getString(1));
	 	            		String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
	 	            		if(!ok) {
	 	           				jResult.setText("Failed: " + reason);
	 	           				return;
	 	           			}
	 	           			BasicCollections.BaseFiles files = ((Replies.RemoveFiles)cm.getReply()).getFiles();
	 	           			if(files == null) {
	 	           				jResult.setText("Failed: NULL");
	 	           				return;
	 	           			}
	 	           			if(files.size() == 0) {
	 	           				jResult.setText("Successed: Empty");
	 	           				return;
	 	           			}
	 	           			
	 	           			List.clear();
	 	           			ListIndex = 0;
	 	           			String item = "";
	 	           			
	 	           			for(int i=0; i<files.size(); i++) {
	 	           				item = "[" + (i+1) + "/" + files.size() + "]" + files.getContent().get(i).output();
	 	           				List.add(item);
	 	           			}
	 	           			
	 	           			jResult.setText(List.get(ListIndex));
	 	           			return;
	 	           		}
	 	            }
		 	          if(CmdName.equals("RemoveMachine")) {
		 	        	 if(cmdcfg.getItemsSize() == 1) {
		 	        		 boolean ok = cm.removeMachine(cmdcfg.getString(0));
		 	        		 String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
		 	        		 jResult.setText(ok ? "Successed" : "Failed: " + reason);
		 	        		 return;
		 	        	 }
		 	          }
		 	         if(CmdName.equals("RemoveDepot")) {
		 	        	if(cmdcfg.getItemsSize() == 1) {
		 	        		 boolean ok = cm.removeDepot(cmdcfg.getString(0));
		 	        		 String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
		 	        		 jResult.setText(ok ? "Successed" : "Failed: " + reason);
		 	        		 return;
		 	        	 }
		 	          }
		 	        if(CmdName.equals("RemoveDataBase")) {
		 	        	if(cmdcfg.getItemsSize() == 1) {
		 	        		 boolean ok = cm.removeDataBase(cmdcfg.getString(0));
		 	        		 String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
		 	        		 jResult.setText(ok ? "Successed" : "Failed: " + reason);
		 	        		 return;
		 	        	 }
		 	          }
		 	        if(CmdName.equals("RemoveUser")) {
		 	        	if(cmdcfg.getItemsSize() == 1) {
		 	        		 boolean ok = cm.removeUser(cmdcfg.getString(0));
		 	        		 String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
		 	        		 jResult.setText(ok ? "Successed" : "Failed: " + reason);
		 	        		 return;
		 	        	 }
		 	          }
		 	       	if(CmdName.equals("RemoveInvitation")) {
				 	       	if(cmdcfg.getItemsSize() == 1) {
			 	        		 boolean ok = cm.removeInvitation(cmdcfg.getString(0));
			 	        		 String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
			 	        		 jResult.setText(ok ? "Successed" : "Failed: " + reason);
			 	        		 return;
			 	        	 }
		 	          }
		 	       	if(CmdName.equals("RemoveFolder")) {
		 	        	 if(cmdcfg.getItemsSize() == 2) {
		 	        		 boolean ok = cm.removeFolder(cmdcfg.getLong(0), cmdcfg.getString(1));
		 	        		 String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
		 	        		jResult.setText(ok ? "Successed" : "Failed: " + reason);
		 	        		 return;
		 	        	 }
		 	        	if(cmdcfg.getItemsSize() == 3) {
		 	        		 boolean ok = cm.removeFolder(cmdcfg.getLong(0), cmdcfg.getLong(1), cmdcfg.getString(2));
		 	        		 String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
		 	        		jResult.setText(ok ? "Successed" : "Failed: " + reason);
		 	        		 return;
		 	        	 }
		 	          }
			 	   	if(CmdName.equals("RemoveFile")) {
		 	        	 if(cmdcfg.getItemsSize() == 2) {
		 	        		 boolean ok = cm.removeFile(cmdcfg.getLong(0), cmdcfg.getString(1));
		 	        		 String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
		 	        		jResult.setText(ok ? "Successed" : "Failed: " + reason);
		 	        		 return;
		 	        	 }
		 	        	if(cmdcfg.getItemsSize() == 3) {
		 	        		 boolean ok = cm.removeFile(cmdcfg.getLong(0), cmdcfg.getLong(1), cmdcfg.getString(2));
		 	        		 String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
		 	        		jResult.setText(ok ? "Successed" : "Failed: " + reason);
		 	        		 return;
		 	        	 }
		 	          }
			 	   if(CmdName.equals("UpdateSpecificFile")) {
		 	        	 if(cmdcfg.getItemsSize() > 0) {
		 	        		 long machine = cmdcfg.fetchFirstLong();
		 	        		 long depot = cmdcfg.fetchFirstLong();
		 	        		 BasicEnums.FileType type = BasicEnums.FileType.valueOf(cmdcfg.fetchFirstString());
		 	        		 BasicModels.BaseFile f = null;
		 	        		 if(type.equals(BasicEnums.FileType.Picture)) {
		 	        			 FileModels.Picture p = new FileModels.Picture();
		 	        			 p.input(cmdcfg.output());
		 	        			 f = p;
		 	        		 }
		 	        		if(type.equals(BasicEnums.FileType.Gif)) {
		 	        			 FileModels.Gif p = new FileModels.Gif();
		 	        			 p.input(cmdcfg.output());
		 	        			 f = p;
		 	        		 }
		 	        		if(type.equals(BasicEnums.FileType.Music)) {
		 	        			 FileModels.Music p = new FileModels.Music();
		 	        			 p.input(cmdcfg.output());
		 	        			 f = p;
		 	        		 }
		 	        		if(type.equals(BasicEnums.FileType.Video)) {
		 	        			 FileModels.Video p = new FileModels.Video();
		 	        			 p.input(cmdcfg.output());
		 	        			 f = p;
		 	        		 }
		 	        		 boolean ok = cm.updateSpecificFile(machine, depot, type, f);
		 	        		 String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
		 	        		jResult.setText(ok ? "Successed" : "Failed: " + reason);
		 	        		 return;
		 	        	 }
		 	          }
			 	   		if(CmdName.equals("UpdateSpecificFiles")) {
			 	   			if(cmdcfg.getItemsSize() > 0) {
			 	        		 long machine = cmdcfg.fetchFirstLong();
			 	        		 long depot = cmdcfg.fetchFirstLong();
			 	        		 BasicEnums.FileType type = BasicEnums.FileType.valueOf(cmdcfg.fetchFirstString());
			 	        		 BasicModels.BaseFile f = null;
			 	        		 if(type.equals(BasicEnums.FileType.Picture)) {
			 	        			 FileModels.Picture p = new FileModels.Picture();
			 	        			 p.input(cmdcfg.output());
			 	        			 f = p;
			 	        		 }
		 	        		if(type.equals(BasicEnums.FileType.Gif)) {
		 	        			 FileModels.Gif p = new FileModels.Gif();
		 	        			 p.input(cmdcfg.output());
		 	        			 f = p;
		 	        		 }
		 	        		if(type.equals(BasicEnums.FileType.Music)) {
		 	        			 FileModels.Music p = new FileModels.Music();
		 	        			 p.input(cmdcfg.output());
		 	        			 f = p;
		 	        		 }
		 	        		if(type.equals(BasicEnums.FileType.Video)) {
		 	        			 FileModels.Video p = new FileModels.Video();
		 	        			 p.input(cmdcfg.output());
		 	        			 f = p;
		 	        		 }
		 	        		 String items = cmdcfg.fetchFirstString();
		 	        		 String conditions = cmdcfg.fetchFirstString();
		 	        		 boolean ok = cm.updateSpecificFiles(machine, depot, type, f, items, conditions);
		 	        		 String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
		 	        		 if(!ok) {
		 	        			 jResult.setText("Failed: " + reason);
		 	        			 return;
		 	        		 }
		 	        		 
		 	        		 BasicCollections.BaseFiles files = ((Replies.UpdateSpecificFiles)cm.getReply()).getFiles();
		 	        		if(files.size() == 0) {
	 	           				jResult.setText("Successed: Empty");
	 	           				return;
	 	           			}
	 	           			
	 	           			List.clear();
	 	           			ListIndex = 0;
	 	           			String item = "";
	 	           			
	 	           			for(int i=0; i<files.size(); i++) {
	 	           				item = "[" + (i+1) + "/" + files.size() + "]" + files.getContent().get(i).output();
	 	           				List.add(item);
	 	           			}
 	           			
	 	           			 jResult.setText(List.get(ListIndex));
		 	        		 return;
		 	        	 }
		 	          }
			 	   	if(CmdName.equals("QuerySpecificFile")) {
				 	   	if(cmdcfg.getItemsSize() == 3) {
		 	        		 long depot = cmdcfg.fetchFirstLong();
		 	        		 BasicEnums.FileType type = BasicEnums.FileType.valueOf(cmdcfg.fetchFirstString());
		 	        		 String conditions = cmdcfg.fetchFirstString();
		 	        		 BasicModels.BaseFile file = cm.querySpecificFile(depot, type, conditions);
		 	        		 String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
		 	        		 if(file == null) {
		 	        			jResult.setText("Failed: " + reason);
		 	        			return;
		 	        		 }
		 	        		 jResult.setText(file.output());
		 	        		 return;
		 	        	 }
		 	        	 if(cmdcfg.getItemsSize() == 4) {
		 	        		 long machine = cmdcfg.fetchFirstLong();
		 	        		 long depot = cmdcfg.fetchFirstLong();
		 	        		 BasicEnums.FileType type = BasicEnums.FileType.valueOf(cmdcfg.fetchFirstString());
		 	        		 String conditions = cmdcfg.fetchFirstString();
		 	        		 BasicModels.BaseFile file = cm.querySpecificFile(machine, depot, type, conditions);
		 	        		 String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
		 	        		 if(file == null) {
		 	        			jResult.setText("Failed: " + reason);
		 	        			return;
		 	        		 }
		 	        		 jResult.setText(file.output());
		 	        		 return;
		 	        	 }
		 	          }
			 	   if(CmdName.equals("QuerySpecificFiles")) {
			 		   	if(cmdcfg.getItemsSize() == 3) {
		 	        		 long depot = cmdcfg.fetchFirstLong();
		 	        		 BasicEnums.FileType type = BasicEnums.FileType.valueOf(cmdcfg.fetchFirstString());
		 	        		 String conditions = cmdcfg.fetchFirstString();
		 	        		 BasicCollections.BaseFiles files = cm.querySpecificFiles(depot, type, conditions);
		 	        		 String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
		 	        		 if(files == null) {
		 	        			 jResult.setText("Failed: " + reason);
		 	        			 return;
		 	        		 }
		 	           			if(files.size() == 0) {
		 	           				jResult.setText("Successed: Empty");
		 	           				return;
		 	           			}
		 	           			
		 	           			List.clear();
		 	           			ListIndex = 0;
		 	           			String item = "";
		 	           			
		 	           			for(int i=0; i<files.size(); i++) {
		 	           				item = "[" + (i+1) + "/" + files.size() + "]" + files.getContent().get(i).output();
		 	           				List.add(item);
		 	           			}
	 	           			
	 	           			jResult.setText(List.get(ListIndex));
	 	           			return;
		 	        	 }
		 	        	 if(cmdcfg.getItemsSize() == 4) {
		 	        		 long machine = cmdcfg.fetchFirstLong();
		 	        		 long depot = cmdcfg.fetchFirstLong();
		 	        		 BasicEnums.FileType type = BasicEnums.FileType.valueOf(cmdcfg.fetchFirstString());
		 	        		 String conditions = cmdcfg.fetchFirstString();
		 	        		 BasicCollections.BaseFiles files = cm.querySpecificFiles(machine, depot, type, conditions);
		 	        		 String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
		 	        		 if(files == null) {
		 	        			 jResult.setText("Failed: " + reason);
		 	        			 return;
		 	        		 }
		 	           			if(files.size() == 0) {
		 	           				jResult.setText("Successed: Empty");
		 	           				return;
		 	           			}
		 	           			
		 	           			List.clear();
		 	           			ListIndex = 0;
		 	           			String item = "";
		 	           			
		 	           			for(int i=0; i<files.size(); i++) {
		 	           				item = "[" + (i+1) + "/" + files.size() + "]" + files.getContent().get(i).output();
		 	           				List.add(item);
		 	           			}
	 	           			
	 	           			jResult.setText(List.get(ListIndex));
	 	           			return;
		 	        	 }
		 	          }
			 	  if(CmdName.equals("RemoveSpecificFile")) {
				 		 if(cmdcfg.getItemsSize() == 3) {
		 	        		 long depot = cmdcfg.fetchFirstLong();
		 	        		 BasicEnums.FileType type = BasicEnums.FileType.valueOf(cmdcfg.fetchFirstString());
		 	        		 String conditions = cmdcfg.fetchFirstString();
		 	        		 boolean ok = cm.removeSpecificFile(depot, type, conditions);
		 	        		 String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
		 	        		jResult.setText(ok ? "Successed" : "Failed: " + reason);
		 	        		 return;
		 	        	 }
		 	        	 if(cmdcfg.getItemsSize() == 4) {
		 	        		 long machine = cmdcfg.fetchFirstLong();
		 	        		 long depot = cmdcfg.fetchFirstLong();
		 	        		 BasicEnums.FileType type = BasicEnums.FileType.valueOf(cmdcfg.fetchFirstString());
		 	        		 String conditions = cmdcfg.fetchFirstString();
		 	        		 boolean ok = cm.removeSpecificFile(machine, depot, type, conditions);
		 	        		 String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
		 	        		jResult.setText(ok ? "Successed" : "Failed: " + reason);
		 	        		 return;
		 	        	 }
		 	          }
			 	   		if(CmdName.equals("RemoveSpecificFiles")) {
				 	   		if(cmdcfg.getItemsSize() == 3) {
			 	        		 long depot = cmdcfg.fetchFirstLong();
			 	        		 BasicEnums.FileType type = BasicEnums.FileType.valueOf(cmdcfg.fetchFirstString());
		 	        		 String conditions = cmdcfg.fetchFirstString();
		 	        		 boolean ok = cm.removeSpecificFiles(depot, type, conditions);
		 	        		 String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
		 	        		 if(!ok) {
		 	        			 jResult.setText("Failed: " + reason);
		 	        			 return;
		 	        		 }
		 	        		 
		 	        		 BasicCollections.BaseFiles files = ((Replies.RemoveSpecificFiles)cm.getReply()).getFiles();
		 	        		if(files.size() == 0) {
		           				jResult.setText("Successed: Empty");
		           				return;
		           			}
		           			
		           			List.clear();
		           			ListIndex = 0;
		           			String item = "";
		           			
		           			for(int i=0; i<files.size(); i++) {
		           				item = "[" + (i+1) + "/" + files.size() + "]" + files.getContent().get(i).output();
		           				List.add(item);
		           			}
	          			
		           			 jResult.setText(List.get(ListIndex));
		 	        		 return;
		 	        	 }
			 	   			if(cmdcfg.getItemsSize() == 4) {
			 	        		 long machine = cmdcfg.fetchFirstLong();
			 	        		 long depot = cmdcfg.fetchFirstLong();
			 	        		 BasicEnums.FileType type = BasicEnums.FileType.valueOf(cmdcfg.fetchFirstString());
		 	        		 String conditions = cmdcfg.fetchFirstString();
		 	        		 boolean ok = cm.removeSpecificFiles(machine, depot, type, conditions);
		 	        		 String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
		 	        		 if(!ok) {
		 	        			 jResult.setText("Failed: " + reason);
		 	        			 return;
		 	        		 }
		 	        		 
		 	        		 BasicCollections.BaseFiles files = ((Replies.RemoveSpecificFiles)cm.getReply()).getFiles();
		 	        		if(files.size() == 0) {
	 	           				jResult.setText("Successed: Empty");
	 	           				return;
	 	           			}
	 	           			
	 	           			List.clear();
	 	           			ListIndex = 0;
	 	           			String item = "";
	 	           			
	 	           			for(int i=0; i<files.size(); i++) {
	 	           				item = "[" + (i+1) + "/" + files.size() + "]" + files.getContent().get(i).output();
	 	           				List.add(item);
	 	           			}
	           			
	 	           			 jResult.setText(List.get(ListIndex));
		 	        		 return;
		 	        	 }
		 	          }
			 	   		if(CmdName.equals("OperateMatch")) {
			 	   			if(cmdcfg.getItemsSize() > 0) {
			 	   				long machine = cmdcfg.fetchFirstLong();
			 	   				BasicEnums.FileType type = BasicEnums.FileType.valueOf(cmdcfg.fetchFirstString());
			 	   				String args = cmdcfg.getValue();
			 	   				
			 	   				boolean ok = cm.operateMatch(machine, type, args);
				 	   			String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
			 	        		 if(!ok) {
			 	        			 jResult.setText("Failed: " + reason);
			 	        			 return;
			 	        		 }
			 	        		 
			 	        		 BasicCollections.BaseFiles files = ((Replies.OperateMatch)cm.getReply()).getFiles();
			 	        		if(files.size() == 0) {
		 	           				jResult.setText("Successed: Empty");
		 	           				return;
		 	           			}
		 	           			
		 	           			List.clear();
		 	           			ListIndex = 0;
		 	           			String item = "";
		 	           			
		 	           			for(int i=0; i<files.size(); i++) {
		 	           				item = "[" + (i+1) + "/" + files.size() + "]" + files.getContent().get(i).output();
		 	           				List.add(item);
		 	           			}
		           			
		 	           			 jResult.setText(List.get(ListIndex));
			 	        		 return;
			 	   			}
			 	   		}
			 	   	if(CmdName.equals("OutputMatchFile")) {
			 	   		if(cmdcfg.getItemsSize() == 1) {
			 	   			boolean ok = cm.outputMatchFile(cmdcfg.getString(0));
			 	   			String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
			 	   			jResult.setText(ok ? "Successed" : "Failed: " + reason);
			 	   			return;
			 	   		}
				 	   	if(cmdcfg.getItemsSize() == 2) {
			 	   			boolean ok = cm.outputMatchFile(cmdcfg.getLong(0), cmdcfg.getString(1));
			 	   			String reason = cm.getReply() == null ? "NULL" : cm.getReply().getFailedReason();
			 	   			jResult.setText(ok ? "Successed" : "Failed: " + reason);
			 	   			return;
			 	   		}
				 	   	if(cmdcfg.getItemsSize() == 3) {
			 	   			boolean ok = cm.outputMatchFile(cmdcfg.getLong(0), cmdcfg.getString(1), cmdcfg.getString(2));
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


