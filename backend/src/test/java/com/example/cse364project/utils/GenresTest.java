package com.example.cse364project.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class GenresTest {
    private Genres genres;

    @BeforeEach
    void setUp() {
        genres = new Genres();
    }

    @Test
    void testAddGenre() {
        genres.addGenre("Action");
        Assertions.assertEquals(Arrays.asList("Action"), genres.getGenres());
    }

    @Test
    void testAddGenre2() {
        List<String> newGenres = new ArrayList<>(Arrays.asList("Comedy", "Drama"));
        genres.addGenre(newGenres);
        Assertions.assertEquals(Arrays.asList("Comedy", "Drama"), genres.getGenres());
    }

    @Test
    void testEquals() {
        Genres genres1 = new Genres(Arrays.asList("Action", "Comedy"));
        Genres genres2 = new Genres(Arrays.asList("Action", "Comedy"));
        Genres genres3 = new Genres(Arrays.asList("Comedy", "Animation"));
        Genres genres4 = new Genres("Action");
        Genres genres5 = new Genres(Set.of("Action", "Comedy"));
        List<String> genreList = new ArrayList<>(Arrays.asList("Action", "Comedy"));

        Assertions.assertEquals(genres1, genres1);
        Assertions.assertNotEquals(genres5, null);
        Assertions.assertNotEquals(genres5, genreList);
        Assertions.assertEquals(genres1, genres2);
        Assertions.assertNotEquals(genres1, genres3);
        Assertions.assertNotEquals(genres1, genres4);
        Assertions.assertEquals(genres1, genres5);
    }

    @Test
    void testGet() {
        genres.addGenre("Action");
        genres.addGenre("Comedy");
        Assertions.assertEquals("Action", genres.get(0));
        Assertions.assertEquals("Comedy", genres.get(1));
    }

    @Test
    void testGetGenres() {
        genres.addGenre("Action");
        genres.addGenre("Comedy");
        Assertions.assertEquals(Arrays.asList("Action", "Comedy"), genres.getGenres());
    }

    @Test
    void testHashCode() {
        Genres genres1 = new Genres(Arrays.asList("Action", "Comedy"));
        Genres genres2 = new Genres(Arrays.asList("Action", "Comedy"));
        Assertions.assertEquals(genres1.hashCode(), genres2.hashCode());
    }

    @Test
    void testSetGenres() {
        List<String> newGenres = new ArrayList<>(Arrays.asList("Action", "Comedy"));
        genres.setGenres(newGenres);
        Assertions.assertEquals(Arrays.asList("Action", "Comedy"), genres.getGenres());
    }

    @Test
    void testSize() {
        genres.addGenre("Action");
        genres.addGenre("Comedy");
        Assertions.assertEquals(2, genres.size());
    }

    @Test
    void testToString() {
        genres.addGenre("Action");
        genres.addGenre("Comedy");
        Assertions.assertEquals("hash " + genres.hashCode() + ": {Action, Comedy, size=2}", genres.toString());
    }
}