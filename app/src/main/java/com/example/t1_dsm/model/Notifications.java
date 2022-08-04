package com.example.t1_dsm.model;

public class Notifications {

    String text, game, idProp, solicitante;

    public Notifications(String text, String game, String idProp, String solicitante){
        this.text = text;
        this.game = game;
        this.idProp = idProp;
        this.solicitante = solicitante;
    }

    public String getText(){return text;}
    public String getGame(){return game;}
    public String getIdProp(){return idProp;}
    public String getSolicitante(){return solicitante;}
}
