package com.example.cse364project.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MovieTest {
    private Movie movie;

    @BeforeEach
    void setUp() {
        List<String> genres = new ArrayList<>(Arrays.asList("Action", "Adventure"));
        movie = new Movie("1", "Movie Title", 2022, genres);
    }

    @Test
    void testAddGenre() {
        movie.addGenre("Comedy");
        Assertions.assertTrue(movie.getGenres().contains("Comedy"));
    }

    @Test
    void testAddGenre2() {
        movie.addGenre("Comedy");
        Assertions.assertTrue(movie.getGenres().contains("Comedy"));
        Assertions.assertFalse(movie.getGenres().contains("Drama"));
    }

    @Test
    void testEquals() {
        Movie sameMovie = new Movie("1", "Movie Title", 2022, new ArrayList<>(Arrays.asList("Action", "Adventure")));
        Assertions.assertTrue(movie.equals(sameMovie));
    }

    @Test
    void testEquals2() {
        Assertions.assertEquals(movie, movie);
        Assertions.assertNotEquals(movie, null);
        Assertions.assertNotEquals(movie, Integer.valueOf(0));
    }

    @Test
    void testGetGenres() {
        List<String> genres = movie.getGenres();
        Assertions.assertEquals(2, genres.size());
        Assertions.assertTrue(genres.contains("Action"));
        Assertions.assertTrue(genres.contains("Adventure"));
    }

    @Test
    void testGetId() {
        Assertions.assertEquals("1", movie.getId());
    }

    @Test
    void testGetTitle() {
        Assertions.assertEquals("Movie Title", movie.getTitle());
    }

    @Test
    void testGetYear() {
        Assertions.assertEquals(2022, movie.getYear());
    }

    @Test
    void testHashCode() {
        Movie sameMovie = new Movie("1", "Movie Title", 2022, new ArrayList<>(Arrays.asList("Action", "Adventure")));
        Assertions.assertEquals(movie.hashCode(), sameMovie.hashCode());
    }

    @Test
    void testSetGenres() {
        List<String> newGenres = new ArrayList<>(Arrays.asList("Drama", "Romance"));
        movie.setGenres(newGenres);
        Assertions.assertEquals(newGenres, movie.getGenres());
    }

    @Test
    void testSetId() {
        movie.setId("2");
        Assertions.assertEquals("2", movie.getId());
    }

    @Test
    void testSetTitle() {
        movie.setTitle("New Movie Title");
        Assertions.assertEquals("New Movie Title", movie.getTitle());
    }

    @Test
    void testSetYear() {
        movie.setYear(2023);
        Assertions.assertEquals(2023, movie.getYear());
    }

    @Test
    void testToString() {
        String expected = "Movie{id='1', title='Movie Title', year=2022, genres=[Action, Adventure]}";
        Assertions.assertEquals(expected, movie.toString());
    }
}