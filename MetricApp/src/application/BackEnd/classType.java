package application.BackEnd;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.Stack;

public class classType {
int classStatus = 0;
String classDeclaration;
String ClassName;
Stack<String>ParentTree = new Stack<>();
Set<String>MethodList = new LinkedHashSet<>();
Set<String>InterfaceList = new LinkedHashSet<>();
LinkedList<classType>InnerClass = new LinkedList<>();

     classType(int classStatus ,String classDeclaration){ 
	  this.classStatus = classStatus;
	  this.classDeclaration = classDeclaration;
      this.ClassName = RegularExpression.fetchClassName(classDeclaration);  
      this.InterfaceList.addAll(RegularExpression.FetchImplements(classDeclaration));
     }
}
