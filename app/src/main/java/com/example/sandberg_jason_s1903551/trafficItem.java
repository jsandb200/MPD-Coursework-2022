package com.example.sandberg_jason_s1903551;

import androidx.annotation.NonNull;



public class trafficItem {
    private String title;
    private String description;
    private String link;
    private String georsspoint;
    private String pubdate;



    public trafficItem(){

    }


    public String getTitle(){
        return title;
    }
    public String getDescription(){
        return description;
    }
    public String getLink(){
        return link;
}
    public String getGeorsspoint(){
        return georsspoint;
}
    public String getPubdate(){
        return pubdate;
}





    public void setTitle(String title){
        this.title = title;
}
    public void setDescription(String description){
        this.description = description;
    }
    public void setLink(String link){
        this.link = link;
    }
    public void setGeorsspoint(String georsspoint){
        this.georsspoint = georsspoint;
    }
    public void setPubdate(String pubdate){
        this.pubdate = pubdate;
    }


    @Override
    public String toString() {
        return "trafficItem{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", link='" + link + '\'' +
                ", georsspoint='" + georsspoint + '\'' +
                ", pubdate='" + pubdate + '\'' +
                '}';
    }
}

