package application.FrontEnd;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import application.BackEnd.AnalyseAll;
import application.BackEnd.Encapsulation;
import application.BackEnd.ExceptionStatus;
import application.BackEnd.ImportStatus;
import application.BackEnd.OOMRCalculator;
import application.BackEnd.SwingComponent;
import application.BackEnd.XMLResult;
import javafx.stage.FileChooser;

public class SaveFileController <T> {
String metricName;
ArrayList<T> metricOutputList = new ArrayList<>();


	
	public SaveFileController(String metricName , ArrayList<T> metricOutputList) {
	 this.metricName = metricName;
	 this.metricOutputList = metricOutputList;
	}
	
	public void saveXML() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Output Of "+ this.metricName +" Metric");

        // Set default file extension
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("XML Files", "*.xlsx")
        );

        // Suggest a default file name
        fileChooser.setInitialFileName(this.metricName+".xlsx");

        File file = fileChooser.showSaveDialog(null);
        
        if (file == null) {
            return;
        }
        
        String filePath = file.getAbsolutePath();
        
        
        if(!filePath.isBlank()) {

       switch (this.metricName) {
	   case "IC":
		   System.out.println(filePath);
        AnalyseAll.AnalyseExcel(filePath,(ArrayList<ImportStatus>) (this.metricOutputList)); 	
		break;
	   case "JEA" :
		   XMLResult.JEA_XML((ArrayList<ExceptionStatus>)(this.metricOutputList) , filePath);
		  break;
	   
	   case "OOMR" :
		   XMLResult.OOMR_XML((OOMRCalculator)(this.metricOutputList).getFirst() , filePath);
		   break;
		  
	   case "ER" :
		   XMLResult.ER_XML((Encapsulation)(this.metricOutputList).getFirst() , filePath);
		 break;
		 
	   case "SM" :
		   XMLResult.SM_XML((SwingComponent)(this.metricOutputList).getFirst() , filePath);
		  break;

	}
    }
    }
}
