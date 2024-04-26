package com.example.cse364project.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class LTAMovieService {
    private static final Logger log = LoggerFactory.getLogger(LTAMovieService.class);
    private final MovieRatingRepository movieRatingRepository;
    private final MovieRepository movieRepository;

    private static final Set<String> VALID_GENRES = new HashSet<>(Arrays.asList(
        "Action", "Adventure", "Animation", "Children's", "Comedy", "Crime", "Documentary",
        "Drama", "Fantasy", "Film-Noir", "Horror", "Musical", "Mystery", "Romance", "Sci-Fi",
        "Thriller", "War", "Western"
    ));

    public LTAMovieService(MovieRatingRepository movieRatingRepository, MovieRepository movieRepository) {
        this.movieRatingRepository = movieRatingRepository;
        this.movieRepository = movieRepository;
    }

    // Greedy 사용하기
    public List<GenreRate> getGenreCombinationsWithAverageRatings(int year) {
        Map<Genres, Pair<Double, Integer>> genreCombinationRatings = initGenreCombinationRatings(); // 각 장르별 조합 맵
        List<GenreRate> result = new ArrayList<>();

        List<MovieRate> moviesWithAverageRating = movieRatingRepository.findMoviesWithAverageRatingByYear(year);

        log.info("Size of movie list: " + moviesWithAverageRating.size());

        for (MovieRate movieRate : moviesWithAverageRating) {
            Genres genres = getGenresByMovieId(movieRate.getMovieId());
            log.info(genres.toString());
            Pair<Double, Integer> value = genreCombinationRatings.getOrDefault(genres, Pair.of(0.0, 0));
            
            double averageRating = value.getFirst() + movieRate.getAverageRating();
            int count = value.getSecond() + 1;

            Pair<Double, Integer> newValue = Pair.of(averageRating, count);
            genreCombinationRatings.put(genres, newValue);
        }

        for (Map.Entry<Genres, Pair<Double, Integer>> entry : genreCombinationRatings.entrySet()) {
            result.add(new GenreRate(entry.getKey(), entry.getValue().getFirst(), entry.getValue().getSecond()));
        }

        Collections.sort(result, new Comparator<GenreRate>() {
            @Override
            public int compare(GenreRate o1, GenreRate o2) {
                if (o1.getAverageRating() / o1.getFrequency() == o2.getAverageRating() / o2.getFrequency()) {
                    if (o1.getGenres().size() == o2.getGenres().size()) {
                        for (int i = 0; i < o1.getGenres().size(); i++) {
                            if (o1.getGenres().get(i).compareTo(o2.getGenres().get(i)) > 0) return -1;
                            if (o1.getGenres().get(i).compareTo(o2.getGenres().get(i)) < 0) return 1;
                        }
                        return 0;
                    }
                }
                if (o1.getAverageRating() / o1.getFrequency() > o2.getAverageRating() / o2.getFrequency()) return -1;
                return 1;
            }
        });

        return result;
    }

    private Map<Genres, Pair<Double, Integer>> initGenreCombinationRatings() {
        Map<Genres, Pair<Double, Integer>> genreCombinationRatings = new HashMap<>();
        Set<Genres> allCombinations = new HashSet<>();

        for (int i = 1; i <= VALID_GENRES.size(); i++) {
            List<Genres> combinations = generateCombinations(new Genres(VALID_GENRES), i);
            allCombinations.addAll(combinations);
        }

        for (Genres combination : allCombinations) {
            genreCombinationRatings.put(combination, Pair.of(0.0, 0));
        }

        return genreCombinationRatings;
    }

    private Genres getGenresByMovieId(String movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException(movieId + " is not exist."));
        Genres genres = new Genres(movie.getGenres());
        return genres;
    }

    private List<Genres> generateCombinations(Genres genres, int size) {
        List<Genres> combinations = new ArrayList<>();
        generateCombinationsHelper(genres, size, 0, new ArrayList<>(), combinations);
        return combinations;
    }

    private void generateCombinationsHelper(Genres genres, int size, int start, List<String> currentCombination, List<Genres> combinations) {
        if (currentCombination.size() == size) {
            combinations.add(new Genres(currentCombination));
            return;
        }
        for (int i = start; i < genres.size(); i++) {
            currentCombination.add(genres.indexOf(i));
            generateCombinationsHelper(genres, size, i+1, currentCombination, combinations);
            currentCombination.remove(currentCombination.size() - 1);
        }
    }
}
