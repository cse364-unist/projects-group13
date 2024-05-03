package com.example.cse364project.service;
/* 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
=import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.cse364project.domain.Movie;
import com.example.cse364project.dto.GenreRate;
import com.example.cse364project.dto.MovieRate;
import com.example.cse364project.exception.MovieNotFoundException;
import com.example.cse364project.repository.MovieRatingRepository;
import com.example.cse364project.repository.MovieRepository;
import com.example.cse364project.service.GFAService;
import com.example.cse364project.utils.Genres;

public class GFAServiceTest {

    @Mock
    private MovieRatingRepository movieRatingRepository;

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private GFAService gfaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetGenreFrequencyWithRatings() {
        // Mock data
        List<MovieRate> movieRates = Arrays.asList(
            new MovieRate("1", 4.5),
            new MovieRate("2", 3.8),
            new MovieRate("3", 4.0)
        );

        List<String> genres = new ArrayList<>();
        genres.add("Action");

        // Mock behavior
        when(movieRatingRepository.findAllRatings()).thenReturn(movieRates);
        when(movieRepository.findById("1")).thenReturn(java.util.Optional.of(new Movie("1", "Test Movie 1", 2022,genres)));
        when(movieRepository.findById("2")).thenReturn(java.util.Optional.of(new Movie("2", "Test Movie 2", 2022,genres)));
        when(movieRepository.findById("3")).thenReturn(java.util.Optional.of(new Movie("3", "Test Movie 3", 2022, genres)));

        // Execute method
        List<GenreRate> genreRates = gfaService.getGenreFrequencyWithRatings();

        // Verify results
        assertEquals(2, genreRates.size()); // Assuming only two genres exist in the mock data
        // Add more assertions based on your expected behavior
    }

    // Add more test methods for other public methods in GFAService if needed
}
*/