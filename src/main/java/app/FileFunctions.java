package app;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.FileUtils;


public class FileFunctions {

	private Boolean success;

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public static void main(String[] args) {
		
		FileFunctions dirList = new FileFunctions();
		
		ArrayList<String> dirlisting = dirList.listDIR("/ProgFiles//PMC-Sierra//PMCInfocenter//eclipse//plugins//");
		
		for(int i = 0;i < dirlisting.size(); i++){	
			System.out.println(dirlisting.get(i).toString());
		}
	}
	
	public String makeDir() {
		
		GenerateUUID dirName = new GenerateUUID();    
		String directory = "xml/" + dirName.generateID();    
	        	
		new File(directory).deleteOnExit();  
		new File(directory).mkdirs();
		new File(directory).setExecutable(true);
		new File(directory).setReadable(true);
		new File(directory).setWritable(true);		
		
		return directory;
	}
	
	public Boolean makeDir(String dirname) {
		new File(dirname).deleteOnExit();  
		new File(dirname).mkdirs();
		new File(dirname).setExecutable(true);		
		success = true;
		return success;
	}
	
	public void copyFile(File location, File destination){
		try {
			Files.copy(location.toPath(), destination.toPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Couldn't copy over file from source " + location.toPath() + " to destination " + destination.toPath());
		}
	}
	
	public boolean isCompletelyWritten(File file) {
	    RandomAccessFile stream = null;
	    try {
	    	System.out.println("Trying to read: " + file.toString());
	        stream = new RandomAccessFile(file, "d");
	        return true;
	    } catch (Exception e) {
	    	System.out.println("Skipping file " + file.getName() + " for this iteration it's not completely written");
	    } finally {
	        if (stream != null) {
	            try {
	                stream.close();
	            } catch (IOException e) {
	                System.out.println("Exception during closing file " + file.getName()); 
	            }
	        }
	    }
	    return false;
	}
	
	public void copyFileUsingFileChannels(File source, File dest) throws IOException {	
	    FileChannel inputChannel = null;	
	    FileChannel outputChannel = null;	
	    try {	
	        inputChannel = new FileInputStream(source).getChannel();	
	        outputChannel = new FileOutputStream(dest).getChannel();	
	        outputChannel.transferFrom(inputChannel, 0, inputChannel.size());	
	    } finally {	
	        inputChannel.close();	
	        outputChannel.close();	
	    }
	
	}


	public void deleteDIR(String path) {

		File dir = new File(path);

		FileUtils.deleteQuietly(dir);

	}
	
	public void deleteDIR(File path) {

		FileUtils.deleteQuietly(path);

	}
	
	public void executeCMD(String command) {

		ProcessBuilder builder = new ProcessBuilder(command);
		
		Process process = null;
		try {
			process = builder.start();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		

	}
	
	
	public ArrayList<String> listDIR(String dir){		
		File f = new File(dir);
		String[] list = f.list();	
		ArrayList<String> parsedlist = new ArrayList<String>();
		
		for(int x = 0; x < list.length;x++){
			if(list[x].length() < 5 ||
			   !list[x].substring(list[x].length() - 4, list[x].length()).equals(".jar") && 
			   !list[x].substring(list[x].length() - 4, list[x].length()).equals(".zip") && 
			   !list[x].substring(0, 4).equals("com.") && 
			   !list[x].substring(0, 4).equals("org.") && 			   
			   !list[x].equals("advanced")){
				parsedlist.add(list[x]);
			}				
		}			
		Collections.sort(parsedlist);		
		return parsedlist;			
	}
	
	public void extractFolder(String zipFile,String extractFolder) 
	{
	    try
	    {
	        int BUFFER = 2048;
	        File file = new File(zipFile);

	        ZipFile zip = new ZipFile(file);
	        String newPath = extractFolder;

	        new File(newPath).mkdir();
	        Enumeration zipFileEntries = zip.entries();

	        // Process each entry
	        while (zipFileEntries.hasMoreElements())
	        {
	            // grab a zip file entry
	            ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();
	            String currentEntry = entry.getName();

	            File destFile = new File(newPath, currentEntry);
	            //destFile = new File(newPath, destFile.getName());
	            File destinationParent = destFile.getParentFile();

	            // create the parent directory structure if needed
	            destinationParent.mkdirs();

	            if (!entry.isDirectory())
	            {
	                BufferedInputStream is = new BufferedInputStream(zip.getInputStream(entry));
	                int currentByte;
	                // establish buffer for writing file
	                byte data[] = new byte[BUFFER];

	                // write the current file to disk
	                FileOutputStream fos = new FileOutputStream(destFile);
	                BufferedOutputStream dest = new BufferedOutputStream(fos,
	                BUFFER);

	                // read and write until last byte is encountered
	                while ((currentByte = is.read(data, 0, BUFFER)) != -1) {
	                    dest.write(data, 0, currentByte);
	                }
	                dest.flush();
	                dest.close();
	                is.close();
	            }
	        }	               
	    }
	    catch (Exception e) 
	    {
	        System.err.println("ERROR: "+e.getMessage());
	    }

	}
	
}
