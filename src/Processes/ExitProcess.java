package Processes;

public class ExitProcess extends Thread implements IProcess{

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private boolean finished;
	private boolean running;
	private boolean abort;
	private boolean stop;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean isFinished() {
		return this.finished;
	}
	public boolean isRunning() {
		return this.running;
	}
	public boolean isAbort() {
		return this.abort;
	}
	public boolean isStop() {
		return this.stop;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public ExitProcess() {
		initThis();
	}
	private void initThis() {
		this.finished = false;
		this.running = false;
		this.abort = false;
		this.stop = false;
		
		this.setName("Process Exit");
	}
	public void run() {
		
		this.finished = false;
		this.running = true;
		this.abort = false;
		this.stop = false;
		
		// step1: disconnect all server connections and disconnect server
		//Globals.Datas.Server.
		
		// step2: disconnect all client connections
		Globals.Datas.Client.removeAllConnections();
		
		// step3: save config to database
		Globals.Datas.DBManager.disconnect();
		
		this.finished = true;
		this.running = false;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean initialize(Object infos) {
		return true;
	}
	
	public boolean startProcess() {
		if(this.finished || (!this.finished && !this.stop && !this.running)) {
			this.start();
			return true;
		}
		if(this.stop) {
			return this.continueProcess();
		}
		if(this.running) {
			return true;
		}
		
		return true;
	}
	public boolean stopProcess() {
		this.stop = true;
		return true;
	}
	public boolean continueProcess() {
		this.stop = false;
		return true;
	}
	public boolean restartProcess() {
		this.abort = true;
		while(this.running);
		return this.startProcess();
	}
	public boolean exitProcess() {
		this.abort = true;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
