package com.example.cse364project.repository;

import com.example.cse364project.domain.Movie;
import com.example.cse364project.dto.MovieRate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MovieRatingRepositoryTest {

    @Mock
    private MongoTemplate mongoTemplate;

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieRatingRepository movieRatingRepository;

    private List<Movie> movies;
    private List<MovieRate> movieRates;
    private AggregationResults<MovieRate> aggregationResults;

    @BeforeEach
    void setUp() {
        movies = Arrays.asList(
                new Movie("1", "Movie 1", 2000, Arrays.asList("Action", "Adventure")),
                new Movie("2", "Movie 2", 2010, Arrays.asList("Drama", "Romance")),
                new Movie("3", "Movie 3", 2005, Arrays.asList("Action", "Sci-Fi")),
                new Movie("4", "Movie 4", 2015, Arrays.asList("Comedy", "Drama")),
                new Movie("5", "Movie 5", 2020, Arrays.asList("Action", "Adventure"))
        );

        movieRates = Arrays.asList(
                new MovieRate("1", 4.5),
                new MovieRate("2", 3.8),
                new MovieRate("3", 4.2),
                new MovieRate("4", 3.5),
                new MovieRate("5", 4.0)
        );
        aggregationResults = (AggregationResults<MovieRate>) mock(AggregationResults.class);
    }

    @Test
    void testFindMoviesWithGTEAverageRating() {
        when(mongoTemplate.aggregate(any(Aggregation.class), any(String.class), any(Class.class))).thenReturn(aggregationResults);
        when(movieRepository.findById("1")).thenReturn(Optional.of(movies.get(0)));
        when(movieRepository.findById("3")).thenReturn(Optional.of(movies.get(2)));
        when(movieRepository.findById("5")).thenReturn(Optional.of(movies.get(4)));
        
        List<MovieRate> filteredMovieRates = new ArrayList<>();
        for (MovieRate movieRate : movieRates) {
            if (Double.compare(movieRate.getAverageRating(), 4.0) >= 0) {
                filteredMovieRates.add(movieRate);
            }
        }
        when(aggregationResults.getMappedResults()).thenReturn(filteredMovieRates);

        List<Movie> result = movieRatingRepository.findMoviesWithGTEAverageRating(4);
        assertEquals(3, result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals("3", result.get(1).getId());
    }

    List<String> getGenres(String id) {
        for (Movie movie : movies) {
            if (movie.getId().equals(id)) {
                return movie.getGenres();
            }
        }
        return null;
    }

    int getYearOfTest(String id) {
        for (Movie movie : movies) {
            if (movie.getId().equals(id)) {
                return movie.getYear();
            }
        }
        return -1;
    }

    @Test
    void testFindMoviesWithGTEAverageRatingAndGenreAndYear() {
        when(mongoTemplate.aggregate(any(Aggregation.class), any(String.class), any(Class.class))).thenReturn(aggregationResults);
        when(movieRepository.findById("1")).thenReturn(Optional.of(movies.get(0)));
        when(movieRepository.findById("3")).thenReturn(Optional.of(movies.get(2)));
        when(movieRepository.findById("5")).thenReturn(Optional.of(movies.get(4)));

        List<MovieRate> filteredMovieRates = new ArrayList<>();
        for (MovieRate movieRate : movieRates) {
            if (Double.compare(movieRate.getAverageRating(), 4.0) >= 0) {
                filteredMovieRates.add(movieRate);
            }
        }
        when(aggregationResults.getMappedResults()).thenReturn(filteredMovieRates);

        List<Movie> result = movieRatingRepository.findMoviesWithGTEAverageRatingAndGenreAndYear(4, Arrays.asList("Action"), 2005);
        assertEquals(1, result.size());
        assertEquals("3", result.get(0).getId());
    }

    @Test
    void testFindMoviesWithGTEAverageRatingAndGenreAndYearWithNullReturn() {
        when(mongoTemplate.aggregate(any(Aggregation.class), any(String.class), any(Class.class))).thenReturn(aggregationResults);
        when(movieRepository.findById("1")).thenReturn(Optional.of(movies.get(0)));
        when(movieRepository.findById("3")).thenReturn(Optional.of(movies.get(2)));
        when(movieRepository.findById("5")).thenReturn(Optional.of(movies.get(4)));

        List<MovieRate> filteredMovieRates = new ArrayList<>();
        for (MovieRate movieRate : movieRates) {
            if (Double.compare(movieRate.getAverageRating(), 4.0) >= 0) {
                filteredMovieRates.add(movieRate);
            }
        }
        when(aggregationResults.getMappedResults()).thenReturn(filteredMovieRates);

        List<Movie> result = movieRatingRepository.findMoviesWithGTEAverageRatingAndGenreAndYear(4, Arrays.asList("Action"), 2007);
        assertEquals(0, result.size());
    }

    @Test
    void testFindMoviesWithGTEAverageRatingAndGenreAndYearWithNullReturn2() {
        when(mongoTemplate.aggregate(any(Aggregation.class), any(String.class), any(Class.class))).thenReturn(aggregationResults);
        when(movieRepository.findById("1")).thenReturn(Optional.of(movies.get(0)));
        when(movieRepository.findById("3")).thenReturn(Optional.of(movies.get(2)));
        when(movieRepository.findById("5")).thenReturn(Optional.of(movies.get(4)));

        List<MovieRate> filteredMovieRates = new ArrayList<>();
        for (MovieRate movieRate : movieRates) {
            if (Double.compare(movieRate.getAverageRating(), 4.0) >= 0) {
                filteredMovieRates.add(movieRate);
            }
        }
        when(aggregationResults.getMappedResults()).thenReturn(filteredMovieRates);

        List<Movie> result = movieRatingRepository.findMoviesWithGTEAverageRatingAndGenreAndYear(4, Arrays.asList("Action", "Animation", "Sci-Fi"), 2000);
        assertEquals(0, result.size());
    }

    @Test
    void testFindMoviesWithGTEAverageRatingAndGenre() {
        when(mongoTemplate.aggregate(any(Aggregation.class), any(String.class), any(Class.class))).thenReturn(aggregationResults);
        when(movieRepository.findById("1")).thenReturn(Optional.of(movies.get(0)));
        when(movieRepository.findById("3")).thenReturn(Optional.of(movies.get(2)));
        when(movieRepository.findById("5")).thenReturn(Optional.of(movies.get(4)));

        List<MovieRate> filteredMovieRates = new ArrayList<>();
        for (MovieRate movieRate : movieRates) {
            if (Double.compare(movieRate.getAverageRating(), 4.0) >= 0) {
                filteredMovieRates.add(movieRate);
            }
        }
        when(aggregationResults.getMappedResults()).thenReturn(filteredMovieRates);

        List<Movie> result = movieRatingRepository.findMoviesWithGTEAverageRatingAndGenres(4, Arrays.asList("Action"));
        assertEquals(3, result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals("3", result.get(1).getId());
        assertEquals("5", result.get(2).getId());
    }

    @Test
    void testFindMoviesWithGTEAverageRatingAndGenreWithNullReturn1() {
        when(mongoTemplate.aggregate(any(Aggregation.class), any(String.class), any(Class.class))).thenReturn(aggregationResults);
        when(movieRepository.findById("1")).thenReturn(Optional.of(movies.get(0)));
        when(movieRepository.findById("3")).thenReturn(Optional.of(movies.get(2)));
        when(movieRepository.findById("5")).thenReturn(Optional.of(movies.get(4)));

        List<MovieRate> filteredMovieRates = new ArrayList<>();
        for (MovieRate movieRate : movieRates) {
            if (Double.compare(movieRate.getAverageRating(), 4.0) >= 0) {
                filteredMovieRates.add(movieRate);
            }
        }
        when(aggregationResults.getMappedResults()).thenReturn(filteredMovieRates);

        List<Movie> result = movieRatingRepository.findMoviesWithGTEAverageRatingAndGenres(4, Arrays.asList("Action", "Animation", "Sci-Fi"));
        assertEquals(0, result.size());
    }

    @Test
    void testFindMoviesWithGTEAverageRatingAndYear() {
        when(mongoTemplate.aggregate(any(Aggregation.class), any(String.class), any(Class.class))).thenReturn(aggregationResults);
        when(movieRepository.findById("1")).thenReturn(Optional.of(movies.get(0)));
        when(movieRepository.findById("3")).thenReturn(Optional.of(movies.get(2)));
        when(movieRepository.findById("5")).thenReturn(Optional.of(movies.get(4)));

        List<MovieRate> filteredMovieRates = new ArrayList<>();
        for (MovieRate movieRate : movieRates) {
            if (Double.compare(movieRate.getAverageRating(), 4.0) >= 0) {
                filteredMovieRates.add(movieRate);
            }
        }
        when(aggregationResults.getMappedResults()).thenReturn(filteredMovieRates);

        List<Movie> result = movieRatingRepository.findMoviesWithGTEAverageRatingAndYear(4, 2005);
        assertEquals(1, result.size());
        assertEquals("3", result.get(0).getId());
    }

    @Test
    void testFindAverageRatingByMovieId() {
        when(mongoTemplate.aggregate(any(Aggregation.class), any(String.class), any(Class.class))).thenReturn(aggregationResults);
        
        List<MovieRate> filterdMovieRates = new ArrayList<>();
        for (MovieRate movieRate : movieRates) {
            if (movieRate.getMovieId().equals("1")) {
                filterdMovieRates.add(movieRate);
            }
        }
        when(aggregationResults.getUniqueMappedResult()).thenReturn(filterdMovieRates.get(0));

        double result = movieRatingRepository.findAverageRatingByMovieId("1");
        assertEquals(4.5, result);
    }

    @Test
    void testFindAverageRatingByMovieIdWithNullResult() {
        when(mongoTemplate.aggregate(any(Aggregation.class), any(String.class), any(Class.class))).thenReturn(aggregationResults);
        
        List<MovieRate> filterdMovieRates = new ArrayList<>();
        for (MovieRate movieRate : movieRates) {
            if (movieRate.getMovieId().equals("6")) {
                filterdMovieRates.add(movieRate);
            }
        }
        when(aggregationResults.getUniqueMappedResult()).thenReturn(null);

        double result = movieRatingRepository.findAverageRatingByMovieId("6");
        assertEquals(0.0, result);
    }
}