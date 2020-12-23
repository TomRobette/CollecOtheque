package com.tomrobette.collecotheque;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.util.ArrayList;


public class LocalAccess {

    //propriété
    private String nomDB = "collecotheque.sqlite";
    private Integer version = 1;
    private BD_access accesBD;
    private SQLiteDatabase database;


    public LocalAccess(Context contexte){
        accesBD = new BD_access(contexte, nomDB, null, version);
    }

    //LIVRES

    public void addLivre(String isbn, String titre, int idCol){
        database = accesBD.getWritableDatabase();
        String request = " Insert into livre(isbn, titre, idCol) values ";
        request += "(\""+isbn+"\" ,"+" \""+titre+"\" ,"+idCol +");";

        database.execSQL(request);
    }

    public void addLivre(Livre livre){
        database = accesBD.getWritableDatabase();
        String request = " Insert into livre(isbn, titre, idCol) values ";
        request += "(\""+livre.getIsbn()+"\" ,"+" \""+livre.getTitre()+"\" ,"+livre.getIdCollection() +");";
        database.execSQL(request);
    }

    public void clearAllLivres(){
        database = accesBD.getWritableDatabase();
        String request = "delete from livre";
        database.execSQL(request);
    }

    public void removeLivByISBN(String isbn){
        database = accesBD.getWritableDatabase();
        String request = "DELETE FROM livre WHERE isbn="+isbn+";";
        database.execSQL(request);
    }

    public Livre getLivByISBN(String isbn){
        database = accesBD.getWritableDatabase();
        String request = "SELECT * FROM livre WHERE isbn="+isbn+";";
        Cursor curseur = database.rawQuery(request, null);
        curseur.moveToFirst();
        String titre = curseur.getString(1);
        int idCol = curseur.getInt(2);
        Livre livre = new Livre(isbn, titre, idCol);
        return livre;
    }

    public String getLastLivISBN(){
        String isbn = "";
        database = accesBD.getReadableDatabase();
        String request = "select * from livre;";
        Cursor curseur = database.rawQuery(request, null);
        curseur.moveToLast();
        if (!curseur.isAfterLast()){
           isbn = curseur.getString(0);
        }
        curseur.close();

        return isbn;
    }

    public Livre getLastLivSave(){
        database = accesBD.getReadableDatabase();
        String request = "select * from livre;";
        Cursor curseur = database.rawQuery(request, null);
        Livre livre = null;
        curseur.moveToLast();
        if (!curseur.isAfterLast()){
            String isbn = curseur.getString(0);
            String titre = curseur.getString(1);
            int idCol = curseur.getInt(2);
            livre = new Livre(isbn, titre,idCol);
        }
        curseur.close();
        return livre;
    }

    public ArrayList<Livre> getAllLiv(){
        ArrayList<Livre> listeLivres = new ArrayList<>();
        database = accesBD.getReadableDatabase();
        String request = "select * from livre;";
        Cursor curseur = database.rawQuery(request, null);
        Livre livre = null;

        curseur.moveToFirst();
        while (curseur.isAfterLast() == false)
        {
            String isbn = curseur.getString(0);
            String titre = curseur.getString(1);
            int idCol = curseur.getInt(2);
            livre = new Livre(isbn, titre,idCol);
            listeLivres.add(livre);
            curseur.moveToNext();
        }
        curseur.close();
        return listeLivres;
    }

    public ArrayList<Livre> getAllLivByColId(int idCol){
        ArrayList<Livre> listeLivres = new ArrayList<>();
        database = accesBD.getReadableDatabase();
        String request = "select * from livre WHERE idCol="+idCol+";";
        Cursor curseur = database.rawQuery(request, null);
        Livre livre = null;

        curseur.moveToFirst();
        while (curseur.isAfterLast() == false)
        {
            String isbn = curseur.getString(0);
            String titre = curseur.getString(1);
            livre = new Livre(isbn, titre, idCol);
            listeLivres.add(livre);
            curseur.moveToNext();
        }
        curseur.close();
        return listeLivres;
    }

    //Collections

    public void addCollection(String nom, int idEta){
        database = accesBD.getWritableDatabase();
        String request = " Insert into collection(id, nom, idEta) values ";
        Integer id =  getLastColId()+1;
        request += "( "+id +" ,"+" \""+nom+"\" ,"+idEta +");";
        database.execSQL(request);
    }

    public void removeColById(int idCol){
        database = accesBD.getWritableDatabase();
        String request = "DELETE FROM collection WHERE id="+idCol+";";
        database.execSQL(request);
    }

    public Collection getColById(int idCol){
        database = accesBD.getWritableDatabase();
        String request = "SELECT * FROM collection WHERE id="+idCol+";";
        Cursor curseur = database.rawQuery(request, null);
        curseur.moveToFirst();
        Integer id = curseur.getInt(0);
        String nom = curseur.getString(1);
        int idEta = curseur.getInt(2);

        ArrayList<Livre> listeLivres = getAllLivByColId(id);
        Collection collection = new Collection(id, nom, idEta, listeLivres);
        return collection;
    }

    public Integer getLastColId(){
        Integer id = 0;
        database = accesBD.getReadableDatabase();
        String request = "select * from collection;";
        Cursor curseur = database.rawQuery(request, null);
        curseur.moveToLast();
        if (!curseur.isAfterLast()){
            id = curseur.getInt(0);
        }
        curseur.close();

        return id;
    }

    public Collection getLastColSave(){
        database = accesBD.getReadableDatabase();
        String request = "select * from collection;";
        Cursor curseur = database.rawQuery(request, null);
        Collection collection = null;
        curseur.moveToLast();
        if (!curseur.isAfterLast()){
            Integer id = curseur.getInt(0);
            String nom = curseur.getString(1);
            int idEta = curseur.getInt(2);

            ArrayList<Livre> listeLivres = getAllLivByColId(id);
            collection = new Collection(id, nom, idEta, listeLivres);
        }
        curseur.close();
        return collection;
    }

    public ArrayList<Collection> getAllCols(){
        ArrayList<Collection> listeCollections = new ArrayList<>();
        database = accesBD.getReadableDatabase();
        String request = "select * from collection;";
        Cursor curseur = database.rawQuery(request, null);
        Collection collection = null;

        curseur.moveToFirst();
        while (curseur.isAfterLast() == false)
        {
            Integer id = curseur.getInt(0);
            String nom = curseur.getString(1);
            int idEta = curseur.getInt(2);

            ArrayList<Livre> listeLivres = getAllLivByColId(id);
            collection = new Collection(id, nom, idEta, listeLivres);
            listeCollections.add(collection);
            curseur.moveToNext();
        }
        curseur.close();
        return listeCollections;
    }

    public ArrayList<Collection> getAllColsByEtaId(int idEta){
        ArrayList<Collection> listeCollections = new ArrayList<>();
        database = accesBD.getReadableDatabase();
        String request = "select * from collection WHERE idEta="+idEta+";";
        Cursor curseur = database.rawQuery(request, null);
        Collection collection = null;

        curseur.moveToFirst();
        while (curseur.isAfterLast() == false)
        {
            Integer id = curseur.getInt(0);
            String nom = curseur.getString(1);

            ArrayList<Livre> listeLivres = getAllLivByColId(id);
            collection = new Collection(id, nom, idEta, listeLivres);
            listeCollections.add(collection);
            curseur.moveToNext();
        }
        curseur.close();
        return listeCollections;
    }

    //Etagères

    public void addEtagere(String nom, int idBib){
        database = accesBD.getWritableDatabase();
        String request = " Insert into etagere(id, nom, idBib) values ";
        Integer id =  getLastEtaId()+1;
        request += "( "+id +" ,"+" \""+nom+"\" ,"+idBib +");";
        database.execSQL(request);
    }

    public void removeEtaById(int idEta){
        database = accesBD.getWritableDatabase();
        String request = "DELETE FROM etagere WHERE id="+idEta+";";
        database.execSQL(request);
    }

    public Etagere getEtaById(int idEta){
        database = accesBD.getWritableDatabase();
        String request = "SELECT * FROM etagere WHERE id="+idEta+";";
        Cursor curseur = database.rawQuery(request, null);
        curseur.moveToFirst();
        Integer id = curseur.getInt(0);
        String nom = curseur.getString(1);
        int idBib = curseur.getInt(2);

        ArrayList<Collection> listeCols = getAllColsByEtaId(id);
        Etagere etagere = new Etagere(id, nom, idBib, listeCols);
        return etagere;
    }

    public Integer getLastEtaId(){
        Integer id = 0;
        database = accesBD.getReadableDatabase();
        String request = "select * from etagere;";
        Cursor curseur = database.rawQuery(request, null);
        curseur.moveToLast();
        if (!curseur.isAfterLast()){
            id = curseur.getInt(0);
        }
        curseur.close();

        return id;
    }

    public Etagere getLastEtaSave(){
        database = accesBD.getReadableDatabase();
        String request = "select * from etagere;";
        Cursor curseur = database.rawQuery(request, null);
        Etagere etagere = null;
        curseur.moveToLast();
        if (!curseur.isAfterLast()){
            Integer id = curseur.getInt(0);
            String nom = curseur.getString(1);
            int idBib = curseur.getInt(2);
            etagere = new Etagere(id, nom, idBib, getAllColsByEtaId(id));
        }
        curseur.close();
        return etagere;
    }

    public ArrayList<Etagere> getAllEtas(){
        ArrayList<Etagere> listeEtas = new ArrayList<>();
        database = accesBD.getReadableDatabase();
        String request = "select * from etagere;";
        Cursor curseur = database.rawQuery(request, null);
        curseur.moveToFirst();
        while (curseur.isAfterLast() == false)
        {
            Integer id = curseur.getInt(0);
            String nom = curseur.getString(1);
            int idBib = curseur.getInt(2);

            ArrayList<Collection> listeCols = getAllColsByEtaId(id);
            Etagere etagere = new Etagere(id, nom, idBib, listeCols);
            listeEtas.add(etagere);
            curseur.moveToNext();
        }
        curseur.close();
        return listeEtas;
    }

    public ArrayList<Etagere> getAllEtaByBibId(int idBib){
        ArrayList<Etagere> listeEtas = new ArrayList<>();
        database = accesBD.getReadableDatabase();
        String request = "select * from etagere WHERE idBib="+idBib+";";
        Cursor curseur = database.rawQuery(request, null);

        curseur.moveToFirst();
        while (curseur.isAfterLast() == false)
        {
            Integer id = curseur.getInt(0);
            String nom = curseur.getString(1);
            Etagere etagere = new Etagere(id, nom, idBib, getAllColsByEtaId(id));
            listeEtas.add(etagere);
            curseur.moveToNext();
        }
        curseur.close();
        return listeEtas;
    }

    //Bibliothèques

    public void addBibliotheque(String nom, @Nullable Integer idUser){
        database = accesBD.getWritableDatabase();
        if (idUser==null){
            idUser=0; //À OPTIMISER
        }
        String request = " Insert into bibliotheque(id, nom, idUser) values ";
        Integer id =  getLastBibId()+1;
        request += "( "+id +" ,"+" \""+nom+"\" ,"+idUser +");";
        database.execSQL(request);
    }

    public void removeBibById(int idBib){
        database = accesBD.getWritableDatabase();
        String request = "DELETE FROM bibliotheque WHERE id="+idBib+";";
        database.execSQL(request);
    }

    public Bibliotheque getBibById(int idBib){
        database = accesBD.getWritableDatabase();
        String request = "SELECT * FROM bibliotheque WHERE id="+idBib+";";
        Cursor curseur = database.rawQuery(request, null);
        curseur.moveToFirst();
        String nom = curseur.getString(1);
        int idUser = curseur.getInt(2);

        ArrayList<Etagere> listeEtas = getAllEtaByBibId(idBib);
        Bibliotheque bibliotheque = new Bibliotheque(idBib, nom, idUser, listeEtas);
        return bibliotheque;
    }

    public Integer getLastBibId(){
        Integer id = 0;
        database = accesBD.getReadableDatabase();
        String request = "select * from bibliotheque;";
        Cursor curseur = database.rawQuery(request, null);
        curseur.moveToLast();
        if (!curseur.isAfterLast()){
            id = curseur.getInt(0);
        }
        curseur.close();

        return id;
    }

    public Bibliotheque getLastBibSave(){
        database = accesBD.getReadableDatabase();
        String request = "select * from bibliotheque;";
        Cursor curseur = database.rawQuery(request, null);
        curseur.moveToLast();
        Bibliotheque bibliotheque = null;
        if (!curseur.isAfterLast()){
            Integer id = curseur.getInt(0);
            String nom = curseur.getString(1);
            int idBib = curseur.getInt(2);
            bibliotheque = new Bibliotheque(id, nom, idBib, getAllEtaByBibId(id));
        }
        curseur.close();
        return bibliotheque;
    }

    public ArrayList<Bibliotheque> getAllBibs(){
        ArrayList<Bibliotheque> listeBibs = new ArrayList<>();
        database = accesBD.getReadableDatabase();
        String request = "select * from bibliotheque;";
        Cursor curseur = database.rawQuery(request, null);
        curseur.moveToFirst();
        while (curseur.isAfterLast() == false)
        {
            Integer id = curseur.getInt(0);
            String nom = curseur.getString(1);
            int idUser = curseur.getInt(2);
            Bibliotheque bibliotheque = new Bibliotheque(id, nom, idUser, getAllEtaByBibId(id));
            listeBibs.add(bibliotheque);
            curseur.moveToNext();
        }
        curseur.close();
        return listeBibs;
    }

    public ArrayList<Bibliotheque> getAllBibByUserId(int idUser){
        ArrayList<Bibliotheque> listeBibs = new ArrayList<>();
        database = accesBD.getReadableDatabase();
        String request = "select * from bibliotheque WHERE idUser="+idUser+";";
        Cursor curseur = database.rawQuery(request, null);

        curseur.moveToFirst();
        while (curseur.isAfterLast() == false)
        {
            Integer id = curseur.getInt(0);
            String nom = curseur.getString(1);
            listeBibs.add(new Bibliotheque(id, nom, idUser, getAllEtaByBibId(id)));
            curseur.moveToNext();
        }
        curseur.close();
        return listeBibs;
    }

    //Utilisateurs

    public void addUser(String email, String mdp, String nom){
        database = accesBD.getWritableDatabase();
        String request = " Insert into utilisateur(id, email, mdp, nom) values ";
        Integer id =  getLastUserId()+1;
        request += "( "+id +" ,"+" \""+email+"\" ,"+" \""+mdp+"\" ,"+" \""+nom+"\");";
        database.execSQL(request);
    }

    public void removeUserById(int idUser){
        database = accesBD.getWritableDatabase();
        String request = "DELETE FROM utilisateur WHERE id="+idUser+";";
        database.execSQL(request);
    }

    public Utilisateur getUserById(int idUser){
        database = accesBD.getWritableDatabase();
        String request = "SELECT * FROM utilisateur WHERE id="+idUser+";";
        Cursor curseur = database.rawQuery(request, null);
        curseur.moveToFirst();
        String email = curseur.getString(1);
        String mdp = curseur.getString(2);
        String nom = curseur.getString(3);

        ArrayList<Bibliotheque> listeBibs = getAllBibByUserId(idUser);
        Utilisateur user = new Utilisateur(idUser, email, mdp, nom, listeBibs);
        return user;
    }

    public Integer getLastUserId(){
        Integer id = 0;
        database = accesBD.getReadableDatabase();
        String request = "select * from utilisateur;";
        Cursor curseur = database.rawQuery(request, null);
        curseur.moveToLast();
        if (!curseur.isAfterLast()){
            id = curseur.getInt(0);
        }
        curseur.close();

        return id;
    }

    public Utilisateur getLastUserSave(){
        database = accesBD.getReadableDatabase();
        String request = "select * from utilisateur;";
        Cursor curseur = database.rawQuery(request, null);
        curseur.moveToLast();
        Utilisateur utilisateur = null;
        if (!curseur.isAfterLast()){
            Integer id = curseur.getInt(0);
            String email = curseur.getString(1);
            String mdp = curseur.getString(2);
            String nom = curseur.getString(3);
            utilisateur = new Utilisateur(id, email, mdp, nom, getAllBibByUserId(id));
        }
        curseur.close();
        return utilisateur;
    }

    public ArrayList<Utilisateur> getAllUsers(){
        ArrayList<Utilisateur> listeUsers = new ArrayList<>();
        database = accesBD.getReadableDatabase();
        String request = "select * from utilisateur;";
        Cursor curseur = database.rawQuery(request, null);
        curseur.moveToFirst();
        while (curseur.isAfterLast() == false)
        {
            Integer id = curseur.getInt(0);
            String email = curseur.getString(1);
            String mdp = curseur.getString(2);
            String nom = curseur.getString(3);
            listeUsers.add(new Utilisateur(id, email, mdp, nom, getAllBibByUserId(id)));
            curseur.moveToNext();
        }
        curseur.close();
        return listeUsers;
    }

    public boolean doesBibNameExist(String name, int idUser){
        boolean eq = false;
        for (Bibliotheque bib:getAllBibByUserId(idUser)){
            if (bib.getNom()==name) {
                eq = true;
            }
        }
        return eq;
    }

    public boolean doesEtaNameExist(String name, int idBib){
        boolean eq = false;
        for (Etagere eta:getAllEtaByBibId(idBib)){
            if (eta.getNom()==name) {
                eq = true;
            }
        }
        return eq;
    }

    public boolean doesColNameExist(String name, int idEta){
        boolean eq = false;
        for (Collection col:getAllColsByEtaId(idEta)){
            if (col.getNom()==name) {
                eq = true;
            }
        }
        return eq;
    }

    public boolean doesLivISBNExist(String isbn, int idCol){
        boolean eq = false;
        for (Livre livre:getAllLivByColId(idCol)){
            if (livre.getIsbn()==isbn) {
                eq = true;
            }
        }
        return eq;
    }


}
