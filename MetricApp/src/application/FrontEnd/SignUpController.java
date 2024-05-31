package application.FrontEnd;

import java.io.IOException;
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
 
 
 private static String TokenGenerator(String Email , String UserName) {
	  LocalDateTime now = LocalDateTime.now();
      int hour = now.getHour();
      int day = now.getDayOfMonth();
      int month = now.getMonthValue();
      int second = now.getSecond();
      int minute = now.getMinute();
      int token = (day*69+hour/2+month+20+second*34)/3+minute-UserName.length()+Email.length();
     return Integer.toString(token);
 }
 
 private static void SendMailAuthentification(String mailReceiver,String UserName) {
	    Properties properties = new Properties();
	    properties.put("mail.smtp.auth", "true");
	    properties.put("mail.smtp.starttls.enable", "false");
        properties.put("mail.smtp.debug", "true");
	    properties.put("mail.smtp.host", "smtp.gmail.com");
	    properties.put("mail.smtp.port", "465");
	    properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
	    properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // Added SSL configuration
	    properties.put("mail.smtp.socketFactory.port", "465"); // Added SSL configuration
	    properties.put("mail.smtp.socketFactory.fallback", "false"); // Added SSL configuration
        
	    String Email = "chabanechaoucherabah4@gmail.com";
	    String Password = "cverzgtgpzglnmxh";

	    Session session = Session.getInstance(properties, new Authenticator() {
	        @Override
	        protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication(Email, Password);
	        }
	    });

	    session.setDebug(true);
	    Message message = PrepareMessage(session, UserName,Email, mailReceiver);
	    try {
	        Transport ts = session.getTransport("smtp");
	        ts.connect("smtp.gmail.com", Email, Password);
	        ts.sendMessage(message, message.getAllRecipients());
	        
	        System.out.println("Mail Sent Successfully");
	        ts.close();
	    } catch (MessagingException e) {
	        e.printStackTrace();
	        System.out.println("Failed to send mail: " + e.getMessage());
	    }
	}


	private static Message PrepareMessage(Session session, String UserName,String mailSender, String mailReceiver) {
	    Message message = new MimeMessage(session);
	    try {
	        message.setFrom(new InternetAddress(mailSender));
	        message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailReceiver));
	        message.setSubject("Mail Authentication For Metric App");
	        String Token = TokenGenerator(mailReceiver,UserName);
	        message.setText("Hello, please confirm that your email address is: " + mailReceiver +" By Inputing The Following Token "+Token+" In The Metric Application");
	        message.saveChanges();
	    } catch (MessagingException e) {
	        e.printStackTrace();
	        System.out.println("Failed to prepare message: " + e.getMessage());
	    }
	    return message;
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
		 System.out.println("Prepare Sending");
		 SendMailAuthentification(Email,UserName);
		 
		 
	 }
 }

 
}
