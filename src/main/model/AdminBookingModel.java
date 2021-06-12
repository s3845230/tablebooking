package main.model;

import main.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminBookingModel{
    Connection connection;

    public AdminBookingModel(){

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

    public void tableStatus(String date, ArrayList<ArrayList<String>> statusList, ArrayList<ArrayList<String>> tableUsers) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            for (int i = 0; i < 9; i++) {
                //might need to make outside method?
                ArrayList<String> individualTableStatuses = new ArrayList<>();
                ArrayList<String> individualTableUsers = new ArrayList<>();
                resultSet = null;
                String query = "select status, employeeid from Bookings where date = ? and tableid= ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, date);
                preparedStatement.setString(2, String.valueOf(i+1));

                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    individualTableStatuses.add(resultSet.getString("status"));
                    individualTableUsers.add(resultSet.getString("employeeid"));
                }
                else {
                    individualTableStatuses.add("open");
                    individualTableUsers.add("-1");
                }
                while (resultSet.next()) {
                    individualTableStatuses.add(resultSet.getString("status"));
                    individualTableUsers.add(resultSet.getString("employeeid"));
                }
                tableUsers.add(individualTableUsers);
                statusList.add(individualTableStatuses);
            }
        }
        catch(Exception e){
        } finally{
            preparedStatement.close();
            resultSet.close();
        }
    }

    public ArrayList<String> getDetails(int user) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        String query = "select * from Employees where id = ?";
        ArrayList<String> employeeInformation = new ArrayList<>();
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, String.valueOf(user));

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                employeeInformation.add(""+resultSet.getString("username"));
                employeeInformation.add(""+resultSet.getString("first name"));
                employeeInformation.add(""+resultSet.getString("surname"));
                employeeInformation.add(""+resultSet.getString("password"));
                employeeInformation.add(""+resultSet.getString("role"));
                employeeInformation.add(""+resultSet.getString("secret question"));
                employeeInformation.add(""+resultSet.getString("answer"));
            }
        }
        catch (Exception e)
        {
        }finally {
            preparedStatement.close();
            resultSet.close();
            return employeeInformation;
        }

    }
    public void approveBooking(int employeeID, String status) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("UPDATE Bookings SET status = ? WHERE employeeid = ?");
            preparedStatement.setString(1, status);
            preparedStatement.setString(2, String.valueOf(employeeID));

            preparedStatement.executeUpdate();
        }
        catch(Exception e){
        } finally{
            preparedStatement.close();
        }
    }

    public void rejectBooking(int employeeID) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM Bookings WHERE employeeid = ?");
            preparedStatement.setString(1, String.valueOf(employeeID));

            preparedStatement.executeUpdate();
        }
        catch(Exception e){
        }
    }
}
