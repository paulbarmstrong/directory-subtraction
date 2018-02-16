import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


/**
Author: Paul Armstrong
Original Date: March 2017
Description:
	This script will scan the contents of a first directory to produce a
	black-list. That black-list will be used to remove corresponding
	files from a second directory. When this happens, the script is
	"subtracting" the first directory from the second directory.
	
	The reason why I made this script as a way to undo a manual
	installation of files at a later date. This script allows me to
	"subtract" an installation quickly, no matter how many files or
	sub-directories the installation includes.


**/

public class DirectorySubtraction
{
	public static void main(String[] args)
	{
		// Define unknown files empty ahead of time to establish scope and allow a .exists() check
		File referenceFolder =	new File("");
		File targetFolder =		new File("");
		
		// Prompt for the folder containing the blacklisted file system
		JOptionPane.showMessageDialog(null,"Select the folder containing files to be blacklisted:");
		
		// Use a file chooser to get folder location input
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
			referenceFolder = fc.getSelectedFile();
		
		
		// Prompt for the folder to remove blacklisted files from
		JOptionPane.showMessageDialog(null,"Select the folder to remove blacklisted files from:");
		
		// Use a file chooser to get folder location input
		fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
			targetFolder = fc.getSelectedFile();
		
		if (referenceFolder.exists() && targetFolder.exists() && !(referenceFolder.equals(targetFolder)))
		{
			// Do a post order traversal of the file system (so folders are deleted last)
			ArrayList<File> referenceFiles = new ArrayList<File>();
			recursiveFileTraversal(referenceFiles, referenceFolder);
			
			// If the corresponding file in the targetFolder exists, mark for deletion
			ArrayList<File> deleteList = new ArrayList<File>();
			String referencePath = fixFileString(referenceFolder.getAbsolutePath());
			String targetPath = fixFileString(targetFolder.getAbsolutePath());
			for (File file : referenceFiles)
			{
				File coFile = new File(fixFileString(file.getAbsolutePath())
												.replaceAll(referencePath, targetPath));
				if (coFile.exists())
				{
					deleteList.add(coFile);
				}
			}
			
			// Construct a warning based on the contents of deleteList
			String delFileWarning = 
					"Type 'yes' to delete the following "+deleteList.size()+" files:\n";
			int i=0;
			while (i<deleteList.size() && i<10)
			{
				delFileWarning += deleteList.get(i).toString()+"\n";
				i++;
			}
			if (i == 10)
			{
				delFileWarning += "and "+(deleteList.size()-i)+" more.";
			} else if (i == 0) {
				delFileWarning += "None";
			}
			String input = JOptionPane.showInputDialog(delFileWarning+"\n");
			
			// If yes, start deleting files
			if (input.equals("yes"))
			{
				for (File file : deleteList)
				{
					// File class shouldn't delete a folder that isn't empty, but just in case
					if (!file.isDirectory() || !(file.listFiles().length > 0))
					{
						file.delete();
					}
				}
				JOptionPane.showMessageDialog(null,"Files have been removed.");
			} else {
				JOptionPane.showMessageDialog(null,"Deletion Canceled");
			}
			
		} else {
			JOptionPane.showMessageDialog(null, "Invalid Folders. Operation Aborted.");
		}
	}
	
	private static void recursiveFileTraversal(ArrayList<File> referenceFiles, File folder)
	{
		// Recursively traverse and record all files within referenceFolder
		File[] listOfFiles = folder.listFiles();
		for (File childFile : listOfFiles)
		{
			if (childFile.isDirectory())
			{
				recursiveFileTraversal(referenceFiles,childFile);
			}
			referenceFiles.add(childFile);
		}
	}
	
	public static String fixFileString(String str)
	{
		// To make java regex happy
		return str.replace("\\", "/");
	}
}