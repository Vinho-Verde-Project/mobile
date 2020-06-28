package com.example.login;

import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class ListEntryPie {

    public String name;
    public ArrayList<PieEntry> list = new ArrayList<PieEntry>();
    public Float lotacao;

    public ListEntryPie (String name, Float valor){
        this.lotacao = 1000 - valor;
        this.list.add(new PieEntry(this.lotacao, "Vazio"));
        this.name = name;
        this.list.add(new PieEntry(valor, "Em Uso"));

    }
}
