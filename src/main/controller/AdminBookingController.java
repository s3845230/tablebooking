package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import main.model.AdminBookingModel;
import main.model.BookingModel;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminBookingController extends SuperController implements Initializable {
    public AdminBookingModel adminBookingModel = new AdminBookingModel();
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
    @FXML
    private ComboBox bookingSelection;
    ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();
    ArrayList<ArrayList<String>> tableStatuses = new ArrayList<>();
    ArrayList<ArrayList<String>> tableUsers = new ArrayList<>();



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
//        for (int i = 0; i < 9; i++) {
//            ArrayList<String> individualTableStatuses = new ArrayList<>();
//            ArrayList<String> individualTableUsers = new ArrayList<>();
//            tableStatuses.add(individualTableStatuses);
//            tableUsers.add(individualTableUsers);
//        }

        if (adminBookingModel.isDbConnected()){
            currentStatus.setText("Connected");
        }else{
            currentStatus.setText("Not Connected");
        }
    }


    public void refreshDate(ActionEvent event) throws Exception{
        if (dateSelection.getValue() != null) {
            currentStatus.setText("Connected");
            tableStatuses.clear();
            tableUsers.clear();
            adminBookingModel.tableStatus(String.valueOf(dateSelection.getValue()), tableStatuses, tableUsers);
            tableSelection.getItems().clear();
            bookingSelection.getItems().clear();
            for (int i = 0; i < 9; i++) {
                String status = "";
                for (int j = 0; j < tableStatuses.get(i).size(); j++) {
                    if (tableStatuses.get(i).get(j).equals("open") || tableStatuses.get(i).get(j).equals("closed") || tableStatuses.get(i).get(j).equals("locked")) {
                        status = tableStatuses.get(i).get(j);
                    }
                    else if (status.equals("")) {
                        status = tableStatuses.get(i).get(j);
                    }
                }
                if (status.equals("open")) {
                    rectangles.get(i).setFill(Color.GREEN);
                }
                if (status.equals("pending")) {
                    rectangles.get(i).setFill(Color.PINK);
                }
                if (status.equals("taken")) {
                    rectangles.get(i).setFill(Color.RED);
                }
                if (status.equals("locked")) {
                    rectangles.get(i).setFill(Color.ORANGE);
                }
                tableSelection.getItems().add("Table " + (i+1));
            }
        }
        else {
            currentStatus.setText("Date required");
        }
    }

    public void fetchBookings(ActionEvent event) throws Exception {
        try {
            bookingSelection.getItems().clear();
            int bookedTable = String.valueOf(tableSelection.getValue()).charAt(6) - '0' - 1;
            String currentInformation = "";
            ArrayList<String> employeeInformation = new ArrayList<>();
            for (int i = 0; i < tableUsers.get(bookedTable).size(); i++) {
                currentInformation += (i + 1);
                currentInformation += ". Status: ";
                //            bookingSelection.getItems().add(
                currentInformation += tableStatuses.get(bookedTable).get(i);
                if (!tableUsers.get(bookedTable).get(i).equals("-1")) {
                    currentInformation += " - ";
                    employeeInformation = adminBookingModel.getDetails(Integer.parseInt(tableUsers.get(bookedTable).get(i)));
                    currentInformation += "Username: ";
                    currentInformation += employeeInformation.get(0);
                    currentInformation += " - Name: ";
                    currentInformation += employeeInformation.get(1);
                    currentInformation += " ";
                    currentInformation += employeeInformation.get(2);
                    bookingSelection.getItems().add(currentInformation);
                }
            }
        } catch (Exception e) {}
    }

    public void approve(ActionEvent event) throws Exception {
        int bookedTable = String.valueOf(tableSelection.getValue()).charAt(6)-'0';
        int selectedBooking = String.valueOf(bookingSelection.getValue()).charAt(0)-'0';
        int selectedUserID = Integer.parseInt(tableUsers.get(bookedTable-1).get(selectedBooking-1));
        adminBookingModel.approveBooking(selectedUserID, "taken");
        refreshDate(event);
    }

    public void reject(ActionEvent event) throws Exception {
        int bookedTable = String.valueOf(tableSelection.getValue()).charAt(6)-'0';
        int selectedBooking = String.valueOf(bookingSelection.getValue()).charAt(0)-'0';
        int selectedUserID = Integer.parseInt(tableUsers.get(bookedTable-1).get(selectedBooking-1));
        adminBookingModel.rejectBooking(selectedUserID);
        refreshDate(event);
    }
//
//    public void submitBooking(ActionEvent event) throws Exception {
//        int bookedTable = String.valueOf(tableSelection.getValue()).charAt(6)-'0';
//        adminBookingModel.updateBooking(String.valueOf(dateSelection.getValue()), bookedTable, currentId);
//    }
}
