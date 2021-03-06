package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.sql.SQLException;

//supercontoller made for easy access to all the pathToFile methods
public class SuperController {
    @FXML
    protected Label currentStatus;
    @FXML
    protected Label txtShownUsername;
    Stage window;

    protected int currentId = -1;
    protected boolean isAdmin = false;
    protected String pathToBooking = "/main/ui/booking.fxml";
    protected String pathToPDetails = "/main/ui/pDetails.fxml";
    protected String pathToMenu = "/main/ui/menu.fxml";
    protected String pathToLogin = "/main/ui/login.fxml";
    protected String pathToRegister = "/main/ui/register.fxml";
    protected String pathToResetPassword = "/main/ui/resetPassword.fxml";
    protected String pathToAdminMenu = "/main/ui/adminMenu.fxml";
    protected String pathToAdminBooking = "/main/ui/adminBooking.fxml";
    protected String pathToAdminAccounts = "/main/ui/adminAccounts.fxml";

    void setCurrentId(int currentId) {
    this.currentId = currentId;
}
    void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
    public void setUp() throws SQLException {}

    //swaps the current shown stage to a premade pathway
    void swapScene(String path) throws Exception {
        window = (Stage) currentStatus.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Scene scene = new Scene(loader.load());
        window.setScene(scene);
        window.show();
        SuperController superController = loader.getController();
        superController.setCurrentId(currentId);
        superController.setIsAdmin(isAdmin);
        superController.setUp();
    }

    public void pathToLogin(ActionEvent event) throws Exception{
        swapScene(pathToLogin);
    }
    public void pathToMenu(ActionEvent event) throws Exception{
        swapScene(pathToMenu);
    }
    public void pathToBooking(ActionEvent event) throws Exception{
        swapScene(pathToBooking);
    }
    public void pathToPDetails(ActionEvent event) throws Exception{
        swapScene(pathToPDetails);
    }
    public void pathToResetPassword(ActionEvent event) throws Exception {
        swapScene(pathToResetPassword);
    }
    public void pathToRegister(ActionEvent event) throws Exception {
        swapScene(pathToRegister);
    }
    public void pathToAdminBooking(ActionEvent event) throws Exception {
        swapScene(pathToAdminBooking);
    }
    public void pathToAdminAccounts(ActionEvent event) throws Exception {
        swapScene(pathToAdminAccounts);
    }
    public void pathToAdminMenu(ActionEvent event) throws Exception {
        swapScene(pathToAdminMenu);
    }
}
