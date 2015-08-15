package com.example.perezjuanjose.movip1;

/**
 * Created by perez.juan.jose on 01/08/2015.
 */
public class Film {

    Boolean adults;
    String backdrop_path;
    String origianlLanguaje;
    String originalTitle;
    String overview;
    String releaseDate;
    String posterPath;
    Double popularity;
    String title;
    Boolean video;
    Double voteAverage;
    int vote_count;

    public Film(Boolean adults, String backdrop_path, String origianlLanguaje, String originalTitle, String overview, String releaseDate, String posterPath, Double popularity, String title, Boolean video, Double voteAverage, int vote_count) {
        this.adults = adults;
        this.backdrop_path = backdrop_path;
        this.origianlLanguaje = origianlLanguaje;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
        this.popularity = popularity;
        this.title = title;
        this.video = video;
        this.voteAverage = voteAverage;
        this.vote_count = vote_count;
    }

    public Boolean getAdults() {
        return adults;
    }

    public void setAdults(Boolean adults) {
        this.adults = adults;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getOrigianlLanguaje() {
        return origianlLanguaje;
    }

    public void setOrigianlLanguaje(String origianlLanguaje) {
        this.origianlLanguaje = origianlLanguaje;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }
}
