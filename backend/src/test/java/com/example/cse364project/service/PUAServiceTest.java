package com.example.cse364project.service;

import com.example.cse364project.domain.Movie;
import com.example.cse364project.domain.Rating;
import com.example.cse364project.domain.User;
import com.example.cse364project.repository.MovieRepository;
import com.example.cse364project.repository.RatingRepository;
import com.example.cse364project.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.bson.Document;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PUAServiceTest {
    @Mock
    private MongoTemplate mongoTemplate;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private PUAService puaService;

    @Test
    public void testFindTopRatedMoviesByGenre() {
        List<String> genres = Arrays.asList("Action", "Comedy");
        Movie movie = new Movie("1", "Movie1", 2020, genres);
        PUAService.MovieAverage movieAverage = new PUAService.MovieAverage("1", 5.0);

        when(mongoTemplate.aggregate(any(Aggregation.class), eq("movies"), eq(Movie.class)))
                .thenReturn(new AggregationResults<>(Collections.singletonList(movie), new Document()));

        when(mongoTemplate.aggregate(any(Aggregation.class), eq("ratings"), eq(PUAService.MovieAverage.class)))
                .thenReturn(new AggregationResults<>(Collections.singletonList(movieAverage), new Document()));

        when(movieRepository.findById("1")).thenReturn(Optional.of(movie));

        List<PUAService.MovieDetail> results = puaService.findTopRatedMoviesByGenre(genres);

        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertEquals("Movie1", results.get(0).getMovie().getTitle());
        assertEquals(5.0, results.get(0).getAverageRating(), 0.01);
    }


    @Test
    public void testFindLowestRatedMoviesByGenre() {
        List<String> genres = Arrays.asList("Action", "Comedy");
        Movie movie = new Movie("1", "Movie1", 2020, genres);
        PUAService.MovieAverage movieAverage = new PUAService.MovieAverage("1", 1.0);

        when(mongoTemplate.aggregate(any(Aggregation.class), eq("movies"), eq(Movie.class)))
                .thenReturn(new AggregationResults<>(Collections.singletonList(movie), new Document()));

        when(mongoTemplate.aggregate(any(Aggregation.class), eq("ratings"), eq(PUAService.MovieAverage.class)))
                .thenReturn(new AggregationResults<>(Collections.singletonList(movieAverage), new Document()));

        when(movieRepository.findById("1")).thenReturn(Optional.of(movie));

        List<PUAService.MovieDetail> results = puaService.findLowestRatedMoviesByGenre(genres);

        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertEquals("Movie1", results.get(0).getMovie().getTitle());
        assertEquals(1.0, results.get(0).getAverageRating(), 0.01);
    }

}
