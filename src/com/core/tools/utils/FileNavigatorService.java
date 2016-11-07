package com.core.tools.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Iterator;

import com.core.dataobjects.FileIdentifier;
import com.core.tools.FileTreeNavigator;

/**
 * This Service class performs the operation of adding the MD5 values in the
 * Database and registering the directories with the watcher.
 * 
 * @author Shashank
 *
 */
public class FileNavigatorService
{

	/**
	 * 
	 * @param rootDirectory
	 */
	public static void initilize(String rootDirectory)
	{
		System.out.println("Root directory is: " + rootDirectory);
		Path start = Paths.get(rootDirectory);
		FileTreeNavigator visitor = new FileTreeNavigator();
		try
		{
			Files.walkFileTree(start, visitor);
			System.out.println("navigation done.");
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		if (!visitor.getFileIDList().isEmpty())
		{
			FileIdentifier currFileId = null;
			int rowAdded = 0;
			Iterator<FileIdentifier> fileIdItr = visitor.getFileIDList().iterator();
			while (fileIdItr.hasNext())
			{
				currFileId = fileIdItr.next();
				String addMD5Value = "INSERT INTO `files_md5`.`files` (`fileName`,`md5Value`) VALUES ('" + currFileId.getFilePathName() + "' , '" + currFileId.getMd5ChecksumValue() + "' );";
				System.out.println(addMD5Value);
				try
				{
					rowAdded = rowAdded + DBconnectorService.insertData(addMD5Value);
				} catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
			System.out.println("Total Number of rows added" + rowAdded);
		}

	}

	/**
	 * 
	 * @param rootDirectory
	 */
	public static void registerDir(String rootDirectory)
	{
		System.out.println("Directory registered is : " + rootDirectory);
		Path start = Paths.get(rootDirectory);
		FileTreeNavigator visitor = new FileTreeNavigator();
		try
		{
			Files.walkFileTree(start, visitor);
			System.out.println("navigation done.");
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}
}
