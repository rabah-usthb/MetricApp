package application.BackEnd;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;


public class Comment {
	
	//To Jump Comment And Increment code and comment line
	static void JumpComment (String Line,BufferedReader reader,int totalCode,int totalComment) {
		++totalComment;
		if(!OpeningMultiCommentOnly(Line)) {
	    ++totalCode;
		}
		try {
			while ((Line = reader.readLine()) != null) { 
			Line = Line.trim();
			Line = Qoute.RemoveQoute(Line);
	        ++totalComment;
			if(Line.contains("*/")) {
				break;
			}
			}
		}
		catch(IOException e) {
			
		}
		++totalComment;
		if(!ClosingMultiCommentOnly(Line)) {
	     ++totalCode;
		}
		
	}
	
	
	
	//To Jump Comment And Fetch Code
	static void JumpComment (String Line,ArrayList<String> List,BufferedReader reader) {
		if(!OpeningMultiCommentOnly(Line)) {
			List.add(CodeOpeningComment(Line));
		}
		//System.out.println(Line);
		try {
			while ((Line = reader.readLine()) != null) { 
			Line = Line.trim();
			Line=Qoute.RemoveQoute(Line);
			//System.out.println(Line);
			if(Line.contains("*/")) {
				break;
			}
			}
		}
		catch(IOException e) {
			
		}
		if(!ClosingMultiCommentOnly(Line)) {
			List.add(CodeClosingComment(Line));
		}
	}
	
	
	//Method To Remove Comment From Code //Comment
	static String RemoveComment(String line) {
	     int index = line.indexOf("//");
	     line = line.substring(0,index);
		 
			return line;
	 } 
	
	//Method To Know If Line Is Code //Comment
	 static boolean ContainsComment(String Line) {	
		 return Line.contains("//") ; 
	 }
	 
	 
	//To Know If Line Is Code /*comment*/ Code 
	 static boolean FinishedComment(String Line) {
		 Line = Line.trim();
		return Line.contains("/*") && Line.contains("*/") ;	 
	 }
	 
	 //To Know If Line is Code /*comment
	static boolean NotFinishedComment(String Line) {
		Line = Line.trim();
		return Line.contains("/*") && !Line.contains("*/") ;
	}
	
	//To Know If Line Contains Code Before /*
	static boolean OpeningMultiCommentOnly(String Line) {
		return Line.startsWith("/*");
	}
    
	//To Know If Line Contains Code After */
	static boolean ClosingMultiCommentOnly(String Line) {
		String line = Line.replaceAll(" ", "");
		return line.endsWith("*/");
	}
	
	//To Fetch Code From Code /*Comment
	static String CodeOpeningComment(String Line) {
		return Line.substring(0,Line.indexOf("/*"));
	}
	
	//To Fetch Code From Comment*/ Code
	static String CodeClosingComment(String Line) {
		return Line.substring(Line.indexOf("*/")+2);
	}
 /**/
	
    //To Know If Line Is //Comment or /*Comment*/	
	static boolean IsCommentOnlyCompleted(String Line) {
		Line = Line.trim();
		Line = Line.replace(" ", "");
	    boolean SimpleComment = Line.startsWith("//");
	    boolean multiLineComment = Line.startsWith("/*")&&Line.endsWith("*/");
	return SimpleComment||multiLineComment;
	}

}
