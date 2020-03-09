package Patient;

import Database.MySQL;
import Login.LoginController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.security.cert.PolicyNode;
import java.util.ResourceBundle;

public class PatientController implements Initializable {
    @FXML
    public BorderPane borderPane;
    @FXML
    public ImageView notificationImage;
    @FXML
    public Text patientName;
    @FXML
    public HBox logout;

    private AppointmentGrid appointmentPage;
    private VBox mainVBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MySQL.getPatientByID(LoginController.patientID);
        appointmentPage = new AppointmentGrid(MySQL.patientID);
        
        getAppointmentsPage();
        patientName.setText(MySQL.patientFirstName + " " + MySQL.patientSurname);

        logout.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                logout(event);
            }
        });
    }

    public void addVBox(VBox vBox){
        borderPane.setCenter(vBox);

        notificationImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                showNotifications();
            }
        });
    }

    private void showNotifications(){
        NotificationDialog dialog = new NotificationDialog();
        addTestData(dialog);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(notificationImage.getScene().getWindow());
        dialog.show();
    }

    private void addTestData(NotificationDialog dialog){
        for (int i = 0; i < 7; i++){
            dialog.addNotification(new NotificationBox("Notification " + i, "Time " + i));
        }
    }

    public void getAppointmentsPage(){
        LoginController.mainVBox.getChildren().clear();
        LoginController.mainVBox.getChildren().add(appointmentPage.getAppointmentsUI());
    }

    public void logout(MouseEvent mouseEvent) {
        Parent recParent = null;
        try {
            recParent = FXMLLoader.load(getClass().getResource("../Login/Login.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene recScene = new Scene(recParent);

        //Set stage info
        Stage window = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        window.setScene(recScene);
        window.show();
    }
}
