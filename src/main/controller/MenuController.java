package main.controller;

import javafx.fxml.Initializable;
import main.model.LoginModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MenuController extends SuperController implements Initializable {
    @FXML
    private Button adminMenuButton;

    public LoginModel loginModel = new LoginModel();
    @Override
    public void initialize(URL location, ResourceBundle resources){
        if (loginModel.isDbConnected()){
            currentStatus.setText("Connected");
        }else{
            currentStatus.setText("Not Connected");
        }
    }
    public void setUp() throws SQLException {
        if (!isAdmin) {
            adminMenuButton.setVisible(false);
        }
    }
}
