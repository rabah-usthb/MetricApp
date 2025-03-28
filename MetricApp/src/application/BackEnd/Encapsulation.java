package application.BackEnd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;
        
public class Encapsulation {
	int Total;
	int CompteurNone;
	int CompteurPublic;
	int CompteurPrivate;
	int CompteurProtected;
	double TauxEncapsulation;
	
	
	public Encapsulation(int Total  ,int CompteurNone,int CompteurPublic , int CompteurPrivate,int CompteurProtected){
		this.Total = Total;
		this.CompteurNone =CompteurNone;
		this.CompteurPublic = CompteurPublic;
		this.CompteurPrivate = CompteurPrivate;
		this.CompteurProtected = CompteurProtected;
		TauxEncapsulation = 0;
	}
	
	public int GetNone() {
		return this.CompteurNone;
	}
	
	public int GetTotal() {
		return this.Total;
	}
	
	public Double GetTaux() {
		return this.TauxEncapsulation;
	}
	
	public int GetPublic() {
		return this.CompteurPublic;
	}
	
	public int GetPrivate() {
		return this.CompteurPrivate;
	}
	
	public int GetProtected() {
		return this.CompteurProtected;
	}
	
	
	public static void CalculTauxEncapsulation(Encapsulation encapsulation) {
	 encapsulation.TauxEncapsulation  = (double) (encapsulation.CompteurPrivate  + encapsulation.CompteurProtected )*100 / encapsulation.Total;
	}
	
	public static void UpdateEncapsulationFlag(Encapsulation encapsulation , String Line) {
		if(Line.startsWith("public")) {
			encapsulation.CompteurPublic++;
		}
		else if(Line.startsWith("private")) {
			encapsulation.CompteurPrivate++;
		}
		else if(Line.startsWith("protected")) {
			encapsulation.CompteurProtected++;
		}
		else {
			encapsulation.CompteurNone++;
		}
		
		encapsulation.Total++;
	}
	
	public static Encapsulation EncapsulationFetch(File file){
		System.out.println("heyy \n\n\n");
		Encapsulation encapsulation = new Encapsulation(0,0,0,0,0);
		String formattedCode = "";
	  	 Integer[] index = new Integer[1];
	  	 index[0] = 0;
	  	  String Code;
		try {
			Code = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
			 formattedCode = new Formatter().formatSource(Code);
		} catch (IOException | FormatterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String [] Line = formattedCode.split("\n");
		
		for(int i = 0 ; i<Line.length;i++) {
	          	Line[i] = Line[i].trim();
	          	Line[i] = Qoute.RemoveQoute(Line[i]);
	          	ArrayList<String> ListCode=new ArrayList<String>();
	          	if(!Line[i].isBlank() && !Line[i].isEmpty() && !Comment.IsCommentOnlyCompleted(Line[i]) && ! RegularExpression.IsPackage(Line[i])) {
	          		//System.out.println(Line);
	          		if(Comment.ContainsComment(Line[i])) {
	  	            	//System.out.println(line);
	  	            		Line[i] = Comment.RemoveComment(Line[i]);
	  	            	}
	          		else {
	          			if(Comment.FinishedComment(Line[i])) {
		            			if(!Comment.OpeningMultiCommentOnly(Line[i])) {
		            				ListCode.add(Comment.CodeOpeningComment(Line[i]));
		            			}
		            			if(!Comment.ClosingMultiCommentOnly(Line[i])) {
		            				ListCode.add(Comment.CodeClosingComment(Line[i]));
		            			}
		            		}
	          			else if (Comment.NotFinishedComment(Line[i])) {
	          				Line[i] = Comment.JumpComment(Line[i],ListCode,Line,index).trim();
	          				Line[i] = Qoute.RemoveQoute(Line[i]);
		            		}

	          			if(!ListCode.isEmpty()) {
	          				for(String code : ListCode) {
	          					if(RegularExpression.IsVariable(code) || RegularExpression.IsMethodPrototype(code)) {
	          	            		System.out.println(code);
	          						UpdateEncapsulationFlag(encapsulation, code);
	          	            	}
	          					
	          					if(RegularExpression.IsMethodPrototype(code)) {
	    	          				RegularExpression.JumpMethodContent(code,index,Line);
	    	          			}	
	          				}
	          			}
	          		}
	          		if(ListCode.isEmpty()) {
	          			System.out.println(Line[i] +" "+RegularExpression.IsVariable(Line[i])+" "+RegularExpression.IsMethodPrototype(Line[i]) );
	          			if(RegularExpression.IsVariable(Line[i]) || RegularExpression.IsMethodPrototype(Line[i])) {
	          				UpdateEncapsulationFlag(encapsulation, Line[i]);
	  	            	}
	          			if(RegularExpression.IsMethodPrototype(Line[i])) {
	          				 System.out.println("before : "+index[0]);
	          				 RegularExpression.JumpMethodContent(Line[i],index,Line);
	          				 System.out.println("After : "+index[0]);
	          			}
	          			
	          		}
	          	}
	          	 i = index[0];
                 ++index[0];
	          }
	     
		CalculTauxEncapsulation(encapsulation);
		return encapsulation;
	}

	

    
}
        