package application.FrontEnd;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.util.stream.Collectors;

import application.BackEnd.RMSRCalculator;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

public class RMRSPieController {
@FXML
private PieChart RMRSPie;
@FXML
private Label Description;
public void initialize() {
	  File file = new File(MetricController.FileSelectedPath);
      RMSRCalculator rm = null;
		try {
			rm = RMSRCalculator.fetchRMSR(file.getAbsolutePath(),file.getName());
		} catch (FileNotFoundException | MalformedURLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	int TotalMethod = rm.totalMethods;
    double OverrideRatio = rm.RatioMethodsRedef;
    double OverloadRatio = rm.RatioMethodsSur;
    double RMRSRatio =  rm.rmsr;
    ObservableList<PieChart.Data> EncapsulationPieData = FXCollections.observableArrayList(
            new PieChart.Data("Overload Methods ", OverloadRatio),
            new PieChart.Data("Override Methods ",OverrideRatio)
            );	

	RMRSPie.setTitle("RMRS Ratio");
	
	ObservableList<PieChart.Data> filteredData = FXCollections.observableArrayList(
		   EncapsulationPieData.stream().filter(data -> data.getPieValue() > 0).collect(Collectors.toList())
	);
	
	filteredData.forEach(data ->
	       data.nameProperty().bind(Bindings.concat(
	       		data.getName(),data.getPieValue(),"%")
	       		)
	      
			);
	
	
	RMRSPie.getData().addAll(filteredData);
	Description.setText("The File "+file.getName()+" has "+TotalMethod+" Methods , "+OverloadRatio+"% are Overload , "+OverrideRatio+"% are Override , and finally the RMRS Ratio is "+RMRSRatio+"%");

	
}
}
