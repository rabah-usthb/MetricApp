package application.FrontEnd;

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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ForgotPasswordController {
@FXML
TextField EmailField;
@FXML
Label EmailError;
private static String token;  


private static String TokenGenerator(String Email) {
	  LocalDateTime now = LocalDateTime.now();
    int hour = now.getHour();
    int day = now.getDayOfMonth();
    int month = now.getMonthValue();
    int second = now.getSecond();
    int minute = now.getMinute();
    int token = (day*69+hour/2+month+20+second*34)/3+minute+Email.length();
   return Integer.toString(token);
}

private static void SendMailAuthentification(String mailReceiver) {
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
    Message message = PrepareMessage(session,Email, mailReceiver);
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


private static Message PrepareMessage(Session session,String mailSender, String mailReceiver) {
    Message message = new MimeMessage(session);
    try {
        message.setFrom(new InternetAddress(mailSender));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailReceiver));
        message.setSubject("Password Reset Metric App");
        token = TokenGenerator(mailReceiver);
        message.setText("Hello, please confirm that your email address is: " + mailReceiver +" By Inputing The Following Token "+token+" In The Metric Application");
        message.saveChanges();
    } catch (MessagingException e) {
        e.printStackTrace();
        System.out.println("Failed to prepare message: " + e.getMessage());
    }
    return message;
}



@FXML
private void ForgotPassword(ActionEvent event) {
	String Email;
	try {
	 Email = EmailField.getText();
	}catch(NullPointerException e) {
		Email = null;
	}
	boolean IsEmptyEmail = RegularExpression.IsFieldEmpty(Email);
	boolean IsMail = RegularExpression.IsGmail(Email);
	if(IsEmptyEmail) {
		EmailError.setText("Mail Field Is Empty");
	}
	else if (!IsMail) {
		EmailError.setText("Not An Email");
	}
	else {
		SendMailAuthentification(Email);
		
	}
}
}
