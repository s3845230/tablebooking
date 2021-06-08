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
    Stage window;
    @FXML
    private Label isConnected;

//    void setCurrentUsername(String currentUsername) {
//        this.currentUsername = currentUsername;
//    }
    @Override
    public void initialize(URL location, ResourceBundle resources){
        if (loginModel.isDbConnected()){
            isConnected.setText("Connected");
        }else{
            isConnected.setText("Not Connected");
        }
    }

    public void pathToBooking(ActionEvent event) throws Exception{
        window = (Stage) isConnected.getScene().getWindow();
        swapScene(pathToBooking,window);
    }

    public void pathToLogin(ActionEvent event) throws Exception{
        window = (Stage) isConnected.getScene().getWindow();
        swapScene(pathToLogin,window);
    }

    public void pathToPDetails(ActionEvent event) throws Exception{
        window = (Stage) isConnected.getScene().getWindow();
        swapScene(pathToPDetails,window);
    }
}
