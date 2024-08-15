package application.BackEnd;


import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularExpression {
  
   static String CurlyBraces="\\s*(\\{|\\{\\s*\\})?\\s*";
   static String PatternAcessModifiers="(private\\s+|protected\\s+|public\\s+)?";
   static String NonAcessModifierSimple = "(static\\s+|final\\s+|abstract\\s+)?";
   static String ModifierSimple = "("+PatternAcessModifiers+")("+NonAcessModifierSimple+")|("+NonAcessModifierSimple+")("+PatternAcessModifiers+")";
   static String ModifierComplex = "("+PatternAcessModifiers+")final\\s+static\\s+|("+PatternAcessModifiers+")static\\s+final\\s+|final\\s+("+PatternAcessModifiers+")static\\s+|static\\s+("+PatternAcessModifiers+")final\\s+|static\\s+final\\s+("+PatternAcessModifiers+")|final\\s+static\\s+("+PatternAcessModifiers+")";
   static String MethodModifierPattern = "("+ModifierSimple+")|("+ModifierComplex+")";
   static String ThrowsPattern = "(\\s*throws\\s+\\w+\\s*(\\s*\\,\\s*\\w+\\s*)*)?"; // Making the throws clause optional
   static String Bracket = "(\\[\\s*\\]){1,2}";
   static String ArrayDeclarationPattern = "\\w+\\s+\\w+\\s*(" + Bracket + ")|\\w+\\s*(" + Bracket + ")\\s*\\w+\\s*";
   static String ArrayTypePattern="\\w+\\s*(" + Bracket + ")\\s*";
 
  static String NormalPattern = "\\w+\\s+\\w+";
  static String WrapperClass="\\s*\\w+\\s*";


  static String WildCardGen = "\\s*\\?(extends\\s+\\w+|super\\s+\\w+)?\\s*"; 

  static String SimpleInside="\\s*"+WrapperClass+"\\s*|\\s*("+ArrayTypePattern+")\\s*|\\s*("+WildCardGen+")\\s*";
  static String InsideCollection = "("+SimpleInside+")|\\s*\\w+<\\s*("+SimpleInside+")\\s*>|\\s*\\w+\\s*<\\s*("+SimpleInside+")\\s*,\\s*("+SimpleInside+")\\s*>\\s*";
	 
  static String SetListPattern ="\\w+\\s*<\\s*("+InsideCollection+")\\s*>\\s*";
  static String MapPattern="\\w+\\s*<\\s*("+InsideCollection+")\\s*,\\s*("+InsideCollection+")\\s*>\\s*";
  static String CollectionPattern ="("+MapPattern+")|("+SetListPattern+")";
  static String ReturnType = "(\\s*\\w+\\s+)|("+CollectionPattern+")|("+ArrayTypePattern+")";
  static String Paramter="\\s*"+NormalPattern+"\\s*|\\s*("+ArrayDeclarationPattern+")\\s*|\\s*("+CollectionPattern+")\\w+\\s*|\\s*("+MapPattern+")\\w+\\s*";
  static String Arg = "\\s*\\(\\s*(("+Paramter+")"+"(,\\s*("+Paramter+"))*)?\\s*\\)\\s*";
  static String StaticModifier="(static\\s+)?";
  
  static String SignPattern="(\\+\\s*|\\-\\s*)?";
  
  static String FloatPattern="\\s*"+SignPattern+"\\d+\\.\\d+(f)?\\s*";
  static String IntPattern="\\s*"+SignPattern+"\\d+\\s*";
  static String NumberPattern = "("+FloatPattern+")|("+IntPattern+")";
  static String Char="[^\"\\n]";
  
 
  static String VarName="\\s*\\w+\\s*";
  
  static String SingleCatch="\\s*\\w+\\s+\\w+\\s*";
  static String MultipleCatch="\\s*\\w+\\s*(\\s*\\|\\s*\\w+\\s*)*\\s*\\|\\s*\\w+\\s+\\w+\\s*";
  static String InsideCatch=SingleCatch+"|"+MultipleCatch;
  static String OptionalClosingCurlyBraces="(\\s*\\}\\s*)?";
    
  
  //static String StaticModifier = "(static\textbackslash s+)?";
  static String FinalModifier = "(final\\s+)?";
  static String VarModifer = (PatternAcessModifiers)+(FinalModifier)+(StaticModifier)+"|"+(PatternAcessModifiers)+(StaticModifier)+(FinalModifier)+"|"+(StaticModifier)+(PatternAcessModifiers)+(FinalModifier)+"|"+(StaticModifier)+(FinalModifier)+(PatternAcessModifiers)+"|"+(FinalModifier)+(PatternAcessModifiers)+(StaticModifier)+"|"+(FinalModifier)+(StaticModifier)+(PatternAcessModifiers);
	 
  static String NonAcessModifierClass = "(abstract\\s+|final\\s+)?";
  static String ModifierClass = "("+PatternAcessModifiers+")("+NonAcessModifierClass+")|("+NonAcessModifierClass+")("+PatternAcessModifiers+")";
  static String ExtendsPattern = "(\\s+extends\\s+\\w+(\\s*<\\s*\\w+\\s*>\\s*)?)?";
  static String ImplementsPattern = "(\\s+implements\\s+\\w+\\s*(\\s*,\\s*\\w+\\s*)*)?";
	
  static String MultipleBoundPattern = "(\\s+extends\\s+\\w+(\\s*<\\s*\\w+\\s*>\\s*)?(\\s+&\\s+\\w+(\\s*<\\s*\\w+\\s*>)?)*\\s*)?";
  static String TypeParameterGen = "(\\s*<\\s*\\w+("+MultipleBoundPattern+")\\s*(,\\s*\\w+("+MultipleBoundPattern+")\\s*)*\\s*>\\s*)?";
  static String ClassCall = "(\\w+\\.)+\\w+";
  static String ClassVariable = "\\w+\\.\\w+";
  static String SimpleArgMethodCall = "("+ClassVariable+")|("+NumberPattern+")|\\w+";
  static String SimpleMethodCall = "\\s*("+ClassCall+")\\((("+SimpleArgMethodCall+")(\\s*,\\s*("+SimpleArgMethodCall+"))*)?\\s*\\)\\s*";
  static String Inside = "("+SimpleArgMethodCall+")|("+ClassCall+")\\((("+SimpleMethodCall+")(\\s*,\\s*("+SimpleMethodCall+"))*)?\\s*\\)\\s*|("+SimpleMethodCall+")";
  static String MethodCall = "\\s*("+ClassCall+")\\((("+Inside+")(\\s*,\\s*("+Inside+"))*)?\\s*\\)\\s*";
	      	
  static String StringConcatElement = "("+ClassVariable+")|("+MethodCall+")|\\w+|\"("+Char+")\"|("+NumberPattern+")";
  static String StringConcat = "("+StringConcatElement+")(\\+("+StringConcatElement+"))*";
  static String LiteralStringPattern = "(("+StringConcat+")\\+)?\"(("+Char+")|\"\\+("+StringConcat+")\\+\")\"(\\+("+StringConcat+"))?";
  static String SimpleArgMethodcall = "("+ClassVariable+")|("+NumberPattern+")|\\w+|("+LiteralStringPattern+")";
  static String SimpleMethodcall = "\\s*("+ClassCall+")\\((("+SimpleArgMethodcall+")(\\s*,\\s*("+SimpleArgMethodcall+"))*)?\\s*\\)\\s*";
  static String inside = "("+SimpleArgMethodcall+")|("+ClassCall+")\\((("+SimpleMethodcall+")(\\s*,\\s*("+SimpleMethodcall+"))*)?\\s*\\)\\s*|("+SimpleMethodcall+")";
  static String Methodcall = "\\s*("+ClassCall+")\\((("+Inside+")(\\s*,\\s*("+Inside+"))*)?\\s*\\)\\s*";
	
  static void AddCurlyBraces(String Line , LinkedList<String> listopening , LinkedList<String> listclosing) {
		
			 if(Line.contains("{")) {
				listopening.add("{");
			 }
			 if(Line.contains("}")) {
				 listclosing.add("}");
			 }
		
		
  }
  
  
  static String fetchClassName(String line) {
	  String classRegex =".*class\\s+(\\w+).*";
	  Pattern PatterneVar = Pattern.compile(classRegex);
	  Matcher matcher = PatterneVar.matcher(line);
	  while(matcher.find()) {
		return matcher.group(1);  
	  }
	  return "";
		
  }
  static void JumpMethodContent(String Line,BufferedReader reader) {
		LinkedList<String>listopening = new LinkedList<>();
		LinkedList<String>listclosing = new LinkedList<>();
		AddCurlyBraces(Line, listopening,listclosing);
	    try {
			while ( listclosing.size()!=listopening.size() &&  (Line = reader.readLine()) != null) { 
	        Line = Line.trim();
			Line = Qoute.RemoveQoute(Line);
			ArrayList<String> ListCode=new ArrayList<String>();
          	if(!Line.isBlank() && !Line.isEmpty() && !Comment.IsCommentOnlyCompleted(Line)) {
          		if(Comment.ContainsComment(Line)) {
  	              Line = Comment.RemoveComment(Line);
  	            	}
          		else {
          			if(Comment.FinishedComment(Line)) {
	            			if(!Comment.ContainsOpeningComment(Line)) {
	            				ListCode.add(Comment.CodeOpeningComment(Line));
	            			}
	            			if(!Comment.ContainsClosingComment(Line)) {
	            				ListCode.add(Comment.CodeClosingComment(Line));
	            			}
	            		}
          			else if (Comment.NotFinishedComment(Line)) {
	            			Comment.JumpComment(Line,ListCode,reader);
	            		}

          			if(!ListCode.isEmpty()) {
          				for(String code : ListCode) {
          					AddCurlyBraces(code, listopening,listclosing);
          				      					
          				}
          			}
          		}
          		if(ListCode.isEmpty()) {
          			
          			AddCurlyBraces(Line, listopening,listclosing);
          		   
          		}
          	}
		
          	
			}
		}
		catch(IOException e) {
			
		}
	   
		}
	
	
  static LinkedList<Henderson> fetchClassesDataHenderson(String Line,BufferedReader reader) {
	  LinkedList<Henderson> listHenderson = new LinkedList<>();
		String className = fetchClassName(Line);
	    int numberAttribut = 0;
	    int numberMethod = 0;
		System.out.println("class : "+Line);
	    try {
			while ((Line = reader.readLine()) != null) { 
				
				System.out.println("\n\n"+Line);
				System.out.println(className);
				System.out.println(numberAttribut);
				System.out.println(numberMethod);
		
			Line = Line.trim();
			Line = Qoute.RemoveQoute(Line);
			ArrayList<String> ListCode=new ArrayList<String>();
        	if(!Line.isBlank() && !Line.isEmpty() && !Comment.IsCommentOnlyCompleted(Line)) {
        		if(Comment.ContainsComment(Line)) {
	              Line = Comment.RemoveComment(Line);
	            	}
        		else {
        			if(Comment.FinishedComment(Line)) {
	            			if(!Comment.ContainsOpeningComment(Line)) {
	            				ListCode.add(Comment.CodeOpeningComment(Line));
	            			}
	            			if(!Comment.ContainsClosingComment(Line)) {
	            				ListCode.add(Comment.CodeClosingComment(Line));
	            			}
	            		}
        			else if (Comment.NotFinishedComment(Line)) {
	            			Comment.JumpComment(Line,ListCode,reader);
	            		}

        			if(!ListCode.isEmpty()) {
        				for(String code : ListCode) {
        				  if(IsClass(code)) {
        			    		Henderson henderson = new Henderson(className, numberAttribut, numberMethod);
        		                numberAttribut = 0;
        		                numberMethod = 0;
        		                listHenderson.add(henderson);        
        		    
        					  className = fetchClassName(code);
        				  }
        					else if(IsMethodPrototype(code)) {
        						++numberMethod;
        						JumpMethodContent(Line, reader);
        					}
        					else if (IsVariable(code)) {
        						++numberAttribut;
        					}
        				      					
        				}
        			}
        		}
        		if(ListCode.isEmpty()) {
        			if(IsClass(Line)) {
        	    		Henderson henderson = new Henderson(className, numberAttribut, numberMethod);
		                numberAttribut = 0;
		                numberMethod = 0;
		                listHenderson.add(henderson);        
		    
  					  className = fetchClassName(Line);
  				  }
        			if(IsMethodPrototype(Line)) {
						++numberMethod;
						JumpMethodContent(Line, reader);
					}
					else if (IsVariable(Line)) {
						++numberAttribut;
					}
        		   
        		}
        	}
		
        	
			}
		}
		catch(IOException e) {
			
		}
	    Henderson henderson = new Henderson(className, numberAttribut, numberMethod);
        numberAttribut = 0;
        numberMethod = 0;
        listHenderson.add(henderson);        

		
	return listHenderson;
		}
	   
		
	
  
  
  //Method to Know If Line Is Bracket Only Line
	static boolean IsBracket(String Line) {
		String line = Line;
		line = line.replace(" ", "");
		return line.equals("{") || line.equals("}");
	}
	
		
	
	
	//Method To Know If Line Is Import
	static boolean IsImport(String Line) {
		String ImportPattern="\\s*import\\s+("+StaticModifier+")\\w+(\\s*\\.\\s*(\\*|\\w+))+\\s*;\\s*";
		return Line.matches(ImportPattern);
	}
	
	public static boolean IsPackage(String Line) {
		String PackagePattern = "\\s*package\\s+\\w+(\\s*\\.\\s*\\w+)*\\s*;\\s*";
		
		return Line.matches(PackagePattern);
	}
	
	
	public static ArrayList<String> FetchImplements(String Line){
		ArrayList<String> ImplementName = new ArrayList<>();
		Pattern pattern = Pattern.compile("implements\\s+(\\w+)|(?:\\s*\\,\\s*(\\w+)\\s*)");	
	    Matcher matcher = pattern.matcher(Line);
	    while (matcher.find()) {
	   
	     
	        String className = matcher.group(1);
	        if(className==null) {
	        	className = matcher.group(2);
	        }
	        ImplementName.add(className);
	    }
	    return ImplementName;
	}
	
	public static String FetchExtends(String Line) {
		Pattern pattern = Pattern.compile("extends\\s+(\\w+)");
	    Matcher matcher = pattern.matcher(Line);
	    while (matcher.find()) {
	        String className = matcher.group(1);
	        return className;
	    }
	    return "";
	}
	
	
	public static boolean IsClass(String Line) {
	 String ClassPattern = "\\s*("+ModifierClass+")class\\s+\\w+("+TypeParameterGen+")("+ExtendsPattern+")("+ImplementsPattern+")\\s*(\\{)?\\s*";
		return Line.matches(ClassPattern);
	}
	
	public static boolean IsFieldEmpty(String value) {
		return value==null||value.isBlank();
	}
	public static boolean IsGmail(String mail) {
		  mail = mail.replace(" ", "");
		   Pattern GMAIL_PATTERN = Pattern.compile("^[\\w.+%\\-]+@gmail\\.com$");
		    Pattern ILLEGAL_PATTERN = Pattern.compile("[._%+\\-]{2,}");

	      boolean isValidGmail = GMAIL_PATTERN.matcher(mail).matches();
         boolean FoundIllegalPattern = ILLEGAL_PATTERN.matcher(mail).find();
	       return isValidGmail && !FoundIllegalPattern;
	    }
	
	static boolean IsAnnotation(String Line) {
		String AnnotationPatten="\\s*@\\s*(?!(Overload|Override))\\w+\\s*";	 
		return Line.matches(AnnotationPatten);	
		}
	
	static String FetchAnnotation(String Line) {
		Line = Line.replace(" ", "");
		return Line.substring(Line.indexOf("@")+1);
		}

	 static boolean IsStaticCall(String Line) {
		 String StaticCallPetten =".+(("+Methodcall+")|("+ClassCall+")).+";
		 return Line.matches(StaticCallPetten);
	 }
	 
	 static ArrayList<String> FetchStaticCall(String Line ) {
		 ArrayList<String>ClassList = new ArrayList<>();
		 Pattern pattern = Pattern.compile("(\\w+)\\.");
		    Matcher matcher = pattern.matcher(Line);
		    while (matcher.find()) {
		        String className = matcher.group(1);
		        ClassList.add(className);
		    }
		    return ClassList;
	 }
	 

	    //Method To Know If Line Is New Line Instantiation
	static boolean IsNew(String Line) {
		String trimmedLine = Line.trim();
		String NewPattern = ".+(\\(|\\=)\\s*new\\s+.+";

	    return trimmedLine.matches(NewPattern);
	}
	
 //Method To Extract ClassNames From NewLine	
	static ArrayList<String> ExtractNewClassNames(String NewLine) {
		ArrayList<String> classNames = new ArrayList<>();
	    Pattern pattern = Pattern.compile("new\\s+(\\w+)|(?:<|,)\\s*(\\w+)");
	    Matcher matcher = pattern.matcher(NewLine);
	    while (matcher.find()) {
	        String className = matcher.group(1);
	        if (className == null) { // If the first capturing group didn't match
	            className = matcher.group(2); // Use the second capturing group
	        }
	        classNames.add(className);
	    }
	    return classNames;
	}

	static boolean IsConstructor(String line) {

	    
	    String ConstructorPattern = "("+PatternAcessModifiers+")("+TypeParameterGen+")\\w+\\s*("+Arg+")\\s*("+ ThrowsPattern+")"+"("+CurlyBraces+")";
	 
	    
	    return  line.matches(ConstructorPattern);	
}
		
	 static boolean IsMethod(String line) {
			String MethodPattern = "("+MethodModifierPattern+")("+TypeParameterGen+")("+ReturnType+")\\w+\\s*("+Arg+")\\s*("+ThrowsPattern+")(("+CurlyBraces+")|\\s*;\\s*)";
		    return line.matches(MethodPattern); 
		}
	
//Method to know If Line Is A Method Prototype	
	 static boolean IsMethodPrototype(String Line) {
		    return IsConstructor(Line) || IsMethod(Line);
	 }
	 

		//Method To Extract Class Names From Method Prototype Line
		static ArrayList<String> FetchMethodThrowable(String line){
			line = line.substring(line.lastIndexOf(")")+1);
			
			ArrayList<String> classNames = new ArrayList<String>();
			Pattern ThrowsPattern = Pattern.compile( "\\s*throws\\s+(\\w+)\\s*|(?:\\s*\\,\\s*(\\w+)\\s*)");
		    Matcher matcher = ThrowsPattern.matcher(line);
	        while (matcher.find()) {
	        	
	        	String className = matcher.group(1);
		        if (className == null) { // If the first capturing group didn't match
		            className = matcher.group(2); // Use the second capturing group
		        }
		        classNames.add(className);	            
	           
	            
	        }    
	        return classNames;
		}
		
    static ArrayList<String> FetchMethodArgumentType(String line){
			ArrayList<String> classNames= new ArrayList<String>();  
			line = line.substring(line.indexOf("(")+1,line.indexOf(")")+1); 
		        Pattern pattern = Pattern.compile("(?<=<\\s*)\\b\\w+\\b|\\b\\w+\\b(?!\\s*,)(?!\\s*\\))"); // Regular expression to match class names

		        Matcher matcher = pattern.matcher(line);
		        while (matcher.find()) {
		            String className = matcher.group();
		            classNames.add(className);
		        }    
		    return classNames;
		}
		
		static String FetchMethodReturnType(String line) {
			if(!RegularExpression.IsConstructor(line)) {
			 String PattrneAcessModfiers="(?:private\\s+|protected\\s+|public\\s+)?";
			 String PatterneNonAcessModifier="(?:static\\s+final\\s+|static\\s+|final\\s+|abstract\\s+)?";
			 Pattern MethodPattern =  Pattern.compile( PattrneAcessModfiers + PatterneNonAcessModifier + "(?!else)(\\w+)\\s*");
			 Matcher matcher = MethodPattern.matcher(line);
			 while(matcher.find())
			 {
				 return matcher.group(1);
			 }
			}
			
			 return null;
			
		}
		 static ArrayList<String> extractClassNamesMethod(String line) { 
			 ArrayList<String> classNames = new ArrayList<>();
			 classNames.addAll(FetchMethodArgumentType(line));
			 classNames.addAll(FetchMethodThrowable(line));
			 if(FetchMethodReturnType(line)!=null) {
			 classNames.add(FetchMethodReturnType(line));
			 }
			 return classNames;
			 }

			//Method To Know If Line Is Catch
			static boolean IsCatch(String line) {
				String CatchPattern = "\\s*"+OptionalClosingCurlyBraces+"catch\\s*\\(("+InsideCatch+")\\)\\s*("+RegularExpression.CurlyBraces+")\\s*";
				   
				return line.matches(CatchPattern);
			}

			//Method To Fetch Exception From Catch
			static ArrayList<String> CatchException(String line) {
			    ArrayList<String>classNames = new ArrayList<String>();
				Pattern pattern = Pattern.compile("\\(\\s*(\\w+)|\\|\\s*(\\w+)");
			    Matcher matcher = pattern.matcher(line);
			    while (matcher.find()) {
			        String className = matcher.group(1);
			        if (className == null) { // If the first capturing group didn't match
			            className = matcher.group(2); // Use the second capturing group
			        }
			        classNames.add(className);
			    }
			    
				return classNames;
			}
			//Method To Know If Line Is Throw
	static boolean IsThrow(String line) {
		String ThrowPattern="\\s*throw\\s+new\\s+\\w+\\s*\\(\\s*("+inside+")\\s*\\)\\s*;\\s*";
			return line.matches(ThrowPattern);
		}

	//Method To Fetch Exception From Throw	
	static ArrayList<String>ThrowException(String line){
		ArrayList<String> classNames = new ArrayList<String>();
		Pattern ThrowsPattern = Pattern.compile("(?:\\s*|\\s+)new\\s+(\\w+)\\s*\\(");
	    Matcher matcher = ThrowsPattern.matcher(line);
        while (matcher.find()) {
        	
        	String className = matcher.group(1);
	        
	        classNames.add(className);	            
            
        }    
        return classNames;	
	}
	//Detect If Line Is Variable
	   static boolean IsVariable(String line) {	    
		    String VariablePattern = "\\s*("+VarModifer+")((?!return\\s+)\\w+\\s+\\w+|("+ArrayDeclarationPattern+")|("+CollectionPattern+")\\w+)\\s*(=\\s*.+)?\\s*;\\s*";
		    return line.matches(VariablePattern);
		}
		
		
		//Fetch Class Name From Var Line
		static ArrayList<String> ExtractVarClassNames(String VarLine) {
			ArrayList<String> classNames = new ArrayList<>();
			String PattrneAcessModfiers="(?:private\\s+|protected\\s+|public\\s+)?";
			String PattrneStatic="(?:static\\s+)?";
			String PatterneFinal="(?:final\\s+)?";
			Pattern PatterneVar = Pattern.compile(PattrneAcessModfiers+PattrneStatic+PatterneFinal+"\\s*(\\w+)\\s*(?=[\\[<>])|(?<=[<])\\s*(\\w+)|"+PattrneAcessModfiers+PattrneStatic+PatterneFinal+"(\\w+)\\s+\\w+|(\\w+)\\.");
			Matcher matcher1 = PatterneVar.matcher(VarLine);
			
		    while (matcher1.find()) {
		    	 String className = matcher1.group(1);
			       if(className==null) {
			    	   className = matcher1.group(2);
			    	   if(className==null) {
			    		   className = matcher1.group(3);
			    	   }
			    	   if(className==null) {
			    		   className=matcher1.group(4);
			    	   }
			    	   
			    	   }

		        classNames.add(className);
		    }
		    return classNames;
		}

	
}
