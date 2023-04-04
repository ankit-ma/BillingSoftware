package Service;

import Model.User;
import com.example.billingsoftware.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;

public class UserService {
    private Connection con;
    public UserService() throws SQLException{
//        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jantastore", "root", "Ankitkumar1@");
        //DatabaseConnection.startDatabaseConnection();
        con = DatabaseConnection.getCon();
    }

    public String addUser(User user){
        try{
            if(user.getUsertype().equals("admin")) return "admin can be only one";
            Statement sm = con.createStatement();
            String query = "INSERT into USER VALUES("+user.getUserid()
                    +",'"+user.getUsername()
                    +"','"+user.getPassword()
                    +"','"+user.getUsertype()+"')";
            sm.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return "false";
        }
        return "true";
    }

    public ArrayList<User> getUsers(){
        ArrayList<User> res = new ArrayList<>();
        try{
            Statement sm = con.createStatement();
            String query="Select * from user";
            ResultSet rs = sm.executeQuery(query);
            while (rs.next()){
                int userid=rs.getInt(1);
                String username = rs.getString(2);
                String password = rs.getString(3);
                String usertype = rs.getString(4);
                res.add(new User(userid,username,password,usertype));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return res;
    }
    public String deleteUser(int id){
        try{
            Statement sm = con.createStatement();
            String query = "Delete from user where userid="+id;
            sm.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return "false";
        }
        return "true";
    }
    public String updateUser(User user){
        try{
            Statement sm = con.createStatement();
            System.out.println(user.toString());
            String query = "Update user set userid="+user.getUserid()+",username='"
            +user.getUsername() +"',password='"+user.getPassword()
                    +"',usertype='"+user.getUsertype()+"' where userid="+user.getUserid();
            sm.execute(query);
        }catch(SQLException sq){
            sq.printStackTrace();
            return "sqlError";
        }
        return "true";
    }

    public boolean adminLoginApproved(String username,String password) throws SQLException {
        Statement sm = con.createStatement();
        String query = "Select username,password from user where usertype= 'admin'";
        ResultSet rs = sm.executeQuery(query);
        while(rs.next()) {
//            String a = rs.getString("username");
//            String b = rs.getString("password");
//            System.out.println(a+" "+b);
            if (rs.getString("username").equals(username) && rs.getString("password").equals(password))
                return true;
        }
        return false;
    }

    public void closeConnection() throws SQLException {
        con.close();
    }

    public String loginEmployee(String username,String password){
        try{
            Statement sm = con.createStatement();
            String query = "SELECT * FROM user where username='"+username+"'";
            ResultSet rs =sm.executeQuery(query);
            while(rs.next()){
                if(rs.getString("password").equals(password))
                    return "true";
            }
        }
        catch(SQLException sq){
            sq.printStackTrace();
            return "false";
        }
        return "false";
    }
    public static void main(String[] args) throws SQLException {
        User user = new User(3,"Aaquib","Aaquib123","cashier2");
        UserService us = new UserService();
//        String res =us.addUser(user);
      //  ArrayList<User> res = us.getUsers();
       // String res = us.deleteUser(3);
       // boolean res = us.adminLoginApproved("Ankit","123456789");
      //  System.out.println(res);
       // System.out.println(us.loginEmployee("Ant","123456789"));
        us.closeConnection();
    }
}
