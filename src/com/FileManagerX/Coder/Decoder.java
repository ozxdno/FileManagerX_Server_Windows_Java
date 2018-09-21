package com.FileManagerX.Coder;

/**
 * 把一个字符串解析成想要的信息格式。
 * 
 * @author ozxdno
 *
 */
public class Decoder {
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static String FAILED_INPUT = "Receive Wrong Message";
	public final static IDecode DECODE = new Decode_Location();

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static byte[] Decode_Byte2Byte(byte[] bytes) {
		byte[] result = new byte[bytes.length >> 1];
		for(int i=0; i<bytes.length; i+=2) {
			int l = bytes[i] - 'A';
			int h = bytes[i+1] - 'A';
			result[i>>1] = (byte)( (h<<4) + l - 128);
		}
		return result;
	}
	
	public final static String Decode_Byte2String(byte[] bytes) {
		return new String(DECODE.decode(bytes));
	}
	public final static byte[] Decode_String2Byte(String str) {
		return DECODE.decode(str.getBytes());
	}
	public final static String Decode_String2String(String str) {
		return new String(DECODE.decode(str.getBytes()));
	}
	public final static String Decode_DataBaseString2String(String str) {
		return str;
	}
	public final static com.FileManagerX.Interfaces.ITransport Decode_Byte2Transport(byte[] bytes) {
		try {
			String s = new String(bytes);
			int type = s.charAt(0);
			
			s = s.substring(1);
			s = new String(DECODE.decode(s.getBytes()));
			
			String name = com.FileManagerX.Tools.StringUtil.getField_FV(s);
			com.FileManagerX.Interfaces.ITransport t = null;
			
			if(type == 'C') { 
				t = (com.FileManagerX.Interfaces.ITransport)
					com.FileManagerX.Globals.Datas.Commands.get(name).newInstance(); 
			}
			if(type == 'R') { 
				t = (com.FileManagerX.Interfaces.ITransport)
					com.FileManagerX.Globals.Datas.Replies.get(name).newInstance(); 
			}
			
			com.FileManagerX.BasicModels.Config c = t.input(s);
			return c == null || !c.getIsOK() ? null : t;
			
		} catch(Exception e) {
			com.FileManagerX.BasicEnums.ErrorType.OTHERS.register(FAILED_INPUT, e.toString());
			return null;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
