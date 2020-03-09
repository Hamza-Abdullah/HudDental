//package Patient;
//
//import Login.LoginController;
//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//
//public class PatientMain extends Application {
//
//
//    @Override
//    public void start(Stage primaryStage) throws Exception{
//
//        this.primaryStage = primaryStage;
//        mainVBox = new VBox();
//
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(getClass().getResource("PatientAppointmentGUI.fxml"));
//        PatientController patientController = new PatientController();
//        loader.setController(patientController);
//        Parent root = loader.load();
//        patientController.addVBox(mainVBox);
//
//        primaryStage.setTitle("HudDental - Dental Management System");
//        primaryStage.setScene(new Scene(root, 1078, 605));
//        primaryStage.show();
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}
