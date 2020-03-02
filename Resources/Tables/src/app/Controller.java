package app;

import app.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class Controller implements Initializable {


        @FXML
        private TableView<User> table_info;

    public static TableView<User> table_info_2;


    @FXML
        private TableColumn<User, String> col_id;

        @FXML
        private TableColumn<User, String> col_name;

        @FXML
        private TableColumn<User, String> col_email;

        @FXML
        private TableColumn<User, String> col_address;

        @FXML
        private TableColumn<User, Button> col_update;

        public static ObservableList<User> data_table;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        table_info_2 = table_info;
initTable();
loadData();
    }
    private void initTable() {
        initCols();
    }
    private void initCols() {
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_address.setCellValueFactory(new PropertyValueFactory<>("address"));
        col_update.setCellValueFactory(new PropertyValueFactory<>("update"));

        editableCols();
    }
    private void editableCols() {
        col_id.setCellFactory(TextFieldTableCell.forTableColumn());
        col_id.setOnEditCommit(e->{
    e.getTableView().getItems().get(e.getTablePosition().getRow()).setId(e.getNewValue());
});

        col_name.setCellFactory(TextFieldTableCell.forTableColumn());
        col_name.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setName(e.getNewValue());
        });
        col_email.setCellFactory(TextFieldTableCell.forTableColumn());
        col_email.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setEmail(e.getNewValue());
        });
        col_address.setCellFactory(TextFieldTableCell.forTableColumn());
        col_address.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setAddress(e.getNewValue());
        });

        table_info.setEditable(true);
    }
    private void loadData(){
        data_table = FXCollections.observableArrayList();
        for (int i = 0; i < 7; i++) {
            data_table.add(new User(String.valueOf(i),
                    "name " + i, "email "+ i, "address " + i, new Button("update")));

        }
        table_info.setItems(data_table);
    }
}
