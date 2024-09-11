package application.BackEnd;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class SoftwareSize {
	 classType ClassList;
     Stack<StackClass>stack = new Stack<>();
   class TrackClass {
	   String OuterClass = "";
	   String CurrentClass = "";
	   
   }
   
   class StackClass {
	   String ClassName;
	   int classStatus = 0;
	   StackClass(String ClassName){
		   this.ClassName = ClassName;
	   }
	   @Override
	   public String toString() {
		   return "ClassName : "+ClassName+"\nclassStatus : "+classStatus;
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
   
   void RecursiveSearchMethod(LinkedList<classType>InnerClass,TrackClass clasTracker,String line,int exit) {
	for(classType classes : InnerClass) {   
		if(classes.ClassName.equals(clasTracker.CurrentClass)) {
			classes.MethodList.add(line);
			exit =1;
			break;
		}
	}
	if (exit != 1) {
        for (classType classes : InnerClass) {
        	if(classes.InnerClass.size()!=0) {
             RecursiveSearchMethod(classes.InnerClass,clasTracker,line, exit);
        	}
             if (exit == 1) { 
                break;
            }
        }
    }
   }
   void addMethod(String line,TrackClass classTracker) {
	   String currentClass=classTracker.CurrentClass;
	   String outerClass=classTracker.OuterClass;
	   line = line.replace("{", "");
	   line = line.replace("}", "");
	   if(currentClass.equals(outerClass)) {
		  this.ClassList.MethodList.add(line); 
	   }
	   else {
		   RecursiveSearchMethod(this.ClassList.InnerClass,classTracker,line,0);
	   }
   }
   
   
   
   void inputClassName(String Line , TrackClass tracker,LinkedList<classType> InnerClasses, int exit) {
	  if(InnerClasses.isEmpty()) {
		  InnerClasses.add(new classType(0,Line));
	  }
	  else {
		  
		  for(classType Class : InnerClasses) {
			  if(Class.ClassName.equals(tracker.OuterClass)) {
				  Class.InnerClass.add(new classType(0,tracker.CurrentClass));
				  exit =1;
				  break;
			  }
		  }
		  if(exit!=1) {
			  for(classType Class : InnerClasses) {
				 if(!Class.InnerClass.isEmpty()) {
				inputClassName(Line, tracker, Class.InnerClass, exit);
				  }
				if(exit ==1) {
					break;
				}
			  }  
		  }
		  
	  }
   }
   
   void UpdateStack(String line,TrackClass tracker,String className) {
	   if(!stack.isEmpty()) {
	   tracker.OuterClass = stack.peek().ClassName;
	   }
	   else{
		   ClassList = new classType(0,line);
		   tracker.OuterClass = className;
		      
	   }
	   stack.push(new StackClass(className));
	   
	   tracker.CurrentClass = className;
	   
	   if(RegularExpression.IsBracket(line)) {
		   UpdateTopStatus(line, tracker);
	   }
   }
   
   void	 UpdateTopStatus(String line,TrackClass tracker) {
	   line = line.replace(" ", "");
	   if(line.contains("{")) {
		   StackClass TopElement = stack.pop();
		   TopElement.classStatus = 1;
		   stack.push(TopElement);
	   }
	    if(line.contains("}")) {
		   stack.pop();
		   if(stack.size()>=2) {
			   tracker.CurrentClass = stack.peek().ClassName;
			   tracker.OuterClass = stack.get(1).ClassName;
		      
		   }
		   else if(stack.size() == 1) {
			   tracker.CurrentClass = stack.peek().ClassName;
			   tracker.OuterClass = stack.peek().ClassName;
		      
		   }
		   else if (stack.isEmpty()) {
			   tracker.CurrentClass = "";
			   tracker.OuterClass = "";
		   }
	   }
	   
   }
   
   void addALL(String Line,BufferedReader reader,TrackClass classTracker) {
	   if(ClassList!=null) {
	//	   print(0, null);
	   }
	 //  System.out.println("\n\n");
	   System.out.println("Normale Line "+Line);
	   if(RegularExpression.IsClass(Line)) {
		   String className = RegularExpression.fetchClassName(Line);
		   UpdateStack(Line,classTracker,className);
		   if(!className.equals(ClassList.ClassName)) {
		   inputClassName(Line, classTracker, ClassList.InnerClass, 0);
		   }
		   
	   }
	   else if(RegularExpression.IsMethodPrototype(Line)) {
		   addMethod(Line, classTracker);
		   RegularExpression.JumpMethodContent(Line, reader);
	   }
	   else if(RegularExpression.IsBracket(Line)) {
		   UpdateTopStatus(Line, classTracker);
	   }
	   System.out.println(stack);
   }
   
   void fetchData(File file) {
		String Line;
		TrackClass classTracker = new TrackClass();
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
	          while ((Line = reader.readLine() )!= null) {
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
	                          addALL(code, reader, classTracker);
	          				}
	          			}
	          		}
	          		if(ListCode.isEmpty()) {
	          		  addALL(Line, reader, classTracker);
	          			
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
