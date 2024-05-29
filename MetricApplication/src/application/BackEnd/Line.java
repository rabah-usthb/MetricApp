package application.BackEnd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Line {
	//CountLine Excluding Comment only Line , Empty Line , Bracket Only Line
	public static int CountLinesContainsCode(File file) {
		int NbLine = 0;
		String Line;
		 try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
	            while ((Line = reader.readLine() )!= null) {
	            //	System.out.println(Line);
	            	if(!Line.isEmpty() && !Line.isBlank() && !Comment.IsCommentOnlyCompleted(Line)) {
                    Line = Line.trim();
                    Line =  Qoute.RemoveQoute(Line);
	                if(!RegularExpression.IsBracket(Line)) {
                    if(!Comment.ContainsComment(Line) ) {
	                	++NbLine;
	                }
                    else if(Comment.FinishedComment(Line) ) {
                    	if(!Comment.ContainsClosingComment(Line) || !Comment.ContainsOpeningComment(Line)) {
                    		
                    		++NbLine;
                    	}
                    }
                    else if(Comment.NotFinishedComment(Line)) {
                    	NbLine+=Comment.JumpComment(Line,reader);
                    }
                    else {
                    	
                    
                    	++NbLine;
                    }
                    
	                }
	                
                    
	            	}
	        
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		
		return NbLine;
	}
	
	public static int CountLineCommentOnly(File file) {
		int NbLine = 0;
		String Line;
		 try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
	            while ((Line = reader.readLine() )!= null) {
	            	if(!Line.isEmpty() && !Line.isBlank()) {
                   Line = Line.trim();
                   Line =  Qoute.RemoveQoute(Line);
	                if(!RegularExpression.IsBracket(Line)) {
                   if(Comment.IsCommentOnlyCompleted(Line) ) {
	                	++NbLine;
	                }
                   else if(Comment.NotFinishedComment(Line)) {
                      	NbLine+=Comment.JumpCommentForCommentOnlyLine(Line,reader);
                      }
                   }
                   
                   
	                }
	                
	            	}
	        
	            }
	         catch (IOException e) {
	            e.printStackTrace();
	        }

		return NbLine;
	}
	
	 public static double CommentRation(File file) {
		int TotalNumberOfLine = CountLineAllLines(file);
		int CommentOnlyLine =CountLineCommentOnly(file);
		return ((double)CommentOnlyLine/TotalNumberOfLine)*100;
	}
	
	 public static double CodeRation(File file) {
		int TotalNumberOfLine = CountLineAllLines(file);
		int CodeOnlyLine = CountLinesContainsCode(file);
		return ((double)CodeOnlyLine/TotalNumberOfLine)*100;
	}
	
	 public static int CountCurlyBracesLine(File file) {
	    int NbLine = 0;
	    String Line = "";
	    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
	        while ((Line = reader.readLine()) != null) {
	            Line = Line.trim();
	            if (!Line.isEmpty() && (Line.equals("{")||Line.equals("}"))) {
	                ++NbLine;
	            }
	          
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return NbLine;
	}

	public static int CountBlankLine(File file) {
	    int NbLine = 0;
	    String Line = "";
	    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
	        while ((Line = reader.readLine()) != null) {
	 //           System.out.println(Line);
	            Line = Line.trim();
	            if (Line.isEmpty()) {
	                ++NbLine; // Increment if the line is empty
	            }
	          
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return NbLine;
	}
	
	public static int CountLineAllLines(File file) {
	    int NbLine = 0;
	    String Line = "";
	    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
	        while ((Line = reader.readLine()) != null) {
	            Line = Line.trim();
	            if (Line.isEmpty()) {
	                ++NbLine; // Increment if the line is empty
	            }
	            ++NbLine; // Increment for non-empty lines
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return NbLine;
	}

	
	public static int CountLineNotEmptyNotBracket(File file) {
		int NbLine = 0;
		String Line;
		 try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
	            while ((Line = reader.readLine() )!= null) {
	            	if(!Line.isEmpty()&& !Line.isBlank() && !RegularExpression.IsBracket(Line)) {
	            	++NbLine;
	                
	            	}
	        
	            }
		 }
	         catch (IOException e) {
	            e.printStackTrace();
	        }

		return NbLine;		
	}
	
	
	
}
