package com.FileManagerX.Interfaces;

public interface IRoutePathPackage extends IPublic {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean setStartTime1(long startTime);
	public boolean setStartTime1();
	public boolean setEndTime1(long backTime);
	public boolean setEndTime1();
	public boolean setStartTime2(long startTime);
	public boolean setStartTime2();
	public boolean setEndTime2(long backTime);
	public boolean setEndTime2();
	
	public boolean setSourMountServer(long server);
	public boolean setDestMountServer(long server);
	
	public boolean setSourMountPath(java.util.List<Long> path);
	public boolean setDestMountPath(java.util.List<Long> path);
	public boolean setRecommendPath(java.util.List<Long> path);
	public boolean setActualPath(java.util.List<Long> path);
	public boolean setVisitedPath(java.util.List<Long> path);
	public boolean setLocalMountPath(java.util.List<Long> path);
	
	public boolean setRecommendDepth(int depth);
	public boolean setActualDepth(int depth);
	public boolean setExecutePart(com.FileManagerX.BasicEnums.RppExecutePart execute);
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public long getStartTime1();
	public long getEndTime1();
	public long getStartTime2();
	public long getEndTime2();
	
	public long getSourMountServer();
	public long getDestMountServer();
	
	public java.util.List<Long> getSourMountPath();
	public java.util.List<Long> getDestMountPath();
	public java.util.List<Long> getRecommendPath();
	public java.util.List<Long> getActualPath();
	public java.util.List<Long> getVisitedPath();
	public java.util.List<Long> getLocalMountPath();
	
	public int getRecommendDepth();
	public int getActualDepth();
	public com.FileManagerX.BasicEnums.RppExecutePart getExecutePart();
	
	public long getRecommendMachineByDepth();
	public long getActualMachineByDepth();
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public <T> void reverse(java.util.List<T> path);
	public boolean visited(long machine);
	
	public boolean addAsNext(long sour);
	public boolean delToBack();
	
	public void updateExecutePart(long dest);
	public void refreshRecommendPath(IRoutePathPackage rpp);
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
