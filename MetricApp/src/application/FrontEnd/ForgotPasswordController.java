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

import application.BackEnd.MailSender;
import application.BackEnd.RegularExpression;
import application.BackEnd.SQLBackEnd;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ForgotPasswordController {
@FXML
TextField EmailField;
@FXML
Label EmailError;

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
	else if(!SQLBackEnd.MailExist(Email)) {
		EmailError.setText("Email Doesnt Exist");
	}
	else {
		MailSender Mail = new MailSender(Email);
		Mail.SendMail(SignUpController.GetUserName(), 1);
		
	}
}
}
