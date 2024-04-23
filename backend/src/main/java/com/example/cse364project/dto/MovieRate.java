package com.example.cse364project.dto;

public class MovieRate {
    private String id; // movieID
    private double averageRating;

    public MovieRate(String id, double averageRating) {
        this.id = id;
        this.averageRating = averageRating;
    }

    // getters
    public String getMovieId() {
        return id;
    }

    public double getAverageRating() {
        return averageRating;
    }

    // setters
    public void setMovieId(String id) {
        this.id = id;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    @Override
    public String toString() {
        return "{ id: " + id + ", averageRating: " + averageRating + "}"; 
    }
}
