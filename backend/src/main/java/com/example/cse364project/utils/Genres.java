package com.example.cse364project.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Genres {
    private List<String> genres;

    public Genres() {
        this.genres = new ArrayList<>();
    }

    public Genres(String genre) {
        this.genres = new ArrayList<>();
        this.genres.add(genre);
    }

    public Genres(List<String> genres) {
        this.genres = genres;
        Collections.sort(this.genres);
    }

    public Genres(Set<String> validGenres) {
        this.genres = new ArrayList<>(validGenres);
        Collections.sort(this.genres);
    }

    public List<String> getGenres() {
        return this.genres;
    }
    
    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public void addGenre(String genre) {
        this.genres.add(genre);
        Collections.sort(this.genres);
    }

    public void addGenre(List<String> genres) {
        this.genres.addAll(genres);
        Collections.sort(this.genres);
    }

    public String get(int i) {
        return genres.get(i);
    }

    public int size() {
        return genres.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Genres)) return false;
        Genres inputGenres = (Genres) o;
        if (this.size() != inputGenres.size()) return false;
        for(int i = 0; i < this.size(); i++) {
            if (!Objects.equals(this.get(i), inputGenres.get(i))) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(genres);
    }

    @Override
    public String toString() {
        String k = "hash " + hashCode() + ": {";
        for (String g : genres) {
            k += g + ", ";
        }
        k += "size=" + size() + "}";
        return k;
    }
}
