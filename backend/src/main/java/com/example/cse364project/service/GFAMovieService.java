package com.example.cse364project.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import com.example.cse364project.repository.MovieRatingRepository;
import com.example.cse364project.repository.MovieRepository;
import com.example.cse364project.utils.Genres;
import com.example.cse364project.domain.Movie;
import com.example.cse364project.dto.GenreRate;
import com.example.cse364project.dto.MovieRate;
import com.example.cse364project.exception.MovieNotFoundException;

@Service
public class GFAMovieService {
    //private static final Logger log = LoggerFactory.getLogger(LTAMovieService.class);
    private final MovieRatingRepository movieRatingRepository;
    private final MovieRepository movieRepository;

    public GFAMovieService(MovieRatingRepository movieRatingRepository, MovieRepository movieRepository) {
        this.movieRatingRepository = movieRatingRepository;
        this.movieRepository = movieRepository;
    }

    private static Comparator<GenreRate> customcomparator = new Comparator<GenreRate>() { 
        @Override
        public int compare(GenreRate o1, GenreRate o2) {
            if (o1.getFrequency() > o2.getFrequency()) return -1;
            if (o1.getFrequency() < o2.getFrequency()) return 1;
            if (o1.getAverageRating() == o2.getAverageRating()) {
                if (o1.getGenres().size() == o2.getGenres().size()) {
                for (int i = 0; i < o1.getGenres().size(); i++) {
                    if (o1.getGenres().get(i).compareTo(o2.getGenres().get(i)) > 0) return -1;
                    if (o1.getGenres().get(i).compareTo(o2.getGenres().get(i)) < 0) return 1;
                }
                return 0;
                }
                if (o1.getGenres().size() > o2.getGenres().size()) return 1;
                if (o1.getGenres().size() < o2.getGenres().size()) return -1;
            }
            if (o1.getAverageRating() > o2.getAverageRating()) return -1;
            return 1;
        }
    };

    /**
     * The function `getGenreFrequency` retrieves the frequency of genres in a list of movies and
     * returns a sorted list of `GenreRate` objects.
     * 
     * @return The `getGenreFrequency` method returns a list of `GenreRate` objects, which represent
     * the frequency of each genre in the list of movies stored in the movie repository. The list is
     * sorted in descending order based on the frequency of each genre.
     */
    public List<GenreRate> getGenreFrequencyWithRatings() {
        Map<Genres, Pair<Double, Integer>> genreFrequencyWithRatings = new HashMap<>();
        List<GenreRate> result = new ArrayList<>();

        List<MovieRate> moviesWithAverageRating = movieRatingRepository.findAllRatings();

        for (MovieRate movieRate : moviesWithAverageRating) {
            Genres genres = getGenresByMovieId(movieRate.getMovieId());
            Pair<Double, Integer> value = genreFrequencyWithRatings.getOrDefault(genres, Pair.of(0.0, 0));
            double sumOfAverageRating = value.getFirst() + movieRate.getAverageRating();
            int count = value.getSecond() + 1;
            Pair<Double, Integer> newValue = Pair.of(sumOfAverageRating, count);
            genreFrequencyWithRatings.put(genres, newValue);
        }

        for (Map.Entry<Genres, Pair<Double, Integer>> entry : genreFrequencyWithRatings.entrySet()) {
            double sumRate = entry.getValue().getFirst();
            int frequency = entry.getValue().getSecond();
            if (frequency == 0) continue;
            double avgRate = sumRate / frequency;
            result.add(new GenreRate(entry.getKey(), avgRate, frequency));
        }

        Collections.sort(result, customcomparator);

        return result;
    }

    /**
     * The function `getGenreFrequencyWithRatingsIncludingYear` calculates the average ratings of genre
     * combinations for movies released in a specific year and returns a sorted list of GenreRate
     * objects.
     * 
     * @param year The method `getGenreFrequencyWithRatingsIncludingYear(int year)` is designed to return
     * a list of `GenreRate` objects that represent combinations of genres with their average ratings
     * for movies released in a specific year.
     * @return The method `getGenreFrequencyWithRatingsIncludingYear(int year)` returns a list of
     * `GenreRate` objects, which represent combinations of genres with their average ratings and
     * frequency of occurrence in movies released in the specified `year`. The list is sorted based on
     * the frequency of occurrence, average rating, and genre combinations in a specific order.
     */
    public List<GenreRate> getGenreFrequencyWithRatingsIncludingYear(int year) {
        Map<Genres, Pair<Double, Integer>> genreCombinationRatings = new HashMap<>();
        List<GenreRate> result = new ArrayList<>();

        List<MovieRate> moviesWithAverageRating = movieRatingRepository.findMoviesWithAverageRatingByYear(year);

        // Simply count
        for (MovieRate movieRate : moviesWithAverageRating) {
            Genres genres = getGenresByMovieId(movieRate.getMovieId());
            Pair<Double, Integer> value = genreCombinationRatings.getOrDefault(genres, Pair.of(0.0, 0));
             
            double sumOfAverageRating = value.getFirst() + movieRate.getAverageRating();
            int count = value.getSecond() + 1;

            Pair<Double, Integer> newValue = Pair.of(sumOfAverageRating, count);
            genreCombinationRatings.put(genres, newValue);
        }

        // Change map to list
        for (Map.Entry<Genres, Pair<Double, Integer>> entry : genreCombinationRatings.entrySet()) {
            double sumRate = entry.getValue().getFirst();
            int frequency = entry.getValue().getSecond();
            if (frequency == 0) continue;
            double avgRate = sumRate / frequency;
            result.add(new GenreRate(entry.getKey(), avgRate, frequency));
        }

        // Sort it
        // Frequency -> Average Rating -> Length -> Lexicographical
        Collections.sort(result, customcomparator);

        return result;
    }

    /**
     * The function `getGenreFrequencyWithRatingsIncludingGenre` calculates the average rating and
     * frequency of movies by genre, sorting the results based on frequency, average rating, length,
     * and lexicographical order.
     * 
     * @param genre The method `getGenreFrequencyWithRatingsIncludingGenre` takes a genre as input and
     * retrieves a list of `GenreRate` objects that include the average rating and frequency of movies
     * in that genre. The method first fetches a list of `MovieRate` objects with average ratings for
     * the specified genre
     * @return The method `getGenreFrequencyWithRatingsIncludingGenre` returns a list of `GenreRate`
     * objects, which represent the average rating and frequency of movies in a specific genre,
     * including the specified genre. The list is sorted based on frequency, average rating, length,
     * and lexicographical order.
     */
    public List<GenreRate> getGenreFrequencyWithRatingsIncludingGenre(String genre) {
        Map<Genres, Pair<Double, Integer>> genreCombinationRatings = new HashMap<>();
        List<GenreRate> result = new ArrayList<>();

        List<MovieRate> moviesWithAverageRating = movieRatingRepository.findMoviesWithAverageRatingByGenre(genre);

        // Simply count
        for (MovieRate movieRate : moviesWithAverageRating) {
            Genres genres = getGenresByMovieId(movieRate.getMovieId());
            Pair<Double, Integer> value = genreCombinationRatings.getOrDefault(genres, Pair.of(0.0, 0));
             
            double sumOfAverageRating = value.getFirst() + movieRate.getAverageRating();
            int count = value.getSecond() + 1;

            Pair<Double, Integer> newValue = Pair.of(sumOfAverageRating, count);
            genreCombinationRatings.put(genres, newValue);
        }

        // Change map to list
        for (Map.Entry<Genres, Pair<Double, Integer>> entry : genreCombinationRatings.entrySet()) {
            double sumRate = entry.getValue().getFirst();
            int frequency = entry.getValue().getSecond();
            if (frequency == 0) continue;
            double avgRate = sumRate / frequency;
            result.add(new GenreRate(entry.getKey(), avgRate, frequency));
        }

        // Sort it
        // Frequency -> Average Rating -> Length -> Lexicographical
        Collections.sort(result, customcomparator);

        return result;
    }
 
    /**
     * This function retrieves the genres of a movie by its ID from a movie repository.
     * 
     * @param movieId The `movieId` parameter is a unique identifier for a movie. It is used to
     * retrieve a specific movie from the movie repository.
     * @return An object of type Genres is being returned.
     */
    private Genres getGenresByMovieId(String movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException(movieId + " is not exist."));
        Genres genres = new Genres(movie.getGenres());
        return genres;
    }
}
