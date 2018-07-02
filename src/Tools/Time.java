package Tools;

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
	
	public final static boolean waitUntil(long ticks) {
		long startTicks = Tools.Time.getTicks();
		while(Tools.Time.getTicks() - startTicks < ticks) {
			;
		}
		return true;
	}
	
	public final static boolean waitUntilConnectionIdle(Interfaces.IConnection connection) {
		while(connection.isBusy());
		return true;
	}
	
	public final static boolean waitUntilConnectionIdle(long ticks, Interfaces.IConnection connection) {
		long startTicks = Tools.Time.getTicks();
		while(Tools.Time.getTicks() - startTicks < ticks) {
			if(!connection.isBusy()) {
				break;
			}
		}
		
		return !connection.isBusy();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
