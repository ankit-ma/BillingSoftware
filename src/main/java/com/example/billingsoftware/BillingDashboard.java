package com.example.billingsoftware;

import Model.Product;
import Service.ProductService;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.*;
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

public class BillingDashboard implements Initializable {
    public Label userText;
    public AnchorPane billingPane;

    public TableView<Product> itemTable;
    public TableColumn<Product,Integer> productIDColumn;
    public TableColumn<Product,String> productNameColumn;
    public TableColumn<Product,Double> mrpColumn;
    public TableColumn<Product,Integer> stockColumn;
    public TableColumn<Product,Double> discountColumn;
    public TableColumn<Product,Double> sellingProceColumn;

    public TextField searchInput;
    public Button searchButton;
    public Button logoutButton;
    public ListView<String> listview;
    public Label productNameLabel;
    public TextField quantityInput;
    public Label discountLabel;
    public Label totalPriceLabel;
    public Label mrpLabel;
    public Button addButton;
    public Label billItem;
    public Label totalAmount;
    public Button generateBillButton;
    private ArrayList<String> data;
    public static ArrayList<Product> obl;
    ProductService ps;
    Product product;
    int items=0;
    double amount=0;
    static double total=0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            ps= new ProductService();
            product= new Product();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        userText.setText(EmployeeLogin.username);
        data= ps.getAllProductNames();
        System.out.println(data);
        listview.getItems().addAll(data);
        listview.setVisible(false);
        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("productid"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productname"));
        mrpColumn.setCellValueFactory(new PropertyValueFactory<>("mrp"));
        discountColumn.setCellValueFactory(new PropertyValueFactory<>("discount"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        sellingProceColumn.setCellValueFactory(new PropertyValueFactory<>("sellingprice"));

    }

    public void onInputActionListner(MouseEvent mouseEvent) {
        listview.setVisible(true);
    }

    public void onSearchButtonListner(ActionEvent actionEvent) {
        listview.getItems().setAll(DicountCalculator.searchList(searchInput.getText(),data));
    }

    public void logoutButtonListner(ActionEvent actionEvent) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("EmployeeLogin.fxml"));
        billingPane.getChildren().setAll(pane);
    }

    public void pane2clickListner(MouseEvent mouseEvent) {
        listview.setVisible(false);
    }

    public void listviewClickListner(MouseEvent mouseEvent) {
        if(listview.getSelectionModel().getSelectedItem()!=null){
            searchInput.setText(listview.getSelectionModel().getSelectedItem());
            product = ps.getProductByName(searchInput.getText());
            productNameLabel.setText(product.getProductname());
            quantityInput.setText(Integer.toString(product.getStock()));
            discountLabel.setText(Double.toString(product.getDiscount()));
            mrpLabel.setText(Double.toString(product.getMrp()));
            totalPriceLabel.setText(Double.toString(product.getMrp()*product.getStock()));
        }
    }

    public void paneClickListner(MouseEvent mouseEvent) {
        listview.setVisible(false);
    }

    public void quantityInputChangeListner(MouseEvent mouseEvent) {
        try {
            if (Integer.parseInt(quantityInput.getText()) > 0
                    && Integer.parseInt(quantityInput.getText()) <= product.getStock()) {
                totalPriceLabel.setText(Double.toString(product.getSellingprice() * Double.parseDouble(quantityInput.getText())));
            }
            else if(Integer.parseInt(quantityInput.getText()) >product.getStock()){
                Alert al = new Alert(Alert.AlertType.WARNING);
                al.setHeaderText("Quantity is not under stock");
                al.setContentText("Give Quantity within stock");
                al.show();
            }
            else{

            }

        }
        catch (NumberFormatException nm){
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setHeaderText("Quantity should be Number");
            al.setContentText("Khelna band karo aur number do");
            al.show();
        }
    }

    public void addButtonListner(ActionEvent actionEvent) {
        Product newProduct = new Product(product.getProductid(),
                product.getProductname(),
                product.getMrp(),
                product.getStock()-Integer.parseInt(quantityInput.getText()),
                product.getDiscount(),product.getSellingprice());
        product.setStock(Integer.parseInt(quantityInput.getText()));
        ps.stockChange(newProduct);
        product.setSellingprice(Double.parseDouble(totalPriceLabel.getText()));
        itemTable.getItems().add(product);
        items++;
        billItem.setText(Integer.toString(items));
        totalPriceLabel.setText(Double.toString(amount+Double.parseDouble(totalPriceLabel.getText())));
        total+=amount+Double.parseDouble(totalPriceLabel.getText());
        totalAmount.setText(Double.toString(total));
    }

    public void generateBillListner(ActionEvent actionEvent) throws IOException {
        obl= new ArrayList<>();
        obl.addAll(itemTable.getItems());
        AnchorPane pane = FXMLLoader.load(getClass().getResource("BillPrintScence.fxml"));
        billingPane.getChildren().setAll(pane);
    }
}
