package application.FrontEnd;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TabedPaneController {

	
	@FXML
	
	private void MetricTreeLoading(ActionEvent event) {
		Button clickedButton = (Button) event.getSource();
        String buttonId = clickedButton.getId();
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        
        switch(buttonId) {
        case "ICMButton":
        	ICOMLoading();
        	ICMPIELoad();
        	stage.close();
        	break;
        case "JAXButton":
        	JAXLoading();
        	stage.close();
        	break;
        	
        case "JEAButton":
        	JEALoading();
        	stage.close();
        	break;
    
        case "SMButton":
        	SMLoading();
        	stage.close();
        	break;
        
        case "TEButton":
        	TELoading();
        	TEPIELoad();
        	stage.close();
        	break;
       
        case "RMRSButton": 	
        	RMRSBLoading();
        	stage.close();
        	break;
        
         default:
        	stage.close();
        	break;
        }
        
       
	}
	
	
	
	private void JAXLoading() {
		 FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ressource/Fxml Folder/Analysis.fxml"));
         Parent root = null;
			try {
				root = fxmlLoader.load();
			} catch (IOException exception) {
				// TODO Auto-generated catch block
				exception.printStackTrace();
			}
    	AnalysisController analysisController = fxmlLoader.getController();
        analysisController.initialize(MetricController.FileSelectedPath);
        Scene scene = new Scene(root);
        String css = this.getClass().getResource("/ressource/Css Folder/Analysis.css").toExternalForm();
        scene.getStylesheets().add(css);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
	}
	
	
	 public void TEPIELoad() {

	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ressource/Fxml Folder/EncapsulationPie.fxml"));
	        Parent root = null;
			try {
				root = fxmlLoader.load();
			} catch (IOException exception) {
		
				exception.printStackTrace();
			}
	        
	        Scene scene = new Scene(root);
	        String css = this.getClass().getResource("/ressource/Css Folder/Pie.css").toExternalForm();
	        scene.getStylesheets().add(css);
	        
	        Stage stage = new Stage();
	        stage.setScene(scene);
	        stage.show();
	    }
	
	 public void ICMPIELoad() {

	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ressource/Fxml Folder/ImportPie.fxml"));
	        Parent root = null;
			try {
				root = fxmlLoader.load();
			} catch (IOException exception) {
		
				exception.printStackTrace();
			}
	        
	        Scene scene = new Scene(root);
	        Stage stage = new Stage();
	        stage.setScene(scene);
	        stage.show();
	    }
	
	private void ICOMLoading() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ressource/Fxml Folder/Import.fxml"));
        Parent root = null;
		try {
			root = fxmlLoader.load();
		} catch (IOException exception) {
	
			exception.printStackTrace();
		}
        ImportController importController = fxmlLoader.getController();
        importController.initialize(MetricController.FileSelectedPath);
        Scene scene = new Scene(root);
        String css = this.getClass().getResource("/ressource/Css Folder/Import.css").toExternalForm();
        scene.getStylesheets().add(css);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
       
	}
	
	private void JEALoading() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ressource/Fxml Folder/Exception.fxml"));
        Parent root = null;
		try {
			root = fxmlLoader.load();
		} catch (IOException exception) {
			// TODO Auto-generated catch block
			exception.printStackTrace();
		}
   	ExceptionController exceptionController = fxmlLoader.getController();
       exceptionController.initialize(MetricController.FileSelectedPath);
       Scene scene = new Scene(root);
       String css = this.getClass().getResource("/ressource/Css Folder/Exception.css").toExternalForm();
       scene.getStylesheets().add(css);
       Stage stage = new Stage();
       stage.setScene(scene);
       stage.show();
	}
	
	
	private void SMLoading() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ressource/Fxml Folder/SwingComponent.fxml"));
        Parent root = null;
		try {
			root = fxmlLoader.load();
		} catch (IOException exception) {
			// TODO Auto-generated catch block
			exception.printStackTrace();
		}
   	SwingComponentController swingController = fxmlLoader.getController();
       swingController.initialize(MetricController.FileSelectedPath);
       Scene scene = new Scene(root);
       String css = this.getClass().getResource("/ressource/Css Folder/Swing.css").toExternalForm();
       scene.getStylesheets().add(css);
       Stage stage = new Stage();
       stage.setScene(scene);
       stage.show();
	}
	
	private void TELoading() {
		 FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ressource/Fxml Folder/Encapsulation.fxml"));
         Parent root = null;
			try {
				root = fxmlLoader.load();
			} catch (IOException exception) {
				// TODO Auto-generated catch block
				exception.printStackTrace();
			}
    	EncapsulationController encapsulationController = fxmlLoader.getController();
        encapsulationController.initialize(MetricController.FileSelectedPath);
        Scene scene = new Scene(root);
        String css = this.getClass().getResource("/ressource/Css Folder/Exception.css").toExternalForm();
        scene.getStylesheets().add(css);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
	}
	
	private void RMRSBLoading() {
		 FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ressource/Fxml Folder/RMRS.fxml"));
         Parent root = null;
			try {
				root = fxmlLoader.load();
			} catch (IOException exception) {
				// TODO Auto-generated catch block
				exception.printStackTrace();
			}
	       RMRScontroller rmrsController = fxmlLoader.getController();
        rmrsController.initialize(MetricController.FileSelectedPath,MetricController.SelectedItem);
        Scene scene = new Scene(root);
        String css = this.getClass().getResource("/ressource/Css Folder/RMRS.css").toExternalForm();
      scene.getStylesheets().add(css);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
	}
}
