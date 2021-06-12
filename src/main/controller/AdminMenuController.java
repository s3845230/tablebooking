package main.controller;

import javafx.fxml.Initializable;
import main.model.LoginModel;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminMenuController extends SuperController implements Initializable {
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
