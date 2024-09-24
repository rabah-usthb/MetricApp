package application.BackEnd;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.Stack;

public class classType {
public int finalStatus = 0;
public int staticStatus = 0;
public int abstractStatus = 0;
public int subStatus = 0;
public String classDeclaration;
public String ClassName;
public Stack<String>ParentTree = new Stack<>();
public Set<String>MethodList = new LinkedHashSet<>();
public Set<String>InterfaceList = new LinkedHashSet<>();
public LinkedList<classType>InnerClass = new LinkedList<>();

     classType(String classDeclaration){ 
	  this.classDeclaration = classDeclaration;
      this.ClassName = RegularExpression.fetchClassName(classDeclaration);  
      this.InterfaceList.addAll(RegularExpression.FetchImplements(classDeclaration));
     }
     
     void setClassStatus(String classDeclaration) {
        if(classDeclaration.contains("extends ")) {
        	this.subStatus = 1;
        }
        if(classDeclaration.contains("abstract ")) {
        	this.abstractStatus = 1;
        }
        if(classDeclaration.contains("final ")) {
        	this.finalStatus = 1;
        }
        if(classDeclaration.contains("static ")) {
        	this.staticStatus = 1; 
        }
     }
}
