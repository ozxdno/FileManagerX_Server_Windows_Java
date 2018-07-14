package Match;

public class PictureMatcher implements Interfaces.IMatcher {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private BasicCollections.BaseFiles input;
	private BasicCollections.BaseFiles result;
	
	private String matchFileName;
	private long[] matchDepots;
	private double matchSimilarPercent;
	private boolean matchRow;
	private boolean matchCol;
	private boolean matchSameSize;
	private boolean matchRotate;
	
	private int permitPixelError;
	private int permitErrorAmount;
	private FileModels.Picture standardFile;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean setInput(BasicCollections.BaseFiles files) {
		if(files == null) {
			return false;
		}
		this.input = files;
		return true;
	}
	public boolean setResult(BasicCollections.BaseFiles files) {
		if(files == null) {
			return false;
		}
		this.result = files;
		return true;
	}
	public boolean setArgs(String args) {
		String res = this.input(args);
		return res != null;
	}

	public boolean setMatchFileName(String name) {
		if(name == null || name.length() == 0) {
			return false;
		}
		this.matchFileName = name;
		return true;
	}
	public boolean setMatchDepots(long[] depots) {
		if(depots == null) {
			return false;
		}
		this.matchDepots = depots;
		
		this.input.clear();
		for(int i=0; i<depots.length; i++) {
			Interfaces.IDBManager dbm = Globals.Datas.DBManagers.fetchDepotIndex(depots[i]);
			BasicCollections.BaseFiles in = dbm.QuerySpecificFiles(BasicEnums.FileType.Picture, "");
			for(int j=0; j<in.size(); j++) {
				this.input.add(in.getContent().get(j));
			}
		}
		
		return true;
	}
	public boolean setMatchSimilarPercent(double matchSimilarPercent) {
		if(matchSimilarPercent < 0) {
			matchSimilarPercent = 0;
		}
		if(matchSimilarPercent > 100) {
			matchSimilarPercent = 100;
		}
		this.matchSimilarPercent = matchSimilarPercent;
		return true;
	}
	public boolean setMatchRow(boolean matchRow) {
		this.matchRow = matchRow;
		return true;
	}
	public boolean setMatchCol(boolean matchCol) {
		this.matchCol = matchCol;
		return true;
	}
	public boolean setMatchSameSize(boolean matchSameSize) {
		this.matchSameSize = matchSameSize;
		return true;
	}
	public boolean setMatchRotate(boolean matchRotate) {
		this.matchRotate = matchRotate;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public BasicCollections.BaseFiles getInput() {
		return this.input;
	}
	public BasicCollections.BaseFiles getResult() {
		return this.result;
	}
	public String getArgs() {
		return this.output();
	}
	
	public String getMatchFileName() {
		return this.matchFileName;
	}
	public long[] getMatchDepots() {
		return this.matchDepots;
	}
	public double getMatchSimilarPercent() {
		return this.matchSimilarPercent;
	}
	public boolean isMatchRow() {
		return this.matchRow;
	}
	public boolean isMatchCol() {
		return this.matchCol;
	}
	public boolean isMatchSameSize() {
		return this.matchSameSize;
	}
	public boolean isMatchRotate() {
		return this.matchRotate;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public PictureMatcher() {
		initThis();
	}
	public PictureMatcher(String args) {
		initThis();
		this.setArgs(args);
	}
	private void initThis() {
		input = new BasicCollections.BaseFiles();
		result = new BasicCollections.BaseFiles(); 
		
		matchSimilarPercent = 80;
		matchRow = false;
		matchCol = false;
		matchSameSize = false;
		matchRotate = true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void clear() {
		initThis();
	}
	public String toString() {
		return this.output();
	}
	public String output() {
		BasicModels.Config c = new BasicModels.Config();
		c.setField(this.getClass().getSimpleName());
		c.addToBottom(this.matchFileName);
		c.addToBottom(Tools.String.link(this.matchDepots, " "));
		c.addToBottom(this.matchSimilarPercent);
		c.addToBottom(this.matchRow);
		c.addToBottom(this.matchCol);
		c.addToBottom(this.matchSameSize);
		c.addToBottom(this.matchRotate);
		
		return c.output();
	}
	public String input(String in) {
		BasicModels.Config c = new BasicModels.Config(in);
		this.matchFileName = c.fetchFirstString();
		if(!c.getIsOK()) { return null; }
		this.matchDepots = Tools.String.splitToLongArray(c.fetchFirstString(), ' ');
		if(!c.getIsOK()) { return null; }
		this.matchSimilarPercent = c.fetchFirstDouble();
		if(!c.getIsOK()) { return null; }
		this.matchRow = c.fetchFirstBoolean();
		if(!c.getIsOK()) { return null; }
		this.matchCol = c.fetchFirstBoolean();
		if(!c.getIsOK()) { return null; }
		this.matchSameSize = c.fetchFirstBoolean();
		if(!c.getIsOK()) { return null; }
		this.matchRotate = c.fetchFirstBoolean();
		if(!c.getIsOK()) { return null; }
		
		return c.output();
	}
	public void copyReference(Object o) {
		PictureMatcher m = (PictureMatcher)o;
		this.input = m.input;
		this.result = m.result;
		this.matchDepots = m.matchDepots;
		this.matchSimilarPercent = m.matchSimilarPercent;
		this.matchRow = m.matchRow;
		this.matchCol = m.matchCol;
		this.matchSameSize = m.matchSameSize;
		this.matchRotate = m.matchRotate;
		this.permitPixelError = m.permitPixelError;
		this.permitErrorAmount = m.permitErrorAmount;
	}
	public void copyValue(Object o) {
		PictureMatcher m = (PictureMatcher)o;
		this.input.copyValue(m.input);
		this.result.copyValue(m.result);
		this.matchDepots = m.matchDepots.clone();
		this.matchSimilarPercent = m.matchSimilarPercent;
		this.matchRow = m.matchRow;
		this.matchCol = m.matchCol;
		this.matchSameSize = m.matchSameSize;
		this.matchRotate = m.matchRotate;
		this.permitPixelError = m.permitPixelError;
		this.permitErrorAmount = m.permitErrorAmount;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public String match() {
		this.result.clear();
		String res = null;
		
		res = this.loadStandardFile();
		if(res != null) { return res; }
		
		for(int i=0; i<input.size(); i++) {
			FileModels.Picture p = (FileModels.Picture)input.getContent().get(i);
			res = this.matchSingle(p);
			if(res != null) {
				return res;
			}
			else {
				this.result.add(p);
			}
		}
		
		return null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private String loadStandardFile() {
		String url = Tools.Pathes.getFolder_TMP_0_Match() + "\\" + this.matchFileName;
		this.standardFile = new FileModels.Picture(url);
		
		if(this.standardFile.load()) {
			return null;
		}
		else {
			return "Load Standard File Failed";
		}
	}
	private String matchSingle(FileModels.Picture p) {
		
		return null;
		
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
