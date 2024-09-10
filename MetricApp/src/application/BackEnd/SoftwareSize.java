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
   }
   public SoftwareSize(File file) {
	
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
   

   void RecursiveSearchInterface(LinkedList<classType>InnerClass,TrackClass clasTracker,String line,int exit) {
		for(classType classes : InnerClass) {   
			if(classes.ClassName.equals(clasTracker.CurrentClass)) {
				classes.InterfaceList.add(line);
				exit =1;
				break;
			}
		}
		if (exit != 1) {
	        for (classType classes : InnerClass) {
	            if(classes.InnerClass.size()!=0) {
	        	RecursiveSearchInterface(classes.InnerClass, clasTracker, line, exit);
	            }
	        	if (exit == 1) { 
	                break;
	            }
	        }
	    }
	   }

   
   void addInterface(String line,TrackClass classTracker) {
	   String currentClass=classTracker.CurrentClass;
	   String outerClass=classTracker.OuterClass;
	   line = line.replace("{", "");
	   line = line.replace("}", "");
	   if(currentClass.equals(outerClass)) {
		  this.ClassList.InterfaceList.addAll(RegularExpression.FetchImplements(line)); 
	   }
	   else {
		   RecursiveSearchInterface(this.ClassList.InnerClass,classTracker,line,0);
	   }
     
   }
   
   void UpdateStack(String line,TrackClass tracker,String className) {
	   if(!stack.isEmpty()) {
	   tracker.OuterClass = stack.peek().ClassName;
	   }
	   else{
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
	   if(RegularExpression.IsClass(Line)) {
		   String className = RegularExpression.fetchClassName(Line);
		   //needs updating the classList
		   UpdateStack(Line,classTracker,className);
		   
	   }
	   else if(RegularExpression.IsMethodPrototype(Line)) {
		   addMethod(Line, classTracker);
		   RegularExpression.JumpMethodContent(Line, reader);
	   }
	   else if(RegularExpression.IsBracket(Line)) {
		   UpdateTopStatus(Line, classTracker);
	   }
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
