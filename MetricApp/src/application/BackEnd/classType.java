package application.BackEnd;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.Stack;

public class classType {
int finalStatus = 0;
int staticStatus = 0;
int abstractStatus = 0;
int subStatus = 0;
String classDeclaration;
String ClassName;
Stack<String>ParentTree = new Stack<>();
Set<String>MethodList = new LinkedHashSet<>();
Set<String>InterfaceList = new LinkedHashSet<>();
LinkedList<classType>InnerClass = new LinkedList<>();

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
