package com.FileManagerX.Interfaces;

import com.FileManagerX.BasicEnums.IOPType;

public interface IIOPackage extends IPublic {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setType(IOPType type);
	public boolean setUncheck(boolean uncheck);
	public boolean setCover(boolean cover);
	public boolean setSourUrl(String url);
	public boolean setDestUrl(String url);
	public boolean setTotalBytes(long totalBytes);
	public boolean setFinishedBytes(long finishedBytes);
	public boolean setMaxFlow(long maxFlow);
	public boolean setContent(String content);
	public boolean setStream(Object stream);
	
	public boolean setThis(IOPType type, boolean uncheck, boolean cover, String sourUrl, String destUrl,
			long finishedBytes, long maxFlow, String content);
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public IOPType getType();
	public boolean isUncheck();
	public boolean isCover();
	public boolean isFinished();
	public String getSourUrl();
	public String getDestUrl();
	public long getTotalBytes();
	public long getFinishedBytes();
	public long getMaxFlow();
	public String getContent();
	public Object getStream();
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean createStream();
	public boolean closeStream();
	public boolean execute();
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
