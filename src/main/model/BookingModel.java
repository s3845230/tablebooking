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

//    public void dateCheck() throws SQLException{
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet=null;
//        ArrayList<String> statusList = new ArrayList<>();
//        try {
//            String query = "select date from Bookings where tableid= 1";
//            preparedStatement = connection.prepareStatement(query);
////            preparedStatement.setString(1, sqlDate);
//            resultSet = preparedStatement.executeQuery();
//            System.out.println(resultSet.getString("date"));
//        }
//        catch (Exception e) {
//        } finally {
//            preparedStatement.close();
//            resultSet.close();
//        }
//    }

    public ArrayList<String> tableStatus(String date) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<String> statusList = new ArrayList<>();
        try {
            for (int i = 1; i < 10; i++) {
                String query = "select status from Bookings where date = ? and tableid= ?";
                preparedStatement = connection.prepareStatement(query);
//                System.out.println(date);
//                System.out.println(i);
                preparedStatement.setString(1, date);
                preparedStatement.setString(2, String.valueOf(i));
//                System.out.println(preparedStatement);

                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
//                    System.out.println("test");
                    statusList.add(resultSet.getString("status"));
//                    System.out.println(statusList.get(i-1));
                }
                else {
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
    public void updateBooking(String date, int tableid) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String query = "SELECT date FROM Bookings WHERE date = ? and tableid= ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, date);
            preparedStatement.setString(2, String.valueOf(tableid));

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                query = "UPDATE Bookings SET status = ? WHERE date = ? and tableid= ?";
                System.out.println(query);
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, "taken");
                preparedStatement.setString(2, date);
                preparedStatement.setString(3, String.valueOf(tableid));

                preparedStatement.executeUpdate();
            }
            else {
                query = "INSERT INTO Bookings (date, tableid, status) VALUES (?, ?, ?)";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, date);
                preparedStatement.setString(2, String.valueOf(tableid));
                preparedStatement.setString(3, "taken");
                System.out.println(preparedStatement);
                preparedStatement.executeUpdate();
            }
        }
        catch(Exception e){
        } finally{
            preparedStatement.close();
            resultSet.close();
        }
    }

//    public Boolean isLogin(String user, String pass) throws SQLException {
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet=null;
//        String query = "select * from employee where username = ? and password= ?";
//        try {
//
//            preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setString(1, user);
//            preparedStatement.setString(2, pass);
//
//            resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                return true;
//            }
//            else{
//                return false;
//            }
//        }
//        catch (Exception e)
//        {
//            return false;
//        }finally {
//           preparedStatement.close();
//           resultSet.close();
//        }
//
//    }

}
