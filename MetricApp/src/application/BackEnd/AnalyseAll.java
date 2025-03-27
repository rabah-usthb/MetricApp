package application.BackEnd;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.*; 
import org.apache.poi.xssf.usermodel.XSSFWorkbook; 

public class AnalyseAll {
	
	public static void TraverseProject(Sheet importSheet,Sheet erSheet,int index,ArrayList<Package> packageList) {
		
		for(Package pkg : packageList) {
			for(fileData fileInfo : pkg.FileNameList) {
				if(fileInfo.fileName.endsWith(".java")) {
				   File file = new File(fileInfo.filePath);
				   ArrayList<ImportStatus> ListImport = new ArrayList<ImportStatus>();
				   ListImport = ImportStatus.update(file,(ImportStatus.ImportFetch(file))); 
				   ImportStatus.UpdateConflictFlag(ListImport);
				   Encapsulation er = Encapsulation.EncapsulationFetch(file);
				   writeRowImport(importSheet,ListImport,index);
				   writeRowER(erSheet, er, index);
				   index++;
				}
				
			}
			
			TraverseProject(importSheet,erSheet, index, pkg.SubPackges);
		}
		
	}
	
    public static void AnalyseExcel(String OutputFilePath,ArrayList<Package> Project) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet importSheet = workbook.createSheet("Import Conflict Metric");
        Sheet erSheet = workbook.createSheet("Encapsulation Rate Metric");

        initColumnIC(importSheet);
        initColumnER(erSheet);
        
        TraverseProject(importSheet,erSheet,1,Project);
        
        expandAllICColumn(importSheet, 7);
        expandAllERColumn(erSheet, 7);
     
        
        try (FileOutputStream out = new FileOutputStream(OutputFilePath)) {
            workbook.write(out);
        }
        workbook.close();
    }
    
    
   public static void initColumnER(Sheet sheet) {
	   Row header = sheet.createRow(0); 
       
       header.createCell(0).setCellValue("Class Name");
       header.createCell(1).setCellValue("Long Class Name");
       header.createCell(2).setCellValue("Total");
       header.createCell(3).setCellValue("Public");
       header.createCell(4).setCellValue("None");
       header.createCell(5).setCellValue("Protected");
       header.createCell(6).setCellValue("Private");
       header.createCell(7).setCellValue("ER");
   }
    
   public static void initColumnIC(Sheet sheet) {
	   Row header = sheet.createRow(0); 
       
       header.createCell(0).setCellValue("Class Name");
       header.createCell(1).setCellValue("Long Class Name");
       header.createCell(2).setCellValue("Total");
       header.createCell(3).setCellValue("Used Imports");
       header.createCell(4).setCellValue("NotUsed");
       header.createCell(5).setCellValue("Duplicate Imports");
       header.createCell(6).setCellValue("Conflict Imports");
   }
   
   public static void writeRowER(Sheet sheet,Encapsulation data,int index) {
	
	   Row row  = sheet.createRow(index);
	   
	   row.createCell(0).setCellValue(ImportStatus.className);
       row.createCell(1).setCellValue(ImportStatus.longClassName);
       row.createCell(2).setCellValue(data.Total);
       row.createCell(3).setCellValue(data.CompteurPublic);
       row.createCell(4).setCellValue(data.CompteurNone);
       row.createCell(5).setCellValue(data.CompteurProtected);
       row.createCell(6).setCellValue(data.CompteurPrivate);
       row.createCell(7).setCellValue(data.TauxEncapsulation);
   
   }
    
   public static void expandAllERColumn(Sheet sheet , int lastCol ) {
		  for (int i = 0 ; i<lastCol ; i++)
			  sheet.autoSizeColumn(i);
}
   
  public static void expandAllICColumn(Sheet sheet , int lastCol ) {
	  for (int i = 0 ; i<lastCol ; i++)
		  sheet.autoSizeColumn(i);
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
