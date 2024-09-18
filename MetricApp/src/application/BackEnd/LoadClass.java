package application.BackEnd;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

public class LoadClass {
   static Class<?> Loading(String path) {
		String fileName;
    	path = path.replace("\\src\\", "\\bin\\");
    	fileName = path.substring(path.indexOf("\\bin\\")+5).replace(".java", "").replace("\\", ".");
    	path = path.substring(0,path.indexOf("\\bin\\")+5);
        
		try (URLClassLoader	classLoader = new URLClassLoader(new URL[]{new File(path).toURI().toURL()})){
			return classLoader.loadClass(fileName);
			
		} catch (ClassNotFoundException|IOException e) {
			e.printStackTrace();
		  return null;
		}
   
 }
   
   
   static Class<?> Loading(String path,String innerClass) {
		String fileName;
    	path = path.replace("\\src\\", "\\bin\\");
    	fileName = path.substring(path.indexOf("\\bin\\")+5).replace(".java", "").replace("\\", ".");
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
