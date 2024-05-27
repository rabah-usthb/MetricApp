package application.BackEnd;

import java.net.URLClassLoader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URLClassLoader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL.*;
import java.util.Iterator;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;


import java.util.Iterator;

import java.nio.file.*;
import java.util.LinkedHashSet;
           import java  .util. *;
import java.util.*;
import java.util.Set;
import java.util.Set;

import application.FrontEnd.ImportController;
import application.FrontEnd.MetricController;
import java.net.URL;

public class ImportStatus {
public String ImportName;
public int ImportStatus;
public int ConflictStatus=0;
public static Set<String> ClassNameList = new LinkedHashSet<>();
public int LineNumber;
public int DuplicatStatus=0;
public Set<String>UsedClassList = new LinkedHashSet<>();
ImportStatus(String ImportName,int ImportStatus,int LineNumber){
	this.ImportName=ImportName;
	this.ImportStatus=ImportStatus;
	this.LineNumber = LineNumber;
}

//method to see if package from src
//method to see if from jre
//method fetch src class , jre class
//method to see if conflict double wild card

static String FetchImportFromCode(String Line) {
	return Line.replace(" ", "").replace("import", "").replace(";", "");
}

static boolean IsPackageSrc(String AsterixImport) {
	String FilePath = FetchSrcPackagePath(AsterixImport);
	 Path path = Paths.get(FilePath);
	 return Files.exists(path);
}

static boolean IsImportSrc(String AsterixImport , String ClassName) {
	String FilePath = FetchSrcPackagePath(AsterixImport);	
	 FilePath = FilePath+"\\"+ClassName+".java";
	 Path path = Paths.get(FilePath);
	 return Files.exists(path);
}

static boolean ClassLoad(String AsterixImport , String ClassName) {

	  if(IsImportSrc(AsterixImport,ClassName)) {
          return true;
      } else {
        return  ClassLoadingPackageJre(AsterixImport, ClassName);
      }
}


static boolean ClassLoadingPackageJre(String AsterixImport , String ClassName) {
	
    try {
    	 Class.forName(AsterixImport.replace("*","") + ClassName);
        return true; 
    } catch (ClassNotFoundException e) {
    	 return false;
        
    }
}


static String FetchSrcPackagePath(String AsterixImport) {
	String PackagePath="";
	if(MetricController.PathProject.endsWith("\\")) {
		PackagePath = MetricController.PathProject+AsterixImport.replace(".", "\\").replace("*",""); 
		}
		else {
			PackagePath = MetricController.PathProject+"\\"+AsterixImport.replace(".", "\\").replace("*","");
		}
	return PackagePath;
}

static Set<String> FetchSrcPackageFile(String AsterixImport){
	Set<String>ListFile = new LinkedHashSet<>();
	String PackagePath = FetchSrcPackagePath(AsterixImport);
	File file = new File(PackagePath);
    File[] FilePackage = file.listFiles();
    
    for(File filepkg : FilePackage) {
    	if(filepkg.getName().endsWith(".java")) {
    	ListFile.add(filepkg.getName().replace(".java", ""));
    }
    	}
    System.out.println(ListFile);
    return ListFile;
}

static boolean IsDoubleWildCardConflictImport(String ImportPackageName1,String ImportPackageName2) {
	if(IsPackageSrc(ImportPackageName2)||IsPackageSrc(ImportPackageName1)) {
		Set<String>ListFile = new LinkedHashSet<>();
		if(IsPackageSrc(ImportPackageName1)) {
			ListFile = FetchSrcPackageFile(ImportPackageName1);
		}
		else {
			ListFile = FetchSrcPackageFile(ImportPackageName2);
		}
		for(String ClassName : ListFile) {
			if(ClassLoad(ImportPackageName1, ClassName) && ClassLoad(ImportPackageName2, ClassName)) {
				return true;
			}
		}
		return false;
	}
	else {
	
	
	for(String classname : ClassNameList) {
		if(ClassLoad(ImportPackageName1, classname) && ClassLoad(ImportPackageName2, classname)){
			return true;
		}
	}
	return false;
	}
}

static boolean IsClassImportConflict(String ImportPackageName1,String ImportPackageName2) {
	String ImportClassName1 = FetchImportClassName(ImportPackageName1);
	String ImportClassName2 = FetchImportClassName(ImportPackageName2);
	if(!ImportClassName1.equals("*") && !ImportClassName2.equals("*")&&ImportClassName2.equals(ImportClassName1)) {
		return true;
	}
	else if(ImportClassName1.equals("*") && !ImportClassName2.equals("*")&&ClassLoad(ImportPackageName1, ImportClassName2)) {
		return true;
	}
	else if(!ImportClassName1.equals("*") && ImportClassName2.equals("*")&&ClassLoad(ImportPackageName2, ImportClassName1)) {
		return true;
	}
	else if(ImportClassName1.equals("*") && ImportClassName2.equals("*")&&IsDoubleWildCardConflictImport(ImportPackageName1, ImportPackageName2)) {
		return true;
	}
	
	return false;
}

static String  FetchImportClassName(String ImportName) {
	return ImportName.substring(ImportName.lastIndexOf(".")+1);
}

public static void UpdateConflictFlag(ArrayList<ImportStatus> ImportList) {
	 
	for(int i = 0;i<ImportList.size();i++) {
		String ImportPackageName1 = ImportList.get(i).ImportName;
		
		for(int j = i+1;j<ImportList.size();j++) {
			String ImportPackageName2 = ImportList.get(j).ImportName; 
		//	System.out.println(ImportPackageName1);
			//System.out.println(ImportPackageName2);
			if(!ImportPackageName1.substring(0,ImportPackageName1.lastIndexOf("."))
					.equals(ImportPackageName2.substring(0,ImportPackageName2.lastIndexOf(".")))){
			if(IsClassImportConflict(ImportPackageName1, ImportPackageName2)) {
				ImportList.get(i).ConflictStatus = 1;
				ImportList.get(j).ConflictStatus = 1;
			
		}
					}
	}
}
}


static void IsAll(Set<String> ListImportFromFile , String line) {
	if(RegularExpression.IsMethodPrototype(line)) {
		
		ListImportFromFile.addAll(RegularExpression.extractClassNamesMethod(line));
		 
	}
	else if(RegularExpression.IsNew(line)) {
		//System.out.println(line);
		ListImportFromFile.addAll(RegularExpression.ExtractNewClassNames(line));
	//	System.out.println(ListImportFromFile);
	}
	else if(RegularExpression.IsCatch(line)) {
		//System.out.println(line);
		ListImportFromFile.addAll(RegularExpression.CatchException(line));
		//System.out.println(CatchException(line));
	}
	else if(RegularExpression.IsThrow(line)) {
		ListImportFromFile.addAll(RegularExpression.ThrowException(line));
	}
	else if (RegularExpression.IsVariable(line)) {
		ListImportFromFile.addAll(RegularExpression.ExtractVarClassNames(line));
	//	System.out.println(line);
		//System.out.println(ListImportFromFile);
	}
	else if(RegularExpression.IsAnnotation(line)) {
		ListImportFromFile.add(RegularExpression.FetchAnnotation(line));
	}
	if (RegularExpression.IsStaticCall(line)) {
		ListImportFromFile.addAll(RegularExpression.FetchStaticCall(line));
	}
}



//Method To Update Flags Of ImportStatus(used , not used)
public static ArrayList<ImportStatus> update(File file , ArrayList<ImportStatus> ImportList){
	  	 Set<String>ClassName = new LinkedHashSet<>();
	try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
          String line;
          while ((line = reader.readLine()) != null) {
          line = line.trim();
          line =  Qoute.RemoveQoute(line);
             
              ArrayList<String> ListCode=new ArrayList<String>();
              
              if(!line.isBlank() && !line.isEmpty() && !RegularExpression.IsBracket(line)&& !Comment.IsCommentOnlyCompleted(line) && !RegularExpression.IsImport(line) &&!RegularExpression.IsPackage(line)) {
          	//System.out.println(line);
              	if(Comment.ContainsComment(line)) {
          	//	System.out.println(line);
          		line = Comment.RemoveComment(line);
          	}
          	else {
          		
          		if(Comment.FinishedComment(line)) {
          			if(!Comment.ContainsOpeningComment(line)) {
          				ListCode.add(Comment.CodeOpeningComment(line));
          			}
          			if(!Comment.ContainsClosingComment(line)) {
          				ListCode.add(Comment.CodeClosingComment(line));
          			}
          		}
          		else if (Comment.NotFinishedComment(line)) {
          			Comment.JumpComment(line,ListCode,reader);
          		}
          		if(!ListCode.isEmpty()) {
          			for(String code : ListCode) {
          				IsAll(ClassName,code);
          			}
          		}
          	}
          	
          	if(ListCode.size()==0) {
          		IsAll(ClassName,line);
          	}
              }
          }
	}catch (IOException e) {
              e.printStackTrace(); // Handle any IO exceptions
          }
	
	ClassNameList = ClassName;
         	
	//Set<String>UsedClassList = new LinkedHashSet<>();

	for(ImportStatus Import : ImportList) {
		
		//	UsedClassList.clear();
		
		
          		for(String classname :  ClassName) {
          			
          			if(!Import.ImportName.contains("*")) {
          			if((FetchImportClassName(Import.ImportName)).equals(classname)) {
          			
          				Import.ImportStatus = 1;
          			}
          			}
          			else {
        if (ClassLoad(Import.ImportName,classname)) {
        	Import.UsedClassList.add(classname);
        	Import.ImportStatus = 1;
        }
          			}/*
          			++cmpt;
          			if(cmpt==ClassNameList.size()) {
          				Import.WildCardUsedClassMap.put(Import.ImportName,UsedClassList);
          				for(Map.Entry<String,Set<String>> entry : Import.WildCardUsedClassMap.entrySet()) {
          					System.out.println(entry.getKey());
          					System.out.println(entry.getValue());
          				}
          			}
          			*/
          		}
          	}
                            
              
      
	 return ImportList;
}


//Method To Fetch Import From File
public static ArrayList<ImportStatus> ImportFetch(File file){
	ArrayList<ImportStatus> ImportList = new ArrayList<ImportStatus>();
	String Line;
	int cmpt =1;
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
          					if(RegularExpression.IsImport(code)) {
          	            		ImportList.add(new ImportStatus(FetchImportFromCode(code),0,cmpt));
          	            		
          	            	}
          					else {
          						break;
          					}
          				}
          			}
          		}
          		if(ListCode.isEmpty()) {
          			if(RegularExpression.IsImport(Line)) {
  	            		ImportList.add(new ImportStatus(FetchImportFromCode(Line),0,cmpt));
  	            		
  	            	}
          			else {
          				break;
          			}
          		}
          	}
          	++cmpt;
          }
      } catch (IOException e) {
          e.printStackTrace();
      }
	  
	 for (int i = 0; i < ImportList.size(); i++) {
         ImportStatus importTypeI = ImportList.get(i);
         Iterator<ImportStatus> iterator = ImportList.listIterator(i + 1);
         while (iterator.hasNext()) {
             ImportStatus importTypeJ = iterator.next();
             if (importTypeI.ImportName.equals(importTypeJ.ImportName)) {
                 importTypeI.DuplicatStatus = 1;
                 iterator.remove();
             }
         }
     }
	   
	 
	
	return ImportList;
}

public static String FetchPackageName(String Import) {
	//System.out.println(Import);
	String PKG = Import.replace(" ", "").replace("import", "");
	PKG =PKG.substring(0,PKG.lastIndexOf("."));
//	System.out.println(PKG);
	return PKG ;
}



public static void RemoveUnusedImports(String FilePath) {
	Path path = Paths.get(FilePath);
	
	List<String> lines = new ArrayList<>();
	try {
	 lines =  Files.readAllLines(path);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	 Set<String>ImportUnusedSet = new LinkedHashSet<>();
	 
	 
	for(ImportStatus Import : ImportController.ListImport) {
	if(Import.ImportStatus == 0) {	
		ImportUnusedSet.add(Import.ImportName);
	}
	}
	
	
	Iterator<String>OuterIterator = lines.listIterator();
   while(OuterIterator.hasNext()) {
//	System.out.println(lines.get(i));
	   String ImportI =OuterIterator.next();
	   if(RegularExpression.IsImport(ImportI)) {
		   if(ImportUnusedSet.contains(FetchImportFromCode(ImportI))) {
			   OuterIterator.remove();
		   }
		   
		       	   
	   
	   }   else if(!ImportI.contains(".")&&(!ImportI.isEmpty()&&!ImportI.isBlank())) {
      	 break;
     }
		
	
   }
   for(int i = 0 ; i<lines.size();i++) {
	String ImportI = lines.get(i);
	   if(RegularExpression.IsImport(ImportI)) {   
		   if(ImportUnusedSet.contains(FetchImportFromCode(ImportI))) {
			   Iterator<String>iterator = lines.listIterator(i+1);
			   while(iterator.hasNext()) {
				   String ImportJ = iterator.next();
				   if(RegularExpression.IsImport(ImportJ)) {
					   if(FetchImportClassName(ImportJ).equals(ImportI)) {
						   iterator.remove();
					   }
				   }
		else if(!ImportJ.contains(".")&&(!ImportJ.isEmpty()&&!ImportJ.isBlank())) {
			      	 break;  
				     }
			   }
		   }
	   }
	   else if(!ImportI.contains(".")&&(!ImportI.isEmpty()&&!ImportI.isBlank())) {
	      	 break;  
	      	 }
   }
   try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(FilePath)))) {
       for (String line : lines) {
           writer.println(line);
       }
   } catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
}


public static void RemoveDuplicate(String FilePath) {
	Path path = Paths.get(FilePath);
	
	List<String> lines = new ArrayList<>();
	try {
	 lines =  Files.readAllLines(path);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   for(int i = 0 ; i<lines.size(); i++) {
//	System.out.println(lines.get(i));
	   String ImportI ="";
	   if(RegularExpression.IsImport(lines.get(i))) {
		   ImportI = lines.get(i);
		   Iterator<String>iterator = lines.listIterator(i+1);
	       while(iterator.hasNext()) {
	    	   String ImportJ = iterator.next();
	    	   if(RegularExpression.IsImport(ImportJ)) {
	    	   
	    	  if(FetchImportFromCode(ImportJ).equals(FetchImportFromCode(ImportI))) {
	    		  iterator.remove();
	    	  }
	       }
	    	  else if(!ImportJ.contains(".")&&(!ImportJ.isEmpty()&&!ImportJ.isBlank())) {
	    	      	 break;
	    	     }
	       }
	   }
            	   
	   
	   else if(!lines.get(i).contains(".")&&(!lines.get(i).isEmpty()&&!lines.get(i).isBlank())) {
      	 break;
     }
		
	}
   int j=25;
   for(String line : lines) {
	   if(j==0) {
		   break;
	   }
	   else {
	   System.out.println(line);
	   --j;}
   }
   
   try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(FilePath)))) {
       for (String line : lines) {
           writer.println(line);
       }
   } catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
} 





public static void ReplaceWildCardImport(String filePath, int lineIndex, String replacementContent) throws IOException {
    Path path = Paths.get(filePath);
    String Pkg = FetchPackageName(replacementContent.substring(replacementContent.lastIndexOf("import ")));
    // Read all lines into memory
    var lines =  Files.readAllLines(path);
    
   // System.out.println("Number of Import "+Replaced.length);
    if (lineIndex >= 0 && lineIndex < lines.size()) {
        // Replace the line at the specified index
        lines.set(lineIndex, replacementContent);
        //System.out.println("Pkg Of Replacement "+Pkg);
       
       System.out.println(lineIndex+"\n\n");
       
       
       Iterator<String> iterator = lines.listIterator();
       int i =0;
       while (iterator.hasNext()) {
           String Line = iterator.next();
           if(Line.startsWith("import ")) {
            if( (i<lineIndex||i>lineIndex)&&FetchPackageName(Line).equals(Pkg)) { 	
            	iterator.remove();
              	}
    	     }
        	        	
       else if(!Line.contains(".")&&(!Line.isEmpty()&&!Line.isBlank())) {
        	 break;
       }
        	    ++i;    	
       }
       
        // Write the modified content back to the file
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filePath)))) {
            for (String line : lines) {
                writer.println(line);
            }
        }
    } else {
        throw new IllegalArgumentException("Invalid line index: " + lineIndex);
    }
}


}
