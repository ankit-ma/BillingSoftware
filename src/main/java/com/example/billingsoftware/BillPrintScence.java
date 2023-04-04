package com.example.billingsoftware;

import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.print.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;

import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class BillPrintScence implements Initializable {
    public AnchorPane billLayout;
    public AnchorPane billViewPane;
    public TableView<Product> billTable;
    public TableColumn<Product,Integer> productID;
    public TableColumn<Product,String> productName;
    public TableColumn<Product,Double> quantity;
    public TableColumn<Product,Double> mrp;
    public TableColumn<Product,Double> discount;
    public TableColumn<Product,Double> totalPrice;
    public TextField phoneNumber;
    public TextField customerName;
    public Label dateLabel;
    public Label nillNumber;
    public Label totalAmount;
    public Button printButton;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productID.setCellValueFactory(new PropertyValueFactory<>("productid"));
        productName.setCellValueFactory(new PropertyValueFactory<>("productname"));
        mrp.setCellValueFactory(new PropertyValueFactory<>("mrp"));
        discount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("stock"));
        totalPrice.setCellValueFactory(new PropertyValueFactory<>("sellingprice"));
        ObservableList<Product> data = FXCollections.observableList(BillingDashboard.obl);
        billTable.setItems(data);
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        dateLabel.setText(timeStamp);
        totalAmount.setText(Double.toString(BillingDashboard.total));

    }
    public void printButtonListner(ActionEvent actionEvent){
        try{
            long a = Long.parseLong(phoneNumber.getText());
        }
        catch(NumberFormatException nm)
        {
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setHeaderText("Phone Number Note");
            al.setContentText("Give integer value");
            al.show();
        }
        if(customerName.getText().length()!=0 && phoneNumber.getText().length()!=10){
            System.out.println(phoneNumber.getText().length()+" "+customerName.getText());
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setHeaderText("Give Valid Details");
            al.setContentText("Give 10 digit phone number and a valid name");
            al.show();
        }
        else {
            PrinterJob job = PrinterJob.createPrinterJob();
            if (job != null) {
                Printer printer = job.getPrinter();
                PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, Printer.MarginType.HARDWARE_MINIMUM);
                job.showPrintDialog(billViewPane.getScene().getWindow());
                job.printPage(pageLayout, billViewPane);
                job.endJob();
            }
        }
    }
}
