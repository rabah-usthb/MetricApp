package application.FrontEnd;

import java.io.File;
import java.text.DecimalFormat;
import java.util.stream.Collectors;

import application.BackEnd.ExceptionStatus;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

public class ExceptionPieController {
@FXML
private PieChart ExceptionPie;
@FXML
private Label Description;
	public void initialize() {
		File file = new File(MetricController.FileSelectedPath);
		int total = ExceptionController.ListException.size();
		int Default = getNbDefault();
		int notDefault = getNbNotDefault();
		int RunTime = getNbRunTime();
		int CompileTime = getCompileTime();
		double DefaultRatio = getRatio(Default, total);
		double NotDefaultRatio = getRatio(notDefault, total);
		double RunTimeRatio = getRatio(RunTime, total);
		double CompileTimeRatio = getRatio(CompileTime, total);
		  ObservableList<PieChart.Data> ExceptionPieData = FXCollections.observableArrayList(
		            new PieChart.Data("Default Exceptions ", DefaultRatio),
		            new PieChart.Data("NotDefault Exceptions ",NotDefaultRatio),
		            new PieChart.Data("RunTime Exceptions ",RunTimeRatio),
		            new PieChart.Data("CompileTime Exceptions ",CompileTimeRatio)
		            
				  );	

			ExceptionPie.setTitle("Exception Ratio");
			
			ObservableList<PieChart.Data> filteredData = FXCollections.observableArrayList(
				   ExceptionPieData.stream().filter(data -> data.getPieValue() > 0).collect(Collectors.toList())
			);
			
			filteredData.forEach(data ->
			       data.nameProperty().bind(Bindings.concat(
			       		data.getName(),data.getPieValue(),"%")
			       		)
			      
					);
			
			
			ExceptionPie.getData().addAll(filteredData);
			Description.setText("The File "+file.getName()+" has "+total+" Exceptions , "+DefaultRatio+"% are default Exception , "+NotDefaultRatio+"% are notDefault Exception , "+RunTime+"% are runTime Exception , and finally "+CompileTimeRatio+"% are compileTime Exception");

			
	}

	private double getRatio(int nb , int total) {
		double Ratio = (double)nb/total;
		 DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(Ratio))*100;
       
	}
	
	private int getCompileTime() {
		int Nb = 0;
		for(ExceptionStatus exc : ExceptionController.ListException) {
			if(exc.CheckedStatus == 0) {
				++Nb;
			}
		}
		return Nb;
	}
	
	private int getNbRunTime() {
		int Nb = 0;
		for(ExceptionStatus exc : ExceptionController.ListException) {
			if(exc.CheckedStatus == 1) {
				++Nb;
			}
		}
		return Nb;
	}
	
	private int getNbNotDefault() {
		int Nb = 0;
		for(ExceptionStatus exc : ExceptionController.ListException) {
			if(exc.DefaultStatus == 1) {
				++Nb;
			}
		}
		return Nb;
	}
	
	private int getNbDefault() {
		int Nb = 0;
		for(ExceptionStatus exc : ExceptionController.ListException) {
			if(exc.DefaultStatus == 0) {
				++Nb;
			}
		}
		return Nb;
	}
	}
