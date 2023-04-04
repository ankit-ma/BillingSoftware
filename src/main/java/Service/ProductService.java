package Service;

import Model.Product;
import com.example.billingsoftware.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;

public class ProductService {
    private Connection con;
    public ProductService() throws SQLException {
        //con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jantastore", "root", "Ankitkumar1@");
        this.con= DatabaseConnection.getCon();
    }

    public String addProduct(Product product){
        try{
            Statement sm = con.createStatement();
            String query = "INSERT INTO product Values("+product.getProductid()
                    +",'"+product.getProductname()
                    +"',"+product.getMrp()
                    +","+product.getStock()
                    +","+product.getDiscount()
                    +","+product.getSellingprice()+")";
            sm.execute(query);
        }
        catch(SQLException sq){
            sq.printStackTrace();
            return "false";
        }
        return "true";
    }
    public String deleteProduct(int id){
        try{
            Statement sm = con.createStatement();
            String query = "Delete from product where productid="+id;
            sm.execute(query);
        }
        catch(SQLException sq){
            sq.printStackTrace();
            return "false";
        }
        return "true";
    }
    public String updateProduct(Product product){
        try{
            Statement sm = con.createStatement();
            String query = "Update product set stock ="+product.getStock()
                    +",productname='"+product.getProductname()
                    +"',mrp="+product.getMrp()
                    +",discount="+product.getDiscount()
                    +",sellingprice="+product.getSellingprice()+
                    " where productid="+product.getProductid();
            sm.execute(query);
        }
        catch(SQLException sq){
            sq.printStackTrace();
            return "false";
        }
        return "true";
    }
    public ArrayList<Product> showAll(){
        ArrayList<Product> res = new ArrayList<>();
        try{
            Statement sm = con.createStatement();
            String query = "Select * from product";
            ResultSet rs = sm.executeQuery(query);
            while(rs.next()){
                res .add(new Product(
                   rs.getInt(1),
                   rs.getString(2),
                   rs.getDouble(3),
                   rs.getInt(4),
                   rs.getDouble(5),
                        rs.getDouble(6)
                ));
            }
        }
        catch(SQLException sq){
            sq.printStackTrace();
            return null;
        }
        return res;
    }

    public String stockChange(Product product){
        try{
            Statement sm = con.createStatement();
            String query = "Update product set stock ="+product.getStock()+" where productid="+product.getProductid();
            sm.execute(query);
        }
        catch(SQLException sq){
            sq.printStackTrace();
            return "false";
        }
        return "true";
    }
    public ArrayList<String> getAllProductNames(){
        ArrayList<String> arr = new ArrayList<>();
        try{
            Statement sm = con.createStatement();
            String query= "SELECT productname from product";
            ResultSet rs = sm.executeQuery(query);
            while(rs.next()){
                arr.add(rs.getString(1));
            }

        }
        catch(SQLException sq){

        }
        return arr;
    }
    public Product getProductByName(String name){
        Product product = new Product();
        try{
            Statement sm = con.createStatement();
            String query = "Select * from product where productname='"+name+"'";
            ResultSet rs = sm.executeQuery(query);
            while(rs.next()){
                product.setProductid(rs.getInt("productid"));
                product.setProductname(rs.getString("productname"));
                product.setStock(rs.getInt("stock"));
                product.setDiscount(rs.getDouble("discount"));
                product.setMrp(rs.getDouble("mrp"));
                product.setSellingprice(rs.getDouble("sellingprice"));
            }


        }
        catch (SQLException sq){

            System.out.println(sq);
            return null;
        }
        return product;
    }

    public void closeConnection() throws SQLException {
        con.close();
    }

    public static void main(String[] args) throws SQLException {
        ProductService ps = new ProductService();
//        Product product = new Product(2,"AVN",89.34,20,5,78);
//        String res =ps.addProduct(product);
        //String res = ps.deleteProduct(1);
        //product.setStock(product.getStock()-1);
        //String res=ps.stockChange(product);
 //       String res = ps.updateProduct(product);
        ArrayList<Product> res = ps.showAll();
        System.out.println(res);
        ps.closeConnection();
    }
}
