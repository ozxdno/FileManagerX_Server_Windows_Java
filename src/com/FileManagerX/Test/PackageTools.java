package com.FileManagerX.Test;

public class PackageTools {

	public static void main(String[] args) {
		java.util.HashMap<String, Class<?>> res = com.FileManagerX.Tools.Package.iteratorPackage(
				"com.FileManagerX.Commands",
				com.FileManagerX.Commands.BaseCommand.class
			);
		for(java.util.Map.Entry<String, Class<?>> e : res.entrySet()) {
			System.out.println(e.getValue().getName());
		}
	}

}
