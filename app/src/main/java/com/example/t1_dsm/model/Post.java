package com.example.t1_dsm.model;

public class Post {
    Integer idProp, vacancies;
    String text, game, rank;

    public Post(String text, String game, String rank, Integer idProp, Integer vacancies){
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

    public Integer getIdProp(){
        return idProp;
    }

    public Integer getVacancies(){
        return vacancies;
    }

    public void setVacancies(Integer vacancies){
        this.vacancies = vacancies;
    }
}
