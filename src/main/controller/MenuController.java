package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import main.model.LoginModel;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController extends SuperController implements Initializable {
    public LoginModel loginModel = new LoginModel();
    @Override
    public void initialize(URL location, ResourceBundle resources){
        if (loginModel.isDbConnected()){
            currentStatus.setText("Connected");
        }else{
            currentStatus.setText("Not Connected");
        }
    }
}
