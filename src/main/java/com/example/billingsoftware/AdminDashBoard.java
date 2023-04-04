package com.example.billingsoftware;

import Model.Product;
import Model.User;
import Service.ProductService;
import Service.UserService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import util.DicountCalculator;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminDashBoard implements Initializable {
    public Tab productListTab;
    public Tab manageStaffTab;
    public Button addUserButton;
    public TextField userNameText;
    public TextField userIdText;
    public PasswordField passwordText;

    public TextField updateUsername;
    public TextField updateUserType;
    public PasswordField updatepassword;
    public Button updateUserButton;

    public Button deleteuserButton;
    public TableView<User> userTable;
    public Button logoutButton;
    public AnchorPane admindashboard;
    public TableColumn<User,Integer> userid;
    public TableColumn<User,String> username;
    public TableColumn<User,String> password;
    public TableColumn<User,String> usertype;
    public TextField usertypeText;
    
    public AnchorPane deleteProductButton;
    public TextField productIDText;
    public TextField productNameText;
    public TextField stockText;
    public TextField mrpText;
    public TextField discountText;
    public TextField sellingPriceText;
    public Button addItemButton;

    public TableView<Product> productTable;
    public TableColumn<Product,Integer> productIDColumn;
    public TableColumn<Product,String> productNameColumn;
    public TableColumn<Product,Double> mrpColumn;
    public TableColumn<Product,Double> discountColumn;
    public TableColumn<Product,Double> sellingPriceColumn;
    public TableColumn<Product,Integer> stockColumn;

    public TextField updateproductNameText;
    public TextField updatemrpText;
    public TextField updatediscountText;
    public TextField updatesellingPriceText;
    public Button updateButton;
    public TextField updateStocktext;
    public Button equalButton;
    public Button updatedicountCalButton;
    public TabPane tabpane;
    public Button deleteButton;

    private ProductService ps;
    private UserService us;
    public void addUserButtonListner(ActionEvent actionEvent) {
            us.addUser(new User(Integer.parseInt(userIdText.getText()),
                    userNameText.getText(),
                    passwordText.getText(),
                    usertypeText.getText()));
            ArrayList<User> res = us.getUsers();
            ObservableList<User> data = FXCollections.observableList(res);
            userTable.getItems().setAll(data);
            //us.closeConnection();
            userIdText.setText("");
            userNameText.setText("");
            passwordText.setText("");
            usertypeText.setText("");


    }

    public void updateUserButtonListner(ActionEvent actionEvent) {
        User user = userTable.getSelectionModel().getSelectedItem();
        if(user.getUsertype().equals("admin")) {
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setHeaderText("Admin Action");
            al.setContentText("Admin data cannot be changed");
            al.show();
            return;
        }
        User newuser = new User(user.getUserid(),updateUsername.getText(),updatepassword.getText(),updateUserType.getText());
        String resp =us.updateUser(newuser);
        ArrayList<User> res = us.getUsers();
        ObservableList<User> data = FXCollections.observableList(res);
        userTable.getItems().setAll(data);
        if(res.equals("sqlError")){
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setHeaderText("SQL Error");
            al.setContentText("Database have some issue contact developer");
            al.show();
        }
    }

    public void deleteUserButtonListner(ActionEvent actionEvent) {
        User user = userTable.getSelectionModel().getSelectedItem();
        if(user.getUsertype().equals("admin")) {
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setHeaderText("Admin Action");
            al.setContentText("Admin Cannot be deleted");
            al.show();
            return;
        }
        us.deleteUser(user.getUserid());
        ArrayList<User> res = us.getUsers();
        ObservableList<User> data = FXCollections.observableList(res);
        userTable.getItems().setAll(data);

    }

    public void logoutButtonListner(ActionEvent actionEvent) throws IOException, SQLException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("AdminLogin.fxml"));
        admindashboard.getChildren().setAll(pane);
        //us.closeConnection();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            us = new UserService();
            ps=new ProductService();

        } catch (SQLException e) {
            e.printStackTrace();
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setHeaderText("SQL Error");
            al.setContentText("Something in conncetion went wrong contact developer");
            al.show();
        }
            userid.setCellValueFactory(new PropertyValueFactory<>("userid"));
            username.setCellValueFactory(new PropertyValueFactory<>("username"));
            password.setCellValueFactory(new PropertyValueFactory<>("password"));
            usertype.setCellValueFactory(new PropertyValueFactory<>("usertype"));

                //UserService us = new UserService();
                ArrayList<User> res = us.getUsers();
                ObservableList<User> data = FXCollections.observableList(res);
                userTable.getItems().setAll(data);
                //us.closeConnection();
            productIDColumn.setCellValueFactory(new PropertyValueFactory<>("productid"));
            productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productname"));
            mrpColumn.setCellValueFactory(new PropertyValueFactory<>("mrp"));
            discountColumn.setCellValueFactory(new PropertyValueFactory<>("discount"));
            stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
            sellingPriceColumn.setCellValueFactory(new PropertyValueFactory<>("sellingprice"));

            ArrayList<Product> pres = ps.showAll();
            ObservableList<Product> pdata = FXCollections.observableList(pres);
            productTable.getItems().setAll(pdata);

    }

    public void tableROwselectedListner(MouseEvent mouseEvent) {
        if(userTable.getSelectionModel().getSelectedItem()!=null) {
            User user = userTable.getSelectionModel().getSelectedItem();
            updateUserType.setText(user.getUsertype());
            updatepassword.setText(user.getPassword());
            updateUsername.setText(user.getUsername());
        }
    }

    public void deleteProductButtonListner(ActionEvent actionEvent) {
        Product product = productTable.getSelectionModel().getSelectedItem();
        //System.out.println(productTable.getItems());
        ps.deleteProduct(product.getProductid());
        ArrayList<Product> res = ps.showAll();
        ObservableList<Product> data = FXCollections.observableList(res);
        productTable.getItems().setAll(data);
    }

    public void addItemButtonListner(ActionEvent actionEvent) {
        if(sellingPriceText.getText().equals("")){
            Alert al = new Alert(Alert.AlertType.INFORMATION);
            al.setHeaderText("Press Equal Button");
            al.setContentText("Equal button calculate selling price");
            al.show();
            return;
        }
        try {
            Product product = new Product(
                    Integer.parseInt(productIDText.getText()),
                    productNameText.getText(),
                    Double.parseDouble(mrpText.getText()),
                    Integer.parseInt(stockText.getText()),
                    Double.parseDouble(discountText.getText()),
                    Double.parseDouble(sellingPriceText.getText())
            );
            ps.addProduct(product);
            ArrayList<Product> res = ps.showAll();
            ObservableList<Product> data = FXCollections.observableList(res);
            productTable.getItems().setAll(data);

        }
        catch(NumberFormatException nm){
            nm.printStackTrace();
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setHeaderText("Invalid Input");
            al.setContentText("Give int value in stock and double value in mrp,discount");
            al.show();
        }

    }

    public void updateButtonListner(ActionEvent actionEvent) {
        if(updatesellingPriceText.getText().equals("")){
            Alert al = new Alert(Alert.AlertType.INFORMATION);
            al.setHeaderText("Press Equal Button");
            al.setContentText("Equal button calculate selling price");
            al.show();
            return;
        }
        Product product= productTable.getSelectionModel().getSelectedItem();
        try {
            Product newProduct = new Product(
                    product.getProductid(),
                    updateproductNameText.getText(),
                    Double.parseDouble(updatemrpText.getText()),
                    Integer.parseInt(updateStocktext.getText()),
                    Double.parseDouble(updatediscountText.getText()),
                    Double.parseDouble(updatesellingPriceText.getText())
            );
            ps.updateProduct(newProduct);
            ArrayList<Product> res = ps.showAll();
            ObservableList<Product> data = FXCollections.observableList(res);
            productTable.getItems().setAll(data);
        }
        catch(NumberFormatException nm){
            nm.printStackTrace();
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setHeaderText("Invalid Input");
            al.setContentText("Give int value in stock and double value in mrp,discount");
            al.show();
        }
    }

    public void productTableRowSelected(MouseEvent mouseEvent) {
        if(productTable.getSelectionModel().getSelectedItem()!=null) {
            Product product = productTable.getSelectionModel().getSelectedItem();
            updateproductNameText.setText(product.getProductname());
            updatesellingPriceText.setText(Double.toString(product.getSellingprice()));
            updatemrpText.setText(Double.toString(product.getMrp()));
            updatediscountText.setText(Double.toString(product.getDiscount()));
            updateStocktext.setText(Integer.toString(product.getStock()));
        }
    }

    public void chnageDiscountListner(ActionEvent actionEvent) {
        try {
            double sp = DicountCalculator.sellingPriceAfterDiscount(Double.parseDouble(updatemrpText.getText()), Double.parseDouble(updatediscountText.getText()));
            updatesellingPriceText.setText(Double.toString(sp));
        }
        catch (NumberFormatException nm){
            nm.printStackTrace();
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setHeaderText("Invalid Input");
            al.setContentText("Give int value in stock and double value in mrp,discount");
            al.show();
        }
    }

    public void addDiscountListner(ActionEvent actionEvent) {
        try {
            double sp = DicountCalculator.sellingPriceAfterDiscount(Double.parseDouble(mrpText.getText()), Double.parseDouble(discountText.getText()));
            sellingPriceText.setText(Double.toString(sp));
        }
        catch (NumberFormatException nm){
            nm.printStackTrace();
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setHeaderText("Invalid Input");
            al.setContentText("Give int value in stock and double value in mrp,discount");
            al.show();
        }
    }
}
