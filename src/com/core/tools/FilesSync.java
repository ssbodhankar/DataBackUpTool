package com.core.tools;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.HashMap;
import java.util.Scanner;

import com.core.tools.utils.FileNavigatorService;

/**
 * @author Shashank
 *
 */
public class FilesSync
{
	private static WatchService watcher;
	private static HashMap<WatchKey, Path> keysMap;

	/**
	 * This starts the program execution.
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		Scanner inScanner = new Scanner(System.in);
		System.out.println("Enter Root Directory");
		String rootDir = inScanner.next();
		FilesSync.initializeFiles(rootDir);
		System.out.println("Initialization is done");
	}

	/**
	 * This method will be used to invoke the 1st initialize the database and
	 * register for the directory to watch for changes.
	 * 
	 * @param rootDir
	 */
	public static void initializeFiles(String rootDir)
	{
		try
		{
			watcher = FileSystems.getDefault().newWatchService();
			keysMap = new HashMap<>();
			FileNavigatorService.initilize(rootDir);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method is used to monitor the changes made to the directory. This
	 * will also monitor the sub-folders as well. When a new sub-folder is
	 * created, this method will invoke the register method and register the
	 * sub-folder. Any file that gets created will be added to the database as
	 * well.
	 * 
	 * @param directoryName
	 */
	public static void watchForChanges(String directoryName)
	{
		boolean keepContinue = true;
		// infinite loop to continue monitoring changes.
		while (keepContinue)
		{
			// key for the event that is triggered.
			WatchKey eventKey;
			try
			{
				eventKey = watcher.take();
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			FileNavigatorService.registerDir(directoryName);
		}
		System.err.println("Watching for changes stopped");
	}

	/**
	 * 
	 * @param dir
	 * @return
	 */
	public static boolean registerFile(Path dir)
	{
		boolean isRegisterOK = false;
		try
		{
			WatchKey currKey = dir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
			keysMap.put(currKey, dir);
			isRegisterOK = true;
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isRegisterOK;
	}
}
