package com.example.cse364project.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * A domain that control actor's information
 * Information will include actor's name, list of titles that actor appeared, counter, and genreScore
 * In this project genre vector is consisted of with this format
 * ["Drama", "Adventure", "Action", "Comedy", "Horror", "Biography", "Crime", "Fantasy", "Family", "Sci-Fi", "Animation", "Romance", "Music", "Western", "Thriller", "History", "Mystery", "Sport", "Musical"]
 *
 * @param name name of actor
 * @param titles titles of actor
 * @param count count of how many genre of movie is actor appeared 
 * @param genreScore the genre vector that is made from genre and rating
 * @return return one actor that has same name
 */
@Document(collection = "actors")
public class Actor {
    @Id
    private String name;
    private List<String> titles;
    private int[] count;
    private double[] genreScore;

    public Actor(String name, List<String> titles, int[] count, double[] genreScore) {
        this.name = name;
        this.titles = titles;
        this.count = count;
        this.genreScore = genreScore;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTitles() {
        return titles;
    }
    public void setTitle(List<String> titles) {
        this.titles = titles;
    }

    public int[] getCount() {
        return count;
    }
    public void setCount(int[] count) {
        this.count = count;
    }

    public double[] getGenre() {
        return genreScore;
    }
    public void setName(double[] genreScore) {
        this.genreScore = genreScore;
    }
}
