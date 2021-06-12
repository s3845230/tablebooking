package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.model.AdminBookingModel;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
        if (adminBookingModel.isDbConnected()){
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
        } catch (Exception e) {}
    }
    //fetches the bookings and information about bookings for a given table and date
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
    //sets a pending booking to taken
    public void approve(ActionEvent event) throws Exception {
        int bookedTable = String.valueOf(tableSelection.getValue()).charAt(6)-'0';
        int selectedBooking = String.valueOf(bookingSelection.getValue()).charAt(0)-'0';
        int selectedUserID = Integer.parseInt(tableUsers.get(bookedTable-1).get(selectedBooking-1));
        adminBookingModel.approveBooking(selectedUserID, "taken");
        refreshDate(event);
    }
    //removes a pending or approved booking
    public void reject(ActionEvent event) throws Exception {
        try {
            int bookedTable = String.valueOf(tableSelection.getValue()).charAt(6)-'0';
            int selectedBooking = String.valueOf(bookingSelection.getValue()).charAt(0)-'0';
            int selectedUserID = Integer.parseInt(tableUsers.get(bookedTable-1).get(selectedBooking-1));
            adminBookingModel.rejectBooking(selectedUserID);
            refreshDate(event);
    } catch (Exception e) {}
    }
    //sets next fortnight to covidsafe
    public void covidMinor(ActionEvent event) throws Exception {
        try {
            for (int i = 0; i < 14; i++) {
                LocalDate tempDate = dateSelection.getValue().plusDays(i);
                adminBookingModel.covidMinor(String.valueOf(tempDate), currentId);
            }
            refreshDate(event);
        } catch (Exception e) {}
    }
    //sets next fortnight to full lockdown
    public void covidMajor(ActionEvent event) throws Exception {
        try {
            for (int i = 0; i < 14; i++) {
                LocalDate tempDate = dateSelection.getValue().plusDays(i);
                adminBookingModel.covidMajor(String.valueOf(tempDate), currentId);
            }
            refreshDate(event);
        } catch (Exception e) {}
    }
    //removes and locked slots for the next fortnight
    public void covidNormal(ActionEvent event) throws Exception {
        try {
            for (int i = 0; i < 14; i++) {
                LocalDate tempDate = dateSelection.getValue().plusDays(i);
                adminBookingModel.covidNormal(String.valueOf(tempDate), currentId);
            }
            refreshDate(event);
        } catch (Exception e) {}
    }
    //generates booking report of the current date with date of creation
    public void generateBookingReport(ActionEvent event) throws IOException {
        LocalDateTime now = LocalDateTime.now();
        String time = "" + now.getYear();
        time += "." + now.getMonthValue();
        time += "." + now.getDayOfMonth();
        time += "." + now.getHour();
        time += "." + now.getMinute();
        time += "." + now.getSecond();
        FileWriter csvWriter = new FileWriter("BookingReportAbout" + dateSelection.getValue() + "At" + time + ".csv");
        csvWriter.append("Date,");
        csvWriter.append(String.valueOf(dateSelection.getValue()));
        csvWriter.append("\n");
        for (int i = 0; i < 9; i++) {
            csvWriter.append("Table " + (i+1) + ",Booking Status,");
            for (int j = 0; j < tableStatuses.get(i).size(); j++) {
                csvWriter.append(tableStatuses.get(i).get(j) + ",");
            }
            csvWriter.append("\n,UserID Involved,");
            for (int j = 0; j < tableUsers.get(i).size(); j++) {
                csvWriter.append(tableUsers.get(i).get(j) + ",");
            }
            csvWriter.append("\n");
        }

        csvWriter.flush();
        csvWriter.close();
    }
}
