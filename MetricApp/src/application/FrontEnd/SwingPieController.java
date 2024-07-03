package application.FrontEnd;

import java.io.File;
import java.text.DecimalFormat;
import java.util.stream.Collectors;

import application.BackEnd.SwingComponent;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

public class SwingPieController {
@FXML
PieChart SwingPie;
@FXML
Label Description;
	public void initialize() {
	File file = new File(MetricController.FileSelectedPath);
	SwingComponent swing = SwingComponent.FetchSwingComponentNumber(file);
   int total = swing.TotalComponent;
   double JInternalFramerRatio = GetRatioComponent(total,swing.compteurJInternalFrame);
   double JComboBoxRatioRatio = GetRatioComponent(total,swing.compteurJComboBox);
   double JCheckBoxRatio = GetRatioComponent(total,swing.compteurJCheckBox);
   double JListRatio = GetRatioComponent(total,swing.compteurJList);
   double JButtonRatio = GetRatioComponent(total,swing.compteurJButton);
   double JMenuRatio = GetRatioComponent(total,swing.compteurJMenu);
   double JRadioButtonRatio = GetRatioComponent(total,swing.compteurJRadioButton);
   double JSliderRatio = GetRatioComponent(total,swing.compteurJSlider);
   double JSpinnerRatio = GetRatioComponent(total,swing.compteurJSpinner);
   double JTextFieldRatio = GetRatioComponent(total,swing.compteurJTextField);
   double JPasswordFieldRatio = GetRatioComponent(total,swing.compteurJPasswordField);
   double JColorChooserRatio = GetRatioComponent(total,swing.compteurJColorChooser);
   double JEditorPaneRatio = GetRatioComponent(total,swing.compteurJEditorPane);
   double JTextPaneRatio = GetRatioComponent(total,swing.compteurJTextPane);
   double JFileChooserRatio = GetRatioComponent(total,swing.compteurJFileChooser);
   double JTableRatio = GetRatioComponent(total,swing.compteurJTable);
   double JLayeredPaneRatio = GetRatioComponent(total,swing.compteurJLayeredPane);
   double JTextAreaRatio = GetRatioComponent(total,swing.compteurJTextArea);
   double JTreeRatio = GetRatioComponent(total,swing.compteurJTree);
   double JLabelRatio = GetRatioComponent(total,swing.compteurJLabel);
   double JProgressBarRatio = GetRatioComponent(total,swing.compteurJProgressBar);
   double JSeparatorRatio = GetRatioComponent(total,swing.compteurJSeparator);
   double JToolTipRatio = GetRatioComponent(total,swing.compteurJToolTip);
   double JAppletRatio = GetRatioComponent(total,swing.compteurJApplet);
   double JDialogRatio = GetRatioComponent(total,swing.compteurJDialog);
   double JFrameRatio = GetRatioComponent(total,swing.compteurJFrame);
   double JPanelRatio = GetRatioComponent(total,swing.compteurJPanel);
   double JScrollPaneRatio = GetRatioComponent(total,swing.compteurJScrollPane);
   double JSplitPaneRatio = GetRatioComponent(total,swing.compteurJSplitPane);
   double JTabbedPaneRatio = GetRatioComponent(total,swing.compteurJTabbedPane);
   double JToolBarRatio = GetRatioComponent(total,swing.compteurJToolBar);
   ObservableList<PieChart.Data> SwingPieData = FXCollections.observableArrayList(
      new PieChart.Data("JInternalFramer ",JInternalFramerRatio),
      new PieChart.Data("JComboBox ",JComboBoxRatioRatio),
      new PieChart.Data("JCheckBox ",JCheckBoxRatio),
      new PieChart.Data("JList ",JListRatio),
      new PieChart.Data("JButton ",JButtonRatio),
      new PieChart.Data("JMenu ",JMenuRatio),
      new PieChart.Data("JRadioButton",JRadioButtonRatio),
      new PieChart.Data("JSlider",JSliderRatio ),
      new PieChart.Data("JSpinner ",JSpinnerRatio),
      new PieChart.Data("JTextField ",JTextFieldRatio),
      new PieChart.Data("JPasswordField ",JPasswordFieldRatio),
      new PieChart.Data("JColorChooser ",JColorChooserRatio),
      new PieChart.Data("JEditorPane ",JEditorPaneRatio),
      new PieChart.Data("JTextPane ",JTextPaneRatio),
      new PieChart.Data("JFileChooser ",JFileChooserRatio),
      new PieChart.Data("JTable ",JTableRatio),
      new PieChart.Data("JLayeredPane ",JLayeredPaneRatio),
      new PieChart.Data("JTextArea ",JTextAreaRatio),
      new PieChart.Data("JTree ",JTreeRatio),
      new PieChart.Data("JLabel ",JLabelRatio ),
      new PieChart.Data("JProgressBar ",JProgressBarRatio),
      new PieChart.Data("JSeparator ",JSeparatorRatio),
      new PieChart.Data("JToolTip ",JToolTipRatio),
      new PieChart.Data("JApplet ",JAppletRatio), 
      new PieChart.Data("JDialog ",JDialogRatio),
      new PieChart.Data("JFrame",JFrameRatio),
      new PieChart.Data("JPanel ",JPanelRatio ),
      new PieChart.Data("JScrollPane",JScrollPaneRatio),
      new PieChart.Data("JSplitPane ",JSplitPaneRatio ),
      new PieChart.Data("JTabbedPane ",JTabbedPaneRatio),
      new PieChart.Data("JToolBar ",JToolBarRatio)
       );	

   SwingPie.setTitle("Swing Component Ratio");
   
   ObservableList<PieChart.Data> filteredData = FXCollections.observableArrayList(
           SwingPieData.stream().filter(data -> data.getPieValue() > 0).collect(Collectors.toList())
   );
   
	   filteredData.forEach(data ->
	       data.nameProperty().bind(Bindings.concat(
	       		data.getName(),data.getPieValue(),"%")
	       		)
	      
			);
	
	  SwingPie.getData().addAll(filteredData);
	  Description.setText("The File "+file.getName()+" Has In Total "+total+" Swing Components , "+JInternalFramerRatio+"% Are JInternalFramer , "+JComboBoxRatioRatio+"% are JComboBox , "+JCheckBoxRatio+"% are JCheckBox , "+
		      JListRatio+"% are JList , "+
		      JButtonRatio+"% are JButton , "+
		      JMenuRatio+"% are JMenu , "+
		      JRadioButtonRatio+"% are JRadioButton , "+
		      JSliderRatio+"% are JSlider , "+
		      JSpinnerRatio+"% are JSpinner , "+
		      JTextFieldRatio+"% are JTextField , "+
		      JPasswordFieldRatio+"% are JPasswordField , "+
		      JColorChooserRatio+"% are JColorChooser , "+
		      JEditorPaneRatio+"% are JEditorPane , "+
		      JTextPaneRatio+"% are JTextPane "+
		      JFileChooserRatio+"% are JFileChooser "+
		      JTableRatio+"% are JTable "+
		      JLayeredPaneRatio+"% are JLayeredPane "+
		      JTextAreaRatio+"% are JTextArea , "+
		      JTreeRatio+"% are JTree , "+
		      JLabelRatio +"% are JLabel , "+
		      JProgressBarRatio+"% are JProgressBar , "+
		      JSeparatorRatio+"% are JSeparator , "+
		      JToolTipRatio+"% are JToolTip , "+
		      JAppletRatio+"% are JApplet , "+ 
		      JDialogRatio+"% are JDialog , "+
		      JFrameRatio+"% are JFrame , "+
		      JPanelRatio+"% are JPanel , "+
		      JScrollPaneRatio+"% are JScrollPane , "+
		      JSplitPaneRatio+"% are JSplitPane , " +
		      JTabbedPaneRatio+"% are JTabbedPane , and finally "+
		      JToolBarRatio+"% are JToolBar ");
		       


  
}
	
	private double GetRatioComponent(int total , int component) {
		double Ratio = (double)(component)/total;
		Ratio = Ratio*100;
		DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(Ratio));
		
	}
}
