package main.model;

import main.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PDetailsModel {

    Connection connection;

    public PDetailsModel(){

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

    public boolean updateDetails(ArrayList<String> newDetails, int currentId) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            String query = "UPDATE Employees SET username = ?, 'first name' = ?, surname = ?, password = ?, role = ?, 'secret question' = ?, " +
                    "answer = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newDetails.get(0));
            preparedStatement.setString(2, newDetails.get(1));
            preparedStatement.setString(3, newDetails.get(2));
            preparedStatement.setString(4, newDetails.get(3));
            preparedStatement.setString(5, newDetails.get(4));
            preparedStatement.setString(6, newDetails.get(5));
            preparedStatement.setString(7, newDetails.get(6));
            preparedStatement.setString(8, String.valueOf(currentId));

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
