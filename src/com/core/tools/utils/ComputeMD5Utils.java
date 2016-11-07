package com.core.tools.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ComputeMD5Utils
{
	/**
	 * 
	 * @param filePath
	 * @return
	 */
	public static byte[] getMD5Value(Path filePath)
	{
		String inputFileName = filePath.toString();
		File inputFile = new File(inputFileName);
		FileInputStream fileInputStream = null;
		byte[] MD5Checksum = null;
		try
		{
			long startTime = System.nanoTime();
			fileInputStream = new FileInputStream(inputFile);
			MessageDigest digest = MessageDigest.getInstance("MD5");
			byte[] byteBuffer = new byte[1024];
			int readBytes = -1;
			while ((readBytes = fileInputStream.read(byteBuffer)) != -1)
			{
				digest.update(byteBuffer, 0, readBytes);
			}
			MD5Checksum = digest.digest();
			System.out.println(MD5Checksum);
			long endTime = System.nanoTime();

			System.out.println("The file size is: " + Files.size(Paths.get(inputFileName)));
			System.out.println("The time taken to compute in nano seconds: " + (endTime - startTime));

		} catch (NoSuchAlgorithmException | IOException e)
		{
			e.printStackTrace();
		} finally
		{
			if (fileInputStream != null)
			{
				try
				{
					fileInputStream.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		return MD5Checksum;
	}

}
