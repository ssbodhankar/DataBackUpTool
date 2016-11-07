package com.core.dataobjects;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author Shashank
 *
 */
public class FileIdentifier
{
	private String filePathName = null;
	private String md5ChecksumValue = null;

	/**
	 * 
	 * @param filePath
	 * @param md5Value
	 */
	public FileIdentifier(String filePath, String md5Value)
	{
		if (!StringUtils.isEmpty(filePath))
		{
			filePathName = StringUtils.replace(filePath, "\\", "\\\\");
		}
		if (!StringUtils.isEmpty(md5Value))
		{
			md5ChecksumValue = md5Value;
		}
	}

	/**
	 * @return the filePathName
	 */
	public String getFilePathName()
	{
		return filePathName;
	}

	/**
	 * @return the md5ChecksumValue
	 */
	public String getMd5ChecksumValue()
	{
		return md5ChecksumValue;
	}

}
