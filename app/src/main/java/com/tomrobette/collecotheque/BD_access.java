package com.tomrobette.collecotheque;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BD_access extends SQLiteOpenHelper {

    private String createUser = " CREATE TABLE utilisateur(" +
            "id INTEGER PRIMARY KEY, "+
            "email VARCHAR(100) NOT NULL, "+
            "mdp VARCHAR(255) NOT NULL, "+
            "nom VARCHAR(100) NOT NULL);";
    private String createBib = " CREATE TABLE bibliotheque(" +
            "id INTEGER PRIMARY KEY, "+
            "nom VARCHAR(100) NOT NULL, " +
            "idUser INTEGER NOT NULL, " +
            "FOREIGN KEY (idUser) REFERENCES utilisateur(id));";
    private String createEta = " CREATE TABLE etagere(" +
            "id INTEGER PRIMARY KEY, "+
            "nom VARCHAR(100) NOT NULL, "+
            "idBib INTEGER NOT NULL, " +
            "FOREIGN KEY (idBib) REFERENCES bibliotheque(id));";
    private String createCol = " CREATE TABLE collection(" +
            "id INTEGER PRIMARY KEY, "+
            "nom VARCHAR(100) NOT NULL, "+
            "idEta INTEGER NOT NULL, " +
            "FOREIGN KEY (idEta) REFERENCES etagere(id));";
    private String createLiv = " CREATE TABLE livre(" +
            "isbn VARCHAR(100) PRIMARY KEY, "+
            "titre VARCHAR(100) NOT NULL, "+
            "idCol INTEGER NOT NULL, " +
            "FOREIGN KEY (idCol) REFERENCES collection(id));";

    public BD_access(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createUser);
        db.execSQL(createBib);
        db.execSQL(createEta);
        db.execSQL(createCol);
        db.execSQL(createLiv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
