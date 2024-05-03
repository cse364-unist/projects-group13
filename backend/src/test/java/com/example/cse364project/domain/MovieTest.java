package com.example.cse364project.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    void testAddGenrewithnull(){
        Movie movie = new Movie("1", "Movie Title", 2022, null);
        movie.addGenre("Action");
        assertEquals(1, movie.getGenres().size());
        assertTrue(movie.getGenres().contains("Action"));
    }

    @Test
    void testEquals() {
        List<String> genres = new ArrayList<>();
        genres.add("Action");
        Movie movie1 = new Movie("1", "Movie Title", 2022, genres);
        assertTrue(movie1.equals(movie1));

        Movie movie2 = new Movie("1", "Movie Title", 2022, genres);
        assertTrue(movie1.equals(movie2));

        Movie differentId = new Movie("2", "Movie Title", 2022, genres);
        assertFalse(movie1.equals(differentId));

        Movie differentTitle = new Movie("1", "Different Title", 2022, genres);
        assertFalse(movie1.equals(differentTitle));

        Movie differentYear = new Movie("1", "Movie Title", 2023, genres);
        assertFalse(movie1.equals(differentYear));

        List<String> differentGenres = new ArrayList<>();
        differentGenres.add("Drama");
        Movie differentGenresMovie = new Movie("1", "Movie Title", 2022, differentGenres);
        assertFalse(movie1.equals(differentGenresMovie));

        Movie allSame = new Movie("1", "Movie Title", 2022, genres);
        assertTrue(movie1.equals(allSame));

        Object otherObject = new Object(); 

        assertFalse(movie1.equals(null));
        assertFalse(movie1.equals(otherObject));
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