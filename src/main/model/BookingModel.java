package main.model;

import main.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookingModel {

    Connection connection;

    public BookingModel(){

        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);

    }

    public Boolean isDbConnected(){
        try {
            return !connection.isClosed();
        }
        catch(Exception e){
            return false;
        }
    }

    public ArrayList<String> tableStatus(String date) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<String> statusList = new ArrayList<>();
        try {
            for (int i = 1; i < 10; i++) {
                boolean found = false;
                String query = "select status from Bookings where date = ? and tableid= ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, date);
                preparedStatement.setString(2, String.valueOf(i));

                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    if (resultSet.getString("status").equals("open") || resultSet.getString("status").equals("taken") || resultSet.getString("status").equals("locked")) {
                        statusList.add(resultSet.getString("status"));
                        found = true;
                    }
                    while (resultSet.next()) {
                        if (resultSet.getString("status").equals("open") || resultSet.getString("status").equals("taken") || resultSet.getString("status").equals("locked")) {
                            statusList.add(resultSet.getString("status"));
                            found = true;
                        }
                    }
                }
                if (!found) {
                    statusList.add("open");
                }
            }
        }
        catch(Exception e){
            return statusList;
        } finally{
            preparedStatement.close();
            resultSet.close();
        }
        return statusList;
    }

    // NEED TO SEND TO ADMIN TO APPROVE
    public void updateBooking(String date, int tableid, int employeeID) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String query = "SELECT * FROM Bookings WHERE employeeid = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, String.valueOf(employeeID));
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String query2 = "UPDATE Bookings SET tableid = ?, status = ?, date = ? WHERE employeeid = ?";
                preparedStatement = connection.prepareStatement(query2);
                preparedStatement.setString(1, String.valueOf(tableid));
                preparedStatement.setString(2, "pending");
                preparedStatement.setString(3, date);
                preparedStatement.setString(4, String.valueOf(employeeID));
                preparedStatement.executeUpdate();

            }
            else {

                String query3 = "INSERT INTO Bookings (date, tableid, employeeid, status) VALUES (?, ?, ?, ?)";
                preparedStatement = connection.prepareStatement(query3);
                preparedStatement.setString(1, date);
                preparedStatement.setString(2, String.valueOf(tableid));
                preparedStatement.setString(3, String.valueOf(employeeID));
                preparedStatement.setString(4, "pending");
                preparedStatement.executeUpdate();

            }
        }
        catch(Exception e){
        } finally{
            preparedStatement.close();
            resultSet.close();
        }
    }
    public void deleteBooking(int employeeID) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            String query = "DELETE FROM Bookings WHERE employeeid = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, String.valueOf(employeeID));
            preparedStatement.executeUpdate();
        }
        catch(Exception e){
        } finally{
            preparedStatement.close();
        }
    }


}
