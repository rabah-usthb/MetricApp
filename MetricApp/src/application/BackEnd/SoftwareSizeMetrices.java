package application.BackEnd;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SoftwareSizeMetrices {
    public ArrayList<String> MethodList=new ArrayList<>();
    public ArrayList<String> InterfaceList = new ArrayList<>();
    public ArrayList<String> ClassList = new ArrayList<>();
    public String Parent = "Object";
    

    public static void FetchData(SoftwareSizeMetrices softwareSizeMetrices , String Line , String fileName) {
    	if(RegularExpression.IsMethodPrototype(Line)) {
				softwareSizeMetrices.MethodList.add(Line.replace("{", ""));
      	}
			else if(RegularExpression.IsClass(Line)) {
				softwareSizeMetrices.ClassList.add(Line.replace("{", ""));
				if(Line.contains("implements ")){
					softwareSizeMetrices.InterfaceList.addAll(RegularExpression.FetchImplements(Line));
				}
				 if(Line.contains("extends ") && Line.substring(0,Line.indexOf("extends ")).replace(" ", "").endsWith(fileName)) {
					 softwareSizeMetrices.Parent = RegularExpression.FetchExtends(Line);
				 }
    }
    }
    /*====================================== Nbr of Methods ======================================== */
        public static SoftwareSizeMetrices FetchSoftware(File file) {
        	SoftwareSizeMetrices softwareSizeMetrices = new SoftwareSizeMetrices();
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
    	          					if(RegularExpression.IsMethodPrototype(code) || RegularExpression.IsClass(code)) {
    	          	                      FetchData(softwareSizeMetrices, code,file.getName().replace(".java", ""));
    	          	            	}
    	          					
    	          				}
    	          			}
    	          		}
    	          		if(ListCode.isEmpty()) {
    	          			if(RegularExpression.IsMethodPrototype(Line) || RegularExpression.IsClass(Line)) {
    	          				FetchData(softwareSizeMetrices, Line,file.getName().replace(".java",""));
    	  	            	}
    	          			
    	          			
    	          		}
    	          	}
    	          }
    	      } catch (IOException e) {
    	          e.printStackTrace();
    	      }
    		
    		return softwareSizeMetrices;
        }
    
    /*========================================= nbr of class ======================================= */

    public class NbrCalssFolder {

        public static int CClassFolder(String folderPath) {
            File folder = new File(folderPath);
            File[] files = folder.listFiles();
            int totalClasses = 0;

            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".java")) {
                        try {
                            int classesInFile = CClassesFile(file.getAbsolutePath());
                            totalClasses += classesInFile;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                System.out.println("Folder is empty or does not exist.");
            }

            return totalClasses;
        }

        public static int CClassesFile(String filePath) throws IOException {
            int classes = 0;
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.startsWith("public class") || line.startsWith("class ")) {
                        classes++;
                    }
                }
            }
            return classes;
        }
    }

    /*====================================== Nbr of child class ==================================== */

    public class NbrChildClassFolder {

        public static int CChildCFolder(String folderPath) {
            File folder = new File(folderPath);
            File[] files = folder.listFiles();
            int totalChildClasses = 0;

            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".java")) {
                        try {
                            int childClassesInFile = CChildCFile(file.getAbsolutePath());
                            totalChildClasses += childClassesInFile;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                System.out.println("Folder is empty or does not exist.");
            }

            return totalChildClasses;
        }

        public static int CChildCFile(String filePath) throws IOException {
            int childClasses = 0;
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.contains("extends")) {
                        childClasses++;
                    }
                }
            }
            return childClasses;
        }
    }

    /*===================================== Nbr of Interface ======================================= */

    public class NbrInterfaceFolder {

        public static int CIFolder(String folderPath) {
            File folder = new File(folderPath);
            File[] files = folder.listFiles();
            int totalInterfaces = 0;

            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".java")) {
                        try {
                            int interfacesInFile = CIFile(file.getAbsolutePath());
                            totalInterfaces += interfacesInFile;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                System.out.println("Folder is empty or does not exist.");
            }

            return totalInterfaces;
        }

        public static int CIFile(String filePath) throws IOException {
            int interfaces = 0;
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.startsWith("public interface") || line.startsWith("interface")) {
                        interfaces++;
                    }
                }
            }
            return interfaces;
        }
    }

    /*======================================== Nbr of Abstract ===================================== */

    public class NbrAbstractClassFolder {

        public static int CACFolder(String folderPath) {
            File folder = new File(folderPath);
            File[] files = folder.listFiles();
            int totalAbstractClasses = 0;

            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".java")) {
                        try {
                            int abstractClassesInFile = CACFile(file.getAbsolutePath());
                            totalAbstractClasses += abstractClassesInFile;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                System.out.println("Folder is empty or does not exist.");
            }

            return totalAbstractClasses;
        }

        public static int CACFile(String filePath) throws IOException {
            int abstractClasses = 0;
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.startsWith("public abstract class") || line.startsWith("abstract class")) {
                        abstractClasses++;
                    }
                }
            }
            return abstractClasses;
        }
    }


}
