package application.FrontEnd;

import application.BackEnd.SQLBackEnd;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class TokenConfirmationController {
@FXML
TextField TokenField;
@FXML
Label ErrorToken;

@FXML
private void TokenConfirmation(ActionEvent event) {
	ErrorToken.setText("   ");
	String TokenInput;
	try {
  TokenInput = TokenField.getText();
	} catch(NullPointerException e) {
		 TokenInput= null;
	 }
if(TokenInput==null||TokenInput.isEmpty()||TokenInput.isBlank()) {
	ErrorToken.setText("Token Field Is Empty");
}
else if(SQLBackEnd.IsRightToken(SignUpController.GetEmail(), TokenInput)==0) {
	ErrorToken.setText("Error Wrong Token");
}
else if(SQLBackEnd.IsRightToken(SignUpController.GetEmail(), TokenInput)==-1) {
	ErrorToken.setText("Token Expired");
}
else { 
	SQLBackEnd.deleteTokenAfterConfirmation(SignUpController.GetEmail());
	if(SQLBackEnd.InjectInDB(SignUpController.GetUserName(), SignUpController.GetEmail())) {
		showSignUpSuccessDialog();
	}
}
}

private void showSignUpSuccessDialog() {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Sign Up Successful");
    alert.setHeaderText(null);
    alert.setContentText("Your sign up was successful!");
    alert.showAndWait();
}
}