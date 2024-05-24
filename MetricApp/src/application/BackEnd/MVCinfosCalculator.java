package application.BackEnd;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;


public class MVCinfosCalculator{

    public Map<String, Integer> dataFlowCount;
   public Map<String, Set<String>> couplings;
   public Class<?> loadedClassController, loadedClassModel ;
	public Class<?> loadedClassView;


    public static MVCinfosCalculator fetchMVCinfos(String path,String filenameController,String filenameModel,String filenameView) throws ClassNotFoundException, IOException  {
    	
    	MVCinfosCalculator t = new MVCinfosCalculator();

        path = path.replace("\\src\\", "\\bin\\");
    	
    	path = path.substring(0,path.indexOf("\\bin\\")+4);
        
        URLClassLoader classLoader = new URLClassLoader(new URL[]{new File(path).toURI().toURL()}); //ou vous allez tout simplement remplacer MyClass par le nom de la classe dont vous avez cree le fichier 
        //ou une classe que vous possedez deja dans votre projet
        Class<?> loadedClassController = classLoader.loadClass(filenameController);
        Class<?> loadedClassModel = classLoader.loadClass(filenameModel);
        Class<?> loadedClassView = classLoader.loadClass(filenameView);
       

        List<Class<?>> classes = Arrays.asList(loadedClassModel, loadedClassView, loadedClassController);
        Map<String, Set<String>> couplings = new HashMap<>();

        for (Class<?> cls : classes) {
            String layer = getLayer(cls,t.loadedClassController,t.loadedClassView,t.loadedClassModel);
            Set<String> dependencies = analyzeDependencies(cls,t);
            couplings.putIfAbsent(layer, new HashSet<>());
            couplings.get(layer).addAll(dependencies);
        }

        t.couplings=printCouplings(couplings);

        Method[] methodsModel=DisplayMethods(loadedClassModel);
        Method[]methodsView=DisplayMethods(loadedClassView);
        Method[] methodsConroller=DisplayMethods(loadedClassController);

        try {
            analyzeDataFlow(filenameController);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        DataFlowVisitor visitor= analyzeDataFlow(filenameController);
         t.dataFlowCount=visitor.dataFlowCount;
        
        return t;
        
    }

    private static String getLayer(Class<?> cls,Class<?> Controller,Class<?> View,Class<?> Model) {
        if (Model.isAssignableFrom(cls)) {
            return "MODEL";
        } else if (View.isAssignableFrom(cls)) {
            return "VIEW";
        } else if (Controller.isAssignableFrom(cls)) {
            return "CONTROLLER";
        }
        return "UNKNOWN";
    }

    private static Set<String> analyzeDependencies(Class<?> cls, MVCinfosCalculator mvc) {
        Set<String> dependencies = new HashSet<>();
        Field[] fields = cls.getDeclaredFields();

        for (Field field : fields) {
            Class<?> fieldType = field.getType();
            String dependencyLayer = getLayer(fieldType,mvc.loadedClassController,mvc.loadedClassView,mvc.loadedClassModel);
            if (!dependencyLayer.equals("UNKNOWN")) {
                dependencies.add(dependencyLayer);
            }
        }

        return dependencies;
    }

    private static Map<String, Set<String>> printCouplings(Map<String, Set<String>> couplings) {
        for (Map.Entry<String, Set<String>> entry : couplings.entrySet()) {
            System.out.println(entry.getKey() + " depends on: " + entry.getValue());
        }
        return couplings;
    }




    private static DataFlowVisitor analyzeDataFlow(String filePath) throws IOException {
        FileInputStream in = new FileInputStream(filePath);
        JavaParser parser = new JavaParser();
        CompilationUnit cu = parser.parse(in).getResult().orElseThrow(() -> new RuntimeException("Parsing failed"));

        DataFlowVisitor visitor = new DataFlowVisitor();
        visitor.visit(cu, null);

        in.close();

        visitor.printReport();
        return visitor;
    }

    private static class DataFlowVisitor extends VoidVisitorAdapter<Void> {
        private Map<String, Integer> dataFlowCount = new HashMap<>();

        @Override
        public void visit(MethodDeclaration n, Void arg) {
            super.visit(n, arg);

            n.getBody().ifPresent(body -> body.findAll(MethodCallExpr.class).forEach(call -> {
                String callee = call.getNameAsString();
                dataFlowCount.put(callee, dataFlowCount.getOrDefault(callee, 0) + 1);
            }));
        }

        public Map<String, Integer> printReport() {
            System.out.println("Data Flow Complexity Report of the class Controller:");
            dataFlowCount.forEach((method, count) -> System.out.println("Method " + method + " is called " + count + " times."));
            System.out.println("Total complexity: " + dataFlowCount.values().stream().mapToInt(Integer::intValue).sum());
            return dataFlowCount;
        }
    }

    private static Method[] DisplayMethods(Class<?> cl){
        Method[] methods = cl.getDeclaredMethods();
        System.out.println("mothods of the class: "+ cl.getName() +" Are: ");
        for(Method method : methods){
            System.out.println("  "+method.getName());
        }
        return methods;
    }
}