package com.appface.akhil.daggerapp.model;

import java.util.Objects;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(tableName = "Stock_table_unavailable", indices = {@Index(value = "barcode", unique = true)})
public class StockUnavailable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private long barcode;

    public StockUnavailable(long barcode) {
        this.barcode = barcode;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public long getBarcode() {
        return barcode;
    }

    public String getBarcodeString() {
        return String.valueOf(barcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash( barcode);
    }
}
