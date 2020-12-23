package com.tomrobette.collecotheque;

import java.util.ArrayList;

public class Etagere {
    private int id;
    private String nom;
    private int idBib;
    private ArrayList<Collection> listeCols;

    public Etagere(int id, String nom, int idBib, ArrayList<Collection> listeCols) {
        this.id = id;
        this.nom = nom;
        this.idBib = idBib;
        this.listeCols = listeCols;
    }

    public void addCol(Collection col){
        this.listeCols.add(col);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getIdBib() {
        return idBib;
    }

    public void setIdBib(int idBib) {
        this.idBib = idBib;
    }

    public ArrayList<Collection> getListeCols() {
        return listeCols;
    }

    public void setListeCols(ArrayList<Collection> listeCols) {
        this.listeCols = listeCols;
    }
}
