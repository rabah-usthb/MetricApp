package a;

public class Project1Analyzer {
    public static void main(String[] args) {
        String className = args[0]; 

        try {
            Class.forName(className, false, ClassLoader.getSystemClassLoader());
            System.out.println(className + " is available.");
        } catch (ClassNotFoundException e) {
            System.out.println(className + " is NOT available.");
        }
    }
}
