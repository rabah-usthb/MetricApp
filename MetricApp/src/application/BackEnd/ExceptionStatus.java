package application.BackEnd;

import java.io.BufferedReader;



import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.jar.JarInputStream;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;

import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;

import application.FrontEnd.MetricController;

import java.io.BufferedReader;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.jar.JarInputStream;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;




public class ExceptionStatus {
 public String ExceptionName;
 public int CheckedStatus;
 public int DefaultStatus;
 ExceptionStatus(String ExceptionName,int CheckedStatus,int DefaultStatus){
	 this.ExceptionName = ExceptionName;
	 this.CheckedStatus = CheckedStatus;
	 this.DefaultStatus = DefaultStatus;
	
 }
 
 static int getTotalNumberDefaultException(ArrayList<ExceptionStatus> List) {
	 int nb = 0;
	 for(ExceptionStatus Exc : List) {
		 if(Exc.DefaultStatus == 0) {
			 ++nb;
		 }
	 }
	 return nb;
 }
 
 static int getTotalNumberNotDefaultException(ArrayList<ExceptionStatus> List) {
	 int nb = 0;
	 for(ExceptionStatus Exc : List) {
		 if(Exc.DefaultStatus == 1) {
			 ++nb;
		 }
	 }
	 return nb;
 }
 
 static int getTotalNumberRunTimeException(ArrayList<ExceptionStatus> List) {
	 int nb = 0;
	 for(ExceptionStatus Exc : List) {
		 if(Exc.CheckedStatus == 1) {
			 ++nb;
		 }
	 }
	 return nb;
 }
 
 static int getTotalNumberCompileTimeException(ArrayList<ExceptionStatus> List) {
	 int nb = 0;
	 for(ExceptionStatus Exc : List) {
		 if(Exc.CheckedStatus == 0) {
			 ++nb;
		 }
	 }
	 return nb;
 }
 
 static void IsThrowable(Set<String> List,String line){
	 
	 if(RegularExpression.IsThrow(line)) {
		 
		 List.addAll(RegularExpression.ThrowException(line));
	 }
	 else if(RegularExpression.IsMethod(line)) {
		
		 List.addAll(RegularExpression.FetchMethodThrowable(line));
	 }
	 else if(line.matches("("+RegularExpression.ThrowsPattern+")"+"("+ RegularExpression.CurlyBraces+")")){
		 List.addAll(RegularExpression.FetchMethodThrowable(line));
	 }
	 else if(RegularExpression.IsCatch(line)) {
		
		 List.addAll(RegularExpression.CatchException(line));
	 }
	 
 }
 
 
 
 
 
 
 
 static String IsSrcFile(String ExceptionName,File[] Srcfile) {
	 // File file = new File(ProjectPath);
	 //FilePath = FilePath.substring(0,FilePath.indexOf("/src")+4);
	 
	 for(File Files : Srcfile) {
		 if( Files.isFile() && Files.getName().endsWith(".java")&& Files.getName().replace(".java", "").equals(ExceptionName)) {
		     
			 return Files.getAbsolutePath();
		 }
		 else if(Files.isDirectory() && Files.listFiles()!=null) {
			 String output = IsSrcFile(ExceptionName,Files.listFiles());
			 if(output != null) {
				 return output;
			 }
			 
		 }
	 }
	 return null;
 }
 
 static boolean IsClassException(String PathSrcFile) throws IOException, ClassNotFoundException {
	 String PathBinFile = PathSrcFile.replace(".java", ".class");
	 if(PathSrcFile.contains("/src/main/java/") || PathSrcFile.contains("/src/java/")) {
		 PathSrcFile = PathSrcFile.replaceAll("/src/main/java/", "/target/classes/");
	 }
	 else if(PathSrcFile.contains("/src/test/")) {
		 PathSrcFile = PathSrcFile.replaceAll("/src/test/", "/target/test-classes/");
	 }
	 PathBinFile = PathSrcFile;
     // Create a custom class loader
     URLClassLoader classLoader;
	try {
		classLoader = new URLClassLoader(new URL[]{new File(PathBinFile.substring(0,PathBinFile.indexOf("/target/")+8)).toURI().toURL()});
	     Class<?> loadedClass = classLoader.loadClass(PathBinFile);

	     // Now you have the loaded class and can work with it
	     //System.out.println("Loaded class: " + loadedClass.getName());
	     Class<?> superClass = loadedClass;
	     // You can also get the superclass
	     while (superClass != null) {
             
                	  if(superClass==Exception.class) {
                		  return true;
                	  }
                	  else if(superClass==Error.class) {
                		  return false;
                	  }
                	  superClass = superClass.getSuperclass();
                      
             // System.out.println("Superclass of " + loadedClass.getName() + ": " + superClass.getName());
         }
	     
	     // Close the class loader when done
	     classLoader.close();
	} catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

     

	 return false;
 }
 
 
 
 static String ExceptionImport(String ExceptionName,File file) {
	
	 for(ImportStatus Import : ImportStatus.ImportFetch(file)) {
		if(Import.ImportName.contains("*")) {
			//System.out.println(Import.ImportName);
                  if( Java.classExists(Import.ImportName, ExceptionName)) {
                	  return Import.ImportName;
                  }
		}else {if(Import.ImportName.replace(" ", "").endsWith(ExceptionName)) {
			 System.out.println("Condition Satisfied");
			 return Import.ImportName;
		 }
		}
	 }
	 return null;
 }
 
 static int UpdateCheckedStatus(String ExceptionName, String ExceptionPath,int FlagDefault) throws IOException {
	   
	 try {
		 if(ExceptionName.equals("Exception")) {
			 return 0;
		 }
		 else if (ExceptionName.equals("RunTimeException")) {
			 return 1;
		 }
		 
	        if (FlagDefault == 0) { // Load using system class loader for JRE classes
	            try {
	            	
	             Class<?> loadedClass = Class.forName(ExceptionPath);
	             //System.out.println(loadedClass.getName());
	                
	                    // Check the superclass of the loaded class
	                    Class<?> superClass = loadedClass;
	                    while (superClass != null) {
	                    	
	                       
	                       // System.out.println(superClass.getName());
	                       
	                            if (superClass == RuntimeException.class) {
	                                return 1;
	                            } else if (superClass == Error.class) {
	                                return -1;
	                            }
	                            else if(superClass == Exception.class) {
	                            	return 0;
	                            }
	                            superClass = superClass.getSuperclass();
	                        
	                    }
	                
	            } catch (ClassNotFoundException e) {
	                // Class not found by the system class loader
	            }
	        } else {
	        	String idk= "\\target\\classes\\";
	       	 if(ExceptionPath.contains("\\src\\main\\java\\")) {
	       		ExceptionPath = ExceptionPath.replace("\\src\\main\\java\\", "\\target\\classes\\");
	       	 } else if (ExceptionPath.contains("\\src\\java\\")) {
	       		ExceptionPath = ExceptionPath.replace("\\src\\java\\", "\\target\\classes\\");
	       	 }
	       	 else if(ExceptionPath.contains("\\src\\test\\")) {
	       		ExceptionPath = ExceptionPath.replace("\\src\\test\\", "\\target\\test-classes\\");
	       		idk = "\\target\\test-classes\\";
	       	 }
	       	String PathBinFile = ExceptionPath.replace(".java", ".class");
	       	 System.out.println("BIN "+PathBinFile.substring(0,PathBinFile.indexOf(idk)+idk.length()));
	            // Create a custom class loader
	            URLClassLoader classLoader;
	            String longClassName =PathBinFile.substring(PathBinFile.indexOf(idk)+idk.length()).replace("\\", ".").replace(".class", ""); 
	       	    System.out.println("LONG "+longClassName);
	            
	       		classLoader = new URLClassLoader(new URL[]{new File(PathBinFile.substring(0,PathBinFile.indexOf(idk)+idk.length())).toURI().toURL()});
	       	     Class<?> loadedClass = classLoader.loadClass(longClassName);

	            
	           // System.out.println(ExceptionPath);
	          //  System.out.println(loadedClass.getName());
	            classLoader.close();

	       
	                // Check the superclass of the loaded class
	                Class<?> superClass = loadedClass;
	                while (superClass != null) {
	                    
	                    //System.out.println(superClass.getName());
	                        if (superClass == RuntimeException.class) {
	                            return 1; // UnChecked exception
	                        } else if (superClass == Error.class) {
	                            return -1; // Error
	                        }
	                        else if(superClass == Exception.class) {
	                        	return 0;
	                        }
	                        superClass = superClass.getSuperclass();
	                    
	                }
		            
		        }
		    } catch (ClassNotFoundException | MalformedURLException e) {
		        // Handle exceptions
		        e.printStackTrace();
		    }

		    return -2; // Default
		}
 
 public static ArrayList<ExceptionStatus> SetFlagException(Set<String> ThrowableList,File FileSrc, File file) {
	ArrayList<ExceptionStatus> ExceptionList = new ArrayList<>();
	int flagDefault = 0;
	int flagChecked = 0;
	for(String ThrowAble : ThrowableList) {
		//System.out.println(ThrowAble); 
		String ThrowAblePath = IsSrcFile(ThrowAble, FileSrc.listFiles());
		System.out.println("EXP : "+ThrowAble + " ISSRC "+ThrowAblePath);
	 if(ThrowAblePath!=null) {
		 flagDefault= 1;
		 
	 }
	 else {
		 ThrowAblePath = ExceptionImport(ThrowAble, file);
		 if(ThrowAblePath!=null) {
		 if(ThrowAblePath.contains("*")) {
		ThrowAblePath =	 ThrowAblePath.replace("*", ThrowAble);
		 }
		 }
		 else {
		 
			 ThrowAblePath = "java.lang."+ThrowAble;
		 }
		 //System.out.println(ThrowAblePath);
	 flagDefault = 0;
	 }
	 
     try {
		flagChecked = UpdateCheckedStatus(ThrowAble, ThrowAblePath,flagDefault);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
     if(flagChecked!=-1 && flagChecked!=-2) {
    	 ExceptionList.add(new ExceptionStatus(ThrowAble, flagChecked,flagDefault));
     }
	}
	return ExceptionList;
 }
 
 
  public static ArrayList<ExceptionStatus> FetchThrowable(File file) throws ClassNotFoundException, IOException{
	  Set<String> ThrowableList = new LinkedHashSet<>();

		String formattedCode = "";
	  	 Integer[] index = new Integer[1];
	  	 index[0] = 0;
	  	  String Code;
		try {
			Code = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
			 formattedCode = new Formatter().formatSource(Code);
		} catch (IOException | FormatterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String [] Line = formattedCode.split("\n");
          // Format the code
       
			for(int i = 0 ; i<Line.length;i++) {
				
	            	Line[i] = Line[i].trim();
	            	Line[i] = Qoute.RemoveQoute(Line[i]);
	            	System.out.println("Line "+Line[i]);
	            	System.out.println("list" +ThrowableList);
	            	ArrayList<String> ListCode=new ArrayList<>();
	            	if(!Line[i].isBlank() && !Line[i].isEmpty() && !Comment.IsCommentOnlyCompleted(Line[i]) && !RegularExpression.IsPackage(Line[i]) && !RegularExpression.IsImport(Line[i])) {
	            		//System.out.println(Line);
	            		if(Comment.ContainsComment(Line[i])) {
	    	            	//System.out.println(line);
	    	            		Line[i] = Comment.RemoveComment(Line[i]);
	    	            	}
	            		else {
	            			if(Comment.FinishedComment(Line[i])) {
		            			if(!Comment.OpeningMultiCommentOnly(Line[i])) {
		            				ListCode.add(Comment.CodeOpeningComment(Line[i]));
		            			}
		            			if(!Comment.ClosingMultiCommentOnly(Line[i])) {
		            				ListCode.add(Comment.CodeClosingComment(Line[i]));
		            			}
		            		}
	            			else if (Comment.NotFinishedComment(Line[i])) {
	            				String temp = Comment.JumpComment(Line[i],ListCode,Line,index);
	                     		 i = index[0];
	                     		 Line[i] = temp;
		            		}

	            			if(!ListCode.isEmpty()) {
	            				for(String code : ListCode) {
	            					IsThrowable(ThrowableList,code);
	            				}
	            			}
	            		}
	            		
	            		Line[i] = Line[i].trim();
	              		Line[i] = Qoute.RemoveQoute(Line[i]);
	              	//	for(int j = i; j<line.length;j++) {
	              		if(i!=Line.length-1 &&!Line[i+1].trim().startsWith("*") && !Line[i+1].trim().startsWith("/*") ){
	              			System.out.println("Current "+Line[i]+" NEXT "+Line[i+1] +" ISTHROWS "+Line[i+1].trim().startsWith("throws "));
	              		if((Line[i+1].trim().startsWith("throws ") ||Line[i+1].trim().startsWith("implements ") ) ) {
	      					Line[i] = Line[i] +" "+ Line[i+1];
	      					 
	      					++index[0];
	      				}
	              		
	              		System.out.println("TRAITED "+Line[i]);
	            			IsThrowable(ThrowableList,Line[i]);
      	            	
	            			
	            		}
	            	 i = index[0];
	                 ++index[0];
	            	}
	         
			}        
	System.out.println("When Finished "+ThrowableList);
	
	ArrayList<ExceptionStatus> ListException = new ArrayList<>();
	File FileSrc = new File(MetricController.PathProject);
	ListException  = SetFlagException(ThrowableList, FileSrc, file);
	
     
	return ListException;
	  
  
}
}