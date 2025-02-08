package application.BackEnd;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileWriter;

public class CleanData {
	File sourceCode;
	File tmpFile;
	
	
	public CleanData (File file){
		this.sourceCode = file;
		writeTmp();
	}
	
	
	void writeTmp() {
		StringBuilder content = CleanCode();
		try {
			tmpFile = File.createTempFile(this.sourceCode.getPath(),".tmp");
			tmpFile.deleteOnExit();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(tmpFile))) {      
	            writer.write(content.toString());
	    
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	}
	
	StringBuilder CleanCode(){
		
		StringBuilder cleanedCodeContent = new StringBuilder();
		
		try (BufferedReader reader = new BufferedReader(new FileReader(this.sourceCode))) {
	          String line;
	          while ((line = reader.readLine()) != null) {
	          line = line.trim();
	          line =  Qoute.RemoveQoute(line);
	        
	              ArrayList<String> ListCode=new ArrayList<String>();
	              
	              if(!line.isBlank() && !line.isEmpty() && !RegularExpression.IsBracket(line)&& !Comment.IsCommentOnlyCompleted(line)) {
	        
	              	if(Comment.ContainsComment(line)) {
	          
	          		line = Comment.RemoveComment(line);
	          	}
	          	else {
	          		
	          		if(Comment.FinishedComment(line)) {
	          			if(!Comment.OpeningMultiCommentOnly(line)) {
	          				ListCode.add(Comment.CodeOpeningComment(line));
	          			}
	          			if(!Comment.ClosingMultiCommentOnly(line)) {
	          				ListCode.add(Comment.CodeClosingComment(line));
	          			}
	          		}
	          		else if (Comment.NotFinishedComment(line)) {
	          			Comment.JumpComment(line,ListCode,reader);
	          		}
	          		if(ListCode.isEmpty()) {
	          			ListCode.add(line);
	          		}
	          	}
	          	
	          	for(String code : ListCode) {
	          		cleanedCodeContent.append(code+"\n");
	          	}
	              	
	              	
	              }
	          }
		}catch (IOException e) {
	              e.printStackTrace(); // Handle any IO exceptions
	          }
		
	 return cleanedCodeContent;
	}

}
