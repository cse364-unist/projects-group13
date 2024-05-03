package com.example.cse364project.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyInt;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.cse364project.domain.Movie;
import com.example.cse364project.domain.Rating;
import com.example.cse364project.exception.RatingNotFoundException;
import com.example.cse364project.repository.MovieRatingRepository;
import com.example.cse364project.repository.RatingRepository;

@ExtendWith(MockitoExtension.class)
public class RatingServiceTest {

    @Mock
    private MovieRatingRepository movieRatingRepository;

    @Mock
    private RatingRepository ratingRepository;

    @InjectMocks
    private RatingService ratingService;

    @Test
    public void getMoviesWithHighAverageRating_RatingThreshold_ReturnsListOfMovies() {
        // Given
        int ratingThreshold = 8;
        List<Movie> expectedMovies = new ArrayList<>();
        // Add some sample movies to the expected list
        expectedMovies.add(new Movie("1", "Movie1", 2022, List.of("Action")));
        expectedMovies.add(new Movie("2", "Movie2", 2023, List.of("Adventure")));

        // Mock the behavior of the movieRatingRepository
        when(movieRatingRepository.findMoviesWithGTEAverageRating(anyInt())).thenReturn(expectedMovies);

        // When
        List<Movie> result = ratingService.getMoviesWithHighAverageRating(ratingThreshold);

        // Then
        assertEquals(expectedMovies.size(), result.size());
        // You may want to assert more specific details of the returned movies here
    }

    @Test
    public void getRatingById_ExistingRating_ReturnsRating() {
        Rating expectedRating = new Rating("123", "456", 5, "2022-01-01");

        // Mock the behavior of ratingRepository
        when(ratingRepository.findById("123")).thenReturn(Optional.of(expectedRating));

        // When
        Rating result = ratingService.getRatingById("123");

        // Then
        assertEquals(expectedRating, result);
    }

    @Test
    public void getRatingById_NonExistingRating_ThrowsException() {
        // Given
        String ratingId = "999";

        // Mock the behavior of ratingRepository
        when(ratingRepository.findById(ratingId)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(RatingNotFoundException.class, () -> ratingService.getRatingById(ratingId));
    }
}
