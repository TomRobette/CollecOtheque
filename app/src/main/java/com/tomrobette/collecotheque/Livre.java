package com.tomrobette.collecotheque;

public class Livre {
    private String isbn;
    private String titre;
    private int idCollection;

    public Livre(String isbn, String titre, int idCollection) {
        this.isbn = isbn;
        this.titre = titre;
        this.idCollection = idCollection;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public int getIdCollection() {
        return idCollection;
    }

    public void setIdCollection(int idCollection) {
        this.idCollection = idCollection;
    }
}
