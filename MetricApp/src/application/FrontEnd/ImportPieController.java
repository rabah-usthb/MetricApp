package application.FrontEnd;

import java.io.File;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.BackEnd.ImportStatus;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class ImportPieController{

	
@FXML
private PieChart ImportPie;
@FXML
private Label Description;

	
	public void initialize() {
		int TotalImportNumber = getTotalImportNumber();
		System.out.println(TotalImportNumber);
		double UsedRatio = getImportUsedRatio(ImportController.ListImport,TotalImportNumber);
		double NotUsedRatio = getImportNotUsedRatio(ImportController.ListImport,TotalImportNumber);
		double ConflictRatio = getImportConflictRation(ImportController.ListImport,TotalImportNumber);
		double DuplicateRatio = getImportDuplicateRation(ImportController.ListImport,TotalImportNumber);
		ObservableList<PieChart.Data> ImportPieData = FXCollections.observableArrayList(
		            new PieChart.Data("Used Imports ", UsedRatio),
		            new PieChart.Data("NotUsed Imports ", NotUsedRatio),
		            new PieChart.Data("Conflict Imports ", ConflictRatio),
		            new PieChart.Data("Duplicate Imports ", DuplicateRatio)
		        );	
		
        ImportPie.setTitle("Import Usage Ratio");
         
        ImportPieData.forEach(data ->
                data.nameProperty().bind(Bindings.concat(
                		data.getName(),data.getPieValue(),"%")
                		)
               
        		);
        
        ImportPie.getData().addAll(ImportPieData);
        File file = new File(MetricController.FileSelectedPath);
        Description.setText("The File "+file.getName()+" Has In Total "+TotalImportNumber+" Imports , "+UsedRatio+"% are used , "+NotUsedRatio+"% are never used , "+DuplicateRatio+"% are Duplicate , and finally "+ConflictRatio+"% are in conflict.");
    }
	
	
	
	private double getImportUsedRatio(ArrayList<ImportStatus>ImportList , int size) { 
		int NbImportUsed = 0;
		for(ImportStatus Import : ImportList) {
			if(Import.ImportStatus == 1) {
				++NbImportUsed;
			}
		}
		System.out.println("NB USED : "+NbImportUsed);
		
		double ratio = (double)(NbImportUsed)/size;
		ratio = ratio*100;
		DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(ratio));
			
		
	}
	
	private double getImportNotUsedRatio(ArrayList<ImportStatus>ImportList, int size) {
		int NbImportNotUsed = 0;
		for(ImportStatus Import : ImportList) {
			if(Import.ImportStatus == 0) {
				++NbImportNotUsed;
			}
		}
		System.out.println("NB NOTUSED : "+NbImportNotUsed);
		double ratio = (double)(NbImportNotUsed)/size;
		ratio = ratio*100;
		DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(ratio));
			
		
	}
	private double getImportConflictRation(ArrayList<ImportStatus>ImportList,int size) {
		int NbImportConflict = 0;
		for(ImportStatus Import : ImportList) {
			if(Import.ConflictStatus == 1) {
				++NbImportConflict;
			}
		}
		System.out.println("NB CONFLICT : "+NbImportConflict);
		double ratio = (double)(NbImportConflict)/size;
		ratio = ratio*100;
		DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(ratio));
			
	}
	
	private double getImportDuplicateRation(ArrayList<ImportStatus>ImportList,int size) {
		int NbImportDuplicate = 0;
		for(ImportStatus Import : ImportList) {
			if(Import.DuplicatStatus >= 1) {
				System.out.println("Import : "+Import.ImportName+"  "+Import.DuplicatStatus);
				NbImportDuplicate = Import.DuplicatStatus+NbImportDuplicate;
			}
		}
		System.out.println("NB DUPLICATE : "+NbImportDuplicate);
		double ratio = (double)(NbImportDuplicate)/size;
		ratio = ratio*100;
		DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(ratio));
			
		
	}
	
	private int getTotalImportNumber() {
		int size = 0;
		for(ImportStatus Import : ImportController.ListImport) {
			size = Import.DuplicatStatus+size+1;
		}
		return size;
	}
	
	

}
