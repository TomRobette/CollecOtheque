package com.tomrobette.collecotheque;

import java.util.ArrayList;

public class Utilisateur {
    private int id;
    private String email;
    private String mdp;
    private String nom;
    private ArrayList<Bibliotheque> listeBibs;

    public Utilisateur(int id, String email, String mdp, String nom, ArrayList<Bibliotheque> listeBibs) {
        this.id = id;
        this.email = email;
        this.mdp = mdp;
        this.nom = nom;
        this.listeBibs = listeBibs;
    }

    public void addBib(Bibliotheque bib){
        this.listeBibs.add(bib);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
