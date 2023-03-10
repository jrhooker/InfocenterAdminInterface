package app;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileSearch {

	private String fileNameToSearch;
	  private List<String> result = new ArrayList<String>();
		
	  public String getFileNameToSearch() {
		return fileNameToSearch;
	  }

	  public void setFileNameToSearch(String fileNameToSearch) {
		this.fileNameToSearch = fileNameToSearch;
	  }

	  public List<String> getResult() {
		return result;
	  }

	  public static void main(String[] args) {

		FileSearch fileSearch = new FileSearch();
	  
	        //try different directory and filename :)
		fileSearch.searchDirectory(new File("/Temp"), "plugin.xml");

		int count = fileSearch.getResult().size();
		if(count ==0){
		    System.out.println("\nNo result found!");
		}else{
		    System.out.println("\nFound " + count + " result!\n");
		    for (String matched : fileSearch.getResult()){
			System.out.println("Found : " + matched);
			int lastslash = matched.lastIndexOf("\\");
			System.out.println("Directory: " + matched.substring(0, lastslash));
			
		    }
		}
	  }
	  
	  public String returnDirectoryPath(String directory, String character){
		
		  int lastslash = directory.lastIndexOf(character);
		  
		  directory = directory.substring(0, lastslash);
		  
		  return directory;
	  }
	  
	  public void searchDirectory(File directory, String fileNameToSearch) {

		setFileNameToSearch(fileNameToSearch);

		if (directory.isDirectory()) {
		    search(directory);
		} else {
		    System.out.println(directory.getAbsoluteFile() + " is not a directory!");
		}

	  }

	  private void search(File file) {

		if (file.isDirectory()) {
		  System.out.println("Searching directory ... " + file.getAbsoluteFile());
			
	            //do you have permission to read this directory?	
		    if (file.canRead()) {
			for (File temp : file.listFiles()) {
			    if (temp.isDirectory()) {
				search(temp);
			    } else {
				if (getFileNameToSearch().equals(temp.getName().toLowerCase())) {			
				    result.add(temp.getAbsoluteFile().toString());
			    }

			}
		    }

		 } else {
			System.out.println(file.getAbsoluteFile() + "Permission Denied");
		 }
	      }

	  }

	}
