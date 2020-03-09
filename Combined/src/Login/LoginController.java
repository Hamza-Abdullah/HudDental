package Login;

import Database.MySQL;
import Patient.PatientController;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    public HBox phoneHBox;
    public Button logInButton;
    public TextField phoneField;
    public PasswordField passwordField;
    public Label errorLabel;

    public static int staffID, patientID;
    public ImageView loader;

    public static VBox mainVBox = new VBox();

    private void setupLoginButton(){
        logInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                double width = logInButton.getScene().getWidth();
                double height = logInButton.getScene().getHeight();
                String phoneNum = phoneField.getText();
                if (phoneNum.length() != 11 | !phoneNum.matches("[0-9]*")){
                    displayErrorMessage();
                    return;
                }

                if (!MySQL.isRegistered(phoneNum)){
                    displayErrorMessage();
                    return;
                }


                if (!BCrypt.checkpw(passwordField.getText(), MySQL.getPasswordHash(phoneNum))){
                    displayErrorMessage();
                    return;
                } else {
                    //Image loaderImage = new Image("../Images/loader.gif");
                    try {
                        loader.setImage(new Image (this.getClass().getResourceAsStream("loader.gif")));
                    } catch (Exception e) {
                        e.printStackTrace();
                        errorLabel.setText("Logging in...");
                    }
                }

                if (MySQL.isStaff) {
                    //load staff UI
                    MySQL.getStaff(phoneNum);
                    LoginController.staffID = MySQL.staffID;
                    LoginController.patientID = -1;
                    if (MySQL.staffRole.equals("Receptionist")) {
                        Parent recParent = null;
                        try {
                            recParent = FXMLLoader.load(getClass().getResource("../Receptionist/BookAppointment.fxml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Scene recScene = new Scene(recParent, width, height);
                        //Set stage info
                        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                        window.setScene(recScene);
                        window.show();
                    } else if(MySQL.staffRole.equals("Dentist") | MySQL.staffRole.equals("Nurse")) {
                        Parent staffParent = null;
                        try {
                            staffParent = FXMLLoader.load(getClass().getResource("../StaffSchedule/GUI.fxml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Scene recScene = new Scene(staffParent, width, height);
                        //Set stage info
                        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                        window.setScene(recScene);
                        window.show();
                    } else {
                        System.out.println("Didn't work");
                    }

                }
                else {
                    //load patient ui
                    MySQL.getPatientByPhone(phoneNum);
                    LoginController.patientID = MySQL.patientID;
                    LoginController.staffID = -1;

                    // LOAD
                    Stage primaryStage = LoginMain.primaryStage;
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("../Patient/PatientAppointmentGUI.fxml"));
                    PatientController patientController = new PatientController();
                    loader.setController(patientController);
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    patientController.addVBox(mainVBox);

                    primaryStage.setTitle("HudDental - Dental Management System");
                    primaryStage.setScene(new Scene(root, 1078, 605));
                    primaryStage.show();
                }
            }
        });
    }

    private void displayErrorMessage(){
        errorLabel.setText("Phone number or password is incorrect.");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupLoginButton();
    }
}
