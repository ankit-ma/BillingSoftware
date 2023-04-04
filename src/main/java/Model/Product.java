package Model;

import java.util.Objects;

public class Product {
    private int productid;
    private String productname;
    private double mrp;
    private int stock;
    private double discount;
    private double sellingprice;

    public Product() {
    }

    public Product(int productid, String productname, double mrp, int stock, double discount, double sellingprice) {
        this.productid = productid;
        this.productname = productname;
        this.mrp = mrp;
        this.stock = stock;
        this.discount = discount;
        this.sellingprice = sellingprice;
    }

    public int getProductid() {
        return productid;
    }

    public void setProductid(int productid) {
        this.productid = productid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public double getMrp() {
        return mrp;
    }

    public void setMrp(double mrp) {
        this.mrp = mrp;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getSellingprice() {
        return sellingprice;
    }

    public void setSellingprice(double sellingprice) {
        this.sellingprice = sellingprice;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productid=" + productid +
                ", productname='" + productname + '\'' +
                ", mrp=" + mrp +
                ", stock=" + stock +
                ", discount=" + discount +
                ", sellingprice=" + sellingprice +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return productid == product.productid && Double.compare(product.mrp, mrp) == 0 && stock == product.stock && Double.compare(product.discount, discount) == 0 && Double.compare(product.sellingprice, sellingprice) == 0 && productname.equals(product.productname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productid, productname, mrp, stock, discount, sellingprice);
    }
}
