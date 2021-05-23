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

public class PDetailsController implements Initializable {
    Stage window;
    public PDetailsModel pDetailsModel = new PDetailsModel();
    private String currentUsername;
    ArrayList<String> currentDetails;
    @FXML
    private Label isConnected;
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
            isConnected.setText("Connected");
        }else{
            isConnected.setText("Not Connected");
        }
    }

    void detailsSetUp() throws SQLException {
        currentDetails = pDetailsModel.getDetails(currentUsername);
        if (currentDetails.size() > 0) {
            txtUsername.setText(currentUsername);
            txtFirstName.setText(currentDetails.get(1));
            txtSurname.setText(currentDetails.get(2));
            txtRole.setText(currentDetails.get(4));
            txtSecretQuestion.setText(currentDetails.get(5));
        }
    }

    public void submitPDetails(ActionEvent event) throws Exception{

    }

    public void pathToMenu(ActionEvent event) throws Exception{

        window = (Stage) isConnected.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/ui/menu.fxml"));
        Scene scene = new Scene(loader.load());
        window.setScene(scene);
        window.show();
        MenuController menuController = loader.getController();
        menuController.setCurrentUsername(currentUsername);
    }

    void setCurrentUsername(String currentUsername) {
        this.currentUsername = currentUsername;
    }

}
