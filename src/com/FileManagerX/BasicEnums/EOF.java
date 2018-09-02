package com.FileManagerX.BasicEnums;

public enum EOF {

	N('\n', '1'),
	R('\r', '2'),
	S('|' , '3'),
	T('\\', '4'),
	;
	
	private byte eof;
	private byte rep;
	
	private EOF(char eof, char rep) {
		this.eof = (byte)eof;
		this.rep = (byte)rep;
	}
	
	public byte getSpecial() {
		return eof;
	}
	public byte getReplce() {
		return rep;
	}
	
	public final static EOF getEofBySpecial(byte eof) {
		if(eof == N.eof) {
			return N;
		}
		if(eof == R.eof) {
			return R;
		}
		if(eof == S.eof) {
			return S;
		}
		if(eof == T.eof) {
			return T;
		}
		return null;
	}
	public final static EOF getEofByReplace(byte rep) {
		if(rep == N.rep) {
			return N;
		}
		if(rep == R.rep) {
			return R;
		}
		if(rep == S.rep) {
			return S;
		}
		if(rep == T.rep) {
			return T;
		}
		return null;
	}
}
