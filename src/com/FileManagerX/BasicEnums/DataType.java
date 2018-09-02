package com.FileManagerX.BasicEnums;

public enum DataType {
	
	STATIC(""),
	BOOLEAN("BOOLEAN"),
	BYTE("INT"),
	CHAR("INT"),
	INTEGER("INT"),
	LONG("BIGINT"),
	DOUBLE("DOUBLE"),
	STRING("VARCHAR(1024)"),
	ENUM("VARCHAR(100)"),
	OTHERS("VARCHAR(5120)"),
	;
	
	private String dbtype = "";
	
	private DataType(String dbtype) {
		this.dbtype = dbtype;
	}
	
	public String getDataBaseType() {
		return this.dbtype;
	}
}
