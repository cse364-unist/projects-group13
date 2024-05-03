package com.example.cse364project.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.cse364project.domain.Movie;
import com.example.cse364project.dto.GenreRate;
import com.example.cse364project.dto.MovieRate;
import com.example.cse364project.repository.MovieRatingRepository;
import com.example.cse364project.repository.MovieRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class GFAServiceTest {

    @Mock
    private MovieRatingRepository movieRatingRepository;

    @Mock
    private MovieRepository movieRepository;

    private GFAService gfaService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        gfaService = new GFAService(movieRatingRepository, movieRepository);
    }

    @Test
    public void testGetGenreFrequencyWithRatings() {
        // Arrange
        List<MovieRate> movieRates = Arrays.asList(
                new MovieRate("1", 4.5),
                new MovieRate("2", 3.8),
                new MovieRate("3", 4.2),
                new MovieRate("4", 4.0),
                new MovieRate("5", 3.5)
        );

        List<Movie> movies = Arrays.asList(
                new Movie("1", "Movie 1", 2021, Arrays.asList("Action")),
                new Movie("2", "Movie 2", 2021, Arrays.asList("Action")),
                new Movie("3", "Movie 3", 2021, Arrays.asList("Action")),
                new Movie("4", "Movie 4", 2021, Arrays.asList("Action")),
                new Movie("5", "Movie 5", 2021, Arrays.asList("Action"))
        );

        when(movieRatingRepository.findAllRatings()).thenReturn(movieRates);
        when(movieRepository.findById(eq("1"))).thenReturn(Optional.of(movies.get(0)));
        when(movieRepository.findById(eq("2"))).thenReturn(Optional.of(movies.get(1)));
        when(movieRepository.findById(eq("3"))).thenReturn(Optional.of(movies.get(2)));
        when(movieRepository.findById(eq("4"))).thenReturn(Optional.of(movies.get(3)));
        when(movieRepository.findById(eq("5"))).thenReturn(Optional.of(movies.get(4)));

        // Act
        List<GenreRate> genreRates = gfaService.getGenreFrequencyWithRatings();

        // Assert
        assertEquals(1, genreRates.size());
        // Add more assertions based on your expected results
    }

    @Test
    public void testGetGenreFrequencyWithRatingsIncludingYear() {
        // Arrange
        int year = 2021;
        List<MovieRate> movieRates = Arrays.asList(
                new MovieRate("1", 4.5),
                new MovieRate("2", 3.8),
                new MovieRate("3", 4.2),
                new MovieRate("4", 4.0),
                new MovieRate("5", 3.5)
        );
        List<Movie> movies = Arrays.asList(
                new Movie("1", "Movie 1", 2021, Arrays.asList("Action")),
                new Movie("2", "Movie 2", 2021, Arrays.asList("Action")),
                new Movie("3", "Movie 3", 2021, Arrays.asList("Action")),
                new Movie("4", "Movie 4", 2021, Arrays.asList("Action")),
                new Movie("5", "Movie 5", 2021, Arrays.asList("Action"))
        );
        when(movieRatingRepository.findMoviesWithAverageRatingByYear(year)).thenReturn(movieRates);
        when(movieRepository.findById(eq("1"))).thenReturn(Optional.of(movies.get(0)));
        when(movieRepository.findById(eq("2"))).thenReturn(Optional.of(movies.get(1)));
        when(movieRepository.findById(eq("3"))).thenReturn(Optional.of(movies.get(2)));
        when(movieRepository.findById(eq("4"))).thenReturn(Optional.of(movies.get(3)));
        when(movieRepository.findById(eq("5"))).thenReturn(Optional.of(movies.get(4)));

        // Act
        List<GenreRate> genreRates = gfaService.getGenreFrequencyWithRatingsIncludingYear(year);

        // Assert
        assertEquals(1, genreRates.size());
        // Add more assertions based on your expected results
    }

    @Test
    public void testGetGenreFrequencyWithRatingsIncludingGenre() {
        // Arrange
        String genre = "Action";
        List<MovieRate> movieRates = Arrays.asList(
                new MovieRate("1", 4.5),
                new MovieRate("2", 3.8),
                new MovieRate("3", 4.2),
                new MovieRate("4", 4.0),
                new MovieRate("5", 3.5)
        );
        List<Movie> movies = Arrays.asList(
                new Movie("1", "Movie 1", 2021, Arrays.asList("Action")),
                new Movie("2", "Movie 2", 2021, Arrays.asList("Action")),
                new Movie("3", "Movie 3", 2021, Arrays.asList("Action")),
                new Movie("4", "Movie 4", 2021, Arrays.asList("Action")),
                new Movie("5", "Movie 5", 2021, Arrays.asList("Action"))
        );
        when(movieRatingRepository.findMoviesWithAverageRatingByGenre(genre)).thenReturn(movieRates);
        when(movieRepository.findById(eq("1"))).thenReturn(Optional.of(movies.get(0)));
        when(movieRepository.findById(eq("2"))).thenReturn(Optional.of(movies.get(1)));
        when(movieRepository.findById(eq("3"))).thenReturn(Optional.of(movies.get(2)));
        when(movieRepository.findById(eq("4"))).thenReturn(Optional.of(movies.get(3)));
        when(movieRepository.findById(eq("5"))).thenReturn(Optional.of(movies.get(4)));

        // Act
        List<GenreRate> genreRates = gfaService.getGenreFrequencyWithRatingsIncludingGenre(genre);

        // Assert
        assertEquals(1, genreRates.size());
        // Add more assertions based on your expected results
    }

    @Test
    void testComparator() {
        // Arrange
        GenreRate genreRate1 = new GenreRate(Arrays.asList("Animation"), 4.5, 5);
        GenreRate genreRate2 = new GenreRate(Arrays.asList("Action"), 4.0, 5);

        // Act
        int result = GFAService.customcomparator.compare(genreRate1, genreRate2);

        // Assert
        assertEquals(-1, result);
    }

    @Test
    void testComparator2() {
        List<GenreRate> genreRates = new ArrayList<>();
        genreRates.add(new GenreRate(Arrays.asList("Drama", "Sci-Fi"), 4.0, 5));
        genreRates.add(new GenreRate(Arrays.asList("Animation"), 4.5, 5));
        genreRates.add(new GenreRate(Arrays.asList("Action", "Animation"), 4.0, 6));
        genreRates.add(new GenreRate(Arrays.asList("Drama"), 2.8, 87));
        genreRates.add(new GenreRate(Arrays.asList("Comedy"), 1.0, 3));
        genreRates.add(new GenreRate(Arrays.asList("Action"), 4.2, 1));
        genreRates.add(new GenreRate(Arrays.asList("Action"), 4.7, 2));
        genreRates.add(new GenreRate(Arrays.asList("Action"), 4.0, 6));
        genreRates.add(new GenreRate(Arrays.asList("Comedy"), 4.0, 6));

        List<GenreRate> expected = new ArrayList<>();
        expected.add(new GenreRate(Arrays.asList("Drama"), 2.8, 87));
        expected.add(new GenreRate(Arrays.asList("Action"), 4.0, 6));
        expected.add(new GenreRate(Arrays.asList("Comedy"), 4.0, 6));
        expected.add(new GenreRate(Arrays.asList("Action", "Animation"), 4.0, 6));
        expected.add(new GenreRate(Arrays.asList("Animation"), 4.5, 5));
        expected.add(new GenreRate(Arrays.asList("Drama", "Sci-Fi"), 4.0, 5));
        expected.add(new GenreRate(Arrays.asList("Comedy"), 1.0, 3));
        expected.add(new GenreRate(Arrays.asList("Action"), 4.2, 1));
        expected.add(new GenreRate(Arrays.asList("Action"), 4.7, 2));

        genreRates.sort(GFAService.customcomparator);

        assertEquals(9, genreRates.size());
        assertEquals(genreRates, expected);
    }

    @Test
    void compareByFrequency() {
        GenreRate gr1 = new GenreRate(Arrays.asList("Action"), 10, 100);
        GenreRate gr2 = new GenreRate(Arrays.asList("Comedy"), 5, 100);
        assertTrue(GFAService.customcomparator.compare(gr1, gr2) < 0);
    }

    @Test
    void compareByAverageRatingWhenFrequencyEqual() {
        GenreRate gr1 = new GenreRate(Arrays.asList("Action"), 10, 90);
        GenreRate gr2 = new GenreRate(Arrays.asList("Comedy"), 10, 95);
        assertTrue(GFAService.customcomparator.compare(gr1, gr2) > 0);
    }

    @Test
    void compareByGenreSizeWhenFrequencyAndRatingEqual() {
        GenreRate gr1 = new GenreRate(Arrays.asList("Action", "Adventure"), 10, 95);
        GenreRate gr2 = new GenreRate(Arrays.asList("Comedy"), 10, 95);
        assertTrue(GFAService.customcomparator.compare(gr1, gr2) > 0);
    }

    @Test
    void compareByGenreOrderWhenAllElseEqual() {
        GenreRate gr1 = new GenreRate(Arrays.asList("Action", "Adventure"), 10, 95);
        GenreRate gr2 = new GenreRate(Arrays.asList("Adventure", "Comedy"), 10, 95);
        assertTrue(GFAService.customcomparator.compare(gr1, gr2) > 0);
    }

    @Test
    void compareByGenreListSizeWhenOtherFactorsEqual() {
        GenreRate gr1 = new GenreRate(Arrays.asList("Action"), 10, 95);
        GenreRate gr2 = new GenreRate(Arrays.asList("Action", "Comedy"), 10, 95);
        assertTrue(GFAService.customcomparator.compare(gr1, gr2) < 0);
    }

    @Test
    void compareGenresWhenFrequenciesAndRatingsAreEqualAndGenresAreIdentical() {
        GenreRate gr1 = new GenreRate(Arrays.asList("Action", "Comedy"), 10, 95);
        GenreRate gr2 = new GenreRate(Arrays.asList("Action", "Comedy"), 10, 95);
        assertEquals(0, GFAService.customcomparator.compare(gr1, gr2));
    }
}