package com.tomrobette.collecotheque;

public class Session {
    protected static Utilisateur user;
    protected static boolean testMode = false;

    public static void setUser(Utilisateur user){
        Session.user=user;
    }

    public static Utilisateur getUser(){
        return Session.user;
    }

    public static void setTestMode(boolean testMode){
        Session.testMode=testMode;
    }

    public static boolean isTest(){
        return Session.testMode;
    }
}
