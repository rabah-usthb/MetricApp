package application.BackEnd;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;

public class LoadClass {
	
   static boolean LoadClass(String className,String lib) throws IOException, InterruptedException {
	//   System.out.println("ClassName : "+className+" lib "+lib);
	   String projectPath = System.getProperty("user.dir");
       String projectBin = projectPath + "\\bin"; // Adjust if your class files are elsewhere
       String classPath = projectBin + ";" + lib; 
        
       ProcessBuilder builder = new ProcessBuilder(
           "java",
           "-cp", classPath,
           "a.Project1Analyzer",
           className
       );
       
       Process process = builder.start();
       BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
       String line;
           
       StringBuilder resultBuilder = new StringBuilder();
       while ((line = reader.readLine()) != null) {
           resultBuilder.append(line).append("\n"); // Preserve new lines
       }
       String result = resultBuilder.toString().trim();

       // Wait for process to finish
       int exitCode = process.waitFor();
    //   System.out.println("Exit Code: " + exitCode);
       
       
       if(result.compareTo(className + " is available.") == 0 ) {
    	 //  System.out.println("Good Result "+result);
    	  return true; 
       }
       else {
    	//   System.out.println("Bad Result "+result);
	      return false;
       }
	   
   }
	
    
   static Class<?> Loading(String filename,String path) {
		String fileName;
    	path = path.replace("\\src\\", "\\bin\\");
    	fileName = path.substring(path.indexOf("\\bin\\")+5).replace(".java", "").replace("\\", ".");
    	path = path.substring(0,path.indexOf("\\bin\\")+5);
        String lastPart = fileName.substring(fileName.lastIndexOf(".")+1);
    	if(!lastPart.equals(filename)) {
               fileName = fileName.replace(lastPart, filename);
        }
		try (URLClassLoader	classLoader = new URLClassLoader(new URL[]{new File(path).toURI().toURL()})){
			return classLoader.loadClass(fileName);
			
		} catch (ClassNotFoundException|IOException e) {
			e.printStackTrace();
		  return null;
		}
   
 }
   
   
   static Class<?> Loading(String filename,String path,String innerClass) {
		String fileName;
    	path = path.replace("\\src\\", "\\bin\\");
    	fileName = path.substring(path.indexOf("\\bin\\")+5).replace(".java", "").replace("\\", ".");
    	String lastPart = fileName.substring(fileName.lastIndexOf(".")+1);
    	if(!lastPart.equals(filename)) {
               fileName = fileName.replace(lastPart, filename);
        }
    	fileName = fileName +innerClass;
    	path = path.substring(0,path.indexOf("\\bin\\")+5);
        
		try (URLClassLoader	classLoader = new URLClassLoader(new URL[]{new File(path).toURI().toURL()})){
			return classLoader.loadClass(fileName);
			
		} catch (ClassNotFoundException|IOException e) {
			e.printStackTrace();
		  return null;
		}
   
 }
}
