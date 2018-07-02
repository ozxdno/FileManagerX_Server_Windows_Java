package Replies;

public class Executor implements Interfaces.IReplyExecutor {
	
	private Interfaces.IReplies reply;
	
	public Interfaces.IReplies getReply() {
		return reply;
	}
	
	public boolean execute(Interfaces.IConnection connection) {
		
		this.reply = null;
		
		BasicModels.Config c = new BasicModels.Config(connection.getReceiveString());
		if(c.isEmpty()) {
			return true;
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
		if(c.getField().equals("QueryConfigurations")) {
			Replies.QueryConfigurations qc = new Replies.QueryConfigurations();
			this.reply = qc;
			return qc.input(connection.getReceiveString()) != null &&
					qc.execute(connection);
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
		
		return false;
	}
	
}
