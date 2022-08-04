package com.example.t1_dsm.model;

public class Post {
    String text, game, rank, idProp, vacancies;

    public Post(String text, String game, String rank, String idProp, String vacancies){
        this.text = text;
        this.game = game;
        this.rank = rank;
        this.idProp = idProp;
        this.vacancies = vacancies;
    }

    public String getText(){
        return text;
    }

    public void setText(String text){
        this.text = text;
    }

    public String getGame(){
        return game;
    }

    public void setGame(String game){
        this.game = game;
    }

    public String getRank(){
        return rank;
    }

    public void setRank(String rank){
        this.rank = rank;
    }

    public String getIdProp(){
        return idProp;
    }

    public String getVacancies(){
        return vacancies;
    }

    public void setVacancies(String vacancies){
        this.vacancies = vacancies;
    }
}
