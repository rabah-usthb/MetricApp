package application.FrontEnd;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SignUpController {
 @FXML
 private void SwitchToLogin(MouseEvent event) {
	 Node source = (Node) event.getSource();
     Stage stagesignup = (Stage) source.getScene().getWindow();
     FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ressource/Fxml Folder/Login.fxml"));
     Parent root = null;
			try {
				root = fxmlLoader.load();
			} catch (IOException exception) {
				// TODO Auto-generated catch block
				exception.printStackTrace();
			}
	  Scene scene = new Scene(root);
    String css = this.getClass().getResource("/ressource/Css Folder/Login.css").toExternalForm();
    scene.getStylesheets().add(css);
    Stage stage = new Stage();
    stage.setScene(scene);
    stage.show();
    stagesignup.close();
 
 }
}
