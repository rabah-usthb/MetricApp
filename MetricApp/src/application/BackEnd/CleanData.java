package application.BackEnd;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;

import java.io.FileWriter;

public class CleanData {
	File sourceCode;
	public File tmpFile;
	
	public CleanData (File file,int op){
		this.sourceCode = file;
		if(op==1) {
		writeER();
		}
		else {
			writeRest();
		}
	}
	
	public CleanData (File file){
		this.sourceCode = file;
		writeTmp();
	}
	
	void writeRest() {
	    StringBuilder content = cleanRest();
	    
	//    System.out.println("tmp BEFORE");
	  

	    try {
	    //	 System.out.println("tmp TRY");
	        File parentDir = this.sourceCode.getParentFile();
	        String prefix = this.sourceCode.getName();
	        this.tmpFile = File.createTempFile(prefix, ".tmp", parentDir);
	        this.tmpFile.deleteOnExit();
	       // System.out.println("tmp "+(this.tmpFile == null));
	    } catch (IOException e) {
	        e.printStackTrace();
	        return;  // Exit the method if we can't create the temp file
	    }

	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(tmpFile))) {
	        writer.write(content.toString());
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	
	void writeER() {
	    StringBuilder content = cleanER();
	    
	    try {
	        File parentDir = this.sourceCode.getParentFile();
	        String prefix = this.sourceCode.getName();
	        this.tmpFile = File.createTempFile(prefix, ".tmp", parentDir);
	        this.tmpFile.deleteOnExit();
	    } catch (IOException e) {
	        e.printStackTrace();
	        return;  // Exit the method if we can't create the temp file
	    }

	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(tmpFile))) {
	        writer.write(content.toString());
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
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
	
	
	public StringBuilder cleanRest(){
		//	System.out.println("heyy \n\n\n");
		
		    StringBuilder cleanedCodeContent = new StringBuilder();
		
			String formattedCode = "";
		  
		  	  String Code;
			try {
				Code = new String(Files.readAllBytes(Paths.get(this.sourceCode.getAbsolutePath())));
				 formattedCode = new Formatter().formatSource(Code);
			} catch (IOException | FormatterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String [] Line = formattedCode.split("\n");
			Index index = new Index();
			
			for( index.val =index.val; index.val<Line.length;index.val++) {
			//	 System.out.println("Start LOOP "+Line[index.val]);
				
				 
		          	Line[index.val] = Line[index.val].trim();
		          	Line[index.val] = Qoute.RemoveQoute(Line[index.val]);
		          	ArrayList<String> ListCode=new ArrayList<String>();
		          	if(!Line[index.val].isBlank() && !Line[index.val].isEmpty() &&!Comment.IsCommentOnlyCompleted(Line[index.val]) && ! RegularExpression.IsPackage(Line[index.val])) {
		          		
		          		if(Comment.ContainsComment(Line[index.val])) {
		  	            		Line[index.val] = Comment.RemoveComment(Line[index.val]);
		  	            	}
		          		else {
		          			if(Comment.FinishedComment(Line[index.val])) {
			            			if(!Comment.OpeningMultiCommentOnly(Line[index.val])) {
			            				ListCode.add(Comment.CodeOpeningComment(Line[index.val]));
			            			}
			            			if(!Comment.ClosingMultiCommentOnly(Line[index.val])) {
			            				ListCode.add(Comment.CodeClosingComment(Line[index.val]));
			            			}
			            		}
		          			else if (Comment.NotFinishedComment(Line[index.val])) {
		          		//		System.out.println("NOT FINISHED "+Line[index.val]);
		          				  Comment.JumpComment(Line[index.val],ListCode,Line,index);
		          				if(index.val==Line.length) {
		          					break;
		          				}
		          			//	System.out.println("FINISHED "+Line[index.val]);
	                     	
		            		}

		          			if(!ListCode.isEmpty()) {
		          				for(String code : ListCode) {
		          				
		          					 cleanedCodeContent.append(code+"\n");
		          				
		          				}
		          			}
		          		}
		          		
		          		//System.out.println("Text "+Line[index.val]);
		          		
	              		
		          		
		          		Line[index.val] = Line[index.val].trim();
			          	Line[index.val] = Qoute.RemoveQoute(Line[index.val]);
			          	
			          	String text = Line[index.val];
			          	
			          	while(index.val<Line.length-1 && (text.endsWith(",") || text.endsWith("(") || Line[index.val+1].trim().startsWith("throws ")||Line[index.val+1].trim().startsWith("extends ")||Line[index.val+1].trim().startsWith("implements "))){
			          		text = text + " " +Qoute.RemoveQoute(Line[index.val+1].trim());
			          		index.val++;
			          	}
			          	
		          		cleanedCodeContent.append(text+"\n");
		          		
		          		Line[index.val] = text;
		          		

		          	}
			}
		     
			
			return cleanedCodeContent;
		}
	
	
	public StringBuilder cleanER(){
		//	System.out.println("heyy \n\n\n");
		
		    StringBuilder cleanedCodeContent = new StringBuilder();
		
			String formattedCode = "";
		  
		  	  String Code;
			try {
				Code = new String(Files.readAllBytes(Paths.get(this.sourceCode.getAbsolutePath())));
				 formattedCode = new Formatter().formatSource(Code);
			} catch (IOException | FormatterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String [] Line = formattedCode.split("\n");
			Index index = new Index();
			
			for( index.val =index.val; index.val<Line.length;index.val++) {
				 System.out.println("Start LOOP "+Line[index.val]);
				
				 
		          	Line[index.val] = Line[index.val].trim();
		          	Line[index.val] = Qoute.RemoveQoute(Line[index.val]);
		          	ArrayList<String> ListCode=new ArrayList<String>();
		          	if(!Line[index.val].isBlank() && !Line[index.val].isEmpty() &&!Comment.IsCommentOnlyCompleted(Line[index.val]) && ! RegularExpression.IsPackage(Line[index.val])) {
		          		
		          		if(Comment.ContainsComment(Line[index.val])) {
		  	            		Line[index.val] = Comment.RemoveComment(Line[index.val]);
		  	            	}
		          		else {
		          			if(Comment.FinishedComment(Line[index.val])) {
			            			if(!Comment.OpeningMultiCommentOnly(Line[index.val])) {
			            				ListCode.add(Comment.CodeOpeningComment(Line[index.val]));
			            			}
			            			if(!Comment.ClosingMultiCommentOnly(Line[index.val])) {
			            				ListCode.add(Comment.CodeClosingComment(Line[index.val]));
			            			}
			            		}
		          			else if (Comment.NotFinishedComment(Line[index.val])) {
		          				System.out.println("NOT FINISHED "+Line[index.val]);
		          				Comment.JumpComment(Line[index.val],ListCode,Line,index); 
		          				if(index.val==Line.length) {
		          					break;
		          				}
		          				System.out.println("FINISHED "+Line[index.val]);
	                     	
		            		}

		          			if(!ListCode.isEmpty()) {
		          				for(String code : ListCode) {
		          				
		          					 cleanedCodeContent.append(code+"\n");
		          					if(RegularExpression.IsMethodPrototype(code)) {
		    	          				RegularExpression.JumpMethodContent(code,index,Line);
		    	          			}	
		          				}
		          			}
		          		}
		          		
		          		//System.out.println("Text "+Line[index.val]);
		          		
	              		
		          		
		          		Line[index.val] = Line[index.val].trim();
			          	Line[index.val] = Qoute.RemoveQoute(Line[index.val]);
			          	
			          	String text = Line[index.val];
			          	
			          	while(index.val<Line.length-1 && (text.endsWith(",") || text.endsWith("(") || Line[index.val+1].trim().startsWith("throws ")||Line[index.val+1].trim().startsWith("extends ")||Line[index.val+1].trim().startsWith("implements "))){
			          		text = text + " " +Qoute.RemoveQoute(Line[index.val+1].trim());
			          		index.val++;
			          	}
			          	
		          		cleanedCodeContent.append(text+"\n");
		          		
		          		Line[index.val] = text;
		          		
		          			if(RegularExpression.IsMethodPrototype(Line[index.val])) {
		          				 System.out.println("START JUMP "+Line[index.val]);
		          				 RegularExpression.JumpMethodContent(Line[index.val],index,Line);
		          				 System.out.println("END JUMP "+Line[index.val]);
		          			
		          			}
		          			
		          			
		          		
		          	
		          
		          	}
			}
		     
			
			return cleanedCodeContent;
		}


}
