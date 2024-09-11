package application.BackEnd;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class SoftwareSize {
	static int cmp = 0;
	 classType ClassList;
     Stack<StackClass>stack = new Stack<>();
   
   
   class StackClass {
	   String ClassName;
	   int classStatus = 0;
	   StackClass(String ClassName){
		   this.ClassName = ClassName;
	   }
	   @Override
	   public String toString() {
		   return "ClassName : "+ClassName+"  classStatus : "+classStatus;
	   }
   }
   public SoftwareSize(File file) {
	fetchData(file);
    }
   
   public void print(int deepness,LinkedList<classType>InnerClass) {
	   if(deepness == 0) {
		   System.out.println("ClassName "+ClassList.ClassName);
		   System.out.println("Methods "+ClassList.MethodList);
		   System.out.println("Interfaces "+ClassList.InterfaceList);
		   ++deepness;
		   if(!ClassList.InnerClass.isEmpty()) {
		   print(deepness,ClassList.InnerClass);
		   }
	   }
	   else {
		   for(classType Class : InnerClass) {
			   System.out.println(" ".repeat(deepness)+"ClassName "+Class.ClassName);
			   System.out.println(" ".repeat(deepness)+"Methods "+Class.MethodList);
			   System.out.println(" ".repeat(deepness)+"Interfaces "+Class.InterfaceList);
			   if(!Class.InnerClass.isEmpty()) {
				   print(deepness+1, Class.InnerClass);
			   }
		   }
		   
	   }
   }
   
   void RecursiveSearchMethod(String currentClass,LinkedList<classType>InnerClass,String line,int exit) {
	for(classType classes : InnerClass) {   
		if(classes.ClassName.equals(currentClass)) {
			classes.MethodList.add(line);
			exit =1;
			break;
		}
	}
	if (exit != 1) {
        for (classType classes : InnerClass) {
        	if(classes.InnerClass.size()!=0) {
             RecursiveSearchMethod(currentClass,classes.InnerClass,line, exit);
        	}
             if (exit == 1) { 
                break;
            }
        }
    }
   }
   void addMethod(String line) {
	   String currentClass=stack.peek().ClassName;
	   String outerClass="";
	   if(stack.size()<2) {
		   outerClass=currentClass;   
	   }
	   else {
		   outerClass=stack.get(stack.size()-2).ClassName;
	   }
	   
	   line = line.replace("{", "");
	   line = line.replace("}", "");
	   if(currentClass.equals(outerClass)) {
		  this.ClassList.MethodList.add(line); 
	   }
	   else {
		   RecursiveSearchMethod(currentClass,this.ClassList.InnerClass,line,0);
	   }
   }
   
   
   
   void inputClassName(String Line,String outerClass,LinkedList<classType> InnerClasses, int exit) {
	  if(InnerClasses.isEmpty()) {
		  InnerClasses.add(new classType(0,Line));
	  }
	  else {
		  
		  for(classType Class : InnerClasses) {
			  if(Class.ClassName.equals(outerClass)) {
				  Class.InnerClass.add(new classType(0,Line));
				  exit =1;
				  break;
			  }
		  }
		  if(exit!=1) {
			  for(classType Class : InnerClasses) {
				 if(!Class.InnerClass.isEmpty()) {
				inputClassName(Line,outerClass,Class.InnerClass, exit);
				  }
				if(exit ==1) {
					break;
				}
			  }  
		  }
		  
	  }
   }
   
   void UpdateStack(String line,String className) {
	   if(stack.isEmpty()) {
		   ClassList = new classType(0,line);	      
	   }
	   stack.push(new StackClass(className));
	   
	   
   }
   
   void	 UpdateTopStatus(String line) {
	   line = line.replace(" ", "");
	   if(line.contains("{")) {
		 StackClass TopElement = stack.peek();
		 TopElement.classStatus = 1;
		 stack.set(stack.size()-1, TopElement); 
	   }
	    if(line.contains("}")) {
		   stack.pop();
	    }
	   
   }
   
   void addALL(String Line,BufferedReader reader) {
	   String currentClass = "";
	   String outerClass= "";
	   if(ClassList!=null) {
	//	   print(0, null);
	   }
	 //  System.out.println("\n\n");
	   System.out.println("\n\n"+cmp+"\t"+Line);
	   if(RegularExpression.IsClass(Line)) {
		   String className = RegularExpression.fetchClassName(Line);
		   UpdateStack(Line,className);
		   if(!stack.isEmpty()) {
			   System.out.println(stack);
			   }
			  
		   if(!className.equals(ClassList.ClassName)) {
			   if(stack.size() <2) {
		   inputClassName(Line,stack.peek().ClassName,ClassList.InnerClass, 0);
			   }
			   else {
				   inputClassName(Line,stack.get(1).ClassName,ClassList.InnerClass, 0);
						   
			   }
			   }
		   
	   }
	   else if(RegularExpression.IsMethodPrototype(Line)) {
		   if(!stack.isEmpty()) {
			   System.out.println(stack);
			   }
		   addMethod(Line);
		   if(!Line.contains("abstract ")) {
		   RegularExpression.JumpMethodContent(Line, reader);
		   }
		   }
	   
	   if(RegularExpression.containsBraces(Line)) {
		   if(!stack.isEmpty()) {
		   UpdateTopStatus(Line);
		   System.out.println(stack);
	 
		  }
	   }
	}
   
   void fetchData(File file) {
		String Line;
	    cmp = 0;
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
	          while ((Line = reader.readLine() )!= null) {
	          	++cmp;
	        	Line = Line.trim();
	          	Line = Qoute.RemoveQoute(Line);
	          	ArrayList<String> ListCode=new ArrayList<String>();
	          	if(!Line.isBlank() && !Line.isEmpty() && !Comment.IsCommentOnlyCompleted(Line) && ! RegularExpression.IsPackage(Line)) {
	          		//System.out.println(Line);
	          		if(Comment.ContainsComment(Line)) {
	  	            	//System.out.println(line);
	  	            		Line = Comment.RemoveComment(Line);
	  	            	}
	          		else {
	          			if(Comment.FinishedComment(Line)) {
		            			if(!Comment.OpeningMultiCommentOnly(Line)) {
		            				ListCode.add(Comment.CodeOpeningComment(Line));
		            			}
		            			if(!Comment.ClosingMultiCommentOnly(Line)) {
		            				ListCode.add(Comment.CodeClosingComment(Line));
		            			}
		            		}
	          			else if (Comment.NotFinishedComment(Line)) {
		            			Comment.JumpComment(Line,ListCode,reader);
		            		}

	          			if(!ListCode.isEmpty()) {
	          				for(String code : ListCode) {
	                          addALL(code, reader);
	          				}
	          			}
	          		}
	          		if(ListCode.isEmpty()) {
	          		  addALL(Line, reader);
	          			
	          		}
	          	}
	          }
	      } catch (IOException e) {
	          e.printStackTrace();
	      }
		
   }
    
    void fillParentList(String filePath) {
        Class<?> superClass = LoadClass.Loading(filePath);
        while (superClass != Object.class) {
              superClass = superClass.getSuperclass();
        }
    }
}
