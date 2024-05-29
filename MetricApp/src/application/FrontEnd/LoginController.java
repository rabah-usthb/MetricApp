package application.FrontEnd;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

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
