package Tools;

import java.util.*;

public class String {
	
	/**
	 * 从 文本中获取一行,可用来清除一行文本末尾的多余字符。
	 * @param text 需要清除的文本
	 * @return 清除成功后的文本
	 */
	public static final java.lang.String getFirstLine(java.lang.String text) {
		if(text == null || text.length() == 0) {
			return "";
		}
		int cut0 = 0;
		while(cut0 < text.length() && text.charAt(cut0) != '\r' && text.charAt(cut0) != '\n' && text.charAt(cut0) != 0) {
			cut0++;
		}
		return text.substring(0, cut0);
	}
	/**
	 * 清除一行文本左右两端的空格
	 * @param line 输入一行字符串
	 * @return 处理后的文本
	 */
	public static final java.lang.String clearLRSpace(java.lang.String line) {
		if(line == null || line.length() == 0) {
			return "";
		}
		
		StringBuilder sb = new StringBuilder(line);
		
		while(sb.length() > 0 && sb.charAt(0) == ' ') {
			sb.deleteCharAt(0);
		}
		while(sb.length() > 0 && sb.charAt(sb.length()-1) == ' ') {
			sb.deleteCharAt(sb.length()-1);
		}
		
		return sb.toString();
	}
	/**
	 * 用指定标识符连接多个字符串，使之成为一个单字符串
	 * @param items 待连接字符串列表
	 * @param mark 标识符
	 * @param bgIndex 指定开始位置
	 * @param edIndex 指定结束位置
	 * @return 连接完成后的字符串
	 */
	public static final java.lang.String link(
			java.lang.String[] items,
			java.lang.String mark,
			int bgIndex,
			int edIndex) {
		if(items == null || items.length == 0) {
			return "";
		}
		java.lang.String res = "";
		for(int i=bgIndex; i<items.length && i<=edIndex; i++) {
			res += mark + items[i];
		}
		if(res.length() != 0) {
			res = res.substring(1, res.length());
		}
		return res;
	}
	/**
	 * 用指定标识符连接多个字符串，使之成为一个单字符串
	 * @param items 待连接字符串列表
	 * @param mark 标识符
	 * @return 连接完成后的字符串
	 */
	public static final java.lang.String link(
			java.lang.String[] items,
			java.lang.String mark) {
		return link(items,mark,0,items.length-1);
	}
	
	public static final java.lang.String linkBefore(java.lang.String[] items, int index, java.lang.String mark) {
		return link(items, mark, 0, index-1);
	}
	public static final java.lang.String linkBehind(java.lang.String[] items, int index, java.lang.String mark) {
		return link(items, mark, index+1, items.length);
	}
	
	public static final java.lang.String link(byte[] array, java.lang.String mark, int bgIndex, int edIndex) {
		if(array == null || array.length == 0) {
			return "";
		}
		if(bgIndex > edIndex) {
			return "";
		}
		
		java.lang.String[] items = new java.lang.String[edIndex - bgIndex + 1];
		for(int i=0; i<items.length; i++) {
			items[i] = java.lang.String.valueOf(array[i]);
		}
		return link(items,mark,0,items.length-1);
	}
	public static final java.lang.String link(byte[] array, java.lang.String mark) {
		return link(array,mark,0,array.length-1);
	}
	
	public static final java.lang.String link(int[] array, java.lang.String mark, int bgIndex, int edIndex) {
		if(array == null || array.length == 0) {
			return "";
		}
		if(bgIndex > edIndex) {
			return "";
		}
		
		java.lang.String[] items = new java.lang.String[edIndex - bgIndex + 1];
		for(int i=0; i<items.length; i++) {
			items[i] = java.lang.String.valueOf(array[i]);
		}
		return link(items,mark,0,items.length-1);
	}
	public static final java.lang.String link(int[] array, java.lang.String mark) {
		return link(array,mark,0,array.length-1);
	}
	
	public static final java.lang.String link(long[] array, java.lang.String mark, int bgIndex, int edIndex) {
		if(array == null || array.length == 0) {
			return "";
		}
		if(bgIndex > edIndex) {
			return "";
		}
		
		java.lang.String[] items = new java.lang.String[edIndex - bgIndex + 1];
		for(int i=0; i<items.length; i++) {
			items[i] = java.lang.String.valueOf(array[i]);
		}
		return link(items,mark,0,items.length-1);
	}
	public static final java.lang.String link(long[] array, java.lang.String mark) {
		return link(array,mark,0,array.length-1);
	}
	
	/**
	 * 把指定一行字符串分割成若干个子字符串，分割标志为 mark 。
	 * @param line 指定字符串
	 * @param mark 标识符
	 * @return 分割结果
	 */
	public static final java.lang.String[] split(java.lang.String line, char mark) {
		if(line == null || line.length() == 0) {
			return new java.lang.String[0];
		}
		ArrayList<Integer> p = new ArrayList<Integer>();
		p.add(-1);
		for(int i=0; i<line.length(); i++) {
			if(line.charAt(i) == mark) {
				p.add(i);
			}
		}
		p.add(line.length());
		
		java.lang.String[] res = new java.lang.String[p.size()-1];
		for(int i=0; i<res.length; i++) {
			res[i] = line.substring(p.get(i)+1, p.get(i+1));
		}
		return res;
	}
	
	public static final byte[] splitToByteArray(java.lang.String line, char mark) {
		java.lang.String[] items = split(line, mark);
		byte[] res = new byte[items.length];
		try {
			for(int i=0; i<items.length; i++) {
				res[i] = Byte.parseByte(items[i]);
			}
			return res;
		} catch(Exception e) {
			BasicEnums.ErrorType.OTHERS.register(e.toString());
			return null;
		}
	}
	public static final int[] splitToIntArray(java.lang.String line, char mark) {
		java.lang.String[] items = split(line, mark);
		int[] res = new int[items.length];
		try {
			for(int i=0; i<items.length; i++) {
				res[i] = Integer.parseInt(items[i]);
			}
			return res;
		} catch(Exception e) {
			BasicEnums.ErrorType.OTHERS.register(e.toString());
			return null;
		}
	}
	public static final long[] splitToLongArray(java.lang.String line, char mark) {
		java.lang.String[] items = split(line, mark);
		long[] res = new long[items.length];
		try {
			for(int i=0; i<items.length; i++) {
				res[i] = Long.parseLong(items[i]);
			}
			return res;
		} catch(Exception e) {
			BasicEnums.ErrorType.OTHERS.register(e.toString());
			return null;
		}
	}
	
	/**
	 * 从一串字符串中获取第一个等号的前面的内容，如果没有等号，返回空字符串。该方法不会对字符串进行任何处理。
	 * @param str 输入给定字符串
	 * @return 第一个等号前的内容，没有等号，返回空字符串。
	 */
	public final static java.lang.String getField(java.lang.String str) {
		if(str == null || str.length() == 0) {
			return "";
		}
		int idx = str.indexOf('=');
		if(idx < 0) {
			return "";
		}
		
		return str.substring(0, idx);
	}
	
	/**
	 * 从一串字符串中获取第一个等号的后面的内容，如果没有等号，返回该字符串。该方法不会对字符串进行任何处理。
	 * @param str 输入给定字符串
	 * @return 第一个等号后的内容，没有等号，返回本身。
	 */
	public final static java.lang.String getValue(java.lang.String str) {
		if(str == null || str.length() == 0) {
			return "";
		}
		int idx = str.indexOf('=');
		if(idx < 0) {
			return str;
		}
		
		return str.substring(idx + 1);
	}
}

