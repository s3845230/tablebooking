package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import main.model.BookingAdminModel;
import main.model.BookingModel;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BookingAdminController extends SuperController implements Initializable {
    public BookingAdminModel bookingAdminModel = new BookingAdminModel();
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
    @FXML
    private ComboBox tableSelection;
    ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();



//    @FXML
//    private TextField txtUsername;
//    @FXML
//    private TextField txtPassword;


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

        if (bookingAdminModel.isDbConnected()){
            currentStatus.setText("Connected");
        }else{
            currentStatus.setText("Not Connected");
        }
    }


    public void refreshDate(ActionEvent event) throws Exception{
        System.out.println(dateSelection.getValue());
        if (dateSelection.getValue() != null) {
            currentStatus.setText("Connected");
            ArrayList<String> tableStatuses = bookingAdminModel.tableStatus(String.valueOf(dateSelection.getValue()));
            tableSelection.getItems().clear();
            for (int i = 0; i < 9; i++) {
                String status = tableStatuses.get(i);
                if (status.equals("open")) {
                    rectangles.get(i).setFill(Color.GREEN);
                    tableSelection.getItems().add("Table " + (i+1));
                }
                if (status.equals("taken")) {
                    rectangles.get(i).setFill(Color.RED);
                }
                if (status.equals("locked")) {
                    rectangles.get(i).setFill(Color.ORANGE);
                }
            }
        }
        else {
            currentStatus.setText("Date required");
        }
    }

    public void submitBooking(ActionEvent event) throws Exception {
        int bookedTable = String.valueOf(tableSelection.getValue()).charAt(6)-'0';
        bookingAdminModel.updateBooking(String.valueOf(dateSelection.getValue()), bookedTable, currentId);
    }
}
