package application.BackEnd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;

public class Line {
final static DecimalFormat df = new DecimalFormat("#.##");
public int totalLine = 0;
public int totalCode = 0;
public int totalComment = 0;
public int totalEmpty = 0;
public int totalBraces = 0;
public double ratioCode = 0.00;
public double ratioComment = 0.00;
public double ratioEmpty = 0.00;
public double ratioBraces = 0.00;
		
	public Line(File file) {
	
		countEverything(file);
		CodeRatio();
		CommentRatio();
		BracesRatio();
        EmptyRatio();
	}
	
	
	
	void incrementAll(String line , BufferedReader reader) {
	     ++this.totalLine;
		if(line.isEmpty()) {
			System.out.println("empty");
			++this.totalEmpty;
		}
		else if(line.equals("}")||line.equals("{")||line.equals("{}")) {
			++this.totalBraces;
		}
		else if(Comment.IsCommentOnlyCompleted(line)) {
		++this.totalComment;
		}
		else {
			if(Comment.ContainsComment(line)||Comment.FinishedComment(line)) {
				++this.totalComment;
				++this.totalCode;
			}
			else if (Comment.NotFinishedComment(line)) {
				Comment.JumpComment(line, reader, this.totalCode, this.totalComment);
			}
			else {
				++this.totalCode;
			}
				
		}
	}
	
	void countEverything(File file) {
	
		String Line  = "";
		 try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
	            while ((Line = reader.readLine() )!= null) {
	            //	System.out.println(Line);
	            	Line = Line.replace(" ","");
                    Line =  Qoute.RemoveQoute(Line);
                    incrementAll(Line, reader);
                  
	            }
		 }catch (IOException e) {
	            e.printStackTrace();
	        }
		
		 if(Line!=null) {
	        	++this.totalLine;
	          }
	}
	
	
	 public void CommentRatio() {
		 if(this.totalLine!=0) {
		   this.ratioComment=Double.parseDouble(df.format(((double)this.totalComment/this.totalLine)*100));
		  }
		 
		 }
	
	 public void CodeRatio() {
		 if(this.totalLine!=0) {
		 this.ratioCode =Double.parseDouble(df.format(((double)this.totalCode/this.totalLine)*100));
		 }
		 }
		

	 public void EmptyRatio() {
		 if(this.totalLine!=0) {
			this.ratioEmpty = Double.parseDouble(df.format(((double)this.totalEmpty/this.totalLine)*100));
		  }
		 
		 }
	
	 public void BracesRatio() {
		 if(this.totalLine!=0) {
		 this.ratioBraces =  Double.parseDouble(df.format(((double)this.totalBraces/this.totalLine)*100));
		 }
		 
		 }
}
