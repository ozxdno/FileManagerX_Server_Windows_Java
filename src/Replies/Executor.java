package Replies;

public class Executor implements Interfaces.IReplyExecutor {
	
	private Interfaces.IReplies reply;
	private String predict = "";
	
	public boolean setPredict(String predict) {
		this.predict = predict;
		return true;
	}
	
	public Interfaces.IReplies getReply() {
		return reply;
	}
	
	public boolean execute(Interfaces.IConnection connection) {
		
		BasicModels.Config c = new BasicModels.Config(connection.getReceiveString());

		if(!c.getField().equals(predict)) {
			Replies.Unsupport u = new Replies.Unsupport();
			u.setUnsupportCommand(connection.getReceiveString());
			u.setOK(false);
			u.setFailedReason("[Expect]: " + this.predict);
			this.reply = u;
			return false;
		}
		
		if(c.getField().equals("Test")) {
			Replies.Test t = new Replies.Test();
			this.reply = t;
			return t.input(connection.getReceiveString()) != null &&
					t.execute(connection);
		}
		if(c.getField().equals("Comman")) {
			Replies.Comman com = new Replies.Comman();
			this.reply = com;
			return com.input(connection.getReceiveString()) != null &&
					com.execute(connection);
		}
		if(c.getField().equals("CloseServer")) {
			Replies.CloseServer cs = new Replies.CloseServer();
			this.reply = cs;
			return cs.input(connection.getReceiveString()) != null &&
					cs.execute(connection);
		}
		if(c.getField().equals("Restart")) {
			Replies.Restart r = new Replies.Restart();
			this.reply = r;
			return r.input(connection.getReceiveString()) != null &&
					r.execute(connection);
		}
		if(c.getField().equals("TimeForExecute")) {
			Replies.TimeForExecute t = new Replies.TimeForExecute();
			this.reply = t;
			return t.input(connection.getReceiveString()) != null &&
					t.execute(connection);
		}
		if(c.getField().equals("Input")) {
			Replies.Input i = new Replies.Input();
			this.reply = i;
			return i.input(connection.getReceiveString()) != null &&
					i.execute(connection);
		}
		if(c.getField().equals("Output")) {
			Replies.Output o = new Replies.Output();
			this.reply = o;
			return o.input(connection.getReceiveString()) != null &&
					o.execute(connection);
		}
		if(c.getField().equals("RegisterUser")) {
			Replies.RegisterUser ru = new Replies.RegisterUser();
			this.reply = ru;
			return ru.input(connection.getReceiveString()) != null &&
					ru.execute(connection);
		}
		if(c.getField().equals("LoginUser")) {
			Replies.LoginUser lu = new Replies.LoginUser();
			this.reply = lu;
			return lu.input(connection.getReceiveString()) != null &&
					lu.execute(connection);
		}
		if(c.getField().equals("LoginMachine")) {
			Replies.LoginMachine lm = new Replies.LoginMachine();
			this.reply = lm;
			return lm.input(connection.getReceiveString()) != null &&
					lm.execute(connection);
		}
		if(c.getField().equals("LoginType")) {
			Replies.LoginType lt = new Replies.LoginType();
			this.reply = lt;
			return lt.input(connection.getReceiveString()) != null &&
					lt.execute(connection);
		}
		if(c.getField().equals("QueryConfigurations")) {
			Replies.QueryConfigurations qc = new Replies.QueryConfigurations();
			this.reply = qc;
			return qc.input(connection.getReceiveString()) != null &&
					qc.execute(connection);
		}
		if(c.getField().equals("QueryMachines")) {
			Replies.QueryMachines qm = new Replies.QueryMachines();
			this.reply = qm;
			Interfaces.ICommunicatorSendTotal st = Factories.CommunicatorFactory.createSendTotal();
			st.input(connection.getReceiveString());
			for(String item : st.getLines()) {
				qm.input(item);
				qm.execute(connection);
			}
			return true;
		}
		if(c.getField().equals("QueryDepots")) {
			Replies.QueryDepots qd = new Replies.QueryDepots();
			this.reply = qd;
			Interfaces.ICommunicatorSendTotal st = Factories.CommunicatorFactory.createSendTotal();
			st.input(connection.getReceiveString());
			for(String item : st.getLines()) {
				qd.input(item);
				qd.execute(connection);
			}
			return true;
		}
		if(c.getField().equals("QueryDataBases")) {
			Replies.QueryDataBases qd = new Replies.QueryDataBases();
			this.reply = qd;
			Interfaces.ICommunicatorSendTotal st = Factories.CommunicatorFactory.createSendTotal();
			st.input(connection.getReceiveString());
			for(String item : st.getLines()) {
				qd.input(item);
				qd.execute(connection);
			}
			return true;
		}
		if(c.getField().equals("QueryUsers")) {
			Replies.QueryUsers qd = new Replies.QueryUsers();
			this.reply = qd;
			Interfaces.ICommunicatorSendTotal st = Factories.CommunicatorFactory.createSendTotal();
			st.input(connection.getReceiveString());
			for(String item : st.getLines()) {
				qd.input(item);
				qd.execute(connection);
			}
			return true;
		}
		if(c.getField().equals("QueryInvitations")) {
			Replies.QueryInvitations qi = new Replies.QueryInvitations();
			this.reply = qi;
			Interfaces.ICommunicatorSendTotal st = Factories.CommunicatorFactory.createSendTotal();
			st.input(connection.getReceiveString());
			for(String item : st.getLines()) {
				qi.input(item);
				qi.execute(connection);
			}
			return true;
		}
		if(c.getField().equals("QueryFolders")) {
			Replies.QueryFolders qf = new Replies.QueryFolders();
			this.reply = qf;
			Interfaces.ICommunicatorSendTotal st = Factories.CommunicatorFactory.createSendTotal();
			st.input(connection.getReceiveString());
			for(String item : st.getLines()) {
				qf.input(item);
				qf.execute(connection);
			}
			return true;
		}
		if(c.getField().equals("QueryFiles")) {
			Replies.QueryFiles qf = new Replies.QueryFiles();
			this.reply = qf;
			Interfaces.ICommunicatorSendTotal st = Factories.CommunicatorFactory.createSendTotal();
			st.input(connection.getReceiveString());
			for(String item : st.getLines()) {
				qf.input(item);
				qf.execute(connection);
			}
			return true;
		}
		if(c.getField().equals("QueryMachine")) {
			Replies.QueryMachine qm = new Replies.QueryMachine();
			this.reply = qm;
			return qm.input(connection.getReceiveString()) != null &&
					qm.execute(connection);
		}
		if(c.getField().equals("QueryDepot")) {
			Replies.QueryDepot qd = new Replies.QueryDepot();
			this.reply = qd;
			return qd.input(connection.getReceiveString()) != null &&
					qd.execute(connection);
		}
		if(c.getField().equals("QueryDataBase")) {
			Replies.QueryDataBase qd = new Replies.QueryDataBase();
			this.reply = qd;
			return qd.input(connection.getReceiveString()) != null &&
					qd.execute(connection);
		}
		if(c.getField().equals("QueryUser")) {
			Replies.QueryUser qu = new Replies.QueryUser();
			this.reply = qu;
			return qu.input(connection.getReceiveString()) != null &&
					qu.execute(connection);
		}
		if(c.getField().equals("QueryInvitation")) {
			Replies.QueryInvitation qi = new Replies.QueryInvitation();
			this.reply = qi;
			return qi.input(connection.getReceiveString()) != null &&
					qi.execute(connection);
		}
		if(c.getField().equals("QueryFolder")) {
			Replies.QueryFolder qf = new Replies.QueryFolder();
			this.reply = qf;
			return qf.input(connection.getReceiveString()) != null &&
					qf.execute(connection);
		}
		if(c.getField().equals("QueryFile")) {
			Replies.QueryFile qf = new Replies.QueryFile();
			this.reply = qf;
			return qf.input(connection.getReceiveString()) != null &&
					qf.execute(connection);
		}
		if(c.getField().equals("QuerySize")) {
			Replies.QuerySize qsz = new Replies.QuerySize();
			this.reply = qsz;
			return qsz.input(connection.getReceiveString()) != null &&
					qsz.execute(connection);
		}
		if(c.getField().equals("UpdateMachines")) {
			Replies.UpdateMachines um = new Replies.UpdateMachines();
			this.reply = um;
			Interfaces.ICommunicatorSendTotal st = Factories.CommunicatorFactory.createSendTotal();
			st.input(connection.getReceiveString());
			for(String item : st.getLines()) {
				um.input(item);
				um.execute(connection);
			}
			return true;
		}
		if(c.getField().equals("UpdateDepots")) {
			Replies.UpdateDepots ud = new Replies.UpdateDepots();
			this.reply = ud;
			Interfaces.ICommunicatorSendTotal st = Factories.CommunicatorFactory.createSendTotal();
			st.input(connection.getReceiveString());
			for(String item : st.getLines()) {
				ud.input(item);
				ud.execute(connection);
			}
			return true;
		}
		if(c.getField().equals("UpdateDataBases")) {
			Replies.UpdateDataBases ud = new Replies.UpdateDataBases();
			this.reply = ud;
			Interfaces.ICommunicatorSendTotal st = Factories.CommunicatorFactory.createSendTotal();
			st.input(connection.getReceiveString());
			for(String item : st.getLines()) {
				ud.input(item);
				ud.execute(connection);
			}
			return true;
		}
		if(c.getField().equals("UpdateUsers")) {
			Replies.UpdateUsers ud = new Replies.UpdateUsers();
			this.reply = ud;
			Interfaces.ICommunicatorSendTotal st = Factories.CommunicatorFactory.createSendTotal();
			st.input(connection.getReceiveString());
			for(String item : st.getLines()) {
				ud.input(item);
				ud.execute(connection);
			}
			return true;
		}
		if(c.getField().equals("UpdateInvitations")) {
			Replies.UpdateInvitations ui = new Replies.UpdateInvitations();
			this.reply = ui;
			Interfaces.ICommunicatorSendTotal st = Factories.CommunicatorFactory.createSendTotal();
			st.input(connection.getReceiveString());
			for(String item : st.getLines()) {
				ui.input(item);
				ui.execute(connection);
			}
			return true;
		}
		if(c.getField().equals("UpdateFolders")) {
			Replies.UpdateFolders uf = new Replies.UpdateFolders();
			this.reply = uf;
			Interfaces.ICommunicatorSendTotal st = Factories.CommunicatorFactory.createSendTotal();
			st.input(connection.getReceiveString());
			for(String item : st.getLines()) {
				uf.input(item);
				uf.execute(connection);
			}
			return true;
		}
		if(c.getField().equals("UpdateFiles")) {
			Replies.UpdateFiles uf = new Replies.UpdateFiles();
			this.reply = uf;
			Interfaces.ICommunicatorSendTotal st = Factories.CommunicatorFactory.createSendTotal();
			st.input(connection.getReceiveString());
			for(String item : st.getLines()) {
				uf.input(item);
				uf.execute(connection);
			}
			return true;
		}
		if(c.getField().equals("UpdateMachine")) {
			Replies.UpdateMachine um = new Replies.UpdateMachine();
			this.reply = um;
			return um.input(connection.getReceiveString()) != null &&
					um.execute(connection);
		}
		if(c.getField().equals("UpdateDepot")) {
			Replies.UpdateDepot ud = new Replies.UpdateDepot();
			this.reply = ud;
			return ud.input(connection.getReceiveString()) != null &&
					ud.execute(connection);
		}
		if(c.getField().equals("UpdateDataBase")) {
			Replies.UpdateDataBase ud = new Replies.UpdateDataBase();
			this.reply = ud;
			return ud.input(connection.getReceiveString()) != null &&
					ud.execute(connection);
		}
		if(c.getField().equals("UpdateUser")) {
			Replies.UpdateUser uu = new Replies.UpdateUser();
			this.reply = uu;
			return uu.input(connection.getReceiveString()) != null &&
					uu.execute(connection);
		}
		if(c.getField().equals("UpdateInvitation")) {
			Replies.UpdateInvitation ui = new Replies.UpdateInvitation();
			this.reply = ui;
			return ui.input(connection.getReceiveString()) != null &&
					ui.execute(connection);
		}
		if(c.getField().equals("UpdateFolder")) {
			Replies.UpdateFolder uf = new Replies.UpdateFolder();
			this.reply = uf;
			return uf.input(connection.getReceiveString()) != null &&
					uf.execute(connection);
		}
		if(c.getField().equals("UpdateFile")) {
			Replies.UpdateFile uf = new Replies.UpdateFile();
			this.reply = uf;
			return uf.input(connection.getReceiveString()) != null &&
					uf.execute(connection);
		}
		if(c.getField().equals("RemoveMachines")) {
			Replies.RemoveMachines um = new Replies.RemoveMachines();
			this.reply = um;
			Interfaces.ICommunicatorSendTotal st = Factories.CommunicatorFactory.createSendTotal();
			st.input(connection.getReceiveString());
			for(String item : st.getLines()) {
				um.input(item);
				um.execute(connection);
			}
			return true;
		}
		if(c.getField().equals("RemoveDepots")) {
			Replies.RemoveDepots ud = new Replies.RemoveDepots();
			this.reply = ud;
			Interfaces.ICommunicatorSendTotal st = Factories.CommunicatorFactory.createSendTotal();
			st.input(connection.getReceiveString());
			for(String item : st.getLines()) {
				ud.input(item);
				ud.execute(connection);
			}
			return true;
		}
		if(c.getField().equals("RemoveDataBases")) {
			Replies.RemoveDataBases ud = new Replies.RemoveDataBases();
			this.reply = ud;
			Interfaces.ICommunicatorSendTotal st = Factories.CommunicatorFactory.createSendTotal();
			st.input(connection.getReceiveString());
			for(String item : st.getLines()) {
				ud.input(item);
				ud.execute(connection);
			}
			return true;
		}
		if(c.getField().equals("RemoveUsers")) {
			Replies.RemoveUsers ud = new Replies.RemoveUsers();
			this.reply = ud;
			Interfaces.ICommunicatorSendTotal st = Factories.CommunicatorFactory.createSendTotal();
			st.input(connection.getReceiveString());
			for(String item : st.getLines()) {
				ud.input(item);
				ud.execute(connection);
			}
			return true;
		}
		if(c.getField().equals("RemoveInvitations")) {
			Replies.RemoveInvitations ui = new Replies.RemoveInvitations();
			this.reply = ui;
			Interfaces.ICommunicatorSendTotal st = Factories.CommunicatorFactory.createSendTotal();
			st.input(connection.getReceiveString());
			for(String item : st.getLines()) {
				ui.input(item);
				ui.execute(connection);
			}
			return true;
		}
		if(c.getField().equals("RemoveFolders")) {
			Replies.RemoveFolders uf = new Replies.RemoveFolders();
			this.reply = uf;
			Interfaces.ICommunicatorSendTotal st = Factories.CommunicatorFactory.createSendTotal();
			st.input(connection.getReceiveString());
			for(String item : st.getLines()) {
				uf.input(item);
				uf.execute(connection);
			}
			return true;
		}
		if(c.getField().equals("RemoveFiles")) {
			Replies.RemoveFiles uf = new Replies.RemoveFiles();
			this.reply = uf;
			Interfaces.ICommunicatorSendTotal st = Factories.CommunicatorFactory.createSendTotal();
			st.input(connection.getReceiveString());
			for(String item : st.getLines()) {
				uf.input(item);
				uf.execute(connection);
			}
			return true;
		}
		if(c.getField().equals("RemoveMachine")) {
			Replies.RemoveMachine um = new Replies.RemoveMachine();
			this.reply = um;
			return um.input(connection.getReceiveString()) != null &&
					um.execute(connection);
		}
		if(c.getField().equals("RemoveDepot")) {
			Replies.RemoveDepot ud = new Replies.RemoveDepot();
			this.reply = ud;
			return ud.input(connection.getReceiveString()) != null &&
					ud.execute(connection);
		}
		if(c.getField().equals("RemoveDataBase")) {
			Replies.RemoveDataBase ud = new Replies.RemoveDataBase();
			this.reply = ud;
			return ud.input(connection.getReceiveString()) != null &&
					ud.execute(connection);
		}
		if(c.getField().equals("RemoveUser")) {
			Replies.RemoveUser uu = new Replies.RemoveUser();
			this.reply = uu;
			return uu.input(connection.getReceiveString()) != null &&
					uu.execute(connection);
		}
		if(c.getField().equals("RemoveInvitation")) {
			Replies.RemoveInvitation ui = new Replies.RemoveInvitation();
			this.reply = ui;
			return ui.input(connection.getReceiveString()) != null &&
					ui.execute(connection);
		}
		if(c.getField().equals("RemoveFolder")) {
			Replies.RemoveFolder uf = new Replies.RemoveFolder();
			this.reply = uf;
			return uf.input(connection.getReceiveString()) != null &&
					uf.execute(connection);
		}
		if(c.getField().equals("RemoveFile")) {
			Replies.RemoveFile uf = new Replies.RemoveFile();
			this.reply = uf;
			return uf.input(connection.getReceiveString()) != null &&
					uf.execute(connection);
		}
		
		
		while(true) {
			Replies.Unsupport u = new Replies.Unsupport();
			u.setOK(false);
			u.setUnsupportCommand(connection.getReceiveString());
			u.setFailedReason("[Not Register Expect]: " + this.predict);
			this.reply = u;
			break;
		}
		return false;
	}
	
}
