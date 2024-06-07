package application.FrontEnd;
import javafx.application.Application;

import javafx.scene.control.*;
import java.awt.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
        	
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ressource/Fxml Folder/SignUp.fxml"));
            String css = this.getClass().getResource("/ressource/Css Folder/SignUp.css").toExternalForm();
            Parent root = loader.load();
            // Set the FXML content to the scene
            Scene scene = new Scene(root);
            scene.getStylesheets().add(css);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
