package Tools;

import java.util.*;

public class String {
	
	/**
	 * �� �ı��л�ȡһ��,���������һ���ı�ĩβ�Ķ����ַ���
	 * @param text ��Ҫ������ı�
	 * @return ����ɹ�����ı�
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
	 * ���һ���ı��������˵Ŀո�
	 * @param line ����һ���ַ���
	 * @return �������ı�
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
	 * ��ָ����ʶ�����Ӷ���ַ�����ʹ֮��Ϊһ�����ַ���
	 * @param items �������ַ����б�
	 * @param mark ��ʶ��
	 * @param bgIndex ָ����ʼλ��
	 * @param edIndex ָ������λ��
	 * @return ������ɺ���ַ���
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
	 * ��ָ����ʶ�����Ӷ���ַ�����ʹ֮��Ϊһ�����ַ���
	 * @param items �������ַ����б�
	 * @param mark ��ʶ��
	 * @return ������ɺ���ַ���
	 */
	public static final java.lang.String link(
			java.lang.String[] items,
			java.lang.String mark) {
		return link(items,mark,0,items.length-1);
	}
	/**
	 * ��ָ��һ���ַ����ָ�����ɸ����ַ������ָ��־Ϊ mark ��
	 * @param line ָ���ַ���
	 * @param mark ��ʶ��
	 * @return �ָ���
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

