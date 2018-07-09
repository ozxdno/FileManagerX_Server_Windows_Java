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
		if(c.getField().equals("RemoveDepots")) {
			Replies.RemoveDepots rd = new Replies.RemoveDepots();
			this.reply = rd;
			return rd.input(connection.getReceiveString()) != null &&
					rd.execute(connection);
		}
		if(c.getField().equals("RemoveDataBases")) {
			Replies.RemoveDataBases rd = new Replies.RemoveDataBases();
			this.reply = rd;
			return rd.input(connection.getReceiveString()) != null &&
					rd.execute(connection);
		}
		if(c.getField().equals("RemoveDepot")) {
			Replies.RemoveDepot rd = new Replies.RemoveDepot();
			this.reply = rd;
			return rd.input(connection.getReceiveString()) != null &&
					rd.execute(connection);
		}
		if(c.getField().equals("RemoveDataBase")) {
			Replies.RemoveDataBase rd = new Replies.RemoveDataBase();
			this.reply = rd;
			return rd.input(connection.getReceiveString()) != null &&
					rd.execute(connection);
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
