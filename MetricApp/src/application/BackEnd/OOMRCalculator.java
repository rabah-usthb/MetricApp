package application.BackEnd;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Scanner;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.DecimalFormat;





public class OOMRCalculator {
	public int totalMethods = 0;
	public int overloadedMethods = 0;
	public int overrideMethods = 0;
    
	public double RatioMethodsSur = 0;
	public  double RatioMethodsRedef = 0;
	public double oomr = 0;
    public static OOMRCalculator fetchOOMR(String path) throws FileNotFoundException, MalformedURLException, ClassNotFoundException  {
    	OOMRCalculator t = new OOMRCalculator();
    	
    	
      	String idk= "\\target\\classes\\";
      	 if(path.contains("\\src\\main\\java\\")) {
      		path = path.replace("\\src\\main\\java\\", "\\target\\classes\\");
      	 } else if (path.contains("\\src\\java\\")) {
      		path = path.replace("\\src\\java\\", "\\target\\classes\\");
      	 }
      	 else if(path.contains("\\src\\test\\")) {
      		path = path.replace("\\src\\test\\", "\\target\\test-classes\\");
      		idk = "\\target\\test-classes\\";
      	 }
      	String PathBinFile = path.replace(".java", ".class");
      	 System.out.println("BIN "+PathBinFile.substring(0,PathBinFile.indexOf(idk)+idk.length()));
           // Create a custom class loader
           URLClassLoader classLoader;
           String longClassName =PathBinFile.substring(PathBinFile.indexOf(idk)+idk.length()).replace("\\", ".").replace(".class", ""); 
      	    System.out.println("LONG "+longClassName);
           
      		classLoader = new URLClassLoader(new URL[]{new File(PathBinFile.substring(0,PathBinFile.indexOf(idk)+idk.length())).toURI().toURL()});
    
      		
      		Class<?> loadedClass = classLoader.loadClass(longClassName);
      	    
      		try {
    			classLoader.close();
    		} catch (IOException e) {
    			//TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	
        //t.totalMethods = countTotalMethods(loadedClass);
        // t.overloadedMethods = countOverloadedMethods(loadedClass);
        // t.overrideMethods = countOverrideMethods(loadedClass);
        // DecimalFormat df = new DecimalFormat("#.##");
        // t.RatioMethodsSur = (double) (t.overloadedMethods) / t.totalMethods;
       //t.RatioMethodsSur= Double.parseDouble(df.format(t.RatioMethodsSur))*100;
        // t.RatioMethodsRedef = (double) (t.overrideMethods) / t.totalMethods;
         //t.RatioMethodsRedef= Double.parseDouble(df.format(t.RatioMethodsRedef))*100;
         //t.oomr = t.RatioMethodsRedef+t.RatioMethodsSur;
    
		return t;
      }

    public static int countTotalMethods(Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        return methods.length;
    }

    public static int countOverloadedMethods(Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        int overloadedCount = 0;
        int i=0;
        for (Method method : methods) {

            if (isOverloaded(method, clazz,i)) {
                overloadedCount++;
                //System.out.println(method.getName()+"\n");
            }
            i++;
        }
        return overloadedCount;
    }

    public static boolean isOverloaded(Method method, Class<?> clazz, int i) {
        // int totalMethods = countTotalMethods(clazz);
        Method[] methods = clazz.getDeclaredMethods();
        Boolean overload=false;
        int j=0;
        for (Method method2 : methods){
            if(method2.getName()==method.getName()&& (j!=i) && compareMethodParameters(method, method2)==false && compareMethodType(method, method2)==true)//parametres differents
                overload=true;
            j++;
        }
        return overload;
    }

    public static int countOverrideMethods (Class<?> clazz) throws FileNotFoundException{
        Method[] methodsClass = clazz.getDeclaredMethods();
        int overridedCount=0;

        for (Method method : methodsClass) {
            if (isOverride(method, clazz)) {
                overridedCount++;
                //System.out.println(method.getName()+"\n");
            }
        }
        return overridedCount;
        
    }
    
    public static Boolean isOverride(Method method,Class<?> clazz) {
    // Obtenez la classe de la classe dérivée (la classe actuelle)
    //TRAITEMENT DE L'OVERRIDE DANS L'HERITAGE
        Class<?> classesSup=clazz;
        classesSup= classesSup.getSuperclass();
        while (classesSup != null) {
            Method[] methodsSuperClass = classesSup.getDeclaredMethods();
            for (Method method2 : methodsSuperClass) {
                if(method.getName()==method2.getName() && compareMethodParameters(method, method2)==true && compareMethodType(method, method2)==true)
                   return true;
                
            }
            classesSup= classesSup.getSuperclass();
        }


    //TRAITEMENT DE  L'OVERRIDE DANS LES INTERFACES
        Class<?>[]interfacesTab = clazz.getInterfaces();
            for(Class<?> Classinterface : interfacesTab){
                for(Method method2 : Classinterface.getDeclaredMethods()){
                    if(method.getName()==method2.getName() && compareMethodParameters(method, method2)==true && compareMethodType(method, method2)==true)
                       return true;
                }
            }
        return false;
    }
    
    public static boolean compareMethodParameters(Method method1, Method method2) {
        // Comparez les types de paramètres
        Class<?>[] paramTypes1 = method1.getParameterTypes();
        Class<?>[] paramTypes2 = method2.getParameterTypes();
        if (!Arrays.equals(paramTypes1, paramTypes2)) {
            return false;
        }
        //Si les types de paramètres 
        return true;
    }
    public static boolean compareMethodType(Method method1, Method method2) {
        // Comparez le type de retour
        Class<?> returnType1 = method1.getReturnType();
        Class<?> returnType2 = method2.getReturnType();
        if (!returnType1.equals(returnType2)) {
            return false;
        }
        // Si les types de retour sont identiques
        return true;
    }

}