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
 	        System.out.println(pack.PackageName);
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
		
	public static String ConcatSrc(String path) {
		if(!(path.endsWith("src") || path.endsWith("src"+File.separator))) {
	
          	if(path.endsWith(File.separator) &&!path.contains("src")) {
          		path+="src";
          	}
          	
          	else if(!path.endsWith(File.separator) &&!path.contains("src")) {
          		path+=File.separator+"src";
          	}
          	else if (path.contains("src")) {
          		path = path.substring(0,path.lastIndexOf("src")+3);
          		System.out.println(path);
          }
		}
		
		return path;
	}
	
	//Method To Know If A Giving Path Is A Java Project
	public static int IsJavaProject(String PathProject) {
		if(!(new File (PathProject).exists())) {
			System.out.println("Error Path Doesnt even Exist");
			return -1;
		}
		PathProject=ConcatSrc(PathProject);
		System.out.println(PathProject);
		File SrcFile = new File(PathProject);
		if(!SrcFile.exists()) {
			System.out.println("Src Folder Doesn't Exist");
		return -2;
		}
		else {
		File [] ListFile = SrcFile.listFiles();
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
			for(File FILE : ListFile) {
				if(FILE.isDirectory()) {
			    File[] SubDir = FILE.listFiles();
			    if(SubDir.length!=0) {
			    return RecursiveDir(SubDir);
			    }
				}
				else if(FILE.isFile()){
					if(FILE.getName().endsWith(".java")) {
						return 1;
					}
				}
			}
			
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
				return IsJavaPackageNotEmpty(file);
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
		if(FileList.length!=0) {
		ArrayList<String> ListInfoFile=new ArrayList<String>();
		ArrayList<Package> SubPackages = new ArrayList<>(); // Store sub-packages
        //loop to fetch java file of package
		for(File file : FileList) {
        	if( file.isFile() && file.getName().endsWith(".java")) {
        		ListInfoFile.add(file.getName());
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
		ArrayList<String>ListInfoFile=new ArrayList<String>();
		for(File file : SrcFile) {
			if(file.getName().endsWith(".java")) {
				ListInfoFile.add(file.getName());
			}
		}
		ListPackage.add(new Package("Default Package",ListInfoFile));
	}
	
	
	
	public static void FetchJavaFilePkg(File[]PkgFile,ArrayList<Package>ListPackage) {
        ArrayList<String>DirectPackage=new ArrayList<>();
		
		for(File file : PkgFile) {
			//System.out.println(file.getName());
				if(file.isDirectory() && file.listFiles()!=null &&IsJavaPackageNotEmpty(file)) {
					//System.out.println(file.getName());
					FetchJavaFile(file,ListPackage);
				}
				else if(file.isFile() && file.getName().endsWith(".java")) {
					DirectPackage.add(file.getName());
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

		ArrayList<String>DefaultPackage=new ArrayList<>();
		
		for(File file : SrcFile) {
			//System.out.println(file.getName());
				if(file.isDirectory() && file.listFiles()!=null &&IsJavaPackageNotEmpty(file)) {
					//System.out.println(file.getName());
					FetchJavaFile(file,ListPackage);
				}
				else if(file.isFile() && file.getName().endsWith(".java")) {
					DefaultPackage.add(file.getName());
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