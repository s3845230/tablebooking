package main.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import main.model.LoginModel;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController extends SuperController implements Initializable {
    public LoginModel loginModel = new LoginModel();
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;


    // Check database connection
    @Override
    public void initialize(URL location, ResourceBundle resources){
        if (loginModel.isDbConnected()){
            currentStatus.setText("Connected");
        }else{
            currentStatus.setText("Not Connected");
        }
    }
    /* login Action method
       check if user input is the same as database.
     */
    public void Login(ActionEvent event) throws Exception{

        try {
            currentId = loginModel.isLogin(txtUsername.getText(),txtPassword.getText());
            if (currentId > 0){
                isAdmin = loginModel.isAdmin(currentId);
                if (isAdmin) {
                    currentStatus.setText("Logged in as admin");
                    swapScene(pathToAdminMenu);
                }
                else {
                    currentStatus.setText("Logged in successfully");
                    swapScene(pathToMenu);
                }
            }else{
                currentStatus.setText("username and password is incorrect");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





    //11.2.3 big sur
//TODO
    // REGISTER -done
    // RESET PASSWORD
    // BOOKING -> GO TO ADMIN
    // ADMIN
        // CANCEL/REJECT BOOKING
        // COVID SAFE SEATING
        // COVID LOCKDOWN SEATS
        // UPDATE ACCOUNTS
        // REGISTER EMPLOYEES
        // GENERATE CSV REPORT

    // MAKE SPECIAL ADMIN MENU THAT ONLY ADMINS CAN ACCESS
    // FIX REFRESH BUTTON IN BOOKING, UNNECESSARY
    //




}
