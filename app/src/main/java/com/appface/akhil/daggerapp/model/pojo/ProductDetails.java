package com.appface.akhil.daggerapp.model.pojo;

public class ProductDetails implements java.io.Serializable {
    private static final long serialVersionUID = -5821309955335903707L;
    private ProductDetailsData data;
    private String message;
    private int statusCode;

    public ProductDetailsData getData() {
        return this.data;
    }

    public void setData(ProductDetailsData data) {
        this.data = data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        return "ProductDetails{" +
                "data=" + data +
                ", message='" + message + '\'' +
                ", statusCode=" + statusCode +
                '}';
    }
}
