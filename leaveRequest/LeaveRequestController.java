package leaveRequest;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.time.LocalDate;

public class LeaveRequestController {
    private int staffNum;

    @FXML
    DatePicker startDate;
    @FXML
    DatePicker endDate;
    @FXML
    ComboBox comboBox;
    @FXML
    TextArea textArea;
    @FXML
    Button submit;
    @FXML
    Text error;

    public LeaveRequestController(int staffNum){
        this.staffNum = staffNum;
    }

    public void setupUI(){
        setupDatePickers();
        setupComboBox();
        setupButton();
    }

    public void restrictDatePicker(DatePicker datePicker, LocalDate minDate) {
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(minDate)) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                };
            }
        };
        datePicker.setDayCellFactory(dayCellFactory);
    }

    private void setupDatePickers(){
        restrictDatePicker(startDate, LocalDate.now());
        restrictDatePicker(endDate, LocalDate.now());

        startDate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    if (startDate.getValue().isAfter(endDate.getValue())){
                        endDate.setValue(null);
                    }
                }catch (NullPointerException e){}
            }
        });

        endDate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if (endDate.getValue().isBefore(startDate.getValue())){
                        startDate.setValue(null);
                    }
                } catch (NullPointerException e) { }
            }
        });
    }

    private void setupComboBox(){
        comboBox.getItems().add("Doctors appointment or other health related reason");
        comboBox.getItems().add("Childcare or other other caring responsibility");
        comboBox.getItems().add("Family emergency");
        comboBox.getItems().add("Holiday");
        comboBox.getItems().add("Other");
    }

    private void setupButton(){
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (startDate.getValue() == null || endDate.getValue() == null || comboBox.getValue() == null || textArea.getText().trim().equals("")){
                    error.setText("All boxes must be filled to submit");
                }
                else{
                    String start = startDate.getValue().toString();
                    String end = endDate.getValue().toString();

                    LeaveRequestSQL.makeLeaveRequest(start, end, staffNum);
                    //Currently no way of storing reasons for leave

                    startDate.setValue(null);
                    endDate.setValue(null);
                    comboBox.getSelectionModel().clearSelection();
                    textArea.setText("");
                    error.setText("");
                }
            }
        });
    }
}