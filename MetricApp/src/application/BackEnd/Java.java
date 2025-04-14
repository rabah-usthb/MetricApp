package application.BackEnd;

import java.io.File;

import java.util.ArrayList;
import java.util.Scanner;

/*
 * add conflict to  import tree 
 * deal with * , * by looping on className And fetching existing class on each 
 * if there is match then conflict exist
 * maybe will add a function that detect a class that has no import
 * make not used import be able to get removed or commanted and refresh the treee
 */

public class Java {
	
	
	public static void FetchPkgName(ArrayList<Package>ListPackage , String ParentPkgName,ArrayList<String>PkgNameList){
		
		 for (Package pack : ListPackage) {
 	      //  System.out.println(pack.PackageName);
 	            String PkgName="";
 	            if(ParentPkgName.equals("")) {
 	             PkgName=pack.PackageName;
 	            }
 	            else {
 	            	 PkgName=ParentPkgName+pack.PackageName;	
 	            }
 	            //System.out.println(ParentPkgName);
 	            //System.out.println(PkgName);
 	            PkgNameList.add(PkgName);
 	        
 	        
 	       if(pack.SubPackges.size()!=0) {
 	    	  String UpdatedParentPkgName;
 	    	   if(ParentPkgName.equals("")) {
 	    	    UpdatedParentPkgName =pack.PackageName+".";
 	    	   }else {
 	    		  UpdatedParentPkgName =ParentPkgName+pack.PackageName+".";
 	    	   }
 	    	   System.out.println("s");
 	    	   FetchPkgName(pack.SubPackges, UpdatedParentPkgName, PkgNameList);
 	       }
 	    }
	}
	
	
	public static boolean IsPackageSrc(String SrcPath , String ImportPackageName) {
		String FullPath = SrcPath+ImportPackageName.replaceAll(".", File.separator).replace("*", "");
	    File file = new File(FullPath);
	    return file.exists();
	}
	
	public static ArrayList<String> FetchSrcPackageClasses( String SrcPath , String ImportPackageName) {
		ArrayList<String> ClassList = new ArrayList<String>();
		String FullPath = SrcPath+ImportPackageName.replaceAll(".", File.separator).replace("*", "");
	    File file = new File(FullPath);
	    File[] PackageFile = file.listFiles();
	    for(File Class : PackageFile) {
	    	ClassList.add(Class.getName());
	    }
	    return ClassList;
	}
	
	public static boolean classExists(String asterixImport, String className) {
	    System.out.println("Class Exists");
		System.out.println(asterixImport.replace("*","") + className);
        try {
        	 Class.forName(asterixImport.replace("*","") + className);
            return true; 
        } catch (ClassNotFoundException e) {
        	 return false;
            
        }
    
   
   
}
	
	//Method To Know If A Giving Path Is A Java Project
	public static int IsJavaProject(String PathProject) {
		File ProjectFile = new File(PathProject);
		if(!(ProjectFile.exists())) {
			System.out.println("Error Path Doesnt even Exist");
			return -1;
		}
		
		else {
		
		File [] ListFile =ProjectFile.listFiles();
		if(ListFile.length == 0) {
			System.out.println("Src Folder Is Empty");
        return 0;
		}
		else {
		return RecursiveDir(ListFile);	
		}
	}
	}
		//Recursive Method To Browse The Src/ Directory
	static int RecursiveDir(File[] ListFile) {
	    for (File FILE : ListFile) {
	    //    System.out.println(FILE.getName());

	        if (FILE.isDirectory()) {
	            // Recursively check the subdirectory
	            File[] SubDir = FILE.listFiles();
	            if (SubDir.length != 0) {
	                int result = RecursiveDir(SubDir); // Recursively check subdirectories
	                if (result == 1) {
	                    return 1; // .java file found in subdirectory, stop searching
	                }
	                // If no .java file found in subdirectory, continue searching other files/dirs
	            }
	        } else if (FILE.isFile() && FILE.getName().endsWith(".java")) {
	            // .java file found, stop searching
	            return 1;
	        }
	    }

	    // No .java files found in this directory or its subdirectories
	    return 2;
	}
	
	
	
	//To Know If A Folder Is A Non Empty Java Package
	static boolean IsJavaPackageNotEmpty(File FileDir) {
		File[]FileDirList = FileDir.listFiles();
		
		for(File file : FileDirList) { 
			if(file.isFile()) {
				if(file.getName().endsWith(".java")) {
					return true;
				}
			}
			
		}
		for(File file : FileDirList) {
			 if (file.isDirectory() && file.listFiles()!=null) {
				if(IsJavaPackageNotEmpty(file)) {
					return true;
				}
	
			}
		}
		return false;
	}
	
	
	//To Know If There Are No Java Package
	static boolean IsDefaultPackage(File[] SrcFiles) {
	    boolean noNamedPackages = true;
	    for (File file : SrcFiles) {
	        if (file.isDirectory() && IsJavaPackageNotEmpty(file)) {
	            noNamedPackages = false;
	            break;
	        }
	    }
	    return noNamedPackages;
	}
	
	//Fetch Java File From A Java Package 
	static void FetchJavaFile(File PackageDir,ArrayList<Package>ListPackage) {
		File[]FileList = PackageDir.listFiles();
		if(FileList.length!=0 && RecursiveDir(FileList)==1 ) {
		ArrayList<fileData> ListInfoFile=new ArrayList<>();
		ArrayList<Package> SubPackages = new ArrayList<>(); // Store sub-packages
        //loop to fetch java file of package
		for(File file : FileList) {
        	if( file.isFile() && file.getName().endsWith(".java")) {
        		ListInfoFile.add(new fileData(file.getName(),file.getPath()));
        	}
        	
        }
		
        ListPackage.add(new Package(PackageDir.getName(),ListInfoFile));
        //loop to fetch SubPackages That Arent Empty
        for(File file : FileList) { 
        	if(file.isDirectory() && file.listFiles()!=null) {
        		   FetchJavaFile(file, SubPackages); // Recursive call to fetch sub-packages
           	
        }
      }
        ListPackage.get(ListPackage.size()-1).SubPackges.addAll(SubPackages);
	}
		
 }
	//Fetch Java File In Case Of Default Package
	public static void FetchJavaFileNoPackage(File[]SrcFile,ArrayList<Package>ListPackage) {
		ArrayList<fileData>ListInfoFile=new ArrayList<fileData>();
		for(File file : SrcFile) {
			if(file.getName().endsWith(".java")) {
				ListInfoFile.add(new fileData(file.getName(),file.getPath()));
			}
		}
		ListPackage.add(new Package("Default Package",ListInfoFile));
	}
	
	
	
	public static void FetchJavaFilePkg(File[]PkgFile,ArrayList<Package>ListPackage) {
        ArrayList<fileData>DirectPackage=new ArrayList<>();
		
		for(File file : PkgFile) {
			//System.out.println(file.getName());
				if(file.isDirectory() && file.listFiles()!=null &&IsJavaPackageNotEmpty(file)) {
				 
					FetchJavaFile(file,ListPackage);
				}
				else if(file.isFile() && file.getName().endsWith(".java")) {
					DirectPackage.add(new fileData(file.getName(),file.getPath()));
				}
			}
			
		//else {
			//FetchJavaFileNoPackage(SrcFile,ListPackage);
		//}
		if(DirectPackage.size()!=0){
		ListPackage.add(new Package(PkgFile[0].getParent().substring(PkgFile[0].getParent().lastIndexOf("\\")+1),DirectPackage));
		}
	}
	
	//Fetch Java File From Src Folder
	public static void FetchSrcJavaFile(File[]SrcFile,ArrayList<Package>ListPackage) {

		ArrayList<fileData>DefaultPackage=new ArrayList<>();
		
		for(File file : SrcFile) {
			System.out.println("FILE "+file.getName());
				if(file.isDirectory() && file.listFiles()!=null &&IsJavaPackageNotEmpty(file)) {
				    System.out.println(file.getName());
					FetchJavaFile(file,ListPackage);
				}
				else if(file.isFile() && file.getName().endsWith(".java")) {
					DefaultPackage.add(new fileData(file.getName(),file.getPath()));
				}
			}
			
		//else {
			//FetchJavaFileNoPackage(SrcFile,ListPackage);
		//}
		if(DefaultPackage.size()!=0){
		ListPackage.add(new Package("Default Package",DefaultPackage));
		}
	}
	   
	

	
	
	
		
	
  	


}