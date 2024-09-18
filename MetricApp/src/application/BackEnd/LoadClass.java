package application.BackEnd;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

public class LoadClass {
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
