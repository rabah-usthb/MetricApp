package application.FrontEnd;

import java.io.File;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.stream.Collectors;

import application.BackEnd.Line;
import application.BackEnd.PerformanceMetric;
import application.BackEnd.SoftwareSizeMetrices;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

public class AnalysisPieController {
	@FXML
	private PieChart AnalysisPie;
	@FXML
	private Label Description;
	public void initialize() {
		File file = new File(MetricController.FileSelectedPath);

        PerformanceMetric performanceMetric = new PerformanceMetric(Paths.get(file.getAbsolutePath()));
       
        String formattedRuntime = String.format("%.5f",performanceMetric.RunTime);
      
		int total = Line.CountLineAllLines(file);
		DecimalFormat df = new DecimalFormat("#.##");
	    double CodeRatio = Double.parseDouble(df.format(Line.CodeRation(file)));      
	    double CommentOnlyLineRatio = Double.parseDouble(df.format(Line.CommentRation(file)));      
	    double BlankRatio = (double) Line.CountBlankLine(file) / total * 100;
	    BlankRatio = Double.parseDouble(df.format(BlankRatio)); System.out.println("Blank Ratio : "+BlankRatio);
	    double CurlyBracesRatio =  Double.parseDouble(df.format((double)(Line.CountCurlyBracesLine(file))/total))*100;
	    ObservableList<PieChart.Data> AnalysisPieData = FXCollections.observableArrayList(
	            new PieChart.Data("Code Lines ", CodeRatio),
	            new PieChart.Data("CommentOnly Lines ",CommentOnlyLineRatio),
	            new PieChart.Data("Blank Lines ", BlankRatio),
	            new PieChart.Data("CurlyBraces Lines ",CurlyBracesRatio)
	            
	          );	

		AnalysisPie.setTitle("Analysis Ratio");
		
		ObservableList<PieChart.Data> filteredData = FXCollections.observableArrayList(
			   AnalysisPieData.stream().filter(data -> data.getPieValue() > 0).collect(Collectors.toList())
		);
		
		filteredData.forEach(data ->
		       data.nameProperty().bind(Bindings.concat(
		       		data.getName(),data.getPieValue(),"%")
		       		)
		      
				);
		
		
		AnalysisPie.getData().addAll(filteredData);
		Description.setText("The size of File "+file.getName()+" is "+performanceMetric.FileSize+" Byte , its runtime is estimated to "+formattedRuntime+" seconds , it has "+total+" lines , "+CodeRatio+"% are Code Lines , "+CommentOnlyLineRatio+"% are comment only lines , "+BlankRatio+"% are blank lines , and finally "+CurlyBracesRatio+"% are curly braces only lines");

	}
}
