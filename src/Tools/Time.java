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
	
	public final static boolean sleepUntil(long ticks) {
		try {
			java.lang.Thread.sleep(ticks);
			return true;
		} catch(Exception e) {
			BasicEnums.ErrorType.OTHERS.register(e.toString());
			return false;
		}
	}
	public final static boolean waitUntil(long ticks) {
		long startTicks = Tools.Time.getTicks();
		while(Tools.Time.getTicks() - startTicks < ticks) {
			sleepUntil(1);
		}
		return true;
	}
	public final static boolean waitUntilConnectionIdle(Interfaces.IConnection connection) {
		while(connection.isBusy()) {
			sleepUntil(1);
		}
		return true;
	}
	public final static boolean waitUntilConnectionIdle(long ticks, Interfaces.IConnection connection) {
		long startTicks = Tools.Time.getTicks();
		while(Tools.Time.getTicks() - startTicks < ticks) {
			if(!connection.isBusy()) {
				break;
			}
			sleepUntil(1);
		}
		if(connection.isBusy()) {
			BasicEnums.ErrorType.COMMON_TIME_OUT.register("waitTicks = " + ticks);
			return false;
		}
		
		return true;
	}
	public final static boolean waitUntilFileConnectorSave(long ticks, Interfaces.IFileConnector fc) {
		Interfaces.IFileConnector opponentFC = fc.getConnection().getFileConnector();
		
		// wait for fill bytes
		long startTick = Tools.Time.getTicks();
		while(Tools.Time.getTicks() - startTick < ticks) {
			if(opponentFC.getSendLength() != 0) {
				break;
			}
			sleepUntil(1);
		}
		if(opponentFC.getSendLength() == 0) {
			BasicEnums.ErrorType.COMMUNICATOR_RECEIVE_FAILED.register("Too long to get Send Bytes From Other Connection");
			return false;
		}
		return true;
	}
	public final static boolean waitUntilFileConnectorLoad(long ticks, Interfaces.IFileConnector fc) {
		// wait for connection finish fetching data
		long startTick = Tools.Time.getTicks();
		while(Tools.Time.getTicks() - startTick < ticks) {
			if(fc.getSendLength() == 0) {
				break;
			}
			sleepUntil(1);
		}
		if(fc.getSendLength() != 0) {
			BasicEnums.ErrorType.COMMUNICATOR_SEND_FAILED.register("Too long to Wait Bytes Fetch By Other Connection");
			return false;
		}
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
