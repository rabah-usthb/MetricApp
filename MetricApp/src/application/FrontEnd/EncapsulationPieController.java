package application.FrontEnd;

import java.io.File;
import java.text.DecimalFormat;
import java.util.stream.Collectors;

import application.BackEnd.Encapsulation;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

public class EncapsulationPieController {
@FXML
private PieChart EncapsulationPie;
@FXML
private Label Description;

public void initialize() {
	   File file = new File(MetricController.FileSelectedPath);
	   Encapsulation encapsulation =   Encapsulation.EncapsulationFetch(file);
	   int TotalMemberNumber = encapsulation.GetTotal();
	   int ProtectedNumber = encapsulation.GetProtected();
	   int PrivateNumber = encapsulation.GetPrivate();
	   int NoneNumber = encapsulation.GetNone();
	   int PublicNumber = encapsulation.GetPublic();
	   DecimalFormat df = new DecimalFormat("#.##");
       double TERatio = Double.parseDouble(df.format(encapsulation.GetTaux()));      
 
	   double ProtectedRatio = (double)(ProtectedNumber) / TotalMemberNumber;
	   ProtectedRatio = ProtectedRatio * 100;
	   ProtectedRatio =  Double.parseDouble(df.format(ProtectedRatio)); 
	   
	   double PrivateRatio = (double)(PrivateNumber) / TotalMemberNumber;
	   PrivateRatio = PrivateRatio * 100;
	   PrivateRatio =  Double.parseDouble(df.format(PrivateRatio));
	   
	   double PublicRatio = (double)(PublicNumber) / TotalMemberNumber;
	   PublicRatio = PublicRatio * 100;
	   PublicRatio =  Double.parseDouble(df.format(PublicRatio));
	   
	   double NoneRatio = (double)(NoneNumber) / TotalMemberNumber;
	   NoneRatio = NoneRatio * 100;
	   NoneRatio =  Double.parseDouble(df.format(NoneRatio));
	
	   ObservableList<PieChart.Data> EncapsulationPieData = FXCollections.observableArrayList(
	            new PieChart.Data("Private Members ", PrivateRatio),
	            new PieChart.Data("Protected Members ", ProtectedRatio),
	            new PieChart.Data("No Acess Modifiers Memebers ", NoneRatio),
	            new PieChart.Data("Pulic Members ", PublicRatio)
	        );	
	
   EncapsulationPie.setTitle("Encapsulation Ratio");
   
   ObservableList<PieChart.Data> filteredData = FXCollections.observableArrayList(
		   EncapsulationPieData.stream().filter(data -> data.getPieValue() > 0).collect(Collectors.toList())
   );
   
   filteredData.forEach(data ->
           data.nameProperty().bind(Bindings.concat(
           		data.getName(),data.getPieValue(),"%")
           		)
          
   		);
   
  
   EncapsulationPie.getData().addAll(filteredData);
   
   Description.setText("The File "+file.getName()+" has "+TotalMemberNumber+" Members , "+PrivateRatio+"% are private , "+ProtectedRatio+"% are protected , "+NoneRatio+"% don't have acess modifiers , "+PublicRatio+"% are public , and finally the ratio of encapsulation is "+TERatio+"%");

}
}
