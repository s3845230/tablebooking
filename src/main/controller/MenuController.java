package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import main.model.LoginModel;

public class MenuController {
    public LoginModel loginModel = new LoginModel();
    Stage window;
    @FXML
    private Label isConnected;

    private String currentUsername;
    void setCurrentUsername(String currentUsername) {
        this.currentUsername = currentUsername;
    }

    public void pathToBooking(ActionEvent event) throws Exception{

        window = (Stage) isConnected.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/ui/booking.fxml"));
        Scene scene = new Scene(loader.load());
        window.setScene(scene);
        window.show();
        BookingController bookingController = loader.getController();
        bookingController.setCurrentUsername(currentUsername);
    }

    public void pathToPDetails(ActionEvent event) throws Exception{

        window = (Stage) isConnected.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/ui/pDetails.fxml"));
        Scene scene = new Scene(loader.load());
        window.setScene(scene);
        window.show();
        PDetailsController pDetailsController = loader.getController();
        pDetailsController.setCurrentUsername(currentUsername);
        pDetailsController.detailsSetUp();
    }
}
