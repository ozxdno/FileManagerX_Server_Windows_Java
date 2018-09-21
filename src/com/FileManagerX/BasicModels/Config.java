package com.FileManagerX.BasicModels;

public class Config implements com.FileManagerX.Interfaces.IPublic {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private String field;
	private String value;
	private String[] items;
	private boolean ok; 
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public String getField() {
		return field;
	}
	public String getValue() {
		return value;
	}
	public String[] getItems() {
		return items;
	}
	public boolean getIsOK() {
		return ok;
	}
	public int getItemsSize() {
		return items.length;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean setLine(String line) {
		if(line == null || line.length() == 0) {
			items = new String[0];
			return false;
		}
		line = com.FileManagerX.Tools.StringUtil.clearSepMarkLR(line);
		int idx = line.indexOf('=');
		if(idx < 0) {
			field = "";
			this.setValue(line);
			return true;
		}
		int cut1 = idx-1;
		int cut2 = idx+1;
		while(cut1 >= 0 && line.charAt(cut1) == ' ') {
			cut1--;
		}
		while(cut2 < line.length() && line.charAt(cut2) == ' ') {
			cut2++;
		}
		setField(line.substring(0, cut1+1));
		setValue(line.substring(cut2));
		return true;
	}
	public boolean setField(String field) {
		if(field == null) {
			return false;
		}
		field = com.FileManagerX.Tools.StringUtil.clearSepMarkLR(field);
		this.field = field;
		return true;
	}
	public boolean setValue(String value) {
		if(value == null) {
			return false;
		}
		value = com.FileManagerX.Tools.StringUtil.clearSepMarkLR(value);
		this.value = value;
		if(this.value.length() == 0) {
			this.items = new String[0];
			return true;
		}
		java.util.ArrayList<String> strs = com.FileManagerX.Tools.StringUtil.split2string(value, "|");
		this.items = com.FileManagerX.Tools.List2Array.toStringArray(strs);
		return true;
	}
	public boolean setItems(String[] items) {
		if(items == null) {
			return false;
		}
		this.items = items;
		value = "";
		if(items.length == 0) {
			return true;
		}
		for(int i=0; i<items.length; i++) {
			value += items[i] + "|";
		}
		value = value.substring(0, value.length()-1);
		return true;
	}
	public boolean setIsOK(boolean ok) {
		this.ok = ok;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Config() {
		initThis();
	}
	public Config(String line) {
		initThis();
		setLine(line);
	}
	public Config(String field, String value) {
		initThis();
		setField(field);
		setValue(value);
	}
	public Config(String field, String[] items) {
		initThis();
		setField(field);
		setItems(items);
	}
	private void initThis() {
		field = "";
		value = "";
		items = new String[0];
		ok = true;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void clear() {
		initThis();
	}
	public String toString() {
		return this.field.length() == 0 ? "No Name" : this.field;
	}
	public Config toConfig() {
		return this;
	}
	public String output() {
		return field + " = " + value;
	}
	public Config input(String in) {
		this.setLine(in);
		return this;
	}
	public Config input(Config c) {
		if(c == null) { return null; }
		this.copyValue(c);
		return this;
	}
	public void copyReference(Object o) {
		if(o == null) { return; }
		
		if(o instanceof Config) {
			Config c = (Config)o;
			this.field = c.field;
			this.value = c.value;
			this.items = c.items;
			this.ok = c.ok;
			return;
		}
	}
	public void copyValue(Object o) {
		if(o == null) { return; }
		
		if(o instanceof Config) {
			Config c = (Config)o;
			this.field = new String(c.field);
			this.value = new String(c.value);
			this.ok = c.ok;
			this.items = new String[c.items.length];
			for(int i=0; i<items.length; i++) {
				this.items[i] = new String(c.items[i]);
			}
		}
	}
	
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean isEmpty() {
		return this.value.length() == 0;
	}
	
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean getBoolean() {
		try {
			ok = true;
			return Double.parseDouble(value) != 0;
		} catch(Exception e) {
			ok = false;
			return false;
		}
	}
	public int getInt() {
		try {
			ok = true;
			return Integer.parseInt(value);
		} catch(Exception e) {
			ok = false;
			return 0;
		}
	}
	public long getLong() {
		try {
			ok = true;
			return Long.parseLong(value);
		} catch(Exception e) {
			ok = false;
			return 0;
		}
	}
	public double getDouble() {
		try {
			ok = true;
			return Double.parseDouble(value);
		} catch(Exception e) {
			ok = false;
			return 0;
		}
	}
	public String getString() {
		ok = true;
		return value;
	}
	public String getEncodeString() {
		ok = true;
		return com.FileManagerX.Coder.Encoder.Encode_String2String(field);
	}
	public String getDecodeString() {
		ok = true;
		return com.FileManagerX.Coder.Decoder.Decode_String2String(field);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean getBoolean(int item) {
		try {
			ok = true;
			return Double.parseDouble(items[item]) != 0;
		} catch(Exception e) {
			ok = false;
			return false;
		}
	}
	public int getInt(int item) {
		try {
			ok = true;
			return Integer.parseInt(items[item]);
		} catch(Exception e) {
			ok = false;
			return 0;
		}
	}
	public long getLong(int item) {
		try {
			ok = true;
			return Long.parseLong(items[item]);
		} catch(Exception e) {
			ok = false;
			return 0;
		}
	}
	public double getDouble(int item) {
		try {
			ok = true;
			return Double.parseDouble(items[item]);
		} catch(Exception e) {
			ok = false;
			return 0;
		}
	}
	public String getString(int item) {
		if(value != null && value.length() == 0 && item == 0) {
			return "";
		}
		
		try {
			ok = true;
			return items[item];
		} catch(Exception e) {
			ok = false;
			return null;
		}
	}
	public String getEncodeString(int item) {
		if(value != null && value.length() == 0 && item == 0) {
			return "";
		}
		
		try {
			ok = true;
			return com.FileManagerX.Coder.Encoder.Encode_String2String(items[item]);
		} catch(Exception e) {
			ok = false;
			return null;
		}
	}
	public String getDecodeString(int item) {
		if(value != null && value.length() == 0 && item == 0) {
			return "";
		}
		
		try {
			ok = true;
			return com.FileManagerX.Coder.Decoder.Decode_String2String(items[item]);
		} catch(Exception e) {
			ok = false;
			return null;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean[] getBooleanList() {
		boolean[] list = new boolean[items.length];
		try {
			ok = true;
			for(int i=0; i<items.length; i++) {
				list[i] = Double.parseDouble(items[i]) != 0;
			}
			return list;
		} catch(Exception e) {
			ok = false;
			return null;
		}
	}
	public int[] getIntList() {
		int[] list = new int[items.length];
		try {
			ok = true;
			for(int i=0; i<items.length; i++) {
				list[i] = Integer.parseInt(items[i]);
			}
			return list;
		} catch(Exception e) {
			ok = false;
			return null;
		}
	}
	public long[] getLongList() {
		long[] list = new long[items.length];
		try {
			ok = true;
			for(int i=0; i<items.length; i++) {
				list[i] = Long.parseLong(items[i]);
			}
			return list;
		} catch(Exception e) {
			ok = false;
			return null;
		}
	}
	public double[] getDoubleList() {
		double[] list = new double[items.length];
		try {
			ok = true;
			for(int i=0; i<items.length; i++) {
				list[i] = Double.parseDouble(items[i]);
			}
			return list;
		} catch(Exception e) {
			ok = false;
			return null;
		}
	}
	public String[] getStringList() {
		ok = true;
		return items;
	}
	public String[] getEncodeStringList() {
		ok = true;
		String[] list = new String[items.length];
		for(int i=0; i<items.length; i++) {
			list[i] = com.FileManagerX.Coder.Encoder.Encode_String2String(items[i]);
		}
		return list;
	}
	public String[] getDecodeStringList() {
		ok = true;
		String[] list = new String[items.length];
		for(int i=0; i<items.length; i++) {
			list[i] = com.FileManagerX.Coder.Decoder.Decode_String2String(items[i]);
		}
		return list;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void addToTop(String item) {
		if(item == null) {
			ok = false;
			return;
		}
		value = value.length() == 0 ? item : item + "|" + value;
		ok = true;
		setValue( value );
	}
	public void addToTop(boolean item) {
		value = value.length() == 0 ? (item?"1":"0") : (item?"1":"0") + "|" + value;
		ok = true;
		setValue( value );
	}
	public void addToTop(int item) {
		value = value.length() == 0 ? String.valueOf(item) : String.valueOf(item) + "|" + value;
		ok = true;
		setValue( value );
	}
	public void addToTop(long item) {
		value = String.valueOf(item) + "|" + value;
		ok = true;
		setValue( value );
	}
	public void addToTop(double item) {
		value = value.length() == 0 ? String.valueOf(item) : String.valueOf(item) + "|" + value;
		ok = true;
		setValue( value );
	}
	public void addToTop(com.FileManagerX.BasicModels.Config c) {
		ok = true;
		if(c == null || c.value.length() == 0) {
			return;
		}
		this.setValue(c.value + "|" + value);
	}
	public void addToTop(byte[] item) {
		if(item == null) {
			ok = false;
			return;
		}
		
		String str = new String(com.FileManagerX.Coder.Encoder.Encode_Byte2Byte(item));
		value = value.length() == 0 ? str : str + "|" + value;
		ok = true;
		setValue( value );
	}
	
	public void addToTop_Encode(String item) {
		if(item == null) {
			ok = false;
			return;
		}
		item = com.FileManagerX.Coder.Encoder.Encode_String2String(item);
		value = value.length() == 0 ? item : item + "|" + value;
		ok = true;
		setValue( value );
	}
	public void addToTop_Decode(String item) {
		if(item == null) {
			ok = false;
			return;
		}
		item = com.FileManagerX.Coder.Decoder.Decode_String2String(item);
		value = value.length() == 0 ? item : item + "|" + value;
		ok = true;
		setValue( value );
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void addToBottom(String item) {
		if(item == null) {
			ok = false;
			return;
		}
		value = value.length() == 0 ? item : value + "|" + item;
		ok = true;
		setValue( value );
	}
	public void addToBottom(boolean item) {
		value = value.length() == 0 ? (item?"1":"0") : value + "|" + (item?"1":"0");
		ok = true;
		setValue( value );
	}
	public void addToBottom(int item) {
		value = value.length() == 0 ? String.valueOf(item) : value + "|" + String.valueOf(item);
		ok = true;
		setValue( value );
	}
	public void addToBottom(long item) {
		value = value.length() == 0 ? String.valueOf(item) : value + "|" + String.valueOf(item);
		ok = true;
		setValue( value );
	}
	public void addToBottom(double item) {
		value = value.length() == 0 ? String.valueOf(item) : value + "|" + String.valueOf(item);
		ok = true;
		setValue( value );
	}
	public void addToBottom(Config c) {
		ok = true;
		if(c == null || c.value.length() == 0) {
			return;
		}
		value = value.length() == 0 ? c.value : value + "|" + c.value;
		this.setValue(value);
	}
	
	public void addToBottom_Encode(String item) {
		if(item == null) {
			ok = false;
			return;
		}
		item = com.FileManagerX.Coder.Encoder.Encode_String2String(item);
		value = value.length() == 0 ? item : value + "|" + item;
		ok = true;
		setValue( value );
	}
	public void addToBottom_Decode(String item) {
		if(item == null) {
			ok = false;
			return;
		}
		item = com.FileManagerX.Coder.Decoder.Decode_String2String(item);
		value = value.length() == 0 ? item : value + "|" + item;
		ok = true;
		setValue( value );
	}
	public void addToBottom(byte[] item) {
		if(item == null) {
			ok = false;
			return;
		}
		String str = new String(com.FileManagerX.Coder.Encoder.Encode_Byte2Byte(item));
		value = value.length() == 0 ? str : value + "|" + str;
		ok = true;
		setValue( value );
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean fetchFirstBoolean() {
		boolean res = false;
		try {
			res = Double.parseDouble(items[0]) != 0;
			ok = true;
			setValue(com.FileManagerX.Tools.StringUtil.link(items, "|", 1, items.length-1));
			return res;
		} catch(Exception e) {
			ok = false;
			return res;
		}
	}
	public int fetchFirstInt() {
		int res = 0;
		try {
			res = Integer.parseInt(items[0]);
			ok = true;
			setValue(com.FileManagerX.Tools.StringUtil.link(items, "|", 1, items.length-1));
			return res;
		} catch(Exception e) {
			ok = false;
			return res;
		}
	}
	public long fetchFirstLong() {
		long res = 0;
		try {
			res = Long.parseLong(items[0]);
			ok = true;
			setValue(com.FileManagerX.Tools.StringUtil.link(items, "|", 1, items.length-1));
			return res;
		} catch(Exception e) {
			ok = false;
			return res;
		}
	}
	public double fetchFirstDouble() {
		double res = 0;
		try {
			res = Double.parseDouble(items[0]);
			ok = true;
			setValue(com.FileManagerX.Tools.StringUtil.link(items, "|", 1, items.length-1));
			return res;
		} catch(Exception e) {
			ok = false;
			return res;
		}
	}
	public String fetchFirstString() {
		if(value != null && value.length() == 0) {
			ok = true;
			return "";
		}
		String res = "";
		try {
			res = items[0];
			ok = true;
			setValue(com.FileManagerX.Tools.StringUtil.link(items, "|", 1, items.length-1));
			return res;
		} catch(Exception e) {
			ok = false;
			return res;
		}
	}
	public Config fetchFirstConfig(int amount) {
		ok = true;
		if(amount > this.items.length) {
			ok = false;
			amount = this.items.length;
		}
		if(amount < 0) {
			ok = false;
			amount =  0;
		}
		String c1 = com.FileManagerX.Tools.StringUtil.link(items, "|", 0, amount-1);
		String c2 = com.FileManagerX.Tools.StringUtil.link(items, "|", amount, items.length-1);
		this.setValue(c2);
		Config c = new Config();
		c.setValue(c1);
		return c;
	}
	
	public String fetchFirstString_Encode() {
		if(value != null && value.length() == 0) {
			ok = true;
			return "";
		}
		String res = "";
		try {
			res = items[0];
			ok = true;
			setValue(com.FileManagerX.Tools.StringUtil.link(items, "|", 1, items.length-1));
			return com.FileManagerX.Coder.Encoder.Encode_String2String(res);
		} catch(Exception e) {
			ok = false;
			return res;
		}
	}
	public String fetchFirstString_Decode() {
		if(value != null && value.length() == 0) {
			ok = true;
			return "";
		}
		String res = "";
		try {
			res = items[0];
			ok = true;
			setValue(com.FileManagerX.Tools.StringUtil.link(items, "|", 1, items.length-1));
			return com.FileManagerX.Coder.Decoder.Decode_String2String(res);
		} catch(Exception e) {
			ok = false;
			return res;
		}
	}
	public byte[] fetchFirstBytes() {
		if(value != null && value.length() == 0) {
			ok = true;
			return new byte[0];
		}
		try {
			byte[] bytes = items[0].getBytes();
			ok = true;
			setValue(com.FileManagerX.Tools.StringUtil.link(items, "|", 1, items.length-1));
			return com.FileManagerX.Coder.Decoder.Decode_Byte2Byte(bytes);
		} catch(Exception e) {
			ok = false;
			return new byte[0];
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean fetchLastBoolean() {
		boolean res = false;
		try {
			res = Double.parseDouble(items[items.length-1]) != 0;
			ok = true;
			setValue(com.FileManagerX.Tools.StringUtil.link(items, "|", 0, items.length-2));
			return res;
		} catch(Exception e) {
			ok = false;
			return res;
		}
	}
	public int fetchLastInt() {
		int res = 0;
		try {
			res = Integer.parseInt(items[items.length-1]);
			ok = true;
			setValue(com.FileManagerX.Tools.StringUtil.link(items, "|", 0, items.length-2));
			return res;
		} catch(Exception e) {
			ok = false;
			return res;
		}
	}
	public long fetchLastLong() {
		long res = 0;
		try {
			res = Long.parseLong(items[items.length-1]);
			ok = true;
			setValue(com.FileManagerX.Tools.StringUtil.link(items, "|", 0, items.length-2));
			return res;
		} catch(Exception e) {
			ok = false;
			return res;
		}
	}
	public double fetchLastDouble() {
		double res = 0;
		try {
			res = Double.parseDouble(items[items.length-1]);
			ok = true;
			setValue(com.FileManagerX.Tools.StringUtil.link(items, "|", 0, items.length-2));
			return res;
		} catch(Exception e) {
			ok = false;
			return res;
		}
	}
	public String fetchLastString() {
		if(value != null && value.length() == 0) {
			ok = true;
			return "";
		}
		String res = "";
		try {
			res = items[items.length-1];
			ok = true;
			setValue(com.FileManagerX.Tools.StringUtil.link(items, "|", 0, items.length-2));
			return res;
		} catch(Exception e) {
			ok = false;
			return res;
		}
	}
	public Config fetchLastConfig(int amount) {
		ok = true;
		if(amount > this.items.length) {
			ok = false;
			amount = this.items.length;
		}
		if(amount < 0) {
			ok = false;
			amount =  0;
		}
		String c1 = com.FileManagerX.Tools.StringUtil.link(items, "|", 0, items.length - amount - 1);
		String c2 = com.FileManagerX.Tools.StringUtil.link(items, "|", items.length - amount, items.length-1);
		this.setValue(c1);
		Config c = new Config();
		c.setValue(c2);
		return c;
	}
	
	public String fetchLastString_Encode() {
		if(value != null && value.length() == 0) {
			ok = true;
			return "";
		}
		String res = "";
		try {
			res = items[items.length-1];
			ok = true;
			setValue(com.FileManagerX.Tools.StringUtil.link(items, "|", 0, items.length-2));
			return com.FileManagerX.Coder.Encoder.Encode_String2String(res);
		} catch(Exception e) {
			ok = false;
			return res;
		}
	}
	public String fetchLastString_Decode() {
		if(value != null && value.length() == 0) {
			ok = true;
			return "";
		}
		String res = "";
		try {
			res = items[items.length-1];
			ok = true;
			setValue(com.FileManagerX.Tools.StringUtil.link(items, "|", 0, items.length-2));
			return com.FileManagerX.Coder.Decoder.Decode_String2String(res);
		} catch(Exception e) {
			ok = false;
			return res;
		}
	}
	public byte[] fetchLastBytes() {
		if(value != null && value.length() == 0) {
			ok = true;
			return new byte[0];
		}
		try {
			byte[] bytes = items[items.length-1].getBytes();
			ok = true;
			setValue(com.FileManagerX.Tools.StringUtil.link(items, "|", 0, items.length-2));
			return com.FileManagerX.Coder.Decoder.Decode_Byte2Byte(bytes);
		} catch(Exception e) {
			ok = false;
			return new byte[0];
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
