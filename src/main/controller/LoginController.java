package main.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import main.model.LoginModel;

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
                currentStatus.setText("Logged in successfully");
                if (isAdmin) {
                    swapScene(pathToAdminMenu);
                }
                else {
                    swapScene(pathToMenu);
                }

            }else{
                currentStatus.setText("username and password is incorrect");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




}
