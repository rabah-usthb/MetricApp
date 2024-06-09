package application.BackEnd;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {
 private static final String DB_MAIL = System.getenv("DB_Mail");
 private static final String DB_MAIL_PASSWORD=System.getenv("DB_Mail_Password");
 private static final String Port ="465";
 private static final String Smpt="smtp.gmail.com";
 private static final String SocketFactory="javax.net.ssl.SSLSocketFactory";
 String MailReceiver;
 Properties properties;
 public MailSender(String MailReceiver) {
	 this.MailReceiver = MailReceiver;
	 SetPropeties();
 }
 
 private void SetPropeties() {
	 this.properties = new Properties();
	 properties.put("mail.smtp.auth", "true");
	 properties.put("mail.smtp.starttls.enable", "false");
     properties.put("mail.smtp.debug", "true");
	 properties.put("mail.smtp.host", Smpt);
	 properties.put("mail.smtp.port", Port);
	 properties.put("mail.smtp.ssl.trust", Smpt);
	 properties.put("mail.smtp.socketFactory.class", SocketFactory); 
	 properties.put("mail.smtp.socketFactory.port", Port);
	 properties.put("mail.smtp.socketFactory.fallback", "false");  
 }
 
 
 private void SendMail(String UserName,int MessageFlag) {
	    Session session = Session.getInstance(this.properties, new Authenticator() {
	        @Override
	        protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication(DB_MAIL, DB_MAIL_PASSWORD);
	        }
	    });

	    session.setDebug(true);
	    Message message;
	    if(MessageFlag == 0) {
	    	message = PrepareMessageAuth(session, UserName);
	    }
	    else {
	    	message =null;
	    }
	    try {
	    	
	        Transport ts = session.getTransport("smtp");
	        ts.connect("smtp.gmail.com", DB_MAIL, DB_MAIL_PASSWORD);
	        ts.sendMessage(message, message.getAllRecipients());
	        
	        System.out.println("Mail Sent Successfully");
	        ts.close();
	    } catch (MessagingException e) {
	        e.printStackTrace();
	        System.out.println("Failed to send mail: " + e.getMessage());
	    }
	}


	private  Message PrepareMessageAuth(Session session, String UserName) {
	    Message message = new MimeMessage(session);
	    try {
	        message.setFrom(new InternetAddress(DB_MAIL));
	        message.setRecipient(Message.RecipientType.TO, new InternetAddress(this.MailReceiver));
	        message.setSubject("Mail Authentication For Metric App");
	        TokenGenerator TGEN= new TokenGenerator(UserName, this.MailReceiver);
	        String Token = TGEN.Wrapper();
	        SQLBackEnd.InjectToken(UserName, this.MailReceiver, Token);
	        message.setText("Hello "+UserName+ ", please confirm that your email address is: " + this.MailReceiver +" By Inputing The Following Token "+Token+" In The Metric Application");
	        message.saveChanges();
	    } catch (MessagingException e) {
	        e.printStackTrace();
	        System.out.println("Failed to prepare message: " + e.getMessage());
	    }
	    return message;
	}
	
	
}
