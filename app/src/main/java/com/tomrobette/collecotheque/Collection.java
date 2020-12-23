package com.tomrobette.collecotheque;

import java.util.ArrayList;

public class Collection {
    private int id;
    private String nom;
    private int idEta;
    private ArrayList<Livre> listeLivres;

    public Collection(int id, String nom, int idEta, ArrayList<Livre> listeLivres) {
        this.id = id;
        this.nom = nom;
        this.idEta = idEta;
        this.listeLivres = listeLivres;
    }

    public void addLivre(Livre livre){
        if (!this.listeLivres.contains(livre)){
            this.listeLivres.add(livre);
        }
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

    public int getIdEta() {
        return idEta;
    }

    public void setIdEta(int idEta) {
        this.idEta = idEta;
    }

    public ArrayList<Livre> getListeLivres() {
        return listeLivres;
    }

    public void setListeLivres(ArrayList<Livre> listeLivres) {
        this.listeLivres = listeLivres;
    }
}
