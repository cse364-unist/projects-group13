package com.example.cse364project.part3.domain;

import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ratings")
public class Rating {
    @Id
    private String id;
    private String movieId;
    private String userId;
    private int rate;
    private String timestamp;

    public Rating(String movieId, String userId, int rate, String timestamp) {
        this.movieId = movieId;
        this.userId = userId;
        this.rate = rate;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getUserId() {
        return userId;
    }

    public int getRate() {
        return rate;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "id='" + id + '\'' +
                ", movieId=" + movieId +
                ", userId=" + userId +
                ", rate=" + rate +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rating rating = (Rating) o;
        return movieId == rating.movieId &&
                userId == rating.userId &&
                rate == rating.rate &&
                timestamp == rating.timestamp &&
                Objects.equals(id, rating.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, movieId, userId, rate, timestamp);
    }
}