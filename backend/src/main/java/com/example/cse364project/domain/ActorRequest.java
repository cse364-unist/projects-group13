package com.example.cse364project.domain;

/**
 * A domain that has informations that client request
 * Information will include genre that means genre vector, supporting actors, and plot
 * In this project genre vector is consisted of with this format
 * ["Drama", "Adventure", "Action", "Comedy", "Horror", "Biography", "Crime", "Fantasy", "Family", "Sci-Fi", "Animation", "Romance", "Music", "Western", "Thriller", "History", "Mystery", "Sport", "Musical"]
 *
 * @param genre genre vector that can be made specific way
 * @param supporter supporting actors
 * @param plot plot of movie 
 * @return return one actor that has same name
 */
public class ActorRequest {
    private double[] genre;
    private String[] supporter;
    private String plot;

    public double[] getGenre() {
        return genre;
    }
    public void setGenre(double[] genre) {
        this.genre = genre;
    }

    public String[] getSupporter() {
        return supporter;
    }
    public void setSupporter(String[] supporter) {
        this.supporter = supporter;
    }

    public String getPlot() {
        return plot;
    }
    public void setPlot(String plot) {
        this.plot = plot;
    }
}
