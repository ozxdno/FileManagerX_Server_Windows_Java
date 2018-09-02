package com.FileManagerX.Tools;

public class StringUtil {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public String expression;
	public String result;
	public String remain;
	public boolean ok;
	
	public boolean useSepMark;
	public char sepMark;
	
	public boolean useBrackets;
	public char bracketL;
	public char bracketR;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public StringUtil() {
		initThis();
	}
	private void initThis() {
		expression = "";
		result = "";
		remain = "";
		ok = true;
		
		useSepMark = true;
		sepMark = ' ';
		
		useBrackets = true;
		bracketL = '(';
		bracketR = ')';
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public StringUtil getNextStringUtil() {
		this.expression = this.remain;
		this.remain = "";
		this.result = "";
		this.ok = true;
		return this;
	}
	
	public boolean clearSepMarkLR() {
		boolean ok = true;
		ok &= this.clearSepMarkL();
		ok &= this.clearSepMarkR();
		return ok;
	}
	public boolean clearSepMarkL() {
		if(this.expression == null || this.expression.length() == 0) { return ok = true; }
		while(this.expression.length() > 0 && this.expression.charAt(0) == this.sepMark) {
			this.expression = this.expression.substring(1);
		}
		return ok = true;
	}
	public boolean clearSepMarkR() {
		if(this.expression == null || this.expression.length() == 0) { return ok = true; }
		while(this.expression.length() > 0 && this.expression.charAt(this.expression.length()-1) == this.sepMark) {
			this.expression = this.expression.substring(0, this.expression.length()-1);
		}
		return ok = true;
	}
	public boolean getNextBracketsContent() {
		if(this.expression == null || this.expression.length() == 0) { 
			this.result = "";
			this.remain = this.expression;
			return ok = false;
		}
		
		int firstL = this.expression.indexOf(bracketL);
		if(firstL < 0) {
			this.result = "";
			this.remain = this.expression;
			return ok = false;
		}
		
		int cntL = 1;
		for(int i=firstL+1; i<this.expression.length(); i++) {
			if(this.expression.charAt(i) == this.bracketL) {
				cntL++;
			}
			else if(this.expression.charAt(i) == this.bracketR) {
				cntL--;
			}
			if(cntL == 0) {
				this.result = this.expression.substring(firstL+1, i);
				this.remain = this.expression.substring(i+1);
				return this.ok = true;
			}
		}
		
		this.result = "";
		this.remain = this.expression;
		return ok = false;
	}
	public boolean getNextArg() {
		if(this.expression == null) { 
			this.result = "";
			this.remain = this.expression;
			return ok = false;
		}
		
		if(this.useSepMark) {
			this.clearSepMarkLR();
		}
		
		if(this.expression.length() == 0) { 
			this.result = "";
			this.remain = this.expression;
			return ok = false;
		}
		
		if(this.useBrackets && this.expression.charAt(0) == this.bracketL) {
			return this.getNextBracketsContent();
		}
		
		int ed = this.expression.indexOf(this.sepMark);
		if(ed < 0) {
			this.result = this.expression;
			this.remain = "";
			return this.ok = true;
		}
		else {
			this.result = this.expression.substring(0, ed);
			this.remain = this.expression.substring(ed+1);
			return this.ok = true;
		}
	}
	public boolean getNextArg_Boolean() {
		if(!this.getNextArg()) { return false; }
		try {
			return Integer.parseInt(this.result) != 0;
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			return this.ok = false;
		}
	}
	public int getNextArg_Integer() {
		if(!this.getNextArg()) { return 0; }
		try {
			return Integer.parseInt(this.result);
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			this.ok = false;
			return 0;
		}
	}
	public long getNextArg_Long() {
		if(!this.getNextArg()) { return 0; }
		try {
			return Long.parseLong(this.result);
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			this.ok = false;
			return 0;
		}
	}
	public double getNextArg_Double() {
		if(!this.getNextArg()) { return 0; }
		try {
			return Double.parseDouble(this.result);
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
			this.ok = false;
			return 0;
		}
	}
	public String getNextArg_String() {
		this.getNextArg();
		return this.result;
	}
	public String getNextArg_Brackets() {
		this.getNextBracketsContent();
		return this.result;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static String link(String[] items, String sepMark) {
		if(items == null || items.length == 0) { return ""; }
		if(items.length == 1) { return items[0]; }
		
		String res = items[0];
		for(int i=1; i<items.length; i++) {
			res += sepMark + items[i];
		}
		return res;
	}
	public final static String link(String[] items, String sepMark, int bg, int ed) {
		if(items == null || items.length == 0) { return ""; }
		if(bg < 0) { bg = 0; }
		if(ed > items.length-1) { ed = items.length-1; }
		if(bg > ed || bg >= items.length) { return ""; }
		if(ed == bg) { return items[bg]; }
		
		String res = items[bg];
		for(int i=bg+1; i<=ed; i++) {
			res += sepMark + items[i];
		}
		return res;
	}
	public final static String link(boolean[] array, String sepMark) {
		if(array == null || array.length == 0) { return ""; }
		String[] strs = new String[array.length];
		for(int i=0; i<array.length; i++) {
			strs[i] = String.valueOf(array[i]);
		}
		return link(strs, sepMark);
	}
	public final static String link(boolean[] array, String sepMark, int bg, int ed) {
		if(array == null || array.length == 0) { return ""; }
		if(bg < 0) { bg = 0; }
		if(ed > array.length-1) { ed = array.length-1; }
		if(bg > ed || bg >= array.length) { return ""; }
		
		String[] strs = new String[ed-bg+1];
		for(int i=0; i<strs.length; i++) {
			strs[i] = String.valueOf(array[i]);
		}
		return link(strs, sepMark);
	}
	public final static String link(byte[] array, String sepMark) {
		if(array == null || array.length == 0) { return ""; }
		String[] strs = new String[array.length];
		for(int i=0; i<array.length; i++) {
			strs[i] = String.valueOf(array[i]);
		}
		return link(strs, sepMark);
	}
	public final static String link(byte[] array, String sepMark, int bg, int ed) {
		if(array == null || array.length == 0) { return ""; }
		if(bg < 0) { bg = 0; }
		if(ed > array.length-1) { ed = array.length-1; }
		if(bg > ed || bg >= array.length) { return ""; }
		
		String[] strs = new String[ed-bg+1];
		for(int i=0; i<strs.length; i++) {
			strs[i] = String.valueOf(array[i]);
		}
		return link(strs, sepMark);
	}
	public final static String link(int[] array, String sepMark) {
		if(array == null || array.length == 0) { return ""; }
		String[] strs = new String[array.length];
		for(int i=0; i<array.length; i++) {
			strs[i] = String.valueOf(array[i]);
		}
		return link(strs, sepMark);
	}
	public final static String link(int[] array, String sepMark, int bg, int ed) {
		if(array == null || array.length == 0) { return ""; }
		if(bg < 0) { bg = 0; }
		if(ed > array.length-1) { ed = array.length-1; }
		if(bg > ed || bg >= array.length) { return ""; }
		
		String[] strs = new String[ed-bg+1];
		for(int i=0; i<strs.length; i++) {
			strs[i] = String.valueOf(array[i]);
		}
		return link(strs, sepMark);
	}
	public final static String link(long[] array, String sepMark) {
		if(array == null || array.length == 0) { return ""; }
		String[] strs = new String[array.length];
		for(int i=0; i<array.length; i++) {
			strs[i] = String.valueOf(array[i]);
		}
		return link(strs, sepMark);
	}
	public final static String link(long[] array, String sepMark, int bg, int ed) {
		if(array == null || array.length == 0) { return ""; }
		if(bg < 0) { bg = 0; }
		if(ed > array.length-1) { ed = array.length-1; }
		if(bg > ed || bg >= array.length) { return ""; }
		
		String[] strs = new String[ed-bg+1];
		for(int i=0; i<strs.length; i++) {
			strs[i] = String.valueOf(array[i]);
		}
		return link(strs, sepMark);
	}
	public final static String link(double[] array, String sepMark) {
		if(array == null || array.length == 0) { return ""; }
		String[] strs = new String[array.length];
		for(int i=0; i<array.length; i++) {
			strs[i] = String.valueOf(array[i]);
		}
		return link(strs, sepMark);
	}
	public final static String link(double[] array, String sepMark, int bg, int ed) {
		if(array == null || array.length == 0) { return ""; }
		if(bg < 0) { bg = 0; }
		if(ed > array.length-1) { ed = array.length-1; }
		if(bg > ed || bg >= array.length) { return ""; }
		
		String[] strs = new String[ed-bg+1];
		for(int i=0; i<strs.length; i++) {
			strs[i] = String.valueOf(array[i]);
		}
		return link(strs, sepMark);
	}
	public final static <T> String link(T[] array, String sepMark) {
		if(array == null || array.length == 0) { return ""; }
		String[] strs = new String[array.length];
		for(int i=0; i<array.length; i++) {
			strs[i] = array[i].toString();
		}
		return link(strs, sepMark);
	}
	public final static <T> String link(T[] array, String sepMark, int bg, int ed) {
		if(array == null || array.length == 0) { return ""; }
		if(bg < 0) { bg = 0; }
		if(ed > array.length-1) { ed = array.length-1; }
		if(bg > ed || bg >= array.length) { return ""; }
		
		String[] strs = new String[ed-bg+1];
		for(int i=0; i<strs.length; i++) {
			strs[i] = array[i+bg].toString();
		}
		return link(strs, sepMark);
	}
	public final static <T extends com.FileManagerX.Interfaces.IPublic> String 
			plink(T[] array, String sepMark) {
		if(array == null || array.length == 0) { return ""; }
		String[] strs = new String[array.length];
		for(int i=0; i<array.length; i++) {
			strs[i] = array[i].output();
		}
		return link(strs, sepMark);
	}
	public final static <T extends com.FileManagerX.Interfaces.IPublic> String 
			plink(T[] array, String sepMark, int bg, int ed) {
		if(array == null || array.length == 0) { return ""; }
		if(bg < 0) { bg = 0; }
		if(ed > array.length-1) { ed = array.length-1; }
		if(bg > ed || bg >= array.length) { return ""; }
		
		String[] strs = new String[ed-bg+1];
		for(int i=0; i<strs.length; i++) {
			strs[i] = array[i+bg].output();
		}
		return link(strs, sepMark);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static java.util.ArrayList<String> split2string(String str, String sepMark) {
		if(str == null || str.length() == 0) {
			return new java.util.ArrayList<>();
		}
		
		java.util.ArrayList<String> res = new java.util.ArrayList<>();
		if(sepMark == null || sepMark.length() == 0) {
			res.add(str);
			return res;
		}
		
		while(str.length() > 0) {
			int idx = -1;
			for(int i=0; i<=str.length()-sepMark.length(); i++) {
				if(sepMark.equals(str.substring(i, i+sepMark.length()))) {
					idx = i;
					break;
				}
			}
			if(idx >= 0) {
				res.add(str.substring(0, idx));
				str = str.substring(idx + sepMark.length());
				if(str.length() == 0) {
					res.add("");
					break;
				}
			}
			else {
				res.add(str);
				break;
			}
		}
		
		return res;
	}
	public final static java.util.ArrayList<Boolean> split2boolean(String str, String sepMark) {
		java.util.ArrayList<String> strs = split2string(str, sepMark);
		java.util.ArrayList<Boolean> res = new java.util.ArrayList<>();
		for(String s : strs) {
			try {
				res.add(Integer.parseInt(s) != 0);
			} catch(Exception e) {
				com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
				res.add(false);
			}
		}
		return res;
	}
	public final static java.util.ArrayList<Byte> split2byte(String str, String sepMark) {
		java.util.ArrayList<String> strs = split2string(str, sepMark);
		java.util.ArrayList<Byte> res = new java.util.ArrayList<>();
		for(String s : strs) {
			try {
				res.add(Byte.parseByte(s));
			} catch(Exception e) {
				com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
				res.add((byte)0);
			}
		}
		return res;
	}
	public final static java.util.ArrayList<Integer> split2int(String str, String sepMark) {
		java.util.ArrayList<String> strs = split2string(str, sepMark);
		java.util.ArrayList<Integer> res = new java.util.ArrayList<>();
		for(String s : strs) {
			try {
				res.add(Integer.parseInt(s));
			} catch(Exception e) {
				com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
				res.add(0);
			}
		}
		return res;
	}
	public final static java.util.ArrayList<Long> split2long(String str, String sepMark) {
		java.util.ArrayList<String> strs = split2string(str, sepMark);
		java.util.ArrayList<Long> res = new java.util.ArrayList<>();
		for(String s : strs) {
			try {
				res.add(Long.parseLong(s));
			} catch(Exception e) {
				com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
				res.add(0L);
			}
		}
		return res;
	}
	public final static java.util.ArrayList<Double> split2double(String str, String sepMark) {
		java.util.ArrayList<String> strs = split2string(str, sepMark);
		java.util.ArrayList<Double> res = new java.util.ArrayList<>();
		for(String s : strs) {
			try {
				res.add(Double.parseDouble(s));
			} catch(Exception e) {
				com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(e.toString());
				res.add(0d);
			}
		}
		return res;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static String clearSepMarkLR(String str, char sepMark) {
		str = clearSepMarkL(str, sepMark);
		str = clearSepMarkR(str, sepMark);
		return str;
	}
	public final static String clearSepMarkL(String str, char sepMark) {
		if(str == null || str.length() == 0) { return str; }
		while(str.length() > 0 && str.charAt(0) == sepMark) {
			str = str.substring(1);
		}
		return str;
	}
	public final static String clearSepMarkR(String str, char sepMark) {
		if(str == null || str.length() == 0) { return str; }
		while(str.length() > 0 && str.charAt(str.length()-1) == sepMark) {
			str = str.substring(0, str.length()-1);
		}
		return str;
	}
	public final static String clearSepMarkLR(String str) {
		str = clearSepMarkL(str, ' ');
		str = clearSepMarkR(str, ' ');
		return str;
	}
	public final static String clearSepMarkL(String str) {
		return clearSepMarkL(str, ' ');
	}
	public final static String clearSepMarkR(String str) {
		return clearSepMarkR(str, ' ');
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static String getField_FV(String str) {
		if(str == null || str.length() == 0) { return str; }
		int idx = str.indexOf('=');
		if(idx > 0) {
			return clearSepMarkLR(str.substring(0, idx), ' ');
		}
		else {
			return "";
		}
	}
	public final static String getValue_FV(String str) {
		if(str == null || str.length() == 0) { return str; }
		int idx = str.indexOf('=');
		if(idx > 0) {
			return clearSepMarkLR(str.substring(idx+1), ' ');
		}
		else {
			return clearSepMarkLR(str, ' ');
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}