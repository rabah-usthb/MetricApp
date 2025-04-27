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
	//	System.out.println("heyy \n\n\n");
		Encapsulation encapsulation = new Encapsulation(0,0,0,0,0);
		String formattedCode = "";
	  
	  	  String Code;
		try {
			Code = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
			 formattedCode = new Formatter().formatSource(Code);
		} catch (IOException | FormatterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String [] Line = formattedCode.split("\n");
		Index index = new Index();
		
		for( index.val =index.val; index.val<Line.length;index.val++) {
			 //System.out.println("index.val "+index.val+" "+Line[index.val]);
			 
	          	Line[index.val] = Line[index.val].trim();
	          	Line[index.val] = Qoute.RemoveQoute(Line[index.val]);
	          	ArrayList<String> ListCode=new ArrayList<String>();
	          	if(!Line[index.val].isBlank() && !Line[index.val].isEmpty() && !Comment.IsCommentOnlyCompleted(Line[index.val]) && ! RegularExpression.IsPackage(Line[index.val])) {
	          		//System.out.println(Line);
	          		if(Comment.ContainsComment(Line[index.val])) {
	  	            	//System.out.println(line);
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
	          				  Comment.JumpComment(Line[index.val],ListCode,Line,index);
                     	//	 System.out.println("index.val "+index.val+" "+Line[index.val]);
	            		}

	          			if(!ListCode.isEmpty()) {
	          				for(String code : ListCode) {
	          				//	 System.out.println("index.val "+index.val+" "+code);
	          					if(RegularExpression.IsVariable(code) || RegularExpression.IsMethodPrototype(code)) {
	          	            	//	System.out.println(code);
	          					//	System.out.println("MEMBER : "+index.val + "  "+code);
	          						UpdateEncapsulationFlag(encapsulation, code);
	          	            	}
	          					
	          					if(RegularExpression.IsMethodPrototype(code)) {
	    	          				RegularExpression.JumpMethodContent(code,index,Line);
	    	          			}	
	          				}
	          			}
	          		}
	          		
	          //		System.out.println("Text "+Line[index.val]);
	          		if(index.val!=Line.length-1 &&!Line[index.val+1].trim().startsWith("*") && !Line[index.val+1].trim().startsWith("/*") ){
              		//	System.out.println("Current "+Line[index.val]+" NEXT "+Line[index.val+1] +" ISTHROWS "+Line[index.val+1].trim().startsWith("throws "));
              		while((Line[index.val+1].trim().startsWith("throws ")) ||(Line[index.val+1].trim().endsWith(","))  ) {
      					Line[index.val] = Line[index.val] +" "+ Line[index.val+1];
      					 
      					++index.val;
      				}
	          		}
              		
	          		
	          		///	System.out.println(Line[index.val] +" "+RegularExpression.IsVariable(Line[index.val])+" "+RegularExpression.IsMethodPrototype(Line[index.val]) );
	          			if(RegularExpression.IsVariable(Line[index.val]) || RegularExpression.IsMethodPrototype(Line[index.val])) {
	          			//	System.out.println("MEMBER : "+index.val + "  "+Line[index.val]);
	          				UpdateEncapsulationFlag(encapsulation, Line[index.val]);
	  	            	}
	          			if(RegularExpression.IsMethodPrototype(Line[index.val])) {
	          				 
	          				 RegularExpression.JumpMethodContent(Line[index.val],index,Line);
	          				// System.out.println("After : "+index[0] + " text "+Line[index[0]]);
	          			}
	          			
	          		
	          	
	          
	          	}
		}
	     
		CalculTauxEncapsulation(encapsulation);
		return encapsulation;
	}

	
	
	public static Encapsulation EncapsulationFetchClean(CleanData clean){
		//	System.out.println("heyy \n\n\n");
			Encapsulation encapsulation = new Encapsulation(0,0,0,0,0);
			 File file = clean.tmpFile;
			
			  	  
			  	  
			  	 try (BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
			            String Line;
			            while ((Line = reader.readLine()) != null) {
			            	if(!Line.isBlank() && !Line.isEmpty() && !RegularExpression.IsPackage(Line) && !RegularExpression.IsImport(Line)) {
			            		if(RegularExpression.IsVariable(Line) || RegularExpression.IsMethodPrototype(Line)) {
		          						UpdateEncapsulationFlag(encapsulation, Line);
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
        