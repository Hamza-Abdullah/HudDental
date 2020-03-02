package app.model;

import app.Controller;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;

public class User {
    String id, name, email, address;

    Button update;

    public User(String id, String name, String email, String address, Button update) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.update = update;

        update.setOnAction(e -> {

            ObservableList<User> users= Controller.table_info_2.getSelectionModel().getSelectedItems();

            for (User user: users) {
                if (user.getUpdate()== update){
                    System.out.println("id: "+user.getId());
                    System.out.println("name: "+user.getName());
                    System.out.println("email: "+user.getEmail());
                    System.out.println("address: "+user.getAddress());
                }

           }

        });
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Button getUpdate() {
        return update;
    }

    public void setUpdate(Button update) {
        this.update = update;
    }
}
