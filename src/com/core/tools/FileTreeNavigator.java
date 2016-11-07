package com.core.tools;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import com.core.dataobjects.FileIdentifier;
import com.core.tools.utils.ComputeMD5Utils;

/**
 * FileTreeNavigator extends the {@link SimpleFileVisitor}. This class will
 * define the added steps needed on visiting the file and pre visiting the file.
 * 
 * @author Shashank
 *
 */
public class FileTreeNavigator extends SimpleFileVisitor<Path>
{
	private List<FileIdentifier> fileIDList = new ArrayList<FileIdentifier>();

	/**
	 * @return the fileIDList
	 */
	public List<FileIdentifier> getFileIDList()
	{
		return fileIDList;
	}

	@Override
	/*
	 * This method checks if the file visited is a regular file. If yes it will
	 * compute the value of the MD5 and add that to the list fileIDList.
	 * 
	 * @see java.nio.file.SimpleFileVisitor#visitFile(java.lang.Object,
	 * java.nio.file.attribute.BasicFileAttributes)
	 */
	public FileVisitResult visitFile(Path currFile, BasicFileAttributes attrs) throws IOException
	{
		super.visitFile(currFile, attrs);
		System.out.println("Back from Super");
		// fileIDList = new ArrayList<>();
		FileIdentifier fileId = null;

		if (attrs.isRegularFile())
		{
			byte[] md5Value = ComputeMD5Utils.getMD5Value(currFile);
			fileId = new FileIdentifier(currFile.toString(), md5Value.toString());
			fileIDList.add(fileId);
		}
		return FileVisitResult.CONTINUE;
	}

	@Override
	/*
	 * This method overrides the parent method from SimpleFileVisitor. In this
	 * method, before visiting the directory, the directory will be added to the
	 * watchlist.
	 * 
	 * @see java.nio.file.SimpleFileVisitor#preVisitDirectory(java.lang.Object,
	 * java.nio.file.attribute.BasicFileAttributes)
	 */
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException
	{
		boolean registerResult = FilesSync.registerFile(dir);
		if (registerResult)
			return super.preVisitDirectory(dir, attrs);
		else
			return FileVisitResult.TERMINATE;
	}
}
