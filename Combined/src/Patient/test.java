package Patient;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class test {
    private void draft()
    {
        Parent patParent = null;
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(getClass().getResource("../Patient/PatientAppointmentGUI.fxml"));

            patParent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene patScene = new Scene(patParent, width, height);
        //Set stage info
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(patScene);
        window.show();
    }
}
