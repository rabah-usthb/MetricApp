package application.BackEnd;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.*; 
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import application.FrontEnd.MetricController; 



public class AnalyseAll {

	public static int index = 1;
	public static int expIndex= 1;
	//public static final String IDK = Files.exists(Paths.get(MetricController.PathProject, "test", "java"))? "\\src\\test\\java": "\\src\\test\\";		
	public static void TraverseProject(Sheet sheet,ArrayList<Package> packageList) {
		
		for(Package pkg : packageList) {
			System.out.println(pkg.PackageName+"\n");
			for(fileData fileInfo : pkg.FileNameList) {
				   File file = new File(fileInfo.filePath);
				   ArrayList<ImportStatus> ListImport = new ArrayList<ImportStatus>();
				   ListImport = ImportStatus.update(file,(ImportStatus.ImportFetch(file))); 
				   ImportStatus.UpdateConflictFlag(ListImport);
				   writeRowImport(sheet,ListImport,index);
				   System.out.println(fileInfo.filePath);
			       System.out.println(ImportStatus.className);
			       System.out.println(ImportStatus.longClassName);
			       System.out.println(index);
			//       if(file.getAbsolutePath().contains("\\src\\main\\java\\")|| file.getAbsolutePath().contains("\\src\\java\\") || file.getAbsolutePath().contains(IDK) ) {
			    /*   
			    	try {
						OOMRCalculator oomr = OOMRCalculator.fetchOOMR(file.getAbsolutePath());
					writeRowOOMR(oomrSheet, oomr, expIndex);
					} catch (FileNotFoundException | MalformedURLException | NoClassDefFoundError |ClassNotFoundException e) {
					
						
						
							System.out.println("Exception Found : "+file.getAbsolutePath());
						String path = file.getAbsolutePath();
						String idk= "\\target\\classes\\";
				      	 if(path.contains("\\src\\main\\java\\")) {
				      		path = path.replace("\\src\\main\\java\\", "\\target\\classes\\");
				      	 } else if (path.contains("\\src\\java\\")) {
				      		path = path.replace("\\src\\java\\", "\\target\\classes\\");
				      	 }
				      	 else if(path.contains("\\src\\test\\")) {
				      		path = path.replace("\\src\\test\\", "\\target\\test-classes\\");
				      		idk = "\\target\\test-classes\\";
				      	 }
				      	String PathBinFile = path.replace(".java", ".class");
				      	 System.out.println("Excpetion BIN "+PathBinFile.substring(0,PathBinFile.indexOf(idk)+idk.length()));
				           // Create a custom class loader
				           URLClassLoader classLoader;
				           String longClassName =PathBinFile.substring(PathBinFile.indexOf(idk)+idk.length()).replace("\\", ".").replace(".class", ""); 
				      	    System.out.println("Exception LONG "+longClassName);
				          e.printStackTrace();
				      	  System.exit(0);
					}
			    	*/
			       CleanData clean = new CleanData(file, 0);
			       ArrayList<ExceptionStatus> ListException = new ArrayList<ExceptionStatus>();
			       try {
						ListException = ExceptionStatus.FetchFromCleanCode(clean);
					System.out.println(ListException);
				       } catch (ClassNotFoundException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			       writeRowExp(sheet, ListException, index);
			    
			      // }
			       clean = new CleanData(file,1);
				   Encapsulation er = Encapsulation.EncapsulationFetchClean(clean);
				   writeRowER(sheet, er, index);
				   index++;	
			}
			
			if(!pkg.SubPackges.isEmpty()) {
			TraverseProject(sheet,pkg.SubPackges);
			}
		}
		
	}
	
    public static void AnalyseExcel(String OutputFilePath,ArrayList<Package> Project) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("JAVA Metrics");
       
        initColumn(sheet);
        
        TraverseProject(sheet,Project);
        
        expandAllColumn(sheet, 17);
        
     
        
        try (FileOutputStream out = new FileOutputStream(OutputFilePath)) {
            workbook.write(out);
        }
        workbook.close();
    }
    
   
   
    
   
    
    
   public static void initColumn(Sheet sheet) {
	   Row header = sheet.createRow(0); 
       
       header.createCell(0).setCellValue("Class Name");
       header.createCell(1).setCellValue("Long Class Name");
       
       header.createCell(2).setCellValue("Total Imports");
       header.createCell(3).setCellValue("Used Imports");
       header.createCell(4).setCellValue("NotUsed Imports");
       header.createCell(5).setCellValue("Duplicate Imports");
       header.createCell(6).setCellValue("Conflict Imports");
       
       header.createCell(7).setCellValue("Total Elements");
       header.createCell(8).setCellValue("Public Elements");
       header.createCell(9).setCellValue("None Elements");
       header.createCell(10).setCellValue("Protected Elements");
       header.createCell(11).setCellValue("Private Elements");
       header.createCell(12).setCellValue("ER Elements");
       
       header.createCell(13).setCellValue("Total Exceptions");
       header.createCell(14).setCellValue("User Exceptions");
       header.createCell(15).setCellValue("Default Exceptions");
       header.createCell(16).setCellValue("RunTime Exceptions");
       header.createCell(17).setCellValue("CompileTime Exceptions");
       
   }
   
   public static void writeRowER(Sheet sheet,Encapsulation data,int index) {
	
	   Row row  = sheet.getRow(index);
	   
       row.createCell(7).setCellValue(data.Total);
       row.createCell(8).setCellValue(data.CompteurPublic);
       row.createCell(9).setCellValue(data.CompteurNone);
       row.createCell(10).setCellValue(data.CompteurProtected);
       row.createCell(11).setCellValue(data.CompteurPrivate);
       row.createCell(12).setCellValue(data.TauxEncapsulation);
   
   }
    
   public static void expandAllColumn(Sheet sheet , int lastCol ) {
		  for (int i = 0 ; i<=lastCol ; i++)
			  sheet.autoSizeColumn(i);
}
   
   public static void writeRowExp(Sheet sheet,ArrayList<ExceptionStatus> list,int index) {
	
	   Row row  = sheet.getRow(index);
	   
       row.createCell(13).setCellValue(list.size());
       row.createCell(14).setCellValue(ExceptionStatus.getTotalNumberNotDefaultException(list));
       row.createCell(15).setCellValue(ExceptionStatus.getTotalNumberDefaultException(list));
       row.createCell(16).setCellValue(ExceptionStatus.getTotalNumberRunTimeException(list));
       row.createCell(17).setCellValue(ExceptionStatus.getTotalNumberCompileTimeException(list));
   
   }
   
   public static void writeRowImport(Sheet sheet,ArrayList<ImportStatus> list,int index) {
	   RowDataImport data = new RowDataImport(list);
	   Row row  = sheet.createRow(index);
	   
	   row.createCell(0).setCellValue(ImportStatus.className);
       row.createCell(1).setCellValue(ImportStatus.longClassName);
       row.createCell(2).setCellValue(list.size());
       row.createCell(3).setCellValue(data.nbUsed);
       row.createCell(4).setCellValue(data.nbNotUsed);
       row.createCell(5).setCellValue(data.nbDuplicate);
       row.createCell(6).setCellValue(data.nbConflict);
   
   }
    
   
  
}
