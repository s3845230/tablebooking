package main.model;

import main.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RegisterModel {

    Connection connection;

    public RegisterModel(){

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

    public void createDetails(ArrayList<String> newDetails, int currentId) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            String query = "INSERT INTO Employees (username, 'first name', surname, password, role, 'secret question', answer) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newDetails.get(0));
            preparedStatement.setString(2, newDetails.get(1));
            preparedStatement.setString(3, newDetails.get(2));
            preparedStatement.setString(4, newDetails.get(3));
            preparedStatement.setString(5, newDetails.get(4));
            preparedStatement.setString(6, newDetails.get(5));
            preparedStatement.setString(7, newDetails.get(6));
            System.out.println(preparedStatement);

            preparedStatement.executeUpdate();

        }
        catch(Exception e){
        } finally{
            preparedStatement.close();
        }
    }
}
