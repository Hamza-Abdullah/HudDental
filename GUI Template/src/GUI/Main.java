package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("GUI.fxml"));
        primaryStage.setTitle("HudDental - Dental Management System");
        primaryStage.setScene(new Scene(root, 1078, 605));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
