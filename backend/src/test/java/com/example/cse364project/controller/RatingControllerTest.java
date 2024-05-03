package com.example.cse364project.controller;

import com.example.cse364project.domain.Movie;
import com.example.cse364project.domain.Rating;
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
                .andExpect(jsonPath("$.content[0].id").value(movies.get(0).getId()))
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
                .andExpect(jsonPath("$.content[0].id").value(movies.get(0).getId()))
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
                .andExpect(jsonPath("$.content[0].id").value(movies.get(0).getId()))
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
                .andExpect(jsonPath("$.content[0].id").value(movies.get(0).getId()))
                .andExpect(status().isOk())
                .andDo(print());

        verify(movieRatingRepository, times(1)).findMoviesWithGTEAverageRatingAndGenreAndYear(eq(rating), eq(genre), eq(year));
    }

    @Test
    void testGetRatingById() throws Exception {
        // Arrange
        String id = "1";
        Rating rating = new Rating("1", "1", 4, "20324");
        rating.setId(id);
        when(ratingService.getRatingById(eq(id))).thenReturn(rating);

        // Assert
        mockMvc.perform(get("/ratings/id/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(rating.getId()))
                .andDo(print());

        verify(ratingService, times(1)).getRatingById(eq(id));
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


    @Test
    void testAddRating() throws Exception {
        // Arrange
        Rating rating = new Rating("1", "1", 4, "20324");
        rating.setId("1");
        when(ratingService.addRating(any(Rating.class))).thenReturn(rating);

        // Assert
        mockMvc.perform(post("/ratings")
                    .contentType("application/json")
                    .content("{\"id\": \"1\",\"movieId\":\"1\",\"userId\":\"1\",\"rate\":4,\"timestamp\":\"20324\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(rating.getId()))
                .andDo(print());

        verify(ratingService, times(1)).addRating(eq(rating));
    }

    @Test
    void testAddRating2() throws Exception {
        // Arrange
        Rating rating = new Rating("2", "1", 4, "20324");
        rating.setId("2");
        when(ratingService.addRating(any(Rating.class))).thenReturn(rating);

        // Assert
        mockMvc.perform(post("/ratings")
                    .contentType("application/json")
                    .content("{\"id\": \"2\", \"movieId\":\"2\",\"userId\":\"1\",\"rate\":4,\"timestamp\":\"20324\"}"))
                .andExpect(status().isCreated())
                .andDo(print());

        verify(ratingService, times(1)).addRating(eq(rating));
    }

    @Test
    void testUpdateRating() throws Exception {
        // Arrange
        String id = "1";
        Rating rating = new Rating("1", "1", 4, "20324");
        rating.setId(null);
        when(ratingService.updateRating(eq(id), any(Rating.class))).thenReturn(rating);

        // Assert
        mockMvc.perform(put("/ratings/id/{id}", id)
                    .contentType("application/json")
                    .content("{\"movieId\":\"1\",\"userId\":\"1\",\"rate\":4,\"timestamp\":\"20324\"}"))
                .andExpect(status().isOk())
                .andDo(print());

        verify(ratingService, times(1)).updateRating(eq(id), eq(rating));
    }

    @Test
    void testUpdateRating2() throws Exception {
        // Arrange
        String id = "2";
        Rating rating = new Rating("2", "1", 4, "20324");
        rating.setId(null);
        when(ratingService.updateRating(eq(id), any(Rating.class))).thenReturn(rating);

        // Assert
        mockMvc.perform(put("/ratings/id/{id}", id)
                    .contentType("application/json")
                    .content("{\"movieId\":\"2\",\"userId\":\"1\",\"rate\":4,\"timestamp\":\"20324\"}"))
                .andExpect(status().isOk())
                .andDo(print());

        verify(ratingService, times(1)).updateRating(eq(id), eq(rating));
    }

    @Test
    void testDeleteRating() throws Exception {
        // Arrange
        String id = "1";

        // Assert
        mockMvc.perform(delete("/ratings/id/{id}", id))
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(ratingService, times(1)).deleteRating(eq(id));
    }

    @Test
    void testDeleteRating2() throws Exception {
        // Arrange
        String id = "2";

        // Assert
        mockMvc.perform(delete("/ratings/id/{id}", id))
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(ratingService, times(1)).deleteRating(eq(id));
    }

    @Test
    void testPatchRating() throws Exception {
        // Arrange
        String id = "1";
        Rating rating = new Rating("1", "1", 4, "20324");
        rating.setId(id);
        when(ratingService.patchRating(eq(id), any(Rating.class))).thenReturn(rating);

        // Assert
        mockMvc.perform(patch("/ratings/id/{id}", id)
                    .contentType("application/json")
                    .content("{\"rate\":4}"))
                .andExpect(status().isOk())
                .andDo(print());

        verify(ratingService, times(1)).patchRating(eq(id), any(Rating.class));
    }

    @Test
    void testPatchRating2() throws Exception {
        // Arrange
        String id = "2";
        Rating rating = new Rating("2", "1", 4, "20324");
        rating.setId(id);
        when(ratingService.patchRating(eq(id), any(Rating.class))).thenReturn(rating);

        // Assert
        mockMvc.perform(patch("/ratings/id/{id}", id)
                    .contentType("application/json")
                    .content("{\"rate\":4}"))
                .andExpect(status().isOk())
                .andDo(print());

        verify(ratingService, times(1)).patchRating(eq(id), any(Rating.class));
    }

    @Test
    void testGetRatingsByMovieId() throws Exception {
        // Arrange
        String movieId = "1";
        List<Rating> ratings = Arrays.asList(new Rating("1", "1", 4, "20324"),
                new Rating("1", "2", 4, "20324"));
        when(ratingService.getRatingsByMovieId(eq(movieId))).thenReturn(ratings);

        // Assert
        mockMvc.perform(get("/ratings/movie/{movieId}", movieId))
                .andExpect(status().isOk())
                .andDo(print());

        verify(ratingService, times(1)).getRatingsByMovieId(eq(movieId));
    }

    @Test
    void testGetRatingsByMovieId2() throws Exception {
        // Arrange
        String movieId = "2";
        List<Rating> ratings = Arrays.asList(new Rating("2", "4", 4, "20324"),
                new Rating("2", "1", 4, "20324"));
        when(ratingService.getRatingsByMovieId(eq(movieId))).thenReturn(ratings);

        // Assert
        mockMvc.perform(get("/ratings/movie/{movieId}", movieId))
                .andExpect(status().isOk())
                .andDo(print());

        verify(ratingService, times(1)).getRatingsByMovieId(eq(movieId));
    }

    @Test
    void testGetRatingsByUserId() throws Exception {
        // Arrange
        String userId = "1";
        List<Rating> ratings = Arrays.asList(new Rating("1", "1", 4, "20324"),
                new Rating("2", "1", 4, "20324"));
        when(ratingService.getRatingsByUserId(eq(userId))).thenReturn(ratings);

        // Assert
        mockMvc.perform(get("/ratings/user/{userId}", userId))
                .andExpect(status().isOk())
                .andDo(print());

        verify(ratingService, times(1)).getRatingsByUserId(eq(userId));
    }

    @Test
    void testGetRatingsByUserId2() throws Exception {
        // Arrange
        String userId = "2";
        List<Rating> ratings = Arrays.asList(new Rating("1", "2", 4, "20324"),
                new Rating("2", "2", 4, "20324"));
        when(ratingService.getRatingsByUserId(eq(userId))).thenReturn(ratings);

        // Assert
        mockMvc.perform(get("/ratings/user/{userId}", userId))
                .andExpect(status().isOk())
                .andDo(print());

        verify(ratingService, times(1)).getRatingsByUserId(eq(userId));
    }
    // Add more test methods for other controller methods

}