package application.BackEnd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class Henderson {
private String className;
private int numberAttribute = 0;
private int numberMethods = 0;

public Henderson(String className,int numberAttribute,int numberMethods) {
	this.className =  className;
	this.numberAttribute = numberAttribute;
	this.numberMethods = numberMethods;
}

public static LinkedList<Henderson> fetchHendersonData(File file){
		String Line;
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
	          					if(RegularExpression.IsClass(code)) {
	          	             
	          					return	RegularExpression.fetchClassesDataHenderson(code, reader);
	          	            	}
	          					
	          					
	          				}
	          			}
	          		}
	          		if(ListCode.isEmpty()) {
                         
	          			if(RegularExpression.IsClass(Line))
  						return RegularExpression.fetchClassesDataHenderson(Line, reader);
  	            
	          			
	          		}
	          	}
	          }
	      } catch (IOException e) {
	          e.printStackTrace();
	      }
		return null;
		
}

public String getclassName() {
	return this.className;
}

public int getNumberMethods() {
	return this.numberMethods;
}

public int getNumberAttributs() {
	return this.numberAttribute;
}
}