package com.FileManagerX.BasicEnums;

public enum EOF {

	N('\n'),
	R('\r'),
	S('|' ),
	;
	
	private byte eof;
	private byte rep;
	
	private EOF(char c) {
		eof = (byte)c;
		rep = (byte) (eof & 0x80);
	}
	
	public byte getSpecial() {
		return eof;
	}
	public byte getReplce() {
		return rep;
	}
	
	
}
