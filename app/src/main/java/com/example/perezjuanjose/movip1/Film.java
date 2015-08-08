package com.example.perezjuanjose.movip1;

/**
 * Created by perez.juan.jose on 01/08/2015.
 */
public class Film {


    // For now, using the format "Day, description, hi/low"
    String title;
    String popularity; //Its a double but i don't need to process the information. So use the string
    String backdrop_path;

    public Film(String title, String popularity, String backdrop_path) {
        this.title = title;
        this.popularity = popularity;
        this.backdrop_path = backdrop_path;
    }

    public void film(String title, String popularity, String backdrop_path) {
        this.title = title;
        this.popularity = popularity;
        this.backdrop_path = backdrop_path;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }
}
