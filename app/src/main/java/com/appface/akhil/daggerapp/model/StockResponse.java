package com.appface.akhil.daggerapp.model;

import java.io.Serializable;

public class StockResponse implements Serializable {

    String message;

    String statusCode;

    Stock data;

    public StockResponse(String message, String statusCode, Stock data) {
        this.message = message;
        this.statusCode = statusCode;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public Stock getData() {
        return data;
    }

    public void setData(Stock data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "StockResponse{" +
                "message='" + message + '\'' +
                ", statusCode='" + statusCode + '\'' +
                ", data=" + data +
                '}';
    }
}
