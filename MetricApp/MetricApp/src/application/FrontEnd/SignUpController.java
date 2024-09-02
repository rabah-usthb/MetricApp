package application.FrontEnd;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import application.BackEnd.MailSender;
import application.BackEnd.RegularExpression;
import application.BackEnd.SQLBackEnd;
import application.BackEnd.TokenGenerator;
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
	
	private static String UserNameInput="";
    private static String EmailInput="";
    private static String PasswordInput="";
	
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
	
	
    
    
    
    static String GetUserName() {
    	return UserNameInput;
    }
    
    static String GetEmail() {
    	return EmailInput;
    }
    
    static String GetPassword() {
    	return PasswordInput;
    }
	
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
 
 
 private void ShowTokenConfirmation() {
	  FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ressource/Fxml Folder/TokenConfirmation.fxml"));
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
	 boolean IsEmptyUserName = RegularExpression.IsFieldEmpty(UserName);
	 boolean IsUserNameRightLength = UserName.length()>=4;
	 boolean IsEmptyEmail = RegularExpression.IsFieldEmpty(Email);
	 boolean IsEmail = RegularExpression.IsGmail(Email);
	 boolean IsEmptyPassword=RegularExpression.IsFieldEmpty(Password);
	 boolean IsPasswordRightSize=Password.length()>=8;
	 boolean IsConfirmPasswordEmpty = RegularExpression.IsFieldEmpty(ConfrimPassword);
	 boolean IsSamePassword = Password.equals(ConfrimPassword);
	 
	 if(IsEmptyUserName) {
		 UserNameError.setText("UserName Field Is Empty");
		 Accepted = false;
	 }
	 else if (!IsUserNameRightLength) {
		 UserNameError.setText("UserName Needs To Be At Least 4 Character");
		 Accepted = false;
	 }
	 
	 if(IsEmptyEmail) {
		 EmailError.setText("EmailField Is Empty");
		 Accepted = false;
	 }
	 else if(!IsEmail) {
		 EmailError.setText("Not An Email");
		 Accepted = false;
	 }
	  if((IsUserNameRightLength && !IsEmptyUserName)||(!IsEmptyEmail && IsEmail)) {
		 if(SQLBackEnd.SqlFetchData(UserName,Email)) {
			if( SQLBackEnd.GetIsMailUsed()) {
			 EmailError.setText("Email Already Used");
			}
			if(SQLBackEnd.GetIsNameUsed()) {
			UserNameError.setText("Name Already Used");	
			}
			 Accepted = false;
		 }
	 }
	 
	 if(IsEmptyPassword) {
		 PasswordError.setText("PasswordField Is Empty");
		 Accepted = false;
	 }
	 else if(!IsPasswordRightSize) {
		 PasswordError.setText("Password At Least 8 Characater");
		 Accepted = false;
	 }
	 
	 if(IsConfirmPasswordEmpty) {
		 ConfirmPasswordError.setText("ConfirmPasswordField Is Empty");
		 Accepted = false;
	 }
	 else if(!IsSamePassword) {
		 ConfirmPasswordError.setText("Not Same Password");
		 Accepted = false;
	 }
	 
	 if(Accepted) {  
		 PasswordInput = Password;
		 UserNameInput = UserName;
		 EmailInput = Email;
		 SQLBackEnd.InjectPendingUser(UserName, Email, Password);
		 System.out.println("Prepare Sending");
		 MailSender Mail = new MailSender(Email);
		 Mail.SendMail(UserName, 0);
		 ShowTokenConfirmation();
		 
	 }
 }

 
}
