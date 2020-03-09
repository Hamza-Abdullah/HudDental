package Login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class LoginMain extends Application {

    // For patient
    public static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(getClass().getResource("Login.fxml"));
//        LoginController controller = new LoginController();
//        loader.setController(controller);
//
//        Parent root = loader.load();
//        primaryStage.setTitle("HudDental - Dental Management System");
//        primaryStage.setScene(new Scene(root, 1078, 605));
//        primaryStage.show();
//
//        controller.setupLoginUI();
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        primaryStage.setTitle("HudDental - Dental Management System");
        primaryStage.setScene(new Scene(root, 1078, 605));
        primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("../Images/icon.png")));
        primaryStage.show();
    }

    // For patient
    public static Stage getPrimaryStage(){
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
