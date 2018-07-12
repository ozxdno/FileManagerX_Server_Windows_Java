package Commands;

public class Executor implements Interfaces.ICommandExecutor {

	public boolean execute(Interfaces.IConnection connection) {
		
		BasicModels.Config c = new BasicModels.Config(connection.getReceiveString());
		/*
		if(c.isEmpty()) {
			return true;
		}
		*/
		
		if(c.getField().equals("Test")) {
			Commands.Test t = new Commands.Test();
			t.setConnection(connection);
			return t.input(connection.getReceiveString()) != null && t.execute();
		}
		if(c.getField().equals("Comman")) {
			Commands.Comman com = new Commands.Comman();
			com.setConnection(connection);
			return com.input(connection.getReceiveString()) != null && com.execute();
		}
		if(c.getField().equals("CloseServer")) {
			Commands.CloseServer cs = new Commands.CloseServer();
			cs.setConnection(connection);
			return cs.input(connection.getReceiveString()) != null && cs.execute();
		}
		if(c.getField().equals("Exchange")) {
			Commands.Exchange cs = new Commands.Exchange();
			cs.setConnection(connection);
			return cs.input(connection.getReceiveString()) != null && cs.execute();
		}
		if(c.getField().equals("CreateConnection")) {
			Commands.CreateConnection cs = new Commands.CreateConnection();
			cs.setConnection(connection);
			return cs.input(connection.getReceiveString()) != null && cs.execute();
		}
		if(c.getField().equals("Restart")) {
			Commands.Restart r = new Commands.Restart();
			r.setConnection(connection);
			return r.input(connection.getReceiveString()) != null && r.execute();
		}
		if(c.getField().equals("TimeForExecute")) {
			Commands.TimeForExecute t = new Commands.TimeForExecute();
			t.setConnection(connection);
			return t.input(connection.getReceiveString()) != null && t.execute();
		}
		if(c.getField().equals("OperateDepot")) {
			Commands.OperateDepot t = new Commands.OperateDepot();
			t.setConnection(connection);
			return t.input(connection.getReceiveString()) != null && t.execute();
		}
		if(c.getField().equals("Input")) {
			Commands.Input i = new Commands.Input();
			i.setConnection(connection);
			return i.input(connection.getReceiveString()) != null && i.execute();
		}
		if(c.getField().equals("Output")) {
			Commands.Output o = new Commands.Output();
			o.setConnection(connection);
			return o.input(connection.getReceiveString()) != null && o.execute();
		}
		if(c.getField().equals("RegisterUser")) {
			Commands.RegisterUser ru = new Commands.RegisterUser();
			ru.setConnection(connection);
			return ru.input(connection.getReceiveString()) != null && ru.execute();
		}
		if(c.getField().equals("LoginUser")) {
			Commands.LoginUser lu = new Commands.LoginUser();
			lu.setConnection(connection);
			return lu.input(connection.getReceiveString()) != null && lu.execute();
		}
		if(c.getField().equals("LoginMachine")) {
			Commands.LoginMachine lm = new Commands.LoginMachine();
			lm.setConnection(connection);
			return lm.input(connection.getReceiveString()) != null && lm.execute();
		}
		if(c.getField().equals("LoginType")) {
			Commands.LoginType lt = new Commands.LoginType();
			lt.setConnection(connection);
			return lt.input(connection.getReceiveString()) != null && lt.execute();
		}
		if(c.getField().equals("LoginIndex")) {
			Commands.LoginIndex lt = new Commands.LoginIndex();
			lt.setConnection(connection);
			return lt.input(connection.getReceiveString()) != null && lt.execute();
		}
		if(c.getField().equals("QueryConfigurations")) {
			Commands.QueryConfigurations qc = new Commands.QueryConfigurations();
			qc.setConnection(connection);
			return qc.input(connection.getReceiveString()) != null && qc.execute();
		}
		if(c.getField().equals("QueryMachines")) {
			Commands.QueryMachines qm = new Commands.QueryMachines();
			qm.setConnection(connection);
			return qm.input(connection.getReceiveString()) != null && qm.execute();
		}
		if(c.getField().equals("QueryDepots")) {
			Commands.QueryDepots qd = new Commands.QueryDepots();
			qd.setConnection(connection);
			return qd.input(connection.getReceiveString()) != null && qd.execute();
		}
		if(c.getField().equals("QueryDataBases")) {
			Commands.QueryDataBases qd = new Commands.QueryDataBases();
			qd.setConnection(connection);
			return qd.input(connection.getReceiveString()) != null && qd.execute();
		}
		if(c.getField().equals("QueryUsers")) {
			Commands.QueryUsers qu = new Commands.QueryUsers();
			qu.setConnection(connection);
			return qu.input(connection.getReceiveString()) != null && qu.execute();
		}
		if(c.getField().equals("QueryInvitations")) {
			Commands.QueryInvitations qi = new Commands.QueryInvitations();
			qi.setConnection(connection);
			return qi.input(connection.getReceiveString()) != null && qi.execute();
		}
		if(c.getField().equals("QueryFolders")) {
			Commands.QueryFolders qf = new Commands.QueryFolders();
			qf.setConnection(connection);
			return qf.input(connection.getReceiveString()) != null && qf.execute();
		}
		if(c.getField().equals("QueryFiles")) {
			Commands.QueryFiles qf = new Commands.QueryFiles();
			qf.setConnection(connection);
			return qf.input(connection.getReceiveString()) != null && qf.execute();
		}
		if(c.getField().equals("QueryMachine")) {
			Commands.QueryMachine qm = new Commands.QueryMachine();
			qm.setConnection(connection);
			return qm.input(connection.getReceiveString()) != null && qm.execute();
		}
		if(c.getField().equals("QueryDepot")) {
			Commands.QueryDepot qd = new Commands.QueryDepot();
			qd.setConnection(connection);
			return qd.input(connection.getReceiveString()) != null && qd.execute();
		}
		if(c.getField().equals("QueryDataBase")) {
			Commands.QueryDataBase qd = new Commands.QueryDataBase();
			qd.setConnection(connection);
			return qd.input(connection.getReceiveString()) != null && qd.execute();
		}
		if(c.getField().equals("QueryUser")) {
			Commands.QueryUser qu = new Commands.QueryUser();
			qu.setConnection(connection);
			return qu.input(connection.getReceiveString()) != null && qu.execute();
		}
		if(c.getField().equals("QueryInvitation")) {
			Commands.QueryInvitation qi = new Commands.QueryInvitation();
			qi.setConnection(connection);
			return qi.input(connection.getReceiveString()) != null && qi.execute();
		}
		if(c.getField().equals("QueryFolder")) {
			Commands.QueryFolder qf = new Commands.QueryFolder();
			qf.setConnection(connection);
			return qf.input(connection.getReceiveString()) != null && qf.execute();
		}
		if(c.getField().equals("QueryFile")) {
			Commands.QueryFile qf = new Commands.QueryFile();
			qf.setConnection(connection);
			return qf.input(connection.getReceiveString()) != null && qf.execute();
		}
		if(c.getField().equals("QuerySize")) {
			Commands.QuerySize qsz = new Commands.QuerySize();
			qsz.setConnection(connection);
			return qsz.input(connection.getReceiveString()) != null && qsz.execute();
		}
		if(c.getField().equals("UpdateMachine")) {
			Commands.UpdateMachine um = new Commands.UpdateMachine();
			um.setConnection(connection);
			return um.input(connection.getReceiveString()) != null && um.execute();
		}
		if(c.getField().equals("UpdateDepot")) {
			Commands.UpdateDepot ud = new Commands.UpdateDepot();
			ud.setConnection(connection);
			return ud.input(connection.getReceiveString()) != null && ud.execute();
		}
		if(c.getField().equals("UpdateDataBase")) {
			Commands.UpdateDataBase ud = new Commands.UpdateDataBase();
			ud.setConnection(connection);
			return ud.input(connection.getReceiveString()) != null && ud.execute();
		}
		if(c.getField().equals("UpdateUser")) {
			Commands.UpdateUser uu = new Commands.UpdateUser();
			uu.setConnection(connection);
			return uu.input(connection.getReceiveString()) != null && uu.execute();
		}
		if(c.getField().equals("UpdateInvitation")) {
			Commands.UpdateInvitation ui = new Commands.UpdateInvitation();
			ui.setConnection(connection);
			return ui.input(connection.getReceiveString()) != null && ui.execute();
		}
		if(c.getField().equals("UpdateFolder")) {
			Commands.UpdateFolder uf = new Commands.UpdateFolder();
			uf.setConnection(connection);
			return uf.input(connection.getReceiveString()) != null && uf.execute();
		}
		if(c.getField().equals("UpdateFile")) {
			Commands.UpdateFile uf = new Commands.UpdateFile();
			uf.setConnection(connection);
			return uf.input(connection.getReceiveString()) != null && uf.execute();
		}
		if(c.getField().equals("UpdateMachines")) {
			Commands.UpdateMachines um = new Commands.UpdateMachines();
			um.setConnection(connection);
			return um.input(connection.getReceiveString()) != null && um.execute();
		}
		if(c.getField().equals("UpdateDepots")) {
			Commands.UpdateDepots ud = new Commands.UpdateDepots();
			ud.setConnection(connection);
			return ud.input(connection.getReceiveString()) != null && ud.execute();
		}
		if(c.getField().equals("UpdateDataBases")) {
			Commands.UpdateDataBases ud = new Commands.UpdateDataBases();
			ud.setConnection(connection);
			return ud.input(connection.getReceiveString()) != null && ud.execute();
		}
		if(c.getField().equals("UpdateUsers")) {
			Commands.UpdateUsers uu = new Commands.UpdateUsers();
			uu.setConnection(connection);
			return uu.input(connection.getReceiveString()) != null && uu.execute();
		}
		if(c.getField().equals("UpdateInvitations")) {
			Commands.UpdateInvitations ui = new Commands.UpdateInvitations();
			ui.setConnection(connection);
			return ui.input(connection.getReceiveString()) != null && ui.execute();
		}
		if(c.getField().equals("UpdateFolders")) {
			Commands.UpdateFolders uf = new Commands.UpdateFolders();
			uf.setConnection(connection);
			return uf.input(connection.getReceiveString()) != null && uf.execute();
		}
		if(c.getField().equals("UpdateFiles")) {
			Commands.UpdateFiles uf = new Commands.UpdateFiles();
			uf.setConnection(connection);
			return uf.input(connection.getReceiveString()) != null && uf.execute();
		}
		if(c.getField().equals("RemoveDepots")) {
			Commands.RemoveDepots rd = new Commands.RemoveDepots();
			rd.setConnection(connection);
			return rd.input(connection.getReceiveString()) != null && rd.execute();
		}
		if(c.getField().equals("RemoveDataBases")) {
			Commands.RemoveDataBases rd = new Commands.RemoveDataBases();
			rd.setConnection(connection);
			return rd.input(connection.getReceiveString()) != null && rd.execute();
		}
		if(c.getField().equals("RemoveMachines")) {
			Commands.RemoveMachines rm = new Commands.RemoveMachines();
			rm.setConnection(connection);
			return rm.input(connection.getReceiveString()) != null && rm.execute();
		}
		if(c.getField().equals("RemoveUsers")) {
			Commands.RemoveUsers ru = new Commands.RemoveUsers();
			ru.setConnection(connection);
			return ru.input(connection.getReceiveString()) != null && ru.execute();
		}
		if(c.getField().equals("RemoveInvitations")) {
			Commands.RemoveInvitations ri = new Commands.RemoveInvitations();
			ri.setConnection(connection);
			return ri.input(connection.getReceiveString()) != null && ri.execute();
		}
		if(c.getField().equals("RemoveFolders")) {
			Commands.RemoveFolders rf = new Commands.RemoveFolders();
			rf.setConnection(connection);
			return rf.input(connection.getReceiveString()) != null && rf.execute();
		}
		if(c.getField().equals("RemoveFiles")) {
			Commands.RemoveFiles rf = new Commands.RemoveFiles();
			rf.setConnection(connection);
			return rf.input(connection.getReceiveString()) != null && rf.execute();
		}
		if(c.getField().equals("RemoveDepot")) {
			Commands.RemoveDepot rd = new Commands.RemoveDepot();
			rd.setConnection(connection);
			return rd.input(connection.getReceiveString()) != null && rd.execute();
		}
		if(c.getField().equals("RemoveDataBase")) {
			Commands.RemoveDataBase rd = new Commands.RemoveDataBase();
			rd.setConnection(connection);
			return rd.input(connection.getReceiveString()) != null && rd.execute();
		}
		if(c.getField().equals("RemoveMachine")) {
			Commands.RemoveMachine rm = new Commands.RemoveMachine();
			rm.setConnection(connection);
			return rm.input(connection.getReceiveString()) != null && rm.execute();
		}
		if(c.getField().equals("RemoveUser")) {
			Commands.RemoveUser ru = new Commands.RemoveUser();
			ru.setConnection(connection);
			return ru.input(connection.getReceiveString()) != null && ru.execute();
		}
		if(c.getField().equals("RemoveInvitation")) {
			Commands.RemoveInvitation ri = new Commands.RemoveInvitation();
			ri.setConnection(connection);
			return ri.input(connection.getReceiveString()) != null && ri.execute();
		}
		if(c.getField().equals("RemoveFolder")) {
			Commands.RemoveFolder rf = new Commands.RemoveFolder();
			rf.setConnection(connection);
			return rf.input(connection.getReceiveString()) != null && rf.execute();
		}
		if(c.getField().equals("RemoveFile")) {
			Commands.RemoveFile rf = new Commands.RemoveFile();
			rf.setConnection(connection);
			return rf.input(connection.getReceiveString()) != null && rf.execute();
		}
		
		return this.executeUnsupport(connection);
	}
	
	private boolean executeUnsupport(Interfaces.IConnection connection) {
		String rece = new String(connection.getReceiveString());
		
		connection.setSendString(rece);
		connection.setSendLength(rece.length());
		connection.setContinueSendString();
		
		return false;
	}
}
