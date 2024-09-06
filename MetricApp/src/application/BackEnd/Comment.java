package application.BackEnd;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;


public class Comment {
	
	//To Jump Comment And Increment CountLine
	static int JumpComment (String Line,BufferedReader reader) {
		int NbLine = 0;
		if(!ContainsOpeningComment(Line)) {
	    ++NbLine;
		}
	//	System.out.println(Line);
		try {
			while ((Line = reader.readLine()) != null) { 
			Line = Line.trim();
			Line = Qoute.RemoveQoute(Line);
			//System.out.println(Line);
			if(Line.contains("*/")) {
				break;
			}
			}
		}
		catch(IOException e) {
			
		}
		if(!ContainsClosingComment(Line)) {
	   ++NbLine;
		}
		return NbLine;
	}
	
	
	
	static int JumpCommentForCommentOnlyLine (String Line,BufferedReader reader) {
		int NbLine = 0;
		if(ContainsOpeningComment(Line)) {
	    ++NbLine;
		}
	//	System.out.println(Line);
		try {
			while ((Line = reader.readLine()) != null) { 
			Line = Line.trim();
			Line = Qoute.RemoveQoute(Line);
			++NbLine;
			if(Line.contains("*/")) {
				break;
			}
			}
		}
		catch(IOException e) {
			
		}
		if(ContainsClosingComment(Line)) {
	   ++NbLine;
		}
		return NbLine;
	}
	
	
	

	//To Jump Comment And Fetch Code
	static void JumpComment (String Line,ArrayList<String> List,BufferedReader reader) {
		if(!ContainsOpeningComment(Line)) {
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
		if(!ContainsClosingComment(Line)) {
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
	static boolean ContainsOpeningComment(String Line) {
		return Line.startsWith("/*");
	}
    
	//To Know If Line Contains Code After */
	static boolean ContainsClosingComment(String Line) {
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
 
	
    //To Know If Line Is //Comment or /*Comment*/	
	static boolean IsCommentOnlyCompleted(String Line) {
		Line = Line.trim();
	    String singleLineCommentPattern = "//.*"; 
	    String multiLineCommentPatternCompleted = "/\\*((?!(\\*/))[^\\n]|\\n)*(\\*/)";
	return Line.matches(multiLineCommentPatternCompleted)||Line.matches(singleLineCommentPattern);
	}

}
