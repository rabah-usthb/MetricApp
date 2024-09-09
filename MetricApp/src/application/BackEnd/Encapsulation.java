package application.BackEnd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
        
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
		Encapsulation encapsulation = new Encapsulation(0,0,0,0,0);
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
	          					if(RegularExpression.IsVariable(code) || RegularExpression.IsMethodPrototype(code)) {
	          	            	//	System.out.println(code);
	          						UpdateEncapsulationFlag(encapsulation, code);
	          	            	}
	          					
	          					if(RegularExpression.IsMethodPrototype(code)) {
	    	          				RegularExpression.JumpMethodContent(code,reader);
	    	          			}	
	          				}
	          			}
	          		}
	          		if(ListCode.isEmpty()) {
	          			if(RegularExpression.IsVariable(Line) || RegularExpression.IsMethodPrototype(Line)) {
	  	            	    //System.out.println(Line);
	          				UpdateEncapsulationFlag(encapsulation, Line);
	  	            	}
	          			if(RegularExpression.IsMethodPrototype(Line)) {
	          				RegularExpression.JumpMethodContent(Line,reader);
	          			}
	          			
	          		}
	          	}
	          }
	      } catch (IOException e) {
	          e.printStackTrace();
	      }
		CalculTauxEncapsulation(encapsulation);
		return encapsulation;
	}

	

    
}
        