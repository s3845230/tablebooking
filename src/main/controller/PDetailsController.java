package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.model.BookingModel;
import main.model.PDetailsModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PDetailsController extends SuperController implements Initializable {
    public PDetailsModel pDetailsModel = new PDetailsModel();
    ArrayList<String> currentDetails;
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
        if (pDetailsModel.isDbConnected()){
            currentStatus.setText("Connected");
        }else{
            currentStatus.setText("Not Connected");
        }
    }

    public void setUp() throws SQLException {
        currentDetails = pDetailsModel.getDetails(currentId);
        if (currentDetails.size() > 0) {
            txtUsername.setText(currentDetails.get(0));
            txtFirstName.setText(currentDetails.get(1));
            txtSurname.setText(currentDetails.get(2));
            txtRole.setText(currentDetails.get(4));
            txtSecretQuestion.setText(currentDetails.get(5));
        }
    }

    public void submitPDetails(ActionEvent event) throws Exception{
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
                newDetails.set(i, currentDetails.get(i));
            }
        }
        pDetailsModel.updateDetails(newDetails, currentId);
    }
//    String query = "UPDATE Employees SET username = ? AND name = ? AND 'sure name' = ? AND password = ? AND role = ? AND " +
//            "'secret question' = ? AND answer = ? WHERE id = ?";

}
