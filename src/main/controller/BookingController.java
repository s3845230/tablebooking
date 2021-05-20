package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.model.BookingModel;
import org.w3c.dom.css.Rect;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class BookingController implements Initializable {
    public BookingModel bookingModel = new BookingModel();
    @FXML
    private Label isConnected;
    @FXML
    private Rectangle Table1;
    @FXML
    private Rectangle Table2;
    @FXML
    private Rectangle Table3;
    @FXML
    private Rectangle Table4;
    @FXML
    private Rectangle Table5;
    @FXML
    private Rectangle Table6;
    @FXML
    private Rectangle Table7;
    @FXML
    private Rectangle Table8;
    @FXML
    private Rectangle Table9;
    @FXML
    private DatePicker dateSelection;
    ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();



//    @FXML
//    private TextField txtUsername;
//    @FXML
//    private TextField txtPassword;


    // Check database connection
    @Override
    public void initialize(URL location, ResourceBundle resources){
        rectangles.add(Table1);
        rectangles.add(Table2);
        rectangles.add(Table3);
        rectangles.add(Table4);
        rectangles.add(Table5);
        rectangles.add(Table6);
        rectangles.add(Table7);
        rectangles.add(Table8);
        rectangles.add(Table9);

        if (bookingModel.isDbConnected()){
            isConnected.setText("Connected");
        }else{
            isConnected.setText("Not Connected");
        }
    }

    public void refreshDate(ActionEvent event) throws Exception{
        System.out.println(dateSelection.getValue());
        if (dateSelection.getValue() != null) {
            ArrayList<String> tableStatuses = bookingModel.tableStatus(String.valueOf(dateSelection.getValue()));
            for (int i = 0; i < 9; i++) {
                String status = tableStatuses.get(i);
                if (status.equals("open")) {
                    rectangles.get(i).setFill(Color.GREEN);
                }
                if (status.equals("taken")) {
                    rectangles.get(i).setFill(Color.RED);
                }
                if (status.equals("locked")) {
                    rectangles.get(i).setFill(Color.ORANGE);
                }
            }
        }
    }
    /* login Action method
       check if user input is the same as database.
     */
//    public void Login(ActionEvent event){
//
//        try {
//            if (loginModel.isLogin(txtUsername.getText(),txtPassword.getText())){
//
//                isConnected.setText("Logged in successfully");
//            }else{
//                isConnected.setText("username and password is incorrect");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }




    //11.2.3 big sur



}