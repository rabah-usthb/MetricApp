package application.FrontEnd;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
else if(!TokenInput.equals(SignUpController.GetToken())) {
	ErrorToken.setText("Error Wrong Token");
}
else {
	System.out.println("SIGNUP SUCCESSFULLY");
}
}
}