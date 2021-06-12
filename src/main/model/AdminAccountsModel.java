package main.model;

import main.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminAccountsModel {

    Connection connection;

    public AdminAccountsModel(){

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
                employeeInformation.add(""+resultSet.getString("id"));
                employeeInformation.add(""+resultSet.getString("first name"));
                employeeInformation.add(""+resultSet.getString("surname"));
                employeeInformation.add(""+resultSet.getString("password"));
                employeeInformation.add(""+resultSet.getString("role"));
                employeeInformation.add(""+resultSet.getString("secret question"));
                employeeInformation.add(""+resultSet.getString("answer"));
                employeeInformation.add(""+resultSet.getString("is admin"));
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

    public boolean updateDetails(ArrayList<String> newDetails, String currentUsername) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            String query = "UPDATE Employees SET id = ?, 'first name' = ?, 'surname' = ?, password = ?, role = ?, 'secret question' = ?, answer = ?, 'is admin' = ? WHERE username = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newDetails.get(0));
            preparedStatement.setString(2, newDetails.get(1));
            preparedStatement.setString(3, newDetails.get(2));
            preparedStatement.setString(4, newDetails.get(3));
            preparedStatement.setString(5, newDetails.get(4));
            preparedStatement.setString(6, newDetails.get(5));
            preparedStatement.setString(7, newDetails.get(6));
            preparedStatement.setString(8, newDetails.get(7));
            preparedStatement.setString(9, String.valueOf(currentUsername));
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
            return true;

        }
        catch(Exception e){
            return false;
        } finally{
            preparedStatement.close();
        }
    }
    public void deleteAccount(String user) throws SQLException {
        PreparedStatement preparedStatement = null;
        String query = "DELETE FROM Employees WHERE username = ?";
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user);

            preparedStatement.executeUpdate();
        }
        catch (Exception e)
        {
        }finally {
            preparedStatement.close();
        }

    }

    public ArrayList<String> findUsers(String targetUsername) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        String query = "select * from Employees where UPPER(username) LIKE ?";
        ArrayList<String> names = new ArrayList<>();
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + targetUsername.toUpperCase() + "%");

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                names.add(resultSet.getString("username"));
            }
        }
        catch (Exception e)
        {
        }finally {
            preparedStatement.close();
            resultSet.close();
            return names;
        }
    }

    public ArrayList<String> findAllUsers(String targetUsername) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        String query = "select * from Employees";
        ArrayList<String> names = new ArrayList<>();
        try {

            preparedStatement = connection.prepareStatement(query);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                names.add(resultSet.getString("username"));
            }
        }
        catch (Exception e)
        {
        }finally {
            preparedStatement.close();
            resultSet.close();
            return names;
        }
    }

    public boolean createDetails(ArrayList<String> newDetails, int currentId) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            String query = "INSERT INTO Employees (id, username, 'first name', surname, password, role, 'secret question', answer, 'is admin') " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newDetails.get(8));
            preparedStatement.setString(2, newDetails.get(0));
            preparedStatement.setString(3, newDetails.get(1));
            preparedStatement.setString(4, newDetails.get(2));
            preparedStatement.setString(5, newDetails.get(3));
            preparedStatement.setString(6, newDetails.get(4));
            preparedStatement.setString(7, newDetails.get(5));
            preparedStatement.setString(8, newDetails.get(6));
            preparedStatement.setString(9, newDetails.get(7));

            preparedStatement.executeUpdate();
            return true;

        }
        catch(Exception e){
            return false;
        } finally{
            preparedStatement.close();
        }
    }
    public ArrayList<ArrayList<String>> getAllAccounts() throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        String query = "select * from Employees";
        ArrayList<ArrayList<String>> accounts = new ArrayList<>();
        try {

            preparedStatement = connection.prepareStatement(query);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ArrayList<String> indivAccount = new ArrayList<>();
                indivAccount.add(resultSet.getString("id"));
                indivAccount.add(resultSet.getString("username"));
                indivAccount.add(resultSet.getString("first name"));
                indivAccount.add(resultSet.getString("surname"));
                indivAccount.add(resultSet.getString("role"));
                indivAccount.add(resultSet.getString("password"));
                indivAccount.add(resultSet.getString("secret question"));
                indivAccount.add(resultSet.getString("answer"));
                indivAccount.add(resultSet.getString("is admin"));
                accounts.add(indivAccount);
            }
        }
        catch (Exception e)
        {
        }finally {
            preparedStatement.close();
            resultSet.close();
            return accounts;
        }
    }
}
