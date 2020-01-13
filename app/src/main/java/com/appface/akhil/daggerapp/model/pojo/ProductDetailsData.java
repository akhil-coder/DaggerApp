package com.appface.akhil.daggerapp.model.pojo;

public class ProductDetailsData implements java.io.Serializable {
    private static final long serialVersionUID = -8041370888417116887L;
    private int stockQty;
    private String productDetails;
    private String brand;
    private long barcode;

    public int getStockQty() {
        return this.stockQty;
    }

    public void setStockQty(int stockQty) {
        this.stockQty = stockQty;
    }

    public String getProductDetails() {
        return this.productDetails;
    }

    public void setProductDetails(String productDetails) {
        this.productDetails = productDetails;
    }

    public String getBrand() {
        return this.brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public long getBarcode() {
        return this.barcode;
    }

    public void setBarcode(long barcode) {
        this.barcode = barcode;
    }
}
