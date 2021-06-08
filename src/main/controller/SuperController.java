package main.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

public class SuperController {
    protected int currentId = -1;
    protected boolean isAdmin = false;
    protected String pathToBooking = "/main/ui/booking.fxml";
    protected String pathToBookingAdmin = "/main/ui/bookingAdmin.fxml";
    protected String pathToPDetails = "/main/ui/pDetails.fxml";
    protected String pathToMenu = "/main/ui/menu.fxml";
    protected String pathToLogin = "/main/ui/login.fxml";
    protected String pathToRegister = "/main/ui/register.fxml";
//    public void swapScene();
    void setCurrentId(int currentId) {
    this.currentId = currentId;
}
    void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
    public void setUp() throws SQLException {}

    void swapScene(String path, Stage window) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Scene scene = new Scene(loader.load());
        window.setScene(scene);
        window.show();
        SuperController superController = loader.getController();
        superController.setCurrentId(currentId);
        superController.setIsAdmin(isAdmin);
        superController.setUp();
    }
}
