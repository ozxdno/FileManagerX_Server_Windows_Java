package com.FileManagerX.Tools;

public final class Random {

	public final static int SimpleRandomNumber(int bg, int ed) {
		int amount = ed - bg + 1;
		if(amount <= 0) { return bg; }
		int rand = (int) (com.FileManagerX.Tools.Time.getTicks() % amount);
		return bg + rand;
	}
}
