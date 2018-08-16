package com.FileManagerX.Factories;

import com.FileManagerX.Interfaces.*;
import com.FileManagerX.Processes.*;

public class ProcessFactory {

	public final static IProcess createBasic() {
		return new BasicProcess();
	}
	public final static IProcess createMain() {
		return new MainProcess();
	}
	
	
}
