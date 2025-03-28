package application.BackEnd;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;


public class Comment {
	
	//To Jump Comment And Increment code and comment line
	static void JumpComment (String Line,BufferedReader reader,Line line) {
		++line.totalComment;
		if(!OpeningMultiCommentOnly(Line)) {
			++line.totalCode;
		}
		try {
			while ((Line = reader.readLine()) != null) { 
			Line = Line.trim();
			Line = Qoute.RemoveQoute(Line);
			++line.totalComment;
			++line.totalLine;
			if(Line.contains("*/")) {
				break;
			}
			}
		}
		catch(IOException e) {
			
		}
		
		if(!ClosingMultiCommentOnly(Line)) {
			++line.totalCode;
			}
		
	}
	
	
	//To Jump Comment And Fetch Code
		static String JumpComment (String Line,ArrayList<String> List,String[] code,Integer[] index) {
			String line = "";
			int index_0 = index[0];
			if(!OpeningMultiCommentOnly(Line)) {
				System.out.println("Before "+Line);
				List.add(CodeOpeningComment(Line));
			}
			//System.out.println(Line);
		
				for ( int i = index_0 ; i<code.length;i++) { 
				Line = code[i].trim();
				Line=Qoute.RemoveQoute(Line);
				//System.out.println(Line);
				if(Line.contains("*/")) {
					line = Line;
					Line = code[i+1];
					index[0] = i+1;
					break;
				}
				}
			
			
			if(!ClosingMultiCommentOnly(line)) {
				List.add(CodeClosingComment(line));
				System.out.println("After "+line);
			}
			
		   // System.out.println("finished "+Line);
		    return Line;
		}
		
	
	
	//To Jump Comment And Fetch Code
	static String JumpComment (String Line,ArrayList<String> List,BufferedReader reader) {
		String line = "";
		if(!OpeningMultiCommentOnly(Line)) {
			System.out.println("Before "+Line);
			List.add(CodeOpeningComment(Line));
		}
		//System.out.println(Line);
		try {
			while ((Line = reader.readLine()) != null) { 
			Line = Line.trim();
			Line=Qoute.RemoveQoute(Line);
			//System.out.println(Line);
			if(Line.contains("*/")) {
				line = Line;
				Line = reader.readLine();
				break;
			}
			}
		}
		catch(IOException e) {
			
		}
		if(!ClosingMultiCommentOnly(line)) {
			List.add(CodeClosingComment(line));
			System.out.println("After "+line);
		}
		
	   // System.out.println("finished "+Line);
	    return Line;
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
		try{return Line.substring(Line.indexOf("*/")+2);}
		catch (StringIndexOutOfBoundsException e){
			return "";
		}
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
