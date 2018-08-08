package com.FileManagerX.BasicEnums;

public enum FileState {
	/**
	 * 文件正在被使用，包括：读、写等造成此时无法访问。
	 */
	Updating,
	/**
	 * 文件在本地不存在，在远端也不存在。
	 */
	NotExistInRemote,
	/**
	 * 文件在本地不存在，但在远端存在。
	 */
	NotExistInLocal,
	/**
	 * 正在下载该文件
	 */
	Downloading,
	/**
	 * 下载完毕，在本地存在该文件，未读入文件任何信息。不知道是否可读可写。
	 */
	Downloaded,
	/**
	 * 准备文件中，让文件可读可写。
	 */
	Preparing,
	/**
	 * 准备就绪，可以随时读取写入。
	 */
	Prepared,
	/**
	 * 读入信息完毕。
	 */
	Done
}
