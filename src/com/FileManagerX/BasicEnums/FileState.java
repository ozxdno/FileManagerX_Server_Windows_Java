package com.FileManagerX.BasicEnums;

public enum FileState {
	/**
	 * �ļ����ڱ�ʹ�ã�����������д����ɴ�ʱ�޷����ʡ�
	 */
	Updating,
	/**
	 * �ļ��ڱ��ز����ڣ���Զ��Ҳ�����ڡ�
	 */
	NotExistInRemote,
	/**
	 * �ļ��ڱ��ز����ڣ�����Զ�˴��ڡ�
	 */
	NotExistInLocal,
	/**
	 * �������ظ��ļ�
	 */
	Downloading,
	/**
	 * ������ϣ��ڱ��ش��ڸ��ļ���δ�����ļ��κ���Ϣ����֪���Ƿ�ɶ���д��
	 */
	Downloaded,
	/**
	 * ׼���ļ��У����ļ��ɶ���д��
	 */
	Preparing,
	/**
	 * ׼��������������ʱ��ȡд�롣
	 */
	Prepared,
	/**
	 * ������Ϣ��ϡ�
	 */
	Done
}
