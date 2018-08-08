package com.FileManagerX.Tools;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Time {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static long getTicks() {
		return System.currentTimeMillis();
	}
	public final static java.lang.String getLongTime() {
		return Time.ticks2LongTime(getTicks());
	}
	public final static java.lang.String getShortTime_Date() {
		return Time.ticks2ShortTime_Date(getTicks());
	}
	public final static java.lang.String getShortTime_Second() {
		return Time.ticks2ShortTime_Second(getTicks());
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static java.lang.String ticks2LongTime(long tick) {
		java.lang.String format = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(format);  
		return sdf.format(new Date(tick));  
	}
	public final static java.lang.String ticks2ShortTime_Date(long tick) {
		java.lang.String format = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(format);  
		return sdf.format(new Date(tick));  
	}
	public final static java.lang.String ticks2ShortTime_Second(long tick) {
		java.lang.String format = "HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(format);  
		return sdf.format(new Date(tick));  
	}
	
	public final static java.lang.String ticks2String(long ticks, java.lang.String format) {
		long ms = ticks % 1000;
		java.lang.String msstr = java.lang.String.format("%03d", ms );
		ticks = (ticks - ms) / 1000;
		if(format.equals("ss")) {
			return java.lang.String.valueOf(ticks);
		}
		if(format.equals("ss:ms")) {
			return java.lang.String.valueOf(ticks) + msstr;
		}
		long s = ticks % 60;
		ticks = (ticks - s) / 60;
		java.lang.String sstr = java.lang.String.format("%02d", s );
		if(format.equals("mm:ss")) {
			return java.lang.String.valueOf(ticks) + ":" + sstr;
		}
		if(format.equals("mm:ss:ms")) {
			return java.lang.String.valueOf(ticks) + ":" + sstr + ":" + msstr;
		}
		long m = ticks % 60;
		ticks = (ticks-m) / 60;
		java.lang.String mstr = java.lang.String.format("%02d", m );
		if(format.equals("HH:mm:ss")) {
			return java.lang.String.valueOf(ticks) + ":" + mstr + ":" + sstr;
		}
		if(format.equals("HH:mm:ss:ms")) {
			return java.lang.String.valueOf(ticks) + ":" + mstr + ":" + sstr + ":" + msstr;
		}
		long h = ticks % 24;
		ticks = (ticks-h) / 24;
		java.lang.String Hstr = java.lang.String.format("%02d", h );
		if(format.equals("dd HH:mm:ss")) {
			return java.lang.String.valueOf(ticks) + " " + Hstr + ":" + mstr + ":" + sstr;
		}
		if(format.equals("dd HH:mm:ss:ms")) {
			return java.lang.String.valueOf(ticks) + " " + Hstr + ":" + mstr + ":" + sstr + ":" + msstr;
		}
		
		return "";
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static long longTime2Ticks(java.lang.String longTime) {
		try {  
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			return sdf.parse(longTime).getTime();  
	    } catch (Exception e) {  
			return -1;
		}
	}
	public final static long shortTimeDate2Ticks(java.lang.String shortTimeDate) {
		shortTimeDate += " 00:00:00";
		return Time.longTime2Ticks(shortTimeDate);
	}
	public final static long shortTimeSecond2Ticks(java.lang.String shortTimeSecond) {
		shortTimeSecond = Time.getShortTime_Date() + " " + shortTimeSecond;
		return Time.longTime2Ticks(shortTimeSecond);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public final static boolean sleepUntil(long ticks) {
		try {
			java.lang.Thread.sleep(ticks);
			return true;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			return false;
		}
	}
	public final static boolean waitUntil(long ticks) {
		long startTicks = com.FileManagerX.Tools.Time.getTicks();
		while(com.FileManagerX.Tools.Time.getTicks() - startTicks < ticks) {
			sleepUntil(1);
		}
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
