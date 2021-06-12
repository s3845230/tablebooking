package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.model.BookingModel;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BookingController extends SuperController implements Initializable {
    public BookingModel bookingModel = new BookingModel();
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

        if (bookingModel.isDbConnected()){
            currentStatus.setText("Connected");
        }else{
            currentStatus.setText("Not Connected");
        }
    }

    //takes new date to find and color the table statuses
    public void refreshDate(ActionEvent event) throws Exception{
        try {
            if (dateSelection.getValue() != null) {
                currentStatus.setText("Connected");
                ArrayList<String> tableStatuses = bookingModel.tableStatus(String.valueOf(dateSelection.getValue()));
                tableSelection.getItems().clear();
                for (int i = 0; i < 9; i++) {
                    String status = tableStatuses.get(i);
                    if (status.equals("open")) {
                        rectangles.get(i).setFill(Color.GREEN);
                        tableSelection.getItems().add("Table " + (i + 1));
                    }
                    if (status.equals("pending")) {
                        rectangles.get(i).setFill(Color.GREEN);
                        tableSelection.getItems().add("Table " + (i + 1));
                    }
                    if (status.equals("taken")) {
                        rectangles.get(i).setFill(Color.RED);
                    }
                    if (status.equals("locked")) {
                        rectangles.get(i).setFill(Color.ORANGE);
                    }
                }
            } else {
                currentStatus.setText("Date required");
            }
        } catch (Exception e) {}
    }
    //submits booking, removing any other bookings owned by the employee
    public void submitBooking(ActionEvent event) throws Exception {
        try {
            int bookedTable = String.valueOf(tableSelection.getValue()).charAt(6)-'0';
            bookingModel.updateBooking(String.valueOf(dateSelection.getValue()), bookedTable, currentId);
        } catch (Exception e) {}
    }
    //removes all bookings by employee
    public void cancelNextBooking(ActionEvent event) throws Exception {
        try {
            bookingModel.deleteBooking(currentId);
        } catch (Exception e) {}
    }
}
