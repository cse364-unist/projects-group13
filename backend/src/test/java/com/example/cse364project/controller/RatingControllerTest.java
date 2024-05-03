package com.example.cse364project.controller;

import com.example.cse364project.domain.Movie;
import com.example.cse364project.repository.MovieRatingRepository;
import com.example.cse364project.service.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RatingControllerTest {

    @Mock
    private MovieRatingRepository movieRatingRepository;

    @Mock
    private RatingService ratingService;

    @InjectMocks
    private RatingController ratingController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new RatingController(ratingService, movieRatingRepository)).build();
    }

    @Test
    void testGetMoviesWithRatingNoGenreNoYear() throws Exception {
        // Arrange
        int rating = 4;
        List<Movie> movies = Arrays.asList(new Movie("1", "Movie 1", 2021, Arrays.asList("Action", "Thriller")),
                new Movie("2", "Movie 2", 2021, Arrays.asList("Action", "Thriller")));
        when(movieRatingRepository.findMoviesWithGTEAverageRating(eq(rating))).thenReturn(movies);

        // Assert
        mockMvc.perform(get("/ratings/{rating}", rating))
                .andExpect(status().isOk())
                .andDo(print());

        verify(movieRatingRepository, times(1)).findMoviesWithGTEAverageRating(eq(rating));
    }

    @Test
    void testGetMoviesWithRatingNoGenre() throws Exception {
        // Arrange
        int rating = 4;
        Integer year = 2021;
        List<Movie> movies = Arrays.asList(new Movie("1", "Movie 1", year, Arrays.asList("Action", "Thriller")),
                new Movie("2", "Movie 2", year, Arrays.asList("Action", "Thriller")));
        when(movieRatingRepository.findMoviesWithGTEAverageRatingAndYear(eq(rating), eq(year))).thenReturn(movies);

        // Assert
        mockMvc.perform(get("/ratings/{rating}", rating)
                    .param("year", "2021"))
                .andExpect(status().isOk())
                .andDo(print());

        verify(movieRatingRepository, times(1)).findMoviesWithGTEAverageRatingAndYear(eq(rating), eq(year));
    }

    @Test
    void testGetMoviesWithRatingNoYear() throws Exception {
        // Arrange
        int rating = 4;
        List<String> genre = Arrays.asList("Action", "Thriller");
        List<Movie> movies = Arrays.asList(new Movie("1", "Movie 1", 2021, genre), new Movie("2", "Movie 2", 2021, genre));
        when(movieRatingRepository.findMoviesWithGTEAverageRatingAndGenres(eq(rating), eq(genre))).thenReturn(movies);

        // Assert
        mockMvc.perform(get("/ratings/{rating}", rating)
                    .param("genre", "Action", "Thriller"))
                .andExpect(status().isOk())
                .andDo(print());

        verify(movieRatingRepository, times(1)).findMoviesWithGTEAverageRatingAndGenres(eq(rating), eq(genre));
    }

    @Test
    void testGetMoviesWithRating() throws Exception {
        // Arrange
        int rating = 4;
        List<String> genre = Arrays.asList("Action", "Thriller");
        Integer year = 2021;
        List<Movie> movies = Arrays.asList(new Movie("1", "Movie 1", year, genre), new Movie("2", "Movie 2", year, genre));
        when(movieRatingRepository.findMoviesWithGTEAverageRatingAndGenreAndYear(eq(rating), eq(genre), eq(year)))
                .thenReturn(movies);

        // Assert
        mockMvc.perform(get("/ratings/{rating}", rating)
                    .param("genre", "Action", "Thriller")
                    .param("year", "2021"))
                .andExpect(status().isOk())
                .andDo(print());

        verify(movieRatingRepository, times(1)).findMoviesWithGTEAverageRatingAndGenreAndYear(eq(rating), eq(genre), eq(year));
    }

    @Test
    void testGetMoviesException() throws Exception {
        // Arrange
        int rating = 6;

        // Assert
        mockMvc.perform(get("/ratings/{rating}", rating))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void testGetMoviesException2() throws Exception {
        // Arrange
        int rating = 0;

        // Assert
        mockMvc.perform(get("/ratings/{rating}", rating))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
    // Add more test methods for other controller methods

}