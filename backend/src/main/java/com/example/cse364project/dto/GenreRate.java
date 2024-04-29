package com.example.cse364project.dto;

import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.example.cse364project.utils.Genres;

public class GenreRate extends RepresentationModel<GenreRate> {
    private List<String> genres;
    private double averageRating;
    private int frequency;

    public GenreRate(List<String> genres, double averageRating, int frequency) {
        this.genres = genres;
        this.averageRating = averageRating;
        this.frequency = frequency;
    }

    public GenreRate(Genres genres, double averageRating, int frequency) {
        this.genres = genres.getGenres();
        this.averageRating = averageRating;
        this.frequency = frequency;
    }

    public List<String> getGenres() {
        return genres;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    @SuppressWarnings("null")
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("GenreRate [genres=");
        builder.append(genres);
        builder.append(", averageRating=");
        builder.append(averageRating);
        builder.append(", frequency=");
        builder.append(frequency);
        builder.append(", links=");
        builder.append(getLinks());
        builder.append("]");
        return builder.toString();
    }
}