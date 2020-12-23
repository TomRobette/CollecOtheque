package com.tomrobette.collecotheque;

import java.util.ArrayList;

public class Bibliotheque {
    private int id;
    private String nom;
    private int idUser;
    private ArrayList<Etagere> listEtas;

    public Bibliotheque(int id, String nom, int idUser, ArrayList<Etagere> listEtas) {
        this.id = id;
        this.nom = nom;
        this.idUser = idUser;
        this.listEtas = listEtas;
    }

    public Bibliotheque(int id, String nom, ArrayList<Etagere> listEtas) {
        this.id = id;
        this.nom = nom;
        this.listEtas = listEtas;
    }

    public void addEta(Etagere etagere){
        this.listEtas.add(etagere);
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

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public ArrayList<Etagere> getListEtas() {
        return listEtas;
    }

    public void setListEtas(ArrayList<Etagere> listEtas) {
        this.listEtas = listEtas;
    }
}
