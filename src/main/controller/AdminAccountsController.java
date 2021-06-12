package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.model.AdminAccountsModel;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminAccountsController extends SuperController implements Initializable {
    public AdminAccountsModel adminAccountsModel = new AdminAccountsModel();
    ArrayList<String> currentDetails;
    @FXML
    private ComboBox txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtRole;
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtSurname;
    @FXML
    private TextField txtID;
    @FXML
    private TextArea txtSecretQuestion;
    @FXML
    private TextField txtSecretQuestionAnswer;
    @FXML
    private CheckBox isAdminBox;


    @Override
    public void initialize(URL location, ResourceBundle resources){
        if (adminAccountsModel.isDbConnected()){
            currentStatus.setText("Connected");
        }else{
            currentStatus.setText("Not Connected");
        }
    }

    //loads the user into the textboxes based off of given username
    public void loadUser(ActionEvent event) throws SQLException {
        currentDetails = adminAccountsModel.getDetails(String.valueOf(txtUsername.getValue()));
        if (currentDetails.size() > 0) {
            txtID.setText(currentDetails.get(0));
            txtFirstName.setText(currentDetails.get(1));
            txtSurname.setText(currentDetails.get(2));
            txtPassword.setText(currentDetails.get(3));
            txtRole.setText(currentDetails.get(4));
            txtSecretQuestion.setText(currentDetails.get(5));
            txtSecretQuestionAnswer.setText(currentDetails.get(6));
            if (currentDetails.get(7).equals("true")) {
                isAdminBox.setSelected(true);
            }
            else {
                isAdminBox.setSelected(false);
            }
        }
        else {
            txtID.setText("");
            txtFirstName.setText("");
            txtSurname.setText("");
            txtPassword.setText("");
            txtRole.setText("");
            txtSecretQuestion.setText("");
            txtSecretQuestionAnswer.setText("");
            isAdminBox.setSelected(false);
        }
    }
    //finds all usernames available
    public void setUp() {
        try {
            txtUsername.getItems().clear();
            ArrayList<String> applicableAccounts = adminAccountsModel.findAllUsers(String.valueOf(txtUsername.getValue()));
            for (int i = 0; i < applicableAccounts.size(); i++) {
                txtUsername.getItems().add(applicableAccounts.get(i));
            }
        } catch (Exception e) {}
    }
    //finds usernames that include the currently typed phrase
    public void findUsers(ActionEvent event) throws SQLException {
        try {
            if (txtUsername.getValue().equals("")) {
                setUp();
            }
            else {
                txtUsername.getItems().clear();
                ArrayList<String> applicableAccounts = adminAccountsModel.findUsers(String.valueOf(txtUsername.getValue()));
                for (int i = 0; i < applicableAccounts.size(); i++) {
                    txtUsername.getItems().add(applicableAccounts.get(i));
                }
            }
        } catch (Exception e) {setUp();}
    }
    public void deleteAccount(ActionEvent event) throws SQLException {
        try {
            adminAccountsModel.deleteAccount(String.valueOf(txtUsername.getValue()));
            currentStatus.setText("Account deleted");
        } catch (Exception e) {currentStatus.setText("Error in account deletion");}
    }
    public void generateAccountReport(ActionEvent event) {
        try {
            LocalDateTime now = LocalDateTime.now();
            String time = "" + now.getYear();
            time += "." + now.getMonthValue();
            time += "." + now.getDayOfMonth();
            time += "." + now.getHour();
            time += "." + now.getMinute();
            time += "." + now.getSecond();
            FileWriter csvWriter = new FileWriter("AccountReportAt" + time + ".csv");
            csvWriter.append("ID,Username,First Name, Surname, Role, Password, Secret Question, Secret Answer, isAdmin\n");
            ArrayList<ArrayList<String>> accounts = adminAccountsModel.getAllAccounts();
            for (int i = 0; i < accounts.size(); i++) {
                for (int j = 0; j < accounts.get(i).size(); j++) {
                    csvWriter.append(accounts.get(i).get(j) + ",");
                }
                csvWriter.append("\n");
            }
            csvWriter.flush();
            csvWriter.close();
        } catch (IOException | SQLException e) {
        }
    }

    public void updateAccount(ActionEvent event) throws Exception{
        try {
            ArrayList<String> newDetails = new ArrayList<String>();
            newDetails.add(txtID.getText());
            newDetails.add(txtFirstName.getText());
            newDetails.add(txtSurname.getText());
            newDetails.add(txtPassword.getText());
            newDetails.add(txtRole.getText());
            newDetails.add(txtSecretQuestion.getText());
            newDetails.add(txtSecretQuestionAnswer.getText());
            if (isAdminBox.isSelected()) {
                newDetails.add("true");
            }
            else {
                newDetails.add("false");
            }
            for (int i = 0; i < newDetails.size(); i++) {
                //if there are missing slots, keeps old information
                if (newDetails.get(i).equals("")) {
                    newDetails.set(i, currentDetails.get(i));
                }
            }
            if (adminAccountsModel.updateDetails(newDetails, String.valueOf(txtUsername.getValue()))) {
                currentStatus.setText("Details Updated");
            }
            else {
                currentStatus.setText("Details not Updated");
            }
        } catch (Exception e) {currentStatus.setText("Update Failed");}
    }
    public void createAccount(ActionEvent event) throws Exception{
        try {
            boolean valid = true;
            ArrayList<String> newDetails = new ArrayList<String>();
            newDetails.add(String.valueOf(txtUsername.getValue()));
            newDetails.add(txtFirstName.getText());
            newDetails.add(txtSurname.getText());
            newDetails.add(txtPassword.getText());
            newDetails.add(txtRole.getText());
            newDetails.add(txtSecretQuestion.getText());
            newDetails.add(txtSecretQuestionAnswer.getText());
            if (isAdminBox.isSelected()) {
                newDetails.add("true");
            } else {
                newDetails.add("false");
            }
            newDetails.add(txtID.getText());

            for (int i = 0; i < 9; i++) {
                if (newDetails.get(i).equals("")) {
                    valid = false;
                    //checks if data is unfilled
                }
            }
            if (adminAccountsModel.createDetails(newDetails, currentId) && valid) {
                currentStatus.setText("Details Updated");
            } else {
                currentStatus.setText("Details not Updated");
            }
        } catch (Exception e) {}
    }
}
