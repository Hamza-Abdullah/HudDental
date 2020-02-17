package Login;

import Database.MySQL;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    public HBox phoneHBox;
    @FXML
    public Button logInButton;
    @FXML
    public PasswordField passwordField;
    @FXML
    public Label errorLabel;

    public static int staffID;
    private NumberField phoneField;

    public void setupLoginUI(){
        setupNumberField();
        setupLoginButton();
    }

    private void setupNumberField(){
        phoneField = new NumberField();
        phoneHBox.getChildren().add(phoneField);
    }

    private void setupLoginButton(){
        logInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String phoneNum = phoneField.getText();
                if (phoneNum.length() != 11){
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
                    errorLabel.setText("Password correct...");
                }

                if (MySQL.isStaff) {
                    //load staff UI
                    MySQL.getStaff(phoneNum);
                    LoginController.staffID = MySQL.staffID;
                    if (MySQL.staffRole.equals("Receptionist")) {
                        Parent recParent = null;
                        try {
                            recParent = FXMLLoader.load(getClass().getResource("../Receptionist/BookAppointment.fxml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Scene recScene = new Scene(recParent);

                        //Set stage info
                        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                        window.setScene(recScene);
                        window.show();
                    } else if(MySQL.staffRole.equals("Dentist") | MySQL.staffRole.equals("Nurse")) {
                        Parent recParent = null;
                        try {
                            recParent = FXMLLoader.load(getClass().getResource("../StaffSchedule/GUI.fxml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Scene recScene = new Scene(recParent);

                        //Set stage info
                        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                        window.setScene(recScene);
                        window.show();
                    } else {
                        System.out.println("Didn't work");
                    }

                }
                else {
                    System.out.println("patient");
                    //load patient ui
                }
            }
        });
    }

    private void displayErrorMessage(){
        errorLabel.setText("Phone number or password is incorrect");
    }
}
