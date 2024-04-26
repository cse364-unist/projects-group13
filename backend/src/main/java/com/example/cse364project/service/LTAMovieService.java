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
public class LTAMovieService {
    //private static final Logger log = LoggerFactory.getLogger(LTAMovieService.class);
    private final MovieRatingRepository movieRatingRepository;
    private final MovieRepository movieRepository;

    public LTAMovieService(MovieRatingRepository movieRatingRepository, MovieRepository movieRepository) {
        this.movieRatingRepository = movieRatingRepository;
        this.movieRepository = movieRepository;
    }

    // Greedy 사용하기
    public List<GenreRate> getGenreCombinationsWithAverageRatings(int year) {
        Map<Genres, Pair<Double, Integer>> genreCombinationRatings = new HashMap<>();// 각 장르별 조합 맵
        List<GenreRate> result = new ArrayList<>();

        List<MovieRate> moviesWithAverageRating = movieRatingRepository.findMoviesWithAverageRatingByYear(year);

        for (MovieRate movieRate : moviesWithAverageRating) {
            Genres genres = getGenresByMovieId(movieRate.getMovieId());
            Pair<Double, Integer> value = genreCombinationRatings.getOrDefault(genres, Pair.of(0.0, 0));
            
            double averageRating = value.getFirst() + movieRate.getAverageRating();
            int count = value.getSecond() + 1;

            Pair<Double, Integer> newValue = Pair.of(averageRating, count);
            genreCombinationRatings.put(genres, newValue);
        }

        for (Map.Entry<Genres, Pair<Double, Integer>> entry : genreCombinationRatings.entrySet()) {
            double sumRate = entry.getValue().getFirst();
            int frequency = entry.getValue().getSecond();
            if (frequency == 0) continue;
            double avgRate = sumRate / frequency;
            result.add(new GenreRate(entry.getKey(), avgRate, frequency));
        }

        Collections.sort(result, new Comparator<GenreRate>() {
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
                }
                if (o1.getAverageRating() > o2.getAverageRating()) return -1;
                return 1;
            }
        });

        return result;
    }

    private Genres getGenresByMovieId(String movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException(movieId + " is not exist."));
        Genres genres = new Genres(movie.getGenres());
        return genres;
    }
}
