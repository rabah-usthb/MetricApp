package application.FrontEnd;

import java.io.IOException;


import application.BackEnd.RegularExpression;
import application.BackEnd.SQLBackEnd;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LoginController {
	@FXML
    TextField UserField;
	@FXML
	Label UserNameError;
	@FXML
	Label PasswordError;
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ressource/Fxml Folder/SignUp.fxml"));
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

    
@FXML
private void LoginAction(ActionEvent event) {
	UserNameError.setText("  ");
	PasswordError.setText("  ");
	String UserName;
	String Password;
	boolean Accepted = true;
	try {
		UserName = UserField.getText();
	}catch(NullPointerException e) {
		UserName = null;
	}
	
	try {
		if(IsVisible) {
			Password = UnMaskedField.getText();
		}
		else {
			Password = passwordField.getText();
		}
	}catch(NullPointerException e) {
		Password = null;
	}
	
	boolean IsEmptyUserName = RegularExpression.IsFieldEmpty(UserName);
	boolean IsEmptyPassword = RegularExpression.IsFieldEmpty(Password);
	boolean IsRightLengthUserName = UserName.length()>=4;
	boolean IsRightLengthPassword = Password.length()>=8;
	if(IsEmptyUserName) {
		UserNameError.setText("User Field Is Empty");
		Accepted = false;
	}
	else if(!IsRightLengthUserName) {
		UserNameError.setText("User At Least 4 Character");
		Accepted = false;
	}
	
	if(IsEmptyPassword) {
		PasswordError.setText("Password Field Is Empty");
		Accepted = false;
	}
	else if(!IsRightLengthPassword) {
		PasswordError.setText("Password At Least 8 Character");
		Accepted = false;
	}
	else if(!IsEmptyUserName&&IsRightLengthUserName) {
		if(!SQLBackEnd.UserExist(UserName, Password)) {
			if(!SQLBackEnd.GetExistUser()) {
				UserNameError.setText("User Log Doesnt Even Exist");
			}
			else if(!SQLBackEnd.GetPasswordExist()) {
				PasswordError.setText("Wrong Password");
			}
			Accepted= false;
		}
	}
	
	if(Accepted) {
		System.out.println("Login Successful");
	}
	
}

}
