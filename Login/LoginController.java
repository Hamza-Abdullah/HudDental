package login;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;

public class LoginController {

    @FXML
    public HBox phoneHBox;
    @FXML
    public Button logInButton;
    @FXML
    public PasswordField passwordField;
    @FXML
    public Label errorLabel;

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

                int isRegistered = LoginQueries.isRegistered(phoneNum);

                if (isRegistered == LoginQueries.NUMBER_NOT_FOUND){
                    displayErrorMessage();
                    return;
                }

                if (!BCrypt.checkpw(passwordField.getText(), LoginQueries.getPassword(isRegistered, phoneNum))){
                    displayErrorMessage();
                    return;
                }

                if (isRegistered == LoginQueries.PATIENT_NUMBER){
                    //load patient UI
                    System.out.println("Patient");
                }
                else {
                    //load staff UI
                    System.out.println("Staff");
                }
            }
        });
    }

    private void displayErrorMessage(){
        errorLabel.setText("Phone number or password is incorrect");
    }
}
