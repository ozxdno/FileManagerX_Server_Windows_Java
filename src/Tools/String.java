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
}

