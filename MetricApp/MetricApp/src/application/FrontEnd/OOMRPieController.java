package application.FrontEnd;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.util.stream.Collectors;

import application.BackEnd.OOMRCalculator;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

public class OOMRPieController {
@FXML
private PieChart OOMRPie;
@FXML
private Label Description;
public void initialize() {
	System.out.println("begin");
	  OOMRCalculator rm = null;
		try {
			rm = OOMRCalculator.fetchOOMR(MetricController.FileSelectedPath,MetricController.SelectedItem);
		} catch (FileNotFoundException | MalformedURLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(rm!=null) {
			System.out.println("rm not null");
		}
	int TotalMethod = rm.totalMethods;
    double OverrideRatio = rm.RatioMethodsRedef;
    double OverloadRatio = rm.RatioMethodsSur;
    double OOMRRatio =  rm.oomr;
    ObservableList<PieChart.Data> EncapsulationPieData = FXCollections.observableArrayList(
            new PieChart.Data("Overload Methods ", OverloadRatio),
            new PieChart.Data("Override Methods ",OverrideRatio)
            );	

	OOMRPie.setTitle("OOMR Ratio");
	
	ObservableList<PieChart.Data> filteredData = FXCollections.observableArrayList(
		   EncapsulationPieData.stream().filter(data -> data.getPieValue() > 0).collect(Collectors.toList())
	);
	
	filteredData.forEach(data ->
	       data.nameProperty().bind(Bindings.concat(
	       		data.getName(),data.getPieValue(),"%")
	       		)
	      
			);
	
	
	OOMRPie.getData().addAll(filteredData);
	Description.setText("The File "+MetricController.SelectedItem+" has "+TotalMethod+" Methods , "+OverloadRatio+"% are Overload , "+OverrideRatio+"% are Override , and finally the OOMR Ratio is "+OOMRRatio+"%");

	System.out.println("end");
}
}
