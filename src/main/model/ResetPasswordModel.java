package main.model;

import main.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ResetPasswordModel {

    Connection connection;

    public ResetPasswordModel(){

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

    public ArrayList<String> getDetails(String user) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        String query = "select * from Employees where username = ?";
        ArrayList<String> employeeInformation = new ArrayList<>();
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
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

    public boolean updatePassword(String newPassword, String currentUsername) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            String query = "UPDATE Employees SET password = ? WHERE username = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, currentUsername);

            preparedStatement.executeUpdate();
            return true;
        }
        catch(Exception e){
            return false;
        } finally{
            preparedStatement.close();
        }
    }
}
