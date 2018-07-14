package Interfaces;

public interface IMatcher extends IPublic {
	
	public boolean setArgs(String args);
	public boolean setInput(BasicCollections.BaseFiles files);
	public boolean setResult(BasicCollections.BaseFiles files);
	
	public String getArgs();
	public BasicCollections.BaseFiles getInput();
	public BasicCollections.BaseFiles getResult();
	
	public String match();
}
