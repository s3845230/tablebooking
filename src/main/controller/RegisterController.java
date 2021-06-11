package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.model.RegisterModel;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RegisterController extends SuperController implements Initializable {
    public RegisterModel registerModel = new RegisterModel();
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private TextField txtRole;
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtSurname;
    @FXML
    private TextArea txtSecretQuestion;
    @FXML
    private PasswordField txtSecretQuestionAnswer;


    @Override
    public void initialize(URL location, ResourceBundle resources){
        if (registerModel.isDbConnected()){
            currentStatus.setText("Connected");
        }else{
            currentStatus.setText("Not Connected");
        }
    }

    public void register(ActionEvent event) throws Exception{
        boolean valid = true;
        ArrayList<String> newDetails = new ArrayList<String>();
        newDetails.add(txtUsername.getText());
        newDetails.add(txtFirstName.getText());
        newDetails.add(txtSurname.getText());
        newDetails.add(txtPassword.getText());
        newDetails.add(txtRole.getText());
        newDetails.add(txtSecretQuestion.getText());
        newDetails.add(txtSecretQuestionAnswer.getText());
        for (int i = 0; i < 7; i++) {
            if (newDetails.get(i).equals("")) {
                valid = false;
            }
        }
        registerModel.createDetails(newDetails, currentId);
    }

}
