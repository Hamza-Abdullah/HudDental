package leaveRequest;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LeaveRequestMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("LeaveRequestUI.fxml"));
        LeaveRequestController controller = new LeaveRequestController(2);
        //When implemented staff number will be passed from login (2 is placeholder)
        loader.setController(controller);

        Parent root = loader.load();
        primaryStage.setTitle("HudDental - Dental Management System");
        primaryStage.setScene(new Scene(root, 1078, 605));
        primaryStage.show();

        controller.setupUI();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
