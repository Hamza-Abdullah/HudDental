package Patient;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class PatientController {
    @FXML
    public BorderPane borderPane;

    public void addVBox(VBox vBox){
        borderPane.setCenter(vBox);
    }

}
