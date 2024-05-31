package application.FrontEnd;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LoginController {
    @FXML
    ImageView VisibilityOnIcon;
    @FXML
    PasswordField passwordField;
    @FXML
    TextField UnMaskedField;
    @FXML
    ImageView LockIcon;
  
    boolean IsVisible = false;

    @FXML
    private void SwitchToSignUp(MouseEvent event) {
    	
    	Node source = (Node) event.getSource();
        Stage stagelogin = (Stage) source.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ressource/Fxml Folder/SingUp.fxml"));
        Parent root = null;
			try {
				root = fxmlLoader.load();
			} catch (IOException exception) {
				// TODO Auto-generated catch block
				exception.printStackTrace();
			}
   	  Scene scene = new Scene(root);
       String css = this.getClass().getResource("/ressource/Css Folder/SignUp.css").toExternalForm();
       scene.getStylesheets().add(css);
       Stage stage = new Stage();
       stage.setScene(scene);
       stage.show();
       stagelogin.close();
    }
  @FXML  
private void SwitchToForgotPassword(MouseEvent event) {
    	
    	Node source = (Node) event.getSource();
        Stage stagelogin = (Stage) source.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ressource/Fxml Folder/ForgotPassword.fxml"));
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
       stagelogin.close();
    }
    
    
    
    
    public void ChangeVisibility(MouseEvent event) {
        if (IsVisible) {
            passwordField.setText(UnMaskedField.getText());
            LockIcon.setImage(new Image(getClass().getResourceAsStream("")));
     	    
            VisibilityOnIcon.setImage(new Image(getClass().getResourceAsStream("")));
            UnMaskedField.setVisible(false);
            passwordField.setVisible(true);
            IsVisible = false;
    } else {
           UnMaskedField.setText(passwordField.getText());
          LockIcon.setImage(new Image(getClass().getResourceAsStream("/ressource/PNG Folder/lock_20dp.png")));
    	   VisibilityOnIcon.setImage(new Image(getClass().getResourceAsStream("/ressource/PNG Folder/visibility_20dp.png")));
    	   UnMaskedField.setVisible(true);
           passwordField.setVisible(false);
    	   IsVisible = true;
    }
    }

    


}
