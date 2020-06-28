package com.example.login;

public class StockWine {
    public Float quantity;
    public Integer stockId;
    public Integer wineId;
    public String unit;
    public Integer employeeId;
    public String dateEntry;

    public StockWine(Float quantity, Integer stockId, Integer wineId, String unit, Integer employeeId, String dateEntry){
        this.quantity = quantity;
        this.stockId = stockId;
        this.wineId = wineId;
        this.unit = unit;
        this.employeeId = employeeId;
        this.dateEntry = dateEntry;
    }
}
