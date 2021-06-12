package main.controller;

import main.model.ResetPasswordModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.Random;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ResetPasswordController extends SuperController implements Initializable {
    public ResetPasswordModel resetPasswordModel = new ResetPasswordModel();
    String secretQuestionAnswer = "";
    ArrayList<String> currentDetails;
    String targetUsername = "";
    @FXML
    protected Label LabelSecretQuestion;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtSecretQuestionAnswer;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        if (resetPasswordModel.isDbConnected()){
            currentStatus.setText("Connected");
        }else{
            currentStatus.setText("Not Connected");
        }
    }

    public void acceptUsername(ActionEvent event) throws SQLException {
        targetUsername = txtUsername.getText();
        currentDetails = resetPasswordModel.getDetails(targetUsername);
        if (currentDetails.size() > 0) {
            LabelSecretQuestion.setText(currentDetails.get(0));
            secretQuestionAnswer = currentDetails.get(1);
            currentStatus.setText("Found Username");
        }
        else {
            currentStatus.setText("Username Not Found");
        }
    }

    public void generatePassword(ActionEvent event) throws SQLException {
        if (secretQuestionAnswer.equals(txtSecretQuestionAnswer.getText())) {
            Random rand = new Random();
            int newPassword = rand.nextInt(1000000000);
            txtPassword.setText(String.valueOf(newPassword));
            if (resetPasswordModel.updatePassword(String.valueOf(newPassword), targetUsername)) {
                currentStatus.setText("Password Updated");
            }
            else {
                currentStatus.setText("Password not Updated");
            }
        }
        else {
            currentStatus.setText("Secret Question Answer was incorrect");
        }
    }
}
