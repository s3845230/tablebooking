package main.controller;

import javafx.fxml.Initializable;
import main.model.BookingModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController extends SuperController implements Initializable {
    @FXML
    private Button adminMenuButton;

    public BookingModel bookingModel = new BookingModel();
    @Override
    public void initialize(URL location, ResourceBundle resources){
        if (bookingModel.isDbConnected()){
            currentStatus.setText("Connected");
        }else{
            currentStatus.setText("Not Connected");
        }
    }
}
