package com.FileManagerX.Factories;

import com.FileManagerX.Interfaces.*;
import com.FileManagerX.Processes.*;

public class ProcessFactory {

	public final static IProcess createMain() {
		return new Main();
	}
	
	public final static IProcessManager createManager() {
		return new com.FileManagerX.Processes.Manager();
	}
	
}
