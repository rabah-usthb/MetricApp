package application.FrontEnd;

import java.io.IOException;

import application.BackEnd.RegularExpression;
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

public class SignUpController {
	
	@FXML
	Label UserNameError;
	@FXML
	Label EmailError;
	@FXML
	Label PasswordError;
	@FXML
	Label ConfirmPasswordError;
	
	@FXML
	TextField UserNameField;
	@FXML
	TextField EmailField;
	@FXML
    ImageView VisibilityOnIcon1;
    @FXML
    PasswordField passwordField1;
    @FXML
    TextField UnMaskedField1;
    @FXML
    ImageView LockIcon1;
    
    @FXML
    ImageView VisibilityOnIcon2;
    @FXML
    PasswordField passwordField2;
    @FXML
    TextField UnMaskedField2;
    @FXML
    ImageView LockIcon2;
    
    
  
    boolean IsVisible1= false;
    boolean IsVisible2=false;
	
	
	
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
 
 @FXML
 public void ChangeVisibility1(MouseEvent event) {
     if (IsVisible1) {
         passwordField1.setText(UnMaskedField1.getText());
         LockIcon1.setImage(new Image(getClass().getResourceAsStream("")));
  	    
         VisibilityOnIcon1.setImage(new Image(getClass().getResourceAsStream("")));
         UnMaskedField1.setVisible(false);
         passwordField1.setVisible(true);
         IsVisible1 = false;
 } else {
        UnMaskedField1.setText(passwordField1.getText());
       LockIcon1.setImage(new Image(getClass().getResourceAsStream("/ressource/PNG Folder/lock_20dp.png")));
 	   VisibilityOnIcon1.setImage(new Image(getClass().getResourceAsStream("/ressource/PNG Folder/visibility_20dp.png")));
 	   UnMaskedField1.setVisible(true);
        passwordField1.setVisible(false);
 	   IsVisible1 = true;
 }
 }

 
 public void ChangeVisibility2(MouseEvent event) {
     if (IsVisible2) {
         passwordField2.setText(UnMaskedField2.getText());
         LockIcon2.setImage(new Image(getClass().getResourceAsStream("")));
  	    
         VisibilityOnIcon2.setImage(new Image(getClass().getResourceAsStream("")));
         UnMaskedField2.setVisible(false);
         passwordField2.setVisible(true);
         IsVisible2 = false;
 } else {
        UnMaskedField2.setText(passwordField2.getText());
       LockIcon2.setImage(new Image(getClass().getResourceAsStream("/ressource/PNG Folder/lock_20dp.png")));
 	   VisibilityOnIcon2.setImage(new Image(getClass().getResourceAsStream("/ressource/PNG Folder/visibility_20dp.png")));
 	   UnMaskedField2.setVisible(true);
        passwordField2.setVisible(false);
 	   IsVisible2 = true;
 }
 }
 
 @FXML
 private void SignUP(ActionEvent event) {
  UserNameError.setText("  ");
  EmailError.setText("  ");
  PasswordError.setText("   ");
  ConfirmPasswordError.setText("   ");
	 boolean Accepted = true;
	 String UserName;
	 String Email;
	 String Password;
	 String ConfrimPassword;
	 try {
	  UserName = UserNameField.getText();
	 }
	 catch(NullPointerException e) {
		 UserName = null;
	 }
	 try {
	  Email=EmailField.getText();
	 }
	 catch(NullPointerException e) {
		 Email = null;
	 }
	 try {
		 if(IsVisible1) {
	  Password=UnMaskedField1.getText();
		 }
		 else {
			 Password = passwordField1.getText();
		 }
	 }
	 catch(NullPointerException e) {
		 Password = null;
	 }
	 try {
		 if(IsVisible2) {
			  ConfrimPassword=UnMaskedField2.getText();
				 }
		else {
			ConfrimPassword = passwordField2.getText();
				 }
	 }catch(NullPointerException e) {
		 ConfrimPassword = null;
	 }
	  if(UserName == null||UserName.isBlank()||UserName.isEmpty()) {
		 UserNameError.setText("UserName Field Is Empty");
		 Accepted = false;
	 }
	 else if (UserName.length()<4) {
		 UserNameError.setText("UserName Needs To Be At Least 4 Character");
		 Accepted = false;
	 }
	 
	 if(Email==null||Email.isBlank()||Email.isEmpty()) {
		 EmailError.setText("EmailField Is Empty");
		 Accepted = false;
	 }
	 else if(!RegularExpression.IsGmail(Email)) {
		 EmailError.setText("Not An Email");
		 Accepted = false;
	 }
	 
	 if(Password==null||Password.isBlank()||Password.isEmpty()) {
		 PasswordError.setText("PasswordField Is Empty");
		 Accepted = false;
	 }
	 else if(Password.length()<8) {
		 PasswordError.setText("Password At Least 8 Characater");
		 Accepted = false;
	 }
	 
	 if(ConfrimPassword==null||ConfrimPassword.isBlank()||ConfrimPassword.isEmpty()) {
		 ConfirmPasswordError.setText("ConfirmPasswordField Is Empty");
		 Accepted = false;
	 }
	 else if(!Password.equals(ConfrimPassword)) {
		 ConfirmPasswordError.setText("Not Same Password");
		 Accepted = false;
	 }
	 
	 if(Accepted) {
		 System.out.println("SignUp Succecfully");
	 }
 }

 
}
